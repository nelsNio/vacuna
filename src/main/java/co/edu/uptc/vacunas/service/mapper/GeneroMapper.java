package co.edu.uptc.vacunas.service.mapper;

import co.edu.uptc.vacunas.domain.*;
import co.edu.uptc.vacunas.service.dto.GeneroDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Genero and its DTO GeneroDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface GeneroMapper extends EntityMapper<GeneroDTO, Genero> {


    @Mapping(target = "pacientes", ignore = true)
    @Mapping(target = "vacunadors", ignore = true)
    @Mapping(target = "acudientes", ignore = true)
    Genero toEntity(GeneroDTO generoDTO);

    default Genero fromId(Long id) {
        if (id == null) {
            return null;
        }
        Genero genero = new Genero();
        genero.setId(id);
        return genero;
    }
}
