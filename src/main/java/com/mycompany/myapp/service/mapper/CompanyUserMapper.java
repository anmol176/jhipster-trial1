package com.mycompany.myapp.service.mapper;


import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.CompanyUserDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CompanyUser} and its DTO {@link CompanyUserDTO}.
 */
@Mapper(componentModel = "spring", uses = {CompanyGroupMapper.class, CompanyUserGroupMapper.class, CompanyEntityAccountMapper.class})
public interface CompanyUserMapper extends EntityMapper<CompanyUserDTO, CompanyUser> {

    @Mapping(source = "companyGroup.id", target = "companyGroupId")
    @Mapping(source = "companyGroup.groupName", target = "companyGroupGroupName")
    CompanyUserDTO toDto(CompanyUser companyUser);

    @Mapping(source = "companyGroupId", target = "companyGroup")
    @Mapping(target = "removeAssignedCompanyUserGroups", ignore = true)
    @Mapping(target = "removeAssignedAccounts", ignore = true)
    CompanyUser toEntity(CompanyUserDTO companyUserDTO);

    default CompanyUser fromId(Long id) {
        if (id == null) {
            return null;
        }
        CompanyUser companyUser = new CompanyUser();
        companyUser.setId(id);
        return companyUser;
    }
}
