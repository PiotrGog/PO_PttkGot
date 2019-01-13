package pttk.repositories;

import org.springframework.data.jpa.repository.Query;
import pttk.model.Employee;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.Optional;

public interface EmployeeRepository extends CrudRepository<Employee, Long> {
    Iterable<Employee> findAllBySurname(String surnname);
    Iterable<Employee> findAllByNameOrSurname(String name, String surnmae);
    Iterable<Employee> findAllByName(String name);
    Employee findByLogin(String login);
    Optional<Employee> findByEmpId(int aLong);
    boolean existsByEmpId(int emp_id);
    boolean existsByPesel(String pesel);
    boolean existsByEmail(String email);
    boolean existsByNameOrSurname(String name, String surname);
}
