package org.airtribe.LearnerManagementSystemBelC22.controller;

import java.util.List;
import org.airtribe.LearnerManagementSystemBelC22.entity.Cohort;
import org.airtribe.LearnerManagementSystemBelC22.entity.CohortLearnerMappingResponse;
import org.airtribe.LearnerManagementSystemBelC22.entity.LearnerListBody;
import org.airtribe.LearnerManagementSystemBelC22.entity.LearnerObjectBody;
import org.airtribe.LearnerManagementSystemBelC22.exception.CohortNotFoundException;
import org.airtribe.LearnerManagementSystemBelC22.exception.LearnerNotFoundException;
import org.airtribe.LearnerManagementSystemBelC22.service.LearnerManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class CohortController {

  @Autowired
  private LearnerManagementService _learnerManagementService;

  @PostMapping("/cohorts")
  public Cohort createCohort(@RequestBody Cohort cohort) {
    return _learnerManagementService.createCohort(cohort);
  }

  @GetMapping("/cohorts")
  public List<Cohort> getAllCohorts() {
    return _learnerManagementService.getAllCohorts();
  }

  // get -> cohorts -> Pagination & sorting
  // incompatible change
  // Request -> pageNumber, pageSize, sortBy, sortOrder
  // Response -> pageNumber, totalPages, content (List<Cohort>), isFirst, isLast

  @PostMapping("/assignLearnersToCohort")
  public Cohort assignLearnersToCohort(@RequestParam("learnerId") Long learnerId, @RequestParam("cohortId") Long cohortId)
      throws CohortNotFoundException, LearnerNotFoundException {
    return _learnerManagementService.assignLearnersToCohort(learnerId, cohortId);
  }

//  @PostMapping("/cohorts/{cohortId}/learners")
//  public ResponseEntity<CohortLearnerMappingResponse> mapLearnersToCohort(@PathVariable("cohortId") Long cohortId, @RequestBody LearnerListBody learnerIds)
//      throws CohortNotFoundException {
//    CohortLearnerMappingResponse response = _learnerManagementService.mapLearnersToCohort(cohortId, learnerIds);
//    return ResponseEntity.status(HttpStatus.OK).body(response);
//  }

  @PostMapping("/cohorts/{cohortId}/learners")
  public ResponseEntity<CohortLearnerMappingResponse> createAndMapLearnersToCohort(@PathVariable("cohortId") Long cohortId,
      @RequestBody LearnerObjectBody learners) throws CohortNotFoundException {
    CohortLearnerMappingResponse response = _learnerManagementService.createAndCascadeLearnersToCohorts(cohortId, learners);
    return ResponseEntity.status(HttpStatus.OK).body(response);
  }
}
