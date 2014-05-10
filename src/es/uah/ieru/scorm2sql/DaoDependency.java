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
public class DaoDependency {

    void insertDependency(BeanDependency dependency, int idResource) throws IOException {
       Connection conn= (new ConexionBD()).getConexion();
       PreparedStatement p_stmt=null;
       String altaDatos=null;
       
       try {

           altaDatos ="INSERT INTO dependency (id_resource, identifierref) VALUES(?,?)";            
           p_stmt = conn.prepareStatement(altaDatos);
           p_stmt.setInt(1,idResource);
           p_stmt.setString(2,dependency.getIdentifierref());         
                         
           Scorm2sql.logger.debug("Query: "+p_stmt.toString());       
           p_stmt.executeUpdate();


           cerrar(conn,p_stmt);
           

           
           
           
           
           
       }catch (Exception ex) {
           MensajeError.mostrar("Problems inserting a Dependency element . " + ex.getMessage());
           Scorm2sql.logger.info("Problems inserting a Dependency element . " + ex.getMessage());
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


