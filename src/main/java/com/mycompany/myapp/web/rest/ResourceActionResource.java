package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.service.ResourceActionService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.service.dto.ResourceActionDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.ResourceAction}.
 */
@RestController
@RequestMapping("/api")
public class ResourceActionResource {

    private final Logger log = LoggerFactory.getLogger(ResourceActionResource.class);

    private static final String ENTITY_NAME = "resourceAction";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ResourceActionService resourceActionService;

    public ResourceActionResource(ResourceActionService resourceActionService) {
        this.resourceActionService = resourceActionService;
    }

    /**
     * {@code POST  /resource-actions} : Create a new resourceAction.
     *
     * @param resourceActionDTO the resourceActionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new resourceActionDTO, or with status {@code 400 (Bad Request)} if the resourceAction has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/resource-actions")
    public ResponseEntity<ResourceActionDTO> createResourceAction(@Valid @RequestBody ResourceActionDTO resourceActionDTO) throws URISyntaxException {
        log.debug("REST request to save ResourceAction : {}", resourceActionDTO);
        if (resourceActionDTO.getId() != null) {
            throw new BadRequestAlertException("A new resourceAction cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ResourceActionDTO result = resourceActionService.save(resourceActionDTO);
        return ResponseEntity.created(new URI("/api/resource-actions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /resource-actions} : Updates an existing resourceAction.
     *
     * @param resourceActionDTO the resourceActionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated resourceActionDTO,
     * or with status {@code 400 (Bad Request)} if the resourceActionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the resourceActionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/resource-actions")
    public ResponseEntity<ResourceActionDTO> updateResourceAction(@Valid @RequestBody ResourceActionDTO resourceActionDTO) throws URISyntaxException {
        log.debug("REST request to update ResourceAction : {}", resourceActionDTO);
        if (resourceActionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ResourceActionDTO result = resourceActionService.save(resourceActionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, resourceActionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /resource-actions} : get all the resourceActions.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of resourceActions in body.
     */
    @GetMapping("/resource-actions")
    public List<ResourceActionDTO> getAllResourceActions() {
        log.debug("REST request to get all ResourceActions");
        return resourceActionService.findAll();
    }

    /**
     * {@code GET  /resource-actions/:id} : get the "id" resourceAction.
     *
     * @param id the id of the resourceActionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the resourceActionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/resource-actions/{id}")
    public ResponseEntity<ResourceActionDTO> getResourceAction(@PathVariable Long id) {
        log.debug("REST request to get ResourceAction : {}", id);
        Optional<ResourceActionDTO> resourceActionDTO = resourceActionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(resourceActionDTO);
    }

    /**
     * {@code DELETE  /resource-actions/:id} : delete the "id" resourceAction.
     *
     * @param id the id of the resourceActionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/resource-actions/{id}")
    public ResponseEntity<Void> deleteResourceAction(@PathVariable Long id) {
        log.debug("REST request to delete ResourceAction : {}", id);
        resourceActionService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
