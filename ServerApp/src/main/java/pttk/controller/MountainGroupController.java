package pttk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pttk.model.MountainGroup;
import pttk.repositories.MountainGroupRepository;

import java.util.ArrayList;

@Controller
public class MountainGroupController {
    @Autowired
    private MountainGroupRepository mountainGroupRepository_;

    @RequestMapping(value = "/mountaingroups", method = RequestMethod.GET)
    public String listMountainGroups(Model model) {
        ArrayList<String> str = new ArrayList<>();
        for (MountainGroup m: mountainGroupRepository_.findAll()) {
            str.add(m.toString());
        }
        model.addAttribute("mountainranges", str);
        return "mountain_ranges";
    }

}
