package co.edu.uptc.vacunas.web.rest;

import co.edu.uptc.vacunas.VacunasApp;

import co.edu.uptc.vacunas.domain.Aseguradora;
import co.edu.uptc.vacunas.domain.Regimen;
import co.edu.uptc.vacunas.repository.AseguradoraRepository;
import co.edu.uptc.vacunas.service.dto.AseguradoraDTO;
import co.edu.uptc.vacunas.service.mapper.AseguradoraMapper;
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
 * Test class for the AseguradoraResource REST controller.
 *
 * @see AseguradoraResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VacunasApp.class)
public class AseguradoraResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    @Autowired
    private AseguradoraRepository aseguradoraRepository;

    @Autowired
    private AseguradoraMapper aseguradoraMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAseguradoraMockMvc;

    private Aseguradora aseguradora;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AseguradoraResource aseguradoraResource = new AseguradoraResource(aseguradoraRepository, aseguradoraMapper);
        this.restAseguradoraMockMvc = MockMvcBuilders.standaloneSetup(aseguradoraResource)
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
    public static Aseguradora createEntity(EntityManager em) {
        Aseguradora aseguradora = new Aseguradora()
            .nombre(DEFAULT_NOMBRE);
        // Add required entity
        Regimen regimen = RegimenResourceIntTest.createEntity(em);
        em.persist(regimen);
        em.flush();
        aseguradora.getRegimen().add(regimen);
        return aseguradora;
    }

    @Before
    public void initTest() {
        aseguradora = createEntity(em);
    }

    @Test
    @Transactional
    public void createAseguradora() throws Exception {
        int databaseSizeBeforeCreate = aseguradoraRepository.findAll().size();

        // Create the Aseguradora
        AseguradoraDTO aseguradoraDTO = aseguradoraMapper.toDto(aseguradora);
        restAseguradoraMockMvc.perform(post("/api/aseguradoras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(aseguradoraDTO)))
            .andExpect(status().isCreated());

        // Validate the Aseguradora in the database
        List<Aseguradora> aseguradoraList = aseguradoraRepository.findAll();
        assertThat(aseguradoraList).hasSize(databaseSizeBeforeCreate + 1);
        Aseguradora testAseguradora = aseguradoraList.get(aseguradoraList.size() - 1);
        assertThat(testAseguradora.getNombre()).isEqualTo(DEFAULT_NOMBRE);
    }

    @Test
    @Transactional
    public void createAseguradoraWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = aseguradoraRepository.findAll().size();

        // Create the Aseguradora with an existing ID
        aseguradora.setId(1L);
        AseguradoraDTO aseguradoraDTO = aseguradoraMapper.toDto(aseguradora);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAseguradoraMockMvc.perform(post("/api/aseguradoras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(aseguradoraDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Aseguradora in the database
        List<Aseguradora> aseguradoraList = aseguradoraRepository.findAll();
        assertThat(aseguradoraList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = aseguradoraRepository.findAll().size();
        // set the field null
        aseguradora.setNombre(null);

        // Create the Aseguradora, which fails.
        AseguradoraDTO aseguradoraDTO = aseguradoraMapper.toDto(aseguradora);

        restAseguradoraMockMvc.perform(post("/api/aseguradoras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(aseguradoraDTO)))
            .andExpect(status().isBadRequest());

        List<Aseguradora> aseguradoraList = aseguradoraRepository.findAll();
        assertThat(aseguradoraList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAseguradoras() throws Exception {
        // Initialize the database
        aseguradoraRepository.saveAndFlush(aseguradora);

        // Get all the aseguradoraList
        restAseguradoraMockMvc.perform(get("/api/aseguradoras?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(aseguradora.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())));
    }

    @Test
    @Transactional
    public void getAseguradora() throws Exception {
        // Initialize the database
        aseguradoraRepository.saveAndFlush(aseguradora);

        // Get the aseguradora
        restAseguradoraMockMvc.perform(get("/api/aseguradoras/{id}", aseguradora.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(aseguradora.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAseguradora() throws Exception {
        // Get the aseguradora
        restAseguradoraMockMvc.perform(get("/api/aseguradoras/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAseguradora() throws Exception {
        // Initialize the database
        aseguradoraRepository.saveAndFlush(aseguradora);
        int databaseSizeBeforeUpdate = aseguradoraRepository.findAll().size();

        // Update the aseguradora
        Aseguradora updatedAseguradora = aseguradoraRepository.findOne(aseguradora.getId());
        // Disconnect from session so that the updates on updatedAseguradora are not directly saved in db
        em.detach(updatedAseguradora);
        updatedAseguradora
            .nombre(UPDATED_NOMBRE);
        AseguradoraDTO aseguradoraDTO = aseguradoraMapper.toDto(updatedAseguradora);

        restAseguradoraMockMvc.perform(put("/api/aseguradoras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(aseguradoraDTO)))
            .andExpect(status().isOk());

        // Validate the Aseguradora in the database
        List<Aseguradora> aseguradoraList = aseguradoraRepository.findAll();
        assertThat(aseguradoraList).hasSize(databaseSizeBeforeUpdate);
        Aseguradora testAseguradora = aseguradoraList.get(aseguradoraList.size() - 1);
        assertThat(testAseguradora.getNombre()).isEqualTo(UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void updateNonExistingAseguradora() throws Exception {
        int databaseSizeBeforeUpdate = aseguradoraRepository.findAll().size();

        // Create the Aseguradora
        AseguradoraDTO aseguradoraDTO = aseguradoraMapper.toDto(aseguradora);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAseguradoraMockMvc.perform(put("/api/aseguradoras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(aseguradoraDTO)))
            .andExpect(status().isCreated());

        // Validate the Aseguradora in the database
        List<Aseguradora> aseguradoraList = aseguradoraRepository.findAll();
        assertThat(aseguradoraList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAseguradora() throws Exception {
        // Initialize the database
        aseguradoraRepository.saveAndFlush(aseguradora);
        int databaseSizeBeforeDelete = aseguradoraRepository.findAll().size();

        // Get the aseguradora
        restAseguradoraMockMvc.perform(delete("/api/aseguradoras/{id}", aseguradora.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Aseguradora> aseguradoraList = aseguradoraRepository.findAll();
        assertThat(aseguradoraList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Aseguradora.class);
        Aseguradora aseguradora1 = new Aseguradora();
        aseguradora1.setId(1L);
        Aseguradora aseguradora2 = new Aseguradora();
        aseguradora2.setId(aseguradora1.getId());
        assertThat(aseguradora1).isEqualTo(aseguradora2);
        aseguradora2.setId(2L);
        assertThat(aseguradora1).isNotEqualTo(aseguradora2);
        aseguradora1.setId(null);
        assertThat(aseguradora1).isNotEqualTo(aseguradora2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AseguradoraDTO.class);
        AseguradoraDTO aseguradoraDTO1 = new AseguradoraDTO();
        aseguradoraDTO1.setId(1L);
        AseguradoraDTO aseguradoraDTO2 = new AseguradoraDTO();
        assertThat(aseguradoraDTO1).isNotEqualTo(aseguradoraDTO2);
        aseguradoraDTO2.setId(aseguradoraDTO1.getId());
        assertThat(aseguradoraDTO1).isEqualTo(aseguradoraDTO2);
        aseguradoraDTO2.setId(2L);
        assertThat(aseguradoraDTO1).isNotEqualTo(aseguradoraDTO2);
        aseguradoraDTO1.setId(null);
        assertThat(aseguradoraDTO1).isNotEqualTo(aseguradoraDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(aseguradoraMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(aseguradoraMapper.fromId(null)).isNull();
    }
}
