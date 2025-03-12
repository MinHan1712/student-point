package student.point.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static student.point.domain.AccountsAsserts.*;
import static student.point.web.rest.TestUtil.createUpdateProxyForBean;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import student.point.IntegrationTest;
import student.point.domain.Accounts;
import student.point.repository.AccountsRepository;
import student.point.service.dto.AccountsDTO;
import student.point.service.mapper.AccountsMapper;

/**
 * Integration tests for the {@link AccountsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AccountsResourceIT {

    private static final String DEFAULT_ACCOUNT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_LOGIN = "AAAAAAAAAA";
    private static final String UPDATED_LOGIN = "BBBBBBBBBB";

    private static final String DEFAULT_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_PASSWORD = "BBBBBBBBBB";

    private static final String DEFAULT_MAIL = "AAAAAAAAAA";
    private static final String UPDATED_MAIL = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_NOTES = "AAAAAAAAAA";
    private static final String UPDATED_NOTES = "BBBBBBBBBB";

    private static final Boolean DEFAULT_STATUS = false;
    private static final Boolean UPDATED_STATUS = true;

    private static final String ENTITY_API_URL = "/api/accounts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private AccountsRepository accountsRepository;

    @Autowired
    private AccountsMapper accountsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAccountsMockMvc;

    private Accounts accounts;

    private Accounts insertedAccounts;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Accounts createEntity() {
        return new Accounts()
            .accountNumber(DEFAULT_ACCOUNT_NUMBER)
            .login(DEFAULT_LOGIN)
            .password(DEFAULT_PASSWORD)
            .mail(DEFAULT_MAIL)
            .phone(DEFAULT_PHONE)
            .notes(DEFAULT_NOTES)
            .status(DEFAULT_STATUS);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Accounts createUpdatedEntity() {
        return new Accounts()
            .accountNumber(UPDATED_ACCOUNT_NUMBER)
            .login(UPDATED_LOGIN)
            .password(UPDATED_PASSWORD)
            .mail(UPDATED_MAIL)
            .phone(UPDATED_PHONE)
            .notes(UPDATED_NOTES)
            .status(UPDATED_STATUS);
    }

    @BeforeEach
    public void initTest() {
        accounts = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedAccounts != null) {
            accountsRepository.delete(insertedAccounts);
            insertedAccounts = null;
        }
    }

    @Test
    @Transactional
    void createAccounts() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Accounts
        AccountsDTO accountsDTO = accountsMapper.toDto(accounts);
        var returnedAccountsDTO = om.readValue(
            restAccountsMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(accountsDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            AccountsDTO.class
        );

        // Validate the Accounts in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedAccounts = accountsMapper.toEntity(returnedAccountsDTO);
        assertAccountsUpdatableFieldsEquals(returnedAccounts, getPersistedAccounts(returnedAccounts));

        insertedAccounts = returnedAccounts;
    }

    @Test
    @Transactional
    void createAccountsWithExistingId() throws Exception {
        // Create the Accounts with an existing ID
        accounts.setId(1L);
        AccountsDTO accountsDTO = accountsMapper.toDto(accounts);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAccountsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(accountsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Accounts in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAccounts() throws Exception {
        // Initialize the database
        insertedAccounts = accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList
        restAccountsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(accounts.getId().intValue())))
            .andExpect(jsonPath("$.[*].accountNumber").value(hasItem(DEFAULT_ACCOUNT_NUMBER)))
            .andExpect(jsonPath("$.[*].login").value(hasItem(DEFAULT_LOGIN)))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD)))
            .andExpect(jsonPath("$.[*].mail").value(hasItem(DEFAULT_MAIL)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));
    }

    @Test
    @Transactional
    void getAccounts() throws Exception {
        // Initialize the database
        insertedAccounts = accountsRepository.saveAndFlush(accounts);

        // Get the accounts
        restAccountsMockMvc
            .perform(get(ENTITY_API_URL_ID, accounts.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(accounts.getId().intValue()))
            .andExpect(jsonPath("$.accountNumber").value(DEFAULT_ACCOUNT_NUMBER))
            .andExpect(jsonPath("$.login").value(DEFAULT_LOGIN))
            .andExpect(jsonPath("$.password").value(DEFAULT_PASSWORD))
            .andExpect(jsonPath("$.mail").value(DEFAULT_MAIL))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE))
            .andExpect(jsonPath("$.notes").value(DEFAULT_NOTES))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS));
    }

    @Test
    @Transactional
    void getAccountsByIdFiltering() throws Exception {
        // Initialize the database
        insertedAccounts = accountsRepository.saveAndFlush(accounts);

        Long id = accounts.getId();

        defaultAccountsFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultAccountsFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultAccountsFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllAccountsByAccountNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAccounts = accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where accountNumber equals to
        defaultAccountsFiltering("accountNumber.equals=" + DEFAULT_ACCOUNT_NUMBER, "accountNumber.equals=" + UPDATED_ACCOUNT_NUMBER);
    }

    @Test
    @Transactional
    void getAllAccountsByAccountNumberIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAccounts = accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where accountNumber in
        defaultAccountsFiltering(
            "accountNumber.in=" + DEFAULT_ACCOUNT_NUMBER + "," + UPDATED_ACCOUNT_NUMBER,
            "accountNumber.in=" + UPDATED_ACCOUNT_NUMBER
        );
    }

    @Test
    @Transactional
    void getAllAccountsByAccountNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAccounts = accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where accountNumber is not null
        defaultAccountsFiltering("accountNumber.specified=true", "accountNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllAccountsByAccountNumberContainsSomething() throws Exception {
        // Initialize the database
        insertedAccounts = accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where accountNumber contains
        defaultAccountsFiltering("accountNumber.contains=" + DEFAULT_ACCOUNT_NUMBER, "accountNumber.contains=" + UPDATED_ACCOUNT_NUMBER);
    }

    @Test
    @Transactional
    void getAllAccountsByAccountNumberNotContainsSomething() throws Exception {
        // Initialize the database
        insertedAccounts = accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where accountNumber does not contain
        defaultAccountsFiltering(
            "accountNumber.doesNotContain=" + UPDATED_ACCOUNT_NUMBER,
            "accountNumber.doesNotContain=" + DEFAULT_ACCOUNT_NUMBER
        );
    }

    @Test
    @Transactional
    void getAllAccountsByLoginIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAccounts = accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where login equals to
        defaultAccountsFiltering("login.equals=" + DEFAULT_LOGIN, "login.equals=" + UPDATED_LOGIN);
    }

    @Test
    @Transactional
    void getAllAccountsByLoginIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAccounts = accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where login in
        defaultAccountsFiltering("login.in=" + DEFAULT_LOGIN + "," + UPDATED_LOGIN, "login.in=" + UPDATED_LOGIN);
    }

    @Test
    @Transactional
    void getAllAccountsByLoginIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAccounts = accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where login is not null
        defaultAccountsFiltering("login.specified=true", "login.specified=false");
    }

    @Test
    @Transactional
    void getAllAccountsByLoginContainsSomething() throws Exception {
        // Initialize the database
        insertedAccounts = accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where login contains
        defaultAccountsFiltering("login.contains=" + DEFAULT_LOGIN, "login.contains=" + UPDATED_LOGIN);
    }

    @Test
    @Transactional
    void getAllAccountsByLoginNotContainsSomething() throws Exception {
        // Initialize the database
        insertedAccounts = accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where login does not contain
        defaultAccountsFiltering("login.doesNotContain=" + UPDATED_LOGIN, "login.doesNotContain=" + DEFAULT_LOGIN);
    }

    @Test
    @Transactional
    void getAllAccountsByPasswordIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAccounts = accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where password equals to
        defaultAccountsFiltering("password.equals=" + DEFAULT_PASSWORD, "password.equals=" + UPDATED_PASSWORD);
    }

    @Test
    @Transactional
    void getAllAccountsByPasswordIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAccounts = accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where password in
        defaultAccountsFiltering("password.in=" + DEFAULT_PASSWORD + "," + UPDATED_PASSWORD, "password.in=" + UPDATED_PASSWORD);
    }

    @Test
    @Transactional
    void getAllAccountsByPasswordIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAccounts = accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where password is not null
        defaultAccountsFiltering("password.specified=true", "password.specified=false");
    }

    @Test
    @Transactional
    void getAllAccountsByPasswordContainsSomething() throws Exception {
        // Initialize the database
        insertedAccounts = accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where password contains
        defaultAccountsFiltering("password.contains=" + DEFAULT_PASSWORD, "password.contains=" + UPDATED_PASSWORD);
    }

    @Test
    @Transactional
    void getAllAccountsByPasswordNotContainsSomething() throws Exception {
        // Initialize the database
        insertedAccounts = accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where password does not contain
        defaultAccountsFiltering("password.doesNotContain=" + UPDATED_PASSWORD, "password.doesNotContain=" + DEFAULT_PASSWORD);
    }

    @Test
    @Transactional
    void getAllAccountsByMailIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAccounts = accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where mail equals to
        defaultAccountsFiltering("mail.equals=" + DEFAULT_MAIL, "mail.equals=" + UPDATED_MAIL);
    }

    @Test
    @Transactional
    void getAllAccountsByMailIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAccounts = accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where mail in
        defaultAccountsFiltering("mail.in=" + DEFAULT_MAIL + "," + UPDATED_MAIL, "mail.in=" + UPDATED_MAIL);
    }

    @Test
    @Transactional
    void getAllAccountsByMailIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAccounts = accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where mail is not null
        defaultAccountsFiltering("mail.specified=true", "mail.specified=false");
    }

    @Test
    @Transactional
    void getAllAccountsByMailContainsSomething() throws Exception {
        // Initialize the database
        insertedAccounts = accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where mail contains
        defaultAccountsFiltering("mail.contains=" + DEFAULT_MAIL, "mail.contains=" + UPDATED_MAIL);
    }

    @Test
    @Transactional
    void getAllAccountsByMailNotContainsSomething() throws Exception {
        // Initialize the database
        insertedAccounts = accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where mail does not contain
        defaultAccountsFiltering("mail.doesNotContain=" + UPDATED_MAIL, "mail.doesNotContain=" + DEFAULT_MAIL);
    }

    @Test
    @Transactional
    void getAllAccountsByPhoneIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAccounts = accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where phone equals to
        defaultAccountsFiltering("phone.equals=" + DEFAULT_PHONE, "phone.equals=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    void getAllAccountsByPhoneIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAccounts = accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where phone in
        defaultAccountsFiltering("phone.in=" + DEFAULT_PHONE + "," + UPDATED_PHONE, "phone.in=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    void getAllAccountsByPhoneIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAccounts = accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where phone is not null
        defaultAccountsFiltering("phone.specified=true", "phone.specified=false");
    }

    @Test
    @Transactional
    void getAllAccountsByPhoneContainsSomething() throws Exception {
        // Initialize the database
        insertedAccounts = accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where phone contains
        defaultAccountsFiltering("phone.contains=" + DEFAULT_PHONE, "phone.contains=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    void getAllAccountsByPhoneNotContainsSomething() throws Exception {
        // Initialize the database
        insertedAccounts = accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where phone does not contain
        defaultAccountsFiltering("phone.doesNotContain=" + UPDATED_PHONE, "phone.doesNotContain=" + DEFAULT_PHONE);
    }

    @Test
    @Transactional
    void getAllAccountsByNotesIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAccounts = accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where notes equals to
        defaultAccountsFiltering("notes.equals=" + DEFAULT_NOTES, "notes.equals=" + UPDATED_NOTES);
    }

    @Test
    @Transactional
    void getAllAccountsByNotesIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAccounts = accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where notes in
        defaultAccountsFiltering("notes.in=" + DEFAULT_NOTES + "," + UPDATED_NOTES, "notes.in=" + UPDATED_NOTES);
    }

    @Test
    @Transactional
    void getAllAccountsByNotesIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAccounts = accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where notes is not null
        defaultAccountsFiltering("notes.specified=true", "notes.specified=false");
    }

    @Test
    @Transactional
    void getAllAccountsByNotesContainsSomething() throws Exception {
        // Initialize the database
        insertedAccounts = accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where notes contains
        defaultAccountsFiltering("notes.contains=" + DEFAULT_NOTES, "notes.contains=" + UPDATED_NOTES);
    }

    @Test
    @Transactional
    void getAllAccountsByNotesNotContainsSomething() throws Exception {
        // Initialize the database
        insertedAccounts = accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where notes does not contain
        defaultAccountsFiltering("notes.doesNotContain=" + UPDATED_NOTES, "notes.doesNotContain=" + DEFAULT_NOTES);
    }

    @Test
    @Transactional
    void getAllAccountsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAccounts = accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where status equals to
        defaultAccountsFiltering("status.equals=" + DEFAULT_STATUS, "status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllAccountsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAccounts = accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where status in
        defaultAccountsFiltering("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS, "status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllAccountsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAccounts = accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where status is not null
        defaultAccountsFiltering("status.specified=true", "status.specified=false");
    }

    private void defaultAccountsFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultAccountsShouldBeFound(shouldBeFound);
        defaultAccountsShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAccountsShouldBeFound(String filter) throws Exception {
        restAccountsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(accounts.getId().intValue())))
            .andExpect(jsonPath("$.[*].accountNumber").value(hasItem(DEFAULT_ACCOUNT_NUMBER)))
            .andExpect(jsonPath("$.[*].login").value(hasItem(DEFAULT_LOGIN)))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD)))
            .andExpect(jsonPath("$.[*].mail").value(hasItem(DEFAULT_MAIL)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));

        // Check, that the count call also returns 1
        restAccountsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAccountsShouldNotBeFound(String filter) throws Exception {
        restAccountsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAccountsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingAccounts() throws Exception {
        // Get the accounts
        restAccountsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAccounts() throws Exception {
        // Initialize the database
        insertedAccounts = accountsRepository.saveAndFlush(accounts);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the accounts
        Accounts updatedAccounts = accountsRepository.findById(accounts.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedAccounts are not directly saved in db
        em.detach(updatedAccounts);
        updatedAccounts
            .accountNumber(UPDATED_ACCOUNT_NUMBER)
            .login(UPDATED_LOGIN)
            .password(UPDATED_PASSWORD)
            .mail(UPDATED_MAIL)
            .phone(UPDATED_PHONE)
            .notes(UPDATED_NOTES)
            .status(UPDATED_STATUS);
        AccountsDTO accountsDTO = accountsMapper.toDto(updatedAccounts);

        restAccountsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, accountsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(accountsDTO))
            )
            .andExpect(status().isOk());

        // Validate the Accounts in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedAccountsToMatchAllProperties(updatedAccounts);
    }

    @Test
    @Transactional
    void putNonExistingAccounts() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        accounts.setId(longCount.incrementAndGet());

        // Create the Accounts
        AccountsDTO accountsDTO = accountsMapper.toDto(accounts);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAccountsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, accountsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(accountsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Accounts in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAccounts() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        accounts.setId(longCount.incrementAndGet());

        // Create the Accounts
        AccountsDTO accountsDTO = accountsMapper.toDto(accounts);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAccountsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(accountsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Accounts in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAccounts() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        accounts.setId(longCount.incrementAndGet());

        // Create the Accounts
        AccountsDTO accountsDTO = accountsMapper.toDto(accounts);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAccountsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(accountsDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Accounts in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAccountsWithPatch() throws Exception {
        // Initialize the database
        insertedAccounts = accountsRepository.saveAndFlush(accounts);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the accounts using partial update
        Accounts partialUpdatedAccounts = new Accounts();
        partialUpdatedAccounts.setId(accounts.getId());

        partialUpdatedAccounts.login(UPDATED_LOGIN).password(UPDATED_PASSWORD).phone(UPDATED_PHONE).notes(UPDATED_NOTES);

        restAccountsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAccounts.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAccounts))
            )
            .andExpect(status().isOk());

        // Validate the Accounts in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAccountsUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedAccounts, accounts), getPersistedAccounts(accounts));
    }

    @Test
    @Transactional
    void fullUpdateAccountsWithPatch() throws Exception {
        // Initialize the database
        insertedAccounts = accountsRepository.saveAndFlush(accounts);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the accounts using partial update
        Accounts partialUpdatedAccounts = new Accounts();
        partialUpdatedAccounts.setId(accounts.getId());

        partialUpdatedAccounts
            .accountNumber(UPDATED_ACCOUNT_NUMBER)
            .login(UPDATED_LOGIN)
            .password(UPDATED_PASSWORD)
            .mail(UPDATED_MAIL)
            .phone(UPDATED_PHONE)
            .notes(UPDATED_NOTES)
            .status(UPDATED_STATUS);

        restAccountsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAccounts.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAccounts))
            )
            .andExpect(status().isOk());

        // Validate the Accounts in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAccountsUpdatableFieldsEquals(partialUpdatedAccounts, getPersistedAccounts(partialUpdatedAccounts));
    }

    @Test
    @Transactional
    void patchNonExistingAccounts() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        accounts.setId(longCount.incrementAndGet());

        // Create the Accounts
        AccountsDTO accountsDTO = accountsMapper.toDto(accounts);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAccountsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, accountsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(accountsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Accounts in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAccounts() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        accounts.setId(longCount.incrementAndGet());

        // Create the Accounts
        AccountsDTO accountsDTO = accountsMapper.toDto(accounts);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAccountsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(accountsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Accounts in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAccounts() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        accounts.setId(longCount.incrementAndGet());

        // Create the Accounts
        AccountsDTO accountsDTO = accountsMapper.toDto(accounts);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAccountsMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(accountsDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Accounts in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAccounts() throws Exception {
        // Initialize the database
        insertedAccounts = accountsRepository.saveAndFlush(accounts);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the accounts
        restAccountsMockMvc
            .perform(delete(ENTITY_API_URL_ID, accounts.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return accountsRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected Accounts getPersistedAccounts(Accounts accounts) {
        return accountsRepository.findById(accounts.getId()).orElseThrow();
    }

    protected void assertPersistedAccountsToMatchAllProperties(Accounts expectedAccounts) {
        assertAccountsAllPropertiesEquals(expectedAccounts, getPersistedAccounts(expectedAccounts));
    }

    protected void assertPersistedAccountsToMatchUpdatableProperties(Accounts expectedAccounts) {
        assertAccountsAllUpdatablePropertiesEquals(expectedAccounts, getPersistedAccounts(expectedAccounts));
    }
}
