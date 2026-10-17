package org.airtribe.LearnerManagementSystemBelC22.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import java.util.List;


@Entity
public class Cohort {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long cohortId;

  private String cohortName;

  private String cohortDescription;

  @ManyToMany(cascade = CascadeType.ALL)
  private List<Learner> learners;

  @ManyToOne
  private Course course;

  public Cohort(Long cohortId, String cohortName, String cohortDescription, List<Learner> learners) {
    this.cohortId = cohortId;
    this.cohortName = cohortName;
    this.cohortDescription = cohortDescription;
    this.learners = learners;
  }

  public Cohort() {

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

  public List<Learner> getLearners() {
    return learners;
  }

  public void setLearners(List<Learner> learners) {
    this.learners = learners;
  }
}
