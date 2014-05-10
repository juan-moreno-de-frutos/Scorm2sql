package es.uah.ieru.scorm2sql;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;


public class InicializaLogger {

	private Logger logger;
	
	public InicializaLogger(String clase){
		
		PropertyConfigurator.configure("src/es/uah/ieru/scorm2sql/log4j.properties");
		logger = Logger.getLogger(clase);
	}
	
	public Logger getMyLogger(){
		return logger;
	}
}
