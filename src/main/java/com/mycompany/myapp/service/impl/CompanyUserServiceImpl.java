package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.CompanyUserService;
import com.mycompany.myapp.domain.CompanyUser;
import com.mycompany.myapp.repository.CompanyUserRepository;
import com.mycompany.myapp.service.dto.CompanyUserDTO;
import com.mycompany.myapp.service.mapper.CompanyUserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CompanyUser}.
 */
@Service
@Transactional
public class CompanyUserServiceImpl implements CompanyUserService {

    private final Logger log = LoggerFactory.getLogger(CompanyUserServiceImpl.class);

    private final CompanyUserRepository companyUserRepository;

    private final CompanyUserMapper companyUserMapper;

    public CompanyUserServiceImpl(CompanyUserRepository companyUserRepository, CompanyUserMapper companyUserMapper) {
        this.companyUserRepository = companyUserRepository;
        this.companyUserMapper = companyUserMapper;
    }

    @Override
    public CompanyUserDTO save(CompanyUserDTO companyUserDTO) {
        log.debug("Request to save CompanyUser : {}", companyUserDTO);
        CompanyUser companyUser = companyUserMapper.toEntity(companyUserDTO);
        companyUser = companyUserRepository.save(companyUser);
        return companyUserMapper.toDto(companyUser);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CompanyUserDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CompanyUsers");
        return companyUserRepository.findAll(pageable)
            .map(companyUserMapper::toDto);
    }


    public Page<CompanyUserDTO> findAllWithEagerRelationships(Pageable pageable) {
        return companyUserRepository.findAllWithEagerRelationships(pageable).map(companyUserMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CompanyUserDTO> findOne(Long id) {
        log.debug("Request to get CompanyUser : {}", id);
        return companyUserRepository.findOneWithEagerRelationships(id)
            .map(companyUserMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CompanyUser : {}", id);
        companyUserRepository.deleteById(id);
    }
}
