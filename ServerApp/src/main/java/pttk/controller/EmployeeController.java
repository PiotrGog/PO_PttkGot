package pttk.controller;

import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import pttk.model.Employee;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pttk.repositories.EmployeeRepository;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping(path = "/employees")
public class EmployeeController {

    @Autowired
    EmployeeRepository employeeRepository;


    @GetMapping("")
    public String indexEmployee(@RequestParam(value = "keywords", defaultValue = "") String keyword, Model model) {
        boolean isDBEmpty = true;
        if (keyword.isEmpty()) {
            if (employeeRepository.count() > 0) {
                model.addAttribute("employees", employeeRepository.findAll());
                isDBEmpty = false;
            }
        } else {
            model.addAttribute("employees", employeeRepository.findAllByNameOrSurname(keyword, keyword));
            isDBEmpty = false;
        }
        model.addAttribute("isDBEmpty", isDBEmpty);
        return "employee_list";
    }

    @GetMapping("/{emp_id}/details")
    public String getEmployeeDetails(@PathVariable(value = "emp_id") int emp_id, Model model) {
        Optional<Employee> foundEmployee = employeeRepository.findByEmpId(emp_id);
        foundEmployee.ifPresent(employee -> model.addAttribute("employee", employee));
        foundEmployee.ifPresent(employee -> model.addAttribute("action", employee.getState() ? "Usuń" : "Przywróc"));
        return "employee_details";
    }

    @GetMapping(value = "/{emp_id}/update")
    public String getUpdateContext(@PathVariable(value = "emp_id") int emp_id, Model model) {
        Optional<Employee> foundEmployee = employeeRepository.findByEmpId(emp_id);
        foundEmployee.ifPresent(employee -> model.addAttribute("employee", employee));
        return "employee_update";
    }

    @PostMapping(value = "/{emp_id}/update")
    public String updateEmployee(@Valid @ModelAttribute Employee employee, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "employee_update";
        } else {
            employeeRepository.save(employee);
        }
        return "redirect:/employees";
    }

    @GetMapping(value = "{emp_id}/update_state")
    public String getDeleteEmployeeContext(@PathVariable(value = "emp_id") int emp_id, Model model) {
        Optional<Employee> foundEmployee = employeeRepository.findByEmpId(emp_id);
        foundEmployee.ifPresent(employee -> model.addAttribute("employee", employee));
        foundEmployee.ifPresent(employee -> model.addAttribute("action", employee.getState() ? "Usuń" : "Przywróc"));
        return "employee_update_state";
    }

    @PostMapping(value = "{emp_id}/update_state")
    public String deleteEmployee(@PathVariable(value = "emp_id") int emp_id, Model model) {
        Optional<Employee> foundEmployee = employeeRepository.findByEmpId(emp_id);
        foundEmployee.ifPresent(employee -> employee.setState(!employee.getState()));
        foundEmployee.ifPresent(employee -> employeeRepository.save(employee));
        return "redirect:/employees";
    }

    @GetMapping("/add")
    public String greetingForm(Model model) {
        model.addAttribute("employee", new Employee());
        return "employee_add";
    }

    @PostMapping("/add")
    public String addEmployee(@Valid @ModelAttribute Employee employee, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "employee_add";
        }
        if (employeeRepository.existsByEmail(employee.getEmail())) {
            FieldError error = new FieldError("email", "email",
                    "Email już istnieje");
            bindingResult.addError(error);
            return "employee_add";
        } else if (employeeRepository.existsByPesel(employee.getPesel())) {
            FieldError error = new FieldError("pesel", "pesel",
                    "Pesel już istnieje");
            bindingResult.addError(error);
            return "employee_add";
        } else
            employeeRepository.save(employee);
        return "redirect:/employees";
    }
}
