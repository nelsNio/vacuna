package co.edu.uptc.vacunas.repository;

import co.edu.uptc.vacunas.domain.Regimen;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Regimen entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RegimenRepository extends JpaRepository<Regimen, Long> {

}
