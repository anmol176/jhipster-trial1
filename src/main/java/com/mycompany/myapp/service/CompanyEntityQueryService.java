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

import com.mycompany.myapp.domain.CompanyEntity;
import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.repository.CompanyEntityRepository;
import com.mycompany.myapp.service.dto.CompanyEntityCriteria;
import com.mycompany.myapp.service.dto.CompanyEntityDTO;
import com.mycompany.myapp.service.mapper.CompanyEntityMapper;

/**
 * Service for executing complex queries for {@link CompanyEntity} entities in the database.
 * The main input is a {@link CompanyEntityCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CompanyEntityDTO} or a {@link Page} of {@link CompanyEntityDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CompanyEntityQueryService extends QueryService<CompanyEntity> {

    private final Logger log = LoggerFactory.getLogger(CompanyEntityQueryService.class);

    private final CompanyEntityRepository companyEntityRepository;

    private final CompanyEntityMapper companyEntityMapper;

    public CompanyEntityQueryService(CompanyEntityRepository companyEntityRepository, CompanyEntityMapper companyEntityMapper) {
        this.companyEntityRepository = companyEntityRepository;
        this.companyEntityMapper = companyEntityMapper;
    }

    /**
     * Return a {@link List} of {@link CompanyEntityDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CompanyEntityDTO> findByCriteria(CompanyEntityCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CompanyEntity> specification = createSpecification(criteria);
        return companyEntityMapper.toDto(companyEntityRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CompanyEntityDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CompanyEntityDTO> findByCriteria(CompanyEntityCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CompanyEntity> specification = createSpecification(criteria);
        return companyEntityRepository.findAll(specification, page)
            .map(companyEntityMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CompanyEntityCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CompanyEntity> specification = createSpecification(criteria);
        return companyEntityRepository.count(specification);
    }

    /**
     * Function to convert {@link CompanyEntityCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CompanyEntity> createSpecification(CompanyEntityCriteria criteria) {
        Specification<CompanyEntity> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CompanyEntity_.id));
            }
            if (criteria.getCif() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCif(), CompanyEntity_.cif));
            }
            if (criteria.getLegalName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLegalName(), CompanyEntity_.legalName));
            }
            if (criteria.getLocationId() != null) {
                specification = specification.and(buildSpecification(criteria.getLocationId(),
                    root -> root.join(CompanyEntity_.location, JoinType.LEFT).get(Location_.id)));
            }
            if (criteria.getCompanyGroupId() != null) {
                specification = specification.and(buildSpecification(criteria.getCompanyGroupId(),
                    root -> root.join(CompanyEntity_.companyGroup, JoinType.LEFT).get(CompanyGroup_.id)));
            }
        }
        return specification;
    }
}
