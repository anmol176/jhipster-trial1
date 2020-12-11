package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Trial1App;
import com.mycompany.myapp.domain.CompanyEntity;
import com.mycompany.myapp.domain.Location;
import com.mycompany.myapp.domain.CompanyGroup;
import com.mycompany.myapp.repository.CompanyEntityRepository;
import com.mycompany.myapp.service.CompanyEntityService;
import com.mycompany.myapp.service.dto.CompanyEntityDTO;
import com.mycompany.myapp.service.mapper.CompanyEntityMapper;
import com.mycompany.myapp.service.dto.CompanyEntityCriteria;
import com.mycompany.myapp.service.CompanyEntityQueryService;

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

/**
 * Integration tests for the {@link CompanyEntityResource} REST controller.
 */
@SpringBootTest(classes = Trial1App.class)
@AutoConfigureMockMvc
@WithMockUser
public class CompanyEntityResourceIT {

    private static final String DEFAULT_CIF = "AAAAAAAAAA";
    private static final String UPDATED_CIF = "BBBBBBBBBB";

    private static final String DEFAULT_LEGAL_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LEGAL_NAME = "BBBBBBBBBB";

    @Autowired
    private CompanyEntityRepository companyEntityRepository;

    @Autowired
    private CompanyEntityMapper companyEntityMapper;

    @Autowired
    private CompanyEntityService companyEntityService;

    @Autowired
    private CompanyEntityQueryService companyEntityQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCompanyEntityMockMvc;

