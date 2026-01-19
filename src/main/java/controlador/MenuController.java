package controlador;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import modelo.dao.PlatoMenuDAO;
import modelo.entidades.Categoria;
import modelo.entidades.PlatoMenu;

@WebServlet("/MenuController")
public class MenuController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private PlatoMenuDAO platoMenuDAO;

	@Override
	public void init() throws ServletException {
		super.init();
		this.platoMenuDAO = new PlatoMenuDAO();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.ruteador(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.ruteador(req, resp);
	}

	/**
	 * Ruteador de acciones según el parámetro 'ruta'
	 */
	private void ruteador(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String ruta = (req.getParameter("ruta") == null) ? "gestionarDisponibilidad" : req.getParameter("ruta");

		switch (ruta) {
			case "gestionarDisponibilidad":
				this.accederGestionDisponibilidadPlato(req, resp);
				break;
			case "formularioPlato":
				this.formularioModificarPlato(req, resp);
				break;
			case "gestionarMenu":
				this.accederModuloGestionMenu(req, resp);
				break;
			case "cambiarDisponibilidad":
				this.cambiarDisponibilidadPlato(req, resp);
				break;

			// CU: Gestionar Platos del Menú (Administrador)
			case "gestionarPlatosMenu":
				this.accederModuloGestionMenu(req, resp);
				break;
			case "crearPlato":
				this.crearPlato(req, resp);
				break;
			case "formularioModificar":
				this.formularioModificarPlato(req, resp);
				break;
			case "modificarPlato":
				this.modificarPlato(req, resp);
				break;
			case "eliminarPlato":
				this.eliminarPlato(req, resp);
				break;
			case "verMenu":
				this.verMenu(req, resp);
				break;
			default:
				this.accederGestionDisponibilidadPlato(req, resp);
				break;
		}
	}

	private void accederGestionDisponibilidadPlato(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		try {
			List<PlatoMenu> platos = platoMenuDAO.obtenerPlatos();
			req.setAttribute("platos", platos);
			req.getRequestDispatcher("/jsp/PanelGestionarDisponibilidad.jsp").forward(req, resp);
		} catch (Exception e) {
			e.printStackTrace();
			resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al cargar los platos");
		}
	}

	private void cambiarDisponibilidadPlato(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		try {
			String idPlatoStr = req.getParameter("idPlato");
			String nuevoEstadoStr = req.getParameter("disponible");

			if (idPlatoStr == null || idPlatoStr.isEmpty() || nuevoEstadoStr == null) {
				resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Parámetros inválidos");
				return;
			}

			int idPlato = Integer.parseInt(idPlatoStr);
			boolean nuevoEstado = Boolean.parseBoolean(nuevoEstadoStr);

			PlatoMenu plato = platoMenuDAO.obtenerPlato(idPlato);
			if (plato == null) {
				resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Plato no encontrado");
				return;
			}

			boolean actualizado = platoMenuDAO.actualizarEstado(plato, nuevoEstado);
			if (actualizado) {
				resp.sendRedirect(req.getContextPath() + "/MenuController?ruta=gestionarDisponibilidad");
			} else {
				resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
						"Error al actualizar la disponibilidad del plato");
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID de plato inválido");
		} catch (Exception e) {
			e.printStackTrace();
			resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error procesando la solicitud");
		}
	}

	private void accederModuloGestionMenu(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		try {
			List<PlatoMenu> platos = platoMenuDAO.obtenerPlatos();
			req.setAttribute("platos", platos);
			req.setAttribute("mensaje", "");
			req.getRequestDispatcher("/jsp/panelGestionarPlatosMenu.jsp").forward(req, resp);
		} catch (Exception e) {
			e.printStackTrace();
			resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al acceder al módulo de gestión");
		}
	}

	private void crearPlato(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		try {
			String nombre = req.getParameter("nombre") != null ? req.getParameter("nombre").trim() : "";
			String descripcion = req.getParameter("descripcion") != null ? req.getParameter("descripcion").trim() : "";
			String precioStr = req.getParameter("precio") != null ? req.getParameter("precio").trim() : "0";
			String categoriaStr = req.getParameter("categoria") != null ? req.getParameter("categoria") : "HAMBURGUESA";

			if (nombre.isEmpty() || descripcion.isEmpty()) {
				setErrorYRecargar(req, resp, "El nombre y descripción son obligatorios");
				return;
			}

			double precio = Double.parseDouble(precioStr);
			if (precio <= 0) {
				setErrorYRecargar(req, resp, "El precio debe ser mayor a 0");
				return;
			}

			PlatoMenu nuevoPlato = new PlatoMenu();
			nuevoPlato.setNombre(nombre);
			nuevoPlato.setDescripcion(descripcion);
			nuevoPlato.setPrecio(precio);
			nuevoPlato.setCategoria(Categoria.valueOf(categoriaStr));
			nuevoPlato.setDisponible(true);
			String imagen = req.getParameter("imagen") != null && !req.getParameter("imagen").isEmpty()
					? req.getParameter("imagen")
					: "plato.jpg";
			nuevoPlato.setImagen(imagen);

			boolean guardado = platoMenuDAO.guardarNuevoPlato(nuevoPlato);

			if (guardado) {
				List<PlatoMenu> platos = platoMenuDAO.obtenerPlatos();
				req.setAttribute("platos", platos);
				req.setAttribute("mensaje", "Plato creado exitosamente");
				req.getRequestDispatcher("/jsp/panelGestionarPlatosMenu.jsp").forward(req, resp);
			} else {
				setErrorYRecargar(req, resp, "Error al guardar el plato");
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
			setErrorYRecargar(req, resp, "Formato de precio inválido");
		} catch (Exception e) {
			e.printStackTrace();
			setErrorYRecargar(req, resp, "Error al crear el plato: " + e.getMessage());
		}
	}

	private void formularioModificarPlato(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		try {
			String idPlatoStr = req.getParameter("idPlato");
			if (idPlatoStr == null || idPlatoStr.isEmpty()) {
				resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID de plato no proporcionado");
				return;
			}

			int idPlato = Integer.parseInt(idPlatoStr);
			PlatoMenu plato = platoMenuDAO.obtenerPlato(idPlato);

			if (plato == null) {
				resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Plato no encontrado");
				return;
			}

			// Asegurar que la vista también reciba la lista de platos para mostrar la
			// grilla
			List<PlatoMenu> platos = platoMenuDAO.obtenerPlatos();
			req.setAttribute("platos", platos);
			req.setAttribute("plato", plato);
			req.getRequestDispatcher("/jsp/panelGestionarPlatosMenu.jsp").forward(req, resp);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID de plato inválido");
		} catch (Exception e) {
			e.printStackTrace();
			resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al cargar el formulario");
		}
	}

	private void modificarPlato(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		try {
			String idPlatoStr = req.getParameter("idPlato");
			if (idPlatoStr == null || idPlatoStr.isEmpty()) {
				setErrorYRecargar(req, resp, "ID de plato no proporcionado");
				return;
			}

			int idPlato = Integer.parseInt(idPlatoStr);
			PlatoMenu plato = platoMenuDAO.obtenerPlato(idPlato);

			if (plato == null) {
				setErrorYRecargar(req, resp, "Plato no encontrado");
				return;
			}

			String nombre = req.getParameter("nombre") != null ? req.getParameter("nombre").trim() : plato.getNombre();
			String descripcion = req.getParameter("descripcion") != null ? req.getParameter("descripcion").trim()
					: plato.getDescripcion();
			String precioStr = req.getParameter("precio") != null ? req.getParameter("precio").trim()
					: String.valueOf(plato.getPrecio());
			String categoriaStr = req.getParameter("categoria") != null ? req.getParameter("categoria")
					: plato.getCategoria().name();
			String disponibleStr = req.getParameter("disponible");

			if (nombre.isEmpty() || descripcion.isEmpty()) {
				setErrorYRecargar(req, resp, "El nombre y descripción son obligatorios");
				return;
			}

			double precio = Double.parseDouble(precioStr);
			if (precio <= 0) {
				setErrorYRecargar(req, resp, "El precio debe ser mayor a 0");
				return;
			}

			plato.setNombre(nombre);
			plato.setDescripcion(descripcion);
			plato.setPrecio(precio);
			plato.setCategoria(Categoria.valueOf(categoriaStr));
			plato.setDisponible(disponibleStr != null);
			String imagenMod = req.getParameter("imagen") != null && !req.getParameter("imagen").isEmpty()
					? req.getParameter("imagen")
					: plato.getImagen();
			plato.setImagen(imagenMod);

			boolean actualizado = platoMenuDAO.guardarCambiosPlato(plato);

			if (actualizado) {
				List<PlatoMenu> platos = platoMenuDAO.obtenerPlatos();
				req.setAttribute("platos", platos);
				req.setAttribute("mensaje", "Plato modificado exitosamente");
				req.getRequestDispatcher("/jsp/panelGestionarPlatosMenu.jsp").forward(req, resp);
			} else {
				setErrorYRecargar(req, resp, "Error al actualizar el plato");
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
			setErrorYRecargar(req, resp, "Formato de datos inválido");
		} catch (Exception e) {
			e.printStackTrace();
			setErrorYRecargar(req, resp, "Error al modificar el plato: " + e.getMessage());
		}
	}

	private void eliminarPlato(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		try {
			String idPlatoStr = req.getParameter("idPlato");
			if (idPlatoStr == null || idPlatoStr.isEmpty()) {
				setErrorYRecargar(req, resp, "ID de plato no proporcionado");
				return;
			}

			int idPlato = Integer.parseInt(idPlatoStr);
			PlatoMenu plato = platoMenuDAO.obtenerPlato(idPlato);

			if (plato == null) {
				setErrorYRecargar(req, resp, "Plato no encontrado");
				return;
			}

			boolean eliminado = platoMenuDAO.eliminarPlatoNoComercializable(plato);

			if (eliminado) {
				List<PlatoMenu> platos = platoMenuDAO.obtenerPlatos();
				req.setAttribute("platos", platos);
				req.setAttribute("mensaje", "Plato eliminado exitosamente");
				req.getRequestDispatcher("/jsp/panelGestionarPlatosMenu.jsp").forward(req, resp);
			} else {
				setErrorYRecargar(req, resp, "Error al eliminar el plato");
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
			setErrorYRecargar(req, resp, "ID de plato inválido");
		} catch (Exception e) {
			e.printStackTrace();
			setErrorYRecargar(req, resp, "Error al eliminar el plato: " + e.getMessage());
		}
	}

	private void verMenu(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		try {
			List<PlatoMenu> platos = platoMenuDAO.obtenerPlatos();
			req.setAttribute("platos", platos);
			req.getRequestDispatcher("/jsp/PanelMenu.jsp").forward(req, resp);
		} catch (Exception e) {
			e.printStackTrace();
			resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al cargar el menú");
		}
	}

	private void setErrorYRecargar(HttpServletRequest req, HttpServletResponse resp, String mensaje)
			throws ServletException, IOException {
		try {
			List<PlatoMenu> platos = platoMenuDAO.obtenerPlatos();
			req.setAttribute("platos", platos);
			req.setAttribute("error", mensaje);
			req.getRequestDispatcher("/jsp/panelGestionarPlatosMenu.jsp").forward(req, resp);
		} catch (Exception e) {
			resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, mensaje);
		}
	}
}
