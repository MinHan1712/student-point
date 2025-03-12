package student.point.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static student.point.domain.CourseAsserts.*;
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
import student.point.domain.enumeration.CourseType;
import student.point.repository.CourseRepository;
import student.point.service.dto.CourseDTO;
import student.point.service.mapper.CourseMapper;

/**
 * Integration tests for the {@link CourseResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CourseResourceIT {

    private static final String DEFAULT_COURSE_CODE = "AAAAAAAAAA";
    private static final String UPDATED_COURSE_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_COURSE_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_COURSE_TITLE = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREDITS = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREDITS = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_LECTURE = 1;
    private static final Integer UPDATED_LECTURE = 2;
    private static final Integer SMALLER_LECTURE = 1 - 1;

    private static final Integer DEFAULT_TUTORIAL_DISCUSSION = 1;
    private static final Integer UPDATED_TUTORIAL_DISCUSSION = 2;
    private static final Integer SMALLER_TUTORIAL_DISCUSSION = 1 - 1;

    private static final Integer DEFAULT_PRACTICAL = 1;
    private static final Integer UPDATED_PRACTICAL = 2;
    private static final Integer SMALLER_PRACTICAL = 1 - 1;

    private static final Integer DEFAULT_LABORATORY = 1;
    private static final Integer UPDATED_LABORATORY = 2;
    private static final Integer SMALLER_LABORATORY = 1 - 1;

    private static final Integer DEFAULT_SELF_STUDY = 1;
    private static final Integer UPDATED_SELF_STUDY = 2;
    private static final Integer SMALLER_SELF_STUDY = 1 - 1;

    private static final Integer DEFAULT_NUMBER_OF_SESSIONS = 1;
    private static final Integer UPDATED_NUMBER_OF_SESSIONS = 2;
    private static final Integer SMALLER_NUMBER_OF_SESSIONS = 1 - 1;

    private static final CourseType DEFAULT_COURSE_TYPE = CourseType.Elective;
    private static final CourseType UPDATED_COURSE_TYPE = CourseType.Mandatory;

    private static final String DEFAULT_NOTES = "AAAAAAAAAA";
    private static final String UPDATED_NOTES = "BBBBBBBBBB";

    private static final Boolean DEFAULT_STATUS = false;
    private static final Boolean UPDATED_STATUS = true;

    private static final String DEFAULT_SEMESTER = "AAAAAAAAAA";
    private static final String UPDATED_SEMESTER = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/courses";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCourseMockMvc;

    private Course course;

    private Course insertedCourse;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Course createEntity() {
        return new Course()
            .courseCode(DEFAULT_COURSE_CODE)
            .courseTitle(DEFAULT_COURSE_TITLE)
            .credits(DEFAULT_CREDITS)
            .lecture(DEFAULT_LECTURE)
            .tutorialDiscussion(DEFAULT_TUTORIAL_DISCUSSION)
            .practical(DEFAULT_PRACTICAL)
            .laboratory(DEFAULT_LABORATORY)
            .selfStudy(DEFAULT_SELF_STUDY)
            .numberOfSessions(DEFAULT_NUMBER_OF_SESSIONS)
            .courseType(DEFAULT_COURSE_TYPE)
            .notes(DEFAULT_NOTES)
            .status(DEFAULT_STATUS)
            .semester(DEFAULT_SEMESTER);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Course createUpdatedEntity() {
        return new Course()
            .courseCode(UPDATED_COURSE_CODE)
            .courseTitle(UPDATED_COURSE_TITLE)
            .credits(UPDATED_CREDITS)
            .lecture(UPDATED_LECTURE)
            .tutorialDiscussion(UPDATED_TUTORIAL_DISCUSSION)
            .practical(UPDATED_PRACTICAL)
            .laboratory(UPDATED_LABORATORY)
            .selfStudy(UPDATED_SELF_STUDY)
            .numberOfSessions(UPDATED_NUMBER_OF_SESSIONS)
            .courseType(UPDATED_COURSE_TYPE)
            .notes(UPDATED_NOTES)
            .status(UPDATED_STATUS)
            .semester(UPDATED_SEMESTER);
    }

    @BeforeEach
    public void initTest() {
        course = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedCourse != null) {
            courseRepository.delete(insertedCourse);
            insertedCourse = null;
        }
    }

    @Test
    @Transactional
    void createCourse() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Course
        CourseDTO courseDTO = courseMapper.toDto(course);
        var returnedCourseDTO = om.readValue(
            restCourseMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(courseDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            CourseDTO.class
        );

        // Validate the Course in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedCourse = courseMapper.toEntity(returnedCourseDTO);
        assertCourseUpdatableFieldsEquals(returnedCourse, getPersistedCourse(returnedCourse));

        insertedCourse = returnedCourse;
    }

    @Test
    @Transactional
    void createCourseWithExistingId() throws Exception {
        // Create the Course with an existing ID
        course.setId(1L);
        CourseDTO courseDTO = courseMapper.toDto(course);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCourseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(courseDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Course in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCourses() throws Exception {
        // Initialize the database
        insertedCourse = courseRepository.saveAndFlush(course);

        // Get all the courseList
        restCourseMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(course.getId().intValue())))
            .andExpect(jsonPath("$.[*].courseCode").value(hasItem(DEFAULT_COURSE_CODE)))
            .andExpect(jsonPath("$.[*].courseTitle").value(hasItem(DEFAULT_COURSE_TITLE)))
            .andExpect(jsonPath("$.[*].credits").value(hasItem(DEFAULT_CREDITS.toString())))
            .andExpect(jsonPath("$.[*].lecture").value(hasItem(DEFAULT_LECTURE)))
            .andExpect(jsonPath("$.[*].tutorialDiscussion").value(hasItem(DEFAULT_TUTORIAL_DISCUSSION)))
            .andExpect(jsonPath("$.[*].practical").value(hasItem(DEFAULT_PRACTICAL)))
            .andExpect(jsonPath("$.[*].laboratory").value(hasItem(DEFAULT_LABORATORY)))
            .andExpect(jsonPath("$.[*].selfStudy").value(hasItem(DEFAULT_SELF_STUDY)))
            .andExpect(jsonPath("$.[*].numberOfSessions").value(hasItem(DEFAULT_NUMBER_OF_SESSIONS)))
            .andExpect(jsonPath("$.[*].courseType").value(hasItem(DEFAULT_COURSE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].semester").value(hasItem(DEFAULT_SEMESTER)));
    }

    @Test
    @Transactional
    void getCourse() throws Exception {
        // Initialize the database
        insertedCourse = courseRepository.saveAndFlush(course);

        // Get the course
        restCourseMockMvc
            .perform(get(ENTITY_API_URL_ID, course.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(course.getId().intValue()))
            .andExpect(jsonPath("$.courseCode").value(DEFAULT_COURSE_CODE))
            .andExpect(jsonPath("$.courseTitle").value(DEFAULT_COURSE_TITLE))
            .andExpect(jsonPath("$.credits").value(DEFAULT_CREDITS.toString()))
            .andExpect(jsonPath("$.lecture").value(DEFAULT_LECTURE))
            .andExpect(jsonPath("$.tutorialDiscussion").value(DEFAULT_TUTORIAL_DISCUSSION))
            .andExpect(jsonPath("$.practical").value(DEFAULT_PRACTICAL))
            .andExpect(jsonPath("$.laboratory").value(DEFAULT_LABORATORY))
            .andExpect(jsonPath("$.selfStudy").value(DEFAULT_SELF_STUDY))
            .andExpect(jsonPath("$.numberOfSessions").value(DEFAULT_NUMBER_OF_SESSIONS))
            .andExpect(jsonPath("$.courseType").value(DEFAULT_COURSE_TYPE.toString()))
            .andExpect(jsonPath("$.notes").value(DEFAULT_NOTES))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.semester").value(DEFAULT_SEMESTER));
    }

    @Test
    @Transactional
    void getCoursesByIdFiltering() throws Exception {
        // Initialize the database
        insertedCourse = courseRepository.saveAndFlush(course);

        Long id = course.getId();

        defaultCourseFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultCourseFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultCourseFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCoursesByCourseCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedCourse = courseRepository.saveAndFlush(course);

        // Get all the courseList where courseCode equals to
        defaultCourseFiltering("courseCode.equals=" + DEFAULT_COURSE_CODE, "courseCode.equals=" + UPDATED_COURSE_CODE);
    }

    @Test
    @Transactional
    void getAllCoursesByCourseCodeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedCourse = courseRepository.saveAndFlush(course);

        // Get all the courseList where courseCode in
        defaultCourseFiltering("courseCode.in=" + DEFAULT_COURSE_CODE + "," + UPDATED_COURSE_CODE, "courseCode.in=" + UPDATED_COURSE_CODE);
    }

    @Test
    @Transactional
    void getAllCoursesByCourseCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedCourse = courseRepository.saveAndFlush(course);

        // Get all the courseList where courseCode is not null
        defaultCourseFiltering("courseCode.specified=true", "courseCode.specified=false");
    }

    @Test
    @Transactional
    void getAllCoursesByCourseCodeContainsSomething() throws Exception {
        // Initialize the database
        insertedCourse = courseRepository.saveAndFlush(course);

        // Get all the courseList where courseCode contains
        defaultCourseFiltering("courseCode.contains=" + DEFAULT_COURSE_CODE, "courseCode.contains=" + UPDATED_COURSE_CODE);
    }

    @Test
    @Transactional
    void getAllCoursesByCourseCodeNotContainsSomething() throws Exception {
        // Initialize the database
        insertedCourse = courseRepository.saveAndFlush(course);

        // Get all the courseList where courseCode does not contain
        defaultCourseFiltering("courseCode.doesNotContain=" + UPDATED_COURSE_CODE, "courseCode.doesNotContain=" + DEFAULT_COURSE_CODE);
    }

    @Test
    @Transactional
    void getAllCoursesByCourseTitleIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedCourse = courseRepository.saveAndFlush(course);

        // Get all the courseList where courseTitle equals to
        defaultCourseFiltering("courseTitle.equals=" + DEFAULT_COURSE_TITLE, "courseTitle.equals=" + UPDATED_COURSE_TITLE);
    }

    @Test
    @Transactional
    void getAllCoursesByCourseTitleIsInShouldWork() throws Exception {
        // Initialize the database
        insertedCourse = courseRepository.saveAndFlush(course);

        // Get all the courseList where courseTitle in
        defaultCourseFiltering(
            "courseTitle.in=" + DEFAULT_COURSE_TITLE + "," + UPDATED_COURSE_TITLE,
            "courseTitle.in=" + UPDATED_COURSE_TITLE
        );
    }

    @Test
    @Transactional
    void getAllCoursesByCourseTitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedCourse = courseRepository.saveAndFlush(course);

        // Get all the courseList where courseTitle is not null
        defaultCourseFiltering("courseTitle.specified=true", "courseTitle.specified=false");
    }

    @Test
    @Transactional
    void getAllCoursesByCourseTitleContainsSomething() throws Exception {
        // Initialize the database
        insertedCourse = courseRepository.saveAndFlush(course);

        // Get all the courseList where courseTitle contains
        defaultCourseFiltering("courseTitle.contains=" + DEFAULT_COURSE_TITLE, "courseTitle.contains=" + UPDATED_COURSE_TITLE);
    }

    @Test
    @Transactional
    void getAllCoursesByCourseTitleNotContainsSomething() throws Exception {
        // Initialize the database
        insertedCourse = courseRepository.saveAndFlush(course);

        // Get all the courseList where courseTitle does not contain
        defaultCourseFiltering("courseTitle.doesNotContain=" + UPDATED_COURSE_TITLE, "courseTitle.doesNotContain=" + DEFAULT_COURSE_TITLE);
    }

    @Test
    @Transactional
    void getAllCoursesByCreditsIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedCourse = courseRepository.saveAndFlush(course);

        // Get all the courseList where credits equals to
        defaultCourseFiltering("credits.equals=" + DEFAULT_CREDITS, "credits.equals=" + UPDATED_CREDITS);
    }

    @Test
    @Transactional
    void getAllCoursesByCreditsIsInShouldWork() throws Exception {
        // Initialize the database
        insertedCourse = courseRepository.saveAndFlush(course);

        // Get all the courseList where credits in
        defaultCourseFiltering("credits.in=" + DEFAULT_CREDITS + "," + UPDATED_CREDITS, "credits.in=" + UPDATED_CREDITS);
    }

    @Test
    @Transactional
    void getAllCoursesByCreditsIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedCourse = courseRepository.saveAndFlush(course);

        // Get all the courseList where credits is not null
        defaultCourseFiltering("credits.specified=true", "credits.specified=false");
    }

    @Test
    @Transactional
    void getAllCoursesByLectureIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedCourse = courseRepository.saveAndFlush(course);

        // Get all the courseList where lecture equals to
        defaultCourseFiltering("lecture.equals=" + DEFAULT_LECTURE, "lecture.equals=" + UPDATED_LECTURE);
    }

    @Test
    @Transactional
    void getAllCoursesByLectureIsInShouldWork() throws Exception {
        // Initialize the database
        insertedCourse = courseRepository.saveAndFlush(course);

        // Get all the courseList where lecture in
        defaultCourseFiltering("lecture.in=" + DEFAULT_LECTURE + "," + UPDATED_LECTURE, "lecture.in=" + UPDATED_LECTURE);
    }

    @Test
    @Transactional
    void getAllCoursesByLectureIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedCourse = courseRepository.saveAndFlush(course);

        // Get all the courseList where lecture is not null
        defaultCourseFiltering("lecture.specified=true", "lecture.specified=false");
    }

    @Test
    @Transactional
    void getAllCoursesByLectureIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedCourse = courseRepository.saveAndFlush(course);

        // Get all the courseList where lecture is greater than or equal to
        defaultCourseFiltering("lecture.greaterThanOrEqual=" + DEFAULT_LECTURE, "lecture.greaterThanOrEqual=" + UPDATED_LECTURE);
    }

    @Test
    @Transactional
    void getAllCoursesByLectureIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedCourse = courseRepository.saveAndFlush(course);

        // Get all the courseList where lecture is less than or equal to
        defaultCourseFiltering("lecture.lessThanOrEqual=" + DEFAULT_LECTURE, "lecture.lessThanOrEqual=" + SMALLER_LECTURE);
    }

    @Test
    @Transactional
    void getAllCoursesByLectureIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedCourse = courseRepository.saveAndFlush(course);

        // Get all the courseList where lecture is less than
        defaultCourseFiltering("lecture.lessThan=" + UPDATED_LECTURE, "lecture.lessThan=" + DEFAULT_LECTURE);
    }

    @Test
    @Transactional
    void getAllCoursesByLectureIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedCourse = courseRepository.saveAndFlush(course);

        // Get all the courseList where lecture is greater than
        defaultCourseFiltering("lecture.greaterThan=" + SMALLER_LECTURE, "lecture.greaterThan=" + DEFAULT_LECTURE);
    }

    @Test
    @Transactional
    void getAllCoursesByTutorialDiscussionIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedCourse = courseRepository.saveAndFlush(course);

        // Get all the courseList where tutorialDiscussion equals to
        defaultCourseFiltering(
            "tutorialDiscussion.equals=" + DEFAULT_TUTORIAL_DISCUSSION,
            "tutorialDiscussion.equals=" + UPDATED_TUTORIAL_DISCUSSION
        );
    }

    @Test
    @Transactional
    void getAllCoursesByTutorialDiscussionIsInShouldWork() throws Exception {
        // Initialize the database
        insertedCourse = courseRepository.saveAndFlush(course);

        // Get all the courseList where tutorialDiscussion in
        defaultCourseFiltering(
            "tutorialDiscussion.in=" + DEFAULT_TUTORIAL_DISCUSSION + "," + UPDATED_TUTORIAL_DISCUSSION,
            "tutorialDiscussion.in=" + UPDATED_TUTORIAL_DISCUSSION
        );
    }

    @Test
    @Transactional
    void getAllCoursesByTutorialDiscussionIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedCourse = courseRepository.saveAndFlush(course);

        // Get all the courseList where tutorialDiscussion is not null
        defaultCourseFiltering("tutorialDiscussion.specified=true", "tutorialDiscussion.specified=false");
    }

    @Test
    @Transactional
    void getAllCoursesByTutorialDiscussionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedCourse = courseRepository.saveAndFlush(course);

        // Get all the courseList where tutorialDiscussion is greater than or equal to
        defaultCourseFiltering(
            "tutorialDiscussion.greaterThanOrEqual=" + DEFAULT_TUTORIAL_DISCUSSION,
            "tutorialDiscussion.greaterThanOrEqual=" + UPDATED_TUTORIAL_DISCUSSION
        );
    }

    @Test
    @Transactional
    void getAllCoursesByTutorialDiscussionIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedCourse = courseRepository.saveAndFlush(course);

        // Get all the courseList where tutorialDiscussion is less than or equal to
        defaultCourseFiltering(
            "tutorialDiscussion.lessThanOrEqual=" + DEFAULT_TUTORIAL_DISCUSSION,
            "tutorialDiscussion.lessThanOrEqual=" + SMALLER_TUTORIAL_DISCUSSION
        );
    }

    @Test
    @Transactional
    void getAllCoursesByTutorialDiscussionIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedCourse = courseRepository.saveAndFlush(course);

        // Get all the courseList where tutorialDiscussion is less than
        defaultCourseFiltering(
            "tutorialDiscussion.lessThan=" + UPDATED_TUTORIAL_DISCUSSION,
            "tutorialDiscussion.lessThan=" + DEFAULT_TUTORIAL_DISCUSSION
        );
    }

    @Test
    @Transactional
    void getAllCoursesByTutorialDiscussionIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedCourse = courseRepository.saveAndFlush(course);

        // Get all the courseList where tutorialDiscussion is greater than
        defaultCourseFiltering(
            "tutorialDiscussion.greaterThan=" + SMALLER_TUTORIAL_DISCUSSION,
            "tutorialDiscussion.greaterThan=" + DEFAULT_TUTORIAL_DISCUSSION
        );
    }

    @Test
    @Transactional
    void getAllCoursesByPracticalIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedCourse = courseRepository.saveAndFlush(course);

        // Get all the courseList where practical equals to
        defaultCourseFiltering("practical.equals=" + DEFAULT_PRACTICAL, "practical.equals=" + UPDATED_PRACTICAL);
    }

    @Test
    @Transactional
    void getAllCoursesByPracticalIsInShouldWork() throws Exception {
        // Initialize the database
        insertedCourse = courseRepository.saveAndFlush(course);

        // Get all the courseList where practical in
        defaultCourseFiltering("practical.in=" + DEFAULT_PRACTICAL + "," + UPDATED_PRACTICAL, "practical.in=" + UPDATED_PRACTICAL);
    }

    @Test
    @Transactional
    void getAllCoursesByPracticalIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedCourse = courseRepository.saveAndFlush(course);

        // Get all the courseList where practical is not null
        defaultCourseFiltering("practical.specified=true", "practical.specified=false");
    }

    @Test
    @Transactional
    void getAllCoursesByPracticalIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedCourse = courseRepository.saveAndFlush(course);

        // Get all the courseList where practical is greater than or equal to
        defaultCourseFiltering("practical.greaterThanOrEqual=" + DEFAULT_PRACTICAL, "practical.greaterThanOrEqual=" + UPDATED_PRACTICAL);
    }

    @Test
    @Transactional
    void getAllCoursesByPracticalIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedCourse = courseRepository.saveAndFlush(course);

        // Get all the courseList where practical is less than or equal to
        defaultCourseFiltering("practical.lessThanOrEqual=" + DEFAULT_PRACTICAL, "practical.lessThanOrEqual=" + SMALLER_PRACTICAL);
    }

    @Test
    @Transactional
    void getAllCoursesByPracticalIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedCourse = courseRepository.saveAndFlush(course);

        // Get all the courseList where practical is less than
        defaultCourseFiltering("practical.lessThan=" + UPDATED_PRACTICAL, "practical.lessThan=" + DEFAULT_PRACTICAL);
    }

    @Test
    @Transactional
    void getAllCoursesByPracticalIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedCourse = courseRepository.saveAndFlush(course);

        // Get all the courseList where practical is greater than
        defaultCourseFiltering("practical.greaterThan=" + SMALLER_PRACTICAL, "practical.greaterThan=" + DEFAULT_PRACTICAL);
    }

    @Test
    @Transactional
    void getAllCoursesByLaboratoryIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedCourse = courseRepository.saveAndFlush(course);

        // Get all the courseList where laboratory equals to
        defaultCourseFiltering("laboratory.equals=" + DEFAULT_LABORATORY, "laboratory.equals=" + UPDATED_LABORATORY);
    }

    @Test
    @Transactional
    void getAllCoursesByLaboratoryIsInShouldWork() throws Exception {
        // Initialize the database
        insertedCourse = courseRepository.saveAndFlush(course);

        // Get all the courseList where laboratory in
        defaultCourseFiltering("laboratory.in=" + DEFAULT_LABORATORY + "," + UPDATED_LABORATORY, "laboratory.in=" + UPDATED_LABORATORY);
    }

    @Test
    @Transactional
    void getAllCoursesByLaboratoryIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedCourse = courseRepository.saveAndFlush(course);

        // Get all the courseList where laboratory is not null
        defaultCourseFiltering("laboratory.specified=true", "laboratory.specified=false");
    }

    @Test
    @Transactional
    void getAllCoursesByLaboratoryIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedCourse = courseRepository.saveAndFlush(course);

        // Get all the courseList where laboratory is greater than or equal to
        defaultCourseFiltering(
            "laboratory.greaterThanOrEqual=" + DEFAULT_LABORATORY,
            "laboratory.greaterThanOrEqual=" + UPDATED_LABORATORY
        );
    }

    @Test
    @Transactional
    void getAllCoursesByLaboratoryIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedCourse = courseRepository.saveAndFlush(course);

        // Get all the courseList where laboratory is less than or equal to
        defaultCourseFiltering("laboratory.lessThanOrEqual=" + DEFAULT_LABORATORY, "laboratory.lessThanOrEqual=" + SMALLER_LABORATORY);
    }

    @Test
    @Transactional
    void getAllCoursesByLaboratoryIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedCourse = courseRepository.saveAndFlush(course);

        // Get all the courseList where laboratory is less than
        defaultCourseFiltering("laboratory.lessThan=" + UPDATED_LABORATORY, "laboratory.lessThan=" + DEFAULT_LABORATORY);
    }

    @Test
    @Transactional
    void getAllCoursesByLaboratoryIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedCourse = courseRepository.saveAndFlush(course);

        // Get all the courseList where laboratory is greater than
        defaultCourseFiltering("laboratory.greaterThan=" + SMALLER_LABORATORY, "laboratory.greaterThan=" + DEFAULT_LABORATORY);
    }

    @Test
    @Transactional
    void getAllCoursesBySelfStudyIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedCourse = courseRepository.saveAndFlush(course);

        // Get all the courseList where selfStudy equals to
        defaultCourseFiltering("selfStudy.equals=" + DEFAULT_SELF_STUDY, "selfStudy.equals=" + UPDATED_SELF_STUDY);
    }

    @Test
    @Transactional
    void getAllCoursesBySelfStudyIsInShouldWork() throws Exception {
        // Initialize the database
        insertedCourse = courseRepository.saveAndFlush(course);

        // Get all the courseList where selfStudy in
        defaultCourseFiltering("selfStudy.in=" + DEFAULT_SELF_STUDY + "," + UPDATED_SELF_STUDY, "selfStudy.in=" + UPDATED_SELF_STUDY);
    }

    @Test
    @Transactional
    void getAllCoursesBySelfStudyIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedCourse = courseRepository.saveAndFlush(course);

        // Get all the courseList where selfStudy is not null
        defaultCourseFiltering("selfStudy.specified=true", "selfStudy.specified=false");
    }

    @Test
    @Transactional
    void getAllCoursesBySelfStudyIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedCourse = courseRepository.saveAndFlush(course);

        // Get all the courseList where selfStudy is greater than or equal to
        defaultCourseFiltering("selfStudy.greaterThanOrEqual=" + DEFAULT_SELF_STUDY, "selfStudy.greaterThanOrEqual=" + UPDATED_SELF_STUDY);
    }

    @Test
    @Transactional
    void getAllCoursesBySelfStudyIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedCourse = courseRepository.saveAndFlush(course);

        // Get all the courseList where selfStudy is less than or equal to
        defaultCourseFiltering("selfStudy.lessThanOrEqual=" + DEFAULT_SELF_STUDY, "selfStudy.lessThanOrEqual=" + SMALLER_SELF_STUDY);
    }

    @Test
    @Transactional
    void getAllCoursesBySelfStudyIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedCourse = courseRepository.saveAndFlush(course);

        // Get all the courseList where selfStudy is less than
        defaultCourseFiltering("selfStudy.lessThan=" + UPDATED_SELF_STUDY, "selfStudy.lessThan=" + DEFAULT_SELF_STUDY);
    }

    @Test
    @Transactional
    void getAllCoursesBySelfStudyIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedCourse = courseRepository.saveAndFlush(course);

        // Get all the courseList where selfStudy is greater than
        defaultCourseFiltering("selfStudy.greaterThan=" + SMALLER_SELF_STUDY, "selfStudy.greaterThan=" + DEFAULT_SELF_STUDY);
    }

    @Test
    @Transactional
    void getAllCoursesByNumberOfSessionsIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedCourse = courseRepository.saveAndFlush(course);

        // Get all the courseList where numberOfSessions equals to
        defaultCourseFiltering(
            "numberOfSessions.equals=" + DEFAULT_NUMBER_OF_SESSIONS,
            "numberOfSessions.equals=" + UPDATED_NUMBER_OF_SESSIONS
        );
    }

    @Test
    @Transactional
    void getAllCoursesByNumberOfSessionsIsInShouldWork() throws Exception {
        // Initialize the database
        insertedCourse = courseRepository.saveAndFlush(course);

        // Get all the courseList where numberOfSessions in
        defaultCourseFiltering(
            "numberOfSessions.in=" + DEFAULT_NUMBER_OF_SESSIONS + "," + UPDATED_NUMBER_OF_SESSIONS,
            "numberOfSessions.in=" + UPDATED_NUMBER_OF_SESSIONS
        );
    }

    @Test
    @Transactional
    void getAllCoursesByNumberOfSessionsIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedCourse = courseRepository.saveAndFlush(course);

        // Get all the courseList where numberOfSessions is not null
        defaultCourseFiltering("numberOfSessions.specified=true", "numberOfSessions.specified=false");
    }

    @Test
    @Transactional
    void getAllCoursesByNumberOfSessionsIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedCourse = courseRepository.saveAndFlush(course);

        // Get all the courseList where numberOfSessions is greater than or equal to
        defaultCourseFiltering(
            "numberOfSessions.greaterThanOrEqual=" + DEFAULT_NUMBER_OF_SESSIONS,
            "numberOfSessions.greaterThanOrEqual=" + UPDATED_NUMBER_OF_SESSIONS
        );
    }

    @Test
    @Transactional
    void getAllCoursesByNumberOfSessionsIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedCourse = courseRepository.saveAndFlush(course);

        // Get all the courseList where numberOfSessions is less than or equal to
        defaultCourseFiltering(
            "numberOfSessions.lessThanOrEqual=" + DEFAULT_NUMBER_OF_SESSIONS,
            "numberOfSessions.lessThanOrEqual=" + SMALLER_NUMBER_OF_SESSIONS
        );
    }

    @Test
    @Transactional
    void getAllCoursesByNumberOfSessionsIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedCourse = courseRepository.saveAndFlush(course);

        // Get all the courseList where numberOfSessions is less than
        defaultCourseFiltering(
            "numberOfSessions.lessThan=" + UPDATED_NUMBER_OF_SESSIONS,
            "numberOfSessions.lessThan=" + DEFAULT_NUMBER_OF_SESSIONS
        );
    }

    @Test
    @Transactional
    void getAllCoursesByNumberOfSessionsIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedCourse = courseRepository.saveAndFlush(course);

        // Get all the courseList where numberOfSessions is greater than
        defaultCourseFiltering(
            "numberOfSessions.greaterThan=" + SMALLER_NUMBER_OF_SESSIONS,
            "numberOfSessions.greaterThan=" + DEFAULT_NUMBER_OF_SESSIONS
        );
    }

    @Test
    @Transactional
    void getAllCoursesByCourseTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedCourse = courseRepository.saveAndFlush(course);

        // Get all the courseList where courseType equals to
        defaultCourseFiltering("courseType.equals=" + DEFAULT_COURSE_TYPE, "courseType.equals=" + UPDATED_COURSE_TYPE);
    }

    @Test
    @Transactional
    void getAllCoursesByCourseTypeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedCourse = courseRepository.saveAndFlush(course);

        // Get all the courseList where courseType in
        defaultCourseFiltering("courseType.in=" + DEFAULT_COURSE_TYPE + "," + UPDATED_COURSE_TYPE, "courseType.in=" + UPDATED_COURSE_TYPE);
    }

    @Test
    @Transactional
    void getAllCoursesByCourseTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedCourse = courseRepository.saveAndFlush(course);

        // Get all the courseList where courseType is not null
        defaultCourseFiltering("courseType.specified=true", "courseType.specified=false");
    }

    @Test
    @Transactional
    void getAllCoursesByNotesIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedCourse = courseRepository.saveAndFlush(course);

        // Get all the courseList where notes equals to
        defaultCourseFiltering("notes.equals=" + DEFAULT_NOTES, "notes.equals=" + UPDATED_NOTES);
    }

    @Test
    @Transactional
    void getAllCoursesByNotesIsInShouldWork() throws Exception {
        // Initialize the database
        insertedCourse = courseRepository.saveAndFlush(course);

        // Get all the courseList where notes in
        defaultCourseFiltering("notes.in=" + DEFAULT_NOTES + "," + UPDATED_NOTES, "notes.in=" + UPDATED_NOTES);
    }

    @Test
    @Transactional
    void getAllCoursesByNotesIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedCourse = courseRepository.saveAndFlush(course);

        // Get all the courseList where notes is not null
        defaultCourseFiltering("notes.specified=true", "notes.specified=false");
    }

    @Test
    @Transactional
    void getAllCoursesByNotesContainsSomething() throws Exception {
        // Initialize the database
        insertedCourse = courseRepository.saveAndFlush(course);

        // Get all the courseList where notes contains
        defaultCourseFiltering("notes.contains=" + DEFAULT_NOTES, "notes.contains=" + UPDATED_NOTES);
    }

    @Test
    @Transactional
    void getAllCoursesByNotesNotContainsSomething() throws Exception {
        // Initialize the database
        insertedCourse = courseRepository.saveAndFlush(course);

        // Get all the courseList where notes does not contain
        defaultCourseFiltering("notes.doesNotContain=" + UPDATED_NOTES, "notes.doesNotContain=" + DEFAULT_NOTES);
    }

    @Test
    @Transactional
    void getAllCoursesByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedCourse = courseRepository.saveAndFlush(course);

        // Get all the courseList where status equals to
        defaultCourseFiltering("status.equals=" + DEFAULT_STATUS, "status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllCoursesByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        insertedCourse = courseRepository.saveAndFlush(course);

        // Get all the courseList where status in
        defaultCourseFiltering("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS, "status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllCoursesByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedCourse = courseRepository.saveAndFlush(course);

        // Get all the courseList where status is not null
        defaultCourseFiltering("status.specified=true", "status.specified=false");
    }

    @Test
    @Transactional
    void getAllCoursesBySemesterIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedCourse = courseRepository.saveAndFlush(course);

        // Get all the courseList where semester equals to
        defaultCourseFiltering("semester.equals=" + DEFAULT_SEMESTER, "semester.equals=" + UPDATED_SEMESTER);
    }

    @Test
    @Transactional
    void getAllCoursesBySemesterIsInShouldWork() throws Exception {
        // Initialize the database
        insertedCourse = courseRepository.saveAndFlush(course);

        // Get all the courseList where semester in
        defaultCourseFiltering("semester.in=" + DEFAULT_SEMESTER + "," + UPDATED_SEMESTER, "semester.in=" + UPDATED_SEMESTER);
    }

    @Test
    @Transactional
    void getAllCoursesBySemesterIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedCourse = courseRepository.saveAndFlush(course);

        // Get all the courseList where semester is not null
        defaultCourseFiltering("semester.specified=true", "semester.specified=false");
    }

    @Test
    @Transactional
    void getAllCoursesBySemesterContainsSomething() throws Exception {
        // Initialize the database
        insertedCourse = courseRepository.saveAndFlush(course);

        // Get all the courseList where semester contains
        defaultCourseFiltering("semester.contains=" + DEFAULT_SEMESTER, "semester.contains=" + UPDATED_SEMESTER);
    }

    @Test
    @Transactional
    void getAllCoursesBySemesterNotContainsSomething() throws Exception {
        // Initialize the database
        insertedCourse = courseRepository.saveAndFlush(course);

        // Get all the courseList where semester does not contain
        defaultCourseFiltering("semester.doesNotContain=" + UPDATED_SEMESTER, "semester.doesNotContain=" + DEFAULT_SEMESTER);
    }

    private void defaultCourseFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultCourseShouldBeFound(shouldBeFound);
        defaultCourseShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCourseShouldBeFound(String filter) throws Exception {
        restCourseMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(course.getId().intValue())))
            .andExpect(jsonPath("$.[*].courseCode").value(hasItem(DEFAULT_COURSE_CODE)))
            .andExpect(jsonPath("$.[*].courseTitle").value(hasItem(DEFAULT_COURSE_TITLE)))
            .andExpect(jsonPath("$.[*].credits").value(hasItem(DEFAULT_CREDITS.toString())))
            .andExpect(jsonPath("$.[*].lecture").value(hasItem(DEFAULT_LECTURE)))
            .andExpect(jsonPath("$.[*].tutorialDiscussion").value(hasItem(DEFAULT_TUTORIAL_DISCUSSION)))
            .andExpect(jsonPath("$.[*].practical").value(hasItem(DEFAULT_PRACTICAL)))
            .andExpect(jsonPath("$.[*].laboratory").value(hasItem(DEFAULT_LABORATORY)))
            .andExpect(jsonPath("$.[*].selfStudy").value(hasItem(DEFAULT_SELF_STUDY)))
            .andExpect(jsonPath("$.[*].numberOfSessions").value(hasItem(DEFAULT_NUMBER_OF_SESSIONS)))
            .andExpect(jsonPath("$.[*].courseType").value(hasItem(DEFAULT_COURSE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].semester").value(hasItem(DEFAULT_SEMESTER)));

        // Check, that the count call also returns 1
        restCourseMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCourseShouldNotBeFound(String filter) throws Exception {
        restCourseMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCourseMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCourse() throws Exception {
        // Get the course
        restCourseMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCourse() throws Exception {
        // Initialize the database
        insertedCourse = courseRepository.saveAndFlush(course);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the course
        Course updatedCourse = courseRepository.findById(course.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedCourse are not directly saved in db
        em.detach(updatedCourse);
        updatedCourse
            .courseCode(UPDATED_COURSE_CODE)
            .courseTitle(UPDATED_COURSE_TITLE)
            .credits(UPDATED_CREDITS)
            .lecture(UPDATED_LECTURE)
            .tutorialDiscussion(UPDATED_TUTORIAL_DISCUSSION)
            .practical(UPDATED_PRACTICAL)
            .laboratory(UPDATED_LABORATORY)
            .selfStudy(UPDATED_SELF_STUDY)
            .numberOfSessions(UPDATED_NUMBER_OF_SESSIONS)
            .courseType(UPDATED_COURSE_TYPE)
            .notes(UPDATED_NOTES)
            .status(UPDATED_STATUS)
            .semester(UPDATED_SEMESTER);
        CourseDTO courseDTO = courseMapper.toDto(updatedCourse);

        restCourseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, courseDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(courseDTO))
            )
            .andExpect(status().isOk());

        // Validate the Course in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedCourseToMatchAllProperties(updatedCourse);
    }

    @Test
    @Transactional
    void putNonExistingCourse() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        course.setId(longCount.incrementAndGet());

        // Create the Course
        CourseDTO courseDTO = courseMapper.toDto(course);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCourseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, courseDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(courseDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Course in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCourse() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        course.setId(longCount.incrementAndGet());

        // Create the Course
        CourseDTO courseDTO = courseMapper.toDto(course);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCourseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(courseDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Course in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCourse() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        course.setId(longCount.incrementAndGet());

        // Create the Course
        CourseDTO courseDTO = courseMapper.toDto(course);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCourseMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(courseDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Course in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCourseWithPatch() throws Exception {
        // Initialize the database
        insertedCourse = courseRepository.saveAndFlush(course);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the course using partial update
        Course partialUpdatedCourse = new Course();
        partialUpdatedCourse.setId(course.getId());

        partialUpdatedCourse
            .credits(UPDATED_CREDITS)
            .lecture(UPDATED_LECTURE)
            .tutorialDiscussion(UPDATED_TUTORIAL_DISCUSSION)
            .courseType(UPDATED_COURSE_TYPE)
            .semester(UPDATED_SEMESTER);

        restCourseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCourse.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCourse))
            )
            .andExpect(status().isOk());

        // Validate the Course in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCourseUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedCourse, course), getPersistedCourse(course));
    }

    @Test
    @Transactional
    void fullUpdateCourseWithPatch() throws Exception {
        // Initialize the database
        insertedCourse = courseRepository.saveAndFlush(course);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the course using partial update
        Course partialUpdatedCourse = new Course();
        partialUpdatedCourse.setId(course.getId());

        partialUpdatedCourse
            .courseCode(UPDATED_COURSE_CODE)
            .courseTitle(UPDATED_COURSE_TITLE)
            .credits(UPDATED_CREDITS)
            .lecture(UPDATED_LECTURE)
            .tutorialDiscussion(UPDATED_TUTORIAL_DISCUSSION)
            .practical(UPDATED_PRACTICAL)
            .laboratory(UPDATED_LABORATORY)
            .selfStudy(UPDATED_SELF_STUDY)
            .numberOfSessions(UPDATED_NUMBER_OF_SESSIONS)
            .courseType(UPDATED_COURSE_TYPE)
            .notes(UPDATED_NOTES)
            .status(UPDATED_STATUS)
            .semester(UPDATED_SEMESTER);

        restCourseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCourse.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCourse))
            )
            .andExpect(status().isOk());

        // Validate the Course in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCourseUpdatableFieldsEquals(partialUpdatedCourse, getPersistedCourse(partialUpdatedCourse));
    }

    @Test
    @Transactional
    void patchNonExistingCourse() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        course.setId(longCount.incrementAndGet());

        // Create the Course
        CourseDTO courseDTO = courseMapper.toDto(course);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCourseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, courseDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(courseDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Course in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCourse() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        course.setId(longCount.incrementAndGet());

        // Create the Course
        CourseDTO courseDTO = courseMapper.toDto(course);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCourseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(courseDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Course in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCourse() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        course.setId(longCount.incrementAndGet());

        // Create the Course
        CourseDTO courseDTO = courseMapper.toDto(course);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCourseMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(courseDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Course in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCourse() throws Exception {
        // Initialize the database
        insertedCourse = courseRepository.saveAndFlush(course);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the course
        restCourseMockMvc
            .perform(delete(ENTITY_API_URL_ID, course.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return courseRepository.count();
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

    protected Course getPersistedCourse(Course course) {
        return courseRepository.findById(course.getId()).orElseThrow();
    }

    protected void assertPersistedCourseToMatchAllProperties(Course expectedCourse) {
        assertCourseAllPropertiesEquals(expectedCourse, getPersistedCourse(expectedCourse));
    }

    protected void assertPersistedCourseToMatchUpdatableProperties(Course expectedCourse) {
        assertCourseAllUpdatablePropertiesEquals(expectedCourse, getPersistedCourse(expectedCourse));
    }
}
