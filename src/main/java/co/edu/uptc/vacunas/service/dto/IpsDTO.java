package co.edu.uptc.vacunas.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Ips entity.
 */
public class IpsDTO implements Serializable {

    private Long id;

    @NotNull
    private String nombre;

    @NotNull
    private String direccion;

    private Set<MunicipioDTO> municipios = new HashSet<>();

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

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Set<MunicipioDTO> getMunicipios() {
        return municipios;
    }

    public void setMunicipios(Set<MunicipioDTO> municipios) {
        this.municipios = municipios;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        IpsDTO ipsDTO = (IpsDTO) o;
        if(ipsDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), ipsDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "IpsDTO{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", direccion='" + getDireccion() + "'" +
            "}";
    }
}
