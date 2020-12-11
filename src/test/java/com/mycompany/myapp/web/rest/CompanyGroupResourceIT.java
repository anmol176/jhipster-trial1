package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Trial1App;
import com.mycompany.myapp.domain.CompanyGroup;
import com.mycompany.myapp.domain.Location;
import com.mycompany.myapp.repository.CompanyGroupRepository;
import com.mycompany.myapp.service.CompanyGroupService;
import com.mycompany.myapp.service.dto.CompanyGroupDTO;
import com.mycompany.myapp.service.mapper.CompanyGroupMapper;
import com.mycompany.myapp.service.dto.CompanyGroupCriteria;
import com.mycompany.myapp.service.CompanyGroupQueryService;

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
 * Integration tests for the {@link CompanyGroupResource} REST controller.
 */
@SpringBootTest(classes = Trial1App.class)
@AutoConfigureMockMvc
@WithMockUser
public class CompanyGroupResourceIT {

    private static final String DEFAULT_G_CIF = "AAAAAAAAAA";
    private static final String UPDATED_G_CIF = "BBBBBBBBBB";

    private static final String DEFAULT_GROUP_NAME = "AAAAAAAAAA";
    private static final String UPDATED_GROUP_NAME = "BBBBBBBBBB";

    @Autowired
    private CompanyGroupRepository companyGroupRepository;

    @Autowired
    private CompanyGroupMapper companyGroupMapper;

    @Autowired
    private CompanyGroupService companyGroupService;

    @Autowired
    private CompanyGroupQueryService companyGroupQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCompanyGroupMockMvc;

    private CompanyGroup companyGroup;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CompanyGroup createEntity(EntityManager em) {
        CompanyGroup companyGroup = new CompanyGroup()
            .gCIF(DEFAULT_G_CIF)
            .groupName(DEFAULT_GROUP_NAME);
        // Add required entity
        Location location;
        if (TestUtil.findAll(em, Location.class).isEmpty()) {
            location = LocationResourceIT.createEntity(em);
            em.persist(location);
            em.flush();
        } else {
            location = TestUtil.findAll(em, Location.class).get(0);
        }
        companyGroup.setLocation(location);
        return companyGroup;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CompanyGroup createUpdatedEntity(EntityManager em) {
        CompanyGroup companyGroup = new CompanyGroup()
            .gCIF(UPDATED_G_CIF)
            .groupName(UPDATED_GROUP_NAME);
        // Add required entity
        Location location;
        if (TestUtil.findAll(em, Location.class).isEmpty()) {
            location = LocationResourceIT.createUpdatedEntity(em);
            em.persist(location);
            em.flush();
        } else {
            location = TestUtil.findAll(em, Location.class).get(0);
        }
        companyGroup.setLocation(location);
        return companyGroup;
    }

    @BeforeEach
    public void initTest() {
        companyGroup = createEntity(em);
    }

    @Test
    @Transactional
    public void createCompanyGroup() throws Exception {
        int databaseSizeBeforeCreate = companyGroupRepository.findAll().size();
        // Create the CompanyGroup
        CompanyGroupDTO companyGroupDTO = companyGroupMapper.toDto(companyGroup);
        restCompanyGroupMockMvc.perform(post("/api/company-groups")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(companyGroupDTO)))
            .andExpect(status().isCreated());

        // Validate the CompanyGroup in the database
        List<CompanyGroup> companyGroupList = companyGroupRepository.findAll();
        assertThat(companyGroupList).hasSize(databaseSizeBeforeCreate + 1);
        CompanyGroup testCompanyGroup = companyGroupList.get(companyGroupList.size() - 1);
        assertThat(testCompanyGroup.getgCIF()).isEqualTo(DEFAULT_G_CIF);
        assertThat(testCompanyGroup.getGroupName()).isEqualTo(DEFAULT_GROUP_NAME);
    }

    @Test
    @Transactional
    public void createCompanyGroupWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = companyGroupRepository.findAll().size();

