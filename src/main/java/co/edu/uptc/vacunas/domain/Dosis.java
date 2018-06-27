package co.edu.uptc.vacunas.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Dosis.
 */
@Entity
@Table(name = "dosis")
public class Dosis implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @NotNull
    @Column(name = "edad_minima", nullable = false)
    private Integer edadMinima;

    @NotNull
    @Column(name = "edad_maxima", nullable = false)
    private Integer edadMaxima;

    @NotNull
    @Column(name = "embarazo", nullable = false)
    private Boolean embarazo;

    @OneToMany(mappedBy = "dosis")
    @JsonIgnore
    private Set<AplicacionVacuna> aplicacions = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    private Biologico biologico;

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

    public Dosis nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getEdadMinima() {
        return edadMinima;
    }

    public Dosis edadMinima(Integer edadMinima) {
        this.edadMinima = edadMinima;
        return this;
    }

    public void setEdadMinima(Integer edadMinima) {
        this.edadMinima = edadMinima;
    }

    public Integer getEdadMaxima() {
        return edadMaxima;
    }

    public Dosis edadMaxima(Integer edadMaxima) {
        this.edadMaxima = edadMaxima;
        return this;
    }

    public void setEdadMaxima(Integer edadMaxima) {
        this.edadMaxima = edadMaxima;
    }

    public Boolean isEmbarazo() {
        return embarazo;
    }

    public Dosis embarazo(Boolean embarazo) {
        this.embarazo = embarazo;
        return this;
    }

    public void setEmbarazo(Boolean embarazo) {
        this.embarazo = embarazo;
    }

    public Set<AplicacionVacuna> getAplicacions() {
        return aplicacions;
    }

    public Dosis aplicacions(Set<AplicacionVacuna> aplicacionVacunas) {
        this.aplicacions = aplicacionVacunas;
        return this;
    }

    public Dosis addAplicacion(AplicacionVacuna aplicacionVacuna) {
        this.aplicacions.add(aplicacionVacuna);
        aplicacionVacuna.setDosis(this);
        return this;
    }

    public Dosis removeAplicacion(AplicacionVacuna aplicacionVacuna) {
        this.aplicacions.remove(aplicacionVacuna);
        aplicacionVacuna.setDosis(null);
        return this;
    }

    public void setAplicacions(Set<AplicacionVacuna> aplicacionVacunas) {
        this.aplicacions = aplicacionVacunas;
    }

    public Biologico getBiologico() {
        return biologico;
    }

    public Dosis biologico(Biologico biologico) {
        this.biologico = biologico;
        return this;
    }

    public void setBiologico(Biologico biologico) {
        this.biologico = biologico;
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
        Dosis dosis = (Dosis) o;
        if (dosis.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), dosis.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Dosis{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", edadMinima=" + getEdadMinima() +
            ", edadMaxima=" + getEdadMaxima() +
            ", embarazo='" + isEmbarazo() + "'" +
            "}";
    }
}
