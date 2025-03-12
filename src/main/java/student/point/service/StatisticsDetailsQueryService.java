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
import student.point.domain.StatisticsDetails;
import student.point.repository.StatisticsDetailsRepository;
import student.point.service.criteria.StatisticsDetailsCriteria;
import student.point.service.dto.StatisticsDetailsDTO;
import student.point.service.mapper.StatisticsDetailsMapper;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link StatisticsDetails} entities in the database.
 * The main input is a {@link StatisticsDetailsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link StatisticsDetailsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class StatisticsDetailsQueryService extends QueryService<StatisticsDetails> {

    private static final Logger LOG = LoggerFactory.getLogger(StatisticsDetailsQueryService.class);

    private final StatisticsDetailsRepository statisticsDetailsRepository;

    private final StatisticsDetailsMapper statisticsDetailsMapper;

    public StatisticsDetailsQueryService(
        StatisticsDetailsRepository statisticsDetailsRepository,
        StatisticsDetailsMapper statisticsDetailsMapper
    ) {
        this.statisticsDetailsRepository = statisticsDetailsRepository;
        this.statisticsDetailsMapper = statisticsDetailsMapper;
    }

    /**
     * Return a {@link Page} of {@link StatisticsDetailsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<StatisticsDetailsDTO> findByCriteria(StatisticsDetailsCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<StatisticsDetails> specification = createSpecification(criteria);
        return statisticsDetailsRepository.findAll(specification, page).map(statisticsDetailsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(StatisticsDetailsCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<StatisticsDetails> specification = createSpecification(criteria);
        return statisticsDetailsRepository.count(specification);
    }

    /**
     * Function to convert {@link StatisticsDetailsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<StatisticsDetails> createSpecification(StatisticsDetailsCriteria criteria) {
        Specification<StatisticsDetails> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), StatisticsDetails_.id));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), StatisticsDetails_.code));
            }
            if (criteria.getTotalScholarship() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getTotalScholarship(), StatisticsDetails_.totalScholarship)
                );
            }
            if (criteria.getGraduationDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getGraduationDate(), StatisticsDetails_.graduationDate));
            }
            if (criteria.getNotes() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNotes(), StatisticsDetails_.notes));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getStatus(), StatisticsDetails_.status));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), StatisticsDetails_.createdBy));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), StatisticsDetails_.createdDate));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(
                    buildStringSpecification(criteria.getLastModifiedBy(), StatisticsDetails_.lastModifiedBy)
                );
            }
            if (criteria.getLastModifiedDate() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getLastModifiedDate(), StatisticsDetails_.lastModifiedDate)
                );
            }
            if (criteria.getStudentId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getStudentId(), root ->
                        root.join(StatisticsDetails_.student, JoinType.LEFT).get(Student_.id)
                    )
                );
            }
            if (criteria.getStatisticsId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getStatisticsId(), root ->
                        root.join(StatisticsDetails_.statistics, JoinType.LEFT).get(Statistics_.id)
                    )
                );
            }
        }
        return specification;
    }
}
