package conexion;

import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBDD {
	private static ConexionBDD conexion = null;
	
	
	public ConexionBDD() {
		String servidor = "localhost:3306";
		String database = "poliburguer";
		String usuario = "root";
		String contrasenia = "root";
		
		String url = "jdbc:mysql://" + servidor + "/" + database;
		
		try {
			DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	}
	
	public static ConexionBDD obtenerConexion() {
		if(conexion == null)
			new ConexionBDD();
		return conexion;
	}
	
	public static void cerrarConexion() {
		if(conexion!=null) {
			conexion.close();
		}
	}

}
