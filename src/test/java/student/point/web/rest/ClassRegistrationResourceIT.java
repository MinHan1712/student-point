package student.point.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static student.point.domain.ClassRegistrationAsserts.*;
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
import student.point.domain.ClassRegistration;
import student.point.domain.Classes;
import student.point.domain.Student;
import student.point.domain.enumeration.ClassRegistrationStatus;
import student.point.repository.ClassRegistrationRepository;
import student.point.service.dto.ClassRegistrationDTO;
import student.point.service.mapper.ClassRegistrationMapper;

/**
 * Integration tests for the {@link ClassRegistrationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ClassRegistrationResourceIT {

    private static final Instant DEFAULT_REGISTER_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_REGISTER_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final ClassRegistrationStatus DEFAULT_STATUS = ClassRegistrationStatus.Completed;
    private static final ClassRegistrationStatus UPDATED_STATUS = ClassRegistrationStatus.InProgress;

    private static final String DEFAULT_REMARKS = "AAAAAAAAAA";
    private static final String UPDATED_REMARKS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/class-registrations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ClassRegistrationRepository classRegistrationRepository;

    @Autowired
    private ClassRegistrationMapper classRegistrationMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restClassRegistrationMockMvc;

    private ClassRegistration classRegistration;

    private ClassRegistration insertedClassRegistration;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ClassRegistration createEntity() {
        return new ClassRegistration().registerDate(DEFAULT_REGISTER_DATE).status(DEFAULT_STATUS).remarks(DEFAULT_REMARKS);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ClassRegistration createUpdatedEntity() {
        return new ClassRegistration().registerDate(UPDATED_REGISTER_DATE).status(UPDATED_STATUS).remarks(UPDATED_REMARKS);
    }

    @BeforeEach
    public void initTest() {
        classRegistration = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedClassRegistration != null) {
            classRegistrationRepository.delete(insertedClassRegistration);
            insertedClassRegistration = null;
        }
    }

    @Test
    @Transactional
    void createClassRegistration() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the ClassRegistration
        ClassRegistrationDTO classRegistrationDTO = classRegistrationMapper.toDto(classRegistration);
        var returnedClassRegistrationDTO = om.readValue(
            restClassRegistrationMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(classRegistrationDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            ClassRegistrationDTO.class
        );

        // Validate the ClassRegistration in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedClassRegistration = classRegistrationMapper.toEntity(returnedClassRegistrationDTO);
        assertClassRegistrationUpdatableFieldsEquals(returnedClassRegistration, getPersistedClassRegistration(returnedClassRegistration));

        insertedClassRegistration = returnedClassRegistration;
    }

    @Test
    @Transactional
    void createClassRegistrationWithExistingId() throws Exception {
        // Create the ClassRegistration with an existing ID
        classRegistration.setId(1L);
        ClassRegistrationDTO classRegistrationDTO = classRegistrationMapper.toDto(classRegistration);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restClassRegistrationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(classRegistrationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ClassRegistration in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllClassRegistrations() throws Exception {
        // Initialize the database
        insertedClassRegistration = classRegistrationRepository.saveAndFlush(classRegistration);

        // Get all the classRegistrationList
        restClassRegistrationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(classRegistration.getId().intValue())))
            .andExpect(jsonPath("$.[*].registerDate").value(hasItem(DEFAULT_REGISTER_DATE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS)));
    }

    @Test
    @Transactional
    void getClassRegistration() throws Exception {
        // Initialize the database
        insertedClassRegistration = classRegistrationRepository.saveAndFlush(classRegistration);

        // Get the classRegistration
        restClassRegistrationMockMvc
            .perform(get(ENTITY_API_URL_ID, classRegistration.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(classRegistration.getId().intValue()))
            .andExpect(jsonPath("$.registerDate").value(DEFAULT_REGISTER_DATE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.remarks").value(DEFAULT_REMARKS));
    }

    @Test
    @Transactional
    void getClassRegistrationsByIdFiltering() throws Exception {
        // Initialize the database
        insertedClassRegistration = classRegistrationRepository.saveAndFlush(classRegistration);

        Long id = classRegistration.getId();

        defaultClassRegistrationFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultClassRegistrationFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultClassRegistrationFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllClassRegistrationsByRegisterDateIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedClassRegistration = classRegistrationRepository.saveAndFlush(classRegistration);

        // Get all the classRegistrationList where registerDate equals to
        defaultClassRegistrationFiltering("registerDate.equals=" + DEFAULT_REGISTER_DATE, "registerDate.equals=" + UPDATED_REGISTER_DATE);
    }

    @Test
    @Transactional
    void getAllClassRegistrationsByRegisterDateIsInShouldWork() throws Exception {
        // Initialize the database
        insertedClassRegistration = classRegistrationRepository.saveAndFlush(classRegistration);

        // Get all the classRegistrationList where registerDate in
        defaultClassRegistrationFiltering(
            "registerDate.in=" + DEFAULT_REGISTER_DATE + "," + UPDATED_REGISTER_DATE,
            "registerDate.in=" + UPDATED_REGISTER_DATE
        );
    }

    @Test
    @Transactional
    void getAllClassRegistrationsByRegisterDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedClassRegistration = classRegistrationRepository.saveAndFlush(classRegistration);

        // Get all the classRegistrationList where registerDate is not null
        defaultClassRegistrationFiltering("registerDate.specified=true", "registerDate.specified=false");
    }

    @Test
    @Transactional
    void getAllClassRegistrationsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedClassRegistration = classRegistrationRepository.saveAndFlush(classRegistration);

        // Get all the classRegistrationList where status equals to
        defaultClassRegistrationFiltering("status.equals=" + DEFAULT_STATUS, "status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllClassRegistrationsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        insertedClassRegistration = classRegistrationRepository.saveAndFlush(classRegistration);

        // Get all the classRegistrationList where status in
        defaultClassRegistrationFiltering("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS, "status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllClassRegistrationsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedClassRegistration = classRegistrationRepository.saveAndFlush(classRegistration);

        // Get all the classRegistrationList where status is not null
        defaultClassRegistrationFiltering("status.specified=true", "status.specified=false");
    }

    @Test
    @Transactional
    void getAllClassRegistrationsByRemarksIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedClassRegistration = classRegistrationRepository.saveAndFlush(classRegistration);

        // Get all the classRegistrationList where remarks equals to
        defaultClassRegistrationFiltering("remarks.equals=" + DEFAULT_REMARKS, "remarks.equals=" + UPDATED_REMARKS);
    }

    @Test
    @Transactional
    void getAllClassRegistrationsByRemarksIsInShouldWork() throws Exception {
        // Initialize the database
        insertedClassRegistration = classRegistrationRepository.saveAndFlush(classRegistration);

        // Get all the classRegistrationList where remarks in
        defaultClassRegistrationFiltering("remarks.in=" + DEFAULT_REMARKS + "," + UPDATED_REMARKS, "remarks.in=" + UPDATED_REMARKS);
    }

    @Test
    @Transactional
    void getAllClassRegistrationsByRemarksIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedClassRegistration = classRegistrationRepository.saveAndFlush(classRegistration);

        // Get all the classRegistrationList where remarks is not null
        defaultClassRegistrationFiltering("remarks.specified=true", "remarks.specified=false");
    }

    @Test
    @Transactional
    void getAllClassRegistrationsByRemarksContainsSomething() throws Exception {
        // Initialize the database
        insertedClassRegistration = classRegistrationRepository.saveAndFlush(classRegistration);

        // Get all the classRegistrationList where remarks contains
        defaultClassRegistrationFiltering("remarks.contains=" + DEFAULT_REMARKS, "remarks.contains=" + UPDATED_REMARKS);
    }

    @Test
    @Transactional
    void getAllClassRegistrationsByRemarksNotContainsSomething() throws Exception {
        // Initialize the database
        insertedClassRegistration = classRegistrationRepository.saveAndFlush(classRegistration);

        // Get all the classRegistrationList where remarks does not contain
        defaultClassRegistrationFiltering("remarks.doesNotContain=" + UPDATED_REMARKS, "remarks.doesNotContain=" + DEFAULT_REMARKS);
    }

    @Test
    @Transactional
    void getAllClassRegistrationsByStudentIsEqualToSomething() throws Exception {
        Student student;
        if (TestUtil.findAll(em, Student.class).isEmpty()) {
            classRegistrationRepository.saveAndFlush(classRegistration);
            student = StudentResourceIT.createEntity();
        } else {
            student = TestUtil.findAll(em, Student.class).get(0);
        }
        em.persist(student);
        em.flush();
        classRegistration.setStudent(student);
        classRegistrationRepository.saveAndFlush(classRegistration);
        Long studentId = student.getId();
        // Get all the classRegistrationList where student equals to studentId
        defaultClassRegistrationShouldBeFound("studentId.equals=" + studentId);

        // Get all the classRegistrationList where student equals to (studentId + 1)
        defaultClassRegistrationShouldNotBeFound("studentId.equals=" + (studentId + 1));
    }

    @Test
    @Transactional
    void getAllClassRegistrationsByClassesIsEqualToSomething() throws Exception {
        Classes classes;
        if (TestUtil.findAll(em, Classes.class).isEmpty()) {
            classRegistrationRepository.saveAndFlush(classRegistration);
            classes = ClassesResourceIT.createEntity();
        } else {
            classes = TestUtil.findAll(em, Classes.class).get(0);
        }
        em.persist(classes);
        em.flush();
        classRegistration.setClasses(classes);
        classRegistrationRepository.saveAndFlush(classRegistration);
        Long classesId = classes.getId();
        // Get all the classRegistrationList where classes equals to classesId
        defaultClassRegistrationShouldBeFound("classesId.equals=" + classesId);

        // Get all the classRegistrationList where classes equals to (classesId + 1)
        defaultClassRegistrationShouldNotBeFound("classesId.equals=" + (classesId + 1));
    }

    private void defaultClassRegistrationFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultClassRegistrationShouldBeFound(shouldBeFound);
        defaultClassRegistrationShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultClassRegistrationShouldBeFound(String filter) throws Exception {
        restClassRegistrationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(classRegistration.getId().intValue())))
            .andExpect(jsonPath("$.[*].registerDate").value(hasItem(DEFAULT_REGISTER_DATE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS)));

        // Check, that the count call also returns 1
        restClassRegistrationMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultClassRegistrationShouldNotBeFound(String filter) throws Exception {
        restClassRegistrationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restClassRegistrationMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingClassRegistration() throws Exception {
        // Get the classRegistration
        restClassRegistrationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingClassRegistration() throws Exception {
        // Initialize the database
        insertedClassRegistration = classRegistrationRepository.saveAndFlush(classRegistration);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the classRegistration
        ClassRegistration updatedClassRegistration = classRegistrationRepository.findById(classRegistration.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedClassRegistration are not directly saved in db
        em.detach(updatedClassRegistration);
        updatedClassRegistration.registerDate(UPDATED_REGISTER_DATE).status(UPDATED_STATUS).remarks(UPDATED_REMARKS);
        ClassRegistrationDTO classRegistrationDTO = classRegistrationMapper.toDto(updatedClassRegistration);

        restClassRegistrationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, classRegistrationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(classRegistrationDTO))
            )
            .andExpect(status().isOk());

        // Validate the ClassRegistration in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedClassRegistrationToMatchAllProperties(updatedClassRegistration);
    }

    @Test
    @Transactional
    void putNonExistingClassRegistration() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        classRegistration.setId(longCount.incrementAndGet());

        // Create the ClassRegistration
        ClassRegistrationDTO classRegistrationDTO = classRegistrationMapper.toDto(classRegistration);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClassRegistrationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, classRegistrationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(classRegistrationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClassRegistration in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchClassRegistration() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        classRegistration.setId(longCount.incrementAndGet());

        // Create the ClassRegistration
        ClassRegistrationDTO classRegistrationDTO = classRegistrationMapper.toDto(classRegistration);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClassRegistrationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(classRegistrationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClassRegistration in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamClassRegistration() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        classRegistration.setId(longCount.incrementAndGet());

        // Create the ClassRegistration
        ClassRegistrationDTO classRegistrationDTO = classRegistrationMapper.toDto(classRegistration);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClassRegistrationMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(classRegistrationDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ClassRegistration in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateClassRegistrationWithPatch() throws Exception {
        // Initialize the database
        insertedClassRegistration = classRegistrationRepository.saveAndFlush(classRegistration);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the classRegistration using partial update
        ClassRegistration partialUpdatedClassRegistration = new ClassRegistration();
        partialUpdatedClassRegistration.setId(classRegistration.getId());

        partialUpdatedClassRegistration.registerDate(UPDATED_REGISTER_DATE).status(UPDATED_STATUS).remarks(UPDATED_REMARKS);

        restClassRegistrationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedClassRegistration.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedClassRegistration))
            )
            .andExpect(status().isOk());

        // Validate the ClassRegistration in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertClassRegistrationUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedClassRegistration, classRegistration),
            getPersistedClassRegistration(classRegistration)
        );
    }

    @Test
    @Transactional
    void fullUpdateClassRegistrationWithPatch() throws Exception {
        // Initialize the database
        insertedClassRegistration = classRegistrationRepository.saveAndFlush(classRegistration);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the classRegistration using partial update
        ClassRegistration partialUpdatedClassRegistration = new ClassRegistration();
        partialUpdatedClassRegistration.setId(classRegistration.getId());

        partialUpdatedClassRegistration.registerDate(UPDATED_REGISTER_DATE).status(UPDATED_STATUS).remarks(UPDATED_REMARKS);

        restClassRegistrationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedClassRegistration.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedClassRegistration))
            )
            .andExpect(status().isOk());

        // Validate the ClassRegistration in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertClassRegistrationUpdatableFieldsEquals(
            partialUpdatedClassRegistration,
            getPersistedClassRegistration(partialUpdatedClassRegistration)
        );
    }

    @Test
    @Transactional
    void patchNonExistingClassRegistration() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        classRegistration.setId(longCount.incrementAndGet());

        // Create the ClassRegistration
        ClassRegistrationDTO classRegistrationDTO = classRegistrationMapper.toDto(classRegistration);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClassRegistrationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, classRegistrationDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(classRegistrationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClassRegistration in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchClassRegistration() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        classRegistration.setId(longCount.incrementAndGet());

        // Create the ClassRegistration
        ClassRegistrationDTO classRegistrationDTO = classRegistrationMapper.toDto(classRegistration);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClassRegistrationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(classRegistrationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClassRegistration in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamClassRegistration() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        classRegistration.setId(longCount.incrementAndGet());

        // Create the ClassRegistration
        ClassRegistrationDTO classRegistrationDTO = classRegistrationMapper.toDto(classRegistration);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClassRegistrationMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(classRegistrationDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ClassRegistration in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteClassRegistration() throws Exception {
        // Initialize the database
        insertedClassRegistration = classRegistrationRepository.saveAndFlush(classRegistration);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the classRegistration
        restClassRegistrationMockMvc
            .perform(delete(ENTITY_API_URL_ID, classRegistration.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return classRegistrationRepository.count();
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

    protected ClassRegistration getPersistedClassRegistration(ClassRegistration classRegistration) {
        return classRegistrationRepository.findById(classRegistration.getId()).orElseThrow();
    }

    protected void assertPersistedClassRegistrationToMatchAllProperties(ClassRegistration expectedClassRegistration) {
        assertClassRegistrationAllPropertiesEquals(expectedClassRegistration, getPersistedClassRegistration(expectedClassRegistration));
    }

    protected void assertPersistedClassRegistrationToMatchUpdatableProperties(ClassRegistration expectedClassRegistration) {
        assertClassRegistrationAllUpdatablePropertiesEquals(
            expectedClassRegistration,
            getPersistedClassRegistration(expectedClassRegistration)
        );
    }
}
