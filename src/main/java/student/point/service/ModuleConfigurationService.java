package student.point.service;

import java.util.Optional;
import student.point.service.dto.ModuleConfigurationDTO;

/**
 * Service Interface for managing {@link student.point.domain.ModuleConfiguration}.
 */
public interface ModuleConfigurationService {
    /**
     * Save a moduleConfiguration.
     *
     * @param moduleConfigurationDTO the entity to save.
     * @return the persisted entity.
     */
    ModuleConfigurationDTO save(ModuleConfigurationDTO moduleConfigurationDTO);

    /**
     * Updates a moduleConfiguration.
     *
     * @param moduleConfigurationDTO the entity to update.
     * @return the persisted entity.
     */
    ModuleConfigurationDTO update(ModuleConfigurationDTO moduleConfigurationDTO);

    /**
     * Partially updates a moduleConfiguration.
     *
     * @param moduleConfigurationDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ModuleConfigurationDTO> partialUpdate(ModuleConfigurationDTO moduleConfigurationDTO);

    /**
     * Get the "id" moduleConfiguration.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ModuleConfigurationDTO> findOne(Long id);

    /**
     * Delete the "id" moduleConfiguration.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
