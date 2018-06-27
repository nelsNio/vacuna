package co.edu.uptc.vacunas.repository;

import co.edu.uptc.vacunas.domain.Biologico;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Biologico entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BiologicoRepository extends JpaRepository<Biologico, Long> {

}
