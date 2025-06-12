package student.point.service;

import java.util.Optional;
import student.point.service.dto.CourseFacultiesDTO;

/**
 * Service Interface for managing {@link student.point.domain.CourseFaculties}.
 */
public interface CourseFacultiesService {
    /**
     * Save a courseFaculties.
     *
     * @param courseFacultiesDTO the entity to save.
     * @return the persisted entity.
     */
    CourseFacultiesDTO save(CourseFacultiesDTO courseFacultiesDTO);

    /**
     * Updates a courseFaculties.
     *
     * @param courseFacultiesDTO the entity to update.
     * @return the persisted entity.
     */
    CourseFacultiesDTO update(CourseFacultiesDTO courseFacultiesDTO);

    /**
     * Partially updates a courseFaculties.
     *
     * @param courseFacultiesDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CourseFacultiesDTO> partialUpdate(CourseFacultiesDTO courseFacultiesDTO);

    /**
     * Get the "id" courseFaculties.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CourseFacultiesDTO> findOne(Long id);

    /**
     * Delete the "id" courseFaculties.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
