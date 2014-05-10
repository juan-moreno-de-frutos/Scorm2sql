/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uah.ieru.scorm2sql;

import java.io.IOException;
import java.sql.*;

/**
 *
 * @author juan
 */
public class DaoFile {

    void insertFile(BeanFile file, int idResource) throws IOException {
       Connection conn= (new ConexionBD()).getConexion();
       PreparedStatement p_stmt=null;
       String altaDatos=null;
       int idMetadata=-1;
       
       try {

           //insert metadata
           if (file.getMetadata()!= null) {                
                DaoMetadata daoMetadata = new DaoMetadata();
                idMetadata = daoMetadata.insertarMetadata(file.getMetadata());
           }
           
           altaDatos ="INSERT INTO file (id_resource, href,id_metadata) VALUES(?,?,?)";            
           p_stmt = conn.prepareStatement(altaDatos, Statement.RETURN_GENERATED_KEYS);
           p_stmt.setInt(1,idResource);
           p_stmt.setString(2,file.getHref());         
           if (idMetadata != -1){  p_stmt.setInt(3, idMetadata);}
                  else{  p_stmt.setString(3,null);}  
           
           Scorm2sql.logger.debug("Query: "+p_stmt.toString());       
           p_stmt.executeUpdate();
           ResultSet rs = p_stmt.getGeneratedKeys();
           int idFile = 0;
           if (rs.next()){
        	   idFile=rs.getInt(1);
           }
           System.out.println("The File has been inserted with id: " + idFile);
           Scorm2sql.logger.debug("The File has been inserted with id: " + idFile);
           cerrar(conn,p_stmt);
           
           
           
           
                      
          
           
//           //update file
//           if (idMetadata != -1){
//           
//                conn= (new ConexionBD()).getConexion();
//                p_stmt=null;
//                altaDatos=null;
//                altaDatos="UPDATE file SET file.id_metadata="+idMetadata+"WHERE file.id_file="+idFile;
//                p_stmt = conn.prepareStatement(altaDatos);
//                p_stmt.executeUpdate();
//                
//           }
           
           
           
           
           
       }catch (Exception ex) {
           MensajeError.mostrar("Problems inserting a File element . " + ex.getMessage());
           Scorm2sql.logger.info("Problems inserting a File element . " + ex.getMessage());
       }
       finally{
           cerrar(conn,p_stmt);
       }
    }
    



  
	
    void cerrar(Connection conn, Statement stmt){
        try{
            stmt.close();
            conn.close();
        } catch (SQLException ex){ }
    }
}


