package com.mycompany.myapp.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.CompanyGroup} entity.
 */
public class CompanyGroupDTO implements Serializable {
    
    private Long id;

    @NotNull
    @Size(min = 10, max = 10)
    private String gCIF;

    @NotNull
    private String groupName;


    private Long locationId;

    private String locationLocationDetail;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getgCIF() {
        return gCIF;
    }

    public void setgCIF(String gCIF) {
        this.gCIF = gCIF;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CompanyGroupDTO)) {
            return false;
        }

        return id != null && id.equals(((CompanyGroupDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CompanyGroupDTO{" +
            "id=" + getId() +
            ", gCIF='" + getgCIF() + "'" +
            ", groupName='" + getGroupName() + "'" +
            ", locationId=" + getLocationId() +
            ", locationLocationDetail='" + getLocationLocationDetail() + "'" +
            "}";
    }
}
