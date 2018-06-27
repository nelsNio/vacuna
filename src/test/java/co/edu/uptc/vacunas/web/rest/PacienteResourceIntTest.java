package co.edu.uptc.vacunas.web.rest;

import co.edu.uptc.vacunas.VacunasApp;

import co.edu.uptc.vacunas.domain.Paciente;
import co.edu.uptc.vacunas.domain.TipoDocumento;
import co.edu.uptc.vacunas.domain.Genero;
import co.edu.uptc.vacunas.domain.Regimen;
import co.edu.uptc.vacunas.domain.TipoResidencia;
import co.edu.uptc.vacunas.repository.PacienteRepository;
import co.edu.uptc.vacunas.service.dto.PacienteDTO;
import co.edu.uptc.vacunas.service.mapper.PacienteMapper;
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
 * Test class for the PacienteResource REST controller.
 *
 * @see PacienteResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VacunasApp.class)
public class PacienteResourceIntTest {

    private static final String DEFAULT_DOCUMENTO = "6";
    private static final String UPDATED_DOCUMENTO = "95";

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_APELLIDO = "AAAAAAAAAA";
    private static final String UPDATED_APELLIDO = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONO = "";
    private static final String UPDATED_TELEFONO = "6";

    private static final String DEFAULT_CORREO = "AAAAAAAAAA";
    private static final String UPDATED_CORREO = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FECHA_NACIMIENTO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_NACIMIENTO = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_DESPLAZADO = false;
    private static final Boolean UPDATED_DESPLAZADO = true;

    private static final Boolean DEFAULT_VICTIMA_CONFLICTO = false;
    private static final Boolean UPDATED_VICTIMA_CONFLICTO = true;

    private static final Boolean DEFAULT_DISCAPACIDAD = false;
    private static final Boolean UPDATED_DISCAPACIDAD = true;

    private static final Boolean DEFAULT_GESTANTE = false;
    private static final Boolean UPDATED_GESTANTE = true;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private PacienteMapper pacienteMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPacienteMockMvc;

    private Paciente paciente;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PacienteResource pacienteResource = new PacienteResource(pacienteRepository, pacienteMapper);
        this.restPacienteMockMvc = MockMvcBuilders.standaloneSetup(pacienteResource)
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
    public static Paciente createEntity(EntityManager em) {
        Paciente paciente = new Paciente()
            .documento(DEFAULT_DOCUMENTO)
            .nombre(DEFAULT_NOMBRE)
            .apellido(DEFAULT_APELLIDO)
            .telefono(DEFAULT_TELEFONO)
            .correo(DEFAULT_CORREO)
            .fechaNacimiento(DEFAULT_FECHA_NACIMIENTO)
            .desplazado(DEFAULT_DESPLAZADO)
            .victimaConflicto(DEFAULT_VICTIMA_CONFLICTO)
            .discapacidad(DEFAULT_DISCAPACIDAD)
            .gestante(DEFAULT_GESTANTE);
        // Add required entity
        TipoDocumento tipoDocumento = TipoDocumentoResourceIntTest.createEntity(em);
        em.persist(tipoDocumento);
        em.flush();
        paciente.setTipoDocumento(tipoDocumento);
        // Add required entity
        Genero genero = GeneroResourceIntTest.createEntity(em);
        em.persist(genero);
        em.flush();
        paciente.setGenero(genero);
        // Add required entity
        Regimen regimen = RegimenResourceIntTest.createEntity(em);
        em.persist(regimen);
        em.flush();
        paciente.setRegimen(regimen);
        // Add required entity
        TipoResidencia tipoResidencia = TipoResidenciaResourceIntTest.createEntity(em);
        em.persist(tipoResidencia);
        em.flush();
        paciente.setTipoResidencia(tipoResidencia);
        return paciente;
    }

    @Before
    public void initTest() {
        paciente = createEntity(em);
    }

