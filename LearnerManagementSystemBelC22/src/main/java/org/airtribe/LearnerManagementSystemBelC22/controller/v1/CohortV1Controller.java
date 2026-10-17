package org.airtribe.LearnerManagementSystemBelC22.controller.v1;

import org.airtribe.LearnerManagementSystemBelC22.entity.Cohort;
import org.airtribe.LearnerManagementSystemBelC22.service.LearnerManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class CohortV1Controller {

  @Autowired
  private LearnerManagementService _learnerManagementService;

  @GetMapping("/v1/cohorts")
  public Page<Cohort> getPaginatedAndSortedCohorts(
      @RequestParam(value = "pageNumber", defaultValue = "0") int pageNumber,
      @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
      @RequestParam(value = "sortBy", defaultValue = "cohortId") String sortBy,
      @RequestParam(value = "sortDir", defaultValue = "asc") String sortDir
  ) {
    if (pageNumber < 0 || pageSize < 0) {
      throw new IllegalArgumentException("Page number must be non-negative and page size must be positive.");
    }

    if (!sortDir.equalsIgnoreCase("asc") && !sortDir.equalsIgnoreCase("desc")) {
      throw new IllegalArgumentException("Sort direction must be either 'asc' or 'desc'.");
    }

    return _learnerManagementService.fetchPaginatedAndSortedCohorts(pageNumber, pageSize, sortBy, sortDir);

  }
}

// Offset based querying
// select * from cohorts order by cohort_id asc limit 10 offset 0
// select * from cohorts order by cohort_id asc limit 10 offset 10
