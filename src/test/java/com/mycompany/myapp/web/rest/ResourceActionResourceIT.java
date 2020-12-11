package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Trial1App;
import com.mycompany.myapp.domain.ResourceAction;
import com.mycompany.myapp.domain.Resources;
import com.mycompany.myapp.repository.ResourceActionRepository;
import com.mycompany.myapp.service.ResourceActionService;
import com.mycompany.myapp.service.dto.ResourceActionDTO;
import com.mycompany.myapp.service.mapper.ResourceActionMapper;

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

import com.mycompany.myapp.domain.enumeration.Action;
/**
 * Integration tests for the {@link ResourceActionResource} REST controller.
 */
@SpringBootTest(classes = Trial1App.class)
@AutoConfigureMockMvc
@WithMockUser
public class ResourceActionResourceIT {

    private static final Action DEFAULT_ACTION = Action.SAVE;
    private static final Action UPDATED_ACTION = Action.SUBMIT;

    private static final String DEFAULT_ACTION_DESCIPTION = "AAAAAAAAAA";
    private static final String UPDATED_ACTION_DESCIPTION = "BBBBBBBBBB";

    @Autowired
    private ResourceActionRepository resourceActionRepository;

    @Autowired
    private ResourceActionMapper resourceActionMapper;

    @Autowired
    private ResourceActionService resourceActionService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restResourceActionMockMvc;

