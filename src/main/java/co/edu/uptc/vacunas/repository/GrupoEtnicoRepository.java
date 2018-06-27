package co.edu.uptc.vacunas.repository;

import co.edu.uptc.vacunas.domain.GrupoEtnico;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the GrupoEtnico entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GrupoEtnicoRepository extends JpaRepository<GrupoEtnico, Long> {

}
