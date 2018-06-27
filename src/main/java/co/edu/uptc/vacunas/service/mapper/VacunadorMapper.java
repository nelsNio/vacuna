package co.edu.uptc.vacunas.service.mapper;

import co.edu.uptc.vacunas.domain.*;
import co.edu.uptc.vacunas.service.dto.VacunadorDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Vacunador and its DTO VacunadorDTO.
 */
@Mapper(componentModel = "spring", uses = {TipoDocumentoMapper.class, GeneroMapper.class, IpsMapper.class})
public interface VacunadorMapper extends EntityMapper<VacunadorDTO, Vacunador> {

    @Mapping(source = "tipoDocumento.id", target = "tipoDocumentoId")
    @Mapping(source = "tipoDocumento.nombre", target = "tipoDocumentoNombre")
    @Mapping(source = "genero.id", target = "generoId")
    @Mapping(source = "genero.nombre", target = "generoNombre")
    @Mapping(source = "ips.id", target = "ipsId")
    @Mapping(source = "ips.nombre", target = "ipsNombre")
    VacunadorDTO toDto(Vacunador vacunador);

    @Mapping(target = "aplicacions", ignore = true)
    @Mapping(source = "tipoDocumentoId", target = "tipoDocumento")
    @Mapping(source = "generoId", target = "genero")
    @Mapping(source = "ipsId", target = "ips")
    Vacunador toEntity(VacunadorDTO vacunadorDTO);

    default Vacunador fromId(Long id) {
        if (id == null) {
            return null;
        }
        Vacunador vacunador = new Vacunador();
        vacunador.setId(id);
        return vacunador;
    }
}
