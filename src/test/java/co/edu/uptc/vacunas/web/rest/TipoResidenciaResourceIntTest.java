package co.edu.uptc.vacunas.web.rest;

import co.edu.uptc.vacunas.VacunasApp;

import co.edu.uptc.vacunas.domain.TipoResidencia;
import co.edu.uptc.vacunas.repository.TipoResidenciaRepository;
import co.edu.uptc.vacunas.service.dto.TipoResidenciaDTO;
import co.edu.uptc.vacunas.service.mapper.TipoResidenciaMapper;
import co.edu.uptc.vacunas.web.rest.errors.ExceptionTranslator;

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

import static co.edu.uptc.vacunas.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the TipoResidenciaResource REST controller.
 *
 * @see TipoResidenciaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VacunasApp.class)
public class TipoResidenciaResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    @Autowired
    private TipoResidenciaRepository tipoResidenciaRepository;

    @Autowired
    private TipoResidenciaMapper tipoResidenciaMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTipoResidenciaMockMvc;

    private TipoResidencia tipoResidencia;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TipoResidenciaResource tipoResidenciaResource = new TipoResidenciaResource(tipoResidenciaRepository, tipoResidenciaMapper);
        this.restTipoResidenciaMockMvc = MockMvcBuilders.standaloneSetup(tipoResidenciaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipoResidencia createEntity(EntityManager em) {
        TipoResidencia tipoResidencia = new TipoResidencia()
            .nombre(DEFAULT_NOMBRE);
        return tipoResidencia;
    }

    @Before
    public void initTest() {
        tipoResidencia = createEntity(em);
    }

    @Test
    @Transactional
    public void createTipoResidencia() throws Exception {
        int databaseSizeBeforeCreate = tipoResidenciaRepository.findAll().size();

        // Create the TipoResidencia
        TipoResidenciaDTO tipoResidenciaDTO = tipoResidenciaMapper.toDto(tipoResidencia);
        restTipoResidenciaMockMvc.perform(post("/api/tipo-residencias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoResidenciaDTO)))
            .andExpect(status().isCreated());

        // Validate the TipoResidencia in the database
        List<TipoResidencia> tipoResidenciaList = tipoResidenciaRepository.findAll();
        assertThat(tipoResidenciaList).hasSize(databaseSizeBeforeCreate + 1);
        TipoResidencia testTipoResidencia = tipoResidenciaList.get(tipoResidenciaList.size() - 1);
        assertThat(testTipoResidencia.getNombre()).isEqualTo(DEFAULT_NOMBRE);
    }

    @Test
    @Transactional
    public void createTipoResidenciaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tipoResidenciaRepository.findAll().size();

        // Create the TipoResidencia with an existing ID
        tipoResidencia.setId(1L);
        TipoResidenciaDTO tipoResidenciaDTO = tipoResidenciaMapper.toDto(tipoResidencia);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTipoResidenciaMockMvc.perform(post("/api/tipo-residencias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoResidenciaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TipoResidencia in the database
        List<TipoResidencia> tipoResidenciaList = tipoResidenciaRepository.findAll();
        assertThat(tipoResidenciaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = tipoResidenciaRepository.findAll().size();
        // set the field null
        tipoResidencia.setNombre(null);

        // Create the TipoResidencia, which fails.
        TipoResidenciaDTO tipoResidenciaDTO = tipoResidenciaMapper.toDto(tipoResidencia);

        restTipoResidenciaMockMvc.perform(post("/api/tipo-residencias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoResidenciaDTO)))
            .andExpect(status().isBadRequest());

        List<TipoResidencia> tipoResidenciaList = tipoResidenciaRepository.findAll();
        assertThat(tipoResidenciaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTipoResidencias() throws Exception {
        // Initialize the database
        tipoResidenciaRepository.saveAndFlush(tipoResidencia);

        // Get all the tipoResidenciaList
        restTipoResidenciaMockMvc.perform(get("/api/tipo-residencias?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoResidencia.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())));
    }

    @Test
    @Transactional
    public void getTipoResidencia() throws Exception {
        // Initialize the database
        tipoResidenciaRepository.saveAndFlush(tipoResidencia);

        // Get the tipoResidencia
        restTipoResidenciaMockMvc.perform(get("/api/tipo-residencias/{id}", tipoResidencia.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tipoResidencia.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTipoResidencia() throws Exception {
        // Get the tipoResidencia
        restTipoResidenciaMockMvc.perform(get("/api/tipo-residencias/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTipoResidencia() throws Exception {
        // Initialize the database
        tipoResidenciaRepository.saveAndFlush(tipoResidencia);
        int databaseSizeBeforeUpdate = tipoResidenciaRepository.findAll().size();

        // Update the tipoResidencia
        TipoResidencia updatedTipoResidencia = tipoResidenciaRepository.findOne(tipoResidencia.getId());
        // Disconnect from session so that the updates on updatedTipoResidencia are not directly saved in db
        em.detach(updatedTipoResidencia);
        updatedTipoResidencia
            .nombre(UPDATED_NOMBRE);
        TipoResidenciaDTO tipoResidenciaDTO = tipoResidenciaMapper.toDto(updatedTipoResidencia);

        restTipoResidenciaMockMvc.perform(put("/api/tipo-residencias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoResidenciaDTO)))
            .andExpect(status().isOk());

        // Validate the TipoResidencia in the database
        List<TipoResidencia> tipoResidenciaList = tipoResidenciaRepository.findAll();
        assertThat(tipoResidenciaList).hasSize(databaseSizeBeforeUpdate);
        TipoResidencia testTipoResidencia = tipoResidenciaList.get(tipoResidenciaList.size() - 1);
        assertThat(testTipoResidencia.getNombre()).isEqualTo(UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void updateNonExistingTipoResidencia() throws Exception {
        int databaseSizeBeforeUpdate = tipoResidenciaRepository.findAll().size();

        // Create the TipoResidencia
        TipoResidenciaDTO tipoResidenciaDTO = tipoResidenciaMapper.toDto(tipoResidencia);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTipoResidenciaMockMvc.perform(put("/api/tipo-residencias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoResidenciaDTO)))
            .andExpect(status().isCreated());

        // Validate the TipoResidencia in the database
        List<TipoResidencia> tipoResidenciaList = tipoResidenciaRepository.findAll();
        assertThat(tipoResidenciaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTipoResidencia() throws Exception {
        // Initialize the database
        tipoResidenciaRepository.saveAndFlush(tipoResidencia);
        int databaseSizeBeforeDelete = tipoResidenciaRepository.findAll().size();

        // Get the tipoResidencia
        restTipoResidenciaMockMvc.perform(delete("/api/tipo-residencias/{id}", tipoResidencia.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TipoResidencia> tipoResidenciaList = tipoResidenciaRepository.findAll();
        assertThat(tipoResidenciaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoResidencia.class);
        TipoResidencia tipoResidencia1 = new TipoResidencia();
        tipoResidencia1.setId(1L);
        TipoResidencia tipoResidencia2 = new TipoResidencia();
        tipoResidencia2.setId(tipoResidencia1.getId());
        assertThat(tipoResidencia1).isEqualTo(tipoResidencia2);
        tipoResidencia2.setId(2L);
        assertThat(tipoResidencia1).isNotEqualTo(tipoResidencia2);
        tipoResidencia1.setId(null);
        assertThat(tipoResidencia1).isNotEqualTo(tipoResidencia2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoResidenciaDTO.class);
        TipoResidenciaDTO tipoResidenciaDTO1 = new TipoResidenciaDTO();
        tipoResidenciaDTO1.setId(1L);
        TipoResidenciaDTO tipoResidenciaDTO2 = new TipoResidenciaDTO();
        assertThat(tipoResidenciaDTO1).isNotEqualTo(tipoResidenciaDTO2);
        tipoResidenciaDTO2.setId(tipoResidenciaDTO1.getId());
        assertThat(tipoResidenciaDTO1).isEqualTo(tipoResidenciaDTO2);
        tipoResidenciaDTO2.setId(2L);
        assertThat(tipoResidenciaDTO1).isNotEqualTo(tipoResidenciaDTO2);
        tipoResidenciaDTO1.setId(null);
        assertThat(tipoResidenciaDTO1).isNotEqualTo(tipoResidenciaDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(tipoResidenciaMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(tipoResidenciaMapper.fromId(null)).isNull();
    }
}
