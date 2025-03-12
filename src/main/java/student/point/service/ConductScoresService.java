package student.point.service;

import java.util.Optional;
import student.point.service.dto.ConductScoresDTO;

/**
 * Service Interface for managing {@link student.point.domain.ConductScores}.
 */
public interface ConductScoresService {
    /**
     * Save a conductScores.
     *
     * @param conductScoresDTO the entity to save.
     * @return the persisted entity.
     */
    ConductScoresDTO save(ConductScoresDTO conductScoresDTO);

    /**
     * Updates a conductScores.
     *
     * @param conductScoresDTO the entity to update.
     * @return the persisted entity.
     */
    ConductScoresDTO update(ConductScoresDTO conductScoresDTO);

    /**
     * Partially updates a conductScores.
     *
     * @param conductScoresDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ConductScoresDTO> partialUpdate(ConductScoresDTO conductScoresDTO);

    /**
     * Get the "id" conductScores.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ConductScoresDTO> findOne(Long id);

    /**
     * Delete the "id" conductScores.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
