package com.mycompany.myapp.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Country} entity.
 */
public class CountryDTO implements Serializable {
    
    private Long id;

    @NotNull
    @Size(min = 5, max = 255)
    private String countryName;

    @NotNull
    @Size(min = 2, max = 2)
    private String isoCode;


    private Long regionId;

    private String regionRegionName;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getIsoCode() {
        return isoCode;
    }

    public void setIsoCode(String isoCode) {
        this.isoCode = isoCode;
    }

    public Long getRegionId() {
        return regionId;
    }

    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }

    public String getRegionRegionName() {
        return regionRegionName;
    }

    public void setRegionRegionName(String regionRegionName) {
        this.regionRegionName = regionRegionName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CountryDTO)) {
            return false;
        }

        return id != null && id.equals(((CountryDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CountryDTO{" +
            "id=" + getId() +
            ", countryName='" + getCountryName() + "'" +
            ", isoCode='" + getIsoCode() + "'" +
            ", regionId=" + getRegionId() +
            ", regionRegionName='" + getRegionRegionName() + "'" +
            "}";
    }
}
