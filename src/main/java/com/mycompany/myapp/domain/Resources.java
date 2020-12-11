package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

import com.mycompany.myapp.domain.enumeration.ProductDomain;

import com.mycompany.myapp.domain.enumeration.ResourceType;

/**
 * A Resources.
 */
@Entity
@Table(name = "resources")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Resources implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "domain", nullable = false)
    private ProductDomain domain;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private ResourceType type;

    @NotNull
    @Size(min = 10, max = 25)
    @Column(name = "name", length = 25, nullable = false, unique = true)
    private String name;

    @Size(min = 5, max = 255)
    @Column(name = "description", length = 255)
    private String description;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProductDomain getDomain() {
        return domain;
    }

    public Resources domain(ProductDomain domain) {
        this.domain = domain;
        return this;
    }

    public void setDomain(ProductDomain domain) {
        this.domain = domain;
    }

    public ResourceType getType() {
        return type;
    }

    public Resources type(ResourceType type) {
        this.type = type;
        return this;
    }

    public void setType(ResourceType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public Resources name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Resources description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Resources)) {
            return false;
        }
        return id != null && id.equals(((Resources) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Resources{" +
            "id=" + getId() +
            ", domain='" + getDomain() + "'" +
            ", type='" + getType() + "'" +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
