package org.airtribe.LearnerManagementSystemBelC22.repository;

import org.airtribe.LearnerManagementSystemBelC22.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
}
