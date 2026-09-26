package org.airtribe.LearnerManagementSystemBelC22.entity;

public class CohortDTO {
  private Long cohortId;
  private String cohortName;
  private String cohortDescription;

  public CohortDTO(Long cohortId, String cohortName, String cohortDescription) {
    this.cohortId = cohortId;
    this.cohortName = cohortName;
    this.cohortDescription = cohortDescription;
  }

  public CohortDTO() {
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
}
