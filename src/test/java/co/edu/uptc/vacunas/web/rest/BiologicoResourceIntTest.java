package co.edu.uptc.vacunas.web.rest;

import co.edu.uptc.vacunas.VacunasApp;

import co.edu.uptc.vacunas.domain.Biologico;
import co.edu.uptc.vacunas.repository.BiologicoRepository;
import co.edu.uptc.vacunas.service.dto.BiologicoDTO;
import co.edu.uptc.vacunas.service.mapper.BiologicoMapper;
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
 * Test class for the BiologicoResource REST controller.
 *
 * @see BiologicoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VacunasApp.class)
public class BiologicoResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_LOTE = "AAAAAAAAAA";
    private static final String UPDATED_LOTE = "BBBBBBBBBB";

    private static final String DEFAULT_LOTE_JERINGA = "AAAAAAAAAA";
    private static final String UPDATED_LOTE_JERINGA = "BBBBBBBBBB";

    @Autowired
    private BiologicoRepository biologicoRepository;

    @Autowired
    private BiologicoMapper biologicoMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBiologicoMockMvc;

    private Biologico biologico;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BiologicoResource biologicoResource = new BiologicoResource(biologicoRepository, biologicoMapper);
        this.restBiologicoMockMvc = MockMvcBuilders.standaloneSetup(biologicoResource)
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
    public static Biologico createEntity(EntityManager em) {
        Biologico biologico = new Biologico()
            .nombre(DEFAULT_NOMBRE)
            .lote(DEFAULT_LOTE)
            .loteJeringa(DEFAULT_LOTE_JERINGA);
        return biologico;
    }

    @Before
    public void initTest() {
        biologico = createEntity(em);
    }

    @Test
    @Transactional
    public void createBiologico() throws Exception {
        int databaseSizeBeforeCreate = biologicoRepository.findAll().size();

        // Create the Biologico
        BiologicoDTO biologicoDTO = biologicoMapper.toDto(biologico);
        restBiologicoMockMvc.perform(post("/api/biologicos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(biologicoDTO)))
            .andExpect(status().isCreated());

        // Validate the Biologico in the database
        List<Biologico> biologicoList = biologicoRepository.findAll();
        assertThat(biologicoList).hasSize(databaseSizeBeforeCreate + 1);
        Biologico testBiologico = biologicoList.get(biologicoList.size() - 1);
        assertThat(testBiologico.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testBiologico.getLote()).isEqualTo(DEFAULT_LOTE);
        assertThat(testBiologico.getLoteJeringa()).isEqualTo(DEFAULT_LOTE_JERINGA);
    }

    @Test
    @Transactional
    public void createBiologicoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = biologicoRepository.findAll().size();

        // Create the Biologico with an existing ID
        biologico.setId(1L);
        BiologicoDTO biologicoDTO = biologicoMapper.toDto(biologico);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBiologicoMockMvc.perform(post("/api/biologicos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(biologicoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Biologico in the database
        List<Biologico> biologicoList = biologicoRepository.findAll();
        assertThat(biologicoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = biologicoRepository.findAll().size();
        // set the field null
        biologico.setNombre(null);

        // Create the Biologico, which fails.
        BiologicoDTO biologicoDTO = biologicoMapper.toDto(biologico);

        restBiologicoMockMvc.perform(post("/api/biologicos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(biologicoDTO)))
            .andExpect(status().isBadRequest());

        List<Biologico> biologicoList = biologicoRepository.findAll();
        assertThat(biologicoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLoteIsRequired() throws Exception {
        int databaseSizeBeforeTest = biologicoRepository.findAll().size();
        // set the field null
        biologico.setLote(null);

        // Create the Biologico, which fails.
        BiologicoDTO biologicoDTO = biologicoMapper.toDto(biologico);

        restBiologicoMockMvc.perform(post("/api/biologicos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(biologicoDTO)))
            .andExpect(status().isBadRequest());

        List<Biologico> biologicoList = biologicoRepository.findAll();
        assertThat(biologicoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLoteJeringaIsRequired() throws Exception {
        int databaseSizeBeforeTest = biologicoRepository.findAll().size();
        // set the field null
        biologico.setLoteJeringa(null);

        // Create the Biologico, which fails.
        BiologicoDTO biologicoDTO = biologicoMapper.toDto(biologico);

        restBiologicoMockMvc.perform(post("/api/biologicos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(biologicoDTO)))
            .andExpect(status().isBadRequest());

        List<Biologico> biologicoList = biologicoRepository.findAll();
        assertThat(biologicoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBiologicos() throws Exception {
        // Initialize the database
        biologicoRepository.saveAndFlush(biologico);

        // Get all the biologicoList
        restBiologicoMockMvc.perform(get("/api/biologicos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(biologico.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].lote").value(hasItem(DEFAULT_LOTE.toString())))
            .andExpect(jsonPath("$.[*].loteJeringa").value(hasItem(DEFAULT_LOTE_JERINGA.toString())));
    }

    @Test
    @Transactional
    public void getBiologico() throws Exception {
        // Initialize the database
        biologicoRepository.saveAndFlush(biologico);

        // Get the biologico
        restBiologicoMockMvc.perform(get("/api/biologicos/{id}", biologico.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(biologico.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.lote").value(DEFAULT_LOTE.toString()))
            .andExpect(jsonPath("$.loteJeringa").value(DEFAULT_LOTE_JERINGA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingBiologico() throws Exception {
        // Get the biologico
        restBiologicoMockMvc.perform(get("/api/biologicos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBiologico() throws Exception {
        // Initialize the database
        biologicoRepository.saveAndFlush(biologico);
        int databaseSizeBeforeUpdate = biologicoRepository.findAll().size();

        // Update the biologico
        Biologico updatedBiologico = biologicoRepository.findOne(biologico.getId());
        // Disconnect from session so that the updates on updatedBiologico are not directly saved in db
        em.detach(updatedBiologico);
        updatedBiologico
            .nombre(UPDATED_NOMBRE)
            .lote(UPDATED_LOTE)
            .loteJeringa(UPDATED_LOTE_JERINGA);
        BiologicoDTO biologicoDTO = biologicoMapper.toDto(updatedBiologico);

        restBiologicoMockMvc.perform(put("/api/biologicos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(biologicoDTO)))
            .andExpect(status().isOk());

        // Validate the Biologico in the database
        List<Biologico> biologicoList = biologicoRepository.findAll();
        assertThat(biologicoList).hasSize(databaseSizeBeforeUpdate);
        Biologico testBiologico = biologicoList.get(biologicoList.size() - 1);
        assertThat(testBiologico.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testBiologico.getLote()).isEqualTo(UPDATED_LOTE);
        assertThat(testBiologico.getLoteJeringa()).isEqualTo(UPDATED_LOTE_JERINGA);
    }

    @Test
    @Transactional
    public void updateNonExistingBiologico() throws Exception {
        int databaseSizeBeforeUpdate = biologicoRepository.findAll().size();

        // Create the Biologico
        BiologicoDTO biologicoDTO = biologicoMapper.toDto(biologico);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBiologicoMockMvc.perform(put("/api/biologicos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(biologicoDTO)))
            .andExpect(status().isCreated());

        // Validate the Biologico in the database
        List<Biologico> biologicoList = biologicoRepository.findAll();
        assertThat(biologicoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteBiologico() throws Exception {
        // Initialize the database
        biologicoRepository.saveAndFlush(biologico);
        int databaseSizeBeforeDelete = biologicoRepository.findAll().size();

        // Get the biologico
        restBiologicoMockMvc.perform(delete("/api/biologicos/{id}", biologico.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Biologico> biologicoList = biologicoRepository.findAll();
        assertThat(biologicoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Biologico.class);
        Biologico biologico1 = new Biologico();
        biologico1.setId(1L);
        Biologico biologico2 = new Biologico();
        biologico2.setId(biologico1.getId());
        assertThat(biologico1).isEqualTo(biologico2);
        biologico2.setId(2L);
        assertThat(biologico1).isNotEqualTo(biologico2);
        biologico1.setId(null);
        assertThat(biologico1).isNotEqualTo(biologico2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BiologicoDTO.class);
        BiologicoDTO biologicoDTO1 = new BiologicoDTO();
        biologicoDTO1.setId(1L);
        BiologicoDTO biologicoDTO2 = new BiologicoDTO();
        assertThat(biologicoDTO1).isNotEqualTo(biologicoDTO2);
        biologicoDTO2.setId(biologicoDTO1.getId());
        assertThat(biologicoDTO1).isEqualTo(biologicoDTO2);
        biologicoDTO2.setId(2L);
        assertThat(biologicoDTO1).isNotEqualTo(biologicoDTO2);
        biologicoDTO1.setId(null);
        assertThat(biologicoDTO1).isNotEqualTo(biologicoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(biologicoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(biologicoMapper.fromId(null)).isNull();
    }
}
