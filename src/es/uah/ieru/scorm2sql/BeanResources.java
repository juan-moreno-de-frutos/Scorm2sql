/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uah.ieru.scorm2sql;

import java.util.ArrayList;

/**
 *
 * @author juan
 */
public class BeanResources {
    
    private String base;
    private ArrayList<BeanResource> Resource;

    
    
    public BeanResources() {
        this.base = null;
        this.Resource= new ArrayList<BeanResource>();
    }

    
    
    
    
    public ArrayList<BeanResource> getResource() {
        return Resource;
    }

    public void setResource(ArrayList<BeanResource> Resource) {
        this.Resource = Resource;
    } 
    
    public void setOneResource(BeanResource Resource) {
        this.Resource.add(Resource);
    }    

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    @Override
    public String toString() {
        return "BeanResources{" + "base=" + base + ", Resource=" + Resource + '}';
    }
    
    
    
}
