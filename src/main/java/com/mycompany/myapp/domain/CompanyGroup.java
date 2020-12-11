package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A CompanyGroup.
 */
@Entity
@Table(name = "company_group")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CompanyGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 10, max = 10)
    @Column(name = "g_cif", length = 10, nullable = false)
    private String gCIF;

    @NotNull
    @Column(name = "group_name", nullable = false)
    private String groupName;

    @OneToOne(optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private Location location;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getgCIF() {
        return gCIF;
    }

    public CompanyGroup gCIF(String gCIF) {
        this.gCIF = gCIF;
        return this;
    }

    public void setgCIF(String gCIF) {
        this.gCIF = gCIF;
    }

    public String getGroupName() {
        return groupName;
    }

    public CompanyGroup groupName(String groupName) {
        this.groupName = groupName;
        return this;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Location getLocation() {
        return location;
    }

    public CompanyGroup location(Location location) {
        this.location = location;
        return this;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CompanyGroup)) {
            return false;
        }
        return id != null && id.equals(((CompanyGroup) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CompanyGroup{" +
            "id=" + getId() +
            ", gCIF='" + getgCIF() + "'" +
            ", groupName='" + getGroupName() + "'" +
            "}";
    }
}
