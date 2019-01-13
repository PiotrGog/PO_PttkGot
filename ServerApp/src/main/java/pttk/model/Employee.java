package pttk.model;

import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name="pracownicy")
public class Employee extends User {


    @Column(unique = true)
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull
    @Id
    private int empId;

    @Column(nullable = false)
    @NotNull
    private boolean state;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(nullable = false)
    @NotNull
    private Date hire_date;

    public Employee() {}

    public int getEmpId() {
        return empId;
    }

    public void setEmpId(int empId) {
        this.empId = empId;
    }

    public boolean getState() {
        return state;
    }

    public String getStringState()
    {
        return state ? "Aktywny":"Nieaktywny";
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public Date getHire_date() {
        return hire_date;
    }

    public String getSimpleHire_date()
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        return dateFormat.format(hire_date);
    }

    public void setHire_date(Date hire_date) {
        this.hire_date = hire_date;
    }

    public void setHire_date(String hire_date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        this.hire_date = sdf.parse(hire_date);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return empId == employee.empId;
    }

    @Override
    public int hashCode() {

        return Objects.hash(empId);
    }

}
