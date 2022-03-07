package ar.com.lasegunda.utiles.driversBrowser;

import java.net.MalformedURLException;

import org.openqa.selenium.WebDriver;
import org.springframework.stereotype.Component;


@Component
public abstract class DriverManager {

	protected WebDriver driver;
	
	protected abstract void createDriver() throws MalformedURLException;
	
	public WebDriver getDriver() throws MalformedURLException {
		if(driver == null) {
			createDriver();
		}
		
		return driver;
	}
	
}
