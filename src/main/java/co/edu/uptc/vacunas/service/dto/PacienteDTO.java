package co.edu.uptc.vacunas.service.dto;


import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Paciente entity.
 */
public class PacienteDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 8, max = 10)
    @Pattern(regexp = "^[1-9][0-9]*$")
    private String documento;

    @NotNull
    private String nombre;

    @NotNull
    private String apellido;

    @Size(min = 10, max = 10)
    @Pattern(regexp = "^[0-9]*$")
    private String telefono;

    private String correo;

    @NotNull
    private LocalDate fechaNacimiento;

    @NotNull
    private Boolean desplazado;

    @NotNull
    private Boolean victimaConflicto;

    @NotNull
    private Boolean discapacidad;

    @NotNull
    private Boolean gestante;

    private Long tipoDocumentoId;

    private String tipoDocumentoNombre;

    private Long generoId;

    private String generoNombre;

    private Long aseguradoraId;

    private String aseguradoraNombre;

    private Long grupoEtnicoId;

    private String grupoEtnicoNombre;

    private Long regimenId;

    private String regimenNombre;

    private Long municipioId;

    private String municipioNombre;

    private Long tipoResidenciaId;

    private String tipoResidenciaNombre;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Boolean isDesplazado() {
        return desplazado;
    }

    public void setDesplazado(Boolean desplazado) {
        this.desplazado = desplazado;
    }

    public Boolean isVictimaConflicto() {
        return victimaConflicto;
    }

    public void setVictimaConflicto(Boolean victimaConflicto) {
        this.victimaConflicto = victimaConflicto;
    }

    public Boolean isDiscapacidad() {
        return discapacidad;
    }

    public void setDiscapacidad(Boolean discapacidad) {
        this.discapacidad = discapacidad;
    }

    public Boolean isGestante() {
        return gestante;
    }

    public void setGestante(Boolean gestante) {
        this.gestante = gestante;
    }

    public Long getTipoDocumentoId() {
        return tipoDocumentoId;
    }

    public void setTipoDocumentoId(Long tipoDocumentoId) {
        this.tipoDocumentoId = tipoDocumentoId;
    }

    public String getTipoDocumentoNombre() {
        return tipoDocumentoNombre;
    }

    public void setTipoDocumentoNombre(String tipoDocumentoNombre) {
        this.tipoDocumentoNombre = tipoDocumentoNombre;
    }

    public Long getGeneroId() {
        return generoId;
    }

    public void setGeneroId(Long generoId) {
        this.generoId = generoId;
    }

    public String getGeneroNombre() {
        return generoNombre;
    }

    public void setGeneroNombre(String generoNombre) {
        this.generoNombre = generoNombre;
    }

    public Long getAseguradoraId() {
        return aseguradoraId;
    }

    public void setAseguradoraId(Long aseguradoraId) {
        this.aseguradoraId = aseguradoraId;
    }

    public String getAseguradoraNombre() {
        return aseguradoraNombre;
    }

    public void setAseguradoraNombre(String aseguradoraNombre) {
        this.aseguradoraNombre = aseguradoraNombre;
    }

    public Long getGrupoEtnicoId() {
        return grupoEtnicoId;
    }

    public void setGrupoEtnicoId(Long grupoEtnicoId) {
        this.grupoEtnicoId = grupoEtnicoId;
    }

    public String getGrupoEtnicoNombre() {
        return grupoEtnicoNombre;
    }

    public void setGrupoEtnicoNombre(String grupoEtnicoNombre) {
        this.grupoEtnicoNombre = grupoEtnicoNombre;
    }

    public Long getRegimenId() {
        return regimenId;
    }

    public void setRegimenId(Long regimenId) {
        this.regimenId = regimenId;
    }

    public String getRegimenNombre() {
        return regimenNombre;
    }

    public void setRegimenNombre(String regimenNombre) {
        this.regimenNombre = regimenNombre;
    }

    public Long getMunicipioId() {
        return municipioId;
    }

    public void setMunicipioId(Long municipioId) {
        this.municipioId = municipioId;
    }

    public String getMunicipioNombre() {
        return municipioNombre;
    }

    public void setMunicipioNombre(String municipioNombre) {
        this.municipioNombre = municipioNombre;
    }

    public Long getTipoResidenciaId() {
        return tipoResidenciaId;
    }

    public void setTipoResidenciaId(Long tipoResidenciaId) {
        this.tipoResidenciaId = tipoResidenciaId;
    }

    public String getTipoResidenciaNombre() {
        return tipoResidenciaNombre;
    }

    public void setTipoResidenciaNombre(String tipoResidenciaNombre) {
        this.tipoResidenciaNombre = tipoResidenciaNombre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PacienteDTO pacienteDTO = (PacienteDTO) o;
        if(pacienteDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pacienteDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PacienteDTO{" +
            "id=" + getId() +
            ", documento='" + getDocumento() + "'" +
            ", nombre='" + getNombre() + "'" +
            ", apellido='" + getApellido() + "'" +
            ", telefono='" + getTelefono() + "'" +
            ", correo='" + getCorreo() + "'" +
            ", fechaNacimiento='" + getFechaNacimiento() + "'" +
            ", desplazado='" + isDesplazado() + "'" +
            ", victimaConflicto='" + isVictimaConflicto() + "'" +
            ", discapacidad='" + isDiscapacidad() + "'" +
            ", gestante='" + isGestante() + "'" +
            "}";
    }
}
