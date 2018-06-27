package co.edu.uptc.vacunas.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Genero.
 */
@Entity
@Table(name = "genero")
public class Genero implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @OneToMany(mappedBy = "genero")
    @JsonIgnore
    private Set<Paciente> pacientes = new HashSet<>();

    @OneToMany(mappedBy = "genero")
    @JsonIgnore
    private Set<Vacunador> vacunadors = new HashSet<>();

    @OneToMany(mappedBy = "genero")
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

    public Genero nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Set<Paciente> getPacientes() {
        return pacientes;
    }

    public Genero pacientes(Set<Paciente> pacientes) {
        this.pacientes = pacientes;
        return this;
    }

    public Genero addPaciente(Paciente paciente) {
        this.pacientes.add(paciente);
        paciente.setGenero(this);
        return this;
    }

    public Genero removePaciente(Paciente paciente) {
        this.pacientes.remove(paciente);
        paciente.setGenero(null);
        return this;
    }

    public void setPacientes(Set<Paciente> pacientes) {
        this.pacientes = pacientes;
    }

    public Set<Vacunador> getVacunadors() {
        return vacunadors;
    }

    public Genero vacunadors(Set<Vacunador> vacunadors) {
        this.vacunadors = vacunadors;
        return this;
    }

    public Genero addVacunador(Vacunador vacunador) {
        this.vacunadors.add(vacunador);
        vacunador.setGenero(this);
        return this;
    }

    public Genero removeVacunador(Vacunador vacunador) {
        this.vacunadors.remove(vacunador);
        vacunador.setGenero(null);
        return this;
    }

    public void setVacunadors(Set<Vacunador> vacunadors) {
        this.vacunadors = vacunadors;
    }

    public Set<Acudiente> getAcudientes() {
        return acudientes;
    }

    public Genero acudientes(Set<Acudiente> acudientes) {
        this.acudientes = acudientes;
        return this;
    }

    public Genero addAcudiente(Acudiente acudiente) {
        this.acudientes.add(acudiente);
        acudiente.setGenero(this);
        return this;
    }

    public Genero removeAcudiente(Acudiente acudiente) {
        this.acudientes.remove(acudiente);
        acudiente.setGenero(null);
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
        Genero genero = (Genero) o;
        if (genero.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), genero.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Genero{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            "}";
    }
}
