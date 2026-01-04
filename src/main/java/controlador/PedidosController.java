package controlador;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import modelo.dao.PedidoDAO;
import modelo.entidades.Pedido;

@WebServlet("/PedidosController")
public class PedidosController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.ruteador(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.ruteador(req, resp);
	}

	private void ruteador(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String ruta = (req.getParameter("ruta") == null) ? "listar" : req.getParameter("ruta");

		switch (ruta) {
			case "listar":
				this.listar(req, resp);
				break;
			case "seleccionarPedido":
				this.seleccionarPedido(req, resp);
				break;
			case "mostrarMenu":
				this.mostrarMenu(req, resp);
				break;
		}
	}

	private void listar(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 1.- Obtener los parámetros
		// 2.- Hablar con el modelo
		PedidoDAO pedidoDAO = new PedidoDAO();
		List<Pedido> pedidos = pedidoDAO.obtenerPedidos();
		// 3.- Llamar a la vista
		req.setAttribute("pedido", pedidos);
		req.getRequestDispatcher("jsp/PanelBandejaCocinero.jsp").forward(req, resp);
	}

	private void seleccionarPedido(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String id = req.getParameter("idPedido");
		PedidoDAO dao = new PedidoDAO();
		Pedido p = dao.obtenerPedido(id);
		if (p != null) {
			if (p.getEstadoPedido() == modelo.entidades.EstadoPedido.PENDIENTE) {
				p.setEstadoPedido(modelo.entidades.EstadoPedido.EN_PREPARACION);
			} else if (p.getEstadoPedido() == modelo.entidades.EstadoPedido.EN_PREPARACION) {
				p.setEstadoPedido(modelo.entidades.EstadoPedido.LISTO);
			}

			dao.actualizarEstado(p);
		}
		resp.sendRedirect("PedidosController?ruta=listar");
	}

	private void cargarMenu(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 1.- Obtener los parámetros
		// 2.- Hablar con el modelo
		// 3.- Llamar a la vista
	}

	private void seleccionarPlato(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// 1.- Obtener los parámetros
		// 2.- Hablar con el modelo
		// 3.- Llamar a la vista
	}

	private void confimarPedido(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 1.- Obtener los parámetros
		// 2.- Hablar con el modelo
		// 3.- Llamar a la vista
	}

	private void mostrarMenu(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 1.- Obtener los parámetros (no hay parámetros necesarios)
		// 2.- Hablar con el modelo
		modelo.dao.PlatoMenuDAO platoDAO = new modelo.dao.PlatoMenuDAO();
		List<modelo.entidades.PlatoMenu> platos = platoDAO.obtenerPlatos();

		// Filtrar solo platos disponibles
		List<modelo.entidades.PlatoMenu> platosDisponibles = platos.stream()
				.filter(modelo.entidades.PlatoMenu::isDisponible)
				.collect(java.util.stream.Collectors.toList());

		// 3.- Llamar a la vista
		req.setAttribute("platos", platosDisponibles);
		req.getRequestDispatcher("jsp/PanelMenu.jsp").forward(req, resp);
	}

}
