package com.mfernandes.app.web.rest;

import com.mfernandes.app.Projeto1App;

import com.mfernandes.app.domain.TBProjeto;
import com.mfernandes.app.repository.TBProjetoRepository;
import com.mfernandes.app.repository.search.TBProjetoSearchRepository;

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
 * Test class for the TBProjetoResource REST controller.
 *
 * @see TBProjetoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Projeto1App.class)
public class TBProjetoResourceIntTest {

    private static final String DEFAULT_IDPROJETO = "AAAAAAAAAA";
    private static final String UPDATED_IDPROJETO = "BBBBBBBBBB";

    private static final Long DEFAULT_IDUSUARIO = 1L;
    private static final Long UPDATED_IDUSUARIO = 2L;

    private static final Long DEFAULT_IDSESSAO = 1L;
    private static final Long UPDATED_IDSESSAO = 2L;

    private static final String DEFAULT_IDBASE = "AAAAAAAAAA";
    private static final String UPDATED_IDBASE = "BBBBBBBBBB";

    private static final String DEFAULT_SCRIPT = "AAAAAAAAAA";
    private static final String UPDATED_SCRIPT = "BBBBBBBBBB";

    @Autowired
    private TBProjetoRepository tBProjetoRepository;

    @Autowired
    private TBProjetoSearchRepository tBProjetoSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private EntityManager em;

    private MockMvc restTBProjetoMockMvc;

