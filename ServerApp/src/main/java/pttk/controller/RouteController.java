package pttk.controller;

import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.AllDirectedPaths;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pttk.model.Section;
import pttk.service.RouteService;

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

        SimpleDirectedWeightedGraph<Integer, DefaultWeightedEdge> graph = routeService_.buildGraph();

        AllDirectedPaths<Integer, DefaultWeightedEdge> directedPaths = new AllDirectedPaths<>(graph);

        List<GraphPath<Integer, DefaultWeightedEdge>> routes =
                directedPaths.getAllPaths(7, 11, true, null);
        for (GraphPath<Integer, DefaultWeightedEdge> route : routes)
        {
            for(Integer v : route.getVertexList()) {
                System.out.println(route);
            }
        }

        model.addAttribute("routes", routes);
        return "route_planning_found";
    }

}
