package pttk.controller;

import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.AllDirectedPaths;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pttk.model.Location;
import pttk.model.Section;
import pttk.service.CustomWeightedEdge;
import pttk.service.GraphPathDecorator;
import pttk.service.RouteService;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

@Controller
@RequestMapping("/route")
public class RouteController {
    @Autowired
    RouteService routeService_;

    List<Pair<Integer, GraphPathDecorator>> paths = null;

    @RequestMapping(value = "/find", method = RequestMethod.GET)
    public String findRoute(/*@RequestParam Integer mountainRange,
                            @RequestParam Integer mountainGroup,
                            @RequestParam Integer locationStart,
                            @RequestParam Integer locationFinish,
                            @RequestParam ArrayList<Integer> locations,
                            @RequestParam Integer pointsAltitude,
                            @RequestParam Integer pointsDistance,*/
            Model model) {

        SimpleDirectedWeightedGraph<Integer, CustomWeightedEdge> graph = routeService_.buildGraph();

        AllDirectedPaths<Integer, CustomWeightedEdge> directedPaths = new AllDirectedPaths<>(graph);

        List<GraphPath<Integer, CustomWeightedEdge>> routes =
                directedPaths.getAllPaths(7, 11, true, null);
        paths = new ArrayList<>();

        int graphPathIndex = 1;
        for (GraphPath<Integer, CustomWeightedEdge> route : routes) {
            GraphPathDecorator newPath = new GraphPathDecorator();
            int sec = 1;
            for (CustomWeightedEdge edge : route.getEdgeList()) {
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
                newPath.locations.add(Pair.of(loc, fLoc.apply(routeService_.locations, vertex)));
                loc++;
            }


            Pair<Integer, Integer> points = routeService_.sumGraphPathPoints(route);
            newPath.altitudePoints = points.getFirst();
            newPath.distancePoints = points.getSecond();

            paths.add(Pair.of(graphPathIndex, newPath));
            System.out.println(route);
            graphPathIndex++;
        }

        model.addAttribute("routes", paths);
        return "route_planning_found";
    }

    @RequestMapping(value = "/details/{id}", method = RequestMethod.GET)
    public String findRoute(@PathVariable Integer id, Model model) {
        model.addAttribute("currentIndex", id);
        model.addAttribute("pathSections", paths.get(id - 1).getSecond());
        return "route_planning_found_details";
    }

}
