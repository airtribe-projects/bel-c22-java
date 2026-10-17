package org.airtribe.LearnerManagementSystemBelC22.entity;

import java.util.List;


public class LearnerListBody {
  private List<Long> learnerIds;

  public LearnerListBody(List<Long> learnerIds) {
    this.learnerIds = learnerIds;
  }

  public List<Long> getLearnerIds() {
    return learnerIds;
  }

  public void setLearnerIds(List<Long> learnerIds) {
    this.learnerIds = learnerIds;
  }
}
