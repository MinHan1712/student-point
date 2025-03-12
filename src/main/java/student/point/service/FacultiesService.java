package student.point.service;

import java.util.Optional;
import student.point.service.dto.FacultiesDTO;

/**
 * Service Interface for managing {@link student.point.domain.Faculties}.
 */
public interface FacultiesService {
    /**
     * Save a faculties.
     *
     * @param facultiesDTO the entity to save.
     * @return the persisted entity.
     */
    FacultiesDTO save(FacultiesDTO facultiesDTO);

    /**
     * Updates a faculties.
     *
     * @param facultiesDTO the entity to update.
     * @return the persisted entity.
     */
    FacultiesDTO update(FacultiesDTO facultiesDTO);

    /**
     * Partially updates a faculties.
     *
     * @param facultiesDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<FacultiesDTO> partialUpdate(FacultiesDTO facultiesDTO);

    /**
     * Get the "id" faculties.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FacultiesDTO> findOne(Long id);

    /**
     * Delete the "id" faculties.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
