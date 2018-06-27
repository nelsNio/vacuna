package co.edu.uptc.vacunas.service.mapper;

import co.edu.uptc.vacunas.domain.*;
import co.edu.uptc.vacunas.service.dto.DepartamentoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Departamento and its DTO DepartamentoDTO.
 */
@Mapper(componentModel = "spring", uses = {PaisMapper.class})
public interface DepartamentoMapper extends EntityMapper<DepartamentoDTO, Departamento> {

    @Mapping(source = "pais.id", target = "paisId")
    @Mapping(source = "pais.nombre", target = "paisNombre")
    DepartamentoDTO toDto(Departamento departamento);

    @Mapping(target = "municipios", ignore = true)
    @Mapping(source = "paisId", target = "pais")
    Departamento toEntity(DepartamentoDTO departamentoDTO);

    default Departamento fromId(Long id) {
        if (id == null) {
            return null;
        }
        Departamento departamento = new Departamento();
        departamento.setId(id);
        return departamento;
    }
}
