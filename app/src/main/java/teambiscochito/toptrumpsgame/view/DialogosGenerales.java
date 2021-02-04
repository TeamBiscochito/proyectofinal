/*
 * Copyright (c) 2021. Team Biscochito.
 *
 * Licensed under the GNU General Public License v3.0
 *
 * https://www.gnu.org/licenses/gpl-3.0.html
 *
 * Permissions of this strong copyleft license are conditioned on making available complete
 * source code of licensed works and modifications, which include larger works using a licensed
 * work, under the same license. Copyright and license notices must be preserved. Contributors
 * provide an express grant of patent rights.
 */

package teambiscochito.toptrumpsgame.view;

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
import teambiscochito.toptrumpsgame.view.fragment.jugar.JuegoFragment;

/**
 * <h2 align="center">Team Biscochito</h2><hr>
 * <p>
 * Clase que contiene método estáticos para un uso generalizado. Como por ejemplo en caso de los
 * botón/view que ejercemos como si fuera un botón para salir de la aplicación.
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
     * Método estático que se encarga de generar el diálogo cuando se acierta una pregunta o se
     * falla la pregunta.
     * <br><br>
     * Referencia del método en: {@link JuegoFragment#viewCerrarJuego()}
     *
     * @param context   contexto del fragmento.
     * @param respuesta pasamos el layout del diálogo a mostrar.
     */
    public static void dialogoRespuesta(Context context, int respuesta) {
        Dialog dialogoRespuesta = new Dialog(context);
        dialogoRespuesta.setContentView(respuesta);
        dialogoRespuesta.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        Window window = dialogoRespuesta.getWindow();
        window.setGravity(Gravity.CENTER);
        window.getAttributes().windowAnimations = R.style.DialogAnimationJuegoAF;

        dialogoRespuesta.setCancelable(true);
        dialogoRespuesta.setCanceledOnTouchOutside(false);
        window.setLayout(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        dialogoRespuesta.show();

        Thread threadCerrarPFalloDialog = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(2000);
                    dialogoRespuesta.dismiss();
                } catch (InterruptedException ignored) {
                }
            }
        };
        threadCerrarPFalloDialog.start();
    }

    /**
     * <h2 align="center">Team Biscochito</h2><hr>
     * <p>
     * Método privado estático para separar los diferentes métodos. Crea el diálogo y lo muestra.
     *
     * @param context       contexto del fragmento.
     * @param irID          fragmento al que deseamos ir (al modo administrador supuestamente
     *                      siempre el mismo).
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