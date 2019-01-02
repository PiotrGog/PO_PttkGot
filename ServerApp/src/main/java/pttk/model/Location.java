package pttk.model;

import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;

@Entity
@Table(name = "locations")
public class Location {

    @Id
    @UniqueElements
    @Column(name = "id_location")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "location_name")
    private String name;

    @Column(name="altitude")
    private Integer altitude;

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

    public Integer getAltitude() {
        return altitude;
    }

    public void setAltitude(Integer altitude) {
        this.altitude = altitude;
    }

    @Override
    public String toString() {
        return "id=" + id + ", name=" + name + ", altitude=" + altitude;
    }
}
