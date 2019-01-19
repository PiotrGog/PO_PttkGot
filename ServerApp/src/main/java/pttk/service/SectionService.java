package pttk.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pttk.model.Location;
import pttk.model.MountainGroup;
import pttk.model.MountainRange;
import pttk.model.Section;
import pttk.repositories.LocationRepository;
import pttk.repositories.MountainGroupRepository;
import pttk.repositories.MountainRangeRepository;
import pttk.repositories.SectionRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SectionService {
    @Autowired
    private SectionRepository sectionRepository_;

    @Autowired
    private MountainRangeRepository mountainRangeRepository_;

    @Autowired
    private MountainGroupRepository mountainGroupRepository_;

    @Autowired
    private LocationRepository locationRepository_;

    /**
     * Return all mountain ranges from repository.
     *
     * @return Iterable collection of MountainRange
     */
    public Iterable<MountainRange> findAllMountainRange() {
        return mountainRangeRepository_.findAll();
    }

    /**
     * Return all mountain group from repository.
     *
     * @return Iterable collection of MountainGroup
     */
    public Iterable<MountainGroup> findAllMountainGroup() {
        return mountainGroupRepository_.findAll();
    }

    /**
     * Return all locations from repository.
     *
     * @return Iterable collection of Location
     */
    public Iterable<Location> findAllLocation() {
        return locationRepository_.findAll();
    }

    /**
     * Return all sections from repository.
     *
     * @return Iterable collection of Section
     */
    public Iterable<Section> findAllSection() {
        return sectionRepository_.findAll();
    }

    public Optional<Section> findByIdSection(Integer id) {
        return sectionRepository_.findById(id);
    }

    public Optional<MountainRange> findByIdMountainRange(Integer id) {
        return mountainRangeRepository_.findById(id);
    }

    public Optional<MountainGroup> findByIdMountainGroup(Integer id) {
        return mountainGroupRepository_.findById(id);
    }

    public Optional<Location> findByIdLocation(Integer id) {
        return locationRepository_.findById(id);
    }

    public Section saveSection(Section section){
        return sectionRepository_.save(section);
    }

    public void deleteSection(Section section){
        sectionRepository_.delete(section);
    }

    public List<Section> filterSections(Iterable<Section> sections, SectionFilter sectionFilter){
        ArrayList<Section> filteredSections = new ArrayList<>();
        for (Section s : sections) {
            if (null != sectionFilter.getDistanceMaxFilter()
                    && !(sectionFilter.getDistanceMaxFilter() >= s.getDistance())) {
                continue;
            }

            if (null != sectionFilter.getDistanceMinFilter()
                    && !(sectionFilter.getDistanceMinFilter() <= s.getDistance())) {
                continue;
            }
            if (null != sectionFilter.getAltitudePointsMaxFilter()
                    && !(sectionFilter.getAltitudePointsMaxFilter() >= s.getPointsAltitude())) {
                continue;
            }

            if (null != sectionFilter.getAltitudePointsMinFilter()
                    && !(sectionFilter.getAltitudePointsMinFilter() <= s.getPointsAltitude())) {
                continue;
            }

            if (null != sectionFilter.getDistancePointsMaxFilter()
                    && !(sectionFilter.getDistancePointsMaxFilter() >= s.getPointsDistance())) {
                continue;
            }

            if (null != sectionFilter.getDistancePointsMinFilter()
                    && !(sectionFilter.getDistancePointsMinFilter() <= s.getPointsDistance())) {
                continue;
            }

            if (null != sectionFilter.getMountainGroupFilter()
                    && !sectionFilter.getMountainGroupFilter().equals(s.getMountainGroup().getId())) {
                continue;
            }

            if (null != sectionFilter.getMountainRangeFilter()
                    &&
                    !sectionFilter.getMountainRangeFilter().equals(s.getMountainGroup().getMountainRange().getId())) {
                continue;
            }

            if (null != sectionFilter.getLocalizationOneFilter()
                    && !(sectionFilter.getLocalizationOneFilter().equals(s.getLocationOne().getId())
                    || sectionFilter.getLocalizationOneFilter().equals(s.getLocationTwo().getId()))) {
                continue;
            }

            if (null != sectionFilter.getLocalizationTwoFilter()
                    && !(sectionFilter.getLocalizationTwoFilter().equals(s.getLocationOne().getId())
                    || sectionFilter.getLocalizationTwoFilter().equals(s.getLocationTwo().getId()))) {
                continue;
            }

            filteredSections.add(s);
        }
        return filteredSections;
    }

}
