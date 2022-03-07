package ar.com.lasegunda.utiles.soporte;

import java.io.File;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("cucumber-glue")
public class MetodosUtiles {

    	/*------------------------------------------------------------------------------------------------
    |  Método: getRutaDeCarpetaImage()
    |
    |  Autor: Nicolas Rioseco
    |
    |  Definición:  Devuelve la ruta completa (C:\\....) de la carpeta image dentro de recursos.
    |
    |  Parámetros: -	
    | 
    |  Retorna:
    |	String ruta -- Ruta completa. Ejemplo: C:\Emmanuel\SQA_Automatizaciones\template\src\test\resources\image
    *------------------------------------------------------------------------------------------------*/
	public String getRutaDeCarpetaArchivo() {
		String path = "src/test/resources/archivos";
		File file = new File(path);
		return file.getAbsolutePath()+"\\";
	}
	
	/*------------------------------------------------------------------------------------------------
    |  Método: getRutaDeCarpetaDrivers()
    |
    |  Autor: Nicolas Rioseco
    |
    |  Definición:  Devuelve la ruta completa (C:\\....) de la carpeta drivers dentro de recursos.
    |
    |  Parámetros: -	
    | 
    |  Retorna:
    |	String ruta -- Ruta completa. Ejemplo: C:\Emmanuel\SQA_Automatizaciones\template\src\test\resources\drivers
    *------------------------------------------------------------------------------------------------*/
	public String getRutaDeCarpetaDrivers() {
		String path = "src/test/resources/drivers";
		File file = new File(path);
		return file.getAbsolutePath()+"\\";
	}
	
}
