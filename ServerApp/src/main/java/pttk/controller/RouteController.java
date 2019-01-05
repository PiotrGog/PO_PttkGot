package pttk.controller;

import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.AllDirectedPaths;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pttk.model.Section;
import pttk.service.CustomWeightedEdge;
import pttk.service.GraphPathDecorator;
import pttk.service.RouteService;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/route")
public class RouteController {
    @Autowired
    RouteService routeService_;

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
        List<GraphPathDecorator> paths = new ArrayList<>();

        for (GraphPath<Integer, CustomWeightedEdge> route : routes) {
            GraphPathDecorator newPath = new GraphPathDecorator();
            int sec = 1;
            for (CustomWeightedEdge edge : route.getEdgeList()) {
                newPath.sections.add(Pair.of(sec, edge.getSection()));
            }
            paths.add(newPath);
            System.out.println(route);
        }

        model.addAttribute("routes", paths);
        return "route_planning_found";
    }

}
