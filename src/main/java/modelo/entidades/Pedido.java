package modelo.entidades;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "Pedidos")
public class Pedido implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_pedido")
	private Integer idPedido;

	@Column(name = "nro_pedido", length = 7)
	private String nroPedido;

	@Column(name = "fecha_creacion")
	private Date fechaCreacion;

	@Column(name = "total_pedido")
	private Double totalPedido;

	@Enumerated(EnumType.STRING)
	@Column(name = "estado_pedido")
	private EstadoPedido estadoPedido;

	@OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
	private List<DetallePedido> detalles = new ArrayList<>();

	public Pedido() {
		this.fechaCreacion = new Date();
		this.estadoPedido = EstadoPedido.PENDIENTE;
		this.detalles = new ArrayList<>();
	}

	public Integer getIdPedido() {
		return idPedido;
	}

	public void setIdPedido(Integer idPedido) {
		this.idPedido = idPedido;
	}

	public String getNroPedido() {
		return nroPedido;
	}

	public void setNroPedido(String nroPedido) {
		this.nroPedido = nroPedido;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Double getTotalPedido() {
		return totalPedido;
	}

	public void setTotalPedido(Double totalPedido) {
		this.totalPedido = totalPedido;
	}

	public EstadoPedido getEstadoPedido() {
		return estadoPedido;
	}

	public void setEstadoPedido(EstadoPedido estadoPedido) {
		this.estadoPedido = estadoPedido;
	}

	public List<DetallePedido> getDetalles() {
		return detalles;
	}

	public void setDetalles(List<DetallePedido> detalles) {
		this.detalles = detalles;
	}

	/**
	 * Calcula el total del pedido sumando los subtotales de todos los detalles
	 *
	 * @param detalles Lista de detalles del pedido
	 * @return Total del pedido
	 */
	public double calcularTotal(List<DetallePedido> detalles) {
		double total = 0.0;
		for (DetallePedido detalle : detalles) {
			total += detalle.getSubtotal();
		}
		return total;
	}

	/**
	 * Genera el número de atención con formato PD-XXXX
	 * El contador se reinicia cada 100 pedidos (PD-0001 a PD-0100)
	 *
	 * @param ultimoNumero Último número generado
	 * @return Número de atención formateado
	 */
	public static String generarNroAtencion(int ultimoNumero) {
		int siguienteNumero = (ultimoNumero % 100) + 1;
		return String.format("PD-%04d", siguienteNumero);
	}

	/**
	 * Calcula el total del pedido sumando subtotales de detalles
	 * Sobrecarga para usar la lista interna del pedido
	 * 
	 * @return Total del pedido
	 */
	public double calcularTotal() {
		return this.calcularTotal(this.detalles);
	}

	/**
	 * Agrega un detalle al pedido y recalcula el total
	 * Si el plato ya existe en el pedido, incrementa su cantidad
	 * 
	 * @param nuevoDetalle Detalle a agregar
	 */
	public void agregarDetalle(DetallePedido nuevoDetalle) {
		// Buscar si ya existe el plato
		boolean existe = false;
		for (DetallePedido detalle : this.detalles) {
			if (detalle.getPlatoMenu().getIdPlatoMenu() == nuevoDetalle.getPlatoMenu().getIdPlatoMenu()) {
				// Incrementar cantidad
				detalle.setCantidad(detalle.getCantidad() + nuevoDetalle.getCantidad());
				detalle.calcularSubtotal();
				existe = true;
				break;
			}
		}

		// Si no existe, agregarlo
		if (!existe) {
			nuevoDetalle.setPedido(this);
			this.detalles.add(nuevoDetalle);
		}

		// Recalcular total automáticamente
		this.totalPedido = this.calcularTotal();
	}

	/**
	 * Modifica la cantidad de un detalle específico
	 * 
	 * @param idPlato       ID del plato a modificar
	 * @param nuevaCantidad Nueva cantidad
	 * @return true si se modificó, false si no se encontró
	 */
	public boolean modificarCantidadDetalle(int idPlato, int nuevaCantidad) {
		for (DetallePedido detalle : this.detalles) {
			if (detalle.getPlatoMenu().getIdPlatoMenu() == idPlato) {
				if (nuevaCantidad <= 0) {
					this.detalles.remove(detalle);
				} else {
					detalle.setCantidad(nuevaCantidad);
					detalle.calcularSubtotal();
				}
				this.totalPedido = this.calcularTotal();
				return true;
			}
		}
		return false;
	}

	/**
	 * Elimina un detalle del pedido
	 * 
	 * @param idPlato ID del plato a eliminar
	 * @return true si se eliminó, false si no se encontró
	 */
	public boolean eliminarDetalle(int idPlato) {
		boolean eliminado = this.detalles.removeIf(
				detalle -> detalle.getPlatoMenu().getIdPlatoMenu() == idPlato);
		if (eliminado) {
			this.totalPedido = this.calcularTotal();
		}
		return eliminado;
	}

	/**
	 * Verifica si el pedido está vacío
	 */
	public boolean estaVacio() {
		return this.detalles == null || this.detalles.isEmpty();
	}

	@Override
	public String toString() {
		return "Pedido [idPedido=" + idPedido + ", nroPedido=" + nroPedido + ", fechaCreacion=" + fechaCreacion
				+ ", totalPedido=" + totalPedido + ", estadoPedido=" + estadoPedido + "]";
	}

}
