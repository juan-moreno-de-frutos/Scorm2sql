
package es.uah.ieru.scorm2sql;

/**
 *
 * @author juan
 */
public class ExceptionOrganizations extends ExceptionChildrenManifest {
    private String mistake;
    
    
    public ExceptionOrganizations(String msg) {
        super(msg);
        mistake= msg; //save the mistake;
    }

    
}
