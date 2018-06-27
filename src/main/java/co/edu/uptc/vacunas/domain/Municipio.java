package co.edu.uptc.vacunas.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Municipio.
 */
@Entity
@Table(name = "municipio")
public class Municipio implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @OneToMany(mappedBy = "municipio")
    @JsonIgnore
    private Set<Paciente> pacientes = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    private Departamento departamento;

    @ManyToMany(mappedBy = "municipios")
    @JsonIgnore
    private Set<Ips> ips = new HashSet<>();

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

    public Municipio nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Set<Paciente> getPacientes() {
        return pacientes;
    }

    public Municipio pacientes(Set<Paciente> pacientes) {
        this.pacientes = pacientes;
        return this;
    }

    public Municipio addPaciente(Paciente paciente) {
        this.pacientes.add(paciente);
        paciente.setMunicipio(this);
        return this;
    }

    public Municipio removePaciente(Paciente paciente) {
        this.pacientes.remove(paciente);
        paciente.setMunicipio(null);
        return this;
    }

    public void setPacientes(Set<Paciente> pacientes) {
        this.pacientes = pacientes;
    }

    public Departamento getDepartamento() {
        return departamento;
    }

    public Municipio departamento(Departamento departamento) {
        this.departamento = departamento;
        return this;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }

    public Set<Ips> getIps() {
        return ips;
    }

    public Municipio ips(Set<Ips> ips) {
        this.ips = ips;
        return this;
    }

    public Municipio addIps(Ips ips) {
        this.ips.add(ips);
        ips.getMunicipios().add(this);
        return this;
    }

    public Municipio removeIps(Ips ips) {
        this.ips.remove(ips);
        ips.getMunicipios().remove(this);
        return this;
    }

    public void setIps(Set<Ips> ips) {
        this.ips = ips;
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
        Municipio municipio = (Municipio) o;
        if (municipio.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), municipio.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Municipio{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            "}";
    }
}
