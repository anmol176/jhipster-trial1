package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.ResourceActionGroupDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.ResourceActionGroup}.
 */
public interface ResourceActionGroupService {

    /**
     * Save a resourceActionGroup.
     *
     * @param resourceActionGroupDTO the entity to save.
     * @return the persisted entity.
     */
    ResourceActionGroupDTO save(ResourceActionGroupDTO resourceActionGroupDTO);

    /**
     * Get all the resourceActionGroups.
     *
     * @return the list of entities.
     */
    List<ResourceActionGroupDTO> findAll();


    /**
     * Get the "id" resourceActionGroup.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ResourceActionGroupDTO> findOne(Long id);

    /**
     * Delete the "id" resourceActionGroup.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
