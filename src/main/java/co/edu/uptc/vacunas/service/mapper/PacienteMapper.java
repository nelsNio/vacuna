package co.edu.uptc.vacunas.service.mapper;

import co.edu.uptc.vacunas.domain.*;
import co.edu.uptc.vacunas.service.dto.PacienteDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Paciente and its DTO PacienteDTO.
 */
@Mapper(componentModel = "spring", uses = {TipoDocumentoMapper.class, GeneroMapper.class, AseguradoraMapper.class, GrupoEtnicoMapper.class, RegimenMapper.class, MunicipioMapper.class, TipoResidenciaMapper.class})
public interface PacienteMapper extends EntityMapper<PacienteDTO, Paciente> {

    @Mapping(source = "tipoDocumento.id", target = "tipoDocumentoId")
    @Mapping(source = "tipoDocumento.nombre", target = "tipoDocumentoNombre")
    @Mapping(source = "genero.id", target = "generoId")
    @Mapping(source = "genero.nombre", target = "generoNombre")
    @Mapping(source = "aseguradora.id", target = "aseguradoraId")
    @Mapping(source = "aseguradora.nombre", target = "aseguradoraNombre")
    @Mapping(source = "grupoEtnico.id", target = "grupoEtnicoId")
    @Mapping(source = "grupoEtnico.nombre", target = "grupoEtnicoNombre")
    @Mapping(source = "regimen.id", target = "regimenId")
    @Mapping(source = "regimen.nombre", target = "regimenNombre")
    @Mapping(source = "municipio.id", target = "municipioId")
    @Mapping(source = "municipio.nombre", target = "municipioNombre")
    @Mapping(source = "tipoResidencia.id", target = "tipoResidenciaId")
    @Mapping(source = "tipoResidencia.nombre", target = "tipoResidenciaNombre")
    PacienteDTO toDto(Paciente paciente);

    @Mapping(target = "aplicacions", ignore = true)
    @Mapping(target = "acudientes", ignore = true)
    @Mapping(source = "tipoDocumentoId", target = "tipoDocumento")
    @Mapping(source = "generoId", target = "genero")
    @Mapping(source = "aseguradoraId", target = "aseguradora")
    @Mapping(source = "grupoEtnicoId", target = "grupoEtnico")
    @Mapping(source = "regimenId", target = "regimen")
    @Mapping(source = "municipioId", target = "municipio")
    @Mapping(source = "tipoResidenciaId", target = "tipoResidencia")
    Paciente toEntity(PacienteDTO pacienteDTO);

    default Paciente fromId(Long id) {
        if (id == null) {
            return null;
        }
        Paciente paciente = new Paciente();
        paciente.setId(id);
        return paciente;
    }
}
