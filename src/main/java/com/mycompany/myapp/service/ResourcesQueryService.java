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

import com.mycompany.myapp.domain.Resources;
import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.repository.ResourcesRepository;
import com.mycompany.myapp.service.dto.ResourcesCriteria;
import com.mycompany.myapp.service.dto.ResourcesDTO;
import com.mycompany.myapp.service.mapper.ResourcesMapper;

/**
 * Service for executing complex queries for {@link Resources} entities in the database.
 * The main input is a {@link ResourcesCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ResourcesDTO} or a {@link Page} of {@link ResourcesDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ResourcesQueryService extends QueryService<Resources> {

    private final Logger log = LoggerFactory.getLogger(ResourcesQueryService.class);

    private final ResourcesRepository resourcesRepository;

    private final ResourcesMapper resourcesMapper;

    public ResourcesQueryService(ResourcesRepository resourcesRepository, ResourcesMapper resourcesMapper) {
        this.resourcesRepository = resourcesRepository;
        this.resourcesMapper = resourcesMapper;
    }

    /**
     * Return a {@link List} of {@link ResourcesDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ResourcesDTO> findByCriteria(ResourcesCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Resources> specification = createSpecification(criteria);
        return resourcesMapper.toDto(resourcesRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ResourcesDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ResourcesDTO> findByCriteria(ResourcesCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Resources> specification = createSpecification(criteria);
        return resourcesRepository.findAll(specification, page)
            .map(resourcesMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ResourcesCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Resources> specification = createSpecification(criteria);
        return resourcesRepository.count(specification);
    }

    /**
     * Function to convert {@link ResourcesCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Resources> createSpecification(ResourcesCriteria criteria) {
        Specification<Resources> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Resources_.id));
            }
            if (criteria.getDomain() != null) {
                specification = specification.and(buildSpecification(criteria.getDomain(), Resources_.domain));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildSpecification(criteria.getType(), Resources_.type));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Resources_.name));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), Resources_.description));
            }
        }
        return specification;
    }
}
