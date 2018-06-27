package co.edu.uptc.vacunas.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Aseguradora entity.
 */
public class AseguradoraDTO implements Serializable {

    private Long id;

    @NotNull
    private String nombre;

    private Set<RegimenDTO> regimen = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Set<RegimenDTO> getRegimen() {
        return regimen;
    }

    public void setRegimen(Set<RegimenDTO> regimen) {
        this.regimen = regimen;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AseguradoraDTO aseguradoraDTO = (AseguradoraDTO) o;
        if(aseguradoraDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), aseguradoraDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AseguradoraDTO{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            "}";
    }
}
