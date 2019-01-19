package pttk.controller;


import org.hamcrest.CoreMatchers;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.empty;
import static org.mockito.Mockito.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pttk.model.Location;
import pttk.model.MountainGroup;
import pttk.service.NewSectionWrapper;
import pttk.service.SectionService;

import static org.hamcrest.MatcherAssert.assertThat;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class SectionControllerTest {
    @Mock
    private SectionService sectionService_;

    @InjectMocks
    private SectionController sectionController;

    @Test
    public void addSection_When_LocationOneIsEqualLocationTwo_Expect_StatusIsFAILED_DEPENDENCYAndHasErrorNumber1() {
        HttpStatus expectedStatus = HttpStatus.BAD_REQUEST;
        Integer expectedHasErrorNum = 1;

        ResponseEntity<List<Integer>> result = sectionController.addSection(
                new NewSectionWrapper(1, 1, 1, 1, 1, 1));

        assertEquals(expectedStatus, result.getStatusCode());
        assertThat(result.getBody(), CoreMatchers.hasItem(expectedHasErrorNum));
    }

    @Test
    public void addSection_When_DistanceIsNotDefined_Expect_StatusIsFAILED_DEPENDENCYAndHasErrorNumber2() {
        HttpStatus expectedStatus = HttpStatus.BAD_REQUEST;
        Integer expectedHasErrorNum = 2;

        ResponseEntity<List<Integer>> result = sectionController.addSection(
                new NewSectionWrapper(1, 1, 1, null, 1, 1));

        assertEquals(expectedStatus, result.getStatusCode());
        assertThat(result.getBody(), CoreMatchers.hasItem(expectedHasErrorNum));
    }

    @Test
    public void addSection_When_MountainGroupIsNotDefined_Expect_StatusIsFAILED_DEPENDENCYAndHasErrorNumber3() {
        HttpStatus expectedStatus = HttpStatus.BAD_REQUEST;
        Integer expectedHasErrorNum = 3;

        ResponseEntity<List<Integer>> result = sectionController.addSection(
                new NewSectionWrapper(null, 1, 1, null, 1, 1));

        assertEquals(expectedStatus, result.getStatusCode());
        assertThat(result.getBody(), CoreMatchers.hasItem(expectedHasErrorNum));
    }

    @Test
    public void addSection_When_DataAreCorrectAndSectionDoesNotExists_Expect_StatusIsCreatedAndEmptyErrorList() {
        HttpStatus expectedStatus = HttpStatus.CREATED;
        Location locationOne = new Location();
        Location locationTwo = new Location();
        MountainGroup mountainGroup = new MountainGroup();
        when(sectionService_.findByIdLocation(any(Integer.class)))
                .thenReturn(Optional.of(locationOne)).thenReturn(Optional.of(locationTwo));
        when(sectionService_.findByIdMountainGroup(any(Integer.class))).thenReturn(Optional.of(mountainGroup));

        ResponseEntity<List<Integer>> result = sectionController.addSection(
                new NewSectionWrapper(1, 1, 2, 1, 1, 1));

        assertEquals(expectedStatus, result.getStatusCode());
        assertThat(result.getBody(), is(empty()));
    }


}
