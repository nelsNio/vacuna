package co.edu.uptc.vacunas.repository;

import co.edu.uptc.vacunas.domain.Vacunador;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Vacunador entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VacunadorRepository extends JpaRepository<Vacunador, Long> {

}