        // Create the CompanyGroup with an existing ID
        companyGroup.setId(1L);
        CompanyGroupDTO companyGroupDTO = companyGroupMapper.toDto(companyGroup);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCompanyGroupMockMvc.perform(post("/api/company-groups")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(companyGroupDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CompanyGroup in the database
        List<CompanyGroup> companyGroupList = companyGroupRepository.findAll();
        assertThat(companyGroupList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkgCIFIsRequired() throws Exception {
        int databaseSizeBeforeTest = companyGroupRepository.findAll().size();
        // set the field null
        companyGroup.setgCIF(null);

        // Create the CompanyGroup, which fails.
        CompanyGroupDTO companyGroupDTO = companyGroupMapper.toDto(companyGroup);


        restCompanyGroupMockMvc.perform(post("/api/company-groups")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(companyGroupDTO)))
            .andExpect(status().isBadRequest());

        List<CompanyGroup> companyGroupList = companyGroupRepository.findAll();
        assertThat(companyGroupList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkGroupNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = companyGroupRepository.findAll().size();
        // set the field null
        companyGroup.setGroupName(null);

        // Create the CompanyGroup, which fails.
        CompanyGroupDTO companyGroupDTO = companyGroupMapper.toDto(companyGroup);


        restCompanyGroupMockMvc.perform(post("/api/company-groups")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(companyGroupDTO)))
            .andExpect(status().isBadRequest());

        List<CompanyGroup> companyGroupList = companyGroupRepository.findAll();
        assertThat(companyGroupList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCompanyGroups() throws Exception {
        // Initialize the database
        companyGroupRepository.saveAndFlush(companyGroup);

        // Get all the companyGroupList
        restCompanyGroupMockMvc.perform(get("/api/company-groups?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(companyGroup.getId().intValue())))
            .andExpect(jsonPath("$.[*].gCIF").value(hasItem(DEFAULT_G_CIF)))
            .andExpect(jsonPath("$.[*].groupName").value(hasItem(DEFAULT_GROUP_NAME)));
    }
    
    @Test
    @Transactional
    public void getCompanyGroup() throws Exception {
        // Initialize the database
        companyGroupRepository.saveAndFlush(companyGroup);

        // Get the companyGroup
        restCompanyGroupMockMvc.perform(get("/api/company-groups/{id}", companyGroup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(companyGroup.getId().intValue()))
            .andExpect(jsonPath("$.gCIF").value(DEFAULT_G_CIF))
            .andExpect(jsonPath("$.groupName").value(DEFAULT_GROUP_NAME));
    }


    @Test
    @Transactional
    public void getCompanyGroupsByIdFiltering() throws Exception {
        // Initialize the database
        companyGroupRepository.saveAndFlush(companyGroup);

        Long id = companyGroup.getId();

        defaultCompanyGroupShouldBeFound("id.equals=" + id);
        defaultCompanyGroupShouldNotBeFound("id.notEquals=" + id);

        defaultCompanyGroupShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCompanyGroupShouldNotBeFound("id.greaterThan=" + id);

        defaultCompanyGroupShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCompanyGroupShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCompanyGroupsBygCIFIsEqualToSomething() throws Exception {
        // Initialize the database
        companyGroupRepository.saveAndFlush(companyGroup);

        // Get all the companyGroupList where gCIF equals to DEFAULT_G_CIF
        defaultCompanyGroupShouldBeFound("gCIF.equals=" + DEFAULT_G_CIF);

        // Get all the companyGroupList where gCIF equals to UPDATED_G_CIF
        defaultCompanyGroupShouldNotBeFound("gCIF.equals=" + UPDATED_G_CIF);
    }

    @Test
    @Transactional
    public void getAllCompanyGroupsBygCIFIsNotEqualToSomething() throws Exception {
        // Initialize the database
        companyGroupRepository.saveAndFlush(companyGroup);

        // Get all the companyGroupList where gCIF not equals to DEFAULT_G_CIF
        defaultCompanyGroupShouldNotBeFound("gCIF.notEquals=" + DEFAULT_G_CIF);

        // Get all the companyGroupList where gCIF not equals to UPDATED_G_CIF
        defaultCompanyGroupShouldBeFound("gCIF.notEquals=" + UPDATED_G_CIF);
    }

    @Test
    @Transactional
    public void getAllCompanyGroupsBygCIFIsInShouldWork() throws Exception {
        // Initialize the database
        companyGroupRepository.saveAndFlush(companyGroup);

        // Get all the companyGroupList where gCIF in DEFAULT_G_CIF or UPDATED_G_CIF
        defaultCompanyGroupShouldBeFound("gCIF.in=" + DEFAULT_G_CIF + "," + UPDATED_G_CIF);

        // Get all the companyGroupList where gCIF equals to UPDATED_G_CIF
        defaultCompanyGroupShouldNotBeFound("gCIF.in=" + UPDATED_G_CIF);
    }

    @Test
    @Transactional
    public void getAllCompanyGroupsBygCIFIsNullOrNotNull() throws Exception {
        // Initialize the database
        companyGroupRepository.saveAndFlush(companyGroup);

        // Get all the companyGroupList where gCIF is not null
        defaultCompanyGroupShouldBeFound("gCIF.specified=true");

        // Get all the companyGroupList where gCIF is null
        defaultCompanyGroupShouldNotBeFound("gCIF.specified=false");
    }
                @Test
    @Transactional
    public void getAllCompanyGroupsBygCIFContainsSomething() throws Exception {
        // Initialize the database
        companyGroupRepository.saveAndFlush(companyGroup);

        // Get all the companyGroupList where gCIF contains DEFAULT_G_CIF
        defaultCompanyGroupShouldBeFound("gCIF.contains=" + DEFAULT_G_CIF);

        // Get all the companyGroupList where gCIF contains UPDATED_G_CIF
        defaultCompanyGroupShouldNotBeFound("gCIF.contains=" + UPDATED_G_CIF);
    }

    @Test
    @Transactional
    public void getAllCompanyGroupsBygCIFNotContainsSomething() throws Exception {
        // Initialize the database
        companyGroupRepository.saveAndFlush(companyGroup);

        // Get all the companyGroupList where gCIF does not contain DEFAULT_G_CIF
        defaultCompanyGroupShouldNotBeFound("gCIF.doesNotContain=" + DEFAULT_G_CIF);

        // Get all the companyGroupList where gCIF does not contain UPDATED_G_CIF
        defaultCompanyGroupShouldBeFound("gCIF.doesNotContain=" + UPDATED_G_CIF);
    }


    @Test
    @Transactional
    public void getAllCompanyGroupsByGroupNameIsEqualToSomething() throws Exception {
        // Initialize the database
        companyGroupRepository.saveAndFlush(companyGroup);

        // Get all the companyGroupList where groupName equals to DEFAULT_GROUP_NAME
        defaultCompanyGroupShouldBeFound("groupName.equals=" + DEFAULT_GROUP_NAME);

        // Get all the companyGroupList where groupName equals to UPDATED_GROUP_NAME
        defaultCompanyGroupShouldNotBeFound("groupName.equals=" + UPDATED_GROUP_NAME);
    }

    @Test
    @Transactional
    public void getAllCompanyGroupsByGroupNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        companyGroupRepository.saveAndFlush(companyGroup);

        // Get all the companyGroupList where groupName not equals to DEFAULT_GROUP_NAME
        defaultCompanyGroupShouldNotBeFound("groupName.notEquals=" + DEFAULT_GROUP_NAME);

        // Get all the companyGroupList where groupName not equals to UPDATED_GROUP_NAME
        defaultCompanyGroupShouldBeFound("groupName.notEquals=" + UPDATED_GROUP_NAME);
    }

    @Test
    @Transactional
    public void getAllCompanyGroupsByGroupNameIsInShouldWork() throws Exception {
        // Initialize the database
        companyGroupRepository.saveAndFlush(companyGroup);

        // Get all the companyGroupList where groupName in DEFAULT_GROUP_NAME or UPDATED_GROUP_NAME
        defaultCompanyGroupShouldBeFound("groupName.in=" + DEFAULT_GROUP_NAME + "," + UPDATED_GROUP_NAME);

        // Get all the companyGroupList where groupName equals to UPDATED_GROUP_NAME
        defaultCompanyGroupShouldNotBeFound("groupName.in=" + UPDATED_GROUP_NAME);
    }

    @Test
    @Transactional
    public void getAllCompanyGroupsByGroupNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        companyGroupRepository.saveAndFlush(companyGroup);

        // Get all the companyGroupList where groupName is not null
        defaultCompanyGroupShouldBeFound("groupName.specified=true");

        // Get all the companyGroupList where groupName is null
        defaultCompanyGroupShouldNotBeFound("groupName.specified=false");
    }
                @Test
    @Transactional
    public void getAllCompanyGroupsByGroupNameContainsSomething() throws Exception {
        // Initialize the database
        companyGroupRepository.saveAndFlush(companyGroup);

        // Get all the companyGroupList where groupName contains DEFAULT_GROUP_NAME
        defaultCompanyGroupShouldBeFound("groupName.contains=" + DEFAULT_GROUP_NAME);

        // Get all the companyGroupList where groupName contains UPDATED_GROUP_NAME
        defaultCompanyGroupShouldNotBeFound("groupName.contains=" + UPDATED_GROUP_NAME);
    }

    @Test
    @Transactional
    public void getAllCompanyGroupsByGroupNameNotContainsSomething() throws Exception {
        // Initialize the database
        companyGroupRepository.saveAndFlush(companyGroup);

        // Get all the companyGroupList where groupName does not contain DEFAULT_GROUP_NAME
        defaultCompanyGroupShouldNotBeFound("groupName.doesNotContain=" + DEFAULT_GROUP_NAME);

        // Get all the companyGroupList where groupName does not contain UPDATED_GROUP_NAME
        defaultCompanyGroupShouldBeFound("groupName.doesNotContain=" + UPDATED_GROUP_NAME);
    }


    @Test
    @Transactional
    public void getAllCompanyGroupsByLocationIsEqualToSomething() throws Exception {
        // Get already existing entity
        Location location = companyGroup.getLocation();
        companyGroupRepository.saveAndFlush(companyGroup);
        Long locationId = location.getId();

        // Get all the companyGroupList where location equals to locationId
        defaultCompanyGroupShouldBeFound("locationId.equals=" + locationId);

        // Get all the companyGroupList where location equals to locationId + 1
        defaultCompanyGroupShouldNotBeFound("locationId.equals=" + (locationId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCompanyGroupShouldBeFound(String filter) throws Exception {
        restCompanyGroupMockMvc.perform(get("/api/company-groups?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(companyGroup.getId().intValue())))
            .andExpect(jsonPath("$.[*].gCIF").value(hasItem(DEFAULT_G_CIF)))
            .andExpect(jsonPath("$.[*].groupName").value(hasItem(DEFAULT_GROUP_NAME)));

        // Check, that the count call also returns 1
        restCompanyGroupMockMvc.perform(get("/api/company-groups/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCompanyGroupShouldNotBeFound(String filter) throws Exception {
        restCompanyGroupMockMvc.perform(get("/api/company-groups?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCompanyGroupMockMvc.perform(get("/api/company-groups/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingCompanyGroup() throws Exception {
        // Get the companyGroup
        restCompanyGroupMockMvc.perform(get("/api/company-groups/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCompanyGroup() throws Exception {
        // Initialize the database
        companyGroupRepository.saveAndFlush(companyGroup);

        int databaseSizeBeforeUpdate = companyGroupRepository.findAll().size();

        // Update the companyGroup
        CompanyGroup updatedCompanyGroup = companyGroupRepository.findById(companyGroup.getId()).get();
        // Disconnect from session so that the updates on updatedCompanyGroup are not directly saved in db
        em.detach(updatedCompanyGroup);
        updatedCompanyGroup
            .gCIF(UPDATED_G_CIF)
            .groupName(UPDATED_GROUP_NAME);
        CompanyGroupDTO companyGroupDTO = companyGroupMapper.toDto(updatedCompanyGroup);

        restCompanyGroupMockMvc.perform(put("/api/company-groups")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(companyGroupDTO)))
            .andExpect(status().isOk());

        // Validate the CompanyGroup in the database
        List<CompanyGroup> companyGroupList = companyGroupRepository.findAll();
        assertThat(companyGroupList).hasSize(databaseSizeBeforeUpdate);
        CompanyGroup testCompanyGroup = companyGroupList.get(companyGroupList.size() - 1);
        assertThat(testCompanyGroup.getgCIF()).isEqualTo(UPDATED_G_CIF);
        assertThat(testCompanyGroup.getGroupName()).isEqualTo(UPDATED_GROUP_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingCompanyGroup() throws Exception {
        int databaseSizeBeforeUpdate = companyGroupRepository.findAll().size();

        // Create the CompanyGroup
        CompanyGroupDTO companyGroupDTO = companyGroupMapper.toDto(companyGroup);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompanyGroupMockMvc.perform(put("/api/company-groups")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(companyGroupDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CompanyGroup in the database
        List<CompanyGroup> companyGroupList = companyGroupRepository.findAll();
        assertThat(companyGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCompanyGroup() throws Exception {
        // Initialize the database
        companyGroupRepository.saveAndFlush(companyGroup);

        int databaseSizeBeforeDelete = companyGroupRepository.findAll().size();

        // Delete the companyGroup
        restCompanyGroupMockMvc.perform(delete("/api/company-groups/{id}", companyGroup.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CompanyGroup> companyGroupList = companyGroupRepository.findAll();
        assertThat(companyGroupList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
