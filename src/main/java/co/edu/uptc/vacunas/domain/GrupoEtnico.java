package co.edu.uptc.vacunas.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A GrupoEtnico.
 */
@Entity
@Table(name = "grupo_etnico")
public class GrupoEtnico implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @OneToMany(mappedBy = "grupoEtnico")
    @JsonIgnore
    private Set<Paciente> pacientes = new HashSet<>();

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

    public GrupoEtnico nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Set<Paciente> getPacientes() {
        return pacientes;
    }

    public GrupoEtnico pacientes(Set<Paciente> pacientes) {
        this.pacientes = pacientes;
        return this;
    }

    public GrupoEtnico addPaciente(Paciente paciente) {
        this.pacientes.add(paciente);
        paciente.setGrupoEtnico(this);
        return this;
    }

    public GrupoEtnico removePaciente(Paciente paciente) {
        this.pacientes.remove(paciente);
        paciente.setGrupoEtnico(null);
        return this;
    }

    public void setPacientes(Set<Paciente> pacientes) {
        this.pacientes = pacientes;
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
        GrupoEtnico grupoEtnico = (GrupoEtnico) o;
        if (grupoEtnico.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), grupoEtnico.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "GrupoEtnico{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            "}";
    }
}
