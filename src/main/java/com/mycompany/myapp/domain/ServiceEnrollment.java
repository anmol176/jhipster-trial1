package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

import com.mycompany.myapp.domain.enumeration.ServiceEnum;

import com.mycompany.myapp.domain.enumeration.Status;

/**
 * A ServiceEnrollment.
 */
@Entity
@Table(name = "service_enrollment")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ServiceEnrollment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "service_name", nullable = false)
    private ServiceEnum serviceName;

    @NotNull
    @Column(name = "uen", nullable = false)
    private String uen;

    @Lob
    @Column(name = "upload_service_request_document")
    private byte[] uploadServiceRequestDocument;

    @Column(name = "upload_service_request_document_content_type")
    private String uploadServiceRequestDocumentContentType;

    @Size(min = 20, max = 255)
    @Column(name = "service_description", length = 255)
    private String serviceDescription;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ServiceEnum getServiceName() {
        return serviceName;
    }

    public ServiceEnrollment serviceName(ServiceEnum serviceName) {
        this.serviceName = serviceName;
        return this;
    }

    public void setServiceName(ServiceEnum serviceName) {
        this.serviceName = serviceName;
    }

    public String getUen() {
        return uen;
    }

    public ServiceEnrollment uen(String uen) {
        this.uen = uen;
        return this;
    }

    public void setUen(String uen) {
        this.uen = uen;
    }

    public byte[] getUploadServiceRequestDocument() {
        return uploadServiceRequestDocument;
    }

    public ServiceEnrollment uploadServiceRequestDocument(byte[] uploadServiceRequestDocument) {
        this.uploadServiceRequestDocument = uploadServiceRequestDocument;
        return this;
    }

    public void setUploadServiceRequestDocument(byte[] uploadServiceRequestDocument) {
        this.uploadServiceRequestDocument = uploadServiceRequestDocument;
    }

    public String getUploadServiceRequestDocumentContentType() {
        return uploadServiceRequestDocumentContentType;
    }

    public ServiceEnrollment uploadServiceRequestDocumentContentType(String uploadServiceRequestDocumentContentType) {
        this.uploadServiceRequestDocumentContentType = uploadServiceRequestDocumentContentType;
        return this;
    }

    public void setUploadServiceRequestDocumentContentType(String uploadServiceRequestDocumentContentType) {
        this.uploadServiceRequestDocumentContentType = uploadServiceRequestDocumentContentType;
    }

    public String getServiceDescription() {
        return serviceDescription;
    }

    public ServiceEnrollment serviceDescription(String serviceDescription) {
        this.serviceDescription = serviceDescription;
        return this;
    }

    public void setServiceDescription(String serviceDescription) {
        this.serviceDescription = serviceDescription;
    }

    public Status getStatus() {
        return status;
    }

    public ServiceEnrollment status(Status status) {
        this.status = status;
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ServiceEnrollment)) {
            return false;
        }
        return id != null && id.equals(((ServiceEnrollment) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ServiceEnrollment{" +
            "id=" + getId() +
            ", serviceName='" + getServiceName() + "'" +
            ", uen='" + getUen() + "'" +
            ", uploadServiceRequestDocument='" + getUploadServiceRequestDocument() + "'" +
            ", uploadServiceRequestDocumentContentType='" + getUploadServiceRequestDocumentContentType() + "'" +
            ", serviceDescription='" + getServiceDescription() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
