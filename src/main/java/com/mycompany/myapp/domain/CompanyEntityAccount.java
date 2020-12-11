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
 * A CompanyEntityAccount.
 */
@Entity
@Table(name = "company_entity_account")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CompanyEntityAccount implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 20, max = 200)
    @Column(name = "nick_name", length = 200, nullable = false)
    private String nickName;

    @NotNull
    @Column(name = "account_no", nullable = false)
    private String accountNo;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "companyEntityAccounts", allowSetters = true)
    private CompanyEntity ownerEntity;

    @ManyToMany(mappedBy = "assignedAccounts")
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

    public String getNickName() {
        return nickName;
    }

    public CompanyEntityAccount nickName(String nickName) {
        this.nickName = nickName;
        return this;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public CompanyEntityAccount accountNo(String accountNo) {
        this.accountNo = accountNo;
        return this;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public CompanyEntity getOwnerEntity() {
        return ownerEntity;
    }

    public CompanyEntityAccount ownerEntity(CompanyEntity companyEntity) {
        this.ownerEntity = companyEntity;
        return this;
    }

    public void setOwnerEntity(CompanyEntity companyEntity) {
        this.ownerEntity = companyEntity;
    }

    public Set<CompanyUser> getAssignedUsers() {
        return assignedUsers;
    }

    public CompanyEntityAccount assignedUsers(Set<CompanyUser> companyUsers) {
        this.assignedUsers = companyUsers;
        return this;
    }

    public CompanyEntityAccount addAssignedUsers(CompanyUser companyUser) {
        this.assignedUsers.add(companyUser);
        companyUser.getAssignedAccounts().add(this);
        return this;
    }

    public CompanyEntityAccount removeAssignedUsers(CompanyUser companyUser) {
        this.assignedUsers.remove(companyUser);
        companyUser.getAssignedAccounts().remove(this);
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
        if (!(o instanceof CompanyEntityAccount)) {
            return false;
        }
        return id != null && id.equals(((CompanyEntityAccount) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CompanyEntityAccount{" +
            "id=" + getId() +
            ", nickName='" + getNickName() + "'" +
            ", accountNo='" + getAccountNo() + "'" +
            "}";
    }
}
