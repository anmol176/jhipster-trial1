package com.mycompany.myapp.service.mapper;


import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.ResourceActionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ResourceAction} and its DTO {@link ResourceActionDTO}.
 */
@Mapper(componentModel = "spring", uses = {ResourcesMapper.class})
public interface ResourceActionMapper extends EntityMapper<ResourceActionDTO, ResourceAction> {

    @Mapping(source = "resourceGroupName.id", target = "resourceGroupNameId")
    @Mapping(source = "resourceGroupName.name", target = "resourceGroupNameName")
    ResourceActionDTO toDto(ResourceAction resourceAction);

    @Mapping(source = "resourceGroupNameId", target = "resourceGroupName")
    ResourceAction toEntity(ResourceActionDTO resourceActionDTO);

    default ResourceAction fromId(Long id) {
        if (id == null) {
            return null;
        }
        ResourceAction resourceAction = new ResourceAction();
        resourceAction.setId(id);
        return resourceAction;
    }
}
