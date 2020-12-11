package com.mycompany.myapp.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.CompanyUserGroup} entity.
 */
public class CompanyUserGroupDTO implements Serializable {
    
    private Long id;

    @NotNull
    @Size(min = 5, max = 25)
    private String userGroupName;


    private Long companyGroupId;

    private String companyGroupGroupName;
    private Set<ResourceActionGroupDTO> assignedResourceGroups = new HashSet<>();
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserGroupName() {
        return userGroupName;
    }

    public void setUserGroupName(String userGroupName) {
        this.userGroupName = userGroupName;
    }

    public Long getCompanyGroupId() {
        return companyGroupId;
    }

    public void setCompanyGroupId(Long companyGroupId) {
        this.companyGroupId = companyGroupId;
    }

    public String getCompanyGroupGroupName() {
        return companyGroupGroupName;
    }

    public void setCompanyGroupGroupName(String companyGroupGroupName) {
        this.companyGroupGroupName = companyGroupGroupName;
    }

    public Set<ResourceActionGroupDTO> getAssignedResourceGroups() {
        return assignedResourceGroups;
    }

    public void setAssignedResourceGroups(Set<ResourceActionGroupDTO> resourceActionGroups) {
        this.assignedResourceGroups = resourceActionGroups;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CompanyUserGroupDTO)) {
            return false;
        }

        return id != null && id.equals(((CompanyUserGroupDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CompanyUserGroupDTO{" +
            "id=" + getId() +
            ", userGroupName='" + getUserGroupName() + "'" +
            ", companyGroupId=" + getCompanyGroupId() +
            ", companyGroupGroupName='" + getCompanyGroupGroupName() + "'" +
            ", assignedResourceGroups='" + getAssignedResourceGroups() + "'" +
            "}";
    }
}
