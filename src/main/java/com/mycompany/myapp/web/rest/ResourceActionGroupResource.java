package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.service.ResourceActionGroupService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.service.dto.ResourceActionGroupDTO;

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
 * REST controller for managing {@link com.mycompany.myapp.domain.ResourceActionGroup}.
 */
@RestController
@RequestMapping("/api")
public class ResourceActionGroupResource {

    private final Logger log = LoggerFactory.getLogger(ResourceActionGroupResource.class);

    private static final String ENTITY_NAME = "resourceActionGroup";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ResourceActionGroupService resourceActionGroupService;

    public ResourceActionGroupResource(ResourceActionGroupService resourceActionGroupService) {
        this.resourceActionGroupService = resourceActionGroupService;
    }

    /**
     * {@code POST  /resource-action-groups} : Create a new resourceActionGroup.
     *
     * @param resourceActionGroupDTO the resourceActionGroupDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new resourceActionGroupDTO, or with status {@code 400 (Bad Request)} if the resourceActionGroup has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/resource-action-groups")
    public ResponseEntity<ResourceActionGroupDTO> createResourceActionGroup(@Valid @RequestBody ResourceActionGroupDTO resourceActionGroupDTO) throws URISyntaxException {
        log.debug("REST request to save ResourceActionGroup : {}", resourceActionGroupDTO);
        if (resourceActionGroupDTO.getId() != null) {
            throw new BadRequestAlertException("A new resourceActionGroup cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ResourceActionGroupDTO result = resourceActionGroupService.save(resourceActionGroupDTO);
        return ResponseEntity.created(new URI("/api/resource-action-groups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /resource-action-groups} : Updates an existing resourceActionGroup.
     *
     * @param resourceActionGroupDTO the resourceActionGroupDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated resourceActionGroupDTO,
     * or with status {@code 400 (Bad Request)} if the resourceActionGroupDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the resourceActionGroupDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/resource-action-groups")
    public ResponseEntity<ResourceActionGroupDTO> updateResourceActionGroup(@Valid @RequestBody ResourceActionGroupDTO resourceActionGroupDTO) throws URISyntaxException {
        log.debug("REST request to update ResourceActionGroup : {}", resourceActionGroupDTO);
        if (resourceActionGroupDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ResourceActionGroupDTO result = resourceActionGroupService.save(resourceActionGroupDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, resourceActionGroupDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /resource-action-groups} : get all the resourceActionGroups.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of resourceActionGroups in body.
     */
    @GetMapping("/resource-action-groups")
    public List<ResourceActionGroupDTO> getAllResourceActionGroups() {
        log.debug("REST request to get all ResourceActionGroups");
        return resourceActionGroupService.findAll();
    }

    /**
     * {@code GET  /resource-action-groups/:id} : get the "id" resourceActionGroup.
     *
     * @param id the id of the resourceActionGroupDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the resourceActionGroupDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/resource-action-groups/{id}")
    public ResponseEntity<ResourceActionGroupDTO> getResourceActionGroup(@PathVariable Long id) {
        log.debug("REST request to get ResourceActionGroup : {}", id);
        Optional<ResourceActionGroupDTO> resourceActionGroupDTO = resourceActionGroupService.findOne(id);
        return ResponseUtil.wrapOrNotFound(resourceActionGroupDTO);
    }

    /**
     * {@code DELETE  /resource-action-groups/:id} : delete the "id" resourceActionGroup.
     *
     * @param id the id of the resourceActionGroupDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/resource-action-groups/{id}")
    public ResponseEntity<Void> deleteResourceActionGroup(@PathVariable Long id) {
        log.debug("REST request to delete ResourceActionGroup : {}", id);
        resourceActionGroupService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
