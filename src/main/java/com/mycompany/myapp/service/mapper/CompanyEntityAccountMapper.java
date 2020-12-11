package com.mycompany.myapp.service.mapper;


import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.CompanyEntityAccountDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CompanyEntityAccount} and its DTO {@link CompanyEntityAccountDTO}.
 */
@Mapper(componentModel = "spring", uses = {CompanyEntityMapper.class})
public interface CompanyEntityAccountMapper extends EntityMapper<CompanyEntityAccountDTO, CompanyEntityAccount> {

    @Mapping(source = "ownerEntity.id", target = "ownerEntityId")
    @Mapping(source = "ownerEntity.legalName", target = "ownerEntityLegalName")
    CompanyEntityAccountDTO toDto(CompanyEntityAccount companyEntityAccount);

    @Mapping(source = "ownerEntityId", target = "ownerEntity")
    @Mapping(target = "assignedUsers", ignore = true)
    @Mapping(target = "removeAssignedUsers", ignore = true)
    CompanyEntityAccount toEntity(CompanyEntityAccountDTO companyEntityAccountDTO);

    default CompanyEntityAccount fromId(Long id) {
        if (id == null) {
            return null;
        }
        CompanyEntityAccount companyEntityAccount = new CompanyEntityAccount();
        companyEntityAccount.setId(id);
        return companyEntityAccount;
    }
}
