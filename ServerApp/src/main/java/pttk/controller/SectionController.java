package pttk.controller;

import org.aspectj.weaver.Iterators;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pttk.Application;
import pttk.model.Section;
import pttk.service.NewSectionWrapper;
import pttk.service.SectionFilter;
import pttk.service.SectionService;

import java.util.*;

/**
 * Spring MVC controller for Sections management.
 */

@Controller
@RequestMapping("/sections")
public class SectionController {

    @Autowired
    private SectionService sectionService_;

    private SectionFilter sectionFilter_ = new SectionFilter();

    /**
     * Request controller function to set sections filter policy.
     *
     * @param sectionFilter SectionFilter object received from client.
     * @return redirect to sections page.
     */
    @RequestMapping(value = "/filter", method = RequestMethod.POST)
    public String acceptFilter(@ModelAttribute(value = "sectionFilter") SectionFilter sectionFilter) {
        sectionFilter_ = sectionFilter;
        Application.log.info(sectionFilter_);
        return "redirect:/sections";
    }

    /**
     * Callback function which call client site with list of filtered sections
     *
     * @param model Client view model
     * @return show section view with sections list
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String listSections(Model model) {
        model.addAttribute("sections",
                sectionService_.filterSections(sectionService_.findAllSection(), sectionFilter_));
        return "sections";
    }

    /**
     * Request function to show section's details
     *
     * @param id    section id
     * @param model Client view model
     * @return view with detailed description of section
     */
    @RequestMapping(value = "/details/{id}", method = RequestMethod.GET)
    public String detailsSection(@PathVariable("id") Integer id, Model model) {
        Optional<Section> section = sectionService_.findByIdSection(id);
        if (section.isPresent()) {
            model.addAttribute("mountainRange", section.get().getMountainGroup().getMountainRange().getName());
            model.addAttribute("mountainGroup", section.get().getMountainGroup().getName());
            model.addAttribute("localizationOne", section.get().getLocationOne().getName());
            model.addAttribute("localizationTwo", section.get().getLocationTwo().getName());
            if (null == section.get().getLocationOne().getAltitude() ||
                    null == section.get().getLocationTwo().getAltitude()) {
                model.addAttribute("altitudeDiff", "--- ");
            } else {
                model.addAttribute("altitudeDiff", Math.abs(section.get().getLocationOne().getAltitude() -
                        section.get().getLocationTwo().getAltitude()));
            }
            model.addAttribute("distance", section.get().getDistance() == null ? "--- " : section.get().getDistance());
            model.addAttribute("altitudePoints", section.get().getPointsAltitude());
            model.addAttribute("distancePoints", section.get().getPointsDistance());
        }
        return "section_details";
    }

    /**
     * Request function on edit button for section
     *
     * @param id    section's id
     * @param model section's model
     * @return show view to edit section
     */
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String editSection(@PathVariable("id") Integer id, Model model) {
        Optional<Section> section = sectionService_.findByIdSection(id);
        if (section.isPresent()) {
            model.addAttribute("section", section.get());
            model.addAttribute("mountainRanges", sectionService_.findAllMountainRange());
            model.addAttribute("mountainGroups", sectionService_.findAllMountainGroup());
            model.addAttribute("locations", sectionService_.findAllLocation());
        }
        return "section_edit";
    }

