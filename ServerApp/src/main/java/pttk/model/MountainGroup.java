package pttk.model;

import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;

@Entity
@Table(name = "mountain_groups")
public class MountainGroup {

    @Id
    @Column(name = "id_mountain_group")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "mountain_group_name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "fk_mountain_range_id")
    private MountainRange mountainRange;

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

    public MountainRange getMountainRange() {
        return mountainRange;
    }

    public void setMountainRange(MountainRange mountainRange) {
        this.mountainRange = mountainRange;
    }

    @Override
    public String toString() {
        return "id=" + id + ", name=" + name + ", mountain range=" + mountainRange;
    }
}
