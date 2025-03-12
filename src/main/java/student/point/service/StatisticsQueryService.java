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
import student.point.domain.Statistics;
import student.point.repository.StatisticsRepository;
import student.point.service.criteria.StatisticsCriteria;
import student.point.service.dto.StatisticsDTO;
import student.point.service.mapper.StatisticsMapper;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Statistics} entities in the database.
 * The main input is a {@link StatisticsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link StatisticsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class StatisticsQueryService extends QueryService<Statistics> {

    private static final Logger LOG = LoggerFactory.getLogger(StatisticsQueryService.class);

    private final StatisticsRepository statisticsRepository;

    private final StatisticsMapper statisticsMapper;

    public StatisticsQueryService(StatisticsRepository statisticsRepository, StatisticsMapper statisticsMapper) {
        this.statisticsRepository = statisticsRepository;
        this.statisticsMapper = statisticsMapper;
    }

    /**
     * Return a {@link Page} of {@link StatisticsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<StatisticsDTO> findByCriteria(StatisticsCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Statistics> specification = createSpecification(criteria);
        return statisticsRepository.findAll(specification, page).map(statisticsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(StatisticsCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<Statistics> specification = createSpecification(criteria);
        return statisticsRepository.count(specification);
    }

    /**
     * Function to convert {@link StatisticsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Statistics> createSpecification(StatisticsCriteria criteria) {
        Specification<Statistics> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Statistics_.id));
            }
            if (criteria.getStatisticsCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStatisticsCode(), Statistics_.statisticsCode));
            }
            if (criteria.getAcademicYear() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAcademicYear(), Statistics_.academicYear));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildSpecification(criteria.getType(), Statistics_.type));
            }
            if (criteria.getNotes() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNotes(), Statistics_.notes));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getStatus(), Statistics_.status));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), Statistics_.createdBy));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), Statistics_.createdDate));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), Statistics_.lastModifiedBy));
            }
            if (criteria.getLastModifiedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModifiedDate(), Statistics_.lastModifiedDate));
            }
            if (criteria.getStatisticsDetailsId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getStatisticsDetailsId(), root ->
                        root.join(Statistics_.statisticsDetails, JoinType.LEFT).get(StatisticsDetails_.id)
                    )
                );
            }
        }
        return specification;
    }
}
