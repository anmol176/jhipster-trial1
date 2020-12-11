package com.mycompany.myapp.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.CompanyEntity} entity.
 */
public class CompanyEntityDTO implements Serializable {
    
    private Long id;

    @NotNull
    @Size(min = 10, max = 10)
    private String cif;

    @NotNull
    private String legalName;


    private Long locationId;

    private String locationLocationDetail;

    private Long companyGroupId;

    private String companyGroupGroupName;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCif() {
        return cif;
    }

    public void setCif(String cif) {
        this.cif = cif;
    }

    public String getLegalName() {
        return legalName;
    }

    public void setLegalName(String legalName) {
        this.legalName = legalName;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    public String getLocationLocationDetail() {
        return locationLocationDetail;
    }

    public void setLocationLocationDetail(String locationLocationDetail) {
        this.locationLocationDetail = locationLocationDetail;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CompanyEntityDTO)) {
            return false;
        }

        return id != null && id.equals(((CompanyEntityDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CompanyEntityDTO{" +
            "id=" + getId() +
            ", cif='" + getCif() + "'" +
            ", legalName='" + getLegalName() + "'" +
            ", locationId=" + getLocationId() +
            ", locationLocationDetail='" + getLocationLocationDetail() + "'" +
            ", companyGroupId=" + getCompanyGroupId() +
            ", companyGroupGroupName='" + getCompanyGroupGroupName() + "'" +
            "}";
    }
}
