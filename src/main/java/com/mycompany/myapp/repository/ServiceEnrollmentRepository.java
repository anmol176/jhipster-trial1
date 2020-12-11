package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.ServiceEnrollment;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ServiceEnrollment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ServiceEnrollmentRepository extends JpaRepository<ServiceEnrollment, Long> {
}
