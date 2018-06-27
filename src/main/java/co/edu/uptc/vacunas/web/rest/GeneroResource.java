package co.edu.uptc.vacunas.web.rest;

import com.codahale.metrics.annotation.Timed;
import co.edu.uptc.vacunas.domain.Genero;

import co.edu.uptc.vacunas.repository.GeneroRepository;
import co.edu.uptc.vacunas.web.rest.errors.BadRequestAlertException;
import co.edu.uptc.vacunas.web.rest.util.HeaderUtil;
import co.edu.uptc.vacunas.service.dto.GeneroDTO;
import co.edu.uptc.vacunas.service.mapper.GeneroMapper;
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
 * REST controller for managing Genero.
 */
@RestController
@RequestMapping("/api")
public class GeneroResource {

    private final Logger log = LoggerFactory.getLogger(GeneroResource.class);

    private static final String ENTITY_NAME = "genero";

    private final GeneroRepository generoRepository;

    private final GeneroMapper generoMapper;

    public GeneroResource(GeneroRepository generoRepository, GeneroMapper generoMapper) {
        this.generoRepository = generoRepository;
        this.generoMapper = generoMapper;
    }

    /**
     * POST  /generos : Create a new genero.
     *
     * @param generoDTO the generoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new generoDTO, or with status 400 (Bad Request) if the genero has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/generos")
    @Timed
    public ResponseEntity<GeneroDTO> createGenero(@Valid @RequestBody GeneroDTO generoDTO) throws URISyntaxException {
        log.debug("REST request to save Genero : {}", generoDTO);
        if (generoDTO.getId() != null) {
            throw new BadRequestAlertException("A new genero cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Genero genero = generoMapper.toEntity(generoDTO);
        genero = generoRepository.save(genero);
        GeneroDTO result = generoMapper.toDto(genero);
        return ResponseEntity.created(new URI("/api/generos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /generos : Updates an existing genero.
     *
     * @param generoDTO the generoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated generoDTO,
     * or with status 400 (Bad Request) if the generoDTO is not valid,
     * or with status 500 (Internal Server Error) if the generoDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/generos")
    @Timed
    public ResponseEntity<GeneroDTO> updateGenero(@Valid @RequestBody GeneroDTO generoDTO) throws URISyntaxException {
        log.debug("REST request to update Genero : {}", generoDTO);
        if (generoDTO.getId() == null) {
            return createGenero(generoDTO);
        }
        Genero genero = generoMapper.toEntity(generoDTO);
        genero = generoRepository.save(genero);
        GeneroDTO result = generoMapper.toDto(genero);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, generoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /generos : get all the generos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of generos in body
     */
    @GetMapping("/generos")
    @Timed
    public List<GeneroDTO> getAllGeneros() {
        log.debug("REST request to get all Generos");
        List<Genero> generos = generoRepository.findAll();
        return generoMapper.toDto(generos);
        }

    /**
     * GET  /generos/:id : get the "id" genero.
     *
     * @param id the id of the generoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the generoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/generos/{id}")
    @Timed
    public ResponseEntity<GeneroDTO> getGenero(@PathVariable Long id) {
        log.debug("REST request to get Genero : {}", id);
        Genero genero = generoRepository.findOne(id);
        GeneroDTO generoDTO = generoMapper.toDto(genero);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(generoDTO));
    }

    /**
     * DELETE  /generos/:id : delete the "id" genero.
     *
     * @param id the id of the generoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/generos/{id}")
    @Timed
    public ResponseEntity<Void> deleteGenero(@PathVariable Long id) {
        log.debug("REST request to delete Genero : {}", id);
        generoRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
