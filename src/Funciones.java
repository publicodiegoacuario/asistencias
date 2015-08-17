
import java.awt.Component;
import static java.lang.Thread.sleep;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
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

    public static String tipoAsistencia(Connection c, Integer id_persona) {
        String consulta = "SELECT time(fecha_hora_registro) as hora, salida "
                + "from asistencias where date(fecha_hora_registro)=date(now()) and id_persona=" + id_persona
                + " order by fecha_hora_registro desc limit 2;";
        String datos = "";
        try {
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery(consulta);
            while (rs.next()) {
                datos += rs.getString("hora") + "," + rs.getString("salida") + "%";
            }
            if (datos.equals("")) {
                return "entrada";
            } else {
                String dat[] = datos.split("%");
                Integer can = dat.length;
                String horaTipo[] = dat[0].split(",");
                if (can == 1) {
                    long segPas = restarHoras(horaTipo[0], obtieneHora(c));
                    if (segPas > 1800) {
                        return "salida";
                    } else {
                        return horaTipo[0];
                    }
                } else {
                    if (horaTipo[1].equals("1")) {
                        return "entrada";
                    } else {
                        long segPas = restarHoras(horaTipo[0], obtieneHora(c));
                        if (segPas > 1800) {
                            return "salida";
                        } else {
                            return horaTipo[0];
                        }
                    }
                }
            }
        } catch (SQLException ex) {
            if ((ex + "").contains("empty result")) {
                return "entrada";
            } else {
                JOptionPane.showMessageDialog(null, "No se puede verificar información desde el servidor.");
                try {
                    c.close();
                } catch (SQLException ex1) {
                }
                System.exit(0);
                return "entrada";
            }
        }
    }

    public static Boolean registraAsistencia(Connection c, Integer id_persona, int tipo) {
        try {
            Statement s = c.createStatement();
            s.executeUpdate("INSERT INTO asistencias "
                    + "(id_persona, salida) VALUES ("
                    + id_persona + "," + tipo + ");");
            return true;
        } catch (SQLException ex) {
            System.out.println(ex);
            return false;
        }
    }

    /**
     *
     * @param horaAnt
     * @param horaAct
     * @return cantidad de segundos
     */
    public static long restarHoras(String horaAnt, String horaAct) {
        try {
            long horaAn = new SimpleDateFormat("HH:mm:ss").parse(horaAnt).getTime();
            long horaAc = new SimpleDateFormat("HH:mm:ss").parse(horaAct).getTime();
            return (horaAc - horaAn) / 1000;
        } catch (ParseException ex) {
            return -1;
        }
    }
/**
 * 
 * @param c Conexion con mysql
 * @param cargo Texto para la tabla docentes
 * @param dni Cedula o pasaporte de la persona
 * @param nom Nombres de la persona
 * @param ape Apellidos de la persona
 * @param cel Celular de la persona
 * @param correo Correo de la persona
 * @param fecNac Fecha de nacimiento de persona
 * @return teue o false
 */
    public Boolean registrarDocente(
            Connection c, String cargo,
            String dni, String nom,
            String ape, String cel,
            String correo, String fecNac
    ) {
        try {
            Statement s = c.createStatement();
            s.executeUpdate("INSERT INTO personas"
                    + " (dni, nombres, apellidos, telefono, correo, fecha_nacimiento) "
                    + "VALUES ('" + dni + "', '" + nom + "', '" + ape + "', '" + cel + "', '" + correo + "', '" + fecNac + "');");
            String sql = "SELECT * FROM personas WHERE dni='" + dni + "'";
            ResultSet rs = s.executeQuery(sql);
            rs.next();
            Integer id = rs.getInt("id_persona");
            s.executeUpdate("INSERT INTO docentes (id_persona, cargo) "
                    + "VALUES ('" + id + "', '" + cargo + "');");
            return true;
        } catch (SQLException ex) {
            System.out.println(ex);
            
            return false;
        }
    }
/**
 * 
 * @param c Conexion con mysql
 * @param us usuario de la tabla usuarios
 * @param cl clave de la tabla usuarios
 * @return true o false 
 */
    public Boolean login(Connection c, String us, String cl) {
        try {
            String sql = "SELECT * FROM usuarios WHERE usuario='" + us + "' AND clave='" + cl + "'";
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery(sql);
            rs.next();
            return rs.getInt("id_rol") == 1;
        } catch (SQLException ex) {
            System.out.println(ex);
            return false;
        }

    }
}
