package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Trial1App;
import com.mycompany.myapp.domain.CompanyUser;
import com.mycompany.myapp.domain.CompanyGroup;
import com.mycompany.myapp.domain.CompanyUserGroup;
import com.mycompany.myapp.domain.CompanyEntityAccount;
import com.mycompany.myapp.repository.CompanyUserRepository;
import com.mycompany.myapp.service.CompanyUserService;
import com.mycompany.myapp.service.dto.CompanyUserDTO;
import com.mycompany.myapp.service.mapper.CompanyUserMapper;
import com.mycompany.myapp.service.dto.CompanyUserCriteria;
import com.mycompany.myapp.service.CompanyUserQueryService;

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

import com.mycompany.myapp.domain.enumeration.Language;
/**
 * Integration tests for the {@link CompanyUserResource} REST controller.
 */
@SpringBootTest(classes = Trial1App.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class CompanyUserResourceIT {

    private static final String DEFAULT_LEGAL_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LEGAL_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_NUMBER = "BBBBBBBBBB";

    private static final Language DEFAULT_PREFERED_LANGUAGE = Language.CHINESE;
    private static final Language UPDATED_PREFERED_LANGUAGE = Language.ENGLISH;

    @Autowired
    private CompanyUserRepository companyUserRepository;

    @Mock
    private CompanyUserRepository companyUserRepositoryMock;

    @Autowired
    private CompanyUserMapper companyUserMapper;

    @Mock
    private CompanyUserService companyUserServiceMock;

    @Autowired
    private CompanyUserService companyUserService;

    @Autowired
    private CompanyUserQueryService companyUserQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCompanyUserMockMvc;

    private CompanyUser companyUser;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CompanyUser createEntity(EntityManager em) {
        CompanyUser companyUser = new CompanyUser()
            .legalName(DEFAULT_LEGAL_NAME)
            .email(DEFAULT_EMAIL)
            .phoneNumber(DEFAULT_PHONE_NUMBER)
            .preferedLanguage(DEFAULT_PREFERED_LANGUAGE);
        // Add required entity
        CompanyGroup companyGroup;
        if (TestUtil.findAll(em, CompanyGroup.class).isEmpty()) {
            companyGroup = CompanyGroupResourceIT.createEntity(em);
            em.persist(companyGroup);
            em.flush();
        } else {
            companyGroup = TestUtil.findAll(em, CompanyGroup.class).get(0);
        }
        companyUser.setCompanyGroup(companyGroup);
        // Add required entity
        CompanyUserGroup companyUserGroup;
        if (TestUtil.findAll(em, CompanyUserGroup.class).isEmpty()) {
            companyUserGroup = CompanyUserGroupResourceIT.createEntity(em);
            em.persist(companyUserGroup);
            em.flush();
        } else {
            companyUserGroup = TestUtil.findAll(em, CompanyUserGroup.class).get(0);
        }
        companyUser.getAssignedCompanyUserGroups().add(companyUserGroup);
        // Add required entity
        CompanyEntityAccount companyEntityAccount;
        if (TestUtil.findAll(em, CompanyEntityAccount.class).isEmpty()) {
            companyEntityAccount = CompanyEntityAccountResourceIT.createEntity(em);
            em.persist(companyEntityAccount);
            em.flush();
        } else {
            companyEntityAccount = TestUtil.findAll(em, CompanyEntityAccount.class).get(0);
        }
        companyUser.getAssignedAccounts().add(companyEntityAccount);
        return companyUser;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CompanyUser createUpdatedEntity(EntityManager em) {
        CompanyUser companyUser = new CompanyUser()
            .legalName(UPDATED_LEGAL_NAME)
            .email(UPDATED_EMAIL)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .preferedLanguage(UPDATED_PREFERED_LANGUAGE);
        // Add required entity
        CompanyGroup companyGroup;
        if (TestUtil.findAll(em, CompanyGroup.class).isEmpty()) {
            companyGroup = CompanyGroupResourceIT.createUpdatedEntity(em);
            em.persist(companyGroup);
            em.flush();
        } else {
            companyGroup = TestUtil.findAll(em, CompanyGroup.class).get(0);
        }
        companyUser.setCompanyGroup(companyGroup);
        // Add required entity
        CompanyUserGroup companyUserGroup;
        if (TestUtil.findAll(em, CompanyUserGroup.class).isEmpty()) {
            companyUserGroup = CompanyUserGroupResourceIT.createUpdatedEntity(em);
            em.persist(companyUserGroup);
            em.flush();
        } else {
            companyUserGroup = TestUtil.findAll(em, CompanyUserGroup.class).get(0);
        }
        companyUser.getAssignedCompanyUserGroups().add(companyUserGroup);
        // Add required entity
        CompanyEntityAccount companyEntityAccount;
        if (TestUtil.findAll(em, CompanyEntityAccount.class).isEmpty()) {
            companyEntityAccount = CompanyEntityAccountResourceIT.createUpdatedEntity(em);
            em.persist(companyEntityAccount);
            em.flush();
        } else {
            companyEntityAccount = TestUtil.findAll(em, CompanyEntityAccount.class).get(0);
        }
        companyUser.getAssignedAccounts().add(companyEntityAccount);
        return companyUser;
    }

    @BeforeEach
    public void initTest() {
        companyUser = createEntity(em);
    }

    @Test
    @Transactional
    public void createCompanyUser() throws Exception {
        int databaseSizeBeforeCreate = companyUserRepository.findAll().size();
        // Create the CompanyUser
        CompanyUserDTO companyUserDTO = companyUserMapper.toDto(companyUser);
        restCompanyUserMockMvc.perform(post("/api/company-users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(companyUserDTO)))
            .andExpect(status().isCreated());

        // Validate the CompanyUser in the database
        List<CompanyUser> companyUserList = companyUserRepository.findAll();
        assertThat(companyUserList).hasSize(databaseSizeBeforeCreate + 1);
        CompanyUser testCompanyUser = companyUserList.get(companyUserList.size() - 1);
        assertThat(testCompanyUser.getLegalName()).isEqualTo(DEFAULT_LEGAL_NAME);
        assertThat(testCompanyUser.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testCompanyUser.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
        assertThat(testCompanyUser.getPreferedLanguage()).isEqualTo(DEFAULT_PREFERED_LANGUAGE);
    }

    @Test
    @Transactional
    public void createCompanyUserWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = companyUserRepository.findAll().size();

        // Create the CompanyUser with an existing ID
        companyUser.setId(1L);
        CompanyUserDTO companyUserDTO = companyUserMapper.toDto(companyUser);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCompanyUserMockMvc.perform(post("/api/company-users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(companyUserDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CompanyUser in the database
        List<CompanyUser> companyUserList = companyUserRepository.findAll();
        assertThat(companyUserList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkLegalNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = companyUserRepository.findAll().size();
        // set the field null
        companyUser.setLegalName(null);

        // Create the CompanyUser, which fails.
        CompanyUserDTO companyUserDTO = companyUserMapper.toDto(companyUser);


        restCompanyUserMockMvc.perform(post("/api/company-users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(companyUserDTO)))
            .andExpect(status().isBadRequest());

        List<CompanyUser> companyUserList = companyUserRepository.findAll();
        assertThat(companyUserList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = companyUserRepository.findAll().size();
        // set the field null
        companyUser.setEmail(null);

        // Create the CompanyUser, which fails.
        CompanyUserDTO companyUserDTO = companyUserMapper.toDto(companyUser);


        restCompanyUserMockMvc.perform(post("/api/company-users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(companyUserDTO)))
            .andExpect(status().isBadRequest());

        List<CompanyUser> companyUserList = companyUserRepository.findAll();
        assertThat(companyUserList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPhoneNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = companyUserRepository.findAll().size();
        // set the field null
        companyUser.setPhoneNumber(null);

        // Create the CompanyUser, which fails.
        CompanyUserDTO companyUserDTO = companyUserMapper.toDto(companyUser);


        restCompanyUserMockMvc.perform(post("/api/company-users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(companyUserDTO)))
            .andExpect(status().isBadRequest());

        List<CompanyUser> companyUserList = companyUserRepository.findAll();
        assertThat(companyUserList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPreferedLanguageIsRequired() throws Exception {
        int databaseSizeBeforeTest = companyUserRepository.findAll().size();
        // set the field null
        companyUser.setPreferedLanguage(null);

        // Create the CompanyUser, which fails.
        CompanyUserDTO companyUserDTO = companyUserMapper.toDto(companyUser);


        restCompanyUserMockMvc.perform(post("/api/company-users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(companyUserDTO)))
            .andExpect(status().isBadRequest());

        List<CompanyUser> companyUserList = companyUserRepository.findAll();
        assertThat(companyUserList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCompanyUsers() throws Exception {
        // Initialize the database
        companyUserRepository.saveAndFlush(companyUser);

        // Get all the companyUserList
        restCompanyUserMockMvc.perform(get("/api/company-users?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(companyUser.getId().intValue())))
            .andExpect(jsonPath("$.[*].legalName").value(hasItem(DEFAULT_LEGAL_NAME)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER)))
            .andExpect(jsonPath("$.[*].preferedLanguage").value(hasItem(DEFAULT_PREFERED_LANGUAGE.toString())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllCompanyUsersWithEagerRelationshipsIsEnabled() throws Exception {
        when(companyUserServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restCompanyUserMockMvc.perform(get("/api/company-users?eagerload=true"))
            .andExpect(status().isOk());

        verify(companyUserServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllCompanyUsersWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(companyUserServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restCompanyUserMockMvc.perform(get("/api/company-users?eagerload=true"))
            .andExpect(status().isOk());

        verify(companyUserServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getCompanyUser() throws Exception {
        // Initialize the database
        companyUserRepository.saveAndFlush(companyUser);

        // Get the companyUser
        restCompanyUserMockMvc.perform(get("/api/company-users/{id}", companyUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(companyUser.getId().intValue()))
            .andExpect(jsonPath("$.legalName").value(DEFAULT_LEGAL_NAME))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER))
            .andExpect(jsonPath("$.preferedLanguage").value(DEFAULT_PREFERED_LANGUAGE.toString()));
    }


    @Test
    @Transactional
    public void getCompanyUsersByIdFiltering() throws Exception {
        // Initialize the database
        companyUserRepository.saveAndFlush(companyUser);

        Long id = companyUser.getId();

        defaultCompanyUserShouldBeFound("id.equals=" + id);
        defaultCompanyUserShouldNotBeFound("id.notEquals=" + id);

        defaultCompanyUserShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCompanyUserShouldNotBeFound("id.greaterThan=" + id);

        defaultCompanyUserShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCompanyUserShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCompanyUsersByLegalNameIsEqualToSomething() throws Exception {
        // Initialize the database
        companyUserRepository.saveAndFlush(companyUser);

        // Get all the companyUserList where legalName equals to DEFAULT_LEGAL_NAME
        defaultCompanyUserShouldBeFound("legalName.equals=" + DEFAULT_LEGAL_NAME);

        // Get all the companyUserList where legalName equals to UPDATED_LEGAL_NAME
        defaultCompanyUserShouldNotBeFound("legalName.equals=" + UPDATED_LEGAL_NAME);
    }

    @Test
    @Transactional
    public void getAllCompanyUsersByLegalNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        companyUserRepository.saveAndFlush(companyUser);

        // Get all the companyUserList where legalName not equals to DEFAULT_LEGAL_NAME
        defaultCompanyUserShouldNotBeFound("legalName.notEquals=" + DEFAULT_LEGAL_NAME);

        // Get all the companyUserList where legalName not equals to UPDATED_LEGAL_NAME
        defaultCompanyUserShouldBeFound("legalName.notEquals=" + UPDATED_LEGAL_NAME);
    }

    @Test
    @Transactional
    public void getAllCompanyUsersByLegalNameIsInShouldWork() throws Exception {
        // Initialize the database
        companyUserRepository.saveAndFlush(companyUser);

        // Get all the companyUserList where legalName in DEFAULT_LEGAL_NAME or UPDATED_LEGAL_NAME
        defaultCompanyUserShouldBeFound("legalName.in=" + DEFAULT_LEGAL_NAME + "," + UPDATED_LEGAL_NAME);

        // Get all the companyUserList where legalName equals to UPDATED_LEGAL_NAME
        defaultCompanyUserShouldNotBeFound("legalName.in=" + UPDATED_LEGAL_NAME);
    }

    @Test
    @Transactional
    public void getAllCompanyUsersByLegalNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        companyUserRepository.saveAndFlush(companyUser);

        // Get all the companyUserList where legalName is not null
        defaultCompanyUserShouldBeFound("legalName.specified=true");

        // Get all the companyUserList where legalName is null
        defaultCompanyUserShouldNotBeFound("legalName.specified=false");
    }
                @Test
    @Transactional
    public void getAllCompanyUsersByLegalNameContainsSomething() throws Exception {
        // Initialize the database
        companyUserRepository.saveAndFlush(companyUser);

        // Get all the companyUserList where legalName contains DEFAULT_LEGAL_NAME
        defaultCompanyUserShouldBeFound("legalName.contains=" + DEFAULT_LEGAL_NAME);

        // Get all the companyUserList where legalName contains UPDATED_LEGAL_NAME
        defaultCompanyUserShouldNotBeFound("legalName.contains=" + UPDATED_LEGAL_NAME);
    }

    @Test
    @Transactional
    public void getAllCompanyUsersByLegalNameNotContainsSomething() throws Exception {
        // Initialize the database
        companyUserRepository.saveAndFlush(companyUser);

        // Get all the companyUserList where legalName does not contain DEFAULT_LEGAL_NAME
        defaultCompanyUserShouldNotBeFound("legalName.doesNotContain=" + DEFAULT_LEGAL_NAME);

        // Get all the companyUserList where legalName does not contain UPDATED_LEGAL_NAME
        defaultCompanyUserShouldBeFound("legalName.doesNotContain=" + UPDATED_LEGAL_NAME);
    }


    @Test
    @Transactional
    public void getAllCompanyUsersByEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        companyUserRepository.saveAndFlush(companyUser);

        // Get all the companyUserList where email equals to DEFAULT_EMAIL
        defaultCompanyUserShouldBeFound("email.equals=" + DEFAULT_EMAIL);

        // Get all the companyUserList where email equals to UPDATED_EMAIL
        defaultCompanyUserShouldNotBeFound("email.equals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllCompanyUsersByEmailIsNotEqualToSomething() throws Exception {
        // Initialize the database
        companyUserRepository.saveAndFlush(companyUser);

        // Get all the companyUserList where email not equals to DEFAULT_EMAIL
        defaultCompanyUserShouldNotBeFound("email.notEquals=" + DEFAULT_EMAIL);

        // Get all the companyUserList where email not equals to UPDATED_EMAIL
        defaultCompanyUserShouldBeFound("email.notEquals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllCompanyUsersByEmailIsInShouldWork() throws Exception {
        // Initialize the database
        companyUserRepository.saveAndFlush(companyUser);

        // Get all the companyUserList where email in DEFAULT_EMAIL or UPDATED_EMAIL
        defaultCompanyUserShouldBeFound("email.in=" + DEFAULT_EMAIL + "," + UPDATED_EMAIL);

        // Get all the companyUserList where email equals to UPDATED_EMAIL
        defaultCompanyUserShouldNotBeFound("email.in=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllCompanyUsersByEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        companyUserRepository.saveAndFlush(companyUser);

        // Get all the companyUserList where email is not null
        defaultCompanyUserShouldBeFound("email.specified=true");

        // Get all the companyUserList where email is null
        defaultCompanyUserShouldNotBeFound("email.specified=false");
    }
                @Test
    @Transactional
    public void getAllCompanyUsersByEmailContainsSomething() throws Exception {
        // Initialize the database
        companyUserRepository.saveAndFlush(companyUser);

        // Get all the companyUserList where email contains DEFAULT_EMAIL
        defaultCompanyUserShouldBeFound("email.contains=" + DEFAULT_EMAIL);

        // Get all the companyUserList where email contains UPDATED_EMAIL
        defaultCompanyUserShouldNotBeFound("email.contains=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllCompanyUsersByEmailNotContainsSomething() throws Exception {
        // Initialize the database
        companyUserRepository.saveAndFlush(companyUser);

        // Get all the companyUserList where email does not contain DEFAULT_EMAIL
        defaultCompanyUserShouldNotBeFound("email.doesNotContain=" + DEFAULT_EMAIL);

        // Get all the companyUserList where email does not contain UPDATED_EMAIL
        defaultCompanyUserShouldBeFound("email.doesNotContain=" + UPDATED_EMAIL);
    }


    @Test
    @Transactional
    public void getAllCompanyUsersByPhoneNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        companyUserRepository.saveAndFlush(companyUser);

        // Get all the companyUserList where phoneNumber equals to DEFAULT_PHONE_NUMBER
        defaultCompanyUserShouldBeFound("phoneNumber.equals=" + DEFAULT_PHONE_NUMBER);

        // Get all the companyUserList where phoneNumber equals to UPDATED_PHONE_NUMBER
        defaultCompanyUserShouldNotBeFound("phoneNumber.equals=" + UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    public void getAllCompanyUsersByPhoneNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        companyUserRepository.saveAndFlush(companyUser);

        // Get all the companyUserList where phoneNumber not equals to DEFAULT_PHONE_NUMBER
        defaultCompanyUserShouldNotBeFound("phoneNumber.notEquals=" + DEFAULT_PHONE_NUMBER);

        // Get all the companyUserList where phoneNumber not equals to UPDATED_PHONE_NUMBER
        defaultCompanyUserShouldBeFound("phoneNumber.notEquals=" + UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    public void getAllCompanyUsersByPhoneNumberIsInShouldWork() throws Exception {
        // Initialize the database
        companyUserRepository.saveAndFlush(companyUser);

        // Get all the companyUserList where phoneNumber in DEFAULT_PHONE_NUMBER or UPDATED_PHONE_NUMBER
        defaultCompanyUserShouldBeFound("phoneNumber.in=" + DEFAULT_PHONE_NUMBER + "," + UPDATED_PHONE_NUMBER);

        // Get all the companyUserList where phoneNumber equals to UPDATED_PHONE_NUMBER
        defaultCompanyUserShouldNotBeFound("phoneNumber.in=" + UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    public void getAllCompanyUsersByPhoneNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        companyUserRepository.saveAndFlush(companyUser);

        // Get all the companyUserList where phoneNumber is not null
        defaultCompanyUserShouldBeFound("phoneNumber.specified=true");

        // Get all the companyUserList where phoneNumber is null
        defaultCompanyUserShouldNotBeFound("phoneNumber.specified=false");
    }
                @Test
    @Transactional
    public void getAllCompanyUsersByPhoneNumberContainsSomething() throws Exception {
        // Initialize the database
        companyUserRepository.saveAndFlush(companyUser);

        // Get all the companyUserList where phoneNumber contains DEFAULT_PHONE_NUMBER
        defaultCompanyUserShouldBeFound("phoneNumber.contains=" + DEFAULT_PHONE_NUMBER);

        // Get all the companyUserList where phoneNumber contains UPDATED_PHONE_NUMBER
        defaultCompanyUserShouldNotBeFound("phoneNumber.contains=" + UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    public void getAllCompanyUsersByPhoneNumberNotContainsSomething() throws Exception {
        // Initialize the database
        companyUserRepository.saveAndFlush(companyUser);

        // Get all the companyUserList where phoneNumber does not contain DEFAULT_PHONE_NUMBER
        defaultCompanyUserShouldNotBeFound("phoneNumber.doesNotContain=" + DEFAULT_PHONE_NUMBER);

        // Get all the companyUserList where phoneNumber does not contain UPDATED_PHONE_NUMBER
        defaultCompanyUserShouldBeFound("phoneNumber.doesNotContain=" + UPDATED_PHONE_NUMBER);
    }


    @Test
    @Transactional
    public void getAllCompanyUsersByPreferedLanguageIsEqualToSomething() throws Exception {
        // Initialize the database
        companyUserRepository.saveAndFlush(companyUser);

        // Get all the companyUserList where preferedLanguage equals to DEFAULT_PREFERED_LANGUAGE
        defaultCompanyUserShouldBeFound("preferedLanguage.equals=" + DEFAULT_PREFERED_LANGUAGE);

        // Get all the companyUserList where preferedLanguage equals to UPDATED_PREFERED_LANGUAGE
        defaultCompanyUserShouldNotBeFound("preferedLanguage.equals=" + UPDATED_PREFERED_LANGUAGE);
    }

    @Test
    @Transactional
    public void getAllCompanyUsersByPreferedLanguageIsNotEqualToSomething() throws Exception {
        // Initialize the database
        companyUserRepository.saveAndFlush(companyUser);

        // Get all the companyUserList where preferedLanguage not equals to DEFAULT_PREFERED_LANGUAGE
        defaultCompanyUserShouldNotBeFound("preferedLanguage.notEquals=" + DEFAULT_PREFERED_LANGUAGE);

        // Get all the companyUserList where preferedLanguage not equals to UPDATED_PREFERED_LANGUAGE
        defaultCompanyUserShouldBeFound("preferedLanguage.notEquals=" + UPDATED_PREFERED_LANGUAGE);
    }

    @Test
    @Transactional
    public void getAllCompanyUsersByPreferedLanguageIsInShouldWork() throws Exception {
        // Initialize the database
        companyUserRepository.saveAndFlush(companyUser);

        // Get all the companyUserList where preferedLanguage in DEFAULT_PREFERED_LANGUAGE or UPDATED_PREFERED_LANGUAGE
        defaultCompanyUserShouldBeFound("preferedLanguage.in=" + DEFAULT_PREFERED_LANGUAGE + "," + UPDATED_PREFERED_LANGUAGE);

        // Get all the companyUserList where preferedLanguage equals to UPDATED_PREFERED_LANGUAGE
        defaultCompanyUserShouldNotBeFound("preferedLanguage.in=" + UPDATED_PREFERED_LANGUAGE);
    }

    @Test
    @Transactional
    public void getAllCompanyUsersByPreferedLanguageIsNullOrNotNull() throws Exception {
        // Initialize the database
        companyUserRepository.saveAndFlush(companyUser);

        // Get all the companyUserList where preferedLanguage is not null
        defaultCompanyUserShouldBeFound("preferedLanguage.specified=true");

        // Get all the companyUserList where preferedLanguage is null
        defaultCompanyUserShouldNotBeFound("preferedLanguage.specified=false");
    }

    @Test
    @Transactional
    public void getAllCompanyUsersByCompanyGroupIsEqualToSomething() throws Exception {
        // Get already existing entity
        CompanyGroup companyGroup = companyUser.getCompanyGroup();
        companyUserRepository.saveAndFlush(companyUser);
        Long companyGroupId = companyGroup.getId();

        // Get all the companyUserList where companyGroup equals to companyGroupId
        defaultCompanyUserShouldBeFound("companyGroupId.equals=" + companyGroupId);

        // Get all the companyUserList where companyGroup equals to companyGroupId + 1
        defaultCompanyUserShouldNotBeFound("companyGroupId.equals=" + (companyGroupId + 1));
    }


    @Test
    @Transactional
    public void getAllCompanyUsersByAssignedCompanyUserGroupsIsEqualToSomething() throws Exception {
        // Get already existing entity
        CompanyUserGroup assignedCompanyUserGroups = companyUser.getAssignedCompanyUserGroups();
        companyUserRepository.saveAndFlush(companyUser);
        Long assignedCompanyUserGroupsId = assignedCompanyUserGroups.getId();

        // Get all the companyUserList where assignedCompanyUserGroups equals to assignedCompanyUserGroupsId
        defaultCompanyUserShouldBeFound("assignedCompanyUserGroupsId.equals=" + assignedCompanyUserGroupsId);

        // Get all the companyUserList where assignedCompanyUserGroups equals to assignedCompanyUserGroupsId + 1
        defaultCompanyUserShouldNotBeFound("assignedCompanyUserGroupsId.equals=" + (assignedCompanyUserGroupsId + 1));
    }


    @Test
    @Transactional
    public void getAllCompanyUsersByAssignedAccountsIsEqualToSomething() throws Exception {
        // Get already existing entity
        CompanyEntityAccount assignedAccounts = companyUser.getAssignedAccounts();
        companyUserRepository.saveAndFlush(companyUser);
        Long assignedAccountsId = assignedAccounts.getId();

        // Get all the companyUserList where assignedAccounts equals to assignedAccountsId
        defaultCompanyUserShouldBeFound("assignedAccountsId.equals=" + assignedAccountsId);

        // Get all the companyUserList where assignedAccounts equals to assignedAccountsId + 1
        defaultCompanyUserShouldNotBeFound("assignedAccountsId.equals=" + (assignedAccountsId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCompanyUserShouldBeFound(String filter) throws Exception {
        restCompanyUserMockMvc.perform(get("/api/company-users?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(companyUser.getId().intValue())))
            .andExpect(jsonPath("$.[*].legalName").value(hasItem(DEFAULT_LEGAL_NAME)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER)))
            .andExpect(jsonPath("$.[*].preferedLanguage").value(hasItem(DEFAULT_PREFERED_LANGUAGE.toString())));

        // Check, that the count call also returns 1
        restCompanyUserMockMvc.perform(get("/api/company-users/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCompanyUserShouldNotBeFound(String filter) throws Exception {
        restCompanyUserMockMvc.perform(get("/api/company-users?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCompanyUserMockMvc.perform(get("/api/company-users/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingCompanyUser() throws Exception {
        // Get the companyUser
        restCompanyUserMockMvc.perform(get("/api/company-users/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCompanyUser() throws Exception {
        // Initialize the database
        companyUserRepository.saveAndFlush(companyUser);

        int databaseSizeBeforeUpdate = companyUserRepository.findAll().size();

        // Update the companyUser
        CompanyUser updatedCompanyUser = companyUserRepository.findById(companyUser.getId()).get();
        // Disconnect from session so that the updates on updatedCompanyUser are not directly saved in db
        em.detach(updatedCompanyUser);
        updatedCompanyUser
            .legalName(UPDATED_LEGAL_NAME)
            .email(UPDATED_EMAIL)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .preferedLanguage(UPDATED_PREFERED_LANGUAGE);
        CompanyUserDTO companyUserDTO = companyUserMapper.toDto(updatedCompanyUser);

        restCompanyUserMockMvc.perform(put("/api/company-users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(companyUserDTO)))
            .andExpect(status().isOk());

        // Validate the CompanyUser in the database
        List<CompanyUser> companyUserList = companyUserRepository.findAll();
        assertThat(companyUserList).hasSize(databaseSizeBeforeUpdate);
        CompanyUser testCompanyUser = companyUserList.get(companyUserList.size() - 1);
        assertThat(testCompanyUser.getLegalName()).isEqualTo(UPDATED_LEGAL_NAME);
        assertThat(testCompanyUser.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testCompanyUser.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testCompanyUser.getPreferedLanguage()).isEqualTo(UPDATED_PREFERED_LANGUAGE);
    }

    @Test
    @Transactional
    public void updateNonExistingCompanyUser() throws Exception {
        int databaseSizeBeforeUpdate = companyUserRepository.findAll().size();

        // Create the CompanyUser
        CompanyUserDTO companyUserDTO = companyUserMapper.toDto(companyUser);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompanyUserMockMvc.perform(put("/api/company-users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(companyUserDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CompanyUser in the database
        List<CompanyUser> companyUserList = companyUserRepository.findAll();
        assertThat(companyUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCompanyUser() throws Exception {
        // Initialize the database
        companyUserRepository.saveAndFlush(companyUser);

        int databaseSizeBeforeDelete = companyUserRepository.findAll().size();

        // Delete the companyUser
        restCompanyUserMockMvc.perform(delete("/api/company-users/{id}", companyUser.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CompanyUser> companyUserList = companyUserRepository.findAll();
        assertThat(companyUserList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
