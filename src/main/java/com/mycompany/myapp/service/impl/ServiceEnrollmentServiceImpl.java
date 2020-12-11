package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.ServiceEnrollmentService;
import com.mycompany.myapp.domain.ServiceEnrollment;
import com.mycompany.myapp.repository.ServiceEnrollmentRepository;
import com.mycompany.myapp.service.dto.ServiceEnrollmentDTO;
import com.mycompany.myapp.service.mapper.ServiceEnrollmentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link ServiceEnrollment}.
 */
@Service
@Transactional
public class ServiceEnrollmentServiceImpl implements ServiceEnrollmentService {

    private final Logger log = LoggerFactory.getLogger(ServiceEnrollmentServiceImpl.class);

    private final ServiceEnrollmentRepository serviceEnrollmentRepository;

    private final ServiceEnrollmentMapper serviceEnrollmentMapper;

    public ServiceEnrollmentServiceImpl(ServiceEnrollmentRepository serviceEnrollmentRepository, ServiceEnrollmentMapper serviceEnrollmentMapper) {
        this.serviceEnrollmentRepository = serviceEnrollmentRepository;
        this.serviceEnrollmentMapper = serviceEnrollmentMapper;
    }

    @Override
    public ServiceEnrollmentDTO save(ServiceEnrollmentDTO serviceEnrollmentDTO) {
        log.debug("Request to save ServiceEnrollment : {}", serviceEnrollmentDTO);
        ServiceEnrollment serviceEnrollment = serviceEnrollmentMapper.toEntity(serviceEnrollmentDTO);
        serviceEnrollment = serviceEnrollmentRepository.save(serviceEnrollment);
        return serviceEnrollmentMapper.toDto(serviceEnrollment);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ServiceEnrollmentDTO> findAll() {
        log.debug("Request to get all ServiceEnrollments");
        return serviceEnrollmentRepository.findAll().stream()
            .map(serviceEnrollmentMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<ServiceEnrollmentDTO> findOne(Long id) {
        log.debug("Request to get ServiceEnrollment : {}", id);
        return serviceEnrollmentRepository.findById(id)
            .map(serviceEnrollmentMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ServiceEnrollment : {}", id);
        serviceEnrollmentRepository.deleteById(id);
    }
}
