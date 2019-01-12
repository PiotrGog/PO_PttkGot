package pttk.controller;

import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.AllDirectedPaths;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pttk.Application;
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

    private List<Pair<Integer, GraphPathDecorator>> paths = null;

    private List<Integer> localizationsList= null;

    @RequestMapping(value = "/find", method = RequestMethod.GET)
    public String findRoute(Model model) {
        model.addAttribute("routes", paths);
        return "route_planning_found";
    }

    @RequestMapping(value = "/find", method = RequestMethod.POST)
    public String findRoute(@RequestParam Integer mountainRange,
                            @RequestParam Integer mountainGroup,
                            @RequestParam Integer locationStart,
                            @RequestParam Integer locationFinish,
                            @RequestParam Integer pointsAltitude,
                            @RequestParam Integer pointsDistance,
                            Model model) {

        if (localizationsList == null) {
            Application.log.info("localizationsList is null");
        } else {
            Application.log.info(localizationsList.size());
            for (int i = 0 ; i<localizationsList.size(); i++) {
                System.out.println(localizationsList.get(i));
            }
        }

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


        List<Pair<Integer, GraphPathDecorator>> tmpPaths = routeService_.graphPathDecorate(routes);

        if (null != pointsAltitude || null != pointsDistance) {
            pointsAltitude = (null == pointsAltitude) ? 0 : pointsAltitude;
            pointsDistance = (null == pointsDistance) ? 0 : pointsDistance;

            for (int i = 0; i < tmpPaths.size(); i++) {
                if (pointsAltitude <= tmpPaths.get(i).getSecond().altitudePoints
                        && pointsDistance <= tmpPaths.get(i).getSecond().distancePoints
                        && tmpPaths.get(i).getSecond().hasLocations(localizationsList)) {
                    paths.add(tmpPaths.get(i));
                }
            }
        }
        else {
            paths = tmpPaths;
        }

        tmpPaths = paths;
        paths = new ArrayList<>();
        for (int i = 0; i < tmpPaths.size(); i++) {
            if (tmpPaths.get(i).getSecond().hasLocations(localizationsList)) {
                paths.add(tmpPaths.get(i));
            }
        }

        localizationsList = null;
        model.addAttribute("routes", paths);
        return "route_planning_found";
    }

    @RequestMapping(value = "/details/{id}", method = RequestMethod.GET)
    public String findRouteDetails(@PathVariable Integer id, Model model) {
        model.addAttribute("startLocation", paths.get(id - 1).getSecond()
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

    @ModelAttribute(value = "localizationsList")
    public List<String> localizationsListModelAttribute() {
        return new ArrayList<>();
    }

    @RequestMapping(value = "/localizationOthers", method = RequestMethod.POST)
    public ResponseEntity receiveLocationsCriteria(@RequestBody List<Integer> localizations){
        this.localizationsList = localizations;
        return ResponseEntity.ok(HttpStatus.OK);
    }

}
