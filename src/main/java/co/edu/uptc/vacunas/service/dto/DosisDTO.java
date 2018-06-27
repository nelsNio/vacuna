package co.edu.uptc.vacunas.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Dosis entity.
 */
public class DosisDTO implements Serializable {

    private Long id;

    @NotNull
    private String nombre;

    @NotNull
    private Integer edadMinima;

    @NotNull
    private Integer edadMaxima;

    @NotNull
    private Boolean embarazo;

    private Long biologicoId;

    private String biologicoNombre;

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

    public Integer getEdadMinima() {
        return edadMinima;
    }

    public void setEdadMinima(Integer edadMinima) {
        this.edadMinima = edadMinima;
    }

    public Integer getEdadMaxima() {
        return edadMaxima;
    }

    public void setEdadMaxima(Integer edadMaxima) {
        this.edadMaxima = edadMaxima;
    }

    public Boolean isEmbarazo() {
        return embarazo;
    }

    public void setEmbarazo(Boolean embarazo) {
        this.embarazo = embarazo;
    }

    public Long getBiologicoId() {
        return biologicoId;
    }

    public void setBiologicoId(Long biologicoId) {
        this.biologicoId = biologicoId;
    }

    public String getBiologicoNombre() {
        return biologicoNombre;
    }

    public void setBiologicoNombre(String biologicoNombre) {
        this.biologicoNombre = biologicoNombre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DosisDTO dosisDTO = (DosisDTO) o;
        if(dosisDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), dosisDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DosisDTO{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", edadMinima=" + getEdadMinima() +
            ", edadMaxima=" + getEdadMaxima() +
            ", embarazo='" + isEmbarazo() + "'" +
            "}";
    }
}
