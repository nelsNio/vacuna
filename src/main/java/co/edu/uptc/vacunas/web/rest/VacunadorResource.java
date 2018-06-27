package co.edu.uptc.vacunas.web.rest;

import com.codahale.metrics.annotation.Timed;
import co.edu.uptc.vacunas.domain.Vacunador;

import co.edu.uptc.vacunas.repository.VacunadorRepository;
import co.edu.uptc.vacunas.web.rest.errors.BadRequestAlertException;
import co.edu.uptc.vacunas.web.rest.util.HeaderUtil;
import co.edu.uptc.vacunas.service.dto.VacunadorDTO;
import co.edu.uptc.vacunas.service.mapper.VacunadorMapper;
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
 * REST controller for managing Vacunador.
 */
@RestController
@RequestMapping("/api")
public class VacunadorResource {

    private final Logger log = LoggerFactory.getLogger(VacunadorResource.class);

    private static final String ENTITY_NAME = "vacunador";

    private final VacunadorRepository vacunadorRepository;

    private final VacunadorMapper vacunadorMapper;

    public VacunadorResource(VacunadorRepository vacunadorRepository, VacunadorMapper vacunadorMapper) {
        this.vacunadorRepository = vacunadorRepository;
        this.vacunadorMapper = vacunadorMapper;
    }

    /**
     * POST  /vacunadors : Create a new vacunador.
     *
     * @param vacunadorDTO the vacunadorDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new vacunadorDTO, or with status 400 (Bad Request) if the vacunador has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/vacunadors")
    @Timed
    public ResponseEntity<VacunadorDTO> createVacunador(@Valid @RequestBody VacunadorDTO vacunadorDTO) throws URISyntaxException {
        log.debug("REST request to save Vacunador : {}", vacunadorDTO);
        if (vacunadorDTO.getId() != null) {
            throw new BadRequestAlertException("A new vacunador cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Vacunador vacunador = vacunadorMapper.toEntity(vacunadorDTO);
        vacunador = vacunadorRepository.save(vacunador);
        VacunadorDTO result = vacunadorMapper.toDto(vacunador);
        return ResponseEntity.created(new URI("/api/vacunadors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /vacunadors : Updates an existing vacunador.
     *
     * @param vacunadorDTO the vacunadorDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated vacunadorDTO,
     * or with status 400 (Bad Request) if the vacunadorDTO is not valid,
     * or with status 500 (Internal Server Error) if the vacunadorDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/vacunadors")
    @Timed
    public ResponseEntity<VacunadorDTO> updateVacunador(@Valid @RequestBody VacunadorDTO vacunadorDTO) throws URISyntaxException {
        log.debug("REST request to update Vacunador : {}", vacunadorDTO);
        if (vacunadorDTO.getId() == null) {
            return createVacunador(vacunadorDTO);
        }
        Vacunador vacunador = vacunadorMapper.toEntity(vacunadorDTO);
        vacunador = vacunadorRepository.save(vacunador);
        VacunadorDTO result = vacunadorMapper.toDto(vacunador);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, vacunadorDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /vacunadors : get all the vacunadors.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of vacunadors in body
     */
    @GetMapping("/vacunadors")
    @Timed
    public List<VacunadorDTO> getAllVacunadors() {
        log.debug("REST request to get all Vacunadors");
        List<Vacunador> vacunadors = vacunadorRepository.findAll();
        return vacunadorMapper.toDto(vacunadors);
        }

    /**
     * GET  /vacunadors/:id : get the "id" vacunador.
     *
     * @param id the id of the vacunadorDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the vacunadorDTO, or with status 404 (Not Found)
     */
    @GetMapping("/vacunadors/{id}")
    @Timed
    public ResponseEntity<VacunadorDTO> getVacunador(@PathVariable Long id) {
        log.debug("REST request to get Vacunador : {}", id);
        Vacunador vacunador = vacunadorRepository.findOne(id);
        VacunadorDTO vacunadorDTO = vacunadorMapper.toDto(vacunador);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(vacunadorDTO));
    }

    /**
     * DELETE  /vacunadors/:id : delete the "id" vacunador.
     *
     * @param id the id of the vacunadorDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/vacunadors/{id}")
    @Timed
    public ResponseEntity<Void> deleteVacunador(@PathVariable Long id) {
        log.debug("REST request to delete Vacunador : {}", id);
        vacunadorRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
