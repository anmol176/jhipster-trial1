package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.ServiceEnrollmentDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.ServiceEnrollment}.
 */
public interface ServiceEnrollmentService {

    /**
     * Save a serviceEnrollment.
     *
     * @param serviceEnrollmentDTO the entity to save.
     * @return the persisted entity.
     */
    ServiceEnrollmentDTO save(ServiceEnrollmentDTO serviceEnrollmentDTO);

    /**
     * Get all the serviceEnrollments.
     *
     * @return the list of entities.
     */
    List<ServiceEnrollmentDTO> findAll();


    /**
     * Get the "id" serviceEnrollment.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ServiceEnrollmentDTO> findOne(Long id);

    /**
     * Delete the "id" serviceEnrollment.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
