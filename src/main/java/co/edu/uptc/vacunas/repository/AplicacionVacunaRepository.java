package co.edu.uptc.vacunas.repository;

import co.edu.uptc.vacunas.domain.AplicacionVacuna;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the AplicacionVacuna entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AplicacionVacunaRepository extends JpaRepository<AplicacionVacuna, Long> {

}
