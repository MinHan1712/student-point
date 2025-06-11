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
import student.point.domain.ClassCourse;
import student.point.repository.ClassCourseRepository;
import student.point.service.criteria.ClassCourseCriteria;
import student.point.service.dto.ClassCourseDTO;
import student.point.service.mapper.ClassCourseMapper;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link ClassCourse} entities in the database.
 * The main input is a {@link ClassCourseCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link ClassCourseDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ClassCourseQueryService extends QueryService<ClassCourse> {

    private static final Logger LOG = LoggerFactory.getLogger(ClassCourseQueryService.class);

    private final ClassCourseRepository classCourseRepository;

    private final ClassCourseMapper classCourseMapper;

    public ClassCourseQueryService(ClassCourseRepository classCourseRepository, ClassCourseMapper classCourseMapper) {
        this.classCourseRepository = classCourseRepository;
        this.classCourseMapper = classCourseMapper;
    }

    /**
     * Return a {@link Page} of {@link ClassCourseDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ClassCourseDTO> findByCriteria(ClassCourseCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ClassCourse> specification = createSpecification(criteria);
        return classCourseRepository.findAll(specification, page).map(classCourseMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ClassCourseCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<ClassCourse> specification = createSpecification(criteria);
        return classCourseRepository.count(specification);
    }

    /**
     * Function to convert {@link ClassCourseCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ClassCourse> createSpecification(ClassCourseCriteria criteria) {
        Specification<ClassCourse> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ClassCourse_.id));
            }
            if (criteria.getClassName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getClassName(), ClassCourse_.className));
            }
            if (criteria.getCourseYear() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCourseYear(), ClassCourse_.courseYear));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), ClassCourse_.createdBy));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), ClassCourse_.createdDate));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), ClassCourse_.lastModifiedBy));
            }
            if (criteria.getLastModifiedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModifiedDate(), ClassCourse_.lastModifiedDate));
            }
            if (criteria.getFacultiesId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getFacultiesId(), root ->
                        root.join(ClassCourse_.faculties, JoinType.LEFT).get(Faculties_.id)
                    )
                );
            }
        }
        return specification;
    }
}