    private ResourceAction resourceAction;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ResourceAction createEntity(EntityManager em) {
        ResourceAction resourceAction = new ResourceAction()
            .action(DEFAULT_ACTION)
            .actionDesciption(DEFAULT_ACTION_DESCIPTION);
        // Add required entity
        Resources resources;
        if (TestUtil.findAll(em, Resources.class).isEmpty()) {
            resources = ResourcesResourceIT.createEntity(em);
            em.persist(resources);
            em.flush();
        } else {
            resources = TestUtil.findAll(em, Resources.class).get(0);
        }
        resourceAction.setResourceGroupName(resources);
        return resourceAction;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ResourceAction createUpdatedEntity(EntityManager em) {
        ResourceAction resourceAction = new ResourceAction()
            .action(UPDATED_ACTION)
            .actionDesciption(UPDATED_ACTION_DESCIPTION);
        // Add required entity
        Resources resources;
        if (TestUtil.findAll(em, Resources.class).isEmpty()) {
            resources = ResourcesResourceIT.createUpdatedEntity(em);
            em.persist(resources);
            em.flush();
        } else {
            resources = TestUtil.findAll(em, Resources.class).get(0);
        }
        resourceAction.setResourceGroupName(resources);
        return resourceAction;
    }

    @BeforeEach
    public void initTest() {
        resourceAction = createEntity(em);
    }

    @Test
    @Transactional
    public void createResourceAction() throws Exception {
        int databaseSizeBeforeCreate = resourceActionRepository.findAll().size();
        // Create the ResourceAction
        ResourceActionDTO resourceActionDTO = resourceActionMapper.toDto(resourceAction);
        restResourceActionMockMvc.perform(post("/api/resource-actions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(resourceActionDTO)))
            .andExpect(status().isCreated());

        // Validate the ResourceAction in the database
        List<ResourceAction> resourceActionList = resourceActionRepository.findAll();
        assertThat(resourceActionList).hasSize(databaseSizeBeforeCreate + 1);
        ResourceAction testResourceAction = resourceActionList.get(resourceActionList.size() - 1);
        assertThat(testResourceAction.getAction()).isEqualTo(DEFAULT_ACTION);
        assertThat(testResourceAction.getActionDesciption()).isEqualTo(DEFAULT_ACTION_DESCIPTION);
    }

    @Test
    @Transactional
    public void createResourceActionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = resourceActionRepository.findAll().size();

        // Create the ResourceAction with an existing ID
        resourceAction.setId(1L);
        ResourceActionDTO resourceActionDTO = resourceActionMapper.toDto(resourceAction);

        // An entity with an existing ID cannot be created, so this API call must fail
        restResourceActionMockMvc.perform(post("/api/resource-actions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(resourceActionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ResourceAction in the database
        List<ResourceAction> resourceActionList = resourceActionRepository.findAll();
        assertThat(resourceActionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkActionIsRequired() throws Exception {
        int databaseSizeBeforeTest = resourceActionRepository.findAll().size();
        // set the field null
        resourceAction.setAction(null);

        // Create the ResourceAction, which fails.
        ResourceActionDTO resourceActionDTO = resourceActionMapper.toDto(resourceAction);


        restResourceActionMockMvc.perform(post("/api/resource-actions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(resourceActionDTO)))
            .andExpect(status().isBadRequest());

        List<ResourceAction> resourceActionList = resourceActionRepository.findAll();
        assertThat(resourceActionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActionDesciptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = resourceActionRepository.findAll().size();
        // set the field null
        resourceAction.setActionDesciption(null);

        // Create the ResourceAction, which fails.
        ResourceActionDTO resourceActionDTO = resourceActionMapper.toDto(resourceAction);


        restResourceActionMockMvc.perform(post("/api/resource-actions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(resourceActionDTO)))
            .andExpect(status().isBadRequest());

        List<ResourceAction> resourceActionList = resourceActionRepository.findAll();
        assertThat(resourceActionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllResourceActions() throws Exception {
        // Initialize the database
        resourceActionRepository.saveAndFlush(resourceAction);

        // Get all the resourceActionList
        restResourceActionMockMvc.perform(get("/api/resource-actions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(resourceAction.getId().intValue())))
            .andExpect(jsonPath("$.[*].action").value(hasItem(DEFAULT_ACTION.toString())))
            .andExpect(jsonPath("$.[*].actionDesciption").value(hasItem(DEFAULT_ACTION_DESCIPTION)));
    }
    
    @Test
    @Transactional
    public void getResourceAction() throws Exception {
        // Initialize the database
        resourceActionRepository.saveAndFlush(resourceAction);

        // Get the resourceAction
        restResourceActionMockMvc.perform(get("/api/resource-actions/{id}", resourceAction.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(resourceAction.getId().intValue()))
            .andExpect(jsonPath("$.action").value(DEFAULT_ACTION.toString()))
            .andExpect(jsonPath("$.actionDesciption").value(DEFAULT_ACTION_DESCIPTION));
    }
    @Test
    @Transactional
    public void getNonExistingResourceAction() throws Exception {
        // Get the resourceAction
        restResourceActionMockMvc.perform(get("/api/resource-actions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateResourceAction() throws Exception {
        // Initialize the database
        resourceActionRepository.saveAndFlush(resourceAction);

        int databaseSizeBeforeUpdate = resourceActionRepository.findAll().size();

        // Update the resourceAction
        ResourceAction updatedResourceAction = resourceActionRepository.findById(resourceAction.getId()).get();
        // Disconnect from session so that the updates on updatedResourceAction are not directly saved in db
        em.detach(updatedResourceAction);
        updatedResourceAction
            .action(UPDATED_ACTION)
            .actionDesciption(UPDATED_ACTION_DESCIPTION);
        ResourceActionDTO resourceActionDTO = resourceActionMapper.toDto(updatedResourceAction);

        restResourceActionMockMvc.perform(put("/api/resource-actions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(resourceActionDTO)))
            .andExpect(status().isOk());

        // Validate the ResourceAction in the database
        List<ResourceAction> resourceActionList = resourceActionRepository.findAll();
        assertThat(resourceActionList).hasSize(databaseSizeBeforeUpdate);
        ResourceAction testResourceAction = resourceActionList.get(resourceActionList.size() - 1);
        assertThat(testResourceAction.getAction()).isEqualTo(UPDATED_ACTION);
        assertThat(testResourceAction.getActionDesciption()).isEqualTo(UPDATED_ACTION_DESCIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingResourceAction() throws Exception {
        int databaseSizeBeforeUpdate = resourceActionRepository.findAll().size();

        // Create the ResourceAction
        ResourceActionDTO resourceActionDTO = resourceActionMapper.toDto(resourceAction);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restResourceActionMockMvc.perform(put("/api/resource-actions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(resourceActionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ResourceAction in the database
        List<ResourceAction> resourceActionList = resourceActionRepository.findAll();
        assertThat(resourceActionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteResourceAction() throws Exception {
        // Initialize the database
        resourceActionRepository.saveAndFlush(resourceAction);

        int databaseSizeBeforeDelete = resourceActionRepository.findAll().size();

        // Delete the resourceAction
        restResourceActionMockMvc.perform(delete("/api/resource-actions/{id}", resourceAction.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ResourceAction> resourceActionList = resourceActionRepository.findAll();
        assertThat(resourceActionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
