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
public class BeanOrganization {
    private String identifier;
    private String structure;
    private String title;
    private ArrayList<BeanItem> item;
    private BeanMetadata metadata;

    
    
    
    public BeanOrganization() {
        this.identifier = null;
        this.structure = null;
        this.title = null;
        this.item= new ArrayList<BeanItem>();
        this.metadata = null;
    }
    
    
    
    
    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public ArrayList<BeanItem> getItem() {
        return item;
    }

    public void setItem(ArrayList<BeanItem> item) {
        this.item = item;
    }
    
    public void setOneItem(BeanItem subItem) {
        this.item.add(subItem);
    }

    public BeanMetadata getMetadata() {
        return metadata;
    }

    public void setMetadata(BeanMetadata metadata) {
        this.metadata = metadata;
    }

    public String getStructure() {
        return structure;
    }

    public void setStructure(String structure) {
        this.structure = structure;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "BeanOrganization{" + "identifier=" + identifier + ", structure=" + structure + ", title=" + title + ", item=" + item + ", metadata=" + metadata + '}';
    }

    
            
}
