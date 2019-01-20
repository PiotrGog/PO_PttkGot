package pttk.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pttk.model.Location;
import pttk.model.MountainGroup;
import pttk.model.MountainRange;
import pttk.model.Section;
import pttk.repositories.LocationRepository;
import pttk.repositories.MountainGroupRepository;
import pttk.repositories.MountainRangeRepository;
import pttk.repositories.SectionRepository;

import java.util.ArrayList;
import java.util.Arrays;
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

    public Section saveSection(Section section) {
        return sectionRepository_.save(section);
    }

    public void deleteSection(Section section) {
        sectionRepository_.delete(section);
    }

    public List<Section> filterSections(Iterable<Section> sections, SectionFilter sectionFilter) {
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

    private MountainRange mountainRangeBuilder(String name) {
        MountainRange mr = new MountainRange();
        mr.setName(name);
        return mr;
    }

    private MountainGroup mountainGroupBuilder(String name, MountainRange mr) {
        MountainGroup mg = new MountainGroup();
        mg.setName(name);
        mg.setMountainRange(mr);
        return mg;
    }

    private Location locationBuilder(String name, Integer altitude) {
        Location l = new Location();
        l.setName(name);
        l.setAltitude(altitude);
        return l;
    }

    private Location locationBuilder(String name) {
        Location l = new Location();
        l.setName(name);
        l.setAltitude(null);
        return l;
    }

    private Section sectionBuilder(Location location1, Location location2, Integer distance,
                                   Integer pDistance, Integer pAltitude, MountainGroup mountainGroup) {
        Section s = new Section();
        s.setLocationOne(location1);
        s.setLocationTwo(location2);
        s.setDistance(distance);
        s.setPointsDistance(pDistance);
        s.setPointsAltitude(pAltitude);
        s.setMountainGroup(mountainGroup);
        return s;
    }

    private Section sectionBuilder(Location destination, Location start,
                                   Integer pDistance, Integer pAltitude, MountainGroup mountainGroup) {
        Section s = new Section();
        s.setLocationOne(start);
        s.setLocationTwo(destination);
        s.setDistance(null);
        s.setPointsDistance(pDistance);
        s.setPointsAltitude(pAltitude);
        s.setMountainGroup(mountainGroup);
        return s;
    }

    public void fillDatabase() {
        List<MountainRange> mountainRanges = new ArrayList<>(
                Arrays.asList(
                        mountainRangeBuilder("TATRY I PODTATRZE"),  //0
                        mountainRangeBuilder("BESKIDY ZACHODNIE"),  //1
                        mountainRangeBuilder("BESKIDY WSCHODNIE"),  //2
                        mountainRangeBuilder("GÓRY ŚWIĘTOKRZYSKIE"),//3
                        mountainRangeBuilder("SUDETY"))             //4
        );
        List<MountainGroup> mountainGroups = new ArrayList<>(
                Arrays.asList(
                        mountainGroupBuilder("T.01", mountainRanges.get(0)),  //0
                        mountainGroupBuilder("T.02", mountainRanges.get(0)),  //1
                        mountainGroupBuilder("T.03", mountainRanges.get(0)),  //2
                        mountainGroupBuilder("BZ.01", mountainRanges.get(1)),  //2
                        mountainGroupBuilder("BZ.02", mountainRanges.get(1)),  //3
                        mountainGroupBuilder("BZ.03", mountainRanges.get(1)),  //4
                        mountainGroupBuilder("BZ.04", mountainRanges.get(1)),  //5
                        mountainGroupBuilder("BZ.05", mountainRanges.get(1)),  //6
                        mountainGroupBuilder("BZ.06", mountainRanges.get(1)),  //7
                        mountainGroupBuilder("BZ.07", mountainRanges.get(1)),  //8
                        mountainGroupBuilder("BZ.08", mountainRanges.get(1)),  //9
                        mountainGroupBuilder("BZ.09", mountainRanges.get(1)),  //10
                        mountainGroupBuilder("BZ.10", mountainRanges.get(1)),  //11
                        mountainGroupBuilder("BZ.11", mountainRanges.get(1)),  //12
                        mountainGroupBuilder("BZ.12", mountainRanges.get(1)),  //13
                        mountainGroupBuilder("BW.01", mountainRanges.get(2)),  //14
                        mountainGroupBuilder("BW.02", mountainRanges.get(2)),  //15
                        mountainGroupBuilder("BW.03", mountainRanges.get(2)),  //16
                        mountainGroupBuilder("BW.04", mountainRanges.get(2)),  //17
                        mountainGroupBuilder("BW.05", mountainRanges.get(2)),  //18
                        mountainGroupBuilder("Ł01", mountainRanges.get(3)),  //19
                        mountainGroupBuilder("Ł02", mountainRanges.get(3)),  //20
                        mountainGroupBuilder("Ł03", mountainRanges.get(3)),  //21
                        mountainGroupBuilder("Ł04", mountainRanges.get(3)),  //22
                        mountainGroupBuilder("Ł05", mountainRanges.get(3)),  //23
                        mountainGroupBuilder("S.01", mountainRanges.get(4)),  //24
                        mountainGroupBuilder("S.02", mountainRanges.get(4)),  //25
                        mountainGroupBuilder("S.03", mountainRanges.get(4)),  //26
                        mountainGroupBuilder("S.04", mountainRanges.get(4)),  //27
                        mountainGroupBuilder("S.05", mountainRanges.get(4)),  //28
                        mountainGroupBuilder("S.06", mountainRanges.get(4)),  //29
                        mountainGroupBuilder("S.07", mountainRanges.get(4)),  //30
                        mountainGroupBuilder("S.08", mountainRanges.get(4)),  //31
                        mountainGroupBuilder("S.09", mountainRanges.get(4)),  //32
                        mountainGroupBuilder("S.10", mountainRanges.get(4)),  //33
                        mountainGroupBuilder("S.11", mountainRanges.get(4)),  //34
                        mountainGroupBuilder("S.12", mountainRanges.get(4)),  //35
                        mountainGroupBuilder("S.13", mountainRanges.get(4)),  //36
                        mountainGroupBuilder("S.14", mountainRanges.get(4)),  //37
                        mountainGroupBuilder("S.15", mountainRanges.get(4)),  //38
                        mountainGroupBuilder("S.16", mountainRanges.get(4)),  //39
                        mountainGroupBuilder("S.17", mountainRanges.get(4)),  //40
                        mountainGroupBuilder("S.18", mountainRanges.get(4)),  //41
                        mountainGroupBuilder("S.19", mountainRanges.get(4)),  //42
                        mountainGroupBuilder("S.20", mountainRanges.get(4)),  //43
                        mountainGroupBuilder("S.21", mountainRanges.get(4))  //44
                )
        );


        List<Location> locations = new ArrayList<>(
                Arrays.asList(
                        locationBuilder("Jaworzynka", 936), //0
                        locationBuilder("Tylmanowa Rzeka"),         //1
                        locationBuilder("Zabrzeż"),                 //2
                        locationBuilder("Łącka"),                   //3
                        locationBuilder("Jazowsko"),                //4
                        locationBuilder("Dzwonkówka", 983), //5
                        locationBuilder("Krościenko nad Dunajcem"), //6
                        locationBuilder("Schronisko na Bereśniku", 843), //7
                        locationBuilder("Przysłopy", 944),  //8
                        locationBuilder("Łosie"),                   //9
                        locationBuilder("Bukowinka"),               //10
                        locationBuilder("Dolina Czarnego Potoku"),  //11
                        locationBuilder("Słotwina"),                //12
                        locationBuilder("Przełęcz Krzyżowa"),        //13
                        locationBuilder("Radziejowa", 1266), //14
                        locationBuilder("Polana Konieczna"),        //15
                        locationBuilder("Rytra"),                   //16
                        locationBuilder("Wielki Rogacz"),            //17
                        locationBuilder("Schronisko PTTK Prehyba", 1175), //18
                        locationBuilder("Szczawnica (PKS)"),         //19
                        locationBuilder("Gabonia (PKS)"),           //20
                        locationBuilder("Jaworek"),                 //21
                        locationBuilder("Stary Sącza"),             //22
                        locationBuilder("Barcic"),                  //23
                        locationBuilder("Szlachtowa"),             //24
                        locationBuilder("Szczawnica Zdrój"),       //25
                        locationBuilder("Niemcowa", 1001),  //26
                        locationBuilder("Młodowa"),                 //27
                        locationBuilder("Piwniczna Zdrój"),         //28
                        locationBuilder("Kosarzysk")                //29
                ));

                List<Section> sections = new ArrayList<>(
                Arrays.asList(
                        sectionBuilder(locations.get(0), locations.get(1), 9, 4, mountainGroups.get(10)),
                        sectionBuilder(locations.get(0), locations.get(2), 12, 6, mountainGroups.get(10)),
                        sectionBuilder(locations.get(0), locations.get(3), 13, 7, mountainGroups.get(10)),
                        sectionBuilder(locations.get(0), locations.get(4), 14, 8, mountainGroups.get(10)),
                        sectionBuilder(locations.get(5), locations.get(6), 11, 6, mountainGroups.get(10)),
                        sectionBuilder(locations.get(5), locations.get(0), 6, 6, mountainGroups.get(10)),
                        sectionBuilder(locations.get(5), locations.get(4), 15, 9, mountainGroups.get(10)),
                        sectionBuilder(locations.get(5), locations.get(7), 5, 3, mountainGroups.get(10)),
                        sectionBuilder(locations.get(8), locations.get(9), 9, 6, mountainGroups.get(10)),
                        sectionBuilder(locations.get(8), locations.get(0), 2, 1, mountainGroups.get(10)),
                        sectionBuilder(locations.get(8), locations.get(10), 2, 1, mountainGroups.get(10)),
                        sectionBuilder(locations.get(8), locations.get(11), 5, 3, mountainGroups.get(10)),
                        sectionBuilder(locations.get(10), locations.get(0), 2, 3, mountainGroups.get(10)),
                        sectionBuilder(locations.get(10), locations.get(12), 3, 3, mountainGroups.get(10)),
                        sectionBuilder(locations.get(10), locations.get(13), 5, 2, mountainGroups.get(10)),
                        sectionBuilder(locations.get(14), locations.get(15), 4, 3, mountainGroups.get(10)),
                        sectionBuilder(locations.get(14), locations.get(16), 17, 9, mountainGroups.get(10)),
                        sectionBuilder(locations.get(14), locations.get(17), 2, 1, mountainGroups.get(10)),
                        sectionBuilder(locations.get(18), locations.get(5), 9, 7, mountainGroups.get(10)),
                        sectionBuilder(locations.get(18), locations.get(16), 18, 10, mountainGroups.get(10)),
                        sectionBuilder(locations.get(18), locations.get(4), 17, 9, mountainGroups.get(10)),
                        sectionBuilder(locations.get(18), locations.get(20), 14, 7, mountainGroups.get(10)),
                        sectionBuilder(locations.get(18), locations.get(21), 14, 7, mountainGroups.get(10)),
                        sectionBuilder(locations.get(18), locations.get(22), 21, 12, mountainGroups.get(10)),
                        sectionBuilder(locations.get(18), locations.get(23), 19, 11, mountainGroups.get(10)),
                        sectionBuilder(locations.get(18), locations.get(15), 4, 3, mountainGroups.get(10)),
                        sectionBuilder(locations.get(18), locations.get(24), 15, 8, mountainGroups.get(10)),
                        sectionBuilder(locations.get(18), locations.get(25), 15, 8, mountainGroups.get(10)),
                        sectionBuilder(locations.get(18), locations.get(19), 16, 9, mountainGroups.get(10)),
                        sectionBuilder(locations.get(18), locations.get(14), 4, 5, mountainGroups.get(10)),

                        sectionBuilder(locations.get(26), locations.get(16), 13, 6, mountainGroups.get(10)),
                        sectionBuilder(locations.get(26), locations.get(27), 11, 5, mountainGroups.get(10)),
                        sectionBuilder(locations.get(26), locations.get(28), 12, 5, mountainGroups.get(10)),
                        sectionBuilder(locations.get(26), locations.get(29), 9, 3, mountainGroups.get(10))


                        ));

        mountainRangeRepository_.saveAll(mountainRanges);
        mountainGroupRepository_.saveAll(mountainGroups);
        locationRepository_.saveAll(locations);
        sectionRepository_.saveAll(sections);

    }


}
