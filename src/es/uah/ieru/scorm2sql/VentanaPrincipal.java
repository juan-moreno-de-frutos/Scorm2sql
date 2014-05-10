package es.uah.ieru.scorm2sql;
import javax.swing.*;

public class VentanaPrincipal extends JFrame {

    static JDesktopPane panelVentanaPrin=new JDesktopPane();


    public VentanaPrincipal(String titulo) {
        super(titulo);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBounds(0,0,640,480);
        this.getContentPane().add(panelVentanaPrin);
    }

    public static JDesktopPane getPanelVentanaPrincipal (){
        return panelVentanaPrin;
    }
}
