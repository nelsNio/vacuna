package co.edu.uptc.vacunas.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Ips.
 */
@Entity
@Table(name = "ips")
public class Ips implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @NotNull
    @Column(name = "direccion", nullable = false)
    private String direccion;

    @OneToMany(mappedBy = "ips")
    @JsonIgnore
    private Set<AplicacionVacuna> aplicacions = new HashSet<>();

    @OneToMany(mappedBy = "ips")
    @JsonIgnore
    private Set<Vacunador> vacunadors = new HashSet<>();

    @ManyToMany
    @NotNull
    @JoinTable(name = "ips_municipio",
               joinColumns = @JoinColumn(name="ips_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="municipios_id", referencedColumnName="id"))
    private Set<Municipio> municipios = new HashSet<>();

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

    public Ips nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public Ips direccion(String direccion) {
        this.direccion = direccion;
        return this;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Set<AplicacionVacuna> getAplicacions() {
        return aplicacions;
    }

    public Ips aplicacions(Set<AplicacionVacuna> aplicacionVacunas) {
        this.aplicacions = aplicacionVacunas;
        return this;
    }

    public Ips addAplicacion(AplicacionVacuna aplicacionVacuna) {
        this.aplicacions.add(aplicacionVacuna);
        aplicacionVacuna.setIps(this);
        return this;
    }

    public Ips removeAplicacion(AplicacionVacuna aplicacionVacuna) {
        this.aplicacions.remove(aplicacionVacuna);
        aplicacionVacuna.setIps(null);
        return this;
    }

    public void setAplicacions(Set<AplicacionVacuna> aplicacionVacunas) {
        this.aplicacions = aplicacionVacunas;
    }

    public Set<Vacunador> getVacunadors() {
        return vacunadors;
    }

    public Ips vacunadors(Set<Vacunador> vacunadors) {
        this.vacunadors = vacunadors;
        return this;
    }

    public Ips addVacunador(Vacunador vacunador) {
        this.vacunadors.add(vacunador);
        vacunador.setIps(this);
        return this;
    }

    public Ips removeVacunador(Vacunador vacunador) {
        this.vacunadors.remove(vacunador);
        vacunador.setIps(null);
        return this;
    }

    public void setVacunadors(Set<Vacunador> vacunadors) {
        this.vacunadors = vacunadors;
    }

    public Set<Municipio> getMunicipios() {
        return municipios;
    }

    public Ips municipios(Set<Municipio> municipios) {
        this.municipios = municipios;
        return this;
    }

    public Ips addMunicipio(Municipio municipio) {
        this.municipios.add(municipio);
        municipio.getIps().add(this);
        return this;
    }

    public Ips removeMunicipio(Municipio municipio) {
        this.municipios.remove(municipio);
        municipio.getIps().remove(this);
        return this;
    }

    public void setMunicipios(Set<Municipio> municipios) {
        this.municipios = municipios;
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
        Ips ips = (Ips) o;
        if (ips.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), ips.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Ips{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", direccion='" + getDireccion() + "'" +
            "}";
    }
}
