
package es.uah.ieru.scorm2sql;

import java.util.ArrayList;

/**
 *
 * @author Juan Moreno de Frutos
 */
public class ValidadorEntradas {
    
    
    public boolean ValidarMaxTimeAllowed(String version){ // Fake Funcion 
         if (version.length()<= 20) {return true;}
            else{return false;}
    }
    
    public boolean ValidarVersion(String version){ 
         if (version.length()<= 20) {return true;}
            else{return false;}
    }
    
    public boolean ValidarSchemaVersion(String schemaVersion){ 
         if (schemaVersion.length()<= 20) {return true;}
            else{return false;}
    }
    
    public boolean ValidarId(String id){
        if (id.length()<= 45) {return true;}
            else{return false;}        
    }
    
    
    public boolean ValidarSchema(String schema){// Li
        if (schema.length()<= 100) {return true;}
            else{return false;}
    }  
    
    
    public boolean ValidarTitle(String title){ 
        if (title.length()<= 200) {return true;}
            else{return false;}
    }
    
    public boolean ValidarStructure(String structure){ 
        if (structure.length()<= 200) {return true;}
            else{return false;}
    }
    
    public boolean ValidarPrerequisites(String prerequisites){ // Like Structure.
        if (prerequisites.length()<= 200) {return true;}
            else{return false;}
    }
    
    public boolean ValidarMasteryscore(String masteryscore){ // Like Structure.
        if (masteryscore.length()<= 200) {return true;}
            else{return false;}
    }
    
        
    public boolean ValidarDataFromLms(String dataFromLms){       
        if (dataFromLms.length()<= 255) {return true;}
            else{return false;}
    }
    
    public boolean ValidarParameters(String param){
        if (param.length()<= 1000) {return true;}
            else{return false;}
    }
    
    public boolean ValidarResourceType(String typeR){ //añadir a que tipo me refiero
        if (typeR.length()<= 1000) {return true;}
            else{return false;} 
    }
    
    public boolean ValidarIdRef(String idRef){ 
        if (idRef.length()<= 2000) {return true;}
            else{return false;}
    }
    
    public boolean ValidarLocation(String location){ 
        if (location.length()<= 2000) {return true;}
            else{return false;}
    }
    
    public boolean ValidarHref(String href){
        if (href.length()<= 2000) {return true;}
            else{return false;}
    }
    
    public boolean ValidarPrerequisiteType(String typeP){ //añadir a que tipo me refiero
        ArrayList<String> vocabulary;
        vocabulary = new ArrayList<String>();
        vocabulary.add("aicc_script");
        
        if (typeP.equals(vocabulary.remove(vocabulary.size()-1))) {return true;}
            else{return false;} 
    }  
     
    
    public boolean ValidarTimeLimitAction(String timeLimitAction){       
        ArrayList<String> vocabulary;
        vocabulary = new ArrayList<String>();
        vocabulary.add("exit,message");
        vocabulary.add("exit,no message");
        vocabulary.add("continue,message");
        vocabulary.add("continue,no message");
        
        if (timeLimitAction.equals(vocabulary.remove(vocabulary.size()-1))) {return true;}
            else{return false;} 
    }
    
    public boolean ValidarScormtype(String scormType){       
        ArrayList<String> vocabulary;
        vocabulary = new ArrayList<String>();
        Boolean out=false;
        vocabulary.add("sco");
        vocabulary.add("asset");  
        
        while (!vocabulary.isEmpty()){
           if (scormType.equals(vocabulary.remove(vocabulary.size()-1))) {out= true;}
        }
        return out;
    }
    
}
