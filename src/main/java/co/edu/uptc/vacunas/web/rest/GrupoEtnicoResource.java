package co.edu.uptc.vacunas.web.rest;

import com.codahale.metrics.annotation.Timed;
import co.edu.uptc.vacunas.domain.GrupoEtnico;

import co.edu.uptc.vacunas.repository.GrupoEtnicoRepository;
import co.edu.uptc.vacunas.web.rest.errors.BadRequestAlertException;
import co.edu.uptc.vacunas.web.rest.util.HeaderUtil;
import co.edu.uptc.vacunas.service.dto.GrupoEtnicoDTO;
import co.edu.uptc.vacunas.service.mapper.GrupoEtnicoMapper;
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
 * REST controller for managing GrupoEtnico.
 */
@RestController
@RequestMapping("/api")
public class GrupoEtnicoResource {

    private final Logger log = LoggerFactory.getLogger(GrupoEtnicoResource.class);

    private static final String ENTITY_NAME = "grupoEtnico";

    private final GrupoEtnicoRepository grupoEtnicoRepository;

    private final GrupoEtnicoMapper grupoEtnicoMapper;

    public GrupoEtnicoResource(GrupoEtnicoRepository grupoEtnicoRepository, GrupoEtnicoMapper grupoEtnicoMapper) {
        this.grupoEtnicoRepository = grupoEtnicoRepository;
        this.grupoEtnicoMapper = grupoEtnicoMapper;
    }

    /**
     * POST  /grupo-etnicos : Create a new grupoEtnico.
     *
     * @param grupoEtnicoDTO the grupoEtnicoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new grupoEtnicoDTO, or with status 400 (Bad Request) if the grupoEtnico has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/grupo-etnicos")
    @Timed
    public ResponseEntity<GrupoEtnicoDTO> createGrupoEtnico(@Valid @RequestBody GrupoEtnicoDTO grupoEtnicoDTO) throws URISyntaxException {
        log.debug("REST request to save GrupoEtnico : {}", grupoEtnicoDTO);
        if (grupoEtnicoDTO.getId() != null) {
            throw new BadRequestAlertException("A new grupoEtnico cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GrupoEtnico grupoEtnico = grupoEtnicoMapper.toEntity(grupoEtnicoDTO);
        grupoEtnico = grupoEtnicoRepository.save(grupoEtnico);
        GrupoEtnicoDTO result = grupoEtnicoMapper.toDto(grupoEtnico);
        return ResponseEntity.created(new URI("/api/grupo-etnicos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /grupo-etnicos : Updates an existing grupoEtnico.
     *
     * @param grupoEtnicoDTO the grupoEtnicoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated grupoEtnicoDTO,
     * or with status 400 (Bad Request) if the grupoEtnicoDTO is not valid,
     * or with status 500 (Internal Server Error) if the grupoEtnicoDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/grupo-etnicos")
    @Timed
    public ResponseEntity<GrupoEtnicoDTO> updateGrupoEtnico(@Valid @RequestBody GrupoEtnicoDTO grupoEtnicoDTO) throws URISyntaxException {
        log.debug("REST request to update GrupoEtnico : {}", grupoEtnicoDTO);
        if (grupoEtnicoDTO.getId() == null) {
            return createGrupoEtnico(grupoEtnicoDTO);
        }
        GrupoEtnico grupoEtnico = grupoEtnicoMapper.toEntity(grupoEtnicoDTO);
        grupoEtnico = grupoEtnicoRepository.save(grupoEtnico);
        GrupoEtnicoDTO result = grupoEtnicoMapper.toDto(grupoEtnico);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, grupoEtnicoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /grupo-etnicos : get all the grupoEtnicos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of grupoEtnicos in body
     */
    @GetMapping("/grupo-etnicos")
    @Timed
    public List<GrupoEtnicoDTO> getAllGrupoEtnicos() {
        log.debug("REST request to get all GrupoEtnicos");
        List<GrupoEtnico> grupoEtnicos = grupoEtnicoRepository.findAll();
        return grupoEtnicoMapper.toDto(grupoEtnicos);
        }

    /**
     * GET  /grupo-etnicos/:id : get the "id" grupoEtnico.
     *
     * @param id the id of the grupoEtnicoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the grupoEtnicoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/grupo-etnicos/{id}")
    @Timed
    public ResponseEntity<GrupoEtnicoDTO> getGrupoEtnico(@PathVariable Long id) {
        log.debug("REST request to get GrupoEtnico : {}", id);
        GrupoEtnico grupoEtnico = grupoEtnicoRepository.findOne(id);
        GrupoEtnicoDTO grupoEtnicoDTO = grupoEtnicoMapper.toDto(grupoEtnico);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(grupoEtnicoDTO));
    }

    /**
     * DELETE  /grupo-etnicos/:id : delete the "id" grupoEtnico.
     *
     * @param id the id of the grupoEtnicoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/grupo-etnicos/{id}")
    @Timed
    public ResponseEntity<Void> deleteGrupoEtnico(@PathVariable Long id) {
        log.debug("REST request to delete GrupoEtnico : {}", id);
        grupoEtnicoRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
