package controlador;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
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
				this.solicitarMenu(req, resp);
				break;
			case "agregarPlato":
				this.agregarPlato(req, resp);
				break;
			case "mostrarResumenPedido":
				this.mostrarResumenPedido(req, resp);
				break;
			case "modificarCantidad":
				this.modificarCantidad(req, resp);
				break;
			case "eliminarPlato":
				this.eliminarPlato(req, resp);
				break;
			case "confirmarPedido":
				this.confirmarPedido(req, resp);
				break;
		}
	}

	private void listar(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 1.- Obtener los parámetros
		// 2.- Hablar con el modelo
		PedidoDAO dao = new PedidoDAO();
		List<Pedido> pedidos = dao.obtenerPedidos();
		// 3.- Llamar a la vista
		req.setAttribute("pedido", pedidos);
		req.getRequestDispatcher("jsp/PanelBandejaCocinero.jsp").forward(req, resp);
	}

	private void seleccionarPedido(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String id = req.getParameter("idPedido");
		PedidoDAO dao = new PedidoDAO();
		Pedido p = dao.obtenerPedido(Integer.parseInt(id));
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

	private void solicitarMenu(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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

	private void agregarPlato(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		int idPlato = Integer.parseInt(req.getParameter("idPlato"));

		// 1. Verificar disponibilidad del plato
		modelo.dao.PlatoMenuDAO platoDAO = new modelo.dao.PlatoMenuDAO();
		if (!platoDAO.verificarDisponibilidad(idPlato)) {
			// Flujo alterno 4.1: Inventario insuficiente
			HttpSession session = req.getSession();
			session.setAttribute("mensaje", "Lo sentimos, el plato solicitado no está disponible en este momento");
			session.setAttribute("tipoMensaje", "error");
			resp.sendRedirect("PedidosController?ruta=mostrarMenu");
			return;
		}

		// 2. Obtener plato
		modelo.entidades.PlatoMenu plato = platoDAO.obtenerPlato(idPlato);

		// 3. Crear DetallePedido
		modelo.entidades.DetallePedido nuevoDetalle = new modelo.entidades.DetallePedido();
		nuevoDetalle.setPlatoMenu(plato);
		nuevoDetalle.setCantidad(1);
		nuevoDetalle.calcularSubtotal(); // DetallePed do calcula su subtotal

		// 4. Obtener o crear Pedido en sesión
		HttpSession session = req.getSession();
		modelo.entidades.Pedido pedidoActual = (modelo.entidades.Pedido) session.getAttribute("pedidoEnCurso");

		if (pedidoActual == null) {
			pedidoActual = new modelo.entidades.Pedido(); // Se crea desde el primer item
		}

		// 5. Agregar detalle al pedido (encapsulado - Pedido maneja su lógica)
		pedidoActual.agregarDetalle(nuevoDetalle); // agregarDetalle() ya llama calcularTotal()

		// 6. Guardar pedido en sesión
		session.setAttribute("pedidoEnCurso", pedidoActual);
		resp.sendRedirect("PedidosController?ruta=mostrarMenu");
	}

	private void mostrarResumenPedido(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		HttpSession session = req.getSession();
		modelo.entidades.Pedido pedidoActual = (modelo.entidades.Pedido) session.getAttribute("pedidoEnCurso");

		if (pedidoActual == null || pedidoActual.estaVacio()) {
			pedidoActual = new modelo.entidades.Pedido(); // Crear pedido vacío para evitar nulls
		}

		// Pedido ya tiene su total calculado - no necesitamos calcular manualmente
		req.setAttribute("pedido", pedidoActual);
		req.getRequestDispatcher("jsp/PanelPedido.jsp").forward(req, resp);
	}

	private void modificarCantidad(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String accion = req.getParameter("accion");
		int idPlato = Integer.parseInt(req.getParameter("idPlato"));

		HttpSession session = req.getSession();
		modelo.entidades.Pedido pedidoActual = (modelo.entidades.Pedido) session.getAttribute("pedidoEnCurso");

		if (pedidoActual == null) {
			resp.sendRedirect("PedidosController?ruta=mostrarMenu");
			return;
		}

		// Calcular nueva cantidad
		int nuevaCantidad = 0;
		for (modelo.entidades.DetallePedido detalle : pedidoActual.getDetalles()) {
			if (detalle.getPlatoMenu().getIdPlatoMenu() == idPlato) {
				nuevaCantidad = detalle.getCantidad();
				break;
			}
		}

		if ("incrementar".equals(accion)) {
			nuevaCantidad++;
		} else if ("decrementar".equals(accion)) {
			nuevaCantidad--;
		}

		// Pedido maneja la modificación y recalcula total
		pedidoActual.modificarCantidadDetalle(idPlato, nuevaCantidad);

		session.setAttribute("pedidoEnCurso", pedidoActual);
		resp.sendRedirect("PedidosController?ruta=mostrarResumenPedido");
	}

	private void eliminarPlato(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		int idPlato = Integer.parseInt(req.getParameter("idPlato"));

		HttpSession session = req.getSession();
		modelo.entidades.Pedido pedidoActual = (modelo.entidades.Pedido) session.getAttribute("pedidoEnCurso");

		if (pedidoActual != null) {
			pedidoActual.eliminarDetalle(idPlato); // Encapsulado
			session.setAttribute("pedidoEnCurso", pedidoActual);
		}

		resp.sendRedirect("PedidosController?ruta=mostrarResumenPedido");
	}

	private void confirmarPedido(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		HttpSession session = req.getSession();
		modelo.entidades.Pedido pedidoActual = (modelo.entidades.Pedido) session.getAttribute("pedidoEnCurso");

		if (pedidoActual == null || pedidoActual.estaVacio()) {
			resp.sendRedirect("PedidosController?ruta=mostrarMenu");
			return;
		}

		// Pedido YA tiene todo configurado:
		// - detalles agregados
		// - subtotales calculados
		// - total calculado

		// 1. Generar número de atención
		modelo.dao.PedidoDAO pedidoDAO = new modelo.dao.PedidoDAO();
		int ultimoNumero = pedidoDAO.obtenerUltimoNumeroPedido();
		String nroAtencion = modelo.entidades.Pedido.generarNroAtencion(ultimoNumero);
		pedidoActual.setNroPedido(nroAtencion);

		// 2. Asegurar relaciones bidireccionales
		for (modelo.entidades.DetallePedido detalle : pedidoActual.getDetalles()) {
			detalle.setPedido(pedidoActual);
		}

		// 3. Guardar en BD
		pedidoDAO.guardarPedido(pedidoActual);

		// 4. Limpiar sesión
		session.removeAttribute("pedidoEnCurso");

		// 5. Mostrar confirmación
		req.setAttribute("pedido", pedidoActual);
		req.getRequestDispatcher("jsp/PanelPedidoConfirmado.jsp").forward(req, resp);
	}

}
