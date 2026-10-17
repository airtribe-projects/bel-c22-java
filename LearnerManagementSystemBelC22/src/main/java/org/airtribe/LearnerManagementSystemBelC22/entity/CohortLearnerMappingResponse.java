package org.airtribe.LearnerManagementSystemBelC22.entity;

import java.util.List;


public class CohortLearnerMappingResponse {
  private Long cohortId;

  private String cohortName;

  private String cohortDescription;

  private List<Long> addedLearnerIds;

  private List<Long> alreadyMappedLearnerIds;

  private List<Long> notFoundLearnerIds;

  public CohortLearnerMappingResponse(Long cohortId, String cohortName, String cohortDescription,
      List<Long> addedLearnerIds, List<Long> alreadyMappedLearnerIds, List<Long> notFoundLearnerIds) {
    this.cohortId = cohortId;
    this.cohortName = cohortName;
    this.cohortDescription = cohortDescription;
    this.addedLearnerIds = addedLearnerIds;
    this.alreadyMappedLearnerIds = alreadyMappedLearnerIds;
    this.notFoundLearnerIds = notFoundLearnerIds;
  }

  public Long getCohortId() {
    return cohortId;
  }

  public void setCohortId(Long cohortId) {
    this.cohortId = cohortId;
  }

  public String getCohortName() {
    return cohortName;
  }

  public void setCohortName(String cohortName) {
    this.cohortName = cohortName;
  }

  public String getCohortDescription() {
    return cohortDescription;
  }

  public void setCohortDescription(String cohortDescription) {
    this.cohortDescription = cohortDescription;
  }

  public List<Long> getAddedLearnerIds() {
    return addedLearnerIds;
  }

  public void setAddedLearnerIds(List<Long> addedLearnerIds) {
    this.addedLearnerIds = addedLearnerIds;
  }

  public List<Long> getAlreadyMappedLearnerIds() {
    return alreadyMappedLearnerIds;
  }

  public void setAlreadyMappedLearnerIds(List<Long> alreadyMappedLearnerIds) {
    this.alreadyMappedLearnerIds = alreadyMappedLearnerIds;
  }

  public List<Long> getNotFoundLearnerIds() {
    return notFoundLearnerIds;
  }

  public void setNotFoundLearnerIds(List<Long> notFoundLearnerIds) {
    this.notFoundLearnerIds = notFoundLearnerIds;
  }
}
