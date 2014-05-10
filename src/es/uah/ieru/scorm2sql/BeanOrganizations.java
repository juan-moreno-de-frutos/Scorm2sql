/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uah.ieru.scorm2sql;

import java.util.ArrayList;

/**
 *
 * @author Juan Moreno de Frutos
 */
public class BeanOrganizations {
    
    private String defaul; //attribute default.
    private ArrayList<BeanOrganization> Organization;

    
    
    public BeanOrganizations() {
        this.defaul = null;
        this.Organization = new ArrayList<BeanOrganization>();
    }

    
    
    
    
    public ArrayList<BeanOrganization> getOrganization() {
        return Organization;
    }

    public void setOrganization(ArrayList<BeanOrganization> Organization) {
        this.Organization = Organization;
    }
    public void setOneOrganization(BeanOrganization Organization) {
        this.Organization.add(Organization);
    }
          

    public String getDefaul() {
        return defaul;
    }

    public void setDefaul(String defaul) {
        this.defaul = defaul;
    }

    @Override
    public String toString() {
        return "BeanOrganizations{" + "defaul=" + defaul + ", Organization=" + Organization + '}';
    }
    
    
    
}
