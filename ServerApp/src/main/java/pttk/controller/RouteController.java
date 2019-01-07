package pttk.controller;

import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.AllDirectedPaths;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import pttk.model.Location;
import pttk.service.CustomWeightedEdge;
import pttk.service.GraphPathDecorator;
import pttk.service.RouteService;

import java.util.*;
import java.util.function.BiFunction;

@Controller
@RequestMapping("/route")
public class RouteController {
    @Autowired
    private RouteService routeService_;

    List<Pair<Integer, GraphPathDecorator>> paths = null;

    @RequestMapping(value = "/find", method = RequestMethod.POST)
    public String findRoute(@RequestParam Integer mountainRange,
                            @RequestParam Integer mountainGroup,
                            @RequestParam Integer locationStart,
                            @RequestParam Integer locationFinish,
//                            @RequestParam(value = "localizationsList[]") List<Integer> localizationsList,
                            @RequestParam Integer pointsAltitude,
                            @RequestParam Integer pointsDistance,
                            Model model) {

        SimpleDirectedWeightedGraph<Integer, CustomWeightedEdge> graph =
                routeService_.buildGraph(mountainRange, mountainGroup);

        AllDirectedPaths<Integer, CustomWeightedEdge> directedPaths = new AllDirectedPaths<>(graph);

        Set<Integer> startLocations = null;
        if (null == locationStart) {
            startLocations = graph.vertexSet();
        } else {
            startLocations = new HashSet<>(Collections.singletonList(locationStart));
        }

        Set<Integer> finishLocations = null;
        if (null == locationStart) {
            finishLocations = graph.vertexSet();
        } else {
            finishLocations = new HashSet<>(Collections.singletonList(locationFinish));
        }

        List<GraphPath<Integer, CustomWeightedEdge>> routes =
                directedPaths.getAllPaths(startLocations, finishLocations, true, null);
        paths = new ArrayList<>();

//        int graphPathIndex = 1;
//        for (GraphPath<Integer, CustomWeightedEdge> route : routes) {
//            List<CustomWeightedEdge> graphsEdges = route.getEdgeList();
//            if (!(2 <= graphsEdges.size()
//                    && !graphsEdges.get(0).getSection().getId().equals(graphsEdges.get(1).getSection().getId()))) {
//                continue;
//            }
//            GraphPathDecorator newPath = new GraphPathDecorator();
//            int sec = 1;
//            for (CustomWeightedEdge edge : graphsEdges) {
//                newPath.sections.add(Pair.of(sec, edge.getSection()));
//                sec++;
//            }
//
//            BiFunction<Iterable<Location>, Integer, Location> fLoc = (x, y) -> {
//                for (Location l : x) {
//                    if (l.getId().equals(y))
//                        return l;
//                }
//                return null;
//            };
//
//            int loc = 1;
//            for (Integer vertex : route.getVertexList()) {
//                newPath.locations.add(Pair.of(loc, fLoc.apply(routeService_.locations, vertex)));
//                loc++;
//            }
//
//
//            Pair<Integer, Integer> points = routeService_.sumGraphPathPoints(route);
//            newPath.altitudePoints = points.getFirst();
//            newPath.distancePoints = points.getSecond();
//
//            paths.add(Pair.of(graphPathIndex, newPath));
//            graphPathIndex++;
//        }

        paths = routeService_.graphPathDecorate(routes);

        model.addAttribute("routes", paths);
        return "route_planning_found";
    }

    @RequestMapping(value = "/details/{id}", method = RequestMethod.GET)
    public String findRouteDetails(@PathVariable Integer id, Model model) {
        model.addAttribute("startLocation", paths.get(id- 1).getSecond()
                .locations.get(0).getSecond().getName());
        model.addAttribute("finishLocation", paths.get(id - 1).getSecond()
                .locations.get(paths.get(id - 1).getSecond()
                        .locations.size() - 1).getSecond().getName());
        model.addAttribute("currentIndex", id);
        model.addAttribute("pathSections", paths.get(id - 1).getSecond());
        return "route_planning_found_details";
    }


    @RequestMapping(value = "", method = RequestMethod.GET)
    public String findRouteCriteria(Model model) {
        model.addAttribute("mountainRanges", routeService_.findAllMountainRange());
        model.addAttribute("mountainGroups", routeService_.findAllMountainGroup());
        model.addAttribute("locations", routeService_.findAllLocation());
        return "route_planning_criteria";
    }


}
