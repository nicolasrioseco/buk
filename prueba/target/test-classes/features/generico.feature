Feature: GRANIZO - SEGURO PARAMETRICO

  @Usuarios
  Scenario: Crear usuario
    Given DosUn usuario ingresa a la pagina "https://petstore.octoperf.com/actions/Catalog.action"
    When Presiona sobre el boton "Sign In"
    And Presiona sobre el boton "Register Now"
    And Completa los campos de User Information <UserId><NewPassword><RepeatPassword>
      | UserId | NewPassword | RepeatPassword |
      | nico2  | nico2       | nico2          |
    And Completa los campos de Account Information <FirstName><LastName><Email><Phone><Adress1><Adress2><City><State><ZIP><Country>
      | FirstName | LastName | Email        | Phone | Adress1         | Adress2        | City   | State        | ZIP  | Country   |
      | nico      | nico2    | nico@buk.com | 12345 | Siempreviva 742 | Las flores 234 | Tandil | Buenos Aires | 7000 | Argentina |
    And Completa los campos de Profile Information <LanguagePreference><FavouriteCategory><EnableMyList><EnableMyBanner>
      | LanguagePreference | FavouriteCategory | EnableMyList | EnableMyBanner |
      | english            | DOGS              | true         | false          |
    And Presiona sobre el boton "Save Account Information"
    Then se crea el usuario y queda posicionado en la homepage

  @RealizarCompra
  Scenario: Realizar una compra
    Given DosUn usuario ingresa a la pagina "https://petstore.octoperf.com/actions/Catalog.action"
    When Presiona sobre el boton "Sign In"
    And Completa <username><password>
      | username | password |
      | nico2    | nico2    |
    And Presiona sobre el boton "Login"
    And Selecciona la categoría "Dogs"
    And Selecciona el producto "K9-PO-02"
    And Presiona sobre el boton "Add to Cart"
    And Presiona sobre el boton "Proceed to Checkout"
    And Completa los campos de PaymentDetails <CardType><CardNumber><ExpiryDate>
      | CardType   | CardNumber         | ExpiryDate |
      | MasterCard | 333 4444 4444 4444 | "12/2033"  |
    And Valida los datos de BillingAdress
      | FirstName | LastName | Email        | Phone | Adress1         | Adress2        | City   | State        | ZIP  | Country   |
      | nico      | nico2    | nico@buk.com | 12345 | Siempreviva 742 | Las flores 234 | Tandil | Buenos Aires | 7000 | Argentina |
    And Presiona sobre el boton "Continue"
    And Valida los datos de ShippingAdress sean los mismos que los del BillingAdress
      | FirstName | LastName | Email        | Phone | Adress1         | Adress2        | City   | State        | ZIP  | Country   |
      | nico      | nico2    | nico@buk.com | 12345 | Siempreviva 742 | Las flores 234 | Tandil | Buenos Aires | 7000 | Argentina |
    And Presiona sobre el boton "Confirm"
		Then El usuario finaliza su compra
		#Thank you, your order has been submitted.
		
		
  @High
  Scenario: Se genera una propuesta de Seguro Parametrico Doble Banda
    Given Un usuario que ingresa a la pagina "https://segurosparametricosagro-desa.npapps.lasegunda.com.ar/"
    And Completa  los campos y presiona ingresar
      | usuario | password |
      | testcac | testcac  |
    When Completa los Datos Mimimos de Cotizacion
      | Localidad      | FormaPago       | CondicionIVA      | CondicionIIBB       |
      | ROSARIO (2000) | 100% AL CONTADO | RESP. MONOTRIBUTO | CONTRIBUYENTE LOCAL |
    And Presiona Continuar
    And Se logea en AON
    And Presiona NUEVA COTIZACION
    When Completa los DATOS DE LOCALIZACION DEL BIEN ASEGURABLE
      | Provincia  | Departamento | Cuartel   |
      | ENTRE RIOS | PARANA       | Espinillo |
    And Presiona SIGUIENTE
    When Selecciona como cultivo la opcion "Soja Segunda"
    And Completa los VALORES DEL BIEN ASEGURABLE
      | Hectareas | SumaAsegurada | FechaInicioBanda | FechaFinBanda | Disparador |
      |      1000 |           500 | 01/01            | 31/01         |         30 |
    And Se selcciona como opcion de banda la opcion "Doble Banda"
    And Presiona Continuar
    When Se completan los datos del Tomador
      | Tipo   | NroDocumento |
      | D.N.I. |     25750890 |
    When Se completan los datos del Asegurado usando datos del Tomador
    And Presiona Continuar
    When Se completan los datos del Historial
      | SembroAñoPasado | CuantasHectareas | Rinde   | ContratarOtroSeguro | Aseguradora       |
      | Si              |              100 | 100/100 | Si                  | AseguradoraPrueba |
    And Presiona Continuar
    Then Se valida que en el Resumen se muestren correctamente los datos del bien asegurable
    And Se imprime la propuesta

  @High
  Scenario: Se genera una propuesta de Seguro Parametrico Triple Banda
    Given Un usuario que ingresa a la pagina "https://segurosparametricosagro-desa.npapps.lasegunda.com.ar/"
    And Completa  los campos y presiona ingresar
      | usuario | password |
      | testcac | testcac  |
    When Completa los Datos Mimimos de Cotizacion
      | Localidad      | FormaPago       | CondicionIVA      | CondicionIIBB       |
      | ROSARIO (2000) | 100% AL CONTADO | RESP. MONOTRIBUTO | CONTRIBUYENTE LOCAL |
    And Presiona Continuar
    And Se logea en AON
    And Presiona NUEVA COTIZACION
    When Completa los DATOS DE LOCALIZACION DEL BIEN ASEGURABLE
      | Provincia  | Departamento | Cuartel   |
      | ENTRE RIOS | PARANA       | Espinillo |
    And Presiona SIGUIENTE
    When Selecciona como cultivo la opcion "Soja Segunda"
    And Completa los VALORES DEL BIEN ASEGURABLE
      | Hectareas | SumaAsegurada | FechaInicioBanda | FechaFinBanda | Disparador |
      |      1000 |           500 | 01/01            | 31/01         |         30 |
    And Se selcciona como opcion de banda la opcion "Triple Banda"
    And Presiona Continuar
    When Se completan los datos del Tomador
      | Tipo   | NroDocumento |
      | D.N.I. |     25750890 |
    When Se completan los datos del Asegurado usando datos del Tomador
    And Presiona Continuar
    When Se completan los datos del Historial
      | SembroAñoPasado | CuantasHectareas | Rinde   | ContratarOtroSeguro | Aseguradora       |
      | Si              |              100 | 100/100 | Si                  | AseguradoraPrueba |
    And Presiona Continuar
    Then Se valida que en el Resumen se muestren correctamente los datos del bien asegurable
    And Se imprime la propuesta

  @login
  Scenario Outline: Se valida login
    Given Un usuario que ingresa a la pagina "https://segurosparametricosagro-desa.npapps.lasegunda.com.ar/"
    And Completa  los campos y presiona ingresar
      | usuario    | password   |
      | testcac    |            |
      |            | testcac    |
      | incorrecto | testcac    |
      | testcac    | incorrecto |
      | incorrecto | incorrecto |
      |            |            |
    Then se corrobora que se valida correctamente los datos ingresados

  @datosMinimos
  Scenario Outline: Se valida la obliegatoriedad de los Datos Mínimos
    Given Un usuario que ingresa a la pagina "https://segurosparametricosagro-desa.npapps.lasegunda.com.ar/"
    And Completa  los campos y presiona ingresar
      | usuario | password |
      | testcac | testcac  |
    When Completa los Datos Mimimos de Cotizacion
      | Localidad      | FormaPago       | CondicionIVA      | CondicionIIBB       |
      | ROSARIO (2000) | 100% AL CONTADO | RESP. MONOTRIBUTO | CONTRIBUYENTE LOCAL |
      | ROSARIO (2000) |                 | RESP. MONOTRIBUTO | CONTRIBUYENTE LOCAL |
      | ROSARIO (2000) | 100% AL CONTADO |                   | CONTRIBUYENTE LOCAL |
      | ROSARIO (2000) | 100% AL CONTADO | RESP. MONOTRIBUTO |                     |
      | ROSARIO (2000) | 100% AL CONTADO | RESP. MONOTRIBUTO | CONTRIBUYENTE LOCAL |
    And Presiona Continuar
    Then se corrobora que se valida correctamente los datos ingresados

  @datosLocalizacion
  Scenario Outline: Se valida la obliegatoriedad de los Datos de Localizacion
    Given Un usuario que ingresa a la pagina "https://segurosparametricosagro-desa.npapps.lasegunda.com.ar/"
    And Completa  los campos y presiona ingresar
      | usuario | password |
      | testcac | testcac  |
    When Completa los Datos Mimimos de Cotizacion
      | Localidad      | FormaPago       | CondicionIVA      | CondicionIIBB       |
      | ROSARIO (2000) | 100% AL CONTADO | RESP. MONOTRIBUTO | CONTRIBUYENTE LOCAL |
    And Presiona Continuar
    And Se logea en AON
    And Presiona NUEVA COTIZACION
    When Completa los DATOS DE LOCALIZACION DEL BIEN ASEGURABLE
      | Provincia  | Departamento | Cuartel   |
      |            | PARANA       | Espinillo |
      | ENTRE RIOS |              | Espinillo |
      | ENTRE RIOS | PARANA       |           |
    Then se corrobora que se valida correctamente los datos ingresados

  @datosValoresBien
  Scenario Outline: Se valida la obliegatoriedad de los Valores del Bien Asegurable
    Given Un usuario que ingresa a la pagina "https://segurosparametricosagro-desa.npapps.lasegunda.com.ar/"
    And Completa  los campos y presiona ingresar
      | usuario | password |
      | testcac | testcac  |
    When Completa los Datos Mimimos de Cotizacion
      | Localidad      | FormaPago       | CondicionIVA      | CondicionIIBB       |
      | ROSARIO (2000) | 100% AL CONTADO | RESP. MONOTRIBUTO | CONTRIBUYENTE LOCAL |
    And Presiona Continuar
    And Se logea en AON
    And Presiona NUEVA COTIZACION
    When Completa los DATOS DE LOCALIZACION DEL BIEN ASEGURABLE
      | Provincia  | Departamento | Cuartel   |
      | ENTRE RIOS | PARANA       | Espinillo |
    And Presiona SIGUIENTE
    When Selecciona como cultivo la opcion "Soja Primera"
    And Completa los VALORES DEL BIEN ASEGURABLE
      | Hectareas | SumaAsegurada | FechaInicioBanda | FechaFinBanda | Disparador |
      |           |           500 | 01/01            | 31/01         |         30 |
      |      1000 |               | 01/01            | 31/01         |         30 |
      |      1000 |           500 | 01/01            | 31/01         |            |
    Then se corrobora que se valida correctamente los datos ingresados

  @datosTomador
  Scenario Outline: Se validan datos Tomador
    Given Un usuario que ingresa a la pagina "https://segurosparametricosagro-desa.npapps.lasegunda.com.ar/"
    And Completa  los campos y presiona ingresar
      | usuario | password |
      | testcac | testcac  |
    When Completa los Datos Mimimos de Cotizacion
      | Localidad      | FormaPago       | CondicionIVA      | CondicionIIBB       |
      | ROSARIO (2000) | 100% AL CONTADO | RESP. MONOTRIBUTO | CONTRIBUYENTE LOCAL |
    And Presiona Continuar
    And Se logea en AON
    And Presiona NUEVA COTIZACION
    When Completa los DATOS DE LOCALIZACION DEL BIEN ASEGURABLE
      | Provincia  | Departamento | Cuartel   |
      | ENTRE RIOS | PARANA       | Espinillo |
    And Presiona SIGUIENTE
    When Selecciona como cultivo la opcion "Soja Primera"
    And Completa los VALORES DEL BIEN ASEGURABLE
      | Hectareas | SumaAsegurada | FechaInicioBanda | FechaFinBanda | Disparador |
      |      1000 |           500 | 01/01            | 31/01         |         30 |
    And Se selcciona como opcion de banda la opcion "Doble Banda"
    And Presiona Continuar
    When Se completan los datos del Tomador
      | Tipo   | NroDocumento |
      | D.N.I. |     25999999 |
      | L.C.   |     25750890 |
      | D.N.I. |              |
    Then se corrobora que se valida correctamente los datos ingresados
