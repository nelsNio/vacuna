package co.edu.uptc.vacunas.web.rest;

import com.codahale.metrics.annotation.Timed;
import co.edu.uptc.vacunas.domain.Biologico;

import co.edu.uptc.vacunas.repository.BiologicoRepository;
import co.edu.uptc.vacunas.web.rest.errors.BadRequestAlertException;
import co.edu.uptc.vacunas.web.rest.util.HeaderUtil;
import co.edu.uptc.vacunas.service.dto.BiologicoDTO;
import co.edu.uptc.vacunas.service.mapper.BiologicoMapper;
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
 * REST controller for managing Biologico.
 */
@RestController
@RequestMapping("/api")
public class BiologicoResource {

    private final Logger log = LoggerFactory.getLogger(BiologicoResource.class);

    private static final String ENTITY_NAME = "biologico";

    private final BiologicoRepository biologicoRepository;

    private final BiologicoMapper biologicoMapper;

    public BiologicoResource(BiologicoRepository biologicoRepository, BiologicoMapper biologicoMapper) {
        this.biologicoRepository = biologicoRepository;
        this.biologicoMapper = biologicoMapper;
    }

    /**
     * POST  /biologicos : Create a new biologico.
     *
     * @param biologicoDTO the biologicoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new biologicoDTO, or with status 400 (Bad Request) if the biologico has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/biologicos")
    @Timed
    public ResponseEntity<BiologicoDTO> createBiologico(@Valid @RequestBody BiologicoDTO biologicoDTO) throws URISyntaxException {
        log.debug("REST request to save Biologico : {}", biologicoDTO);
        if (biologicoDTO.getId() != null) {
            throw new BadRequestAlertException("A new biologico cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Biologico biologico = biologicoMapper.toEntity(biologicoDTO);
        biologico = biologicoRepository.save(biologico);
        BiologicoDTO result = biologicoMapper.toDto(biologico);
        return ResponseEntity.created(new URI("/api/biologicos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /biologicos : Updates an existing biologico.
     *
     * @param biologicoDTO the biologicoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated biologicoDTO,
     * or with status 400 (Bad Request) if the biologicoDTO is not valid,
     * or with status 500 (Internal Server Error) if the biologicoDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/biologicos")
    @Timed
    public ResponseEntity<BiologicoDTO> updateBiologico(@Valid @RequestBody BiologicoDTO biologicoDTO) throws URISyntaxException {
        log.debug("REST request to update Biologico : {}", biologicoDTO);
        if (biologicoDTO.getId() == null) {
            return createBiologico(biologicoDTO);
        }
        Biologico biologico = biologicoMapper.toEntity(biologicoDTO);
        biologico = biologicoRepository.save(biologico);
        BiologicoDTO result = biologicoMapper.toDto(biologico);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, biologicoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /biologicos : get all the biologicos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of biologicos in body
     */
    @GetMapping("/biologicos")
    @Timed
    public List<BiologicoDTO> getAllBiologicos() {
        log.debug("REST request to get all Biologicos");
        List<Biologico> biologicos = biologicoRepository.findAll();
        return biologicoMapper.toDto(biologicos);
        }

    /**
     * GET  /biologicos/:id : get the "id" biologico.
     *
     * @param id the id of the biologicoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the biologicoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/biologicos/{id}")
    @Timed
    public ResponseEntity<BiologicoDTO> getBiologico(@PathVariable Long id) {
        log.debug("REST request to get Biologico : {}", id);
        Biologico biologico = biologicoRepository.findOne(id);
        BiologicoDTO biologicoDTO = biologicoMapper.toDto(biologico);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(biologicoDTO));
    }

    /**
     * DELETE  /biologicos/:id : delete the "id" biologico.
     *
     * @param id the id of the biologicoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/biologicos/{id}")
    @Timed
    public ResponseEntity<Void> deleteBiologico(@PathVariable Long id) {
        log.debug("REST request to delete Biologico : {}", id);
        biologicoRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
