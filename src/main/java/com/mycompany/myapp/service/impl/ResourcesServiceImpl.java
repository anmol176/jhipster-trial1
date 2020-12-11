package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.ResourcesService;
import com.mycompany.myapp.domain.Resources;
import com.mycompany.myapp.repository.ResourcesRepository;
import com.mycompany.myapp.service.dto.ResourcesDTO;
import com.mycompany.myapp.service.mapper.ResourcesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Resources}.
 */
@Service
@Transactional
public class ResourcesServiceImpl implements ResourcesService {

    private final Logger log = LoggerFactory.getLogger(ResourcesServiceImpl.class);

    private final ResourcesRepository resourcesRepository;

    private final ResourcesMapper resourcesMapper;

    public ResourcesServiceImpl(ResourcesRepository resourcesRepository, ResourcesMapper resourcesMapper) {
        this.resourcesRepository = resourcesRepository;
        this.resourcesMapper = resourcesMapper;
    }

    @Override
    public ResourcesDTO save(ResourcesDTO resourcesDTO) {
        log.debug("Request to save Resources : {}", resourcesDTO);
        Resources resources = resourcesMapper.toEntity(resourcesDTO);
        resources = resourcesRepository.save(resources);
        return resourcesMapper.toDto(resources);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ResourcesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Resources");
        return resourcesRepository.findAll(pageable)
            .map(resourcesMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<ResourcesDTO> findOne(Long id) {
        log.debug("Request to get Resources : {}", id);
        return resourcesRepository.findById(id)
            .map(resourcesMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Resources : {}", id);
        resourcesRepository.deleteById(id);
    }
}
