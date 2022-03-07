package ar.com.lasegunda.utiles.baseDeDatos;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class FactoryConnection {
//	private String driver = "oracle.jdbc.driver.OracleDriver";
//	private String host="db02test.lasegunda.com.ar";
//	private String port="1521";
//	private String user="ekippes";
//	private String password="ekippes.123";
//	private String db="test.lasegunda.com.ar";
	private Connection conn = null;
	private int cantConn = 0;

	private static FactoryConnection instancia; // para tener solo y exclusivamente un único objeto de una clase.

	private FactoryConnection() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver").getConstructor().newInstance();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static FactoryConnection getinstancia() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException { // devuelve la unica conexion
		if (FactoryConnection.instancia == null) {
			FactoryConnection.instancia = new FactoryConnection();
		}
		return FactoryConnection.instancia;
	}

	public Connection obtenerConexion() throws Exception {
		try {
			if (conn == null || conn.isClosed()) {
				conn = DriverManager.getConnection("jdbc:oracle:thin:@10.5.1.214:1526:desa","automatizacionSQA", "automatizacionSQA.102030");
				//conn = DriverManager.getConnection("jdbc:oracle:thin:@db02test.lasegunda.com.ar:1521:test","OPS$usuario", "clave");
			}
		} catch (Exception e) {
			throw e;
		}
		cantConn++;
		return conn;
	}

	public void cerrarConexion() throws Exception {
		try {
			cantConn--;
			if (cantConn == 0) {
				conn.close();
			}
		} catch (SQLException e) {
			throw e;
		} catch (Exception e1) {
			throw e1;
		}
	}
}