package ar.com.lasegunda;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import ar.com.lasegunda.utiles.configuracionSpring.TestConfig;
import io.cucumber.java.Before;
import io.cucumber.spring.CucumberContextConfiguration;

@SpringBootTest
@CucumberContextConfiguration
@ContextConfiguration(classes = {TestConfig.class, StartApplication.class}, loader = SpringBootContextLoader.class)
public class CucumberSpringContextConfiguration {
	protected Logger log = LoggerFactory.getLogger(CucumberSpringContextConfiguration.class);
	
	@Before
	public void inicializarContexto() {
		log.info("Se inicializo correctamente el contexto de spring");
	}
}
