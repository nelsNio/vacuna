package co.edu.uptc.vacunas.service.mapper;

import co.edu.uptc.vacunas.domain.*;
import co.edu.uptc.vacunas.service.dto.AcudienteDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Acudiente and its DTO AcudienteDTO.
 */
@Mapper(componentModel = "spring", uses = {TipoDocumentoMapper.class, GeneroMapper.class, PacienteMapper.class})
public interface AcudienteMapper extends EntityMapper<AcudienteDTO, Acudiente> {

    @Mapping(source = "tipoDocumento.id", target = "tipoDocumentoId")
    @Mapping(source = "tipoDocumento.nombre", target = "tipoDocumentoNombre")
    @Mapping(source = "genero.id", target = "generoId")
    @Mapping(source = "genero.nombre", target = "generoNombre")
    @Mapping(source = "paciente.id", target = "pacienteId")
    @Mapping(source = "paciente.nombre", target = "pacienteNombre")
    AcudienteDTO toDto(Acudiente acudiente);

    @Mapping(source = "tipoDocumentoId", target = "tipoDocumento")
    @Mapping(source = "generoId", target = "genero")
    @Mapping(source = "pacienteId", target = "paciente")
    Acudiente toEntity(AcudienteDTO acudienteDTO);

    default Acudiente fromId(Long id) {
        if (id == null) {
            return null;
        }
        Acudiente acudiente = new Acudiente();
        acudiente.setId(id);
        return acudiente;
    }
}
