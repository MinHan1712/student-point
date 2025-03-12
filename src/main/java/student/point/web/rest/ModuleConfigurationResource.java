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
import student.point.repository.ModuleConfigurationRepository;
import student.point.service.ModuleConfigurationQueryService;
import student.point.service.ModuleConfigurationService;
import student.point.service.criteria.ModuleConfigurationCriteria;
import student.point.service.dto.ModuleConfigurationDTO;
import student.point.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link student.point.domain.ModuleConfiguration}.
 */
@RestController
@RequestMapping("/api/module-configurations")
public class ModuleConfigurationResource {

    private static final Logger LOG = LoggerFactory.getLogger(ModuleConfigurationResource.class);

    private static final String ENTITY_NAME = "moduleConfiguration";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ModuleConfigurationService moduleConfigurationService;

    private final ModuleConfigurationRepository moduleConfigurationRepository;

    private final ModuleConfigurationQueryService moduleConfigurationQueryService;

    public ModuleConfigurationResource(
        ModuleConfigurationService moduleConfigurationService,
        ModuleConfigurationRepository moduleConfigurationRepository,
        ModuleConfigurationQueryService moduleConfigurationQueryService
    ) {
        this.moduleConfigurationService = moduleConfigurationService;
        this.moduleConfigurationRepository = moduleConfigurationRepository;
        this.moduleConfigurationQueryService = moduleConfigurationQueryService;
    }

    /**
     * {@code POST  /module-configurations} : Create a new moduleConfiguration.
     *
     * @param moduleConfigurationDTO the moduleConfigurationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new moduleConfigurationDTO, or with status {@code 400 (Bad Request)} if the moduleConfiguration has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ModuleConfigurationDTO> createModuleConfiguration(@RequestBody ModuleConfigurationDTO moduleConfigurationDTO)
        throws URISyntaxException {
        LOG.debug("REST request to save ModuleConfiguration : {}", moduleConfigurationDTO);
        if (moduleConfigurationDTO.getId() != null) {
            throw new BadRequestAlertException("A new moduleConfiguration cannot already have an ID", ENTITY_NAME, "idexists");
        }
        moduleConfigurationDTO = moduleConfigurationService.save(moduleConfigurationDTO);
        return ResponseEntity.created(new URI("/api/module-configurations/" + moduleConfigurationDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, moduleConfigurationDTO.getId().toString()))
            .body(moduleConfigurationDTO);
    }

    /**
     * {@code PUT  /module-configurations/:id} : Updates an existing moduleConfiguration.
     *
     * @param id the id of the moduleConfigurationDTO to save.
     * @param moduleConfigurationDTO the moduleConfigurationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated moduleConfigurationDTO,
     * or with status {@code 400 (Bad Request)} if the moduleConfigurationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the moduleConfigurationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ModuleConfigurationDTO> updateModuleConfiguration(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ModuleConfigurationDTO moduleConfigurationDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update ModuleConfiguration : {}, {}", id, moduleConfigurationDTO);
        if (moduleConfigurationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, moduleConfigurationDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!moduleConfigurationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        moduleConfigurationDTO = moduleConfigurationService.update(moduleConfigurationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, moduleConfigurationDTO.getId().toString()))
            .body(moduleConfigurationDTO);
    }

    /**
     * {@code PATCH  /module-configurations/:id} : Partial updates given fields of an existing moduleConfiguration, field will ignore if it is null
     *
     * @param id the id of the moduleConfigurationDTO to save.
     * @param moduleConfigurationDTO the moduleConfigurationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated moduleConfigurationDTO,
     * or with status {@code 400 (Bad Request)} if the moduleConfigurationDTO is not valid,
     * or with status {@code 404 (Not Found)} if the moduleConfigurationDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the moduleConfigurationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ModuleConfigurationDTO> partialUpdateModuleConfiguration(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ModuleConfigurationDTO moduleConfigurationDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update ModuleConfiguration partially : {}, {}", id, moduleConfigurationDTO);
        if (moduleConfigurationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, moduleConfigurationDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!moduleConfigurationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ModuleConfigurationDTO> result = moduleConfigurationService.partialUpdate(moduleConfigurationDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, moduleConfigurationDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /module-configurations} : get all the moduleConfigurations.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of moduleConfigurations in body.
     */
    @GetMapping("")
    public ResponseEntity<List<ModuleConfigurationDTO>> getAllModuleConfigurations(
        ModuleConfigurationCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get ModuleConfigurations by criteria: {}", criteria);

        Page<ModuleConfigurationDTO> page = moduleConfigurationQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /module-configurations/count} : count all the moduleConfigurations.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countModuleConfigurations(ModuleConfigurationCriteria criteria) {
        LOG.debug("REST request to count ModuleConfigurations by criteria: {}", criteria);
        return ResponseEntity.ok().body(moduleConfigurationQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /module-configurations/:id} : get the "id" moduleConfiguration.
     *
     * @param id the id of the moduleConfigurationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the moduleConfigurationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ModuleConfigurationDTO> getModuleConfiguration(@PathVariable("id") Long id) {
        LOG.debug("REST request to get ModuleConfiguration : {}", id);
        Optional<ModuleConfigurationDTO> moduleConfigurationDTO = moduleConfigurationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(moduleConfigurationDTO);
    }

    /**
     * {@code DELETE  /module-configurations/:id} : delete the "id" moduleConfiguration.
     *
     * @param id the id of the moduleConfigurationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteModuleConfiguration(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete ModuleConfiguration : {}", id);
        moduleConfigurationService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