    @Test
    @Transactional
    public void createPaciente() throws Exception {
        int databaseSizeBeforeCreate = pacienteRepository.findAll().size();

        // Create the Paciente
        PacienteDTO pacienteDTO = pacienteMapper.toDto(paciente);
        restPacienteMockMvc.perform(post("/api/pacientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pacienteDTO)))
            .andExpect(status().isCreated());

        // Validate the Paciente in the database
        List<Paciente> pacienteList = pacienteRepository.findAll();
        assertThat(pacienteList).hasSize(databaseSizeBeforeCreate + 1);
        Paciente testPaciente = pacienteList.get(pacienteList.size() - 1);
        assertThat(testPaciente.getDocumento()).isEqualTo(DEFAULT_DOCUMENTO);
        assertThat(testPaciente.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testPaciente.getApellido()).isEqualTo(DEFAULT_APELLIDO);
        assertThat(testPaciente.getTelefono()).isEqualTo(DEFAULT_TELEFONO);
        assertThat(testPaciente.getCorreo()).isEqualTo(DEFAULT_CORREO);
        assertThat(testPaciente.getFechaNacimiento()).isEqualTo(DEFAULT_FECHA_NACIMIENTO);
        assertThat(testPaciente.isDesplazado()).isEqualTo(DEFAULT_DESPLAZADO);
        assertThat(testPaciente.isVictimaConflicto()).isEqualTo(DEFAULT_VICTIMA_CONFLICTO);
        assertThat(testPaciente.isDiscapacidad()).isEqualTo(DEFAULT_DISCAPACIDAD);
        assertThat(testPaciente.isGestante()).isEqualTo(DEFAULT_GESTANTE);
    }

    @Test
    @Transactional
    public void createPacienteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pacienteRepository.findAll().size();

        // Create the Paciente with an existing ID
        paciente.setId(1L);
        PacienteDTO pacienteDTO = pacienteMapper.toDto(paciente);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPacienteMockMvc.perform(post("/api/pacientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pacienteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Paciente in the database
        List<Paciente> pacienteList = pacienteRepository.findAll();
        assertThat(pacienteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDocumentoIsRequired() throws Exception {
        int databaseSizeBeforeTest = pacienteRepository.findAll().size();
        // set the field null
        paciente.setDocumento(null);

        // Create the Paciente, which fails.
        PacienteDTO pacienteDTO = pacienteMapper.toDto(paciente);

        restPacienteMockMvc.perform(post("/api/pacientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pacienteDTO)))
            .andExpect(status().isBadRequest());

        List<Paciente> pacienteList = pacienteRepository.findAll();
        assertThat(pacienteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = pacienteRepository.findAll().size();
        // set the field null
        paciente.setNombre(null);

        // Create the Paciente, which fails.
        PacienteDTO pacienteDTO = pacienteMapper.toDto(paciente);

        restPacienteMockMvc.perform(post("/api/pacientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pacienteDTO)))
            .andExpect(status().isBadRequest());

        List<Paciente> pacienteList = pacienteRepository.findAll();
        assertThat(pacienteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkApellidoIsRequired() throws Exception {
        int databaseSizeBeforeTest = pacienteRepository.findAll().size();
        // set the field null
        paciente.setApellido(null);

        // Create the Paciente, which fails.
        PacienteDTO pacienteDTO = pacienteMapper.toDto(paciente);

        restPacienteMockMvc.perform(post("/api/pacientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pacienteDTO)))
            .andExpect(status().isBadRequest());

        List<Paciente> pacienteList = pacienteRepository.findAll();
        assertThat(pacienteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFechaNacimientoIsRequired() throws Exception {
        int databaseSizeBeforeTest = pacienteRepository.findAll().size();
        // set the field null
        paciente.setFechaNacimiento(null);

        // Create the Paciente, which fails.
        PacienteDTO pacienteDTO = pacienteMapper.toDto(paciente);

        restPacienteMockMvc.perform(post("/api/pacientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pacienteDTO)))
            .andExpect(status().isBadRequest());

        List<Paciente> pacienteList = pacienteRepository.findAll();
        assertThat(pacienteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDesplazadoIsRequired() throws Exception {
        int databaseSizeBeforeTest = pacienteRepository.findAll().size();
        // set the field null
        paciente.setDesplazado(null);

        // Create the Paciente, which fails.
        PacienteDTO pacienteDTO = pacienteMapper.toDto(paciente);

        restPacienteMockMvc.perform(post("/api/pacientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pacienteDTO)))
            .andExpect(status().isBadRequest());

        List<Paciente> pacienteList = pacienteRepository.findAll();
        assertThat(pacienteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkVictimaConflictoIsRequired() throws Exception {
        int databaseSizeBeforeTest = pacienteRepository.findAll().size();
        // set the field null
        paciente.setVictimaConflicto(null);

        // Create the Paciente, which fails.
        PacienteDTO pacienteDTO = pacienteMapper.toDto(paciente);

        restPacienteMockMvc.perform(post("/api/pacientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pacienteDTO)))
            .andExpect(status().isBadRequest());

        List<Paciente> pacienteList = pacienteRepository.findAll();
        assertThat(pacienteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDiscapacidadIsRequired() throws Exception {
        int databaseSizeBeforeTest = pacienteRepository.findAll().size();
        // set the field null
        paciente.setDiscapacidad(null);

        // Create the Paciente, which fails.
        PacienteDTO pacienteDTO = pacienteMapper.toDto(paciente);

        restPacienteMockMvc.perform(post("/api/pacientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pacienteDTO)))
            .andExpect(status().isBadRequest());

        List<Paciente> pacienteList = pacienteRepository.findAll();
        assertThat(pacienteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkGestanteIsRequired() throws Exception {
        int databaseSizeBeforeTest = pacienteRepository.findAll().size();
        // set the field null
        paciente.setGestante(null);

        // Create the Paciente, which fails.
        PacienteDTO pacienteDTO = pacienteMapper.toDto(paciente);

        restPacienteMockMvc.perform(post("/api/pacientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pacienteDTO)))
            .andExpect(status().isBadRequest());

        List<Paciente> pacienteList = pacienteRepository.findAll();
        assertThat(pacienteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPacientes() throws Exception {
        // Initialize the database
        pacienteRepository.saveAndFlush(paciente);

        // Get all the pacienteList
        restPacienteMockMvc.perform(get("/api/pacientes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paciente.getId().intValue())))
            .andExpect(jsonPath("$.[*].documento").value(hasItem(DEFAULT_DOCUMENTO.toString())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].apellido").value(hasItem(DEFAULT_APELLIDO.toString())))
            .andExpect(jsonPath("$.[*].telefono").value(hasItem(DEFAULT_TELEFONO.toString())))
            .andExpect(jsonPath("$.[*].correo").value(hasItem(DEFAULT_CORREO.toString())))
            .andExpect(jsonPath("$.[*].fechaNacimiento").value(hasItem(DEFAULT_FECHA_NACIMIENTO.toString())))
            .andExpect(jsonPath("$.[*].desplazado").value(hasItem(DEFAULT_DESPLAZADO.booleanValue())))
            .andExpect(jsonPath("$.[*].victimaConflicto").value(hasItem(DEFAULT_VICTIMA_CONFLICTO.booleanValue())))
            .andExpect(jsonPath("$.[*].discapacidad").value(hasItem(DEFAULT_DISCAPACIDAD.booleanValue())))
            .andExpect(jsonPath("$.[*].gestante").value(hasItem(DEFAULT_GESTANTE.booleanValue())));
    }

    @Test
    @Transactional
    public void getPaciente() throws Exception {
        // Initialize the database
        pacienteRepository.saveAndFlush(paciente);

        // Get the paciente
        restPacienteMockMvc.perform(get("/api/pacientes/{id}", paciente.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(paciente.getId().intValue()))
            .andExpect(jsonPath("$.documento").value(DEFAULT_DOCUMENTO.toString()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.apellido").value(DEFAULT_APELLIDO.toString()))
            .andExpect(jsonPath("$.telefono").value(DEFAULT_TELEFONO.toString()))
            .andExpect(jsonPath("$.correo").value(DEFAULT_CORREO.toString()))
            .andExpect(jsonPath("$.fechaNacimiento").value(DEFAULT_FECHA_NACIMIENTO.toString()))
            .andExpect(jsonPath("$.desplazado").value(DEFAULT_DESPLAZADO.booleanValue()))
            .andExpect(jsonPath("$.victimaConflicto").value(DEFAULT_VICTIMA_CONFLICTO.booleanValue()))
            .andExpect(jsonPath("$.discapacidad").value(DEFAULT_DISCAPACIDAD.booleanValue()))
            .andExpect(jsonPath("$.gestante").value(DEFAULT_GESTANTE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPaciente() throws Exception {
        // Get the paciente
        restPacienteMockMvc.perform(get("/api/pacientes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePaciente() throws Exception {
        // Initialize the database
        pacienteRepository.saveAndFlush(paciente);
        int databaseSizeBeforeUpdate = pacienteRepository.findAll().size();

        // Update the paciente
        Paciente updatedPaciente = pacienteRepository.findOne(paciente.getId());
        // Disconnect from session so that the updates on updatedPaciente are not directly saved in db
        em.detach(updatedPaciente);
        updatedPaciente
            .documento(UPDATED_DOCUMENTO)
            .nombre(UPDATED_NOMBRE)
            .apellido(UPDATED_APELLIDO)
            .telefono(UPDATED_TELEFONO)
            .correo(UPDATED_CORREO)
            .fechaNacimiento(UPDATED_FECHA_NACIMIENTO)
            .desplazado(UPDATED_DESPLAZADO)
            .victimaConflicto(UPDATED_VICTIMA_CONFLICTO)
            .discapacidad(UPDATED_DISCAPACIDAD)
            .gestante(UPDATED_GESTANTE);
        PacienteDTO pacienteDTO = pacienteMapper.toDto(updatedPaciente);

        restPacienteMockMvc.perform(put("/api/pacientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pacienteDTO)))
            .andExpect(status().isOk());

        // Validate the Paciente in the database
        List<Paciente> pacienteList = pacienteRepository.findAll();
        assertThat(pacienteList).hasSize(databaseSizeBeforeUpdate);
        Paciente testPaciente = pacienteList.get(pacienteList.size() - 1);
        assertThat(testPaciente.getDocumento()).isEqualTo(UPDATED_DOCUMENTO);
        assertThat(testPaciente.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testPaciente.getApellido()).isEqualTo(UPDATED_APELLIDO);
        assertThat(testPaciente.getTelefono()).isEqualTo(UPDATED_TELEFONO);
        assertThat(testPaciente.getCorreo()).isEqualTo(UPDATED_CORREO);
        assertThat(testPaciente.getFechaNacimiento()).isEqualTo(UPDATED_FECHA_NACIMIENTO);
        assertThat(testPaciente.isDesplazado()).isEqualTo(UPDATED_DESPLAZADO);
        assertThat(testPaciente.isVictimaConflicto()).isEqualTo(UPDATED_VICTIMA_CONFLICTO);
        assertThat(testPaciente.isDiscapacidad()).isEqualTo(UPDATED_DISCAPACIDAD);
        assertThat(testPaciente.isGestante()).isEqualTo(UPDATED_GESTANTE);
    }

    @Test
    @Transactional
    public void updateNonExistingPaciente() throws Exception {
        int databaseSizeBeforeUpdate = pacienteRepository.findAll().size();

        // Create the Paciente
        PacienteDTO pacienteDTO = pacienteMapper.toDto(paciente);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPacienteMockMvc.perform(put("/api/pacientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pacienteDTO)))
            .andExpect(status().isCreated());

        // Validate the Paciente in the database
        List<Paciente> pacienteList = pacienteRepository.findAll();
        assertThat(pacienteList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePaciente() throws Exception {
        // Initialize the database
        pacienteRepository.saveAndFlush(paciente);
        int databaseSizeBeforeDelete = pacienteRepository.findAll().size();

        // Get the paciente
        restPacienteMockMvc.perform(delete("/api/pacientes/{id}", paciente.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Paciente> pacienteList = pacienteRepository.findAll();
        assertThat(pacienteList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Paciente.class);
        Paciente paciente1 = new Paciente();
        paciente1.setId(1L);
        Paciente paciente2 = new Paciente();
        paciente2.setId(paciente1.getId());
        assertThat(paciente1).isEqualTo(paciente2);
        paciente2.setId(2L);
        assertThat(paciente1).isNotEqualTo(paciente2);
        paciente1.setId(null);
        assertThat(paciente1).isNotEqualTo(paciente2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PacienteDTO.class);
        PacienteDTO pacienteDTO1 = new PacienteDTO();
        pacienteDTO1.setId(1L);
        PacienteDTO pacienteDTO2 = new PacienteDTO();
        assertThat(pacienteDTO1).isNotEqualTo(pacienteDTO2);
        pacienteDTO2.setId(pacienteDTO1.getId());
        assertThat(pacienteDTO1).isEqualTo(pacienteDTO2);
        pacienteDTO2.setId(2L);
        assertThat(pacienteDTO1).isNotEqualTo(pacienteDTO2);
        pacienteDTO1.setId(null);
        assertThat(pacienteDTO1).isNotEqualTo(pacienteDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(pacienteMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(pacienteMapper.fromId(null)).isNull();
    }
}
