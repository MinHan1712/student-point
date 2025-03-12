package student.point.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static student.point.domain.ClassesAsserts.*;
import static student.point.web.rest.TestUtil.createUpdateProxyForBean;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
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
import student.point.domain.Classes;
import student.point.domain.Course;
import student.point.domain.Teachers;
import student.point.domain.enumeration.ClassesType;
import student.point.domain.enumeration.DeliveryMode;
import student.point.repository.ClassesRepository;
import student.point.service.dto.ClassesDTO;
import student.point.service.mapper.ClassesMapper;

/**
 * Integration tests for the {@link ClassesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ClassesResourceIT {

    private static final String DEFAULT_CLASS_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CLASS_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_CLASS_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CLASS_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CLASSROOM = "AAAAAAAAAA";
    private static final String UPDATED_CLASSROOM = "BBBBBBBBBB";

    private static final Integer DEFAULT_CREDITS = 1;
    private static final Integer UPDATED_CREDITS = 2;
    private static final Integer SMALLER_CREDITS = 1 - 1;

    private static final Integer DEFAULT_NUMBER_OF_SESSIONS = 1;
    private static final Integer UPDATED_NUMBER_OF_SESSIONS = 2;
    private static final Integer SMALLER_NUMBER_OF_SESSIONS = 1 - 1;

    private static final Integer DEFAULT_TOTAL_NUMBER_OF_STUDENTS = 1;
    private static final Integer UPDATED_TOTAL_NUMBER_OF_STUDENTS = 2;
    private static final Integer SMALLER_TOTAL_NUMBER_OF_STUDENTS = 1 - 1;

    private static final Instant DEFAULT_START_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_END_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_END_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final ClassesType DEFAULT_CLASS_TYPE = ClassesType.Lecture;
    private static final ClassesType UPDATED_CLASS_TYPE = ClassesType.Lab;

    private static final DeliveryMode DEFAULT_DELIVERY_MODE = DeliveryMode.Online;
    private static final DeliveryMode UPDATED_DELIVERY_MODE = DeliveryMode.Offline;

    private static final Boolean DEFAULT_STATUS = false;
    private static final Boolean UPDATED_STATUS = true;

    private static final String DEFAULT_NOTES = "AAAAAAAAAA";
    private static final String UPDATED_NOTES = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_ACADEMIC_YEAR = "AAAAAAAAAA";
    private static final String UPDATED_ACADEMIC_YEAR = "BBBBBBBBBB";

    private static final Long DEFAULT_PARENT_ID = 1L;
    private static final Long UPDATED_PARENT_ID = 2L;
    private static final Long SMALLER_PARENT_ID = 1L - 1L;

    private static final String ENTITY_API_URL = "/api/classes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ClassesRepository classesRepository;

    @Autowired
    private ClassesMapper classesMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restClassesMockMvc;

    private Classes classes;

    private Classes insertedClasses;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Classes createEntity() {
        return new Classes()
            .classCode(DEFAULT_CLASS_CODE)
            .className(DEFAULT_CLASS_NAME)
            .classroom(DEFAULT_CLASSROOM)
            .credits(DEFAULT_CREDITS)
            .numberOfSessions(DEFAULT_NUMBER_OF_SESSIONS)
            .totalNumberOfStudents(DEFAULT_TOTAL_NUMBER_OF_STUDENTS)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .classType(DEFAULT_CLASS_TYPE)
            .deliveryMode(DEFAULT_DELIVERY_MODE)
            .status(DEFAULT_STATUS)
            .notes(DEFAULT_NOTES)
            .description(DEFAULT_DESCRIPTION)
            .academicYear(DEFAULT_ACADEMIC_YEAR)
            .parentId(DEFAULT_PARENT_ID);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Classes createUpdatedEntity() {
        return new Classes()
            .classCode(UPDATED_CLASS_CODE)
            .className(UPDATED_CLASS_NAME)
            .classroom(UPDATED_CLASSROOM)
            .credits(UPDATED_CREDITS)
            .numberOfSessions(UPDATED_NUMBER_OF_SESSIONS)
            .totalNumberOfStudents(UPDATED_TOTAL_NUMBER_OF_STUDENTS)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .classType(UPDATED_CLASS_TYPE)
            .deliveryMode(UPDATED_DELIVERY_MODE)
            .status(UPDATED_STATUS)
            .notes(UPDATED_NOTES)
            .description(UPDATED_DESCRIPTION)
            .academicYear(UPDATED_ACADEMIC_YEAR)
            .parentId(UPDATED_PARENT_ID);
    }

    @BeforeEach
    public void initTest() {
        classes = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedClasses != null) {
            classesRepository.delete(insertedClasses);
            insertedClasses = null;
        }
    }

    @Test
    @Transactional
    void createClasses() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Classes
        ClassesDTO classesDTO = classesMapper.toDto(classes);
        var returnedClassesDTO = om.readValue(
            restClassesMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(classesDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            ClassesDTO.class
        );

        // Validate the Classes in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedClasses = classesMapper.toEntity(returnedClassesDTO);
        assertClassesUpdatableFieldsEquals(returnedClasses, getPersistedClasses(returnedClasses));

        insertedClasses = returnedClasses;
    }

    @Test
    @Transactional
    void createClassesWithExistingId() throws Exception {
        // Create the Classes with an existing ID
        classes.setId(1L);
        ClassesDTO classesDTO = classesMapper.toDto(classes);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restClassesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(classesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Classes in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllClasses() throws Exception {
        // Initialize the database
        insertedClasses = classesRepository.saveAndFlush(classes);

        // Get all the classesList
        restClassesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(classes.getId().intValue())))
            .andExpect(jsonPath("$.[*].classCode").value(hasItem(DEFAULT_CLASS_CODE)))
            .andExpect(jsonPath("$.[*].className").value(hasItem(DEFAULT_CLASS_NAME)))
            .andExpect(jsonPath("$.[*].classroom").value(hasItem(DEFAULT_CLASSROOM)))
            .andExpect(jsonPath("$.[*].credits").value(hasItem(DEFAULT_CREDITS)))
            .andExpect(jsonPath("$.[*].numberOfSessions").value(hasItem(DEFAULT_NUMBER_OF_SESSIONS)))
            .andExpect(jsonPath("$.[*].totalNumberOfStudents").value(hasItem(DEFAULT_TOTAL_NUMBER_OF_STUDENTS)))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].classType").value(hasItem(DEFAULT_CLASS_TYPE.toString())))
            .andExpect(jsonPath("$.[*].deliveryMode").value(hasItem(DEFAULT_DELIVERY_MODE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].academicYear").value(hasItem(DEFAULT_ACADEMIC_YEAR)))
            .andExpect(jsonPath("$.[*].parentId").value(hasItem(DEFAULT_PARENT_ID.intValue())));
    }

    @Test
    @Transactional
    void getClasses() throws Exception {
        // Initialize the database
        insertedClasses = classesRepository.saveAndFlush(classes);

        // Get the classes
        restClassesMockMvc
            .perform(get(ENTITY_API_URL_ID, classes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(classes.getId().intValue()))
            .andExpect(jsonPath("$.classCode").value(DEFAULT_CLASS_CODE))
            .andExpect(jsonPath("$.className").value(DEFAULT_CLASS_NAME))
            .andExpect(jsonPath("$.classroom").value(DEFAULT_CLASSROOM))
            .andExpect(jsonPath("$.credits").value(DEFAULT_CREDITS))
            .andExpect(jsonPath("$.numberOfSessions").value(DEFAULT_NUMBER_OF_SESSIONS))
            .andExpect(jsonPath("$.totalNumberOfStudents").value(DEFAULT_TOTAL_NUMBER_OF_STUDENTS))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.classType").value(DEFAULT_CLASS_TYPE.toString()))
            .andExpect(jsonPath("$.deliveryMode").value(DEFAULT_DELIVERY_MODE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.notes").value(DEFAULT_NOTES))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.academicYear").value(DEFAULT_ACADEMIC_YEAR))
            .andExpect(jsonPath("$.parentId").value(DEFAULT_PARENT_ID.intValue()));
    }

    @Test
    @Transactional
    void getClassesByIdFiltering() throws Exception {
        // Initialize the database
        insertedClasses = classesRepository.saveAndFlush(classes);

        Long id = classes.getId();

        defaultClassesFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultClassesFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultClassesFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllClassesByClassCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedClasses = classesRepository.saveAndFlush(classes);

        // Get all the classesList where classCode equals to
        defaultClassesFiltering("classCode.equals=" + DEFAULT_CLASS_CODE, "classCode.equals=" + UPDATED_CLASS_CODE);
    }

    @Test
    @Transactional
    void getAllClassesByClassCodeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedClasses = classesRepository.saveAndFlush(classes);

        // Get all the classesList where classCode in
        defaultClassesFiltering("classCode.in=" + DEFAULT_CLASS_CODE + "," + UPDATED_CLASS_CODE, "classCode.in=" + UPDATED_CLASS_CODE);
    }

    @Test
    @Transactional
    void getAllClassesByClassCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedClasses = classesRepository.saveAndFlush(classes);

        // Get all the classesList where classCode is not null
        defaultClassesFiltering("classCode.specified=true", "classCode.specified=false");
    }

    @Test
    @Transactional
    void getAllClassesByClassCodeContainsSomething() throws Exception {
        // Initialize the database
        insertedClasses = classesRepository.saveAndFlush(classes);

        // Get all the classesList where classCode contains
        defaultClassesFiltering("classCode.contains=" + DEFAULT_CLASS_CODE, "classCode.contains=" + UPDATED_CLASS_CODE);
    }

    @Test
    @Transactional
    void getAllClassesByClassCodeNotContainsSomething() throws Exception {
        // Initialize the database
        insertedClasses = classesRepository.saveAndFlush(classes);

        // Get all the classesList where classCode does not contain
        defaultClassesFiltering("classCode.doesNotContain=" + UPDATED_CLASS_CODE, "classCode.doesNotContain=" + DEFAULT_CLASS_CODE);
    }

    @Test
    @Transactional
    void getAllClassesByClassNameIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedClasses = classesRepository.saveAndFlush(classes);

        // Get all the classesList where className equals to
        defaultClassesFiltering("className.equals=" + DEFAULT_CLASS_NAME, "className.equals=" + UPDATED_CLASS_NAME);
    }

    @Test
    @Transactional
    void getAllClassesByClassNameIsInShouldWork() throws Exception {
        // Initialize the database
        insertedClasses = classesRepository.saveAndFlush(classes);

        // Get all the classesList where className in
        defaultClassesFiltering("className.in=" + DEFAULT_CLASS_NAME + "," + UPDATED_CLASS_NAME, "className.in=" + UPDATED_CLASS_NAME);
    }

    @Test
    @Transactional
    void getAllClassesByClassNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedClasses = classesRepository.saveAndFlush(classes);

        // Get all the classesList where className is not null
        defaultClassesFiltering("className.specified=true", "className.specified=false");
    }

    @Test
    @Transactional
    void getAllClassesByClassNameContainsSomething() throws Exception {
        // Initialize the database
        insertedClasses = classesRepository.saveAndFlush(classes);

        // Get all the classesList where className contains
        defaultClassesFiltering("className.contains=" + DEFAULT_CLASS_NAME, "className.contains=" + UPDATED_CLASS_NAME);
    }

    @Test
    @Transactional
    void getAllClassesByClassNameNotContainsSomething() throws Exception {
        // Initialize the database
        insertedClasses = classesRepository.saveAndFlush(classes);

        // Get all the classesList where className does not contain
        defaultClassesFiltering("className.doesNotContain=" + UPDATED_CLASS_NAME, "className.doesNotContain=" + DEFAULT_CLASS_NAME);
    }

    @Test
    @Transactional
    void getAllClassesByClassroomIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedClasses = classesRepository.saveAndFlush(classes);

        // Get all the classesList where classroom equals to
        defaultClassesFiltering("classroom.equals=" + DEFAULT_CLASSROOM, "classroom.equals=" + UPDATED_CLASSROOM);
    }

    @Test
    @Transactional
    void getAllClassesByClassroomIsInShouldWork() throws Exception {
        // Initialize the database
        insertedClasses = classesRepository.saveAndFlush(classes);

        // Get all the classesList where classroom in
        defaultClassesFiltering("classroom.in=" + DEFAULT_CLASSROOM + "," + UPDATED_CLASSROOM, "classroom.in=" + UPDATED_CLASSROOM);
    }

    @Test
    @Transactional
    void getAllClassesByClassroomIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedClasses = classesRepository.saveAndFlush(classes);

        // Get all the classesList where classroom is not null
        defaultClassesFiltering("classroom.specified=true", "classroom.specified=false");
    }

    @Test
    @Transactional
    void getAllClassesByClassroomContainsSomething() throws Exception {
        // Initialize the database
        insertedClasses = classesRepository.saveAndFlush(classes);

        // Get all the classesList where classroom contains
        defaultClassesFiltering("classroom.contains=" + DEFAULT_CLASSROOM, "classroom.contains=" + UPDATED_CLASSROOM);
    }

    @Test
    @Transactional
    void getAllClassesByClassroomNotContainsSomething() throws Exception {
        // Initialize the database
        insertedClasses = classesRepository.saveAndFlush(classes);

        // Get all the classesList where classroom does not contain
        defaultClassesFiltering("classroom.doesNotContain=" + UPDATED_CLASSROOM, "classroom.doesNotContain=" + DEFAULT_CLASSROOM);
    }

    @Test
    @Transactional
    void getAllClassesByCreditsIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedClasses = classesRepository.saveAndFlush(classes);

        // Get all the classesList where credits equals to
        defaultClassesFiltering("credits.equals=" + DEFAULT_CREDITS, "credits.equals=" + UPDATED_CREDITS);
    }

    @Test
    @Transactional
    void getAllClassesByCreditsIsInShouldWork() throws Exception {
        // Initialize the database
        insertedClasses = classesRepository.saveAndFlush(classes);

        // Get all the classesList where credits in
        defaultClassesFiltering("credits.in=" + DEFAULT_CREDITS + "," + UPDATED_CREDITS, "credits.in=" + UPDATED_CREDITS);
    }

    @Test
    @Transactional
    void getAllClassesByCreditsIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedClasses = classesRepository.saveAndFlush(classes);

        // Get all the classesList where credits is not null
        defaultClassesFiltering("credits.specified=true", "credits.specified=false");
    }

    @Test
    @Transactional
    void getAllClassesByCreditsIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedClasses = classesRepository.saveAndFlush(classes);

        // Get all the classesList where credits is greater than or equal to
        defaultClassesFiltering("credits.greaterThanOrEqual=" + DEFAULT_CREDITS, "credits.greaterThanOrEqual=" + UPDATED_CREDITS);
    }

    @Test
    @Transactional
    void getAllClassesByCreditsIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedClasses = classesRepository.saveAndFlush(classes);

        // Get all the classesList where credits is less than or equal to
        defaultClassesFiltering("credits.lessThanOrEqual=" + DEFAULT_CREDITS, "credits.lessThanOrEqual=" + SMALLER_CREDITS);
    }

    @Test
    @Transactional
    void getAllClassesByCreditsIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedClasses = classesRepository.saveAndFlush(classes);

        // Get all the classesList where credits is less than
        defaultClassesFiltering("credits.lessThan=" + UPDATED_CREDITS, "credits.lessThan=" + DEFAULT_CREDITS);
    }

    @Test
    @Transactional
    void getAllClassesByCreditsIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedClasses = classesRepository.saveAndFlush(classes);

        // Get all the classesList where credits is greater than
        defaultClassesFiltering("credits.greaterThan=" + SMALLER_CREDITS, "credits.greaterThan=" + DEFAULT_CREDITS);
    }

    @Test
    @Transactional
    void getAllClassesByNumberOfSessionsIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedClasses = classesRepository.saveAndFlush(classes);

        // Get all the classesList where numberOfSessions equals to
        defaultClassesFiltering(
            "numberOfSessions.equals=" + DEFAULT_NUMBER_OF_SESSIONS,
            "numberOfSessions.equals=" + UPDATED_NUMBER_OF_SESSIONS
        );
    }

    @Test
    @Transactional
    void getAllClassesByNumberOfSessionsIsInShouldWork() throws Exception {
        // Initialize the database
        insertedClasses = classesRepository.saveAndFlush(classes);

        // Get all the classesList where numberOfSessions in
        defaultClassesFiltering(
            "numberOfSessions.in=" + DEFAULT_NUMBER_OF_SESSIONS + "," + UPDATED_NUMBER_OF_SESSIONS,
            "numberOfSessions.in=" + UPDATED_NUMBER_OF_SESSIONS
        );
    }

    @Test
    @Transactional
    void getAllClassesByNumberOfSessionsIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedClasses = classesRepository.saveAndFlush(classes);

        // Get all the classesList where numberOfSessions is not null
        defaultClassesFiltering("numberOfSessions.specified=true", "numberOfSessions.specified=false");
    }

    @Test
    @Transactional
    void getAllClassesByNumberOfSessionsIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedClasses = classesRepository.saveAndFlush(classes);

        // Get all the classesList where numberOfSessions is greater than or equal to
        defaultClassesFiltering(
            "numberOfSessions.greaterThanOrEqual=" + DEFAULT_NUMBER_OF_SESSIONS,
            "numberOfSessions.greaterThanOrEqual=" + UPDATED_NUMBER_OF_SESSIONS
        );
    }

    @Test
    @Transactional
    void getAllClassesByNumberOfSessionsIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedClasses = classesRepository.saveAndFlush(classes);

        // Get all the classesList where numberOfSessions is less than or equal to
        defaultClassesFiltering(
            "numberOfSessions.lessThanOrEqual=" + DEFAULT_NUMBER_OF_SESSIONS,
            "numberOfSessions.lessThanOrEqual=" + SMALLER_NUMBER_OF_SESSIONS
        );
    }

    @Test
    @Transactional
    void getAllClassesByNumberOfSessionsIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedClasses = classesRepository.saveAndFlush(classes);

        // Get all the classesList where numberOfSessions is less than
        defaultClassesFiltering(
            "numberOfSessions.lessThan=" + UPDATED_NUMBER_OF_SESSIONS,
            "numberOfSessions.lessThan=" + DEFAULT_NUMBER_OF_SESSIONS
        );
    }

    @Test
    @Transactional
    void getAllClassesByNumberOfSessionsIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedClasses = classesRepository.saveAndFlush(classes);

        // Get all the classesList where numberOfSessions is greater than
        defaultClassesFiltering(
            "numberOfSessions.greaterThan=" + SMALLER_NUMBER_OF_SESSIONS,
            "numberOfSessions.greaterThan=" + DEFAULT_NUMBER_OF_SESSIONS
        );
    }

    @Test
    @Transactional
    void getAllClassesByTotalNumberOfStudentsIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedClasses = classesRepository.saveAndFlush(classes);

        // Get all the classesList where totalNumberOfStudents equals to
        defaultClassesFiltering(
            "totalNumberOfStudents.equals=" + DEFAULT_TOTAL_NUMBER_OF_STUDENTS,
            "totalNumberOfStudents.equals=" + UPDATED_TOTAL_NUMBER_OF_STUDENTS
        );
    }

    @Test
    @Transactional
    void getAllClassesByTotalNumberOfStudentsIsInShouldWork() throws Exception {
        // Initialize the database
        insertedClasses = classesRepository.saveAndFlush(classes);

        // Get all the classesList where totalNumberOfStudents in
        defaultClassesFiltering(
            "totalNumberOfStudents.in=" + DEFAULT_TOTAL_NUMBER_OF_STUDENTS + "," + UPDATED_TOTAL_NUMBER_OF_STUDENTS,
            "totalNumberOfStudents.in=" + UPDATED_TOTAL_NUMBER_OF_STUDENTS
        );
    }

    @Test
    @Transactional
    void getAllClassesByTotalNumberOfStudentsIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedClasses = classesRepository.saveAndFlush(classes);

        // Get all the classesList where totalNumberOfStudents is not null
        defaultClassesFiltering("totalNumberOfStudents.specified=true", "totalNumberOfStudents.specified=false");
    }

    @Test
    @Transactional
    void getAllClassesByTotalNumberOfStudentsIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedClasses = classesRepository.saveAndFlush(classes);

        // Get all the classesList where totalNumberOfStudents is greater than or equal to
        defaultClassesFiltering(
            "totalNumberOfStudents.greaterThanOrEqual=" + DEFAULT_TOTAL_NUMBER_OF_STUDENTS,
            "totalNumberOfStudents.greaterThanOrEqual=" + UPDATED_TOTAL_NUMBER_OF_STUDENTS
        );
    }

    @Test
    @Transactional
    void getAllClassesByTotalNumberOfStudentsIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedClasses = classesRepository.saveAndFlush(classes);

        // Get all the classesList where totalNumberOfStudents is less than or equal to
        defaultClassesFiltering(
            "totalNumberOfStudents.lessThanOrEqual=" + DEFAULT_TOTAL_NUMBER_OF_STUDENTS,
            "totalNumberOfStudents.lessThanOrEqual=" + SMALLER_TOTAL_NUMBER_OF_STUDENTS
        );
    }

    @Test
    @Transactional
    void getAllClassesByTotalNumberOfStudentsIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedClasses = classesRepository.saveAndFlush(classes);

        // Get all the classesList where totalNumberOfStudents is less than
        defaultClassesFiltering(
            "totalNumberOfStudents.lessThan=" + UPDATED_TOTAL_NUMBER_OF_STUDENTS,
            "totalNumberOfStudents.lessThan=" + DEFAULT_TOTAL_NUMBER_OF_STUDENTS
        );
    }

    @Test
    @Transactional
    void getAllClassesByTotalNumberOfStudentsIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedClasses = classesRepository.saveAndFlush(classes);

        // Get all the classesList where totalNumberOfStudents is greater than
        defaultClassesFiltering(
            "totalNumberOfStudents.greaterThan=" + SMALLER_TOTAL_NUMBER_OF_STUDENTS,
            "totalNumberOfStudents.greaterThan=" + DEFAULT_TOTAL_NUMBER_OF_STUDENTS
        );
    }

    @Test
    @Transactional
    void getAllClassesByStartDateIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedClasses = classesRepository.saveAndFlush(classes);

        // Get all the classesList where startDate equals to
        defaultClassesFiltering("startDate.equals=" + DEFAULT_START_DATE, "startDate.equals=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    void getAllClassesByStartDateIsInShouldWork() throws Exception {
        // Initialize the database
        insertedClasses = classesRepository.saveAndFlush(classes);

        // Get all the classesList where startDate in
        defaultClassesFiltering("startDate.in=" + DEFAULT_START_DATE + "," + UPDATED_START_DATE, "startDate.in=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    void getAllClassesByStartDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedClasses = classesRepository.saveAndFlush(classes);

        // Get all the classesList where startDate is not null
        defaultClassesFiltering("startDate.specified=true", "startDate.specified=false");
    }

    @Test
    @Transactional
    void getAllClassesByEndDateIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedClasses = classesRepository.saveAndFlush(classes);

        // Get all the classesList where endDate equals to
        defaultClassesFiltering("endDate.equals=" + DEFAULT_END_DATE, "endDate.equals=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    void getAllClassesByEndDateIsInShouldWork() throws Exception {
        // Initialize the database
        insertedClasses = classesRepository.saveAndFlush(classes);

        // Get all the classesList where endDate in
        defaultClassesFiltering("endDate.in=" + DEFAULT_END_DATE + "," + UPDATED_END_DATE, "endDate.in=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    void getAllClassesByEndDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedClasses = classesRepository.saveAndFlush(classes);

        // Get all the classesList where endDate is not null
        defaultClassesFiltering("endDate.specified=true", "endDate.specified=false");
    }

    @Test
    @Transactional
    void getAllClassesByClassTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedClasses = classesRepository.saveAndFlush(classes);

        // Get all the classesList where classType equals to
        defaultClassesFiltering("classType.equals=" + DEFAULT_CLASS_TYPE, "classType.equals=" + UPDATED_CLASS_TYPE);
    }

    @Test
    @Transactional
    void getAllClassesByClassTypeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedClasses = classesRepository.saveAndFlush(classes);

        // Get all the classesList where classType in
        defaultClassesFiltering("classType.in=" + DEFAULT_CLASS_TYPE + "," + UPDATED_CLASS_TYPE, "classType.in=" + UPDATED_CLASS_TYPE);
    }

    @Test
    @Transactional
    void getAllClassesByClassTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedClasses = classesRepository.saveAndFlush(classes);

        // Get all the classesList where classType is not null
        defaultClassesFiltering("classType.specified=true", "classType.specified=false");
    }

    @Test
    @Transactional
    void getAllClassesByDeliveryModeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedClasses = classesRepository.saveAndFlush(classes);

        // Get all the classesList where deliveryMode equals to
        defaultClassesFiltering("deliveryMode.equals=" + DEFAULT_DELIVERY_MODE, "deliveryMode.equals=" + UPDATED_DELIVERY_MODE);
    }

    @Test
    @Transactional
    void getAllClassesByDeliveryModeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedClasses = classesRepository.saveAndFlush(classes);

        // Get all the classesList where deliveryMode in
        defaultClassesFiltering(
            "deliveryMode.in=" + DEFAULT_DELIVERY_MODE + "," + UPDATED_DELIVERY_MODE,
            "deliveryMode.in=" + UPDATED_DELIVERY_MODE
        );
    }

    @Test
    @Transactional
    void getAllClassesByDeliveryModeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedClasses = classesRepository.saveAndFlush(classes);

        // Get all the classesList where deliveryMode is not null
        defaultClassesFiltering("deliveryMode.specified=true", "deliveryMode.specified=false");
    }

    @Test
    @Transactional
    void getAllClassesByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedClasses = classesRepository.saveAndFlush(classes);

        // Get all the classesList where status equals to
        defaultClassesFiltering("status.equals=" + DEFAULT_STATUS, "status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllClassesByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        insertedClasses = classesRepository.saveAndFlush(classes);

        // Get all the classesList where status in
        defaultClassesFiltering("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS, "status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllClassesByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedClasses = classesRepository.saveAndFlush(classes);

        // Get all the classesList where status is not null
        defaultClassesFiltering("status.specified=true", "status.specified=false");
    }

    @Test
    @Transactional
    void getAllClassesByNotesIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedClasses = classesRepository.saveAndFlush(classes);

        // Get all the classesList where notes equals to
        defaultClassesFiltering("notes.equals=" + DEFAULT_NOTES, "notes.equals=" + UPDATED_NOTES);
    }

    @Test
    @Transactional
    void getAllClassesByNotesIsInShouldWork() throws Exception {
        // Initialize the database
        insertedClasses = classesRepository.saveAndFlush(classes);

        // Get all the classesList where notes in
        defaultClassesFiltering("notes.in=" + DEFAULT_NOTES + "," + UPDATED_NOTES, "notes.in=" + UPDATED_NOTES);
    }

    @Test
    @Transactional
    void getAllClassesByNotesIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedClasses = classesRepository.saveAndFlush(classes);

        // Get all the classesList where notes is not null
        defaultClassesFiltering("notes.specified=true", "notes.specified=false");
    }

    @Test
    @Transactional
    void getAllClassesByNotesContainsSomething() throws Exception {
        // Initialize the database
        insertedClasses = classesRepository.saveAndFlush(classes);

        // Get all the classesList where notes contains
        defaultClassesFiltering("notes.contains=" + DEFAULT_NOTES, "notes.contains=" + UPDATED_NOTES);
    }

    @Test
    @Transactional
    void getAllClassesByNotesNotContainsSomething() throws Exception {
        // Initialize the database
        insertedClasses = classesRepository.saveAndFlush(classes);

        // Get all the classesList where notes does not contain
        defaultClassesFiltering("notes.doesNotContain=" + UPDATED_NOTES, "notes.doesNotContain=" + DEFAULT_NOTES);
    }

    @Test
    @Transactional
    void getAllClassesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedClasses = classesRepository.saveAndFlush(classes);

        // Get all the classesList where description equals to
        defaultClassesFiltering("description.equals=" + DEFAULT_DESCRIPTION, "description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllClassesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        insertedClasses = classesRepository.saveAndFlush(classes);

        // Get all the classesList where description in
        defaultClassesFiltering(
            "description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION,
            "description.in=" + UPDATED_DESCRIPTION
        );
    }

    @Test
    @Transactional
    void getAllClassesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedClasses = classesRepository.saveAndFlush(classes);

        // Get all the classesList where description is not null
        defaultClassesFiltering("description.specified=true", "description.specified=false");
    }

    @Test
    @Transactional
    void getAllClassesByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        insertedClasses = classesRepository.saveAndFlush(classes);

        // Get all the classesList where description contains
        defaultClassesFiltering("description.contains=" + DEFAULT_DESCRIPTION, "description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllClassesByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        insertedClasses = classesRepository.saveAndFlush(classes);

        // Get all the classesList where description does not contain
        defaultClassesFiltering("description.doesNotContain=" + UPDATED_DESCRIPTION, "description.doesNotContain=" + DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllClassesByAcademicYearIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedClasses = classesRepository.saveAndFlush(classes);

        // Get all the classesList where academicYear equals to
        defaultClassesFiltering("academicYear.equals=" + DEFAULT_ACADEMIC_YEAR, "academicYear.equals=" + UPDATED_ACADEMIC_YEAR);
    }

    @Test
    @Transactional
    void getAllClassesByAcademicYearIsInShouldWork() throws Exception {
        // Initialize the database
        insertedClasses = classesRepository.saveAndFlush(classes);

        // Get all the classesList where academicYear in
        defaultClassesFiltering(
            "academicYear.in=" + DEFAULT_ACADEMIC_YEAR + "," + UPDATED_ACADEMIC_YEAR,
            "academicYear.in=" + UPDATED_ACADEMIC_YEAR
        );
    }

    @Test
    @Transactional
    void getAllClassesByAcademicYearIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedClasses = classesRepository.saveAndFlush(classes);

        // Get all the classesList where academicYear is not null
        defaultClassesFiltering("academicYear.specified=true", "academicYear.specified=false");
    }

    @Test
    @Transactional
    void getAllClassesByAcademicYearContainsSomething() throws Exception {
        // Initialize the database
        insertedClasses = classesRepository.saveAndFlush(classes);

        // Get all the classesList where academicYear contains
        defaultClassesFiltering("academicYear.contains=" + DEFAULT_ACADEMIC_YEAR, "academicYear.contains=" + UPDATED_ACADEMIC_YEAR);
    }

    @Test
    @Transactional
    void getAllClassesByAcademicYearNotContainsSomething() throws Exception {
        // Initialize the database
        insertedClasses = classesRepository.saveAndFlush(classes);

        // Get all the classesList where academicYear does not contain
        defaultClassesFiltering(
            "academicYear.doesNotContain=" + UPDATED_ACADEMIC_YEAR,
            "academicYear.doesNotContain=" + DEFAULT_ACADEMIC_YEAR
        );
    }

    @Test
    @Transactional
    void getAllClassesByParentIdIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedClasses = classesRepository.saveAndFlush(classes);

        // Get all the classesList where parentId equals to
        defaultClassesFiltering("parentId.equals=" + DEFAULT_PARENT_ID, "parentId.equals=" + UPDATED_PARENT_ID);
    }

    @Test
    @Transactional
    void getAllClassesByParentIdIsInShouldWork() throws Exception {
        // Initialize the database
        insertedClasses = classesRepository.saveAndFlush(classes);

        // Get all the classesList where parentId in
        defaultClassesFiltering("parentId.in=" + DEFAULT_PARENT_ID + "," + UPDATED_PARENT_ID, "parentId.in=" + UPDATED_PARENT_ID);
    }

    @Test
    @Transactional
    void getAllClassesByParentIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedClasses = classesRepository.saveAndFlush(classes);

        // Get all the classesList where parentId is not null
        defaultClassesFiltering("parentId.specified=true", "parentId.specified=false");
    }

    @Test
    @Transactional
    void getAllClassesByParentIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedClasses = classesRepository.saveAndFlush(classes);

        // Get all the classesList where parentId is greater than or equal to
        defaultClassesFiltering("parentId.greaterThanOrEqual=" + DEFAULT_PARENT_ID, "parentId.greaterThanOrEqual=" + UPDATED_PARENT_ID);
    }

    @Test
    @Transactional
    void getAllClassesByParentIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedClasses = classesRepository.saveAndFlush(classes);

        // Get all the classesList where parentId is less than or equal to
        defaultClassesFiltering("parentId.lessThanOrEqual=" + DEFAULT_PARENT_ID, "parentId.lessThanOrEqual=" + SMALLER_PARENT_ID);
    }

    @Test
    @Transactional
    void getAllClassesByParentIdIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedClasses = classesRepository.saveAndFlush(classes);

        // Get all the classesList where parentId is less than
        defaultClassesFiltering("parentId.lessThan=" + UPDATED_PARENT_ID, "parentId.lessThan=" + DEFAULT_PARENT_ID);
    }

    @Test
    @Transactional
    void getAllClassesByParentIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedClasses = classesRepository.saveAndFlush(classes);

        // Get all the classesList where parentId is greater than
        defaultClassesFiltering("parentId.greaterThan=" + SMALLER_PARENT_ID, "parentId.greaterThan=" + DEFAULT_PARENT_ID);
    }

    @Test
    @Transactional
    void getAllClassesByCourseIsEqualToSomething() throws Exception {
        Course course;
        if (TestUtil.findAll(em, Course.class).isEmpty()) {
            classesRepository.saveAndFlush(classes);
            course = CourseResourceIT.createEntity();
        } else {
            course = TestUtil.findAll(em, Course.class).get(0);
        }
        em.persist(course);
        em.flush();
        classes.setCourse(course);
        classesRepository.saveAndFlush(classes);
        Long courseId = course.getId();
        // Get all the classesList where course equals to courseId
        defaultClassesShouldBeFound("courseId.equals=" + courseId);

        // Get all the classesList where course equals to (courseId + 1)
        defaultClassesShouldNotBeFound("courseId.equals=" + (courseId + 1));
    }

    @Test
    @Transactional
    void getAllClassesByTeachersIsEqualToSomething() throws Exception {
        Teachers teachers;
        if (TestUtil.findAll(em, Teachers.class).isEmpty()) {
            classesRepository.saveAndFlush(classes);
            teachers = TeachersResourceIT.createEntity();
        } else {
            teachers = TestUtil.findAll(em, Teachers.class).get(0);
        }
        em.persist(teachers);
        em.flush();
        classes.setTeachers(teachers);
        classesRepository.saveAndFlush(classes);
        Long teachersId = teachers.getId();
        // Get all the classesList where teachers equals to teachersId
        defaultClassesShouldBeFound("teachersId.equals=" + teachersId);

        // Get all the classesList where teachers equals to (teachersId + 1)
        defaultClassesShouldNotBeFound("teachersId.equals=" + (teachersId + 1));
    }

    private void defaultClassesFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultClassesShouldBeFound(shouldBeFound);
        defaultClassesShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultClassesShouldBeFound(String filter) throws Exception {
        restClassesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(classes.getId().intValue())))
            .andExpect(jsonPath("$.[*].classCode").value(hasItem(DEFAULT_CLASS_CODE)))
            .andExpect(jsonPath("$.[*].className").value(hasItem(DEFAULT_CLASS_NAME)))
            .andExpect(jsonPath("$.[*].classroom").value(hasItem(DEFAULT_CLASSROOM)))
            .andExpect(jsonPath("$.[*].credits").value(hasItem(DEFAULT_CREDITS)))
            .andExpect(jsonPath("$.[*].numberOfSessions").value(hasItem(DEFAULT_NUMBER_OF_SESSIONS)))
            .andExpect(jsonPath("$.[*].totalNumberOfStudents").value(hasItem(DEFAULT_TOTAL_NUMBER_OF_STUDENTS)))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].classType").value(hasItem(DEFAULT_CLASS_TYPE.toString())))
            .andExpect(jsonPath("$.[*].deliveryMode").value(hasItem(DEFAULT_DELIVERY_MODE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].academicYear").value(hasItem(DEFAULT_ACADEMIC_YEAR)))
            .andExpect(jsonPath("$.[*].parentId").value(hasItem(DEFAULT_PARENT_ID.intValue())));

        // Check, that the count call also returns 1
        restClassesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultClassesShouldNotBeFound(String filter) throws Exception {
        restClassesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restClassesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingClasses() throws Exception {
        // Get the classes
        restClassesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingClasses() throws Exception {
        // Initialize the database
        insertedClasses = classesRepository.saveAndFlush(classes);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the classes
        Classes updatedClasses = classesRepository.findById(classes.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedClasses are not directly saved in db
        em.detach(updatedClasses);
        updatedClasses
            .classCode(UPDATED_CLASS_CODE)
            .className(UPDATED_CLASS_NAME)
            .classroom(UPDATED_CLASSROOM)
            .credits(UPDATED_CREDITS)
            .numberOfSessions(UPDATED_NUMBER_OF_SESSIONS)
            .totalNumberOfStudents(UPDATED_TOTAL_NUMBER_OF_STUDENTS)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .classType(UPDATED_CLASS_TYPE)
            .deliveryMode(UPDATED_DELIVERY_MODE)
            .status(UPDATED_STATUS)
            .notes(UPDATED_NOTES)
            .description(UPDATED_DESCRIPTION)
            .academicYear(UPDATED_ACADEMIC_YEAR)
            .parentId(UPDATED_PARENT_ID);
        ClassesDTO classesDTO = classesMapper.toDto(updatedClasses);

        restClassesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, classesDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(classesDTO))
            )
            .andExpect(status().isOk());

        // Validate the Classes in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedClassesToMatchAllProperties(updatedClasses);
    }

    @Test
    @Transactional
    void putNonExistingClasses() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        classes.setId(longCount.incrementAndGet());

        // Create the Classes
        ClassesDTO classesDTO = classesMapper.toDto(classes);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClassesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, classesDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(classesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Classes in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchClasses() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        classes.setId(longCount.incrementAndGet());

        // Create the Classes
        ClassesDTO classesDTO = classesMapper.toDto(classes);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClassesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(classesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Classes in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamClasses() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        classes.setId(longCount.incrementAndGet());

        // Create the Classes
        ClassesDTO classesDTO = classesMapper.toDto(classes);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClassesMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(classesDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Classes in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateClassesWithPatch() throws Exception {
        // Initialize the database
        insertedClasses = classesRepository.saveAndFlush(classes);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the classes using partial update
        Classes partialUpdatedClasses = new Classes();
        partialUpdatedClasses.setId(classes.getId());

        partialUpdatedClasses
            .classCode(UPDATED_CLASS_CODE)
            .classroom(UPDATED_CLASSROOM)
            .credits(UPDATED_CREDITS)
            .numberOfSessions(UPDATED_NUMBER_OF_SESSIONS)
            .totalNumberOfStudents(UPDATED_TOTAL_NUMBER_OF_STUDENTS)
            .endDate(UPDATED_END_DATE)
            .classType(UPDATED_CLASS_TYPE)
            .description(UPDATED_DESCRIPTION)
            .academicYear(UPDATED_ACADEMIC_YEAR)
            .parentId(UPDATED_PARENT_ID);

        restClassesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedClasses.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedClasses))
            )
            .andExpect(status().isOk());

        // Validate the Classes in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertClassesUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedClasses, classes), getPersistedClasses(classes));
    }

    @Test
    @Transactional
    void fullUpdateClassesWithPatch() throws Exception {
        // Initialize the database
        insertedClasses = classesRepository.saveAndFlush(classes);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the classes using partial update
        Classes partialUpdatedClasses = new Classes();
        partialUpdatedClasses.setId(classes.getId());

        partialUpdatedClasses
            .classCode(UPDATED_CLASS_CODE)
            .className(UPDATED_CLASS_NAME)
            .classroom(UPDATED_CLASSROOM)
            .credits(UPDATED_CREDITS)
            .numberOfSessions(UPDATED_NUMBER_OF_SESSIONS)
            .totalNumberOfStudents(UPDATED_TOTAL_NUMBER_OF_STUDENTS)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .classType(UPDATED_CLASS_TYPE)
            .deliveryMode(UPDATED_DELIVERY_MODE)
            .status(UPDATED_STATUS)
            .notes(UPDATED_NOTES)
            .description(UPDATED_DESCRIPTION)
            .academicYear(UPDATED_ACADEMIC_YEAR)
            .parentId(UPDATED_PARENT_ID);

        restClassesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedClasses.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedClasses))
            )
            .andExpect(status().isOk());

        // Validate the Classes in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertClassesUpdatableFieldsEquals(partialUpdatedClasses, getPersistedClasses(partialUpdatedClasses));
    }

    @Test
    @Transactional
    void patchNonExistingClasses() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        classes.setId(longCount.incrementAndGet());

        // Create the Classes
        ClassesDTO classesDTO = classesMapper.toDto(classes);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClassesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, classesDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(classesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Classes in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchClasses() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        classes.setId(longCount.incrementAndGet());

        // Create the Classes
        ClassesDTO classesDTO = classesMapper.toDto(classes);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClassesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(classesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Classes in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamClasses() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        classes.setId(longCount.incrementAndGet());

        // Create the Classes
        ClassesDTO classesDTO = classesMapper.toDto(classes);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClassesMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(classesDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Classes in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteClasses() throws Exception {
        // Initialize the database
        insertedClasses = classesRepository.saveAndFlush(classes);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the classes
        restClassesMockMvc
            .perform(delete(ENTITY_API_URL_ID, classes.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return classesRepository.count();
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

    protected Classes getPersistedClasses(Classes classes) {
        return classesRepository.findById(classes.getId()).orElseThrow();
    }

    protected void assertPersistedClassesToMatchAllProperties(Classes expectedClasses) {
        assertClassesAllPropertiesEquals(expectedClasses, getPersistedClasses(expectedClasses));
    }

    protected void assertPersistedClassesToMatchUpdatableProperties(Classes expectedClasses) {
        assertClassesAllUpdatablePropertiesEquals(expectedClasses, getPersistedClasses(expectedClasses));
    }
}
