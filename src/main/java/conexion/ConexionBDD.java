package conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConexionBDD {
	private static Connection cnn = null;
	
	
	public ConexionBDD() {
		String servidor = "localhost:3306";
		String database = "poliburguer";
		String usuario = "root";
		String contrasenia = "";
		
		String url = "jdbc:mysql://" + servidor + "/" + database;
		
		try {
			DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
			cnn = DriverManager.getConnection(url, usuario, contrasenia);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	}
	
	public static Connection obtenerConexion() {
		if(cnn == null)
			new ConexionBDD();
		return cnn;
	}
	
	public static void cerrar(ResultSet rs) {
		try {
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void cerrar(PreparedStatement pstmt) {
		try {
			pstmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/*
	 * Cerrar la conexión 
	 * */
	public static void cerrarConexion() {
		if(cnn!=null) {
			try {
				cnn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
