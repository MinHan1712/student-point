package student.point.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static student.point.domain.FacultiesAsserts.*;
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
import student.point.domain.Course;
import student.point.domain.Faculties;
import student.point.repository.FacultiesRepository;
import student.point.service.dto.FacultiesDTO;
import student.point.service.mapper.FacultiesMapper;

/**
 * Integration tests for the {@link FacultiesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FacultiesResourceIT {

    private static final String DEFAULT_FACULTY_CODE = "AAAAAAAAAA";
    private static final String UPDATED_FACULTY_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_FACULTY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FACULTY_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Instant DEFAULT_ESTABLISHED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ESTABLISHED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_PHONE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_LOCATION = "AAAAAAAAAA";
    private static final String UPDATED_LOCATION = "BBBBBBBBBB";

    private static final String DEFAULT_NOTES = "AAAAAAAAAA";
    private static final String UPDATED_NOTES = "BBBBBBBBBB";

    private static final Long DEFAULT_PARENT_ID = 1L;
    private static final Long UPDATED_PARENT_ID = 2L;
    private static final Long SMALLER_PARENT_ID = 1L - 1L;

    private static final String ENTITY_API_URL = "/api/faculties";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private FacultiesRepository facultiesRepository;

    @Autowired
    private FacultiesMapper facultiesMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFacultiesMockMvc;

    private Faculties faculties;

    private Faculties insertedFaculties;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Faculties createEntity() {
        return new Faculties()
            .facultyCode(DEFAULT_FACULTY_CODE)
            .facultyName(DEFAULT_FACULTY_NAME)
            .description(DEFAULT_DESCRIPTION)
            .establishedDate(DEFAULT_ESTABLISHED_DATE)
            .phoneNumber(DEFAULT_PHONE_NUMBER)
            .location(DEFAULT_LOCATION)
            .notes(DEFAULT_NOTES)
            .parentId(DEFAULT_PARENT_ID);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Faculties createUpdatedEntity() {
        return new Faculties()
            .facultyCode(UPDATED_FACULTY_CODE)
            .facultyName(UPDATED_FACULTY_NAME)
            .description(UPDATED_DESCRIPTION)
            .establishedDate(UPDATED_ESTABLISHED_DATE)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .location(UPDATED_LOCATION)
            .notes(UPDATED_NOTES)
            .parentId(UPDATED_PARENT_ID);
    }

    @BeforeEach
    public void initTest() {
        faculties = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedFaculties != null) {
            facultiesRepository.delete(insertedFaculties);
            insertedFaculties = null;
        }
    }

    @Test
    @Transactional
    void createFaculties() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Faculties
        FacultiesDTO facultiesDTO = facultiesMapper.toDto(faculties);
        var returnedFacultiesDTO = om.readValue(
            restFacultiesMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(facultiesDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            FacultiesDTO.class
        );

        // Validate the Faculties in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedFaculties = facultiesMapper.toEntity(returnedFacultiesDTO);
        assertFacultiesUpdatableFieldsEquals(returnedFaculties, getPersistedFaculties(returnedFaculties));

        insertedFaculties = returnedFaculties;
    }

    @Test
    @Transactional
    void createFacultiesWithExistingId() throws Exception {
        // Create the Faculties with an existing ID
        faculties.setId(1L);
        FacultiesDTO facultiesDTO = facultiesMapper.toDto(faculties);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFacultiesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(facultiesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Faculties in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllFaculties() throws Exception {
        // Initialize the database
        insertedFaculties = facultiesRepository.saveAndFlush(faculties);

        // Get all the facultiesList
        restFacultiesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(faculties.getId().intValue())))
            .andExpect(jsonPath("$.[*].facultyCode").value(hasItem(DEFAULT_FACULTY_CODE)))
            .andExpect(jsonPath("$.[*].facultyName").value(hasItem(DEFAULT_FACULTY_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].establishedDate").value(hasItem(DEFAULT_ESTABLISHED_DATE.toString())))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER)))
            .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION)))
            .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES)))
            .andExpect(jsonPath("$.[*].parentId").value(hasItem(DEFAULT_PARENT_ID.intValue())));
    }

    @Test
    @Transactional
    void getFaculties() throws Exception {
        // Initialize the database
        insertedFaculties = facultiesRepository.saveAndFlush(faculties);

        // Get the faculties
        restFacultiesMockMvc
            .perform(get(ENTITY_API_URL_ID, faculties.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(faculties.getId().intValue()))
            .andExpect(jsonPath("$.facultyCode").value(DEFAULT_FACULTY_CODE))
            .andExpect(jsonPath("$.facultyName").value(DEFAULT_FACULTY_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.establishedDate").value(DEFAULT_ESTABLISHED_DATE.toString()))
            .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER))
            .andExpect(jsonPath("$.location").value(DEFAULT_LOCATION))
            .andExpect(jsonPath("$.notes").value(DEFAULT_NOTES))
            .andExpect(jsonPath("$.parentId").value(DEFAULT_PARENT_ID.intValue()));
    }

    @Test
    @Transactional
    void getFacultiesByIdFiltering() throws Exception {
        // Initialize the database
        insertedFaculties = facultiesRepository.saveAndFlush(faculties);

        Long id = faculties.getId();

        defaultFacultiesFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultFacultiesFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultFacultiesFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllFacultiesByFacultyCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedFaculties = facultiesRepository.saveAndFlush(faculties);

        // Get all the facultiesList where facultyCode equals to
        defaultFacultiesFiltering("facultyCode.equals=" + DEFAULT_FACULTY_CODE, "facultyCode.equals=" + UPDATED_FACULTY_CODE);
    }

    @Test
    @Transactional
    void getAllFacultiesByFacultyCodeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedFaculties = facultiesRepository.saveAndFlush(faculties);

        // Get all the facultiesList where facultyCode in
        defaultFacultiesFiltering(
            "facultyCode.in=" + DEFAULT_FACULTY_CODE + "," + UPDATED_FACULTY_CODE,
            "facultyCode.in=" + UPDATED_FACULTY_CODE
        );
    }

    @Test
    @Transactional
    void getAllFacultiesByFacultyCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedFaculties = facultiesRepository.saveAndFlush(faculties);

        // Get all the facultiesList where facultyCode is not null
        defaultFacultiesFiltering("facultyCode.specified=true", "facultyCode.specified=false");
    }

    @Test
    @Transactional
    void getAllFacultiesByFacultyCodeContainsSomething() throws Exception {
        // Initialize the database
        insertedFaculties = facultiesRepository.saveAndFlush(faculties);

        // Get all the facultiesList where facultyCode contains
        defaultFacultiesFiltering("facultyCode.contains=" + DEFAULT_FACULTY_CODE, "facultyCode.contains=" + UPDATED_FACULTY_CODE);
    }

    @Test
    @Transactional
    void getAllFacultiesByFacultyCodeNotContainsSomething() throws Exception {
        // Initialize the database
        insertedFaculties = facultiesRepository.saveAndFlush(faculties);

        // Get all the facultiesList where facultyCode does not contain
        defaultFacultiesFiltering(
            "facultyCode.doesNotContain=" + UPDATED_FACULTY_CODE,
            "facultyCode.doesNotContain=" + DEFAULT_FACULTY_CODE
        );
    }

    @Test
    @Transactional
    void getAllFacultiesByFacultyNameIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedFaculties = facultiesRepository.saveAndFlush(faculties);

        // Get all the facultiesList where facultyName equals to
        defaultFacultiesFiltering("facultyName.equals=" + DEFAULT_FACULTY_NAME, "facultyName.equals=" + UPDATED_FACULTY_NAME);
    }

    @Test
    @Transactional
    void getAllFacultiesByFacultyNameIsInShouldWork() throws Exception {
        // Initialize the database
        insertedFaculties = facultiesRepository.saveAndFlush(faculties);

        // Get all the facultiesList where facultyName in
        defaultFacultiesFiltering(
            "facultyName.in=" + DEFAULT_FACULTY_NAME + "," + UPDATED_FACULTY_NAME,
            "facultyName.in=" + UPDATED_FACULTY_NAME
        );
    }

    @Test
    @Transactional
    void getAllFacultiesByFacultyNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedFaculties = facultiesRepository.saveAndFlush(faculties);

        // Get all the facultiesList where facultyName is not null
        defaultFacultiesFiltering("facultyName.specified=true", "facultyName.specified=false");
    }

    @Test
    @Transactional
    void getAllFacultiesByFacultyNameContainsSomething() throws Exception {
        // Initialize the database
        insertedFaculties = facultiesRepository.saveAndFlush(faculties);

        // Get all the facultiesList where facultyName contains
        defaultFacultiesFiltering("facultyName.contains=" + DEFAULT_FACULTY_NAME, "facultyName.contains=" + UPDATED_FACULTY_NAME);
    }

    @Test
    @Transactional
    void getAllFacultiesByFacultyNameNotContainsSomething() throws Exception {
        // Initialize the database
        insertedFaculties = facultiesRepository.saveAndFlush(faculties);

        // Get all the facultiesList where facultyName does not contain
        defaultFacultiesFiltering(
            "facultyName.doesNotContain=" + UPDATED_FACULTY_NAME,
            "facultyName.doesNotContain=" + DEFAULT_FACULTY_NAME
        );
    }

    @Test
    @Transactional
    void getAllFacultiesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedFaculties = facultiesRepository.saveAndFlush(faculties);

        // Get all the facultiesList where description equals to
        defaultFacultiesFiltering("description.equals=" + DEFAULT_DESCRIPTION, "description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllFacultiesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        insertedFaculties = facultiesRepository.saveAndFlush(faculties);

        // Get all the facultiesList where description in
        defaultFacultiesFiltering(
            "description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION,
            "description.in=" + UPDATED_DESCRIPTION
        );
    }

    @Test
    @Transactional
    void getAllFacultiesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedFaculties = facultiesRepository.saveAndFlush(faculties);

        // Get all the facultiesList where description is not null
        defaultFacultiesFiltering("description.specified=true", "description.specified=false");
    }

    @Test
    @Transactional
    void getAllFacultiesByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        insertedFaculties = facultiesRepository.saveAndFlush(faculties);

        // Get all the facultiesList where description contains
        defaultFacultiesFiltering("description.contains=" + DEFAULT_DESCRIPTION, "description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllFacultiesByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        insertedFaculties = facultiesRepository.saveAndFlush(faculties);

        // Get all the facultiesList where description does not contain
        defaultFacultiesFiltering("description.doesNotContain=" + UPDATED_DESCRIPTION, "description.doesNotContain=" + DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllFacultiesByEstablishedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedFaculties = facultiesRepository.saveAndFlush(faculties);

        // Get all the facultiesList where establishedDate equals to
        defaultFacultiesFiltering(
            "establishedDate.equals=" + DEFAULT_ESTABLISHED_DATE,
            "establishedDate.equals=" + UPDATED_ESTABLISHED_DATE
        );
    }

    @Test
    @Transactional
    void getAllFacultiesByEstablishedDateIsInShouldWork() throws Exception {
        // Initialize the database
        insertedFaculties = facultiesRepository.saveAndFlush(faculties);

        // Get all the facultiesList where establishedDate in
        defaultFacultiesFiltering(
            "establishedDate.in=" + DEFAULT_ESTABLISHED_DATE + "," + UPDATED_ESTABLISHED_DATE,
            "establishedDate.in=" + UPDATED_ESTABLISHED_DATE
        );
    }

    @Test
    @Transactional
    void getAllFacultiesByEstablishedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedFaculties = facultiesRepository.saveAndFlush(faculties);

        // Get all the facultiesList where establishedDate is not null
        defaultFacultiesFiltering("establishedDate.specified=true", "establishedDate.specified=false");
    }

    @Test
    @Transactional
    void getAllFacultiesByPhoneNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedFaculties = facultiesRepository.saveAndFlush(faculties);

        // Get all the facultiesList where phoneNumber equals to
        defaultFacultiesFiltering("phoneNumber.equals=" + DEFAULT_PHONE_NUMBER, "phoneNumber.equals=" + UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    void getAllFacultiesByPhoneNumberIsInShouldWork() throws Exception {
        // Initialize the database
        insertedFaculties = facultiesRepository.saveAndFlush(faculties);

        // Get all the facultiesList where phoneNumber in
        defaultFacultiesFiltering(
            "phoneNumber.in=" + DEFAULT_PHONE_NUMBER + "," + UPDATED_PHONE_NUMBER,
            "phoneNumber.in=" + UPDATED_PHONE_NUMBER
        );
    }

    @Test
    @Transactional
    void getAllFacultiesByPhoneNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedFaculties = facultiesRepository.saveAndFlush(faculties);

        // Get all the facultiesList where phoneNumber is not null
        defaultFacultiesFiltering("phoneNumber.specified=true", "phoneNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllFacultiesByPhoneNumberContainsSomething() throws Exception {
        // Initialize the database
        insertedFaculties = facultiesRepository.saveAndFlush(faculties);

        // Get all the facultiesList where phoneNumber contains
        defaultFacultiesFiltering("phoneNumber.contains=" + DEFAULT_PHONE_NUMBER, "phoneNumber.contains=" + UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    void getAllFacultiesByPhoneNumberNotContainsSomething() throws Exception {
        // Initialize the database
        insertedFaculties = facultiesRepository.saveAndFlush(faculties);

        // Get all the facultiesList where phoneNumber does not contain
        defaultFacultiesFiltering(
            "phoneNumber.doesNotContain=" + UPDATED_PHONE_NUMBER,
            "phoneNumber.doesNotContain=" + DEFAULT_PHONE_NUMBER
        );
    }

    @Test
    @Transactional
    void getAllFacultiesByLocationIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedFaculties = facultiesRepository.saveAndFlush(faculties);

        // Get all the facultiesList where location equals to
        defaultFacultiesFiltering("location.equals=" + DEFAULT_LOCATION, "location.equals=" + UPDATED_LOCATION);
    }

    @Test
    @Transactional
    void getAllFacultiesByLocationIsInShouldWork() throws Exception {
        // Initialize the database
        insertedFaculties = facultiesRepository.saveAndFlush(faculties);

        // Get all the facultiesList where location in
        defaultFacultiesFiltering("location.in=" + DEFAULT_LOCATION + "," + UPDATED_LOCATION, "location.in=" + UPDATED_LOCATION);
    }

    @Test
    @Transactional
    void getAllFacultiesByLocationIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedFaculties = facultiesRepository.saveAndFlush(faculties);

        // Get all the facultiesList where location is not null
        defaultFacultiesFiltering("location.specified=true", "location.specified=false");
    }

    @Test
    @Transactional
    void getAllFacultiesByLocationContainsSomething() throws Exception {
        // Initialize the database
        insertedFaculties = facultiesRepository.saveAndFlush(faculties);

        // Get all the facultiesList where location contains
        defaultFacultiesFiltering("location.contains=" + DEFAULT_LOCATION, "location.contains=" + UPDATED_LOCATION);
    }

    @Test
    @Transactional
    void getAllFacultiesByLocationNotContainsSomething() throws Exception {
        // Initialize the database
        insertedFaculties = facultiesRepository.saveAndFlush(faculties);

        // Get all the facultiesList where location does not contain
        defaultFacultiesFiltering("location.doesNotContain=" + UPDATED_LOCATION, "location.doesNotContain=" + DEFAULT_LOCATION);
    }

    @Test
    @Transactional
    void getAllFacultiesByNotesIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedFaculties = facultiesRepository.saveAndFlush(faculties);

        // Get all the facultiesList where notes equals to
        defaultFacultiesFiltering("notes.equals=" + DEFAULT_NOTES, "notes.equals=" + UPDATED_NOTES);
    }

    @Test
    @Transactional
    void getAllFacultiesByNotesIsInShouldWork() throws Exception {
        // Initialize the database
        insertedFaculties = facultiesRepository.saveAndFlush(faculties);

        // Get all the facultiesList where notes in
        defaultFacultiesFiltering("notes.in=" + DEFAULT_NOTES + "," + UPDATED_NOTES, "notes.in=" + UPDATED_NOTES);
    }

    @Test
    @Transactional
    void getAllFacultiesByNotesIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedFaculties = facultiesRepository.saveAndFlush(faculties);

        // Get all the facultiesList where notes is not null
        defaultFacultiesFiltering("notes.specified=true", "notes.specified=false");
    }

    @Test
    @Transactional
    void getAllFacultiesByNotesContainsSomething() throws Exception {
        // Initialize the database
        insertedFaculties = facultiesRepository.saveAndFlush(faculties);

        // Get all the facultiesList where notes contains
        defaultFacultiesFiltering("notes.contains=" + DEFAULT_NOTES, "notes.contains=" + UPDATED_NOTES);
    }

    @Test
    @Transactional
    void getAllFacultiesByNotesNotContainsSomething() throws Exception {
        // Initialize the database
        insertedFaculties = facultiesRepository.saveAndFlush(faculties);

        // Get all the facultiesList where notes does not contain
        defaultFacultiesFiltering("notes.doesNotContain=" + UPDATED_NOTES, "notes.doesNotContain=" + DEFAULT_NOTES);
    }

    @Test
    @Transactional
    void getAllFacultiesByParentIdIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedFaculties = facultiesRepository.saveAndFlush(faculties);

        // Get all the facultiesList where parentId equals to
        defaultFacultiesFiltering("parentId.equals=" + DEFAULT_PARENT_ID, "parentId.equals=" + UPDATED_PARENT_ID);
    }

    @Test
    @Transactional
    void getAllFacultiesByParentIdIsInShouldWork() throws Exception {
        // Initialize the database
        insertedFaculties = facultiesRepository.saveAndFlush(faculties);

        // Get all the facultiesList where parentId in
        defaultFacultiesFiltering("parentId.in=" + DEFAULT_PARENT_ID + "," + UPDATED_PARENT_ID, "parentId.in=" + UPDATED_PARENT_ID);
    }

    @Test
    @Transactional
    void getAllFacultiesByParentIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedFaculties = facultiesRepository.saveAndFlush(faculties);

        // Get all the facultiesList where parentId is not null
        defaultFacultiesFiltering("parentId.specified=true", "parentId.specified=false");
    }

    @Test
    @Transactional
    void getAllFacultiesByParentIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedFaculties = facultiesRepository.saveAndFlush(faculties);

        // Get all the facultiesList where parentId is greater than or equal to
        defaultFacultiesFiltering("parentId.greaterThanOrEqual=" + DEFAULT_PARENT_ID, "parentId.greaterThanOrEqual=" + UPDATED_PARENT_ID);
    }

    @Test
    @Transactional
    void getAllFacultiesByParentIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedFaculties = facultiesRepository.saveAndFlush(faculties);

        // Get all the facultiesList where parentId is less than or equal to
        defaultFacultiesFiltering("parentId.lessThanOrEqual=" + DEFAULT_PARENT_ID, "parentId.lessThanOrEqual=" + SMALLER_PARENT_ID);
    }

    @Test
    @Transactional
    void getAllFacultiesByParentIdIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedFaculties = facultiesRepository.saveAndFlush(faculties);

        // Get all the facultiesList where parentId is less than
        defaultFacultiesFiltering("parentId.lessThan=" + UPDATED_PARENT_ID, "parentId.lessThan=" + DEFAULT_PARENT_ID);
    }

    @Test
    @Transactional
    void getAllFacultiesByParentIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedFaculties = facultiesRepository.saveAndFlush(faculties);

        // Get all the facultiesList where parentId is greater than
        defaultFacultiesFiltering("parentId.greaterThan=" + SMALLER_PARENT_ID, "parentId.greaterThan=" + DEFAULT_PARENT_ID);
    }

    @Test
    @Transactional
    void getAllFacultiesByCourseIsEqualToSomething() throws Exception {
        Course course;
        if (TestUtil.findAll(em, Course.class).isEmpty()) {
            facultiesRepository.saveAndFlush(faculties);
            course = CourseResourceIT.createEntity();
        } else {
            course = TestUtil.findAll(em, Course.class).get(0);
        }
        em.persist(course);
        em.flush();
        faculties.setCourse(course);
        facultiesRepository.saveAndFlush(faculties);
        Long courseId = course.getId();
        // Get all the facultiesList where course equals to courseId
        defaultFacultiesShouldBeFound("courseId.equals=" + courseId);

        // Get all the facultiesList where course equals to (courseId + 1)
        defaultFacultiesShouldNotBeFound("courseId.equals=" + (courseId + 1));
    }

    private void defaultFacultiesFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultFacultiesShouldBeFound(shouldBeFound);
        defaultFacultiesShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultFacultiesShouldBeFound(String filter) throws Exception {
        restFacultiesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(faculties.getId().intValue())))
            .andExpect(jsonPath("$.[*].facultyCode").value(hasItem(DEFAULT_FACULTY_CODE)))
            .andExpect(jsonPath("$.[*].facultyName").value(hasItem(DEFAULT_FACULTY_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].establishedDate").value(hasItem(DEFAULT_ESTABLISHED_DATE.toString())))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER)))
            .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION)))
            .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES)))
            .andExpect(jsonPath("$.[*].parentId").value(hasItem(DEFAULT_PARENT_ID.intValue())));

        // Check, that the count call also returns 1
        restFacultiesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultFacultiesShouldNotBeFound(String filter) throws Exception {
        restFacultiesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restFacultiesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingFaculties() throws Exception {
        // Get the faculties
        restFacultiesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingFaculties() throws Exception {
        // Initialize the database
        insertedFaculties = facultiesRepository.saveAndFlush(faculties);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the faculties
        Faculties updatedFaculties = facultiesRepository.findById(faculties.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedFaculties are not directly saved in db
        em.detach(updatedFaculties);
        updatedFaculties
            .facultyCode(UPDATED_FACULTY_CODE)
            .facultyName(UPDATED_FACULTY_NAME)
            .description(UPDATED_DESCRIPTION)
            .establishedDate(UPDATED_ESTABLISHED_DATE)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .location(UPDATED_LOCATION)
            .notes(UPDATED_NOTES)
            .parentId(UPDATED_PARENT_ID);
        FacultiesDTO facultiesDTO = facultiesMapper.toDto(updatedFaculties);

        restFacultiesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, facultiesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(facultiesDTO))
            )
            .andExpect(status().isOk());

        // Validate the Faculties in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedFacultiesToMatchAllProperties(updatedFaculties);
    }

    @Test
    @Transactional
    void putNonExistingFaculties() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        faculties.setId(longCount.incrementAndGet());

        // Create the Faculties
        FacultiesDTO facultiesDTO = facultiesMapper.toDto(faculties);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFacultiesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, facultiesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(facultiesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Faculties in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFaculties() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        faculties.setId(longCount.incrementAndGet());

        // Create the Faculties
        FacultiesDTO facultiesDTO = facultiesMapper.toDto(faculties);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFacultiesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(facultiesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Faculties in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFaculties() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        faculties.setId(longCount.incrementAndGet());

        // Create the Faculties
        FacultiesDTO facultiesDTO = facultiesMapper.toDto(faculties);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFacultiesMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(facultiesDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Faculties in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFacultiesWithPatch() throws Exception {
        // Initialize the database
        insertedFaculties = facultiesRepository.saveAndFlush(faculties);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the faculties using partial update
        Faculties partialUpdatedFaculties = new Faculties();
        partialUpdatedFaculties.setId(faculties.getId());

        partialUpdatedFaculties
            .facultyCode(UPDATED_FACULTY_CODE)
            .facultyName(UPDATED_FACULTY_NAME)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .location(UPDATED_LOCATION)
            .parentId(UPDATED_PARENT_ID);

        restFacultiesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFaculties.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedFaculties))
            )
            .andExpect(status().isOk());

        // Validate the Faculties in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertFacultiesUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedFaculties, faculties),
            getPersistedFaculties(faculties)
        );
    }

    @Test
    @Transactional
    void fullUpdateFacultiesWithPatch() throws Exception {
        // Initialize the database
        insertedFaculties = facultiesRepository.saveAndFlush(faculties);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the faculties using partial update
        Faculties partialUpdatedFaculties = new Faculties();
        partialUpdatedFaculties.setId(faculties.getId());

        partialUpdatedFaculties
            .facultyCode(UPDATED_FACULTY_CODE)
            .facultyName(UPDATED_FACULTY_NAME)
            .description(UPDATED_DESCRIPTION)
            .establishedDate(UPDATED_ESTABLISHED_DATE)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .location(UPDATED_LOCATION)
            .notes(UPDATED_NOTES)
            .parentId(UPDATED_PARENT_ID);

        restFacultiesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFaculties.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedFaculties))
            )
            .andExpect(status().isOk());

        // Validate the Faculties in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertFacultiesUpdatableFieldsEquals(partialUpdatedFaculties, getPersistedFaculties(partialUpdatedFaculties));
    }

    @Test
    @Transactional
    void patchNonExistingFaculties() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        faculties.setId(longCount.incrementAndGet());

        // Create the Faculties
        FacultiesDTO facultiesDTO = facultiesMapper.toDto(faculties);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFacultiesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, facultiesDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(facultiesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Faculties in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFaculties() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        faculties.setId(longCount.incrementAndGet());

        // Create the Faculties
        FacultiesDTO facultiesDTO = facultiesMapper.toDto(faculties);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFacultiesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(facultiesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Faculties in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFaculties() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        faculties.setId(longCount.incrementAndGet());

        // Create the Faculties
        FacultiesDTO facultiesDTO = facultiesMapper.toDto(faculties);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFacultiesMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(facultiesDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Faculties in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFaculties() throws Exception {
        // Initialize the database
        insertedFaculties = facultiesRepository.saveAndFlush(faculties);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the faculties
        restFacultiesMockMvc
            .perform(delete(ENTITY_API_URL_ID, faculties.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return facultiesRepository.count();
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

    protected Faculties getPersistedFaculties(Faculties faculties) {
        return facultiesRepository.findById(faculties.getId()).orElseThrow();
    }

    protected void assertPersistedFacultiesToMatchAllProperties(Faculties expectedFaculties) {
        assertFacultiesAllPropertiesEquals(expectedFaculties, getPersistedFaculties(expectedFaculties));
    }

    protected void assertPersistedFacultiesToMatchUpdatableProperties(Faculties expectedFaculties) {
        assertFacultiesAllUpdatablePropertiesEquals(expectedFaculties, getPersistedFaculties(expectedFaculties));
    }
}
