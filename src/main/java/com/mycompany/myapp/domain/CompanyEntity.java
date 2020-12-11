package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A CompanyEntity.
 */
@Entity
@Table(name = "company_entity")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CompanyEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 10, max = 10)
    @Column(name = "cif", length = 10, nullable = false)
    private String cif;

    @NotNull
    @Column(name = "legal_name", nullable = false)
    private String legalName;

    @OneToOne(optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private Location location;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "companyEntities", allowSetters = true)
    private CompanyGroup companyGroup;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCif() {
        return cif;
    }

    public CompanyEntity cif(String cif) {
        this.cif = cif;
        return this;
    }

    public void setCif(String cif) {
        this.cif = cif;
    }

    public String getLegalName() {
        return legalName;
    }

    public CompanyEntity legalName(String legalName) {
        this.legalName = legalName;
        return this;
    }

    public void setLegalName(String legalName) {
        this.legalName = legalName;
    }

    public Location getLocation() {
        return location;
    }

    public CompanyEntity location(Location location) {
        this.location = location;
        return this;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public CompanyGroup getCompanyGroup() {
        return companyGroup;
    }

    public CompanyEntity companyGroup(CompanyGroup companyGroup) {
        this.companyGroup = companyGroup;
        return this;
    }

    public void setCompanyGroup(CompanyGroup companyGroup) {
        this.companyGroup = companyGroup;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CompanyEntity)) {
            return false;
        }
        return id != null && id.equals(((CompanyEntity) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CompanyEntity{" +
            "id=" + getId() +
            ", cif='" + getCif() + "'" +
            ", legalName='" + getLegalName() + "'" +
            "}";
    }
}
