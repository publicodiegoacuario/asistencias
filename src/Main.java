
import java.sql.Connection;

/**
 *
 * @author Ing. Diego Romero
 * @version 1.0
 * @fecha 2015-05-01
 */
public class Main {

    public static void main(String[] args) {
        Connection c = ConexionBD.getConecion();
        new ActualizaHora(c).start();

    }

}
