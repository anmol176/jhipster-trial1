package com.mycompany.myapp.service.mapper;


import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.ResourceActionGroupDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ResourceActionGroup} and its DTO {@link ResourceActionGroupDTO}.
 */
@Mapper(componentModel = "spring", uses = {ResourceActionMapper.class})
public interface ResourceActionGroupMapper extends EntityMapper<ResourceActionGroupDTO, ResourceActionGroup> {

    @Mapping(source = "resourceActions.id", target = "resourceActionsId")
    @Mapping(source = "resourceActions.actionDesciption", target = "resourceActionsActionDesciption")
    ResourceActionGroupDTO toDto(ResourceActionGroup resourceActionGroup);

    @Mapping(source = "resourceActionsId", target = "resourceActions")
    @Mapping(target = "assignedCompanyUserGroups", ignore = true)
    @Mapping(target = "removeAssignedCompanyUserGroups", ignore = true)
    ResourceActionGroup toEntity(ResourceActionGroupDTO resourceActionGroupDTO);

    default ResourceActionGroup fromId(Long id) {
        if (id == null) {
            return null;
        }
        ResourceActionGroup resourceActionGroup = new ResourceActionGroup();
        resourceActionGroup.setId(id);
        return resourceActionGroup;
    }
}
