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
import student.point.domain.Grades;
import student.point.repository.GradesRepository;
import student.point.service.criteria.GradesCriteria;
import student.point.service.dto.GradesDTO;
import student.point.service.mapper.GradesMapper;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Grades} entities in the database.
 * The main input is a {@link GradesCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link GradesDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class GradesQueryService extends QueryService<Grades> {

    private static final Logger LOG = LoggerFactory.getLogger(GradesQueryService.class);

    private final GradesRepository gradesRepository;

    private final GradesMapper gradesMapper;

    public GradesQueryService(GradesRepository gradesRepository, GradesMapper gradesMapper) {
        this.gradesRepository = gradesRepository;
        this.gradesMapper = gradesMapper;
    }

    /**
     * Return a {@link Page} of {@link GradesDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<GradesDTO> findByCriteria(GradesCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Grades> specification = createSpecification(criteria);
        return gradesRepository.findAll(specification, page).map(gradesMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(GradesCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<Grades> specification = createSpecification(criteria);
        return gradesRepository.count(specification);
    }

    /**
     * Function to convert {@link GradesCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Grades> createSpecification(GradesCriteria criteria) {
        Specification<Grades> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Grades_.id));
            }
            if (criteria.getGradesCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getGradesCode(), Grades_.gradesCode));
            }
            if (criteria.getCredit() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCredit(), Grades_.credit));
            }
            if (criteria.getStudyAttempt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStudyAttempt(), Grades_.studyAttempt));
            }
            if (criteria.getExamAttempt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getExamAttempt(), Grades_.examAttempt));
            }
            if (criteria.getProcessScore() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getProcessScore(), Grades_.processScore));
            }
            if (criteria.getExamScore() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getExamScore(), Grades_.examScore));
            }
            if (criteria.getScore10() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getScore10(), Grades_.score10));
            }
            if (criteria.getScore4() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getScore4(), Grades_.score4));
            }
            if (criteria.getLetterGrade() != null) {
                specification = specification.and(buildSpecification(criteria.getLetterGrade(), Grades_.letterGrade));
            }
            if (criteria.getEvaluation() != null) {
                specification = specification.and(buildSpecification(criteria.getEvaluation(), Grades_.evaluation));
            }
            if (criteria.getNotes() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNotes(), Grades_.notes));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getStatus(), Grades_.status));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), Grades_.createdBy));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), Grades_.createdDate));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), Grades_.lastModifiedBy));
            }
            if (criteria.getLastModifiedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModifiedDate(), Grades_.lastModifiedDate));
            }
            if (criteria.getStudentId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getStudentId(), root -> root.join(Grades_.student, JoinType.LEFT).get(Student_.id))
                );
            }
            if (criteria.getClassesId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getClassesId(), root -> root.join(Grades_.classes, JoinType.LEFT).get(Classes_.id))
                );
            }
        }
        return specification;
    }
}
