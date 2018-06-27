package co.edu.uptc.vacunas.web.rest;

import co.edu.uptc.vacunas.VacunasApp;

import co.edu.uptc.vacunas.domain.GrupoEtnico;
import co.edu.uptc.vacunas.repository.GrupoEtnicoRepository;
import co.edu.uptc.vacunas.service.dto.GrupoEtnicoDTO;
import co.edu.uptc.vacunas.service.mapper.GrupoEtnicoMapper;
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
 * Test class for the GrupoEtnicoResource REST controller.
 *
 * @see GrupoEtnicoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VacunasApp.class)
public class GrupoEtnicoResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    @Autowired
    private GrupoEtnicoRepository grupoEtnicoRepository;

    @Autowired
    private GrupoEtnicoMapper grupoEtnicoMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restGrupoEtnicoMockMvc;

    private GrupoEtnico grupoEtnico;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final GrupoEtnicoResource grupoEtnicoResource = new GrupoEtnicoResource(grupoEtnicoRepository, grupoEtnicoMapper);
        this.restGrupoEtnicoMockMvc = MockMvcBuilders.standaloneSetup(grupoEtnicoResource)
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
    public static GrupoEtnico createEntity(EntityManager em) {
        GrupoEtnico grupoEtnico = new GrupoEtnico()
            .nombre(DEFAULT_NOMBRE);
        return grupoEtnico;
    }

    @Before
    public void initTest() {
        grupoEtnico = createEntity(em);
    }

    @Test
    @Transactional
    public void createGrupoEtnico() throws Exception {
        int databaseSizeBeforeCreate = grupoEtnicoRepository.findAll().size();

        // Create the GrupoEtnico
        GrupoEtnicoDTO grupoEtnicoDTO = grupoEtnicoMapper.toDto(grupoEtnico);
        restGrupoEtnicoMockMvc.perform(post("/api/grupo-etnicos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(grupoEtnicoDTO)))
            .andExpect(status().isCreated());

        // Validate the GrupoEtnico in the database
        List<GrupoEtnico> grupoEtnicoList = grupoEtnicoRepository.findAll();
        assertThat(grupoEtnicoList).hasSize(databaseSizeBeforeCreate + 1);
        GrupoEtnico testGrupoEtnico = grupoEtnicoList.get(grupoEtnicoList.size() - 1);
        assertThat(testGrupoEtnico.getNombre()).isEqualTo(DEFAULT_NOMBRE);
    }

    @Test
    @Transactional
    public void createGrupoEtnicoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = grupoEtnicoRepository.findAll().size();

        // Create the GrupoEtnico with an existing ID
        grupoEtnico.setId(1L);
        GrupoEtnicoDTO grupoEtnicoDTO = grupoEtnicoMapper.toDto(grupoEtnico);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGrupoEtnicoMockMvc.perform(post("/api/grupo-etnicos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(grupoEtnicoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the GrupoEtnico in the database
        List<GrupoEtnico> grupoEtnicoList = grupoEtnicoRepository.findAll();
        assertThat(grupoEtnicoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = grupoEtnicoRepository.findAll().size();
        // set the field null
        grupoEtnico.setNombre(null);

        // Create the GrupoEtnico, which fails.
        GrupoEtnicoDTO grupoEtnicoDTO = grupoEtnicoMapper.toDto(grupoEtnico);

        restGrupoEtnicoMockMvc.perform(post("/api/grupo-etnicos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(grupoEtnicoDTO)))
            .andExpect(status().isBadRequest());

        List<GrupoEtnico> grupoEtnicoList = grupoEtnicoRepository.findAll();
        assertThat(grupoEtnicoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllGrupoEtnicos() throws Exception {
        // Initialize the database
        grupoEtnicoRepository.saveAndFlush(grupoEtnico);

        // Get all the grupoEtnicoList
        restGrupoEtnicoMockMvc.perform(get("/api/grupo-etnicos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(grupoEtnico.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())));
    }

    @Test
    @Transactional
    public void getGrupoEtnico() throws Exception {
        // Initialize the database
        grupoEtnicoRepository.saveAndFlush(grupoEtnico);

        // Get the grupoEtnico
        restGrupoEtnicoMockMvc.perform(get("/api/grupo-etnicos/{id}", grupoEtnico.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(grupoEtnico.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingGrupoEtnico() throws Exception {
        // Get the grupoEtnico
        restGrupoEtnicoMockMvc.perform(get("/api/grupo-etnicos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGrupoEtnico() throws Exception {
        // Initialize the database
        grupoEtnicoRepository.saveAndFlush(grupoEtnico);
        int databaseSizeBeforeUpdate = grupoEtnicoRepository.findAll().size();

        // Update the grupoEtnico
        GrupoEtnico updatedGrupoEtnico = grupoEtnicoRepository.findOne(grupoEtnico.getId());
        // Disconnect from session so that the updates on updatedGrupoEtnico are not directly saved in db
        em.detach(updatedGrupoEtnico);
        updatedGrupoEtnico
            .nombre(UPDATED_NOMBRE);
        GrupoEtnicoDTO grupoEtnicoDTO = grupoEtnicoMapper.toDto(updatedGrupoEtnico);

        restGrupoEtnicoMockMvc.perform(put("/api/grupo-etnicos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(grupoEtnicoDTO)))
            .andExpect(status().isOk());

        // Validate the GrupoEtnico in the database
        List<GrupoEtnico> grupoEtnicoList = grupoEtnicoRepository.findAll();
        assertThat(grupoEtnicoList).hasSize(databaseSizeBeforeUpdate);
        GrupoEtnico testGrupoEtnico = grupoEtnicoList.get(grupoEtnicoList.size() - 1);
        assertThat(testGrupoEtnico.getNombre()).isEqualTo(UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void updateNonExistingGrupoEtnico() throws Exception {
        int databaseSizeBeforeUpdate = grupoEtnicoRepository.findAll().size();

        // Create the GrupoEtnico
        GrupoEtnicoDTO grupoEtnicoDTO = grupoEtnicoMapper.toDto(grupoEtnico);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restGrupoEtnicoMockMvc.perform(put("/api/grupo-etnicos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(grupoEtnicoDTO)))
            .andExpect(status().isCreated());

        // Validate the GrupoEtnico in the database
        List<GrupoEtnico> grupoEtnicoList = grupoEtnicoRepository.findAll();
        assertThat(grupoEtnicoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteGrupoEtnico() throws Exception {
        // Initialize the database
        grupoEtnicoRepository.saveAndFlush(grupoEtnico);
        int databaseSizeBeforeDelete = grupoEtnicoRepository.findAll().size();

        // Get the grupoEtnico
        restGrupoEtnicoMockMvc.perform(delete("/api/grupo-etnicos/{id}", grupoEtnico.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<GrupoEtnico> grupoEtnicoList = grupoEtnicoRepository.findAll();
        assertThat(grupoEtnicoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GrupoEtnico.class);
        GrupoEtnico grupoEtnico1 = new GrupoEtnico();
        grupoEtnico1.setId(1L);
        GrupoEtnico grupoEtnico2 = new GrupoEtnico();
        grupoEtnico2.setId(grupoEtnico1.getId());
        assertThat(grupoEtnico1).isEqualTo(grupoEtnico2);
        grupoEtnico2.setId(2L);
        assertThat(grupoEtnico1).isNotEqualTo(grupoEtnico2);
        grupoEtnico1.setId(null);
        assertThat(grupoEtnico1).isNotEqualTo(grupoEtnico2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(GrupoEtnicoDTO.class);
        GrupoEtnicoDTO grupoEtnicoDTO1 = new GrupoEtnicoDTO();
        grupoEtnicoDTO1.setId(1L);
        GrupoEtnicoDTO grupoEtnicoDTO2 = new GrupoEtnicoDTO();
        assertThat(grupoEtnicoDTO1).isNotEqualTo(grupoEtnicoDTO2);
        grupoEtnicoDTO2.setId(grupoEtnicoDTO1.getId());
        assertThat(grupoEtnicoDTO1).isEqualTo(grupoEtnicoDTO2);
        grupoEtnicoDTO2.setId(2L);
        assertThat(grupoEtnicoDTO1).isNotEqualTo(grupoEtnicoDTO2);
        grupoEtnicoDTO1.setId(null);
        assertThat(grupoEtnicoDTO1).isNotEqualTo(grupoEtnicoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(grupoEtnicoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(grupoEtnicoMapper.fromId(null)).isNull();
    }
}
