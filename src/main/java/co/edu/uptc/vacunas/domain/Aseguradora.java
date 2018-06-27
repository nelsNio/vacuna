package co.edu.uptc.vacunas.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Aseguradora.
 */
@Entity
@Table(name = "aseguradora")
public class Aseguradora implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @OneToMany(mappedBy = "aseguradora")
    @JsonIgnore
    private Set<Paciente> pacientes = new HashSet<>();

    @ManyToMany
    @NotNull
    @JoinTable(name = "aseguradora_regimen",
               joinColumns = @JoinColumn(name="aseguradoras_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="regimen_id", referencedColumnName="id"))
    private Set<Regimen> regimen = new HashSet<>();

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

    public Aseguradora nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Set<Paciente> getPacientes() {
        return pacientes;
    }

    public Aseguradora pacientes(Set<Paciente> pacientes) {
        this.pacientes = pacientes;
        return this;
    }

    public Aseguradora addPaciente(Paciente paciente) {
        this.pacientes.add(paciente);
        paciente.setAseguradora(this);
        return this;
    }

    public Aseguradora removePaciente(Paciente paciente) {
        this.pacientes.remove(paciente);
        paciente.setAseguradora(null);
        return this;
    }

    public void setPacientes(Set<Paciente> pacientes) {
        this.pacientes = pacientes;
    }

    public Set<Regimen> getRegimen() {
        return regimen;
    }

    public Aseguradora regimen(Set<Regimen> regimen) {
        this.regimen = regimen;
        return this;
    }

    public Aseguradora addRegimen(Regimen regimen) {
        this.regimen.add(regimen);
        regimen.getAseguradoras().add(this);
        return this;
    }

    public Aseguradora removeRegimen(Regimen regimen) {
        this.regimen.remove(regimen);
        regimen.getAseguradoras().remove(this);
        return this;
    }

    public void setRegimen(Set<Regimen> regimen) {
        this.regimen = regimen;
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
        Aseguradora aseguradora = (Aseguradora) o;
        if (aseguradora.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), aseguradora.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Aseguradora{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            "}";
    }
}
