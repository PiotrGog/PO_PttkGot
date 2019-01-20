package pttk.service;

import org.jgrapht.GraphPath;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import pttk.model.Location;
import pttk.model.MountainGroup;
import pttk.model.MountainRange;
import pttk.model.Section;
import pttk.repositories.LocationRepository;
import pttk.repositories.MountainGroupRepository;
import pttk.repositories.MountainRangeRepository;
import pttk.repositories.SectionRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;


/**
 * RouteService class contains needed repositories and useful methods
 * which are called from RouteController class.
 */
@Service
public class RouteService {
    public Iterable<Section> sections = null;
    public Iterable<Location> locations = null;

    @Autowired
    private SectionRepository sectionRepository_;

    @Autowired
    private LocationRepository locationRepository_;

    @Autowired
    private MountainRangeRepository mountainRangeRepository_;

    @Autowired
    private MountainGroupRepository mountainGroupRepository_;

    /**
     * Return all mountain ranges from repository.
     *
     * @return Iterable collection of MountainRange
     */
    public Iterable<MountainRange> findAllMountainRange() {
        return mountainRangeRepository_.findAll();
    }

    /**
     * Return all mountain group from repository.
     *
     * @return Iterable collection of MountainGroup
     */
    public Iterable<MountainGroup> findAllMountainGroup() {
        return mountainGroupRepository_.findAll();
    }

    /**
     * Return all locations from repository.
     *
     * @return Iterable collection of Location
     */
    public Iterable<Location> findAllLocation() {
        return locationRepository_.findAll();
    }

    /**
     * Return all sections from repository.
     *
     * @return Iterable collection of Section
     */
    public Iterable<Section> findAllSection() {
        return sectionRepository_.findAll();
    }


    /**
     * Function builds graph from locations and sections between that locations.
     *
     * @return built graph from locations and sections in repository
     */
    public SimpleDirectedWeightedGraph<Integer, CustomWeightedEdge> buildGraph(Integer mountainRange,
                                                                               Integer mountainGroup) {
        SimpleDirectedWeightedGraph<Integer, CustomWeightedEdge> graph =
                new SimpleDirectedWeightedGraph<>(CustomWeightedEdge.class);
        sections = findAllSection();
        locations = findAllLocation();

        for (Location l : locations) {
            graph.addVertex(l.getId());
        }

        for (Section s : sections) {
            if ((null == mountainGroup || s.getMountainGroup().getId().equals(mountainGroup))
                    && (null == mountainRange || s.getMountainGroup().getMountainRange().getId().equals(mountainRange))) {
                CustomWeightedEdge edge = graph.addEdge(s.getLocationOne().getId(), s.getLocationTwo().getId());
                edge.setSection(s);
                graph.setEdgeWeight(edge, null==s.getDistance()?0:s.getDistance().doubleValue());
            }
        }

        return graph;
    }


    /**
     * Calculate altitude and distance points for graph path.
     *
     * @param graphPath is found graph path inside graph.
     * @return Pair which first value is sum of altitude points, second - sum of distance points in found GraphPath
     */
    public Pair<Integer, Integer> sumGraphPathPoints(GraphPath<Integer, CustomWeightedEdge> graphPath) {
        int altitudePoints = 0;
        int distancePoints = 0;
        for (CustomWeightedEdge edge : graphPath.getEdgeList()) {
            altitudePoints += edge.getSection().getPointsAltitude();
            distancePoints += edge.getSection().getPointsDistance();
        }
        return Pair.of(altitudePoints, distancePoints);
    }

    /**
     * Pack GraphPack with CustomWeightedEdge into GraphPackDecorator
     * @param routes list of GraphPaths with CustomWeightedEdge
     * @return List&lt;Pair&lt;Integer, GraphPathDecorator&gt;&gt; where GraphPathDecorator is class which packs GraphPaths from routes parameter
     */
    public List<Pair<Integer, GraphPathDecorator>> graphPathDecorate(List<GraphPath<Integer, CustomWeightedEdge>> routes)
    {
        List<Pair<Integer, GraphPathDecorator>> paths= new ArrayList<>();
        int graphPathIndex = 1;
        for (GraphPath<Integer, CustomWeightedEdge> route : routes) {
            List<CustomWeightedEdge> graphsEdges = route.getEdgeList();
//            if (!(2 <= graphsEdges.size()
//                    && !graphsEdges.get(0).getSection().getId().equals(graphsEdges.get(1).getSection().getId()))) {
//                continue;
//            }
            if(0 == graphsEdges.size())
            {
                continue;
            }
            GraphPathDecorator newPath = new GraphPathDecorator();
            int sec = 1;
            for (CustomWeightedEdge edge : graphsEdges) {
                newPath.sections.add(Pair.of(sec, edge.getSection()));
                sec++;
            }

            BiFunction<Iterable<Location>, Integer, Location> fLoc = (x, y) -> {
                for (Location l : x) {
                    if (l.getId().equals(y))
                        return l;
                }
                return null;
            };

            int loc = 1;
            for (Integer vertex : route.getVertexList()) {
                newPath.locations.add(Pair.of(loc, fLoc.apply(this.locations, vertex)));
                loc++;
            }


            Pair<Integer, Integer> points = this.sumGraphPathPoints(route);
            newPath.altitudePoints = points.getFirst();
            newPath.distancePoints = points.getSecond();

            paths.add(Pair.of(graphPathIndex, newPath));
            graphPathIndex++;
        }
        return paths;
    }
}
