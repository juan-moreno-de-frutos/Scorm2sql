package es.uah.ieru.scorm2sql;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;

public class ConexionBD {
    Connection conn=null;

    public ConexionBD() throws IOException {
        try {
            Config cfg = new Config();
            String driver = cfg.getProperty("DRIVER");
            Class.forName(driver).newInstance();
        } catch (Exception ex) {
            MensajeError.mostrar("Error al obtener la conexión con la base de datos. " + ex.getMessage());
            return;
        }
        
        
        try {
            
            Config cfg = new Config();
            String nombre = cfg.getProperty("USERNAME");
            String clave = cfg.getProperty("PASSWORD");
        	
            conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost/scorm?user="+nombre+"&password="+clave);
            conn.setReadOnly(false);
            conn.setAutoCommit(true);
            
        } catch (FileNotFoundException ex) {
            MensajeError.mostrar(ex.getMessage());
            Scorm2sql.logger.info("ERROR: " + ex.getMessage()); 
            
        } catch (SQLException ex) {
            MensajeError.mostrar("Failed to get connection to the database. "+
                   ex.getMessage()+" "+ex.getSQLState()+" "+ex.getErrorCode());
        }
    }


    public Connection getConexion(){
        return conn;
    }
}
