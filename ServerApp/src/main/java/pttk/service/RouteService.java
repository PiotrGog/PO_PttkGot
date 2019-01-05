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

    //BUILD GRAPH
    public SimpleDirectedWeightedGraph<Integer, DefaultWeightedEdge> buildGraph() {
        SimpleDirectedWeightedGraph<Integer, DefaultWeightedEdge> graph =
                new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);
        sections = sectionRepository_.findAll();
        locations = locationRepository_.findAll();

        for (Location l : locations) {
            graph.addVertex(l.getId());
        }

        for (Section s : sections) {
            DefaultWeightedEdge edge = graph.addEdge(s.getLocationOne().getId(), s.getLocationTwo().getId());
            graph.setEdgeWeight(edge, s.getDistance().doubleValue());
        }

        return graph;
    }


    public Pair<Integer, Integer> sumGraphPathPoints(GraphPath<Location, Section> graphPath)
    {
        int altitudePoints = 0;
        int distancePoints = 0;
//        for (DefaultWeightedEdge edge : graphPath.getEdgeList()) {
//            altitudePoints+=edge.getWeight();
//        }
        return Pair.of(altitudePoints, distancePoints);
    }
}
