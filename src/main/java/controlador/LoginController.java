package controlador;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import modelo.dao.UsuarioDAO;
import modelo.entidades.Usuario;

@WebServlet("/LoginController")
public class LoginController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        this.ruteador(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        this.ruteador(req, resp);
    }

    private void ruteador(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String ruta = (req.getParameter("ruta") == null) ? "mostrarFormulario" : req.getParameter("ruta");

        switch (ruta) {
            case "mostrarFormulario":
                this.solicitarAcceso(req, resp);
                break;
            case "autenticar":
                this.autenticar(req, resp);
                break;
            case "cerrarSesion":
                this.cerrarSesion(req, resp);
                break;
            default:
                this.solicitarAcceso(req, resp);
                break;
        }
    }

    /**
     * Muestra el formulario de login
     */
    private void solicitarAcceso(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("jsp/Login.jsp").forward(req, resp);
    }

    /**
     * Autentica al usuario y redirige según su rol
     */
    private void autenticar(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // 1. Obtener parámetros
        String cedula = req.getParameter("cedula");
        String contrasenia = req.getParameter("contrasenia");

        // 2. Validar credenciales
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        Usuario usuario = usuarioDAO.validarCredenciales(cedula, contrasenia);

        // Flujo alterno 4.1: Credenciales incorrectas
        if (usuario == null) {
            HttpSession session = req.getSession();
            session.setAttribute("mensaje", "Cédula o contraseña incorrectos");
            session.setAttribute("tipoMensaje", "error");
            resp.sendRedirect("LoginController?ruta=mostrarFormulario");
            return;
        }

        // Flujo alterno 4.2: Cuenta desactivada
        if (!usuarioDAO.esCuentaActiva(usuario)) {
            HttpSession session = req.getSession();
            session.setAttribute("mensaje", "Tu cuenta está desactivada. Contacta al administrador.");
            session.setAttribute("tipoMensaje", "error");
            resp.sendRedirect("LoginController?ruta=mostrarFormulario");
            return;
        }

        // 3. Crear sesión y guardar usuario
        HttpSession session = req.getSession();
        session.setAttribute("usuarioAutenticado", usuario);
        session.setAttribute("nombreUsuario", usuario.getNombre() + " " + usuario.getApellido());

        // 4. Redirigir según tipo de usuario (usando instanceof)
        if (usuario instanceof modelo.entidades.Cocinero) {
            session.setAttribute("rolUsuario", "COCINERO");
            this.redirigirCocinero(req, resp);
        } else if (usuario instanceof modelo.entidades.Administrador) {
            session.setAttribute("rolUsuario", "ADMINISTRADOR");
            this.redirigirAdministrador(req, resp);
        } else {
            // Rol desconocido (no debería ocurrir con la estructura actual)
            session.setAttribute("mensaje", "Rol de usuario no reconocido");
            session.setAttribute("tipoMensaje", "error");
            resp.sendRedirect("LoginController?ruta=mostrarFormulario");
        }
    }

    /**
     * Redirige al panel de cocinero
     */
    private void redirigirCocinero(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("jsp/PanelCocinero.jsp").forward(req, resp);
    }

    /**
     * Redirige al panel de administrador
     */
    private void redirigirAdministrador(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("jsp/PanelAdministrador.jsp").forward(req, resp);
    }

    /**
     * Cierra la sesión del usuario
     */
    private void cerrarSesion(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        resp.sendRedirect(req.getContextPath() + "/PedidosController?ruta=mostrarMenu");
    }
}
