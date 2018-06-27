package co.edu.uptc.vacunas.service.mapper;

import co.edu.uptc.vacunas.domain.*;
import co.edu.uptc.vacunas.service.dto.TipoResidenciaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity TipoResidencia and its DTO TipoResidenciaDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TipoResidenciaMapper extends EntityMapper<TipoResidenciaDTO, TipoResidencia> {


    @Mapping(target = "pacientes", ignore = true)
    TipoResidencia toEntity(TipoResidenciaDTO tipoResidenciaDTO);

    default TipoResidencia fromId(Long id) {
        if (id == null) {
            return null;
        }
        TipoResidencia tipoResidencia = new TipoResidencia();
        tipoResidencia.setId(id);
        return tipoResidencia;
    }
}