    private TBProjeto tBProjeto;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
            TBProjetoResource tBProjetoResource = new TBProjetoResource(tBProjetoRepository, tBProjetoSearchRepository);
        this.restTBProjetoMockMvc = MockMvcBuilders.standaloneSetup(tBProjetoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TBProjeto createEntity(EntityManager em) {
        TBProjeto tBProjeto = new TBProjeto()
                .idprojeto(DEFAULT_IDPROJETO)
                .idusuario(DEFAULT_IDUSUARIO)
                .idsessao(DEFAULT_IDSESSAO)
                .idbase(DEFAULT_IDBASE)
                .script(DEFAULT_SCRIPT);
        return tBProjeto;
    }

    @Before
    public void initTest() {
        tBProjetoSearchRepository.deleteAll();
        tBProjeto = createEntity(em);
    }

    @Test
    @Transactional
    public void createTBProjeto() throws Exception {
        int databaseSizeBeforeCreate = tBProjetoRepository.findAll().size();

        // Create the TBProjeto

        restTBProjetoMockMvc.perform(post("/api/t-b-projetos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tBProjeto)))
            .andExpect(status().isCreated());

        // Validate the TBProjeto in the database
        List<TBProjeto> tBProjetoList = tBProjetoRepository.findAll();
        assertThat(tBProjetoList).hasSize(databaseSizeBeforeCreate + 1);
        TBProjeto testTBProjeto = tBProjetoList.get(tBProjetoList.size() - 1);
        assertThat(testTBProjeto.getIdprojeto()).isEqualTo(DEFAULT_IDPROJETO);
        assertThat(testTBProjeto.getIdusuario()).isEqualTo(DEFAULT_IDUSUARIO);
        assertThat(testTBProjeto.getIdsessao()).isEqualTo(DEFAULT_IDSESSAO);
        assertThat(testTBProjeto.getIdbase()).isEqualTo(DEFAULT_IDBASE);
        assertThat(testTBProjeto.getScript()).isEqualTo(DEFAULT_SCRIPT);

        // Validate the TBProjeto in Elasticsearch
        TBProjeto tBProjetoEs = tBProjetoSearchRepository.findOne(testTBProjeto.getId());
        assertThat(tBProjetoEs).isEqualToComparingFieldByField(testTBProjeto);
    }

    @Test
    @Transactional
    public void createTBProjetoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tBProjetoRepository.findAll().size();

        // Create the TBProjeto with an existing ID
        TBProjeto existingTBProjeto = new TBProjeto();
        existingTBProjeto.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTBProjetoMockMvc.perform(post("/api/t-b-projetos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingTBProjeto)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<TBProjeto> tBProjetoList = tBProjetoRepository.findAll();
        assertThat(tBProjetoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkIdprojetoIsRequired() throws Exception {
        int databaseSizeBeforeTest = tBProjetoRepository.findAll().size();
        // set the field null
        tBProjeto.setIdprojeto(null);

        // Create the TBProjeto, which fails.

        restTBProjetoMockMvc.perform(post("/api/t-b-projetos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tBProjeto)))
            .andExpect(status().isBadRequest());

        List<TBProjeto> tBProjetoList = tBProjetoRepository.findAll();
        assertThat(tBProjetoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIdusuarioIsRequired() throws Exception {
        int databaseSizeBeforeTest = tBProjetoRepository.findAll().size();
        // set the field null
        tBProjeto.setIdusuario(null);

        // Create the TBProjeto, which fails.

        restTBProjetoMockMvc.perform(post("/api/t-b-projetos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tBProjeto)))
            .andExpect(status().isBadRequest());

        List<TBProjeto> tBProjetoList = tBProjetoRepository.findAll();
        assertThat(tBProjetoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTBProjetos() throws Exception {
        // Initialize the database
        tBProjetoRepository.saveAndFlush(tBProjeto);

        // Get all the tBProjetoList
        restTBProjetoMockMvc.perform(get("/api/t-b-projetos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tBProjeto.getId().intValue())))
            .andExpect(jsonPath("$.[*].idprojeto").value(hasItem(DEFAULT_IDPROJETO.toString())))
            .andExpect(jsonPath("$.[*].idusuario").value(hasItem(DEFAULT_IDUSUARIO.intValue())))
            .andExpect(jsonPath("$.[*].idsessao").value(hasItem(DEFAULT_IDSESSAO.intValue())))
            .andExpect(jsonPath("$.[*].idbase").value(hasItem(DEFAULT_IDBASE.toString())))
            .andExpect(jsonPath("$.[*].script").value(hasItem(DEFAULT_SCRIPT.toString())));
    }

    @Test
    @Transactional
    public void getTBProjeto() throws Exception {
        // Initialize the database
        tBProjetoRepository.saveAndFlush(tBProjeto);

        // Get the tBProjeto
        restTBProjetoMockMvc.perform(get("/api/t-b-projetos/{id}", tBProjeto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tBProjeto.getId().intValue()))
            .andExpect(jsonPath("$.idprojeto").value(DEFAULT_IDPROJETO.toString()))
            .andExpect(jsonPath("$.idusuario").value(DEFAULT_IDUSUARIO.intValue()))
            .andExpect(jsonPath("$.idsessao").value(DEFAULT_IDSESSAO.intValue()))
            .andExpect(jsonPath("$.idbase").value(DEFAULT_IDBASE.toString()))
            .andExpect(jsonPath("$.script").value(DEFAULT_SCRIPT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTBProjeto() throws Exception {
        // Get the tBProjeto
        restTBProjetoMockMvc.perform(get("/api/t-b-projetos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTBProjeto() throws Exception {
        // Initialize the database
        tBProjetoRepository.saveAndFlush(tBProjeto);
        tBProjetoSearchRepository.save(tBProjeto);
        int databaseSizeBeforeUpdate = tBProjetoRepository.findAll().size();

        // Update the tBProjeto
        TBProjeto updatedTBProjeto = tBProjetoRepository.findOne(tBProjeto.getId());
        updatedTBProjeto
                .idprojeto(UPDATED_IDPROJETO)
                .idusuario(UPDATED_IDUSUARIO)
                .idsessao(UPDATED_IDSESSAO)
                .idbase(UPDATED_IDBASE)
                .script(UPDATED_SCRIPT);

        restTBProjetoMockMvc.perform(put("/api/t-b-projetos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTBProjeto)))
            .andExpect(status().isOk());

        // Validate the TBProjeto in the database
        List<TBProjeto> tBProjetoList = tBProjetoRepository.findAll();
        assertThat(tBProjetoList).hasSize(databaseSizeBeforeUpdate);
        TBProjeto testTBProjeto = tBProjetoList.get(tBProjetoList.size() - 1);
        assertThat(testTBProjeto.getIdprojeto()).isEqualTo(UPDATED_IDPROJETO);
        assertThat(testTBProjeto.getIdusuario()).isEqualTo(UPDATED_IDUSUARIO);
        assertThat(testTBProjeto.getIdsessao()).isEqualTo(UPDATED_IDSESSAO);
        assertThat(testTBProjeto.getIdbase()).isEqualTo(UPDATED_IDBASE);
        assertThat(testTBProjeto.getScript()).isEqualTo(UPDATED_SCRIPT);

        // Validate the TBProjeto in Elasticsearch
        TBProjeto tBProjetoEs = tBProjetoSearchRepository.findOne(testTBProjeto.getId());
        assertThat(tBProjetoEs).isEqualToComparingFieldByField(testTBProjeto);
    }

    @Test
    @Transactional
    public void updateNonExistingTBProjeto() throws Exception {
        int databaseSizeBeforeUpdate = tBProjetoRepository.findAll().size();

        // Create the TBProjeto

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTBProjetoMockMvc.perform(put("/api/t-b-projetos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tBProjeto)))
            .andExpect(status().isCreated());

        // Validate the TBProjeto in the database
        List<TBProjeto> tBProjetoList = tBProjetoRepository.findAll();
        assertThat(tBProjetoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTBProjeto() throws Exception {
        // Initialize the database
        tBProjetoRepository.saveAndFlush(tBProjeto);
        tBProjetoSearchRepository.save(tBProjeto);
        int databaseSizeBeforeDelete = tBProjetoRepository.findAll().size();

        // Get the tBProjeto
        restTBProjetoMockMvc.perform(delete("/api/t-b-projetos/{id}", tBProjeto.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean tBProjetoExistsInEs = tBProjetoSearchRepository.exists(tBProjeto.getId());
        assertThat(tBProjetoExistsInEs).isFalse();

        // Validate the database is empty
        List<TBProjeto> tBProjetoList = tBProjetoRepository.findAll();
        assertThat(tBProjetoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchTBProjeto() throws Exception {
        // Initialize the database
        tBProjetoRepository.saveAndFlush(tBProjeto);
        tBProjetoSearchRepository.save(tBProjeto);

        // Search the tBProjeto
        restTBProjetoMockMvc.perform(get("/api/_search/t-b-projetos?query=id:" + tBProjeto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tBProjeto.getId().intValue())))
            .andExpect(jsonPath("$.[*].idprojeto").value(hasItem(DEFAULT_IDPROJETO.toString())))
            .andExpect(jsonPath("$.[*].idusuario").value(hasItem(DEFAULT_IDUSUARIO.intValue())))
            .andExpect(jsonPath("$.[*].idsessao").value(hasItem(DEFAULT_IDSESSAO.intValue())))
            .andExpect(jsonPath("$.[*].idbase").value(hasItem(DEFAULT_IDBASE.toString())))
            .andExpect(jsonPath("$.[*].script").value(hasItem(DEFAULT_SCRIPT.toString())));
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TBProjeto.class);
    }
}
