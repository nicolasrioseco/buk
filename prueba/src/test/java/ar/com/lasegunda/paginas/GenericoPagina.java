package ar.com.lasegunda.paginas;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ar.com.lasegunda.utiles.enums.Tecla;
import ar.com.lasegunda.utiles.soporte.MetodosUtiles;

@Component
@Scope("cucumber-glue")
public class GenericoPagina extends PaginaBase {

	@Autowired
	public GenericoPagina(WebDriver driver){super(driver);}
	@Autowired
	public MetodosUtiles metodosUtiles;
	@Autowired
	protected Login paginaLogin;
	
	private final static String spinner = "//img[@class='spinner']";
	private final static String nroDocumento = "//input[@id='Nro. de documento']";
	private final static String localidad = "//input[@id='locationSelect']";
	private final static String btnLocalidad = "//button[@id='ngb-typeahead-0-0']";
	private final static String btnContinuar = "//button[@class='btn btn-primary aonButton']";
	public final static String btnNuevaCotizacion = "//button[@class='cta']";
	private final static String comboLocalidad  = "//input[@id='mat-input-0']";
	private final static String localidadSeleccionada = "/html[1]/body[1]/div[2]/div[1]/div[1]/div[1]/mat-option[1]/span[1]";
	private final static String textoDocumentoAdjunto = "//input[@id='idPrueba']";
	private final static String tituloPantallas = "//div[@class='title']";
	private final static String selectProvincia = "//*[@id='request-form']/div[2]/div[1]/select";
	private final static String selectDepartamento = "//*[@id='request-form']/div[2]/div[2]/select";
	private final static String selectCuartel = "//*[@id='request-form']/div[2]/div[3]/select";
	private final static String btnSiguiente = "//*[@id='app']/div[2]/div/div/form/div[2]/div[2]/button[3]";
	private final static String elementoCultivo = "//*[@id='app']//label[contains(text(),'";
	private final static String hectareas = "//*[@id='app']/div[2]/div/div/form/div[2]/div[2]/div[2]/div[2]/div[1]/div[1]/input";
	private final static String sumaAsegurada = "//*[@id='app']/div[2]/div/div/form/div[2]/div[2]/div[2]/div[2]/div[1]/div[2]/input";
	private final static String disparador = "//*[@id='app']/div[2]/div/div/form/div[2]/div[2]/div[2]/div[2]/div[3]/div[4]/input";
	private final static String inicioBanda = "//*[@id='app']//div[contains(text(),'";
	private final static String selectBanda = "/html/body/app-root/div/app-aon-testing/app-band-choice/div/div/div['";
	private final static String clickCalculo = "//div[@class = 'card-form full']";
	private final static String btnDatosTomador = "//*[@class='link']";
	private final static String selectSembro = "//div[@class='form-row'][1]//label[@class='btn btn-light']";
	private final static String inputCuantasHectareas = "//input[@id='¿Cuántas hectareas?']";
	private final static String inputRindeAnterior = "//input[@id='¿Qué rinde tuvo? (qq/ha)']";
	private final static String selectOtraAseguradora = "//div[@class='form-row'][2]//label[@class='btn btn-light']";
	private final static String inputAseguradora = "//input[@id='¿En qué compañía aseguradora?']";
	public final static String iframe = "//iframe [@class='iframeClass']";
	public final static String tituloIframe = "//*[@id='app']";
	public final static String spinnerButton = "/html/body/app-root/div/app-aon-testing/div[2]/div/div/img";
	
	
	@SuppressWarnings("deprecation")
	public void esperarCargaPantalla() throws Exception {
		WebDriverWait wait = new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(spinner)));
	}
		
	public void esperarCargaCliente() throws InterruptedException{
		while (true){
		    try {
		    	if (driver.findElement(By.xpath(spinnerButton)).isDisplayed())
                    Thread.sleep(2000);
		    } catch (NoSuchElementException ex){
		      break;   // button is missing, exit the loop
		    }
		}
	}
	
	public void loginAON() throws Exception {
		//para hacer el segundo login
		paginaLogin.AON(iframe);
	}
	
	/*
	 * public void seleccionarTipo(String Tipo) throws Exception { WebElement
	 * selectElement = driver.findElement(By.id("documentTypeInput")); Select
	 * selectObject = new Select(selectElement);
	 * selectObject.selectByVisibleText(Tipo); }
	 */
	
	public void seleccionarProvincia(String Provincia) throws Exception {
		ingresarAIframe(iframe);
		WebElement selectElement = driver.findElement(By.xpath(selectProvincia));
		Select selectObject = new Select(selectElement);
		esperarOpciones(selectObject);
		selectObject.selectByVisibleText(Provincia);
		salirIframe();
	}

	public void seleccionarDepartamento(String Departamento) throws Exception {
		ingresarAIframe(iframe);
		WebElement selectElement = driver.findElement(By.xpath(selectDepartamento));
		Select selectObject = new Select(selectElement);
		esperarOpciones(selectObject);
		selectObject.selectByVisibleText(Departamento);
		salirIframe();
	}

	public void seleccionarCuartel(String Cuartel) throws Exception {
		ingresarAIframe(iframe);
		WebElement selectElement = driver.findElement(By.xpath(selectCuartel));
		Select selectObject = new Select(selectElement);
		esperarOpciones(selectObject);
		selectObject.selectByVisibleText(Cuartel);
		salirIframe();
	}
	
	private void esperarOpciones(Select selectObject) throws Exception {
		int i = 0;
		while (i < 10) {
			List<WebElement> allAvailableOptions = selectObject.getOptions();
			for(WebElement item : allAvailableOptions) {
				System.out.println(item .getText());
			}
			if (selectObject.getOptions().size() > 2) {
				break;
			}
			Thread.sleep(1000);
			i++;
		} 
	}
	
	public void ingresarHectareas(String ValorHectareas) throws Exception {
		ingresarAIframe(iframe);
		escribirTextoEnElemento(hectareas, ValorHectareas, "Hectareas");
		salirIframe();
	}
	
	public void ingresarSumaAsegurada(String ValorSumaAsegurada) throws Exception {
		ingresarAIframe(iframe);
		escribirTextoEnElemento(sumaAsegurada, ValorSumaAsegurada, "Suma Asegurada");
		salirIframe();
	}
	
	public void seleccionarInicioBanda(String opcionBanda) throws Exception {
		String elementoBanda = inicioBanda + opcionBanda + "')]";
		desplazarseAbajoIframe(iframe, 1);
		clickElementoIframe(elementoBanda, "Seleccion Inicio Banda");
	}
	
	public void seleccionarOpcionBanda(String opcionBanda) throws Exception {
		String btnBanda = selectBanda + opcionBanda + "']";
		clickElemento(btnBanda, "Seleccion Tipo Banda");
	}
	
	public void ingresarDisparador(String ValorDisparador) throws Exception {
		ingresarAIframe(iframe);
		escribirTextoEnElemento(disparador, ValorDisparador, "Disparador");
		salirIframe();
		clickElementoIframe(clickCalculo, "Calcular");
	}
	
	public void ingresarNroDocumento(String ValorNroDocuemento) throws Exception {
		escribirTextoEnElemento(nroDocumento, ValorNroDocuemento, "NroDocuemento");
	}
	
	public void ingresarLocalidad(String ValorLocalidad) throws Exception {
		escribirTextoEnElemento(localidad, ValorLocalidad, "Localidad");
		clickElemento(btnLocalidad, "SeleccionLocalidad");
	}
	
	public void clickUsarDatosTomador() throws Exception {
		Thread.sleep(3000);
		clickElemento(btnDatosTomador, "UsarDatosTomador");
		Thread.sleep(3000);
	}
	
	public void seleccionarCondicionIVA(String ValorIVA) throws Exception {
		WebElement selectElement = driver.findElement(By.id("ivaInput"));
		Select selectObject = new Select(selectElement);
		selectObject.selectByVisibleText(ValorIVA);
	}
	
	public void seleccionarCondicionIIBB(String ValorIIBB) throws Exception {
		WebElement selectElement = driver.findElement(By.id("iibbInput"));
		Select selectObject = new Select(selectElement);
		selectObject.selectByVisibleText(ValorIIBB);
	}
	
	public void seleccionarFormaPago(String ValorFormaPago) throws Exception {
		WebElement selectElement = driver.findElement(By.id("paymentInput"));
		Select selectObject = new Select(selectElement);
		selectObject.selectByVisibleText(ValorFormaPago);
	}
	
	public void clickContinuar() throws Exception {
		clickElemento(btnContinuar, "Boton Continuar");
	}
	
	public void clickNuevaCotizacion() throws Exception {
		clickElementoIframe(btnNuevaCotizacion, "Boton Nueva Cotizacion");
	}
	
	public void clickSiguiente() throws Exception {
		clickElementoIframe(btnSiguiente, "Boton Nueva Cotizacion");
	}
	
	public void clickCultivo(String cultivo) throws Exception {
		String opcionCultivo = elementoCultivo + cultivo + "')]";
		clickElementoIframe(opcionCultivo, "Elegir Cultivo");
	}
	
	public void seleccionarLocalidad(String pLocalidad) throws Exception {
		escribirTextoEnElemento(comboLocalidad, pLocalidad, "Localidad");
		presionarUnaTecla(comboLocalidad, Tecla.ABAJO);
		clickElemento(localidadSeleccionada, "Localidad");
	}
	
	public void seleccionarSembroAñoPasado(String sembroAñoPasado) throws Exception {
		clickElemento(selectSembro, "Seleccion Sembro Año Pasado");
	}
	
	public void cuantasHectareas(String cuantasHectareas) throws Exception {
		escribirTextoEnElemento(inputCuantasHectareas, cuantasHectareas, "Cuantas Hectareas");
	}
	
	public void queRindeTuvo(String rindeAnterior) throws Exception {
		escribirTextoEnElemento(inputRindeAnterior, rindeAnterior, "Rinde Anterior");
	}
	
	public void seleccionarOtraAseguradora(String otraAseguradora) throws Exception {
		clickElemento(selectOtraAseguradora, "Seleccion Contratar Otra Aseguradora");
	}
	
	public void aseguradora(String nombreAseguradora) throws Exception {
		escribirTextoEnElemento(inputAseguradora, nombreAseguradora, "Cuantas Hectareas");
	}
	
	public void validarTitulos(String tituloPag) throws Exception {
		Thread.sleep(5000);
		hacerScrollHaciaUnElemento(tituloPantallas, "Titulo");
		switch (convertStringToEnumTituloPaginas(tituloPag)) {
		case DATOS_MINIMOS_COTIZACION:
			validarTextoDeElemento(tituloPantallas, "Datos mínimos cotización", "Datos Minimos Cotizacion");
			break;		
		default:
			break;
		}
	}
	
	private TituloPaginas convertStringToEnumTituloPaginas(String tituloPag) {
		TituloPaginas tituloPagina = TituloPaginas.valueOf(tituloPag);
		return tituloPagina;	
	}
		
	enum TituloPaginas{
		DATOS_MINIMOS_COTIZACION,OTRO
	}

	public void seleccionarDocumento(String imagen) throws Exception {
		js.executeScript("document.getElementsByClassName('hiddenInputFile')[0].setAttribute('id','idPrueba');");
		js.executeScript("document.getElementById('idPrueba').classList.remove('hiddenInputFile');");
		String imageURL = metodosUtiles.getRutaDeCarpetaArchivo()+imagen;
		escribirTextoEnElemento(textoDocumentoAdjunto, imageURL, "Imagen Url");
	}
}
