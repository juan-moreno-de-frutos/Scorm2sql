/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uah.ieru.scorm2sql;

/**
 *
 * @author juan
 */
public class ExceptionChildrenManifest extends ExceptionManifest{
    private String mistake;
    
    
    public ExceptionChildrenManifest(String msg) {
        super(msg);
        mistake= msg; //save the mistake;
    }

    
}
