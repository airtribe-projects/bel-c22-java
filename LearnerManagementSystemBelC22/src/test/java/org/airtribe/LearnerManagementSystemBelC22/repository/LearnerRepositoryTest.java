package org.airtribe.LearnerManagementSystemBelC22.repository;

import jakarta.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.airtribe.LearnerManagementSystemBelC22.entity.Learner;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.Rollback;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class LearnerRepositoryTest {

  @Autowired
  private LearnerRepository _learnerRepository;

  @Test
  public void testCreateLearner_Successfully() {
    // ARRANGE
    Learner learner = new Learner("test", "test@gmail.com", "1234");
    // ACT
    Learner savedLearner = _learnerRepository.save(learner);
    // ASSERT
    Assertions.assertNotNull(savedLearner.getLearnerId());
    Assertions.assertEquals("test", savedLearner.getLearnerName());
    Assertions.assertEquals("test@gmail.com", savedLearner.getLearnerEmail());
  }

  @Test
  public void testCreateLearner_Failure_DuplicateEmail() {
    // ARRANGE
    Learner learner = new Learner("test", "test@gmail.com", "1234");
    Learner learner1 = new Learner("test1", "test@gmail.com", "12345");
    _learnerRepository.save(learner);
    // ACT
    try {
      _learnerRepository.save(learner1);
    } catch (DataIntegrityViolationException exception) {
      Assertions.assertNotNull(exception);
    }
  }

  @ParameterizedTest(name = "[{index}] name={0}, email={1}, phone={2} -> violates {3}")
  @MethodSource("invalidLearners")
  public void testCreateLearner_Failure_ValidationViolations(String name, String email,
      String phone, Set<String> expectedInvalidFields) {
    // ARRANGE
    Learner learner = new Learner(name, email, phone);
    // ACT
    ConstraintViolationException exception = Assertions.assertThrows(
        ConstraintViolationException.class, () -> _learnerRepository.saveAndFlush(learner));
    // ASSERT
    Set<String> actualInvalidFields = exception.getConstraintViolations().stream()
        .map(violation -> violation.getPropertyPath().toString())
        .collect(Collectors.toSet());
    Assertions.assertEquals(expectedInvalidFields, actualInvalidFields);
  }

  static Stream<Arguments> invalidLearners() {
    return Stream.of(
        Arguments.of(null, "test@gmail.com", "1234", Set.of("learnerName")),
        Arguments.of("", "test@gmail.com", "1234", Set.of("learnerName")),
        Arguments.of("test", null, "1234", Set.of("learnerEmail")),
        Arguments.of("test", "", "1234", Set.of("learnerEmail")),
        Arguments.of("test", "test@gmail.com", null, Set.of("learnerPhone")),
        Arguments.of("test", "test@gmail.com", "", Set.of("learnerPhone")),
        Arguments.of(null, null, "1234", Set.of("learnerName", "learnerEmail")),
        Arguments.of(null, null, null, Set.of("learnerName", "learnerEmail", "learnerPhone")),
        Arguments.of("", "", "", Set.of("learnerName", "learnerEmail", "learnerPhone"))
    );
  }


  @Test
  public void testFindAllLearners_Successfully() {
    // ARRANGE
    List<Learner> learnersToBeSaved = new ArrayList<>();
    Learner learner = new Learner("test", "test@gmail.com", "1234");
    Learner learner1 = new Learner("test1", "test1@gmail.com", "12345");
    learnersToBeSaved.add(learner);
    learnersToBeSaved.add(learner1);
    _learnerRepository.saveAll(learnersToBeSaved);
    List<Learner> learners = _learnerRepository.findAll();
    Assertions.assertEquals(2, learners.size());
  }


}
