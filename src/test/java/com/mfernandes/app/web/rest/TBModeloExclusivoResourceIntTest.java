package com.mfernandes.app.web.rest;

import com.mfernandes.app.Projeto1App;

import com.mfernandes.app.domain.TBModeloExclusivo;
import com.mfernandes.app.repository.TBModeloExclusivoRepository;
import com.mfernandes.app.repository.search.TBModeloExclusivoSearchRepository;

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
 * Test class for the TBModeloExclusivoResource REST controller.
 *
 * @see TBModeloExclusivoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Projeto1App.class)
public class TBModeloExclusivoResourceIntTest {

    private static final String DEFAULT_IDMODELOEXCLUSIVO = "AAAAAAAAAA";
    private static final String UPDATED_IDMODELOEXCLUSIVO = "BBBBBBBBBB";

    private static final Long DEFAULT_IDUSUARIO = 1L;
    private static final Long UPDATED_IDUSUARIO = 2L;

    private static final String DEFAULT_IDMODELOGENERICO = "AAAAAAAAAA";
    private static final String UPDATED_IDMODELOGENERICO = "BBBBBBBBBB";

    private static final String DEFAULT_MAPEAMENTO = "AAAAAAAAAA";
    private static final String UPDATED_MAPEAMENTO = "BBBBBBBBBB";

    @Autowired
    private TBModeloExclusivoRepository tBModeloExclusivoRepository;

    @Autowired
    private TBModeloExclusivoSearchRepository tBModeloExclusivoSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private EntityManager em;

    private MockMvc restTBModeloExclusivoMockMvc;

