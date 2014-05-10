
package es.uah.ieru.scorm2sql;

/**
 *
 * @author juan
 */
public class BeanFile {
    
    private String href;
    private BeanMetadata metadata;

    
    
    
    public BeanFile() {
        this.href = null;
        this.metadata = null;
    }

    
    
    
    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public BeanMetadata getMetadata() {
        return metadata;
    }

    public void setMetadata(BeanMetadata metadata) {
        this.metadata = metadata;
    }

    @Override
    public String toString() {
        return "BeanFile{" + "href=" + href + ", metadata=" + metadata + '}';
    }
   
    
    
}
