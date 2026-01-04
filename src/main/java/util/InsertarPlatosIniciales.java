package util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import modelo.entidades.Categoria;
import modelo.entidades.PlatoMenu;

/**
 * Clase de utilidad para insertar platos iniciales en la base de datos
 * Ejecutar como aplicación Java standalone
 */
public class InsertarPlatosIniciales {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("PolyBurguer_Proyecto1B");
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            // Plato 1: PoliBurguer Clásica
            PlatoMenu plato1 = new PlatoMenu();
            plato1.setNombre("PoliBurguer Clásica");
            plato1.setDescripcion("Hamburguesa de carne 100% res, lechuga, tomate, cebolla y salsa especial");
            plato1.setPrecio(8.99);
            plato1.setImagen("hamburguesa-clasica.jpg");
            plato1.setDisponible(true);
            plato1.setCategoria(Categoria.HAMBURGUESA);
            em.persist(plato1);

            // Plato 2: PoliBurguer Doble
            PlatoMenu plato2 = new PlatoMenu();
            plato2.setNombre("PoliBurguer Doble");
            plato2.setDescripcion("Doble carne de res, queso cheddar, bacon crujiente y salsa BBQ");
            plato2.setPrecio(12.99);
            plato2.setImagen("hamburguesa-doble.jpg");
            plato2.setDisponible(true);
            plato2.setCategoria(Categoria.HAMBURGUESA);
            em.persist(plato2);

            // Plato 3: Coca Cola
            PlatoMenu plato3 = new PlatoMenu();
            plato3.setNombre("Coca Cola");
            plato3.setDescripcion("Refresco Coca Cola 500ml");
            plato3.setPrecio(2.50);
            plato3.setImagen("coca-cola.jpg");
            plato3.setDisponible(true);
            plato3.setCategoria(Categoria.BEBIDA);
            em.persist(plato3);

            em.getTransaction().commit();

            System.out.println("✅ Se insertaron 3 platos exitosamente:");
            System.out.println("   - " + plato1.getNombre() + " (HAMBURGUESA)");
            System.out.println("   - " + plato2.getNombre() + " (HAMBURGUESA)");
            System.out.println("   - " + plato3.getNombre() + " (BEBIDA)");

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            System.err.println("❌ Error al insertar platos: " + e.getMessage());
            e.printStackTrace();
        } finally {
            em.close();
            emf.close();
        }
    }
}
