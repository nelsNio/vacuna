package co.edu.uptc.vacunas.web.rest;

import com.codahale.metrics.annotation.Timed;
import co.edu.uptc.vacunas.domain.Pais;

import co.edu.uptc.vacunas.repository.PaisRepository;
import co.edu.uptc.vacunas.web.rest.errors.BadRequestAlertException;
import co.edu.uptc.vacunas.web.rest.util.HeaderUtil;
import co.edu.uptc.vacunas.service.dto.PaisDTO;
import co.edu.uptc.vacunas.service.mapper.PaisMapper;
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
 * REST controller for managing Pais.
 */
@RestController
@RequestMapping("/api")
public class PaisResource {

    private final Logger log = LoggerFactory.getLogger(PaisResource.class);

    private static final String ENTITY_NAME = "pais";

    private final PaisRepository paisRepository;

    private final PaisMapper paisMapper;

    public PaisResource(PaisRepository paisRepository, PaisMapper paisMapper) {
        this.paisRepository = paisRepository;
        this.paisMapper = paisMapper;
    }

    /**
     * POST  /pais : Create a new pais.
     *
     * @param paisDTO the paisDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new paisDTO, or with status 400 (Bad Request) if the pais has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/pais")
    @Timed
    public ResponseEntity<PaisDTO> createPais(@Valid @RequestBody PaisDTO paisDTO) throws URISyntaxException {
        log.debug("REST request to save Pais : {}", paisDTO);
        if (paisDTO.getId() != null) {
            throw new BadRequestAlertException("A new pais cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Pais pais = paisMapper.toEntity(paisDTO);
        pais = paisRepository.save(pais);
        PaisDTO result = paisMapper.toDto(pais);
        return ResponseEntity.created(new URI("/api/pais/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /pais : Updates an existing pais.
     *
     * @param paisDTO the paisDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated paisDTO,
     * or with status 400 (Bad Request) if the paisDTO is not valid,
     * or with status 500 (Internal Server Error) if the paisDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/pais")
    @Timed
    public ResponseEntity<PaisDTO> updatePais(@Valid @RequestBody PaisDTO paisDTO) throws URISyntaxException {
        log.debug("REST request to update Pais : {}", paisDTO);
        if (paisDTO.getId() == null) {
            return createPais(paisDTO);
        }
        Pais pais = paisMapper.toEntity(paisDTO);
        pais = paisRepository.save(pais);
        PaisDTO result = paisMapper.toDto(pais);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, paisDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /pais : get all the pais.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of pais in body
     */
    @GetMapping("/pais")
    @Timed
    public List<PaisDTO> getAllPais() {
        log.debug("REST request to get all Pais");
        List<Pais> pais = paisRepository.findAll();
        return paisMapper.toDto(pais);
        }

    /**
     * GET  /pais/:id : get the "id" pais.
     *
     * @param id the id of the paisDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the paisDTO, or with status 404 (Not Found)
     */
    @GetMapping("/pais/{id}")
    @Timed
    public ResponseEntity<PaisDTO> getPais(@PathVariable Long id) {
        log.debug("REST request to get Pais : {}", id);
        Pais pais = paisRepository.findOne(id);
        PaisDTO paisDTO = paisMapper.toDto(pais);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(paisDTO));
    }

    /**
     * DELETE  /pais/:id : delete the "id" pais.
     *
     * @param id the id of the paisDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/pais/{id}")
    @Timed
    public ResponseEntity<Void> deletePais(@PathVariable Long id) {
        log.debug("REST request to delete Pais : {}", id);
        paisRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
