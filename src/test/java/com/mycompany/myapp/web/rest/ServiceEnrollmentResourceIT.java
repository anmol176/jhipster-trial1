package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Trial1App;
import com.mycompany.myapp.domain.ServiceEnrollment;
import com.mycompany.myapp.repository.ServiceEnrollmentRepository;
import com.mycompany.myapp.service.ServiceEnrollmentService;
import com.mycompany.myapp.service.dto.ServiceEnrollmentDTO;
import com.mycompany.myapp.service.mapper.ServiceEnrollmentMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.domain.enumeration.ServiceEnum;
import com.mycompany.myapp.domain.enumeration.Status;
/**
 * Integration tests for the {@link ServiceEnrollmentResource} REST controller.
 */
@SpringBootTest(classes = Trial1App.class)
@AutoConfigureMockMvc
@WithMockUser
public class ServiceEnrollmentResourceIT {

    private static final ServiceEnum DEFAULT_SERVICE_NAME = ServiceEnum.BIZ_SMART;
    private static final ServiceEnum UPDATED_SERVICE_NAME = ServiceEnum.TT_AMMEND;

    private static final String DEFAULT_UEN = "AAAAAAAAAA";
    private static final String UPDATED_UEN = "BBBBBBBBBB";

    private static final byte[] DEFAULT_UPLOAD_SERVICE_REQUEST_DOCUMENT = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_UPLOAD_SERVICE_REQUEST_DOCUMENT = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_UPLOAD_SERVICE_REQUEST_DOCUMENT_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_UPLOAD_SERVICE_REQUEST_DOCUMENT_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_SERVICE_DESCRIPTION = "AAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_SERVICE_DESCRIPTION = "BBBBBBBBBBBBBBBBBBBB";

    private static final Status DEFAULT_STATUS = Status.APPROVED;
    private static final Status UPDATED_STATUS = Status.DRAFT;

    @Autowired
    private ServiceEnrollmentRepository serviceEnrollmentRepository;

    @Autowired
    private ServiceEnrollmentMapper serviceEnrollmentMapper;

    @Autowired
    private ServiceEnrollmentService serviceEnrollmentService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restServiceEnrollmentMockMvc;

