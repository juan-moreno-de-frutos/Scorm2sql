/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uah.ieru.scorm2sql;

import java.util.ArrayList;

/**
 * BeanResource.
 * Class to get and set Resource elements.
 * 
 * @author Juan Moreno de Frutos
 */
public class BeanResource {
        

    private String identifier;
    private String base;
    private String type;
    private String scormtype;
    private String href;
    private BeanMetadata metadata;
    private ArrayList<BeanFile> File;
    private ArrayList<BeanDependency> Dependency;

    
    
    
    public BeanResource() {
        this.identifier = null;
        this.base = null;
        this.type = null;
        this.scormtype = null;
        this.href = null;
        this.metadata = null;
        this.File = new ArrayList<BeanFile>();
        this.Dependency = new ArrayList<BeanDependency>();
    }

    
    
    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public ArrayList<BeanDependency> getDependency() {
        return Dependency;
    }

    public void setDependency(ArrayList<BeanDependency> Dependency) {
        this.Dependency = Dependency;
    }
    
    public void setOneDependency(BeanDependency Dependency) {
        this.Dependency.add(Dependency);
    }

    public ArrayList<BeanFile> getFile() {
        return File;
    }

    public void setFile(ArrayList<BeanFile> File) {
        this.File = File;
    }
    
    public void setOneFile(BeanFile File) {
        this.File.add(File);
    } 


    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }


    public BeanMetadata getMetadata() {
        return metadata;
    }

    public void setMetadata(BeanMetadata metadata) {
        this.metadata = metadata;
    }

    public String getScormtype() {
        return scormtype;
    }

    public void setScormtype(String scormtype) {
        this.scormtype = scormtype;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }
    
    
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "BeanResource{" + "identifier=" + identifier + ", base=" + base + ", type=" + type + ", scormtype=" + scormtype + ", href=" + href + ", metadata=" + metadata + ", File=" + File + ", Dependency=" + Dependency + '}';
    }

    
    
}
