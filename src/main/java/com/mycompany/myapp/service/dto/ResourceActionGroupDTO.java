package com.mycompany.myapp.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.ResourceActionGroup} entity.
 */
public class ResourceActionGroupDTO implements Serializable {
    
    private Long id;

    @NotNull
    @Size(min = 10, max = 25)
    private String resourceGroupName;


    private Long resourceActionsId;

    private String resourceActionsActionDesciption;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getResourceGroupName() {
        return resourceGroupName;
    }

    public void setResourceGroupName(String resourceGroupName) {
        this.resourceGroupName = resourceGroupName;
    }

    public Long getResourceActionsId() {
        return resourceActionsId;
    }

    public void setResourceActionsId(Long resourceActionId) {
        this.resourceActionsId = resourceActionId;
    }

    public String getResourceActionsActionDesciption() {
        return resourceActionsActionDesciption;
    }

    public void setResourceActionsActionDesciption(String resourceActionActionDesciption) {
        this.resourceActionsActionDesciption = resourceActionActionDesciption;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ResourceActionGroupDTO)) {
            return false;
        }

        return id != null && id.equals(((ResourceActionGroupDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ResourceActionGroupDTO{" +
            "id=" + getId() +
            ", resourceGroupName='" + getResourceGroupName() + "'" +
            ", resourceActionsId=" + getResourceActionsId() +
            ", resourceActionsActionDesciption='" + getResourceActionsActionDesciption() + "'" +
            "}";
    }
}