    /**
     * Callback function to save edited section
     *
     * @param id                edited section id
     * @param newSectionReceive new data for edited section
     * @return ResponseEntity&lt;List&lt;Integer&gt;&gt;
     * if section is created HttpStatus is Created
     * if section has not been created HttpStatus is FAILED_DEPENDENCY and data list contains error numbers:
     * 1 - Location One and Location Two are the same
     * 2 - Distance is not defined
     * 3 - Mountain group is not defined
     * 4 - Database exception - section with the same start and finish locations exists in database
     */
    @RequestMapping(value = "/edit/save/{id}", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public ResponseEntity<List<Integer>> updateSection(@PathVariable("id") Integer id,
                                                       @RequestBody NewSectionWrapper newSectionReceive) {
        Application.log.info("mountainGroup=" + newSectionReceive.getMountainGroup());
        Application.log.info("locationOne=" + newSectionReceive.getLocationOne());
        Application.log.info("locationTwo=" + newSectionReceive.getLocationTwo());
        Application.log.info("distance=" + newSectionReceive.getDistance());
        Application.log.info("pointsAltitude=" + newSectionReceive.getPointsAltitude());
        Application.log.info("pointsDistance=" + newSectionReceive.getPointsDistance());
        List<Integer> errors = new ArrayList<>();
        HttpStatus status = HttpStatus.CREATED;
        if (Objects.equals(newSectionReceive.getLocationOne(), newSectionReceive.getLocationTwo())) {
            errors.add(1);
            status = HttpStatus.BAD_REQUEST;
        }
//        if (newSectionReceive.getDistance() == null) {
//            errors.add(2);
//            status = HttpStatus.BAD_REQUEST;
//        }
        if (newSectionReceive.getMountainGroup() == null) {
            errors.add(3);
            status = HttpStatus.BAD_REQUEST;
        }

        if (HttpStatus.CREATED == status) {
            try {
                Section newSection = new Section();
                newSection.setId(id);
                newSection.setLocationOne(sectionService_.findByIdLocation(newSectionReceive.getLocationOne()).get());
                newSection.setLocationTwo(sectionService_.findByIdLocation(newSectionReceive.getLocationTwo()).get());
                newSection.setDistance(newSectionReceive.getDistance());
                newSection.setPointsAltitude(newSectionReceive.getPointsAltitude());
                newSection.setPointsDistance(newSectionReceive.getPointsDistance());
                newSection.setMountainGroup(
                        sectionService_.findByIdMountainGroup(newSectionReceive.getMountainGroup()).get());
                Application.log.info("edited section = " + newSection);
                sectionService_.saveSection(newSection);
            } catch (Exception e) {
                System.err.println(e.getMessage());
                System.err.println(e.getCause());
                Application.log.info(e.getMessage());
                errors.add(4);
                status = HttpStatus.BAD_REQUEST;
            }
        }
        return ResponseEntity.status(status).body(errors);
    }

    /**
     * Request function to show form to allow defile new section
     *
     * @param model new section form model
     * @return view with form for new section
     */
    @GetMapping(value = "/addSectionForm")
    public String showAddSectionForm(Model model) {
        System.out.println("showAddSectionForm");
        System.out.println("showAddSectionForm");
        model.addAttribute("mountainRanges", sectionService_.findAllMountainRange());
        model.addAttribute("mountainGroups", sectionService_.findAllMountainGroup());
        model.addAttribute("locations", sectionService_.findAllLocation());
        return "section_add_new";
    }

    /**
     * @param model
     * @return
     */
    @GetMapping(value = "/filterSectionsForm")
    public String filterSectionForm(Model model) {
        model.addAttribute("mountainRanges", sectionService_.findAllMountainRange());
        model.addAttribute("mountainGroups", sectionService_.findAllMountainGroup());
        model.addAttribute("locations", sectionService_.findAllLocation());
        return "section_filter";
    }

    /**
     * Response method to add new section operation.
     *
     * @param newSectionReceive NewSectionWrapper which holds all required data to create new seciton.
     * @return ResponseEntity&lt;List&lt;Integer&gt;&gt;
     * if section is created HttpStatus is Created
     * if section has not been created HttpStatus is FAILED_DEPENDENCY and data list contains error numbers:
     * 1 - Location One and Location Two are the same
     * 2 - Distance is not defined
     * 3 - Mountain group is not defined
     * 4 - Database exception - section with the same start and finish locations exists in database
     */
    @RequestMapping(value = "/addSection", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public ResponseEntity<List<Integer>> addSection(@RequestBody NewSectionWrapper newSectionReceive) {
        Application.log.info("mountainGroup=" + newSectionReceive.getMountainGroup());
        Application.log.info("locationOne=" + newSectionReceive.getLocationOne());
        Application.log.info("locationTwo=" + newSectionReceive.getLocationTwo());
        Application.log.info("distance=" + newSectionReceive.getDistance());
        Application.log.info("pointsAltitude=" + newSectionReceive.getPointsAltitude());
        Application.log.info("pointsDistance=" + newSectionReceive.getPointsDistance());
        List<Integer> errors = new ArrayList<>();
        HttpStatus status = HttpStatus.CREATED;
        if (Objects.equals(newSectionReceive.getLocationOne(), newSectionReceive.getLocationTwo())) {
            errors.add(1);
            status = HttpStatus.BAD_REQUEST;
        }
//        if (newSectionReceive.getDistance() == null) {
//            errors.add(2);
//            status = HttpStatus.BAD_REQUEST;
//        }
        if (newSectionReceive.getMountainGroup() == null) {
            errors.add(3);
            status = HttpStatus.BAD_REQUEST;
        }

        if (HttpStatus.CREATED == status) {
            try {
                Section newSection = new Section();
                newSection.setLocationOne(sectionService_.findByIdLocation(newSectionReceive.getLocationOne()).get());
                newSection.setLocationTwo(sectionService_.findByIdLocation(newSectionReceive.getLocationTwo()).get());
                newSection.setDistance(newSectionReceive.getDistance());
                newSection.setPointsAltitude(newSectionReceive.getPointsAltitude());
                newSection.setPointsDistance(newSectionReceive.getPointsDistance());
                newSection.setMountainGroup(
                        sectionService_.findByIdMountainGroup(newSectionReceive.getMountainGroup()).get());
                Application.log.info("added section = " + newSection);
                sectionService_.saveSection(newSection);
            } catch (Exception e) {
                System.err.println(e.getMessage());
                System.err.println(e.getCause());
                Application.log.info(e.getMessage());
                errors.add(4);
                status = HttpStatus.BAD_REQUEST;
            }
        }
        return ResponseEntity.status(status).body(errors);
    }

    /**
     * Callback function performing the removal of section
     *
     * @param id is of section to remove
     * @return redirect to section main window
     */
    @RequestMapping(value = "/remove/{id}", method = RequestMethod.GET)
    public String removeSection(@PathVariable("id") Integer id) {
        Optional<Section> section = sectionService_.findByIdSection(id);
        sectionService_.deleteSection(section.get());
        return "redirect:/sections";
    }

    /**
     * Callback function performing the removal of selected sections
     *
     * @param sectionsToDelete list of sections' ids to remove
     * @return list of removed sections' ids
     */
    @RequestMapping(value = "/removeAll", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public List<Integer> removeCheckedSection(@RequestBody List<Integer> sectionsToDelete) {
        for (Integer i : sectionsToDelete) {
            Application.log.info(i);
            Optional<Section> section = sectionService_.findByIdSection(i);
            sectionService_.deleteSection(section.get());
        }
        return sectionsToDelete;
    }

    @ModelAttribute(value = "sectionFilter")
    public SectionFilter filter() {
        return this.sectionFilter_;
    }


    @RequestMapping(value = "/fillDefaultData", method = RequestMethod.GET)
    public String fillDatabase() {
        sectionService_.fillDatabase();
        return "/";
    }


}
