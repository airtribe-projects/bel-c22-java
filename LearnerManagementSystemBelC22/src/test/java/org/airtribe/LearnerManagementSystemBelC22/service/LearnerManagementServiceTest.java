package org.airtribe.LearnerManagementSystemBelC22.service;

import java.util.Optional;
import org.airtribe.LearnerManagementSystemBelC22.entity.Learner;
import org.airtribe.LearnerManagementSystemBelC22.exception.LearnerNotFoundException;
import org.airtribe.LearnerManagementSystemBelC22.repository.LearnerRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.*;

// Tests are not guranteed to run in order
// output of first test would be used in second test -> coupled the tests together -> flakiness tests

@ExtendWith(MockitoExtension.class)
public class LearnerManagementServiceTest {

  @InjectMocks
  private LearnerManagementService _learnerManagementService;

  @Mock
  private LearnerRepository _learnerRepository;

  @BeforeAll
  public static void beforeAllTests() {
    System.out.println("Running before all the tests");
  }

  @AfterAll
  public static void afterAllTests() {
    System.out.println("Running after all the tests");
  }

  @BeforeEach
  public void beforeEachTest() {
    System.out.println("Running before each test");
  }

  @AfterEach
  public void afterEachTests() {
    System.out.println("Running after each test");
  }

  // Test for happy and unhappy (exception) cases
  @Test
  public void testCreateLearner_Successfully() {
    // ARRANGE
    Learner learner = new Learner(1L, "test", "test", "1234");
    when(_learnerRepository.save(learner)).thenReturn(learner);
    // ACT
    Learner createdLearner = _learnerManagementService.createLearner(learner);
    // ASSERT
    Assertions.assertEquals(learner, createdLearner);
    Assertions.assertEquals(learner.getLearnerId(), createdLearner.getLearnerId());
    Assertions.assertEquals(learner.getLearnerName(), createdLearner.getLearnerName());
    Assertions.assertEquals(learner.getLearnerEmail(), createdLearner.getLearnerEmail());
    Assertions.assertEquals(learner.getLearnerPhone(), createdLearner.getLearnerPhone());
  }

  @Test
  public void testFetchLearnerById_Successfully() throws LearnerNotFoundException {
    // ARRANGE
    Learner learner = new Learner(1L, "test", "test", "1234");
    when(_learnerRepository.findById(1L)).thenReturn(Optional.of(learner));

    // ACT
    Learner fetchedLearner = _learnerManagementService.fetchLearnerById(1L);

    // Assert
    Assertions.assertEquals(learner, fetchedLearner);
    Assertions.assertEquals(learner.getLearnerId(), fetchedLearner.getLearnerId());
    Assertions.assertEquals(learner.getLearnerName(), fetchedLearner.getLearnerName());
    Assertions.assertEquals(learner.getLearnerEmail(), fetchedLearner.getLearnerEmail());
    Assertions.assertEquals(learner.getLearnerPhone(), fetchedLearner.getLearnerPhone());
  }

  // One test should validate one business condition
  @Test
  public void testFetchLearnerById_LearnerNotFoundException() {
    // ARRANGE
    when(_learnerRepository.findById(1L)).thenReturn(Optional.empty());

    // ACT
    // Functional programmaing
    LearnerNotFoundException exception = Assertions.assertThrows(LearnerNotFoundException.class, () -> {
      _learnerManagementService.fetchLearnerById(1L);
    });
    Assertions.assertEquals("Learner with id 1 not found", exception.getMessage());
//    try {
//      _learnerManagementService.fetchLearnerById(1L);
//    } catch (LearnerNotFoundException exception) {
//      // ASSERT
//      Assertions.assertEquals("Learner with id 1 not found", exception.getMessage());
//    }
  }

  // Unhappy scenarios
  @Test
  public void testFetchLearnerById_IllegalArgumentException() {
    // ARRANGE
    // ACT
    IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
      _learnerManagementService.fetchLearnerById(-1L);
    });
    Assertions.assertEquals("Learner Id cannot be null or negative", exception.getMessage());
  }
}
