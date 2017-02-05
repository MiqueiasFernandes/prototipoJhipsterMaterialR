package com.mfernandes.app.web.rest;

import com.mfernandes.app.Projeto1App;

import com.mfernandes.app.domain.TBModeloGenerico;
import com.mfernandes.app.repository.TBModeloGenericoRepository;
import com.mfernandes.app.repository.search.TBModeloGenericoSearchRepository;

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
 * Test class for the TBModeloGenericoResource REST controller.
 *
 * @see TBModeloGenericoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Projeto1App.class)
public class TBModeloGenericoResourceIntTest {

    private static final String DEFAULT_IDMODELOGENERICO = "AAAAAAAAAA";
    private static final String UPDATED_IDMODELOGENERICO = "BBBBBBBBBB";

    private static final Long DEFAULT_IDUSUARIO = 1L;
    private static final Long UPDATED_IDUSUARIO = 2L;

    private static final String DEFAULT_SCRIPT = "AAAAAAAAAA";
    private static final String UPDATED_SCRIPT = "BBBBBBBBBB";

    @Autowired
    private TBModeloGenericoRepository tBModeloGenericoRepository;

    @Autowired
    private TBModeloGenericoSearchRepository tBModeloGenericoSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private EntityManager em;

    private MockMvc restTBModeloGenericoMockMvc;

