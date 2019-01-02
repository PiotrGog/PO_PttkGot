package pttk.model;

import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;


@Entity
@Table(name = "sections")
public class Section {
    @Id
    @UniqueElements
    @Column(name = "id_section")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "points_altitude")
    private Integer pointsAltitude;

    @Column(name = "points_distance")
    private Integer pointsDistance;

    @Column(name = "distance")
    private Integer distance;

    @ManyToOne
    @JoinColumn(name = "fk_location_one_id")
    private Location locationOne;

    @ManyToOne
    @JoinColumn(name = "fk_location_two_id")
    private Location locationTwo;

    @ManyToOne
    @JoinColumn(name = "fk_mountain_group_id")
    private MountainGroup mountainGroup;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPointsAltitude() {
        return pointsAltitude;
    }

    public void setPointsAltitude(Integer pointsAltitude) {
        this.pointsAltitude = pointsAltitude;
    }

    public Integer getPointsDistance() {
        return pointsDistance;
    }

    public void setPointsDistance(Integer pointsDistance) {
        this.pointsDistance = pointsDistance;
    }

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    public Location getLocationOne() {
        return locationOne;
    }

    public void setLocationOne(Location locationOne) {
        this.locationOne = locationOne;
    }

    public Location getLocationTwo() {
        return locationTwo;
    }

    public void setLocationTwo(Location locationTwo) {
        this.locationTwo = locationTwo;
    }

    public MountainGroup getMountainGroup() {
        return mountainGroup;
    }

    public void setMountainGroup(MountainGroup mountainGroup) {
        this.mountainGroup = mountainGroup;
    }

    @Override
    public String toString() {
        return "id=" + id + ", location 1 " + locationOne.getName() + " location two "
                + locationTwo.getName() + " mountain " + mountainGroup;
    }
}
