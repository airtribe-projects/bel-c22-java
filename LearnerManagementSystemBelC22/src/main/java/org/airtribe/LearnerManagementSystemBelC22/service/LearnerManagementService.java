package org.airtribe.LearnerManagementSystemBelC22.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.swing.text.html.Option;
import org.airtribe.LearnerManagementSystemBelC22.entity.Cohort;
import org.airtribe.LearnerManagementSystemBelC22.entity.CohortDTO;
import org.airtribe.LearnerManagementSystemBelC22.entity.Learner;
import org.airtribe.LearnerManagementSystemBelC22.entity.LearnerDTO;
import org.airtribe.LearnerManagementSystemBelC22.exception.CohortNotFoundException;
import org.airtribe.LearnerManagementSystemBelC22.exception.LearnerNotFoundException;
import org.airtribe.LearnerManagementSystemBelC22.repository.CohortRepository;
import org.airtribe.LearnerManagementSystemBelC22.repository.LearnerRepository;
import org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.servlet.filter.OrderedHiddenHttpMethodFilter;
import org.springframework.stereotype.Service;


@Service
public class LearnerManagementService {

  @Autowired
  private LearnerRepository _learnerRepository;

  @Autowired
  private CohortRepository _cohortRepository;

  public Learner createLearner(Learner learner) {
      return _learnerRepository.save(learner);
  }

  public List<Learner> fetchAllLearners() {
    return _learnerRepository.findAll();
  }

  public Learner fetchLearnerById(Long learnerId) throws LearnerNotFoundException {
    if (_learnerRepository.findById(learnerId).isPresent()) {
      return _learnerRepository.findById(learnerId).get();
    }

    throw new LearnerNotFoundException("Learner with id " + learnerId + " not found");

  }


  public List<Learner> fetchLearnerByName(String learnerName) {
    return _learnerRepository.findByLearnerName(learnerName); // Placeholder for actual implementation
  }

  public List<Learner> fetchLearnersBasedOnBusinessLogic(String learnerName, Long learnerId)
      throws LearnerNotFoundException {
    if ((learnerName == null || learnerName.isEmpty()) && (learnerId == null)) {
      return fetchAllLearners();
    }
    if (learnerId != null) {
      return List.of(fetchLearnerById(learnerId));
    }
    return fetchLearnerByName(learnerName);
  }

  public Cohort createCohort(Cohort cohort) {
    return _cohortRepository.save(cohort);
  }

  public List<Cohort> getAllCohorts() {
    return _cohortRepository.findAll();
  }

  public Cohort assignLearnersToCohort(Long learnerId, Long cohortId)
      throws LearnerNotFoundException, CohortNotFoundException {
    Optional<Learner> learnerOptional = _learnerRepository.findById(learnerId);

    if (!learnerOptional.isPresent()) {
      throw new LearnerNotFoundException("Learner with id " + learnerId + " not found");
    }
    Optional<Cohort> cohortOptional = _cohortRepository.findById(cohortId);

    if (!cohortOptional.isPresent()) {
      throw new CohortNotFoundException("Cohort with id " + cohortId + " not found");
    }

    Cohort cohort = cohortOptional.get();
    Learner learner = learnerOptional.get();

    cohort.getLearners().add(learner);
    return _cohortRepository.save(cohort);
  }

  public List<LearnerDTO> convertLearnersToDTOs(List<Learner> learners) {
    List<LearnerDTO> learnerDTOS = new ArrayList<>();
    for (Learner learner : learners) {
      LearnerDTO learnerDTO = new LearnerDTO();
      learnerDTO.setLearnerId(learner.getLearnerId());
      learnerDTO.setLearnerName(learner.getLearnerName());
      learnerDTO.setLearnerEmail(learner.getLearnerEmail());
      learnerDTO.setLearnerPhone(learner.getLearnerPhone());
      List<CohortDTO> cohortDTOS = new ArrayList<>();
      for (Cohort cohort : learner.getCohorts()) {
        CohortDTO cohortDTO = new CohortDTO();
        cohortDTO.setCohortId(cohort.getCohortId());
        cohortDTO.setCohortName(cohort.getCohortName());
        cohortDTO.setCohortDescription(cohort.getCohortDescription());
        cohortDTOS.add(cohortDTO);
      }
      learnerDTO.setCohortDtos(cohortDTOS);
      learnerDTOS.add(learnerDTO);
    }
    return learnerDTOS;

  }

  // JPQL Query
}
