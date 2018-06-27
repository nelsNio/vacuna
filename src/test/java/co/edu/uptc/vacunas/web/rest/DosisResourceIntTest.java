package co.edu.uptc.vacunas.web.rest;

import co.edu.uptc.vacunas.VacunasApp;

import co.edu.uptc.vacunas.domain.Dosis;
import co.edu.uptc.vacunas.domain.Biologico;
import co.edu.uptc.vacunas.repository.DosisRepository;
import co.edu.uptc.vacunas.service.dto.DosisDTO;
import co.edu.uptc.vacunas.service.mapper.DosisMapper;
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
 * Test class for the DosisResource REST controller.
 *
 * @see DosisResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VacunasApp.class)
public class DosisResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final Integer DEFAULT_EDAD_MINIMA = 1;
    private static final Integer UPDATED_EDAD_MINIMA = 2;

    private static final Integer DEFAULT_EDAD_MAXIMA = 1;
    private static final Integer UPDATED_EDAD_MAXIMA = 2;

    private static final Boolean DEFAULT_EMBARAZO = false;
    private static final Boolean UPDATED_EMBARAZO = true;

    @Autowired
    private DosisRepository dosisRepository;

    @Autowired
    private DosisMapper dosisMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDosisMockMvc;

    private Dosis dosis;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DosisResource dosisResource = new DosisResource(dosisRepository, dosisMapper);
        this.restDosisMockMvc = MockMvcBuilders.standaloneSetup(dosisResource)
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
    public static Dosis createEntity(EntityManager em) {
        Dosis dosis = new Dosis()
            .nombre(DEFAULT_NOMBRE)
            .edadMinima(DEFAULT_EDAD_MINIMA)
            .edadMaxima(DEFAULT_EDAD_MAXIMA)
            .embarazo(DEFAULT_EMBARAZO);
        // Add required entity
        Biologico biologico = BiologicoResourceIntTest.createEntity(em);
        em.persist(biologico);
        em.flush();
        dosis.setBiologico(biologico);
        return dosis;
    }

    @Before
    public void initTest() {
        dosis = createEntity(em);
    }

    @Test
    @Transactional
    public void createDosis() throws Exception {
        int databaseSizeBeforeCreate = dosisRepository.findAll().size();

        // Create the Dosis
        DosisDTO dosisDTO = dosisMapper.toDto(dosis);
        restDosisMockMvc.perform(post("/api/doses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dosisDTO)))
            .andExpect(status().isCreated());

        // Validate the Dosis in the database
        List<Dosis> dosisList = dosisRepository.findAll();
        assertThat(dosisList).hasSize(databaseSizeBeforeCreate + 1);
        Dosis testDosis = dosisList.get(dosisList.size() - 1);
        assertThat(testDosis.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testDosis.getEdadMinima()).isEqualTo(DEFAULT_EDAD_MINIMA);
        assertThat(testDosis.getEdadMaxima()).isEqualTo(DEFAULT_EDAD_MAXIMA);
        assertThat(testDosis.isEmbarazo()).isEqualTo(DEFAULT_EMBARAZO);
    }

    @Test
    @Transactional
    public void createDosisWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = dosisRepository.findAll().size();

        // Create the Dosis with an existing ID
        dosis.setId(1L);
        DosisDTO dosisDTO = dosisMapper.toDto(dosis);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDosisMockMvc.perform(post("/api/doses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dosisDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Dosis in the database
        List<Dosis> dosisList = dosisRepository.findAll();
        assertThat(dosisList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = dosisRepository.findAll().size();
        // set the field null
        dosis.setNombre(null);

        // Create the Dosis, which fails.
        DosisDTO dosisDTO = dosisMapper.toDto(dosis);

        restDosisMockMvc.perform(post("/api/doses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dosisDTO)))
            .andExpect(status().isBadRequest());

        List<Dosis> dosisList = dosisRepository.findAll();
        assertThat(dosisList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEdadMinimaIsRequired() throws Exception {
        int databaseSizeBeforeTest = dosisRepository.findAll().size();
        // set the field null
        dosis.setEdadMinima(null);

        // Create the Dosis, which fails.
        DosisDTO dosisDTO = dosisMapper.toDto(dosis);

        restDosisMockMvc.perform(post("/api/doses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dosisDTO)))
            .andExpect(status().isBadRequest());

        List<Dosis> dosisList = dosisRepository.findAll();
        assertThat(dosisList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEdadMaximaIsRequired() throws Exception {
        int databaseSizeBeforeTest = dosisRepository.findAll().size();
        // set the field null
        dosis.setEdadMaxima(null);

        // Create the Dosis, which fails.
        DosisDTO dosisDTO = dosisMapper.toDto(dosis);

        restDosisMockMvc.perform(post("/api/doses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dosisDTO)))
            .andExpect(status().isBadRequest());

        List<Dosis> dosisList = dosisRepository.findAll();
        assertThat(dosisList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmbarazoIsRequired() throws Exception {
        int databaseSizeBeforeTest = dosisRepository.findAll().size();
        // set the field null
        dosis.setEmbarazo(null);

        // Create the Dosis, which fails.
        DosisDTO dosisDTO = dosisMapper.toDto(dosis);

        restDosisMockMvc.perform(post("/api/doses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dosisDTO)))
            .andExpect(status().isBadRequest());

        List<Dosis> dosisList = dosisRepository.findAll();
        assertThat(dosisList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDoses() throws Exception {
        // Initialize the database
        dosisRepository.saveAndFlush(dosis);

        // Get all the dosisList
        restDosisMockMvc.perform(get("/api/doses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dosis.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].edadMinima").value(hasItem(DEFAULT_EDAD_MINIMA)))
            .andExpect(jsonPath("$.[*].edadMaxima").value(hasItem(DEFAULT_EDAD_MAXIMA)))
            .andExpect(jsonPath("$.[*].embarazo").value(hasItem(DEFAULT_EMBARAZO.booleanValue())));
    }

    @Test
    @Transactional
    public void getDosis() throws Exception {
        // Initialize the database
        dosisRepository.saveAndFlush(dosis);

        // Get the dosis
        restDosisMockMvc.perform(get("/api/doses/{id}", dosis.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(dosis.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.edadMinima").value(DEFAULT_EDAD_MINIMA))
            .andExpect(jsonPath("$.edadMaxima").value(DEFAULT_EDAD_MAXIMA))
            .andExpect(jsonPath("$.embarazo").value(DEFAULT_EMBARAZO.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingDosis() throws Exception {
        // Get the dosis
        restDosisMockMvc.perform(get("/api/doses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDosis() throws Exception {
        // Initialize the database
        dosisRepository.saveAndFlush(dosis);
        int databaseSizeBeforeUpdate = dosisRepository.findAll().size();

        // Update the dosis
        Dosis updatedDosis = dosisRepository.findOne(dosis.getId());
        // Disconnect from session so that the updates on updatedDosis are not directly saved in db
        em.detach(updatedDosis);
        updatedDosis
            .nombre(UPDATED_NOMBRE)
            .edadMinima(UPDATED_EDAD_MINIMA)
            .edadMaxima(UPDATED_EDAD_MAXIMA)
            .embarazo(UPDATED_EMBARAZO);
        DosisDTO dosisDTO = dosisMapper.toDto(updatedDosis);

        restDosisMockMvc.perform(put("/api/doses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dosisDTO)))
            .andExpect(status().isOk());

        // Validate the Dosis in the database
        List<Dosis> dosisList = dosisRepository.findAll();
        assertThat(dosisList).hasSize(databaseSizeBeforeUpdate);
        Dosis testDosis = dosisList.get(dosisList.size() - 1);
        assertThat(testDosis.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testDosis.getEdadMinima()).isEqualTo(UPDATED_EDAD_MINIMA);
        assertThat(testDosis.getEdadMaxima()).isEqualTo(UPDATED_EDAD_MAXIMA);
        assertThat(testDosis.isEmbarazo()).isEqualTo(UPDATED_EMBARAZO);
    }

    @Test
    @Transactional
    public void updateNonExistingDosis() throws Exception {
        int databaseSizeBeforeUpdate = dosisRepository.findAll().size();

        // Create the Dosis
        DosisDTO dosisDTO = dosisMapper.toDto(dosis);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDosisMockMvc.perform(put("/api/doses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dosisDTO)))
            .andExpect(status().isCreated());

        // Validate the Dosis in the database
        List<Dosis> dosisList = dosisRepository.findAll();
        assertThat(dosisList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteDosis() throws Exception {
        // Initialize the database
        dosisRepository.saveAndFlush(dosis);
        int databaseSizeBeforeDelete = dosisRepository.findAll().size();

        // Get the dosis
        restDosisMockMvc.perform(delete("/api/doses/{id}", dosis.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Dosis> dosisList = dosisRepository.findAll();
        assertThat(dosisList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Dosis.class);
        Dosis dosis1 = new Dosis();
        dosis1.setId(1L);
        Dosis dosis2 = new Dosis();
        dosis2.setId(dosis1.getId());
        assertThat(dosis1).isEqualTo(dosis2);
        dosis2.setId(2L);
        assertThat(dosis1).isNotEqualTo(dosis2);
        dosis1.setId(null);
        assertThat(dosis1).isNotEqualTo(dosis2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DosisDTO.class);
        DosisDTO dosisDTO1 = new DosisDTO();
        dosisDTO1.setId(1L);
        DosisDTO dosisDTO2 = new DosisDTO();
        assertThat(dosisDTO1).isNotEqualTo(dosisDTO2);
        dosisDTO2.setId(dosisDTO1.getId());
        assertThat(dosisDTO1).isEqualTo(dosisDTO2);
        dosisDTO2.setId(2L);
        assertThat(dosisDTO1).isNotEqualTo(dosisDTO2);
        dosisDTO1.setId(null);
        assertThat(dosisDTO1).isNotEqualTo(dosisDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(dosisMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(dosisMapper.fromId(null)).isNull();
    }
}
