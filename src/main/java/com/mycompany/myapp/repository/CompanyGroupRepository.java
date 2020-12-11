package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.CompanyGroup;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CompanyGroup entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompanyGroupRepository extends JpaRepository<CompanyGroup, Long>, JpaSpecificationExecutor<CompanyGroup> {
}
