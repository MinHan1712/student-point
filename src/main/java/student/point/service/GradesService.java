package student.point.service;

import java.util.Optional;
import student.point.service.dto.GradesDTO;

/**
 * Service Interface for managing {@link student.point.domain.Grades}.
 */
public interface GradesService {
    /**
     * Save a grades.
     *
     * @param gradesDTO the entity to save.
     * @return the persisted entity.
     */
    GradesDTO save(GradesDTO gradesDTO);

    /**
     * Updates a grades.
     *
     * @param gradesDTO the entity to update.
     * @return the persisted entity.
     */
    GradesDTO update(GradesDTO gradesDTO);

    /**
     * Partially updates a grades.
     *
     * @param gradesDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<GradesDTO> partialUpdate(GradesDTO gradesDTO);

    /**
     * Get the "id" grades.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<GradesDTO> findOne(Long id);

    /**
     * Delete the "id" grades.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
