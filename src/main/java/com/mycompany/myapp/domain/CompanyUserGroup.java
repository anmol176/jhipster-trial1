package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A CompanyUserGroup.
 */
@Entity
@Table(name = "company_user_group")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CompanyUserGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 5, max = 25)
    @Column(name = "user_group_name", length = 25, nullable = false)
    private String userGroupName;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "companyUserGroups", allowSetters = true)
    private CompanyGroup companyGroup;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @NotNull
    @JoinTable(name = "company_user_group_assigned_resource_groups",
               joinColumns = @JoinColumn(name = "company_user_group_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "assigned_resource_groups_id", referencedColumnName = "id"))
    private Set<ResourceActionGroup> assignedResourceGroups = new HashSet<>();

    @ManyToMany(mappedBy = "assignedCompanyUserGroups")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnore
    private Set<CompanyUser> assignedUsers = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserGroupName() {
        return userGroupName;
    }

    public CompanyUserGroup userGroupName(String userGroupName) {
        this.userGroupName = userGroupName;
        return this;
    }

    public void setUserGroupName(String userGroupName) {
        this.userGroupName = userGroupName;
    }

    public CompanyGroup getCompanyGroup() {
        return companyGroup;
    }

    public CompanyUserGroup companyGroup(CompanyGroup companyGroup) {
        this.companyGroup = companyGroup;
        return this;
    }

    public void setCompanyGroup(CompanyGroup companyGroup) {
        this.companyGroup = companyGroup;
    }

    public Set<ResourceActionGroup> getAssignedResourceGroups() {
        return assignedResourceGroups;
    }

    public CompanyUserGroup assignedResourceGroups(Set<ResourceActionGroup> resourceActionGroups) {
        this.assignedResourceGroups = resourceActionGroups;
        return this;
    }

    public CompanyUserGroup addAssignedResourceGroups(ResourceActionGroup resourceActionGroup) {
        this.assignedResourceGroups.add(resourceActionGroup);
        resourceActionGroup.getAssignedCompanyUserGroups().add(this);
        return this;
    }

    public CompanyUserGroup removeAssignedResourceGroups(ResourceActionGroup resourceActionGroup) {
        this.assignedResourceGroups.remove(resourceActionGroup);
        resourceActionGroup.getAssignedCompanyUserGroups().remove(this);
        return this;
    }

    public void setAssignedResourceGroups(Set<ResourceActionGroup> resourceActionGroups) {
        this.assignedResourceGroups = resourceActionGroups;
    }

    public Set<CompanyUser> getAssignedUsers() {
        return assignedUsers;
    }

    public CompanyUserGroup assignedUsers(Set<CompanyUser> companyUsers) {
        this.assignedUsers = companyUsers;
        return this;
    }

    public CompanyUserGroup addAssignedUsers(CompanyUser companyUser) {
        this.assignedUsers.add(companyUser);
        companyUser.getAssignedCompanyUserGroups().add(this);
        return this;
    }

    public CompanyUserGroup removeAssignedUsers(CompanyUser companyUser) {
        this.assignedUsers.remove(companyUser);
        companyUser.getAssignedCompanyUserGroups().remove(this);
        return this;
    }

    public void setAssignedUsers(Set<CompanyUser> companyUsers) {
        this.assignedUsers = companyUsers;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CompanyUserGroup)) {
            return false;
        }
        return id != null && id.equals(((CompanyUserGroup) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CompanyUserGroup{" +
            "id=" + getId() +
            ", userGroupName='" + getUserGroupName() + "'" +
            "}";
    }
}
