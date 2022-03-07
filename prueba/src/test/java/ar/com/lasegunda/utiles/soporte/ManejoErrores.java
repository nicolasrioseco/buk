package ar.com.lasegunda.utiles.soporte;

import java.io.ByteArrayInputStream;
import java.io.File;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import io.qameta.allure.Allure;

@Component
@Scope("cucumber-glue")
public class ManejoErrores {

    @Autowired
    private WebDriver driver;

    /*------------------------------------------------------------------------------------------------
    |  Método: adherirErrorAlReporteAllure(String message)
    |
    |  Autor: Nicolas Rioseco
    |
    |  Definición: Adhiere mensaje de error y saca captura del error.
    |
    |  Parámetros:
    |	String mensaje -- Mensaje de error a mostar en Allure
    |
    *------------------------------------------------------------------------------------------------*/
	public void adherirErrorAlReporteAllure(String mensaje) {
		Allure.addAttachment("Error", mensaje);
		Allure.addAttachment("Captura de Pantalla", new ByteArrayInputStream(((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES)));
	}
	
	/*------------------------------------------------------------------------------------------------
    |  Método: adherirCapturaAlReporteAllure(String message)
    |
    |  Autor: Nicolas Rioseco
    |
    |  Definición: Adhiere captura de pantalla al reporte de Allure.
    |
    |  Parámetros: -
    |
    *------------------------------------------------------------------------------------------------*/
	public void adherirCapturaAlReporteAllure() {
		Allure.addAttachment("Captura de Pantalla", new ByteArrayInputStream(((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES)));
    }
    
    	/*------------------------------------------------------------------------------------------------
    |  Método: getMensajeDeExcepcion(Exception e)
    |
    |  Autor: Nicolas Rioseco
    |
    |  Definición:  Según la excepción que recibe devuelve un mensaje personalizado junto al mensaje y la causa
    |				propia de la excepcion.
    |
    |  Parámetros:
    |	Exception e -- Excepcion que se produjo.	
    | 
    |  Retorna:
    |	String mensaje -- Retorna un mensaje en formato amigable para el usuario.
    *------------------------------------------------------------------------------------------------*/
	public String getMensajeDeExcepcion(Exception e) {
		String mensaje = e.getMessage();
		String causa = "";
		if (e.getCause() != null) {
			causa = e.getCause().toString();
		}
		if (e instanceof NoSuchElementException)
			return String.format("No se encontró el elemento en la página con el locator especificado. %n *Mensaje*: %1$s %n *Causa*: %2$s %n", mensaje, causa);
		if (e instanceof ElementNotVisibleException)
			return String.format("Elemento se encontró en la página pero no está visible. Verificar si otro elemento lo está tapando. %n *Mensaje*: %1$s %n *Causa*: %2$s %n", mensaje, causa);
		if (e instanceof TimeoutException)
			return String.format("Se superó el tiempo de espera del elemento en el DOM de la página. %n *Mensaje*: %1$s %n *Causa*: %2$s %n", mensaje, causa);
		if (e instanceof StaleElementReferenceException)
			return String.format("El elemento se encontraba en el DOM de la página pero ya no. %n *Mensaje*: %1$s %n *Causa*: %2$s %n", mensaje, causa);
		return String.format("Ocurrio un error inesperado. %n Mensaje: %1$s %n Causa: %2$s %n", mensaje, causa);
	}
	
	/*------------------------------------------------------------------------------------------------
    |  Método: capturarPantallaParaReporteCucumber()
    |
    |  Autor: Nicolas Rioseco
    |
    |  Definición:  Toma una captura de la pantalla y la devuelve en formato byte[] para ser usanda dentro del 
    |				reporte de Cucumber.
    |
    |  Parámetros: -
    | 
    |  Retorna:
    |	byte[] screenshot -- Retorna la captura de pantalla.
    *------------------------------------------------------------------------------------------------*/
	public byte[] capturarPantallaParaReporteCucumber() {
		final byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
		return screenshot;
	}
	
	
	/*------------------------------------------------------------------------------------------------
    |  Método: capturarPantallaYGuardarla(String nameFile)
    |
    |  Autor: Nicolas Rioseco
    |
    |  Definición:  Toma una captura de la pantalla y la guarda en el proyecto.
    |				Ruta donde la guarda: ./target/imagenes/
    |
    |  Parámetros:
    |	String namFile -- Nombre que se le quiere dar a la captura de pantalla. 
    |					  Debe contentar la extensión. Ejemplo: nombreCaptura.png
    |
    |  Retorna:
    |	String urlDestino -- Retorna el path de la captura de pantalla. Donde se guardó.
    *------------------------------------------------------------------------------------------------*/
	public String capturarPantallaYGuardarla(String nameFile) throws Exception {
		final File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String urlDestino = "./target/imagenes/" + nameFile;
		FileUtils.copyFile(screenshot, new File(urlDestino));
		return urlDestino;
	}
    
}
