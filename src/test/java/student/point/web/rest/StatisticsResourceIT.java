package student.point.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static student.point.domain.StatisticsAsserts.*;
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
import student.point.domain.Statistics;
import student.point.domain.enumeration.StatisticsType;
import student.point.repository.StatisticsRepository;
import student.point.service.dto.StatisticsDTO;
import student.point.service.mapper.StatisticsMapper;

/**
 * Integration tests for the {@link StatisticsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class StatisticsResourceIT {

    private static final String DEFAULT_STATISTICS_CODE = "AAAAAAAAAA";
    private static final String UPDATED_STATISTICS_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_ACADEMIC_YEAR = "AAAAAAAAAA";
    private static final String UPDATED_ACADEMIC_YEAR = "BBBBBBBBBB";

    private static final StatisticsType DEFAULT_TYPE = StatisticsType.Scholarship;
    private static final StatisticsType UPDATED_TYPE = StatisticsType.Warning;

    private static final String DEFAULT_NOTES = "AAAAAAAAAA";
    private static final String UPDATED_NOTES = "BBBBBBBBBB";

    private static final Boolean DEFAULT_STATUS = false;
    private static final Boolean UPDATED_STATUS = true;

    private static final String ENTITY_API_URL = "/api/statistics";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private StatisticsRepository statisticsRepository;

    @Autowired
    private StatisticsMapper statisticsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStatisticsMockMvc;

    private Statistics statistics;

    private Statistics insertedStatistics;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Statistics createEntity() {
        return new Statistics()
            .statisticsCode(DEFAULT_STATISTICS_CODE)
            .academicYear(DEFAULT_ACADEMIC_YEAR)
            .type(DEFAULT_TYPE)
            .notes(DEFAULT_NOTES)
            .status(DEFAULT_STATUS);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Statistics createUpdatedEntity() {
        return new Statistics()
            .statisticsCode(UPDATED_STATISTICS_CODE)
            .academicYear(UPDATED_ACADEMIC_YEAR)
            .type(UPDATED_TYPE)
            .notes(UPDATED_NOTES)
            .status(UPDATED_STATUS);
    }

    @BeforeEach
    public void initTest() {
        statistics = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedStatistics != null) {
            statisticsRepository.delete(insertedStatistics);
            insertedStatistics = null;
        }
    }

    @Test
    @Transactional
    void createStatistics() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Statistics
        StatisticsDTO statisticsDTO = statisticsMapper.toDto(statistics);
        var returnedStatisticsDTO = om.readValue(
            restStatisticsMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(statisticsDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            StatisticsDTO.class
        );

        // Validate the Statistics in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedStatistics = statisticsMapper.toEntity(returnedStatisticsDTO);
        assertStatisticsUpdatableFieldsEquals(returnedStatistics, getPersistedStatistics(returnedStatistics));

        insertedStatistics = returnedStatistics;
    }

    @Test
    @Transactional
    void createStatisticsWithExistingId() throws Exception {
        // Create the Statistics with an existing ID
        statistics.setId(1L);
        StatisticsDTO statisticsDTO = statisticsMapper.toDto(statistics);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restStatisticsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(statisticsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Statistics in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllStatistics() throws Exception {
        // Initialize the database
        insertedStatistics = statisticsRepository.saveAndFlush(statistics);

        // Get all the statisticsList
        restStatisticsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(statistics.getId().intValue())))
            .andExpect(jsonPath("$.[*].statisticsCode").value(hasItem(DEFAULT_STATISTICS_CODE)))
            .andExpect(jsonPath("$.[*].academicYear").value(hasItem(DEFAULT_ACADEMIC_YEAR)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));
    }

    @Test
    @Transactional
    void getStatistics() throws Exception {
        // Initialize the database
        insertedStatistics = statisticsRepository.saveAndFlush(statistics);

        // Get the statistics
        restStatisticsMockMvc
            .perform(get(ENTITY_API_URL_ID, statistics.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(statistics.getId().intValue()))
            .andExpect(jsonPath("$.statisticsCode").value(DEFAULT_STATISTICS_CODE))
            .andExpect(jsonPath("$.academicYear").value(DEFAULT_ACADEMIC_YEAR))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.notes").value(DEFAULT_NOTES))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS));
    }

    @Test
    @Transactional
    void getStatisticsByIdFiltering() throws Exception {
        // Initialize the database
        insertedStatistics = statisticsRepository.saveAndFlush(statistics);

        Long id = statistics.getId();

        defaultStatisticsFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultStatisticsFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultStatisticsFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllStatisticsByStatisticsCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedStatistics = statisticsRepository.saveAndFlush(statistics);

        // Get all the statisticsList where statisticsCode equals to
        defaultStatisticsFiltering("statisticsCode.equals=" + DEFAULT_STATISTICS_CODE, "statisticsCode.equals=" + UPDATED_STATISTICS_CODE);
    }

    @Test
    @Transactional
    void getAllStatisticsByStatisticsCodeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedStatistics = statisticsRepository.saveAndFlush(statistics);

        // Get all the statisticsList where statisticsCode in
        defaultStatisticsFiltering(
            "statisticsCode.in=" + DEFAULT_STATISTICS_CODE + "," + UPDATED_STATISTICS_CODE,
            "statisticsCode.in=" + UPDATED_STATISTICS_CODE
        );
    }

    @Test
    @Transactional
    void getAllStatisticsByStatisticsCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedStatistics = statisticsRepository.saveAndFlush(statistics);

        // Get all the statisticsList where statisticsCode is not null
        defaultStatisticsFiltering("statisticsCode.specified=true", "statisticsCode.specified=false");
    }

    @Test
    @Transactional
    void getAllStatisticsByStatisticsCodeContainsSomething() throws Exception {
        // Initialize the database
        insertedStatistics = statisticsRepository.saveAndFlush(statistics);

        // Get all the statisticsList where statisticsCode contains
        defaultStatisticsFiltering(
            "statisticsCode.contains=" + DEFAULT_STATISTICS_CODE,
            "statisticsCode.contains=" + UPDATED_STATISTICS_CODE
        );
    }

    @Test
    @Transactional
    void getAllStatisticsByStatisticsCodeNotContainsSomething() throws Exception {
        // Initialize the database
        insertedStatistics = statisticsRepository.saveAndFlush(statistics);

        // Get all the statisticsList where statisticsCode does not contain
        defaultStatisticsFiltering(
            "statisticsCode.doesNotContain=" + UPDATED_STATISTICS_CODE,
            "statisticsCode.doesNotContain=" + DEFAULT_STATISTICS_CODE
        );
    }

    @Test
    @Transactional
    void getAllStatisticsByAcademicYearIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedStatistics = statisticsRepository.saveAndFlush(statistics);

        // Get all the statisticsList where academicYear equals to
        defaultStatisticsFiltering("academicYear.equals=" + DEFAULT_ACADEMIC_YEAR, "academicYear.equals=" + UPDATED_ACADEMIC_YEAR);
    }

    @Test
    @Transactional
    void getAllStatisticsByAcademicYearIsInShouldWork() throws Exception {
        // Initialize the database
        insertedStatistics = statisticsRepository.saveAndFlush(statistics);

        // Get all the statisticsList where academicYear in
        defaultStatisticsFiltering(
            "academicYear.in=" + DEFAULT_ACADEMIC_YEAR + "," + UPDATED_ACADEMIC_YEAR,
            "academicYear.in=" + UPDATED_ACADEMIC_YEAR
        );
    }

    @Test
    @Transactional
    void getAllStatisticsByAcademicYearIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedStatistics = statisticsRepository.saveAndFlush(statistics);

        // Get all the statisticsList where academicYear is not null
        defaultStatisticsFiltering("academicYear.specified=true", "academicYear.specified=false");
    }

    @Test
    @Transactional
    void getAllStatisticsByAcademicYearContainsSomething() throws Exception {
        // Initialize the database
        insertedStatistics = statisticsRepository.saveAndFlush(statistics);

        // Get all the statisticsList where academicYear contains
        defaultStatisticsFiltering("academicYear.contains=" + DEFAULT_ACADEMIC_YEAR, "academicYear.contains=" + UPDATED_ACADEMIC_YEAR);
    }

    @Test
    @Transactional
    void getAllStatisticsByAcademicYearNotContainsSomething() throws Exception {
        // Initialize the database
        insertedStatistics = statisticsRepository.saveAndFlush(statistics);

        // Get all the statisticsList where academicYear does not contain
        defaultStatisticsFiltering(
            "academicYear.doesNotContain=" + UPDATED_ACADEMIC_YEAR,
            "academicYear.doesNotContain=" + DEFAULT_ACADEMIC_YEAR
        );
    }

    @Test
    @Transactional
    void getAllStatisticsByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedStatistics = statisticsRepository.saveAndFlush(statistics);

        // Get all the statisticsList where type equals to
        defaultStatisticsFiltering("type.equals=" + DEFAULT_TYPE, "type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllStatisticsByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedStatistics = statisticsRepository.saveAndFlush(statistics);

        // Get all the statisticsList where type in
        defaultStatisticsFiltering("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE, "type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllStatisticsByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedStatistics = statisticsRepository.saveAndFlush(statistics);

        // Get all the statisticsList where type is not null
        defaultStatisticsFiltering("type.specified=true", "type.specified=false");
    }

    @Test
    @Transactional
    void getAllStatisticsByNotesIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedStatistics = statisticsRepository.saveAndFlush(statistics);

        // Get all the statisticsList where notes equals to
        defaultStatisticsFiltering("notes.equals=" + DEFAULT_NOTES, "notes.equals=" + UPDATED_NOTES);
    }

    @Test
    @Transactional
    void getAllStatisticsByNotesIsInShouldWork() throws Exception {
        // Initialize the database
        insertedStatistics = statisticsRepository.saveAndFlush(statistics);

        // Get all the statisticsList where notes in
        defaultStatisticsFiltering("notes.in=" + DEFAULT_NOTES + "," + UPDATED_NOTES, "notes.in=" + UPDATED_NOTES);
    }

    @Test
    @Transactional
    void getAllStatisticsByNotesIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedStatistics = statisticsRepository.saveAndFlush(statistics);

        // Get all the statisticsList where notes is not null
        defaultStatisticsFiltering("notes.specified=true", "notes.specified=false");
    }

    @Test
    @Transactional
    void getAllStatisticsByNotesContainsSomething() throws Exception {
        // Initialize the database
        insertedStatistics = statisticsRepository.saveAndFlush(statistics);

        // Get all the statisticsList where notes contains
        defaultStatisticsFiltering("notes.contains=" + DEFAULT_NOTES, "notes.contains=" + UPDATED_NOTES);
    }

    @Test
    @Transactional
    void getAllStatisticsByNotesNotContainsSomething() throws Exception {
        // Initialize the database
        insertedStatistics = statisticsRepository.saveAndFlush(statistics);

        // Get all the statisticsList where notes does not contain
        defaultStatisticsFiltering("notes.doesNotContain=" + UPDATED_NOTES, "notes.doesNotContain=" + DEFAULT_NOTES);
    }

    @Test
    @Transactional
    void getAllStatisticsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedStatistics = statisticsRepository.saveAndFlush(statistics);

        // Get all the statisticsList where status equals to
        defaultStatisticsFiltering("status.equals=" + DEFAULT_STATUS, "status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllStatisticsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        insertedStatistics = statisticsRepository.saveAndFlush(statistics);

        // Get all the statisticsList where status in
        defaultStatisticsFiltering("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS, "status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllStatisticsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedStatistics = statisticsRepository.saveAndFlush(statistics);

        // Get all the statisticsList where status is not null
        defaultStatisticsFiltering("status.specified=true", "status.specified=false");
    }

    private void defaultStatisticsFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultStatisticsShouldBeFound(shouldBeFound);
        defaultStatisticsShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultStatisticsShouldBeFound(String filter) throws Exception {
        restStatisticsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(statistics.getId().intValue())))
            .andExpect(jsonPath("$.[*].statisticsCode").value(hasItem(DEFAULT_STATISTICS_CODE)))
            .andExpect(jsonPath("$.[*].academicYear").value(hasItem(DEFAULT_ACADEMIC_YEAR)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));

        // Check, that the count call also returns 1
        restStatisticsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultStatisticsShouldNotBeFound(String filter) throws Exception {
        restStatisticsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restStatisticsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingStatistics() throws Exception {
        // Get the statistics
        restStatisticsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingStatistics() throws Exception {
        // Initialize the database
        insertedStatistics = statisticsRepository.saveAndFlush(statistics);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the statistics
        Statistics updatedStatistics = statisticsRepository.findById(statistics.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedStatistics are not directly saved in db
        em.detach(updatedStatistics);
        updatedStatistics
            .statisticsCode(UPDATED_STATISTICS_CODE)
            .academicYear(UPDATED_ACADEMIC_YEAR)
            .type(UPDATED_TYPE)
            .notes(UPDATED_NOTES)
            .status(UPDATED_STATUS);
        StatisticsDTO statisticsDTO = statisticsMapper.toDto(updatedStatistics);

        restStatisticsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, statisticsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(statisticsDTO))
            )
            .andExpect(status().isOk());

        // Validate the Statistics in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedStatisticsToMatchAllProperties(updatedStatistics);
    }

    @Test
    @Transactional
    void putNonExistingStatistics() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        statistics.setId(longCount.incrementAndGet());

        // Create the Statistics
        StatisticsDTO statisticsDTO = statisticsMapper.toDto(statistics);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStatisticsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, statisticsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(statisticsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Statistics in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchStatistics() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        statistics.setId(longCount.incrementAndGet());

        // Create the Statistics
        StatisticsDTO statisticsDTO = statisticsMapper.toDto(statistics);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStatisticsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(statisticsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Statistics in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamStatistics() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        statistics.setId(longCount.incrementAndGet());

        // Create the Statistics
        StatisticsDTO statisticsDTO = statisticsMapper.toDto(statistics);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStatisticsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(statisticsDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Statistics in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateStatisticsWithPatch() throws Exception {
        // Initialize the database
        insertedStatistics = statisticsRepository.saveAndFlush(statistics);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the statistics using partial update
        Statistics partialUpdatedStatistics = new Statistics();
        partialUpdatedStatistics.setId(statistics.getId());

        partialUpdatedStatistics.statisticsCode(UPDATED_STATISTICS_CODE).type(UPDATED_TYPE).notes(UPDATED_NOTES);

        restStatisticsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStatistics.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedStatistics))
            )
            .andExpect(status().isOk());

        // Validate the Statistics in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertStatisticsUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedStatistics, statistics),
            getPersistedStatistics(statistics)
        );
    }

    @Test
    @Transactional
    void fullUpdateStatisticsWithPatch() throws Exception {
        // Initialize the database
        insertedStatistics = statisticsRepository.saveAndFlush(statistics);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the statistics using partial update
        Statistics partialUpdatedStatistics = new Statistics();
        partialUpdatedStatistics.setId(statistics.getId());

        partialUpdatedStatistics
            .statisticsCode(UPDATED_STATISTICS_CODE)
            .academicYear(UPDATED_ACADEMIC_YEAR)
            .type(UPDATED_TYPE)
            .notes(UPDATED_NOTES)
            .status(UPDATED_STATUS);

        restStatisticsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStatistics.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedStatistics))
            )
            .andExpect(status().isOk());

        // Validate the Statistics in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertStatisticsUpdatableFieldsEquals(partialUpdatedStatistics, getPersistedStatistics(partialUpdatedStatistics));
    }

    @Test
    @Transactional
    void patchNonExistingStatistics() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        statistics.setId(longCount.incrementAndGet());

        // Create the Statistics
        StatisticsDTO statisticsDTO = statisticsMapper.toDto(statistics);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStatisticsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, statisticsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(statisticsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Statistics in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchStatistics() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        statistics.setId(longCount.incrementAndGet());

        // Create the Statistics
        StatisticsDTO statisticsDTO = statisticsMapper.toDto(statistics);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStatisticsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(statisticsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Statistics in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamStatistics() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        statistics.setId(longCount.incrementAndGet());

        // Create the Statistics
        StatisticsDTO statisticsDTO = statisticsMapper.toDto(statistics);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStatisticsMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(statisticsDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Statistics in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteStatistics() throws Exception {
        // Initialize the database
        insertedStatistics = statisticsRepository.saveAndFlush(statistics);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the statistics
        restStatisticsMockMvc
            .perform(delete(ENTITY_API_URL_ID, statistics.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return statisticsRepository.count();
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

    protected Statistics getPersistedStatistics(Statistics statistics) {
        return statisticsRepository.findById(statistics.getId()).orElseThrow();
    }

    protected void assertPersistedStatisticsToMatchAllProperties(Statistics expectedStatistics) {
        assertStatisticsAllPropertiesEquals(expectedStatistics, getPersistedStatistics(expectedStatistics));
    }

    protected void assertPersistedStatisticsToMatchUpdatableProperties(Statistics expectedStatistics) {
        assertStatisticsAllUpdatablePropertiesEquals(expectedStatistics, getPersistedStatistics(expectedStatistics));
    }
}
