package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Trial1App;
import com.mycompany.myapp.domain.ResourceActionGroup;
import com.mycompany.myapp.domain.ResourceAction;
import com.mycompany.myapp.repository.ResourceActionGroupRepository;
import com.mycompany.myapp.service.ResourceActionGroupService;
import com.mycompany.myapp.service.dto.ResourceActionGroupDTO;
import com.mycompany.myapp.service.mapper.ResourceActionGroupMapper;

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
 * Integration tests for the {@link ResourceActionGroupResource} REST controller.
 */
@SpringBootTest(classes = Trial1App.class)
@AutoConfigureMockMvc
@WithMockUser
public class ResourceActionGroupResourceIT {

    private static final String DEFAULT_RESOURCE_GROUP_NAME = "AAAAAAAAAA";
    private static final String UPDATED_RESOURCE_GROUP_NAME = "BBBBBBBBBB";

    @Autowired
    private ResourceActionGroupRepository resourceActionGroupRepository;

    @Autowired
    private ResourceActionGroupMapper resourceActionGroupMapper;

    @Autowired
    private ResourceActionGroupService resourceActionGroupService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restResourceActionGroupMockMvc;

    private ResourceActionGroup resourceActionGroup;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ResourceActionGroup createEntity(EntityManager em) {
        ResourceActionGroup resourceActionGroup = new ResourceActionGroup()
            .resourceGroupName(DEFAULT_RESOURCE_GROUP_NAME);
        // Add required entity
        ResourceAction resourceAction;
        if (TestUtil.findAll(em, ResourceAction.class).isEmpty()) {
            resourceAction = ResourceActionResourceIT.createEntity(em);
            em.persist(resourceAction);
            em.flush();
        } else {
            resourceAction = TestUtil.findAll(em, ResourceAction.class).get(0);
        }
        resourceActionGroup.setResourceActions(resourceAction);
        return resourceActionGroup;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ResourceActionGroup createUpdatedEntity(EntityManager em) {
        ResourceActionGroup resourceActionGroup = new ResourceActionGroup()
            .resourceGroupName(UPDATED_RESOURCE_GROUP_NAME);
        // Add required entity
        ResourceAction resourceAction;
        if (TestUtil.findAll(em, ResourceAction.class).isEmpty()) {
            resourceAction = ResourceActionResourceIT.createUpdatedEntity(em);
            em.persist(resourceAction);
            em.flush();
        } else {
            resourceAction = TestUtil.findAll(em, ResourceAction.class).get(0);
        }
        resourceActionGroup.setResourceActions(resourceAction);
        return resourceActionGroup;
    }

    @BeforeEach
    public void initTest() {
        resourceActionGroup = createEntity(em);
    }

    @Test
    @Transactional
    public void createResourceActionGroup() throws Exception {
        int databaseSizeBeforeCreate = resourceActionGroupRepository.findAll().size();
        // Create the ResourceActionGroup
        ResourceActionGroupDTO resourceActionGroupDTO = resourceActionGroupMapper.toDto(resourceActionGroup);
        restResourceActionGroupMockMvc.perform(post("/api/resource-action-groups")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(resourceActionGroupDTO)))
            .andExpect(status().isCreated());

        // Validate the ResourceActionGroup in the database
        List<ResourceActionGroup> resourceActionGroupList = resourceActionGroupRepository.findAll();
        assertThat(resourceActionGroupList).hasSize(databaseSizeBeforeCreate + 1);
        ResourceActionGroup testResourceActionGroup = resourceActionGroupList.get(resourceActionGroupList.size() - 1);
        assertThat(testResourceActionGroup.getResourceGroupName()).isEqualTo(DEFAULT_RESOURCE_GROUP_NAME);
    }

    @Test
    @Transactional
    public void createResourceActionGroupWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = resourceActionGroupRepository.findAll().size();

        // Create the ResourceActionGroup with an existing ID
        resourceActionGroup.setId(1L);
        ResourceActionGroupDTO resourceActionGroupDTO = resourceActionGroupMapper.toDto(resourceActionGroup);

