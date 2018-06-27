package co.edu.uptc.vacunas.service.mapper;

import co.edu.uptc.vacunas.domain.*;
import co.edu.uptc.vacunas.service.dto.GrupoEtnicoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity GrupoEtnico and its DTO GrupoEtnicoDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface GrupoEtnicoMapper extends EntityMapper<GrupoEtnicoDTO, GrupoEtnico> {


    @Mapping(target = "pacientes", ignore = true)
    GrupoEtnico toEntity(GrupoEtnicoDTO grupoEtnicoDTO);

    default GrupoEtnico fromId(Long id) {
        if (id == null) {
            return null;
        }
        GrupoEtnico grupoEtnico = new GrupoEtnico();
        grupoEtnico.setId(id);
        return grupoEtnico;
    }
}
