package co.edu.uptc.vacunas.web.rest;

import co.edu.uptc.vacunas.VacunasApp;

import co.edu.uptc.vacunas.domain.AplicacionVacuna;
import co.edu.uptc.vacunas.domain.Vacunador;
import co.edu.uptc.vacunas.domain.Paciente;
import co.edu.uptc.vacunas.domain.Dosis;
import co.edu.uptc.vacunas.domain.Ips;
import co.edu.uptc.vacunas.repository.AplicacionVacunaRepository;
import co.edu.uptc.vacunas.service.dto.AplicacionVacunaDTO;
import co.edu.uptc.vacunas.service.mapper.AplicacionVacunaMapper;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static co.edu.uptc.vacunas.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the AplicacionVacunaResource REST controller.
 *
 * @see AplicacionVacunaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VacunasApp.class)
public class AplicacionVacunaResourceIntTest {

    private static final LocalDate DEFAULT_FECHA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private AplicacionVacunaRepository aplicacionVacunaRepository;

    @Autowired
    private AplicacionVacunaMapper aplicacionVacunaMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAplicacionVacunaMockMvc;

    private AplicacionVacuna aplicacionVacuna;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AplicacionVacunaResource aplicacionVacunaResource = new AplicacionVacunaResource(aplicacionVacunaRepository, aplicacionVacunaMapper);
        this.restAplicacionVacunaMockMvc = MockMvcBuilders.standaloneSetup(aplicacionVacunaResource)
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
    public static AplicacionVacuna createEntity(EntityManager em) {
        AplicacionVacuna aplicacionVacuna = new AplicacionVacuna()
            .fecha(DEFAULT_FECHA);
        // Add required entity
        Vacunador vacunador = VacunadorResourceIntTest.createEntity(em);
        em.persist(vacunador);
        em.flush();
        aplicacionVacuna.setVacunador(vacunador);
        // Add required entity
        Paciente paciente = PacienteResourceIntTest.createEntity(em);
        em.persist(paciente);
        em.flush();
        aplicacionVacuna.setPaciente(paciente);
        // Add required entity
        Dosis dosis = DosisResourceIntTest.createEntity(em);
        em.persist(dosis);
        em.flush();
        aplicacionVacuna.setDosis(dosis);
        // Add required entity
        Ips ips = IpsResourceIntTest.createEntity(em);
        em.persist(ips);
        em.flush();
        aplicacionVacuna.setIps(ips);
        return aplicacionVacuna;
    }

    @Before
    public void initTest() {
        aplicacionVacuna = createEntity(em);
    }

