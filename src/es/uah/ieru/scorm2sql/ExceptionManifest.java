
package es.uah.ieru.scorm2sql;

/**
 *
 * @author juan
 */
public class ExceptionManifest extends Exception {
    
    private String mistake;
    
    
    public ExceptionManifest(String msg) {
        super(msg);
        mistake= msg; //save the mistake;
    }

    public String getMistake() {
        return mistake;
    }
    
    
}


