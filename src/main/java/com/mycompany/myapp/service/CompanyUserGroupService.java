package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.CompanyUserGroupDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.CompanyUserGroup}.
 */
public interface CompanyUserGroupService {

    /**
     * Save a companyUserGroup.
     *
     * @param companyUserGroupDTO the entity to save.
     * @return the persisted entity.
     */
    CompanyUserGroupDTO save(CompanyUserGroupDTO companyUserGroupDTO);

    /**
     * Get all the companyUserGroups.
     *
     * @return the list of entities.
     */
    List<CompanyUserGroupDTO> findAll();

    /**
     * Get all the companyUserGroups with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    Page<CompanyUserGroupDTO> findAllWithEagerRelationships(Pageable pageable);


    /**
     * Get the "id" companyUserGroup.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CompanyUserGroupDTO> findOne(Long id);

    /**
     * Delete the "id" companyUserGroup.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
