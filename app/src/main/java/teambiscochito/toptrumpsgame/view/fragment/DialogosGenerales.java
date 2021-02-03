package teambiscochito.toptrumpsgame.view.fragment;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import teambiscochito.toptrumpsgame.R;

/**
 * <h2 align="center">Team Biscochito</h2><hr>
 * <p>
 * Clase que contiene un método generalizado para todas las clases en este caso es el botón/view que
 * ejercemos como si fuera un botón para salir de la aplicación.
 */
public class DialogosGenerales {
    /**
     * <h2 align="center">Team Biscochito</h2><hr>
     * <p>
     * Método estático genérico para cerrar el modo administrador y volver al principio (selección
     * de usuario).
     *
     * @param irID          dirección del fragmento al que queremos ir.
     * @param context       contexto del fragmento.
     * @param cerrarView    vista del "botón" de cerrar (X).
     * @param onViewCreated le pasamos la vista principal al la hora de crear el fragmento.
     */
    public static void salirDialogAdmin(int irID, Context context, View onViewCreated, View cerrarView) {
        Animation animScaleUp, animScaleDown;

        animScaleUp = AnimationUtils.loadAnimation(context, R.anim.scale_up);
        animScaleDown = AnimationUtils.loadAnimation(context, R.anim.scale_down);

        cerrarView.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    cerrarView.startAnimation(animScaleUp);
                    dialogoCerrarAdmin(context, irID, onViewCreated);
                    break;
                case MotionEvent.ACTION_UP:
                    cerrarView.startAnimation(animScaleDown);
                    v.performClick();
                    break;
            }
            return true;
        });
    }

    /**
     * <h2 align="center">Team Biscochito</h2><hr>
     * <p>
     * Método estático genérico para volver al fragmento anterior.
     *
     * @param irID            dirección del fragmento al que queremos ir.
     * @param context         contexto del fragmento.
     * @param volverAtrasView vista del "volver" de ir al fragmento anterior.
     * @param onViewCreated   le pasamos la vista principal al la hora de crear el fragmento.
     */
    public static void volverAtrasDialog(int irID, Context context, View onViewCreated, View volverAtrasView) {
        Animation animScaleUp, animScaleDown;

        animScaleUp = AnimationUtils.loadAnimation(context, R.anim.scale_up);
        animScaleDown = AnimationUtils.loadAnimation(context, R.anim.scale_down);

        NavController navController = Navigation.findNavController(onViewCreated);

        volverAtrasView.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    volverAtrasView.startAnimation(animScaleUp);
                    navController.navigate(irID);
                    break;
                case MotionEvent.ACTION_UP:
                    volverAtrasView.startAnimation(animScaleDown);
                    v.performClick();
                    break;
                default:
                    break;
            }
            return true;
        });
    }

    /**
     * <h2 align="center">Team Biscochito</h2><hr>
     * <p>
     * Método privado estático para separar los diferentes métodos. Crea el diálogo y lo muestra.
     *
     * @param context       contexto del fragmento.
     * @param irID          fragmento al que deseamos ir (al modo administrador supuestamente siempre el mismo).
     * @param onViewCreated le pasamos la vista principal al la hora de crear el fragmento.
     */
    private static void dialogoCerrarAdmin(Context context, int irID, View onViewCreated) {
        Dialog dialogSalirAdmin = new Dialog(context);

        dialogSalirAdmin.setContentView(R.layout.salir_admin_dialog);
        dialogSalirAdmin.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        NavController navController = Navigation.findNavController(onViewCreated);

        Window window = dialogSalirAdmin.getWindow();
        window.setGravity(Gravity.CENTER);
        window.getAttributes().windowAnimations = R.style.DialogAnimation;

        View aceptar = dialogSalirAdmin.findViewById(R.id.viewSalirDialogAdmin_Accept);
        View rechazar = dialogSalirAdmin.findViewById(R.id.viewSalirDialogAdmin_Cancel);

        rechazar.setOnClickListener(v -> dialogSalirAdmin.dismiss());

        aceptar.setOnClickListener(v -> {
            dialogSalirAdmin.dismiss();
            navController.navigate(irID);
        });

        dialogSalirAdmin.setCancelable(true);
        dialogSalirAdmin.setCanceledOnTouchOutside(false);
        window.setLayout(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        dialogSalirAdmin.show();
    }
}