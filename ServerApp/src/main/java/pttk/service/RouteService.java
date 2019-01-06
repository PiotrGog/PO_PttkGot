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

import java.util.List;


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
                graph.setEdgeWeight(edge, s.getDistance().doubleValue());
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


}
