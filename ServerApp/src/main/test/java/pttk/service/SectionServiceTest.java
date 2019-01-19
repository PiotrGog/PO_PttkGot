package pttk.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import pttk.model.Location;
import pttk.model.MountainGroup;
import pttk.model.MountainRange;
import pttk.model.Section;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;


import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.assertEquals;


@RunWith(MockitoJUnitRunner.class)
public class SectionServiceTest {

    private Section makeSection(Integer id,
                                Integer mountainRange,
                                Integer mountainGroup,
                                Integer locationOne,
                                Integer locationTwo,
                                Integer distance,
                                Integer pointsAltitude,
                                Integer pointsDistance) {
        Section newSection = new Section();
        newSection.setId(id);

        MountainRange mr = new MountainRange();
        mr.setId(mountainRange);
        MountainGroup mg = new MountainGroup();
        mg.setMountainRange(mr);
        mg.setId(mountainGroup);
        newSection.setMountainGroup(mg);

        newSection.setPointsDistance(pointsDistance);
        newSection.setPointsAltitude(pointsAltitude);
        newSection.setDistance(distance);

        Location l1 = new Location();
        l1.setId(locationOne);

        Location l2 = new Location();
        l2.setId(locationTwo);

        newSection.setLocationOne(l1);
        newSection.setLocationTwo(l2);
        return newSection;
    }

    private SectionFilter sectionFilter;
    private ArrayList<Section> inputSections;
    private SectionService sectionService_;

    @Before
    public void setUp() {
        sectionService_ = new SectionService();
        inputSections = new ArrayList<>(Arrays.asList(
                makeSection(0, 1, 5, 3, 1, 2, 10,
                        15),
                makeSection(1, 2, 4, 3, 1, 30, 24,
                        16),
                makeSection(2, 3, 1, 3, 1, 11, 9,
                        17),
                makeSection(3, 1, 9, 3, 1, 9, 25,
                        10),
                makeSection(4, 2, 4, 3, 1, 21, 21,
                        9),
                makeSection(5, 3, 1, 9, 5, 20, 15,
                        9),
                makeSection(6, 1, 1, 3, 1, 2, 16,
                        2),
                makeSection(7, 2, 1, 3, 1, 15, 16,
                        14),
                makeSection(8, 3, 2, 3, 1, 9, 5,
                        16),
                makeSection(9, 3, 0, 3, 1, 25, 1,
                        8),
                makeSection(10, 2, 1, 3, 1, 22, 11,
                        7),
                makeSection(11, 1, 9, 5, 9, 19, 4,
                        12),
                makeSection(12, 5, 3, 3, 1, 2, 4,
                        22),
                makeSection(13, 5, 3, 3, 1, 7, 9,
                        19),
                makeSection(14, 2, 2, 3, 1, 1, 4,
                        30),
                makeSection(15, 1, 6, 3, 1, 2, 14,
                        4)));
    }

    @Test
    public void filterSection_When_MountainRange2FilterIsGiven_Expect_SectionWithinMountainRange2() {
        sectionFilter = new SectionFilter(2, null, null,
                null, null, null, null,
                null, null, null);

        List<Integer> expectedIds = Arrays.asList(1, 4, 7, 10, 14);

        List<Section> result = sectionService_.filterSections(inputSections, sectionFilter);

        assertEquals(expectedIds.size(), result.size());
        List<Integer> resultIdx = new ArrayList<>();
        for (Section r : result) {
            resultIdx.add(r.getId());
        }
        assertThat(resultIdx, containsInAnyOrder(expectedIds.toArray()));
    }

    @Test
    public void filterSection_When_MountainGroup1FilterIsGiven_Expect_SectionWithinMountainGroup1() {
        sectionFilter = new SectionFilter(null, 1, null,
                null, null, null, null,
                null, null, null);

        List<Integer> expectedIds = Arrays.asList(2, 5, 6, 7, 10);

        List<Section> result = sectionService_.filterSections(inputSections, sectionFilter);

        assertEquals(expectedIds.size(), result.size());
        List<Integer> resultIdx = new ArrayList<>();
        for (Section r : result) {
            resultIdx.add(r.getId());
        }
        assertThat(resultIdx, containsInAnyOrder(expectedIds.toArray()));
    }

