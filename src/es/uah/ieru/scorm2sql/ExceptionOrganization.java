
package es.uah.ieru.scorm2sql;

/**
 *
 * @author juan
 */
public class ExceptionOrganization extends ExceptionOrganizations{
    private String mistake;
    
    
    public ExceptionOrganization(String msg) {
        super(msg);
        mistake= msg; //save the mistake;
    }

    
}
