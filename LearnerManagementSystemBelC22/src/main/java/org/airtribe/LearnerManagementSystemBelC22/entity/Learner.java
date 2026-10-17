package org.airtribe.LearnerManagementSystemBelC22.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;


@Entity
public class Learner {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long learnerId;

  @NotNull
  @NotEmpty
  private String learnerName;

  @Column(unique = true)
  @NotNull
  @NotEmpty
  @Email
  private String learnerEmail;

  @NotNull
  @NotEmpty
  private String learnerPhone;

  @ManyToMany(mappedBy = "learners")
  @JsonIgnore
  private List<Cohort> cohorts;


  public Learner(Long learnerId, String learnerName, String learnerEmail, String learnerPhone) {
    this.learnerId = learnerId;
    this.learnerName = learnerName;
    this.learnerEmail = learnerEmail;
    this.learnerPhone = learnerPhone;
  }

  public Learner(Long learnerId, String learnerName, String learnerEmail, String learnerPhone, List<Cohort> cohorts) {
    this.learnerId = learnerId;
    this.learnerName = learnerName;
    this.learnerEmail = learnerEmail;
    this.learnerPhone = learnerPhone;
    this.cohorts = cohorts;
  }

  public Learner(String learnerName, String learnerEmail, String learnerPhone) {
    this.learnerName = learnerName;
    this.learnerEmail = learnerEmail;
    this.learnerPhone = learnerPhone;
  }

  public Learner() {

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

  public List<Cohort> getCohorts() {
    return cohorts;
  }

  public void setCohorts(List<Cohort> cohorts) {
    this.cohorts = cohorts;
  }
}
