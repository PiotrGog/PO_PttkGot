package pttk.model;


import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.empty;
import static org.mockito.Mockito.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.matchers.Any;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import pttk.controller.SectionController;
import pttk.repositories.SectionRepository;
import pttk.service.NewSectionWrapper;
import pttk.service.SectionService;

import static org.hamcrest.MatcherAssert.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

//@RunWith(MockitoJUnitRunner.class)
@RunWith(SpringRunner.class)
@SpringBootTest
public class SectionControllerTest {
    private SectionController sectionController;

    @BeforeEach
    private void initSectionController() {
        sectionController = new SectionController();
    }

    @Test
    public void SectionController_When_LocationOneIsEqualLocationTwo_Expect_StatusIsFAILED_DEPENDENCYAndHasErrorNumber1() {
        HttpStatus expectedStatus = HttpStatus.FAILED_DEPENDENCY;
        Integer expectedHasErrorNum = 1;

        ResponseEntity<List<Integer>> result = sectionController.addSection(
                new NewSectionWrapper(1, 1, 1, 1, 1, 1));

        assertEquals(expectedStatus, result.getStatusCode());
        assertThat(result.getBody(), CoreMatchers.hasItem(expectedHasErrorNum));
    }

    @Test
    public void SectionController_When_DistanceIsNotDefined_Expect_StatusIsFAILED_DEPENDENCYAndHasErrorNumber2() {
        HttpStatus expectedStatus = HttpStatus.FAILED_DEPENDENCY;
        Integer expectedHasErrorNum = 2;

        ResponseEntity<List<Integer>> result = sectionController.addSection(
                new NewSectionWrapper(1, 1, 1, null, 1, 1));

        assertEquals(expectedStatus, result.getStatusCode());
        assertThat(result.getBody(), CoreMatchers.hasItem(expectedHasErrorNum));
    }

    @Test
    public void SectionController_When_MountainGroup_Expect_StatusIsFAILED_DEPENDENCYAndHasErrorNumber3() {
        HttpStatus expectedStatus = HttpStatus.FAILED_DEPENDENCY;
        Integer expectedHasErrorNum = 3;

        ResponseEntity<List<Integer>> result = sectionController.addSection(
                new NewSectionWrapper(null, 1, 1, null, 1, 1));

        assertEquals(expectedStatus, result.getStatusCode());
        assertThat(result.getBody(), CoreMatchers.hasItem(expectedHasErrorNum));
    }

    @MockBean


    @Autowired
    SectionService sectionService;

    @Test
    public void SectionController_When_DataAreCorrectAndSectionDoesNotExists_Expect_StatusIsCreatedAndEmptyErrorList() {
        HttpStatus expectedStatus = HttpStatus.CREATED;
        Location locationOne = new Location();
        Location locationTwo = new Location();
        MountainGroup mountainGroup = new MountainGroup();
        when(sectionService.findByIdLocation(any(Integer.class)))
                .thenReturn(Optional.of(locationOne)).thenReturn(Optional.of(locationTwo));
        when(sectionService.findByIdMountainGroup(any(Integer.class))).thenReturn(Optional.of(mountainGroup));

        ResponseEntity<List<Integer>> result = sectionController.addSection(
                new NewSectionWrapper(null, 1, 1, null, 1, 1));

        assertEquals(expectedStatus, result.getStatusCode());
        assertThat(result.getBody(), is(empty()));
    }


}
