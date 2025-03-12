package student.point.service;

import java.util.Optional;
import student.point.service.dto.MasterDataDTO;

/**
 * Service Interface for managing {@link student.point.domain.MasterData}.
 */
public interface MasterDataService {
    /**
     * Save a masterData.
     *
     * @param masterDataDTO the entity to save.
     * @return the persisted entity.
     */
    MasterDataDTO save(MasterDataDTO masterDataDTO);

    /**
     * Updates a masterData.
     *
     * @param masterDataDTO the entity to update.
     * @return the persisted entity.
     */
    MasterDataDTO update(MasterDataDTO masterDataDTO);

    /**
     * Partially updates a masterData.
     *
     * @param masterDataDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<MasterDataDTO> partialUpdate(MasterDataDTO masterDataDTO);

    /**
     * Get the "id" masterData.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MasterDataDTO> findOne(Long id);

    /**
     * Delete the "id" masterData.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