    private TBModeloExclusivo tBModeloExclusivo;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
            TBModeloExclusivoResource tBModeloExclusivoResource = new TBModeloExclusivoResource(tBModeloExclusivoRepository, tBModeloExclusivoSearchRepository);
        this.restTBModeloExclusivoMockMvc = MockMvcBuilders.standaloneSetup(tBModeloExclusivoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TBModeloExclusivo createEntity(EntityManager em) {
        TBModeloExclusivo tBModeloExclusivo = new TBModeloExclusivo()
                .idmodeloexclusivo(DEFAULT_IDMODELOEXCLUSIVO)
                .idusuario(DEFAULT_IDUSUARIO)
                .idmodelogenerico(DEFAULT_IDMODELOGENERICO)
                .mapeamento(DEFAULT_MAPEAMENTO);
        return tBModeloExclusivo;
    }

    @Before
    public void initTest() {
        tBModeloExclusivoSearchRepository.deleteAll();
        tBModeloExclusivo = createEntity(em);
    }

    @Test
    @Transactional
    public void createTBModeloExclusivo() throws Exception {
        int databaseSizeBeforeCreate = tBModeloExclusivoRepository.findAll().size();

        // Create the TBModeloExclusivo

        restTBModeloExclusivoMockMvc.perform(post("/api/t-b-modelo-exclusivos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tBModeloExclusivo)))
            .andExpect(status().isCreated());

        // Validate the TBModeloExclusivo in the database
        List<TBModeloExclusivo> tBModeloExclusivoList = tBModeloExclusivoRepository.findAll();
        assertThat(tBModeloExclusivoList).hasSize(databaseSizeBeforeCreate + 1);
        TBModeloExclusivo testTBModeloExclusivo = tBModeloExclusivoList.get(tBModeloExclusivoList.size() - 1);
        assertThat(testTBModeloExclusivo.getIdmodeloexclusivo()).isEqualTo(DEFAULT_IDMODELOEXCLUSIVO);
        assertThat(testTBModeloExclusivo.getIdusuario()).isEqualTo(DEFAULT_IDUSUARIO);
        assertThat(testTBModeloExclusivo.getIdmodelogenerico()).isEqualTo(DEFAULT_IDMODELOGENERICO);
        assertThat(testTBModeloExclusivo.getMapeamento()).isEqualTo(DEFAULT_MAPEAMENTO);

        // Validate the TBModeloExclusivo in Elasticsearch
        TBModeloExclusivo tBModeloExclusivoEs = tBModeloExclusivoSearchRepository.findOne(testTBModeloExclusivo.getId());
        assertThat(tBModeloExclusivoEs).isEqualToComparingFieldByField(testTBModeloExclusivo);
    }

    @Test
    @Transactional
    public void createTBModeloExclusivoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tBModeloExclusivoRepository.findAll().size();

        // Create the TBModeloExclusivo with an existing ID
        TBModeloExclusivo existingTBModeloExclusivo = new TBModeloExclusivo();
        existingTBModeloExclusivo.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTBModeloExclusivoMockMvc.perform(post("/api/t-b-modelo-exclusivos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingTBModeloExclusivo)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<TBModeloExclusivo> tBModeloExclusivoList = tBModeloExclusivoRepository.findAll();
        assertThat(tBModeloExclusivoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkIdmodeloexclusivoIsRequired() throws Exception {
        int databaseSizeBeforeTest = tBModeloExclusivoRepository.findAll().size();
        // set the field null
        tBModeloExclusivo.setIdmodeloexclusivo(null);

        // Create the TBModeloExclusivo, which fails.

        restTBModeloExclusivoMockMvc.perform(post("/api/t-b-modelo-exclusivos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tBModeloExclusivo)))
            .andExpect(status().isBadRequest());

        List<TBModeloExclusivo> tBModeloExclusivoList = tBModeloExclusivoRepository.findAll();
        assertThat(tBModeloExclusivoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIdusuarioIsRequired() throws Exception {
        int databaseSizeBeforeTest = tBModeloExclusivoRepository.findAll().size();
        // set the field null
        tBModeloExclusivo.setIdusuario(null);

        // Create the TBModeloExclusivo, which fails.

        restTBModeloExclusivoMockMvc.perform(post("/api/t-b-modelo-exclusivos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tBModeloExclusivo)))
            .andExpect(status().isBadRequest());

        List<TBModeloExclusivo> tBModeloExclusivoList = tBModeloExclusivoRepository.findAll();
        assertThat(tBModeloExclusivoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTBModeloExclusivos() throws Exception {
        // Initialize the database
        tBModeloExclusivoRepository.saveAndFlush(tBModeloExclusivo);

        // Get all the tBModeloExclusivoList
        restTBModeloExclusivoMockMvc.perform(get("/api/t-b-modelo-exclusivos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tBModeloExclusivo.getId().intValue())))
            .andExpect(jsonPath("$.[*].idmodeloexclusivo").value(hasItem(DEFAULT_IDMODELOEXCLUSIVO.toString())))
            .andExpect(jsonPath("$.[*].idusuario").value(hasItem(DEFAULT_IDUSUARIO.intValue())))
            .andExpect(jsonPath("$.[*].idmodelogenerico").value(hasItem(DEFAULT_IDMODELOGENERICO.toString())))
            .andExpect(jsonPath("$.[*].mapeamento").value(hasItem(DEFAULT_MAPEAMENTO.toString())));
    }

    @Test
    @Transactional
    public void getTBModeloExclusivo() throws Exception {
        // Initialize the database
        tBModeloExclusivoRepository.saveAndFlush(tBModeloExclusivo);

        // Get the tBModeloExclusivo
        restTBModeloExclusivoMockMvc.perform(get("/api/t-b-modelo-exclusivos/{id}", tBModeloExclusivo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tBModeloExclusivo.getId().intValue()))
            .andExpect(jsonPath("$.idmodeloexclusivo").value(DEFAULT_IDMODELOEXCLUSIVO.toString()))
            .andExpect(jsonPath("$.idusuario").value(DEFAULT_IDUSUARIO.intValue()))
            .andExpect(jsonPath("$.idmodelogenerico").value(DEFAULT_IDMODELOGENERICO.toString()))
            .andExpect(jsonPath("$.mapeamento").value(DEFAULT_MAPEAMENTO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTBModeloExclusivo() throws Exception {
        // Get the tBModeloExclusivo
        restTBModeloExclusivoMockMvc.perform(get("/api/t-b-modelo-exclusivos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTBModeloExclusivo() throws Exception {
        // Initialize the database
        tBModeloExclusivoRepository.saveAndFlush(tBModeloExclusivo);
        tBModeloExclusivoSearchRepository.save(tBModeloExclusivo);
        int databaseSizeBeforeUpdate = tBModeloExclusivoRepository.findAll().size();

        // Update the tBModeloExclusivo
        TBModeloExclusivo updatedTBModeloExclusivo = tBModeloExclusivoRepository.findOne(tBModeloExclusivo.getId());
        updatedTBModeloExclusivo
                .idmodeloexclusivo(UPDATED_IDMODELOEXCLUSIVO)
                .idusuario(UPDATED_IDUSUARIO)
                .idmodelogenerico(UPDATED_IDMODELOGENERICO)
                .mapeamento(UPDATED_MAPEAMENTO);

        restTBModeloExclusivoMockMvc.perform(put("/api/t-b-modelo-exclusivos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTBModeloExclusivo)))
            .andExpect(status().isOk());

        // Validate the TBModeloExclusivo in the database
        List<TBModeloExclusivo> tBModeloExclusivoList = tBModeloExclusivoRepository.findAll();
        assertThat(tBModeloExclusivoList).hasSize(databaseSizeBeforeUpdate);
        TBModeloExclusivo testTBModeloExclusivo = tBModeloExclusivoList.get(tBModeloExclusivoList.size() - 1);
        assertThat(testTBModeloExclusivo.getIdmodeloexclusivo()).isEqualTo(UPDATED_IDMODELOEXCLUSIVO);
        assertThat(testTBModeloExclusivo.getIdusuario()).isEqualTo(UPDATED_IDUSUARIO);
        assertThat(testTBModeloExclusivo.getIdmodelogenerico()).isEqualTo(UPDATED_IDMODELOGENERICO);
        assertThat(testTBModeloExclusivo.getMapeamento()).isEqualTo(UPDATED_MAPEAMENTO);

        // Validate the TBModeloExclusivo in Elasticsearch
        TBModeloExclusivo tBModeloExclusivoEs = tBModeloExclusivoSearchRepository.findOne(testTBModeloExclusivo.getId());
        assertThat(tBModeloExclusivoEs).isEqualToComparingFieldByField(testTBModeloExclusivo);
    }

    @Test
    @Transactional
    public void updateNonExistingTBModeloExclusivo() throws Exception {
        int databaseSizeBeforeUpdate = tBModeloExclusivoRepository.findAll().size();

        // Create the TBModeloExclusivo

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTBModeloExclusivoMockMvc.perform(put("/api/t-b-modelo-exclusivos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tBModeloExclusivo)))
            .andExpect(status().isCreated());

        // Validate the TBModeloExclusivo in the database
        List<TBModeloExclusivo> tBModeloExclusivoList = tBModeloExclusivoRepository.findAll();
        assertThat(tBModeloExclusivoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTBModeloExclusivo() throws Exception {
        // Initialize the database
        tBModeloExclusivoRepository.saveAndFlush(tBModeloExclusivo);
        tBModeloExclusivoSearchRepository.save(tBModeloExclusivo);
        int databaseSizeBeforeDelete = tBModeloExclusivoRepository.findAll().size();

        // Get the tBModeloExclusivo
        restTBModeloExclusivoMockMvc.perform(delete("/api/t-b-modelo-exclusivos/{id}", tBModeloExclusivo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean tBModeloExclusivoExistsInEs = tBModeloExclusivoSearchRepository.exists(tBModeloExclusivo.getId());
        assertThat(tBModeloExclusivoExistsInEs).isFalse();

        // Validate the database is empty
        List<TBModeloExclusivo> tBModeloExclusivoList = tBModeloExclusivoRepository.findAll();
        assertThat(tBModeloExclusivoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchTBModeloExclusivo() throws Exception {
        // Initialize the database
        tBModeloExclusivoRepository.saveAndFlush(tBModeloExclusivo);
        tBModeloExclusivoSearchRepository.save(tBModeloExclusivo);

        // Search the tBModeloExclusivo
        restTBModeloExclusivoMockMvc.perform(get("/api/_search/t-b-modelo-exclusivos?query=id:" + tBModeloExclusivo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tBModeloExclusivo.getId().intValue())))
            .andExpect(jsonPath("$.[*].idmodeloexclusivo").value(hasItem(DEFAULT_IDMODELOEXCLUSIVO.toString())))
            .andExpect(jsonPath("$.[*].idusuario").value(hasItem(DEFAULT_IDUSUARIO.intValue())))
            .andExpect(jsonPath("$.[*].idmodelogenerico").value(hasItem(DEFAULT_IDMODELOGENERICO.toString())))
            .andExpect(jsonPath("$.[*].mapeamento").value(hasItem(DEFAULT_MAPEAMENTO.toString())));
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TBModeloExclusivo.class);
    }
}
