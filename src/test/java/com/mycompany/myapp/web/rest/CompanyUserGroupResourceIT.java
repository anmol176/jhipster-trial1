package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Trial1App;
import com.mycompany.myapp.domain.CompanyUserGroup;
import com.mycompany.myapp.domain.CompanyGroup;
import com.mycompany.myapp.domain.ResourceActionGroup;
import com.mycompany.myapp.repository.CompanyUserGroupRepository;
import com.mycompany.myapp.service.CompanyUserGroupService;
import com.mycompany.myapp.service.dto.CompanyUserGroupDTO;
import com.mycompany.myapp.service.mapper.CompanyUserGroupMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link CompanyUserGroupResource} REST controller.
 */
@SpringBootTest(classes = Trial1App.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class CompanyUserGroupResourceIT {

    private static final String DEFAULT_USER_GROUP_NAME = "AAAAAAAAAA";
    private static final String UPDATED_USER_GROUP_NAME = "BBBBBBBBBB";

    @Autowired
    private CompanyUserGroupRepository companyUserGroupRepository;

    @Mock
    private CompanyUserGroupRepository companyUserGroupRepositoryMock;

    @Autowired
    private CompanyUserGroupMapper companyUserGroupMapper;

    @Mock
    private CompanyUserGroupService companyUserGroupServiceMock;

    @Autowired
    private CompanyUserGroupService companyUserGroupService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCompanyUserGroupMockMvc;

    private CompanyUserGroup companyUserGroup;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CompanyUserGroup createEntity(EntityManager em) {
        CompanyUserGroup companyUserGroup = new CompanyUserGroup()
            .userGroupName(DEFAULT_USER_GROUP_NAME);
        // Add required entity
        CompanyGroup companyGroup;
        if (TestUtil.findAll(em, CompanyGroup.class).isEmpty()) {
            companyGroup = CompanyGroupResourceIT.createEntity(em);
            em.persist(companyGroup);
            em.flush();
        } else {
            companyGroup = TestUtil.findAll(em, CompanyGroup.class).get(0);
        }
        companyUserGroup.setCompanyGroup(companyGroup);
        // Add required entity
        ResourceActionGroup resourceActionGroup;
        if (TestUtil.findAll(em, ResourceActionGroup.class).isEmpty()) {
            resourceActionGroup = ResourceActionGroupResourceIT.createEntity(em);
            em.persist(resourceActionGroup);
            em.flush();
        } else {
            resourceActionGroup = TestUtil.findAll(em, ResourceActionGroup.class).get(0);
        }
        companyUserGroup.getAssignedResourceGroups().add(resourceActionGroup);
        return companyUserGroup;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CompanyUserGroup createUpdatedEntity(EntityManager em) {
        CompanyUserGroup companyUserGroup = new CompanyUserGroup()
            .userGroupName(UPDATED_USER_GROUP_NAME);
        // Add required entity
        CompanyGroup companyGroup;
        if (TestUtil.findAll(em, CompanyGroup.class).isEmpty()) {
            companyGroup = CompanyGroupResourceIT.createUpdatedEntity(em);
            em.persist(companyGroup);
            em.flush();
        } else {
            companyGroup = TestUtil.findAll(em, CompanyGroup.class).get(0);
        }
        companyUserGroup.setCompanyGroup(companyGroup);
        // Add required entity
        ResourceActionGroup resourceActionGroup;
        if (TestUtil.findAll(em, ResourceActionGroup.class).isEmpty()) {
            resourceActionGroup = ResourceActionGroupResourceIT.createUpdatedEntity(em);
            em.persist(resourceActionGroup);
            em.flush();
        } else {
            resourceActionGroup = TestUtil.findAll(em, ResourceActionGroup.class).get(0);
        }
        companyUserGroup.getAssignedResourceGroups().add(resourceActionGroup);
        return companyUserGroup;
    }

    @BeforeEach
    public void initTest() {
        companyUserGroup = createEntity(em);
    }

    @Test
    @Transactional
    public void createCompanyUserGroup() throws Exception {
        int databaseSizeBeforeCreate = companyUserGroupRepository.findAll().size();
        // Create the CompanyUserGroup
        CompanyUserGroupDTO companyUserGroupDTO = companyUserGroupMapper.toDto(companyUserGroup);
        restCompanyUserGroupMockMvc.perform(post("/api/company-user-groups")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(companyUserGroupDTO)))
            .andExpect(status().isCreated());

        // Validate the CompanyUserGroup in the database
        List<CompanyUserGroup> companyUserGroupList = companyUserGroupRepository.findAll();
        assertThat(companyUserGroupList).hasSize(databaseSizeBeforeCreate + 1);
        CompanyUserGroup testCompanyUserGroup = companyUserGroupList.get(companyUserGroupList.size() - 1);
        assertThat(testCompanyUserGroup.getUserGroupName()).isEqualTo(DEFAULT_USER_GROUP_NAME);
    }

    @Test
    @Transactional
    public void createCompanyUserGroupWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = companyUserGroupRepository.findAll().size();

        // Create the CompanyUserGroup with an existing ID
        companyUserGroup.setId(1L);
        CompanyUserGroupDTO companyUserGroupDTO = companyUserGroupMapper.toDto(companyUserGroup);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCompanyUserGroupMockMvc.perform(post("/api/company-user-groups")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(companyUserGroupDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CompanyUserGroup in the database
        List<CompanyUserGroup> companyUserGroupList = companyUserGroupRepository.findAll();
        assertThat(companyUserGroupList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkUserGroupNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = companyUserGroupRepository.findAll().size();
        // set the field null
        companyUserGroup.setUserGroupName(null);

        // Create the CompanyUserGroup, which fails.
        CompanyUserGroupDTO companyUserGroupDTO = companyUserGroupMapper.toDto(companyUserGroup);


        restCompanyUserGroupMockMvc.perform(post("/api/company-user-groups")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(companyUserGroupDTO)))
            .andExpect(status().isBadRequest());

        List<CompanyUserGroup> companyUserGroupList = companyUserGroupRepository.findAll();
        assertThat(companyUserGroupList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCompanyUserGroups() throws Exception {
        // Initialize the database
        companyUserGroupRepository.saveAndFlush(companyUserGroup);

        // Get all the companyUserGroupList
        restCompanyUserGroupMockMvc.perform(get("/api/company-user-groups?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(companyUserGroup.getId().intValue())))
            .andExpect(jsonPath("$.[*].userGroupName").value(hasItem(DEFAULT_USER_GROUP_NAME)));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllCompanyUserGroupsWithEagerRelationshipsIsEnabled() throws Exception {
        when(companyUserGroupServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restCompanyUserGroupMockMvc.perform(get("/api/company-user-groups?eagerload=true"))
            .andExpect(status().isOk());

        verify(companyUserGroupServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllCompanyUserGroupsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(companyUserGroupServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restCompanyUserGroupMockMvc.perform(get("/api/company-user-groups?eagerload=true"))
            .andExpect(status().isOk());

        verify(companyUserGroupServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getCompanyUserGroup() throws Exception {
        // Initialize the database
        companyUserGroupRepository.saveAndFlush(companyUserGroup);

        // Get the companyUserGroup
        restCompanyUserGroupMockMvc.perform(get("/api/company-user-groups/{id}", companyUserGroup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(companyUserGroup.getId().intValue()))
            .andExpect(jsonPath("$.userGroupName").value(DEFAULT_USER_GROUP_NAME));
    }
    @Test
    @Transactional
    public void getNonExistingCompanyUserGroup() throws Exception {
        // Get the companyUserGroup
        restCompanyUserGroupMockMvc.perform(get("/api/company-user-groups/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCompanyUserGroup() throws Exception {
        // Initialize the database
        companyUserGroupRepository.saveAndFlush(companyUserGroup);

        int databaseSizeBeforeUpdate = companyUserGroupRepository.findAll().size();

        // Update the companyUserGroup
        CompanyUserGroup updatedCompanyUserGroup = companyUserGroupRepository.findById(companyUserGroup.getId()).get();
        // Disconnect from session so that the updates on updatedCompanyUserGroup are not directly saved in db
        em.detach(updatedCompanyUserGroup);
        updatedCompanyUserGroup
            .userGroupName(UPDATED_USER_GROUP_NAME);
        CompanyUserGroupDTO companyUserGroupDTO = companyUserGroupMapper.toDto(updatedCompanyUserGroup);

        restCompanyUserGroupMockMvc.perform(put("/api/company-user-groups")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(companyUserGroupDTO)))
            .andExpect(status().isOk());

        // Validate the CompanyUserGroup in the database
        List<CompanyUserGroup> companyUserGroupList = companyUserGroupRepository.findAll();
        assertThat(companyUserGroupList).hasSize(databaseSizeBeforeUpdate);
        CompanyUserGroup testCompanyUserGroup = companyUserGroupList.get(companyUserGroupList.size() - 1);
        assertThat(testCompanyUserGroup.getUserGroupName()).isEqualTo(UPDATED_USER_GROUP_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingCompanyUserGroup() throws Exception {
        int databaseSizeBeforeUpdate = companyUserGroupRepository.findAll().size();

        // Create the CompanyUserGroup
        CompanyUserGroupDTO companyUserGroupDTO = companyUserGroupMapper.toDto(companyUserGroup);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompanyUserGroupMockMvc.perform(put("/api/company-user-groups")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(companyUserGroupDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CompanyUserGroup in the database
        List<CompanyUserGroup> companyUserGroupList = companyUserGroupRepository.findAll();
        assertThat(companyUserGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCompanyUserGroup() throws Exception {
        // Initialize the database
        companyUserGroupRepository.saveAndFlush(companyUserGroup);

        int databaseSizeBeforeDelete = companyUserGroupRepository.findAll().size();

        // Delete the companyUserGroup
        restCompanyUserGroupMockMvc.perform(delete("/api/company-user-groups/{id}", companyUserGroup.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CompanyUserGroup> companyUserGroupList = companyUserGroupRepository.findAll();
        assertThat(companyUserGroupList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
