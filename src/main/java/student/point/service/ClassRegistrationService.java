package student.point.service;

import java.util.Optional;
import student.point.service.dto.ClassRegistrationDTO;

/**
 * Service Interface for managing {@link student.point.domain.ClassRegistration}.
 */
public interface ClassRegistrationService {
    /**
     * Save a classRegistration.
     *
     * @param classRegistrationDTO the entity to save.
     * @return the persisted entity.
     */
    ClassRegistrationDTO save(ClassRegistrationDTO classRegistrationDTO);

    /**
     * Updates a classRegistration.
     *
     * @param classRegistrationDTO the entity to update.
     * @return the persisted entity.
     */
    ClassRegistrationDTO update(ClassRegistrationDTO classRegistrationDTO);

    /**
     * Partially updates a classRegistration.
     *
     * @param classRegistrationDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ClassRegistrationDTO> partialUpdate(ClassRegistrationDTO classRegistrationDTO);

    /**
     * Get the "id" classRegistration.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ClassRegistrationDTO> findOne(Long id);

    /**
     * Delete the "id" classRegistration.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
