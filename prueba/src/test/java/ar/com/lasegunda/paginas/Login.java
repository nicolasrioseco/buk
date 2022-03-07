package ar.com.lasegunda.paginas;


import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("cucumber-glue")
public class Login extends PaginaBase {
	
	@Autowired
	public Login (WebDriver driver) {super(driver);}

	private final static String textoUsuario = "//input[@id='username']";
	private final static String textoClave = "//input[@name='password']";
	private final static String botonLogin = "//button[@class='btnLogin']";
	private final static String botonSesion = "//dev[@id='dropdownButton']";
	private final static String botonCerrarSesion = "//a[@class='dropdown-item']";
	private final static String tituloStep1 = "//div[@class='title']";
	private final static String textoLogin = "//h3[@class='card-title']";
	private final static String textoUsuarioIframe = "//input[@placeholder='Ingresar Email']";
	private final static String textoPass = "//input[@placeholder='Ingresar mi Clave']";
	private final static String btnLoginIframe = "//*[@id=\"app\"]/div[2]/div/div/form/button";
	

	public void AON(String iframe) throws Exception {
		ingresarAIframe(iframe);
		escribirTextoEnElemento(textoUsuarioIframe, "desarrollo@lasegunda.com.ar", "Usuario");
		escribirTextoEnElemento(textoPass, "desarrollo***", "Pass");
		clickElemento(btnLoginIframe, "Login");
		salirIframe();
	}
	
	@Value("${url}")
	private String urlConfig;
	
	public void navegarASeguroParametrico(String url) throws Exception {
		if (url==null || url.isEmpty()) {
			url = urlConfig;
		}
		navegarA(url);
		driver.manage().window().maximize();
	}
	
	public void ingresarUsuario(String pUsuario) throws Exception {
		escribirTextoEnElemento(textoUsuario, pUsuario, "Usuario");
	}
	
	public void ingresarClave(String pClave) throws Exception {
		escribirTextoEnElemento(textoClave, pClave, "Clave");
	}
	
	public void clickEnIngresar() throws Exception {
		clickElemento(botonLogin, "Login");
	}

	public void cerrarSesion() throws Exception {
		clickElemento(botonSesion, "Sesion");
		clickElemento(botonCerrarSesion,"CerrarSesion");
		
	}

	public void validarTextoDeStep1() throws Exception {
		validarTextoDeElemento(tituloStep1, "Datos mínimos cotización", "Step1");
		
	}

	public void validarCargaLanding() throws Exception {
		validarTextoDeElemento(textoLogin,"Ingresar a mi cuenta", "IniciarSesion");
	}
}
