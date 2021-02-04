package teambiscochito.toptrumpsgame.validar;

/**
 * <h2 align="center">Team Biscochito</h2><hr>
 * <p>
 * Clase generalizada para tener un mejor control de errores y más organizado, donde implementamos
 * validaciones tanto de perfil de jugador como de las cartas.
 */
public class ValidarDatos {

    public static boolean validarNombreJugador(String nom) {
        boolean error = false;

        if (nom.length() > 18) {
            error = true;
        }
        return error;
    }

    public static boolean validarNombreCarta(String nom) {
        boolean error = false;

        if (nom.length() > 12) {
            error = true;
        } else {
            for (int i = 0; i < nom.length(); i++) {
                if (!Character.isAlphabetic(nom.charAt(i)) && nom.charAt(i) != ' ') {
                    error = true;
                }
            }
        }
        return error;
    }

    public static boolean validarDescCarta(String desc) {
        boolean error = false;

        if (desc.length() > 300) {
            error = true;
        }
        return error;
    }

    public static boolean validarDatosCarta(double dato) {
        boolean error = false;

        if (dato > 9999) {
            error = true;
        }
        return error;
    }

    public static boolean validarPoderCarta(double poder) {
        boolean error = false;

        if (poder > 10 || poder < 1) {
            error = true;
        }
        return error;
    }
}