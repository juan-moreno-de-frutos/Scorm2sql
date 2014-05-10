/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uah.ieru.scorm2sql;

/**
 *
 * @author juan
 */
public class BeanDependency {
    
    private String identifierref;

    
    public BeanDependency() {
        this.identifierref = null;
    }

    
    
    
    public String getIdentifierref() {
        return identifierref;
    }

    public void setIdentifierref(String identifierref) {
        this.identifierref = identifierref;
    }

    @Override
    public String toString() {
        return "BeanDependency{" + "identifierref=" + identifierref + '}';
    }
    
    
}
