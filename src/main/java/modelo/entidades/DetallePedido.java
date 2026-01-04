package modelo.entidades;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Detalle_pedidos")
public class DetallePedido {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idDetallePedido;
	private int cantidad;
	private double subtotal;
	
	@ManyToOne
    @JoinColumn(name = "id_plato_menu") // Clave foránea en la DB
    private PlatoMenu platoMenu;

	@ManyToOne
    @JoinColumn(name = "nro_pedido") // Clave foránea que une el detalle al pedido
    private Pedido pedido;

	public int getIdDetallePedido() {
		return idDetallePedido;
	}

	public void setIdDetallePedido(int idDetallePedido) {
		this.idDetallePedido = idDetallePedido;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public double getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(double subtotal) {
		this.subtotal = subtotal;
	}

	public PlatoMenu getPlatoMenu() {
		return platoMenu;
	}

	public void setPlatoMenu(PlatoMenu platoMenu) {
		this.platoMenu = platoMenu;
	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}
	
	
}
