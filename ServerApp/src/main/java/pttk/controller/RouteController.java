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


/**
 * Route Spring MVC controller performing automatic route planning with given criteria.
 */
@Controller
@RequestMapping("/route")
public class RouteController {
    @Autowired
    private RouteService routeService_;

    private List<Pair<Integer, GraphPathDecorator>> paths = null;

    private List<Integer> localizationsList = null;

    /**
     * Callback function to generate view with found paths.
     * @param model model for paths
     * @return view with paths list
     */
    @RequestMapping(value = "/find", method = RequestMethod.GET)
    public String findRoute(Model model) {
        model.addAttribute("routes", paths);
        return "route_planning_found";
    }

    /**
     * Callback function performing planning route with given criteria
     *
     * @param mountainRange  mountain range criteria
     * @param mountainGroup  mountain group criteria
     * @param locationStart  start location criteria
     * @param locationFinish finish location criteria
     * @param pointsAltitude minimum altitude points criteria
     * @param pointsDistance minimum distance points criteria
     * @param model          to receive found paths
     * @return view showing found paths
     */
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
            for (int i = 0; i < localizationsList.size(); i++) {
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
        if (null == locationFinish) {
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
        } else {
            paths = tmpPaths;
        }

        tmpPaths = paths;
        paths = new ArrayList<>();
        int index = 1;
        for (int i = 0; i < tmpPaths.size(); i++) {
            if (tmpPaths.get(i).getSecond().hasLocations(localizationsList)) {
                paths.add(Pair.of(index, tmpPaths.get(i).getSecond()));
                index++;
            }
        }

        localizationsList = null;
        model.addAttribute("routes", paths);
        return "route_planning_found";
    }

    /**
     * One route details
     * @param id route id
     * @param model model which receive path details
     * @return view
     */
    @RequestMapping(value = "/details/{id}", method = RequestMethod.GET)
    public String findRouteDetails(@PathVariable Integer id, Model model) {
        model.addAttribute("startLocation", paths.get(id - 1).getSecond()
                .locations.get(0).getSecond().getName());
        model.addAttribute("finishLocation", paths.get(id - 1).getSecond()
                .locations.get(paths.get(id - 1).getSecond()
                        .locations.size() - 1).getSecond().getName());
        model.addAttribute("currentIndex", id);
        model.addAttribute("pathSections", paths.get(id - 1).getSecond());
        model.addAttribute("size", paths.size());
        return "route_planning_found_details";
    }


    /**
     * Show view to define route planning criteria
     *
     * @param model view model
     * @return define route criteria view
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String findRouteCriteria(Model model) {
        model.addAttribute("mountainRanges", routeService_.findAllMountainRange());
        model.addAttribute("mountainGroups", routeService_.findAllMountainGroup());
        model.addAttribute("locations", routeService_.findAllLocation());
        return "route_planning_criteria";
    }

    /**
     * ModelAttribute function for wanted locations inside route
     *
     * @return empty ArrayList
     */
    @ModelAttribute(value = "localizationsList")
    public List<String> localizationsListModelAttribute() {
        return new ArrayList<>();
    }

    /**
     * Receive the list of wanted locations in route
     *
     * @param localizations locations ids
     * @return Request status
     */
    @RequestMapping(value = "/localizationOthers", method = RequestMethod.POST)
    public ResponseEntity receiveLocationsCriteria(@RequestBody List<Integer> localizations) {
        this.localizationsList = localizations;
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
