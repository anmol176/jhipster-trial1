package com.mycompany.myapp.service.mapper;


import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.CompanyEntityDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CompanyEntity} and its DTO {@link CompanyEntityDTO}.
 */
@Mapper(componentModel = "spring", uses = {LocationMapper.class, CompanyGroupMapper.class})
public interface CompanyEntityMapper extends EntityMapper<CompanyEntityDTO, CompanyEntity> {

    @Mapping(source = "location.id", target = "locationId")
    @Mapping(source = "location.locationDetail", target = "locationLocationDetail")
    @Mapping(source = "companyGroup.id", target = "companyGroupId")
    @Mapping(source = "companyGroup.groupName", target = "companyGroupGroupName")
    CompanyEntityDTO toDto(CompanyEntity companyEntity);

    @Mapping(source = "locationId", target = "location")
    @Mapping(source = "companyGroupId", target = "companyGroup")
    CompanyEntity toEntity(CompanyEntityDTO companyEntityDTO);

    default CompanyEntity fromId(Long id) {
        if (id == null) {
            return null;
        }
        CompanyEntity companyEntity = new CompanyEntity();
        companyEntity.setId(id);
        return companyEntity;
    }
}
