package co.edu.uptc.vacunas.service.mapper;

import co.edu.uptc.vacunas.domain.*;
import co.edu.uptc.vacunas.service.dto.RegimenDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Regimen and its DTO RegimenDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RegimenMapper extends EntityMapper<RegimenDTO, Regimen> {


    @Mapping(target = "pacientes", ignore = true)
    @Mapping(target = "aseguradoras", ignore = true)
    Regimen toEntity(RegimenDTO regimenDTO);

    default Regimen fromId(Long id) {
        if (id == null) {
            return null;
        }
        Regimen regimen = new Regimen();
        regimen.setId(id);
        return regimen;
    }
}
