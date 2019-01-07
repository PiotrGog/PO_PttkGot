package pttk.controller;

import org.springframework.http.MediaType;
import pttk.model.Employee;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pttk.repositories.EmployeeRepository;

@Controller
@RequestMapping(path = "/employees")
public class EmployeeController {

    @Autowired
    EmployeeRepository employeeRepository;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String getAllEmployees(Model model) {
        model.addAttribute("employees", employeeRepository.findAll());
        return "employees";
    }

    @GetMapping(path = "/{name}")
    public @ResponseBody
    Iterable<Employee> getEmployeesByName(@PathVariable(value = "name") String name) {
        return employeeRepository.findAllByName(name);
    }

    @GetMapping(path = "/{surname}")
    public @ResponseBody
    Iterable<Employee> getEmployeesBySurname(@PathVariable(value = "surname") String surname) {
        return employeeRepository.findAllBySurname(surname);
    }

    @RequestMapping(method = RequestMethod.POST, path = "")
    public @ResponseBody
    String addNewEmployee(@RequestBody Employee employee) {
        ServerResponse sr = new ServerResponse();
        try {
            employeeRepository.save(employee);
            sr.setResponseMsg("Saved");
        } catch (IllegalArgumentException exc) {
            sr.setResponseMsg(exc.getMessage());
        }
        return "employee_list";
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/{login}")
    public @ResponseBody
    String deleteEmployee(@PathVariable(value = "login") String login) {
        Employee empToUpdate = employeeRepository.findByLogin(login);
        empToUpdate.setState(false);
        employeeRepository.save(empToUpdate);
        return "OK";
    }

    @RequestMapping(method = RequestMethod.PUT, path="/{login}")
    public @ResponseBody
    Employee modifyEmployee(@RequestBody Employee modified)
    {
        employeeRepository.save(modified);
        return modified;
    }
}
