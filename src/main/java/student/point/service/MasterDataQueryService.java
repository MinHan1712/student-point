package student.point.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import student.point.domain.*; // for static metamodels
import student.point.domain.MasterData;
import student.point.repository.MasterDataRepository;
import student.point.service.criteria.MasterDataCriteria;
import student.point.service.dto.MasterDataDTO;
import student.point.service.mapper.MasterDataMapper;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link MasterData} entities in the database.
 * The main input is a {@link MasterDataCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link MasterDataDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MasterDataQueryService extends QueryService<MasterData> {

    private static final Logger LOG = LoggerFactory.getLogger(MasterDataQueryService.class);

    private final MasterDataRepository masterDataRepository;

    private final MasterDataMapper masterDataMapper;

    public MasterDataQueryService(MasterDataRepository masterDataRepository, MasterDataMapper masterDataMapper) {
        this.masterDataRepository = masterDataRepository;
        this.masterDataMapper = masterDataMapper;
    }

    /**
     * Return a {@link Page} of {@link MasterDataDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MasterDataDTO> findByCriteria(MasterDataCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MasterData> specification = createSpecification(criteria);
        return masterDataRepository.findAll(specification, page).map(masterDataMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MasterDataCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<MasterData> specification = createSpecification(criteria);
        return masterDataRepository.count(specification);
    }

    /**
     * Function to convert {@link MasterDataCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<MasterData> createSpecification(MasterDataCriteria criteria) {
        Specification<MasterData> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), MasterData_.id));
            }
            if (criteria.getKey() != null) {
                specification = specification.and(buildStringSpecification(criteria.getKey(), MasterData_.key));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), MasterData_.code));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), MasterData_.name));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), MasterData_.description));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getStatus(), MasterData_.status));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), MasterData_.createdBy));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), MasterData_.createdDate));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), MasterData_.lastModifiedBy));
            }
            if (criteria.getLastModifiedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModifiedDate(), MasterData_.lastModifiedDate));
            }
        }
        return specification;
    }
}
