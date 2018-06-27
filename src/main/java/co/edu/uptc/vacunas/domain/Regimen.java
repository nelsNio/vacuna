package co.edu.uptc.vacunas.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Regimen.
 */
@Entity
@Table(name = "regimen")
public class Regimen implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @OneToMany(mappedBy = "regimen")
    @JsonIgnore
    private Set<Paciente> pacientes = new HashSet<>();

    @ManyToMany(mappedBy = "regimen")
    @JsonIgnore
    private Set<Aseguradora> aseguradoras = new HashSet<>();

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

    public Regimen nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Set<Paciente> getPacientes() {
        return pacientes;
    }

    public Regimen pacientes(Set<Paciente> pacientes) {
        this.pacientes = pacientes;
        return this;
    }

    public Regimen addPaciente(Paciente paciente) {
        this.pacientes.add(paciente);
        paciente.setRegimen(this);
        return this;
    }

    public Regimen removePaciente(Paciente paciente) {
        this.pacientes.remove(paciente);
        paciente.setRegimen(null);
        return this;
    }

    public void setPacientes(Set<Paciente> pacientes) {
        this.pacientes = pacientes;
    }

    public Set<Aseguradora> getAseguradoras() {
        return aseguradoras;
    }

    public Regimen aseguradoras(Set<Aseguradora> aseguradoras) {
        this.aseguradoras = aseguradoras;
        return this;
    }

    public Regimen addAseguradora(Aseguradora aseguradora) {
        this.aseguradoras.add(aseguradora);
        aseguradora.getRegimen().add(this);
        return this;
    }

    public Regimen removeAseguradora(Aseguradora aseguradora) {
        this.aseguradoras.remove(aseguradora);
        aseguradora.getRegimen().remove(this);
        return this;
    }

    public void setAseguradoras(Set<Aseguradora> aseguradoras) {
        this.aseguradoras = aseguradoras;
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
        Regimen regimen = (Regimen) o;
        if (regimen.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), regimen.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Regimen{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            "}";
    }
}
