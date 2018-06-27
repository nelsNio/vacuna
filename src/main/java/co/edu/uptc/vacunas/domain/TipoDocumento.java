package co.edu.uptc.vacunas.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A TipoDocumento.
 */
@Entity
@Table(name = "tipo_documento")
public class TipoDocumento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @OneToMany(mappedBy = "tipoDocumento")
    @JsonIgnore
    private Set<Paciente> pacientes = new HashSet<>();

    @OneToMany(mappedBy = "tipoDocumento")
    @JsonIgnore
    private Set<Vacunador> vacunadors = new HashSet<>();

    @OneToMany(mappedBy = "tipoDocumento")
    @JsonIgnore
    private Set<Acudiente> acudientes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public TipoDocumento nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Set<Paciente> getPacientes() {
        return pacientes;
    }

    public TipoDocumento pacientes(Set<Paciente> pacientes) {
        this.pacientes = pacientes;
        return this;
    }

    public TipoDocumento addPaciente(Paciente paciente) {
        this.pacientes.add(paciente);
        paciente.setTipoDocumento(this);
        return this;
    }

    public TipoDocumento removePaciente(Paciente paciente) {
        this.pacientes.remove(paciente);
        paciente.setTipoDocumento(null);
        return this;
    }

    public void setPacientes(Set<Paciente> pacientes) {
        this.pacientes = pacientes;
    }

    public Set<Vacunador> getVacunadors() {
        return vacunadors;
    }

    public TipoDocumento vacunadors(Set<Vacunador> vacunadors) {
        this.vacunadors = vacunadors;
        return this;
    }

    public TipoDocumento addVacunador(Vacunador vacunador) {
        this.vacunadors.add(vacunador);
        vacunador.setTipoDocumento(this);
        return this;
    }

    public TipoDocumento removeVacunador(Vacunador vacunador) {
        this.vacunadors.remove(vacunador);
        vacunador.setTipoDocumento(null);
        return this;
    }

    public void setVacunadors(Set<Vacunador> vacunadors) {
        this.vacunadors = vacunadors;
    }

    public Set<Acudiente> getAcudientes() {
        return acudientes;
    }

    public TipoDocumento acudientes(Set<Acudiente> acudientes) {
        this.acudientes = acudientes;
        return this;
    }

    public TipoDocumento addAcudiente(Acudiente acudiente) {
        this.acudientes.add(acudiente);
        acudiente.setTipoDocumento(this);
        return this;
    }

    public TipoDocumento removeAcudiente(Acudiente acudiente) {
        this.acudientes.remove(acudiente);
        acudiente.setTipoDocumento(null);
        return this;
    }

    public void setAcudientes(Set<Acudiente> acudientes) {
        this.acudientes = acudientes;
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
        TipoDocumento tipoDocumento = (TipoDocumento) o;
        if (tipoDocumento.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tipoDocumento.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TipoDocumento{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            "}";
    }
}
