package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Trial1App;
import com.mycompany.myapp.domain.Resources;
import com.mycompany.myapp.repository.ResourcesRepository;
import com.mycompany.myapp.service.ResourcesService;
import com.mycompany.myapp.service.dto.ResourcesDTO;
import com.mycompany.myapp.service.mapper.ResourcesMapper;
import com.mycompany.myapp.service.dto.ResourcesCriteria;
import com.mycompany.myapp.service.ResourcesQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.domain.enumeration.ProductDomain;
import com.mycompany.myapp.domain.enumeration.ResourceType;
/**
 * Integration tests for the {@link ResourcesResource} REST controller.
 */
@SpringBootTest(classes = Trial1App.class)
@AutoConfigureMockMvc
@WithMockUser
public class ResourcesResourceIT {

    private static final ProductDomain DEFAULT_DOMAIN = ProductDomain.CASHMGMT;
    private static final ProductDomain UPDATED_DOMAIN = ProductDomain.FXMGMT;

    private static final ResourceType DEFAULT_TYPE = ResourceType.SERVICE;
    private static final ResourceType UPDATED_TYPE = ResourceType.PYMT;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private ResourcesRepository resourcesRepository;

    @Autowired
    private ResourcesMapper resourcesMapper;

    @Autowired
    private ResourcesService resourcesService;

    @Autowired
    private ResourcesQueryService resourcesQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restResourcesMockMvc;

