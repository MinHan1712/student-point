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
import student.point.domain.ConductScores;
import student.point.repository.ConductScoresRepository;
import student.point.service.criteria.ConductScoresCriteria;
import student.point.service.dto.ConductScoresDTO;
import student.point.service.mapper.ConductScoresMapper;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link ConductScores} entities in the database.
 * The main input is a {@link ConductScoresCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link ConductScoresDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ConductScoresQueryService extends QueryService<ConductScores> {

    private static final Logger LOG = LoggerFactory.getLogger(ConductScoresQueryService.class);

    private final ConductScoresRepository conductScoresRepository;

    private final ConductScoresMapper conductScoresMapper;

    public ConductScoresQueryService(ConductScoresRepository conductScoresRepository, ConductScoresMapper conductScoresMapper) {
        this.conductScoresRepository = conductScoresRepository;
        this.conductScoresMapper = conductScoresMapper;
    }

    /**
     * Return a {@link Page} of {@link ConductScoresDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ConductScoresDTO> findByCriteria(ConductScoresCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ConductScores> specification = createSpecification(criteria);
        return conductScoresRepository.findAll(specification, page).map(conductScoresMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ConductScoresCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<ConductScores> specification = createSpecification(criteria);
        return conductScoresRepository.count(specification);
    }

    /**
     * Function to convert {@link ConductScoresCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ConductScores> createSpecification(ConductScoresCriteria criteria) {
        Specification<ConductScores> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ConductScores_.id));
            }
            if (criteria.getConductScoresCode() != null) {
                specification = specification.and(
                    buildStringSpecification(criteria.getConductScoresCode(), ConductScores_.conductScoresCode)
                );
            }
            if (criteria.getAcademicYear() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAcademicYear(), ConductScores_.academicYear));
            }
            if (criteria.getScore() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getScore(), ConductScores_.score));
            }
            if (criteria.getClassification() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getClassification(), ConductScores_.classification));
            }
            if (criteria.getEvaluation() != null) {
                specification = specification.and(buildSpecification(criteria.getEvaluation(), ConductScores_.evaluation));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), ConductScores_.createdBy));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), ConductScores_.createdDate));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), ConductScores_.lastModifiedBy));
            }
            if (criteria.getLastModifiedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModifiedDate(), ConductScores_.lastModifiedDate));
            }
            if (criteria.getStudentId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getStudentId(), root -> root.join(ConductScores_.student, JoinType.LEFT).get(Student_.id))
                );
            }
        }
        return specification;
    }
}
