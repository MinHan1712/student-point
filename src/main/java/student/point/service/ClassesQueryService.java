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
import student.point.domain.Classes;
import student.point.repository.ClassesRepository;
import student.point.service.criteria.ClassesCriteria;
import student.point.service.dto.ClassesDTO;
import student.point.service.mapper.ClassesMapper;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Classes} entities in the database.
 * The main input is a {@link ClassesCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link ClassesDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ClassesQueryService extends QueryService<Classes> {

    private static final Logger LOG = LoggerFactory.getLogger(ClassesQueryService.class);

    private final ClassesRepository classesRepository;

    private final ClassesMapper classesMapper;

    public ClassesQueryService(ClassesRepository classesRepository, ClassesMapper classesMapper) {
        this.classesRepository = classesRepository;
        this.classesMapper = classesMapper;
    }

    /**
     * Return a {@link Page} of {@link ClassesDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ClassesDTO> findByCriteria(ClassesCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Classes> specification = createSpecification(criteria);
        return classesRepository.findAll(specification, page).map(classesMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ClassesCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<Classes> specification = createSpecification(criteria);
        return classesRepository.count(specification);
    }

    /**
     * Function to convert {@link ClassesCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Classes> createSpecification(ClassesCriteria criteria) {
        Specification<Classes> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Classes_.id));
            }
            if (criteria.getClassCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getClassCode(), Classes_.classCode));
            }
            if (criteria.getClassName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getClassName(), Classes_.className));
            }
            if (criteria.getClassroom() != null) {
                specification = specification.and(buildStringSpecification(criteria.getClassroom(), Classes_.classroom));
            }
            if (criteria.getCredits() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCredits(), Classes_.credits));
            }
            if (criteria.getNumberOfSessions() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNumberOfSessions(), Classes_.numberOfSessions));
            }
            if (criteria.getTotalNumberOfStudents() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getTotalNumberOfStudents(), Classes_.totalNumberOfStudents)
                );
            }
            if (criteria.getStartDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStartDate(), Classes_.startDate));
            }
            if (criteria.getEndDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEndDate(), Classes_.endDate));
            }
            if (criteria.getClassType() != null) {
                specification = specification.and(buildSpecification(criteria.getClassType(), Classes_.classType));
            }
            if (criteria.getDeliveryMode() != null) {
                specification = specification.and(buildSpecification(criteria.getDeliveryMode(), Classes_.deliveryMode));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getStatus(), Classes_.status));
            }
            if (criteria.getNotes() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNotes(), Classes_.notes));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), Classes_.description));
            }
            if (criteria.getAcademicYear() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAcademicYear(), Classes_.academicYear));
            }
            if (criteria.getParentId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getParentId(), Classes_.parentId));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), Classes_.createdBy));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), Classes_.createdDate));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), Classes_.lastModifiedBy));
            }
            if (criteria.getLastModifiedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModifiedDate(), Classes_.lastModifiedDate));
            }
            if (criteria.getClassRegistrationId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getClassRegistrationId(), root ->
                        root.join(Classes_.classRegistrations, JoinType.LEFT).get(ClassRegistration_.id)
                    )
                );
            }
            if (criteria.getGradesId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getGradesId(), root -> root.join(Classes_.grades, JoinType.LEFT).get(Grades_.id))
                );
            }
            if (criteria.getCourseId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getCourseId(), root -> root.join(Classes_.course, JoinType.LEFT).get(Course_.id))
                );
            }
            if (criteria.getTeachersId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getTeachersId(), root -> root.join(Classes_.teachers, JoinType.LEFT).get(Teachers_.id))
                );
            }
        }
        return specification;
    }
}
