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
 * A ResourceActionGroup.
 */
@Entity
@Table(name = "resources_actions_groups")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ResourceActionGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 10, max = 25)
    @Column(name = "resource_group_name", length = 25, nullable = false)
    private String resourceGroupName;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "resourceActionGroups", allowSetters = true)
    private ResourceAction resourceActions;

    @ManyToMany(mappedBy = "assignedResourceGroups")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnore
    private Set<CompanyUserGroup> assignedCompanyUserGroups = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getResourceGroupName() {
        return resourceGroupName;
    }

    public ResourceActionGroup resourceGroupName(String resourceGroupName) {
        this.resourceGroupName = resourceGroupName;
        return this;
    }

    public void setResourceGroupName(String resourceGroupName) {
        this.resourceGroupName = resourceGroupName;
    }

    public ResourceAction getResourceActions() {
        return resourceActions;
    }

    public ResourceActionGroup resourceActions(ResourceAction resourceAction) {
        this.resourceActions = resourceAction;
        return this;
    }

    public void setResourceActions(ResourceAction resourceAction) {
        this.resourceActions = resourceAction;
    }

    public Set<CompanyUserGroup> getAssignedCompanyUserGroups() {
        return assignedCompanyUserGroups;
    }

    public ResourceActionGroup assignedCompanyUserGroups(Set<CompanyUserGroup> companyUserGroups) {
        this.assignedCompanyUserGroups = companyUserGroups;
        return this;
    }

    public ResourceActionGroup addAssignedCompanyUserGroups(CompanyUserGroup companyUserGroup) {
        this.assignedCompanyUserGroups.add(companyUserGroup);
        companyUserGroup.getAssignedResourceGroups().add(this);
        return this;
    }

    public ResourceActionGroup removeAssignedCompanyUserGroups(CompanyUserGroup companyUserGroup) {
        this.assignedCompanyUserGroups.remove(companyUserGroup);
        companyUserGroup.getAssignedResourceGroups().remove(this);
        return this;
    }

    public void setAssignedCompanyUserGroups(Set<CompanyUserGroup> companyUserGroups) {
        this.assignedCompanyUserGroups = companyUserGroups;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ResourceActionGroup)) {
            return false;
        }
        return id != null && id.equals(((ResourceActionGroup) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ResourceActionGroup{" +
            "id=" + getId() +
            ", resourceGroupName='" + getResourceGroupName() + "'" +
            "}";
    }
}
