package modelo.dao;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
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
		String sentenciaJPQL = "SELECT p FROM PlatoMenu p";
		Query query = em.createQuery(sentenciaJPQL);
		List<PlatoMenu> platos = query.getResultList();
		return platos;
	}

	public PlatoMenu obtenerPlato(int id) {
		PlatoMenu plato = em.find(PlatoMenu.class, id);
		return plato;
	}

}
