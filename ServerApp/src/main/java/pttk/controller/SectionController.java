package pttk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pttk.model.Section;
import pttk.repositories.LocationRepository;
import pttk.repositories.MountainRangeRepository;
import pttk.repositories.SectionRepository;

import java.util.Optional;

@Controller
@RequestMapping("/sections")
public class SectionController {
    @Autowired
    private SectionRepository sectionRepository_;

    @Autowired
    private MountainRangeRepository mountainRangeRepository_;

    @Autowired
    private LocationRepository locationRepository_;

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
            model.addAttribute("section", section.get());
            model.addAttribute("mountainRanges", mountainRangeRepository_.findAll());
            model.addAttribute("locations", locationRepository_.findAll());
            model.addAttribute("mountainRange", section.get().getMountainGroup().getMountainRange());
            model.addAttribute("mountainGroup", section.get().getMountainGroup());
            model.addAttribute("localizationOne", section.get().getLocationOne());
            model.addAttribute("localizationTwo", section.get().getLocationTwo());
            model.addAttribute("distance", section.get().getDistance());
            model.addAttribute("altitudePoints", section.get().getPointsAltitude());
            model.addAttribute("distancePoints", section.get().getPointsDistance());
        }
        return "section_edit";
    }

    @RequestMapping(value = "/edit/{id}/save", method = RequestMethod.POST)
    public String updateSection(@PathVariable("id") Integer id, @RequestParam("altitudePoints") String ap, Model model) {
        Optional<Section> originalSection = null;//sectionRepository_.findById(id);
        System.out.println("update");
        if (originalSection.isPresent()) {
//            originalSection.get().setPointsAltitude(ap);
//            section.get().set
//            model.addAttribute("mountainRanges", mountainRangeRepository_.findAll());
//            model.addAttribute("locations", locationRepository_.findAll());
//            model.addAttribute("mountainRange", section.get().getMountainGroup().getMountainRange());
//            model.addAttribute("mountainGroup", section.get().getMountainGroup());
//            model.addAttribute("localizationOne", section.get().getLocationOne());
//            model.addAttribute("localizationTwo", section.get().getLocationTwo());
//            model.addAttribute("distance", section.get().getDistance());
//            model.addAttribute("altitudePoints", section.get().getPointsAltitude());
//            model.addAttribute("distancePoints", section.get().getPointsDistance());
            sectionRepository_.save(originalSection.get());
        }
        model.addAttribute("sections", sectionRepository_.findAll());
        return "sections";
    }
}
