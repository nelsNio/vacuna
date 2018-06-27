package co.edu.uptc.vacunas.service.mapper;

import co.edu.uptc.vacunas.domain.*;
import co.edu.uptc.vacunas.service.dto.DosisDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Dosis and its DTO DosisDTO.
 */
@Mapper(componentModel = "spring", uses = {BiologicoMapper.class})
public interface DosisMapper extends EntityMapper<DosisDTO, Dosis> {

    @Mapping(source = "biologico.id", target = "biologicoId")
    @Mapping(source = "biologico.nombre", target = "biologicoNombre")
    DosisDTO toDto(Dosis dosis);

    @Mapping(target = "aplicacions", ignore = true)
    @Mapping(source = "biologicoId", target = "biologico")
    Dosis toEntity(DosisDTO dosisDTO);

    default Dosis fromId(Long id) {
        if (id == null) {
            return null;
        }
        Dosis dosis = new Dosis();
        dosis.setId(id);
        return dosis;
    }
}
