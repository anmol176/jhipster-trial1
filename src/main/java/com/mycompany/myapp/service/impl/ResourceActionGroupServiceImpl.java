package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.ResourceActionGroupService;
import com.mycompany.myapp.domain.ResourceActionGroup;
import com.mycompany.myapp.repository.ResourceActionGroupRepository;
import com.mycompany.myapp.service.dto.ResourceActionGroupDTO;
import com.mycompany.myapp.service.mapper.ResourceActionGroupMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link ResourceActionGroup}.
 */
@Service
@Transactional
public class ResourceActionGroupServiceImpl implements ResourceActionGroupService {

    private final Logger log = LoggerFactory.getLogger(ResourceActionGroupServiceImpl.class);

    private final ResourceActionGroupRepository resourceActionGroupRepository;

    private final ResourceActionGroupMapper resourceActionGroupMapper;

    public ResourceActionGroupServiceImpl(ResourceActionGroupRepository resourceActionGroupRepository, ResourceActionGroupMapper resourceActionGroupMapper) {
        this.resourceActionGroupRepository = resourceActionGroupRepository;
        this.resourceActionGroupMapper = resourceActionGroupMapper;
    }

    @Override
    public ResourceActionGroupDTO save(ResourceActionGroupDTO resourceActionGroupDTO) {
        log.debug("Request to save ResourceActionGroup : {}", resourceActionGroupDTO);
        ResourceActionGroup resourceActionGroup = resourceActionGroupMapper.toEntity(resourceActionGroupDTO);
        resourceActionGroup = resourceActionGroupRepository.save(resourceActionGroup);
        return resourceActionGroupMapper.toDto(resourceActionGroup);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ResourceActionGroupDTO> findAll() {
        log.debug("Request to get all ResourceActionGroups");
        return resourceActionGroupRepository.findAll().stream()
            .map(resourceActionGroupMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<ResourceActionGroupDTO> findOne(Long id) {
        log.debug("Request to get ResourceActionGroup : {}", id);
        return resourceActionGroupRepository.findById(id)
            .map(resourceActionGroupMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ResourceActionGroup : {}", id);
        resourceActionGroupRepository.deleteById(id);
    }
}
