package student.point.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import student.point.repository.ClassCourseRepository;
import student.point.service.ClassCourseQueryService;
import student.point.service.ClassCourseService;
import student.point.service.criteria.ClassCourseCriteria;
import student.point.service.dto.ClassCourseDTO;
import student.point.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link student.point.domain.ClassCourse}.
 */
@RestController
@RequestMapping("/api/class-courses")
public class ClassCourseResource {

    private static final Logger LOG = LoggerFactory.getLogger(ClassCourseResource.class);

    private static final String ENTITY_NAME = "classCourse";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ClassCourseService classCourseService;

    private final ClassCourseRepository classCourseRepository;

    private final ClassCourseQueryService classCourseQueryService;

    public ClassCourseResource(
        ClassCourseService classCourseService,
        ClassCourseRepository classCourseRepository,
        ClassCourseQueryService classCourseQueryService
    ) {
        this.classCourseService = classCourseService;
        this.classCourseRepository = classCourseRepository;
        this.classCourseQueryService = classCourseQueryService;
    }

    /**
     * {@code POST  /class-courses} : Create a new classCourse.
     *
     * @param classCourseDTO the classCourseDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new classCourseDTO, or with status {@code 400 (Bad Request)} if the classCourse has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ClassCourseDTO> createClassCourse(@RequestBody ClassCourseDTO classCourseDTO) throws URISyntaxException {
        LOG.debug("REST request to save ClassCourse : {}", classCourseDTO);
        if (classCourseDTO.getId() != null) {
            throw new BadRequestAlertException("A new classCourse cannot already have an ID", ENTITY_NAME, "idexists");
        }
        classCourseDTO = classCourseService.save(classCourseDTO);
        return ResponseEntity.created(new URI("/api/class-courses/" + classCourseDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, classCourseDTO.getId().toString()))
            .body(classCourseDTO);
    }

    /**
     * {@code PUT  /class-courses/:id} : Updates an existing classCourse.
     *
     * @param id the id of the classCourseDTO to save.
     * @param classCourseDTO the classCourseDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated classCourseDTO,
     * or with status {@code 400 (Bad Request)} if the classCourseDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the classCourseDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ClassCourseDTO> updateClassCourse(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ClassCourseDTO classCourseDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update ClassCourse : {}, {}", id, classCourseDTO);
        if (classCourseDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, classCourseDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!classCourseRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        classCourseDTO = classCourseService.update(classCourseDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, classCourseDTO.getId().toString()))
            .body(classCourseDTO);
    }

    /**
     * {@code PATCH  /class-courses/:id} : Partial updates given fields of an existing classCourse, field will ignore if it is null
     *
     * @param id the id of the classCourseDTO to save.
     * @param classCourseDTO the classCourseDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated classCourseDTO,
     * or with status {@code 400 (Bad Request)} if the classCourseDTO is not valid,
     * or with status {@code 404 (Not Found)} if the classCourseDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the classCourseDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ClassCourseDTO> partialUpdateClassCourse(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ClassCourseDTO classCourseDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update ClassCourse partially : {}, {}", id, classCourseDTO);
        if (classCourseDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, classCourseDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!classCourseRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ClassCourseDTO> result = classCourseService.partialUpdate(classCourseDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, classCourseDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /class-courses} : get all the classCourses.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of classCourses in body.
     */
    @GetMapping("")
    public ResponseEntity<List<ClassCourseDTO>> getAllClassCourses(
        ClassCourseCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get ClassCourses by criteria: {}", criteria);

        Page<ClassCourseDTO> page = classCourseQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /class-courses/count} : count all the classCourses.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countClassCourses(ClassCourseCriteria criteria) {
        LOG.debug("REST request to count ClassCourses by criteria: {}", criteria);
        return ResponseEntity.ok().body(classCourseQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /class-courses/:id} : get the "id" classCourse.
     *
     * @param id the id of the classCourseDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the classCourseDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ClassCourseDTO> getClassCourse(@PathVariable("id") Long id) {
        LOG.debug("REST request to get ClassCourse : {}", id);
        Optional<ClassCourseDTO> classCourseDTO = classCourseService.findOne(id);
        return ResponseUtil.wrapOrNotFound(classCourseDTO);
    }

    /**
     * {@code DELETE  /class-courses/:id} : delete the "id" classCourse.
     *
     * @param id the id of the classCourseDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClassCourse(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete ClassCourse : {}", id);
        classCourseService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
