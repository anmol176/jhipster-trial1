package com.mycompany.myapp.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import com.mycompany.myapp.domain.enumeration.ProductDomain;
import com.mycompany.myapp.domain.enumeration.ResourceType;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Resources} entity.
 */
public class ResourcesDTO implements Serializable {
    
    private Long id;

    @NotNull
    private ProductDomain domain;

    @NotNull
    private ResourceType type;

    @NotNull
    @Size(min = 10, max = 25)
    private String name;

    @Size(min = 5, max = 255)
    private String description;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProductDomain getDomain() {
        return domain;
    }

    public void setDomain(ProductDomain domain) {
        this.domain = domain;
    }

    public ResourceType getType() {
        return type;
    }

    public void setType(ResourceType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ResourcesDTO)) {
            return false;
        }

        return id != null && id.equals(((ResourcesDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ResourcesDTO{" +
            "id=" + getId() +
            ", domain='" + getDomain() + "'" +
            ", type='" + getType() + "'" +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
