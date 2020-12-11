package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.service.ResourcesService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.service.dto.ResourcesDTO;
import com.mycompany.myapp.service.dto.ResourcesCriteria;
import com.mycompany.myapp.service.ResourcesQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.Resources}.
 */
@RestController
@RequestMapping("/api")
public class ResourcesResource {

    private final Logger log = LoggerFactory.getLogger(ResourcesResource.class);

    private static final String ENTITY_NAME = "resources";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ResourcesService resourcesService;

    private final ResourcesQueryService resourcesQueryService;

    public ResourcesResource(ResourcesService resourcesService, ResourcesQueryService resourcesQueryService) {
        this.resourcesService = resourcesService;
        this.resourcesQueryService = resourcesQueryService;
    }

    /**
     * {@code POST  /resources} : Create a new resources.
     *
     * @param resourcesDTO the resourcesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new resourcesDTO, or with status {@code 400 (Bad Request)} if the resources has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/resources")
    public ResponseEntity<ResourcesDTO> createResources(@Valid @RequestBody ResourcesDTO resourcesDTO) throws URISyntaxException {
        log.debug("REST request to save Resources : {}", resourcesDTO);
        if (resourcesDTO.getId() != null) {
            throw new BadRequestAlertException("A new resources cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ResourcesDTO result = resourcesService.save(resourcesDTO);
        return ResponseEntity.created(new URI("/api/resources/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /resources} : Updates an existing resources.
     *
     * @param resourcesDTO the resourcesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated resourcesDTO,
     * or with status {@code 400 (Bad Request)} if the resourcesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the resourcesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/resources")
    public ResponseEntity<ResourcesDTO> updateResources(@Valid @RequestBody ResourcesDTO resourcesDTO) throws URISyntaxException {
        log.debug("REST request to update Resources : {}", resourcesDTO);
        if (resourcesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ResourcesDTO result = resourcesService.save(resourcesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, resourcesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /resources} : get all the resources.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of resources in body.
     */
    @GetMapping("/resources")
    public ResponseEntity<List<ResourcesDTO>> getAllResources(ResourcesCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Resources by criteria: {}", criteria);
        Page<ResourcesDTO> page = resourcesQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /resources/count} : count all the resources.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/resources/count")
    public ResponseEntity<Long> countResources(ResourcesCriteria criteria) {
        log.debug("REST request to count Resources by criteria: {}", criteria);
        return ResponseEntity.ok().body(resourcesQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /resources/:id} : get the "id" resources.
     *
     * @param id the id of the resourcesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the resourcesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/resources/{id}")
    public ResponseEntity<ResourcesDTO> getResources(@PathVariable Long id) {
        log.debug("REST request to get Resources : {}", id);
        Optional<ResourcesDTO> resourcesDTO = resourcesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(resourcesDTO);
    }

    /**
     * {@code DELETE  /resources/:id} : delete the "id" resources.
     *
     * @param id the id of the resourcesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/resources/{id}")
    public ResponseEntity<Void> deleteResources(@PathVariable Long id) {
        log.debug("REST request to delete Resources : {}", id);
        resourcesService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
