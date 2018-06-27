package co.edu.uptc.vacunas.repository;

import co.edu.uptc.vacunas.domain.Dosis;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Dosis entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DosisRepository extends JpaRepository<Dosis, Long> {

}
