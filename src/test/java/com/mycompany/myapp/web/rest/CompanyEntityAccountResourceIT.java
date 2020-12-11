package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Trial1App;
import com.mycompany.myapp.domain.CompanyEntityAccount;
import com.mycompany.myapp.domain.CompanyEntity;
import com.mycompany.myapp.repository.CompanyEntityAccountRepository;
import com.mycompany.myapp.service.CompanyEntityAccountService;
import com.mycompany.myapp.service.dto.CompanyEntityAccountDTO;
import com.mycompany.myapp.service.mapper.CompanyEntityAccountMapper;

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
 * Integration tests for the {@link CompanyEntityAccountResource} REST controller.
 */
@SpringBootTest(classes = Trial1App.class)
@AutoConfigureMockMvc
@WithMockUser
public class CompanyEntityAccountResourceIT {

    private static final String DEFAULT_NICK_NAME = "AAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_NICK_NAME = "BBBBBBBBBBBBBBBBBBBB";

    private static final String DEFAULT_ACCOUNT_NO = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT_NO = "BBBBBBBBBB";

    @Autowired
    private CompanyEntityAccountRepository companyEntityAccountRepository;

    @Autowired
    private CompanyEntityAccountMapper companyEntityAccountMapper;

    @Autowired
    private CompanyEntityAccountService companyEntityAccountService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCompanyEntityAccountMockMvc;

    private CompanyEntityAccount companyEntityAccount;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CompanyEntityAccount createEntity(EntityManager em) {
        CompanyEntityAccount companyEntityAccount = new CompanyEntityAccount()
            .nickName(DEFAULT_NICK_NAME)
            .accountNo(DEFAULT_ACCOUNT_NO);
        // Add required entity
        CompanyEntity companyEntity;
        if (TestUtil.findAll(em, CompanyEntity.class).isEmpty()) {
            companyEntity = CompanyEntityResourceIT.createEntity(em);
            em.persist(companyEntity);
            em.flush();
        } else {
            companyEntity = TestUtil.findAll(em, CompanyEntity.class).get(0);
        }
        companyEntityAccount.setOwnerEntity(companyEntity);
        return companyEntityAccount;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CompanyEntityAccount createUpdatedEntity(EntityManager em) {
        CompanyEntityAccount companyEntityAccount = new CompanyEntityAccount()
            .nickName(UPDATED_NICK_NAME)
            .accountNo(UPDATED_ACCOUNT_NO);
        // Add required entity
        CompanyEntity companyEntity;
        if (TestUtil.findAll(em, CompanyEntity.class).isEmpty()) {
            companyEntity = CompanyEntityResourceIT.createUpdatedEntity(em);
            em.persist(companyEntity);
            em.flush();
        } else {
            companyEntity = TestUtil.findAll(em, CompanyEntity.class).get(0);
        }
        companyEntityAccount.setOwnerEntity(companyEntity);
        return companyEntityAccount;
    }

    @BeforeEach
    public void initTest() {
        companyEntityAccount = createEntity(em);
    }

    @Test
    @Transactional
    public void createCompanyEntityAccount() throws Exception {
        int databaseSizeBeforeCreate = companyEntityAccountRepository.findAll().size();
        // Create the CompanyEntityAccount
        CompanyEntityAccountDTO companyEntityAccountDTO = companyEntityAccountMapper.toDto(companyEntityAccount);
        restCompanyEntityAccountMockMvc.perform(post("/api/company-entity-accounts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(companyEntityAccountDTO)))
            .andExpect(status().isCreated());

        // Validate the CompanyEntityAccount in the database
        List<CompanyEntityAccount> companyEntityAccountList = companyEntityAccountRepository.findAll();
        assertThat(companyEntityAccountList).hasSize(databaseSizeBeforeCreate + 1);
        CompanyEntityAccount testCompanyEntityAccount = companyEntityAccountList.get(companyEntityAccountList.size() - 1);
        assertThat(testCompanyEntityAccount.getNickName()).isEqualTo(DEFAULT_NICK_NAME);
        assertThat(testCompanyEntityAccount.getAccountNo()).isEqualTo(DEFAULT_ACCOUNT_NO);
    }

    @Test
    @Transactional
    public void createCompanyEntityAccountWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = companyEntityAccountRepository.findAll().size();

        // Create the CompanyEntityAccount with an existing ID
        companyEntityAccount.setId(1L);
        CompanyEntityAccountDTO companyEntityAccountDTO = companyEntityAccountMapper.toDto(companyEntityAccount);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCompanyEntityAccountMockMvc.perform(post("/api/company-entity-accounts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(companyEntityAccountDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CompanyEntityAccount in the database
        List<CompanyEntityAccount> companyEntityAccountList = companyEntityAccountRepository.findAll();
        assertThat(companyEntityAccountList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNickNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = companyEntityAccountRepository.findAll().size();
        // set the field null
        companyEntityAccount.setNickName(null);

        // Create the CompanyEntityAccount, which fails.
        CompanyEntityAccountDTO companyEntityAccountDTO = companyEntityAccountMapper.toDto(companyEntityAccount);


        restCompanyEntityAccountMockMvc.perform(post("/api/company-entity-accounts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(companyEntityAccountDTO)))
            .andExpect(status().isBadRequest());

        List<CompanyEntityAccount> companyEntityAccountList = companyEntityAccountRepository.findAll();
        assertThat(companyEntityAccountList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAccountNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = companyEntityAccountRepository.findAll().size();
        // set the field null
        companyEntityAccount.setAccountNo(null);

        // Create the CompanyEntityAccount, which fails.
        CompanyEntityAccountDTO companyEntityAccountDTO = companyEntityAccountMapper.toDto(companyEntityAccount);


        restCompanyEntityAccountMockMvc.perform(post("/api/company-entity-accounts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(companyEntityAccountDTO)))
            .andExpect(status().isBadRequest());

        List<CompanyEntityAccount> companyEntityAccountList = companyEntityAccountRepository.findAll();
        assertThat(companyEntityAccountList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCompanyEntityAccounts() throws Exception {
        // Initialize the database
        companyEntityAccountRepository.saveAndFlush(companyEntityAccount);

        // Get all the companyEntityAccountList
        restCompanyEntityAccountMockMvc.perform(get("/api/company-entity-accounts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(companyEntityAccount.getId().intValue())))
            .andExpect(jsonPath("$.[*].nickName").value(hasItem(DEFAULT_NICK_NAME)))
            .andExpect(jsonPath("$.[*].accountNo").value(hasItem(DEFAULT_ACCOUNT_NO)));
    }
    
    @Test
    @Transactional
    public void getCompanyEntityAccount() throws Exception {
        // Initialize the database
        companyEntityAccountRepository.saveAndFlush(companyEntityAccount);

        // Get the companyEntityAccount
        restCompanyEntityAccountMockMvc.perform(get("/api/company-entity-accounts/{id}", companyEntityAccount.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(companyEntityAccount.getId().intValue()))
            .andExpect(jsonPath("$.nickName").value(DEFAULT_NICK_NAME))
            .andExpect(jsonPath("$.accountNo").value(DEFAULT_ACCOUNT_NO));
    }
    @Test
    @Transactional
    public void getNonExistingCompanyEntityAccount() throws Exception {
        // Get the companyEntityAccount
        restCompanyEntityAccountMockMvc.perform(get("/api/company-entity-accounts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCompanyEntityAccount() throws Exception {
        // Initialize the database
        companyEntityAccountRepository.saveAndFlush(companyEntityAccount);

        int databaseSizeBeforeUpdate = companyEntityAccountRepository.findAll().size();

        // Update the companyEntityAccount
        CompanyEntityAccount updatedCompanyEntityAccount = companyEntityAccountRepository.findById(companyEntityAccount.getId()).get();
        // Disconnect from session so that the updates on updatedCompanyEntityAccount are not directly saved in db
        em.detach(updatedCompanyEntityAccount);
        updatedCompanyEntityAccount
            .nickName(UPDATED_NICK_NAME)
            .accountNo(UPDATED_ACCOUNT_NO);
        CompanyEntityAccountDTO companyEntityAccountDTO = companyEntityAccountMapper.toDto(updatedCompanyEntityAccount);

        restCompanyEntityAccountMockMvc.perform(put("/api/company-entity-accounts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(companyEntityAccountDTO)))
            .andExpect(status().isOk());

        // Validate the CompanyEntityAccount in the database
        List<CompanyEntityAccount> companyEntityAccountList = companyEntityAccountRepository.findAll();
        assertThat(companyEntityAccountList).hasSize(databaseSizeBeforeUpdate);
        CompanyEntityAccount testCompanyEntityAccount = companyEntityAccountList.get(companyEntityAccountList.size() - 1);
        assertThat(testCompanyEntityAccount.getNickName()).isEqualTo(UPDATED_NICK_NAME);
        assertThat(testCompanyEntityAccount.getAccountNo()).isEqualTo(UPDATED_ACCOUNT_NO);
    }

    @Test
    @Transactional
    public void updateNonExistingCompanyEntityAccount() throws Exception {
        int databaseSizeBeforeUpdate = companyEntityAccountRepository.findAll().size();

        // Create the CompanyEntityAccount
        CompanyEntityAccountDTO companyEntityAccountDTO = companyEntityAccountMapper.toDto(companyEntityAccount);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompanyEntityAccountMockMvc.perform(put("/api/company-entity-accounts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(companyEntityAccountDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CompanyEntityAccount in the database
        List<CompanyEntityAccount> companyEntityAccountList = companyEntityAccountRepository.findAll();
        assertThat(companyEntityAccountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCompanyEntityAccount() throws Exception {
        // Initialize the database
        companyEntityAccountRepository.saveAndFlush(companyEntityAccount);

        int databaseSizeBeforeDelete = companyEntityAccountRepository.findAll().size();

        // Delete the companyEntityAccount
        restCompanyEntityAccountMockMvc.perform(delete("/api/company-entity-accounts/{id}", companyEntityAccount.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CompanyEntityAccount> companyEntityAccountList = companyEntityAccountRepository.findAll();
        assertThat(companyEntityAccountList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
