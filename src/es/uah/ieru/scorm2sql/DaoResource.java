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
public class DaoResource {

    void insertarResource(BeanResources resources, int idManifest) throws IOException {
       Connection conn= (new ConexionBD()).getConexion();
       PreparedStatement p_stmt=null;
       String altaDatos=null;

       try {
           while (!resources.getResource().isEmpty()){
                BeanResource resource;
                resource=resources.getResource().remove(resources.getResource().size()-1);
                
                //insert metadata
                int idMetadata=-1;
                if (resource.getMetadata()!= null) {                    
                    DaoMetadata daoMetadata = new DaoMetadata();
                    idMetadata = daoMetadata.insertarMetadata(resource.getMetadata());
                }
                
                altaDatos ="INSERT INTO resource (scorm_id, identifier, type, href, scormtype,resource_base,id_metadata) VALUES(?,?,?,?,?,?,?)";
                p_stmt = conn.prepareStatement(altaDatos, Statement.RETURN_GENERATED_KEYS);
                p_stmt.setInt(1,idManifest);
                p_stmt.setString(2,resource.getIdentifier());
                p_stmt.setString(3,resource.getType());
                p_stmt.setString(4,resource.getHref()); 
                p_stmt.setString(5,resource.getScormtype());
                p_stmt.setString(6,resource.getBase());                      
                if (idMetadata != -1){  p_stmt.setInt(7, idMetadata);}
                  else{  p_stmt.setString(7,null);}
           
                Scorm2sql.logger.debug("Query: "+p_stmt.toString());
                p_stmt.executeUpdate();
                ResultSet rs = p_stmt.getGeneratedKeys();
                int idResource = 0;
                if (rs.next()){
                        idResource=rs.getInt(1);
                }
                System.out.println("The resource has been inserted with id: " + idResource);
                Scorm2sql.logger.debug("The resource has been inserted with id: " + idResource);


  

                //insert file
                while (!resource.getFile().isEmpty()){
                    DaoFile daoFile = new DaoFile();
                    daoFile.insertFile(resource.getFile().remove(resource.getFile().size()-1), idResource);
                }
                
                //insert deendency
                while (!resource.getDependency().isEmpty()){
                    DaoDependency daoDependency = new DaoDependency();
                    daoDependency.insertDependency(resource.getDependency().remove(resource.getDependency().size()-1), idResource);
                }


//                //Update organization           
//                if (idMetadata!=-1){
//                    conn= (new ConexionBD()).getConexion();
//                    p_stmt=null;
//                    altaDatos=null;
//                    altaDatos="UPDATE resource SET resource.id_metadata="+idMetadata+"WHERE resource.id_resource="+idResource;
//                    p_stmt = conn.prepareStatement(altaDatos);
//                    p_stmt.executeUpdate();
//                
//                }
                 
           
           
           }
           
       }catch (Exception ex) {
           MensajeError.mostrar("Problems inserting a resource element . " + ex.getMessage());
           Scorm2sql.logger.debug("Problems inserting a resource element . " + ex.getMessage());
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

