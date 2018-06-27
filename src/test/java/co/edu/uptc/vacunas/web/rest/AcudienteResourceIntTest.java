package co.edu.uptc.vacunas.web.rest;

import co.edu.uptc.vacunas.VacunasApp;

import co.edu.uptc.vacunas.domain.Acudiente;
import co.edu.uptc.vacunas.domain.TipoDocumento;
import co.edu.uptc.vacunas.domain.Genero;
import co.edu.uptc.vacunas.domain.Paciente;
import co.edu.uptc.vacunas.repository.AcudienteRepository;
import co.edu.uptc.vacunas.service.dto.AcudienteDTO;
import co.edu.uptc.vacunas.service.mapper.AcudienteMapper;
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
 * Test class for the AcudienteResource REST controller.
 *
 * @see AcudienteResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VacunasApp.class)
public class AcudienteResourceIntTest {

    private static final String DEFAULT_DOCUMENTO = "32";
    private static final String UPDATED_DOCUMENTO = "6";

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_APELLIDO = "AAAAAAAAAA";
    private static final String UPDATED_APELLIDO = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FECHA_NACIMIENTO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_NACIMIENTO = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private AcudienteRepository acudienteRepository;

    @Autowired
    private AcudienteMapper acudienteMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAcudienteMockMvc;

    private Acudiente acudiente;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AcudienteResource acudienteResource = new AcudienteResource(acudienteRepository, acudienteMapper);
        this.restAcudienteMockMvc = MockMvcBuilders.standaloneSetup(acudienteResource)
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
    public static Acudiente createEntity(EntityManager em) {
        Acudiente acudiente = new Acudiente()
            .documento(DEFAULT_DOCUMENTO)
            .nombre(DEFAULT_NOMBRE)
            .apellido(DEFAULT_APELLIDO)
            .fechaNacimiento(DEFAULT_FECHA_NACIMIENTO);
        // Add required entity
        TipoDocumento tipoDocumento = TipoDocumentoResourceIntTest.createEntity(em);
        em.persist(tipoDocumento);
        em.flush();
        acudiente.setTipoDocumento(tipoDocumento);
        // Add required entity
        Genero genero = GeneroResourceIntTest.createEntity(em);
        em.persist(genero);
        em.flush();
        acudiente.setGenero(genero);
        // Add required entity
        Paciente paciente = PacienteResourceIntTest.createEntity(em);
        em.persist(paciente);
        em.flush();
        acudiente.setPaciente(paciente);
        return acudiente;
    }

    @Before
    public void initTest() {
        acudiente = createEntity(em);
    }

