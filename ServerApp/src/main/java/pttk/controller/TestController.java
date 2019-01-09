package pttk.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import pttk.Application;
import pttk.service.Test;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/test")
public class TestController {

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String test() {
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
}
