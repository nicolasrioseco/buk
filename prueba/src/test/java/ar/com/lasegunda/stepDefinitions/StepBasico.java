package ar.com.lasegunda.stepDefinitions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import ar.com.lasegunda.utiles.soporte.ManejoErrores;

public class StepBasico {

    @Autowired
    protected ManejoErrores manejoErrores;

    protected static final Logger log = LoggerFactory.getLogger(StepBasico.class);

    public void manejarError(Exception e) {
        String message = manejoErrores.getMensajeDeExcepcion(e);
        manejoErrores.adherirErrorAlReporteAllure(message);
        log.error(message);
	}
    
}
