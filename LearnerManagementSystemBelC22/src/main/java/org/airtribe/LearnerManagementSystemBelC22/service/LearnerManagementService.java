package org.airtribe.LearnerManagementSystemBelC22.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.airtribe.LearnerManagementSystemBelC22.entity.Cohort;
import org.airtribe.LearnerManagementSystemBelC22.entity.CohortDTO;
import org.airtribe.LearnerManagementSystemBelC22.entity.CohortLearnerMappingResponse;
import org.airtribe.LearnerManagementSystemBelC22.entity.Course;
import org.airtribe.LearnerManagementSystemBelC22.entity.Learner;
import org.airtribe.LearnerManagementSystemBelC22.entity.LearnerDTO;
import org.airtribe.LearnerManagementSystemBelC22.entity.LearnerListBody;
import org.airtribe.LearnerManagementSystemBelC22.entity.LearnerObjectBody;
import org.airtribe.LearnerManagementSystemBelC22.exception.CohortNotFoundException;
import org.airtribe.LearnerManagementSystemBelC22.exception.LearnerNotFoundException;
import org.airtribe.LearnerManagementSystemBelC22.repository.CohortRepository;
import org.airtribe.LearnerManagementSystemBelC22.repository.CourseRepository;
import org.airtribe.LearnerManagementSystemBelC22.repository.LearnerRepository;
import org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.servlet.filter.OrderedHiddenHttpMethodFilter;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class LearnerManagementService {

  @Autowired
  private LearnerRepository _learnerRepository;

  @Autowired
  private CohortRepository _cohortRepository;

  @Autowired
  private CourseRepository _courseRepository;

  public Learner createLearner(Learner learner) {
      return _learnerRepository.save(learner);
  }

  public List<Learner> fetchAllLearners() {
    return _learnerRepository.findAll();
  }

//  @Cacheable(value = "learners", key = "#learnerId")
  public Learner fetchLearnerById(Long learnerId) throws LearnerNotFoundException {

    //  branching
    if (learnerId == null || learnerId < 0) {
      throw new IllegalArgumentException("Learner Id cannot be null or negative");
    }

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

  public Course createCourse(Course course) {
    return _courseRepository.save(course);
  }

  public CohortLearnerMappingResponse mapLearnersToCohort(Long cohortId, LearnerListBody learnerIds)
      throws CohortNotFoundException {
    Optional<Cohort> cohortOptional = _cohortRepository.findById(cohortId);
    if (!cohortOptional.isPresent()) {
      throw new CohortNotFoundException("Cohort with id " + cohortId + " not found");
    }

    Cohort cohort = cohortOptional.get();

    Set<Long> requestedLearnerIds = new HashSet<>();
    for (Long learnerId : learnerIds.getLearnerIds()) {
      requestedLearnerIds.add(learnerId);
    }

    List<Long> addedLearners = new ArrayList<>();
    List<Long> notFoundLearnerIds = new ArrayList<>();
    List<Long> alreadyAssignedLearnerIds = new ArrayList<>();
    List<Learner> learnersToAdd = new ArrayList<>();

    for (Long learnerId : requestedLearnerIds) {
      // Learner not found
      Optional<Learner> learnerOptional = _learnerRepository.findById(learnerId);
      if (!learnerOptional.isPresent()) {
        notFoundLearnerIds.add(learnerId);
        continue;
      }

      // Already assigned Learner
      if (cohort.getLearners().contains(learnerOptional.get())) {
        alreadyAssignedLearnerIds.add(learnerId);
        continue;
      }

      learnersToAdd.add(learnerOptional.get());
      addedLearners.add(learnerId);
    }

    cohort.getLearners().addAll(learnersToAdd);
    _cohortRepository.save(cohort);


    return new CohortLearnerMappingResponse(cohortId, cohort.getCohortName(), cohort.getCohortDescription(), addedLearners, alreadyAssignedLearnerIds, notFoundLearnerIds);

  }

  public CohortLearnerMappingResponse createAndCascadeLearnersToCohorts(Long cohortId, LearnerObjectBody learners)
      throws CohortNotFoundException {

    Cohort cohort = _cohortRepository.findById(cohortId)
        .orElseThrow(() -> new CohortNotFoundException("Cohort with id " + cohortId + " not found"));

    List<Long> addedLearnerIds = new ArrayList<>();
    List<Long> alreadyMappedLearnerIds = new ArrayList<>();

    for (Learner requestedLearner : learners.getLearners()) {
      Learner resolvedLearner = resolveOrCreateLearner(requestedLearner);

      // Relationship already exists: leave it untouched, just report it.
      if (isLearnerMappedToCohort(cohort, resolvedLearner.getLearnerId())) {
        alreadyMappedLearnerIds.add(resolvedLearner.getLearnerId());
        continue;
      }

      // Learner exists (or was just created) but isn't mapped yet: map the managed entity.
      cohort.getLearners().add(resolvedLearner);
      addedLearnerIds.add(resolvedLearner.getLearnerId());
    }

    Cohort savedCohort = _cohortRepository.save(cohort);

    return new CohortLearnerMappingResponse(cohortId, savedCohort.getCohortName(), savedCohort.getCohortDescription(),
        addedLearnerIds, alreadyMappedLearnerIds, null);

  }

  /**
   * Resolves a requested learner to an already-persisted, managed entity when one exists
   * (by id, falling back to the unique email), so that an existing learner is never
   * re-inserted/cascaded as a new row. If no matching learner exists yet, it is created.
   */
  private Learner resolveOrCreateLearner(Learner requestedLearner) {
    return findExistingLearner(requestedLearner).orElseGet(() -> _learnerRepository.save(requestedLearner));
  }

  private Optional<Learner> findExistingLearner(Learner learner) {
    if (learner.getLearnerId() != null) {
      Optional<Learner> learnerById = _learnerRepository.findById(learner.getLearnerId());
      if (learnerById.isPresent()) {
        return learnerById;
      }
    }

    if (learner.getLearnerEmail() != null && !learner.getLearnerEmail().isEmpty()) {
      return _learnerRepository.findByLearnerEmail(learner.getLearnerEmail());
    }

    return Optional.empty();
  }

  private boolean isLearnerMappedToCohort(Cohort cohort, Long learnerId) {
    return cohort.getLearners().stream()
        .anyMatch(mappedLearner -> learnerId.equals(mappedLearner.getLearnerId()));
  }

  public Page<Cohort> fetchPaginatedAndSortedCohorts(int pageNumber, int pageSize, String sortBy, String sortDir) {
    Sort.Direction direction;
    if (sortDir.equalsIgnoreCase("asc")) {
      direction = Sort.Direction.ASC;
    } else if (sortDir.equalsIgnoreCase("desc")) {
      direction = Sort.Direction.DESC;
    } else {
      throw new IllegalArgumentException("Invalid sort direction: " + sortDir);
    }
    Sort sort = Sort.by(direction, sortBy);
    Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
    return _cohortRepository.findAll(pageable);
  }

  // JPQL Query
}
