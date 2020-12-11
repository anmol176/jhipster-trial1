package com.mycompany.myapp.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import javax.persistence.Lob;
import com.mycompany.myapp.domain.enumeration.ServiceEnum;
import com.mycompany.myapp.domain.enumeration.Status;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.ServiceEnrollment} entity.
 */
public class ServiceEnrollmentDTO implements Serializable {
    
    private Long id;

    @NotNull
    private ServiceEnum serviceName;

    @NotNull
    private String uen;

    @Lob
    private byte[] uploadServiceRequestDocument;

    private String uploadServiceRequestDocumentContentType;
    @Size(min = 20, max = 255)
    private String serviceDescription;

    private Status status;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ServiceEnum getServiceName() {
        return serviceName;
    }

    public void setServiceName(ServiceEnum serviceName) {
        this.serviceName = serviceName;
    }

    public String getUen() {
        return uen;
    }

    public void setUen(String uen) {
        this.uen = uen;
    }

    public byte[] getUploadServiceRequestDocument() {
        return uploadServiceRequestDocument;
    }

    public void setUploadServiceRequestDocument(byte[] uploadServiceRequestDocument) {
        this.uploadServiceRequestDocument = uploadServiceRequestDocument;
    }

    public String getUploadServiceRequestDocumentContentType() {
        return uploadServiceRequestDocumentContentType;
    }

    public void setUploadServiceRequestDocumentContentType(String uploadServiceRequestDocumentContentType) {
        this.uploadServiceRequestDocumentContentType = uploadServiceRequestDocumentContentType;
    }

    public String getServiceDescription() {
        return serviceDescription;
    }

    public void setServiceDescription(String serviceDescription) {
        this.serviceDescription = serviceDescription;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ServiceEnrollmentDTO)) {
            return false;
        }

        return id != null && id.equals(((ServiceEnrollmentDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ServiceEnrollmentDTO{" +
            "id=" + getId() +
            ", serviceName='" + getServiceName() + "'" +
            ", uen='" + getUen() + "'" +
            ", uploadServiceRequestDocument='" + getUploadServiceRequestDocument() + "'" +
            ", serviceDescription='" + getServiceDescription() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
