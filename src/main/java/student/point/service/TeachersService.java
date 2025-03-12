package student.point.service;

import java.util.Optional;
import student.point.service.dto.TeachersDTO;

/**
 * Service Interface for managing {@link student.point.domain.Teachers}.
 */
public interface TeachersService {
    /**
     * Save a teachers.
     *
     * @param teachersDTO the entity to save.
     * @return the persisted entity.
     */
    TeachersDTO save(TeachersDTO teachersDTO);

    /**
     * Updates a teachers.
     *
     * @param teachersDTO the entity to update.
     * @return the persisted entity.
     */
    TeachersDTO update(TeachersDTO teachersDTO);

    /**
     * Partially updates a teachers.
     *
     * @param teachersDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TeachersDTO> partialUpdate(TeachersDTO teachersDTO);

    /**
     * Get the "id" teachers.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TeachersDTO> findOne(Long id);

    /**
     * Delete the "id" teachers.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
