package pttk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pttk.model.Section;
import pttk.repositories.SectionRepository;

import java.util.ArrayList;

@Controller
public class SectionController {
    @Autowired
    private SectionRepository sectionRepository_;

    @RequestMapping(value = "/sections", method = RequestMethod.GET)
    public String listSections(Model model) {
        ArrayList<String> str = new ArrayList<>();
        for (Section m: sectionRepository_.findAll()) {
            str.add(m.toString());
        }
        model.addAttribute("mountainranges", str);
        return "mountain_ranges";
    }
}
