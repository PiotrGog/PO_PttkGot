package pttk.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pttk.model.MountainRange;
import pttk.repositories.MountainRangeRepository;

import java.util.ArrayList;


/**
 * MountainRangeController class
 * Class is used to test access to mountain group repository
 */
@Controller
public class MountainRangeController {
    @Autowired
    private MountainRangeRepository mountainRangeRepository_;

    /**
     * Method called for get request method. Method is used to test access to mountain ranges
     * and print simple data about them.
     * @param model for attributes used in view
     * @return mountain_ranges view
     */
    @RequestMapping(value = "/mountainranges", method = RequestMethod.GET)
    public String listMountainRanges(Model model) {
        ArrayList<String> str = new ArrayList<>();
        for (MountainRange m: mountainRangeRepository_.findAll()) {
            str.add(m.toString());
        }
        model.addAttribute("mountainranges", str);
        return "mountain_ranges";
    }
}
