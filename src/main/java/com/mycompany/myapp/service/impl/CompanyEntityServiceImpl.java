package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.CompanyEntityService;
import com.mycompany.myapp.domain.CompanyEntity;
import com.mycompany.myapp.repository.CompanyEntityRepository;
import com.mycompany.myapp.service.dto.CompanyEntityDTO;
import com.mycompany.myapp.service.mapper.CompanyEntityMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CompanyEntity}.
 */
@Service
@Transactional
public class CompanyEntityServiceImpl implements CompanyEntityService {

    private final Logger log = LoggerFactory.getLogger(CompanyEntityServiceImpl.class);

    private final CompanyEntityRepository companyEntityRepository;

    private final CompanyEntityMapper companyEntityMapper;

    public CompanyEntityServiceImpl(CompanyEntityRepository companyEntityRepository, CompanyEntityMapper companyEntityMapper) {
        this.companyEntityRepository = companyEntityRepository;
        this.companyEntityMapper = companyEntityMapper;
    }

    @Override
    public CompanyEntityDTO save(CompanyEntityDTO companyEntityDTO) {
        log.debug("Request to save CompanyEntity : {}", companyEntityDTO);
        CompanyEntity companyEntity = companyEntityMapper.toEntity(companyEntityDTO);
        companyEntity = companyEntityRepository.save(companyEntity);
        return companyEntityMapper.toDto(companyEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CompanyEntityDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CompanyEntities");
        return companyEntityRepository.findAll(pageable)
            .map(companyEntityMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<CompanyEntityDTO> findOne(Long id) {
        log.debug("Request to get CompanyEntity : {}", id);
        return companyEntityRepository.findById(id)
            .map(companyEntityMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CompanyEntity : {}", id);
        companyEntityRepository.deleteById(id);
    }
}
