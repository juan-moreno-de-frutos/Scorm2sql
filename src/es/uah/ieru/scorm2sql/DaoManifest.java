/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uah.ieru.scorm2sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Juan Moreno de Frutos
 */
public class DaoManifest {
   
	
    public int insertarManifest(BeanManifest manifest, int idManifP){
//        If It is subnode idManifest is != -1;
       
       Connection conn=null;
       PreparedStatement p_stmt=null;
       
       try {
           conn= (new ConexionBD()).getConexion();
           if (conn!=null){
                p_stmt=null;
                String altaDatos=null;
                int idMetadata=-1;
                Scorm2sql.logger.debug("Established connection.");




                //insert metadata
                if (manifest.getMetadata()!= null) {                
                    DaoMetadata daoMetadata = new DaoMetadata();
                    idMetadata = daoMetadata.insertarMetadata(manifest.getMetadata());
                    Scorm2sql.logger.debug("Has been inserted a Metadata record with id:"+idMetadata);
                }


                if (idManifP == -1){
                        altaDatos ="INSERT INTO manifest (manifest_identifier,manifest_version, manifest_base, organizations_default, resources_base, id_metadata) VALUES(?,?,?,?,?,?)";
                        p_stmt = conn.prepareStatement(altaDatos, Statement.RETURN_GENERATED_KEYS);
                        p_stmt.setString(1,manifest.getIdentifier());
                        p_stmt.setString(2,manifest.getVersion());
                        p_stmt.setString(3,manifest.getBase());
                        p_stmt.setString(4,manifest.getOrganizations().getDefaul());
                        p_stmt.setString(5,manifest.getResources().getBase());
                        if (idMetadata != -1){  p_stmt.setInt(6, idMetadata);}
                        else{  p_stmt.setString(6,null);}

                }else{
                        altaDatos ="INSERT INTO manifest (manifest_identifier,manifest_version, manifest_base, organizations_default, resources_base, manifest_scorm_id, id_metadata) VALUES(?,?,?,?,?,?,?)";
                        p_stmt = conn.prepareStatement(altaDatos, Statement.RETURN_GENERATED_KEYS);
                        p_stmt.setString(1,manifest.getIdentifier());
                        p_stmt.setString(2,manifest.getVersion());
                        p_stmt.setString(3,manifest.getBase());
                        p_stmt.setString(4,manifest.getOrganizations().getDefaul());
                        p_stmt.setString(5,manifest.getResources().getBase());
                        p_stmt.setInt(6, idManifP);
                        if (idMetadata != -1){  p_stmt.setInt(7, idMetadata);}
                        else{  p_stmt.setString(7,null);}
                }


                Scorm2sql.logger.debug("Query: "+p_stmt.toString());
                p_stmt.executeUpdate();
                ResultSet rs = p_stmt.getGeneratedKeys();
                int idManifest = 0;
                if (rs.next()){
                        idManifest=rs.getInt(1);
                }

                manifest.setId(idManifest);
                System.out.println("The manifest has been inserted with id: " + idManifest);
                Scorm2sql.logger.debug("The manifest has been inserted with id: " + idManifest);
                cerrar(conn,p_stmt);




                //insert organization
                DaoOrganization daoOrganization = new DaoOrganization();
                daoOrganization.insertOrganization(manifest.getOrganizations(), idManifest);

                //insert resource
                DaoResource daoResource = new DaoResource();
                daoResource.insertarResource(manifest.getResources(), idManifest);

                //insert manifest
                while (!manifest.getManifest().isEmpty()){
                    DaoManifest daoManifest = new DaoManifest();
                    daoManifest.insertarManifest(manifest.getManifest().remove(manifest.getManifest().size()-1) , idManifest);
                }
                return 0;
           }else{
               Scorm2sql.logger.info("Error in conexion");
               System.out.println("Error in conexion");
               return -1;
           }
           
       }catch (SQLException ex) {
           MensajeError.mostrar("Problems with connection ." + ex.getMessage());
           Scorm2sql.logger.info("Problems with connection  ." + ex.getMessage());
           return -1;
       
       }catch (Exception ex) {
           MensajeError.mostrar("Problems inserting a manifest element . " + ex.getMessage());
           Scorm2sql.logger.info("Problems inserting a manifest element . " + ex.getMessage());
           return -1;
       }
       
       finally{
           if (conn!=null){
           cerrar(conn,p_stmt);}
       }
    }
    



  
	
    void cerrar(Connection conn, Statement stmt){
        try{
            stmt.close();
            conn.close();
        } catch (SQLException ex){ }
    }
}
