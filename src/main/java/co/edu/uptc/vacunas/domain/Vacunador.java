package co.edu.uptc.vacunas.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Vacunador.
 */
@Entity
@Table(name = "vacunador")
public class Vacunador implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 8, max = 10)
    @Pattern(regexp = "^[1-9][0-9]*$")
    @Column(name = "documento", length = 10, nullable = false)
    private String documento;

    @NotNull
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @NotNull
    @Column(name = "apellido", nullable = false)
    private String apellido;

    @NotNull
    @Size(min = 10, max = 10)
    @Pattern(regexp = "^[0-9]*$")
    @Column(name = "telefono", length = 10, nullable = false)
    private String telefono;

    @Column(name = "correo")
    private String correo;

    @NotNull
    @Column(name = "fecha_nacimiento", nullable = false)
    private LocalDate fechaNacimiento;

    @OneToMany(mappedBy = "vacunador")
    @JsonIgnore
    private Set<AplicacionVacuna> aplicacions = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    private TipoDocumento tipoDocumento;

    @ManyToOne(optional = false)
    @NotNull
    private Genero genero;

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

    public String getDocumento() {
        return documento;
    }

    public Vacunador documento(String documento) {
        this.documento = documento;
        return this;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getNombre() {
        return nombre;
    }

    public Vacunador nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public Vacunador apellido(String apellido) {
        this.apellido = apellido;
        return this;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getTelefono() {
        return telefono;
    }

    public Vacunador telefono(String telefono) {
        this.telefono = telefono;
        return this;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public Vacunador correo(String correo) {
        this.correo = correo;
        return this;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public Vacunador fechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
        return this;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Set<AplicacionVacuna> getAplicacions() {
        return aplicacions;
    }

    public Vacunador aplicacions(Set<AplicacionVacuna> aplicacionVacunas) {
        this.aplicacions = aplicacionVacunas;
        return this;
    }

    public Vacunador addAplicacion(AplicacionVacuna aplicacionVacuna) {
        this.aplicacions.add(aplicacionVacuna);
        aplicacionVacuna.setVacunador(this);
        return this;
    }

    public Vacunador removeAplicacion(AplicacionVacuna aplicacionVacuna) {
        this.aplicacions.remove(aplicacionVacuna);
        aplicacionVacuna.setVacunador(null);
        return this;
    }

    public void setAplicacions(Set<AplicacionVacuna> aplicacionVacunas) {
        this.aplicacions = aplicacionVacunas;
    }

    public TipoDocumento getTipoDocumento() {
        return tipoDocumento;
    }

    public Vacunador tipoDocumento(TipoDocumento tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
        return this;
    }

    public void setTipoDocumento(TipoDocumento tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public Genero getGenero() {
        return genero;
    }

    public Vacunador genero(Genero genero) {
        this.genero = genero;
        return this;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public Ips getIps() {
        return ips;
    }

    public Vacunador ips(Ips ips) {
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
        Vacunador vacunador = (Vacunador) o;
        if (vacunador.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), vacunador.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Vacunador{" +
            "id=" + getId() +
            ", documento='" + getDocumento() + "'" +
            ", nombre='" + getNombre() + "'" +
            ", apellido='" + getApellido() + "'" +
            ", telefono='" + getTelefono() + "'" +
            ", correo='" + getCorreo() + "'" +
            ", fechaNacimiento='" + getFechaNacimiento() + "'" +
            "}";
    }
}
