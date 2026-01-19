package modelo.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import modelo.entidades.Administrador;
import modelo.entidades.Cocinero;
import modelo.entidades.EstadoUsuario;
import modelo.entidades.Usuario;

public class UsuarioDAO {

    EntityManager em = null;

    public UsuarioDAO() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("PolyBurguer_Proyecto1B");
        this.em = emf.createEntityManager();
    }

    /**
     * Valida las credenciales de un usuario por cédula y contraseña
     *
     * @param cedula      Número de cédula del usuario
     * @param contrasenia Contraseña del usuario
     * @return Usuario autenticado o null si las credenciales son inválidas
     */
    public Usuario validarCredenciales(String cedula, String contrasenia) {
        String jpql = "SELECT u FROM Usuario u WHERE u.cedula = :cedula AND u.contrasenia = :contrasenia";
        TypedQuery<Usuario> query = em.createQuery(jpql, Usuario.class);
        query.setParameter("cedula", cedula);
        query.setParameter("contrasenia", contrasenia);

        try {
            return query.getSingleResult();
        } catch (Exception e) {
            return null; // No se encontró usuario con esas credenciales
        }
    }

    /**
     * Verifica si la cuenta del usuario está activa
     *
     * @param usuario Usuario a verificar
     * @return true si está activo, false en caso contrario
     */
    public boolean esCuentaActiva(Usuario usuario) {
        return usuario != null && usuario.getEstado() == EstadoUsuario.ACTIVO;
    }

    /**
     * Determina el rol del usuario
     *
     * @param usuario Usuario autenticado
     * @return "COCINERO" o "ADMINISTRADOR"
     */
    public String determinarRol(Usuario usuario) {
        if (usuario instanceof Cocinero) {
            return "COCINERO";
        } else if (usuario instanceof Administrador) {
            return "ADMINISTRADOR";
        }
        return "DESCONOCIDO";
    }
}