    @Test
    public void filterSection_When_LocalizationOneAndLocalizationTwoFilterIsGiven_Expect_SectionWithBothLocalization() {
        sectionFilter = new SectionFilter(null, null, 5,
                9, null, null, null,
                null, null, null);

        List<Integer> expectedIds = Arrays.asList(5, 11);

        List<Section> result = sectionService_.filterSections(inputSections, sectionFilter);

        assertEquals(expectedIds.size(), result.size());
        List<Integer> resultIdx = new ArrayList<>();
        for (Section r : result) {
            resultIdx.add(r.getId());
        }
        assertThat(resultIdx, containsInAnyOrder(expectedIds.toArray()));
    }

    @Test
    public void filterSection_When_DistanceFilterIsGiven_Expect_SectionWithDistanceBetweenSpecifiedValue() {
        sectionFilter = new SectionFilter(null, null, null,
                null, 20, 10, null,
                null, null, null);

        List<Integer> expectedIds = Arrays.asList(2, 5, 7, 11);

        List<Section> result = sectionService_.filterSections(inputSections, sectionFilter);

        assertEquals(expectedIds.size(), result.size());
        List<Integer> resultIdx = new ArrayList<>();
        for (Section r : result) {
            resultIdx.add(r.getId());
        }
        assertThat(resultIdx, containsInAnyOrder(expectedIds.toArray()));
    }

    @Test
    public void filterSection_When_DistancePointsFilterIsGiven_Expect_SectionWithDistanceBetweenSpecifiedValue() {
        sectionFilter = new SectionFilter(null, null, null,
                null, null, null, 15,
                10, null, null);

        List<Integer> expectedIds = Arrays.asList(0, 3, 7, 11);

        List<Section> result = sectionService_.filterSections(inputSections, sectionFilter);

        assertEquals(expectedIds.size(), result.size());
        List<Integer> resultIdx = new ArrayList<>();
        for (Section r : result) {
            resultIdx.add(r.getId());
        }
        assertThat(resultIdx, containsInAnyOrder(expectedIds.toArray()));
    }

    @Test
    public void filterSection_When_AltitudePointsFilterIsGiven_Expect_SectionWithDistanceBetweenSpecifiedValue() {
        sectionFilter = new SectionFilter(null, null, null,
                null, null, null, null,
                null, 15, 10);

        List<Integer> expectedIds = Arrays.asList(0, 5, 10, 15);

        List<Section> result = sectionService_.filterSections(inputSections, sectionFilter);

        assertEquals(expectedIds.size(), result.size());
        List<Integer> resultIdx = new ArrayList<>();
        for (Section r : result) {
            resultIdx.add(r.getId());
        }
        assertThat(resultIdx, containsInAnyOrder(expectedIds.toArray()));
    }

    @Test
    public void filterSection_When_NoFilterIsGiven_Expect_ReturnAllSections() {
        sectionFilter = new SectionFilter(null, null, null,
                null, null, null, null,
                null, null, null);

        List<Integer> expectedIds = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15);

        List<Section> result = sectionService_.filterSections(inputSections, sectionFilter);