    private CompanyEntity companyEntity;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CompanyEntity createEntity(EntityManager em) {
        CompanyEntity companyEntity = new CompanyEntity()
            .cif(DEFAULT_CIF)
            .legalName(DEFAULT_LEGAL_NAME);
        // Add required entity
        Location location;
        if (TestUtil.findAll(em, Location.class).isEmpty()) {
            location = LocationResourceIT.createEntity(em);
            em.persist(location);
            em.flush();
        } else {
            location = TestUtil.findAll(em, Location.class).get(0);
        }
        companyEntity.setLocation(location);
        // Add required entity
        CompanyGroup companyGroup;
        if (TestUtil.findAll(em, CompanyGroup.class).isEmpty()) {
            companyGroup = CompanyGroupResourceIT.createEntity(em);
            em.persist(companyGroup);
            em.flush();
        } else {
            companyGroup = TestUtil.findAll(em, CompanyGroup.class).get(0);
        }
        companyEntity.setCompanyGroup(companyGroup);
        return companyEntity;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CompanyEntity createUpdatedEntity(EntityManager em) {
        CompanyEntity companyEntity = new CompanyEntity()
            .cif(UPDATED_CIF)
            .legalName(UPDATED_LEGAL_NAME);
        // Add required entity
        Location location;
        if (TestUtil.findAll(em, Location.class).isEmpty()) {
            location = LocationResourceIT.createUpdatedEntity(em);
            em.persist(location);
            em.flush();
        } else {
            location = TestUtil.findAll(em, Location.class).get(0);
        }
        companyEntity.setLocation(location);
        // Add required entity
        CompanyGroup companyGroup;
        if (TestUtil.findAll(em, CompanyGroup.class).isEmpty()) {
            companyGroup = CompanyGroupResourceIT.createUpdatedEntity(em);
            em.persist(companyGroup);
            em.flush();
        } else {
            companyGroup = TestUtil.findAll(em, CompanyGroup.class).get(0);
        }
        companyEntity.setCompanyGroup(companyGroup);
        return companyEntity;
    }

    @BeforeEach
    public void initTest() {
        companyEntity = createEntity(em);
    }

    @Test
    @Transactional
    public void createCompanyEntity() throws Exception {
        int databaseSizeBeforeCreate = companyEntityRepository.findAll().size();
        // Create the CompanyEntity
        CompanyEntityDTO companyEntityDTO = companyEntityMapper.toDto(companyEntity);
        restCompanyEntityMockMvc.perform(post("/api/company-entities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(companyEntityDTO)))
            .andExpect(status().isCreated());

        // Validate the CompanyEntity in the database
        List<CompanyEntity> companyEntityList = companyEntityRepository.findAll();
        assertThat(companyEntityList).hasSize(databaseSizeBeforeCreate + 1);
        CompanyEntity testCompanyEntity = companyEntityList.get(companyEntityList.size() - 1);
        assertThat(testCompanyEntity.getCif()).isEqualTo(DEFAULT_CIF);
        assertThat(testCompanyEntity.getLegalName()).isEqualTo(DEFAULT_LEGAL_NAME);
    }

    @Test
    @Transactional
    public void createCompanyEntityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = companyEntityRepository.findAll().size();

        // Create the CompanyEntity with an existing ID
        companyEntity.setId(1L);
        CompanyEntityDTO companyEntityDTO = companyEntityMapper.toDto(companyEntity);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCompanyEntityMockMvc.perform(post("/api/company-entities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(companyEntityDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CompanyEntity in the database
        List<CompanyEntity> companyEntityList = companyEntityRepository.findAll();
        assertThat(companyEntityList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCifIsRequired() throws Exception {
        int databaseSizeBeforeTest = companyEntityRepository.findAll().size();
        // set the field null
        companyEntity.setCif(null);

        // Create the CompanyEntity, which fails.
        CompanyEntityDTO companyEntityDTO = companyEntityMapper.toDto(companyEntity);


        restCompanyEntityMockMvc.perform(post("/api/company-entities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(companyEntityDTO)))
            .andExpect(status().isBadRequest());

        List<CompanyEntity> companyEntityList = companyEntityRepository.findAll();
        assertThat(companyEntityList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLegalNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = companyEntityRepository.findAll().size();
        // set the field null
        companyEntity.setLegalName(null);

        // Create the CompanyEntity, which fails.
        CompanyEntityDTO companyEntityDTO = companyEntityMapper.toDto(companyEntity);


        restCompanyEntityMockMvc.perform(post("/api/company-entities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(companyEntityDTO)))
            .andExpect(status().isBadRequest());

        List<CompanyEntity> companyEntityList = companyEntityRepository.findAll();
        assertThat(companyEntityList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCompanyEntities() throws Exception {
        // Initialize the database
        companyEntityRepository.saveAndFlush(companyEntity);

        // Get all the companyEntityList
        restCompanyEntityMockMvc.perform(get("/api/company-entities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(companyEntity.getId().intValue())))
            .andExpect(jsonPath("$.[*].cif").value(hasItem(DEFAULT_CIF)))
            .andExpect(jsonPath("$.[*].legalName").value(hasItem(DEFAULT_LEGAL_NAME)));
    }
    
    @Test
    @Transactional
    public void getCompanyEntity() throws Exception {
        // Initialize the database
        companyEntityRepository.saveAndFlush(companyEntity);

        // Get the companyEntity
        restCompanyEntityMockMvc.perform(get("/api/company-entities/{id}", companyEntity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(companyEntity.getId().intValue()))
            .andExpect(jsonPath("$.cif").value(DEFAULT_CIF))
            .andExpect(jsonPath("$.legalName").value(DEFAULT_LEGAL_NAME));
    }


    @Test
    @Transactional
    public void getCompanyEntitiesByIdFiltering() throws Exception {
        // Initialize the database
        companyEntityRepository.saveAndFlush(companyEntity);

        Long id = companyEntity.getId();

        defaultCompanyEntityShouldBeFound("id.equals=" + id);
        defaultCompanyEntityShouldNotBeFound("id.notEquals=" + id);

        defaultCompanyEntityShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCompanyEntityShouldNotBeFound("id.greaterThan=" + id);

        defaultCompanyEntityShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCompanyEntityShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCompanyEntitiesByCifIsEqualToSomething() throws Exception {
        // Initialize the database
        companyEntityRepository.saveAndFlush(companyEntity);

        // Get all the companyEntityList where cif equals to DEFAULT_CIF
        defaultCompanyEntityShouldBeFound("cif.equals=" + DEFAULT_CIF);

        // Get all the companyEntityList where cif equals to UPDATED_CIF
        defaultCompanyEntityShouldNotBeFound("cif.equals=" + UPDATED_CIF);
    }

    @Test
    @Transactional
    public void getAllCompanyEntitiesByCifIsNotEqualToSomething() throws Exception {
        // Initialize the database
        companyEntityRepository.saveAndFlush(companyEntity);

        // Get all the companyEntityList where cif not equals to DEFAULT_CIF
        defaultCompanyEntityShouldNotBeFound("cif.notEquals=" + DEFAULT_CIF);

        // Get all the companyEntityList where cif not equals to UPDATED_CIF
        defaultCompanyEntityShouldBeFound("cif.notEquals=" + UPDATED_CIF);
    }

    @Test
    @Transactional
    public void getAllCompanyEntitiesByCifIsInShouldWork() throws Exception {
        // Initialize the database
        companyEntityRepository.saveAndFlush(companyEntity);

        // Get all the companyEntityList where cif in DEFAULT_CIF or UPDATED_CIF
        defaultCompanyEntityShouldBeFound("cif.in=" + DEFAULT_CIF + "," + UPDATED_CIF);

        // Get all the companyEntityList where cif equals to UPDATED_CIF
        defaultCompanyEntityShouldNotBeFound("cif.in=" + UPDATED_CIF);
    }

    @Test
    @Transactional
    public void getAllCompanyEntitiesByCifIsNullOrNotNull() throws Exception {
        // Initialize the database
        companyEntityRepository.saveAndFlush(companyEntity);

        // Get all the companyEntityList where cif is not null
        defaultCompanyEntityShouldBeFound("cif.specified=true");

        // Get all the companyEntityList where cif is null
        defaultCompanyEntityShouldNotBeFound("cif.specified=false");
    }
                @Test
    @Transactional
    public void getAllCompanyEntitiesByCifContainsSomething() throws Exception {
        // Initialize the database
        companyEntityRepository.saveAndFlush(companyEntity);

        // Get all the companyEntityList where cif contains DEFAULT_CIF
        defaultCompanyEntityShouldBeFound("cif.contains=" + DEFAULT_CIF);

        // Get all the companyEntityList where cif contains UPDATED_CIF
        defaultCompanyEntityShouldNotBeFound("cif.contains=" + UPDATED_CIF);
    }

    @Test
    @Transactional
    public void getAllCompanyEntitiesByCifNotContainsSomething() throws Exception {
        // Initialize the database
        companyEntityRepository.saveAndFlush(companyEntity);

        // Get all the companyEntityList where cif does not contain DEFAULT_CIF
        defaultCompanyEntityShouldNotBeFound("cif.doesNotContain=" + DEFAULT_CIF);

        // Get all the companyEntityList where cif does not contain UPDATED_CIF
        defaultCompanyEntityShouldBeFound("cif.doesNotContain=" + UPDATED_CIF);
    }


    @Test
    @Transactional
    public void getAllCompanyEntitiesByLegalNameIsEqualToSomething() throws Exception {
        // Initialize the database
        companyEntityRepository.saveAndFlush(companyEntity);

        // Get all the companyEntityList where legalName equals to DEFAULT_LEGAL_NAME
        defaultCompanyEntityShouldBeFound("legalName.equals=" + DEFAULT_LEGAL_NAME);

        // Get all the companyEntityList where legalName equals to UPDATED_LEGAL_NAME
        defaultCompanyEntityShouldNotBeFound("legalName.equals=" + UPDATED_LEGAL_NAME);
    }

    @Test
    @Transactional
    public void getAllCompanyEntitiesByLegalNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        companyEntityRepository.saveAndFlush(companyEntity);

        // Get all the companyEntityList where legalName not equals to DEFAULT_LEGAL_NAME
        defaultCompanyEntityShouldNotBeFound("legalName.notEquals=" + DEFAULT_LEGAL_NAME);

        // Get all the companyEntityList where legalName not equals to UPDATED_LEGAL_NAME
        defaultCompanyEntityShouldBeFound("legalName.notEquals=" + UPDATED_LEGAL_NAME);
    }

    @Test
    @Transactional
    public void getAllCompanyEntitiesByLegalNameIsInShouldWork() throws Exception {
        // Initialize the database
        companyEntityRepository.saveAndFlush(companyEntity);

        // Get all the companyEntityList where legalName in DEFAULT_LEGAL_NAME or UPDATED_LEGAL_NAME
        defaultCompanyEntityShouldBeFound("legalName.in=" + DEFAULT_LEGAL_NAME + "," + UPDATED_LEGAL_NAME);

        // Get all the companyEntityList where legalName equals to UPDATED_LEGAL_NAME
        defaultCompanyEntityShouldNotBeFound("legalName.in=" + UPDATED_LEGAL_NAME);
    }

    @Test
    @Transactional
    public void getAllCompanyEntitiesByLegalNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        companyEntityRepository.saveAndFlush(companyEntity);

        // Get all the companyEntityList where legalName is not null
        defaultCompanyEntityShouldBeFound("legalName.specified=true");

        // Get all the companyEntityList where legalName is null
        defaultCompanyEntityShouldNotBeFound("legalName.specified=false");
    }
                @Test
    @Transactional
    public void getAllCompanyEntitiesByLegalNameContainsSomething() throws Exception {
        // Initialize the database
        companyEntityRepository.saveAndFlush(companyEntity);

        // Get all the companyEntityList where legalName contains DEFAULT_LEGAL_NAME
        defaultCompanyEntityShouldBeFound("legalName.contains=" + DEFAULT_LEGAL_NAME);

        // Get all the companyEntityList where legalName contains UPDATED_LEGAL_NAME
        defaultCompanyEntityShouldNotBeFound("legalName.contains=" + UPDATED_LEGAL_NAME);
    }

    @Test
    @Transactional
    public void getAllCompanyEntitiesByLegalNameNotContainsSomething() throws Exception {
        // Initialize the database
        companyEntityRepository.saveAndFlush(companyEntity);

        // Get all the companyEntityList where legalName does not contain DEFAULT_LEGAL_NAME
        defaultCompanyEntityShouldNotBeFound("legalName.doesNotContain=" + DEFAULT_LEGAL_NAME);

        // Get all the companyEntityList where legalName does not contain UPDATED_LEGAL_NAME
        defaultCompanyEntityShouldBeFound("legalName.doesNotContain=" + UPDATED_LEGAL_NAME);
    }


    @Test
    @Transactional
    public void getAllCompanyEntitiesByLocationIsEqualToSomething() throws Exception {
        // Get already existing entity
        Location location = companyEntity.getLocation();
        companyEntityRepository.saveAndFlush(companyEntity);
        Long locationId = location.getId();

        // Get all the companyEntityList where location equals to locationId
        defaultCompanyEntityShouldBeFound("locationId.equals=" + locationId);

        // Get all the companyEntityList where location equals to locationId + 1
        defaultCompanyEntityShouldNotBeFound("locationId.equals=" + (locationId + 1));
    }


    @Test
    @Transactional
    public void getAllCompanyEntitiesByCompanyGroupIsEqualToSomething() throws Exception {
        // Get already existing entity
        CompanyGroup companyGroup = companyEntity.getCompanyGroup();
        companyEntityRepository.saveAndFlush(companyEntity);
        Long companyGroupId = companyGroup.getId();

        // Get all the companyEntityList where companyGroup equals to companyGroupId
        defaultCompanyEntityShouldBeFound("companyGroupId.equals=" + companyGroupId);

        // Get all the companyEntityList where companyGroup equals to companyGroupId + 1
        defaultCompanyEntityShouldNotBeFound("companyGroupId.equals=" + (companyGroupId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCompanyEntityShouldBeFound(String filter) throws Exception {
        restCompanyEntityMockMvc.perform(get("/api/company-entities?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(companyEntity.getId().intValue())))
            .andExpect(jsonPath("$.[*].cif").value(hasItem(DEFAULT_CIF)))
            .andExpect(jsonPath("$.[*].legalName").value(hasItem(DEFAULT_LEGAL_NAME)));

        // Check, that the count call also returns 1
        restCompanyEntityMockMvc.perform(get("/api/company-entities/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCompanyEntityShouldNotBeFound(String filter) throws Exception {
        restCompanyEntityMockMvc.perform(get("/api/company-entities?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCompanyEntityMockMvc.perform(get("/api/company-entities/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingCompanyEntity() throws Exception {
        // Get the companyEntity
        restCompanyEntityMockMvc.perform(get("/api/company-entities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCompanyEntity() throws Exception {
        // Initialize the database
        companyEntityRepository.saveAndFlush(companyEntity);

        int databaseSizeBeforeUpdate = companyEntityRepository.findAll().size();

        // Update the companyEntity
        CompanyEntity updatedCompanyEntity = companyEntityRepository.findById(companyEntity.getId()).get();
        // Disconnect from session so that the updates on updatedCompanyEntity are not directly saved in db
        em.detach(updatedCompanyEntity);
        updatedCompanyEntity
            .cif(UPDATED_CIF)
            .legalName(UPDATED_LEGAL_NAME);
        CompanyEntityDTO companyEntityDTO = companyEntityMapper.toDto(updatedCompanyEntity);

        restCompanyEntityMockMvc.perform(put("/api/company-entities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(companyEntityDTO)))
            .andExpect(status().isOk());

        // Validate the CompanyEntity in the database
        List<CompanyEntity> companyEntityList = companyEntityRepository.findAll();
        assertThat(companyEntityList).hasSize(databaseSizeBeforeUpdate);
        CompanyEntity testCompanyEntity = companyEntityList.get(companyEntityList.size() - 1);
        assertThat(testCompanyEntity.getCif()).isEqualTo(UPDATED_CIF);
        assertThat(testCompanyEntity.getLegalName()).isEqualTo(UPDATED_LEGAL_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingCompanyEntity() throws Exception {
        int databaseSizeBeforeUpdate = companyEntityRepository.findAll().size();

        // Create the CompanyEntity
        CompanyEntityDTO companyEntityDTO = companyEntityMapper.toDto(companyEntity);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompanyEntityMockMvc.perform(put("/api/company-entities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(companyEntityDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CompanyEntity in the database
        List<CompanyEntity> companyEntityList = companyEntityRepository.findAll();
        assertThat(companyEntityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCompanyEntity() throws Exception {
        // Initialize the database
        companyEntityRepository.saveAndFlush(companyEntity);

        int databaseSizeBeforeDelete = companyEntityRepository.findAll().size();

        // Delete the companyEntity
        restCompanyEntityMockMvc.perform(delete("/api/company-entities/{id}", companyEntity.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CompanyEntity> companyEntityList = companyEntityRepository.findAll();
        assertThat(companyEntityList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
