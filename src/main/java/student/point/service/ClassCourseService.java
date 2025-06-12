package student.point.service;

import java.util.Optional;
import student.point.service.dto.ClassCourseDTO;

/**
 * Service Interface for managing {@link student.point.domain.ClassCourse}.
 */
public interface ClassCourseService {
    /**
     * Save a classCourse.
     *
     * @param classCourseDTO the entity to save.
     * @return the persisted entity.
     */
    ClassCourseDTO save(ClassCourseDTO classCourseDTO);

    /**
     * Updates a classCourse.
     *
     * @param classCourseDTO the entity to update.
     * @return the persisted entity.
     */
    ClassCourseDTO update(ClassCourseDTO classCourseDTO);

    /**
     * Partially updates a classCourse.
     *
     * @param classCourseDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ClassCourseDTO> partialUpdate(ClassCourseDTO classCourseDTO);

    /**
     * Get the "id" classCourse.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ClassCourseDTO> findOne(Long id);

    /**
     * Delete the "id" classCourse.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
