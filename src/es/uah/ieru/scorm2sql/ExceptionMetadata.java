
package es.uah.ieru.scorm2sql;

/**
 *
 * @author juan
 */
public class ExceptionMetadata extends ExceptionChildrenManifest {
    private String mistake;
    
    
    public ExceptionMetadata(String msg) {
        super(msg);
        mistake= msg; //save the mistake;
    }

    
}
