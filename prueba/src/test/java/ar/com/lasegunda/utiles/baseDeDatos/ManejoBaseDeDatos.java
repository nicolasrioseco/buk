package ar.com.lasegunda.utiles.baseDeDatos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("cucumber-glue")
public class ManejoBaseDeDatos {

    protected static PreparedStatement pstm = null;
	protected static ResultSet rs = null;
    protected Logger log = LoggerFactory.getLogger(ManejoBaseDeDatos.class);

    /*------------------------------------------------------------------------------------------------
    |  Método: obtenerTodosLosRegistrosDeUnaTablaDeLaBaseDeDatos(String tabla)
    |
    |  Autor: Emmanuel Kippes
    |
    |  Definición:  Devuelve todos los registros de una tabla de la base de datos.
    |
    |  Parámetros:
    |	String tabla -- Tabla de la base de datos sobre la que queremos todos los registros 
    |
    |  Retorna:
    |	String ResultSet -- Todos los registros de la tabla
    *------------------------------------------------------------------------------------------------*/
	public ResultSet obtenerTodosLosRegistrosDeUnaTablaDeLaBaseDeDatos(String consulta) throws SQLException, Exception {
		pstm = FactoryConnection.getinstancia().obtenerConexion().prepareStatement(consulta);
		rs = pstm.executeQuery();
		return rs;
	}
	

	/*------------------------------------------------------------------------------------------------
    |  Método: cerrarConexion()
    |
    |  Autor: Emmanuel Kippes
    |
    |  Definición:  Cerrar conexion con la base de datos
    |
    |  Parámetros:
    |
    |  Retorna:
    *------------------------------------------------------------------------------------------------*/
	public void cerrarConexion() throws InstantiationException, IllegalAccessException, Exception{
		if(rs!=null)rs.close();
		if(pstm!=null)pstm.close();
		FactoryConnection.getinstancia().cerrarConexion();
	}
	
	
	/*------------------------------------------------------------------------------------------------
    |  Método: List<Map<String,String>> convertirResultSetAListadoDeMapas(ResultSet resultado)
    |
    |  Autor: Emmanuel Kippes
    |
    |  Definición:  Convierte el ResultSet en un listado de mapa (Clave, valor)
    | 				Cada registro de la base de datos es una fila de la lista que contiene un mapa de columna de la base - valor
    |
    |  Parámetros:
    |	ResultSet resultado -- El ResultSet que nos devuelve la consulta a la base 
    |
    |  Retorna:
    |	String List<Map<String,String>> -- Listado de Mapa(Columna de la base - Valor)
    *------------------------------------------------------------------------------------------------*/
	public List<Map<String,String>> convertirResultSetAListadoDeMapas(ResultSet resultado) throws SQLException {
		ResultSetMetaData metaDataResultado = resultado.getMetaData();
		List<String> listadoDeColumnas = new ArrayList<String>(metaDataResultado.getColumnCount());
		List<Map<String,String>> mapaConResultados = new ArrayList<Map<String,String>>();
		
		for(int i = 1; i <= metaDataResultado.getColumnCount(); i++){
		    listadoDeColumnas.add(metaDataResultado.getColumnName(i));
		 }
		
	    while(resultado.next()){    
	    	Map<String,String> row = new HashMap<String, String>(listadoDeColumnas.size());
	        for(String columnas : listadoDeColumnas) {
	            row.put(columnas, resultado.getString(columnas));
	        }
	        mapaConResultados.add(row);
	    }
	    
	    return mapaConResultados;
	}
    

    protected void recorrerYValidarResultados(String valorAVerificar, ArrayList<String> resultadosOtvalor, String tabla) {
        Boolean contieneValor = false;
        for (String resultadosAValidar : resultadosOtvalor) {
            if (resultadosAValidar != null && resultadosAValidar.contains(valorAVerificar)) {
                contieneValor = true;
                break;
            }
        }
        if(!contieneValor) Assert.assertTrue("No se encontro el valor "+valorAVerificar+" en el listado de resultados para la tabla "+tabla+" : "+resultadosOtvalor, contieneValor);
        else log.info("Se encontro el valor "+valorAVerificar+" en el listado de resultados para la tabla "+tabla+" : "+resultadosOtvalor);
    }

    
	
	/*------------------------------------------------------------------------------------------------
    |  Método: realizarConsultaSql(String Consulta)
    |
    |  Autor: Marcia Sonego
    |
    |  Definición: Devuelve los registros de la consulta realizada a la base de datos
    |
    |  Parámetros:
    |	String consulta -- Consulta sql que se desea ejecutar
    |
    |  Retorna:
    |	ResultSet -- Retorna un resultset con el resultado de la consultado.
    |
    *------------------------------------------------------------------------------------------------*/

	public ResultSet realizarConsultaSql(String consulta) throws SQLException, Exception {
		PreparedStatement pstm = null;
		ResultSet rs = null;
		pstm = FactoryConnection.getinstancia().obtenerConexion().prepareStatement(consulta);
		rs=pstm.executeQuery();

		//cerrar la conexion
		if(pstm!=null)pstm.close();
		FactoryConnection.getinstancia().cerrarConexion();
		
		return rs;
	}
	
	/*------------------------------------------------------------------------------------------------
    |  Método: validarConsultaSql(String Consulta, String CantReg)
    |
    |  Autor: Marcia Sonego
    |
    |  Definición: Validar que la consulta realizada devuelva la cantidad de registros esperados
    |
    |  Parámetros:
    |	String Consulta -- Consulta sql que se desea ejecutar
    |	String CantMinReg -- Cantidad mínima de registros esperados.
    |	String CantMaxReg -- Cantidad máxima de registros esperados. 
    |						 Utilizar -1 para no especificar límite máximo de régistros, 
    |					     en este caso solo se validará que la cantidad sea mayor al mínimo.
    |
    *------------------------------------------------------------------------------------------------*/

	public void validarConsultaSql(String Consulta, int CantMinReg, int CantMaxReg) throws SQLException, Exception {
		
		ResultSet rs = realizarConsultaSql(Consulta);
		int cantRegRs = 0;
		if (rs != null) 
		{
		  rs.last();
		  cantRegRs = rs.getRow();
		  rs.close();
		}
		
		if (CantMaxReg == -1) {
			if (cantRegRs < CantMinReg) {
				throw new Exception("La consulta a la BD no retornó la cantidad de registros esperados. Se esperaban " + CantMinReg + " o más registros y se encontraron " + cantRegRs);	
			}
		} else {
			if (cantRegRs < CantMinReg || cantRegRs > CantMinReg) {
				throw new Exception("La consulta a la BD no retornó la cantidad de registros esperados. Se esperan de " + CantMinReg + " a " + CantMinReg + " registro/s y se encontraron " + cantRegRs);	
			}
		}
	}

}
