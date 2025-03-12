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
import student.point.repository.ClassesRepository;
import student.point.service.ClassesQueryService;
import student.point.service.ClassesService;
import student.point.service.criteria.ClassesCriteria;
import student.point.service.dto.ClassesDTO;
import student.point.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link student.point.domain.Classes}.
 */
@RestController
@RequestMapping("/api/classes")
public class ClassesResource {

    private static final Logger LOG = LoggerFactory.getLogger(ClassesResource.class);

    private static final String ENTITY_NAME = "classes";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ClassesService classesService;

    private final ClassesRepository classesRepository;

    private final ClassesQueryService classesQueryService;

    public ClassesResource(ClassesService classesService, ClassesRepository classesRepository, ClassesQueryService classesQueryService) {
        this.classesService = classesService;
        this.classesRepository = classesRepository;
        this.classesQueryService = classesQueryService;
    }

    /**
     * {@code POST  /classes} : Create a new classes.
     *
     * @param classesDTO the classesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new classesDTO, or with status {@code 400 (Bad Request)} if the classes has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ClassesDTO> createClasses(@RequestBody ClassesDTO classesDTO) throws URISyntaxException {
        LOG.debug("REST request to save Classes : {}", classesDTO);
        if (classesDTO.getId() != null) {
            throw new BadRequestAlertException("A new classes cannot already have an ID", ENTITY_NAME, "idexists");
        }
        classesDTO = classesService.save(classesDTO);
        return ResponseEntity.created(new URI("/api/classes/" + classesDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, classesDTO.getId().toString()))
            .body(classesDTO);
    }

    /**
     * {@code PUT  /classes/:id} : Updates an existing classes.
     *
     * @param id the id of the classesDTO to save.
     * @param classesDTO the classesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated classesDTO,
     * or with status {@code 400 (Bad Request)} if the classesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the classesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ClassesDTO> updateClasses(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ClassesDTO classesDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update Classes : {}, {}", id, classesDTO);
        if (classesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, classesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!classesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        classesDTO = classesService.update(classesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, classesDTO.getId().toString()))
            .body(classesDTO);
    }

    /**
     * {@code PATCH  /classes/:id} : Partial updates given fields of an existing classes, field will ignore if it is null
     *
     * @param id the id of the classesDTO to save.
     * @param classesDTO the classesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated classesDTO,
     * or with status {@code 400 (Bad Request)} if the classesDTO is not valid,
     * or with status {@code 404 (Not Found)} if the classesDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the classesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ClassesDTO> partialUpdateClasses(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ClassesDTO classesDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Classes partially : {}, {}", id, classesDTO);
        if (classesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, classesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!classesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ClassesDTO> result = classesService.partialUpdate(classesDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, classesDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /classes} : get all the classes.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of classes in body.
     */
    @GetMapping("")
    public ResponseEntity<List<ClassesDTO>> getAllClasses(
        ClassesCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get Classes by criteria: {}", criteria);

        Page<ClassesDTO> page = classesQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /classes/count} : count all the classes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countClasses(ClassesCriteria criteria) {
        LOG.debug("REST request to count Classes by criteria: {}", criteria);
        return ResponseEntity.ok().body(classesQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /classes/:id} : get the "id" classes.
     *
     * @param id the id of the classesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the classesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ClassesDTO> getClasses(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Classes : {}", id);
        Optional<ClassesDTO> classesDTO = classesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(classesDTO);
    }

    /**
     * {@code DELETE  /classes/:id} : delete the "id" classes.
     *
     * @param id the id of the classesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClasses(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Classes : {}", id);
        classesService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
