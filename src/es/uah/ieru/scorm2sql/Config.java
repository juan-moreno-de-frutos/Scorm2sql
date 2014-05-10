/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uah.ieru.scorm2sql;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


/**
 * Class that retrieves config data from the application.
 * @author  Fernando Luis Álvarez
 * @since  1.0
 * @see Visit http://www.ieru.org/
 */
public class Config {

    Properties configFile;

    public Config() throws FileNotFoundException, IOException {
        configFile = new Properties();
        String fileName = "/home/juan/NetBeansProjects/Scorm2sql/config.properties";
        InputStream is = new FileInputStream(fileName);

        configFile.load(is);

    }

    /**
     * 
     * @param key
     * @return 
     */
    public String getProperty(String key) {
        String value = this.configFile.getProperty(key);
        return value;
    }
}