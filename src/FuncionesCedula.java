
/**
 *
 * @author Ing. Diego Romero
 * @version 1.0
 * @fecha 2015-05-01
 */
public class FuncionesCedula {

    public static String corrigeCedulaNumerica(String cedula) {
        if (!provinciaValida(Integer.parseInt(cedula.substring(0, 2))) || Integer.parseInt(cedula.substring(2, 3)) >= 6 || cedula.length() > 10 || cedula.length() < 9) {
            return null;
        }
        int suma = 0, cheksum;
        for (int i = 0; i < cedula.length(); i++) {
            suma += Integer.parseInt(cedula.substring(i, i + 1)) * (2 - i % 2) % 10 + Integer.parseInt(cedula.substring(i, i + 1)) * (2 - i % 2) / 10;
        }
        cheksum = suma % 10 != 0 ? 10 - suma % 10 : 0;
        return cedula + cheksum;
    }

    public static boolean provinciaValida(int pro) {
        return pro >= 1 && pro <= 24;
    }

}
