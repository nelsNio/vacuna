package co.edu.uptc.vacunas.web.rest;

import co.edu.uptc.vacunas.VacunasApp;

import co.edu.uptc.vacunas.domain.Vacunador;
import co.edu.uptc.vacunas.domain.TipoDocumento;
import co.edu.uptc.vacunas.domain.Genero;
import co.edu.uptc.vacunas.domain.Ips;
import co.edu.uptc.vacunas.repository.VacunadorRepository;
import co.edu.uptc.vacunas.service.dto.VacunadorDTO;
import co.edu.uptc.vacunas.service.mapper.VacunadorMapper;
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
 * Test class for the VacunadorResource REST controller.
 *
 * @see VacunadorResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VacunasApp.class)
public class VacunadorResourceIntTest {

    private static final String DEFAULT_DOCUMENTO = "4";
    private static final String UPDATED_DOCUMENTO = "24";

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_APELLIDO = "AAAAAAAAAA";
    private static final String UPDATED_APELLIDO = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONO = "6";
    private static final String UPDATED_TELEFONO = "";

    private static final String DEFAULT_CORREO = "AAAAAAAAAA";
    private static final String UPDATED_CORREO = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FECHA_NACIMIENTO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_NACIMIENTO = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private VacunadorRepository vacunadorRepository;

    @Autowired
    private VacunadorMapper vacunadorMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restVacunadorMockMvc;

    private Vacunador vacunador;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final VacunadorResource vacunadorResource = new VacunadorResource(vacunadorRepository, vacunadorMapper);
        this.restVacunadorMockMvc = MockMvcBuilders.standaloneSetup(vacunadorResource)
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
    public static Vacunador createEntity(EntityManager em) {
        Vacunador vacunador = new Vacunador()
            .documento(DEFAULT_DOCUMENTO)
            .nombre(DEFAULT_NOMBRE)
            .apellido(DEFAULT_APELLIDO)
            .telefono(DEFAULT_TELEFONO)
            .correo(DEFAULT_CORREO)
            .fechaNacimiento(DEFAULT_FECHA_NACIMIENTO);
        // Add required entity
        TipoDocumento tipoDocumento = TipoDocumentoResourceIntTest.createEntity(em);
        em.persist(tipoDocumento);
        em.flush();
        vacunador.setTipoDocumento(tipoDocumento);
        // Add required entity
        Genero genero = GeneroResourceIntTest.createEntity(em);
        em.persist(genero);
        em.flush();
        vacunador.setGenero(genero);
        // Add required entity
        Ips ips = IpsResourceIntTest.createEntity(em);
        em.persist(ips);
        em.flush();
        vacunador.setIps(ips);
        return vacunador;
    }

    @Before
    public void initTest() {
        vacunador = createEntity(em);
    }

