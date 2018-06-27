package co.edu.uptc.vacunas.web.rest;

import com.codahale.metrics.annotation.Timed;
import co.edu.uptc.vacunas.domain.Departamento;

import co.edu.uptc.vacunas.repository.DepartamentoRepository;
import co.edu.uptc.vacunas.web.rest.errors.BadRequestAlertException;
import co.edu.uptc.vacunas.web.rest.util.HeaderUtil;
import co.edu.uptc.vacunas.service.dto.DepartamentoDTO;
import co.edu.uptc.vacunas.service.mapper.DepartamentoMapper;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Departamento.
 */
@RestController
@RequestMapping("/api")
public class DepartamentoResource {

    private final Logger log = LoggerFactory.getLogger(DepartamentoResource.class);

    private static final String ENTITY_NAME = "departamento";

    private final DepartamentoRepository departamentoRepository;

    private final DepartamentoMapper departamentoMapper;

    public DepartamentoResource(DepartamentoRepository departamentoRepository, DepartamentoMapper departamentoMapper) {
        this.departamentoRepository = departamentoRepository;
        this.departamentoMapper = departamentoMapper;
    }

    /**
     * POST  /departamentos : Create a new departamento.
     *
     * @param departamentoDTO the departamentoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new departamentoDTO, or with status 400 (Bad Request) if the departamento has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/departamentos")
    @Timed
    public ResponseEntity<DepartamentoDTO> createDepartamento(@Valid @RequestBody DepartamentoDTO departamentoDTO) throws URISyntaxException {
        log.debug("REST request to save Departamento : {}", departamentoDTO);
        if (departamentoDTO.getId() != null) {
            throw new BadRequestAlertException("A new departamento cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Departamento departamento = departamentoMapper.toEntity(departamentoDTO);
        departamento = departamentoRepository.save(departamento);
        DepartamentoDTO result = departamentoMapper.toDto(departamento);
        return ResponseEntity.created(new URI("/api/departamentos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /departamentos : Updates an existing departamento.
     *
     * @param departamentoDTO the departamentoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated departamentoDTO,
     * or with status 400 (Bad Request) if the departamentoDTO is not valid,
     * or with status 500 (Internal Server Error) if the departamentoDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/departamentos")
    @Timed
    public ResponseEntity<DepartamentoDTO> updateDepartamento(@Valid @RequestBody DepartamentoDTO departamentoDTO) throws URISyntaxException {
        log.debug("REST request to update Departamento : {}", departamentoDTO);
        if (departamentoDTO.getId() == null) {
            return createDepartamento(departamentoDTO);
        }
        Departamento departamento = departamentoMapper.toEntity(departamentoDTO);
        departamento = departamentoRepository.save(departamento);
        DepartamentoDTO result = departamentoMapper.toDto(departamento);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, departamentoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /departamentos : get all the departamentos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of departamentos in body
     */
    @GetMapping("/departamentos")
    @Timed
    public List<DepartamentoDTO> getAllDepartamentos() {
        log.debug("REST request to get all Departamentos");
        List<Departamento> departamentos = departamentoRepository.findAll();
        return departamentoMapper.toDto(departamentos);
        }

    /**
     * GET  /departamentos/:id : get the "id" departamento.
     *
     * @param id the id of the departamentoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the departamentoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/departamentos/{id}")
    @Timed
    public ResponseEntity<DepartamentoDTO> getDepartamento(@PathVariable Long id) {
        log.debug("REST request to get Departamento : {}", id);
        Departamento departamento = departamentoRepository.findOne(id);
        DepartamentoDTO departamentoDTO = departamentoMapper.toDto(departamento);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(departamentoDTO));
    }

    /**
     * DELETE  /departamentos/:id : delete the "id" departamento.
     *
     * @param id the id of the departamentoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/departamentos/{id}")
    @Timed
    public ResponseEntity<Void> deleteDepartamento(@PathVariable Long id) {
        log.debug("REST request to delete Departamento : {}", id);
        departamentoRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
