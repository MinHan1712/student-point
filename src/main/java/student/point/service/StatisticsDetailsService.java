package student.point.service;

import java.util.Optional;
import student.point.service.dto.StatisticsDetailsDTO;

/**
 * Service Interface for managing {@link student.point.domain.StatisticsDetails}.
 */
public interface StatisticsDetailsService {
    /**
     * Save a statisticsDetails.
     *
     * @param statisticsDetailsDTO the entity to save.
     * @return the persisted entity.
     */
    StatisticsDetailsDTO save(StatisticsDetailsDTO statisticsDetailsDTO);

    /**
     * Updates a statisticsDetails.
     *
     * @param statisticsDetailsDTO the entity to update.
     * @return the persisted entity.
     */
    StatisticsDetailsDTO update(StatisticsDetailsDTO statisticsDetailsDTO);

    /**
     * Partially updates a statisticsDetails.
     *
     * @param statisticsDetailsDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<StatisticsDetailsDTO> partialUpdate(StatisticsDetailsDTO statisticsDetailsDTO);

    /**
     * Get the "id" statisticsDetails.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<StatisticsDetailsDTO> findOne(Long id);

    /**
     * Delete the "id" statisticsDetails.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
