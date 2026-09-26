package org.airtribe.LearnerManagementSystemBelC22.entity;

import java.util.List;


public class LearnerDTO {
  private Long learnerId;
  private String learnerName;
  private String learnerEmail;
  private String learnerPhone;
  private List<CohortDTO> cohortDtos;

  public LearnerDTO(Long learnerId, String learnerName, String learnerEmail, String learnerPhone) {
    this.learnerId = learnerId;
    this.learnerName = learnerName;
    this.learnerEmail = learnerEmail;
    this.learnerPhone = learnerPhone;
  }



  public LearnerDTO() {
  }

  public LearnerDTO(Long learnerId, String learnerName, String learnerEmail, String learnerPhone,
      List<CohortDTO> cohortDtos) {
    this.learnerId = learnerId;
    this.learnerName = learnerName;
    this.learnerEmail = learnerEmail;
    this.learnerPhone = learnerPhone;
    this.cohortDtos = cohortDtos;
  }

  public Long getLearnerId() {
    return learnerId;
  }

  public void setLearnerId(Long learnerId) {
    this.learnerId = learnerId;
  }

  public String getLearnerName() {
    return learnerName;
  }

  public void setLearnerName(String learnerName) {
    this.learnerName = learnerName;
  }

  public String getLearnerEmail() {
    return learnerEmail;
  }

  public void setLearnerEmail(String learnerEmail) {
    this.learnerEmail = learnerEmail;
  }

  public String getLearnerPhone() {
    return learnerPhone;
  }

  public void setLearnerPhone(String learnerPhone) {
    this.learnerPhone = learnerPhone;
  }

  public List<CohortDTO> getCohortDtos() {
    return cohortDtos;
  }

  public void setCohortDtos(List<CohortDTO> cohortDtos) {
    this.cohortDtos = cohortDtos;
  }
}
