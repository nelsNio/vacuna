package co.edu.uptc.vacunas.service.mapper;

import co.edu.uptc.vacunas.domain.*;
import co.edu.uptc.vacunas.service.dto.BiologicoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Biologico and its DTO BiologicoDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface BiologicoMapper extends EntityMapper<BiologicoDTO, Biologico> {


    @Mapping(target = "doses", ignore = true)
    Biologico toEntity(BiologicoDTO biologicoDTO);

    default Biologico fromId(Long id) {
        if (id == null) {
            return null;
        }
        Biologico biologico = new Biologico();
        biologico.setId(id);
        return biologico;
    }
}
