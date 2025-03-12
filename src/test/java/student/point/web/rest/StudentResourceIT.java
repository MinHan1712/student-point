package student.point.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static student.point.domain.StudentAsserts.*;
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
import student.point.domain.Student;
import student.point.domain.enumeration.StudentStatus;
import student.point.domain.enumeration.gender;
import student.point.repository.StudentRepository;
import student.point.service.dto.StudentDTO;
import student.point.service.mapper.StudentMapper;

/**
 * Integration tests for the {@link StudentResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class StudentResourceIT {

    private static final String DEFAULT_STUDENT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_STUDENT_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_FULL_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FULL_NAME = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATE_OF_BIRTH = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_OF_BIRTH = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final gender DEFAULT_GENDER = gender.M;
    private static final gender UPDATED_GENDER = gender.F;

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_NOTES = "AAAAAAAAAA";
    private static final String UPDATED_NOTES = "BBBBBBBBBB";

    private static final StudentStatus DEFAULT_STATUS = StudentStatus.Withdrawn;
    private static final StudentStatus UPDATED_STATUS = StudentStatus.Graduated;

    private static final Instant DEFAULT_DATE_ENROLLMENT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_ENROLLMENT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/students";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStudentMockMvc;

    private Student student;

    private Student insertedStudent;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Student createEntity() {
        return new Student()
            .studentCode(DEFAULT_STUDENT_CODE)
            .fullName(DEFAULT_FULL_NAME)
            .dateOfBirth(DEFAULT_DATE_OF_BIRTH)
            .gender(DEFAULT_GENDER)
            .address(DEFAULT_ADDRESS)
            .phoneNumber(DEFAULT_PHONE_NUMBER)
            .email(DEFAULT_EMAIL)
            .notes(DEFAULT_NOTES)
            .status(DEFAULT_STATUS)
            .dateEnrollment(DEFAULT_DATE_ENROLLMENT);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Student createUpdatedEntity() {
        return new Student()
            .studentCode(UPDATED_STUDENT_CODE)
            .fullName(UPDATED_FULL_NAME)
            .dateOfBirth(UPDATED_DATE_OF_BIRTH)
            .gender(UPDATED_GENDER)
            .address(UPDATED_ADDRESS)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .email(UPDATED_EMAIL)
            .notes(UPDATED_NOTES)
            .status(UPDATED_STATUS)
            .dateEnrollment(UPDATED_DATE_ENROLLMENT);
    }

    @BeforeEach
    public void initTest() {
        student = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedStudent != null) {
            studentRepository.delete(insertedStudent);
            insertedStudent = null;
        }
    }

    @Test
    @Transactional
    void createStudent() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Student
        StudentDTO studentDTO = studentMapper.toDto(student);
        var returnedStudentDTO = om.readValue(
            restStudentMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(studentDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            StudentDTO.class
        );

        // Validate the Student in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedStudent = studentMapper.toEntity(returnedStudentDTO);
        assertStudentUpdatableFieldsEquals(returnedStudent, getPersistedStudent(returnedStudent));

        insertedStudent = returnedStudent;
    }

    @Test
    @Transactional
    void createStudentWithExistingId() throws Exception {
        // Create the Student with an existing ID
        student.setId(1L);
        StudentDTO studentDTO = studentMapper.toDto(student);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restStudentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(studentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Student in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllStudents() throws Exception {
        // Initialize the database
        insertedStudent = studentRepository.saveAndFlush(student);

        // Get all the studentList
        restStudentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(student.getId().intValue())))
            .andExpect(jsonPath("$.[*].studentCode").value(hasItem(DEFAULT_STUDENT_CODE)))
            .andExpect(jsonPath("$.[*].fullName").value(hasItem(DEFAULT_FULL_NAME)))
            .andExpect(jsonPath("$.[*].dateOfBirth").value(hasItem(DEFAULT_DATE_OF_BIRTH.toString())))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].dateEnrollment").value(hasItem(DEFAULT_DATE_ENROLLMENT.toString())));
    }

    @Test
    @Transactional
    void getStudent() throws Exception {
        // Initialize the database
        insertedStudent = studentRepository.saveAndFlush(student);

        // Get the student
        restStudentMockMvc
            .perform(get(ENTITY_API_URL_ID, student.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(student.getId().intValue()))
            .andExpect(jsonPath("$.studentCode").value(DEFAULT_STUDENT_CODE))
            .andExpect(jsonPath("$.fullName").value(DEFAULT_FULL_NAME))
            .andExpect(jsonPath("$.dateOfBirth").value(DEFAULT_DATE_OF_BIRTH.toString()))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER.toString()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
            .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.notes").value(DEFAULT_NOTES))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.dateEnrollment").value(DEFAULT_DATE_ENROLLMENT.toString()));
    }

    @Test
    @Transactional
    void getStudentsByIdFiltering() throws Exception {
        // Initialize the database
        insertedStudent = studentRepository.saveAndFlush(student);

        Long id = student.getId();

        defaultStudentFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultStudentFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultStudentFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllStudentsByStudentCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedStudent = studentRepository.saveAndFlush(student);

        // Get all the studentList where studentCode equals to
        defaultStudentFiltering("studentCode.equals=" + DEFAULT_STUDENT_CODE, "studentCode.equals=" + UPDATED_STUDENT_CODE);
    }

    @Test
    @Transactional
    void getAllStudentsByStudentCodeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedStudent = studentRepository.saveAndFlush(student);

        // Get all the studentList where studentCode in
        defaultStudentFiltering(
            "studentCode.in=" + DEFAULT_STUDENT_CODE + "," + UPDATED_STUDENT_CODE,
            "studentCode.in=" + UPDATED_STUDENT_CODE
        );
    }

    @Test
    @Transactional
    void getAllStudentsByStudentCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedStudent = studentRepository.saveAndFlush(student);

        // Get all the studentList where studentCode is not null
        defaultStudentFiltering("studentCode.specified=true", "studentCode.specified=false");
    }

    @Test
    @Transactional
    void getAllStudentsByStudentCodeContainsSomething() throws Exception {
        // Initialize the database
        insertedStudent = studentRepository.saveAndFlush(student);

        // Get all the studentList where studentCode contains
        defaultStudentFiltering("studentCode.contains=" + DEFAULT_STUDENT_CODE, "studentCode.contains=" + UPDATED_STUDENT_CODE);
    }

    @Test
    @Transactional
    void getAllStudentsByStudentCodeNotContainsSomething() throws Exception {
        // Initialize the database
        insertedStudent = studentRepository.saveAndFlush(student);

        // Get all the studentList where studentCode does not contain
        defaultStudentFiltering("studentCode.doesNotContain=" + UPDATED_STUDENT_CODE, "studentCode.doesNotContain=" + DEFAULT_STUDENT_CODE);
    }

    @Test
    @Transactional
    void getAllStudentsByFullNameIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedStudent = studentRepository.saveAndFlush(student);

        // Get all the studentList where fullName equals to
        defaultStudentFiltering("fullName.equals=" + DEFAULT_FULL_NAME, "fullName.equals=" + UPDATED_FULL_NAME);
    }

    @Test
    @Transactional
    void getAllStudentsByFullNameIsInShouldWork() throws Exception {
        // Initialize the database
        insertedStudent = studentRepository.saveAndFlush(student);

        // Get all the studentList where fullName in
        defaultStudentFiltering("fullName.in=" + DEFAULT_FULL_NAME + "," + UPDATED_FULL_NAME, "fullName.in=" + UPDATED_FULL_NAME);
    }

    @Test
    @Transactional
    void getAllStudentsByFullNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedStudent = studentRepository.saveAndFlush(student);

        // Get all the studentList where fullName is not null
        defaultStudentFiltering("fullName.specified=true", "fullName.specified=false");
    }

    @Test
    @Transactional
    void getAllStudentsByFullNameContainsSomething() throws Exception {
        // Initialize the database
        insertedStudent = studentRepository.saveAndFlush(student);

        // Get all the studentList where fullName contains
        defaultStudentFiltering("fullName.contains=" + DEFAULT_FULL_NAME, "fullName.contains=" + UPDATED_FULL_NAME);
    }

    @Test
    @Transactional
    void getAllStudentsByFullNameNotContainsSomething() throws Exception {
        // Initialize the database
        insertedStudent = studentRepository.saveAndFlush(student);

        // Get all the studentList where fullName does not contain
        defaultStudentFiltering("fullName.doesNotContain=" + UPDATED_FULL_NAME, "fullName.doesNotContain=" + DEFAULT_FULL_NAME);
    }

    @Test
    @Transactional
    void getAllStudentsByDateOfBirthIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedStudent = studentRepository.saveAndFlush(student);

        // Get all the studentList where dateOfBirth equals to
        defaultStudentFiltering("dateOfBirth.equals=" + DEFAULT_DATE_OF_BIRTH, "dateOfBirth.equals=" + UPDATED_DATE_OF_BIRTH);
    }

    @Test
    @Transactional
    void getAllStudentsByDateOfBirthIsInShouldWork() throws Exception {
        // Initialize the database
        insertedStudent = studentRepository.saveAndFlush(student);

        // Get all the studentList where dateOfBirth in
        defaultStudentFiltering(
            "dateOfBirth.in=" + DEFAULT_DATE_OF_BIRTH + "," + UPDATED_DATE_OF_BIRTH,
            "dateOfBirth.in=" + UPDATED_DATE_OF_BIRTH
        );
    }

    @Test
    @Transactional
    void getAllStudentsByDateOfBirthIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedStudent = studentRepository.saveAndFlush(student);

        // Get all the studentList where dateOfBirth is not null
        defaultStudentFiltering("dateOfBirth.specified=true", "dateOfBirth.specified=false");
    }

    @Test
    @Transactional
    void getAllStudentsByGenderIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedStudent = studentRepository.saveAndFlush(student);

        // Get all the studentList where gender equals to
        defaultStudentFiltering("gender.equals=" + DEFAULT_GENDER, "gender.equals=" + UPDATED_GENDER);
    }

    @Test
    @Transactional
    void getAllStudentsByGenderIsInShouldWork() throws Exception {
        // Initialize the database
        insertedStudent = studentRepository.saveAndFlush(student);

        // Get all the studentList where gender in
        defaultStudentFiltering("gender.in=" + DEFAULT_GENDER + "," + UPDATED_GENDER, "gender.in=" + UPDATED_GENDER);
    }

    @Test
    @Transactional
    void getAllStudentsByGenderIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedStudent = studentRepository.saveAndFlush(student);

        // Get all the studentList where gender is not null
        defaultStudentFiltering("gender.specified=true", "gender.specified=false");
    }

    @Test
    @Transactional
    void getAllStudentsByAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedStudent = studentRepository.saveAndFlush(student);

        // Get all the studentList where address equals to
        defaultStudentFiltering("address.equals=" + DEFAULT_ADDRESS, "address.equals=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllStudentsByAddressIsInShouldWork() throws Exception {
        // Initialize the database
        insertedStudent = studentRepository.saveAndFlush(student);

        // Get all the studentList where address in
        defaultStudentFiltering("address.in=" + DEFAULT_ADDRESS + "," + UPDATED_ADDRESS, "address.in=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllStudentsByAddressIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedStudent = studentRepository.saveAndFlush(student);

        // Get all the studentList where address is not null
        defaultStudentFiltering("address.specified=true", "address.specified=false");
    }

    @Test
    @Transactional
    void getAllStudentsByAddressContainsSomething() throws Exception {
        // Initialize the database
        insertedStudent = studentRepository.saveAndFlush(student);

        // Get all the studentList where address contains
        defaultStudentFiltering("address.contains=" + DEFAULT_ADDRESS, "address.contains=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllStudentsByAddressNotContainsSomething() throws Exception {
        // Initialize the database
        insertedStudent = studentRepository.saveAndFlush(student);

        // Get all the studentList where address does not contain
        defaultStudentFiltering("address.doesNotContain=" + UPDATED_ADDRESS, "address.doesNotContain=" + DEFAULT_ADDRESS);
    }

    @Test
    @Transactional
    void getAllStudentsByPhoneNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedStudent = studentRepository.saveAndFlush(student);

        // Get all the studentList where phoneNumber equals to
        defaultStudentFiltering("phoneNumber.equals=" + DEFAULT_PHONE_NUMBER, "phoneNumber.equals=" + UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    void getAllStudentsByPhoneNumberIsInShouldWork() throws Exception {
        // Initialize the database
        insertedStudent = studentRepository.saveAndFlush(student);

        // Get all the studentList where phoneNumber in
        defaultStudentFiltering(
            "phoneNumber.in=" + DEFAULT_PHONE_NUMBER + "," + UPDATED_PHONE_NUMBER,
            "phoneNumber.in=" + UPDATED_PHONE_NUMBER
        );
    }

    @Test
    @Transactional
    void getAllStudentsByPhoneNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedStudent = studentRepository.saveAndFlush(student);

        // Get all the studentList where phoneNumber is not null
        defaultStudentFiltering("phoneNumber.specified=true", "phoneNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllStudentsByPhoneNumberContainsSomething() throws Exception {
        // Initialize the database
        insertedStudent = studentRepository.saveAndFlush(student);

        // Get all the studentList where phoneNumber contains
        defaultStudentFiltering("phoneNumber.contains=" + DEFAULT_PHONE_NUMBER, "phoneNumber.contains=" + UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    void getAllStudentsByPhoneNumberNotContainsSomething() throws Exception {
        // Initialize the database
        insertedStudent = studentRepository.saveAndFlush(student);

        // Get all the studentList where phoneNumber does not contain
        defaultStudentFiltering("phoneNumber.doesNotContain=" + UPDATED_PHONE_NUMBER, "phoneNumber.doesNotContain=" + DEFAULT_PHONE_NUMBER);
    }

    @Test
    @Transactional
    void getAllStudentsByEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedStudent = studentRepository.saveAndFlush(student);

        // Get all the studentList where email equals to
        defaultStudentFiltering("email.equals=" + DEFAULT_EMAIL, "email.equals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllStudentsByEmailIsInShouldWork() throws Exception {
        // Initialize the database
        insertedStudent = studentRepository.saveAndFlush(student);

        // Get all the studentList where email in
        defaultStudentFiltering("email.in=" + DEFAULT_EMAIL + "," + UPDATED_EMAIL, "email.in=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllStudentsByEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedStudent = studentRepository.saveAndFlush(student);

        // Get all the studentList where email is not null
        defaultStudentFiltering("email.specified=true", "email.specified=false");
    }

    @Test
    @Transactional
    void getAllStudentsByEmailContainsSomething() throws Exception {
        // Initialize the database
        insertedStudent = studentRepository.saveAndFlush(student);

        // Get all the studentList where email contains
        defaultStudentFiltering("email.contains=" + DEFAULT_EMAIL, "email.contains=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllStudentsByEmailNotContainsSomething() throws Exception {
        // Initialize the database
        insertedStudent = studentRepository.saveAndFlush(student);

        // Get all the studentList where email does not contain
        defaultStudentFiltering("email.doesNotContain=" + UPDATED_EMAIL, "email.doesNotContain=" + DEFAULT_EMAIL);
    }

    @Test
    @Transactional
    void getAllStudentsByNotesIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedStudent = studentRepository.saveAndFlush(student);

        // Get all the studentList where notes equals to
        defaultStudentFiltering("notes.equals=" + DEFAULT_NOTES, "notes.equals=" + UPDATED_NOTES);
    }

    @Test
    @Transactional
    void getAllStudentsByNotesIsInShouldWork() throws Exception {
        // Initialize the database
        insertedStudent = studentRepository.saveAndFlush(student);

        // Get all the studentList where notes in
        defaultStudentFiltering("notes.in=" + DEFAULT_NOTES + "," + UPDATED_NOTES, "notes.in=" + UPDATED_NOTES);
    }

    @Test
    @Transactional
    void getAllStudentsByNotesIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedStudent = studentRepository.saveAndFlush(student);

        // Get all the studentList where notes is not null
        defaultStudentFiltering("notes.specified=true", "notes.specified=false");
    }

    @Test
    @Transactional
    void getAllStudentsByNotesContainsSomething() throws Exception {
        // Initialize the database
        insertedStudent = studentRepository.saveAndFlush(student);

        // Get all the studentList where notes contains
        defaultStudentFiltering("notes.contains=" + DEFAULT_NOTES, "notes.contains=" + UPDATED_NOTES);
    }

    @Test
    @Transactional
    void getAllStudentsByNotesNotContainsSomething() throws Exception {
        // Initialize the database
        insertedStudent = studentRepository.saveAndFlush(student);

        // Get all the studentList where notes does not contain
        defaultStudentFiltering("notes.doesNotContain=" + UPDATED_NOTES, "notes.doesNotContain=" + DEFAULT_NOTES);
    }

    @Test
    @Transactional
    void getAllStudentsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedStudent = studentRepository.saveAndFlush(student);

        // Get all the studentList where status equals to
        defaultStudentFiltering("status.equals=" + DEFAULT_STATUS, "status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllStudentsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        insertedStudent = studentRepository.saveAndFlush(student);

        // Get all the studentList where status in
        defaultStudentFiltering("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS, "status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllStudentsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedStudent = studentRepository.saveAndFlush(student);

        // Get all the studentList where status is not null
        defaultStudentFiltering("status.specified=true", "status.specified=false");
    }

    @Test
    @Transactional
    void getAllStudentsByDateEnrollmentIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedStudent = studentRepository.saveAndFlush(student);

        // Get all the studentList where dateEnrollment equals to
        defaultStudentFiltering("dateEnrollment.equals=" + DEFAULT_DATE_ENROLLMENT, "dateEnrollment.equals=" + UPDATED_DATE_ENROLLMENT);
    }

    @Test
    @Transactional
    void getAllStudentsByDateEnrollmentIsInShouldWork() throws Exception {
        // Initialize the database
        insertedStudent = studentRepository.saveAndFlush(student);

        // Get all the studentList where dateEnrollment in
        defaultStudentFiltering(
            "dateEnrollment.in=" + DEFAULT_DATE_ENROLLMENT + "," + UPDATED_DATE_ENROLLMENT,
            "dateEnrollment.in=" + UPDATED_DATE_ENROLLMENT
        );
    }

    @Test
    @Transactional
    void getAllStudentsByDateEnrollmentIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedStudent = studentRepository.saveAndFlush(student);

        // Get all the studentList where dateEnrollment is not null
        defaultStudentFiltering("dateEnrollment.specified=true", "dateEnrollment.specified=false");
    }

    private void defaultStudentFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultStudentShouldBeFound(shouldBeFound);
        defaultStudentShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultStudentShouldBeFound(String filter) throws Exception {
        restStudentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(student.getId().intValue())))
            .andExpect(jsonPath("$.[*].studentCode").value(hasItem(DEFAULT_STUDENT_CODE)))
            .andExpect(jsonPath("$.[*].fullName").value(hasItem(DEFAULT_FULL_NAME)))
            .andExpect(jsonPath("$.[*].dateOfBirth").value(hasItem(DEFAULT_DATE_OF_BIRTH.toString())))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].dateEnrollment").value(hasItem(DEFAULT_DATE_ENROLLMENT.toString())));

        // Check, that the count call also returns 1
        restStudentMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultStudentShouldNotBeFound(String filter) throws Exception {
        restStudentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restStudentMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingStudent() throws Exception {
        // Get the student
        restStudentMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingStudent() throws Exception {
        // Initialize the database
        insertedStudent = studentRepository.saveAndFlush(student);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the student
        Student updatedStudent = studentRepository.findById(student.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedStudent are not directly saved in db
        em.detach(updatedStudent);
        updatedStudent
            .studentCode(UPDATED_STUDENT_CODE)
            .fullName(UPDATED_FULL_NAME)
            .dateOfBirth(UPDATED_DATE_OF_BIRTH)
            .gender(UPDATED_GENDER)
            .address(UPDATED_ADDRESS)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .email(UPDATED_EMAIL)
            .notes(UPDATED_NOTES)
            .status(UPDATED_STATUS)
            .dateEnrollment(UPDATED_DATE_ENROLLMENT);
        StudentDTO studentDTO = studentMapper.toDto(updatedStudent);

        restStudentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, studentDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(studentDTO))
            )
            .andExpect(status().isOk());

        // Validate the Student in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedStudentToMatchAllProperties(updatedStudent);
    }

    @Test
    @Transactional
    void putNonExistingStudent() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        student.setId(longCount.incrementAndGet());

        // Create the Student
        StudentDTO studentDTO = studentMapper.toDto(student);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStudentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, studentDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(studentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Student in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchStudent() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        student.setId(longCount.incrementAndGet());

        // Create the Student
        StudentDTO studentDTO = studentMapper.toDto(student);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStudentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(studentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Student in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamStudent() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        student.setId(longCount.incrementAndGet());

        // Create the Student
        StudentDTO studentDTO = studentMapper.toDto(student);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStudentMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(studentDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Student in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateStudentWithPatch() throws Exception {
        // Initialize the database
        insertedStudent = studentRepository.saveAndFlush(student);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the student using partial update
        Student partialUpdatedStudent = new Student();
        partialUpdatedStudent.setId(student.getId());

        partialUpdatedStudent
            .gender(UPDATED_GENDER)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .status(UPDATED_STATUS)
            .dateEnrollment(UPDATED_DATE_ENROLLMENT);

        restStudentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStudent.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedStudent))
            )
            .andExpect(status().isOk());

        // Validate the Student in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertStudentUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedStudent, student), getPersistedStudent(student));
    }

    @Test
    @Transactional
    void fullUpdateStudentWithPatch() throws Exception {
        // Initialize the database
        insertedStudent = studentRepository.saveAndFlush(student);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the student using partial update
        Student partialUpdatedStudent = new Student();
        partialUpdatedStudent.setId(student.getId());

        partialUpdatedStudent
            .studentCode(UPDATED_STUDENT_CODE)
            .fullName(UPDATED_FULL_NAME)
            .dateOfBirth(UPDATED_DATE_OF_BIRTH)
            .gender(UPDATED_GENDER)
            .address(UPDATED_ADDRESS)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .email(UPDATED_EMAIL)
            .notes(UPDATED_NOTES)
            .status(UPDATED_STATUS)
            .dateEnrollment(UPDATED_DATE_ENROLLMENT);

        restStudentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStudent.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedStudent))
            )
            .andExpect(status().isOk());

        // Validate the Student in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertStudentUpdatableFieldsEquals(partialUpdatedStudent, getPersistedStudent(partialUpdatedStudent));
    }

    @Test
    @Transactional
    void patchNonExistingStudent() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        student.setId(longCount.incrementAndGet());

        // Create the Student
        StudentDTO studentDTO = studentMapper.toDto(student);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStudentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, studentDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(studentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Student in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchStudent() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        student.setId(longCount.incrementAndGet());

        // Create the Student
        StudentDTO studentDTO = studentMapper.toDto(student);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStudentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(studentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Student in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamStudent() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        student.setId(longCount.incrementAndGet());

        // Create the Student
        StudentDTO studentDTO = studentMapper.toDto(student);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStudentMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(studentDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Student in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteStudent() throws Exception {
        // Initialize the database
        insertedStudent = studentRepository.saveAndFlush(student);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the student
        restStudentMockMvc
            .perform(delete(ENTITY_API_URL_ID, student.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return studentRepository.count();
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

    protected Student getPersistedStudent(Student student) {
        return studentRepository.findById(student.getId()).orElseThrow();
    }

    protected void assertPersistedStudentToMatchAllProperties(Student expectedStudent) {
        assertStudentAllPropertiesEquals(expectedStudent, getPersistedStudent(expectedStudent));
    }

    protected void assertPersistedStudentToMatchUpdatableProperties(Student expectedStudent) {
        assertStudentAllUpdatablePropertiesEquals(expectedStudent, getPersistedStudent(expectedStudent));
    }
}
