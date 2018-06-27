package co.edu.uptc.vacunas.web.rest;

import com.codahale.metrics.annotation.Timed;
import co.edu.uptc.vacunas.domain.TipoDocumento;

import co.edu.uptc.vacunas.repository.TipoDocumentoRepository;
import co.edu.uptc.vacunas.web.rest.errors.BadRequestAlertException;
import co.edu.uptc.vacunas.web.rest.util.HeaderUtil;
import co.edu.uptc.vacunas.service.dto.TipoDocumentoDTO;
import co.edu.uptc.vacunas.service.mapper.TipoDocumentoMapper;
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
 * REST controller for managing TipoDocumento.
 */
@RestController
@RequestMapping("/api")
public class TipoDocumentoResource {

    private final Logger log = LoggerFactory.getLogger(TipoDocumentoResource.class);

    private static final String ENTITY_NAME = "tipoDocumento";

    private final TipoDocumentoRepository tipoDocumentoRepository;

    private final TipoDocumentoMapper tipoDocumentoMapper;

    public TipoDocumentoResource(TipoDocumentoRepository tipoDocumentoRepository, TipoDocumentoMapper tipoDocumentoMapper) {
        this.tipoDocumentoRepository = tipoDocumentoRepository;
        this.tipoDocumentoMapper = tipoDocumentoMapper;
    }

    /**
     * POST  /tipo-documentos : Create a new tipoDocumento.
     *
     * @param tipoDocumentoDTO the tipoDocumentoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tipoDocumentoDTO, or with status 400 (Bad Request) if the tipoDocumento has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tipo-documentos")
    @Timed
    public ResponseEntity<TipoDocumentoDTO> createTipoDocumento(@Valid @RequestBody TipoDocumentoDTO tipoDocumentoDTO) throws URISyntaxException {
        log.debug("REST request to save TipoDocumento : {}", tipoDocumentoDTO);
        if (tipoDocumentoDTO.getId() != null) {
            throw new BadRequestAlertException("A new tipoDocumento cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TipoDocumento tipoDocumento = tipoDocumentoMapper.toEntity(tipoDocumentoDTO);
        tipoDocumento = tipoDocumentoRepository.save(tipoDocumento);
        TipoDocumentoDTO result = tipoDocumentoMapper.toDto(tipoDocumento);
        return ResponseEntity.created(new URI("/api/tipo-documentos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tipo-documentos : Updates an existing tipoDocumento.
     *
     * @param tipoDocumentoDTO the tipoDocumentoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tipoDocumentoDTO,
     * or with status 400 (Bad Request) if the tipoDocumentoDTO is not valid,
     * or with status 500 (Internal Server Error) if the tipoDocumentoDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tipo-documentos")
    @Timed
    public ResponseEntity<TipoDocumentoDTO> updateTipoDocumento(@Valid @RequestBody TipoDocumentoDTO tipoDocumentoDTO) throws URISyntaxException {
        log.debug("REST request to update TipoDocumento : {}", tipoDocumentoDTO);
        if (tipoDocumentoDTO.getId() == null) {
            return createTipoDocumento(tipoDocumentoDTO);
        }
        TipoDocumento tipoDocumento = tipoDocumentoMapper.toEntity(tipoDocumentoDTO);
        tipoDocumento = tipoDocumentoRepository.save(tipoDocumento);
        TipoDocumentoDTO result = tipoDocumentoMapper.toDto(tipoDocumento);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, tipoDocumentoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tipo-documentos : get all the tipoDocumentos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of tipoDocumentos in body
     */
    @GetMapping("/tipo-documentos")
    @Timed
    public List<TipoDocumentoDTO> getAllTipoDocumentos() {
        log.debug("REST request to get all TipoDocumentos");
        List<TipoDocumento> tipoDocumentos = tipoDocumentoRepository.findAll();
        return tipoDocumentoMapper.toDto(tipoDocumentos);
        }

    /**
     * GET  /tipo-documentos/:id : get the "id" tipoDocumento.
     *
     * @param id the id of the tipoDocumentoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tipoDocumentoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/tipo-documentos/{id}")
    @Timed
    public ResponseEntity<TipoDocumentoDTO> getTipoDocumento(@PathVariable Long id) {
        log.debug("REST request to get TipoDocumento : {}", id);
        TipoDocumento tipoDocumento = tipoDocumentoRepository.findOne(id);
        TipoDocumentoDTO tipoDocumentoDTO = tipoDocumentoMapper.toDto(tipoDocumento);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(tipoDocumentoDTO));
    }

    /**
     * DELETE  /tipo-documentos/:id : delete the "id" tipoDocumento.
     *
     * @param id the id of the tipoDocumentoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tipo-documentos/{id}")
    @Timed
    public ResponseEntity<Void> deleteTipoDocumento(@PathVariable Long id) {
        log.debug("REST request to delete TipoDocumento : {}", id);
        tipoDocumentoRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