        // An entity with an existing ID cannot be created, so this API call must fail
        restResourceActionGroupMockMvc.perform(post("/api/resource-action-groups")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(resourceActionGroupDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ResourceActionGroup in the database
        List<ResourceActionGroup> resourceActionGroupList = resourceActionGroupRepository.findAll();
        assertThat(resourceActionGroupList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkResourceGroupNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = resourceActionGroupRepository.findAll().size();
        // set the field null
        resourceActionGroup.setResourceGroupName(null);

        // Create the ResourceActionGroup, which fails.
        ResourceActionGroupDTO resourceActionGroupDTO = resourceActionGroupMapper.toDto(resourceActionGroup);


        restResourceActionGroupMockMvc.perform(post("/api/resource-action-groups")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(resourceActionGroupDTO)))
            .andExpect(status().isBadRequest());

        List<ResourceActionGroup> resourceActionGroupList = resourceActionGroupRepository.findAll();
        assertThat(resourceActionGroupList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllResourceActionGroups() throws Exception {
        // Initialize the database
        resourceActionGroupRepository.saveAndFlush(resourceActionGroup);

        // Get all the resourceActionGroupList
        restResourceActionGroupMockMvc.perform(get("/api/resource-action-groups?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(resourceActionGroup.getId().intValue())))
            .andExpect(jsonPath("$.[*].resourceGroupName").value(hasItem(DEFAULT_RESOURCE_GROUP_NAME)));
    }
    
    @Test
    @Transactional
    public void getResourceActionGroup() throws Exception {
        // Initialize the database
        resourceActionGroupRepository.saveAndFlush(resourceActionGroup);

        // Get the resourceActionGroup
        restResourceActionGroupMockMvc.perform(get("/api/resource-action-groups/{id}", resourceActionGroup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(resourceActionGroup.getId().intValue()))
            .andExpect(jsonPath("$.resourceGroupName").value(DEFAULT_RESOURCE_GROUP_NAME));
    }
    @Test
    @Transactional
    public void getNonExistingResourceActionGroup() throws Exception {
        // Get the resourceActionGroup
        restResourceActionGroupMockMvc.perform(get("/api/resource-action-groups/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateResourceActionGroup() throws Exception {
        // Initialize the database
        resourceActionGroupRepository.saveAndFlush(resourceActionGroup);

        int databaseSizeBeforeUpdate = resourceActionGroupRepository.findAll().size();

        // Update the resourceActionGroup
        ResourceActionGroup updatedResourceActionGroup = resourceActionGroupRepository.findById(resourceActionGroup.getId()).get();
        // Disconnect from session so that the updates on updatedResourceActionGroup are not directly saved in db
        em.detach(updatedResourceActionGroup);
        updatedResourceActionGroup
            .resourceGroupName(UPDATED_RESOURCE_GROUP_NAME);
        ResourceActionGroupDTO resourceActionGroupDTO = resourceActionGroupMapper.toDto(updatedResourceActionGroup);

        restResourceActionGroupMockMvc.perform(put("/api/resource-action-groups")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(resourceActionGroupDTO)))
            .andExpect(status().isOk());

        // Validate the ResourceActionGroup in the database
        List<ResourceActionGroup> resourceActionGroupList = resourceActionGroupRepository.findAll();
        assertThat(resourceActionGroupList).hasSize(databaseSizeBeforeUpdate);
        ResourceActionGroup testResourceActionGroup = resourceActionGroupList.get(resourceActionGroupList.size() - 1);
        assertThat(testResourceActionGroup.getResourceGroupName()).isEqualTo(UPDATED_RESOURCE_GROUP_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingResourceActionGroup() throws Exception {
        int databaseSizeBeforeUpdate = resourceActionGroupRepository.findAll().size();

        // Create the ResourceActionGroup
        ResourceActionGroupDTO resourceActionGroupDTO = resourceActionGroupMapper.toDto(resourceActionGroup);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restResourceActionGroupMockMvc.perform(put("/api/resource-action-groups")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(resourceActionGroupDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ResourceActionGroup in the database
        List<ResourceActionGroup> resourceActionGroupList = resourceActionGroupRepository.findAll();
        assertThat(resourceActionGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteResourceActionGroup() throws Exception {
        // Initialize the database
        resourceActionGroupRepository.saveAndFlush(resourceActionGroup);

        int databaseSizeBeforeDelete = resourceActionGroupRepository.findAll().size();

        // Delete the resourceActionGroup
        restResourceActionGroupMockMvc.perform(delete("/api/resource-action-groups/{id}", resourceActionGroup.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ResourceActionGroup> resourceActionGroupList = resourceActionGroupRepository.findAll();
        assertThat(resourceActionGroupList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
