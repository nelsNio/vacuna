package co.edu.uptc.vacunas.repository;

import co.edu.uptc.vacunas.domain.Ips;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the Ips entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IpsRepository extends JpaRepository<Ips, Long> {
    @Query("select distinct ips from Ips ips left join fetch ips.municipios")
    List<Ips> findAllWithEagerRelationships();

    @Query("select ips from Ips ips left join fetch ips.municipios where ips.id =:id")
    Ips findOneWithEagerRelationships(@Param("id") Long id);

}
