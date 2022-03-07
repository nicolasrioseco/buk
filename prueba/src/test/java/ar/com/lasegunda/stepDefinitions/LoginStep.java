package ar.com.lasegunda.stepDefinitions;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import ar.com.lasegunda.paginas.Login;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;

public class LoginStep extends StepBasico{
	
	@Autowired
	protected Login paginaLogin;
	

	//PASOS DEL LOGIN
	
	@Given("Un usuario que ingresa a la pagina {string}")
	public void un_usuario_que_ingresa_a_la_pagina(String url) throws Exception {
		try {
				paginaLogin.navegarASeguroParametrico(url);	
		} catch (Exception e) {
			manejarError(e);
			throw e;
		}
	}

	@When("Completa  los campos y presiona ingresar")
	public void completa_los_campos_y_presiona_ingresar(DataTable dataTable) throws Exception {
		try {
			List<Map<String,String>> tablaDatos = dataTable.asMaps(String.class, String.class);
			for (int i = 0; i < tablaDatos.size(); i++) {
				paginaLogin.ingresarUsuario(tablaDatos.get(0).get("usuario"));
				paginaLogin.ingresarClave(tablaDatos.get(0).get("password"));
				paginaLogin.clickEnIngresar();
			}
		} catch (Exception e) {
			manejarError(e);
			throw e;
		}
	}
}
