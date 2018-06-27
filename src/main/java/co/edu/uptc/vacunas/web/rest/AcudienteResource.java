package co.edu.uptc.vacunas.web.rest;

import com.codahale.metrics.annotation.Timed;
import co.edu.uptc.vacunas.domain.Acudiente;

import co.edu.uptc.vacunas.repository.AcudienteRepository;
import co.edu.uptc.vacunas.web.rest.errors.BadRequestAlertException;
import co.edu.uptc.vacunas.web.rest.util.HeaderUtil;
import co.edu.uptc.vacunas.service.dto.AcudienteDTO;
import co.edu.uptc.vacunas.service.mapper.AcudienteMapper;
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
 * REST controller for managing Acudiente.
 */
@RestController
@RequestMapping("/api")
public class AcudienteResource {

    private final Logger log = LoggerFactory.getLogger(AcudienteResource.class);

    private static final String ENTITY_NAME = "acudiente";

    private final AcudienteRepository acudienteRepository;

    private final AcudienteMapper acudienteMapper;

    public AcudienteResource(AcudienteRepository acudienteRepository, AcudienteMapper acudienteMapper) {
        this.acudienteRepository = acudienteRepository;
        this.acudienteMapper = acudienteMapper;
    }

    /**
     * POST  /acudientes : Create a new acudiente.
     *
     * @param acudienteDTO the acudienteDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new acudienteDTO, or with status 400 (Bad Request) if the acudiente has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/acudientes")
    @Timed
    public ResponseEntity<AcudienteDTO> createAcudiente(@Valid @RequestBody AcudienteDTO acudienteDTO) throws URISyntaxException {
        log.debug("REST request to save Acudiente : {}", acudienteDTO);
        if (acudienteDTO.getId() != null) {
            throw new BadRequestAlertException("A new acudiente cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Acudiente acudiente = acudienteMapper.toEntity(acudienteDTO);
        acudiente = acudienteRepository.save(acudiente);
        AcudienteDTO result = acudienteMapper.toDto(acudiente);
        return ResponseEntity.created(new URI("/api/acudientes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /acudientes : Updates an existing acudiente.
     *
     * @param acudienteDTO the acudienteDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated acudienteDTO,
     * or with status 400 (Bad Request) if the acudienteDTO is not valid,
     * or with status 500 (Internal Server Error) if the acudienteDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/acudientes")
    @Timed
    public ResponseEntity<AcudienteDTO> updateAcudiente(@Valid @RequestBody AcudienteDTO acudienteDTO) throws URISyntaxException {
        log.debug("REST request to update Acudiente : {}", acudienteDTO);
        if (acudienteDTO.getId() == null) {
            return createAcudiente(acudienteDTO);
        }
        Acudiente acudiente = acudienteMapper.toEntity(acudienteDTO);
        acudiente = acudienteRepository.save(acudiente);
        AcudienteDTO result = acudienteMapper.toDto(acudiente);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, acudienteDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /acudientes : get all the acudientes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of acudientes in body
     */
    @GetMapping("/acudientes")
    @Timed
    public List<AcudienteDTO> getAllAcudientes() {
        log.debug("REST request to get all Acudientes");
        List<Acudiente> acudientes = acudienteRepository.findAll();
        return acudienteMapper.toDto(acudientes);
        }

    /**
     * GET  /acudientes/:id : get the "id" acudiente.
     *
     * @param id the id of the acudienteDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the acudienteDTO, or with status 404 (Not Found)
     */
    @GetMapping("/acudientes/{id}")
    @Timed
    public ResponseEntity<AcudienteDTO> getAcudiente(@PathVariable Long id) {
        log.debug("REST request to get Acudiente : {}", id);
        Acudiente acudiente = acudienteRepository.findOne(id);
        AcudienteDTO acudienteDTO = acudienteMapper.toDto(acudiente);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(acudienteDTO));
    }

    /**
     * DELETE  /acudientes/:id : delete the "id" acudiente.
     *
     * @param id the id of the acudienteDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/acudientes/{id}")
    @Timed
    public ResponseEntity<Void> deleteAcudiente(@PathVariable Long id) {
        log.debug("REST request to delete Acudiente : {}", id);
        acudienteRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