        assertEquals(expectedIds.size(), result.size());
        List<Integer> resultIdx = new ArrayList<>();
        for (Section r : result) {
            resultIdx.add(r.getId());
        }
        assertThat(resultIdx, containsInAnyOrder(expectedIds.toArray()));
    }


    @Test
    public void filterSection_When_DistanceLowerBoundIsGiven_Expect_SectionsWithDistanceGreater() {
        sectionFilter = new SectionFilter(null, null, null,
                null, null, 10, null,
                null, null, null);

        List<Integer> expectedIds = Arrays.asList(2, 4, 5, 7, 11);

        List<Section> result = sectionService_.filterSections(inputSections, sectionFilter);

        assertEquals(expectedIds.size(), result.size());
        List<Integer> resultIdx = new ArrayList<>();
        for (Section r : result) {
            resultIdx.add(r.getId());
        }
        assertThat(resultIdx, containsInAnyOrder(expectedIds.toArray()));
    }

    @Test
    public void filterSection_When_DistancePointsLowerBoundIsGiven_Expect_SectionWithDistancePointsGreater() {
        sectionFilter = new SectionFilter(null, null, null,
                null, null, null, null,
                10, null, null);

        List<Integer> expectedIds = Arrays.asList(0, 1, 3, 7, 8, 11);

        List<Section> result = sectionService_.filterSections(inputSections, sectionFilter);

        assertEquals(expectedIds.size(), result.size());
        List<Integer> resultIdx = new ArrayList<>();
        for (Section r : result) {
            resultIdx.add(r.getId());
        }
        assertThat(resultIdx, containsInAnyOrder(expectedIds.toArray()));
    }

    @Test
    public void filterSection_When_AltitudePointsLowerBoundIsGiven_Expect_SectionWithAltitudePointsGreater() {
        sectionFilter = new SectionFilter(null, null, null,
                null, null, null, null,
                null, null, 10);

        List<Integer> expectedIds = Arrays.asList(0, 3, 5, 7, 10, 15);

        List<Section> result = sectionService_.filterSections(inputSections, sectionFilter);

        assertEquals(expectedIds.size(), result.size());
        List<Integer> resultIdx = new ArrayList<>();
        for (Section r : result) {
            resultIdx.add(r.getId());
        }
        assertThat(resultIdx, containsInAnyOrder(expectedIds.toArray()));
    }

    @Test
    public void filterSection_When_DistanceUpperBoundIsGiven_Expect_SectionsWithDistanceLower() {
        sectionFilter = new SectionFilter(null, null, null,
                null, 15, null, null,
                null, null, null);

        List<Integer> expectedIds = Arrays.asList(0, 2, 3, 6, 7, 8, 12, 13, 14, 15);

        List<Section> result = sectionService_.filterSections(inputSections, sectionFilter);

        assertEquals(expectedIds.size(), result.size());
        List<Integer> resultIdx = new ArrayList<>();
        for (Section r : result) {
            resultIdx.add(r.getId());
        }
        assertThat(resultIdx, containsInAnyOrder(expectedIds.toArray()));
    }

    @Test
    public void filterSection_When_DistancePointsUpperBoundIsGiven_Expect_SectionWithDistancePointsLower() {
        sectionFilter = new SectionFilter(null, null, null,
                null, null, null, 15,
                null, null, null);

        List<Integer> expectedIds = Arrays.asList(0, 3, 4, 5, 6, 7, 9, 10, 11, 15);

        List<Section> result = sectionService_.filterSections(inputSections, sectionFilter);

        assertEquals(expectedIds.size(), result.size());
        List<Integer> resultIdx = new ArrayList<>();
        for (Section r : result) {
            resultIdx.add(r.getId());
        }
        assertThat(resultIdx, containsInAnyOrder(expectedIds.toArray()));
    }

    @Test
    public void filterSection_When_AltitudePointsUpperBoundIsGiven_Expect_SectionWithAltitudePointsLower() {
        sectionFilter = new SectionFilter(null, null, null,
                null, null, null, null,
                null, 15, null);

        List<Integer> expectedIds = Arrays.asList(0, 2, 5, 8, 9, 10, 11, 12, 13, 14, 15);

        List<Section> result = sectionService_.filterSections(inputSections, sectionFilter);

        assertEquals(expectedIds.size(), result.size());
        List<Integer> resultIdx = new ArrayList<>();
        for (Section r : result) {
            resultIdx.add(r.getId());
        }
        assertThat(resultIdx, containsInAnyOrder(expectedIds.toArray()));
    }


}
