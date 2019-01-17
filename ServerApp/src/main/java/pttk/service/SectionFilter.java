package pttk.service;

/**
 * Class defining filters for sections.
 */
public class SectionFilter {

    /**
     * Filter parametric constructor
     * @param mountainRangeFilter mountain range id
     * @param groupRangeFilter mountain group id
     * @param localizationOneFilter localization id
     * @param localizationTwoFilter localization id
     * @param distanceMaxFilter maximum distance
     * @param distanceMinFilter minimum distance
     * @param distancePointsMaxFilter maximum distance points
     * @param distancePointsMinFilter minimum distance points
     * @param altitudePointsMaxFilter maximum altitude points
     * @param altitudePointsMinFilter minimum altitude points
     */
    public SectionFilter(Integer mountainRangeFilter,
                         Integer groupRangeFilter,
                         Integer localizationOneFilter,
                         Integer localizationTwoFilter,
                         Integer distanceMaxFilter,
                         Integer distanceMinFilter,
                         Integer distancePointsMaxFilter,
                         Integer distancePointsMinFilter,
                         Integer altitudePointsMaxFilter,
                         Integer altitudePointsMinFilter) {
        this.mountainRangeFilter = mountainRangeFilter;
        this.mountainGroupFilter = groupRangeFilter;
        this.localizationOneFilter = localizationOneFilter;
        this.localizationTwoFilter = localizationTwoFilter;
        this.distanceMaxFilter = distanceMaxFilter;
        this.distanceMinFilter = distanceMinFilter;
        this.distancePointsMaxFilter = distancePointsMaxFilter;
        this.distancePointsMinFilter = distancePointsMinFilter;
        this.altitudePointsMaxFilter = altitudePointsMaxFilter;
        this.altitudePointsMinFilter = altitudePointsMinFilter;
    }

    /**
     * Filter default non-parametric constructor
     */
    public SectionFilter() {
    }

    /**
     * To string function
     * @return filter string representation.
     */
    @Override
    public String toString() {
        return "SectionFilter{" +
                "mountainRangeFilter='" + mountainRangeFilter + '\'' +
                ", mountainGroupFilter='" + mountainGroupFilter + '\'' +
                ", localizationOneFilter='" + localizationOneFilter + '\'' +
                ", localizationTwoFilter='" + localizationTwoFilter + '\'' +
                ", distanceMaxFilter=" + distanceMaxFilter +
                ", distanceMinFilter=" + distanceMinFilter +
                ", distancePointsMaxFilter=" + distancePointsMaxFilter +
                ", distancePointsMinFilter=" + distancePointsMinFilter +
                ", altitudePointsMaxFilter=" + altitudePointsMaxFilter +
                ", altitudePointsMinFilter=" + altitudePointsMinFilter +
                '}';
    }

    public Integer getMountainRangeFilter() {
        return mountainRangeFilter;
    }

    public void setMountainRangeFilter(Integer mountainRangeFilter) {
        this.mountainRangeFilter = mountainRangeFilter;
    }

    public Integer getMountainGroupFilter() {
        return mountainGroupFilter;
    }

    public void setMountainGroupFilter(Integer mountainGroupFilter) {
        this.mountainGroupFilter = mountainGroupFilter;
    }

    public Integer getLocalizationOneFilter() {
        return localizationOneFilter;
    }

    public void setLocalizationOneFilter(Integer localizationOneFilter) {
        this.localizationOneFilter = localizationOneFilter;
    }

    public Integer getLocalizationTwoFilter() {
        return localizationTwoFilter;
    }

    public void setLocalizationTwoFilter(Integer localizationTwoFilter) {
        this.localizationTwoFilter = localizationTwoFilter;
    }

    public Integer getDistanceMaxFilter() {
        return distanceMaxFilter;
    }

    public void setDistanceMaxFilter(Integer distanceMaxFilter) {
        this.distanceMaxFilter = distanceMaxFilter;
    }

    public Integer getDistanceMinFilter() {
        return distanceMinFilter;
    }

    public void setDistanceMinFilter(Integer distanceMinFilter) {
        this.distanceMinFilter = distanceMinFilter;
    }

    public Integer getDistancePointsMaxFilter() {
        return distancePointsMaxFilter;
    }

    public void setDistancePointsMaxFilter(Integer distancePointsMaxFilter) {
        this.distancePointsMaxFilter = distancePointsMaxFilter;
    }

    public Integer getDistancePointsMinFilter() {
        return distancePointsMinFilter;
    }

    public void setDistancePointsMinFilter(Integer distancePointsMinFilter) {
        this.distancePointsMinFilter = distancePointsMinFilter;
    }

    public Integer getAltitudePointsMaxFilter() {
        return altitudePointsMaxFilter;
    }

    public void setAltitudePointsMaxFilter(Integer altitudePointsMaxFilter) {
        this.altitudePointsMaxFilter = altitudePointsMaxFilter;
    }

    public Integer getAltitudePointsMinFilter() {
        return altitudePointsMinFilter;
    }

    public void setAltitudePointsMinFilter(Integer altitudePointsMinFilter) {
        this.altitudePointsMinFilter = altitudePointsMinFilter;
    }

    private Integer mountainRangeFilter;
    private Integer mountainGroupFilter;
    private Integer localizationOneFilter;
    private Integer localizationTwoFilter;
    private Integer distanceMaxFilter;
    private Integer distanceMinFilter;
    private Integer distancePointsMaxFilter;
    private Integer distancePointsMinFilter;
    private Integer altitudePointsMaxFilter;
    private Integer altitudePointsMinFilter;

}
