package co.edu.uptc.vacunas.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Biologico entity.
 */
public class BiologicoDTO implements Serializable {

    private Long id;

    @NotNull
    private String nombre;

    @NotNull
    private String lote;

    @NotNull
    private String loteJeringa;

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

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public String getLoteJeringa() {
        return loteJeringa;
    }

    public void setLoteJeringa(String loteJeringa) {
        this.loteJeringa = loteJeringa;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BiologicoDTO biologicoDTO = (BiologicoDTO) o;
        if(biologicoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), biologicoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BiologicoDTO{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", lote='" + getLote() + "'" +
            ", loteJeringa='" + getLoteJeringa() + "'" +
            "}";
    }
}
