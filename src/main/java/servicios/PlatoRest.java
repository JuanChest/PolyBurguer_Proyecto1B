package servicios;

import java.util.List;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import modelo.dao.PlatoMenuDAO;
import modelo.entidades.PlatoMenu;

@Path("/platos")
public class PlatoRest {

    private PlatoMenuDAO dao = new PlatoMenuDAO();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<PlatoMenu> listar() {
        return dao.obtenerPlatos();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public PlatoMenu buscar(@PathParam("id") int id) {
        return dao.obtenerPlato(id);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON) 
    @Produces(MediaType.APPLICATION_JSON)
    public boolean crear(PlatoMenu nuevo) {
        return dao.guardarNuevoPlato(nuevo);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public boolean actualizar(PlatoMenu modificado) {
        return dao.guardarCambiosPlato(modificado);
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public boolean eliminar(@PathParam("id") int id) {
        PlatoMenu plato = new PlatoMenu();
        plato.setIdPlatoMenu(id);
        return dao.eliminarPlatoNoComercializable(plato);
    }
    
}