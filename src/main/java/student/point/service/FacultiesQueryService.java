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
import student.point.domain.Faculties;
import student.point.repository.FacultiesRepository;
import student.point.service.criteria.FacultiesCriteria;
import student.point.service.dto.FacultiesDTO;
import student.point.service.mapper.FacultiesMapper;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Faculties} entities in the database.
 * The main input is a {@link FacultiesCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link FacultiesDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class FacultiesQueryService extends QueryService<Faculties> {

    private static final Logger LOG = LoggerFactory.getLogger(FacultiesQueryService.class);

    private final FacultiesRepository facultiesRepository;

    private final FacultiesMapper facultiesMapper;

    public FacultiesQueryService(FacultiesRepository facultiesRepository, FacultiesMapper facultiesMapper) {
        this.facultiesRepository = facultiesRepository;
        this.facultiesMapper = facultiesMapper;
    }

    /**
     * Return a {@link Page} of {@link FacultiesDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<FacultiesDTO> findByCriteria(FacultiesCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Faculties> specification = createSpecification(criteria);
        return facultiesRepository.findAll(specification, page).map(facultiesMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(FacultiesCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<Faculties> specification = createSpecification(criteria);
        return facultiesRepository.count(specification);
    }

    /**
     * Function to convert {@link FacultiesCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Faculties> createSpecification(FacultiesCriteria criteria) {
        Specification<Faculties> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Faculties_.id));
            }
            if (criteria.getFacultyCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFacultyCode(), Faculties_.facultyCode));
            }
            if (criteria.getFacultyName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFacultyName(), Faculties_.facultyName));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), Faculties_.description));
            }
            if (criteria.getEstablishedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEstablishedDate(), Faculties_.establishedDate));
            }
            if (criteria.getPhoneNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPhoneNumber(), Faculties_.phoneNumber));
            }
            if (criteria.getLocation() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLocation(), Faculties_.location));
            }
            if (criteria.getNotes() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNotes(), Faculties_.notes));
            }
            if (criteria.getParentId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getParentId(), Faculties_.parentId));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), Faculties_.createdBy));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), Faculties_.createdDate));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), Faculties_.lastModifiedBy));
            }
            if (criteria.getLastModifiedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModifiedDate(), Faculties_.lastModifiedDate));
            }
            if (criteria.getTeachersId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getTeachersId(), root -> root.join(Faculties_.teachers, JoinType.LEFT).get(Teachers_.id))
                );
            }
            if (criteria.getCourseFacultiesId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getCourseFacultiesId(), root ->
                        root.join(Faculties_.courseFaculties, JoinType.LEFT).get(CourseFaculties_.id)
                    )
                );
            }
            if (criteria.getStudentId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getStudentId(), root -> root.join(Faculties_.students, JoinType.LEFT).get(Student_.id))
                );
            }
            if (criteria.getClassCourseId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getClassCourseId(), root ->
                        root.join(Faculties_.classCourses, JoinType.LEFT).get(ClassCourse_.id)
                    )
                );
            }
        }
        return specification;
    }
}
