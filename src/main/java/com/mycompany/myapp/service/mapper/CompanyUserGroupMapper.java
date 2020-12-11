package com.mycompany.myapp.service.mapper;


import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.CompanyUserGroupDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CompanyUserGroup} and its DTO {@link CompanyUserGroupDTO}.
 */
@Mapper(componentModel = "spring", uses = {CompanyGroupMapper.class, ResourceActionGroupMapper.class})
public interface CompanyUserGroupMapper extends EntityMapper<CompanyUserGroupDTO, CompanyUserGroup> {

    @Mapping(source = "companyGroup.id", target = "companyGroupId")
    @Mapping(source = "companyGroup.groupName", target = "companyGroupGroupName")
    CompanyUserGroupDTO toDto(CompanyUserGroup companyUserGroup);

    @Mapping(source = "companyGroupId", target = "companyGroup")
    @Mapping(target = "removeAssignedResourceGroups", ignore = true)
    @Mapping(target = "assignedUsers", ignore = true)
    @Mapping(target = "removeAssignedUsers", ignore = true)
    CompanyUserGroup toEntity(CompanyUserGroupDTO companyUserGroupDTO);

    default CompanyUserGroup fromId(Long id) {
        if (id == null) {
            return null;
        }
        CompanyUserGroup companyUserGroup = new CompanyUserGroup();
        companyUserGroup.setId(id);
        return companyUserGroup;
    }
}
