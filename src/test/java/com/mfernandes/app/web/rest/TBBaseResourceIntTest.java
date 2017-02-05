package com.mfernandes.app.web.rest;

import com.mfernandes.app.Projeto1App;

import com.mfernandes.app.domain.TBBase;
import com.mfernandes.app.repository.TBBaseRepository;
import com.mfernandes.app.repository.search.TBBaseSearchRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the TBBaseResource REST controller.
 *
 * @see TBBaseResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Projeto1App.class)
public class TBBaseResourceIntTest {

    private static final String DEFAULT_IDBASE = "AAAAAAAAAA";
    private static final String UPDATED_IDBASE = "BBBBBBBBBB";

    private static final Long DEFAULT_IDUSUARIO = 1L;
    private static final Long UPDATED_IDUSUARIO = 2L;

    private static final String DEFAULT_URL = "AAAAAAAAAA";
    private static final String UPDATED_URL = "BBBBBBBBBB";

    @Autowired
    private TBBaseRepository tBBaseRepository;

    @Autowired
    private TBBaseSearchRepository tBBaseSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private EntityManager em;

    private MockMvc restTBBaseMockMvc;

    private TBBase tBBase;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
            TBBaseResource tBBaseResource = new TBBaseResource(tBBaseRepository, tBBaseSearchRepository);
        this.restTBBaseMockMvc = MockMvcBuilders.standaloneSetup(tBBaseResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TBBase createEntity(EntityManager em) {
        TBBase tBBase = new TBBase()
                .idbase(DEFAULT_IDBASE)
                .idusuario(DEFAULT_IDUSUARIO)
                .url(DEFAULT_URL);
        return tBBase;
    }

    @Before
    public void initTest() {
        tBBaseSearchRepository.deleteAll();
        tBBase = createEntity(em);
    }

    @Test
    @Transactional
    public void createTBBase() throws Exception {
        int databaseSizeBeforeCreate = tBBaseRepository.findAll().size();

        // Create the TBBase

        restTBBaseMockMvc.perform(post("/api/t-b-bases")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tBBase)))
            .andExpect(status().isCreated());

        // Validate the TBBase in the database
        List<TBBase> tBBaseList = tBBaseRepository.findAll();
        assertThat(tBBaseList).hasSize(databaseSizeBeforeCreate + 1);
        TBBase testTBBase = tBBaseList.get(tBBaseList.size() - 1);
        assertThat(testTBBase.getIdbase()).isEqualTo(DEFAULT_IDBASE);
        assertThat(testTBBase.getIdusuario()).isEqualTo(DEFAULT_IDUSUARIO);
        assertThat(testTBBase.getUrl()).isEqualTo(DEFAULT_URL);

