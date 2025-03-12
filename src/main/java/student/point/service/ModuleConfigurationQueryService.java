package student.point.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import student.point.domain.*; // for static metamodels
import student.point.domain.ModuleConfiguration;
import student.point.repository.ModuleConfigurationRepository;
import student.point.service.criteria.ModuleConfigurationCriteria;
import student.point.service.dto.ModuleConfigurationDTO;
import student.point.service.mapper.ModuleConfigurationMapper;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link ModuleConfiguration} entities in the database.
 * The main input is a {@link ModuleConfigurationCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link ModuleConfigurationDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ModuleConfigurationQueryService extends QueryService<ModuleConfiguration> {

    private static final Logger LOG = LoggerFactory.getLogger(ModuleConfigurationQueryService.class);

    private final ModuleConfigurationRepository moduleConfigurationRepository;

    private final ModuleConfigurationMapper moduleConfigurationMapper;

    public ModuleConfigurationQueryService(
        ModuleConfigurationRepository moduleConfigurationRepository,
        ModuleConfigurationMapper moduleConfigurationMapper
    ) {
        this.moduleConfigurationRepository = moduleConfigurationRepository;
        this.moduleConfigurationMapper = moduleConfigurationMapper;
    }

    /**
     * Return a {@link Page} of {@link ModuleConfigurationDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ModuleConfigurationDTO> findByCriteria(ModuleConfigurationCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ModuleConfiguration> specification = createSpecification(criteria);
        return moduleConfigurationRepository.findAll(specification, page).map(moduleConfigurationMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ModuleConfigurationCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<ModuleConfiguration> specification = createSpecification(criteria);
        return moduleConfigurationRepository.count(specification);
    }

    /**
     * Function to convert {@link ModuleConfigurationCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ModuleConfiguration> createSpecification(ModuleConfigurationCriteria criteria) {
        Specification<ModuleConfiguration> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ModuleConfiguration_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), ModuleConfiguration_.name));
            }
            if (criteria.getPrefix() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPrefix(), ModuleConfiguration_.prefix));
            }
            if (criteria.getPadding() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPadding(), ModuleConfiguration_.padding));
            }
            if (criteria.getNumberNextActual() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getNumberNextActual(), ModuleConfiguration_.numberNextActual)
                );
            }
            if (criteria.getNumberIncrement() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getNumberIncrement(), ModuleConfiguration_.numberIncrement)
                );
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), ModuleConfiguration_.createdBy));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), ModuleConfiguration_.createdDate));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(
                    buildStringSpecification(criteria.getLastModifiedBy(), ModuleConfiguration_.lastModifiedBy)
                );
            }
            if (criteria.getLastModifiedDate() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getLastModifiedDate(), ModuleConfiguration_.lastModifiedDate)
                );
            }
        }
        return specification;
    }
}
