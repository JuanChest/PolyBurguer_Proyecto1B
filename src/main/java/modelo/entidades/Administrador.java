package modelo.entidades;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("ADMINISTRADOR")
public class Administrador extends Usuario {

    private static final long serialVersionUID = 1L;

    public Administrador() {
        super();
    }

    public Administrador(String nombre, String apellido, String cedula, String contrasenia) {
        super(nombre, apellido, cedula, contrasenia);
    }

    /**
     * Retorna el rol del usuario
     * 
     * @return "ADMINISTRADOR"
     */
    public String determinarRol() {
        return "ADMINISTRADOR";
    }
}
