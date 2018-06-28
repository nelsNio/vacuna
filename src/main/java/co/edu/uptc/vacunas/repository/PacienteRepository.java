package co.edu.uptc.vacunas.repository;

import co.edu.uptc.vacunas.domain.Paciente;
import java.util.List;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Paciente entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long> {

   @Query(value="select p.documento, p.nombre, p.apellido, d.nombre as dosis from paciente p " +
"left join aplicacion_vacuna a on a.paciente_id = p.id " +
"left join dosis d on d.id= a.dosis_id",nativeQuery = true)
    public List<Object[]> findAllApplies();

    
}
