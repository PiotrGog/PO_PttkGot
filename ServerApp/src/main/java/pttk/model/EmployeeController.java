package pttk.controller;

import org.springframework.http.MediaType;
import pttk.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pttk.repositories.EmployeeRepository;

@Controller
@RequestMapping(path = "/api")
public class EmployeeController {

    @Autowired
    EmployeeRepository employeeRepository;

    @GetMapping(path = "/emp")
    public @ResponseBody
    Iterable<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @GetMapping(path = "/emp/{name}")
    public @ResponseBody
    Iterable<Employee> getEmployeesByName(@PathVariable(value = "name") String name) {
        return employeeRepository.findAllByName(name);
    }

    @GetMapping(path = "/emp/{surname}")
    public @ResponseBody
    Iterable<Employee> getEmployeesBySurname(@PathVariable(value = "surname") String surname) {
        return employeeRepository.findAllBySurname(surname);
    }

    @RequestMapping(method = RequestMethod.POST, path = "/emp")
    public @ResponseBody
    String addNewEmployee(@RequestBody Employee employee) {
        ServerResponse sr = new ServerResponse();
        try {
            employeeRepository.save(employee);
            sr.setResponseMsg("Saved");
        } catch (IllegalArgumentException exc) {
            sr.setResponseMsg(exc.getMessage());
        }
        return sr.toString();
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/emp/{login}")
    public @ResponseBody
    String deleteEmployee(@PathVariable(value = "login") String login) {
        Employee empToUpdate = employeeRepository.findByLogin(login);
        empToUpdate.setState(false);
        employeeRepository.save(empToUpdate);
        return "OK";
    }

    @RequestMapping(method = RequestMethod.PUT, path="/emp/{login}")
    public @ResponseBody
    Employee modifyEmployee(@RequestBody Employee modified)
    {
        employeeRepository.save(modified);
        return modified;
    }
}
