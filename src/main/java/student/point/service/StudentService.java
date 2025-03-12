package student.point.service;

import java.util.Optional;
import student.point.service.dto.StudentDTO;

/**
 * Service Interface for managing {@link student.point.domain.Student}.
 */
public interface StudentService {
    /**
     * Save a student.
     *
     * @param studentDTO the entity to save.
     * @return the persisted entity.
     */
    StudentDTO save(StudentDTO studentDTO);

    /**
     * Updates a student.
     *
     * @param studentDTO the entity to update.
     * @return the persisted entity.
     */
    StudentDTO update(StudentDTO studentDTO);

    /**
     * Partially updates a student.
     *
     * @param studentDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<StudentDTO> partialUpdate(StudentDTO studentDTO);

    /**
     * Get the "id" student.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<StudentDTO> findOne(Long id);

    /**
     * Delete the "id" student.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
