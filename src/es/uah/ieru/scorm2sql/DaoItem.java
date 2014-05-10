/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uah.ieru.scorm2sql;

import java.io.IOException;
import java.sql.*;

/**
 *
 * @author Juan Moreno de Frutos
 */
public class DaoItem {

    void insertItem(BeanItem item, int idOrganization, int idItemP) throws IOException {
      //idItemP is -1 when this item isn't subnode. 

       Connection conn= (new ConexionBD()).getConexion();
       PreparedStatement p_stmt=null;
       String altaDatos=null;
       int idMetadata=-1;
       
       try {
           
           //insert metadata
           if (item.getMetadata()!= null) {                
                DaoMetadata daoMetadata = new DaoMetadata();
                idMetadata = daoMetadata.insertarMetadata(item.getMetadata());
           }
           Scorm2sql.logger.debug("kk: ");
           Scorm2sql.logger.debug("item: " + item);
           
           if (idItemP ==-1){
            
            altaDatos ="INSERT INTO item (id_organization, identifier,title,identifierref, isvisible, parameters,prerequisites,"
                    + "prerequisites_type,maxtimeallowed,timelimitaction,datafromlms,masteryscore,id_metadata)"
                    + " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";            
            p_stmt = conn.prepareStatement(altaDatos, Statement.RETURN_GENERATED_KEYS);
            
            p_stmt.setInt(1,idOrganization);
            p_stmt.setString(2,item.getIdentifier());
            p_stmt.setString(3,item.getTitle());
            p_stmt.setString(4,item.getIdentifierref());
            Scorm2sql.logger.debug("kk211");
            if (item.getIsvisible() != null){p_stmt.setBoolean(5,item.getIsvisible());}
             else {p_stmt.setNull(5, 16);} //16 = BOOLEAN
            Scorm2sql.logger.debug("kk212");
            p_stmt.setString(6,item.getParameters());;
            p_stmt.setString(7,item.getPrerequisites());
            p_stmt.setString(8,item.getPrerequisites_type());
            p_stmt.setString(9,item.getMaxtimeallowed());
            p_stmt.setString(10,item.getTimelimitaction());            
            p_stmt.setString(11,item.getDatafromlms());
            p_stmt.setString(12,item.getMasteryscore());
            if (idMetadata != -1){  p_stmt.setInt(13, idMetadata);}
                else{  p_stmt.setNull(13, 4);} // 4 =INTEGER
              //    else{  p_stmt.setString(13,null);}
            Scorm2sql.logger.debug("kk4");
            
           }else{
            Scorm2sql.logger.debug("kk3");
            altaDatos ="INSERT INTO item (id_organization,identifier,title,identifierref, isvisible, parameters,prerequisites,prerequisites_type,"
                    + "maxtimeallowed,timelimitaction,datafromlms,masteryscore,item_id_item,id_metadata)"
                    + " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            p_stmt = conn.prepareStatement(altaDatos, Statement.RETURN_GENERATED_KEYS);
            p_stmt.setInt(1,idOrganization);
            p_stmt.setString(2,item.getIdentifier());
            p_stmt.setString(3,item.getTitle());
            p_stmt.setString(4,item.getIdentifierref());
            if (item.getIsvisible() != null){p_stmt.setBoolean(5,item.getIsvisible());}
             else {p_stmt.setNull(5, 16);} //16 = BOOLEAN
            p_stmt.setString(6,item.getParameters());
            p_stmt.setString(7,item.getPrerequisites());
            p_stmt.setString(8,item.getPrerequisites_type());
            p_stmt.setString(9,item.getMaxtimeallowed());
            p_stmt.setString(10,item.getTimelimitaction());
            p_stmt.setString(11,item.getDatafromlms());
            p_stmt.setString(12,item.getMasteryscore());
            p_stmt.setInt(13,idItemP);
            if (idMetadata != -1){  p_stmt.setInt(14, idMetadata);}
                  else{  p_stmt.setNull(14, 4);} // 4 =INTEGER
           }                 
           
           
           Scorm2sql.logger.debug("Query: "+p_stmt.toString());
           p_stmt.executeUpdate();
           ResultSet rs = p_stmt.getGeneratedKeys();
           int idItem = 0;
           if (rs.next()){
        	   idItem=rs.getInt(1);
           }
           System.out.println("The Item has been inserted with id: " + idItem);
           Scorm2sql.logger.debug("The Item has been inserted with id: " + idItem);
           cerrar(conn,p_stmt);
           
     
                      
           //insert item
           while (!item.getItem().isEmpty()){
              Scorm2sql.logger.debug("Item: "+item.getItem().toString()); 
              DaoItem daoItem= new DaoItem();
              daoItem.insertItem(item.getItem().remove(item.getItem().size()-1),idOrganization , idItem);
           }
           
          
           
           
       }catch (Exception ex) {
           MensajeError.mostrar("Problems inserting a item element . " + ex.getMessage());
           Scorm2sql.logger.info("Problems inserting a item element . " + ex.toString());
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

