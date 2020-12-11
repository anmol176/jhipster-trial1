package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.CompanyGroupDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.CompanyGroup}.
 */
public interface CompanyGroupService {

    /**
     * Save a companyGroup.
     *
     * @param companyGroupDTO the entity to save.
     * @return the persisted entity.
     */
    CompanyGroupDTO save(CompanyGroupDTO companyGroupDTO);

    /**
     * Get all the companyGroups.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CompanyGroupDTO> findAll(Pageable pageable);


    /**
     * Get the "id" companyGroup.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CompanyGroupDTO> findOne(Long id);

    /**
     * Delete the "id" companyGroup.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
