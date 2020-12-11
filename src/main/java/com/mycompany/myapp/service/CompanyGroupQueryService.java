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

import com.mycompany.myapp.domain.CompanyGroup;
import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.repository.CompanyGroupRepository;
import com.mycompany.myapp.service.dto.CompanyGroupCriteria;
import com.mycompany.myapp.service.dto.CompanyGroupDTO;
import com.mycompany.myapp.service.mapper.CompanyGroupMapper;

/**
 * Service for executing complex queries for {@link CompanyGroup} entities in the database.
 * The main input is a {@link CompanyGroupCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CompanyGroupDTO} or a {@link Page} of {@link CompanyGroupDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CompanyGroupQueryService extends QueryService<CompanyGroup> {

    private final Logger log = LoggerFactory.getLogger(CompanyGroupQueryService.class);

    private final CompanyGroupRepository companyGroupRepository;

    private final CompanyGroupMapper companyGroupMapper;

    public CompanyGroupQueryService(CompanyGroupRepository companyGroupRepository, CompanyGroupMapper companyGroupMapper) {
        this.companyGroupRepository = companyGroupRepository;
        this.companyGroupMapper = companyGroupMapper;
    }

    /**
     * Return a {@link List} of {@link CompanyGroupDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CompanyGroupDTO> findByCriteria(CompanyGroupCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CompanyGroup> specification = createSpecification(criteria);
        return companyGroupMapper.toDto(companyGroupRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CompanyGroupDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CompanyGroupDTO> findByCriteria(CompanyGroupCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CompanyGroup> specification = createSpecification(criteria);
        return companyGroupRepository.findAll(specification, page)
            .map(companyGroupMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CompanyGroupCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CompanyGroup> specification = createSpecification(criteria);
        return companyGroupRepository.count(specification);
    }

    /**
     * Function to convert {@link CompanyGroupCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CompanyGroup> createSpecification(CompanyGroupCriteria criteria) {
        Specification<CompanyGroup> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CompanyGroup_.id));
            }
            if (criteria.getgCIF() != null) {
                specification = specification.and(buildStringSpecification(criteria.getgCIF(), CompanyGroup_.gCIF));
            }
            if (criteria.getGroupName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getGroupName(), CompanyGroup_.groupName));
            }
            if (criteria.getLocationId() != null) {
                specification = specification.and(buildSpecification(criteria.getLocationId(),
                    root -> root.join(CompanyGroup_.location, JoinType.LEFT).get(Location_.id)));
            }
        }
        return specification;
    }
}
