package co.edu.uptc.vacunas.web.rest;

import com.codahale.metrics.annotation.Timed;
import co.edu.uptc.vacunas.domain.Paciente;

import co.edu.uptc.vacunas.repository.PacienteRepository;
import co.edu.uptc.vacunas.web.rest.errors.BadRequestAlertException;
import co.edu.uptc.vacunas.web.rest.util.HeaderUtil;
import co.edu.uptc.vacunas.service.dto.PacienteDTO;
import co.edu.uptc.vacunas.service.dto.ReporteVacunadosDTO;
import co.edu.uptc.vacunas.service.mapper.PacienteMapper;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Paciente.
 */
@RestController
@RequestMapping("/api")
public class PacienteResource {

    private final Logger log = LoggerFactory.getLogger(PacienteResource.class);

    private static final String ENTITY_NAME = "paciente";

    private final PacienteRepository pacienteRepository;

    private final PacienteMapper pacienteMapper;

    public PacienteResource(PacienteRepository pacienteRepository, PacienteMapper pacienteMapper) {
        this.pacienteRepository = pacienteRepository;
        this.pacienteMapper = pacienteMapper;
    }

    /**
     * POST  /pacientes : Create a new paciente.
     *
     * @param pacienteDTO the pacienteDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new pacienteDTO, or with status 400 (Bad Request) if the paciente has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/pacientes")
    @Timed
    public ResponseEntity<PacienteDTO> createPaciente(@Valid @RequestBody PacienteDTO pacienteDTO) throws URISyntaxException {
        log.debug("REST request to save Paciente : {}", pacienteDTO);
        if (pacienteDTO.getId() != null) {
            throw new BadRequestAlertException("A new paciente cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Paciente paciente = pacienteMapper.toEntity(pacienteDTO);
        paciente = pacienteRepository.save(paciente);
        PacienteDTO result = pacienteMapper.toDto(paciente);
        return ResponseEntity.created(new URI("/api/pacientes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /pacientes : Updates an existing paciente.
     *
     * @param pacienteDTO the pacienteDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated pacienteDTO,
     * or with status 400 (Bad Request) if the pacienteDTO is not valid,
     * or with status 500 (Internal Server Error) if the pacienteDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/pacientes")
    @Timed
    public ResponseEntity<PacienteDTO> updatePaciente(@Valid @RequestBody PacienteDTO pacienteDTO) throws URISyntaxException {
        log.debug("REST request to update Paciente : {}", pacienteDTO);
        if (pacienteDTO.getId() == null) {
            return createPaciente(pacienteDTO);
        }
        Paciente paciente = pacienteMapper.toEntity(pacienteDTO);
        paciente = pacienteRepository.save(paciente);
        PacienteDTO result = pacienteMapper.toDto(paciente);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, pacienteDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /pacientes : get all the pacientes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of pacientes in body
     */
    @GetMapping("/pacientes")
    @Timed
    public List<PacienteDTO> getAllPacientes() {
        log.debug("REST request to get all Pacientes");
        List<Paciente> pacientes = pacienteRepository.findAll();
        return pacienteMapper.toDto(pacientes);
        }
    /**
     * GET  /pacientes : get all the pacientes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of pacientes in body
     */
    @GetMapping("/pacientes/reporte")
    @Timed
    public List<ReporteVacunadosDTO> getReportesPacientes() {
        log.debug("REST request to get all Pacientes");
        ReporteVacunadosDTO dTO = new ReporteVacunadosDTO();
        dTO.setDocumento("1021232112");
        dTO.setNombre("Marcos");
        dTO.setApellido("lo chupa");
        ArrayList<String> al = new ArrayList();
        al.add("peste");
        al.add("buaso");
        
        ArrayList<String> al2 = new ArrayList();
        al2.add("qweqwe");
        al2.add("zxccc");
        
        dTO.setVacunadoContra(al2);
        dTO.setVacunadoFaltantes(al);
        ArrayList<ReporteVacunadosDTO> al3 = new ArrayList();
        //al3.add(dTO);
        //al3.add(dTO);
        
        //object tiene esta estructura
        //documento
        //nombre
        //apellido
        //dosis
        
        List<String> documentos = new ArrayList<>();
        List<Object[]> list = pacienteRepository.findAllApplies();
        
        for (Object[] objects : list) {
            ReporteVacunadosDTO dTO1 = new ReporteVacunadosDTO();
            for (int i = 0; i < objects.length; i++) {
                if (!documentos.contains(""+objects[0])) {
                    documentos.add(""+objects[0]);
                    dTO1.setDocumento(""+objects[0]);
                    dTO1.setNombre(""+objects[1]);
                    dTO1.setApellido(""+objects[2]);
                    if (objects[3]==null) {
                        dTO1.addVacunaContra("");
                    }else{
                        dTO1.addVacunaContra(""+objects[3]);
                    }
                    dTO1.setVacunadoFaltantes(al2);
                }
            }
        }        
        
        return al3;
        }


    /**
     * GET  /pacientes/:id : get the "id" paciente.
     *
     * @param id the id of the pacienteDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the pacienteDTO, or with status 404 (Not Found)
     */
    @GetMapping("/pacientes/{id}")
    @Timed
    public ResponseEntity<PacienteDTO> getPaciente(@PathVariable Long id) {
        log.debug("REST request to get Paciente : {}", id);
        Paciente paciente = pacienteRepository.findOne(id);
        PacienteDTO pacienteDTO = pacienteMapper.toDto(paciente);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(pacienteDTO));
    }

    /**
     * DELETE  /pacientes/:id : delete the "id" paciente.
     *
     * @param id the id of the pacienteDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/pacientes/{id}")
    @Timed
    public ResponseEntity<Void> deletePaciente(@PathVariable Long id) {
        log.debug("REST request to delete Paciente : {}", id);
        pacienteRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
