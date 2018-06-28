package co.edu.uptc.vacunas.service.dto;


import java.io.Serializable;
import java.util.ArrayList;

/**
 * A DTO for the Paciente entity.
 */
public class ReporteVacunadosDTO implements Serializable {

    private static final long serialVersionUID = 1L;

	

   
    private String documento;

    
    private String nombre;

    
    private String apellido;
    
    
    private ArrayList<String> vacunadoContra;

    private ArrayList<String> vacunadoFaltantes;

    public ReporteVacunadosDTO() {
    }

    public ReporteVacunadosDTO(String documento, String nombre, String apellido, ArrayList<String> vacunadoContra, ArrayList<String> vacunadoFaltantes) {
        this.documento = documento;
        this.nombre = nombre;
        this.apellido = apellido;
        this.vacunadoContra = vacunadoContra;
        this.vacunadoFaltantes = vacunadoFaltantes;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
/**
	 * @return the documento
	 */
	public String getDocumento() {
		return documento;
	}

	/**
	 * @return the vacunadoFaltantes
	 */
	public ArrayList<String> getVacunadoFaltantes() {
		return vacunadoFaltantes;
	}

	/**
	 * @param vacunadoFaltantes the vacunadoFaltantes to set
	 */
	public void setVacunadoFaltantes(ArrayList<String> vacunadoFaltantes) {
		this.vacunadoFaltantes = vacunadoFaltantes;
	}

	/**
	 * @return the vacunadoContra
	 */
	public ArrayList<String> getVacunadoContra() {
		return vacunadoContra;
	}

	/**
	 * @param vacunadoContra the vacunadoContra to set
	 */
	public void setVacunadoContra(ArrayList<String> vacunadoContra) {
		this.vacunadoContra = vacunadoContra;
	}

	/**
	 * @return the apellido
	 */
	public String getApellido() {
		return apellido;
	}

	/**
	 * @param apellido the apellido to set
	 */
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	/**
	 * @param documento the documento to set
	 */
	public void setDocumento(String documento) {
		this.documento = documento;
	}


        public ArrayList<String> addVacunaContra(String dosis){
            if(this.vacunadoContra==null){
                this.vacunadoContra=new ArrayList<>();
            }
            this.vacunadoContra.add(dosis);
            return this.vacunadoContra;
        }
        public ArrayList<String> addVacunaFaltante(String dosis){
            if(this.vacunadoFaltantes==null){
                this.vacunadoFaltantes=new ArrayList<>();
            }
            this.vacunadoFaltantes.add(dosis);
            return this.vacunadoFaltantes;
        }

    
}
