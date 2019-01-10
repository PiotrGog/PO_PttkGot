package pttk.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pttk.Application;
import pttk.service.StringContainer;
import pttk.service.StringWrapper;
import pttk.service.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/test")
public class TestController {

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String test(Model model) {
        model.addAttribute("stringContainer", new StringContainer(Arrays.asList(new StringWrapper("aaaa"))/*Arrays.asList("raz", "dwa", "trzy", "cztery")*/));
        return "test";
    }


    @RequestMapping(value = "/test2", method = RequestMethod.POST)
    public String test2() {
        return "index";
    }

    @RequestMapping(value = "/result", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public List<String> testResult(@RequestBody Test data) {
        Application.log.info("raz dwa trzy");
        Application.log.info(data.getRaz() + " " + data.getDwa());
        for (Integer i : data.getList()) {
            Application.log.info(i);
        }

        return new ArrayList<>();
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public void save(@ModelAttribute("stringContainer") StringContainer stringContainer) {
        if (stringContainer != null) {
            Application.log.info("SpringContainer is not null");
            if (null != stringContainer.getStringList()) {
                for (StringWrapper s : stringContainer.getStringList()) {
                    System.out.println(s.getString());
                }
            } else {
                Application.log.info("SpringContainer.getList() is null");
            }
        } else {
            Application.log.info("SpringContainer is null");
        }
    }

    @ModelAttribute(value = "stringContainer")
    public StringContainer newEntity() {
        return new StringContainer(Arrays.asList(new StringWrapper("jksdlajfkl")));
    }

    @ModelAttribute(value = "stringWrapper")
    public StringWrapper stringWrapper() {
        return new StringWrapper();
    }


    @RequestMapping(value = "/save1", method = RequestMethod.POST)
    public void save2(@ModelAttribute("stringWrapper") StringWrapper stringContainer) {
        if (stringContainer != null) {
            Application.log.info("SpringContainer is not null");
            System.out.println(stringContainer.getString());
        }

    }
}
