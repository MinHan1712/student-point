package student.point.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import student.point.domain.*; // for static metamodels
import student.point.domain.Accounts;
import student.point.repository.AccountsRepository;
import student.point.service.criteria.AccountsCriteria;
import student.point.service.dto.AccountsDTO;
import student.point.service.mapper.AccountsMapper;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Accounts} entities in the database.
 * The main input is a {@link AccountsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link AccountsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AccountsQueryService extends QueryService<Accounts> {

    private static final Logger LOG = LoggerFactory.getLogger(AccountsQueryService.class);

    private final AccountsRepository accountsRepository;

    private final AccountsMapper accountsMapper;

    public AccountsQueryService(AccountsRepository accountsRepository, AccountsMapper accountsMapper) {
        this.accountsRepository = accountsRepository;
        this.accountsMapper = accountsMapper;
    }

    /**
     * Return a {@link Page} of {@link AccountsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AccountsDTO> findByCriteria(AccountsCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Accounts> specification = createSpecification(criteria);
        return accountsRepository.findAll(specification, page).map(accountsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AccountsCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<Accounts> specification = createSpecification(criteria);
        return accountsRepository.count(specification);
    }

    /**
     * Function to convert {@link AccountsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Accounts> createSpecification(AccountsCriteria criteria) {
        Specification<Accounts> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Accounts_.id));
            }
            if (criteria.getAccountNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAccountNumber(), Accounts_.accountNumber));
            }
            if (criteria.getLogin() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLogin(), Accounts_.login));
            }
            if (criteria.getPassword() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPassword(), Accounts_.password));
            }
            if (criteria.getMail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMail(), Accounts_.mail));
            }
            if (criteria.getPhone() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPhone(), Accounts_.phone));
            }
            if (criteria.getNotes() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNotes(), Accounts_.notes));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getStatus(), Accounts_.status));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), Accounts_.createdBy));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), Accounts_.createdDate));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), Accounts_.lastModifiedBy));
            }
            if (criteria.getLastModifiedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModifiedDate(), Accounts_.lastModifiedDate));
            }
        }
        return specification;
    }
}
