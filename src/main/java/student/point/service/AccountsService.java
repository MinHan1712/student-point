package student.point.service;

import java.util.Optional;
import student.point.service.dto.AccountsDTO;

/**
 * Service Interface for managing {@link student.point.domain.Accounts}.
 */
public interface AccountsService {
    /**
     * Save a accounts.
     *
     * @param accountsDTO the entity to save.
     * @return the persisted entity.
     */
    AccountsDTO save(AccountsDTO accountsDTO);

    /**
     * Updates a accounts.
     *
     * @param accountsDTO the entity to update.
     * @return the persisted entity.
     */
    AccountsDTO update(AccountsDTO accountsDTO);

    /**
     * Partially updates a accounts.
     *
     * @param accountsDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AccountsDTO> partialUpdate(AccountsDTO accountsDTO);

    /**
     * Get the "id" accounts.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AccountsDTO> findOne(Long id);

    /**
     * Delete the "id" accounts.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
