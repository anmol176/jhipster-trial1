package com.mycompany.myapp.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import com.mycompany.myapp.domain.enumeration.Action;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.ResourceAction} entity.
 */
public class ResourceActionDTO implements Serializable {
    
    private Long id;

    @NotNull
    private Action action;

    @NotNull
    @Size(min = 5, max = 50)
    private String actionDesciption;


    private Long resourceGroupNameId;

    private String resourceGroupNameName;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public String getActionDesciption() {
        return actionDesciption;
    }

    public void setActionDesciption(String actionDesciption) {
        this.actionDesciption = actionDesciption;
    }

    public Long getResourceGroupNameId() {
        return resourceGroupNameId;
    }

    public void setResourceGroupNameId(Long resourcesId) {
        this.resourceGroupNameId = resourcesId;
    }

    public String getResourceGroupNameName() {
        return resourceGroupNameName;
    }

    public void setResourceGroupNameName(String resourcesName) {
        this.resourceGroupNameName = resourcesName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ResourceActionDTO)) {
            return false;
        }

        return id != null && id.equals(((ResourceActionDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ResourceActionDTO{" +
            "id=" + getId() +
            ", action='" + getAction() + "'" +
            ", actionDesciption='" + getActionDesciption() + "'" +
            ", resourceGroupNameId=" + getResourceGroupNameId() +
            ", resourceGroupNameName='" + getResourceGroupNameName() + "'" +
            "}";
    }
}
