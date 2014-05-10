package es.uah.ieru.scorm2sql;
import javax.swing.UIManager;

import org.apache.log4j.Logger;


/**
 * @author Juan Moreno de Frutos
 * 
 */
public class Scorm2sql {

	static VentanaPrincipal pantalla;
        static Logger logger = (new InicializaLogger("es.uah.ieru.scorm2sql")).getMyLogger();
	
	public static void main(String[] args) {

		logger.info("START APLICATION");
                new JManejadorFichero().leerDirectorio();

	}
}
