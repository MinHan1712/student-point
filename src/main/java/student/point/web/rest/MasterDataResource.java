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
import student.point.repository.MasterDataRepository;
import student.point.service.MasterDataQueryService;
import student.point.service.MasterDataService;
import student.point.service.criteria.MasterDataCriteria;
import student.point.service.dto.MasterDataDTO;
import student.point.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link student.point.domain.MasterData}.
 */
@RestController
@RequestMapping("/api/master-data")
public class MasterDataResource {

    private static final Logger LOG = LoggerFactory.getLogger(MasterDataResource.class);

    private static final String ENTITY_NAME = "masterData";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MasterDataService masterDataService;

    private final MasterDataRepository masterDataRepository;

    private final MasterDataQueryService masterDataQueryService;

    public MasterDataResource(
        MasterDataService masterDataService,
        MasterDataRepository masterDataRepository,
        MasterDataQueryService masterDataQueryService
    ) {
        this.masterDataService = masterDataService;
        this.masterDataRepository = masterDataRepository;
        this.masterDataQueryService = masterDataQueryService;
    }

    /**
     * {@code POST  /master-data} : Create a new masterData.
     *
     * @param masterDataDTO the masterDataDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new masterDataDTO, or with status {@code 400 (Bad Request)} if the masterData has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<MasterDataDTO> createMasterData(@RequestBody MasterDataDTO masterDataDTO) throws URISyntaxException {
        LOG.debug("REST request to save MasterData : {}", masterDataDTO);
        if (masterDataDTO.getId() != null) {
            throw new BadRequestAlertException("A new masterData cannot already have an ID", ENTITY_NAME, "idexists");
        }
        masterDataDTO = masterDataService.save(masterDataDTO);
        return ResponseEntity.created(new URI("/api/master-data/" + masterDataDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, masterDataDTO.getId().toString()))
            .body(masterDataDTO);
    }

    /**
     * {@code PUT  /master-data/:id} : Updates an existing masterData.
     *
     * @param id the id of the masterDataDTO to save.
     * @param masterDataDTO the masterDataDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated masterDataDTO,
     * or with status {@code 400 (Bad Request)} if the masterDataDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the masterDataDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<MasterDataDTO> updateMasterData(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MasterDataDTO masterDataDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update MasterData : {}, {}", id, masterDataDTO);
        if (masterDataDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, masterDataDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!masterDataRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        masterDataDTO = masterDataService.update(masterDataDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, masterDataDTO.getId().toString()))
            .body(masterDataDTO);
    }

    /**
     * {@code PATCH  /master-data/:id} : Partial updates given fields of an existing masterData, field will ignore if it is null
     *
     * @param id the id of the masterDataDTO to save.
     * @param masterDataDTO the masterDataDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated masterDataDTO,
     * or with status {@code 400 (Bad Request)} if the masterDataDTO is not valid,
     * or with status {@code 404 (Not Found)} if the masterDataDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the masterDataDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MasterDataDTO> partialUpdateMasterData(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MasterDataDTO masterDataDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update MasterData partially : {}, {}", id, masterDataDTO);
        if (masterDataDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, masterDataDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!masterDataRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MasterDataDTO> result = masterDataService.partialUpdate(masterDataDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, masterDataDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /master-data} : get all the masterData.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of masterData in body.
     */
    @GetMapping("")
    public ResponseEntity<List<MasterDataDTO>> getAllMasterData(
        MasterDataCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get MasterData by criteria: {}", criteria);

        Page<MasterDataDTO> page = masterDataQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /master-data/count} : count all the masterData.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countMasterData(MasterDataCriteria criteria) {
        LOG.debug("REST request to count MasterData by criteria: {}", criteria);
        return ResponseEntity.ok().body(masterDataQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /master-data/:id} : get the "id" masterData.
     *
     * @param id the id of the masterDataDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the masterDataDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<MasterDataDTO> getMasterData(@PathVariable("id") Long id) {
        LOG.debug("REST request to get MasterData : {}", id);
        Optional<MasterDataDTO> masterDataDTO = masterDataService.findOne(id);
        return ResponseUtil.wrapOrNotFound(masterDataDTO);
    }

    /**
     * {@code DELETE  /master-data/:id} : delete the "id" masterData.
     *
     * @param id the id of the masterDataDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMasterData(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete MasterData : {}", id);
        masterDataService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
