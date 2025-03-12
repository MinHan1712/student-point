package student.point.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static student.point.domain.StatisticsDetailsAsserts.*;
import static student.point.web.rest.TestUtil.createUpdateProxyForBean;
import static student.point.web.rest.TestUtil.sameNumber;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
import student.point.domain.StatisticsDetails;
import student.point.domain.Student;
import student.point.repository.StatisticsDetailsRepository;
import student.point.service.dto.StatisticsDetailsDTO;
import student.point.service.mapper.StatisticsDetailsMapper;

/**
 * Integration tests for the {@link StatisticsDetailsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class StatisticsDetailsResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_TOTAL_SCHOLARSHIP = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL_SCHOLARSHIP = new BigDecimal(2);
    private static final BigDecimal SMALLER_TOTAL_SCHOLARSHIP = new BigDecimal(1 - 1);

    private static final Instant DEFAULT_GRADUATION_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_GRADUATION_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_NOTES = "AAAAAAAAAA";
    private static final String UPDATED_NOTES = "BBBBBBBBBB";

    private static final Boolean DEFAULT_STATUS = false;
    private static final Boolean UPDATED_STATUS = true;

    private static final String ENTITY_API_URL = "/api/statistics-details";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private StatisticsDetailsRepository statisticsDetailsRepository;

    @Autowired
    private StatisticsDetailsMapper statisticsDetailsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStatisticsDetailsMockMvc;

    private StatisticsDetails statisticsDetails;

    private StatisticsDetails insertedStatisticsDetails;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StatisticsDetails createEntity() {
        return new StatisticsDetails()
            .code(DEFAULT_CODE)
            .totalScholarship(DEFAULT_TOTAL_SCHOLARSHIP)
            .graduationDate(DEFAULT_GRADUATION_DATE)
            .notes(DEFAULT_NOTES)
            .status(DEFAULT_STATUS);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StatisticsDetails createUpdatedEntity() {
        return new StatisticsDetails()
            .code(UPDATED_CODE)
            .totalScholarship(UPDATED_TOTAL_SCHOLARSHIP)
            .graduationDate(UPDATED_GRADUATION_DATE)
            .notes(UPDATED_NOTES)
            .status(UPDATED_STATUS);
    }

    @BeforeEach
    public void initTest() {
        statisticsDetails = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedStatisticsDetails != null) {
            statisticsDetailsRepository.delete(insertedStatisticsDetails);
            insertedStatisticsDetails = null;
        }
    }

    @Test
    @Transactional
    void createStatisticsDetails() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the StatisticsDetails
        StatisticsDetailsDTO statisticsDetailsDTO = statisticsDetailsMapper.toDto(statisticsDetails);
        var returnedStatisticsDetailsDTO = om.readValue(
            restStatisticsDetailsMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(statisticsDetailsDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            StatisticsDetailsDTO.class
        );

        // Validate the StatisticsDetails in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedStatisticsDetails = statisticsDetailsMapper.toEntity(returnedStatisticsDetailsDTO);
        assertStatisticsDetailsUpdatableFieldsEquals(returnedStatisticsDetails, getPersistedStatisticsDetails(returnedStatisticsDetails));

        insertedStatisticsDetails = returnedStatisticsDetails;
    }

    @Test
    @Transactional
    void createStatisticsDetailsWithExistingId() throws Exception {
        // Create the StatisticsDetails with an existing ID
        statisticsDetails.setId(1L);
        StatisticsDetailsDTO statisticsDetailsDTO = statisticsDetailsMapper.toDto(statisticsDetails);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restStatisticsDetailsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(statisticsDetailsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the StatisticsDetails in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllStatisticsDetails() throws Exception {
        // Initialize the database
        insertedStatisticsDetails = statisticsDetailsRepository.saveAndFlush(statisticsDetails);

        // Get all the statisticsDetailsList
        restStatisticsDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(statisticsDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].totalScholarship").value(hasItem(sameNumber(DEFAULT_TOTAL_SCHOLARSHIP))))
            .andExpect(jsonPath("$.[*].graduationDate").value(hasItem(DEFAULT_GRADUATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));
    }

    @Test
    @Transactional
    void getStatisticsDetails() throws Exception {
        // Initialize the database
        insertedStatisticsDetails = statisticsDetailsRepository.saveAndFlush(statisticsDetails);

        // Get the statisticsDetails
        restStatisticsDetailsMockMvc
            .perform(get(ENTITY_API_URL_ID, statisticsDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(statisticsDetails.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.totalScholarship").value(sameNumber(DEFAULT_TOTAL_SCHOLARSHIP)))
            .andExpect(jsonPath("$.graduationDate").value(DEFAULT_GRADUATION_DATE.toString()))
            .andExpect(jsonPath("$.notes").value(DEFAULT_NOTES))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS));
    }

    @Test
    @Transactional
    void getStatisticsDetailsByIdFiltering() throws Exception {
        // Initialize the database
        insertedStatisticsDetails = statisticsDetailsRepository.saveAndFlush(statisticsDetails);

        Long id = statisticsDetails.getId();

        defaultStatisticsDetailsFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultStatisticsDetailsFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultStatisticsDetailsFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllStatisticsDetailsByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedStatisticsDetails = statisticsDetailsRepository.saveAndFlush(statisticsDetails);

        // Get all the statisticsDetailsList where code equals to
        defaultStatisticsDetailsFiltering("code.equals=" + DEFAULT_CODE, "code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllStatisticsDetailsByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedStatisticsDetails = statisticsDetailsRepository.saveAndFlush(statisticsDetails);

        // Get all the statisticsDetailsList where code in
        defaultStatisticsDetailsFiltering("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE, "code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllStatisticsDetailsByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedStatisticsDetails = statisticsDetailsRepository.saveAndFlush(statisticsDetails);

        // Get all the statisticsDetailsList where code is not null
        defaultStatisticsDetailsFiltering("code.specified=true", "code.specified=false");
    }

    @Test
    @Transactional
    void getAllStatisticsDetailsByCodeContainsSomething() throws Exception {
        // Initialize the database
        insertedStatisticsDetails = statisticsDetailsRepository.saveAndFlush(statisticsDetails);

        // Get all the statisticsDetailsList where code contains
        defaultStatisticsDetailsFiltering("code.contains=" + DEFAULT_CODE, "code.contains=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllStatisticsDetailsByCodeNotContainsSomething() throws Exception {
        // Initialize the database
        insertedStatisticsDetails = statisticsDetailsRepository.saveAndFlush(statisticsDetails);

        // Get all the statisticsDetailsList where code does not contain
        defaultStatisticsDetailsFiltering("code.doesNotContain=" + UPDATED_CODE, "code.doesNotContain=" + DEFAULT_CODE);
    }

    @Test
    @Transactional
    void getAllStatisticsDetailsByTotalScholarshipIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedStatisticsDetails = statisticsDetailsRepository.saveAndFlush(statisticsDetails);

        // Get all the statisticsDetailsList where totalScholarship equals to
        defaultStatisticsDetailsFiltering(
            "totalScholarship.equals=" + DEFAULT_TOTAL_SCHOLARSHIP,
            "totalScholarship.equals=" + UPDATED_TOTAL_SCHOLARSHIP
        );
    }

    @Test
    @Transactional
    void getAllStatisticsDetailsByTotalScholarshipIsInShouldWork() throws Exception {
        // Initialize the database
        insertedStatisticsDetails = statisticsDetailsRepository.saveAndFlush(statisticsDetails);

        // Get all the statisticsDetailsList where totalScholarship in
        defaultStatisticsDetailsFiltering(
            "totalScholarship.in=" + DEFAULT_TOTAL_SCHOLARSHIP + "," + UPDATED_TOTAL_SCHOLARSHIP,
            "totalScholarship.in=" + UPDATED_TOTAL_SCHOLARSHIP
        );
    }

    @Test
    @Transactional
    void getAllStatisticsDetailsByTotalScholarshipIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedStatisticsDetails = statisticsDetailsRepository.saveAndFlush(statisticsDetails);

        // Get all the statisticsDetailsList where totalScholarship is not null
        defaultStatisticsDetailsFiltering("totalScholarship.specified=true", "totalScholarship.specified=false");
    }

    @Test
    @Transactional
    void getAllStatisticsDetailsByTotalScholarshipIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedStatisticsDetails = statisticsDetailsRepository.saveAndFlush(statisticsDetails);

        // Get all the statisticsDetailsList where totalScholarship is greater than or equal to
        defaultStatisticsDetailsFiltering(
            "totalScholarship.greaterThanOrEqual=" + DEFAULT_TOTAL_SCHOLARSHIP,
            "totalScholarship.greaterThanOrEqual=" + UPDATED_TOTAL_SCHOLARSHIP
        );
    }

    @Test
    @Transactional
    void getAllStatisticsDetailsByTotalScholarshipIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedStatisticsDetails = statisticsDetailsRepository.saveAndFlush(statisticsDetails);

        // Get all the statisticsDetailsList where totalScholarship is less than or equal to
        defaultStatisticsDetailsFiltering(
            "totalScholarship.lessThanOrEqual=" + DEFAULT_TOTAL_SCHOLARSHIP,
            "totalScholarship.lessThanOrEqual=" + SMALLER_TOTAL_SCHOLARSHIP
        );
    }

    @Test
    @Transactional
    void getAllStatisticsDetailsByTotalScholarshipIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedStatisticsDetails = statisticsDetailsRepository.saveAndFlush(statisticsDetails);

        // Get all the statisticsDetailsList where totalScholarship is less than
        defaultStatisticsDetailsFiltering(
            "totalScholarship.lessThan=" + UPDATED_TOTAL_SCHOLARSHIP,
            "totalScholarship.lessThan=" + DEFAULT_TOTAL_SCHOLARSHIP
        );
    }

    @Test
    @Transactional
    void getAllStatisticsDetailsByTotalScholarshipIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedStatisticsDetails = statisticsDetailsRepository.saveAndFlush(statisticsDetails);

        // Get all the statisticsDetailsList where totalScholarship is greater than
        defaultStatisticsDetailsFiltering(
            "totalScholarship.greaterThan=" + SMALLER_TOTAL_SCHOLARSHIP,
            "totalScholarship.greaterThan=" + DEFAULT_TOTAL_SCHOLARSHIP
        );
    }

    @Test
    @Transactional
    void getAllStatisticsDetailsByGraduationDateIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedStatisticsDetails = statisticsDetailsRepository.saveAndFlush(statisticsDetails);

        // Get all the statisticsDetailsList where graduationDate equals to
        defaultStatisticsDetailsFiltering(
            "graduationDate.equals=" + DEFAULT_GRADUATION_DATE,
            "graduationDate.equals=" + UPDATED_GRADUATION_DATE
        );
    }

    @Test
    @Transactional
    void getAllStatisticsDetailsByGraduationDateIsInShouldWork() throws Exception {
        // Initialize the database
        insertedStatisticsDetails = statisticsDetailsRepository.saveAndFlush(statisticsDetails);

        // Get all the statisticsDetailsList where graduationDate in
        defaultStatisticsDetailsFiltering(
            "graduationDate.in=" + DEFAULT_GRADUATION_DATE + "," + UPDATED_GRADUATION_DATE,
            "graduationDate.in=" + UPDATED_GRADUATION_DATE
        );
    }

    @Test
    @Transactional
    void getAllStatisticsDetailsByGraduationDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedStatisticsDetails = statisticsDetailsRepository.saveAndFlush(statisticsDetails);

        // Get all the statisticsDetailsList where graduationDate is not null
        defaultStatisticsDetailsFiltering("graduationDate.specified=true", "graduationDate.specified=false");
    }

    @Test
    @Transactional
    void getAllStatisticsDetailsByNotesIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedStatisticsDetails = statisticsDetailsRepository.saveAndFlush(statisticsDetails);

        // Get all the statisticsDetailsList where notes equals to
        defaultStatisticsDetailsFiltering("notes.equals=" + DEFAULT_NOTES, "notes.equals=" + UPDATED_NOTES);
    }

    @Test
    @Transactional
    void getAllStatisticsDetailsByNotesIsInShouldWork() throws Exception {
        // Initialize the database
        insertedStatisticsDetails = statisticsDetailsRepository.saveAndFlush(statisticsDetails);

        // Get all the statisticsDetailsList where notes in
        defaultStatisticsDetailsFiltering("notes.in=" + DEFAULT_NOTES + "," + UPDATED_NOTES, "notes.in=" + UPDATED_NOTES);
    }

    @Test
    @Transactional
    void getAllStatisticsDetailsByNotesIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedStatisticsDetails = statisticsDetailsRepository.saveAndFlush(statisticsDetails);

        // Get all the statisticsDetailsList where notes is not null
        defaultStatisticsDetailsFiltering("notes.specified=true", "notes.specified=false");
    }

    @Test
    @Transactional
    void getAllStatisticsDetailsByNotesContainsSomething() throws Exception {
        // Initialize the database
        insertedStatisticsDetails = statisticsDetailsRepository.saveAndFlush(statisticsDetails);

        // Get all the statisticsDetailsList where notes contains
        defaultStatisticsDetailsFiltering("notes.contains=" + DEFAULT_NOTES, "notes.contains=" + UPDATED_NOTES);
    }

    @Test
    @Transactional
    void getAllStatisticsDetailsByNotesNotContainsSomething() throws Exception {
        // Initialize the database
        insertedStatisticsDetails = statisticsDetailsRepository.saveAndFlush(statisticsDetails);

        // Get all the statisticsDetailsList where notes does not contain
        defaultStatisticsDetailsFiltering("notes.doesNotContain=" + UPDATED_NOTES, "notes.doesNotContain=" + DEFAULT_NOTES);
    }

    @Test
    @Transactional
    void getAllStatisticsDetailsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedStatisticsDetails = statisticsDetailsRepository.saveAndFlush(statisticsDetails);

        // Get all the statisticsDetailsList where status equals to
        defaultStatisticsDetailsFiltering("status.equals=" + DEFAULT_STATUS, "status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllStatisticsDetailsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        insertedStatisticsDetails = statisticsDetailsRepository.saveAndFlush(statisticsDetails);

        // Get all the statisticsDetailsList where status in
        defaultStatisticsDetailsFiltering("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS, "status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllStatisticsDetailsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedStatisticsDetails = statisticsDetailsRepository.saveAndFlush(statisticsDetails);

        // Get all the statisticsDetailsList where status is not null
        defaultStatisticsDetailsFiltering("status.specified=true", "status.specified=false");
    }

    @Test
    @Transactional
    void getAllStatisticsDetailsByStudentIsEqualToSomething() throws Exception {
        Student student;
        if (TestUtil.findAll(em, Student.class).isEmpty()) {
            statisticsDetailsRepository.saveAndFlush(statisticsDetails);
            student = StudentResourceIT.createEntity();
        } else {
            student = TestUtil.findAll(em, Student.class).get(0);
        }
        em.persist(student);
        em.flush();
        statisticsDetails.setStudent(student);
        statisticsDetailsRepository.saveAndFlush(statisticsDetails);
        Long studentId = student.getId();
        // Get all the statisticsDetailsList where student equals to studentId
        defaultStatisticsDetailsShouldBeFound("studentId.equals=" + studentId);

        // Get all the statisticsDetailsList where student equals to (studentId + 1)
        defaultStatisticsDetailsShouldNotBeFound("studentId.equals=" + (studentId + 1));
    }

    @Test
    @Transactional
    void getAllStatisticsDetailsByStatisticsIsEqualToSomething() throws Exception {
        Statistics statistics;
        if (TestUtil.findAll(em, Statistics.class).isEmpty()) {
            statisticsDetailsRepository.saveAndFlush(statisticsDetails);
            statistics = StatisticsResourceIT.createEntity();
        } else {
            statistics = TestUtil.findAll(em, Statistics.class).get(0);
        }
        em.persist(statistics);
        em.flush();
        statisticsDetails.setStatistics(statistics);
        statisticsDetailsRepository.saveAndFlush(statisticsDetails);
        Long statisticsId = statistics.getId();
        // Get all the statisticsDetailsList where statistics equals to statisticsId
        defaultStatisticsDetailsShouldBeFound("statisticsId.equals=" + statisticsId);

        // Get all the statisticsDetailsList where statistics equals to (statisticsId + 1)
        defaultStatisticsDetailsShouldNotBeFound("statisticsId.equals=" + (statisticsId + 1));
    }

    private void defaultStatisticsDetailsFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultStatisticsDetailsShouldBeFound(shouldBeFound);
        defaultStatisticsDetailsShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultStatisticsDetailsShouldBeFound(String filter) throws Exception {
        restStatisticsDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(statisticsDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].totalScholarship").value(hasItem(sameNumber(DEFAULT_TOTAL_SCHOLARSHIP))))
            .andExpect(jsonPath("$.[*].graduationDate").value(hasItem(DEFAULT_GRADUATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));

        // Check, that the count call also returns 1
        restStatisticsDetailsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultStatisticsDetailsShouldNotBeFound(String filter) throws Exception {
        restStatisticsDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restStatisticsDetailsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingStatisticsDetails() throws Exception {
        // Get the statisticsDetails
        restStatisticsDetailsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingStatisticsDetails() throws Exception {
        // Initialize the database
        insertedStatisticsDetails = statisticsDetailsRepository.saveAndFlush(statisticsDetails);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the statisticsDetails
        StatisticsDetails updatedStatisticsDetails = statisticsDetailsRepository.findById(statisticsDetails.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedStatisticsDetails are not directly saved in db
        em.detach(updatedStatisticsDetails);
        updatedStatisticsDetails
            .code(UPDATED_CODE)
            .totalScholarship(UPDATED_TOTAL_SCHOLARSHIP)
            .graduationDate(UPDATED_GRADUATION_DATE)
            .notes(UPDATED_NOTES)
            .status(UPDATED_STATUS);
        StatisticsDetailsDTO statisticsDetailsDTO = statisticsDetailsMapper.toDto(updatedStatisticsDetails);

        restStatisticsDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, statisticsDetailsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(statisticsDetailsDTO))
            )
            .andExpect(status().isOk());

        // Validate the StatisticsDetails in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedStatisticsDetailsToMatchAllProperties(updatedStatisticsDetails);
    }

    @Test
    @Transactional
    void putNonExistingStatisticsDetails() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        statisticsDetails.setId(longCount.incrementAndGet());

        // Create the StatisticsDetails
        StatisticsDetailsDTO statisticsDetailsDTO = statisticsDetailsMapper.toDto(statisticsDetails);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStatisticsDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, statisticsDetailsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(statisticsDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the StatisticsDetails in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchStatisticsDetails() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        statisticsDetails.setId(longCount.incrementAndGet());

        // Create the StatisticsDetails
        StatisticsDetailsDTO statisticsDetailsDTO = statisticsDetailsMapper.toDto(statisticsDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStatisticsDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(statisticsDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the StatisticsDetails in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamStatisticsDetails() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        statisticsDetails.setId(longCount.incrementAndGet());

        // Create the StatisticsDetails
        StatisticsDetailsDTO statisticsDetailsDTO = statisticsDetailsMapper.toDto(statisticsDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStatisticsDetailsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(statisticsDetailsDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the StatisticsDetails in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateStatisticsDetailsWithPatch() throws Exception {
        // Initialize the database
        insertedStatisticsDetails = statisticsDetailsRepository.saveAndFlush(statisticsDetails);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the statisticsDetails using partial update
        StatisticsDetails partialUpdatedStatisticsDetails = new StatisticsDetails();
        partialUpdatedStatisticsDetails.setId(statisticsDetails.getId());

        partialUpdatedStatisticsDetails
            .code(UPDATED_CODE)
            .totalScholarship(UPDATED_TOTAL_SCHOLARSHIP)
            .notes(UPDATED_NOTES)
            .status(UPDATED_STATUS);

        restStatisticsDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStatisticsDetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedStatisticsDetails))
            )
            .andExpect(status().isOk());

        // Validate the StatisticsDetails in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertStatisticsDetailsUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedStatisticsDetails, statisticsDetails),
            getPersistedStatisticsDetails(statisticsDetails)
        );
    }

    @Test
    @Transactional
    void fullUpdateStatisticsDetailsWithPatch() throws Exception {
        // Initialize the database
        insertedStatisticsDetails = statisticsDetailsRepository.saveAndFlush(statisticsDetails);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the statisticsDetails using partial update
        StatisticsDetails partialUpdatedStatisticsDetails = new StatisticsDetails();
        partialUpdatedStatisticsDetails.setId(statisticsDetails.getId());

        partialUpdatedStatisticsDetails
            .code(UPDATED_CODE)
            .totalScholarship(UPDATED_TOTAL_SCHOLARSHIP)
            .graduationDate(UPDATED_GRADUATION_DATE)
            .notes(UPDATED_NOTES)
            .status(UPDATED_STATUS);

        restStatisticsDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStatisticsDetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedStatisticsDetails))
            )
            .andExpect(status().isOk());

        // Validate the StatisticsDetails in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertStatisticsDetailsUpdatableFieldsEquals(
            partialUpdatedStatisticsDetails,
            getPersistedStatisticsDetails(partialUpdatedStatisticsDetails)
        );
    }

    @Test
    @Transactional
    void patchNonExistingStatisticsDetails() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        statisticsDetails.setId(longCount.incrementAndGet());

        // Create the StatisticsDetails
        StatisticsDetailsDTO statisticsDetailsDTO = statisticsDetailsMapper.toDto(statisticsDetails);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStatisticsDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, statisticsDetailsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(statisticsDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the StatisticsDetails in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchStatisticsDetails() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        statisticsDetails.setId(longCount.incrementAndGet());

        // Create the StatisticsDetails
        StatisticsDetailsDTO statisticsDetailsDTO = statisticsDetailsMapper.toDto(statisticsDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStatisticsDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(statisticsDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the StatisticsDetails in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamStatisticsDetails() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        statisticsDetails.setId(longCount.incrementAndGet());

        // Create the StatisticsDetails
        StatisticsDetailsDTO statisticsDetailsDTO = statisticsDetailsMapper.toDto(statisticsDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStatisticsDetailsMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(statisticsDetailsDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the StatisticsDetails in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteStatisticsDetails() throws Exception {
        // Initialize the database
        insertedStatisticsDetails = statisticsDetailsRepository.saveAndFlush(statisticsDetails);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the statisticsDetails
        restStatisticsDetailsMockMvc
            .perform(delete(ENTITY_API_URL_ID, statisticsDetails.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return statisticsDetailsRepository.count();
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

    protected StatisticsDetails getPersistedStatisticsDetails(StatisticsDetails statisticsDetails) {
        return statisticsDetailsRepository.findById(statisticsDetails.getId()).orElseThrow();
    }

    protected void assertPersistedStatisticsDetailsToMatchAllProperties(StatisticsDetails expectedStatisticsDetails) {
        assertStatisticsDetailsAllPropertiesEquals(expectedStatisticsDetails, getPersistedStatisticsDetails(expectedStatisticsDetails));
    }

    protected void assertPersistedStatisticsDetailsToMatchUpdatableProperties(StatisticsDetails expectedStatisticsDetails) {
        assertStatisticsDetailsAllUpdatablePropertiesEquals(
            expectedStatisticsDetails,
            getPersistedStatisticsDetails(expectedStatisticsDetails)
        );
    }
}
