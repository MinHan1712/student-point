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
import student.point.repository.TeachersRepository;
import student.point.service.TeachersQueryService;
import student.point.service.TeachersService;
import student.point.service.criteria.TeachersCriteria;
import student.point.service.dto.TeachersDTO;
import student.point.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link student.point.domain.Teachers}.
 */
@RestController
@RequestMapping("/api/teachers")
public class TeachersResource {

    private static final Logger LOG = LoggerFactory.getLogger(TeachersResource.class);

    private static final String ENTITY_NAME = "teachers";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TeachersService teachersService;

    private final TeachersRepository teachersRepository;

    private final TeachersQueryService teachersQueryService;

    public TeachersResource(
        TeachersService teachersService,
        TeachersRepository teachersRepository,
        TeachersQueryService teachersQueryService
    ) {
        this.teachersService = teachersService;
        this.teachersRepository = teachersRepository;
        this.teachersQueryService = teachersQueryService;
    }

    /**
     * {@code POST  /teachers} : Create a new teachers.
     *
     * @param teachersDTO the teachersDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new teachersDTO, or with status {@code 400 (Bad Request)} if the teachers has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<TeachersDTO> createTeachers(@RequestBody TeachersDTO teachersDTO) throws URISyntaxException {
        LOG.debug("REST request to save Teachers : {}", teachersDTO);
        if (teachersDTO.getId() != null) {
            throw new BadRequestAlertException("A new teachers cannot already have an ID", ENTITY_NAME, "idexists");
        }
        teachersDTO = teachersService.save(teachersDTO);
        return ResponseEntity.created(new URI("/api/teachers/" + teachersDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, teachersDTO.getId().toString()))
            .body(teachersDTO);
    }

    /**
     * {@code PUT  /teachers/:id} : Updates an existing teachers.
     *
     * @param id the id of the teachersDTO to save.
     * @param teachersDTO the teachersDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated teachersDTO,
     * or with status {@code 400 (Bad Request)} if the teachersDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the teachersDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<TeachersDTO> updateTeachers(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TeachersDTO teachersDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update Teachers : {}, {}", id, teachersDTO);
        if (teachersDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, teachersDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!teachersRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        teachersDTO = teachersService.update(teachersDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, teachersDTO.getId().toString()))
            .body(teachersDTO);
    }

    /**
     * {@code PATCH  /teachers/:id} : Partial updates given fields of an existing teachers, field will ignore if it is null
     *
     * @param id the id of the teachersDTO to save.
     * @param teachersDTO the teachersDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated teachersDTO,
     * or with status {@code 400 (Bad Request)} if the teachersDTO is not valid,
     * or with status {@code 404 (Not Found)} if the teachersDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the teachersDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TeachersDTO> partialUpdateTeachers(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TeachersDTO teachersDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Teachers partially : {}, {}", id, teachersDTO);
        if (teachersDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, teachersDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!teachersRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TeachersDTO> result = teachersService.partialUpdate(teachersDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, teachersDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /teachers} : get all the teachers.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of teachers in body.
     */
    @GetMapping("")
    public ResponseEntity<List<TeachersDTO>> getAllTeachers(
        TeachersCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get Teachers by criteria: {}", criteria);

        Page<TeachersDTO> page = teachersQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /teachers/count} : count all the teachers.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countTeachers(TeachersCriteria criteria) {
        LOG.debug("REST request to count Teachers by criteria: {}", criteria);
        return ResponseEntity.ok().body(teachersQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /teachers/:id} : get the "id" teachers.
     *
     * @param id the id of the teachersDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the teachersDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<TeachersDTO> getTeachers(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Teachers : {}", id);
        Optional<TeachersDTO> teachersDTO = teachersService.findOne(id);
        return ResponseUtil.wrapOrNotFound(teachersDTO);
    }

    /**
     * {@code DELETE  /teachers/:id} : delete the "id" teachers.
     *
     * @param id the id of the teachersDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeachers(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Teachers : {}", id);
        teachersService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
