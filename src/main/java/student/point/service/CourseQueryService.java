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
import student.point.domain.Course;
import student.point.repository.CourseRepository;
import student.point.service.criteria.CourseCriteria;
import student.point.service.dto.CourseDTO;
import student.point.service.mapper.CourseMapper;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Course} entities in the database.
 * The main input is a {@link CourseCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link CourseDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CourseQueryService extends QueryService<Course> {

    private static final Logger LOG = LoggerFactory.getLogger(CourseQueryService.class);

    private final CourseRepository courseRepository;

    private final CourseMapper courseMapper;

    public CourseQueryService(CourseRepository courseRepository, CourseMapper courseMapper) {
        this.courseRepository = courseRepository;
        this.courseMapper = courseMapper;
    }

    /**
     * Return a {@link Page} of {@link CourseDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CourseDTO> findByCriteria(CourseCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Course> specification = createSpecification(criteria);
        return courseRepository.findAll(specification, page).map(courseMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CourseCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<Course> specification = createSpecification(criteria);
        return courseRepository.count(specification);
    }

    /**
     * Function to convert {@link CourseCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Course> createSpecification(CourseCriteria criteria) {
        Specification<Course> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Course_.id));
            }
            if (criteria.getCourseCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCourseCode(), Course_.courseCode));
            }
            if (criteria.getCourseTitle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCourseTitle(), Course_.courseTitle));
            }
            if (criteria.getCredits() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCredits(), Course_.credits));
            }
            if (criteria.getLecture() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLecture(), Course_.lecture));
            }
            if (criteria.getTutorialDiscussion() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTutorialDiscussion(), Course_.tutorialDiscussion));
            }
            if (criteria.getPractical() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPractical(), Course_.practical));
            }
            if (criteria.getLaboratory() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLaboratory(), Course_.laboratory));
            }
            if (criteria.getSelfStudy() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSelfStudy(), Course_.selfStudy));
            }
            if (criteria.getNumberOfSessions() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNumberOfSessions(), Course_.numberOfSessions));
            }
            if (criteria.getCourseType() != null) {
                specification = specification.and(buildSpecification(criteria.getCourseType(), Course_.courseType));
            }
            if (criteria.getNotes() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNotes(), Course_.notes));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getStatus(), Course_.status));
            }
            if (criteria.getSemester() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSemester(), Course_.semester));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), Course_.createdBy));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), Course_.createdDate));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), Course_.lastModifiedBy));
            }
            if (criteria.getLastModifiedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModifiedDate(), Course_.lastModifiedDate));
            }
            if (criteria.getClassesId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getClassesId(), root -> root.join(Course_.classes, JoinType.LEFT).get(Classes_.id))
                );
            }
            if (criteria.getFacultiesId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getFacultiesId(), root -> root.join(Course_.faculties, JoinType.LEFT).get(Faculties_.id))
                );
            }
        }
        return specification;
    }
}
