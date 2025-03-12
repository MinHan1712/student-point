package student.point.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static student.point.domain.GradesAsserts.*;
import static student.point.web.rest.TestUtil.createUpdateProxyForBean;
import static student.point.web.rest.TestUtil.sameNumber;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.math.BigDecimal;
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
import student.point.domain.Classes;
import student.point.domain.Grades;
import student.point.domain.Student;
import student.point.domain.enumeration.EvaluationScores;
import student.point.domain.enumeration.LetterGrade;
import student.point.repository.GradesRepository;
import student.point.service.dto.GradesDTO;
import student.point.service.mapper.GradesMapper;

/**
 * Integration tests for the {@link GradesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class GradesResourceIT {

    private static final String DEFAULT_GRADES_CODE = "AAAAAAAAAA";
    private static final String UPDATED_GRADES_CODE = "BBBBBBBBBB";

    private static final Integer DEFAULT_CREDIT = 1;
    private static final Integer UPDATED_CREDIT = 2;
    private static final Integer SMALLER_CREDIT = 1 - 1;

    private static final Integer DEFAULT_STUDY_ATTEMPT = 1;
    private static final Integer UPDATED_STUDY_ATTEMPT = 2;
    private static final Integer SMALLER_STUDY_ATTEMPT = 1 - 1;

    private static final Integer DEFAULT_EXAM_ATTEMPT = 1;
    private static final Integer UPDATED_EXAM_ATTEMPT = 2;
    private static final Integer SMALLER_EXAM_ATTEMPT = 1 - 1;

    private static final BigDecimal DEFAULT_PROCESS_SCORE = new BigDecimal(1);
    private static final BigDecimal UPDATED_PROCESS_SCORE = new BigDecimal(2);
    private static final BigDecimal SMALLER_PROCESS_SCORE = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_EXAM_SCORE = new BigDecimal(1);
    private static final BigDecimal UPDATED_EXAM_SCORE = new BigDecimal(2);
    private static final BigDecimal SMALLER_EXAM_SCORE = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_SCORE_10 = new BigDecimal(1);
    private static final BigDecimal UPDATED_SCORE_10 = new BigDecimal(2);
    private static final BigDecimal SMALLER_SCORE_10 = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_SCORE_4 = new BigDecimal(1);
    private static final BigDecimal UPDATED_SCORE_4 = new BigDecimal(2);
    private static final BigDecimal SMALLER_SCORE_4 = new BigDecimal(1 - 1);

    private static final LetterGrade DEFAULT_LETTER_GRADE = LetterGrade.A;
    private static final LetterGrade UPDATED_LETTER_GRADE = LetterGrade.APlus;

    private static final EvaluationScores DEFAULT_EVALUATION = EvaluationScores.Pass;
    private static final EvaluationScores UPDATED_EVALUATION = EvaluationScores.Retake;

    private static final String DEFAULT_NOTES = "AAAAAAAAAA";
    private static final String UPDATED_NOTES = "BBBBBBBBBB";

    private static final Boolean DEFAULT_STATUS = false;
    private static final Boolean UPDATED_STATUS = true;

    private static final String ENTITY_API_URL = "/api/grades";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private GradesRepository gradesRepository;

    @Autowired
    private GradesMapper gradesMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGradesMockMvc;

    private Grades grades;

    private Grades insertedGrades;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Grades createEntity() {
        return new Grades()
            .gradesCode(DEFAULT_GRADES_CODE)
            .credit(DEFAULT_CREDIT)
            .studyAttempt(DEFAULT_STUDY_ATTEMPT)
            .examAttempt(DEFAULT_EXAM_ATTEMPT)
            .processScore(DEFAULT_PROCESS_SCORE)
            .examScore(DEFAULT_EXAM_SCORE)
            .score10(DEFAULT_SCORE_10)
            .score4(DEFAULT_SCORE_4)
            .letterGrade(DEFAULT_LETTER_GRADE)
            .evaluation(DEFAULT_EVALUATION)
            .notes(DEFAULT_NOTES)
            .status(DEFAULT_STATUS);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Grades createUpdatedEntity() {
        return new Grades()
            .gradesCode(UPDATED_GRADES_CODE)
            .credit(UPDATED_CREDIT)
            .studyAttempt(UPDATED_STUDY_ATTEMPT)
            .examAttempt(UPDATED_EXAM_ATTEMPT)
            .processScore(UPDATED_PROCESS_SCORE)
            .examScore(UPDATED_EXAM_SCORE)
            .score10(UPDATED_SCORE_10)
            .score4(UPDATED_SCORE_4)
            .letterGrade(UPDATED_LETTER_GRADE)
            .evaluation(UPDATED_EVALUATION)
            .notes(UPDATED_NOTES)
            .status(UPDATED_STATUS);
    }

    @BeforeEach
    public void initTest() {
        grades = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedGrades != null) {
            gradesRepository.delete(insertedGrades);
            insertedGrades = null;
        }
    }

    @Test
    @Transactional
    void createGrades() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Grades
        GradesDTO gradesDTO = gradesMapper.toDto(grades);
        var returnedGradesDTO = om.readValue(
            restGradesMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(gradesDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            GradesDTO.class
        );

        // Validate the Grades in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedGrades = gradesMapper.toEntity(returnedGradesDTO);
        assertGradesUpdatableFieldsEquals(returnedGrades, getPersistedGrades(returnedGrades));

        insertedGrades = returnedGrades;
    }

    @Test
    @Transactional
    void createGradesWithExistingId() throws Exception {
        // Create the Grades with an existing ID
        grades.setId(1L);
        GradesDTO gradesDTO = gradesMapper.toDto(grades);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restGradesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(gradesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Grades in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllGrades() throws Exception {
        // Initialize the database
        insertedGrades = gradesRepository.saveAndFlush(grades);

        // Get all the gradesList
        restGradesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(grades.getId().intValue())))
            .andExpect(jsonPath("$.[*].gradesCode").value(hasItem(DEFAULT_GRADES_CODE)))
            .andExpect(jsonPath("$.[*].credit").value(hasItem(DEFAULT_CREDIT)))
            .andExpect(jsonPath("$.[*].studyAttempt").value(hasItem(DEFAULT_STUDY_ATTEMPT)))
            .andExpect(jsonPath("$.[*].examAttempt").value(hasItem(DEFAULT_EXAM_ATTEMPT)))
            .andExpect(jsonPath("$.[*].processScore").value(hasItem(sameNumber(DEFAULT_PROCESS_SCORE))))
            .andExpect(jsonPath("$.[*].examScore").value(hasItem(sameNumber(DEFAULT_EXAM_SCORE))))
            .andExpect(jsonPath("$.[*].score10").value(hasItem(sameNumber(DEFAULT_SCORE_10))))
            .andExpect(jsonPath("$.[*].score4").value(hasItem(sameNumber(DEFAULT_SCORE_4))))
            .andExpect(jsonPath("$.[*].letterGrade").value(hasItem(DEFAULT_LETTER_GRADE.toString())))
            .andExpect(jsonPath("$.[*].evaluation").value(hasItem(DEFAULT_EVALUATION.toString())))
            .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));
    }

    @Test
    @Transactional
    void getGrades() throws Exception {
        // Initialize the database
        insertedGrades = gradesRepository.saveAndFlush(grades);

        // Get the grades
        restGradesMockMvc
            .perform(get(ENTITY_API_URL_ID, grades.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(grades.getId().intValue()))
            .andExpect(jsonPath("$.gradesCode").value(DEFAULT_GRADES_CODE))
            .andExpect(jsonPath("$.credit").value(DEFAULT_CREDIT))
            .andExpect(jsonPath("$.studyAttempt").value(DEFAULT_STUDY_ATTEMPT))
            .andExpect(jsonPath("$.examAttempt").value(DEFAULT_EXAM_ATTEMPT))
            .andExpect(jsonPath("$.processScore").value(sameNumber(DEFAULT_PROCESS_SCORE)))
            .andExpect(jsonPath("$.examScore").value(sameNumber(DEFAULT_EXAM_SCORE)))
            .andExpect(jsonPath("$.score10").value(sameNumber(DEFAULT_SCORE_10)))
            .andExpect(jsonPath("$.score4").value(sameNumber(DEFAULT_SCORE_4)))
            .andExpect(jsonPath("$.letterGrade").value(DEFAULT_LETTER_GRADE.toString()))
            .andExpect(jsonPath("$.evaluation").value(DEFAULT_EVALUATION.toString()))
            .andExpect(jsonPath("$.notes").value(DEFAULT_NOTES))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS));
    }

    @Test
    @Transactional
    void getGradesByIdFiltering() throws Exception {
        // Initialize the database
        insertedGrades = gradesRepository.saveAndFlush(grades);

        Long id = grades.getId();

        defaultGradesFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultGradesFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultGradesFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllGradesByGradesCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedGrades = gradesRepository.saveAndFlush(grades);

        // Get all the gradesList where gradesCode equals to
        defaultGradesFiltering("gradesCode.equals=" + DEFAULT_GRADES_CODE, "gradesCode.equals=" + UPDATED_GRADES_CODE);
    }

    @Test
    @Transactional
    void getAllGradesByGradesCodeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedGrades = gradesRepository.saveAndFlush(grades);

        // Get all the gradesList where gradesCode in
        defaultGradesFiltering("gradesCode.in=" + DEFAULT_GRADES_CODE + "," + UPDATED_GRADES_CODE, "gradesCode.in=" + UPDATED_GRADES_CODE);
    }

    @Test
    @Transactional
    void getAllGradesByGradesCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedGrades = gradesRepository.saveAndFlush(grades);

        // Get all the gradesList where gradesCode is not null
        defaultGradesFiltering("gradesCode.specified=true", "gradesCode.specified=false");
    }

    @Test
    @Transactional
    void getAllGradesByGradesCodeContainsSomething() throws Exception {
        // Initialize the database
        insertedGrades = gradesRepository.saveAndFlush(grades);

        // Get all the gradesList where gradesCode contains
        defaultGradesFiltering("gradesCode.contains=" + DEFAULT_GRADES_CODE, "gradesCode.contains=" + UPDATED_GRADES_CODE);
    }

    @Test
    @Transactional
    void getAllGradesByGradesCodeNotContainsSomething() throws Exception {
        // Initialize the database
        insertedGrades = gradesRepository.saveAndFlush(grades);

        // Get all the gradesList where gradesCode does not contain
        defaultGradesFiltering("gradesCode.doesNotContain=" + UPDATED_GRADES_CODE, "gradesCode.doesNotContain=" + DEFAULT_GRADES_CODE);
    }

    @Test
    @Transactional
    void getAllGradesByCreditIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedGrades = gradesRepository.saveAndFlush(grades);

        // Get all the gradesList where credit equals to
        defaultGradesFiltering("credit.equals=" + DEFAULT_CREDIT, "credit.equals=" + UPDATED_CREDIT);
    }

    @Test
    @Transactional
    void getAllGradesByCreditIsInShouldWork() throws Exception {
        // Initialize the database
        insertedGrades = gradesRepository.saveAndFlush(grades);

        // Get all the gradesList where credit in
        defaultGradesFiltering("credit.in=" + DEFAULT_CREDIT + "," + UPDATED_CREDIT, "credit.in=" + UPDATED_CREDIT);
    }

    @Test
    @Transactional
    void getAllGradesByCreditIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedGrades = gradesRepository.saveAndFlush(grades);

        // Get all the gradesList where credit is not null
        defaultGradesFiltering("credit.specified=true", "credit.specified=false");
    }

    @Test
    @Transactional
    void getAllGradesByCreditIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedGrades = gradesRepository.saveAndFlush(grades);

        // Get all the gradesList where credit is greater than or equal to
        defaultGradesFiltering("credit.greaterThanOrEqual=" + DEFAULT_CREDIT, "credit.greaterThanOrEqual=" + UPDATED_CREDIT);
    }

    @Test
    @Transactional
    void getAllGradesByCreditIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedGrades = gradesRepository.saveAndFlush(grades);

        // Get all the gradesList where credit is less than or equal to
        defaultGradesFiltering("credit.lessThanOrEqual=" + DEFAULT_CREDIT, "credit.lessThanOrEqual=" + SMALLER_CREDIT);
    }

    @Test
    @Transactional
    void getAllGradesByCreditIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedGrades = gradesRepository.saveAndFlush(grades);

        // Get all the gradesList where credit is less than
        defaultGradesFiltering("credit.lessThan=" + UPDATED_CREDIT, "credit.lessThan=" + DEFAULT_CREDIT);
    }

    @Test
    @Transactional
    void getAllGradesByCreditIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedGrades = gradesRepository.saveAndFlush(grades);

        // Get all the gradesList where credit is greater than
        defaultGradesFiltering("credit.greaterThan=" + SMALLER_CREDIT, "credit.greaterThan=" + DEFAULT_CREDIT);
    }

    @Test
    @Transactional
    void getAllGradesByStudyAttemptIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedGrades = gradesRepository.saveAndFlush(grades);

        // Get all the gradesList where studyAttempt equals to
        defaultGradesFiltering("studyAttempt.equals=" + DEFAULT_STUDY_ATTEMPT, "studyAttempt.equals=" + UPDATED_STUDY_ATTEMPT);
    }

    @Test
    @Transactional
    void getAllGradesByStudyAttemptIsInShouldWork() throws Exception {
        // Initialize the database
        insertedGrades = gradesRepository.saveAndFlush(grades);

        // Get all the gradesList where studyAttempt in
        defaultGradesFiltering(
            "studyAttempt.in=" + DEFAULT_STUDY_ATTEMPT + "," + UPDATED_STUDY_ATTEMPT,
            "studyAttempt.in=" + UPDATED_STUDY_ATTEMPT
        );
    }

    @Test
    @Transactional
    void getAllGradesByStudyAttemptIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedGrades = gradesRepository.saveAndFlush(grades);

        // Get all the gradesList where studyAttempt is not null
        defaultGradesFiltering("studyAttempt.specified=true", "studyAttempt.specified=false");
    }

    @Test
    @Transactional
    void getAllGradesByStudyAttemptIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedGrades = gradesRepository.saveAndFlush(grades);

        // Get all the gradesList where studyAttempt is greater than or equal to
        defaultGradesFiltering(
            "studyAttempt.greaterThanOrEqual=" + DEFAULT_STUDY_ATTEMPT,
            "studyAttempt.greaterThanOrEqual=" + UPDATED_STUDY_ATTEMPT
        );
    }

    @Test
    @Transactional
    void getAllGradesByStudyAttemptIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedGrades = gradesRepository.saveAndFlush(grades);

        // Get all the gradesList where studyAttempt is less than or equal to
        defaultGradesFiltering(
            "studyAttempt.lessThanOrEqual=" + DEFAULT_STUDY_ATTEMPT,
            "studyAttempt.lessThanOrEqual=" + SMALLER_STUDY_ATTEMPT
        );
    }

    @Test
    @Transactional
    void getAllGradesByStudyAttemptIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedGrades = gradesRepository.saveAndFlush(grades);

        // Get all the gradesList where studyAttempt is less than
        defaultGradesFiltering("studyAttempt.lessThan=" + UPDATED_STUDY_ATTEMPT, "studyAttempt.lessThan=" + DEFAULT_STUDY_ATTEMPT);
    }

    @Test
    @Transactional
    void getAllGradesByStudyAttemptIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedGrades = gradesRepository.saveAndFlush(grades);

        // Get all the gradesList where studyAttempt is greater than
        defaultGradesFiltering("studyAttempt.greaterThan=" + SMALLER_STUDY_ATTEMPT, "studyAttempt.greaterThan=" + DEFAULT_STUDY_ATTEMPT);
    }

    @Test
    @Transactional
    void getAllGradesByExamAttemptIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedGrades = gradesRepository.saveAndFlush(grades);

        // Get all the gradesList where examAttempt equals to
        defaultGradesFiltering("examAttempt.equals=" + DEFAULT_EXAM_ATTEMPT, "examAttempt.equals=" + UPDATED_EXAM_ATTEMPT);
    }

    @Test
    @Transactional
    void getAllGradesByExamAttemptIsInShouldWork() throws Exception {
        // Initialize the database
        insertedGrades = gradesRepository.saveAndFlush(grades);

        // Get all the gradesList where examAttempt in
        defaultGradesFiltering(
            "examAttempt.in=" + DEFAULT_EXAM_ATTEMPT + "," + UPDATED_EXAM_ATTEMPT,
            "examAttempt.in=" + UPDATED_EXAM_ATTEMPT
        );
    }

    @Test
    @Transactional
    void getAllGradesByExamAttemptIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedGrades = gradesRepository.saveAndFlush(grades);

        // Get all the gradesList where examAttempt is not null
        defaultGradesFiltering("examAttempt.specified=true", "examAttempt.specified=false");
    }

    @Test
    @Transactional
    void getAllGradesByExamAttemptIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedGrades = gradesRepository.saveAndFlush(grades);

        // Get all the gradesList where examAttempt is greater than or equal to
        defaultGradesFiltering(
            "examAttempt.greaterThanOrEqual=" + DEFAULT_EXAM_ATTEMPT,
            "examAttempt.greaterThanOrEqual=" + UPDATED_EXAM_ATTEMPT
        );
    }

    @Test
    @Transactional
    void getAllGradesByExamAttemptIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedGrades = gradesRepository.saveAndFlush(grades);

        // Get all the gradesList where examAttempt is less than or equal to
        defaultGradesFiltering(
            "examAttempt.lessThanOrEqual=" + DEFAULT_EXAM_ATTEMPT,
            "examAttempt.lessThanOrEqual=" + SMALLER_EXAM_ATTEMPT
        );
    }

    @Test
    @Transactional
    void getAllGradesByExamAttemptIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedGrades = gradesRepository.saveAndFlush(grades);

        // Get all the gradesList where examAttempt is less than
        defaultGradesFiltering("examAttempt.lessThan=" + UPDATED_EXAM_ATTEMPT, "examAttempt.lessThan=" + DEFAULT_EXAM_ATTEMPT);
    }

    @Test
    @Transactional
    void getAllGradesByExamAttemptIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedGrades = gradesRepository.saveAndFlush(grades);

        // Get all the gradesList where examAttempt is greater than
        defaultGradesFiltering("examAttempt.greaterThan=" + SMALLER_EXAM_ATTEMPT, "examAttempt.greaterThan=" + DEFAULT_EXAM_ATTEMPT);
    }

    @Test
    @Transactional
    void getAllGradesByProcessScoreIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedGrades = gradesRepository.saveAndFlush(grades);

        // Get all the gradesList where processScore equals to
        defaultGradesFiltering("processScore.equals=" + DEFAULT_PROCESS_SCORE, "processScore.equals=" + UPDATED_PROCESS_SCORE);
    }

    @Test
    @Transactional
    void getAllGradesByProcessScoreIsInShouldWork() throws Exception {
        // Initialize the database
        insertedGrades = gradesRepository.saveAndFlush(grades);

        // Get all the gradesList where processScore in
        defaultGradesFiltering(
            "processScore.in=" + DEFAULT_PROCESS_SCORE + "," + UPDATED_PROCESS_SCORE,
            "processScore.in=" + UPDATED_PROCESS_SCORE
        );
    }

    @Test
    @Transactional
    void getAllGradesByProcessScoreIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedGrades = gradesRepository.saveAndFlush(grades);

        // Get all the gradesList where processScore is not null
        defaultGradesFiltering("processScore.specified=true", "processScore.specified=false");
    }

    @Test
    @Transactional
    void getAllGradesByProcessScoreIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedGrades = gradesRepository.saveAndFlush(grades);

        // Get all the gradesList where processScore is greater than or equal to
        defaultGradesFiltering(
            "processScore.greaterThanOrEqual=" + DEFAULT_PROCESS_SCORE,
            "processScore.greaterThanOrEqual=" + UPDATED_PROCESS_SCORE
        );
    }

    @Test
    @Transactional
    void getAllGradesByProcessScoreIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedGrades = gradesRepository.saveAndFlush(grades);

        // Get all the gradesList where processScore is less than or equal to
        defaultGradesFiltering(
            "processScore.lessThanOrEqual=" + DEFAULT_PROCESS_SCORE,
            "processScore.lessThanOrEqual=" + SMALLER_PROCESS_SCORE
        );
    }

    @Test
    @Transactional
    void getAllGradesByProcessScoreIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedGrades = gradesRepository.saveAndFlush(grades);

        // Get all the gradesList where processScore is less than
        defaultGradesFiltering("processScore.lessThan=" + UPDATED_PROCESS_SCORE, "processScore.lessThan=" + DEFAULT_PROCESS_SCORE);
    }

    @Test
    @Transactional
    void getAllGradesByProcessScoreIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedGrades = gradesRepository.saveAndFlush(grades);

        // Get all the gradesList where processScore is greater than
        defaultGradesFiltering("processScore.greaterThan=" + SMALLER_PROCESS_SCORE, "processScore.greaterThan=" + DEFAULT_PROCESS_SCORE);
    }

    @Test
    @Transactional
    void getAllGradesByExamScoreIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedGrades = gradesRepository.saveAndFlush(grades);

        // Get all the gradesList where examScore equals to
        defaultGradesFiltering("examScore.equals=" + DEFAULT_EXAM_SCORE, "examScore.equals=" + UPDATED_EXAM_SCORE);
    }

    @Test
    @Transactional
    void getAllGradesByExamScoreIsInShouldWork() throws Exception {
        // Initialize the database
        insertedGrades = gradesRepository.saveAndFlush(grades);

        // Get all the gradesList where examScore in
        defaultGradesFiltering("examScore.in=" + DEFAULT_EXAM_SCORE + "," + UPDATED_EXAM_SCORE, "examScore.in=" + UPDATED_EXAM_SCORE);
    }

    @Test
    @Transactional
    void getAllGradesByExamScoreIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedGrades = gradesRepository.saveAndFlush(grades);

        // Get all the gradesList where examScore is not null
        defaultGradesFiltering("examScore.specified=true", "examScore.specified=false");
    }

    @Test
    @Transactional
    void getAllGradesByExamScoreIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedGrades = gradesRepository.saveAndFlush(grades);

        // Get all the gradesList where examScore is greater than or equal to
        defaultGradesFiltering("examScore.greaterThanOrEqual=" + DEFAULT_EXAM_SCORE, "examScore.greaterThanOrEqual=" + UPDATED_EXAM_SCORE);
    }

    @Test
    @Transactional
    void getAllGradesByExamScoreIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedGrades = gradesRepository.saveAndFlush(grades);

        // Get all the gradesList where examScore is less than or equal to
        defaultGradesFiltering("examScore.lessThanOrEqual=" + DEFAULT_EXAM_SCORE, "examScore.lessThanOrEqual=" + SMALLER_EXAM_SCORE);
    }

    @Test
    @Transactional
    void getAllGradesByExamScoreIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedGrades = gradesRepository.saveAndFlush(grades);

        // Get all the gradesList where examScore is less than
        defaultGradesFiltering("examScore.lessThan=" + UPDATED_EXAM_SCORE, "examScore.lessThan=" + DEFAULT_EXAM_SCORE);
    }

    @Test
    @Transactional
    void getAllGradesByExamScoreIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedGrades = gradesRepository.saveAndFlush(grades);

        // Get all the gradesList where examScore is greater than
        defaultGradesFiltering("examScore.greaterThan=" + SMALLER_EXAM_SCORE, "examScore.greaterThan=" + DEFAULT_EXAM_SCORE);
    }

    @Test
    @Transactional
    void getAllGradesByScore10IsEqualToSomething() throws Exception {
        // Initialize the database
        insertedGrades = gradesRepository.saveAndFlush(grades);

        // Get all the gradesList where score10 equals to
        defaultGradesFiltering("score10.equals=" + DEFAULT_SCORE_10, "score10.equals=" + UPDATED_SCORE_10);
    }

    @Test
    @Transactional
    void getAllGradesByScore10IsInShouldWork() throws Exception {
        // Initialize the database
        insertedGrades = gradesRepository.saveAndFlush(grades);

        // Get all the gradesList where score10 in
        defaultGradesFiltering("score10.in=" + DEFAULT_SCORE_10 + "," + UPDATED_SCORE_10, "score10.in=" + UPDATED_SCORE_10);
    }

    @Test
    @Transactional
    void getAllGradesByScore10IsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedGrades = gradesRepository.saveAndFlush(grades);

        // Get all the gradesList where score10 is not null
        defaultGradesFiltering("score10.specified=true", "score10.specified=false");
    }

    @Test
    @Transactional
    void getAllGradesByScore10IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedGrades = gradesRepository.saveAndFlush(grades);

        // Get all the gradesList where score10 is greater than or equal to
        defaultGradesFiltering("score10.greaterThanOrEqual=" + DEFAULT_SCORE_10, "score10.greaterThanOrEqual=" + UPDATED_SCORE_10);
    }

    @Test
    @Transactional
    void getAllGradesByScore10IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedGrades = gradesRepository.saveAndFlush(grades);

        // Get all the gradesList where score10 is less than or equal to
        defaultGradesFiltering("score10.lessThanOrEqual=" + DEFAULT_SCORE_10, "score10.lessThanOrEqual=" + SMALLER_SCORE_10);
    }

    @Test
    @Transactional
    void getAllGradesByScore10IsLessThanSomething() throws Exception {
        // Initialize the database
        insertedGrades = gradesRepository.saveAndFlush(grades);

        // Get all the gradesList where score10 is less than
        defaultGradesFiltering("score10.lessThan=" + UPDATED_SCORE_10, "score10.lessThan=" + DEFAULT_SCORE_10);
    }

    @Test
    @Transactional
    void getAllGradesByScore10IsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedGrades = gradesRepository.saveAndFlush(grades);

        // Get all the gradesList where score10 is greater than
        defaultGradesFiltering("score10.greaterThan=" + SMALLER_SCORE_10, "score10.greaterThan=" + DEFAULT_SCORE_10);
    }

    @Test
    @Transactional
    void getAllGradesByScore4IsEqualToSomething() throws Exception {
        // Initialize the database
        insertedGrades = gradesRepository.saveAndFlush(grades);

        // Get all the gradesList where score4 equals to
        defaultGradesFiltering("score4.equals=" + DEFAULT_SCORE_4, "score4.equals=" + UPDATED_SCORE_4);
    }

    @Test
    @Transactional
    void getAllGradesByScore4IsInShouldWork() throws Exception {
        // Initialize the database
        insertedGrades = gradesRepository.saveAndFlush(grades);

        // Get all the gradesList where score4 in
        defaultGradesFiltering("score4.in=" + DEFAULT_SCORE_4 + "," + UPDATED_SCORE_4, "score4.in=" + UPDATED_SCORE_4);
    }

    @Test
    @Transactional
    void getAllGradesByScore4IsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedGrades = gradesRepository.saveAndFlush(grades);

        // Get all the gradesList where score4 is not null
        defaultGradesFiltering("score4.specified=true", "score4.specified=false");
    }

    @Test
    @Transactional
    void getAllGradesByScore4IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedGrades = gradesRepository.saveAndFlush(grades);

        // Get all the gradesList where score4 is greater than or equal to
        defaultGradesFiltering("score4.greaterThanOrEqual=" + DEFAULT_SCORE_4, "score4.greaterThanOrEqual=" + UPDATED_SCORE_4);
    }

    @Test
    @Transactional
    void getAllGradesByScore4IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedGrades = gradesRepository.saveAndFlush(grades);

        // Get all the gradesList where score4 is less than or equal to
        defaultGradesFiltering("score4.lessThanOrEqual=" + DEFAULT_SCORE_4, "score4.lessThanOrEqual=" + SMALLER_SCORE_4);
    }

    @Test
    @Transactional
    void getAllGradesByScore4IsLessThanSomething() throws Exception {
        // Initialize the database
        insertedGrades = gradesRepository.saveAndFlush(grades);

        // Get all the gradesList where score4 is less than
        defaultGradesFiltering("score4.lessThan=" + UPDATED_SCORE_4, "score4.lessThan=" + DEFAULT_SCORE_4);
    }

    @Test
    @Transactional
    void getAllGradesByScore4IsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedGrades = gradesRepository.saveAndFlush(grades);

        // Get all the gradesList where score4 is greater than
        defaultGradesFiltering("score4.greaterThan=" + SMALLER_SCORE_4, "score4.greaterThan=" + DEFAULT_SCORE_4);
    }

    @Test
    @Transactional
    void getAllGradesByLetterGradeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedGrades = gradesRepository.saveAndFlush(grades);

        // Get all the gradesList where letterGrade equals to
        defaultGradesFiltering("letterGrade.equals=" + DEFAULT_LETTER_GRADE, "letterGrade.equals=" + UPDATED_LETTER_GRADE);
    }

    @Test
    @Transactional
    void getAllGradesByLetterGradeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedGrades = gradesRepository.saveAndFlush(grades);

        // Get all the gradesList where letterGrade in
        defaultGradesFiltering(
            "letterGrade.in=" + DEFAULT_LETTER_GRADE + "," + UPDATED_LETTER_GRADE,
            "letterGrade.in=" + UPDATED_LETTER_GRADE
        );
    }

    @Test
    @Transactional
    void getAllGradesByLetterGradeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedGrades = gradesRepository.saveAndFlush(grades);

        // Get all the gradesList where letterGrade is not null
        defaultGradesFiltering("letterGrade.specified=true", "letterGrade.specified=false");
    }

    @Test
    @Transactional
    void getAllGradesByEvaluationIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedGrades = gradesRepository.saveAndFlush(grades);

        // Get all the gradesList where evaluation equals to
        defaultGradesFiltering("evaluation.equals=" + DEFAULT_EVALUATION, "evaluation.equals=" + UPDATED_EVALUATION);
    }

    @Test
    @Transactional
    void getAllGradesByEvaluationIsInShouldWork() throws Exception {
        // Initialize the database
        insertedGrades = gradesRepository.saveAndFlush(grades);

        // Get all the gradesList where evaluation in
        defaultGradesFiltering("evaluation.in=" + DEFAULT_EVALUATION + "," + UPDATED_EVALUATION, "evaluation.in=" + UPDATED_EVALUATION);
    }

    @Test
    @Transactional
    void getAllGradesByEvaluationIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedGrades = gradesRepository.saveAndFlush(grades);

        // Get all the gradesList where evaluation is not null
        defaultGradesFiltering("evaluation.specified=true", "evaluation.specified=false");
    }

    @Test
    @Transactional
    void getAllGradesByNotesIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedGrades = gradesRepository.saveAndFlush(grades);

        // Get all the gradesList where notes equals to
        defaultGradesFiltering("notes.equals=" + DEFAULT_NOTES, "notes.equals=" + UPDATED_NOTES);
    }

    @Test
    @Transactional
    void getAllGradesByNotesIsInShouldWork() throws Exception {
        // Initialize the database
        insertedGrades = gradesRepository.saveAndFlush(grades);

        // Get all the gradesList where notes in
        defaultGradesFiltering("notes.in=" + DEFAULT_NOTES + "," + UPDATED_NOTES, "notes.in=" + UPDATED_NOTES);
    }

    @Test
    @Transactional
    void getAllGradesByNotesIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedGrades = gradesRepository.saveAndFlush(grades);

        // Get all the gradesList where notes is not null
        defaultGradesFiltering("notes.specified=true", "notes.specified=false");
    }

    @Test
    @Transactional
    void getAllGradesByNotesContainsSomething() throws Exception {
        // Initialize the database
        insertedGrades = gradesRepository.saveAndFlush(grades);

        // Get all the gradesList where notes contains
        defaultGradesFiltering("notes.contains=" + DEFAULT_NOTES, "notes.contains=" + UPDATED_NOTES);
    }

    @Test
    @Transactional
    void getAllGradesByNotesNotContainsSomething() throws Exception {
        // Initialize the database
        insertedGrades = gradesRepository.saveAndFlush(grades);

        // Get all the gradesList where notes does not contain
        defaultGradesFiltering("notes.doesNotContain=" + UPDATED_NOTES, "notes.doesNotContain=" + DEFAULT_NOTES);
    }

    @Test
    @Transactional
    void getAllGradesByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedGrades = gradesRepository.saveAndFlush(grades);

        // Get all the gradesList where status equals to
        defaultGradesFiltering("status.equals=" + DEFAULT_STATUS, "status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllGradesByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        insertedGrades = gradesRepository.saveAndFlush(grades);

        // Get all the gradesList where status in
        defaultGradesFiltering("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS, "status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllGradesByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedGrades = gradesRepository.saveAndFlush(grades);

        // Get all the gradesList where status is not null
        defaultGradesFiltering("status.specified=true", "status.specified=false");
    }

    @Test
    @Transactional
    void getAllGradesByStudentIsEqualToSomething() throws Exception {
        Student student;
        if (TestUtil.findAll(em, Student.class).isEmpty()) {
            gradesRepository.saveAndFlush(grades);
            student = StudentResourceIT.createEntity();
        } else {
            student = TestUtil.findAll(em, Student.class).get(0);
        }
        em.persist(student);
        em.flush();
        grades.setStudent(student);
        gradesRepository.saveAndFlush(grades);
        Long studentId = student.getId();
        // Get all the gradesList where student equals to studentId
        defaultGradesShouldBeFound("studentId.equals=" + studentId);

        // Get all the gradesList where student equals to (studentId + 1)
        defaultGradesShouldNotBeFound("studentId.equals=" + (studentId + 1));
    }

    @Test
    @Transactional
    void getAllGradesByClassesIsEqualToSomething() throws Exception {
        Classes classes;
        if (TestUtil.findAll(em, Classes.class).isEmpty()) {
            gradesRepository.saveAndFlush(grades);
            classes = ClassesResourceIT.createEntity();
        } else {
            classes = TestUtil.findAll(em, Classes.class).get(0);
        }
        em.persist(classes);
        em.flush();
        grades.setClasses(classes);
        gradesRepository.saveAndFlush(grades);
        Long classesId = classes.getId();
        // Get all the gradesList where classes equals to classesId
        defaultGradesShouldBeFound("classesId.equals=" + classesId);

        // Get all the gradesList where classes equals to (classesId + 1)
        defaultGradesShouldNotBeFound("classesId.equals=" + (classesId + 1));
    }

    private void defaultGradesFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultGradesShouldBeFound(shouldBeFound);
        defaultGradesShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultGradesShouldBeFound(String filter) throws Exception {
        restGradesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(grades.getId().intValue())))
            .andExpect(jsonPath("$.[*].gradesCode").value(hasItem(DEFAULT_GRADES_CODE)))
            .andExpect(jsonPath("$.[*].credit").value(hasItem(DEFAULT_CREDIT)))
            .andExpect(jsonPath("$.[*].studyAttempt").value(hasItem(DEFAULT_STUDY_ATTEMPT)))
            .andExpect(jsonPath("$.[*].examAttempt").value(hasItem(DEFAULT_EXAM_ATTEMPT)))
            .andExpect(jsonPath("$.[*].processScore").value(hasItem(sameNumber(DEFAULT_PROCESS_SCORE))))
            .andExpect(jsonPath("$.[*].examScore").value(hasItem(sameNumber(DEFAULT_EXAM_SCORE))))
            .andExpect(jsonPath("$.[*].score10").value(hasItem(sameNumber(DEFAULT_SCORE_10))))
            .andExpect(jsonPath("$.[*].score4").value(hasItem(sameNumber(DEFAULT_SCORE_4))))
            .andExpect(jsonPath("$.[*].letterGrade").value(hasItem(DEFAULT_LETTER_GRADE.toString())))
            .andExpect(jsonPath("$.[*].evaluation").value(hasItem(DEFAULT_EVALUATION.toString())))
            .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));

        // Check, that the count call also returns 1
        restGradesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultGradesShouldNotBeFound(String filter) throws Exception {
        restGradesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restGradesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingGrades() throws Exception {
        // Get the grades
        restGradesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingGrades() throws Exception {
        // Initialize the database
        insertedGrades = gradesRepository.saveAndFlush(grades);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the grades
        Grades updatedGrades = gradesRepository.findById(grades.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedGrades are not directly saved in db
        em.detach(updatedGrades);
        updatedGrades
            .gradesCode(UPDATED_GRADES_CODE)
            .credit(UPDATED_CREDIT)
            .studyAttempt(UPDATED_STUDY_ATTEMPT)
            .examAttempt(UPDATED_EXAM_ATTEMPT)
            .processScore(UPDATED_PROCESS_SCORE)
            .examScore(UPDATED_EXAM_SCORE)
            .score10(UPDATED_SCORE_10)
            .score4(UPDATED_SCORE_4)
            .letterGrade(UPDATED_LETTER_GRADE)
            .evaluation(UPDATED_EVALUATION)
            .notes(UPDATED_NOTES)
            .status(UPDATED_STATUS);
        GradesDTO gradesDTO = gradesMapper.toDto(updatedGrades);

        restGradesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, gradesDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(gradesDTO))
            )
            .andExpect(status().isOk());

        // Validate the Grades in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedGradesToMatchAllProperties(updatedGrades);
    }

    @Test
    @Transactional
    void putNonExistingGrades() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        grades.setId(longCount.incrementAndGet());

        // Create the Grades
        GradesDTO gradesDTO = gradesMapper.toDto(grades);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGradesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, gradesDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(gradesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Grades in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchGrades() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        grades.setId(longCount.incrementAndGet());

        // Create the Grades
        GradesDTO gradesDTO = gradesMapper.toDto(grades);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGradesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(gradesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Grades in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamGrades() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        grades.setId(longCount.incrementAndGet());

        // Create the Grades
        GradesDTO gradesDTO = gradesMapper.toDto(grades);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGradesMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(gradesDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Grades in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateGradesWithPatch() throws Exception {
        // Initialize the database
        insertedGrades = gradesRepository.saveAndFlush(grades);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the grades using partial update
        Grades partialUpdatedGrades = new Grades();
        partialUpdatedGrades.setId(grades.getId());

        partialUpdatedGrades.gradesCode(UPDATED_GRADES_CODE).credit(UPDATED_CREDIT).studyAttempt(UPDATED_STUDY_ATTEMPT);

        restGradesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGrades.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedGrades))
            )
            .andExpect(status().isOk());

        // Validate the Grades in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertGradesUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedGrades, grades), getPersistedGrades(grades));
    }

    @Test
    @Transactional
    void fullUpdateGradesWithPatch() throws Exception {
        // Initialize the database
        insertedGrades = gradesRepository.saveAndFlush(grades);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the grades using partial update
        Grades partialUpdatedGrades = new Grades();
        partialUpdatedGrades.setId(grades.getId());

        partialUpdatedGrades
            .gradesCode(UPDATED_GRADES_CODE)
            .credit(UPDATED_CREDIT)
            .studyAttempt(UPDATED_STUDY_ATTEMPT)
            .examAttempt(UPDATED_EXAM_ATTEMPT)
            .processScore(UPDATED_PROCESS_SCORE)
            .examScore(UPDATED_EXAM_SCORE)
            .score10(UPDATED_SCORE_10)
            .score4(UPDATED_SCORE_4)
            .letterGrade(UPDATED_LETTER_GRADE)
            .evaluation(UPDATED_EVALUATION)
            .notes(UPDATED_NOTES)
            .status(UPDATED_STATUS);

        restGradesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGrades.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedGrades))
            )
            .andExpect(status().isOk());

        // Validate the Grades in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertGradesUpdatableFieldsEquals(partialUpdatedGrades, getPersistedGrades(partialUpdatedGrades));
    }

    @Test
    @Transactional
    void patchNonExistingGrades() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        grades.setId(longCount.incrementAndGet());

        // Create the Grades
        GradesDTO gradesDTO = gradesMapper.toDto(grades);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGradesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, gradesDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(gradesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Grades in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchGrades() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        grades.setId(longCount.incrementAndGet());

        // Create the Grades
        GradesDTO gradesDTO = gradesMapper.toDto(grades);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGradesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(gradesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Grades in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamGrades() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        grades.setId(longCount.incrementAndGet());

        // Create the Grades
        GradesDTO gradesDTO = gradesMapper.toDto(grades);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGradesMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(gradesDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Grades in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteGrades() throws Exception {
        // Initialize the database
        insertedGrades = gradesRepository.saveAndFlush(grades);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the grades
        restGradesMockMvc
            .perform(delete(ENTITY_API_URL_ID, grades.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return gradesRepository.count();
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

    protected Grades getPersistedGrades(Grades grades) {
        return gradesRepository.findById(grades.getId()).orElseThrow();
    }

    protected void assertPersistedGradesToMatchAllProperties(Grades expectedGrades) {
        assertGradesAllPropertiesEquals(expectedGrades, getPersistedGrades(expectedGrades));
    }

    protected void assertPersistedGradesToMatchUpdatableProperties(Grades expectedGrades) {
        assertGradesAllUpdatablePropertiesEquals(expectedGrades, getPersistedGrades(expectedGrades));
    }
}
