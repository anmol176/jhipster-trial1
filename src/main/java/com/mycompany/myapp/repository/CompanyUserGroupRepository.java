package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.CompanyUserGroup;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the CompanyUserGroup entity.
 */
@Repository
public interface CompanyUserGroupRepository extends JpaRepository<CompanyUserGroup, Long> {

    @Query(value = "select distinct companyUserGroup from CompanyUserGroup companyUserGroup left join fetch companyUserGroup.assignedResourceGroups",
        countQuery = "select count(distinct companyUserGroup) from CompanyUserGroup companyUserGroup")
    Page<CompanyUserGroup> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct companyUserGroup from CompanyUserGroup companyUserGroup left join fetch companyUserGroup.assignedResourceGroups")
    List<CompanyUserGroup> findAllWithEagerRelationships();

    @Query("select companyUserGroup from CompanyUserGroup companyUserGroup left join fetch companyUserGroup.assignedResourceGroups where companyUserGroup.id =:id")
    Optional<CompanyUserGroup> findOneWithEagerRelationships(@Param("id") Long id);
}
