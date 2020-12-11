package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.CompanyUser;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the CompanyUser entity.
 */
@Repository
public interface CompanyUserRepository extends JpaRepository<CompanyUser, Long>, JpaSpecificationExecutor<CompanyUser> {

    @Query(value = "select distinct companyUser from CompanyUser companyUser left join fetch companyUser.assignedCompanyUserGroups left join fetch companyUser.assignedAccounts",
        countQuery = "select count(distinct companyUser) from CompanyUser companyUser")
    Page<CompanyUser> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct companyUser from CompanyUser companyUser left join fetch companyUser.assignedCompanyUserGroups left join fetch companyUser.assignedAccounts")
    List<CompanyUser> findAllWithEagerRelationships();

    @Query("select companyUser from CompanyUser companyUser left join fetch companyUser.assignedCompanyUserGroups left join fetch companyUser.assignedAccounts where companyUser.id =:id")
    Optional<CompanyUser> findOneWithEagerRelationships(@Param("id") Long id);
}
