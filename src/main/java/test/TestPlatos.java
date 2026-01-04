package test;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import modelo.dao.PlatoMenuDAO;
import modelo.entidades.DetallePedido;
import modelo.entidades.Pedido;
import modelo.entidades.PlatoMenu;

public class TestPlatos {
	public static void main(String[] args) {
        // 1. Instanciar el DAO de PlatoMenu (que ya inicializa el EntityManager)
        PlatoMenuDAO platoDAO = new PlatoMenuDAO();
        
        EntityManager em = Persistence.createEntityManagerFactory("PolyBurguer_Proyecto1B").createEntityManager();

        try {
            em.getTransaction().begin();

            // 2. Crear y persistir un PlatoMenu
            PlatoMenu hamburguesa = new PlatoMenu();
            hamburguesa.setNombre("Burger Especial");
            hamburguesa.setDescripcion("Carne, queso y tocino");
            hamburguesa.setCategoria(modelo.entidades.Categoria.HAMBURGUESA);
            hamburguesa.setPrecio(8.50);
            hamburguesa.setDisponible(true);
            em.persist(hamburguesa);

            // 3. Crear un Pedido
            Pedido nuevoPedido = new Pedido();
            nuevoPedido.setNroPedido("pb1001");
            nuevoPedido.setTotalPedido(17.00); // Total inicial
            nuevoPedido.setEstadoPedido(modelo.entidades.EstadoPedido.PENDIENTE);
            nuevoPedido.setFechaCreacion(new java.util.Date());
            
            // Configura los campos de Pedido aquí...
            em.persist(nuevoPedido);

            // 4. Crear el DetallePedido relacionando los dos anteriores
            DetallePedido detalle = new DetallePedido();
            detalle.setPlatoMenu(hamburguesa);
            detalle.setPedido(nuevoPedido);
            detalle.setCantidad(2);
            detalle.setSubtotal(17.00); // 8.50 * 2
            em.persist(detalle);

            em.getTransaction().commit();
            System.out.println("Datos de prueba insertados con éxito");

        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

}