    private ServiceEnrollment serviceEnrollment;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ServiceEnrollment createEntity(EntityManager em) {
        ServiceEnrollment serviceEnrollment = new ServiceEnrollment()
            .serviceName(DEFAULT_SERVICE_NAME)
            .uen(DEFAULT_UEN)
            .uploadServiceRequestDocument(DEFAULT_UPLOAD_SERVICE_REQUEST_DOCUMENT)
            .uploadServiceRequestDocumentContentType(DEFAULT_UPLOAD_SERVICE_REQUEST_DOCUMENT_CONTENT_TYPE)
            .serviceDescription(DEFAULT_SERVICE_DESCRIPTION)
            .status(DEFAULT_STATUS);
        return serviceEnrollment;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ServiceEnrollment createUpdatedEntity(EntityManager em) {
        ServiceEnrollment serviceEnrollment = new ServiceEnrollment()
            .serviceName(UPDATED_SERVICE_NAME)
            .uen(UPDATED_UEN)
            .uploadServiceRequestDocument(UPDATED_UPLOAD_SERVICE_REQUEST_DOCUMENT)
            .uploadServiceRequestDocumentContentType(UPDATED_UPLOAD_SERVICE_REQUEST_DOCUMENT_CONTENT_TYPE)
            .serviceDescription(UPDATED_SERVICE_DESCRIPTION)
            .status(UPDATED_STATUS);
        return serviceEnrollment;
    }

    @BeforeEach
    public void initTest() {
        serviceEnrollment = createEntity(em);
    }

    @Test
    @Transactional
    public void createServiceEnrollment() throws Exception {
        int databaseSizeBeforeCreate = serviceEnrollmentRepository.findAll().size();
        // Create the ServiceEnrollment
        ServiceEnrollmentDTO serviceEnrollmentDTO = serviceEnrollmentMapper.toDto(serviceEnrollment);
        restServiceEnrollmentMockMvc.perform(post("/api/service-enrollments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(serviceEnrollmentDTO)))
            .andExpect(status().isCreated());

        // Validate the ServiceEnrollment in the database
        List<ServiceEnrollment> serviceEnrollmentList = serviceEnrollmentRepository.findAll();
        assertThat(serviceEnrollmentList).hasSize(databaseSizeBeforeCreate + 1);
        ServiceEnrollment testServiceEnrollment = serviceEnrollmentList.get(serviceEnrollmentList.size() - 1);
        assertThat(testServiceEnrollment.getServiceName()).isEqualTo(DEFAULT_SERVICE_NAME);
        assertThat(testServiceEnrollment.getUen()).isEqualTo(DEFAULT_UEN);
        assertThat(testServiceEnrollment.getUploadServiceRequestDocument()).isEqualTo(DEFAULT_UPLOAD_SERVICE_REQUEST_DOCUMENT);
        assertThat(testServiceEnrollment.getUploadServiceRequestDocumentContentType()).isEqualTo(DEFAULT_UPLOAD_SERVICE_REQUEST_DOCUMENT_CONTENT_TYPE);
        assertThat(testServiceEnrollment.getServiceDescription()).isEqualTo(DEFAULT_SERVICE_DESCRIPTION);
        assertThat(testServiceEnrollment.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createServiceEnrollmentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = serviceEnrollmentRepository.findAll().size();

        // Create the ServiceEnrollment with an existing ID
        serviceEnrollment.setId(1L);
        ServiceEnrollmentDTO serviceEnrollmentDTO = serviceEnrollmentMapper.toDto(serviceEnrollment);

        // An entity with an existing ID cannot be created, so this API call must fail
        restServiceEnrollmentMockMvc.perform(post("/api/service-enrollments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(serviceEnrollmentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ServiceEnrollment in the database
        List<ServiceEnrollment> serviceEnrollmentList = serviceEnrollmentRepository.findAll();
        assertThat(serviceEnrollmentList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkServiceNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = serviceEnrollmentRepository.findAll().size();
        // set the field null
        serviceEnrollment.setServiceName(null);

        // Create the ServiceEnrollment, which fails.
        ServiceEnrollmentDTO serviceEnrollmentDTO = serviceEnrollmentMapper.toDto(serviceEnrollment);


        restServiceEnrollmentMockMvc.perform(post("/api/service-enrollments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(serviceEnrollmentDTO)))
            .andExpect(status().isBadRequest());

        List<ServiceEnrollment> serviceEnrollmentList = serviceEnrollmentRepository.findAll();
        assertThat(serviceEnrollmentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUenIsRequired() throws Exception {
        int databaseSizeBeforeTest = serviceEnrollmentRepository.findAll().size();
        // set the field null
        serviceEnrollment.setUen(null);

        // Create the ServiceEnrollment, which fails.
        ServiceEnrollmentDTO serviceEnrollmentDTO = serviceEnrollmentMapper.toDto(serviceEnrollment);


        restServiceEnrollmentMockMvc.perform(post("/api/service-enrollments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(serviceEnrollmentDTO)))
            .andExpect(status().isBadRequest());

        List<ServiceEnrollment> serviceEnrollmentList = serviceEnrollmentRepository.findAll();
        assertThat(serviceEnrollmentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllServiceEnrollments() throws Exception {
        // Initialize the database
        serviceEnrollmentRepository.saveAndFlush(serviceEnrollment);

        // Get all the serviceEnrollmentList
        restServiceEnrollmentMockMvc.perform(get("/api/service-enrollments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(serviceEnrollment.getId().intValue())))
            .andExpect(jsonPath("$.[*].serviceName").value(hasItem(DEFAULT_SERVICE_NAME.toString())))
            .andExpect(jsonPath("$.[*].uen").value(hasItem(DEFAULT_UEN)))
            .andExpect(jsonPath("$.[*].uploadServiceRequestDocumentContentType").value(hasItem(DEFAULT_UPLOAD_SERVICE_REQUEST_DOCUMENT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].uploadServiceRequestDocument").value(hasItem(Base64Utils.encodeToString(DEFAULT_UPLOAD_SERVICE_REQUEST_DOCUMENT))))
            .andExpect(jsonPath("$.[*].serviceDescription").value(hasItem(DEFAULT_SERVICE_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }
    
    @Test
    @Transactional
    public void getServiceEnrollment() throws Exception {
        // Initialize the database
        serviceEnrollmentRepository.saveAndFlush(serviceEnrollment);

        // Get the serviceEnrollment
        restServiceEnrollmentMockMvc.perform(get("/api/service-enrollments/{id}", serviceEnrollment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(serviceEnrollment.getId().intValue()))
            .andExpect(jsonPath("$.serviceName").value(DEFAULT_SERVICE_NAME.toString()))
            .andExpect(jsonPath("$.uen").value(DEFAULT_UEN))
            .andExpect(jsonPath("$.uploadServiceRequestDocumentContentType").value(DEFAULT_UPLOAD_SERVICE_REQUEST_DOCUMENT_CONTENT_TYPE))
            .andExpect(jsonPath("$.uploadServiceRequestDocument").value(Base64Utils.encodeToString(DEFAULT_UPLOAD_SERVICE_REQUEST_DOCUMENT)))
            .andExpect(jsonPath("$.serviceDescription").value(DEFAULT_SERVICE_DESCRIPTION))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingServiceEnrollment() throws Exception {
        // Get the serviceEnrollment
        restServiceEnrollmentMockMvc.perform(get("/api/service-enrollments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateServiceEnrollment() throws Exception {
        // Initialize the database
        serviceEnrollmentRepository.saveAndFlush(serviceEnrollment);

        int databaseSizeBeforeUpdate = serviceEnrollmentRepository.findAll().size();

        // Update the serviceEnrollment
        ServiceEnrollment updatedServiceEnrollment = serviceEnrollmentRepository.findById(serviceEnrollment.getId()).get();
        // Disconnect from session so that the updates on updatedServiceEnrollment are not directly saved in db
        em.detach(updatedServiceEnrollment);
        updatedServiceEnrollment
            .serviceName(UPDATED_SERVICE_NAME)
            .uen(UPDATED_UEN)
            .uploadServiceRequestDocument(UPDATED_UPLOAD_SERVICE_REQUEST_DOCUMENT)
            .uploadServiceRequestDocumentContentType(UPDATED_UPLOAD_SERVICE_REQUEST_DOCUMENT_CONTENT_TYPE)
            .serviceDescription(UPDATED_SERVICE_DESCRIPTION)
            .status(UPDATED_STATUS);
        ServiceEnrollmentDTO serviceEnrollmentDTO = serviceEnrollmentMapper.toDto(updatedServiceEnrollment);

        restServiceEnrollmentMockMvc.perform(put("/api/service-enrollments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(serviceEnrollmentDTO)))
            .andExpect(status().isOk());

        // Validate the ServiceEnrollment in the database
        List<ServiceEnrollment> serviceEnrollmentList = serviceEnrollmentRepository.findAll();
        assertThat(serviceEnrollmentList).hasSize(databaseSizeBeforeUpdate);
        ServiceEnrollment testServiceEnrollment = serviceEnrollmentList.get(serviceEnrollmentList.size() - 1);
        assertThat(testServiceEnrollment.getServiceName()).isEqualTo(UPDATED_SERVICE_NAME);
        assertThat(testServiceEnrollment.getUen()).isEqualTo(UPDATED_UEN);
        assertThat(testServiceEnrollment.getUploadServiceRequestDocument()).isEqualTo(UPDATED_UPLOAD_SERVICE_REQUEST_DOCUMENT);
        assertThat(testServiceEnrollment.getUploadServiceRequestDocumentContentType()).isEqualTo(UPDATED_UPLOAD_SERVICE_REQUEST_DOCUMENT_CONTENT_TYPE);
        assertThat(testServiceEnrollment.getServiceDescription()).isEqualTo(UPDATED_SERVICE_DESCRIPTION);
        assertThat(testServiceEnrollment.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingServiceEnrollment() throws Exception {
        int databaseSizeBeforeUpdate = serviceEnrollmentRepository.findAll().size();

        // Create the ServiceEnrollment
        ServiceEnrollmentDTO serviceEnrollmentDTO = serviceEnrollmentMapper.toDto(serviceEnrollment);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServiceEnrollmentMockMvc.perform(put("/api/service-enrollments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(serviceEnrollmentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ServiceEnrollment in the database
        List<ServiceEnrollment> serviceEnrollmentList = serviceEnrollmentRepository.findAll();
        assertThat(serviceEnrollmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteServiceEnrollment() throws Exception {
        // Initialize the database
        serviceEnrollmentRepository.saveAndFlush(serviceEnrollment);

        int databaseSizeBeforeDelete = serviceEnrollmentRepository.findAll().size();

        // Delete the serviceEnrollment
        restServiceEnrollmentMockMvc.perform(delete("/api/service-enrollments/{id}", serviceEnrollment.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ServiceEnrollment> serviceEnrollmentList = serviceEnrollmentRepository.findAll();
        assertThat(serviceEnrollmentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
