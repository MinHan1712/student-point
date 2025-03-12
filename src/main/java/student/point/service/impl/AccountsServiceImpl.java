package student.point.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import student.point.domain.Accounts;
import student.point.repository.AccountsRepository;
import student.point.service.AccountsService;
import student.point.service.dto.AccountsDTO;
import student.point.service.mapper.AccountsMapper;

/**
 * Service Implementation for managing {@link student.point.domain.Accounts}.
 */
@Service
@Transactional
public class AccountsServiceImpl implements AccountsService {

    private static final Logger LOG = LoggerFactory.getLogger(AccountsServiceImpl.class);

    private final AccountsRepository accountsRepository;

    private final AccountsMapper accountsMapper;

    public AccountsServiceImpl(AccountsRepository accountsRepository, AccountsMapper accountsMapper) {
        this.accountsRepository = accountsRepository;
        this.accountsMapper = accountsMapper;
    }

    @Override
    public AccountsDTO save(AccountsDTO accountsDTO) {
        LOG.debug("Request to save Accounts : {}", accountsDTO);
        Accounts accounts = accountsMapper.toEntity(accountsDTO);
        accounts = accountsRepository.save(accounts);
        return accountsMapper.toDto(accounts);
    }

    @Override
    public AccountsDTO update(AccountsDTO accountsDTO) {
        LOG.debug("Request to update Accounts : {}", accountsDTO);
        Accounts accounts = accountsMapper.toEntity(accountsDTO);
        accounts = accountsRepository.save(accounts);
        return accountsMapper.toDto(accounts);
    }

    @Override
    public Optional<AccountsDTO> partialUpdate(AccountsDTO accountsDTO) {
        LOG.debug("Request to partially update Accounts : {}", accountsDTO);

        return accountsRepository
            .findById(accountsDTO.getId())
            .map(existingAccounts -> {
                accountsMapper.partialUpdate(existingAccounts, accountsDTO);

                return existingAccounts;
            })
            .map(accountsRepository::save)
            .map(accountsMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AccountsDTO> findOne(Long id) {
        LOG.debug("Request to get Accounts : {}", id);
        return accountsRepository.findById(id).map(accountsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete Accounts : {}", id);
        accountsRepository.deleteById(id);
    }
}
