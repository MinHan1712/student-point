package student.point.service;

import java.util.Optional;
import student.point.service.dto.ClassesDTO;

/**
 * Service Interface for managing {@link student.point.domain.Classes}.
 */
public interface ClassesService {
    /**
     * Save a classes.
     *
     * @param classesDTO the entity to save.
     * @return the persisted entity.
     */
    ClassesDTO save(ClassesDTO classesDTO);

    /**
     * Updates a classes.
     *
     * @param classesDTO the entity to update.
     * @return the persisted entity.
     */
    ClassesDTO update(ClassesDTO classesDTO);

    /**
     * Partially updates a classes.
     *
     * @param classesDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ClassesDTO> partialUpdate(ClassesDTO classesDTO);

    /**
     * Get the "id" classes.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ClassesDTO> findOne(Long id);

    /**
     * Delete the "id" classes.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