    @Test
    @Transactional
    public void createVacunador() throws Exception {
        int databaseSizeBeforeCreate = vacunadorRepository.findAll().size();

        // Create the Vacunador
        VacunadorDTO vacunadorDTO = vacunadorMapper.toDto(vacunador);
        restVacunadorMockMvc.perform(post("/api/vacunadors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vacunadorDTO)))
            .andExpect(status().isCreated());

        // Validate the Vacunador in the database
        List<Vacunador> vacunadorList = vacunadorRepository.findAll();
        assertThat(vacunadorList).hasSize(databaseSizeBeforeCreate + 1);
        Vacunador testVacunador = vacunadorList.get(vacunadorList.size() - 1);
        assertThat(testVacunador.getDocumento()).isEqualTo(DEFAULT_DOCUMENTO);
        assertThat(testVacunador.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testVacunador.getApellido()).isEqualTo(DEFAULT_APELLIDO);
        assertThat(testVacunador.getTelefono()).isEqualTo(DEFAULT_TELEFONO);
        assertThat(testVacunador.getCorreo()).isEqualTo(DEFAULT_CORREO);
        assertThat(testVacunador.getFechaNacimiento()).isEqualTo(DEFAULT_FECHA_NACIMIENTO);
    }

    @Test
    @Transactional
    public void createVacunadorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = vacunadorRepository.findAll().size();

        // Create the Vacunador with an existing ID
        vacunador.setId(1L);
        VacunadorDTO vacunadorDTO = vacunadorMapper.toDto(vacunador);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVacunadorMockMvc.perform(post("/api/vacunadors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vacunadorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Vacunador in the database
        List<Vacunador> vacunadorList = vacunadorRepository.findAll();
        assertThat(vacunadorList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDocumentoIsRequired() throws Exception {
        int databaseSizeBeforeTest = vacunadorRepository.findAll().size();
        // set the field null
        vacunador.setDocumento(null);

        // Create the Vacunador, which fails.
        VacunadorDTO vacunadorDTO = vacunadorMapper.toDto(vacunador);

        restVacunadorMockMvc.perform(post("/api/vacunadors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vacunadorDTO)))
            .andExpect(status().isBadRequest());

        List<Vacunador> vacunadorList = vacunadorRepository.findAll();
        assertThat(vacunadorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = vacunadorRepository.findAll().size();
        // set the field null
        vacunador.setNombre(null);

        // Create the Vacunador, which fails.
        VacunadorDTO vacunadorDTO = vacunadorMapper.toDto(vacunador);

        restVacunadorMockMvc.perform(post("/api/vacunadors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vacunadorDTO)))
            .andExpect(status().isBadRequest());

        List<Vacunador> vacunadorList = vacunadorRepository.findAll();
        assertThat(vacunadorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkApellidoIsRequired() throws Exception {
        int databaseSizeBeforeTest = vacunadorRepository.findAll().size();
        // set the field null
        vacunador.setApellido(null);

        // Create the Vacunador, which fails.
        VacunadorDTO vacunadorDTO = vacunadorMapper.toDto(vacunador);

        restVacunadorMockMvc.perform(post("/api/vacunadors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vacunadorDTO)))
            .andExpect(status().isBadRequest());

        List<Vacunador> vacunadorList = vacunadorRepository.findAll();
        assertThat(vacunadorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTelefonoIsRequired() throws Exception {
        int databaseSizeBeforeTest = vacunadorRepository.findAll().size();
        // set the field null
        vacunador.setTelefono(null);

        // Create the Vacunador, which fails.
        VacunadorDTO vacunadorDTO = vacunadorMapper.toDto(vacunador);

        restVacunadorMockMvc.perform(post("/api/vacunadors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vacunadorDTO)))
            .andExpect(status().isBadRequest());

        List<Vacunador> vacunadorList = vacunadorRepository.findAll();
        assertThat(vacunadorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFechaNacimientoIsRequired() throws Exception {
        int databaseSizeBeforeTest = vacunadorRepository.findAll().size();
        // set the field null
        vacunador.setFechaNacimiento(null);

        // Create the Vacunador, which fails.
        VacunadorDTO vacunadorDTO = vacunadorMapper.toDto(vacunador);

        restVacunadorMockMvc.perform(post("/api/vacunadors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vacunadorDTO)))
            .andExpect(status().isBadRequest());

        List<Vacunador> vacunadorList = vacunadorRepository.findAll();
        assertThat(vacunadorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllVacunadors() throws Exception {
        // Initialize the database
        vacunadorRepository.saveAndFlush(vacunador);

        // Get all the vacunadorList
        restVacunadorMockMvc.perform(get("/api/vacunadors?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vacunador.getId().intValue())))
            .andExpect(jsonPath("$.[*].documento").value(hasItem(DEFAULT_DOCUMENTO.toString())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].apellido").value(hasItem(DEFAULT_APELLIDO.toString())))
            .andExpect(jsonPath("$.[*].telefono").value(hasItem(DEFAULT_TELEFONO.toString())))
            .andExpect(jsonPath("$.[*].correo").value(hasItem(DEFAULT_CORREO.toString())))
            .andExpect(jsonPath("$.[*].fechaNacimiento").value(hasItem(DEFAULT_FECHA_NACIMIENTO.toString())));
    }

    @Test
    @Transactional
    public void getVacunador() throws Exception {
        // Initialize the database
        vacunadorRepository.saveAndFlush(vacunador);

        // Get the vacunador
        restVacunadorMockMvc.perform(get("/api/vacunadors/{id}", vacunador.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(vacunador.getId().intValue()))
            .andExpect(jsonPath("$.documento").value(DEFAULT_DOCUMENTO.toString()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.apellido").value(DEFAULT_APELLIDO.toString()))
            .andExpect(jsonPath("$.telefono").value(DEFAULT_TELEFONO.toString()))
            .andExpect(jsonPath("$.correo").value(DEFAULT_CORREO.toString()))
            .andExpect(jsonPath("$.fechaNacimiento").value(DEFAULT_FECHA_NACIMIENTO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingVacunador() throws Exception {
        // Get the vacunador
        restVacunadorMockMvc.perform(get("/api/vacunadors/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVacunador() throws Exception {
        // Initialize the database
        vacunadorRepository.saveAndFlush(vacunador);
        int databaseSizeBeforeUpdate = vacunadorRepository.findAll().size();

        // Update the vacunador
        Vacunador updatedVacunador = vacunadorRepository.findOne(vacunador.getId());
        // Disconnect from session so that the updates on updatedVacunador are not directly saved in db
        em.detach(updatedVacunador);
        updatedVacunador
            .documento(UPDATED_DOCUMENTO)
            .nombre(UPDATED_NOMBRE)
            .apellido(UPDATED_APELLIDO)
            .telefono(UPDATED_TELEFONO)
            .correo(UPDATED_CORREO)
            .fechaNacimiento(UPDATED_FECHA_NACIMIENTO);
        VacunadorDTO vacunadorDTO = vacunadorMapper.toDto(updatedVacunador);

        restVacunadorMockMvc.perform(put("/api/vacunadors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vacunadorDTO)))
            .andExpect(status().isOk());

        // Validate the Vacunador in the database
        List<Vacunador> vacunadorList = vacunadorRepository.findAll();
        assertThat(vacunadorList).hasSize(databaseSizeBeforeUpdate);
        Vacunador testVacunador = vacunadorList.get(vacunadorList.size() - 1);
        assertThat(testVacunador.getDocumento()).isEqualTo(UPDATED_DOCUMENTO);
        assertThat(testVacunador.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testVacunador.getApellido()).isEqualTo(UPDATED_APELLIDO);
        assertThat(testVacunador.getTelefono()).isEqualTo(UPDATED_TELEFONO);
        assertThat(testVacunador.getCorreo()).isEqualTo(UPDATED_CORREO);
        assertThat(testVacunador.getFechaNacimiento()).isEqualTo(UPDATED_FECHA_NACIMIENTO);
    }

    @Test
    @Transactional
    public void updateNonExistingVacunador() throws Exception {
        int databaseSizeBeforeUpdate = vacunadorRepository.findAll().size();

        // Create the Vacunador
        VacunadorDTO vacunadorDTO = vacunadorMapper.toDto(vacunador);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restVacunadorMockMvc.perform(put("/api/vacunadors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vacunadorDTO)))
            .andExpect(status().isCreated());

        // Validate the Vacunador in the database
        List<Vacunador> vacunadorList = vacunadorRepository.findAll();
        assertThat(vacunadorList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteVacunador() throws Exception {
        // Initialize the database
        vacunadorRepository.saveAndFlush(vacunador);
        int databaseSizeBeforeDelete = vacunadorRepository.findAll().size();

        // Get the vacunador
        restVacunadorMockMvc.perform(delete("/api/vacunadors/{id}", vacunador.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Vacunador> vacunadorList = vacunadorRepository.findAll();
        assertThat(vacunadorList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Vacunador.class);
        Vacunador vacunador1 = new Vacunador();
        vacunador1.setId(1L);
        Vacunador vacunador2 = new Vacunador();
        vacunador2.setId(vacunador1.getId());
        assertThat(vacunador1).isEqualTo(vacunador2);
        vacunador2.setId(2L);
        assertThat(vacunador1).isNotEqualTo(vacunador2);
        vacunador1.setId(null);
        assertThat(vacunador1).isNotEqualTo(vacunador2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(VacunadorDTO.class);
        VacunadorDTO vacunadorDTO1 = new VacunadorDTO();
        vacunadorDTO1.setId(1L);
        VacunadorDTO vacunadorDTO2 = new VacunadorDTO();
        assertThat(vacunadorDTO1).isNotEqualTo(vacunadorDTO2);
        vacunadorDTO2.setId(vacunadorDTO1.getId());
        assertThat(vacunadorDTO1).isEqualTo(vacunadorDTO2);
        vacunadorDTO2.setId(2L);
        assertThat(vacunadorDTO1).isNotEqualTo(vacunadorDTO2);
        vacunadorDTO1.setId(null);
        assertThat(vacunadorDTO1).isNotEqualTo(vacunadorDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(vacunadorMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(vacunadorMapper.fromId(null)).isNull();
    }
}
