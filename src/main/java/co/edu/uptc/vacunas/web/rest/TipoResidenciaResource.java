package co.edu.uptc.vacunas.web.rest;

import com.codahale.metrics.annotation.Timed;
import co.edu.uptc.vacunas.domain.TipoResidencia;

import co.edu.uptc.vacunas.repository.TipoResidenciaRepository;
import co.edu.uptc.vacunas.web.rest.errors.BadRequestAlertException;
import co.edu.uptc.vacunas.web.rest.util.HeaderUtil;
import co.edu.uptc.vacunas.service.dto.TipoResidenciaDTO;
import co.edu.uptc.vacunas.service.mapper.TipoResidenciaMapper;
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
 * REST controller for managing TipoResidencia.
 */
@RestController
@RequestMapping("/api")
public class TipoResidenciaResource {

    private final Logger log = LoggerFactory.getLogger(TipoResidenciaResource.class);

    private static final String ENTITY_NAME = "tipoResidencia";

    private final TipoResidenciaRepository tipoResidenciaRepository;

    private final TipoResidenciaMapper tipoResidenciaMapper;

    public TipoResidenciaResource(TipoResidenciaRepository tipoResidenciaRepository, TipoResidenciaMapper tipoResidenciaMapper) {
        this.tipoResidenciaRepository = tipoResidenciaRepository;
        this.tipoResidenciaMapper = tipoResidenciaMapper;
    }

    /**
     * POST  /tipo-residencias : Create a new tipoResidencia.
     *
     * @param tipoResidenciaDTO the tipoResidenciaDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tipoResidenciaDTO, or with status 400 (Bad Request) if the tipoResidencia has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tipo-residencias")
    @Timed
    public ResponseEntity<TipoResidenciaDTO> createTipoResidencia(@Valid @RequestBody TipoResidenciaDTO tipoResidenciaDTO) throws URISyntaxException {
        log.debug("REST request to save TipoResidencia : {}", tipoResidenciaDTO);
        if (tipoResidenciaDTO.getId() != null) {
            throw new BadRequestAlertException("A new tipoResidencia cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TipoResidencia tipoResidencia = tipoResidenciaMapper.toEntity(tipoResidenciaDTO);
        tipoResidencia = tipoResidenciaRepository.save(tipoResidencia);
        TipoResidenciaDTO result = tipoResidenciaMapper.toDto(tipoResidencia);
        return ResponseEntity.created(new URI("/api/tipo-residencias/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tipo-residencias : Updates an existing tipoResidencia.
     *
     * @param tipoResidenciaDTO the tipoResidenciaDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tipoResidenciaDTO,
     * or with status 400 (Bad Request) if the tipoResidenciaDTO is not valid,
     * or with status 500 (Internal Server Error) if the tipoResidenciaDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tipo-residencias")
    @Timed
    public ResponseEntity<TipoResidenciaDTO> updateTipoResidencia(@Valid @RequestBody TipoResidenciaDTO tipoResidenciaDTO) throws URISyntaxException {
        log.debug("REST request to update TipoResidencia : {}", tipoResidenciaDTO);
        if (tipoResidenciaDTO.getId() == null) {
            return createTipoResidencia(tipoResidenciaDTO);
        }
        TipoResidencia tipoResidencia = tipoResidenciaMapper.toEntity(tipoResidenciaDTO);
        tipoResidencia = tipoResidenciaRepository.save(tipoResidencia);
        TipoResidenciaDTO result = tipoResidenciaMapper.toDto(tipoResidencia);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, tipoResidenciaDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tipo-residencias : get all the tipoResidencias.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of tipoResidencias in body
     */
    @GetMapping("/tipo-residencias")
    @Timed
    public List<TipoResidenciaDTO> getAllTipoResidencias() {
        log.debug("REST request to get all TipoResidencias");
        List<TipoResidencia> tipoResidencias = tipoResidenciaRepository.findAll();
        return tipoResidenciaMapper.toDto(tipoResidencias);
        }

    /**
     * GET  /tipo-residencias/:id : get the "id" tipoResidencia.
     *
     * @param id the id of the tipoResidenciaDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tipoResidenciaDTO, or with status 404 (Not Found)
     */
    @GetMapping("/tipo-residencias/{id}")
    @Timed
    public ResponseEntity<TipoResidenciaDTO> getTipoResidencia(@PathVariable Long id) {
        log.debug("REST request to get TipoResidencia : {}", id);
        TipoResidencia tipoResidencia = tipoResidenciaRepository.findOne(id);
        TipoResidenciaDTO tipoResidenciaDTO = tipoResidenciaMapper.toDto(tipoResidencia);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(tipoResidenciaDTO));
    }

    /**
     * DELETE  /tipo-residencias/:id : delete the "id" tipoResidencia.
     *
     * @param id the id of the tipoResidenciaDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tipo-residencias/{id}")
    @Timed
    public ResponseEntity<Void> deleteTipoResidencia(@PathVariable Long id) {
        log.debug("REST request to delete TipoResidencia : {}", id);
        tipoResidenciaRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
