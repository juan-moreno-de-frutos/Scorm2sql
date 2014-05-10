/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uah.ieru.scorm2sql;

/**
 *
 * @author juan
 */
public class BeanMetadata {
    
    private String type;
    private String schema;
    private String schemaversion;
    private String location;
    private int meta_data;

    
    
    
    public BeanMetadata() {
        this.type = null;
        this.schema = null;
        this.schemaversion = null;
        this.location = null;
        this.meta_data = -1;
    }  
    
       
    

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }          
    
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getMeta_data() {
        return meta_data;
    }

    public void setMeta_data(int meta_data) {
        this.meta_data = meta_data;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public String getSchemaversion() {
        return schemaversion;
    }

    public void setSchemaversion(String schemaversion) {
        this.schemaversion = schemaversion;
    }

    @Override
    public String toString() {
        return "BeanMetadata{" + "type=" + type + ", schema=" + schema + ", schemaversion=" + schemaversion + ", location=" + location + ", meta_data=" + meta_data + '}';
    }

    
    
    
}
