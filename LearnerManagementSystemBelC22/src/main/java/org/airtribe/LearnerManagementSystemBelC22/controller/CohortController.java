package org.airtribe.LearnerManagementSystemBelC22.controller;

import java.util.List;
import org.airtribe.LearnerManagementSystemBelC22.entity.Cohort;
import org.airtribe.LearnerManagementSystemBelC22.exception.CohortNotFoundException;
import org.airtribe.LearnerManagementSystemBelC22.exception.LearnerNotFoundException;
import org.airtribe.LearnerManagementSystemBelC22.service.LearnerManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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

  @PostMapping("/assignLearnersToCohort")
  public Cohort assignLearnersToCohort(@RequestParam("learnerId") Long learnerId, @RequestParam("cohortId") Long cohortId)
      throws CohortNotFoundException, LearnerNotFoundException {
    return _learnerManagementService.assignLearnersToCohort(learnerId, cohortId);
  }
}
