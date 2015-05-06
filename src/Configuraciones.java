
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.MissingResourceException;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import javax.swing.JOptionPane;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author CEL3
 */
public class Configuraciones {

    private FileInputStream f;
    private ResourceBundle rb;

    public Configuraciones() {
        try {
            this.f = new FileInputStream("src/config/config.properties");
            this.rb = new PropertyResourceBundle(f);
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "No se encuentra archivo de configuración");
            System.exit(0);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error en la lectura del archivo de configuración");
            System.exit(0);
        }
    }

    public String getUsuarioBd() {
        try {
            return rb.getString("usuario_bd");
        } catch (MissingResourceException e) {
            System.out.println(e);
            return null;
        }
    }

    public String getClaveBd() {
        try {
            return rb.getString("clave_bd");
        } catch (MissingResourceException e) {
            System.out.println(e);
            return null;
        }
    }

    public String getBase() {
        try {
            return rb.getString("base");
        } catch (MissingResourceException e) {
            System.out.println(e);
            return null;
        }
    }

    public String getHost() {
        try {
            return rb.getString("host");
        } catch (MissingResourceException e) {
            System.out.println(e);
            return null;
        }
    }

}
