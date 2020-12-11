package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.CompanyUserGroupService;
import com.mycompany.myapp.domain.CompanyUserGroup;
import com.mycompany.myapp.repository.CompanyUserGroupRepository;
import com.mycompany.myapp.service.dto.CompanyUserGroupDTO;
import com.mycompany.myapp.service.mapper.CompanyUserGroupMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link CompanyUserGroup}.
 */
@Service
@Transactional
public class CompanyUserGroupServiceImpl implements CompanyUserGroupService {

    private final Logger log = LoggerFactory.getLogger(CompanyUserGroupServiceImpl.class);

    private final CompanyUserGroupRepository companyUserGroupRepository;

    private final CompanyUserGroupMapper companyUserGroupMapper;

    public CompanyUserGroupServiceImpl(CompanyUserGroupRepository companyUserGroupRepository, CompanyUserGroupMapper companyUserGroupMapper) {
        this.companyUserGroupRepository = companyUserGroupRepository;
        this.companyUserGroupMapper = companyUserGroupMapper;
    }

    @Override
    public CompanyUserGroupDTO save(CompanyUserGroupDTO companyUserGroupDTO) {
        log.debug("Request to save CompanyUserGroup : {}", companyUserGroupDTO);
        CompanyUserGroup companyUserGroup = companyUserGroupMapper.toEntity(companyUserGroupDTO);
        companyUserGroup = companyUserGroupRepository.save(companyUserGroup);
        return companyUserGroupMapper.toDto(companyUserGroup);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CompanyUserGroupDTO> findAll() {
        log.debug("Request to get all CompanyUserGroups");
        return companyUserGroupRepository.findAllWithEagerRelationships().stream()
            .map(companyUserGroupMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    public Page<CompanyUserGroupDTO> findAllWithEagerRelationships(Pageable pageable) {
        return companyUserGroupRepository.findAllWithEagerRelationships(pageable).map(companyUserGroupMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CompanyUserGroupDTO> findOne(Long id) {
        log.debug("Request to get CompanyUserGroup : {}", id);
        return companyUserGroupRepository.findOneWithEagerRelationships(id)
            .map(companyUserGroupMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CompanyUserGroup : {}", id);
        companyUserGroupRepository.deleteById(id);
    }
}
