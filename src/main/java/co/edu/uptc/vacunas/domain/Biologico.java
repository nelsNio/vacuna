package co.edu.uptc.vacunas.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Biologico.
 */
@Entity
@Table(name = "biologico")
public class Biologico implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @NotNull
    @Column(name = "lote", nullable = false)
    private String lote;

    @NotNull
    @Column(name = "lote_jeringa", nullable = false)
    private String loteJeringa;

    @OneToMany(mappedBy = "biologico")
    @JsonIgnore
    private Set<Dosis> doses = new HashSet<>();

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

    public Biologico nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getLote() {
        return lote;
    }

    public Biologico lote(String lote) {
        this.lote = lote;
        return this;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public String getLoteJeringa() {
        return loteJeringa;
    }

    public Biologico loteJeringa(String loteJeringa) {
        this.loteJeringa = loteJeringa;
        return this;
    }

    public void setLoteJeringa(String loteJeringa) {
        this.loteJeringa = loteJeringa;
    }

    public Set<Dosis> getDoses() {
        return doses;
    }

    public Biologico doses(Set<Dosis> doses) {
        this.doses = doses;
        return this;
    }

    public Biologico addDosis(Dosis dosis) {
        this.doses.add(dosis);
        dosis.setBiologico(this);
        return this;
    }

    public Biologico removeDosis(Dosis dosis) {
        this.doses.remove(dosis);
        dosis.setBiologico(null);
        return this;
    }

    public void setDoses(Set<Dosis> doses) {
        this.doses = doses;
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
        Biologico biologico = (Biologico) o;
        if (biologico.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), biologico.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Biologico{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", lote='" + getLote() + "'" +
            ", loteJeringa='" + getLoteJeringa() + "'" +
            "}";
    }
}
