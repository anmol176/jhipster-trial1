package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.ResourceActionService;
import com.mycompany.myapp.domain.ResourceAction;
import com.mycompany.myapp.repository.ResourceActionRepository;
import com.mycompany.myapp.service.dto.ResourceActionDTO;
import com.mycompany.myapp.service.mapper.ResourceActionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link ResourceAction}.
 */
@Service
@Transactional
public class ResourceActionServiceImpl implements ResourceActionService {

    private final Logger log = LoggerFactory.getLogger(ResourceActionServiceImpl.class);

    private final ResourceActionRepository resourceActionRepository;

    private final ResourceActionMapper resourceActionMapper;

    public ResourceActionServiceImpl(ResourceActionRepository resourceActionRepository, ResourceActionMapper resourceActionMapper) {
        this.resourceActionRepository = resourceActionRepository;
        this.resourceActionMapper = resourceActionMapper;
    }

    @Override
    public ResourceActionDTO save(ResourceActionDTO resourceActionDTO) {
        log.debug("Request to save ResourceAction : {}", resourceActionDTO);
        ResourceAction resourceAction = resourceActionMapper.toEntity(resourceActionDTO);
        resourceAction = resourceActionRepository.save(resourceAction);
        return resourceActionMapper.toDto(resourceAction);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ResourceActionDTO> findAll() {
        log.debug("Request to get all ResourceActions");
        return resourceActionRepository.findAll().stream()
            .map(resourceActionMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<ResourceActionDTO> findOne(Long id) {
        log.debug("Request to get ResourceAction : {}", id);
        return resourceActionRepository.findById(id)
            .map(resourceActionMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ResourceAction : {}", id);
        resourceActionRepository.deleteById(id);
    }
}
