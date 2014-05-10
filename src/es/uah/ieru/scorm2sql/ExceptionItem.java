package es.uah.ieru.scorm2sql;

/**
 *
 * @author juan
 */
public class ExceptionItem extends ExceptionOrganization{
    private String mistake;
    
    
    public ExceptionItem(String msg) {
        super(msg);
        mistake= msg; //save the mistake;
    }

    
}