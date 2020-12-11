package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.ResourceAction;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ResourceAction entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ResourceActionRepository extends JpaRepository<ResourceAction, Long> {
}
