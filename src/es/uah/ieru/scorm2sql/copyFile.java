/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uah.ieru.scorm2sql;

/**
 *
 * @author juan
 */

import java.io.IOException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

public class copyFile {
     
    public static void copyErrorFile(String sourceString) throws IOException {
        
        Config cfg = new Config();
        String destPath = cfg.getProperty("ERRORSPATH");
        if ( destPath.charAt(destPath.length()-1)!='/'){ destPath=destPath+"/";}
        
        String fichero = sourceString.split("/")[sourceString.split("/").length -1];
        
        File origenFile= new File(sourceString); 
        System.out.println("Fichero erróneo enviado en: "+ destPath+fichero);
        File destFile= new File (destPath.trim()+fichero);
        
        if(!destFile.exists()) {
            destFile.createNewFile();
        }

        FileChannel origen = null;
        FileChannel destino = null;
        try {
            origen = new FileInputStream(origenFile).getChannel();
            destino = new FileOutputStream(destFile).getChannel();

            long count = 0;
            long size = origen.size();             
            while((count += destino.transferFrom(origen, count, size-count))<size);
        }
        finally {
            if(origen != null) {
                origen.close();
            }
            if(destino != null) {
                destino.close();
            }
        }
    }
    
}
