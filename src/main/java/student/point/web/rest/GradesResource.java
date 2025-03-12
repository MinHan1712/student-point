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
import student.point.repository.GradesRepository;
import student.point.service.GradesQueryService;
import student.point.service.GradesService;
import student.point.service.criteria.GradesCriteria;
import student.point.service.dto.GradesDTO;
import student.point.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link student.point.domain.Grades}.
 */
@RestController
@RequestMapping("/api/grades")
public class GradesResource {

    private static final Logger LOG = LoggerFactory.getLogger(GradesResource.class);

    private static final String ENTITY_NAME = "grades";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GradesService gradesService;

    private final GradesRepository gradesRepository;

    private final GradesQueryService gradesQueryService;

    public GradesResource(GradesService gradesService, GradesRepository gradesRepository, GradesQueryService gradesQueryService) {
        this.gradesService = gradesService;
        this.gradesRepository = gradesRepository;
        this.gradesQueryService = gradesQueryService;
    }

    /**
     * {@code POST  /grades} : Create a new grades.
     *
     * @param gradesDTO the gradesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new gradesDTO, or with status {@code 400 (Bad Request)} if the grades has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<GradesDTO> createGrades(@RequestBody GradesDTO gradesDTO) throws URISyntaxException {
        LOG.debug("REST request to save Grades : {}", gradesDTO);
        if (gradesDTO.getId() != null) {
            throw new BadRequestAlertException("A new grades cannot already have an ID", ENTITY_NAME, "idexists");
        }
        gradesDTO = gradesService.save(gradesDTO);
        return ResponseEntity.created(new URI("/api/grades/" + gradesDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, gradesDTO.getId().toString()))
            .body(gradesDTO);
    }

    /**
     * {@code PUT  /grades/:id} : Updates an existing grades.
     *
     * @param id the id of the gradesDTO to save.
     * @param gradesDTO the gradesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated gradesDTO,
     * or with status {@code 400 (Bad Request)} if the gradesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the gradesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<GradesDTO> updateGrades(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody GradesDTO gradesDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update Grades : {}, {}", id, gradesDTO);
        if (gradesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, gradesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!gradesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        gradesDTO = gradesService.update(gradesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, gradesDTO.getId().toString()))
            .body(gradesDTO);
    }

    /**
     * {@code PATCH  /grades/:id} : Partial updates given fields of an existing grades, field will ignore if it is null
     *
     * @param id the id of the gradesDTO to save.
     * @param gradesDTO the gradesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated gradesDTO,
     * or with status {@code 400 (Bad Request)} if the gradesDTO is not valid,
     * or with status {@code 404 (Not Found)} if the gradesDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the gradesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<GradesDTO> partialUpdateGrades(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody GradesDTO gradesDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Grades partially : {}, {}", id, gradesDTO);
        if (gradesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, gradesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!gradesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<GradesDTO> result = gradesService.partialUpdate(gradesDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, gradesDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /grades} : get all the grades.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of grades in body.
     */
    @GetMapping("")
    public ResponseEntity<List<GradesDTO>> getAllGrades(
        GradesCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get Grades by criteria: {}", criteria);

        Page<GradesDTO> page = gradesQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /grades/count} : count all the grades.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countGrades(GradesCriteria criteria) {
        LOG.debug("REST request to count Grades by criteria: {}", criteria);
        return ResponseEntity.ok().body(gradesQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /grades/:id} : get the "id" grades.
     *
     * @param id the id of the gradesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the gradesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<GradesDTO> getGrades(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Grades : {}", id);
        Optional<GradesDTO> gradesDTO = gradesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(gradesDTO);
    }

    /**
     * {@code DELETE  /grades/:id} : delete the "id" grades.
     *
     * @param id the id of the gradesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGrades(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Grades : {}", id);
        gradesService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
