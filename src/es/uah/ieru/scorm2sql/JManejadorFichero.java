package es.uah.ieru.scorm2sql;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdom.Document;
import org.jdom.input.SAXBuilder;

/**
 *
 * @author Juan Moreno de Frutos
 */
public class JManejadorFichero {


    
    public void leerDirectorio(){
        
        BeanManifest manifest;
        Document doc;
        File[] ficheros=null;
        Connection conn=null;
        int x = 0;
        try{
            /** Create the config objet */
            Config cfg = new Config();

/* Pendiente crear boton de selección xml/zip */
            
            /** Get the config parameters */
            String dirPath = cfg.getProperty("XMLPATH");
            System.out.println(cfg.getProperty("XMLPATH"));
            Scorm2sql.logger.info(cfg.getProperty("XMLPATH"));
            File dir = new File(dirPath);
            
            
//*********************************
//*  En caso de escanear desde ZIP            
//************                        
//*            FileExtensionFilter filter = new FileExtensionFilter(".zip");
//*            ficheros=dir.listFiles(filter);           
//*            JUnZip unZip= new JUnZip();  
//******************          
            
            FileExtensionFilter filter = new FileExtensionFilter(".xml");
            ficheros=dir.listFiles(filter);           
             
            
            /*** Checking DataBase conexion ***/
            conn= (new ConexionBD()).getConexion();
            if (conn!=null){            
            
                Scorm2sql.logger.debug("Correct Parameters");

                for (x = 0; x < ficheros.length; x++)
                { try{  
                        doc=leerManifest(ficheros[x].getAbsolutePath());               
                        Scorm2sql.logger.info("Reading imsmanifest.xml: "+ ficheros[x].getAbsolutePath()+" ...");
                        manifest=new TratarManifest().leer(doc, ficheros[x].getAbsolutePath()); 
                        if (manifest!= null){
                            Scorm2sql.logger.info("Manifest has been obtained correctly.");
                            System.out.println("Manifest has been obtained correctly.");

                            if((new DaoManifest().insertarManifest(manifest,-1))!=-1){                        
                                Scorm2sql.logger.info("Manifest and its elemts have been inserted correctly.");
                                System.out.println("Manifest and its elemts have been inserted correctly.");

                            }else{                            
                                Scorm2sql.logger.info("Error:Manifest and its elemts haven't been inserted correctly.");
                                System.out.println("Error: Manifest and its elemts haven't been inserted correctly.");                            
                                copyFile.copyErrorFile(ficheros[x].getAbsolutePath());
                            }
                         } else {
                            Scorm2sql.logger.info("Error: Manifest hasn't been obtained correctly.");
                            System.out.println("Error: Manifest hasn't been obtained correctly.");
                            copyFile.copyErrorFile(ficheros[x].getAbsolutePath());
                         }

                        /*Al terminar con un recurso, se borra de la carpeta contenedora. */
                        /*(Esto evita tener que repetir desde el principio en caso de error no contemplado)*/
                        if (ficheros[x].delete())
                        {   Scorm2sql.logger.info("Deleted  imsmanifest.xml file.");
                            System.out.println("Deleted  imsmanifest.xml file.");
                        }else{
                            Scorm2sql.logger.info("ERROR in deleting imsmanifest.xml file.");
                            System.out.println("ERROR in deleting imsmanifest.xml file.");
                        }
                    
                    }catch(Exception e){
//                        MensajeError.mostrar(e.getMessage());
                        Scorm2sql.logger.info("ERROR: " + e.getMessage());
                        try {
                            /*En caso de error, muevo el recurso a la carpera errores. */
                            copyFile.copyErrorFile(ficheros[x].getAbsolutePath());
                            ficheros[x].delete();
                        } catch (IOException ex) {
//                            MensajeError.mostrar("Fallo al copiar el fichero erroneo: "+ex.getMessage());
                            Scorm2sql.logger.info("Fallo al copiar el fichero erroneo: " + ex.getMessage()); 
                        }

                    }
                    
                }
                
//*********************************
//*  En caso de escanear desde ZIP            
//************                  
//                    Scorm2sql.logger.info("Analyzing Zipfile: "+ficheros[x].getAbsolutePath());
//                    System.out.println("Analyzing Zipfile: "+ficheros[x].getAbsolutePath());
//
//                 // unZip the imsmanifest file.               
//                    if (unZip.jUnZip(ficheros[x].getAbsolutePath(), dirPath)==0){ 
//                        Scorm2sql.logger.info("Created a unZip temporal imsmanifest.xml file");
//                        System.out.println("Created a unZip temporal imsmanifest.xml file");
//
//                        doc=leerManifest(dirPath+"/imsmanifest.xml");               
//                        Scorm2sql.logger.info("Reading imsmanifest.xml ...");
//                        manifest=new TratarManifest().leer(doc, dirPath); 
//                        if (manifest!= null){
//                            Scorm2sql.logger.info("Manifest has been obtained correctly.");
//                            System.out.println("Manifest has been obtained correctly.");
//
//                            //if(manifest.getId()!=-1){new DaoManifest().insertarManifest(manifest,-1);}
//                            if((new DaoManifest().insertarManifest(manifest,-1))!=-1){                        
//                                Scorm2sql.logger.info("Manifest and its elemts have been inserted correctly.");
//                                System.out.println("Manifest and its elemts have been inserted correctly.");
//                            }else{
//                                Scorm2sql.logger.info("Error:Manifest and its elemts haven't been inserted correctly.");
//                                System.out.println("Error: Manifest and its elemts haven't been inserted correctly.");
//                            }
//
//
//                        }else{
//                        Scorm2sql.logger.info("Error: Manifest hasm't been obtained correctly.");
//                        System.out.println("Error: Manifest hasn't been obtained correctly.");
//                        }
//
//                        File fichero =new File(dirPath+"/imsmanifest.xml");
//                        if (fichero.delete())
//                            {   Scorm2sql.logger.info("Deleted unZip imsmanifest.xml file.");
//                                System.out.println("Deleted unZip imsmanifest.xml file.");
//                            }else{
//                            Scorm2sql.logger.info("ERROR in deleting unZip imsmanifest.xml file.");
//                            System.out.println("ERROR in deleting unZip imsmanifest.xml file.");
//                            } 
//
//                    }else{
//                        Scorm2sql.logger.info("UnZip error with: "+ficheros[x].getAbsolutePath());
//                        System.out.println("UnZip error with: "+ficheros[x].getAbsolutePath());
//                    }
//                }
//****************************
                
            }else{
                Scorm2sql.logger.info("Conexion error with DataBase");
                System.out.println("Conexion error with DataBase");
            }
            
            
        }catch(Exception e){
//            MensajeError.mostrar(e.getMessage());
            Scorm2sql.logger.info("ERROR: " + e.getMessage());
            try {                
                /*En caso de error, muevo el recurso a la carpera errores. */
                copyFile.copyErrorFile(ficheros[x].getAbsolutePath());
                ficheros[x].delete();
            } catch (IOException ex) {
                MensajeError.mostrar("Fallo al copiar el fichero erroneo: "+ex.getMessage());
                Scorm2sql.logger.info("Fallo al copiar el fichero erroneo: " + ex.getMessage()); 
            }
        }
    }
            
    
    
    	public Document leerManifest(String file) {
		Document doc = null;
		try {
                    SAXBuilder builder = new SAXBuilder();
                    builder.setValidation(false);
                    builder.setIgnoringElementContentWhitespace(true);
                    doc = builder.build(file);
                    Scorm2sql.logger.debug("Correct XML");
	        
		} catch (Exception e){			
			Scorm2sql.logger.info("ERROR: " + e.getMessage());                        
		}
		return doc;	
		
	}
	
	

}