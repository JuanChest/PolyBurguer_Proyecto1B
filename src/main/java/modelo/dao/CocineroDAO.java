package modelo.dao;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import modelo.entidades.Cocinero;

public class CocineroDAO {

	EntityManager em = null;

	public CocineroDAO() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("PolyBurguer_Proyecto1B");
		this.em = emf.createEntityManager();
	}

	public List<Cocinero> obtenerCuentas() {
	    return em.createQuery("SELECT c FROM Cocinero c", Cocinero.class)
	             .getResultList();
	}

	public boolean validarUsuario(Cocinero usuario) {
	    String sentenciaJPQL = "SELECT c FROM Cocinero c WHERE c.cedula = :cedula";
	    Query query = em.createQuery(sentenciaJPQL);
	    query.setParameter("cedula", usuario.getCedula());

	    List<Cocinero> resultado = query.getResultList();
	    return resultado.isEmpty();
	}

	public Cocinero buscarPorId(int id) {
		try {
	        return em.find(Cocinero.class, id);
	     } catch (Exception e) {
	        e.printStackTrace();
	        return null;
	     }
	}

	public void cambiarEstado(Cocinero usuario) {
	    try {
	        em.getTransaction().begin();
	        em.merge(usuario);
	        em.getTransaction().commit();
	    } catch (Exception e) {
	        if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
	        e.printStackTrace();
	    }
	}

	public void procesarSolicitud(Cocinero usuario) {
	    try {
	        em.getTransaction().begin();
	        em.persist(usuario);
	        em.getTransaction().commit();
	    } catch (Exception e) {
	        if (em.getTransaction().isActive()) {
	            em.getTransaction().rollback();
	        }
	        e.printStackTrace();
	    }
	}
}
