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
public class BeanManifest {
    
    private int id;
    private String identifier;
    private String version;
    private String base;
    private BeanMetadata Metadata;
    private BeanOrganizations Organizations;
    private BeanResources Resources;
    private ArrayList<BeanManifest> Manifest;
    private String nameResource;

    
    
    
    public BeanManifest() {
        this.id = -1;
        this.identifier = null;
        this.version = null;
        this.base = null;
        this.Metadata = null;//new BeanMetadata();
        this.Organizations = null;//new BeanOrganizations();
        this.Resources = null;//new BeanResources();
        this.Manifest=new ArrayList<BeanManifest>();        
    }
    
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }    
    
    public ArrayList<BeanManifest> getManifest() {
        return Manifest;
    }

    public void setManifest(ArrayList<BeanManifest> Manifest) {
        this.Manifest = Manifest;
    }
    public void setOneManifest(BeanManifest Manifest) {
        this.Manifest.add(Manifest);
    } 
   
   
    public BeanMetadata getMetadata() {
        return Metadata;
    }

    public void setMetadata(BeanMetadata Metadata) {
        this.Metadata = Metadata;
    }

    public BeanOrganizations getOrganizations() {
        return Organizations;
    }

    public void setOrganizations(BeanOrganizations Organizations) {
        this.Organizations = Organizations;
    }

    public BeanResources getResources() {
        return Resources;
    }

    public void setResources(BeanResources Resources) {
        this.Resources = Resources;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    
    
    @Override
    public String toString() {
        return "BeanManifest{" + "id=" + id + ", identifier=" + identifier + ", version=" + version + ", base=" + base + ", Metadata=" + Metadata + ", Organizations=" + Organizations + ", Resources=" + Resources + ", Manifest=" + Manifest + ", nameResource=" + nameResource + '}';
    }
    
    
}
