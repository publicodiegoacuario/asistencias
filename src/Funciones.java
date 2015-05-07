
import java.awt.Component;
import static java.lang.Thread.sleep;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

/**
 *
 * @author Ing. Diego Romero
 * @version 1.0
 * @fecha 2015-05-01
 */
public class Funciones {

    public static void visualizaDialogo(Component padre, String texto, String titulo, final int timeout) {
        JOptionPane option = new JOptionPane("", JOptionPane.INFORMATION_MESSAGE);
        option.setMessage(texto);
        final JDialog dialogo = option.createDialog(padre, titulo);

        Thread hilo = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(timeout);
                    if (dialogo.isVisible()) {
                        dialogo.setVisible(false);
                    }
                } catch (InterruptedException e) {
                }
            }
        };
        hilo.start();
        dialogo.setVisible(true);
        dialogo.dispose();
    }
    public static String obtieneHora(Connection c) {
        String consulta = "SELECT NOW() AS fecha_hora";
        try {
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery(consulta);
            rs.next();
            String fechaHora = rs.getString("fecha_hora").substring(11, 19);
            return fechaHora;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "No se puede obtener la hora desde el servidor.");
            try {
                c.close();
            } catch (SQLException ex1) {
            }
            System.exit(0);
        }

        return new SimpleDateFormat("HH:mm:ss").format(new Date());
    }

    public static String existeDocente(Connection c, String dni) {
        String consulta = "SELECT CONCAT(p.nombres, ' ',p.apellidos) AS nom_ape,p.id_persona "
                + "FROM docentes d, personas p "
                + "where p.id_persona=d.id_persona AND p.dni='" + dni + "'";
        try {
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery(consulta);
            rs.next();
            String datos = rs.getInt("id_persona") + "," + rs.getString("nom_ape");
            return datos;
        } catch (SQLException ex) {
            if ((ex + "").contains("empty result")) {
                return "";
            } else {
                JOptionPane.showMessageDialog(null, "No se puede obtener información desde el servidor.");
                try {
                    c.close();
                } catch (SQLException ex1) {
                }
                System.exit(0);
            }
        }
        return null;
    }

    public static Boolean registraAsistencia(Connection c, Integer id_persona) {
        try {
            Statement s = c.createStatement();
            s.executeUpdate("INSERT INTO asistencias "
                    + "(id_persona) VALUES ("
                    + id_persona + ")");
            return true;
        } catch (SQLException ex) {
            System.out.println(ex);
            return false;
        }
    }

}
