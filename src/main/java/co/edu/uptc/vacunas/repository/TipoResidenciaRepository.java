package co.edu.uptc.vacunas.repository;

import co.edu.uptc.vacunas.domain.TipoResidencia;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the TipoResidencia entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TipoResidenciaRepository extends JpaRepository<TipoResidencia, Long> {

}
