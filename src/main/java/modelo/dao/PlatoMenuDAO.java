package modelo.dao;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import modelo.entidades.PlatoMenu;

public class PlatoMenuDAO {
	EntityManager em = null;

	public PlatoMenuDAO() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("PolyBurguer_Proyecto1B");
		this.em = emf.createEntityManager();
	}

	public List<PlatoMenu> obtenerPlatos() {
		String sentenciaJPQL = "SELECT p FROM PlatoMenu p ORDER BY p.categoria, p.nombre";
		Query query = em.createQuery(sentenciaJPQL);
		List<PlatoMenu> platos = query.getResultList();
		return platos;
	}

	public PlatoMenu obtenerPlato(int id) {
		PlatoMenu plato = em.find(PlatoMenu.class, id);
		return plato;
	}

	public boolean actualizarEstado(PlatoMenu plato, boolean nuevoEstado) {
		try {
			EntityTransaction transaction = em.getTransaction();
			transaction.begin();

			PlatoMenu platoGestionado = em.find(PlatoMenu.class, plato.getIdPlatoMenu());

			if (platoGestionado != null) {
				platoGestionado.setDisponible(nuevoEstado);
				em.merge(platoGestionado);
				transaction.commit();
				return true;
			}

			transaction.commit();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Guarda un nuevo plato en la base de datos.
	 * @param nuevoPlato - PlatoMenu a guardar
	 * @return true si se guardó exitosamente, false en caso contrario
	 */
	public boolean guardarNuevoPlato(PlatoMenu nuevoPlato) {
		try {
			EntityTransaction transaction = em.getTransaction();
			transaction.begin();

			em.persist(nuevoPlato);
			transaction.commit();

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Guarda los cambios realizados en un plato existente.
	 * @param platoModificado - PlatoMenu con los datos actualizados
	 * @return true si se guardaron los cambios exitosamente, false en caso contrario
	 */
	public boolean guardarCambiosPlato(PlatoMenu platoModificado) {
		try {
			EntityTransaction transaction = em.getTransaction();
			transaction.begin();

			PlatoMenu platoEnBD = em.find(PlatoMenu.class, platoModificado.getIdPlatoMenu());

			if (platoEnBD != null) {
				platoEnBD.setNombre(platoModificado.getNombre());
				platoEnBD.setDescripcion(platoModificado.getDescripcion());
				platoEnBD.setPrecio(platoModificado.getPrecio());
				platoEnBD.setCategoria(platoModificado.getCategoria());
				platoEnBD.setDisponible(platoModificado.isDisponible());

				em.merge(platoEnBD);
				transaction.commit();

				return true;
			}

			transaction.rollback();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Elimina un plato no comercializable de la base de datos.
	 * @param platoAEliminar - PlatoMenu a eliminar
	 * @return true si se eliminó exitosamente, false en caso contrario
	 */
	public boolean eliminarPlatoNoComercializable(PlatoMenu platoAEliminar) {
		try {
			EntityTransaction transaction = em.getTransaction();
			transaction.begin();

			PlatoMenu platoEnBD = em.find(PlatoMenu.class, platoAEliminar.getIdPlatoMenu());

			if (platoEnBD != null) {
				em.remove(platoEnBD);
				transaction.commit();
				return true;
			}

			transaction.rollback();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}
