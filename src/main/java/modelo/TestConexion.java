package modelo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import conexion.ConexionBDD;

public class TestConexion {
	public static void main(String args[]) {
		String _SQL_GET_ALL_ = "SELECT * FROM plato;";
		try {
			PreparedStatement pstmt = ConexionBDD.obtenerConexion().prepareStatement(_SQL_GET_ALL_);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				System.out.println(rs.getInt("id_plato") + " " + rs.getString("nombre_plato") + " "
						+ rs.getDouble("precio") + " " + rs.getString("descripcion") + " " + rs.getString("imagen")
						+ " " + rs.getBoolean("estado"));
			}
			ConexionBDD.cerrar(rs);
			ConexionBDD.cerrar(pstmt);
			ConexionBDD.cerrarConexion();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
