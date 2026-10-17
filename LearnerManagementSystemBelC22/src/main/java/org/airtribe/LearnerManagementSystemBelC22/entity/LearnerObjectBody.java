package org.airtribe.LearnerManagementSystemBelC22.entity;

import java.util.List;


public class LearnerObjectBody {
  private List<Learner> learners;

  public LearnerObjectBody(List<Learner> learners) {
    this.learners = learners;
  }

  public List<Learner> getLearners() {
    return learners;
  }

  public void setLearners(List<Learner> learners) {
    this.learners = learners;
  }
}
