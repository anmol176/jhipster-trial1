package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.CompanyEntityAccount;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CompanyEntityAccount entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompanyEntityAccountRepository extends JpaRepository<CompanyEntityAccount, Long> {
}
