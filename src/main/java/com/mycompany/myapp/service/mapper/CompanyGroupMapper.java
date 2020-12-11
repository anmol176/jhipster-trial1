package com.mycompany.myapp.service.mapper;


import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.CompanyGroupDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CompanyGroup} and its DTO {@link CompanyGroupDTO}.
 */
@Mapper(componentModel = "spring", uses = {LocationMapper.class})
public interface CompanyGroupMapper extends EntityMapper<CompanyGroupDTO, CompanyGroup> {

    @Mapping(source = "location.id", target = "locationId")
    @Mapping(source = "location.locationDetail", target = "locationLocationDetail")
    CompanyGroupDTO toDto(CompanyGroup companyGroup);

    @Mapping(source = "locationId", target = "location")
    CompanyGroup toEntity(CompanyGroupDTO companyGroupDTO);

    default CompanyGroup fromId(Long id) {
        if (id == null) {
            return null;
        }
        CompanyGroup companyGroup = new CompanyGroup();
        companyGroup.setId(id);
        return companyGroup;
    }
}
