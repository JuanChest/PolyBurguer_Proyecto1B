package modelo;

/**
 * JavaBean que representa un plato del menú
 */
public class PlatoMenu {
    private int idPlato;
    private String nombrePlato;
    private double precio;
    private String descripcion;
    private String imagen;
    private boolean estado;

    // Constructor vacío
    public PlatoMenu() {
    }

    // Constructor con parámetros
    public PlatoMenu(int idPlato, String nombrePlato, double precio, String descripcion, String imagen,
            boolean estado) {
        this.idPlato = idPlato;
        this.nombrePlato = nombrePlato;
        this.precio = precio;
        this.descripcion = descripcion;
        this.imagen = imagen;
        this.estado = estado;
    }

    // Getters y Setters
    public int getIdPlato() {
        return idPlato;
    }

    public void setIdPlato(int idPlato) {
        this.idPlato = idPlato;
    }

    public String getNombrePlato() {
        return nombrePlato;
    }

    public void setNombrePlato(String nombrePlato) {
        this.nombrePlato = nombrePlato;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "PlatoMenu [idPlato=" + idPlato + ", nombrePlato=" + nombrePlato + ", precio=" + precio
                + ", descripcion=" + descripcion + ", imagen=" + imagen + ", estado=" + estado + "]";
    }
}
