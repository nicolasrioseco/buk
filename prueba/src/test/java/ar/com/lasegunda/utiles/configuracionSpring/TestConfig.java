package ar.com.lasegunda.utiles.configuracionSpring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@ComponentScan(basePackages = "ar.com.lasegunda")
@PropertySource("classpath:/configProperties/application-${ambiente:uat}.properties")
public class TestConfig {

}
