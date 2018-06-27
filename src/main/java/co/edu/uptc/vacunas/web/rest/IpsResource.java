package co.edu.uptc.vacunas.web.rest;

import com.codahale.metrics.annotation.Timed;
import co.edu.uptc.vacunas.domain.Ips;

import co.edu.uptc.vacunas.repository.IpsRepository;
import co.edu.uptc.vacunas.web.rest.errors.BadRequestAlertException;
import co.edu.uptc.vacunas.web.rest.util.HeaderUtil;
import co.edu.uptc.vacunas.service.dto.IpsDTO;
import co.edu.uptc.vacunas.service.mapper.IpsMapper;
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
 * REST controller for managing Ips.
 */
@RestController
@RequestMapping("/api")
public class IpsResource {

    private final Logger log = LoggerFactory.getLogger(IpsResource.class);

    private static final String ENTITY_NAME = "ips";

    private final IpsRepository ipsRepository;

    private final IpsMapper ipsMapper;

    public IpsResource(IpsRepository ipsRepository, IpsMapper ipsMapper) {
        this.ipsRepository = ipsRepository;
        this.ipsMapper = ipsMapper;
    }

    /**
     * POST  /ips : Create a new ips.
     *
     * @param ipsDTO the ipsDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new ipsDTO, or with status 400 (Bad Request) if the ips has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/ips")
    @Timed
    public ResponseEntity<IpsDTO> createIps(@Valid @RequestBody IpsDTO ipsDTO) throws URISyntaxException {
        log.debug("REST request to save Ips : {}", ipsDTO);
        if (ipsDTO.getId() != null) {
            throw new BadRequestAlertException("A new ips cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Ips ips = ipsMapper.toEntity(ipsDTO);
        ips = ipsRepository.save(ips);
        IpsDTO result = ipsMapper.toDto(ips);
        return ResponseEntity.created(new URI("/api/ips/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /ips : Updates an existing ips.
     *
     * @param ipsDTO the ipsDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated ipsDTO,
     * or with status 400 (Bad Request) if the ipsDTO is not valid,
     * or with status 500 (Internal Server Error) if the ipsDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/ips")
    @Timed
    public ResponseEntity<IpsDTO> updateIps(@Valid @RequestBody IpsDTO ipsDTO) throws URISyntaxException {
        log.debug("REST request to update Ips : {}", ipsDTO);
        if (ipsDTO.getId() == null) {
            return createIps(ipsDTO);
        }
        Ips ips = ipsMapper.toEntity(ipsDTO);
        ips = ipsRepository.save(ips);
        IpsDTO result = ipsMapper.toDto(ips);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, ipsDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /ips : get all the ips.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of ips in body
     */
    @GetMapping("/ips")
    @Timed
    public List<IpsDTO> getAllIps() {
        log.debug("REST request to get all Ips");
        List<Ips> ips = ipsRepository.findAllWithEagerRelationships();
        return ipsMapper.toDto(ips);
        }

    /**
     * GET  /ips/:id : get the "id" ips.
     *
     * @param id the id of the ipsDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the ipsDTO, or with status 404 (Not Found)
     */
    @GetMapping("/ips/{id}")
    @Timed
    public ResponseEntity<IpsDTO> getIps(@PathVariable Long id) {
        log.debug("REST request to get Ips : {}", id);
        Ips ips = ipsRepository.findOneWithEagerRelationships(id);
        IpsDTO ipsDTO = ipsMapper.toDto(ips);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(ipsDTO));
    }

    /**
     * DELETE  /ips/:id : delete the "id" ips.
     *
     * @param id the id of the ipsDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/ips/{id}")
    @Timed
    public ResponseEntity<Void> deleteIps(@PathVariable Long id) {
        log.debug("REST request to delete Ips : {}", id);
        ipsRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
