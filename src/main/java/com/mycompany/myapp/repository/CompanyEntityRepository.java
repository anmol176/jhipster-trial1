package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.CompanyEntity;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CompanyEntity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompanyEntityRepository extends JpaRepository<CompanyEntity, Long>, JpaSpecificationExecutor<CompanyEntity> {
}
