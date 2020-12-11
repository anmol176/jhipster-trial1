package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.ResourceActionGroup;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ResourceActionGroup entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ResourceActionGroupRepository extends JpaRepository<ResourceActionGroup, Long> {
}
