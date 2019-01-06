package pttk.service;

import org.jgrapht.GraphPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import pttk.model.Location;
import pttk.model.Section;
import pttk.repositories.LocationRepository;
import pttk.repositories.MountainGroupRepository;
import pttk.repositories.MountainRangeRepository;
import pttk.repositories.SectionRepository;


@Service
public class RouteService {
    public Iterable<Section> sections = null;
    public Iterable<Location> locations = null;

    @Autowired
    SectionRepository sectionRepository_;

    @Autowired
    LocationRepository locationRepository_;

    @Autowired
    MountainRangeRepository mountainRangeRepository_;

    @Autowired
    MountainGroupRepository mountainGroupRepository_;

    /**
     * Function builds graph from locations and sections between that locations.
     * @return built graph from locations and sections in repository
     */
    public SimpleDirectedWeightedGraph<Integer, CustomWeightedEdge> buildGraph() {
        SimpleDirectedWeightedGraph<Integer, CustomWeightedEdge> graph =
                new SimpleDirectedWeightedGraph<>(CustomWeightedEdge.class);
        sections = sectionRepository_.findAll();
        locations = locationRepository_.findAll();

        for (Location l : locations) {
            graph.addVertex(l.getId());
        }

        for (Section s : sections) {
            CustomWeightedEdge edge = graph.addEdge(s.getLocationOne().getId(), s.getLocationTwo().getId());
            edge.setSection(s);
            graph.setEdgeWeight(edge, s.getDistance().doubleValue());
        }

        return graph;
    }


    /**
     * Calculate altitude and distance points for graph path.
     * @param graphPath is found graph path inside graph.
     * @return Pair which first value is sum of altitude points, second - sum of distance points in found GraphPath
     */
    public Pair<Integer, Integer> sumGraphPathPoints(GraphPath<Integer, CustomWeightedEdge> graphPath)
    {
        int altitudePoints = 0;
        int distancePoints = 0;
        for (CustomWeightedEdge edge : graphPath.getEdgeList()) {
            altitudePoints+=edge.getSection().getPointsAltitude();
            distancePoints+=edge.getSection().getPointsDistance();
        }
        return Pair.of(altitudePoints, distancePoints);
    }
}
