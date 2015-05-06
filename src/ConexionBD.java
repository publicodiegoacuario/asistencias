
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author CEL3
 */
public class ConexionBD {

    public static Connection getConecion() {
        Connection c;
        Configuraciones cf = new Configuraciones();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String usuarioBd = cf.getUsuarioBd();
            String claveBd = cf.getClaveBd();
            String base = cf.getBase();
            String host = cf.getHost();
            if (usuarioBd == null
                    || claveBd == null
                    || base == null
                    || host == null) {
                JOptionPane.showMessageDialog(null, "Verifique variables del archivo de configuración.");
                System.exit(0);
            }
            String servidor = "jdbc:mysql://" + host + "/" + base;
            c = DriverManager.getConnection(servidor, usuarioBd, claveBd);
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println(ex);
            JOptionPane.showMessageDialog(null, "Verifique la conexión a servidor de base de datos.");
            System.exit(0);
            c = null;
        }
        return c;
    }
}
