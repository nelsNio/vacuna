package co.edu.uptc.vacunas.web.rest;

import com.codahale.metrics.annotation.Timed;
import co.edu.uptc.vacunas.domain.Regimen;

import co.edu.uptc.vacunas.repository.RegimenRepository;
import co.edu.uptc.vacunas.web.rest.errors.BadRequestAlertException;
import co.edu.uptc.vacunas.web.rest.util.HeaderUtil;
import co.edu.uptc.vacunas.service.dto.RegimenDTO;
import co.edu.uptc.vacunas.service.mapper.RegimenMapper;
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
 * REST controller for managing Regimen.
 */
@RestController
@RequestMapping("/api")
public class RegimenResource {

    private final Logger log = LoggerFactory.getLogger(RegimenResource.class);

    private static final String ENTITY_NAME = "regimen";

    private final RegimenRepository regimenRepository;

    private final RegimenMapper regimenMapper;

    public RegimenResource(RegimenRepository regimenRepository, RegimenMapper regimenMapper) {
        this.regimenRepository = regimenRepository;
        this.regimenMapper = regimenMapper;
    }

    /**
     * POST  /regimen : Create a new regimen.
     *
     * @param regimenDTO the regimenDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new regimenDTO, or with status 400 (Bad Request) if the regimen has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/regimen")
    @Timed
    public ResponseEntity<RegimenDTO> createRegimen(@Valid @RequestBody RegimenDTO regimenDTO) throws URISyntaxException {
        log.debug("REST request to save Regimen : {}", regimenDTO);
        if (regimenDTO.getId() != null) {
            throw new BadRequestAlertException("A new regimen cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Regimen regimen = regimenMapper.toEntity(regimenDTO);
        regimen = regimenRepository.save(regimen);
        RegimenDTO result = regimenMapper.toDto(regimen);
        return ResponseEntity.created(new URI("/api/regimen/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /regimen : Updates an existing regimen.
     *
     * @param regimenDTO the regimenDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated regimenDTO,
     * or with status 400 (Bad Request) if the regimenDTO is not valid,
     * or with status 500 (Internal Server Error) if the regimenDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/regimen")
    @Timed
    public ResponseEntity<RegimenDTO> updateRegimen(@Valid @RequestBody RegimenDTO regimenDTO) throws URISyntaxException {
        log.debug("REST request to update Regimen : {}", regimenDTO);
        if (regimenDTO.getId() == null) {
            return createRegimen(regimenDTO);
        }
        Regimen regimen = regimenMapper.toEntity(regimenDTO);
        regimen = regimenRepository.save(regimen);
        RegimenDTO result = regimenMapper.toDto(regimen);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, regimenDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /regimen : get all the regimen.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of regimen in body
     */
    @GetMapping("/regimen")
    @Timed
    public List<RegimenDTO> getAllRegimen() {
        log.debug("REST request to get all Regimen");
        List<Regimen> regimen = regimenRepository.findAll();
        return regimenMapper.toDto(regimen);
        }

    /**
     * GET  /regimen/:id : get the "id" regimen.
     *
     * @param id the id of the regimenDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the regimenDTO, or with status 404 (Not Found)
     */
    @GetMapping("/regimen/{id}")
    @Timed
    public ResponseEntity<RegimenDTO> getRegimen(@PathVariable Long id) {
        log.debug("REST request to get Regimen : {}", id);
        Regimen regimen = regimenRepository.findOne(id);
        RegimenDTO regimenDTO = regimenMapper.toDto(regimen);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(regimenDTO));
    }

    /**
     * DELETE  /regimen/:id : delete the "id" regimen.
     *
     * @param id the id of the regimenDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/regimen/{id}")
    @Timed
    public ResponseEntity<Void> deleteRegimen(@PathVariable Long id) {
        log.debug("REST request to delete Regimen : {}", id);
        regimenRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
