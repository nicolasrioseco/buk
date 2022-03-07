package ar.com.lasegunda.paginas;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.TargetLocator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ar.com.lasegunda.utiles.enums.Tecla;

@Component
@Scope("cucumber-glue")
public class PaginaBase {

	protected WebDriver driver;
	
	@Autowired
	protected WebDriverWait wait;
	
	protected JavascriptExecutor js;
	protected PreparedStatement pstm = null;
	protected ResultSet rs = null;
    protected TargetLocator IFrame;
    protected Logger log = LoggerFactory.getLogger(PaginaBase.class);
	
	@Value("${demo}")
	private boolean demo;
	
	@Autowired
	public PaginaBase(WebDriver pDriver){
        driver = pDriver;
        js = (JavascriptExecutor) driver;
    }
	
	public void cerrarNavegador() throws Exception {
		driver.quit(); 
		log.info("Se cierra el navegador correctamente");
	}
	
	public void navegarA(String url) throws Exception {
		driver.get(url);
		log.info("Se ingresa a la url: "+url);
	}

	public WebElement encontrarElemento(String locator) throws Exception {
		if (demo) {Thread.sleep(1000);}
		return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator)));
	}

	public void clickElemento(String locator, String nombreElemento) throws Exception {
		hacerScrollHaciaUnElemento(locator, nombreElemento);
		encontrarElemento(locator).click();
		log.info("Se hizo click en el elemento: "+nombreElemento);
	}
	
	/*------------------------------------------------------------------------------------------------
    |  Método: desplazarseAbajoIframe(String iframe, int desplazarse)
    |  Autor: Nicolás Rioseco
    |  Definición:  Permite hacer un scroll hacia abajo dentro de un Iframe.
    |  Parámetros:
    |	String ifrma -- Xpath por el cual se va a encontrar el iframa.
    |	String desplazarse   -- Cuantas veces se quiere desplazar hacia abajo. 
    |  Retorna: -
    *------------------------------------------------------------------------------------------------*/
	public void desplazarseAbajoIframe(String iframe, int desplazarse) {
		ingresarAIframe(iframe);
		int i = 0;
		while (i < desplazarse) {
			IFrame.activeElement().sendKeys(Keys.TAB);
			IFrame.activeElement().sendKeys(Keys.PAGE_DOWN);
			i++;
			if (i == desplazarse) {
				break;
			}
		}
		salirIframe();
	}

	public void desplazarseArribaIframe(String iframe, int desplazarse) {
		ingresarAIframe(iframe);
		int i = 0;
		while (i < desplazarse) {
			IFrame.activeElement().sendKeys(Keys.TAB);
			IFrame.activeElement().sendKeys(Keys.PAGE_UP);
			i++;
			if (i == desplazarse) {
				break;
			}
		}
		salirIframe();
	}

	public void clickElementoIframe(String elementoACompletar, String nombreElemento) throws Exception {
		ingresarAIframe(GenericoPagina.iframe);
		encontrarElemento(elementoACompletar).click();
		log.info("Se hizo click en el elemento: "+nombreElemento);
		salirIframe();
	}
	
	public void ingresarAIframe (String iframe) {
		IFrame = driver.switchTo();
	    IFrame.frame(0); // Switching the Outer Frame
	    }

	public void salirIframe() {
		IFrame = driver.switchTo();
		IFrame.defaultContent();
	}

	public void escribirTextoEnElemento(String locator, String texto, String nombreElemento) throws Exception{
		hacerScrollHaciaUnElemento(locator, nombreElemento);
		encontrarElemento(locator).sendKeys(texto);
		log.info("Se escribio: "+texto+" sobre el elemento: "+nombreElemento);
	}

	public void presionarUnaTecla(String locator, Tecla tecla) throws Exception {
        if (tecla.equals(Tecla.ABAJO)) encontrarElemento(locator).sendKeys(Keys.ARROW_DOWN);
        if (tecla.equals(Tecla.ENTER)) encontrarElemento(locator).sendKeys(Keys.ENTER);
        if (tecla.equals(Tecla.TAB)) encontrarElemento(locator).sendKeys(Keys.TAB);
		log.info("Se presiono la tecla "+tecla+" sobre el elemento con locator: "+locator);
	}

	public void validarTextoDeElemento(String locator, String text, String nombreElemento) throws Exception {
		hacerScrollHaciaUnElemento(locator, nombreElemento);
		Assert.assertEquals("El valor: \'"+text+"\' no se encuentra dentro de: \'"+encontrarElemento(locator).getText()+"\'", text, encontrarElemento(locator).getText());
		log.info("Se valido que "+text+" se visualiza en el elemento: "+nombreElemento);
	}

	public void validarQueElementoContengaUnTexto(String locator, String textoEsperado, String nombreElemento) throws Exception {
		String textoQueSeVisualiza = encontrarElemento(locator).getText();
		Assert.assertTrue("El valor: \'"+textoEsperado+"\' no se encuentra dentro de: \'"+textoQueSeVisualiza+"\'", textoQueSeVisualiza.contains(textoEsperado));
		log.info("Se valido que "+textoEsperado+" se visualiza dentro del elemento: "+nombreElemento);
	}

	public void seleccionarUnElementoDeUnCombo(String combo, String value, String nombreElemento) throws Exception {
		hacerScrollHaciaUnElemento(combo, nombreElemento);
		encontrarElemento(combo).click();
		encontrarElemento(value).click();
		log.info("Se selecciono el valor: "+value+" del elemento: "+nombreElemento);
	}

	public void hacerScrollHaciaUnElemento(String locator, String nombreElemento) throws Exception {
		js.executeScript("arguments[0].scrollIntoView(false);", encontrarElemento(locator));
//			Actions actions = new Actions(driver);
//			actions.moveToElement(encontrarElemento(locator));
//			actions.perform();
        log.info("Se hizo scroll hacia el elemento: "+nombreElemento);
	}

	public void validarImagen(String locator, String nomImagen) throws SQLException, Exception {
		String scr = encontrarElemento(locator).getAttribute("src");
		String nomImgScr = scr.substring(scr.lastIndexOf("/") + 1, scr.length());
		Assert.assertEquals(nomImagen, nomImgScr);
	}

	public void esperarMilisegundos(long milisegundos) throws InterruptedException {
		Thread.sleep(milisegundos);		
	}

    public void esperarObjeto(String locator){
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator)));
    }

    public void esperarQueObjetoDesaparezca(String locator){
      //  wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator)));
        wait.until(ExpectedConditions.not(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator))));
    }

    public void quitarClassAObjeto(String idElemento, String classElemento){
        js.executeScript(String.format("document.getElementById('%1$s').classList.remove('%2$s');", idElemento, classElemento));
     }

     public void agregarIdAObjeto(String elemento, String idAAgregar) {
         js.executeScript(String.format("document.querySelector(\"%1$s\").setAttribute('id', '%2$s');", elemento, idAAgregar));
     }

     public String obtenerTextoDeElemento(String locator) throws Exception {
         return encontrarElemento(locator).getText();
     }
	
}
