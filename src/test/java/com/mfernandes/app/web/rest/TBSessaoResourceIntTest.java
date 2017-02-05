package com.mfernandes.app.web.rest;

import com.mfernandes.app.Projeto1App;

import com.mfernandes.app.domain.TBSessao;
import com.mfernandes.app.repository.TBSessaoRepository;
import com.mfernandes.app.repository.search.TBSessaoSearchRepository;

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

import com.mfernandes.app.domain.enumeration.TBTiposessao;
/**
 * Test class for the TBSessaoResource REST controller.
 *
 * @see TBSessaoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Projeto1App.class)
public class TBSessaoResourceIntTest {

    private static final Long DEFAULT_IDSESSAO = 1L;
    private static final Long UPDATED_IDSESSAO = 2L;

    private static final String DEFAULT_IDMODELOEXCLUSIVO = "AAAAAAAAAA";
    private static final String UPDATED_IDMODELOEXCLUSIVO = "BBBBBBBBBB";

    private static final TBTiposessao DEFAULT_TIPO = TBTiposessao.BATCH;
    private static final TBTiposessao UPDATED_TIPO = TBTiposessao.LIVE;

    @Autowired
    private TBSessaoRepository tBSessaoRepository;

    @Autowired
    private TBSessaoSearchRepository tBSessaoSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private EntityManager em;

    private MockMvc restTBSessaoMockMvc;

    private TBSessao tBSessao;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
            TBSessaoResource tBSessaoResource = new TBSessaoResource(tBSessaoRepository, tBSessaoSearchRepository);
        this.restTBSessaoMockMvc = MockMvcBuilders.standaloneSetup(tBSessaoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TBSessao createEntity(EntityManager em) {
        TBSessao tBSessao = new TBSessao()
                .idsessao(DEFAULT_IDSESSAO)
                .idmodeloexclusivo(DEFAULT_IDMODELOEXCLUSIVO)
                .tipo(DEFAULT_TIPO);
        return tBSessao;
    }

    @Before
    public void initTest() {
        tBSessaoSearchRepository.deleteAll();
        tBSessao = createEntity(em);
    }

    @Test
    @Transactional
    public void createTBSessao() throws Exception {
        int databaseSizeBeforeCreate = tBSessaoRepository.findAll().size();

        // Create the TBSessao

        restTBSessaoMockMvc.perform(post("/api/t-b-sessaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tBSessao)))
            .andExpect(status().isCreated());

        // Validate the TBSessao in the database
        List<TBSessao> tBSessaoList = tBSessaoRepository.findAll();
        assertThat(tBSessaoList).hasSize(databaseSizeBeforeCreate + 1);
        TBSessao testTBSessao = tBSessaoList.get(tBSessaoList.size() - 1);
        assertThat(testTBSessao.getIdsessao()).isEqualTo(DEFAULT_IDSESSAO);
        assertThat(testTBSessao.getIdmodeloexclusivo()).isEqualTo(DEFAULT_IDMODELOEXCLUSIVO);
        assertThat(testTBSessao.getTipo()).isEqualTo(DEFAULT_TIPO);

        // Validate the TBSessao in Elasticsearch
        TBSessao tBSessaoEs = tBSessaoSearchRepository.findOne(testTBSessao.getId());
        assertThat(tBSessaoEs).isEqualToComparingFieldByField(testTBSessao);
    }

    @Test
    @Transactional
    public void createTBSessaoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tBSessaoRepository.findAll().size();

        // Create the TBSessao with an existing ID
        TBSessao existingTBSessao = new TBSessao();
        existingTBSessao.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTBSessaoMockMvc.perform(post("/api/t-b-sessaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingTBSessao)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<TBSessao> tBSessaoList = tBSessaoRepository.findAll();
        assertThat(tBSessaoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkIdsessaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = tBSessaoRepository.findAll().size();
        // set the field null
        tBSessao.setIdsessao(null);

        // Create the TBSessao, which fails.

        restTBSessaoMockMvc.perform(post("/api/t-b-sessaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tBSessao)))
            .andExpect(status().isBadRequest());

        List<TBSessao> tBSessaoList = tBSessaoRepository.findAll();
        assertThat(tBSessaoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTipoIsRequired() throws Exception {
        int databaseSizeBeforeTest = tBSessaoRepository.findAll().size();
        // set the field null
        tBSessao.setTipo(null);

        // Create the TBSessao, which fails.

        restTBSessaoMockMvc.perform(post("/api/t-b-sessaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tBSessao)))
            .andExpect(status().isBadRequest());

        List<TBSessao> tBSessaoList = tBSessaoRepository.findAll();
        assertThat(tBSessaoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTBSessaos() throws Exception {
        // Initialize the database
        tBSessaoRepository.saveAndFlush(tBSessao);

        // Get all the tBSessaoList
        restTBSessaoMockMvc.perform(get("/api/t-b-sessaos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tBSessao.getId().intValue())))
            .andExpect(jsonPath("$.[*].idsessao").value(hasItem(DEFAULT_IDSESSAO.intValue())))
            .andExpect(jsonPath("$.[*].idmodeloexclusivo").value(hasItem(DEFAULT_IDMODELOEXCLUSIVO.toString())))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO.toString())));
    }

    @Test
    @Transactional
    public void getTBSessao() throws Exception {
        // Initialize the database
        tBSessaoRepository.saveAndFlush(tBSessao);

        // Get the tBSessao
        restTBSessaoMockMvc.perform(get("/api/t-b-sessaos/{id}", tBSessao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tBSessao.getId().intValue()))
            .andExpect(jsonPath("$.idsessao").value(DEFAULT_IDSESSAO.intValue()))
            .andExpect(jsonPath("$.idmodeloexclusivo").value(DEFAULT_IDMODELOEXCLUSIVO.toString()))
            .andExpect(jsonPath("$.tipo").value(DEFAULT_TIPO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTBSessao() throws Exception {
        // Get the tBSessao
        restTBSessaoMockMvc.perform(get("/api/t-b-sessaos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTBSessao() throws Exception {
        // Initialize the database
        tBSessaoRepository.saveAndFlush(tBSessao);
        tBSessaoSearchRepository.save(tBSessao);
        int databaseSizeBeforeUpdate = tBSessaoRepository.findAll().size();

        // Update the tBSessao
        TBSessao updatedTBSessao = tBSessaoRepository.findOne(tBSessao.getId());
        updatedTBSessao
                .idsessao(UPDATED_IDSESSAO)
                .idmodeloexclusivo(UPDATED_IDMODELOEXCLUSIVO)
                .tipo(UPDATED_TIPO);

        restTBSessaoMockMvc.perform(put("/api/t-b-sessaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTBSessao)))
            .andExpect(status().isOk());

        // Validate the TBSessao in the database
        List<TBSessao> tBSessaoList = tBSessaoRepository.findAll();
        assertThat(tBSessaoList).hasSize(databaseSizeBeforeUpdate);
        TBSessao testTBSessao = tBSessaoList.get(tBSessaoList.size() - 1);
        assertThat(testTBSessao.getIdsessao()).isEqualTo(UPDATED_IDSESSAO);
        assertThat(testTBSessao.getIdmodeloexclusivo()).isEqualTo(UPDATED_IDMODELOEXCLUSIVO);
        assertThat(testTBSessao.getTipo()).isEqualTo(UPDATED_TIPO);

        // Validate the TBSessao in Elasticsearch
        TBSessao tBSessaoEs = tBSessaoSearchRepository.findOne(testTBSessao.getId());
        assertThat(tBSessaoEs).isEqualToComparingFieldByField(testTBSessao);
    }

    @Test
    @Transactional
    public void updateNonExistingTBSessao() throws Exception {
        int databaseSizeBeforeUpdate = tBSessaoRepository.findAll().size();

        // Create the TBSessao

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTBSessaoMockMvc.perform(put("/api/t-b-sessaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tBSessao)))
            .andExpect(status().isCreated());

        // Validate the TBSessao in the database
        List<TBSessao> tBSessaoList = tBSessaoRepository.findAll();
        assertThat(tBSessaoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTBSessao() throws Exception {
        // Initialize the database
        tBSessaoRepository.saveAndFlush(tBSessao);
        tBSessaoSearchRepository.save(tBSessao);
        int databaseSizeBeforeDelete = tBSessaoRepository.findAll().size();

        // Get the tBSessao
        restTBSessaoMockMvc.perform(delete("/api/t-b-sessaos/{id}", tBSessao.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean tBSessaoExistsInEs = tBSessaoSearchRepository.exists(tBSessao.getId());
        assertThat(tBSessaoExistsInEs).isFalse();

        // Validate the database is empty
        List<TBSessao> tBSessaoList = tBSessaoRepository.findAll();
        assertThat(tBSessaoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchTBSessao() throws Exception {
        // Initialize the database
        tBSessaoRepository.saveAndFlush(tBSessao);
        tBSessaoSearchRepository.save(tBSessao);

        // Search the tBSessao
        restTBSessaoMockMvc.perform(get("/api/_search/t-b-sessaos?query=id:" + tBSessao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tBSessao.getId().intValue())))
            .andExpect(jsonPath("$.[*].idsessao").value(hasItem(DEFAULT_IDSESSAO.intValue())))
            .andExpect(jsonPath("$.[*].idmodeloexclusivo").value(hasItem(DEFAULT_IDMODELOEXCLUSIVO.toString())))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO.toString())));
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TBSessao.class);
    }
}
