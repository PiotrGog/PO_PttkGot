package pttk.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pttk.model.MountainRange;
import pttk.service.MountainRangeService;

@Controller
public class MountainRangeController {
    private MountainRangeService mountainRangeService_;

    @Autowired(required = true)
    @Qualifier(value = "mountainRangeService_")
    public void setPersonService(MountainRangeService mountainRangeService) {
        this.mountainRangeService_ = mountainRangeService;
    }

    @RequestMapping(value = "/mountainranges", method = RequestMethod.GET)
    public String listPersons(Model model) {
        model.addAttribute("person", new MountainRange());
        model.addAttribute("listPersons", this.mountainRangeService_.listMountainRange());
        return "mountainranges";
    }

    //For add and update person both
    @RequestMapping(value = "/mountainranges/add", method = RequestMethod.POST)
    public String addPerson(@ModelAttribute("person") MountainRange p) {

        if (p.getId() == 0) {
            //new person, add it
            this.mountainRangeService_.addMountainRage(p);
        } else {
            //existing person, call update
            this.mountainRangeService_.updateMountainRange(p);
        }

        return "redirect:/mountainranges";

    }

    @RequestMapping("/mountainranges/{id}")
    public String removePerson(@PathVariable("id") int id) {

        this.mountainRangeService_.removeMountainRange(id);
        return "redirect:/mountainranges";
    }

    @RequestMapping("/mountainranges/{id}")
    public String editPerson(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", this.mountainRangeService_.getMountainRangeById(id));
        model.addAttribute("listPersons", this.mountainRangeService_.listMountainRange());
        return "mountainranges";
    }


}
