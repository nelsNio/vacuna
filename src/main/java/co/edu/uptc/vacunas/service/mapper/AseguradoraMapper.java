package co.edu.uptc.vacunas.service.mapper;

import co.edu.uptc.vacunas.domain.*;
import co.edu.uptc.vacunas.service.dto.AseguradoraDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Aseguradora and its DTO AseguradoraDTO.
 */
@Mapper(componentModel = "spring", uses = {RegimenMapper.class})
public interface AseguradoraMapper extends EntityMapper<AseguradoraDTO, Aseguradora> {


    @Mapping(target = "pacientes", ignore = true)
    Aseguradora toEntity(AseguradoraDTO aseguradoraDTO);

    default Aseguradora fromId(Long id) {
        if (id == null) {
            return null;
        }
        Aseguradora aseguradora = new Aseguradora();
        aseguradora.setId(id);
        return aseguradora;
    }
}
