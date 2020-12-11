package com.mycompany.myapp.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.CompanyEntityAccount} entity.
 */
public class CompanyEntityAccountDTO implements Serializable {
    
    private Long id;

    @NotNull
    @Size(min = 20, max = 200)
    private String nickName;

    @NotNull
    private String accountNo;


    private Long ownerEntityId;

    private String ownerEntityLegalName;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public Long getOwnerEntityId() {
        return ownerEntityId;
    }

    public void setOwnerEntityId(Long companyEntityId) {
        this.ownerEntityId = companyEntityId;
    }

    public String getOwnerEntityLegalName() {
        return ownerEntityLegalName;
    }

    public void setOwnerEntityLegalName(String companyEntityLegalName) {
        this.ownerEntityLegalName = companyEntityLegalName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CompanyEntityAccountDTO)) {
            return false;
        }

        return id != null && id.equals(((CompanyEntityAccountDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CompanyEntityAccountDTO{" +
            "id=" + getId() +
            ", nickName='" + getNickName() + "'" +
            ", accountNo='" + getAccountNo() + "'" +
            ", ownerEntityId=" + getOwnerEntityId() +
            ", ownerEntityLegalName='" + getOwnerEntityLegalName() + "'" +
            "}";
    }
}
