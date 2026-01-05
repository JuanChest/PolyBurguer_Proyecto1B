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
				this.mostrarMenu(req, resp);
				break;
			case "seleccionarPlato":
				this.seleccionarPlato(req, resp);
				break;
			case "cargarMenu":
				this.cargarMenu(req, resp);
				break;
			case "incrementarCantidad":
				this.incrementarCantidad(req, resp);
				break;
			case "decrementarCantidad":
				this.decrementarCantidad(req, resp);
				break;
			case "eliminarDelCarrito":
				this.eliminarDelCarrito(req, resp);
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

	private void seleccionarPlato(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// 1. Obtener parámetros
		int idPlato = Integer.parseInt(req.getParameter("idPlato"));

		// 2. Obtener plato del DAO
		modelo.dao.PlatoMenuDAO platoDAO = new modelo.dao.PlatoMenuDAO();
		modelo.entidades.PlatoMenu plato = platoDAO.obtenerPlato(idPlato);

		// 3. Obtener o crear carrito en sesión
		HttpSession session = req.getSession();
		List<modelo.entidades.DetallePedido> carrito = (List<modelo.entidades.DetallePedido>) session
				.getAttribute("carrito");

		if (carrito == null) {
			carrito = new ArrayList<>();
		}

		// 4. Verificar si el plato ya está en el carrito
		boolean platoExiste = false;
		for (modelo.entidades.DetallePedido detalle : carrito) {
			if (detalle.getPlatoMenu().getIdPlatoMenu() == idPlato) {
				detalle.setCantidad(detalle.getCantidad() + 1);
				detalle.setSubtotal(detalle.getCantidad() * plato.getPrecio());
				platoExiste = true;
				break;
			}
		}

		// 5. Si no existe, crear nuevo DetallePedido
		if (!platoExiste) {
			modelo.entidades.DetallePedido nuevoDetalle = new modelo.entidades.DetallePedido();
			nuevoDetalle.setPlatoMenu(plato);
			nuevoDetalle.setCantidad(1);
			nuevoDetalle.setSubtotal(plato.getPrecio());
			carrito.add(nuevoDetalle);
		}

		// 6. Guardar carrito en sesión
		session.setAttribute("carrito", carrito);

		// 7. Redirigir al menú
		resp.sendRedirect("PedidosController?ruta=mostrarMenu");
	}

	private void cargarMenu(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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

	private void incrementarCantidad(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		int idPlato = Integer.parseInt(req.getParameter("idPlato"));
		HttpSession session = req.getSession();
		List<modelo.entidades.DetallePedido> carrito = (List<modelo.entidades.DetallePedido>) session
				.getAttribute("carrito");

		for (modelo.entidades.DetallePedido detalle : carrito) {
			if (detalle.getPlatoMenu().getIdPlatoMenu() == idPlato) {
				detalle.setCantidad(detalle.getCantidad() + 1);
				detalle.setSubtotal(detalle.getCantidad() * detalle.getPlatoMenu().getPrecio());
				break;
			}
		}

		session.setAttribute("carrito", carrito);
		resp.sendRedirect("PedidosController?ruta=cargarMenu");
	}

	private void decrementarCantidad(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		int idPlato = Integer.parseInt(req.getParameter("idPlato"));
		HttpSession session = req.getSession();
		List<modelo.entidades.DetallePedido> carrito = (List<modelo.entidades.DetallePedido>) session
				.getAttribute("carrito");

		Iterator<modelo.entidades.DetallePedido> iterator = carrito.iterator();
		while (iterator.hasNext()) {
			modelo.entidades.DetallePedido detalle = iterator.next();
			if (detalle.getPlatoMenu().getIdPlatoMenu() == idPlato) {
				if (detalle.getCantidad() > 1) {
					detalle.setCantidad(detalle.getCantidad() - 1);
					detalle.setSubtotal(detalle.getCantidad() * detalle.getPlatoMenu().getPrecio());
				} else {
					iterator.remove();
				}
				break;
			}
		}

		session.setAttribute("carrito", carrito);
		resp.sendRedirect("PedidosController?ruta=cargarMenu");
	}

	private void eliminarDelCarrito(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		int idPlato = Integer.parseInt(req.getParameter("idPlato"));
		HttpSession session = req.getSession();
		List<modelo.entidades.DetallePedido> carrito = (List<modelo.entidades.DetallePedido>) session
				.getAttribute("carrito");

		carrito.removeIf(detalle -> detalle.getPlatoMenu().getIdPlatoMenu() == idPlato);

		session.setAttribute("carrito", carrito);
		resp.sendRedirect("PedidosController?ruta=cargarMenu");
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

		// 3. Generar número de pedido
		modelo.dao.PedidoDAO pedidoDAO = new modelo.dao.PedidoDAO();
		int ultimoNumero = pedidoDAO.obtenerUltimoNumeroPedido();
		String nroPedido = modelo.entidades.Pedido.generarNroPedido(ultimoNumero);
		nuevoPedido.setNroPedido(nroPedido);

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
