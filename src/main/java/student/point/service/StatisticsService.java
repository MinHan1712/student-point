package student.point.service;

import java.util.Optional;
import student.point.service.dto.StatisticsDTO;

/**
 * Service Interface for managing {@link student.point.domain.Statistics}.
 */
public interface StatisticsService {
    /**
     * Save a statistics.
     *
     * @param statisticsDTO the entity to save.
     * @return the persisted entity.
     */
    StatisticsDTO save(StatisticsDTO statisticsDTO);

    /**
     * Updates a statistics.
     *
     * @param statisticsDTO the entity to update.
     * @return the persisted entity.
     */
    StatisticsDTO update(StatisticsDTO statisticsDTO);

    /**
     * Partially updates a statistics.
     *
     * @param statisticsDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<StatisticsDTO> partialUpdate(StatisticsDTO statisticsDTO);

    /**
     * Get the "id" statistics.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<StatisticsDTO> findOne(Long id);

    /**
     * Delete the "id" statistics.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
