package co.edu.uptc.vacunas.web.rest;

import com.codahale.metrics.annotation.Timed;
import co.edu.uptc.vacunas.domain.Aseguradora;

import co.edu.uptc.vacunas.repository.AseguradoraRepository;
import co.edu.uptc.vacunas.web.rest.errors.BadRequestAlertException;
import co.edu.uptc.vacunas.web.rest.util.HeaderUtil;
import co.edu.uptc.vacunas.service.dto.AseguradoraDTO;
import co.edu.uptc.vacunas.service.mapper.AseguradoraMapper;
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
 * REST controller for managing Aseguradora.
 */
@RestController
@RequestMapping("/api")
public class AseguradoraResource {

    private final Logger log = LoggerFactory.getLogger(AseguradoraResource.class);

    private static final String ENTITY_NAME = "aseguradora";

    private final AseguradoraRepository aseguradoraRepository;

    private final AseguradoraMapper aseguradoraMapper;

    public AseguradoraResource(AseguradoraRepository aseguradoraRepository, AseguradoraMapper aseguradoraMapper) {
        this.aseguradoraRepository = aseguradoraRepository;
        this.aseguradoraMapper = aseguradoraMapper;
    }

    /**
     * POST  /aseguradoras : Create a new aseguradora.
     *
     * @param aseguradoraDTO the aseguradoraDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new aseguradoraDTO, or with status 400 (Bad Request) if the aseguradora has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/aseguradoras")
    @Timed
    public ResponseEntity<AseguradoraDTO> createAseguradora(@Valid @RequestBody AseguradoraDTO aseguradoraDTO) throws URISyntaxException {
        log.debug("REST request to save Aseguradora : {}", aseguradoraDTO);
        if (aseguradoraDTO.getId() != null) {
            throw new BadRequestAlertException("A new aseguradora cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Aseguradora aseguradora = aseguradoraMapper.toEntity(aseguradoraDTO);
        aseguradora = aseguradoraRepository.save(aseguradora);
        AseguradoraDTO result = aseguradoraMapper.toDto(aseguradora);
        return ResponseEntity.created(new URI("/api/aseguradoras/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /aseguradoras : Updates an existing aseguradora.
     *
     * @param aseguradoraDTO the aseguradoraDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated aseguradoraDTO,
     * or with status 400 (Bad Request) if the aseguradoraDTO is not valid,
     * or with status 500 (Internal Server Error) if the aseguradoraDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/aseguradoras")
    @Timed
    public ResponseEntity<AseguradoraDTO> updateAseguradora(@Valid @RequestBody AseguradoraDTO aseguradoraDTO) throws URISyntaxException {
        log.debug("REST request to update Aseguradora : {}", aseguradoraDTO);
        if (aseguradoraDTO.getId() == null) {
            return createAseguradora(aseguradoraDTO);
        }
        Aseguradora aseguradora = aseguradoraMapper.toEntity(aseguradoraDTO);
        aseguradora = aseguradoraRepository.save(aseguradora);
        AseguradoraDTO result = aseguradoraMapper.toDto(aseguradora);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, aseguradoraDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /aseguradoras : get all the aseguradoras.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of aseguradoras in body
     */
    @GetMapping("/aseguradoras")
    @Timed
    public List<AseguradoraDTO> getAllAseguradoras() {
        log.debug("REST request to get all Aseguradoras");
        List<Aseguradora> aseguradoras = aseguradoraRepository.findAllWithEagerRelationships();
        return aseguradoraMapper.toDto(aseguradoras);
        }

    /**
     * GET  /aseguradoras/:id : get the "id" aseguradora.
     *
     * @param id the id of the aseguradoraDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the aseguradoraDTO, or with status 404 (Not Found)
     */
    @GetMapping("/aseguradoras/{id}")
    @Timed
    public ResponseEntity<AseguradoraDTO> getAseguradora(@PathVariable Long id) {
        log.debug("REST request to get Aseguradora : {}", id);
        Aseguradora aseguradora = aseguradoraRepository.findOneWithEagerRelationships(id);
        AseguradoraDTO aseguradoraDTO = aseguradoraMapper.toDto(aseguradora);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(aseguradoraDTO));
    }

    /**
     * DELETE  /aseguradoras/:id : delete the "id" aseguradora.
     *
     * @param id the id of the aseguradoraDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/aseguradoras/{id}")
    @Timed
    public ResponseEntity<Void> deleteAseguradora(@PathVariable Long id) {
        log.debug("REST request to delete Aseguradora : {}", id);
        aseguradoraRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
