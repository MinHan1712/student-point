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
import student.point.repository.ConductScoresRepository;
import student.point.service.ConductScoresQueryService;
import student.point.service.ConductScoresService;
import student.point.service.criteria.ConductScoresCriteria;
import student.point.service.dto.ConductScoresDTO;
import student.point.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link student.point.domain.ConductScores}.
 */
@RestController
@RequestMapping("/api/conduct-scores")
public class ConductScoresResource {

    private static final Logger LOG = LoggerFactory.getLogger(ConductScoresResource.class);

    private static final String ENTITY_NAME = "conductScores";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ConductScoresService conductScoresService;

    private final ConductScoresRepository conductScoresRepository;

    private final ConductScoresQueryService conductScoresQueryService;

    public ConductScoresResource(
        ConductScoresService conductScoresService,
        ConductScoresRepository conductScoresRepository,
        ConductScoresQueryService conductScoresQueryService
    ) {
        this.conductScoresService = conductScoresService;
        this.conductScoresRepository = conductScoresRepository;
        this.conductScoresQueryService = conductScoresQueryService;
    }

    /**
     * {@code POST  /conduct-scores} : Create a new conductScores.
     *
     * @param conductScoresDTO the conductScoresDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new conductScoresDTO, or with status {@code 400 (Bad Request)} if the conductScores has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ConductScoresDTO> createConductScores(@RequestBody ConductScoresDTO conductScoresDTO) throws URISyntaxException {
        LOG.debug("REST request to save ConductScores : {}", conductScoresDTO);
        if (conductScoresDTO.getId() != null) {
            throw new BadRequestAlertException("A new conductScores cannot already have an ID", ENTITY_NAME, "idexists");
        }
        conductScoresDTO = conductScoresService.save(conductScoresDTO);
        return ResponseEntity.created(new URI("/api/conduct-scores/" + conductScoresDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, conductScoresDTO.getId().toString()))
            .body(conductScoresDTO);
    }

    /**
     * {@code PUT  /conduct-scores/:id} : Updates an existing conductScores.
     *
     * @param id the id of the conductScoresDTO to save.
     * @param conductScoresDTO the conductScoresDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated conductScoresDTO,
     * or with status {@code 400 (Bad Request)} if the conductScoresDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the conductScoresDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ConductScoresDTO> updateConductScores(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ConductScoresDTO conductScoresDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update ConductScores : {}, {}", id, conductScoresDTO);
        if (conductScoresDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, conductScoresDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!conductScoresRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        conductScoresDTO = conductScoresService.update(conductScoresDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, conductScoresDTO.getId().toString()))
            .body(conductScoresDTO);
    }

    /**
     * {@code PATCH  /conduct-scores/:id} : Partial updates given fields of an existing conductScores, field will ignore if it is null
     *
     * @param id the id of the conductScoresDTO to save.
     * @param conductScoresDTO the conductScoresDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated conductScoresDTO,
     * or with status {@code 400 (Bad Request)} if the conductScoresDTO is not valid,
     * or with status {@code 404 (Not Found)} if the conductScoresDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the conductScoresDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ConductScoresDTO> partialUpdateConductScores(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ConductScoresDTO conductScoresDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update ConductScores partially : {}, {}", id, conductScoresDTO);
        if (conductScoresDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, conductScoresDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!conductScoresRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ConductScoresDTO> result = conductScoresService.partialUpdate(conductScoresDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, conductScoresDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /conduct-scores} : get all the conductScores.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of conductScores in body.
     */
    @GetMapping("")
    public ResponseEntity<List<ConductScoresDTO>> getAllConductScores(
        ConductScoresCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get ConductScores by criteria: {}", criteria);

        Page<ConductScoresDTO> page = conductScoresQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /conduct-scores/count} : count all the conductScores.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countConductScores(ConductScoresCriteria criteria) {
        LOG.debug("REST request to count ConductScores by criteria: {}", criteria);
        return ResponseEntity.ok().body(conductScoresQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /conduct-scores/:id} : get the "id" conductScores.
     *
     * @param id the id of the conductScoresDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the conductScoresDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ConductScoresDTO> getConductScores(@PathVariable("id") Long id) {
        LOG.debug("REST request to get ConductScores : {}", id);
        Optional<ConductScoresDTO> conductScoresDTO = conductScoresService.findOne(id);
        return ResponseUtil.wrapOrNotFound(conductScoresDTO);
    }

    /**
     * {@code DELETE  /conduct-scores/:id} : delete the "id" conductScores.
     *
     * @param id the id of the conductScoresDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteConductScores(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete ConductScores : {}", id);
        conductScoresService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
