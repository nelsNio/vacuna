package co.edu.uptc.vacunas.service.mapper;

import co.edu.uptc.vacunas.domain.*;
import co.edu.uptc.vacunas.service.dto.MunicipioDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Municipio and its DTO MunicipioDTO.
 */
@Mapper(componentModel = "spring", uses = {DepartamentoMapper.class})
public interface MunicipioMapper extends EntityMapper<MunicipioDTO, Municipio> {

    @Mapping(source = "departamento.id", target = "departamentoId")
    @Mapping(source = "departamento.nombre", target = "departamentoNombre")
    MunicipioDTO toDto(Municipio municipio);

    @Mapping(target = "pacientes", ignore = true)
    @Mapping(source = "departamentoId", target = "departamento")
    @Mapping(target = "ips", ignore = true)
    Municipio toEntity(MunicipioDTO municipioDTO);

    default Municipio fromId(Long id) {
        if (id == null) {
            return null;
        }
        Municipio municipio = new Municipio();
        municipio.setId(id);
        return municipio;
    }
}
