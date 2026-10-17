package org.airtribe.LearnerManagementSystemBelC22.controller;

import jakarta.validation.Valid;
import java.util.List;
import org.airtribe.LearnerManagementSystemBelC22.entity.Learner;
import org.airtribe.LearnerManagementSystemBelC22.entity.LearnerDTO;
import org.airtribe.LearnerManagementSystemBelC22.exception.LearnerNotFoundException;
import org.airtribe.LearnerManagementSystemBelC22.repository.LearnerRepository;
import org.airtribe.LearnerManagementSystemBelC22.service.LearnerManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class LearnerController {

  @Autowired
  private LearnerManagementService _learnerManagementService;

  @Autowired
  private LearnerRepository _learnerRepository;

  @PostMapping("/learners")
  public Learner createLearner(@Valid @RequestBody Learner learner) {
    return _learnerManagementService.createLearner(learner);
  }

  @GetMapping("/learners/{learnerId}")
  public ResponseEntity<Learner> fetchLearnerById(@PathVariable Long learnerId) throws LearnerNotFoundException {
      return ResponseEntity.status(HttpStatus.OK).body(_learnerManagementService.fetchLearnerById(learnerId));
  }

  @GetMapping("/learners")
  public List<LearnerDTO> fetchLearnerByName(@RequestParam(required = false) String learnerName,
      @RequestParam(required = false) Long learnerId) throws LearnerNotFoundException {
   List<Learner> learners = _learnerManagementService.fetchLearnersBasedOnBusinessLogic(learnerName, learnerId);
   return _learnerManagementService.convertLearnersToDTOs(learners);
  }
}
/// cohorts -> learners -> Has-a
// Many to Many
// List<Learner>
// normalization
// Join table
// cohortId and learnerId