package modelo.entidades;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("COCINERO")
public class Cocinero extends Usuario {

	private static final long serialVersionUID = 1L;

    public Cocinero() {
        super();
    }

    public Cocinero(String nombre, String apellido, String cedula, String contrasenia) {
        super(nombre, apellido, cedula, contrasenia);
    }

    /**
     * Retorna el rol del usuario
     * 
     * @return "COCINERO"
     */
    public String determinarRol() {
        return "COCINERO";
    }

}