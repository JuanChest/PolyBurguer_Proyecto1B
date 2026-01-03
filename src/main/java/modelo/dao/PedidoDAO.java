package modelo.dao;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import modelo.entidades.Pedido;

public class PedidoDAO {
	
	EntityManager em = null;
	
	public PedidoDAO () {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("PolyBurguer_Proyecto1B");
		this.em = emf.createEntityManager();
	}

	public List<Pedido> obtenerPedidos() {
		String sentenciaJPQL = "SELECT p FROM Pedido p " +
                			   "ORDER BY CASE p.estadoPedido " +
                			   "  WHEN modelo.entidades.EstadoPedido.PENDIENTE THEN 1 " +
                			   "  WHEN modelo.entidades.EstadoPedido.EN_PREPARACION THEN 2 " +
                			   "  WHEN modelo.entidades.EstadoPedido.LISTO THEN 3 " +
                			   "  ELSE 4 END ASC";
		Query query = em.createQuery(sentenciaJPQL);
		List<Pedido> resultado = query.getResultList();
		return resultado;
	}

	public Pedido obtenerPedido(String nroPedido) {
	    Pedido p = em.find(Pedido.class, nroPedido); 
	    return p;
	}
	
	public void guardarPedido(Pedido pedidoAGuardar) {
	}

	public void actualizarEstado(Pedido pedido) {
		em.getTransaction().begin();
		try {
			em.merge(pedido);
			em.getTransaction().commit();
		} catch (Exception e) {
			System.out.println(">>>> ERROR: Actualización de Estado");
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
		}
	}
	
//	public Pedido crearPedido(PlatoMenu plato) {
//		return null;
//	}
	
	public double calcularTotal() {
		return 0;
	}
	
	public String generarNroPedido() {
		String nuevoNro = "pb0001";

	    try {
	        String jpql = "SELECT p.nroPedido FROM Pedido p ORDER BY p.nroPedido DESC";
	        List<String> resultados = em.createQuery(jpql, String.class)
	                                    .setMaxResults(1)
	                                    .getResultList();

	        if (!resultados.isEmpty()) {
	            String ultimoNro = resultados.get(0);
	            
	            int numeroActual = Integer.parseInt(ultimoNro.replace("pb", ""));
	            int siguienteNumero = numeroActual + 1;
	            
	            nuevoNro = "pb" + String.format("%04d", siguienteNumero);
	        }
	    } catch (Exception e) {
	        System.out.println("Error al generar ID, usando pb0001 por defecto");
	    }
	    
	    return nuevoNro;
	}
}
