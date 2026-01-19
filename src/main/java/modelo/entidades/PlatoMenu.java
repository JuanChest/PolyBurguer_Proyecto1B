package modelo.entidades;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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

	@Enumerated(EnumType.STRING)
	@Column(name = "categoria")
	private Categoria categoria;

	public PlatoMenu() {
	}

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

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

}
