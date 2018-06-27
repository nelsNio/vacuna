package co.edu.uptc.vacunas.repository;

import co.edu.uptc.vacunas.domain.Aseguradora;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the Aseguradora entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AseguradoraRepository extends JpaRepository<Aseguradora, Long> {
    @Query("select distinct aseguradora from Aseguradora aseguradora left join fetch aseguradora.regimen")
    List<Aseguradora> findAllWithEagerRelationships();

    @Query("select aseguradora from Aseguradora aseguradora left join fetch aseguradora.regimen where aseguradora.id =:id")
    Aseguradora findOneWithEagerRelationships(@Param("id") Long id);

}