        // Validate the TBBase in Elasticsearch
        TBBase tBBaseEs = tBBaseSearchRepository.findOne(testTBBase.getId());
        assertThat(tBBaseEs).isEqualToComparingFieldByField(testTBBase);
    }

    @Test
    @Transactional
    public void createTBBaseWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tBBaseRepository.findAll().size();

        // Create the TBBase with an existing ID
        TBBase existingTBBase = new TBBase();
        existingTBBase.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTBBaseMockMvc.perform(post("/api/t-b-bases")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingTBBase)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<TBBase> tBBaseList = tBBaseRepository.findAll();
        assertThat(tBBaseList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkIdbaseIsRequired() throws Exception {
        int databaseSizeBeforeTest = tBBaseRepository.findAll().size();
        // set the field null
        tBBase.setIdbase(null);

        // Create the TBBase, which fails.

        restTBBaseMockMvc.perform(post("/api/t-b-bases")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tBBase)))
            .andExpect(status().isBadRequest());

        List<TBBase> tBBaseList = tBBaseRepository.findAll();
        assertThat(tBBaseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIdusuarioIsRequired() throws Exception {
        int databaseSizeBeforeTest = tBBaseRepository.findAll().size();
        // set the field null
        tBBase.setIdusuario(null);

        // Create the TBBase, which fails.

        restTBBaseMockMvc.perform(post("/api/t-b-bases")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tBBase)))
            .andExpect(status().isBadRequest());

        List<TBBase> tBBaseList = tBBaseRepository.findAll();
        assertThat(tBBaseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUrlIsRequired() throws Exception {
        int databaseSizeBeforeTest = tBBaseRepository.findAll().size();
        // set the field null
        tBBase.setUrl(null);

        // Create the TBBase, which fails.

        restTBBaseMockMvc.perform(post("/api/t-b-bases")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tBBase)))
            .andExpect(status().isBadRequest());

        List<TBBase> tBBaseList = tBBaseRepository.findAll();
        assertThat(tBBaseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTBBases() throws Exception {
        // Initialize the database
        tBBaseRepository.saveAndFlush(tBBase);

        // Get all the tBBaseList
        restTBBaseMockMvc.perform(get("/api/t-b-bases?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tBBase.getId().intValue())))
            .andExpect(jsonPath("$.[*].idbase").value(hasItem(DEFAULT_IDBASE.toString())))
            .andExpect(jsonPath("$.[*].idusuario").value(hasItem(DEFAULT_IDUSUARIO.intValue())))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL.toString())));
    }

    @Test
    @Transactional
    public void getTBBase() throws Exception {
        // Initialize the database
        tBBaseRepository.saveAndFlush(tBBase);

        // Get the tBBase
        restTBBaseMockMvc.perform(get("/api/t-b-bases/{id}", tBBase.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tBBase.getId().intValue()))
            .andExpect(jsonPath("$.idbase").value(DEFAULT_IDBASE.toString()))
            .andExpect(jsonPath("$.idusuario").value(DEFAULT_IDUSUARIO.intValue()))
            .andExpect(jsonPath("$.url").value(DEFAULT_URL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTBBase() throws Exception {
        // Get the tBBase
        restTBBaseMockMvc.perform(get("/api/t-b-bases/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTBBase() throws Exception {
        // Initialize the database
        tBBaseRepository.saveAndFlush(tBBase);
        tBBaseSearchRepository.save(tBBase);
        int databaseSizeBeforeUpdate = tBBaseRepository.findAll().size();

        // Update the tBBase
        TBBase updatedTBBase = tBBaseRepository.findOne(tBBase.getId());
        updatedTBBase
                .idbase(UPDATED_IDBASE)
                .idusuario(UPDATED_IDUSUARIO)
                .url(UPDATED_URL);

        restTBBaseMockMvc.perform(put("/api/t-b-bases")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTBBase)))
            .andExpect(status().isOk());

        // Validate the TBBase in the database
        List<TBBase> tBBaseList = tBBaseRepository.findAll();
        assertThat(tBBaseList).hasSize(databaseSizeBeforeUpdate);
        TBBase testTBBase = tBBaseList.get(tBBaseList.size() - 1);
        assertThat(testTBBase.getIdbase()).isEqualTo(UPDATED_IDBASE);
        assertThat(testTBBase.getIdusuario()).isEqualTo(UPDATED_IDUSUARIO);
        assertThat(testTBBase.getUrl()).isEqualTo(UPDATED_URL);

        // Validate the TBBase in Elasticsearch
        TBBase tBBaseEs = tBBaseSearchRepository.findOne(testTBBase.getId());
        assertThat(tBBaseEs).isEqualToComparingFieldByField(testTBBase);
    }

    @Test
    @Transactional
    public void updateNonExistingTBBase() throws Exception {
        int databaseSizeBeforeUpdate = tBBaseRepository.findAll().size();

        // Create the TBBase

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTBBaseMockMvc.perform(put("/api/t-b-bases")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tBBase)))
            .andExpect(status().isCreated());

        // Validate the TBBase in the database
        List<TBBase> tBBaseList = tBBaseRepository.findAll();
        assertThat(tBBaseList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTBBase() throws Exception {
        // Initialize the database
        tBBaseRepository.saveAndFlush(tBBase);
        tBBaseSearchRepository.save(tBBase);
        int databaseSizeBeforeDelete = tBBaseRepository.findAll().size();

        // Get the tBBase
        restTBBaseMockMvc.perform(delete("/api/t-b-bases/{id}", tBBase.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean tBBaseExistsInEs = tBBaseSearchRepository.exists(tBBase.getId());
        assertThat(tBBaseExistsInEs).isFalse();

        // Validate the database is empty
        List<TBBase> tBBaseList = tBBaseRepository.findAll();
        assertThat(tBBaseList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchTBBase() throws Exception {
        // Initialize the database
        tBBaseRepository.saveAndFlush(tBBase);
        tBBaseSearchRepository.save(tBBase);

        // Search the tBBase
        restTBBaseMockMvc.perform(get("/api/_search/t-b-bases?query=id:" + tBBase.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tBBase.getId().intValue())))
            .andExpect(jsonPath("$.[*].idbase").value(hasItem(DEFAULT_IDBASE.toString())))
            .andExpect(jsonPath("$.[*].idusuario").value(hasItem(DEFAULT_IDUSUARIO.intValue())))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL.toString())));
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TBBase.class);
    }
}
