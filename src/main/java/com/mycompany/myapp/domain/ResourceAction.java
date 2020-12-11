package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

import com.mycompany.myapp.domain.enumeration.Action;

/**
 * A ResourceAction.
 */
@Entity
@Table(name = "resources_actions")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ResourceAction implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "action", nullable = false)
    private Action action;

    @NotNull
    @Size(min = 5, max = 50)
    @Column(name = "action_desciption", length = 50, nullable = false)
    private String actionDesciption;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "resourceActions", allowSetters = true)
    private Resources resourceGroupName;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Action getAction() {
        return action;
    }

    public ResourceAction action(Action action) {
        this.action = action;
        return this;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public String getActionDesciption() {
        return actionDesciption;
    }

    public ResourceAction actionDesciption(String actionDesciption) {
        this.actionDesciption = actionDesciption;
        return this;
    }

    public void setActionDesciption(String actionDesciption) {
        this.actionDesciption = actionDesciption;
    }

    public Resources getResourceGroupName() {
        return resourceGroupName;
    }

    public ResourceAction resourceGroupName(Resources resources) {
        this.resourceGroupName = resources;
        return this;
    }

    public void setResourceGroupName(Resources resources) {
        this.resourceGroupName = resources;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ResourceAction)) {
            return false;
        }
        return id != null && id.equals(((ResourceAction) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ResourceAction{" +
            "id=" + getId() +
            ", action='" + getAction() + "'" +
            ", actionDesciption='" + getActionDesciption() + "'" +
            "}";
    }
}
