package com.mycompany.myapp.service.mapper;


import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.ResourcesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Resources} and its DTO {@link ResourcesDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ResourcesMapper extends EntityMapper<ResourcesDTO, Resources> {



    default Resources fromId(Long id) {
        if (id == null) {
            return null;
        }
        Resources resources = new Resources();
        resources.setId(id);
        return resources;
    }
}
