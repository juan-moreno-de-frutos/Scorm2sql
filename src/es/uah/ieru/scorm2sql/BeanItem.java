
package es.uah.ieru.scorm2sql;

import java.sql.Time;
import java.util.ArrayList;

/**
 *
 * @author juan
 */
public class BeanItem {
    private String identifier;
    private String identifierref;
    private Boolean isvisible;
    private String parameters;
    private String title;
    private ArrayList<BeanItem> item;
    private BeanMetadata metadata;
    private String prerequisites;
    private String prerequisites_type;
    private String maxtimeallowed;
    private String timelimitaction;
    private String datafromlms;
    private String masteryscore;

    public BeanItem() {
        this.identifier = null;
        this.identifierref = null;
        this.isvisible = null;
        this.parameters = null;
        this.title = null;
        this.item = new ArrayList<BeanItem>();
        this.metadata = null;
        this.prerequisites = null;
        this.prerequisites_type = null;
        this.maxtimeallowed = null;
        this.timelimitaction = null;
        this.datafromlms = null;
        this.masteryscore = null;
    }  
    
    
       
    
    
    
    public String getDatafromlms() {
        return datafromlms;
    }

    public void setDatafromlms(String datafromlms) {
        this.datafromlms = datafromlms;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getIdentifierref() {
        return identifierref;
    }

    public void setIdentifierref(String identifierref) {
        this.identifierref = identifierref;
    }

    public Boolean getIsvisible() {
        return isvisible;
    }

    public void setIsvisible(Boolean isvisible) {
        this.isvisible = isvisible;
    }

    public ArrayList<BeanItem> getItem() {
        return item;
    }

    public void setItem(ArrayList<BeanItem> item) {
        this.item = item;
    }
    
    public void setOneItem(BeanItem item) {
        this.item.add(item);
    }

    public String getMasteryscore() {
        return masteryscore;
    }

    public void setMasteryscore(String masteryscore) {
        this.masteryscore = masteryscore;
    }

    public String getMaxtimeallowed() {
        return maxtimeallowed;
    }

    public void setMaxtimeallowed(String maxtimeallowed) {
        this.maxtimeallowed = maxtimeallowed;
    }

    public BeanMetadata getMetadata() {
        return metadata;
    }

    public void setMetadata(BeanMetadata metadata) {
        this.metadata = metadata;
    }

    public String getParameters() {
        return parameters;
    }

    public void setParameters(String parameters) {
        this.parameters = parameters;
    }

    public String getPrerequisites() {
        return prerequisites;
    }

    public void setPrerequisites(String prerequisites) {
        this.prerequisites = prerequisites;
    }

    public String getPrerequisites_type() {
        return prerequisites_type;
    }

    public void setPrerequisites_type(String prerequisites_type) {
        this.prerequisites_type = prerequisites_type;
    }

    public String getTimelimitaction() {
        return timelimitaction;
    }

    public void setTimelimitaction(String timelimitaction) {
        this.timelimitaction = timelimitaction;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "BeanItem{" + "identifier=" + identifier + ", identifierref=" + identifierref + ", isvisible=" + isvisible + ", parameters=" + parameters + ", title=" + title + ", item=" + item + ", metadata=" + metadata + ", prerequisites=" + prerequisites + ", prerequisites_type=" + prerequisites_type + ", maxtimeallowed=" + maxtimeallowed + ", timelimitaction=" + timelimitaction + ", datafromlms=" + datafromlms + ", masteryscore=" + masteryscore + '}';
    }
    
    
    
            
    
}
