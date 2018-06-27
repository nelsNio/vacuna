package co.edu.uptc.vacunas.web.rest;

import com.codahale.metrics.annotation.Timed;
import co.edu.uptc.vacunas.domain.Dosis;

import co.edu.uptc.vacunas.repository.DosisRepository;
import co.edu.uptc.vacunas.web.rest.errors.BadRequestAlertException;
import co.edu.uptc.vacunas.web.rest.util.HeaderUtil;
import co.edu.uptc.vacunas.service.dto.DosisDTO;
import co.edu.uptc.vacunas.service.mapper.DosisMapper;
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
 * REST controller for managing Dosis.
 */
@RestController
@RequestMapping("/api")
public class DosisResource {

    private final Logger log = LoggerFactory.getLogger(DosisResource.class);

    private static final String ENTITY_NAME = "dosis";

    private final DosisRepository dosisRepository;

    private final DosisMapper dosisMapper;

    public DosisResource(DosisRepository dosisRepository, DosisMapper dosisMapper) {
        this.dosisRepository = dosisRepository;
        this.dosisMapper = dosisMapper;
    }

    /**
     * POST  /doses : Create a new dosis.
     *
     * @param dosisDTO the dosisDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new dosisDTO, or with status 400 (Bad Request) if the dosis has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/doses")
    @Timed
    public ResponseEntity<DosisDTO> createDosis(@Valid @RequestBody DosisDTO dosisDTO) throws URISyntaxException {
        log.debug("REST request to save Dosis : {}", dosisDTO);
        if (dosisDTO.getId() != null) {
            throw new BadRequestAlertException("A new dosis cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Dosis dosis = dosisMapper.toEntity(dosisDTO);
        dosis = dosisRepository.save(dosis);
        DosisDTO result = dosisMapper.toDto(dosis);
        return ResponseEntity.created(new URI("/api/doses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /doses : Updates an existing dosis.
     *
     * @param dosisDTO the dosisDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated dosisDTO,
     * or with status 400 (Bad Request) if the dosisDTO is not valid,
     * or with status 500 (Internal Server Error) if the dosisDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/doses")
    @Timed
    public ResponseEntity<DosisDTO> updateDosis(@Valid @RequestBody DosisDTO dosisDTO) throws URISyntaxException {
        log.debug("REST request to update Dosis : {}", dosisDTO);
        if (dosisDTO.getId() == null) {
            return createDosis(dosisDTO);
        }
        Dosis dosis = dosisMapper.toEntity(dosisDTO);
        dosis = dosisRepository.save(dosis);
        DosisDTO result = dosisMapper.toDto(dosis);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, dosisDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /doses : get all the doses.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of doses in body
     */
    @GetMapping("/doses")
    @Timed
    public List<DosisDTO> getAllDoses() {
        log.debug("REST request to get all Doses");
        List<Dosis> doses = dosisRepository.findAll();
        return dosisMapper.toDto(doses);
        }

    /**
     * GET  /doses/:id : get the "id" dosis.
     *
     * @param id the id of the dosisDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the dosisDTO, or with status 404 (Not Found)
     */
    @GetMapping("/doses/{id}")
    @Timed
    public ResponseEntity<DosisDTO> getDosis(@PathVariable Long id) {
        log.debug("REST request to get Dosis : {}", id);
        Dosis dosis = dosisRepository.findOne(id);
        DosisDTO dosisDTO = dosisMapper.toDto(dosis);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(dosisDTO));
    }

    /**
     * DELETE  /doses/:id : delete the "id" dosis.
     *
     * @param id the id of the dosisDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/doses/{id}")
    @Timed
    public ResponseEntity<Void> deleteDosis(@PathVariable Long id) {
        log.debug("REST request to delete Dosis : {}", id);
        dosisRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
