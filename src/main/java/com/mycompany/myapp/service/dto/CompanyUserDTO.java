package com.mycompany.myapp.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import com.mycompany.myapp.domain.enumeration.Language;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.CompanyUser} entity.
 */
public class CompanyUserDTO implements Serializable {
    
    private Long id;

    @NotNull
    @Size(max = 255)
    private String legalName;

    @NotNull
    @Size(max = 125)
    private String email;

    @NotNull
    @Size(max = 32)
    private String phoneNumber;

    @NotNull
    private Language preferedLanguage;


    private Long companyGroupId;

    private String companyGroupGroupName;
    private Set<CompanyUserGroupDTO> assignedCompanyUserGroups = new HashSet<>();
    private Set<CompanyEntityAccountDTO> assignedAccounts = new HashSet<>();
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLegalName() {
        return legalName;
    }

    public void setLegalName(String legalName) {
        this.legalName = legalName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Language getPreferedLanguage() {
        return preferedLanguage;
    }

    public void setPreferedLanguage(Language preferedLanguage) {
        this.preferedLanguage = preferedLanguage;
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

    public Set<CompanyUserGroupDTO> getAssignedCompanyUserGroups() {
        return assignedCompanyUserGroups;
    }

    public void setAssignedCompanyUserGroups(Set<CompanyUserGroupDTO> companyUserGroups) {
        this.assignedCompanyUserGroups = companyUserGroups;
    }

    public Set<CompanyEntityAccountDTO> getAssignedAccounts() {
        return assignedAccounts;
    }

    public void setAssignedAccounts(Set<CompanyEntityAccountDTO> companyEntityAccounts) {
        this.assignedAccounts = companyEntityAccounts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CompanyUserDTO)) {
            return false;
        }

        return id != null && id.equals(((CompanyUserDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CompanyUserDTO{" +
            "id=" + getId() +
            ", legalName='" + getLegalName() + "'" +
            ", email='" + getEmail() + "'" +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            ", preferedLanguage='" + getPreferedLanguage() + "'" +
            ", companyGroupId=" + getCompanyGroupId() +
            ", companyGroupGroupName='" + getCompanyGroupGroupName() + "'" +
            ", assignedCompanyUserGroups='" + getAssignedCompanyUserGroups() + "'" +
            ", assignedAccounts='" + getAssignedAccounts() + "'" +
            "}";
    }
}
