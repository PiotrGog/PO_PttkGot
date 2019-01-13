package pttk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.HandlerMapping;
import pttk.model.*;
import pttk.repositories.*;

import java.util.*;

@Controller
@RequestMapping(path = "/report/{id}")
public class ReportController {
    @Autowired
    TripRepository tripRepository;

    @Autowired
    TouristRepository touristRepository;

    @Autowired
    LeaderRepository leaderRepository;

    @Autowired
    ReportRepository reportRepository;

    @Autowired
    MountainGroupRepository mountainGroupRepository;

    @GetMapping("")
    public String getPage(@PathVariable(value = "id") int touristId, @RequestParam(value = "trip", required = false) Integer tripId, Model model) {
        Tourist tourist = touristRepository.findByTouristId(touristId);
        Set<Trip> allTrips = tourist.getTouristTrips();
        Set<Trip> notReportedTrips = new HashSet<>();
        for (Trip t : allTrips) {
            int section_reported = 0;
            for (RouteSection rs : t.getRoute().getRouteSections()) {
                if (reportRepository.existsByRouteSection(rs))
                    section_reported++;
            }
            if (section_reported < t.getRoute().getRouteSections().size())
                notReportedTrips.add(t);
        }
        model.addAttribute("trips", notReportedTrips);
        model.addAttribute("tourist_id", touristId);
        if (tripId == null)
            model.addAttribute("selected_trip", new Trip());
        else {
            Trip selectedTrip = tripRepository.findByTripId(tripId);
            Set<RouteSection> routeSections = selectedTrip.getRoute().getRouteSections();
            ArrayList<Report> reports = new ArrayList<>();
            for (RouteSection rs : routeSections) {
                Report report = new Report();
                if (!reportRepository.existsByRouteSection(rs)) {
                    report.setRouteSection(rs);
                    report.setTrip(selectedTrip);
                    reports.add(report);
                }
            }
            ReportWrapper rp = null;
            if (!reports.isEmpty())
                rp = new ReportWrapper(reports);
            model.addAttribute("report_wrapper", rp);
        }
        return "report_form";
    }

    @PostMapping("/commit")
    public String commitReport(@PathVariable(value = "id") int touristId, @ModelAttribute("report_wrapper") ReportWrapper reportWrapper, Model model) {
        List<RouteSection> notReported = new ArrayList<>();
        for (Report r : reportWrapper.getReports()) {
            Leader leader = null;
            ArrayList<Leader> matching_leaders = leaderRepository.findByMG(r.getRouteSection().getSection().getMountainGroup());
            if (!matching_leaders.isEmpty()) {
                int item = new Random().nextInt(matching_leaders.size());
                leader = matching_leaders.get(item);
            }
            if (leader == null)
                notReported.add(r.getRouteSection());
            else {
                r.setLeader(leader);
                reportRepository.save(r);
            }
        }
        model.addAttribute("not_reported", notReported);
        model.addAttribute("tourist_id", touristId);
        return "report_after_add";
    }
}
