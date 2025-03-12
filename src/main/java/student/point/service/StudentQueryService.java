package student.point.service;

import jakarta.persistence.criteria.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import student.point.domain.*; // for static metamodels
import student.point.domain.Student;
import student.point.repository.StudentRepository;
import student.point.service.criteria.StudentCriteria;
import student.point.service.dto.StudentDTO;
import student.point.service.mapper.StudentMapper;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Student} entities in the database.
 * The main input is a {@link StudentCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link StudentDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class StudentQueryService extends QueryService<Student> {

    private static final Logger LOG = LoggerFactory.getLogger(StudentQueryService.class);

    private final StudentRepository studentRepository;

    private final StudentMapper studentMapper;

    public StudentQueryService(StudentRepository studentRepository, StudentMapper studentMapper) {
        this.studentRepository = studentRepository;
        this.studentMapper = studentMapper;
    }

    /**
     * Return a {@link Page} of {@link StudentDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<StudentDTO> findByCriteria(StudentCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Student> specification = createSpecification(criteria);
        return studentRepository.findAll(specification, page).map(studentMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(StudentCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<Student> specification = createSpecification(criteria);
        return studentRepository.count(specification);
    }

    /**
     * Function to convert {@link StudentCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Student> createSpecification(StudentCriteria criteria) {
        Specification<Student> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Student_.id));
            }
            if (criteria.getStudentCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStudentCode(), Student_.studentCode));
            }
            if (criteria.getFullName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFullName(), Student_.fullName));
            }
            if (criteria.getDateOfBirth() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateOfBirth(), Student_.dateOfBirth));
            }
            if (criteria.getGender() != null) {
                specification = specification.and(buildSpecification(criteria.getGender(), Student_.gender));
            }
            if (criteria.getAddress() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAddress(), Student_.address));
            }
            if (criteria.getPhoneNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPhoneNumber(), Student_.phoneNumber));
            }
            if (criteria.getEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmail(), Student_.email));
            }
            if (criteria.getNotes() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNotes(), Student_.notes));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getStatus(), Student_.status));
            }
            if (criteria.getDateEnrollment() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateEnrollment(), Student_.dateEnrollment));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), Student_.createdBy));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), Student_.createdDate));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), Student_.lastModifiedBy));
            }
            if (criteria.getLastModifiedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModifiedDate(), Student_.lastModifiedDate));
            }
            if (criteria.getClassRegistrationId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getClassRegistrationId(), root ->
                        root.join(Student_.classRegistrations, JoinType.LEFT).get(ClassRegistration_.id)
                    )
                );
            }
            if (criteria.getConductScoresId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getConductScoresId(), root ->
                        root.join(Student_.conductScores, JoinType.LEFT).get(ConductScores_.id)
                    )
                );
            }
            if (criteria.getStatisticsDetailsId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getStatisticsDetailsId(), root ->
                        root.join(Student_.statisticsDetails, JoinType.LEFT).get(StatisticsDetails_.id)
                    )
                );
            }
            if (criteria.getGradesId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getGradesId(), root -> root.join(Student_.grades, JoinType.LEFT).get(Grades_.id))
                );
            }
        }
        return specification;
    }
}
