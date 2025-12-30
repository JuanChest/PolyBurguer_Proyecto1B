package modelo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import conexion.ConexionBDD;

/**
 * DAO para gestionar operaciones con la tabla plato
 */
public class PlatoMenuDAO {

    private static final String SQL_SELECT_ACTIVOS = "SELECT * FROM plato WHERE estado = 1";
    private static final String SQL_SELECT_BY_ID = "SELECT * FROM plato WHERE id_plato = ?";

    /**
     * Obtiene todos los platos activos del menú
     * 
     * @return Lista de platos activos
     */
    public List<PlatoMenu> obtenerPlatosActivos() {
        List<PlatoMenu> platos = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = ConexionBDD.obtenerConexion().prepareStatement(SQL_SELECT_ACTIVOS);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                PlatoMenu plato = new PlatoMenu(
                        rs.getInt("id_plato"),
                        rs.getString("nombre_plato"),
                        rs.getDouble("precio"),
                        rs.getString("descripcion"),
                        rs.getString("imagen"),
                        rs.getBoolean("estado"));
                platos.add(plato);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener platos activos: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (rs != null)
                ConexionBDD.cerrar(rs);
            if (pstmt != null)
                ConexionBDD.cerrar(pstmt);
        }

        return platos;
    }

    /**
     * Obtiene un plato específico por su ID
     * 
     * @param idPlato ID del plato a buscar
     * @return PlatoMenu encontrado o null si no existe
     */
    public PlatoMenu obtenerPlatoPorId(int idPlato) {
        PlatoMenu plato = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = ConexionBDD.obtenerConexion().prepareStatement(SQL_SELECT_BY_ID);
            pstmt.setInt(1, idPlato);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                plato = new PlatoMenu(
                        rs.getInt("id_plato"),
                        rs.getString("nombre_plato"),
                        rs.getDouble("precio"),
                        rs.getString("descripcion"),
                        rs.getString("imagen"),
                        rs.getBoolean("estado"));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener plato por ID: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (rs != null)
                ConexionBDD.cerrar(rs);
            if (pstmt != null)
                ConexionBDD.cerrar(pstmt);
        }

        return plato;
    }
}
