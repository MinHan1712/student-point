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
import student.point.repository.StatisticsDetailsRepository;
import student.point.service.StatisticsDetailsQueryService;
import student.point.service.StatisticsDetailsService;
import student.point.service.criteria.StatisticsDetailsCriteria;
import student.point.service.dto.StatisticsDetailsDTO;
import student.point.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link student.point.domain.StatisticsDetails}.
 */
@RestController
@RequestMapping("/api/statistics-details")
public class StatisticsDetailsResource {

    private static final Logger LOG = LoggerFactory.getLogger(StatisticsDetailsResource.class);

    private static final String ENTITY_NAME = "statisticsDetails";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StatisticsDetailsService statisticsDetailsService;

    private final StatisticsDetailsRepository statisticsDetailsRepository;

    private final StatisticsDetailsQueryService statisticsDetailsQueryService;

    public StatisticsDetailsResource(
        StatisticsDetailsService statisticsDetailsService,
        StatisticsDetailsRepository statisticsDetailsRepository,
        StatisticsDetailsQueryService statisticsDetailsQueryService
    ) {
        this.statisticsDetailsService = statisticsDetailsService;
        this.statisticsDetailsRepository = statisticsDetailsRepository;
        this.statisticsDetailsQueryService = statisticsDetailsQueryService;
    }

    /**
     * {@code POST  /statistics-details} : Create a new statisticsDetails.
     *
     * @param statisticsDetailsDTO the statisticsDetailsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new statisticsDetailsDTO, or with status {@code 400 (Bad Request)} if the statisticsDetails has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<StatisticsDetailsDTO> createStatisticsDetails(@RequestBody StatisticsDetailsDTO statisticsDetailsDTO)
        throws URISyntaxException {
        LOG.debug("REST request to save StatisticsDetails : {}", statisticsDetailsDTO);
        if (statisticsDetailsDTO.getId() != null) {
            throw new BadRequestAlertException("A new statisticsDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        statisticsDetailsDTO = statisticsDetailsService.save(statisticsDetailsDTO);
        return ResponseEntity.created(new URI("/api/statistics-details/" + statisticsDetailsDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, statisticsDetailsDTO.getId().toString()))
            .body(statisticsDetailsDTO);
    }

    /**
     * {@code PUT  /statistics-details/:id} : Updates an existing statisticsDetails.
     *
     * @param id the id of the statisticsDetailsDTO to save.
     * @param statisticsDetailsDTO the statisticsDetailsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated statisticsDetailsDTO,
     * or with status {@code 400 (Bad Request)} if the statisticsDetailsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the statisticsDetailsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<StatisticsDetailsDTO> updateStatisticsDetails(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody StatisticsDetailsDTO statisticsDetailsDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update StatisticsDetails : {}, {}", id, statisticsDetailsDTO);
        if (statisticsDetailsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, statisticsDetailsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!statisticsDetailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        statisticsDetailsDTO = statisticsDetailsService.update(statisticsDetailsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, statisticsDetailsDTO.getId().toString()))
            .body(statisticsDetailsDTO);
    }

    /**
     * {@code PATCH  /statistics-details/:id} : Partial updates given fields of an existing statisticsDetails, field will ignore if it is null
     *
     * @param id the id of the statisticsDetailsDTO to save.
     * @param statisticsDetailsDTO the statisticsDetailsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated statisticsDetailsDTO,
     * or with status {@code 400 (Bad Request)} if the statisticsDetailsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the statisticsDetailsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the statisticsDetailsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<StatisticsDetailsDTO> partialUpdateStatisticsDetails(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody StatisticsDetailsDTO statisticsDetailsDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update StatisticsDetails partially : {}, {}", id, statisticsDetailsDTO);
        if (statisticsDetailsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, statisticsDetailsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!statisticsDetailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<StatisticsDetailsDTO> result = statisticsDetailsService.partialUpdate(statisticsDetailsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, statisticsDetailsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /statistics-details} : get all the statisticsDetails.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of statisticsDetails in body.
     */
    @GetMapping("")
    public ResponseEntity<List<StatisticsDetailsDTO>> getAllStatisticsDetails(
        StatisticsDetailsCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get StatisticsDetails by criteria: {}", criteria);

        Page<StatisticsDetailsDTO> page = statisticsDetailsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /statistics-details/count} : count all the statisticsDetails.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countStatisticsDetails(StatisticsDetailsCriteria criteria) {
        LOG.debug("REST request to count StatisticsDetails by criteria: {}", criteria);
        return ResponseEntity.ok().body(statisticsDetailsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /statistics-details/:id} : get the "id" statisticsDetails.
     *
     * @param id the id of the statisticsDetailsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the statisticsDetailsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<StatisticsDetailsDTO> getStatisticsDetails(@PathVariable("id") Long id) {
        LOG.debug("REST request to get StatisticsDetails : {}", id);
        Optional<StatisticsDetailsDTO> statisticsDetailsDTO = statisticsDetailsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(statisticsDetailsDTO);
    }

    /**
     * {@code DELETE  /statistics-details/:id} : delete the "id" statisticsDetails.
     *
     * @param id the id of the statisticsDetailsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStatisticsDetails(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete StatisticsDetails : {}", id);
        statisticsDetailsService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
