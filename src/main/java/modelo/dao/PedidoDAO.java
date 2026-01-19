package modelo.dao;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import modelo.entidades.Pedido;

public class PedidoDAO {

	EntityManager em = null;

	public PedidoDAO() {
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

	public Pedido obtenerPedido(Integer idPedido) {
		return em.find(Pedido.class, idPedido);
	}

	public void guardarPedido(Pedido pedidoAGuardar) {
		em.getTransaction().begin();
		try {
			em.persist(pedidoAGuardar);
			em.getTransaction().commit();
			System.out.println("✅ Pedido guardado: " + pedidoAGuardar.getNroPedido());
		} catch (Exception e) {
			System.err.println("❌ Error al guardar pedido: " + e.getMessage());
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			throw e;
		}
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

	// public Pedido crearPedido(PlatoMenu plato) {
	// return null;
	// }

	/**
	 * Obtiene el último número de pedido para generar el siguiente
	 *
	 * @return Cantidad total de pedidos registrados
	 */
	public int obtenerUltimoNumeroPedido() {
		try {
			String jpql = "SELECT COUNT(p) FROM Pedido p";
			Long count = em.createQuery(jpql, Long.class).getSingleResult();
			return count.intValue();
		} catch (Exception e) {
			System.out.println("Error al obtener último número, usando 0 por defecto");
			return 0;
		}
	}
}
