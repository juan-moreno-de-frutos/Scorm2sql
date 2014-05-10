/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uah.ieru.scorm2sql;


import java.io.File;
import java.io.FilenameFilter;

/**
 * Class that create de Filefilter to search resources on a specific folder.
 * @author  Juan Moreno de Frutos *  
 */

public class FileExtensionFilter implements FilenameFilter{
   private String ext="*";
   
   /**
    * Get the extension to filter
    * @param ext 
    */
   public FileExtensionFilter(String ext){
     this.ext = ext;
   }
   
   /**
    * Check if the file metches the filter
    * @param dir
    * @param name
    * 
    */
   public boolean accept(File dir, String name){
     if (name.endsWith(ext))
       return true;
     return false;
   }
}