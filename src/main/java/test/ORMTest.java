package test;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import modelo.entidades.Pedido;

public class ORMTest {

	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("PolyBurguer_Proyecto1B");
		EntityManager em = emf.createEntityManager();

		insertar(em);
		insertar(em);
		insertar(em);
		insertar(em);
		insertar(em);
		insertar(em);
		insertar(em);
		insertar(em);
		insertar(em);
		insertar(em);
		insertar(em);
		insertar(em);
		insertar(em);
		insertar(em);
		insertar(em);
		insertar(em);
		insertar(em);

		Pedido pedidoParaCambiar = em.find(Pedido.class, "pb0001");

		String nuevoId = generarNroPedido(em);

		if (pedidoParaCambiar != null) {
			System.out.println("Estado antes: " + pedidoParaCambiar.getEstadoPedido());
			pedidoParaCambiar.setEstadoPedido(modelo.entidades.EstadoPedido.EN_PREPARACION);
			actualizarEstado(pedidoParaCambiar, em);
			System.out.println("Estado después: " + pedidoParaCambiar.getEstadoPedido());
		} else {
			System.out.println("No se encontró el pedido pb0001 para actualizar.");
		}

		// leer(em);

		// actualizacion(em);

		// eliminar(em);

		List<Pedido> lista = obtenerPedidos(em);

		System.out.println("Siguiente pedido= - " + nuevoId);
		System.out.println("--- LISTA DE PEDIDOS ORDENADOS ---");
		for (Pedido p : lista) {
			System.out.println("ID: " + p.getNroPedido() + " - Estado: " + p.getEstadoPedido());
		}
	}

	private static void eliminar(EntityManager em) {
		Pedido prueba = em.find(Pedido.class, "pb1");

		em.getTransaction().begin();
		em.remove(prueba);
		em.getTransaction().commit();

	}

	public static List<Pedido> obtenerPedidos(EntityManager em) {
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

	private static void actualizacion(EntityManager em) {
		Pedido prueba = em.find(Pedido.class, "pb1");
		prueba.setTotalPedido(12.59);

		em.getTransaction().begin();
		em.merge(prueba);
		em.getTransaction().commit();

	}

	private static void leer(EntityManager em) {
		Pedido prueba = em.find(Pedido.class, "pb1");
		System.out.println(prueba);
	}

	private static void insertar(EntityManager em) {
		String proximoId = generarNroPedido(em);

		Pedido prueba = new Pedido();
		prueba.setNroPedido(proximoId);

		em.getTransaction().begin();
		em.persist(prueba);
		em.getTransaction().commit();
	}

	public static String generarNroPedido(EntityManager em) {
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

	public static void actualizarEstado(Pedido pedido, EntityManager em) {
		em.getTransaction().begin();
		try {
			em.merge(pedido);
			em.getTransaction().commit();
			System.out.println(">>>> Pedido " + pedido.getNroPedido() + " actualizado exitosamente.");
		} catch (Exception e) {
			System.out.println(">>>> ERROR: Actualización de Estado");
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
		}
	}
}
