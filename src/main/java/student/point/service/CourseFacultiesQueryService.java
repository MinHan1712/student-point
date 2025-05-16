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
import student.point.domain.CourseFaculties;
import student.point.repository.CourseFacultiesRepository;
import student.point.service.criteria.CourseFacultiesCriteria;
import student.point.service.dto.CourseFacultiesDTO;
import student.point.service.mapper.CourseFacultiesMapper;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link CourseFaculties} entities in the database.
 * The main input is a {@link CourseFacultiesCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link CourseFacultiesDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CourseFacultiesQueryService extends QueryService<CourseFaculties> {

    private static final Logger LOG = LoggerFactory.getLogger(CourseFacultiesQueryService.class);

    private final CourseFacultiesRepository courseFacultiesRepository;

    private final CourseFacultiesMapper courseFacultiesMapper;

    public CourseFacultiesQueryService(CourseFacultiesRepository courseFacultiesRepository, CourseFacultiesMapper courseFacultiesMapper) {
        this.courseFacultiesRepository = courseFacultiesRepository;
        this.courseFacultiesMapper = courseFacultiesMapper;
    }

    /**
     * Return a {@link Page} of {@link CourseFacultiesDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CourseFacultiesDTO> findByCriteria(CourseFacultiesCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CourseFaculties> specification = createSpecification(criteria);
        return courseFacultiesRepository.findAll(specification, page).map(courseFacultiesMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CourseFacultiesCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<CourseFaculties> specification = createSpecification(criteria);
        return courseFacultiesRepository.count(specification);
    }

    /**
     * Function to convert {@link CourseFacultiesCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CourseFaculties> createSpecification(CourseFacultiesCriteria criteria) {
        Specification<CourseFaculties> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CourseFaculties_.id));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getStatus(), CourseFaculties_.status));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), CourseFaculties_.createdBy));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), CourseFaculties_.createdDate));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), CourseFaculties_.lastModifiedBy));
            }
            if (criteria.getLastModifiedDate() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getLastModifiedDate(), CourseFaculties_.lastModifiedDate)
                );
            }
            if (criteria.getCourseId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getCourseId(), root -> root.join(CourseFaculties_.course, JoinType.LEFT).get(Course_.id))
                );
            }
            if (criteria.getFacultiesId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getFacultiesId(), root ->
                        root.join(CourseFaculties_.faculties, JoinType.LEFT).get(Faculties_.id)
                    )
                );
            }
        }
        return specification;
    }
}
