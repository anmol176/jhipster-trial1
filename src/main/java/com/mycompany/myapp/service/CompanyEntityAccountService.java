package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.CompanyEntityAccountDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.CompanyEntityAccount}.
 */
public interface CompanyEntityAccountService {

    /**
     * Save a companyEntityAccount.
     *
     * @param companyEntityAccountDTO the entity to save.
     * @return the persisted entity.
     */
    CompanyEntityAccountDTO save(CompanyEntityAccountDTO companyEntityAccountDTO);

    /**
     * Get all the companyEntityAccounts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CompanyEntityAccountDTO> findAll(Pageable pageable);


    /**
     * Get the "id" companyEntityAccount.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CompanyEntityAccountDTO> findOne(Long id);

    /**
     * Delete the "id" companyEntityAccount.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
