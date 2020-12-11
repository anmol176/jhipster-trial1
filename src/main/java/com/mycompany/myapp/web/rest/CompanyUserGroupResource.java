package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.service.CompanyUserGroupService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.service.dto.CompanyUserGroupDTO;

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
 * REST controller for managing {@link com.mycompany.myapp.domain.CompanyUserGroup}.
 */
@RestController
@RequestMapping("/api")
public class CompanyUserGroupResource {

    private final Logger log = LoggerFactory.getLogger(CompanyUserGroupResource.class);

    private static final String ENTITY_NAME = "companyUserGroup";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CompanyUserGroupService companyUserGroupService;

    public CompanyUserGroupResource(CompanyUserGroupService companyUserGroupService) {
        this.companyUserGroupService = companyUserGroupService;
    }

    /**
     * {@code POST  /company-user-groups} : Create a new companyUserGroup.
     *
     * @param companyUserGroupDTO the companyUserGroupDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new companyUserGroupDTO, or with status {@code 400 (Bad Request)} if the companyUserGroup has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/company-user-groups")
    public ResponseEntity<CompanyUserGroupDTO> createCompanyUserGroup(@Valid @RequestBody CompanyUserGroupDTO companyUserGroupDTO) throws URISyntaxException {
        log.debug("REST request to save CompanyUserGroup : {}", companyUserGroupDTO);
        if (companyUserGroupDTO.getId() != null) {
            throw new BadRequestAlertException("A new companyUserGroup cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CompanyUserGroupDTO result = companyUserGroupService.save(companyUserGroupDTO);
        return ResponseEntity.created(new URI("/api/company-user-groups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /company-user-groups} : Updates an existing companyUserGroup.
     *
     * @param companyUserGroupDTO the companyUserGroupDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated companyUserGroupDTO,
     * or with status {@code 400 (Bad Request)} if the companyUserGroupDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the companyUserGroupDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/company-user-groups")
    public ResponseEntity<CompanyUserGroupDTO> updateCompanyUserGroup(@Valid @RequestBody CompanyUserGroupDTO companyUserGroupDTO) throws URISyntaxException {
        log.debug("REST request to update CompanyUserGroup : {}", companyUserGroupDTO);
        if (companyUserGroupDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CompanyUserGroupDTO result = companyUserGroupService.save(companyUserGroupDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, companyUserGroupDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /company-user-groups} : get all the companyUserGroups.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of companyUserGroups in body.
     */
    @GetMapping("/company-user-groups")
    public List<CompanyUserGroupDTO> getAllCompanyUserGroups(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all CompanyUserGroups");
        return companyUserGroupService.findAll();
    }

    /**
     * {@code GET  /company-user-groups/:id} : get the "id" companyUserGroup.
     *
     * @param id the id of the companyUserGroupDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the companyUserGroupDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/company-user-groups/{id}")
    public ResponseEntity<CompanyUserGroupDTO> getCompanyUserGroup(@PathVariable Long id) {
        log.debug("REST request to get CompanyUserGroup : {}", id);
        Optional<CompanyUserGroupDTO> companyUserGroupDTO = companyUserGroupService.findOne(id);
        return ResponseUtil.wrapOrNotFound(companyUserGroupDTO);
    }

    /**
     * {@code DELETE  /company-user-groups/:id} : delete the "id" companyUserGroup.
     *
     * @param id the id of the companyUserGroupDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/company-user-groups/{id}")
    public ResponseEntity<Void> deleteCompanyUserGroup(@PathVariable Long id) {
        log.debug("REST request to delete CompanyUserGroup : {}", id);
        companyUserGroupService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
