package ar.com.lasegunda.utiles.soporte;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.search.FlagTerm;

public class ManejoEmail {
    
    /*------------------------------------------------------------------------------------------------
    |  Método: obtenerTextoDeMensajeMultiparte(Multipart msjMultiparte)
    |
    |  Autor: Nicolas Rioseco
    |
    |  Definición: Toma una mensaje multiparte y devuelve un string.
    |			   Se utiliza para obtener el texto del cuerpo de un correo de tipo multipart
    |
    |  Parámetros:
    |	Multipart msjMultiparte -- mensaje multiparte
    |
    |  Retorna:
    |	String -- Retorna el mensaje multiparte convertido a string.
    *------------------------------------------------------------------------------------------------*/

	private String obtenerTextoDeMensajeMultiparte(Multipart msjMultiparte) throws Exception {
		String strTexto = "";
		// Extraemos cada una de las partes.
	   for (int j=0;j<msjMultiparte.getCount();j++)
	   {
	      Part unaParte = msjMultiparte.getBodyPart(j);

	      // Volvemos a analizar cada parte de la MultiParte
	      if (unaParte.isMimeType("text/*")){
	    	  // mensaje de texto simple
	    	  strTexto = strTexto + "\n" + unaParte.getContent();
	      }
	      if (unaParte.isMimeType("multipart/*")) {
	    	  // mensaje compuesto
	    	  strTexto = strTexto + obtenerTextoDeMensajeMultiparte((Multipart) unaParte.getContent());
	   	  }  	
	   }
		
	   return strTexto;
	}
	
	/*------------------------------------------------------------------------------------------------
    |  Método: validarCorreo(String Asunto, String Cuerpo, String Destinatario)
    |
    |  Autor: Nicolas Rioseco
    |
    |  Definición: Valida que exista un correo con los datos especificados.
    |			   Se verificar los correos no leidos recibidos en correodesa a partir de una fecha y hora.
    |
    |  Parámetros:
    |	String Asunto -- Asunto del correo
    |	String Cuerpo -- Cuerpo del correo
    |	String Destinatario -- Destinatario real del correo
    |	String carpetaProyecto -- Nombre de la carpeta creada dentro de la cuenta de email para el proyecto
    |	Date minFecRecibido -- Se verificar todos los correos no leidos recibidos a partir de esta fecha y hora
    |
    *------------------------------------------------------------------------------------------------*/

	public void validarMensajeCorreo(String asunto, String cuerpo, String destinatario, String carpetaProyecto, Date minFecRecibido) throws Exception {

		String host = "imap.gmail.com";
	    String storeType = "imaps";
	    String correo = "pruebassqa52@gmail.com";
	    String password = "adminqaL2";
	      
	    // Properties es una clase java que nos permite guardar datos de tipo texto
	    // La usamos para guardar los datos de configuracion
	    Properties prop = new Properties();
	      
	    prop.setProperty("mail.store.protocol", "imaps");
	      
	    // Obtener la instancia de sesión
	    Session sesion = Session.getInstance(prop);
	    // sesion.setDebug(true);
	    // Lo de setDebug(true) es sólo para obtener más información en la pantalla de lo que se está haciendo. Cuando nuestro programa funcione correctamente, podemos quitar esta línea.

	    // Obtener el Store y el Folder de Inbox
	    Store store = sesion.getStore(storeType);
	    store.connect(host, correo, password);
	      
	    //Abrimos la carpeta del proyecto 
	    Folder folder = store.getFolder(carpetaProyecto);
	    folder.open(Folder.READ_WRITE);
	      
	    //leer mensajes no leidos
	    Message [] mensajes = folder.search(new FlagTerm(new Flags(Flags.Flag.SEEN), false));

	    boolean mensajeValido = false;
	    //Recorremos los mensajes no leidos recibidos en los ultimos dos minutos 
	    for (int i=0;i<mensajes.length;i++)
	    {
	    	
	    	DateFormat formatFecha = new SimpleDateFormat("yyyyMMddHHmmss");
	 		Integer FechaMinima = Integer.parseInt(formatFecha.format(minFecRecibido));
	 		Integer FechaCorreo = Integer.parseInt(formatFecha.format(mensajes[i].getReceivedDate()));
	    	if (FechaCorreo < FechaMinima) {
		    	break;
	    	}
	    	
	    	//String msjDe =  mensajes[i].getFrom()[0].toString(); 
	    	String msjAsunto = mensajes[i].getSubject();
	    	String msjCuerpo = "";
	    	
	        if (mensajes[i].isMimeType("text/*"))
	        {
	        	msjCuerpo = mensajes[i].getContent().toString();
	        }
	        if (mensajes[i].isMimeType("multipart/*")) 
	        {
	        	msjCuerpo = obtenerTextoDeMensajeMultiparte((Multipart) mensajes[i].getContent());
	        }
	 
	    	if (msjAsunto == asunto && msjCuerpo.contains(cuerpo) && msjCuerpo.contains("DESTINATARIOS REALES: " + destinatario)) {
	    		mensajeValido = true;	
	    	}

	        //marco el correo como leido
	        mensajes[i].setFlag(Flags.Flag.SEEN,true); 
	        
	    }

	    // Cerrar los objetos folder y store
	    folder.close(false);
	    store.close();
	    
	    if (mensajeValido == false) {
	    	throw new Exception("No se encontró el correo.");
	    }
	    
	}
	
}
