package es.uah.ieru.scorm2sql;
import javax.swing.JOptionPane;

/**
 * @author Juan Moreno de Frutos
 * 
 */
public class MensajeError extends JOptionPane {
    public MensajeError() {
        super();
    }
    public static void mostrar(String msg){
        showMessageDialog(Scorm2sql.pantalla, msg, "", JOptionPane.ERROR_MESSAGE);
    }
}
