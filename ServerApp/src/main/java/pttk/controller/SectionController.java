package pttk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pttk.Application;
import pttk.model.Section;
import pttk.repositories.LocationRepository;
import pttk.repositories.MountainGroupRepository;
import pttk.repositories.MountainRangeRepository;
import pttk.repositories.SectionRepository;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/sections")
public class SectionController {

    @Autowired
    private SectionRepository sectionRepository_;

    @Autowired
    private MountainRangeRepository mountainRangeRepository_;

    @Autowired
    private MountainGroupRepository mountainGroupRepository_;

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
            model.addAttribute("mountainGroups", mountainGroupRepository_.findAll());
            model.addAttribute("locations", locationRepository_.findAll());
        }
        return "section_edit";
    }

    @RequestMapping(value = "/edit/save/{id}", method = RequestMethod.POST)
    public String updateSection(@PathVariable("id") Integer id,
                                @RequestParam Integer mountainGroup,
                                @RequestParam Integer locationOne,
                                @RequestParam Integer locationTwo,
                                @RequestParam Integer distance,
                                @RequestParam Integer pointsAltitude,
                                @RequestParam Integer pointsDistance) {

        Application.log.info("mountainGroup=" + mountainGroup);
        Application.log.info("locationOne=" + mountainGroup);
        Application.log.info("locationOne=" + locationOne);
        Application.log.info("locationTwo=" + locationTwo);
        Application.log.info("distance=" + distance);
        Application.log.info("pointsAltitude=" + pointsAltitude);
        Application.log.info("pointsDistance=" + pointsDistance);

        Section newSection = new Section();
        newSection.setId(id);
        newSection.setLocationOne(locationRepository_.findById(locationOne).get());
        newSection.setLocationTwo(locationRepository_.findById(locationTwo).get());
        newSection.setDistance(distance);
        newSection.setPointsAltitude(pointsAltitude);
        newSection.setPointsDistance(pointsDistance);
        newSection.setMountainGroup(mountainGroupRepository_.findById(mountainGroup).get());
        Application.log.info("updated section = " + newSection);
        sectionRepository_.save(newSection);
        return "redirect:/sections";
    }

    @GetMapping(value = "/addSectionForm")
    public String showAddSectionForm(Model model) {
        System.out.println("showAddSectionForm");
        System.out.println("showAddSectionForm");
        model.addAttribute("mountainRanges", mountainRangeRepository_.findAll());
        model.addAttribute("mountainGroups", mountainGroupRepository_.findAll());
        model.addAttribute("locations", locationRepository_.findAll());
        return "section_add_new";
    }

    @GetMapping(value = "/filterSectionsForm")
    public String filterSectionForm(Model model) {
        System.out.println("showAddSectionForm");
        return "error";
    }

    @RequestMapping(value = "/addSection", method = RequestMethod.POST)
    public String addSection(@RequestParam Integer mountainGroup,
                             @RequestParam Integer locationOne,
                             @RequestParam Integer locationTwo,
                             @RequestParam Integer distance,
                             @RequestParam Integer pointsAltitude,
                             @RequestParam Integer pointsDistance) {
        Application.log.info("mountainGroup=" + mountainGroup);
        Application.log.info("locationOne=" + mountainGroup);
        Application.log.info("locationOne=" + locationOne);
        Application.log.info("locationTwo=" + locationTwo);
        Application.log.info("distance=" + distance);
        Application.log.info("pointsAltitude=" + pointsAltitude);
        Application.log.info("pointsDistance=" + pointsDistance);
        Section newSection = new Section();
        newSection.setLocationOne(locationRepository_.findById(locationOne).get());
        newSection.setLocationTwo(locationRepository_.findById(locationTwo).get());
        newSection.setDistance(distance);
        newSection.setPointsAltitude(pointsAltitude);
        newSection.setPointsDistance(pointsDistance);
        newSection.setMountainGroup(mountainGroupRepository_.findById(mountainGroup).get());
        Application.log.info("added section = " + newSection);
        sectionRepository_.save(newSection);
        return "redirect:/sections";
    }

    @RequestMapping(value = "/remove/{id}", method = RequestMethod.GET)
    public String removeSection(@PathVariable("id") Integer id) {
        Optional<Section> section = sectionRepository_.findById(id);
        sectionRepository_.delete(section.get());
        return "redirect:/sections";
    }


    @RequestMapping(value = "/removeAll", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public List<Integer> removeCheckedSection(@RequestBody List<Integer> sectionsToDelete) {
        for (Integer i : sectionsToDelete) {
            Application.log.info(i);
            Optional<Section> section = sectionRepository_.findById(i);
            sectionRepository_.delete(section.get());
        }
        return sectionsToDelete;
    }
}
