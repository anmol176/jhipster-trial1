package com.mycompany.myapp.service.mapper;


import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.ServiceEnrollmentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ServiceEnrollment} and its DTO {@link ServiceEnrollmentDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ServiceEnrollmentMapper extends EntityMapper<ServiceEnrollmentDTO, ServiceEnrollment> {



    default ServiceEnrollment fromId(Long id) {
        if (id == null) {
            return null;
        }
        ServiceEnrollment serviceEnrollment = new ServiceEnrollment();
        serviceEnrollment.setId(id);
        return serviceEnrollment;
    }
}
