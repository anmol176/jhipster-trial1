package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.CompanyGroupService;
import com.mycompany.myapp.domain.CompanyGroup;
import com.mycompany.myapp.repository.CompanyGroupRepository;
import com.mycompany.myapp.service.dto.CompanyGroupDTO;
import com.mycompany.myapp.service.mapper.CompanyGroupMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CompanyGroup}.
 */
@Service
@Transactional
public class CompanyGroupServiceImpl implements CompanyGroupService {

    private final Logger log = LoggerFactory.getLogger(CompanyGroupServiceImpl.class);

    private final CompanyGroupRepository companyGroupRepository;

    private final CompanyGroupMapper companyGroupMapper;

    public CompanyGroupServiceImpl(CompanyGroupRepository companyGroupRepository, CompanyGroupMapper companyGroupMapper) {
        this.companyGroupRepository = companyGroupRepository;
        this.companyGroupMapper = companyGroupMapper;
    }

    @Override
    public CompanyGroupDTO save(CompanyGroupDTO companyGroupDTO) {
        log.debug("Request to save CompanyGroup : {}", companyGroupDTO);
        CompanyGroup companyGroup = companyGroupMapper.toEntity(companyGroupDTO);
        companyGroup = companyGroupRepository.save(companyGroup);
        return companyGroupMapper.toDto(companyGroup);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CompanyGroupDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CompanyGroups");
        return companyGroupRepository.findAll(pageable)
            .map(companyGroupMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<CompanyGroupDTO> findOne(Long id) {
        log.debug("Request to get CompanyGroup : {}", id);
        return companyGroupRepository.findById(id)
            .map(companyGroupMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CompanyGroup : {}", id);
        companyGroupRepository.deleteById(id);
    }
}
