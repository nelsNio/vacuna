package co.edu.uptc.vacunas.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A AplicacionVacuna.
 */
@Entity
@Table(name = "aplicacion_vacuna")
public class AplicacionVacuna implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    @ManyToOne(optional = false)
    @NotNull
    private Vacunador vacunador;

    @ManyToOne(optional = false)
    @NotNull
    private Paciente paciente;

    @ManyToOne(optional = false)
    @NotNull
    private Dosis dosis;

    @ManyToOne(optional = false)
    @NotNull
    private Ips ips;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public AplicacionVacuna fecha(LocalDate fecha) {
        this.fecha = fecha;
        return this;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Vacunador getVacunador() {
        return vacunador;
    }

    public AplicacionVacuna vacunador(Vacunador vacunador) {
        this.vacunador = vacunador;
        return this;
    }

    public void setVacunador(Vacunador vacunador) {
        this.vacunador = vacunador;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public AplicacionVacuna paciente(Paciente paciente) {
        this.paciente = paciente;
        return this;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Dosis getDosis() {
        return dosis;
    }

    public AplicacionVacuna dosis(Dosis dosis) {
        this.dosis = dosis;
        return this;
    }

    public void setDosis(Dosis dosis) {
        this.dosis = dosis;
    }

    public Ips getIps() {
        return ips;
    }

    public AplicacionVacuna ips(Ips ips) {
        this.ips = ips;
        return this;
    }

    public void setIps(Ips ips) {
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
        AplicacionVacuna aplicacionVacuna = (AplicacionVacuna) o;
        if (aplicacionVacuna.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), aplicacionVacuna.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AplicacionVacuna{" +
            "id=" + getId() +
            ", fecha='" + getFecha() + "'" +
            "}";
    }
}
