
package es.uah.ieru.scorm2sql;


import java.io.*;
import java.util.*;
import java.util.zip.*;


/**
 *
 * @author Juan Moreno de Frutos
 */
public class JUnZip {
    
   private int BUFFER = 2048;
   
      
public int jUnZip (String Zip, String DirOut){
    try {
            Config cfg = new Config();
            File dirDestino = new File(DirOut);
            BufferedOutputStream dest = null;
            FileInputStream fis = new FileInputStream(Zip);
            
            ZipInputStream zis = new ZipInputStream(new BufferedInputStream(fis));
            FileOutputStream fos = null;
            ZipEntry entry;
            int count;
            int index = 0;
            byte data[] = new byte[BUFFER];
            String rutaarchivo = null;
            while(zis.available()==1) {//(entry = zis.getNextEntry()) != null
               
                entry = zis.getNextEntry();
                rutaarchivo = entry.getName();
                
                index = rutaarchivo.indexOf("/");
                rutaarchivo = rutaarchivo.substring(index+1);
                
               
                if((rutaarchivo.trim().length() > 0)&&(rutaarchivo.equals("imsmanifest.xml")))
                {
                    Scorm2sql.logger.debug("Rute: " +rutaarchivo);
                    // write the files 
                    try {
                        dest = null;
                        File fileDest = new File(dirDestino.getAbsolutePath() + "/" + rutaarchivo);
                        Scorm2sql.logger.debug("File: " +fileDest);
                       
                            fos = new FileOutputStream(fileDest);
                            dest = new BufferedOutputStream(fos, BUFFER);
                            while ((count = zis.read(data, 0, BUFFER)) != -1)
                            {
                                dest.write(data, 0, count);
                            }
                            dest.flush();

                    } finally {
                        try { if(dest != null) dest.close(); } 
                          catch(Exception e) { 
                              Scorm2sql.logger.debug("ERROR: problems in unZiping.");
                              return -1;
                          }
                    }
                }
            }
            zis.close();
            return 0;
        } catch(Exception e) {  
             return -1;
        }
    }
    
}
