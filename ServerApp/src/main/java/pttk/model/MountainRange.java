package pttk.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="mounstain_ranges")
public class MountainRange {
    @Id
    @Column(name="IdMountainRange")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    @Column(name="MountainRangeName")
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
