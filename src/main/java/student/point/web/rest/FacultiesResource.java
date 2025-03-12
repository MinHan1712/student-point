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
import student.point.repository.FacultiesRepository;
import student.point.service.FacultiesQueryService;
import student.point.service.FacultiesService;
import student.point.service.criteria.FacultiesCriteria;
import student.point.service.dto.FacultiesDTO;
import student.point.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link student.point.domain.Faculties}.
 */
@RestController
@RequestMapping("/api/faculties")
public class FacultiesResource {

    private static final Logger LOG = LoggerFactory.getLogger(FacultiesResource.class);

    private static final String ENTITY_NAME = "faculties";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FacultiesService facultiesService;

    private final FacultiesRepository facultiesRepository;

    private final FacultiesQueryService facultiesQueryService;

    public FacultiesResource(
        FacultiesService facultiesService,
        FacultiesRepository facultiesRepository,
        FacultiesQueryService facultiesQueryService
    ) {
        this.facultiesService = facultiesService;
        this.facultiesRepository = facultiesRepository;
        this.facultiesQueryService = facultiesQueryService;
    }

    /**
     * {@code POST  /faculties} : Create a new faculties.
     *
     * @param facultiesDTO the facultiesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new facultiesDTO, or with status {@code 400 (Bad Request)} if the faculties has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<FacultiesDTO> createFaculties(@RequestBody FacultiesDTO facultiesDTO) throws URISyntaxException {
        LOG.debug("REST request to save Faculties : {}", facultiesDTO);
        if (facultiesDTO.getId() != null) {
            throw new BadRequestAlertException("A new faculties cannot already have an ID", ENTITY_NAME, "idexists");
        }
        facultiesDTO = facultiesService.save(facultiesDTO);
        return ResponseEntity.created(new URI("/api/faculties/" + facultiesDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, facultiesDTO.getId().toString()))
            .body(facultiesDTO);
    }

    /**
     * {@code PUT  /faculties/:id} : Updates an existing faculties.
     *
     * @param id the id of the facultiesDTO to save.
     * @param facultiesDTO the facultiesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated facultiesDTO,
     * or with status {@code 400 (Bad Request)} if the facultiesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the facultiesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<FacultiesDTO> updateFaculties(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FacultiesDTO facultiesDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update Faculties : {}, {}", id, facultiesDTO);
        if (facultiesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, facultiesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!facultiesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        facultiesDTO = facultiesService.update(facultiesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, facultiesDTO.getId().toString()))
            .body(facultiesDTO);
    }

    /**
     * {@code PATCH  /faculties/:id} : Partial updates given fields of an existing faculties, field will ignore if it is null
     *
     * @param id the id of the facultiesDTO to save.
     * @param facultiesDTO the facultiesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated facultiesDTO,
     * or with status {@code 400 (Bad Request)} if the facultiesDTO is not valid,
     * or with status {@code 404 (Not Found)} if the facultiesDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the facultiesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FacultiesDTO> partialUpdateFaculties(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FacultiesDTO facultiesDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Faculties partially : {}, {}", id, facultiesDTO);
        if (facultiesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, facultiesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!facultiesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FacultiesDTO> result = facultiesService.partialUpdate(facultiesDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, facultiesDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /faculties} : get all the faculties.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of faculties in body.
     */
    @GetMapping("")
    public ResponseEntity<List<FacultiesDTO>> getAllFaculties(
        FacultiesCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get Faculties by criteria: {}", criteria);

        Page<FacultiesDTO> page = facultiesQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /faculties/count} : count all the faculties.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countFaculties(FacultiesCriteria criteria) {
        LOG.debug("REST request to count Faculties by criteria: {}", criteria);
        return ResponseEntity.ok().body(facultiesQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /faculties/:id} : get the "id" faculties.
     *
     * @param id the id of the facultiesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the facultiesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<FacultiesDTO> getFaculties(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Faculties : {}", id);
        Optional<FacultiesDTO> facultiesDTO = facultiesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(facultiesDTO);
    }

    /**
     * {@code DELETE  /faculties/:id} : delete the "id" faculties.
     *
     * @param id the id of the facultiesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFaculties(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Faculties : {}", id);
        facultiesService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
