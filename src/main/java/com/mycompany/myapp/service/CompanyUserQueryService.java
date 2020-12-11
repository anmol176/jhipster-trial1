package com.mycompany.myapp.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.mycompany.myapp.domain.CompanyUser;
import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.repository.CompanyUserRepository;
import com.mycompany.myapp.service.dto.CompanyUserCriteria;
import com.mycompany.myapp.service.dto.CompanyUserDTO;
import com.mycompany.myapp.service.mapper.CompanyUserMapper;

/**
 * Service for executing complex queries for {@link CompanyUser} entities in the database.
 * The main input is a {@link CompanyUserCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CompanyUserDTO} or a {@link Page} of {@link CompanyUserDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CompanyUserQueryService extends QueryService<CompanyUser> {

    private final Logger log = LoggerFactory.getLogger(CompanyUserQueryService.class);

    private final CompanyUserRepository companyUserRepository;

    private final CompanyUserMapper companyUserMapper;

    public CompanyUserQueryService(CompanyUserRepository companyUserRepository, CompanyUserMapper companyUserMapper) {
        this.companyUserRepository = companyUserRepository;
        this.companyUserMapper = companyUserMapper;
    }

    /**
     * Return a {@link List} of {@link CompanyUserDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CompanyUserDTO> findByCriteria(CompanyUserCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CompanyUser> specification = createSpecification(criteria);
        return companyUserMapper.toDto(companyUserRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CompanyUserDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CompanyUserDTO> findByCriteria(CompanyUserCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CompanyUser> specification = createSpecification(criteria);
        return companyUserRepository.findAll(specification, page)
            .map(companyUserMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CompanyUserCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CompanyUser> specification = createSpecification(criteria);
        return companyUserRepository.count(specification);
    }

    /**
     * Function to convert {@link CompanyUserCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CompanyUser> createSpecification(CompanyUserCriteria criteria) {
        Specification<CompanyUser> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CompanyUser_.id));
            }
            if (criteria.getLegalName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLegalName(), CompanyUser_.legalName));
            }
            if (criteria.getEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmail(), CompanyUser_.email));
            }
            if (criteria.getPhoneNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPhoneNumber(), CompanyUser_.phoneNumber));
            }
            if (criteria.getPreferedLanguage() != null) {
                specification = specification.and(buildSpecification(criteria.getPreferedLanguage(), CompanyUser_.preferedLanguage));
            }
            if (criteria.getCompanyGroupId() != null) {
                specification = specification.and(buildSpecification(criteria.getCompanyGroupId(),
                    root -> root.join(CompanyUser_.companyGroup, JoinType.LEFT).get(CompanyGroup_.id)));
            }
            if (criteria.getAssignedCompanyUserGroupsId() != null) {
                specification = specification.and(buildSpecification(criteria.getAssignedCompanyUserGroupsId(),
                    root -> root.join(CompanyUser_.assignedCompanyUserGroups, JoinType.LEFT).get(CompanyUserGroup_.id)));
            }
            if (criteria.getAssignedAccountsId() != null) {
                specification = specification.and(buildSpecification(criteria.getAssignedAccountsId(),
                    root -> root.join(CompanyUser_.assignedAccounts, JoinType.LEFT).get(CompanyEntityAccount_.id)));
            }
        }
        return specification;
    }
}
