package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.mycompany.myapp.domain.enumeration.Language;

/**
 * A CompanyUser.
 */
@Entity
@Table(name = "company_user")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CompanyUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 255)
    @Column(name = "legal_name", length = 255, nullable = false)
    private String legalName;

    @NotNull
    @Size(max = 125)
    @Column(name = "email", length = 125, nullable = false)
    private String email;

    @NotNull
    @Size(max = 32)
    @Column(name = "phone_number", length = 32, nullable = false)
    private String phoneNumber;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "prefered_language", nullable = false)
    private Language preferedLanguage;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "companyUsers", allowSetters = true)
    private CompanyGroup companyGroup;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @NotNull
    @JoinTable(name = "company_user_assigned_company_user_groups",
               joinColumns = @JoinColumn(name = "company_user_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "assigned_company_user_groups_id", referencedColumnName = "id"))
    private Set<CompanyUserGroup> assignedCompanyUserGroups = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @NotNull
    @JoinTable(name = "company_user_assigned_accounts",
               joinColumns = @JoinColumn(name = "company_user_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "assigned_accounts_id", referencedColumnName = "id"))
    private Set<CompanyEntityAccount> assignedAccounts = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLegalName() {
        return legalName;
    }

    public CompanyUser legalName(String legalName) {
        this.legalName = legalName;
        return this;
    }

    public void setLegalName(String legalName) {
        this.legalName = legalName;
    }

    public String getEmail() {
        return email;
    }

    public CompanyUser email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public CompanyUser phoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Language getPreferedLanguage() {
        return preferedLanguage;
    }

    public CompanyUser preferedLanguage(Language preferedLanguage) {
        this.preferedLanguage = preferedLanguage;
        return this;
    }

    public void setPreferedLanguage(Language preferedLanguage) {
        this.preferedLanguage = preferedLanguage;
    }

    public CompanyGroup getCompanyGroup() {
        return companyGroup;
    }

    public CompanyUser companyGroup(CompanyGroup companyGroup) {
        this.companyGroup = companyGroup;
        return this;
    }

    public void setCompanyGroup(CompanyGroup companyGroup) {
        this.companyGroup = companyGroup;
    }

    public Set<CompanyUserGroup> getAssignedCompanyUserGroups() {
        return assignedCompanyUserGroups;
    }

    public CompanyUser assignedCompanyUserGroups(Set<CompanyUserGroup> companyUserGroups) {
        this.assignedCompanyUserGroups = companyUserGroups;
        return this;
    }

    public CompanyUser addAssignedCompanyUserGroups(CompanyUserGroup companyUserGroup) {
        this.assignedCompanyUserGroups.add(companyUserGroup);
        companyUserGroup.getAssignedUsers().add(this);
        return this;
    }

    public CompanyUser removeAssignedCompanyUserGroups(CompanyUserGroup companyUserGroup) {
        this.assignedCompanyUserGroups.remove(companyUserGroup);
        companyUserGroup.getAssignedUsers().remove(this);
        return this;
    }

    public void setAssignedCompanyUserGroups(Set<CompanyUserGroup> companyUserGroups) {
        this.assignedCompanyUserGroups = companyUserGroups;
    }

    public Set<CompanyEntityAccount> getAssignedAccounts() {
        return assignedAccounts;
    }

    public CompanyUser assignedAccounts(Set<CompanyEntityAccount> companyEntityAccounts) {
        this.assignedAccounts = companyEntityAccounts;
        return this;
    }

    public CompanyUser addAssignedAccounts(CompanyEntityAccount companyEntityAccount) {
        this.assignedAccounts.add(companyEntityAccount);
        companyEntityAccount.getAssignedUsers().add(this);
        return this;
    }

    public CompanyUser removeAssignedAccounts(CompanyEntityAccount companyEntityAccount) {
        this.assignedAccounts.remove(companyEntityAccount);
        companyEntityAccount.getAssignedUsers().remove(this);
        return this;
    }

    public void setAssignedAccounts(Set<CompanyEntityAccount> companyEntityAccounts) {
        this.assignedAccounts = companyEntityAccounts;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CompanyUser)) {
            return false;
        }
        return id != null && id.equals(((CompanyUser) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CompanyUser{" +
            "id=" + getId() +
            ", legalName='" + getLegalName() + "'" +
            ", email='" + getEmail() + "'" +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            ", preferedLanguage='" + getPreferedLanguage() + "'" +
            "}";
    }
}
