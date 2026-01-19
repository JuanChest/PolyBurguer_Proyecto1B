package controlador;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import modelo.dao.CocineroDAO;
import modelo.entidades.Cocinero;
import modelo.entidades.EstadoUsuario;

@WebServlet("/CuentasController")
public class CuentasController extends HttpServlet {

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
		String ruta = (req.getParameter("ruta") == null) ? "listarCuentas" : req.getParameter("ruta");

		switch (ruta) {
			case "listarCuentas":
				this.listarCuentas(req, resp);
				break;
			case "crearUsuario":
				this.crearUsuario(req, resp);
				break;
			case "desactivarCuenta":
				this.desactivarUsuario(req, resp);
				break;
			case "mostrarFormulario":
			    req.getRequestDispatcher("jsp/FormularioCuentas.jsp").forward(req, resp);
			    break;
			default:
				this.listarCuentas(req, resp);
				break;
		}
	}

	private void listarCuentas(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		CocineroDAO dao = new CocineroDAO();
		List<Cocinero> cuentas = dao.obtenerCuentas();

		req.setAttribute("cuentas", cuentas);
		req.getRequestDispatcher("jsp/PanelGestionarCuentas.jsp").forward(req, resp);
	}

	private void crearUsuario(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	    String nombre = req.getParameter("nombre");
	    String apellido = req.getParameter("apellido");
	    String cedula = req.getParameter("cedula");
	    String contrasenia = req.getParameter("contrasenia");

	    Cocinero nuevoCocinero = new Cocinero(nombre, apellido, cedula, contrasenia);
	    CocineroDAO dao = new CocineroDAO();

	    if (dao.validarUsuario(nuevoCocinero)) {
	        try {
	            dao.procesarSolicitud(nuevoCocinero);
	            System.out.println("DEBUG: Guardado exitoso para " + nombre);
	            resp.sendRedirect(req.getContextPath() + "/CuentasController?ruta=listarCuentas");
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    } else {
	        System.out.println("DEBUG: La cédula " + cedula + " ya está registrada.");

	        req.setAttribute("mensajeError", "Error: La cédula " + cedula + " ya se encuentra registrada.");

	        req.setAttribute("nombreAnterior", nombre);
	        req.setAttribute("apellidoAnterior", apellido);

	        req.getRequestDispatcher("jsp/FormularioCuentas.jsp").forward(req, resp);
	    }
	}

	private void desactivarUsuario(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	    int id = Integer.parseInt(req.getParameter("id"));
	    boolean estadoBooleano = Boolean.parseBoolean(req.getParameter("estado"));

	    CocineroDAO dao = new CocineroDAO();
	    Cocinero c = dao.buscarPorId(id);

	    if (c != null) {
	        EstadoUsuario nuevoEstadoEnum = estadoBooleano ? EstadoUsuario.ACTIVO : EstadoUsuario.INACTIVO;

	        c.setEstado(nuevoEstadoEnum);
	        dao.cambiarEstado(c);
	        resp.getWriter().print("OK");
	    } else {
	        resp.getWriter().print("ERROR");
	    }
	}
}