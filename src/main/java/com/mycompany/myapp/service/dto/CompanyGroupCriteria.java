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
 * Criteria class for the {@link com.mycompany.myapp.domain.CompanyGroup} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.CompanyGroupResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /company-groups?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CompanyGroupCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter gCIF;

    private StringFilter groupName;

    private LongFilter locationId;

    public CompanyGroupCriteria() {
    }

    public CompanyGroupCriteria(CompanyGroupCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.gCIF = other.gCIF == null ? null : other.gCIF.copy();
        this.groupName = other.groupName == null ? null : other.groupName.copy();
        this.locationId = other.locationId == null ? null : other.locationId.copy();
    }

    @Override
    public CompanyGroupCriteria copy() {
        return new CompanyGroupCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getgCIF() {
        return gCIF;
    }

    public void setgCIF(StringFilter gCIF) {
        this.gCIF = gCIF;
    }

    public StringFilter getGroupName() {
        return groupName;
    }

    public void setGroupName(StringFilter groupName) {
        this.groupName = groupName;
    }

    public LongFilter getLocationId() {
        return locationId;
    }

    public void setLocationId(LongFilter locationId) {
        this.locationId = locationId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CompanyGroupCriteria that = (CompanyGroupCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(gCIF, that.gCIF) &&
            Objects.equals(groupName, that.groupName) &&
            Objects.equals(locationId, that.locationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        gCIF,
        groupName,
        locationId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CompanyGroupCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (gCIF != null ? "gCIF=" + gCIF + ", " : "") +
                (groupName != null ? "groupName=" + groupName + ", " : "") +
                (locationId != null ? "locationId=" + locationId + ", " : "") +
            "}";
    }

}
