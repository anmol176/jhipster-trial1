package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.mycompany.myapp.domain.CompanyEntity} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.CompanyEntityResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /company-entities?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CompanyEntityCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter cif;

    private StringFilter legalName;

    private LongFilter locationId;

    private LongFilter companyGroupId;

    public CompanyEntityCriteria() {
    }

    public CompanyEntityCriteria(CompanyEntityCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.cif = other.cif == null ? null : other.cif.copy();
        this.legalName = other.legalName == null ? null : other.legalName.copy();
        this.locationId = other.locationId == null ? null : other.locationId.copy();
        this.companyGroupId = other.companyGroupId == null ? null : other.companyGroupId.copy();
    }

    @Override
    public CompanyEntityCriteria copy() {
        return new CompanyEntityCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getCif() {
        return cif;
    }

    public void setCif(StringFilter cif) {
        this.cif = cif;
    }

    public StringFilter getLegalName() {
        return legalName;
    }

    public void setLegalName(StringFilter legalName) {
        this.legalName = legalName;
    }

    public LongFilter getLocationId() {
        return locationId;
    }

    public void setLocationId(LongFilter locationId) {
        this.locationId = locationId;
    }

    public LongFilter getCompanyGroupId() {
        return companyGroupId;
    }

    public void setCompanyGroupId(LongFilter companyGroupId) {
        this.companyGroupId = companyGroupId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CompanyEntityCriteria that = (CompanyEntityCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(cif, that.cif) &&
            Objects.equals(legalName, that.legalName) &&
            Objects.equals(locationId, that.locationId) &&
            Objects.equals(companyGroupId, that.companyGroupId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        cif,
        legalName,
        locationId,
        companyGroupId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CompanyEntityCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (cif != null ? "cif=" + cif + ", " : "") +
                (legalName != null ? "legalName=" + legalName + ", " : "") +
                (locationId != null ? "locationId=" + locationId + ", " : "") +
                (companyGroupId != null ? "companyGroupId=" + companyGroupId + ", " : "") +
            "}";
    }

}
