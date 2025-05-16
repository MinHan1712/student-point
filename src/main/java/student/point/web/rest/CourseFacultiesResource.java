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
import student.point.repository.CourseFacultiesRepository;
import student.point.service.CourseFacultiesQueryService;
import student.point.service.CourseFacultiesService;
import student.point.service.criteria.CourseFacultiesCriteria;
import student.point.service.dto.CourseFacultiesDTO;
import student.point.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link student.point.domain.CourseFaculties}.
 */
@RestController
@RequestMapping("/api/course-faculties")
public class CourseFacultiesResource {

    private static final Logger LOG = LoggerFactory.getLogger(CourseFacultiesResource.class);

    private static final String ENTITY_NAME = "courseFaculties";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CourseFacultiesService courseFacultiesService;

    private final CourseFacultiesRepository courseFacultiesRepository;

    private final CourseFacultiesQueryService courseFacultiesQueryService;

    public CourseFacultiesResource(
        CourseFacultiesService courseFacultiesService,
        CourseFacultiesRepository courseFacultiesRepository,
        CourseFacultiesQueryService courseFacultiesQueryService
    ) {
        this.courseFacultiesService = courseFacultiesService;
        this.courseFacultiesRepository = courseFacultiesRepository;
        this.courseFacultiesQueryService = courseFacultiesQueryService;
    }

    /**
     * {@code POST  /course-faculties} : Create a new courseFaculties.
     *
     * @param courseFacultiesDTO the courseFacultiesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new courseFacultiesDTO, or with status {@code 400 (Bad Request)} if the courseFaculties has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<CourseFacultiesDTO> createCourseFaculties(@RequestBody CourseFacultiesDTO courseFacultiesDTO)
        throws URISyntaxException {
        LOG.debug("REST request to save CourseFaculties : {}", courseFacultiesDTO);
        if (courseFacultiesDTO.getId() != null) {
            throw new BadRequestAlertException("A new courseFaculties cannot already have an ID", ENTITY_NAME, "idexists");
        }
        courseFacultiesDTO = courseFacultiesService.save(courseFacultiesDTO);
        return ResponseEntity.created(new URI("/api/course-faculties/" + courseFacultiesDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, courseFacultiesDTO.getId().toString()))
            .body(courseFacultiesDTO);
    }

    /**
     * {@code PUT  /course-faculties/:id} : Updates an existing courseFaculties.
     *
     * @param id the id of the courseFacultiesDTO to save.
     * @param courseFacultiesDTO the courseFacultiesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated courseFacultiesDTO,
     * or with status {@code 400 (Bad Request)} if the courseFacultiesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the courseFacultiesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CourseFacultiesDTO> updateCourseFaculties(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CourseFacultiesDTO courseFacultiesDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update CourseFaculties : {}, {}", id, courseFacultiesDTO);
        if (courseFacultiesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, courseFacultiesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!courseFacultiesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        courseFacultiesDTO = courseFacultiesService.update(courseFacultiesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, courseFacultiesDTO.getId().toString()))
            .body(courseFacultiesDTO);
    }

    /**
     * {@code PATCH  /course-faculties/:id} : Partial updates given fields of an existing courseFaculties, field will ignore if it is null
     *
     * @param id the id of the courseFacultiesDTO to save.
     * @param courseFacultiesDTO the courseFacultiesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated courseFacultiesDTO,
     * or with status {@code 400 (Bad Request)} if the courseFacultiesDTO is not valid,
     * or with status {@code 404 (Not Found)} if the courseFacultiesDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the courseFacultiesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CourseFacultiesDTO> partialUpdateCourseFaculties(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CourseFacultiesDTO courseFacultiesDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update CourseFaculties partially : {}, {}", id, courseFacultiesDTO);
        if (courseFacultiesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, courseFacultiesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!courseFacultiesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CourseFacultiesDTO> result = courseFacultiesService.partialUpdate(courseFacultiesDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, courseFacultiesDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /course-faculties} : get all the courseFaculties.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of courseFaculties in body.
     */
    @GetMapping("")
    public ResponseEntity<List<CourseFacultiesDTO>> getAllCourseFaculties(
        CourseFacultiesCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get CourseFaculties by criteria: {}", criteria);

        Page<CourseFacultiesDTO> page = courseFacultiesQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /course-faculties/count} : count all the courseFaculties.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countCourseFaculties(CourseFacultiesCriteria criteria) {
        LOG.debug("REST request to count CourseFaculties by criteria: {}", criteria);
        return ResponseEntity.ok().body(courseFacultiesQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /course-faculties/:id} : get the "id" courseFaculties.
     *
     * @param id the id of the courseFacultiesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the courseFacultiesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CourseFacultiesDTO> getCourseFaculties(@PathVariable("id") Long id) {
        LOG.debug("REST request to get CourseFaculties : {}", id);
        Optional<CourseFacultiesDTO> courseFacultiesDTO = courseFacultiesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(courseFacultiesDTO);
    }

    /**
     * {@code DELETE  /course-faculties/:id} : delete the "id" courseFaculties.
     *
     * @param id the id of the courseFacultiesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourseFaculties(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete CourseFaculties : {}", id);
        courseFacultiesService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
