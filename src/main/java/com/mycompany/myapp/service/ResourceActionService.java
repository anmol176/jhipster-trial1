package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.ResourceActionDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.ResourceAction}.
 */
public interface ResourceActionService {

    /**
     * Save a resourceAction.
     *
     * @param resourceActionDTO the entity to save.
     * @return the persisted entity.
     */
    ResourceActionDTO save(ResourceActionDTO resourceActionDTO);

    /**
     * Get all the resourceActions.
     *
     * @return the list of entities.
     */
    List<ResourceActionDTO> findAll();


    /**
     * Get the "id" resourceAction.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ResourceActionDTO> findOne(Long id);

    /**
     * Delete the "id" resourceAction.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
