package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.CompanyEntityAccountService;
import com.mycompany.myapp.domain.CompanyEntityAccount;
import com.mycompany.myapp.repository.CompanyEntityAccountRepository;
import com.mycompany.myapp.service.dto.CompanyEntityAccountDTO;
import com.mycompany.myapp.service.mapper.CompanyEntityAccountMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CompanyEntityAccount}.
 */
@Service
@Transactional
public class CompanyEntityAccountServiceImpl implements CompanyEntityAccountService {

    private final Logger log = LoggerFactory.getLogger(CompanyEntityAccountServiceImpl.class);

    private final CompanyEntityAccountRepository companyEntityAccountRepository;

    private final CompanyEntityAccountMapper companyEntityAccountMapper;

    public CompanyEntityAccountServiceImpl(CompanyEntityAccountRepository companyEntityAccountRepository, CompanyEntityAccountMapper companyEntityAccountMapper) {
        this.companyEntityAccountRepository = companyEntityAccountRepository;
        this.companyEntityAccountMapper = companyEntityAccountMapper;
    }

    @Override
    public CompanyEntityAccountDTO save(CompanyEntityAccountDTO companyEntityAccountDTO) {
        log.debug("Request to save CompanyEntityAccount : {}", companyEntityAccountDTO);
        CompanyEntityAccount companyEntityAccount = companyEntityAccountMapper.toEntity(companyEntityAccountDTO);
        companyEntityAccount = companyEntityAccountRepository.save(companyEntityAccount);
        return companyEntityAccountMapper.toDto(companyEntityAccount);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CompanyEntityAccountDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CompanyEntityAccounts");
        return companyEntityAccountRepository.findAll(pageable)
            .map(companyEntityAccountMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<CompanyEntityAccountDTO> findOne(Long id) {
        log.debug("Request to get CompanyEntityAccount : {}", id);
        return companyEntityAccountRepository.findById(id)
            .map(companyEntityAccountMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CompanyEntityAccount : {}", id);
        companyEntityAccountRepository.deleteById(id);
    }
}
