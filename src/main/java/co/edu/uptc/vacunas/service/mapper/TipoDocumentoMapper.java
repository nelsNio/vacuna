package co.edu.uptc.vacunas.service.mapper;

import co.edu.uptc.vacunas.domain.*;
import co.edu.uptc.vacunas.service.dto.TipoDocumentoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity TipoDocumento and its DTO TipoDocumentoDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TipoDocumentoMapper extends EntityMapper<TipoDocumentoDTO, TipoDocumento> {


    @Mapping(target = "pacientes", ignore = true)
    @Mapping(target = "vacunadors", ignore = true)
    @Mapping(target = "acudientes", ignore = true)
    TipoDocumento toEntity(TipoDocumentoDTO tipoDocumentoDTO);

    default TipoDocumento fromId(Long id) {
        if (id == null) {
            return null;
        }
        TipoDocumento tipoDocumento = new TipoDocumento();
        tipoDocumento.setId(id);
        return tipoDocumento;
    }
}
