package co.edu.uptc.vacunas.web.rest;

import co.edu.uptc.vacunas.VacunasApp;

import co.edu.uptc.vacunas.domain.Ips;
import co.edu.uptc.vacunas.domain.Municipio;
import co.edu.uptc.vacunas.repository.IpsRepository;
import co.edu.uptc.vacunas.service.dto.IpsDTO;
import co.edu.uptc.vacunas.service.mapper.IpsMapper;
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
 * Test class for the IpsResource REST controller.
 *
 * @see IpsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VacunasApp.class)
public class IpsResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_DIRECCION = "AAAAAAAAAA";
    private static final String UPDATED_DIRECCION = "BBBBBBBBBB";

    @Autowired
    private IpsRepository ipsRepository;

    @Autowired
    private IpsMapper ipsMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restIpsMockMvc;

    private Ips ips;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final IpsResource ipsResource = new IpsResource(ipsRepository, ipsMapper);
        this.restIpsMockMvc = MockMvcBuilders.standaloneSetup(ipsResource)
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
    public static Ips createEntity(EntityManager em) {
        Ips ips = new Ips()
            .nombre(DEFAULT_NOMBRE)
            .direccion(DEFAULT_DIRECCION);
        // Add required entity
        Municipio municipio = MunicipioResourceIntTest.createEntity(em);
        em.persist(municipio);
        em.flush();
        ips.getMunicipios().add(municipio);
        return ips;
    }

    @Before
    public void initTest() {
        ips = createEntity(em);
    }

    @Test
    @Transactional
    public void createIps() throws Exception {
        int databaseSizeBeforeCreate = ipsRepository.findAll().size();

        // Create the Ips
        IpsDTO ipsDTO = ipsMapper.toDto(ips);
        restIpsMockMvc.perform(post("/api/ips")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ipsDTO)))
            .andExpect(status().isCreated());

        // Validate the Ips in the database
        List<Ips> ipsList = ipsRepository.findAll();
        assertThat(ipsList).hasSize(databaseSizeBeforeCreate + 1);
        Ips testIps = ipsList.get(ipsList.size() - 1);
        assertThat(testIps.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testIps.getDireccion()).isEqualTo(DEFAULT_DIRECCION);
    }

    @Test
    @Transactional
    public void createIpsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ipsRepository.findAll().size();

        // Create the Ips with an existing ID
        ips.setId(1L);
        IpsDTO ipsDTO = ipsMapper.toDto(ips);

        // An entity with an existing ID cannot be created, so this API call must fail
        restIpsMockMvc.perform(post("/api/ips")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ipsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Ips in the database
        List<Ips> ipsList = ipsRepository.findAll();
        assertThat(ipsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = ipsRepository.findAll().size();
        // set the field null
        ips.setNombre(null);

        // Create the Ips, which fails.
        IpsDTO ipsDTO = ipsMapper.toDto(ips);

        restIpsMockMvc.perform(post("/api/ips")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ipsDTO)))
            .andExpect(status().isBadRequest());

        List<Ips> ipsList = ipsRepository.findAll();
        assertThat(ipsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDireccionIsRequired() throws Exception {
        int databaseSizeBeforeTest = ipsRepository.findAll().size();
        // set the field null
        ips.setDireccion(null);

        // Create the Ips, which fails.
        IpsDTO ipsDTO = ipsMapper.toDto(ips);

        restIpsMockMvc.perform(post("/api/ips")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ipsDTO)))
            .andExpect(status().isBadRequest());

        List<Ips> ipsList = ipsRepository.findAll();
        assertThat(ipsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllIps() throws Exception {
        // Initialize the database
        ipsRepository.saveAndFlush(ips);

        // Get all the ipsList
        restIpsMockMvc.perform(get("/api/ips?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ips.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].direccion").value(hasItem(DEFAULT_DIRECCION.toString())));
    }

    @Test
    @Transactional
    public void getIps() throws Exception {
        // Initialize the database
        ipsRepository.saveAndFlush(ips);

        // Get the ips
        restIpsMockMvc.perform(get("/api/ips/{id}", ips.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(ips.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.direccion").value(DEFAULT_DIRECCION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingIps() throws Exception {
        // Get the ips
        restIpsMockMvc.perform(get("/api/ips/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateIps() throws Exception {
        // Initialize the database
        ipsRepository.saveAndFlush(ips);
        int databaseSizeBeforeUpdate = ipsRepository.findAll().size();

        // Update the ips
        Ips updatedIps = ipsRepository.findOne(ips.getId());
        // Disconnect from session so that the updates on updatedIps are not directly saved in db
        em.detach(updatedIps);
        updatedIps
            .nombre(UPDATED_NOMBRE)
            .direccion(UPDATED_DIRECCION);
        IpsDTO ipsDTO = ipsMapper.toDto(updatedIps);

        restIpsMockMvc.perform(put("/api/ips")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ipsDTO)))
            .andExpect(status().isOk());

        // Validate the Ips in the database
        List<Ips> ipsList = ipsRepository.findAll();
        assertThat(ipsList).hasSize(databaseSizeBeforeUpdate);
        Ips testIps = ipsList.get(ipsList.size() - 1);
        assertThat(testIps.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testIps.getDireccion()).isEqualTo(UPDATED_DIRECCION);
    }

    @Test
    @Transactional
    public void updateNonExistingIps() throws Exception {
        int databaseSizeBeforeUpdate = ipsRepository.findAll().size();

        // Create the Ips
        IpsDTO ipsDTO = ipsMapper.toDto(ips);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restIpsMockMvc.perform(put("/api/ips")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ipsDTO)))
            .andExpect(status().isCreated());

        // Validate the Ips in the database
        List<Ips> ipsList = ipsRepository.findAll();
        assertThat(ipsList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteIps() throws Exception {
        // Initialize the database
        ipsRepository.saveAndFlush(ips);
        int databaseSizeBeforeDelete = ipsRepository.findAll().size();

        // Get the ips
        restIpsMockMvc.perform(delete("/api/ips/{id}", ips.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Ips> ipsList = ipsRepository.findAll();
        assertThat(ipsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Ips.class);
        Ips ips1 = new Ips();
        ips1.setId(1L);
        Ips ips2 = new Ips();
        ips2.setId(ips1.getId());
        assertThat(ips1).isEqualTo(ips2);
        ips2.setId(2L);
        assertThat(ips1).isNotEqualTo(ips2);
        ips1.setId(null);
        assertThat(ips1).isNotEqualTo(ips2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(IpsDTO.class);
        IpsDTO ipsDTO1 = new IpsDTO();
        ipsDTO1.setId(1L);
        IpsDTO ipsDTO2 = new IpsDTO();
        assertThat(ipsDTO1).isNotEqualTo(ipsDTO2);
        ipsDTO2.setId(ipsDTO1.getId());
        assertThat(ipsDTO1).isEqualTo(ipsDTO2);
        ipsDTO2.setId(2L);
        assertThat(ipsDTO1).isNotEqualTo(ipsDTO2);
        ipsDTO1.setId(null);
        assertThat(ipsDTO1).isNotEqualTo(ipsDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(ipsMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(ipsMapper.fromId(null)).isNull();
    }
}
