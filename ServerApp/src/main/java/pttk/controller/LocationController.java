package pttk.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pttk.model.Location;
import pttk.repositories.LocationRepository;

import java.util.ArrayList;

@Controller
public class LocationController {
    @Autowired
    private LocationRepository locationRepository_;

    @RequestMapping(value = "/locations", method = RequestMethod.GET)
    public String listMountainRanges(Model model) {
        ArrayList<String> str = new ArrayList<>();
        for (Location m: locationRepository_.findAll()) {
            str.add(m.toString());
        }
        model.addAttribute("mountainranges", str);
        return "mountain_ranges";
    }
}
