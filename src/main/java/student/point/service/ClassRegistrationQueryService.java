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
import student.point.domain.ClassRegistration;
import student.point.repository.ClassRegistrationRepository;
import student.point.service.criteria.ClassRegistrationCriteria;
import student.point.service.dto.ClassRegistrationDTO;
import student.point.service.mapper.ClassRegistrationMapper;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link ClassRegistration} entities in the database.
 * The main input is a {@link ClassRegistrationCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link ClassRegistrationDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ClassRegistrationQueryService extends QueryService<ClassRegistration> {

    private static final Logger LOG = LoggerFactory.getLogger(ClassRegistrationQueryService.class);

    private final ClassRegistrationRepository classRegistrationRepository;

    private final ClassRegistrationMapper classRegistrationMapper;

    public ClassRegistrationQueryService(
        ClassRegistrationRepository classRegistrationRepository,
        ClassRegistrationMapper classRegistrationMapper
    ) {
        this.classRegistrationRepository = classRegistrationRepository;
        this.classRegistrationMapper = classRegistrationMapper;
    }

    /**
     * Return a {@link Page} of {@link ClassRegistrationDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ClassRegistrationDTO> findByCriteria(ClassRegistrationCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ClassRegistration> specification = createSpecification(criteria);
        return classRegistrationRepository.findAll(specification, page).map(classRegistrationMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ClassRegistrationCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<ClassRegistration> specification = createSpecification(criteria);
        return classRegistrationRepository.count(specification);
    }

    /**
     * Function to convert {@link ClassRegistrationCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ClassRegistration> createSpecification(ClassRegistrationCriteria criteria) {
        Specification<ClassRegistration> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ClassRegistration_.id));
            }
            if (criteria.getRegisterDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRegisterDate(), ClassRegistration_.registerDate));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getStatus(), ClassRegistration_.status));
            }
            if (criteria.getRemarks() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRemarks(), ClassRegistration_.remarks));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), ClassRegistration_.createdBy));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), ClassRegistration_.createdDate));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(
                    buildStringSpecification(criteria.getLastModifiedBy(), ClassRegistration_.lastModifiedBy)
                );
            }
            if (criteria.getLastModifiedDate() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getLastModifiedDate(), ClassRegistration_.lastModifiedDate)
                );
            }
            if (criteria.getStudentId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getStudentId(), root ->
                        root.join(ClassRegistration_.student, JoinType.LEFT).get(Student_.id)
                    )
                );
            }
            if (criteria.getClassesId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getClassesId(), root ->
                        root.join(ClassRegistration_.classes, JoinType.LEFT).get(Classes_.id)
                    )
                );
            }
        }
        return specification;
    }
}