    @Test
    @Transactional
    public void createAcudiente() throws Exception {
        int databaseSizeBeforeCreate = acudienteRepository.findAll().size();

        // Create the Acudiente
        AcudienteDTO acudienteDTO = acudienteMapper.toDto(acudiente);
        restAcudienteMockMvc.perform(post("/api/acudientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(acudienteDTO)))
            .andExpect(status().isCreated());

        // Validate the Acudiente in the database
        List<Acudiente> acudienteList = acudienteRepository.findAll();
        assertThat(acudienteList).hasSize(databaseSizeBeforeCreate + 1);
        Acudiente testAcudiente = acudienteList.get(acudienteList.size() - 1);
        assertThat(testAcudiente.getDocumento()).isEqualTo(DEFAULT_DOCUMENTO);
        assertThat(testAcudiente.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testAcudiente.getApellido()).isEqualTo(DEFAULT_APELLIDO);
        assertThat(testAcudiente.getFechaNacimiento()).isEqualTo(DEFAULT_FECHA_NACIMIENTO);
    }

    @Test
    @Transactional
    public void createAcudienteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = acudienteRepository.findAll().size();

        // Create the Acudiente with an existing ID
        acudiente.setId(1L);
        AcudienteDTO acudienteDTO = acudienteMapper.toDto(acudiente);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAcudienteMockMvc.perform(post("/api/acudientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(acudienteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Acudiente in the database
        List<Acudiente> acudienteList = acudienteRepository.findAll();
        assertThat(acudienteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDocumentoIsRequired() throws Exception {
        int databaseSizeBeforeTest = acudienteRepository.findAll().size();
        // set the field null
        acudiente.setDocumento(null);

        // Create the Acudiente, which fails.
        AcudienteDTO acudienteDTO = acudienteMapper.toDto(acudiente);

        restAcudienteMockMvc.perform(post("/api/acudientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(acudienteDTO)))
            .andExpect(status().isBadRequest());

        List<Acudiente> acudienteList = acudienteRepository.findAll();
        assertThat(acudienteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = acudienteRepository.findAll().size();
        // set the field null
        acudiente.setNombre(null);

        // Create the Acudiente, which fails.
        AcudienteDTO acudienteDTO = acudienteMapper.toDto(acudiente);

        restAcudienteMockMvc.perform(post("/api/acudientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(acudienteDTO)))
            .andExpect(status().isBadRequest());

        List<Acudiente> acudienteList = acudienteRepository.findAll();
        assertThat(acudienteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkApellidoIsRequired() throws Exception {
        int databaseSizeBeforeTest = acudienteRepository.findAll().size();
        // set the field null
        acudiente.setApellido(null);

        // Create the Acudiente, which fails.
        AcudienteDTO acudienteDTO = acudienteMapper.toDto(acudiente);

        restAcudienteMockMvc.perform(post("/api/acudientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(acudienteDTO)))
            .andExpect(status().isBadRequest());

        List<Acudiente> acudienteList = acudienteRepository.findAll();
        assertThat(acudienteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFechaNacimientoIsRequired() throws Exception {
        int databaseSizeBeforeTest = acudienteRepository.findAll().size();
        // set the field null
        acudiente.setFechaNacimiento(null);

        // Create the Acudiente, which fails.
        AcudienteDTO acudienteDTO = acudienteMapper.toDto(acudiente);

        restAcudienteMockMvc.perform(post("/api/acudientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(acudienteDTO)))
            .andExpect(status().isBadRequest());

        List<Acudiente> acudienteList = acudienteRepository.findAll();
        assertThat(acudienteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAcudientes() throws Exception {
        // Initialize the database
        acudienteRepository.saveAndFlush(acudiente);

        // Get all the acudienteList
        restAcudienteMockMvc.perform(get("/api/acudientes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(acudiente.getId().intValue())))
            .andExpect(jsonPath("$.[*].documento").value(hasItem(DEFAULT_DOCUMENTO.toString())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].apellido").value(hasItem(DEFAULT_APELLIDO.toString())))
            .andExpect(jsonPath("$.[*].fechaNacimiento").value(hasItem(DEFAULT_FECHA_NACIMIENTO.toString())));
    }

    @Test
    @Transactional
    public void getAcudiente() throws Exception {
        // Initialize the database
        acudienteRepository.saveAndFlush(acudiente);

        // Get the acudiente
        restAcudienteMockMvc.perform(get("/api/acudientes/{id}", acudiente.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(acudiente.getId().intValue()))
            .andExpect(jsonPath("$.documento").value(DEFAULT_DOCUMENTO.toString()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.apellido").value(DEFAULT_APELLIDO.toString()))
            .andExpect(jsonPath("$.fechaNacimiento").value(DEFAULT_FECHA_NACIMIENTO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAcudiente() throws Exception {
        // Get the acudiente
        restAcudienteMockMvc.perform(get("/api/acudientes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAcudiente() throws Exception {
        // Initialize the database
        acudienteRepository.saveAndFlush(acudiente);
        int databaseSizeBeforeUpdate = acudienteRepository.findAll().size();

        // Update the acudiente
        Acudiente updatedAcudiente = acudienteRepository.findOne(acudiente.getId());
        // Disconnect from session so that the updates on updatedAcudiente are not directly saved in db
        em.detach(updatedAcudiente);
        updatedAcudiente
            .documento(UPDATED_DOCUMENTO)
            .nombre(UPDATED_NOMBRE)
            .apellido(UPDATED_APELLIDO)
            .fechaNacimiento(UPDATED_FECHA_NACIMIENTO);
        AcudienteDTO acudienteDTO = acudienteMapper.toDto(updatedAcudiente);

        restAcudienteMockMvc.perform(put("/api/acudientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(acudienteDTO)))
            .andExpect(status().isOk());

        // Validate the Acudiente in the database
        List<Acudiente> acudienteList = acudienteRepository.findAll();
        assertThat(acudienteList).hasSize(databaseSizeBeforeUpdate);
        Acudiente testAcudiente = acudienteList.get(acudienteList.size() - 1);
        assertThat(testAcudiente.getDocumento()).isEqualTo(UPDATED_DOCUMENTO);
        assertThat(testAcudiente.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testAcudiente.getApellido()).isEqualTo(UPDATED_APELLIDO);
        assertThat(testAcudiente.getFechaNacimiento()).isEqualTo(UPDATED_FECHA_NACIMIENTO);
    }

    @Test
    @Transactional
    public void updateNonExistingAcudiente() throws Exception {
        int databaseSizeBeforeUpdate = acudienteRepository.findAll().size();

        // Create the Acudiente
        AcudienteDTO acudienteDTO = acudienteMapper.toDto(acudiente);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAcudienteMockMvc.perform(put("/api/acudientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(acudienteDTO)))
            .andExpect(status().isCreated());

        // Validate the Acudiente in the database
        List<Acudiente> acudienteList = acudienteRepository.findAll();
        assertThat(acudienteList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAcudiente() throws Exception {
        // Initialize the database
        acudienteRepository.saveAndFlush(acudiente);
        int databaseSizeBeforeDelete = acudienteRepository.findAll().size();

        // Get the acudiente
        restAcudienteMockMvc.perform(delete("/api/acudientes/{id}", acudiente.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Acudiente> acudienteList = acudienteRepository.findAll();
        assertThat(acudienteList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Acudiente.class);
        Acudiente acudiente1 = new Acudiente();
        acudiente1.setId(1L);
        Acudiente acudiente2 = new Acudiente();
        acudiente2.setId(acudiente1.getId());
        assertThat(acudiente1).isEqualTo(acudiente2);
        acudiente2.setId(2L);
        assertThat(acudiente1).isNotEqualTo(acudiente2);
        acudiente1.setId(null);
        assertThat(acudiente1).isNotEqualTo(acudiente2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AcudienteDTO.class);
        AcudienteDTO acudienteDTO1 = new AcudienteDTO();
        acudienteDTO1.setId(1L);
        AcudienteDTO acudienteDTO2 = new AcudienteDTO();
        assertThat(acudienteDTO1).isNotEqualTo(acudienteDTO2);
        acudienteDTO2.setId(acudienteDTO1.getId());
        assertThat(acudienteDTO1).isEqualTo(acudienteDTO2);
        acudienteDTO2.setId(2L);
        assertThat(acudienteDTO1).isNotEqualTo(acudienteDTO2);
        acudienteDTO1.setId(null);
        assertThat(acudienteDTO1).isNotEqualTo(acudienteDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(acudienteMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(acudienteMapper.fromId(null)).isNull();
    }
}