    @Test
    @Transactional
    public void createAplicacionVacuna() throws Exception {
        int databaseSizeBeforeCreate = aplicacionVacunaRepository.findAll().size();

        // Create the AplicacionVacuna
        AplicacionVacunaDTO aplicacionVacunaDTO = aplicacionVacunaMapper.toDto(aplicacionVacuna);
        restAplicacionVacunaMockMvc.perform(post("/api/aplicacion-vacunas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(aplicacionVacunaDTO)))
            .andExpect(status().isCreated());

        // Validate the AplicacionVacuna in the database
        List<AplicacionVacuna> aplicacionVacunaList = aplicacionVacunaRepository.findAll();
        assertThat(aplicacionVacunaList).hasSize(databaseSizeBeforeCreate + 1);
        AplicacionVacuna testAplicacionVacuna = aplicacionVacunaList.get(aplicacionVacunaList.size() - 1);
        assertThat(testAplicacionVacuna.getFecha()).isEqualTo(DEFAULT_FECHA);
    }

    @Test
    @Transactional
    public void createAplicacionVacunaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = aplicacionVacunaRepository.findAll().size();

        // Create the AplicacionVacuna with an existing ID
        aplicacionVacuna.setId(1L);
        AplicacionVacunaDTO aplicacionVacunaDTO = aplicacionVacunaMapper.toDto(aplicacionVacuna);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAplicacionVacunaMockMvc.perform(post("/api/aplicacion-vacunas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(aplicacionVacunaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AplicacionVacuna in the database
        List<AplicacionVacuna> aplicacionVacunaList = aplicacionVacunaRepository.findAll();
        assertThat(aplicacionVacunaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkFechaIsRequired() throws Exception {
        int databaseSizeBeforeTest = aplicacionVacunaRepository.findAll().size();
        // set the field null
        aplicacionVacuna.setFecha(null);

        // Create the AplicacionVacuna, which fails.
        AplicacionVacunaDTO aplicacionVacunaDTO = aplicacionVacunaMapper.toDto(aplicacionVacuna);

        restAplicacionVacunaMockMvc.perform(post("/api/aplicacion-vacunas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(aplicacionVacunaDTO)))
            .andExpect(status().isBadRequest());

        List<AplicacionVacuna> aplicacionVacunaList = aplicacionVacunaRepository.findAll();
        assertThat(aplicacionVacunaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAplicacionVacunas() throws Exception {
        // Initialize the database
        aplicacionVacunaRepository.saveAndFlush(aplicacionVacuna);

        // Get all the aplicacionVacunaList
        restAplicacionVacunaMockMvc.perform(get("/api/aplicacion-vacunas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(aplicacionVacuna.getId().intValue())))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())));
    }

    @Test
    @Transactional
    public void getAplicacionVacuna() throws Exception {
        // Initialize the database
        aplicacionVacunaRepository.saveAndFlush(aplicacionVacuna);

        // Get the aplicacionVacuna
        restAplicacionVacunaMockMvc.perform(get("/api/aplicacion-vacunas/{id}", aplicacionVacuna.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(aplicacionVacuna.getId().intValue()))
            .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAplicacionVacuna() throws Exception {
        // Get the aplicacionVacuna
        restAplicacionVacunaMockMvc.perform(get("/api/aplicacion-vacunas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAplicacionVacuna() throws Exception {
        // Initialize the database
        aplicacionVacunaRepository.saveAndFlush(aplicacionVacuna);
        int databaseSizeBeforeUpdate = aplicacionVacunaRepository.findAll().size();

        // Update the aplicacionVacuna
        AplicacionVacuna updatedAplicacionVacuna = aplicacionVacunaRepository.findOne(aplicacionVacuna.getId());
        // Disconnect from session so that the updates on updatedAplicacionVacuna are not directly saved in db
        em.detach(updatedAplicacionVacuna);
        updatedAplicacionVacuna
            .fecha(UPDATED_FECHA);
        AplicacionVacunaDTO aplicacionVacunaDTO = aplicacionVacunaMapper.toDto(updatedAplicacionVacuna);

        restAplicacionVacunaMockMvc.perform(put("/api/aplicacion-vacunas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(aplicacionVacunaDTO)))
            .andExpect(status().isOk());

        // Validate the AplicacionVacuna in the database
        List<AplicacionVacuna> aplicacionVacunaList = aplicacionVacunaRepository.findAll();
        assertThat(aplicacionVacunaList).hasSize(databaseSizeBeforeUpdate);
        AplicacionVacuna testAplicacionVacuna = aplicacionVacunaList.get(aplicacionVacunaList.size() - 1);
        assertThat(testAplicacionVacuna.getFecha()).isEqualTo(UPDATED_FECHA);
    }

    @Test
    @Transactional
    public void updateNonExistingAplicacionVacuna() throws Exception {
        int databaseSizeBeforeUpdate = aplicacionVacunaRepository.findAll().size();

        // Create the AplicacionVacuna
        AplicacionVacunaDTO aplicacionVacunaDTO = aplicacionVacunaMapper.toDto(aplicacionVacuna);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAplicacionVacunaMockMvc.perform(put("/api/aplicacion-vacunas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(aplicacionVacunaDTO)))
            .andExpect(status().isCreated());

        // Validate the AplicacionVacuna in the database
        List<AplicacionVacuna> aplicacionVacunaList = aplicacionVacunaRepository.findAll();
        assertThat(aplicacionVacunaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAplicacionVacuna() throws Exception {
        // Initialize the database
        aplicacionVacunaRepository.saveAndFlush(aplicacionVacuna);
        int databaseSizeBeforeDelete = aplicacionVacunaRepository.findAll().size();

        // Get the aplicacionVacuna
        restAplicacionVacunaMockMvc.perform(delete("/api/aplicacion-vacunas/{id}", aplicacionVacuna.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<AplicacionVacuna> aplicacionVacunaList = aplicacionVacunaRepository.findAll();
        assertThat(aplicacionVacunaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AplicacionVacuna.class);
        AplicacionVacuna aplicacionVacuna1 = new AplicacionVacuna();
        aplicacionVacuna1.setId(1L);
        AplicacionVacuna aplicacionVacuna2 = new AplicacionVacuna();
        aplicacionVacuna2.setId(aplicacionVacuna1.getId());
        assertThat(aplicacionVacuna1).isEqualTo(aplicacionVacuna2);
        aplicacionVacuna2.setId(2L);
        assertThat(aplicacionVacuna1).isNotEqualTo(aplicacionVacuna2);
        aplicacionVacuna1.setId(null);
        assertThat(aplicacionVacuna1).isNotEqualTo(aplicacionVacuna2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AplicacionVacunaDTO.class);
        AplicacionVacunaDTO aplicacionVacunaDTO1 = new AplicacionVacunaDTO();
        aplicacionVacunaDTO1.setId(1L);
        AplicacionVacunaDTO aplicacionVacunaDTO2 = new AplicacionVacunaDTO();
        assertThat(aplicacionVacunaDTO1).isNotEqualTo(aplicacionVacunaDTO2);
        aplicacionVacunaDTO2.setId(aplicacionVacunaDTO1.getId());
        assertThat(aplicacionVacunaDTO1).isEqualTo(aplicacionVacunaDTO2);
        aplicacionVacunaDTO2.setId(2L);
        assertThat(aplicacionVacunaDTO1).isNotEqualTo(aplicacionVacunaDTO2);
        aplicacionVacunaDTO1.setId(null);
        assertThat(aplicacionVacunaDTO1).isNotEqualTo(aplicacionVacunaDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(aplicacionVacunaMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(aplicacionVacunaMapper.fromId(null)).isNull();
    }
}
