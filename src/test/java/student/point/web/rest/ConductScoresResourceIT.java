package student.point.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static student.point.domain.ConductScoresAsserts.*;
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
import student.point.domain.ConductScores;
import student.point.domain.Student;
import student.point.domain.enumeration.EvaluationConductScores;
import student.point.repository.ConductScoresRepository;
import student.point.service.dto.ConductScoresDTO;
import student.point.service.mapper.ConductScoresMapper;

/**
 * Integration tests for the {@link ConductScoresResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ConductScoresResourceIT {

    private static final String DEFAULT_CONDUCT_SCORES_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CONDUCT_SCORES_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_ACADEMIC_YEAR = "AAAAAAAAAA";
    private static final String UPDATED_ACADEMIC_YEAR = "BBBBBBBBBB";

    private static final Integer DEFAULT_SCORE = 1;
    private static final Integer UPDATED_SCORE = 2;
    private static final Integer SMALLER_SCORE = 1 - 1;

    private static final Integer DEFAULT_CLASSIFICATION = 1;
    private static final Integer UPDATED_CLASSIFICATION = 2;
    private static final Integer SMALLER_CLASSIFICATION = 1 - 1;

    private static final EvaluationConductScores DEFAULT_EVALUATION = EvaluationConductScores.Good;
    private static final EvaluationConductScores UPDATED_EVALUATION = EvaluationConductScores.Fair;

    private static final String ENTITY_API_URL = "/api/conduct-scores";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ConductScoresRepository conductScoresRepository;

    @Autowired
    private ConductScoresMapper conductScoresMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restConductScoresMockMvc;

    private ConductScores conductScores;

    private ConductScores insertedConductScores;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ConductScores createEntity() {
        return new ConductScores()
            .conductScoresCode(DEFAULT_CONDUCT_SCORES_CODE)
            .academicYear(DEFAULT_ACADEMIC_YEAR)
            .score(DEFAULT_SCORE)
            .classification(DEFAULT_CLASSIFICATION)
            .evaluation(DEFAULT_EVALUATION);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ConductScores createUpdatedEntity() {
        return new ConductScores()
            .conductScoresCode(UPDATED_CONDUCT_SCORES_CODE)
            .academicYear(UPDATED_ACADEMIC_YEAR)
            .score(UPDATED_SCORE)
            .classification(UPDATED_CLASSIFICATION)
            .evaluation(UPDATED_EVALUATION);
    }

    @BeforeEach
    public void initTest() {
        conductScores = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedConductScores != null) {
            conductScoresRepository.delete(insertedConductScores);
            insertedConductScores = null;
        }
    }

    @Test
    @Transactional
    void createConductScores() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the ConductScores
        ConductScoresDTO conductScoresDTO = conductScoresMapper.toDto(conductScores);
        var returnedConductScoresDTO = om.readValue(
            restConductScoresMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(conductScoresDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            ConductScoresDTO.class
        );

        // Validate the ConductScores in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedConductScores = conductScoresMapper.toEntity(returnedConductScoresDTO);
        assertConductScoresUpdatableFieldsEquals(returnedConductScores, getPersistedConductScores(returnedConductScores));

        insertedConductScores = returnedConductScores;
    }

    @Test
    @Transactional
    void createConductScoresWithExistingId() throws Exception {
        // Create the ConductScores with an existing ID
        conductScores.setId(1L);
        ConductScoresDTO conductScoresDTO = conductScoresMapper.toDto(conductScores);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restConductScoresMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(conductScoresDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ConductScores in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllConductScores() throws Exception {
        // Initialize the database
        insertedConductScores = conductScoresRepository.saveAndFlush(conductScores);

        // Get all the conductScoresList
        restConductScoresMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(conductScores.getId().intValue())))
            .andExpect(jsonPath("$.[*].conductScoresCode").value(hasItem(DEFAULT_CONDUCT_SCORES_CODE)))
            .andExpect(jsonPath("$.[*].academicYear").value(hasItem(DEFAULT_ACADEMIC_YEAR)))
            .andExpect(jsonPath("$.[*].score").value(hasItem(DEFAULT_SCORE)))
            .andExpect(jsonPath("$.[*].classification").value(hasItem(DEFAULT_CLASSIFICATION)))
            .andExpect(jsonPath("$.[*].evaluation").value(hasItem(DEFAULT_EVALUATION.toString())));
    }

    @Test
    @Transactional
    void getConductScores() throws Exception {
        // Initialize the database
        insertedConductScores = conductScoresRepository.saveAndFlush(conductScores);

        // Get the conductScores
        restConductScoresMockMvc
            .perform(get(ENTITY_API_URL_ID, conductScores.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(conductScores.getId().intValue()))
            .andExpect(jsonPath("$.conductScoresCode").value(DEFAULT_CONDUCT_SCORES_CODE))
            .andExpect(jsonPath("$.academicYear").value(DEFAULT_ACADEMIC_YEAR))
            .andExpect(jsonPath("$.score").value(DEFAULT_SCORE))
            .andExpect(jsonPath("$.classification").value(DEFAULT_CLASSIFICATION))
            .andExpect(jsonPath("$.evaluation").value(DEFAULT_EVALUATION.toString()));
    }

    @Test
    @Transactional
    void getConductScoresByIdFiltering() throws Exception {
        // Initialize the database
        insertedConductScores = conductScoresRepository.saveAndFlush(conductScores);

        Long id = conductScores.getId();

        defaultConductScoresFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultConductScoresFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultConductScoresFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllConductScoresByConductScoresCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedConductScores = conductScoresRepository.saveAndFlush(conductScores);

        // Get all the conductScoresList where conductScoresCode equals to
        defaultConductScoresFiltering(
            "conductScoresCode.equals=" + DEFAULT_CONDUCT_SCORES_CODE,
            "conductScoresCode.equals=" + UPDATED_CONDUCT_SCORES_CODE
        );
    }

    @Test
    @Transactional
    void getAllConductScoresByConductScoresCodeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedConductScores = conductScoresRepository.saveAndFlush(conductScores);

        // Get all the conductScoresList where conductScoresCode in
        defaultConductScoresFiltering(
            "conductScoresCode.in=" + DEFAULT_CONDUCT_SCORES_CODE + "," + UPDATED_CONDUCT_SCORES_CODE,
            "conductScoresCode.in=" + UPDATED_CONDUCT_SCORES_CODE
        );
    }

    @Test
    @Transactional
    void getAllConductScoresByConductScoresCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedConductScores = conductScoresRepository.saveAndFlush(conductScores);

        // Get all the conductScoresList where conductScoresCode is not null
        defaultConductScoresFiltering("conductScoresCode.specified=true", "conductScoresCode.specified=false");
    }

    @Test
    @Transactional
    void getAllConductScoresByConductScoresCodeContainsSomething() throws Exception {
        // Initialize the database
        insertedConductScores = conductScoresRepository.saveAndFlush(conductScores);

        // Get all the conductScoresList where conductScoresCode contains
        defaultConductScoresFiltering(
            "conductScoresCode.contains=" + DEFAULT_CONDUCT_SCORES_CODE,
            "conductScoresCode.contains=" + UPDATED_CONDUCT_SCORES_CODE
        );
    }

    @Test
    @Transactional
    void getAllConductScoresByConductScoresCodeNotContainsSomething() throws Exception {
        // Initialize the database
        insertedConductScores = conductScoresRepository.saveAndFlush(conductScores);

        // Get all the conductScoresList where conductScoresCode does not contain
        defaultConductScoresFiltering(
            "conductScoresCode.doesNotContain=" + UPDATED_CONDUCT_SCORES_CODE,
            "conductScoresCode.doesNotContain=" + DEFAULT_CONDUCT_SCORES_CODE
        );
    }

    @Test
    @Transactional
    void getAllConductScoresByAcademicYearIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedConductScores = conductScoresRepository.saveAndFlush(conductScores);

        // Get all the conductScoresList where academicYear equals to
        defaultConductScoresFiltering("academicYear.equals=" + DEFAULT_ACADEMIC_YEAR, "academicYear.equals=" + UPDATED_ACADEMIC_YEAR);
    }

    @Test
    @Transactional
    void getAllConductScoresByAcademicYearIsInShouldWork() throws Exception {
        // Initialize the database
        insertedConductScores = conductScoresRepository.saveAndFlush(conductScores);

        // Get all the conductScoresList where academicYear in
        defaultConductScoresFiltering(
            "academicYear.in=" + DEFAULT_ACADEMIC_YEAR + "," + UPDATED_ACADEMIC_YEAR,
            "academicYear.in=" + UPDATED_ACADEMIC_YEAR
        );
    }

    @Test
    @Transactional
    void getAllConductScoresByAcademicYearIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedConductScores = conductScoresRepository.saveAndFlush(conductScores);

        // Get all the conductScoresList where academicYear is not null
        defaultConductScoresFiltering("academicYear.specified=true", "academicYear.specified=false");
    }

    @Test
    @Transactional
    void getAllConductScoresByAcademicYearContainsSomething() throws Exception {
        // Initialize the database
        insertedConductScores = conductScoresRepository.saveAndFlush(conductScores);

        // Get all the conductScoresList where academicYear contains
        defaultConductScoresFiltering("academicYear.contains=" + DEFAULT_ACADEMIC_YEAR, "academicYear.contains=" + UPDATED_ACADEMIC_YEAR);
    }

    @Test
    @Transactional
    void getAllConductScoresByAcademicYearNotContainsSomething() throws Exception {
        // Initialize the database
        insertedConductScores = conductScoresRepository.saveAndFlush(conductScores);

        // Get all the conductScoresList where academicYear does not contain
        defaultConductScoresFiltering(
            "academicYear.doesNotContain=" + UPDATED_ACADEMIC_YEAR,
            "academicYear.doesNotContain=" + DEFAULT_ACADEMIC_YEAR
        );
    }

    @Test
    @Transactional
    void getAllConductScoresByScoreIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedConductScores = conductScoresRepository.saveAndFlush(conductScores);

        // Get all the conductScoresList where score equals to
        defaultConductScoresFiltering("score.equals=" + DEFAULT_SCORE, "score.equals=" + UPDATED_SCORE);
    }

    @Test
    @Transactional
    void getAllConductScoresByScoreIsInShouldWork() throws Exception {
        // Initialize the database
        insertedConductScores = conductScoresRepository.saveAndFlush(conductScores);

        // Get all the conductScoresList where score in
        defaultConductScoresFiltering("score.in=" + DEFAULT_SCORE + "," + UPDATED_SCORE, "score.in=" + UPDATED_SCORE);
    }

    @Test
    @Transactional
    void getAllConductScoresByScoreIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedConductScores = conductScoresRepository.saveAndFlush(conductScores);

        // Get all the conductScoresList where score is not null
        defaultConductScoresFiltering("score.specified=true", "score.specified=false");
    }

    @Test
    @Transactional
    void getAllConductScoresByScoreIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedConductScores = conductScoresRepository.saveAndFlush(conductScores);

        // Get all the conductScoresList where score is greater than or equal to
        defaultConductScoresFiltering("score.greaterThanOrEqual=" + DEFAULT_SCORE, "score.greaterThanOrEqual=" + UPDATED_SCORE);
    }

    @Test
    @Transactional
    void getAllConductScoresByScoreIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedConductScores = conductScoresRepository.saveAndFlush(conductScores);

        // Get all the conductScoresList where score is less than or equal to
        defaultConductScoresFiltering("score.lessThanOrEqual=" + DEFAULT_SCORE, "score.lessThanOrEqual=" + SMALLER_SCORE);
    }

    @Test
    @Transactional
    void getAllConductScoresByScoreIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedConductScores = conductScoresRepository.saveAndFlush(conductScores);

        // Get all the conductScoresList where score is less than
        defaultConductScoresFiltering("score.lessThan=" + UPDATED_SCORE, "score.lessThan=" + DEFAULT_SCORE);
    }

    @Test
    @Transactional
    void getAllConductScoresByScoreIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedConductScores = conductScoresRepository.saveAndFlush(conductScores);

        // Get all the conductScoresList where score is greater than
        defaultConductScoresFiltering("score.greaterThan=" + SMALLER_SCORE, "score.greaterThan=" + DEFAULT_SCORE);
    }

    @Test
    @Transactional
    void getAllConductScoresByClassificationIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedConductScores = conductScoresRepository.saveAndFlush(conductScores);

        // Get all the conductScoresList where classification equals to
        defaultConductScoresFiltering("classification.equals=" + DEFAULT_CLASSIFICATION, "classification.equals=" + UPDATED_CLASSIFICATION);
    }

    @Test
    @Transactional
    void getAllConductScoresByClassificationIsInShouldWork() throws Exception {
        // Initialize the database
        insertedConductScores = conductScoresRepository.saveAndFlush(conductScores);

        // Get all the conductScoresList where classification in
        defaultConductScoresFiltering(
            "classification.in=" + DEFAULT_CLASSIFICATION + "," + UPDATED_CLASSIFICATION,
            "classification.in=" + UPDATED_CLASSIFICATION
        );
    }

    @Test
    @Transactional
    void getAllConductScoresByClassificationIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedConductScores = conductScoresRepository.saveAndFlush(conductScores);

        // Get all the conductScoresList where classification is not null
        defaultConductScoresFiltering("classification.specified=true", "classification.specified=false");
    }

    @Test
    @Transactional
    void getAllConductScoresByClassificationIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedConductScores = conductScoresRepository.saveAndFlush(conductScores);

        // Get all the conductScoresList where classification is greater than or equal to
        defaultConductScoresFiltering(
            "classification.greaterThanOrEqual=" + DEFAULT_CLASSIFICATION,
            "classification.greaterThanOrEqual=" + UPDATED_CLASSIFICATION
        );
    }

    @Test
    @Transactional
    void getAllConductScoresByClassificationIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedConductScores = conductScoresRepository.saveAndFlush(conductScores);

        // Get all the conductScoresList where classification is less than or equal to
        defaultConductScoresFiltering(
            "classification.lessThanOrEqual=" + DEFAULT_CLASSIFICATION,
            "classification.lessThanOrEqual=" + SMALLER_CLASSIFICATION
        );
    }

    @Test
    @Transactional
    void getAllConductScoresByClassificationIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedConductScores = conductScoresRepository.saveAndFlush(conductScores);

        // Get all the conductScoresList where classification is less than
        defaultConductScoresFiltering(
            "classification.lessThan=" + UPDATED_CLASSIFICATION,
            "classification.lessThan=" + DEFAULT_CLASSIFICATION
        );
    }

    @Test
    @Transactional
    void getAllConductScoresByClassificationIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedConductScores = conductScoresRepository.saveAndFlush(conductScores);

        // Get all the conductScoresList where classification is greater than
        defaultConductScoresFiltering(
            "classification.greaterThan=" + SMALLER_CLASSIFICATION,
            "classification.greaterThan=" + DEFAULT_CLASSIFICATION
        );
    }

    @Test
    @Transactional
    void getAllConductScoresByEvaluationIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedConductScores = conductScoresRepository.saveAndFlush(conductScores);

        // Get all the conductScoresList where evaluation equals to
        defaultConductScoresFiltering("evaluation.equals=" + DEFAULT_EVALUATION, "evaluation.equals=" + UPDATED_EVALUATION);
    }

    @Test
    @Transactional
    void getAllConductScoresByEvaluationIsInShouldWork() throws Exception {
        // Initialize the database
        insertedConductScores = conductScoresRepository.saveAndFlush(conductScores);

        // Get all the conductScoresList where evaluation in
        defaultConductScoresFiltering(
            "evaluation.in=" + DEFAULT_EVALUATION + "," + UPDATED_EVALUATION,
            "evaluation.in=" + UPDATED_EVALUATION
        );
    }

    @Test
    @Transactional
    void getAllConductScoresByEvaluationIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedConductScores = conductScoresRepository.saveAndFlush(conductScores);

        // Get all the conductScoresList where evaluation is not null
        defaultConductScoresFiltering("evaluation.specified=true", "evaluation.specified=false");
    }

    @Test
    @Transactional
    void getAllConductScoresByStudentIsEqualToSomething() throws Exception {
        Student student;
        if (TestUtil.findAll(em, Student.class).isEmpty()) {
            conductScoresRepository.saveAndFlush(conductScores);
            student = StudentResourceIT.createEntity();
        } else {
            student = TestUtil.findAll(em, Student.class).get(0);
        }
        em.persist(student);
        em.flush();
        conductScores.setStudent(student);
        conductScoresRepository.saveAndFlush(conductScores);
        Long studentId = student.getId();
        // Get all the conductScoresList where student equals to studentId
        defaultConductScoresShouldBeFound("studentId.equals=" + studentId);

        // Get all the conductScoresList where student equals to (studentId + 1)
        defaultConductScoresShouldNotBeFound("studentId.equals=" + (studentId + 1));
    }

    private void defaultConductScoresFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultConductScoresShouldBeFound(shouldBeFound);
        defaultConductScoresShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultConductScoresShouldBeFound(String filter) throws Exception {
        restConductScoresMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(conductScores.getId().intValue())))
            .andExpect(jsonPath("$.[*].conductScoresCode").value(hasItem(DEFAULT_CONDUCT_SCORES_CODE)))
            .andExpect(jsonPath("$.[*].academicYear").value(hasItem(DEFAULT_ACADEMIC_YEAR)))
            .andExpect(jsonPath("$.[*].score").value(hasItem(DEFAULT_SCORE)))
            .andExpect(jsonPath("$.[*].classification").value(hasItem(DEFAULT_CLASSIFICATION)))
            .andExpect(jsonPath("$.[*].evaluation").value(hasItem(DEFAULT_EVALUATION.toString())));

        // Check, that the count call also returns 1
        restConductScoresMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultConductScoresShouldNotBeFound(String filter) throws Exception {
        restConductScoresMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restConductScoresMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingConductScores() throws Exception {
        // Get the conductScores
        restConductScoresMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingConductScores() throws Exception {
        // Initialize the database
        insertedConductScores = conductScoresRepository.saveAndFlush(conductScores);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the conductScores
        ConductScores updatedConductScores = conductScoresRepository.findById(conductScores.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedConductScores are not directly saved in db
        em.detach(updatedConductScores);
        updatedConductScores
            .conductScoresCode(UPDATED_CONDUCT_SCORES_CODE)
            .academicYear(UPDATED_ACADEMIC_YEAR)
            .score(UPDATED_SCORE)
            .classification(UPDATED_CLASSIFICATION)
            .evaluation(UPDATED_EVALUATION);
        ConductScoresDTO conductScoresDTO = conductScoresMapper.toDto(updatedConductScores);

        restConductScoresMockMvc
            .perform(
                put(ENTITY_API_URL_ID, conductScoresDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(conductScoresDTO))
            )
            .andExpect(status().isOk());

        // Validate the ConductScores in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedConductScoresToMatchAllProperties(updatedConductScores);
    }

    @Test
    @Transactional
    void putNonExistingConductScores() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        conductScores.setId(longCount.incrementAndGet());

        // Create the ConductScores
        ConductScoresDTO conductScoresDTO = conductScoresMapper.toDto(conductScores);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConductScoresMockMvc
            .perform(
                put(ENTITY_API_URL_ID, conductScoresDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(conductScoresDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ConductScores in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchConductScores() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        conductScores.setId(longCount.incrementAndGet());

        // Create the ConductScores
        ConductScoresDTO conductScoresDTO = conductScoresMapper.toDto(conductScores);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConductScoresMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(conductScoresDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ConductScores in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamConductScores() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        conductScores.setId(longCount.incrementAndGet());

        // Create the ConductScores
        ConductScoresDTO conductScoresDTO = conductScoresMapper.toDto(conductScores);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConductScoresMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(conductScoresDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ConductScores in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateConductScoresWithPatch() throws Exception {
        // Initialize the database
        insertedConductScores = conductScoresRepository.saveAndFlush(conductScores);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the conductScores using partial update
        ConductScores partialUpdatedConductScores = new ConductScores();
        partialUpdatedConductScores.setId(conductScores.getId());

        partialUpdatedConductScores.score(UPDATED_SCORE).classification(UPDATED_CLASSIFICATION).evaluation(UPDATED_EVALUATION);

        restConductScoresMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedConductScores.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedConductScores))
            )
            .andExpect(status().isOk());

        // Validate the ConductScores in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertConductScoresUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedConductScores, conductScores),
            getPersistedConductScores(conductScores)
        );
    }

    @Test
    @Transactional
    void fullUpdateConductScoresWithPatch() throws Exception {
        // Initialize the database
        insertedConductScores = conductScoresRepository.saveAndFlush(conductScores);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the conductScores using partial update
        ConductScores partialUpdatedConductScores = new ConductScores();
        partialUpdatedConductScores.setId(conductScores.getId());

        partialUpdatedConductScores
            .conductScoresCode(UPDATED_CONDUCT_SCORES_CODE)
            .academicYear(UPDATED_ACADEMIC_YEAR)
            .score(UPDATED_SCORE)
            .classification(UPDATED_CLASSIFICATION)
            .evaluation(UPDATED_EVALUATION);

        restConductScoresMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedConductScores.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedConductScores))
            )
            .andExpect(status().isOk());

        // Validate the ConductScores in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertConductScoresUpdatableFieldsEquals(partialUpdatedConductScores, getPersistedConductScores(partialUpdatedConductScores));
    }

    @Test
    @Transactional
    void patchNonExistingConductScores() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        conductScores.setId(longCount.incrementAndGet());

        // Create the ConductScores
        ConductScoresDTO conductScoresDTO = conductScoresMapper.toDto(conductScores);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConductScoresMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, conductScoresDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(conductScoresDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ConductScores in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchConductScores() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        conductScores.setId(longCount.incrementAndGet());

        // Create the ConductScores
        ConductScoresDTO conductScoresDTO = conductScoresMapper.toDto(conductScores);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConductScoresMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(conductScoresDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ConductScores in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamConductScores() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        conductScores.setId(longCount.incrementAndGet());

        // Create the ConductScores
        ConductScoresDTO conductScoresDTO = conductScoresMapper.toDto(conductScores);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConductScoresMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(conductScoresDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ConductScores in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteConductScores() throws Exception {
        // Initialize the database
        insertedConductScores = conductScoresRepository.saveAndFlush(conductScores);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the conductScores
        restConductScoresMockMvc
            .perform(delete(ENTITY_API_URL_ID, conductScores.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return conductScoresRepository.count();
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

    protected ConductScores getPersistedConductScores(ConductScores conductScores) {
        return conductScoresRepository.findById(conductScores.getId()).orElseThrow();
    }

    protected void assertPersistedConductScoresToMatchAllProperties(ConductScores expectedConductScores) {
        assertConductScoresAllPropertiesEquals(expectedConductScores, getPersistedConductScores(expectedConductScores));
    }

    protected void assertPersistedConductScoresToMatchUpdatableProperties(ConductScores expectedConductScores) {
        assertConductScoresAllUpdatablePropertiesEquals(expectedConductScores, getPersistedConductScores(expectedConductScores));
    }
}
