package controladores;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import modelo.PlatoMenu;
import modelo.PlatoMenuDAO;

/**
 * Servlet controlador para gestionar pedidos y visualización del menú
 */
@WebServlet("/menu")
public class PedidosController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private PlatoMenuDAO platoDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        platoDAO = new PlatoMenuDAO();
    }

    /**
     * Maneja peticiones GET para mostrar el menú
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // Obtener lista de platos activos desde la base de datos
            List<PlatoMenu> platos = platoDAO.obtenerPlatosActivos();

            // Almacenar la lista en el request para que esté disponible en el JSP
            request.setAttribute("platos", platos);

            // Redirigir a la vista JSP
            RequestDispatcher dispatcher = request.getRequestDispatcher("/vista/index.jsp");
            dispatcher.forward(request, response);

        } catch (Exception e) {
            System.err.println("Error al cargar el menú: " + e.getMessage());
            e.printStackTrace();

            // En caso de error, enviar mensaje al usuario
            request.setAttribute("error", "Error al cargar el menú. Por favor, intente más tarde.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/vista/index.jsp");
            dispatcher.forward(request, response);
        }
    }
}
