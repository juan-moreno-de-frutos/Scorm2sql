
package es.uah.ieru.scorm2sql;

/**
 *
 * @author juan
 */
public class ExceptionResources extends ExceptionChildrenManifest{
    private String mistake;
    
    
    public ExceptionResources(String msg) {
        super(msg);
        mistake= msg; //save the mistake;
    }

    
}
