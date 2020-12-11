package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.service.CompanyGroupService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.service.dto.CompanyGroupDTO;
import com.mycompany.myapp.service.dto.CompanyGroupCriteria;
import com.mycompany.myapp.service.CompanyGroupQueryService;

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
 * REST controller for managing {@link com.mycompany.myapp.domain.CompanyGroup}.
 */
@RestController
@RequestMapping("/api")
public class CompanyGroupResource {

    private final Logger log = LoggerFactory.getLogger(CompanyGroupResource.class);

    private static final String ENTITY_NAME = "companyGroup";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CompanyGroupService companyGroupService;

    private final CompanyGroupQueryService companyGroupQueryService;

    public CompanyGroupResource(CompanyGroupService companyGroupService, CompanyGroupQueryService companyGroupQueryService) {
        this.companyGroupService = companyGroupService;
        this.companyGroupQueryService = companyGroupQueryService;
    }

    /**
     * {@code POST  /company-groups} : Create a new companyGroup.
     *
     * @param companyGroupDTO the companyGroupDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new companyGroupDTO, or with status {@code 400 (Bad Request)} if the companyGroup has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/company-groups")
    public ResponseEntity<CompanyGroupDTO> createCompanyGroup(@Valid @RequestBody CompanyGroupDTO companyGroupDTO) throws URISyntaxException {
        log.debug("REST request to save CompanyGroup : {}", companyGroupDTO);
        if (companyGroupDTO.getId() != null) {
            throw new BadRequestAlertException("A new companyGroup cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CompanyGroupDTO result = companyGroupService.save(companyGroupDTO);
        return ResponseEntity.created(new URI("/api/company-groups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /company-groups} : Updates an existing companyGroup.
     *
     * @param companyGroupDTO the companyGroupDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated companyGroupDTO,
     * or with status {@code 400 (Bad Request)} if the companyGroupDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the companyGroupDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/company-groups")
    public ResponseEntity<CompanyGroupDTO> updateCompanyGroup(@Valid @RequestBody CompanyGroupDTO companyGroupDTO) throws URISyntaxException {
        log.debug("REST request to update CompanyGroup : {}", companyGroupDTO);
        if (companyGroupDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CompanyGroupDTO result = companyGroupService.save(companyGroupDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, companyGroupDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /company-groups} : get all the companyGroups.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of companyGroups in body.
     */
    @GetMapping("/company-groups")
    public ResponseEntity<List<CompanyGroupDTO>> getAllCompanyGroups(CompanyGroupCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CompanyGroups by criteria: {}", criteria);
        Page<CompanyGroupDTO> page = companyGroupQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /company-groups/count} : count all the companyGroups.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/company-groups/count")
    public ResponseEntity<Long> countCompanyGroups(CompanyGroupCriteria criteria) {
        log.debug("REST request to count CompanyGroups by criteria: {}", criteria);
        return ResponseEntity.ok().body(companyGroupQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /company-groups/:id} : get the "id" companyGroup.
     *
     * @param id the id of the companyGroupDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the companyGroupDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/company-groups/{id}")
    public ResponseEntity<CompanyGroupDTO> getCompanyGroup(@PathVariable Long id) {
        log.debug("REST request to get CompanyGroup : {}", id);
        Optional<CompanyGroupDTO> companyGroupDTO = companyGroupService.findOne(id);
        return ResponseUtil.wrapOrNotFound(companyGroupDTO);
    }

    /**
     * {@code DELETE  /company-groups/:id} : delete the "id" companyGroup.
     *
     * @param id the id of the companyGroupDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/company-groups/{id}")
    public ResponseEntity<Void> deleteCompanyGroup(@PathVariable Long id) {
        log.debug("REST request to delete CompanyGroup : {}", id);
        companyGroupService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
