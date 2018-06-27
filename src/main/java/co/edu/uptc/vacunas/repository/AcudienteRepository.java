package co.edu.uptc.vacunas.repository;

import co.edu.uptc.vacunas.domain.Acudiente;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Acudiente entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AcudienteRepository extends JpaRepository<Acudiente, Long> {

}
