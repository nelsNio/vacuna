package co.edu.uptc.vacunas.web.rest;

import com.codahale.metrics.annotation.Timed;
import co.edu.uptc.vacunas.domain.AplicacionVacuna;

import co.edu.uptc.vacunas.repository.AplicacionVacunaRepository;
import co.edu.uptc.vacunas.web.rest.errors.BadRequestAlertException;
import co.edu.uptc.vacunas.web.rest.util.HeaderUtil;
import co.edu.uptc.vacunas.service.dto.AplicacionVacunaDTO;
import co.edu.uptc.vacunas.service.mapper.AplicacionVacunaMapper;
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
 * REST controller for managing AplicacionVacuna.
 */
@RestController
@RequestMapping("/api")
public class AplicacionVacunaResource {

    private final Logger log = LoggerFactory.getLogger(AplicacionVacunaResource.class);

    private static final String ENTITY_NAME = "aplicacionVacuna";

    private final AplicacionVacunaRepository aplicacionVacunaRepository;

    private final AplicacionVacunaMapper aplicacionVacunaMapper;

    public AplicacionVacunaResource(AplicacionVacunaRepository aplicacionVacunaRepository, AplicacionVacunaMapper aplicacionVacunaMapper) {
        this.aplicacionVacunaRepository = aplicacionVacunaRepository;
        this.aplicacionVacunaMapper = aplicacionVacunaMapper;
    }

    /**
     * POST  /aplicacion-vacunas : Create a new aplicacionVacuna.
     *
     * @param aplicacionVacunaDTO the aplicacionVacunaDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new aplicacionVacunaDTO, or with status 400 (Bad Request) if the aplicacionVacuna has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/aplicacion-vacunas")
    @Timed
    public ResponseEntity<AplicacionVacunaDTO> createAplicacionVacuna(@Valid @RequestBody AplicacionVacunaDTO aplicacionVacunaDTO) throws URISyntaxException {
        log.debug("REST request to save AplicacionVacuna : {}", aplicacionVacunaDTO);
        if (aplicacionVacunaDTO.getId() != null) {
            throw new BadRequestAlertException("A new aplicacionVacuna cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AplicacionVacuna aplicacionVacuna = aplicacionVacunaMapper.toEntity(aplicacionVacunaDTO);
        aplicacionVacuna = aplicacionVacunaRepository.save(aplicacionVacuna);
        AplicacionVacunaDTO result = aplicacionVacunaMapper.toDto(aplicacionVacuna);
        return ResponseEntity.created(new URI("/api/aplicacion-vacunas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /aplicacion-vacunas : Updates an existing aplicacionVacuna.
     *
     * @param aplicacionVacunaDTO the aplicacionVacunaDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated aplicacionVacunaDTO,
     * or with status 400 (Bad Request) if the aplicacionVacunaDTO is not valid,
     * or with status 500 (Internal Server Error) if the aplicacionVacunaDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/aplicacion-vacunas")
    @Timed
    public ResponseEntity<AplicacionVacunaDTO> updateAplicacionVacuna(@Valid @RequestBody AplicacionVacunaDTO aplicacionVacunaDTO) throws URISyntaxException {
        log.debug("REST request to update AplicacionVacuna : {}", aplicacionVacunaDTO);
        if (aplicacionVacunaDTO.getId() == null) {
            return createAplicacionVacuna(aplicacionVacunaDTO);
        }
        AplicacionVacuna aplicacionVacuna = aplicacionVacunaMapper.toEntity(aplicacionVacunaDTO);
        aplicacionVacuna = aplicacionVacunaRepository.save(aplicacionVacuna);
        AplicacionVacunaDTO result = aplicacionVacunaMapper.toDto(aplicacionVacuna);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, aplicacionVacunaDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /aplicacion-vacunas : get all the aplicacionVacunas.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of aplicacionVacunas in body
     */
    @GetMapping("/aplicacion-vacunas")
    @Timed
    public List<AplicacionVacunaDTO> getAllAplicacionVacunas() {
        log.debug("REST request to get all AplicacionVacunas");
        List<AplicacionVacuna> aplicacionVacunas = aplicacionVacunaRepository.findAll();
        return aplicacionVacunaMapper.toDto(aplicacionVacunas);
        }

    /**
     * GET  /aplicacion-vacunas/:id : get the "id" aplicacionVacuna.
     *
     * @param id the id of the aplicacionVacunaDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the aplicacionVacunaDTO, or with status 404 (Not Found)
     */
    @GetMapping("/aplicacion-vacunas/{id}")
    @Timed
    public ResponseEntity<AplicacionVacunaDTO> getAplicacionVacuna(@PathVariable Long id) {
        log.debug("REST request to get AplicacionVacuna : {}", id);
        AplicacionVacuna aplicacionVacuna = aplicacionVacunaRepository.findOne(id);
        AplicacionVacunaDTO aplicacionVacunaDTO = aplicacionVacunaMapper.toDto(aplicacionVacuna);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(aplicacionVacunaDTO));
    }

    /**
     * DELETE  /aplicacion-vacunas/:id : delete the "id" aplicacionVacuna.
     *
     * @param id the id of the aplicacionVacunaDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/aplicacion-vacunas/{id}")
    @Timed
    public ResponseEntity<Void> deleteAplicacionVacuna(@PathVariable Long id) {
        log.debug("REST request to delete AplicacionVacuna : {}", id);
        aplicacionVacunaRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
