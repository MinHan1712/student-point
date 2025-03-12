package student.point.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static student.point.domain.TeachersAsserts.*;
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
import student.point.domain.Faculties;
import student.point.domain.Teachers;
import student.point.domain.enumeration.TeacherPosition;
import student.point.domain.enumeration.TeacherQualification;
import student.point.repository.TeachersRepository;
import student.point.service.dto.TeachersDTO;
import student.point.service.mapper.TeachersMapper;

/**
 * Integration tests for the {@link TeachersResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TeachersResourceIT {

    private static final String DEFAULT_TEACHERS_CODE = "AAAAAAAAAA";
    private static final String UPDATED_TEACHERS_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_NUMBER = "BBBBBBBBBB";

    private static final Instant DEFAULT_START_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_END_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_END_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final TeacherPosition DEFAULT_POSITION = TeacherPosition.Dean;
    private static final TeacherPosition UPDATED_POSITION = TeacherPosition.ViceDean;

    private static final TeacherQualification DEFAULT_QUALIFICATION = TeacherQualification.PhD;
    private static final TeacherQualification UPDATED_QUALIFICATION = TeacherQualification.MSc;

    private static final Boolean DEFAULT_STATUS = false;
    private static final Boolean UPDATED_STATUS = true;

    private static final String DEFAULT_NOTES = "AAAAAAAAAA";
    private static final String UPDATED_NOTES = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/teachers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private TeachersRepository teachersRepository;

    @Autowired
    private TeachersMapper teachersMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTeachersMockMvc;

    private Teachers teachers;

    private Teachers insertedTeachers;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Teachers createEntity() {
        return new Teachers()
            .teachersCode(DEFAULT_TEACHERS_CODE)
            .name(DEFAULT_NAME)
            .email(DEFAULT_EMAIL)
            .phoneNumber(DEFAULT_PHONE_NUMBER)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .position(DEFAULT_POSITION)
            .qualification(DEFAULT_QUALIFICATION)
            .status(DEFAULT_STATUS)
            .notes(DEFAULT_NOTES);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Teachers createUpdatedEntity() {
        return new Teachers()
            .teachersCode(UPDATED_TEACHERS_CODE)
            .name(UPDATED_NAME)
            .email(UPDATED_EMAIL)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .position(UPDATED_POSITION)
            .qualification(UPDATED_QUALIFICATION)
            .status(UPDATED_STATUS)
            .notes(UPDATED_NOTES);
    }

    @BeforeEach
    public void initTest() {
        teachers = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedTeachers != null) {
            teachersRepository.delete(insertedTeachers);
            insertedTeachers = null;
        }
    }

    @Test
    @Transactional
    void createTeachers() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Teachers
        TeachersDTO teachersDTO = teachersMapper.toDto(teachers);
        var returnedTeachersDTO = om.readValue(
            restTeachersMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(teachersDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            TeachersDTO.class
        );

        // Validate the Teachers in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedTeachers = teachersMapper.toEntity(returnedTeachersDTO);
        assertTeachersUpdatableFieldsEquals(returnedTeachers, getPersistedTeachers(returnedTeachers));

        insertedTeachers = returnedTeachers;
    }

    @Test
    @Transactional
    void createTeachersWithExistingId() throws Exception {
        // Create the Teachers with an existing ID
        teachers.setId(1L);
        TeachersDTO teachersDTO = teachersMapper.toDto(teachers);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTeachersMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(teachersDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Teachers in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTeachers() throws Exception {
        // Initialize the database
        insertedTeachers = teachersRepository.saveAndFlush(teachers);

        // Get all the teachersList
        restTeachersMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(teachers.getId().intValue())))
            .andExpect(jsonPath("$.[*].teachersCode").value(hasItem(DEFAULT_TEACHERS_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER)))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].position").value(hasItem(DEFAULT_POSITION.toString())))
            .andExpect(jsonPath("$.[*].qualification").value(hasItem(DEFAULT_QUALIFICATION.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES)));
    }

    @Test
    @Transactional
    void getTeachers() throws Exception {
        // Initialize the database
        insertedTeachers = teachersRepository.saveAndFlush(teachers);

        // Get the teachers
        restTeachersMockMvc
            .perform(get(ENTITY_API_URL_ID, teachers.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(teachers.getId().intValue()))
            .andExpect(jsonPath("$.teachersCode").value(DEFAULT_TEACHERS_CODE))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.position").value(DEFAULT_POSITION.toString()))
            .andExpect(jsonPath("$.qualification").value(DEFAULT_QUALIFICATION.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.notes").value(DEFAULT_NOTES));
    }

    @Test
    @Transactional
    void getTeachersByIdFiltering() throws Exception {
        // Initialize the database
        insertedTeachers = teachersRepository.saveAndFlush(teachers);

        Long id = teachers.getId();

        defaultTeachersFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultTeachersFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultTeachersFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllTeachersByTeachersCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedTeachers = teachersRepository.saveAndFlush(teachers);

        // Get all the teachersList where teachersCode equals to
        defaultTeachersFiltering("teachersCode.equals=" + DEFAULT_TEACHERS_CODE, "teachersCode.equals=" + UPDATED_TEACHERS_CODE);
    }

    @Test
    @Transactional
    void getAllTeachersByTeachersCodeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedTeachers = teachersRepository.saveAndFlush(teachers);

        // Get all the teachersList where teachersCode in
        defaultTeachersFiltering(
            "teachersCode.in=" + DEFAULT_TEACHERS_CODE + "," + UPDATED_TEACHERS_CODE,
            "teachersCode.in=" + UPDATED_TEACHERS_CODE
        );
    }

    @Test
    @Transactional
    void getAllTeachersByTeachersCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedTeachers = teachersRepository.saveAndFlush(teachers);

        // Get all the teachersList where teachersCode is not null
        defaultTeachersFiltering("teachersCode.specified=true", "teachersCode.specified=false");
    }

    @Test
    @Transactional
    void getAllTeachersByTeachersCodeContainsSomething() throws Exception {
        // Initialize the database
        insertedTeachers = teachersRepository.saveAndFlush(teachers);

        // Get all the teachersList where teachersCode contains
        defaultTeachersFiltering("teachersCode.contains=" + DEFAULT_TEACHERS_CODE, "teachersCode.contains=" + UPDATED_TEACHERS_CODE);
    }

    @Test
    @Transactional
    void getAllTeachersByTeachersCodeNotContainsSomething() throws Exception {
        // Initialize the database
        insertedTeachers = teachersRepository.saveAndFlush(teachers);

        // Get all the teachersList where teachersCode does not contain
        defaultTeachersFiltering(
            "teachersCode.doesNotContain=" + UPDATED_TEACHERS_CODE,
            "teachersCode.doesNotContain=" + DEFAULT_TEACHERS_CODE
        );
    }

    @Test
    @Transactional
    void getAllTeachersByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedTeachers = teachersRepository.saveAndFlush(teachers);

        // Get all the teachersList where name equals to
        defaultTeachersFiltering("name.equals=" + DEFAULT_NAME, "name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllTeachersByNameIsInShouldWork() throws Exception {
        // Initialize the database
        insertedTeachers = teachersRepository.saveAndFlush(teachers);

        // Get all the teachersList where name in
        defaultTeachersFiltering("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME, "name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllTeachersByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedTeachers = teachersRepository.saveAndFlush(teachers);

        // Get all the teachersList where name is not null
        defaultTeachersFiltering("name.specified=true", "name.specified=false");
    }

    @Test
    @Transactional
    void getAllTeachersByNameContainsSomething() throws Exception {
        // Initialize the database
        insertedTeachers = teachersRepository.saveAndFlush(teachers);

        // Get all the teachersList where name contains
        defaultTeachersFiltering("name.contains=" + DEFAULT_NAME, "name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllTeachersByNameNotContainsSomething() throws Exception {
        // Initialize the database
        insertedTeachers = teachersRepository.saveAndFlush(teachers);

        // Get all the teachersList where name does not contain
        defaultTeachersFiltering("name.doesNotContain=" + UPDATED_NAME, "name.doesNotContain=" + DEFAULT_NAME);
    }

    @Test
    @Transactional
    void getAllTeachersByEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedTeachers = teachersRepository.saveAndFlush(teachers);

        // Get all the teachersList where email equals to
        defaultTeachersFiltering("email.equals=" + DEFAULT_EMAIL, "email.equals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllTeachersByEmailIsInShouldWork() throws Exception {
        // Initialize the database
        insertedTeachers = teachersRepository.saveAndFlush(teachers);

        // Get all the teachersList where email in
        defaultTeachersFiltering("email.in=" + DEFAULT_EMAIL + "," + UPDATED_EMAIL, "email.in=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllTeachersByEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedTeachers = teachersRepository.saveAndFlush(teachers);

        // Get all the teachersList where email is not null
        defaultTeachersFiltering("email.specified=true", "email.specified=false");
    }

    @Test
    @Transactional
    void getAllTeachersByEmailContainsSomething() throws Exception {
        // Initialize the database
        insertedTeachers = teachersRepository.saveAndFlush(teachers);

        // Get all the teachersList where email contains
        defaultTeachersFiltering("email.contains=" + DEFAULT_EMAIL, "email.contains=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllTeachersByEmailNotContainsSomething() throws Exception {
        // Initialize the database
        insertedTeachers = teachersRepository.saveAndFlush(teachers);

        // Get all the teachersList where email does not contain
        defaultTeachersFiltering("email.doesNotContain=" + UPDATED_EMAIL, "email.doesNotContain=" + DEFAULT_EMAIL);
    }

    @Test
    @Transactional
    void getAllTeachersByPhoneNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedTeachers = teachersRepository.saveAndFlush(teachers);

        // Get all the teachersList where phoneNumber equals to
        defaultTeachersFiltering("phoneNumber.equals=" + DEFAULT_PHONE_NUMBER, "phoneNumber.equals=" + UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    void getAllTeachersByPhoneNumberIsInShouldWork() throws Exception {
        // Initialize the database
        insertedTeachers = teachersRepository.saveAndFlush(teachers);

        // Get all the teachersList where phoneNumber in
        defaultTeachersFiltering(
            "phoneNumber.in=" + DEFAULT_PHONE_NUMBER + "," + UPDATED_PHONE_NUMBER,
            "phoneNumber.in=" + UPDATED_PHONE_NUMBER
        );
    }

    @Test
    @Transactional
    void getAllTeachersByPhoneNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedTeachers = teachersRepository.saveAndFlush(teachers);

        // Get all the teachersList where phoneNumber is not null
        defaultTeachersFiltering("phoneNumber.specified=true", "phoneNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllTeachersByPhoneNumberContainsSomething() throws Exception {
        // Initialize the database
        insertedTeachers = teachersRepository.saveAndFlush(teachers);

        // Get all the teachersList where phoneNumber contains
        defaultTeachersFiltering("phoneNumber.contains=" + DEFAULT_PHONE_NUMBER, "phoneNumber.contains=" + UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    void getAllTeachersByPhoneNumberNotContainsSomething() throws Exception {
        // Initialize the database
        insertedTeachers = teachersRepository.saveAndFlush(teachers);

        // Get all the teachersList where phoneNumber does not contain
        defaultTeachersFiltering(
            "phoneNumber.doesNotContain=" + UPDATED_PHONE_NUMBER,
            "phoneNumber.doesNotContain=" + DEFAULT_PHONE_NUMBER
        );
    }

    @Test
    @Transactional
    void getAllTeachersByStartDateIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedTeachers = teachersRepository.saveAndFlush(teachers);

        // Get all the teachersList where startDate equals to
        defaultTeachersFiltering("startDate.equals=" + DEFAULT_START_DATE, "startDate.equals=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    void getAllTeachersByStartDateIsInShouldWork() throws Exception {
        // Initialize the database
        insertedTeachers = teachersRepository.saveAndFlush(teachers);

        // Get all the teachersList where startDate in
        defaultTeachersFiltering("startDate.in=" + DEFAULT_START_DATE + "," + UPDATED_START_DATE, "startDate.in=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    void getAllTeachersByStartDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedTeachers = teachersRepository.saveAndFlush(teachers);

        // Get all the teachersList where startDate is not null
        defaultTeachersFiltering("startDate.specified=true", "startDate.specified=false");
    }

    @Test
    @Transactional
    void getAllTeachersByEndDateIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedTeachers = teachersRepository.saveAndFlush(teachers);

        // Get all the teachersList where endDate equals to
        defaultTeachersFiltering("endDate.equals=" + DEFAULT_END_DATE, "endDate.equals=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    void getAllTeachersByEndDateIsInShouldWork() throws Exception {
        // Initialize the database
        insertedTeachers = teachersRepository.saveAndFlush(teachers);

        // Get all the teachersList where endDate in
        defaultTeachersFiltering("endDate.in=" + DEFAULT_END_DATE + "," + UPDATED_END_DATE, "endDate.in=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    void getAllTeachersByEndDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedTeachers = teachersRepository.saveAndFlush(teachers);

        // Get all the teachersList where endDate is not null
        defaultTeachersFiltering("endDate.specified=true", "endDate.specified=false");
    }

    @Test
    @Transactional
    void getAllTeachersByPositionIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedTeachers = teachersRepository.saveAndFlush(teachers);

        // Get all the teachersList where position equals to
        defaultTeachersFiltering("position.equals=" + DEFAULT_POSITION, "position.equals=" + UPDATED_POSITION);
    }

    @Test
    @Transactional
    void getAllTeachersByPositionIsInShouldWork() throws Exception {
        // Initialize the database
        insertedTeachers = teachersRepository.saveAndFlush(teachers);

        // Get all the teachersList where position in
        defaultTeachersFiltering("position.in=" + DEFAULT_POSITION + "," + UPDATED_POSITION, "position.in=" + UPDATED_POSITION);
    }

    @Test
    @Transactional
    void getAllTeachersByPositionIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedTeachers = teachersRepository.saveAndFlush(teachers);

        // Get all the teachersList where position is not null
        defaultTeachersFiltering("position.specified=true", "position.specified=false");
    }

    @Test
    @Transactional
    void getAllTeachersByQualificationIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedTeachers = teachersRepository.saveAndFlush(teachers);

        // Get all the teachersList where qualification equals to
        defaultTeachersFiltering("qualification.equals=" + DEFAULT_QUALIFICATION, "qualification.equals=" + UPDATED_QUALIFICATION);
    }

    @Test
    @Transactional
    void getAllTeachersByQualificationIsInShouldWork() throws Exception {
        // Initialize the database
        insertedTeachers = teachersRepository.saveAndFlush(teachers);

        // Get all the teachersList where qualification in
        defaultTeachersFiltering(
            "qualification.in=" + DEFAULT_QUALIFICATION + "," + UPDATED_QUALIFICATION,
            "qualification.in=" + UPDATED_QUALIFICATION
        );
    }

    @Test
    @Transactional
    void getAllTeachersByQualificationIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedTeachers = teachersRepository.saveAndFlush(teachers);

        // Get all the teachersList where qualification is not null
        defaultTeachersFiltering("qualification.specified=true", "qualification.specified=false");
    }

    @Test
    @Transactional
    void getAllTeachersByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedTeachers = teachersRepository.saveAndFlush(teachers);

        // Get all the teachersList where status equals to
        defaultTeachersFiltering("status.equals=" + DEFAULT_STATUS, "status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllTeachersByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        insertedTeachers = teachersRepository.saveAndFlush(teachers);

        // Get all the teachersList where status in
        defaultTeachersFiltering("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS, "status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllTeachersByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedTeachers = teachersRepository.saveAndFlush(teachers);

        // Get all the teachersList where status is not null
        defaultTeachersFiltering("status.specified=true", "status.specified=false");
    }

    @Test
    @Transactional
    void getAllTeachersByNotesIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedTeachers = teachersRepository.saveAndFlush(teachers);

        // Get all the teachersList where notes equals to
        defaultTeachersFiltering("notes.equals=" + DEFAULT_NOTES, "notes.equals=" + UPDATED_NOTES);
    }

    @Test
    @Transactional
    void getAllTeachersByNotesIsInShouldWork() throws Exception {
        // Initialize the database
        insertedTeachers = teachersRepository.saveAndFlush(teachers);

        // Get all the teachersList where notes in
        defaultTeachersFiltering("notes.in=" + DEFAULT_NOTES + "," + UPDATED_NOTES, "notes.in=" + UPDATED_NOTES);
    }

    @Test
    @Transactional
    void getAllTeachersByNotesIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedTeachers = teachersRepository.saveAndFlush(teachers);

        // Get all the teachersList where notes is not null
        defaultTeachersFiltering("notes.specified=true", "notes.specified=false");
    }

    @Test
    @Transactional
    void getAllTeachersByNotesContainsSomething() throws Exception {
        // Initialize the database
        insertedTeachers = teachersRepository.saveAndFlush(teachers);

        // Get all the teachersList where notes contains
        defaultTeachersFiltering("notes.contains=" + DEFAULT_NOTES, "notes.contains=" + UPDATED_NOTES);
    }

    @Test
    @Transactional
    void getAllTeachersByNotesNotContainsSomething() throws Exception {
        // Initialize the database
        insertedTeachers = teachersRepository.saveAndFlush(teachers);

        // Get all the teachersList where notes does not contain
        defaultTeachersFiltering("notes.doesNotContain=" + UPDATED_NOTES, "notes.doesNotContain=" + DEFAULT_NOTES);
    }

    @Test
    @Transactional
    void getAllTeachersByFacultiesIsEqualToSomething() throws Exception {
        Faculties faculties;
        if (TestUtil.findAll(em, Faculties.class).isEmpty()) {
            teachersRepository.saveAndFlush(teachers);
            faculties = FacultiesResourceIT.createEntity();
        } else {
            faculties = TestUtil.findAll(em, Faculties.class).get(0);
        }
        em.persist(faculties);
        em.flush();
        teachers.setFaculties(faculties);
        teachersRepository.saveAndFlush(teachers);
        Long facultiesId = faculties.getId();
        // Get all the teachersList where faculties equals to facultiesId
        defaultTeachersShouldBeFound("facultiesId.equals=" + facultiesId);

        // Get all the teachersList where faculties equals to (facultiesId + 1)
        defaultTeachersShouldNotBeFound("facultiesId.equals=" + (facultiesId + 1));
    }

    private void defaultTeachersFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultTeachersShouldBeFound(shouldBeFound);
        defaultTeachersShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTeachersShouldBeFound(String filter) throws Exception {
        restTeachersMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(teachers.getId().intValue())))
            .andExpect(jsonPath("$.[*].teachersCode").value(hasItem(DEFAULT_TEACHERS_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER)))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].position").value(hasItem(DEFAULT_POSITION.toString())))
            .andExpect(jsonPath("$.[*].qualification").value(hasItem(DEFAULT_QUALIFICATION.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES)));

        // Check, that the count call also returns 1
        restTeachersMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTeachersShouldNotBeFound(String filter) throws Exception {
        restTeachersMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTeachersMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingTeachers() throws Exception {
        // Get the teachers
        restTeachersMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTeachers() throws Exception {
        // Initialize the database
        insertedTeachers = teachersRepository.saveAndFlush(teachers);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the teachers
        Teachers updatedTeachers = teachersRepository.findById(teachers.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedTeachers are not directly saved in db
        em.detach(updatedTeachers);
        updatedTeachers
            .teachersCode(UPDATED_TEACHERS_CODE)
            .name(UPDATED_NAME)
            .email(UPDATED_EMAIL)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .position(UPDATED_POSITION)
            .qualification(UPDATED_QUALIFICATION)
            .status(UPDATED_STATUS)
            .notes(UPDATED_NOTES);
        TeachersDTO teachersDTO = teachersMapper.toDto(updatedTeachers);

        restTeachersMockMvc
            .perform(
                put(ENTITY_API_URL_ID, teachersDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(teachersDTO))
            )
            .andExpect(status().isOk());

        // Validate the Teachers in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedTeachersToMatchAllProperties(updatedTeachers);
    }

    @Test
    @Transactional
    void putNonExistingTeachers() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        teachers.setId(longCount.incrementAndGet());

        // Create the Teachers
        TeachersDTO teachersDTO = teachersMapper.toDto(teachers);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTeachersMockMvc
            .perform(
                put(ENTITY_API_URL_ID, teachersDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(teachersDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Teachers in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTeachers() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        teachers.setId(longCount.incrementAndGet());

        // Create the Teachers
        TeachersDTO teachersDTO = teachersMapper.toDto(teachers);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTeachersMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(teachersDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Teachers in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTeachers() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        teachers.setId(longCount.incrementAndGet());

        // Create the Teachers
        TeachersDTO teachersDTO = teachersMapper.toDto(teachers);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTeachersMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(teachersDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Teachers in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTeachersWithPatch() throws Exception {
        // Initialize the database
        insertedTeachers = teachersRepository.saveAndFlush(teachers);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the teachers using partial update
        Teachers partialUpdatedTeachers = new Teachers();
        partialUpdatedTeachers.setId(teachers.getId());

        partialUpdatedTeachers
            .teachersCode(UPDATED_TEACHERS_CODE)
            .email(UPDATED_EMAIL)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .qualification(UPDATED_QUALIFICATION);

        restTeachersMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTeachers.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTeachers))
            )
            .andExpect(status().isOk());

        // Validate the Teachers in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTeachersUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedTeachers, teachers), getPersistedTeachers(teachers));
    }

    @Test
    @Transactional
    void fullUpdateTeachersWithPatch() throws Exception {
        // Initialize the database
        insertedTeachers = teachersRepository.saveAndFlush(teachers);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the teachers using partial update
        Teachers partialUpdatedTeachers = new Teachers();
        partialUpdatedTeachers.setId(teachers.getId());

        partialUpdatedTeachers
            .teachersCode(UPDATED_TEACHERS_CODE)
            .name(UPDATED_NAME)
            .email(UPDATED_EMAIL)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .position(UPDATED_POSITION)
            .qualification(UPDATED_QUALIFICATION)
            .status(UPDATED_STATUS)
            .notes(UPDATED_NOTES);

        restTeachersMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTeachers.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTeachers))
            )
            .andExpect(status().isOk());

        // Validate the Teachers in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTeachersUpdatableFieldsEquals(partialUpdatedTeachers, getPersistedTeachers(partialUpdatedTeachers));
    }

    @Test
    @Transactional
    void patchNonExistingTeachers() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        teachers.setId(longCount.incrementAndGet());

        // Create the Teachers
        TeachersDTO teachersDTO = teachersMapper.toDto(teachers);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTeachersMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, teachersDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(teachersDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Teachers in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTeachers() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        teachers.setId(longCount.incrementAndGet());

        // Create the Teachers
        TeachersDTO teachersDTO = teachersMapper.toDto(teachers);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTeachersMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(teachersDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Teachers in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTeachers() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        teachers.setId(longCount.incrementAndGet());

        // Create the Teachers
        TeachersDTO teachersDTO = teachersMapper.toDto(teachers);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTeachersMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(teachersDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Teachers in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTeachers() throws Exception {
        // Initialize the database
        insertedTeachers = teachersRepository.saveAndFlush(teachers);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the teachers
        restTeachersMockMvc
            .perform(delete(ENTITY_API_URL_ID, teachers.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return teachersRepository.count();
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

    protected Teachers getPersistedTeachers(Teachers teachers) {
        return teachersRepository.findById(teachers.getId()).orElseThrow();
    }

    protected void assertPersistedTeachersToMatchAllProperties(Teachers expectedTeachers) {
        assertTeachersAllPropertiesEquals(expectedTeachers, getPersistedTeachers(expectedTeachers));
    }

    protected void assertPersistedTeachersToMatchUpdatableProperties(Teachers expectedTeachers) {
        assertTeachersAllUpdatablePropertiesEquals(expectedTeachers, getPersistedTeachers(expectedTeachers));
    }
}
