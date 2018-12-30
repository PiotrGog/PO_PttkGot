package models;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Employee extends User {

    @Column(unique = true)
    private int emp_id;

    @Column(nullable = false)
    private EmployeeState state;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(nullable = false)
    private Date hire_date;

    public Employee() {}

    public int getEmp_id() {
        return emp_id;
    }

    public void setEmp_id(int emp_id) {
        this.emp_id = emp_id;
    }

    public EmployeeState getState() {
        return state;
    }

    public void setState(EmployeeState state) {
        this.state = state;
    }

    public Date getHire_date() {
        return hire_date;
    }

    public void setHire_date(Date hire_date) {
        this.hire_date = hire_date;
    }

}
