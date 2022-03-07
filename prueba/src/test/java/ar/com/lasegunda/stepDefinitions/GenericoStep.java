package ar.com.lasegunda.stepDefinitions;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import ar.com.lasegunda.paginas.GenericoPagina;
import ar.com.lasegunda.paginas.Login;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class GenericoStep extends StepBasico{

	@Autowired
	protected Login paginaLogin;
	@Autowired
	protected GenericoPagina generico;


	// PASOS PARA GENERAR UNA PROPUESTA SEGURO PARAMETRICO

	@When("Completa los Datos Mimimos de Cotizacion")
	public void completa_los_Datos_Mimimos_de_Cotizacion(DataTable dataTable) throws Exception {
		try {
			generico.esperarCargaPantalla();
			generico.validarTitulos("DATOS_MINIMOS_COTIZACION");
			List<Map<String, String>> tablaDatos = dataTable.asMaps(String.class, String.class);
			for (int i = 0; i < tablaDatos.size(); i++) {
				generico.ingresarLocalidad(tablaDatos.get(i).get("Localidad"));
				generico.seleccionarFormaPago(tablaDatos.get(i).get("FormaPago"));
				generico.seleccionarCondicionIVA(tablaDatos.get(i).get("CondicionIVA"));
				generico.seleccionarCondicionIIBB(tablaDatos.get(i).get("CondicionIIBB"));
			}
		} catch (Exception e) {
			manejarError(e);
			throw e;
		}
	}

	@When("Presiona Continuar")
	public void presiona_continuar() throws Exception {
		try {
			generico.clickContinuar();
		} catch (Exception e) {
			manejarError(e);
			throw e;
		}
	}

	@When("Se logea en AON")
	public void se_logea_en_AON() throws Exception {
		try {
			generico.loginAON();
		} catch (Exception e) {
			manejarError(e);
			throw e;
		}
	}

	@When("Presiona NUEVA COTIZACION")
	public void presiona_nueva_cotizacion() throws Exception {
		try {
			generico.desplazarseAbajoIframe(GenericoPagina.iframe, 4);
			generico.clickNuevaCotizacion();
			generico.salirIframe();
		} catch (Exception e) {
			manejarError(e);
			throw e;
		}
	}

	@When("Completa los DATOS DE LOCALIZACION DEL BIEN ASEGURABLE")
	public void completa_los_Datos_de_localizacion_del_bien_asegurable(DataTable dataTable) throws Exception {
		try {
			generico.desplazarseArribaIframe(GenericoPagina.iframe, 6);
			Thread.sleep(1000);
			List<Map<String, String>> tablaDatos = dataTable.asMaps(String.class, String.class);
			for (int i = 0; i < tablaDatos.size(); i++) {
				generico.seleccionarProvincia(tablaDatos.get(i).get("Provincia"));
				generico.seleccionarDepartamento(tablaDatos.get(i).get("Departamento"));
				generico.seleccionarCuartel(tablaDatos.get(i).get("Cuartel"));
			}
		} catch (Exception e) {
			manejarError(e);
			throw e;
		}
	}

	@When("Presiona SIGUIENTE")
	public void presiona_siguiente() throws Exception {
		try {
			generico.desplazarseAbajoIframe(GenericoPagina.iframe, 5);
			generico.clickSiguiente();
		} catch (Exception e) {
			manejarError(e);
			throw e;
		}
	}

	@When("Selecciona como cultivo la opcion {string}")
	public void selecciona_como_cultivo_la_opcion(String cultivo) throws Exception {
		try {
			generico.clickCultivo(cultivo);
		} catch (Exception e) {
			manejarError(e);
			throw e;
		}
	}

	@When("Completa los VALORES DEL BIEN ASEGURABLE")
	public void completa_los_valores_del_bien_asegurable(DataTable dataTable) throws Exception {
		try {
			generico.desplazarseArribaIframe(GenericoPagina.iframe, 6);
			Thread.sleep(1000);
			List<Map<String, String>> tablaDatos = dataTable.asMaps(String.class, String.class);
			for (int i = 0; i < tablaDatos.size(); i++) {
				generico.ingresarHectareas(tablaDatos.get(i).get("Hectareas"));
				generico.ingresarSumaAsegurada(tablaDatos.get(i).get("SumaAsegurada"));
				generico.seleccionarInicioBanda(tablaDatos.get(i).get("FechaInicioBanda"));
				generico.ingresarDisparador(tablaDatos.get(i).get("Disparador"));
			}
		} catch (Exception e) {
			manejarError(e);
			throw e;
		}
	}

	@When("Se selcciona como opcion de banda la opcion {string}")
	public void se_seleccion_como_opcion_de_banda_la_opcion(String opcionBanda) throws Exception {
		try {
			Thread.sleep(1000);
			generico.esperarCargaPantalla();
			generico.seleccionarOpcionBanda(opcionBanda);	
		} catch (Exception e) {
			manejarError(e);
			throw e;
		}
	}

	@When("Se completan los datos del Tomador")
	public void se_completan_los_datos_del_tomador(DataTable dataTable) throws Exception {
		try {
			Thread.sleep(2000);
			generico.esperarCargaPantalla();
			List<Map<String, String>> tablaDatos = dataTable.asMaps(String.class, String.class);
			for (int i = 0; i < tablaDatos.size(); i++) {
				//generico.seleccionarTipo(tablaDatos.get(i).get("Tipo"));
				generico.ingresarNroDocumento(tablaDatos.get(i).get("NroDocumento"));
				generico.esperarCargaCliente();
			}
		} catch (Exception e) {
			manejarError(e);
			throw e;
		}
	}

	@When("Se completan los datos del Asegurado usando datos del Tomador")
	public void se_completan_los_datos_del_asegurado_usando_datos_del_tomador() throws Exception {
		try {
			generico.clickUsarDatosTomador();
			generico.esperarCargaCliente();
		} catch (Exception e) {
			manejarError(e);
			throw e;
		}

	}
	
	@When("Se completan los datos del Historial")
	public void se_completan_los_datos_del_Historial(DataTable dataTable) throws Exception {
		try {
			generico.desplazarseArribaIframe(GenericoPagina.iframe, 6);
			Thread.sleep(1000);
			List<Map<String, String>> tablaDatos = dataTable.asMaps(String.class, String.class);
			for (int i = 0; i < tablaDatos.size(); i++) {
				generico.seleccionarSembroAñoPasado(tablaDatos.get(i).get("SembroAñoPasado"));
				generico.cuantasHectareas(tablaDatos.get(i).get("CuantasHectareas"));
				generico.queRindeTuvo(tablaDatos.get(i).get("Rinde"));
				generico.seleccionarOtraAseguradora(tablaDatos.get(i).get("ContratarOtroSeguro"));
				generico.aseguradora(tablaDatos.get(i).get("Aseguradora"));
			}
			Thread.sleep(1000);
		} catch (Exception e) {
			manejarError(e);
			throw e;
		}
	}

	@Then("Se valida que en el Resumen se muestren correctamente los datos del bien asegurable")
	public void se_valida_que_en_el_resumen_se_muestren_correctamente_los_datos_del_bien_asegurable() throws Exception {
		try {
			generico.clickContinuar();
		} catch (Exception e) {
			manejarError(e);
			throw e;
		}
	}
	
	@And("Se imprime la propuesta")
	public void se_imprime_la_propuesta() throws Exception {
		try {
			generico.clickContinuar();
		} catch (Exception e) {
			manejarError(e);
			throw e;
		}
	}
	
	@Then("se corrobora que se valida correctamente los datos ingresados")
	public void se_corrobora_que_se_valida_correctamente_los_datos_ingresados() throws Exception {
		try {
			generico.clickContinuar();
		} catch (Exception e) {
			manejarError(e);
			throw e;
		}
	}
}
