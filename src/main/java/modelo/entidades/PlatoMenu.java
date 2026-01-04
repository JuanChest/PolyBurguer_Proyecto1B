package modelo.entidades;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Platos_menu")
public class PlatoMenu {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idPlatoMenu;
	private String nombre;
	private String descripcion;
	private String imagen;
	private double precio;
	private boolean disponible;

	public int getIdPlatoMenu() {
		return idPlatoMenu;
	}

	public void setIdPlatoMenu(int idPlatoMenu) {
		this.idPlatoMenu = idPlatoMenu;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public boolean isDisponible() {
		return disponible;
	}

	public void setDisponible(boolean disponible) {
		this.disponible = disponible;
	}

	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

}
