package org.airtribe.LearnerManagementSystemBelC22.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.List;


@Entity
public class Course {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long courseId;

  private String courseName;

  private String courseDescription;

  @OneToMany(mappedBy = "course")
  private List<Cohort> cohorts;

  public Course(Long courseId, String courseName, String courseDescription, List<Cohort> cohorts) {
    this.courseId = courseId;
    this.courseName = courseName;
    this.courseDescription = courseDescription;
    this.cohorts = cohorts;
  }

  public Course() {

  }

  public Long getCourseId() {
    return courseId;
  }

  public void setCourseId(Long courseId) {
    this.courseId = courseId;
  }

  public String getCourseName() {
    return courseName;
  }

  public void setCourseName(String courseName) {
    this.courseName = courseName;
  }

  public String getCourseDescription() {
    return courseDescription;
  }

  public void setCourseDescription(String courseDescription) {
    this.courseDescription = courseDescription;
  }

  public List<Cohort> getCohorts() {
    return cohorts;
  }

  public void setCohorts(List<Cohort> cohorts) {
    this.cohorts = cohorts;
  }
}
