package student.point.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static student.point.domain.ModuleConfigurationAsserts.*;
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
import student.point.domain.ModuleConfiguration;
import student.point.repository.ModuleConfigurationRepository;
import student.point.service.dto.ModuleConfigurationDTO;
import student.point.service.mapper.ModuleConfigurationMapper;

/**
 * Integration tests for the {@link ModuleConfigurationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ModuleConfigurationResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PREFIX = "AAAAAAAAAA";
    private static final String UPDATED_PREFIX = "BBBBBBBBBB";

    private static final Long DEFAULT_PADDING = 1L;
    private static final Long UPDATED_PADDING = 2L;
    private static final Long SMALLER_PADDING = 1L - 1L;

    private static final Long DEFAULT_NUMBER_NEXT_ACTUAL = 1L;
    private static final Long UPDATED_NUMBER_NEXT_ACTUAL = 2L;
    private static final Long SMALLER_NUMBER_NEXT_ACTUAL = 1L - 1L;

    private static final Long DEFAULT_NUMBER_INCREMENT = 1L;
    private static final Long UPDATED_NUMBER_INCREMENT = 2L;
    private static final Long SMALLER_NUMBER_INCREMENT = 1L - 1L;

    private static final String ENTITY_API_URL = "/api/module-configurations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ModuleConfigurationRepository moduleConfigurationRepository;

    @Autowired
    private ModuleConfigurationMapper moduleConfigurationMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restModuleConfigurationMockMvc;

    private ModuleConfiguration moduleConfiguration;

    private ModuleConfiguration insertedModuleConfiguration;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ModuleConfiguration createEntity() {
        return new ModuleConfiguration()
            .name(DEFAULT_NAME)
            .prefix(DEFAULT_PREFIX)
            .padding(DEFAULT_PADDING)
            .numberNextActual(DEFAULT_NUMBER_NEXT_ACTUAL)
            .numberIncrement(DEFAULT_NUMBER_INCREMENT);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ModuleConfiguration createUpdatedEntity() {
        return new ModuleConfiguration()
            .name(UPDATED_NAME)
            .prefix(UPDATED_PREFIX)
            .padding(UPDATED_PADDING)
            .numberNextActual(UPDATED_NUMBER_NEXT_ACTUAL)
            .numberIncrement(UPDATED_NUMBER_INCREMENT);
    }

    @BeforeEach
    public void initTest() {
        moduleConfiguration = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedModuleConfiguration != null) {
            moduleConfigurationRepository.delete(insertedModuleConfiguration);
            insertedModuleConfiguration = null;
        }
    }

    @Test
    @Transactional
    void createModuleConfiguration() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the ModuleConfiguration
        ModuleConfigurationDTO moduleConfigurationDTO = moduleConfigurationMapper.toDto(moduleConfiguration);
        var returnedModuleConfigurationDTO = om.readValue(
            restModuleConfigurationMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(moduleConfigurationDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            ModuleConfigurationDTO.class
        );

        // Validate the ModuleConfiguration in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedModuleConfiguration = moduleConfigurationMapper.toEntity(returnedModuleConfigurationDTO);
        assertModuleConfigurationUpdatableFieldsEquals(
            returnedModuleConfiguration,
            getPersistedModuleConfiguration(returnedModuleConfiguration)
        );

        insertedModuleConfiguration = returnedModuleConfiguration;
    }

    @Test
    @Transactional
    void createModuleConfigurationWithExistingId() throws Exception {
        // Create the ModuleConfiguration with an existing ID
        moduleConfiguration.setId(1L);
        ModuleConfigurationDTO moduleConfigurationDTO = moduleConfigurationMapper.toDto(moduleConfiguration);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restModuleConfigurationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(moduleConfigurationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ModuleConfiguration in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllModuleConfigurations() throws Exception {
        // Initialize the database
        insertedModuleConfiguration = moduleConfigurationRepository.saveAndFlush(moduleConfiguration);

        // Get all the moduleConfigurationList
        restModuleConfigurationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(moduleConfiguration.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].prefix").value(hasItem(DEFAULT_PREFIX)))
            .andExpect(jsonPath("$.[*].padding").value(hasItem(DEFAULT_PADDING.intValue())))
            .andExpect(jsonPath("$.[*].numberNextActual").value(hasItem(DEFAULT_NUMBER_NEXT_ACTUAL.intValue())))
            .andExpect(jsonPath("$.[*].numberIncrement").value(hasItem(DEFAULT_NUMBER_INCREMENT.intValue())));
    }

    @Test
    @Transactional
    void getModuleConfiguration() throws Exception {
        // Initialize the database
        insertedModuleConfiguration = moduleConfigurationRepository.saveAndFlush(moduleConfiguration);

        // Get the moduleConfiguration
        restModuleConfigurationMockMvc
            .perform(get(ENTITY_API_URL_ID, moduleConfiguration.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(moduleConfiguration.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.prefix").value(DEFAULT_PREFIX))
            .andExpect(jsonPath("$.padding").value(DEFAULT_PADDING.intValue()))
            .andExpect(jsonPath("$.numberNextActual").value(DEFAULT_NUMBER_NEXT_ACTUAL.intValue()))
            .andExpect(jsonPath("$.numberIncrement").value(DEFAULT_NUMBER_INCREMENT.intValue()));
    }

    @Test
    @Transactional
    void getModuleConfigurationsByIdFiltering() throws Exception {
        // Initialize the database
        insertedModuleConfiguration = moduleConfigurationRepository.saveAndFlush(moduleConfiguration);

        Long id = moduleConfiguration.getId();

        defaultModuleConfigurationFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultModuleConfigurationFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultModuleConfigurationFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllModuleConfigurationsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedModuleConfiguration = moduleConfigurationRepository.saveAndFlush(moduleConfiguration);

        // Get all the moduleConfigurationList where name equals to
        defaultModuleConfigurationFiltering("name.equals=" + DEFAULT_NAME, "name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllModuleConfigurationsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        insertedModuleConfiguration = moduleConfigurationRepository.saveAndFlush(moduleConfiguration);

        // Get all the moduleConfigurationList where name in
        defaultModuleConfigurationFiltering("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME, "name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllModuleConfigurationsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedModuleConfiguration = moduleConfigurationRepository.saveAndFlush(moduleConfiguration);

        // Get all the moduleConfigurationList where name is not null
        defaultModuleConfigurationFiltering("name.specified=true", "name.specified=false");
    }

    @Test
    @Transactional
    void getAllModuleConfigurationsByNameContainsSomething() throws Exception {
        // Initialize the database
        insertedModuleConfiguration = moduleConfigurationRepository.saveAndFlush(moduleConfiguration);

        // Get all the moduleConfigurationList where name contains
        defaultModuleConfigurationFiltering("name.contains=" + DEFAULT_NAME, "name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllModuleConfigurationsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        insertedModuleConfiguration = moduleConfigurationRepository.saveAndFlush(moduleConfiguration);

        // Get all the moduleConfigurationList where name does not contain
        defaultModuleConfigurationFiltering("name.doesNotContain=" + UPDATED_NAME, "name.doesNotContain=" + DEFAULT_NAME);
    }

    @Test
    @Transactional
    void getAllModuleConfigurationsByPrefixIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedModuleConfiguration = moduleConfigurationRepository.saveAndFlush(moduleConfiguration);

        // Get all the moduleConfigurationList where prefix equals to
        defaultModuleConfigurationFiltering("prefix.equals=" + DEFAULT_PREFIX, "prefix.equals=" + UPDATED_PREFIX);
    }

    @Test
    @Transactional
    void getAllModuleConfigurationsByPrefixIsInShouldWork() throws Exception {
        // Initialize the database
        insertedModuleConfiguration = moduleConfigurationRepository.saveAndFlush(moduleConfiguration);

        // Get all the moduleConfigurationList where prefix in
        defaultModuleConfigurationFiltering("prefix.in=" + DEFAULT_PREFIX + "," + UPDATED_PREFIX, "prefix.in=" + UPDATED_PREFIX);
    }

    @Test
    @Transactional
    void getAllModuleConfigurationsByPrefixIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedModuleConfiguration = moduleConfigurationRepository.saveAndFlush(moduleConfiguration);

        // Get all the moduleConfigurationList where prefix is not null
        defaultModuleConfigurationFiltering("prefix.specified=true", "prefix.specified=false");
    }

    @Test
    @Transactional
    void getAllModuleConfigurationsByPrefixContainsSomething() throws Exception {
        // Initialize the database
        insertedModuleConfiguration = moduleConfigurationRepository.saveAndFlush(moduleConfiguration);

        // Get all the moduleConfigurationList where prefix contains
        defaultModuleConfigurationFiltering("prefix.contains=" + DEFAULT_PREFIX, "prefix.contains=" + UPDATED_PREFIX);
    }

    @Test
    @Transactional
    void getAllModuleConfigurationsByPrefixNotContainsSomething() throws Exception {
        // Initialize the database
        insertedModuleConfiguration = moduleConfigurationRepository.saveAndFlush(moduleConfiguration);

        // Get all the moduleConfigurationList where prefix does not contain
        defaultModuleConfigurationFiltering("prefix.doesNotContain=" + UPDATED_PREFIX, "prefix.doesNotContain=" + DEFAULT_PREFIX);
    }

    @Test
    @Transactional
    void getAllModuleConfigurationsByPaddingIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedModuleConfiguration = moduleConfigurationRepository.saveAndFlush(moduleConfiguration);

        // Get all the moduleConfigurationList where padding equals to
        defaultModuleConfigurationFiltering("padding.equals=" + DEFAULT_PADDING, "padding.equals=" + UPDATED_PADDING);
    }

    @Test
    @Transactional
    void getAllModuleConfigurationsByPaddingIsInShouldWork() throws Exception {
        // Initialize the database
        insertedModuleConfiguration = moduleConfigurationRepository.saveAndFlush(moduleConfiguration);

        // Get all the moduleConfigurationList where padding in
        defaultModuleConfigurationFiltering("padding.in=" + DEFAULT_PADDING + "," + UPDATED_PADDING, "padding.in=" + UPDATED_PADDING);
    }

    @Test
    @Transactional
    void getAllModuleConfigurationsByPaddingIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedModuleConfiguration = moduleConfigurationRepository.saveAndFlush(moduleConfiguration);

        // Get all the moduleConfigurationList where padding is not null
        defaultModuleConfigurationFiltering("padding.specified=true", "padding.specified=false");
    }

    @Test
    @Transactional
    void getAllModuleConfigurationsByPaddingIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedModuleConfiguration = moduleConfigurationRepository.saveAndFlush(moduleConfiguration);

        // Get all the moduleConfigurationList where padding is greater than or equal to
        defaultModuleConfigurationFiltering(
            "padding.greaterThanOrEqual=" + DEFAULT_PADDING,
            "padding.greaterThanOrEqual=" + UPDATED_PADDING
        );
    }

    @Test
    @Transactional
    void getAllModuleConfigurationsByPaddingIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedModuleConfiguration = moduleConfigurationRepository.saveAndFlush(moduleConfiguration);

        // Get all the moduleConfigurationList where padding is less than or equal to
        defaultModuleConfigurationFiltering("padding.lessThanOrEqual=" + DEFAULT_PADDING, "padding.lessThanOrEqual=" + SMALLER_PADDING);
    }

    @Test
    @Transactional
    void getAllModuleConfigurationsByPaddingIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedModuleConfiguration = moduleConfigurationRepository.saveAndFlush(moduleConfiguration);

        // Get all the moduleConfigurationList where padding is less than
        defaultModuleConfigurationFiltering("padding.lessThan=" + UPDATED_PADDING, "padding.lessThan=" + DEFAULT_PADDING);
    }

    @Test
    @Transactional
    void getAllModuleConfigurationsByPaddingIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedModuleConfiguration = moduleConfigurationRepository.saveAndFlush(moduleConfiguration);

        // Get all the moduleConfigurationList where padding is greater than
        defaultModuleConfigurationFiltering("padding.greaterThan=" + SMALLER_PADDING, "padding.greaterThan=" + DEFAULT_PADDING);
    }

    @Test
    @Transactional
    void getAllModuleConfigurationsByNumberNextActualIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedModuleConfiguration = moduleConfigurationRepository.saveAndFlush(moduleConfiguration);

        // Get all the moduleConfigurationList where numberNextActual equals to
        defaultModuleConfigurationFiltering(
            "numberNextActual.equals=" + DEFAULT_NUMBER_NEXT_ACTUAL,
            "numberNextActual.equals=" + UPDATED_NUMBER_NEXT_ACTUAL
        );
    }

    @Test
    @Transactional
    void getAllModuleConfigurationsByNumberNextActualIsInShouldWork() throws Exception {
        // Initialize the database
        insertedModuleConfiguration = moduleConfigurationRepository.saveAndFlush(moduleConfiguration);

        // Get all the moduleConfigurationList where numberNextActual in
        defaultModuleConfigurationFiltering(
            "numberNextActual.in=" + DEFAULT_NUMBER_NEXT_ACTUAL + "," + UPDATED_NUMBER_NEXT_ACTUAL,
            "numberNextActual.in=" + UPDATED_NUMBER_NEXT_ACTUAL
        );
    }

    @Test
    @Transactional
    void getAllModuleConfigurationsByNumberNextActualIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedModuleConfiguration = moduleConfigurationRepository.saveAndFlush(moduleConfiguration);

        // Get all the moduleConfigurationList where numberNextActual is not null
        defaultModuleConfigurationFiltering("numberNextActual.specified=true", "numberNextActual.specified=false");
    }

    @Test
    @Transactional
    void getAllModuleConfigurationsByNumberNextActualIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedModuleConfiguration = moduleConfigurationRepository.saveAndFlush(moduleConfiguration);

        // Get all the moduleConfigurationList where numberNextActual is greater than or equal to
        defaultModuleConfigurationFiltering(
            "numberNextActual.greaterThanOrEqual=" + DEFAULT_NUMBER_NEXT_ACTUAL,
            "numberNextActual.greaterThanOrEqual=" + UPDATED_NUMBER_NEXT_ACTUAL
        );
    }

    @Test
    @Transactional
    void getAllModuleConfigurationsByNumberNextActualIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedModuleConfiguration = moduleConfigurationRepository.saveAndFlush(moduleConfiguration);

        // Get all the moduleConfigurationList where numberNextActual is less than or equal to
        defaultModuleConfigurationFiltering(
            "numberNextActual.lessThanOrEqual=" + DEFAULT_NUMBER_NEXT_ACTUAL,
            "numberNextActual.lessThanOrEqual=" + SMALLER_NUMBER_NEXT_ACTUAL
        );
    }

    @Test
    @Transactional
    void getAllModuleConfigurationsByNumberNextActualIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedModuleConfiguration = moduleConfigurationRepository.saveAndFlush(moduleConfiguration);

        // Get all the moduleConfigurationList where numberNextActual is less than
        defaultModuleConfigurationFiltering(
            "numberNextActual.lessThan=" + UPDATED_NUMBER_NEXT_ACTUAL,
            "numberNextActual.lessThan=" + DEFAULT_NUMBER_NEXT_ACTUAL
        );
    }

    @Test
    @Transactional
    void getAllModuleConfigurationsByNumberNextActualIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedModuleConfiguration = moduleConfigurationRepository.saveAndFlush(moduleConfiguration);

        // Get all the moduleConfigurationList where numberNextActual is greater than
        defaultModuleConfigurationFiltering(
            "numberNextActual.greaterThan=" + SMALLER_NUMBER_NEXT_ACTUAL,
            "numberNextActual.greaterThan=" + DEFAULT_NUMBER_NEXT_ACTUAL
        );
    }

    @Test
    @Transactional
    void getAllModuleConfigurationsByNumberIncrementIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedModuleConfiguration = moduleConfigurationRepository.saveAndFlush(moduleConfiguration);

        // Get all the moduleConfigurationList where numberIncrement equals to
        defaultModuleConfigurationFiltering(
            "numberIncrement.equals=" + DEFAULT_NUMBER_INCREMENT,
            "numberIncrement.equals=" + UPDATED_NUMBER_INCREMENT
        );
    }

    @Test
    @Transactional
    void getAllModuleConfigurationsByNumberIncrementIsInShouldWork() throws Exception {
        // Initialize the database
        insertedModuleConfiguration = moduleConfigurationRepository.saveAndFlush(moduleConfiguration);

        // Get all the moduleConfigurationList where numberIncrement in
        defaultModuleConfigurationFiltering(
            "numberIncrement.in=" + DEFAULT_NUMBER_INCREMENT + "," + UPDATED_NUMBER_INCREMENT,
            "numberIncrement.in=" + UPDATED_NUMBER_INCREMENT
        );
    }

    @Test
    @Transactional
    void getAllModuleConfigurationsByNumberIncrementIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedModuleConfiguration = moduleConfigurationRepository.saveAndFlush(moduleConfiguration);

        // Get all the moduleConfigurationList where numberIncrement is not null
        defaultModuleConfigurationFiltering("numberIncrement.specified=true", "numberIncrement.specified=false");
    }

    @Test
    @Transactional
    void getAllModuleConfigurationsByNumberIncrementIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedModuleConfiguration = moduleConfigurationRepository.saveAndFlush(moduleConfiguration);

        // Get all the moduleConfigurationList where numberIncrement is greater than or equal to
        defaultModuleConfigurationFiltering(
            "numberIncrement.greaterThanOrEqual=" + DEFAULT_NUMBER_INCREMENT,
            "numberIncrement.greaterThanOrEqual=" + UPDATED_NUMBER_INCREMENT
        );
    }

    @Test
    @Transactional
    void getAllModuleConfigurationsByNumberIncrementIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedModuleConfiguration = moduleConfigurationRepository.saveAndFlush(moduleConfiguration);

        // Get all the moduleConfigurationList where numberIncrement is less than or equal to
        defaultModuleConfigurationFiltering(
            "numberIncrement.lessThanOrEqual=" + DEFAULT_NUMBER_INCREMENT,
            "numberIncrement.lessThanOrEqual=" + SMALLER_NUMBER_INCREMENT
        );
    }

    @Test
    @Transactional
    void getAllModuleConfigurationsByNumberIncrementIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedModuleConfiguration = moduleConfigurationRepository.saveAndFlush(moduleConfiguration);

        // Get all the moduleConfigurationList where numberIncrement is less than
        defaultModuleConfigurationFiltering(
            "numberIncrement.lessThan=" + UPDATED_NUMBER_INCREMENT,
            "numberIncrement.lessThan=" + DEFAULT_NUMBER_INCREMENT
        );
    }

    @Test
    @Transactional
    void getAllModuleConfigurationsByNumberIncrementIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedModuleConfiguration = moduleConfigurationRepository.saveAndFlush(moduleConfiguration);

        // Get all the moduleConfigurationList where numberIncrement is greater than
        defaultModuleConfigurationFiltering(
            "numberIncrement.greaterThan=" + SMALLER_NUMBER_INCREMENT,
            "numberIncrement.greaterThan=" + DEFAULT_NUMBER_INCREMENT
        );
    }

    private void defaultModuleConfigurationFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultModuleConfigurationShouldBeFound(shouldBeFound);
        defaultModuleConfigurationShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultModuleConfigurationShouldBeFound(String filter) throws Exception {
        restModuleConfigurationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(moduleConfiguration.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].prefix").value(hasItem(DEFAULT_PREFIX)))
            .andExpect(jsonPath("$.[*].padding").value(hasItem(DEFAULT_PADDING.intValue())))
            .andExpect(jsonPath("$.[*].numberNextActual").value(hasItem(DEFAULT_NUMBER_NEXT_ACTUAL.intValue())))
            .andExpect(jsonPath("$.[*].numberIncrement").value(hasItem(DEFAULT_NUMBER_INCREMENT.intValue())));

        // Check, that the count call also returns 1
        restModuleConfigurationMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultModuleConfigurationShouldNotBeFound(String filter) throws Exception {
        restModuleConfigurationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restModuleConfigurationMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingModuleConfiguration() throws Exception {
        // Get the moduleConfiguration
        restModuleConfigurationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingModuleConfiguration() throws Exception {
        // Initialize the database
        insertedModuleConfiguration = moduleConfigurationRepository.saveAndFlush(moduleConfiguration);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the moduleConfiguration
        ModuleConfiguration updatedModuleConfiguration = moduleConfigurationRepository.findById(moduleConfiguration.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedModuleConfiguration are not directly saved in db
        em.detach(updatedModuleConfiguration);
        updatedModuleConfiguration
            .name(UPDATED_NAME)
            .prefix(UPDATED_PREFIX)
            .padding(UPDATED_PADDING)
            .numberNextActual(UPDATED_NUMBER_NEXT_ACTUAL)
            .numberIncrement(UPDATED_NUMBER_INCREMENT);
        ModuleConfigurationDTO moduleConfigurationDTO = moduleConfigurationMapper.toDto(updatedModuleConfiguration);

        restModuleConfigurationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, moduleConfigurationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(moduleConfigurationDTO))
            )
            .andExpect(status().isOk());

        // Validate the ModuleConfiguration in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedModuleConfigurationToMatchAllProperties(updatedModuleConfiguration);
    }

    @Test
    @Transactional
    void putNonExistingModuleConfiguration() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        moduleConfiguration.setId(longCount.incrementAndGet());

        // Create the ModuleConfiguration
        ModuleConfigurationDTO moduleConfigurationDTO = moduleConfigurationMapper.toDto(moduleConfiguration);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restModuleConfigurationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, moduleConfigurationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(moduleConfigurationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ModuleConfiguration in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchModuleConfiguration() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        moduleConfiguration.setId(longCount.incrementAndGet());

        // Create the ModuleConfiguration
        ModuleConfigurationDTO moduleConfigurationDTO = moduleConfigurationMapper.toDto(moduleConfiguration);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restModuleConfigurationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(moduleConfigurationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ModuleConfiguration in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamModuleConfiguration() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        moduleConfiguration.setId(longCount.incrementAndGet());

        // Create the ModuleConfiguration
        ModuleConfigurationDTO moduleConfigurationDTO = moduleConfigurationMapper.toDto(moduleConfiguration);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restModuleConfigurationMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(moduleConfigurationDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ModuleConfiguration in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateModuleConfigurationWithPatch() throws Exception {
        // Initialize the database
        insertedModuleConfiguration = moduleConfigurationRepository.saveAndFlush(moduleConfiguration);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the moduleConfiguration using partial update
        ModuleConfiguration partialUpdatedModuleConfiguration = new ModuleConfiguration();
        partialUpdatedModuleConfiguration.setId(moduleConfiguration.getId());

        partialUpdatedModuleConfiguration.name(UPDATED_NAME).prefix(UPDATED_PREFIX).numberNextActual(UPDATED_NUMBER_NEXT_ACTUAL);

        restModuleConfigurationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedModuleConfiguration.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedModuleConfiguration))
            )
            .andExpect(status().isOk());

        // Validate the ModuleConfiguration in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertModuleConfigurationUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedModuleConfiguration, moduleConfiguration),
            getPersistedModuleConfiguration(moduleConfiguration)
        );
    }

    @Test
    @Transactional
    void fullUpdateModuleConfigurationWithPatch() throws Exception {
        // Initialize the database
        insertedModuleConfiguration = moduleConfigurationRepository.saveAndFlush(moduleConfiguration);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the moduleConfiguration using partial update
        ModuleConfiguration partialUpdatedModuleConfiguration = new ModuleConfiguration();
        partialUpdatedModuleConfiguration.setId(moduleConfiguration.getId());

        partialUpdatedModuleConfiguration
            .name(UPDATED_NAME)
            .prefix(UPDATED_PREFIX)
            .padding(UPDATED_PADDING)
            .numberNextActual(UPDATED_NUMBER_NEXT_ACTUAL)
            .numberIncrement(UPDATED_NUMBER_INCREMENT);

        restModuleConfigurationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedModuleConfiguration.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedModuleConfiguration))
            )
            .andExpect(status().isOk());

        // Validate the ModuleConfiguration in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertModuleConfigurationUpdatableFieldsEquals(
            partialUpdatedModuleConfiguration,
            getPersistedModuleConfiguration(partialUpdatedModuleConfiguration)
        );
    }

    @Test
    @Transactional
    void patchNonExistingModuleConfiguration() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        moduleConfiguration.setId(longCount.incrementAndGet());

        // Create the ModuleConfiguration
        ModuleConfigurationDTO moduleConfigurationDTO = moduleConfigurationMapper.toDto(moduleConfiguration);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restModuleConfigurationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, moduleConfigurationDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(moduleConfigurationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ModuleConfiguration in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchModuleConfiguration() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        moduleConfiguration.setId(longCount.incrementAndGet());

        // Create the ModuleConfiguration
        ModuleConfigurationDTO moduleConfigurationDTO = moduleConfigurationMapper.toDto(moduleConfiguration);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restModuleConfigurationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(moduleConfigurationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ModuleConfiguration in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamModuleConfiguration() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        moduleConfiguration.setId(longCount.incrementAndGet());

        // Create the ModuleConfiguration
        ModuleConfigurationDTO moduleConfigurationDTO = moduleConfigurationMapper.toDto(moduleConfiguration);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restModuleConfigurationMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(moduleConfigurationDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ModuleConfiguration in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteModuleConfiguration() throws Exception {
        // Initialize the database
        insertedModuleConfiguration = moduleConfigurationRepository.saveAndFlush(moduleConfiguration);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the moduleConfiguration
        restModuleConfigurationMockMvc
            .perform(delete(ENTITY_API_URL_ID, moduleConfiguration.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return moduleConfigurationRepository.count();
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

    protected ModuleConfiguration getPersistedModuleConfiguration(ModuleConfiguration moduleConfiguration) {
        return moduleConfigurationRepository.findById(moduleConfiguration.getId()).orElseThrow();
    }

    protected void assertPersistedModuleConfigurationToMatchAllProperties(ModuleConfiguration expectedModuleConfiguration) {
        assertModuleConfigurationAllPropertiesEquals(
            expectedModuleConfiguration,
            getPersistedModuleConfiguration(expectedModuleConfiguration)
        );
    }

    protected void assertPersistedModuleConfigurationToMatchUpdatableProperties(ModuleConfiguration expectedModuleConfiguration) {
        assertModuleConfigurationAllUpdatablePropertiesEquals(
            expectedModuleConfiguration,
            getPersistedModuleConfiguration(expectedModuleConfiguration)
        );
    }
}
