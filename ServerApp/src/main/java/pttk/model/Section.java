package pttk.model;

import javax.persistence.*;
import java.util.Set;


@Entity
@Table(name = "sections")
public class Section {
    @Id
    @Column(name = "id_section")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer sectionId;

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

    @OneToMany(mappedBy = "section")
    private Set<RouteSection> routeSections;

    public Set<RouteSection> getRouteSections() {
        return routeSections;
    }

    public void setRouteSections(Set<RouteSection> routeSections) {
        this.routeSections = routeSections;
    }


    public String getSectionInfo()
    {
        return locationOne.getName() + "->" + locationTwo.getName();
    }


    public Integer getSectionId() {
        return sectionId;
    }

    public void setSectionId(Integer sectionId) {
        this.sectionId = sectionId;
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
        return "sectionId=" + sectionId + ", location 1 " + locationOne.getName() + " location two "
                + locationTwo.getName() + " mountain " + mountainGroup;
    }
}
