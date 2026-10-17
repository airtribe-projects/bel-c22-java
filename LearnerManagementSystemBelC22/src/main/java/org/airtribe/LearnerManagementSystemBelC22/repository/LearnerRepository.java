package org.airtribe.LearnerManagementSystemBelC22.repository;

import java.util.List;
import java.util.Optional;
import javax.swing.text.html.Option;
import org.airtribe.LearnerManagementSystemBelC22.entity.Learner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface LearnerRepository extends JpaRepository<Learner, Long> {
  List<Learner> findByLearnerName(String learnerName);

  Optional<Learner> findByLearnerEmail(String learnerEmail);

  @Query("SELECT l FROM Learner l WHERE l.learnerName = :learnerName") // JPQL query
  List<Learner> findByName(String learnerName);
}


// Learner
   // -> fetchById
  // -> getAll
   // create
// learnerName
// Cohort
// -> fetchById
// -> getAll
// create
// Courses
// -> fetchById
// -> getAll
// create
