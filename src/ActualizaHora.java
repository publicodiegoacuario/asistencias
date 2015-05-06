
import java.sql.Connection;

/**
 *
 * @author Ing. Diego Romero
 * @version 1.0
 * @fecha 2015-05-01
 */
public class ActualizaHora extends Thread {

    Connection c;
    RegistroAsistencias ra;

    public ActualizaHora(Connection c) {
        this.c = c;
        ra = new RegistroAsistencias(c);
        ra.setVisible(true);
    }

    public void run() {
        while (true) {
            ra.setTitle("Registro de asistencias " + Funciones.obtieneHora(c));
            try {
                sleep(1000);
            } catch (InterruptedException ex) {

            }
        }

    }
}
