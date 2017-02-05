package com.mfernandes.app.web.rest;

import com.mfernandes.app.Projeto1App;

import com.mfernandes.app.domain.TBUsuario;
import com.mfernandes.app.repository.TBUsuarioRepository;
import com.mfernandes.app.repository.search.TBUsuarioSearchRepository;

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
 * Test class for the TBUsuarioResource REST controller.
 *
 * @see TBUsuarioResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Projeto1App.class)
public class TBUsuarioResourceIntTest {

    private static final Long DEFAULT_IDUSUARIO = 1L;
    private static final Long UPDATED_IDUSUARIO = 2L;

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_SENHA = "AAAAAAAAAA";
    private static final String UPDATED_SENHA = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_DIRETORIO = "AAAAAAAAAA";
    private static final String UPDATED_DIRETORIO = "BBBBBBBBBB";

    @Autowired
    private TBUsuarioRepository tBUsuarioRepository;

    @Autowired
    private TBUsuarioSearchRepository tBUsuarioSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private EntityManager em;

    private MockMvc restTBUsuarioMockMvc;

    private TBUsuario tBUsuario;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
            TBUsuarioResource tBUsuarioResource = new TBUsuarioResource(tBUsuarioRepository, tBUsuarioSearchRepository);
        this.restTBUsuarioMockMvc = MockMvcBuilders.standaloneSetup(tBUsuarioResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TBUsuario createEntity(EntityManager em) {
        TBUsuario tBUsuario = new TBUsuario()
                .idusuario(DEFAULT_IDUSUARIO)
                .nome(DEFAULT_NOME)
                .senha(DEFAULT_SENHA)
                .email(DEFAULT_EMAIL)
                .diretorio(DEFAULT_DIRETORIO);
        return tBUsuario;
    }

    @Before
    public void initTest() {
        tBUsuarioSearchRepository.deleteAll();
        tBUsuario = createEntity(em);
    }

    @Test
    @Transactional
    public void createTBUsuario() throws Exception {
        int databaseSizeBeforeCreate = tBUsuarioRepository.findAll().size();

        // Create the TBUsuario

        restTBUsuarioMockMvc.perform(post("/api/t-b-usuarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tBUsuario)))
            .andExpect(status().isCreated());

        // Validate the TBUsuario in the database
        List<TBUsuario> tBUsuarioList = tBUsuarioRepository.findAll();
        assertThat(tBUsuarioList).hasSize(databaseSizeBeforeCreate + 1);
        TBUsuario testTBUsuario = tBUsuarioList.get(tBUsuarioList.size() - 1);
        assertThat(testTBUsuario.getIdusuario()).isEqualTo(DEFAULT_IDUSUARIO);
        assertThat(testTBUsuario.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testTBUsuario.getSenha()).isEqualTo(DEFAULT_SENHA);
        assertThat(testTBUsuario.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testTBUsuario.getDiretorio()).isEqualTo(DEFAULT_DIRETORIO);

        // Validate the TBUsuario in Elasticsearch
        TBUsuario tBUsuarioEs = tBUsuarioSearchRepository.findOne(testTBUsuario.getId());
        assertThat(tBUsuarioEs).isEqualToComparingFieldByField(testTBUsuario);
    }

    @Test
    @Transactional
    public void createTBUsuarioWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tBUsuarioRepository.findAll().size();

        // Create the TBUsuario with an existing ID
        TBUsuario existingTBUsuario = new TBUsuario();
        existingTBUsuario.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTBUsuarioMockMvc.perform(post("/api/t-b-usuarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingTBUsuario)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<TBUsuario> tBUsuarioList = tBUsuarioRepository.findAll();
        assertThat(tBUsuarioList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkIdusuarioIsRequired() throws Exception {
        int databaseSizeBeforeTest = tBUsuarioRepository.findAll().size();
        // set the field null
        tBUsuario.setIdusuario(null);

        // Create the TBUsuario, which fails.

        restTBUsuarioMockMvc.perform(post("/api/t-b-usuarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tBUsuario)))
            .andExpect(status().isBadRequest());

        List<TBUsuario> tBUsuarioList = tBUsuarioRepository.findAll();
        assertThat(tBUsuarioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTBUsuarios() throws Exception {
        // Initialize the database
        tBUsuarioRepository.saveAndFlush(tBUsuario);

        // Get all the tBUsuarioList
        restTBUsuarioMockMvc.perform(get("/api/t-b-usuarios?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tBUsuario.getId().intValue())))
            .andExpect(jsonPath("$.[*].idusuario").value(hasItem(DEFAULT_IDUSUARIO.intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].senha").value(hasItem(DEFAULT_SENHA.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].diretorio").value(hasItem(DEFAULT_DIRETORIO.toString())));
    }

    @Test
    @Transactional
    public void getTBUsuario() throws Exception {
        // Initialize the database
        tBUsuarioRepository.saveAndFlush(tBUsuario);

        // Get the tBUsuario
        restTBUsuarioMockMvc.perform(get("/api/t-b-usuarios/{id}", tBUsuario.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tBUsuario.getId().intValue()))
            .andExpect(jsonPath("$.idusuario").value(DEFAULT_IDUSUARIO.intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()))
            .andExpect(jsonPath("$.senha").value(DEFAULT_SENHA.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.diretorio").value(DEFAULT_DIRETORIO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTBUsuario() throws Exception {
        // Get the tBUsuario
        restTBUsuarioMockMvc.perform(get("/api/t-b-usuarios/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTBUsuario() throws Exception {
        // Initialize the database
        tBUsuarioRepository.saveAndFlush(tBUsuario);
        tBUsuarioSearchRepository.save(tBUsuario);
        int databaseSizeBeforeUpdate = tBUsuarioRepository.findAll().size();

        // Update the tBUsuario
        TBUsuario updatedTBUsuario = tBUsuarioRepository.findOne(tBUsuario.getId());
        updatedTBUsuario
                .idusuario(UPDATED_IDUSUARIO)
                .nome(UPDATED_NOME)
                .senha(UPDATED_SENHA)
                .email(UPDATED_EMAIL)
                .diretorio(UPDATED_DIRETORIO);

        restTBUsuarioMockMvc.perform(put("/api/t-b-usuarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTBUsuario)))
            .andExpect(status().isOk());

        // Validate the TBUsuario in the database
        List<TBUsuario> tBUsuarioList = tBUsuarioRepository.findAll();
        assertThat(tBUsuarioList).hasSize(databaseSizeBeforeUpdate);
        TBUsuario testTBUsuario = tBUsuarioList.get(tBUsuarioList.size() - 1);
        assertThat(testTBUsuario.getIdusuario()).isEqualTo(UPDATED_IDUSUARIO);
        assertThat(testTBUsuario.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testTBUsuario.getSenha()).isEqualTo(UPDATED_SENHA);
        assertThat(testTBUsuario.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testTBUsuario.getDiretorio()).isEqualTo(UPDATED_DIRETORIO);

        // Validate the TBUsuario in Elasticsearch
        TBUsuario tBUsuarioEs = tBUsuarioSearchRepository.findOne(testTBUsuario.getId());
        assertThat(tBUsuarioEs).isEqualToComparingFieldByField(testTBUsuario);
    }

    @Test
    @Transactional
    public void updateNonExistingTBUsuario() throws Exception {
        int databaseSizeBeforeUpdate = tBUsuarioRepository.findAll().size();

        // Create the TBUsuario

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTBUsuarioMockMvc.perform(put("/api/t-b-usuarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tBUsuario)))
            .andExpect(status().isCreated());

        // Validate the TBUsuario in the database
        List<TBUsuario> tBUsuarioList = tBUsuarioRepository.findAll();
        assertThat(tBUsuarioList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTBUsuario() throws Exception {
        // Initialize the database
        tBUsuarioRepository.saveAndFlush(tBUsuario);
        tBUsuarioSearchRepository.save(tBUsuario);
        int databaseSizeBeforeDelete = tBUsuarioRepository.findAll().size();

        // Get the tBUsuario
        restTBUsuarioMockMvc.perform(delete("/api/t-b-usuarios/{id}", tBUsuario.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean tBUsuarioExistsInEs = tBUsuarioSearchRepository.exists(tBUsuario.getId());
        assertThat(tBUsuarioExistsInEs).isFalse();

        // Validate the database is empty
        List<TBUsuario> tBUsuarioList = tBUsuarioRepository.findAll();
        assertThat(tBUsuarioList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchTBUsuario() throws Exception {
        // Initialize the database
        tBUsuarioRepository.saveAndFlush(tBUsuario);
        tBUsuarioSearchRepository.save(tBUsuario);

        // Search the tBUsuario
        restTBUsuarioMockMvc.perform(get("/api/_search/t-b-usuarios?query=id:" + tBUsuario.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tBUsuario.getId().intValue())))
            .andExpect(jsonPath("$.[*].idusuario").value(hasItem(DEFAULT_IDUSUARIO.intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].senha").value(hasItem(DEFAULT_SENHA.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].diretorio").value(hasItem(DEFAULT_DIRETORIO.toString())));
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TBUsuario.class);
    }
}
