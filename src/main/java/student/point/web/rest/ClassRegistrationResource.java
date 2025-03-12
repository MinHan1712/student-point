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
import student.point.repository.ClassRegistrationRepository;
import student.point.service.ClassRegistrationQueryService;
import student.point.service.ClassRegistrationService;
import student.point.service.criteria.ClassRegistrationCriteria;
import student.point.service.dto.ClassRegistrationDTO;
import student.point.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link student.point.domain.ClassRegistration}.
 */
@RestController
@RequestMapping("/api/class-registrations")
public class ClassRegistrationResource {

    private static final Logger LOG = LoggerFactory.getLogger(ClassRegistrationResource.class);

    private static final String ENTITY_NAME = "classRegistration";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ClassRegistrationService classRegistrationService;

    private final ClassRegistrationRepository classRegistrationRepository;

    private final ClassRegistrationQueryService classRegistrationQueryService;

    public ClassRegistrationResource(
        ClassRegistrationService classRegistrationService,
        ClassRegistrationRepository classRegistrationRepository,
        ClassRegistrationQueryService classRegistrationQueryService
    ) {
        this.classRegistrationService = classRegistrationService;
        this.classRegistrationRepository = classRegistrationRepository;
        this.classRegistrationQueryService = classRegistrationQueryService;
    }

    /**
     * {@code POST  /class-registrations} : Create a new classRegistration.
     *
     * @param classRegistrationDTO the classRegistrationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new classRegistrationDTO, or with status {@code 400 (Bad Request)} if the classRegistration has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ClassRegistrationDTO> createClassRegistration(@RequestBody ClassRegistrationDTO classRegistrationDTO)
        throws URISyntaxException {
        LOG.debug("REST request to save ClassRegistration : {}", classRegistrationDTO);
        if (classRegistrationDTO.getId() != null) {
            throw new BadRequestAlertException("A new classRegistration cannot already have an ID", ENTITY_NAME, "idexists");
        }
        classRegistrationDTO = classRegistrationService.save(classRegistrationDTO);
        return ResponseEntity.created(new URI("/api/class-registrations/" + classRegistrationDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, classRegistrationDTO.getId().toString()))
            .body(classRegistrationDTO);
    }

    /**
     * {@code PUT  /class-registrations/:id} : Updates an existing classRegistration.
     *
     * @param id the id of the classRegistrationDTO to save.
     * @param classRegistrationDTO the classRegistrationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated classRegistrationDTO,
     * or with status {@code 400 (Bad Request)} if the classRegistrationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the classRegistrationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ClassRegistrationDTO> updateClassRegistration(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ClassRegistrationDTO classRegistrationDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update ClassRegistration : {}, {}", id, classRegistrationDTO);
        if (classRegistrationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, classRegistrationDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!classRegistrationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        classRegistrationDTO = classRegistrationService.update(classRegistrationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, classRegistrationDTO.getId().toString()))
            .body(classRegistrationDTO);
    }

    /**
     * {@code PATCH  /class-registrations/:id} : Partial updates given fields of an existing classRegistration, field will ignore if it is null
     *
     * @param id the id of the classRegistrationDTO to save.
     * @param classRegistrationDTO the classRegistrationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated classRegistrationDTO,
     * or with status {@code 400 (Bad Request)} if the classRegistrationDTO is not valid,
     * or with status {@code 404 (Not Found)} if the classRegistrationDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the classRegistrationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ClassRegistrationDTO> partialUpdateClassRegistration(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ClassRegistrationDTO classRegistrationDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update ClassRegistration partially : {}, {}", id, classRegistrationDTO);
        if (classRegistrationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, classRegistrationDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!classRegistrationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ClassRegistrationDTO> result = classRegistrationService.partialUpdate(classRegistrationDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, classRegistrationDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /class-registrations} : get all the classRegistrations.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of classRegistrations in body.
     */
    @GetMapping("")
    public ResponseEntity<List<ClassRegistrationDTO>> getAllClassRegistrations(
        ClassRegistrationCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get ClassRegistrations by criteria: {}", criteria);

        Page<ClassRegistrationDTO> page = classRegistrationQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /class-registrations/count} : count all the classRegistrations.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countClassRegistrations(ClassRegistrationCriteria criteria) {
        LOG.debug("REST request to count ClassRegistrations by criteria: {}", criteria);
        return ResponseEntity.ok().body(classRegistrationQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /class-registrations/:id} : get the "id" classRegistration.
     *
     * @param id the id of the classRegistrationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the classRegistrationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ClassRegistrationDTO> getClassRegistration(@PathVariable("id") Long id) {
        LOG.debug("REST request to get ClassRegistration : {}", id);
        Optional<ClassRegistrationDTO> classRegistrationDTO = classRegistrationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(classRegistrationDTO);
    }

    /**
     * {@code DELETE  /class-registrations/:id} : delete the "id" classRegistration.
     *
     * @param id the id of the classRegistrationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClassRegistration(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete ClassRegistration : {}", id);
        classRegistrationService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