    private Resources resources;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Resources createEntity(EntityManager em) {
        Resources resources = new Resources()
            .domain(DEFAULT_DOMAIN)
            .type(DEFAULT_TYPE)
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return resources;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Resources createUpdatedEntity(EntityManager em) {
        Resources resources = new Resources()
            .domain(UPDATED_DOMAIN)
            .type(UPDATED_TYPE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        return resources;
    }

    @BeforeEach
    public void initTest() {
        resources = createEntity(em);
    }

    @Test
    @Transactional
    public void createResources() throws Exception {
        int databaseSizeBeforeCreate = resourcesRepository.findAll().size();
        // Create the Resources
        ResourcesDTO resourcesDTO = resourcesMapper.toDto(resources);
        restResourcesMockMvc.perform(post("/api/resources")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(resourcesDTO)))
            .andExpect(status().isCreated());

        // Validate the Resources in the database
        List<Resources> resourcesList = resourcesRepository.findAll();
        assertThat(resourcesList).hasSize(databaseSizeBeforeCreate + 1);
        Resources testResources = resourcesList.get(resourcesList.size() - 1);
        assertThat(testResources.getDomain()).isEqualTo(DEFAULT_DOMAIN);
        assertThat(testResources.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testResources.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testResources.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createResourcesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = resourcesRepository.findAll().size();

        // Create the Resources with an existing ID
        resources.setId(1L);
        ResourcesDTO resourcesDTO = resourcesMapper.toDto(resources);

        // An entity with an existing ID cannot be created, so this API call must fail
        restResourcesMockMvc.perform(post("/api/resources")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(resourcesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Resources in the database
        List<Resources> resourcesList = resourcesRepository.findAll();
        assertThat(resourcesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkDomainIsRequired() throws Exception {
        int databaseSizeBeforeTest = resourcesRepository.findAll().size();
        // set the field null
        resources.setDomain(null);

        // Create the Resources, which fails.
        ResourcesDTO resourcesDTO = resourcesMapper.toDto(resources);


        restResourcesMockMvc.perform(post("/api/resources")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(resourcesDTO)))
            .andExpect(status().isBadRequest());

        List<Resources> resourcesList = resourcesRepository.findAll();
        assertThat(resourcesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = resourcesRepository.findAll().size();
        // set the field null
        resources.setType(null);

        // Create the Resources, which fails.
        ResourcesDTO resourcesDTO = resourcesMapper.toDto(resources);


        restResourcesMockMvc.perform(post("/api/resources")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(resourcesDTO)))
            .andExpect(status().isBadRequest());

        List<Resources> resourcesList = resourcesRepository.findAll();
        assertThat(resourcesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = resourcesRepository.findAll().size();
        // set the field null
        resources.setName(null);

        // Create the Resources, which fails.
        ResourcesDTO resourcesDTO = resourcesMapper.toDto(resources);


        restResourcesMockMvc.perform(post("/api/resources")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(resourcesDTO)))
            .andExpect(status().isBadRequest());

        List<Resources> resourcesList = resourcesRepository.findAll();
        assertThat(resourcesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllResources() throws Exception {
        // Initialize the database
        resourcesRepository.saveAndFlush(resources);

        // Get all the resourcesList
        restResourcesMockMvc.perform(get("/api/resources?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(resources.getId().intValue())))
            .andExpect(jsonPath("$.[*].domain").value(hasItem(DEFAULT_DOMAIN.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
    
    @Test
    @Transactional
    public void getResources() throws Exception {
        // Initialize the database
        resourcesRepository.saveAndFlush(resources);

        // Get the resources
        restResourcesMockMvc.perform(get("/api/resources/{id}", resources.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(resources.getId().intValue()))
            .andExpect(jsonPath("$.domain").value(DEFAULT_DOMAIN.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }


    @Test
    @Transactional
    public void getResourcesByIdFiltering() throws Exception {
        // Initialize the database
        resourcesRepository.saveAndFlush(resources);

        Long id = resources.getId();

        defaultResourcesShouldBeFound("id.equals=" + id);
        defaultResourcesShouldNotBeFound("id.notEquals=" + id);

        defaultResourcesShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultResourcesShouldNotBeFound("id.greaterThan=" + id);

        defaultResourcesShouldBeFound("id.lessThanOrEqual=" + id);
        defaultResourcesShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllResourcesByDomainIsEqualToSomething() throws Exception {
        // Initialize the database
        resourcesRepository.saveAndFlush(resources);

        // Get all the resourcesList where domain equals to DEFAULT_DOMAIN
        defaultResourcesShouldBeFound("domain.equals=" + DEFAULT_DOMAIN);

        // Get all the resourcesList where domain equals to UPDATED_DOMAIN
        defaultResourcesShouldNotBeFound("domain.equals=" + UPDATED_DOMAIN);
    }

    @Test
    @Transactional
    public void getAllResourcesByDomainIsNotEqualToSomething() throws Exception {
        // Initialize the database
        resourcesRepository.saveAndFlush(resources);

        // Get all the resourcesList where domain not equals to DEFAULT_DOMAIN
        defaultResourcesShouldNotBeFound("domain.notEquals=" + DEFAULT_DOMAIN);

        // Get all the resourcesList where domain not equals to UPDATED_DOMAIN
        defaultResourcesShouldBeFound("domain.notEquals=" + UPDATED_DOMAIN);
    }

    @Test
    @Transactional
    public void getAllResourcesByDomainIsInShouldWork() throws Exception {
        // Initialize the database
        resourcesRepository.saveAndFlush(resources);

        // Get all the resourcesList where domain in DEFAULT_DOMAIN or UPDATED_DOMAIN
        defaultResourcesShouldBeFound("domain.in=" + DEFAULT_DOMAIN + "," + UPDATED_DOMAIN);

        // Get all the resourcesList where domain equals to UPDATED_DOMAIN
        defaultResourcesShouldNotBeFound("domain.in=" + UPDATED_DOMAIN);
    }

    @Test
    @Transactional
    public void getAllResourcesByDomainIsNullOrNotNull() throws Exception {
        // Initialize the database
        resourcesRepository.saveAndFlush(resources);

        // Get all the resourcesList where domain is not null
        defaultResourcesShouldBeFound("domain.specified=true");

        // Get all the resourcesList where domain is null
        defaultResourcesShouldNotBeFound("domain.specified=false");
    }

    @Test
    @Transactional
    public void getAllResourcesByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        resourcesRepository.saveAndFlush(resources);

        // Get all the resourcesList where type equals to DEFAULT_TYPE
        defaultResourcesShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the resourcesList where type equals to UPDATED_TYPE
        defaultResourcesShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllResourcesByTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        resourcesRepository.saveAndFlush(resources);

        // Get all the resourcesList where type not equals to DEFAULT_TYPE
        defaultResourcesShouldNotBeFound("type.notEquals=" + DEFAULT_TYPE);

        // Get all the resourcesList where type not equals to UPDATED_TYPE
        defaultResourcesShouldBeFound("type.notEquals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllResourcesByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        resourcesRepository.saveAndFlush(resources);

        // Get all the resourcesList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultResourcesShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the resourcesList where type equals to UPDATED_TYPE
        defaultResourcesShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllResourcesByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        resourcesRepository.saveAndFlush(resources);

        // Get all the resourcesList where type is not null
        defaultResourcesShouldBeFound("type.specified=true");

        // Get all the resourcesList where type is null
        defaultResourcesShouldNotBeFound("type.specified=false");
    }

    @Test
    @Transactional
    public void getAllResourcesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        resourcesRepository.saveAndFlush(resources);

        // Get all the resourcesList where name equals to DEFAULT_NAME
        defaultResourcesShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the resourcesList where name equals to UPDATED_NAME
        defaultResourcesShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllResourcesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        resourcesRepository.saveAndFlush(resources);

        // Get all the resourcesList where name not equals to DEFAULT_NAME
        defaultResourcesShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the resourcesList where name not equals to UPDATED_NAME
        defaultResourcesShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllResourcesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        resourcesRepository.saveAndFlush(resources);

        // Get all the resourcesList where name in DEFAULT_NAME or UPDATED_NAME
        defaultResourcesShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the resourcesList where name equals to UPDATED_NAME
        defaultResourcesShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllResourcesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        resourcesRepository.saveAndFlush(resources);

        // Get all the resourcesList where name is not null
        defaultResourcesShouldBeFound("name.specified=true");

        // Get all the resourcesList where name is null
        defaultResourcesShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllResourcesByNameContainsSomething() throws Exception {
        // Initialize the database
        resourcesRepository.saveAndFlush(resources);

        // Get all the resourcesList where name contains DEFAULT_NAME
        defaultResourcesShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the resourcesList where name contains UPDATED_NAME
        defaultResourcesShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllResourcesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        resourcesRepository.saveAndFlush(resources);

        // Get all the resourcesList where name does not contain DEFAULT_NAME
        defaultResourcesShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the resourcesList where name does not contain UPDATED_NAME
        defaultResourcesShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllResourcesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        resourcesRepository.saveAndFlush(resources);

        // Get all the resourcesList where description equals to DEFAULT_DESCRIPTION
        defaultResourcesShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the resourcesList where description equals to UPDATED_DESCRIPTION
        defaultResourcesShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllResourcesByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        resourcesRepository.saveAndFlush(resources);

        // Get all the resourcesList where description not equals to DEFAULT_DESCRIPTION
        defaultResourcesShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the resourcesList where description not equals to UPDATED_DESCRIPTION
        defaultResourcesShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllResourcesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        resourcesRepository.saveAndFlush(resources);

        // Get all the resourcesList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultResourcesShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the resourcesList where description equals to UPDATED_DESCRIPTION
        defaultResourcesShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllResourcesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        resourcesRepository.saveAndFlush(resources);

        // Get all the resourcesList where description is not null
        defaultResourcesShouldBeFound("description.specified=true");

        // Get all the resourcesList where description is null
        defaultResourcesShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllResourcesByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        resourcesRepository.saveAndFlush(resources);

        // Get all the resourcesList where description contains DEFAULT_DESCRIPTION
        defaultResourcesShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the resourcesList where description contains UPDATED_DESCRIPTION
        defaultResourcesShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllResourcesByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        resourcesRepository.saveAndFlush(resources);

        // Get all the resourcesList where description does not contain DEFAULT_DESCRIPTION
        defaultResourcesShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the resourcesList where description does not contain UPDATED_DESCRIPTION
        defaultResourcesShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultResourcesShouldBeFound(String filter) throws Exception {
        restResourcesMockMvc.perform(get("/api/resources?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(resources.getId().intValue())))
            .andExpect(jsonPath("$.[*].domain").value(hasItem(DEFAULT_DOMAIN.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));

        // Check, that the count call also returns 1
        restResourcesMockMvc.perform(get("/api/resources/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultResourcesShouldNotBeFound(String filter) throws Exception {
        restResourcesMockMvc.perform(get("/api/resources?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restResourcesMockMvc.perform(get("/api/resources/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingResources() throws Exception {
        // Get the resources
        restResourcesMockMvc.perform(get("/api/resources/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateResources() throws Exception {
        // Initialize the database
        resourcesRepository.saveAndFlush(resources);

        int databaseSizeBeforeUpdate = resourcesRepository.findAll().size();

        // Update the resources
        Resources updatedResources = resourcesRepository.findById(resources.getId()).get();
        // Disconnect from session so that the updates on updatedResources are not directly saved in db
        em.detach(updatedResources);
        updatedResources
            .domain(UPDATED_DOMAIN)
            .type(UPDATED_TYPE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        ResourcesDTO resourcesDTO = resourcesMapper.toDto(updatedResources);

        restResourcesMockMvc.perform(put("/api/resources")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(resourcesDTO)))
            .andExpect(status().isOk());

        // Validate the Resources in the database
        List<Resources> resourcesList = resourcesRepository.findAll();
        assertThat(resourcesList).hasSize(databaseSizeBeforeUpdate);
        Resources testResources = resourcesList.get(resourcesList.size() - 1);
        assertThat(testResources.getDomain()).isEqualTo(UPDATED_DOMAIN);
        assertThat(testResources.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testResources.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testResources.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingResources() throws Exception {
        int databaseSizeBeforeUpdate = resourcesRepository.findAll().size();

        // Create the Resources
        ResourcesDTO resourcesDTO = resourcesMapper.toDto(resources);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restResourcesMockMvc.perform(put("/api/resources")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(resourcesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Resources in the database
        List<Resources> resourcesList = resourcesRepository.findAll();
        assertThat(resourcesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteResources() throws Exception {
        // Initialize the database
        resourcesRepository.saveAndFlush(resources);

        int databaseSizeBeforeDelete = resourcesRepository.findAll().size();

        // Delete the resources
        restResourcesMockMvc.perform(delete("/api/resources/{id}", resources.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Resources> resourcesList = resourcesRepository.findAll();
        assertThat(resourcesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
