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
import student.point.repository.StatisticsRepository;
import student.point.service.StatisticsQueryService;
import student.point.service.StatisticsService;
import student.point.service.criteria.StatisticsCriteria;
import student.point.service.dto.StatisticsDTO;
import student.point.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link student.point.domain.Statistics}.
 */
@RestController
@RequestMapping("/api/statistics")
public class StatisticsResource {

    private static final Logger LOG = LoggerFactory.getLogger(StatisticsResource.class);

    private static final String ENTITY_NAME = "statistics";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StatisticsService statisticsService;

    private final StatisticsRepository statisticsRepository;

    private final StatisticsQueryService statisticsQueryService;

    public StatisticsResource(
        StatisticsService statisticsService,
        StatisticsRepository statisticsRepository,
        StatisticsQueryService statisticsQueryService
    ) {
        this.statisticsService = statisticsService;
        this.statisticsRepository = statisticsRepository;
        this.statisticsQueryService = statisticsQueryService;
    }

    /**
     * {@code POST  /statistics} : Create a new statistics.
     *
     * @param statisticsDTO the statisticsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new statisticsDTO, or with status {@code 400 (Bad Request)} if the statistics has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<StatisticsDTO> createStatistics(@RequestBody StatisticsDTO statisticsDTO) throws URISyntaxException {
        LOG.debug("REST request to save Statistics : {}", statisticsDTO);
        if (statisticsDTO.getId() != null) {
            throw new BadRequestAlertException("A new statistics cannot already have an ID", ENTITY_NAME, "idexists");
        }
        statisticsDTO = statisticsService.save(statisticsDTO);
        return ResponseEntity.created(new URI("/api/statistics/" + statisticsDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, statisticsDTO.getId().toString()))
            .body(statisticsDTO);
    }

    /**
     * {@code PUT  /statistics/:id} : Updates an existing statistics.
     *
     * @param id the id of the statisticsDTO to save.
     * @param statisticsDTO the statisticsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated statisticsDTO,
     * or with status {@code 400 (Bad Request)} if the statisticsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the statisticsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<StatisticsDTO> updateStatistics(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody StatisticsDTO statisticsDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update Statistics : {}, {}", id, statisticsDTO);
        if (statisticsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, statisticsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!statisticsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        statisticsDTO = statisticsService.update(statisticsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, statisticsDTO.getId().toString()))
            .body(statisticsDTO);
    }

    /**
     * {@code PATCH  /statistics/:id} : Partial updates given fields of an existing statistics, field will ignore if it is null
     *
     * @param id the id of the statisticsDTO to save.
     * @param statisticsDTO the statisticsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated statisticsDTO,
     * or with status {@code 400 (Bad Request)} if the statisticsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the statisticsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the statisticsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<StatisticsDTO> partialUpdateStatistics(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody StatisticsDTO statisticsDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Statistics partially : {}, {}", id, statisticsDTO);
        if (statisticsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, statisticsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!statisticsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<StatisticsDTO> result = statisticsService.partialUpdate(statisticsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, statisticsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /statistics} : get all the statistics.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of statistics in body.
     */
    @GetMapping("")
    public ResponseEntity<List<StatisticsDTO>> getAllStatistics(
        StatisticsCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get Statistics by criteria: {}", criteria);

        Page<StatisticsDTO> page = statisticsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /statistics/count} : count all the statistics.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countStatistics(StatisticsCriteria criteria) {
        LOG.debug("REST request to count Statistics by criteria: {}", criteria);
        return ResponseEntity.ok().body(statisticsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /statistics/:id} : get the "id" statistics.
     *
     * @param id the id of the statisticsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the statisticsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<StatisticsDTO> getStatistics(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Statistics : {}", id);
        Optional<StatisticsDTO> statisticsDTO = statisticsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(statisticsDTO);
    }

    /**
     * {@code DELETE  /statistics/:id} : delete the "id" statistics.
     *
     * @param id the id of the statisticsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStatistics(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Statistics : {}", id);
        statisticsService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
