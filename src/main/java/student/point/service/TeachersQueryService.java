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
import student.point.domain.Teachers;
import student.point.repository.TeachersRepository;
import student.point.service.criteria.TeachersCriteria;
import student.point.service.dto.TeachersDTO;
import student.point.service.mapper.TeachersMapper;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Teachers} entities in the database.
 * The main input is a {@link TeachersCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link TeachersDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TeachersQueryService extends QueryService<Teachers> {

    private static final Logger LOG = LoggerFactory.getLogger(TeachersQueryService.class);

    private final TeachersRepository teachersRepository;

    private final TeachersMapper teachersMapper;

    public TeachersQueryService(TeachersRepository teachersRepository, TeachersMapper teachersMapper) {
        this.teachersRepository = teachersRepository;
        this.teachersMapper = teachersMapper;
    }

    /**
     * Return a {@link Page} of {@link TeachersDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TeachersDTO> findByCriteria(TeachersCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Teachers> specification = createSpecification(criteria);
        return teachersRepository.findAll(specification, page).map(teachersMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TeachersCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<Teachers> specification = createSpecification(criteria);
        return teachersRepository.count(specification);
    }

    /**
     * Function to convert {@link TeachersCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Teachers> createSpecification(TeachersCriteria criteria) {
        Specification<Teachers> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Teachers_.id));
            }
            if (criteria.getTeachersCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTeachersCode(), Teachers_.teachersCode));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Teachers_.name));
            }
            if (criteria.getEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmail(), Teachers_.email));
            }
            if (criteria.getPhoneNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPhoneNumber(), Teachers_.phoneNumber));
            }
            if (criteria.getStartDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStartDate(), Teachers_.startDate));
            }
            if (criteria.getEndDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEndDate(), Teachers_.endDate));
            }
            if (criteria.getPosition() != null) {
                specification = specification.and(buildSpecification(criteria.getPosition(), Teachers_.position));
            }
            if (criteria.getQualification() != null) {
                specification = specification.and(buildSpecification(criteria.getQualification(), Teachers_.qualification));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getStatus(), Teachers_.status));
            }
            if (criteria.getNotes() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNotes(), Teachers_.notes));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), Teachers_.createdBy));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), Teachers_.createdDate));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), Teachers_.lastModifiedBy));
            }
            if (criteria.getLastModifiedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModifiedDate(), Teachers_.lastModifiedDate));
            }
            if (criteria.getClassesId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getClassesId(), root -> root.join(Teachers_.classes, JoinType.LEFT).get(Classes_.id))
                );
            }
            if (criteria.getFacultiesId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getFacultiesId(), root -> root.join(Teachers_.faculties, JoinType.LEFT).get(Faculties_.id))
                );
            }
        }
        return specification;
    }
}
