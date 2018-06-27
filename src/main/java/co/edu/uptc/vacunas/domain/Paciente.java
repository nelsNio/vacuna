package co.edu.uptc.vacunas.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Paciente.
 */
@Entity
@Table(name = "paciente")
public class Paciente implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 8, max = 10)
    @Pattern(regexp = "^[1-9][0-9]*$")
    @Column(name = "documento", length = 10, nullable = false)
    private String documento;

    @NotNull
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @NotNull
    @Column(name = "apellido", nullable = false)
    private String apellido;

    @Size(min = 10, max = 10)
    @Pattern(regexp = "^[0-9]*$")
    @Column(name = "telefono", length = 10)
    private String telefono;

    @Column(name = "correo")
    private String correo;

    @NotNull
    @Column(name = "fecha_nacimiento", nullable = false)
    private LocalDate fechaNacimiento;

    @NotNull
    @Column(name = "desplazado", nullable = false)
    private Boolean desplazado;

    @NotNull
    @Column(name = "victima_conflicto", nullable = false)
    private Boolean victimaConflicto;

    @NotNull
    @Column(name = "discapacidad", nullable = false)
    private Boolean discapacidad;

    @NotNull
    @Column(name = "gestante", nullable = false)
    private Boolean gestante;

    @OneToMany(mappedBy = "paciente")
    @JsonIgnore
    private Set<AplicacionVacuna> aplicacions = new HashSet<>();

    @OneToMany(mappedBy = "paciente")
    @JsonIgnore
    private Set<Acudiente> acudientes = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    private TipoDocumento tipoDocumento;

    @ManyToOne(optional = false)
    @NotNull
    private Genero genero;

    @ManyToOne
    private Aseguradora aseguradora;

    @ManyToOne
    private GrupoEtnico grupoEtnico;

    @ManyToOne(optional = false)
    @NotNull
    private Regimen regimen;

    @ManyToOne
    private Municipio municipio;

    @ManyToOne(optional = false)
    @NotNull
    private TipoResidencia tipoResidencia;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDocumento() {
        return documento;
    }

    public Paciente documento(String documento) {
        this.documento = documento;
        return this;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getNombre() {
        return nombre;
    }

    public Paciente nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public Paciente apellido(String apellido) {
        this.apellido = apellido;
        return this;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getTelefono() {
        return telefono;
    }

    public Paciente telefono(String telefono) {
        this.telefono = telefono;
        return this;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public Paciente correo(String correo) {
        this.correo = correo;
        return this;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public Paciente fechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
        return this;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Boolean isDesplazado() {
        return desplazado;
    }

    public Paciente desplazado(Boolean desplazado) {
        this.desplazado = desplazado;
        return this;
    }

    public void setDesplazado(Boolean desplazado) {
        this.desplazado = desplazado;
    }

    public Boolean isVictimaConflicto() {
        return victimaConflicto;
    }

    public Paciente victimaConflicto(Boolean victimaConflicto) {
        this.victimaConflicto = victimaConflicto;
        return this;
    }

    public void setVictimaConflicto(Boolean victimaConflicto) {
        this.victimaConflicto = victimaConflicto;
    }

    public Boolean isDiscapacidad() {
        return discapacidad;
    }

    public Paciente discapacidad(Boolean discapacidad) {
        this.discapacidad = discapacidad;
        return this;
    }

    public void setDiscapacidad(Boolean discapacidad) {
        this.discapacidad = discapacidad;
    }

    public Boolean isGestante() {
        return gestante;
    }

    public Paciente gestante(Boolean gestante) {
        this.gestante = gestante;
        return this;
    }

    public void setGestante(Boolean gestante) {
        this.gestante = gestante;
    }

    public Set<AplicacionVacuna> getAplicacions() {
        return aplicacions;
    }

    public Paciente aplicacions(Set<AplicacionVacuna> aplicacionVacunas) {
        this.aplicacions = aplicacionVacunas;
        return this;
    }

    public Paciente addAplicacion(AplicacionVacuna aplicacionVacuna) {
        this.aplicacions.add(aplicacionVacuna);
        aplicacionVacuna.setPaciente(this);
        return this;
    }

    public Paciente removeAplicacion(AplicacionVacuna aplicacionVacuna) {
        this.aplicacions.remove(aplicacionVacuna);
        aplicacionVacuna.setPaciente(null);
        return this;
    }

    public void setAplicacions(Set<AplicacionVacuna> aplicacionVacunas) {
        this.aplicacions = aplicacionVacunas;
    }

    public Set<Acudiente> getAcudientes() {
        return acudientes;
    }

    public Paciente acudientes(Set<Acudiente> acudientes) {
        this.acudientes = acudientes;
        return this;
    }

    public Paciente addAcudiente(Acudiente acudiente) {
        this.acudientes.add(acudiente);
        acudiente.setPaciente(this);
        return this;
    }

    public Paciente removeAcudiente(Acudiente acudiente) {
        this.acudientes.remove(acudiente);
        acudiente.setPaciente(null);
        return this;
    }

    public void setAcudientes(Set<Acudiente> acudientes) {
        this.acudientes = acudientes;
    }

    public TipoDocumento getTipoDocumento() {
        return tipoDocumento;
    }

    public Paciente tipoDocumento(TipoDocumento tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
        return this;
    }

    public void setTipoDocumento(TipoDocumento tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public Genero getGenero() {
        return genero;
    }

    public Paciente genero(Genero genero) {
        this.genero = genero;
        return this;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public Aseguradora getAseguradora() {
        return aseguradora;
    }

    public Paciente aseguradora(Aseguradora aseguradora) {
        this.aseguradora = aseguradora;
        return this;
    }

    public void setAseguradora(Aseguradora aseguradora) {
        this.aseguradora = aseguradora;
    }

    public GrupoEtnico getGrupoEtnico() {
        return grupoEtnico;
    }

    public Paciente grupoEtnico(GrupoEtnico grupoEtnico) {
        this.grupoEtnico = grupoEtnico;
        return this;
    }

    public void setGrupoEtnico(GrupoEtnico grupoEtnico) {
        this.grupoEtnico = grupoEtnico;
    }

    public Regimen getRegimen() {
        return regimen;
    }

    public Paciente regimen(Regimen regimen) {
        this.regimen = regimen;
        return this;
    }

    public void setRegimen(Regimen regimen) {
        this.regimen = regimen;
    }

    public Municipio getMunicipio() {
        return municipio;
    }

    public Paciente municipio(Municipio municipio) {
        this.municipio = municipio;
        return this;
    }

    public void setMunicipio(Municipio municipio) {
        this.municipio = municipio;
    }

    public TipoResidencia getTipoResidencia() {
        return tipoResidencia;
    }

    public Paciente tipoResidencia(TipoResidencia tipoResidencia) {
        this.tipoResidencia = tipoResidencia;
        return this;
    }

    public void setTipoResidencia(TipoResidencia tipoResidencia) {
        this.tipoResidencia = tipoResidencia;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Paciente paciente = (Paciente) o;
        if (paciente.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), paciente.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Paciente{" +
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
