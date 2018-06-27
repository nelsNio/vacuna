package co.edu.uptc.vacunas.service.mapper;

import co.edu.uptc.vacunas.domain.*;
import co.edu.uptc.vacunas.service.dto.AplicacionVacunaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity AplicacionVacuna and its DTO AplicacionVacunaDTO.
 */
@Mapper(componentModel = "spring", uses = {VacunadorMapper.class, PacienteMapper.class, DosisMapper.class, IpsMapper.class})
public interface AplicacionVacunaMapper extends EntityMapper<AplicacionVacunaDTO, AplicacionVacuna> {

    @Mapping(source = "vacunador.id", target = "vacunadorId")
    @Mapping(source = "vacunador.nombre", target = "vacunadorNombre")
    @Mapping(source = "paciente.id", target = "pacienteId")
    @Mapping(source = "paciente.nombre", target = "pacienteNombre")
    @Mapping(source = "dosis.id", target = "dosisId")
    @Mapping(source = "dosis.nombre", target = "dosisNombre")
    @Mapping(source = "ips.id", target = "ipsId")
    @Mapping(source = "ips.nombre", target = "ipsNombre")
    AplicacionVacunaDTO toDto(AplicacionVacuna aplicacionVacuna);

    @Mapping(source = "vacunadorId", target = "vacunador")
    @Mapping(source = "pacienteId", target = "paciente")
    @Mapping(source = "dosisId", target = "dosis")
    @Mapping(source = "ipsId", target = "ips")
    AplicacionVacuna toEntity(AplicacionVacunaDTO aplicacionVacunaDTO);

    default AplicacionVacuna fromId(Long id) {
        if (id == null) {
            return null;
        }
        AplicacionVacuna aplicacionVacuna = new AplicacionVacuna();
        aplicacionVacuna.setId(id);
        return aplicacionVacuna;
    }
}
