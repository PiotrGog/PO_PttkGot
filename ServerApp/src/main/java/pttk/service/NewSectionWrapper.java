package pttk.service;

public class NewSectionWrapper {
    private Integer mountainGroup;
    private Integer locationOne;
    private Integer locationTwo;
    private Integer distance;
    private Integer pointsAltitude;
    private Integer pointsDistance;


    public NewSectionWrapper(Integer mountainGroup, Integer locationOne, Integer locationTwo, Integer distance,
                             Integer pointsAltitude, Integer pointsDistance) {
        this.mountainGroup = mountainGroup;
        this.locationOne = locationOne;
        this.locationTwo = locationTwo;
        this.distance = distance;
        this.pointsAltitude = pointsAltitude;
        this.pointsDistance = pointsDistance;
    }

    public NewSectionWrapper() {
    }

    public Integer getMountainGroup() {
        return mountainGroup;
    }

    public void setMountainGroup(Integer mountainGroup) {
        this.mountainGroup = mountainGroup;
    }

    public Integer getLocationOne() {
        return locationOne;
    }

    public void setLocationOne(Integer locationOne) {
        this.locationOne = locationOne;
    }

    public Integer getLocationTwo() {
        return locationTwo;
    }

    public void setLocationTwo(Integer locationTwo) {
        this.locationTwo = locationTwo;
    }

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
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
}
