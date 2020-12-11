package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.CompanyEntityDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.CompanyEntity}.
 */
public interface CompanyEntityService {

    /**
     * Save a companyEntity.
     *
     * @param companyEntityDTO the entity to save.
     * @return the persisted entity.
     */
    CompanyEntityDTO save(CompanyEntityDTO companyEntityDTO);

    /**
     * Get all the companyEntities.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CompanyEntityDTO> findAll(Pageable pageable);


    /**
     * Get the "id" companyEntity.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CompanyEntityDTO> findOne(Long id);

    /**
     * Delete the "id" companyEntity.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
