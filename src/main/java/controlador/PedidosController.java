package controlador;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
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

		// Verificar disponibilidad del plato
		modelo.dao.PlatoMenuDAO platoDAO = new modelo.dao.PlatoMenuDAO();
		if (!platoDAO.verificarDisponibilidad(idPlato)) {
			// Flujo alterno 4.1: Inventario insuficiente
			HttpSession session = req.getSession();
			session.setAttribute("mensaje", "Lo sentimos, el plato solicitado no está disponible en este momento");
			session.setAttribute("tipoMensaje", "error");
			resp.sendRedirect("PedidosController?ruta=mostrarMenu");
			return;
		}

		modelo.entidades.PlatoMenu plato = platoDAO.obtenerPlato(idPlato);

		// Obtener o crear carrito en sesión
		HttpSession session = req.getSession();
		List<modelo.entidades.DetallePedido> carrito = (List<modelo.entidades.DetallePedido>) session
				.getAttribute("carrito");

		if (carrito == null) {
			carrito = new ArrayList<>();
		}

		// Verificar si el plato ya está en el carrito
		boolean platoExiste = false;
		for (modelo.entidades.DetallePedido detalle : carrito) {
			if (detalle.getPlatoMenu().getIdPlatoMenu() == idPlato) {
				detalle.setCantidad(detalle.getCantidad() + 1);
				detalle.calcularSubtotal();
				platoExiste = true;
				break;
			}
		}

		// Si no existe, crear nuevo DetallePedido
		if (!platoExiste) {
			modelo.entidades.DetallePedido nuevoDetalle = new modelo.entidades.DetallePedido();
			nuevoDetalle.setPlatoMenu(plato);
			nuevoDetalle.setCantidad(1);
			nuevoDetalle.calcularSubtotal();
			carrito.add(nuevoDetalle);
		}

		session.setAttribute("carrito", carrito);
		resp.sendRedirect("PedidosController?ruta=mostrarMenu");
	}

	private void mostrarResumenPedido(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		HttpSession session = req.getSession();
		List<modelo.entidades.DetallePedido> carrito = (List<modelo.entidades.DetallePedido>) session
				.getAttribute("carrito");

		if (carrito == null) {
			carrito = new ArrayList<>();
		}

		// Calcular totales
		double subtotal = 0.0;
		for (modelo.entidades.DetallePedido detalle : carrito) {
			subtotal += detalle.getSubtotal();
		}

		double impuesto = subtotal * 0.15;
		double total = subtotal + impuesto;

		// Enviar datos a la vista
		req.setAttribute("carrito", carrito);
		req.setAttribute("subtotal", subtotal);
		req.setAttribute("impuesto", impuesto);
		req.setAttribute("total", total);

		req.getRequestDispatcher("jsp/PanelPedido.jsp").forward(req, resp);
	}

	private void modificarCantidad(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String accion = req.getParameter("accion");
		int idPlato = Integer.parseInt(req.getParameter("idPlato"));

		HttpSession session = req.getSession();
		List<modelo.entidades.DetallePedido> carrito = (List<modelo.entidades.DetallePedido>) session
				.getAttribute("carrito");

		if ("incrementar".equals(accion)) {
			// Lógica de incrementar
			for (modelo.entidades.DetallePedido detalle : carrito) {
				if (detalle.getPlatoMenu().getIdPlatoMenu() == idPlato) {
					detalle.setCantidad(detalle.getCantidad() + 1);
					detalle.calcularSubtotal();
					break;
				}
			}
		} else if ("decrementar".equals(accion)) {
			// Lógica de decrementar
			Iterator<modelo.entidades.DetallePedido> iterator = carrito.iterator();
			while (iterator.hasNext()) {
				modelo.entidades.DetallePedido detalle = iterator.next();
				if (detalle.getPlatoMenu().getIdPlatoMenu() == idPlato) {
					if (detalle.getCantidad() > 1) {
						detalle.setCantidad(detalle.getCantidad() - 1);
						detalle.calcularSubtotal();
					} else {
						iterator.remove();
					}
					break;
				}
			}
		}

		session.setAttribute("carrito", carrito);
		resp.sendRedirect("PedidosController?ruta=mostrarResumenPedido");
	}

	private void eliminarPlato(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		int idPlato = Integer.parseInt(req.getParameter("idPlato"));
		HttpSession session = req.getSession();
		List<modelo.entidades.DetallePedido> carrito = (List<modelo.entidades.DetallePedido>) session
				.getAttribute("carrito");

		carrito.removeIf(detalle -> detalle.getPlatoMenu().getIdPlatoMenu() == idPlato);

		session.setAttribute("carrito", carrito);
		resp.sendRedirect("PedidosController?ruta=mostrarResumenPedido");
	}

	private void confirmarPedido(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		HttpSession session = req.getSession();
		List<modelo.entidades.DetallePedido> carrito = (List<modelo.entidades.DetallePedido>) session
				.getAttribute("carrito");

		if (carrito == null || carrito.isEmpty()) {
			resp.sendRedirect("PedidosController?ruta=mostrarMenu");
			return;
		}

		// 1. Crear nuevo pedido
		modelo.entidades.Pedido nuevoPedido = new modelo.entidades.Pedido();
		nuevoPedido.setEstadoPedido(modelo.entidades.EstadoPedido.PENDIENTE);
		nuevoPedido.setFechaCreacion(new Date());

		// 2. Calcular total usando método de negocio
		double total = nuevoPedido.calcularTotal(carrito);
		nuevoPedido.setTotalPedido(total);

		// 3. Generar número de atención
		modelo.dao.PedidoDAO pedidoDAO = new modelo.dao.PedidoDAO();
		int ultimoNumero = pedidoDAO.obtenerUltimoNumeroPedido();
		String nroAtencion = modelo.entidades.Pedido.generarNroAtencion(ultimoNumero);
		nuevoPedido.setNroPedido(nroAtencion);

		// 4. Asociar detalles al pedido
		for (modelo.entidades.DetallePedido detalle : carrito) {
			detalle.setPedido(nuevoPedido);
		}
		nuevoPedido.setDetalles(carrito);

		// 5. Guardar en base de datos
		pedidoDAO.guardarPedido(nuevoPedido);

		// 6. Limpiar carrito de la sesión
		session.removeAttribute("carrito");

		// 7. Enviar pedido a la vista de confirmación
		req.setAttribute("pedido", nuevoPedido);
		req.getRequestDispatcher("jsp/PanelPedidoConfirmado.jsp").forward(req, resp);
	}

}
