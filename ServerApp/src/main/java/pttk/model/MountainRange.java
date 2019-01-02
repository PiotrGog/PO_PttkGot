package pttk.model;

import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.context.annotation.Primary;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="mountain_ranges")
public class MountainRange {

    @Id
    @UniqueElements
    @Column(name="id_mountain_range")
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    @Column(name="mountain_range_name")
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString(){
        return "id="+id+", name="+name;
    }
}
