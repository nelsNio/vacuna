package co.edu.uptc.vacunas.service.dto;


import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the AplicacionVacuna entity.
 */
public class AplicacionVacunaDTO implements Serializable {

    private Long id;

    @NotNull
    private LocalDate fecha;

    private Long vacunadorId;

    private String vacunadorNombre;

    private Long pacienteId;

    private String pacienteNombre;

    private Long dosisId;

    private String dosisNombre;

    private Long ipsId;

    private String ipsNombre;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Long getVacunadorId() {
        return vacunadorId;
    }

    public void setVacunadorId(Long vacunadorId) {
        this.vacunadorId = vacunadorId;
    }

    public String getVacunadorNombre() {
        return vacunadorNombre;
    }

    public void setVacunadorNombre(String vacunadorNombre) {
        this.vacunadorNombre = vacunadorNombre;
    }

    public Long getPacienteId() {
        return pacienteId;
    }

    public void setPacienteId(Long pacienteId) {
        this.pacienteId = pacienteId;
    }

    public String getPacienteNombre() {
        return pacienteNombre;
    }

    public void setPacienteNombre(String pacienteNombre) {
        this.pacienteNombre = pacienteNombre;
    }

    public Long getDosisId() {
        return dosisId;
    }

    public void setDosisId(Long dosisId) {
        this.dosisId = dosisId;
    }

    public String getDosisNombre() {
        return dosisNombre;
    }

    public void setDosisNombre(String dosisNombre) {
        this.dosisNombre = dosisNombre;
    }

    public Long getIpsId() {
        return ipsId;
    }

    public void setIpsId(Long ipsId) {
        this.ipsId = ipsId;
    }

    public String getIpsNombre() {
        return ipsNombre;
    }

    public void setIpsNombre(String ipsNombre) {
        this.ipsNombre = ipsNombre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AplicacionVacunaDTO aplicacionVacunaDTO = (AplicacionVacunaDTO) o;
        if(aplicacionVacunaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), aplicacionVacunaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AplicacionVacunaDTO{" +
            "id=" + getId() +
            ", fecha='" + getFecha() + "'" +
            "}";
    }
}
