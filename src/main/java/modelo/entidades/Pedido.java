package modelo.entidades;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Pedidos")
public class Pedido implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "nroPedido")
	private String nroPedido;
	
	@Column (name = "fechaCreacion")
	private Date fechaCreacion;
	
	@Column (name = "totalPedido")
	private Double totalPedido;
	
	@Enumerated(EnumType.STRING)
	@Column (name = "estadoPedido")
	private EstadoPedido estadoPedido;
		
	public Pedido(String nroPedido) {
		this.nroPedido = nroPedido;
        this.estadoPedido = EstadoPedido.PENDIENTE;
        this.fechaCreacion = new Date();
    }
	
	public Pedido() {
	    this.fechaCreacion = new Date();
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
	@Override
	public String toString() {
		return "Pedido [nroPedido=" + nroPedido + ", fechaCreacion=" + fechaCreacion + ", totalPedido=" + totalPedido
				+ ", estadoPedido=" + estadoPedido + "]";
	}
	
}
