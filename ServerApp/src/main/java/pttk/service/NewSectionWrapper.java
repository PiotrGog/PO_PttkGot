package pttk.service;


/**
 * Wrapper class for Section data. Class is used when communication between Server and Client is needed.
 */
public class NewSectionWrapper {
    private Integer mountainGroup;
    private Integer locationOne;
    private Integer locationTwo;
    private Integer distance;
    private Integer pointsAltitude;
    private Integer pointsDistance;

    /**
     * NewSectionWrapper parametrized constructor
     * @param mountainGroup section's mountain group id
     * @param locationOne section's start location
     * @param locationTwo section's finish location
     * @param distance section's distance
     * @param pointsAltitude section's altitude points
     * @param pointsDistance section's distance points
     */
    public NewSectionWrapper(Integer mountainGroup, Integer locationOne, Integer locationTwo, Integer distance,
                             Integer pointsAltitude, Integer pointsDistance) {
        this.mountainGroup = mountainGroup;
        this.locationOne = locationOne;
        this.locationTwo = locationTwo;
        this.distance = distance;
        this.pointsAltitude = pointsAltitude;
        this.pointsDistance = pointsDistance;
    }

    /**
     * NewSectionWrapper non-parametrized constructor
     */
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