    private TBModeloGenerico tBModeloGenerico;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
            TBModeloGenericoResource tBModeloGenericoResource = new TBModeloGenericoResource(tBModeloGenericoRepository, tBModeloGenericoSearchRepository);
        this.restTBModeloGenericoMockMvc = MockMvcBuilders.standaloneSetup(tBModeloGenericoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TBModeloGenerico createEntity(EntityManager em) {
        TBModeloGenerico tBModeloGenerico = new TBModeloGenerico()
                .idmodelogenerico(DEFAULT_IDMODELOGENERICO)
                .idusuario(DEFAULT_IDUSUARIO)
                .script(DEFAULT_SCRIPT);
        return tBModeloGenerico;
    }

    @Before
    public void initTest() {
        tBModeloGenericoSearchRepository.deleteAll();
        tBModeloGenerico = createEntity(em);
    }

    @Test
    @Transactional
    public void createTBModeloGenerico() throws Exception {
        int databaseSizeBeforeCreate = tBModeloGenericoRepository.findAll().size();

        // Create the TBModeloGenerico

        restTBModeloGenericoMockMvc.perform(post("/api/t-b-modelo-genericos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tBModeloGenerico)))
            .andExpect(status().isCreated());

        // Validate the TBModeloGenerico in the database
        List<TBModeloGenerico> tBModeloGenericoList = tBModeloGenericoRepository.findAll();
        assertThat(tBModeloGenericoList).hasSize(databaseSizeBeforeCreate + 1);
        TBModeloGenerico testTBModeloGenerico = tBModeloGenericoList.get(tBModeloGenericoList.size() - 1);
        assertThat(testTBModeloGenerico.getIdmodelogenerico()).isEqualTo(DEFAULT_IDMODELOGENERICO);
        assertThat(testTBModeloGenerico.getIdusuario()).isEqualTo(DEFAULT_IDUSUARIO);
        assertThat(testTBModeloGenerico.getScript()).isEqualTo(DEFAULT_SCRIPT);

        // Validate the TBModeloGenerico in Elasticsearch
        TBModeloGenerico tBModeloGenericoEs = tBModeloGenericoSearchRepository.findOne(testTBModeloGenerico.getId());
        assertThat(tBModeloGenericoEs).isEqualToComparingFieldByField(testTBModeloGenerico);
    }

    @Test
    @Transactional
    public void createTBModeloGenericoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tBModeloGenericoRepository.findAll().size();

        // Create the TBModeloGenerico with an existing ID
        TBModeloGenerico existingTBModeloGenerico = new TBModeloGenerico();
        existingTBModeloGenerico.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTBModeloGenericoMockMvc.perform(post("/api/t-b-modelo-genericos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingTBModeloGenerico)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<TBModeloGenerico> tBModeloGenericoList = tBModeloGenericoRepository.findAll();
        assertThat(tBModeloGenericoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkIdmodelogenericoIsRequired() throws Exception {
        int databaseSizeBeforeTest = tBModeloGenericoRepository.findAll().size();
        // set the field null
        tBModeloGenerico.setIdmodelogenerico(null);

        // Create the TBModeloGenerico, which fails.

        restTBModeloGenericoMockMvc.perform(post("/api/t-b-modelo-genericos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tBModeloGenerico)))
            .andExpect(status().isBadRequest());

        List<TBModeloGenerico> tBModeloGenericoList = tBModeloGenericoRepository.findAll();
        assertThat(tBModeloGenericoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIdusuarioIsRequired() throws Exception {
        int databaseSizeBeforeTest = tBModeloGenericoRepository.findAll().size();
        // set the field null
        tBModeloGenerico.setIdusuario(null);

        // Create the TBModeloGenerico, which fails.

        restTBModeloGenericoMockMvc.perform(post("/api/t-b-modelo-genericos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tBModeloGenerico)))
            .andExpect(status().isBadRequest());

        List<TBModeloGenerico> tBModeloGenericoList = tBModeloGenericoRepository.findAll();
        assertThat(tBModeloGenericoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTBModeloGenericos() throws Exception {
        // Initialize the database
        tBModeloGenericoRepository.saveAndFlush(tBModeloGenerico);

        // Get all the tBModeloGenericoList
        restTBModeloGenericoMockMvc.perform(get("/api/t-b-modelo-genericos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tBModeloGenerico.getId().intValue())))
            .andExpect(jsonPath("$.[*].idmodelogenerico").value(hasItem(DEFAULT_IDMODELOGENERICO.toString())))
            .andExpect(jsonPath("$.[*].idusuario").value(hasItem(DEFAULT_IDUSUARIO.intValue())))
            .andExpect(jsonPath("$.[*].script").value(hasItem(DEFAULT_SCRIPT.toString())));
    }

    @Test
    @Transactional
    public void getTBModeloGenerico() throws Exception {
        // Initialize the database
        tBModeloGenericoRepository.saveAndFlush(tBModeloGenerico);

        // Get the tBModeloGenerico
        restTBModeloGenericoMockMvc.perform(get("/api/t-b-modelo-genericos/{id}", tBModeloGenerico.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tBModeloGenerico.getId().intValue()))
            .andExpect(jsonPath("$.idmodelogenerico").value(DEFAULT_IDMODELOGENERICO.toString()))
            .andExpect(jsonPath("$.idusuario").value(DEFAULT_IDUSUARIO.intValue()))
            .andExpect(jsonPath("$.script").value(DEFAULT_SCRIPT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTBModeloGenerico() throws Exception {
        // Get the tBModeloGenerico
        restTBModeloGenericoMockMvc.perform(get("/api/t-b-modelo-genericos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTBModeloGenerico() throws Exception {
        // Initialize the database
        tBModeloGenericoRepository.saveAndFlush(tBModeloGenerico);
        tBModeloGenericoSearchRepository.save(tBModeloGenerico);
        int databaseSizeBeforeUpdate = tBModeloGenericoRepository.findAll().size();

        // Update the tBModeloGenerico
        TBModeloGenerico updatedTBModeloGenerico = tBModeloGenericoRepository.findOne(tBModeloGenerico.getId());
        updatedTBModeloGenerico
                .idmodelogenerico(UPDATED_IDMODELOGENERICO)
                .idusuario(UPDATED_IDUSUARIO)
                .script(UPDATED_SCRIPT);

        restTBModeloGenericoMockMvc.perform(put("/api/t-b-modelo-genericos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTBModeloGenerico)))
            .andExpect(status().isOk());

        // Validate the TBModeloGenerico in the database
        List<TBModeloGenerico> tBModeloGenericoList = tBModeloGenericoRepository.findAll();
        assertThat(tBModeloGenericoList).hasSize(databaseSizeBeforeUpdate);
        TBModeloGenerico testTBModeloGenerico = tBModeloGenericoList.get(tBModeloGenericoList.size() - 1);
        assertThat(testTBModeloGenerico.getIdmodelogenerico()).isEqualTo(UPDATED_IDMODELOGENERICO);
        assertThat(testTBModeloGenerico.getIdusuario()).isEqualTo(UPDATED_IDUSUARIO);
        assertThat(testTBModeloGenerico.getScript()).isEqualTo(UPDATED_SCRIPT);

        // Validate the TBModeloGenerico in Elasticsearch
        TBModeloGenerico tBModeloGenericoEs = tBModeloGenericoSearchRepository.findOne(testTBModeloGenerico.getId());
        assertThat(tBModeloGenericoEs).isEqualToComparingFieldByField(testTBModeloGenerico);
    }

    @Test
    @Transactional
    public void updateNonExistingTBModeloGenerico() throws Exception {
        int databaseSizeBeforeUpdate = tBModeloGenericoRepository.findAll().size();

        // Create the TBModeloGenerico

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTBModeloGenericoMockMvc.perform(put("/api/t-b-modelo-genericos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tBModeloGenerico)))
            .andExpect(status().isCreated());

        // Validate the TBModeloGenerico in the database
        List<TBModeloGenerico> tBModeloGenericoList = tBModeloGenericoRepository.findAll();
        assertThat(tBModeloGenericoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTBModeloGenerico() throws Exception {
        // Initialize the database
        tBModeloGenericoRepository.saveAndFlush(tBModeloGenerico);
        tBModeloGenericoSearchRepository.save(tBModeloGenerico);
        int databaseSizeBeforeDelete = tBModeloGenericoRepository.findAll().size();

        // Get the tBModeloGenerico
        restTBModeloGenericoMockMvc.perform(delete("/api/t-b-modelo-genericos/{id}", tBModeloGenerico.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean tBModeloGenericoExistsInEs = tBModeloGenericoSearchRepository.exists(tBModeloGenerico.getId());
        assertThat(tBModeloGenericoExistsInEs).isFalse();

        // Validate the database is empty
        List<TBModeloGenerico> tBModeloGenericoList = tBModeloGenericoRepository.findAll();
        assertThat(tBModeloGenericoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchTBModeloGenerico() throws Exception {
        // Initialize the database
        tBModeloGenericoRepository.saveAndFlush(tBModeloGenerico);
        tBModeloGenericoSearchRepository.save(tBModeloGenerico);

        // Search the tBModeloGenerico
        restTBModeloGenericoMockMvc.perform(get("/api/_search/t-b-modelo-genericos?query=id:" + tBModeloGenerico.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tBModeloGenerico.getId().intValue())))
            .andExpect(jsonPath("$.[*].idmodelogenerico").value(hasItem(DEFAULT_IDMODELOGENERICO.toString())))
            .andExpect(jsonPath("$.[*].idusuario").value(hasItem(DEFAULT_IDUSUARIO.intValue())))
            .andExpect(jsonPath("$.[*].script").value(hasItem(DEFAULT_SCRIPT.toString())));
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TBModeloGenerico.class);
    }
}
