package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import com.mycompany.myapp.domain.enumeration.Language;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.mycompany.myapp.domain.CompanyUser} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.CompanyUserResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /company-users?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CompanyUserCriteria implements Serializable, Criteria {
    /**
     * Class for filtering Language
     */
    public static class LanguageFilter extends Filter<Language> {

        public LanguageFilter() {
        }

        public LanguageFilter(LanguageFilter filter) {
            super(filter);
        }

        @Override
        public LanguageFilter copy() {
            return new LanguageFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter legalName;

    private StringFilter email;

    private StringFilter phoneNumber;

    private LanguageFilter preferedLanguage;

    private LongFilter companyGroupId;

    private LongFilter assignedCompanyUserGroupsId;

    private LongFilter assignedAccountsId;

    public CompanyUserCriteria() {
    }

    public CompanyUserCriteria(CompanyUserCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.legalName = other.legalName == null ? null : other.legalName.copy();
        this.email = other.email == null ? null : other.email.copy();
        this.phoneNumber = other.phoneNumber == null ? null : other.phoneNumber.copy();
        this.preferedLanguage = other.preferedLanguage == null ? null : other.preferedLanguage.copy();
        this.companyGroupId = other.companyGroupId == null ? null : other.companyGroupId.copy();
        this.assignedCompanyUserGroupsId = other.assignedCompanyUserGroupsId == null ? null : other.assignedCompanyUserGroupsId.copy();
        this.assignedAccountsId = other.assignedAccountsId == null ? null : other.assignedAccountsId.copy();
    }

    @Override
    public CompanyUserCriteria copy() {
        return new CompanyUserCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getLegalName() {
        return legalName;
    }

    public void setLegalName(StringFilter legalName) {
        this.legalName = legalName;
    }

    public StringFilter getEmail() {
        return email;
    }

    public void setEmail(StringFilter email) {
        this.email = email;
    }

    public StringFilter getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(StringFilter phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LanguageFilter getPreferedLanguage() {
        return preferedLanguage;
    }

    public void setPreferedLanguage(LanguageFilter preferedLanguage) {
        this.preferedLanguage = preferedLanguage;
    }

    public LongFilter getCompanyGroupId() {
        return companyGroupId;
    }

    public void setCompanyGroupId(LongFilter companyGroupId) {
        this.companyGroupId = companyGroupId;
    }

    public LongFilter getAssignedCompanyUserGroupsId() {
        return assignedCompanyUserGroupsId;
    }

    public void setAssignedCompanyUserGroupsId(LongFilter assignedCompanyUserGroupsId) {
        this.assignedCompanyUserGroupsId = assignedCompanyUserGroupsId;
    }

    public LongFilter getAssignedAccountsId() {
        return assignedAccountsId;
    }

    public void setAssignedAccountsId(LongFilter assignedAccountsId) {
        this.assignedAccountsId = assignedAccountsId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CompanyUserCriteria that = (CompanyUserCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(legalName, that.legalName) &&
            Objects.equals(email, that.email) &&
            Objects.equals(phoneNumber, that.phoneNumber) &&
            Objects.equals(preferedLanguage, that.preferedLanguage) &&
            Objects.equals(companyGroupId, that.companyGroupId) &&
            Objects.equals(assignedCompanyUserGroupsId, that.assignedCompanyUserGroupsId) &&
            Objects.equals(assignedAccountsId, that.assignedAccountsId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        legalName,
        email,
        phoneNumber,
        preferedLanguage,
        companyGroupId,
        assignedCompanyUserGroupsId,
        assignedAccountsId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CompanyUserCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (legalName != null ? "legalName=" + legalName + ", " : "") +
                (email != null ? "email=" + email + ", " : "") +
                (phoneNumber != null ? "phoneNumber=" + phoneNumber + ", " : "") +
                (preferedLanguage != null ? "preferedLanguage=" + preferedLanguage + ", " : "") +
                (companyGroupId != null ? "companyGroupId=" + companyGroupId + ", " : "") +
                (assignedCompanyUserGroupsId != null ? "assignedCompanyUserGroupsId=" + assignedCompanyUserGroupsId + ", " : "") +
                (assignedAccountsId != null ? "assignedAccountsId=" + assignedAccountsId + ", " : "") +
            "}";
    }

}
