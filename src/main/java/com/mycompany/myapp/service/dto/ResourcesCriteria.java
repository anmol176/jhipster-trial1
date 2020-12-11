package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import com.mycompany.myapp.domain.enumeration.ProductDomain;
import com.mycompany.myapp.domain.enumeration.ResourceType;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.mycompany.myapp.domain.Resources} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.ResourcesResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /resources?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ResourcesCriteria implements Serializable, Criteria {
    /**
     * Class for filtering ProductDomain
     */
    public static class ProductDomainFilter extends Filter<ProductDomain> {

        public ProductDomainFilter() {
        }

        public ProductDomainFilter(ProductDomainFilter filter) {
            super(filter);
        }

        @Override
        public ProductDomainFilter copy() {
            return new ProductDomainFilter(this);
        }

    }
    /**
     * Class for filtering ResourceType
     */
    public static class ResourceTypeFilter extends Filter<ResourceType> {

        public ResourceTypeFilter() {
        }

        public ResourceTypeFilter(ResourceTypeFilter filter) {
            super(filter);
        }

        @Override
        public ResourceTypeFilter copy() {
            return new ResourceTypeFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private ProductDomainFilter domain;

    private ResourceTypeFilter type;

    private StringFilter name;

    private StringFilter description;

    public ResourcesCriteria() {
    }

    public ResourcesCriteria(ResourcesCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.domain = other.domain == null ? null : other.domain.copy();
        this.type = other.type == null ? null : other.type.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.description = other.description == null ? null : other.description.copy();
    }

    @Override
    public ResourcesCriteria copy() {
        return new ResourcesCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public ProductDomainFilter getDomain() {
        return domain;
    }

    public void setDomain(ProductDomainFilter domain) {
        this.domain = domain;
    }

    public ResourceTypeFilter getType() {
        return type;
    }

    public void setType(ResourceTypeFilter type) {
        this.type = type;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ResourcesCriteria that = (ResourcesCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(domain, that.domain) &&
            Objects.equals(type, that.type) &&
            Objects.equals(name, that.name) &&
            Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        domain,
        type,
        name,
        description
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ResourcesCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (domain != null ? "domain=" + domain + ", " : "") +
                (type != null ? "type=" + type + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
            "}";
    }

}
