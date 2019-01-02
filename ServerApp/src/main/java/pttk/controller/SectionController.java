package pttk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pttk.model.Section;
import pttk.repositories.SectionRepository;

import java.util.ArrayList;
import java.util.Optional;

@Controller
@RequestMapping("/sections")
public class SectionController {
    @Autowired
    private SectionRepository sectionRepository_;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String listSections(Model model) {
        model.addAttribute("sections", sectionRepository_.findAll());
        return "sections";
    }

    @RequestMapping(value = "/details/{id}", method = RequestMethod.GET)
    public String detailsSection(@PathVariable("id") Integer id, Model model) {
        Optional<Section> section = sectionRepository_.findById(id);
        if (section.isPresent()) {
            model.addAttribute("mountainRange", section.get().getMountainGroup().getMountainRange().getName());
            model.addAttribute("mountainGroup", section.get().getMountainGroup().getName());
            model.addAttribute("localizationOne", section.get().getLocationOne().getName());
            model.addAttribute("localizationTwo", section.get().getLocationTwo().getName());
            model.addAttribute("altitudeDiff", Math.abs(section.get().getLocationOne().getAltitude() -
                    section.get().getLocationTwo().getAltitude()));
            model.addAttribute("distance", section.get().getDistance());
            model.addAttribute("altitudePoints", section.get().getPointsAltitude());
            model.addAttribute("distancePoints", section.get().getPointsDistance());
        }
        return "section_details";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String editSection(@PathVariable("id") Integer id, Model model) {
        Optional<Section> section = sectionRepository_.findById(id);
        if (section.isPresent()) {
            model.addAttribute("mountainRange", section.get().getMountainGroup().getMountainRange().getName());
            model.addAttribute("mountainGroup", section.get().getMountainGroup().getName());
            model.addAttribute("localizationOne", section.get().getLocationOne().getName());
            model.addAttribute("localizationTwo", section.get().getLocationTwo().getName());
            model.addAttribute("altitudeDiff", Math.abs(section.get().getLocationOne().getAltitude() -
                    section.get().getLocationTwo().getAltitude()));
            model.addAttribute("distance", section.get().getDistance());
            model.addAttribute("altitudePoints", section.get().getPointsAltitude());
            model.addAttribute("distancePoints", section.get().getPointsDistance());
        }
        return "section_edit";
    }

}
