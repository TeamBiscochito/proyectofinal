package teambiscochito.toptrumpsgame.validar;

public class ValidarDatos {

    public static boolean validarNombre(String nom) {
        boolean error = false;

        if(nom.length() > 18) {

            error = true;

        }

        return error;
    }

}
