package co.edu.uptc.vacunas.service.mapper;

import co.edu.uptc.vacunas.domain.*;
import co.edu.uptc.vacunas.service.dto.IpsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Ips and its DTO IpsDTO.
 */
@Mapper(componentModel = "spring", uses = {MunicipioMapper.class})
public interface IpsMapper extends EntityMapper<IpsDTO, Ips> {


    @Mapping(target = "aplicacions", ignore = true)
    @Mapping(target = "vacunadors", ignore = true)
    Ips toEntity(IpsDTO ipsDTO);

    default Ips fromId(Long id) {
        if (id == null) {
            return null;
        }
        Ips ips = new Ips();
        ips.setId(id);
        return ips;
    }
}
