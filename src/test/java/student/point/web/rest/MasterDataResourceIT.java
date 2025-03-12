package student.point.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static student.point.domain.MasterDataAsserts.*;
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
import student.point.domain.MasterData;
import student.point.repository.MasterDataRepository;
import student.point.service.dto.MasterDataDTO;
import student.point.service.mapper.MasterDataMapper;

/**
 * Integration tests for the {@link MasterDataResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MasterDataResourceIT {

    private static final String DEFAULT_KEY = "AAAAAAAAAA";
    private static final String UPDATED_KEY = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_STATUS = false;
    private static final Boolean UPDATED_STATUS = true;

    private static final String ENTITY_API_URL = "/api/master-data";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private MasterDataRepository masterDataRepository;

    @Autowired
    private MasterDataMapper masterDataMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMasterDataMockMvc;

    private MasterData masterData;

    private MasterData insertedMasterData;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MasterData createEntity() {
        return new MasterData()
            .key(DEFAULT_KEY)
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .status(DEFAULT_STATUS);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MasterData createUpdatedEntity() {
        return new MasterData()
            .key(UPDATED_KEY)
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .status(UPDATED_STATUS);
    }

    @BeforeEach
    public void initTest() {
        masterData = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedMasterData != null) {
            masterDataRepository.delete(insertedMasterData);
            insertedMasterData = null;
        }
    }

    @Test
    @Transactional
    void createMasterData() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the MasterData
        MasterDataDTO masterDataDTO = masterDataMapper.toDto(masterData);
        var returnedMasterDataDTO = om.readValue(
            restMasterDataMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(masterDataDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            MasterDataDTO.class
        );

        // Validate the MasterData in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedMasterData = masterDataMapper.toEntity(returnedMasterDataDTO);
        assertMasterDataUpdatableFieldsEquals(returnedMasterData, getPersistedMasterData(returnedMasterData));

        insertedMasterData = returnedMasterData;
    }

    @Test
    @Transactional
    void createMasterDataWithExistingId() throws Exception {
        // Create the MasterData with an existing ID
        masterData.setId(1L);
        MasterDataDTO masterDataDTO = masterDataMapper.toDto(masterData);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMasterDataMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(masterDataDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MasterData in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllMasterData() throws Exception {
        // Initialize the database
        insertedMasterData = masterDataRepository.saveAndFlush(masterData);

        // Get all the masterDataList
        restMasterDataMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(masterData.getId().intValue())))
            .andExpect(jsonPath("$.[*].key").value(hasItem(DEFAULT_KEY)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));
    }

    @Test
    @Transactional
    void getMasterData() throws Exception {
        // Initialize the database
        insertedMasterData = masterDataRepository.saveAndFlush(masterData);

        // Get the masterData
        restMasterDataMockMvc
            .perform(get(ENTITY_API_URL_ID, masterData.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(masterData.getId().intValue()))
            .andExpect(jsonPath("$.key").value(DEFAULT_KEY))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS));
    }

    @Test
    @Transactional
    void getMasterDataByIdFiltering() throws Exception {
        // Initialize the database
        insertedMasterData = masterDataRepository.saveAndFlush(masterData);

        Long id = masterData.getId();

        defaultMasterDataFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultMasterDataFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultMasterDataFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllMasterDataByKeyIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedMasterData = masterDataRepository.saveAndFlush(masterData);

        // Get all the masterDataList where key equals to
        defaultMasterDataFiltering("key.equals=" + DEFAULT_KEY, "key.equals=" + UPDATED_KEY);
    }

    @Test
    @Transactional
    void getAllMasterDataByKeyIsInShouldWork() throws Exception {
        // Initialize the database
        insertedMasterData = masterDataRepository.saveAndFlush(masterData);

        // Get all the masterDataList where key in
        defaultMasterDataFiltering("key.in=" + DEFAULT_KEY + "," + UPDATED_KEY, "key.in=" + UPDATED_KEY);
    }

    @Test
    @Transactional
    void getAllMasterDataByKeyIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedMasterData = masterDataRepository.saveAndFlush(masterData);

        // Get all the masterDataList where key is not null
        defaultMasterDataFiltering("key.specified=true", "key.specified=false");
    }

    @Test
    @Transactional
    void getAllMasterDataByKeyContainsSomething() throws Exception {
        // Initialize the database
        insertedMasterData = masterDataRepository.saveAndFlush(masterData);

        // Get all the masterDataList where key contains
        defaultMasterDataFiltering("key.contains=" + DEFAULT_KEY, "key.contains=" + UPDATED_KEY);
    }

    @Test
    @Transactional
    void getAllMasterDataByKeyNotContainsSomething() throws Exception {
        // Initialize the database
        insertedMasterData = masterDataRepository.saveAndFlush(masterData);

        // Get all the masterDataList where key does not contain
        defaultMasterDataFiltering("key.doesNotContain=" + UPDATED_KEY, "key.doesNotContain=" + DEFAULT_KEY);
    }

    @Test
    @Transactional
    void getAllMasterDataByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedMasterData = masterDataRepository.saveAndFlush(masterData);

        // Get all the masterDataList where code equals to
        defaultMasterDataFiltering("code.equals=" + DEFAULT_CODE, "code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllMasterDataByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedMasterData = masterDataRepository.saveAndFlush(masterData);

        // Get all the masterDataList where code in
        defaultMasterDataFiltering("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE, "code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllMasterDataByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedMasterData = masterDataRepository.saveAndFlush(masterData);

        // Get all the masterDataList where code is not null
        defaultMasterDataFiltering("code.specified=true", "code.specified=false");
    }

    @Test
    @Transactional
    void getAllMasterDataByCodeContainsSomething() throws Exception {
        // Initialize the database
        insertedMasterData = masterDataRepository.saveAndFlush(masterData);

        // Get all the masterDataList where code contains
        defaultMasterDataFiltering("code.contains=" + DEFAULT_CODE, "code.contains=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllMasterDataByCodeNotContainsSomething() throws Exception {
        // Initialize the database
        insertedMasterData = masterDataRepository.saveAndFlush(masterData);

        // Get all the masterDataList where code does not contain
        defaultMasterDataFiltering("code.doesNotContain=" + UPDATED_CODE, "code.doesNotContain=" + DEFAULT_CODE);
    }

    @Test
    @Transactional
    void getAllMasterDataByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedMasterData = masterDataRepository.saveAndFlush(masterData);

        // Get all the masterDataList where name equals to
        defaultMasterDataFiltering("name.equals=" + DEFAULT_NAME, "name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllMasterDataByNameIsInShouldWork() throws Exception {
        // Initialize the database
        insertedMasterData = masterDataRepository.saveAndFlush(masterData);

        // Get all the masterDataList where name in
        defaultMasterDataFiltering("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME, "name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllMasterDataByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedMasterData = masterDataRepository.saveAndFlush(masterData);

        // Get all the masterDataList where name is not null
        defaultMasterDataFiltering("name.specified=true", "name.specified=false");
    }

    @Test
    @Transactional
    void getAllMasterDataByNameContainsSomething() throws Exception {
        // Initialize the database
        insertedMasterData = masterDataRepository.saveAndFlush(masterData);

        // Get all the masterDataList where name contains
        defaultMasterDataFiltering("name.contains=" + DEFAULT_NAME, "name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllMasterDataByNameNotContainsSomething() throws Exception {
        // Initialize the database
        insertedMasterData = masterDataRepository.saveAndFlush(masterData);

        // Get all the masterDataList where name does not contain
        defaultMasterDataFiltering("name.doesNotContain=" + UPDATED_NAME, "name.doesNotContain=" + DEFAULT_NAME);
    }

    @Test
    @Transactional
    void getAllMasterDataByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedMasterData = masterDataRepository.saveAndFlush(masterData);

        // Get all the masterDataList where description equals to
        defaultMasterDataFiltering("description.equals=" + DEFAULT_DESCRIPTION, "description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllMasterDataByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        insertedMasterData = masterDataRepository.saveAndFlush(masterData);

        // Get all the masterDataList where description in
        defaultMasterDataFiltering(
            "description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION,
            "description.in=" + UPDATED_DESCRIPTION
        );
    }

    @Test
    @Transactional
    void getAllMasterDataByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedMasterData = masterDataRepository.saveAndFlush(masterData);

        // Get all the masterDataList where description is not null
        defaultMasterDataFiltering("description.specified=true", "description.specified=false");
    }

    @Test
    @Transactional
    void getAllMasterDataByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        insertedMasterData = masterDataRepository.saveAndFlush(masterData);

        // Get all the masterDataList where description contains
        defaultMasterDataFiltering("description.contains=" + DEFAULT_DESCRIPTION, "description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllMasterDataByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        insertedMasterData = masterDataRepository.saveAndFlush(masterData);

        // Get all the masterDataList where description does not contain
        defaultMasterDataFiltering(
            "description.doesNotContain=" + UPDATED_DESCRIPTION,
            "description.doesNotContain=" + DEFAULT_DESCRIPTION
        );
    }

    @Test
    @Transactional
    void getAllMasterDataByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedMasterData = masterDataRepository.saveAndFlush(masterData);

        // Get all the masterDataList where status equals to
        defaultMasterDataFiltering("status.equals=" + DEFAULT_STATUS, "status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllMasterDataByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        insertedMasterData = masterDataRepository.saveAndFlush(masterData);

        // Get all the masterDataList where status in
        defaultMasterDataFiltering("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS, "status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllMasterDataByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedMasterData = masterDataRepository.saveAndFlush(masterData);

        // Get all the masterDataList where status is not null
        defaultMasterDataFiltering("status.specified=true", "status.specified=false");
    }

    private void defaultMasterDataFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultMasterDataShouldBeFound(shouldBeFound);
        defaultMasterDataShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMasterDataShouldBeFound(String filter) throws Exception {
        restMasterDataMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(masterData.getId().intValue())))
            .andExpect(jsonPath("$.[*].key").value(hasItem(DEFAULT_KEY)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));

        // Check, that the count call also returns 1
        restMasterDataMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMasterDataShouldNotBeFound(String filter) throws Exception {
        restMasterDataMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMasterDataMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingMasterData() throws Exception {
        // Get the masterData
        restMasterDataMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingMasterData() throws Exception {
        // Initialize the database
        insertedMasterData = masterDataRepository.saveAndFlush(masterData);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the masterData
        MasterData updatedMasterData = masterDataRepository.findById(masterData.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedMasterData are not directly saved in db
        em.detach(updatedMasterData);
        updatedMasterData.key(UPDATED_KEY).code(UPDATED_CODE).name(UPDATED_NAME).description(UPDATED_DESCRIPTION).status(UPDATED_STATUS);
        MasterDataDTO masterDataDTO = masterDataMapper.toDto(updatedMasterData);

        restMasterDataMockMvc
            .perform(
                put(ENTITY_API_URL_ID, masterDataDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(masterDataDTO))
            )
            .andExpect(status().isOk());

        // Validate the MasterData in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedMasterDataToMatchAllProperties(updatedMasterData);
    }

    @Test
    @Transactional
    void putNonExistingMasterData() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        masterData.setId(longCount.incrementAndGet());

        // Create the MasterData
        MasterDataDTO masterDataDTO = masterDataMapper.toDto(masterData);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMasterDataMockMvc
            .perform(
                put(ENTITY_API_URL_ID, masterDataDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(masterDataDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MasterData in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMasterData() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        masterData.setId(longCount.incrementAndGet());

        // Create the MasterData
        MasterDataDTO masterDataDTO = masterDataMapper.toDto(masterData);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMasterDataMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(masterDataDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MasterData in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMasterData() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        masterData.setId(longCount.incrementAndGet());

        // Create the MasterData
        MasterDataDTO masterDataDTO = masterDataMapper.toDto(masterData);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMasterDataMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(masterDataDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the MasterData in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMasterDataWithPatch() throws Exception {
        // Initialize the database
        insertedMasterData = masterDataRepository.saveAndFlush(masterData);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the masterData using partial update
        MasterData partialUpdatedMasterData = new MasterData();
        partialUpdatedMasterData.setId(masterData.getId());

        partialUpdatedMasterData.key(UPDATED_KEY).name(UPDATED_NAME).description(UPDATED_DESCRIPTION).status(UPDATED_STATUS);

        restMasterDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMasterData.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedMasterData))
            )
            .andExpect(status().isOk());

        // Validate the MasterData in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMasterDataUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedMasterData, masterData),
            getPersistedMasterData(masterData)
        );
    }

    @Test
    @Transactional
    void fullUpdateMasterDataWithPatch() throws Exception {
        // Initialize the database
        insertedMasterData = masterDataRepository.saveAndFlush(masterData);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the masterData using partial update
        MasterData partialUpdatedMasterData = new MasterData();
        partialUpdatedMasterData.setId(masterData.getId());

        partialUpdatedMasterData
            .key(UPDATED_KEY)
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .status(UPDATED_STATUS);

        restMasterDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMasterData.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedMasterData))
            )
            .andExpect(status().isOk());

        // Validate the MasterData in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMasterDataUpdatableFieldsEquals(partialUpdatedMasterData, getPersistedMasterData(partialUpdatedMasterData));
    }

    @Test
    @Transactional
    void patchNonExistingMasterData() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        masterData.setId(longCount.incrementAndGet());

        // Create the MasterData
        MasterDataDTO masterDataDTO = masterDataMapper.toDto(masterData);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMasterDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, masterDataDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(masterDataDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MasterData in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMasterData() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        masterData.setId(longCount.incrementAndGet());

        // Create the MasterData
        MasterDataDTO masterDataDTO = masterDataMapper.toDto(masterData);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMasterDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(masterDataDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MasterData in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMasterData() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        masterData.setId(longCount.incrementAndGet());

        // Create the MasterData
        MasterDataDTO masterDataDTO = masterDataMapper.toDto(masterData);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMasterDataMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(masterDataDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the MasterData in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMasterData() throws Exception {
        // Initialize the database
        insertedMasterData = masterDataRepository.saveAndFlush(masterData);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the masterData
        restMasterDataMockMvc
            .perform(delete(ENTITY_API_URL_ID, masterData.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return masterDataRepository.count();
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

    protected MasterData getPersistedMasterData(MasterData masterData) {
        return masterDataRepository.findById(masterData.getId()).orElseThrow();
    }

    protected void assertPersistedMasterDataToMatchAllProperties(MasterData expectedMasterData) {
        assertMasterDataAllPropertiesEquals(expectedMasterData, getPersistedMasterData(expectedMasterData));
    }

    protected void assertPersistedMasterDataToMatchUpdatableProperties(MasterData expectedMasterData) {
        assertMasterDataAllUpdatablePropertiesEquals(expectedMasterData, getPersistedMasterData(expectedMasterData));
    }
}
