package pttk.repositories;

import pttk.model.Employee;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface EmployeeRepository extends CrudRepository<Employee, Long> {
    Iterable<Employee> findAllBySurname(String surnname);
    Iterable<Employee> findAllByName(String name);
    Employee findByLogin(String login);
}
