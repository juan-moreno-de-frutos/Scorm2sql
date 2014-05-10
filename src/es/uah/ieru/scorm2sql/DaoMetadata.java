/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uah.ieru.scorm2sql;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author Juan Moreno de Frutos
 */
public class DaoMetadata {
    
    
    public int insertarMetadata(BeanMetadata metadata) throws IOException{
       Connection conn= (new ConexionBD()).getConexion();
       PreparedStatement p_stmt=null;
       String altaDatos=null;
       int idMetadata = -1;
       
     try {
           altaDatos ="INSERT INTO metadata (metadata_type,metadata_schema, metadata_schemaversion, location, metadata) VALUES(?,?,?,?,?)";
           p_stmt = conn.prepareStatement(altaDatos, Statement.RETURN_GENERATED_KEYS);
           p_stmt.setString(1,metadata.getType());
           p_stmt.setString(2,metadata.getSchema());
           p_stmt.setString(3,metadata.getSchemaversion());
           p_stmt.setString(4,metadata.getLocation());
           p_stmt.setInt(5,metadata.getMeta_data());
           
           Scorm2sql.logger.debug("Query: "+p_stmt.toString()); 
           p_stmt.executeUpdate();
           
           //Get AutoIncrementate ID_Metadata for its parents. 
           ResultSet rs = p_stmt.getGeneratedKeys();           
           if (rs.next()){
        	   idMetadata=rs.getInt(1);
           }           
           System.out.println("The metadata has been inserted with id: " + idMetadata);
           Scorm2sql.logger.debug("The metadata has been inserted with id: " + idMetadata);
           
           
          
           
                      
        }catch (Exception ex) {
           MensajeError.mostrar("problems inserting a metadata element . " + ex.getMessage());
           Scorm2sql.logger.debug("problems inserting a metadata element . " + ex.getMessage());
        }
     
        return idMetadata;
    }
    
    
    
    
}
