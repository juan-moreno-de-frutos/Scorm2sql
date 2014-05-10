/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uah.ieru.scorm2sql;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author Juan Moreno de Fruutos
 */
public class DaoOrganization {

    void insertOrganization(BeanOrganizations organizations, int idManifest) throws IOException {       
       
       Connection conn= (new ConexionBD()).getConexion();
       PreparedStatement p_stmt=null;
       String altaDatos=null;
              
       try {
           while (!organizations.getOrganization().isEmpty()){
                BeanOrganization org;
                org=organizations.getOrganization().remove(organizations.getOrganization().size()-1);
                                
                //insert metadata
                int idMetadata=-1;
                if (org.getMetadata()!= null) {                
                    DaoMetadata daoMetadata = new DaoMetadata();
                    idMetadata = daoMetadata.insertarMetadata(org.getMetadata());
                }
                
                altaDatos ="INSERT INTO organization (scorm_id, identifier, title, structure,id_metadata) VALUES(?,?,?,?,?)";
                p_stmt = conn.prepareStatement(altaDatos, Statement.RETURN_GENERATED_KEYS);
                p_stmt.setInt(1,idManifest);
                p_stmt.setString(2,org.getIdentifier());
                p_stmt.setString(3,org.getTitle());
                p_stmt.setString(4,org.getStructure());
                if (idMetadata != -1){  p_stmt.setInt(5, idMetadata);}
                  else{  p_stmt.setString(5,null);}
                Scorm2sql.logger.debug("Query: "+p_stmt.toString());     
                
           
                p_stmt.executeUpdate();
                ResultSet rs = p_stmt.getGeneratedKeys();
                int idOrganization = 0;
                if (rs.next()){
                        idOrganization=rs.getInt(1);
                }
                System.out.println("The organization has been inserted with id: " + idOrganization);
                Scorm2sql.logger.debug("The organization has been inserted with id: " + idOrganization);


                

                //insert item
                while (!org.getItem().isEmpty()){
                    Scorm2sql.logger.debug("Item: "+org.getItem().toString()); 
                    DaoItem daoItem = new DaoItem();
                    daoItem.insertItem(org.getItem().remove(org.getItem().size()-1), idOrganization, -1);
                }

         
                 
           
           
           }
           
       }catch (Exception ex) {
           MensajeError.mostrar("Problems inserting a organization element . " + ex.getMessage());
           Scorm2sql.logger.debug("Problems inserting a organization element . " + ex.getMessage());
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
