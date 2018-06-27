package co.edu.uptc.vacunas.service.mapper;

import co.edu.uptc.vacunas.domain.*;
import co.edu.uptc.vacunas.service.dto.PaisDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Pais and its DTO PaisDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PaisMapper extends EntityMapper<PaisDTO, Pais> {


    @Mapping(target = "departamentos", ignore = true)
    Pais toEntity(PaisDTO paisDTO);

    default Pais fromId(Long id) {
        if (id == null) {
            return null;
        }
        Pais pais = new Pais();
        pais.setId(id);
        return pais;
    }
}
