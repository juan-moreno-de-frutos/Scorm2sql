
package es.uah.ieru.scorm2sql;

/**
 *
 * @author juan
 */
public class ExceptionResource extends ExceptionResources{
    private String mistake;
    
    
    public ExceptionResource(String msg) {
        super(msg);
        mistake= msg; //save the mistake;
    }
 
    
}
