package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.service.CompanyUserService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.service.dto.CompanyUserDTO;
import com.mycompany.myapp.service.dto.CompanyUserCriteria;
import com.mycompany.myapp.service.CompanyUserQueryService;

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
 * REST controller for managing {@link com.mycompany.myapp.domain.CompanyUser}.
 */
@RestController
@RequestMapping("/api")
public class CompanyUserResource {

    private final Logger log = LoggerFactory.getLogger(CompanyUserResource.class);

    private static final String ENTITY_NAME = "companyUser";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CompanyUserService companyUserService;

    private final CompanyUserQueryService companyUserQueryService;

    public CompanyUserResource(CompanyUserService companyUserService, CompanyUserQueryService companyUserQueryService) {
        this.companyUserService = companyUserService;
        this.companyUserQueryService = companyUserQueryService;
    }

    /**
     * {@code POST  /company-users} : Create a new companyUser.
     *
     * @param companyUserDTO the companyUserDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new companyUserDTO, or with status {@code 400 (Bad Request)} if the companyUser has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/company-users")
    public ResponseEntity<CompanyUserDTO> createCompanyUser(@Valid @RequestBody CompanyUserDTO companyUserDTO) throws URISyntaxException {
        log.debug("REST request to save CompanyUser : {}", companyUserDTO);
        if (companyUserDTO.getId() != null) {
            throw new BadRequestAlertException("A new companyUser cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CompanyUserDTO result = companyUserService.save(companyUserDTO);
        return ResponseEntity.created(new URI("/api/company-users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /company-users} : Updates an existing companyUser.
     *
     * @param companyUserDTO the companyUserDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated companyUserDTO,
     * or with status {@code 400 (Bad Request)} if the companyUserDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the companyUserDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/company-users")
    public ResponseEntity<CompanyUserDTO> updateCompanyUser(@Valid @RequestBody CompanyUserDTO companyUserDTO) throws URISyntaxException {
        log.debug("REST request to update CompanyUser : {}", companyUserDTO);
        if (companyUserDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CompanyUserDTO result = companyUserService.save(companyUserDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, companyUserDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /company-users} : get all the companyUsers.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of companyUsers in body.
     */
    @GetMapping("/company-users")
    public ResponseEntity<List<CompanyUserDTO>> getAllCompanyUsers(CompanyUserCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CompanyUsers by criteria: {}", criteria);
        Page<CompanyUserDTO> page = companyUserQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /company-users/count} : count all the companyUsers.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/company-users/count")
    public ResponseEntity<Long> countCompanyUsers(CompanyUserCriteria criteria) {
        log.debug("REST request to count CompanyUsers by criteria: {}", criteria);
        return ResponseEntity.ok().body(companyUserQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /company-users/:id} : get the "id" companyUser.
     *
     * @param id the id of the companyUserDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the companyUserDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/company-users/{id}")
    public ResponseEntity<CompanyUserDTO> getCompanyUser(@PathVariable Long id) {
        log.debug("REST request to get CompanyUser : {}", id);
        Optional<CompanyUserDTO> companyUserDTO = companyUserService.findOne(id);
        return ResponseUtil.wrapOrNotFound(companyUserDTO);
    }

    /**
     * {@code DELETE  /company-users/:id} : delete the "id" companyUser.
     *
     * @param id the id of the companyUserDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/company-users/{id}")
    public ResponseEntity<Void> deleteCompanyUser(@PathVariable Long id) {
        log.debug("REST request to delete CompanyUser : {}", id);
        companyUserService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
