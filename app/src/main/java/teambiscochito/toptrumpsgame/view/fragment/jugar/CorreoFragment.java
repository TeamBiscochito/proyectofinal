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

package teambiscochito.toptrumpsgame.view.fragment.jugar;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Patterns;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.text.DecimalFormat;

import teambiscochito.toptrumpsgame.R;
import teambiscochito.toptrumpsgame.model.room.pojo.User;
import teambiscochito.toptrumpsgame.view.fragment.DialogosGenerales;
import teambiscochito.toptrumpsgame.viewmodel.ViewModel;

/**
 * <h2 align="center">Team Biscochito</h2><hr>
 * <p>
 * Clase para que el usuario pueda enviar su puntuación conseguida por correo para que la pueda
 * guardar.
 */
@SuppressWarnings({"Convert2Lambda"})
// Comente la línea de arriba para ver los posibles Lambdas a convertir
public class CorreoFragment extends Fragment {
    private User userActual;
    private View viewBackCorreo, viewCorreoBorrar, viewCorreoEnviar;
    private Animation animCaraLeon, animScaleUp, animScaleDown;
    private EditText etCorreo;
    private TextView tvCorreoBorrar, tvCorreoEnviar, tvAlertaCorreo;
    private ImageView imgCaraLeonCorreo;
    private Dialog dialogCorreo;
    private MediaPlayer mp_correo;

    public CorreoFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_correo, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init(view);

        userActual = ViewModel.userActual;

        imgCaraLeonCorreo.startAnimation(animCaraLeon);

        DialogosGenerales.volverAtrasDialog(R.id.action_correoFragment_to_menuFragment,
                getContext(), view, viewBackCorreo);

        borrarEditText();
        viewCorreoEnviar();
    }

    /**
     * <h2 align="center">Team Biscochito</h2><hr>
     * <p>
     * Método que se ejecuta al presionar la vista de borrar, pone el EditText del correo sin ningún
     * carácter.
     * <br><br>
     * Referencia del método en: {@link CorreoFragment#onViewCreated(View, Bundle)}
     **/
    public void borrarEditText() {
        viewCorreoBorrar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        viewCorreoBorrar.startAnimation(animScaleUp);
                        tvCorreoBorrar.startAnimation(animScaleUp);

                        etCorreo.setText("");
                        break;
                    case MotionEvent.ACTION_UP:
                        viewCorreoBorrar.startAnimation(animScaleDown);
                        tvCorreoBorrar.startAnimation(animScaleDown);
                        v.performClick();
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
    }

    /**
     * <h2 align="center">Team Biscochito</h2><hr>
     * <p>
     * Método que se ejecuta al presionar la vista de enviar, cogemos los valores de nuestro perfil
     * para parsearlos y después proceder a una validación en {@link #validarCorreo(String, String, String)}
     * <br><br>
     * Referencia del método en: {@link CorreoFragment#onViewCreated(View, Bundle)}
     **/
    public void viewCorreoEnviar() {
        viewCorreoEnviar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        viewCorreoEnviar.startAnimation(animScaleUp);
                        tvCorreoEnviar.startAnimation(animScaleUp);

                        DecimalFormat format = new DecimalFormat("##.#");
                        String porcentajeAciertos = format.format(((Double.parseDouble(String.valueOf(userActual.getTrueAnswer()))) / (Double.parseDouble(String.valueOf(userActual.getAnswer()))) * 100)) + " %";

                        String correo = etCorreo.getText().toString();
                        String subject = getString(R.string.textCorreoSubject);

                        validarCorreo(porcentajeAciertos, correo, subject);
                        break;
                    case MotionEvent.ACTION_UP:
                        viewCorreoEnviar.startAnimation(animScaleDown);
                        tvCorreoEnviar.startAnimation(animScaleDown);
                        v.performClick();
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
    }

    /**
     * <h2 align="center">Team Biscochito</h2><hr>
     * <p>
     * Método que se ejecuta para validar el correo por si el EditText del correo está vacío o es
     * un correo inválido. También tiene en cuanta que si no hemos respondida ninguna pregunta la
     * plantilla del correo a enviar cambia. Una vez hecho esto se mostrará un diálogo para
     * verificar: {@link #correoDialogConfirmar()}
     * <br><br>
     * Referencia del método en: {@link CorreoFragment#viewCorreoEnviar()}
     *
     * @param porcentajeAciertos el porcentaje de aciertos.
     * @param correo             el correo del EditText.
     * @param subject            String de el asunto del correo.
     */
    public void validarCorreo(String porcentajeAciertos, String correo, String subject) {
        String mensaje;
        if (userActual.getAnswer() == 0) {
            mensaje = "Hola, 🦁 " + userActual.getName() + " 🦁\n\n" +
                    "La puntuación total que llevas en nuestro juego, Animales Salvajes es de:\n" +
                    "\n❗No has respondido ninguna pregunta❗\n" +
                    "\n¡Empieza a jugar para tener una puntuación!" + "\n" +
                    "\nTeam Biscochito © 2021 - 💯";
        } else {
            mensaje = "Hola, 🦁 " + userActual.getName() + " 🦁\n\n" +
                    "La puntuación total que llevas en nuestro juego, Animales Salvajes es de:\n" +
                    "\n - Preguntas respondidas: ❗" + userActual.getAnswer() + " ❗" + "\n" +
                    "\n - Preguntas acertadas: ✔ " + userActual.getTrueAnswer() + " ✔" + "\n" +
                    "\n - Media de preguntas acertadas: ✔ " + porcentajeAciertos + " ✔" + "\n" +
                    "\n¡Que sigas disfrutando del juego!" + "\n" +
                    "\nTeam Biscochito © 2021 - 💯";
        }
        if (correo.isEmpty()) {

            tvAlertaCorreo.setText(R.string.textIntroduceCorreo);

        } else if (!(Patterns.EMAIL_ADDRESS.matcher(correo).matches())) {

            tvAlertaCorreo.setText(R.string.textCorreoNoValido);

        } else if (Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
            Intent intent = new Intent(Intent.ACTION_SENDTO);

            intent.putExtra(Intent.EXTRA_EMAIL, new String[]{correo});
            intent.putExtra(Intent.EXTRA_SUBJECT, subject);
            intent.putExtra(Intent.EXTRA_TEXT, mensaje);

            intent.setData(Uri.parse("mailto:"));

            if (intent.resolveActivity(requireContext().getPackageManager()) != null) {
                startActivity(intent);

                etCorreo.setText("");
                tvAlertaCorreo.setText("");

                correoDialogConfirmar();
            } else {
                tvAlertaCorreo.setText(R.string.textNoSePudoEnviarCorreo);
            }
        }
    }


    /**
     * <h2 align="center">Team Biscochito</h2><hr>
     * <p>
     * Método que se ejecuta después de la validación de correo y abierta una vez el Gmail mediante
     * el intent. Sirve para recordar que revises tu correo en caso de que finalmente lo hayas
     * enviado.
     * <br><br>
     * Referencia del método en: {@link CorreoFragment#validarCorreo(String, String, String)}
     */
    public void correoDialogConfirmar() {
        View viewBackCorreoDialog;

        dialogCorreo = new Dialog(getContext());
        dialogCorreo.setContentView(R.layout.correo_dialog);
        dialogCorreo.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        Window window = dialogCorreo.getWindow();
        window.setGravity(Gravity.CENTER);
        window.getAttributes().windowAnimations = R.style.DialogAnimation;

        viewBackCorreoDialog = dialogCorreo.findViewById(R.id.viewDialogCorreoBack);

        viewBackCorreoDialog.setOnClickListener(v -> dialogCorreo.dismiss());

        dialogCorreo.setCancelable(true);
        dialogCorreo.setCanceledOnTouchOutside(false);
        window.setLayout(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        dialogCorreo.show();
    }

    public void init(View view) {
        animCaraLeon = AnimationUtils.loadAnimation(getContext(), R.anim.slide_tutorial);
        animScaleUp = AnimationUtils.loadAnimation(getContext(), R.anim.scale_up);
        animScaleDown = AnimationUtils.loadAnimation(getContext(), R.anim.scale_down);

        initMediaPlayerCorreo();

        viewBackCorreo = view.findViewById(R.id.viewCorreo_Back);
        viewCorreoBorrar = view.findViewById(R.id.viewCorreo_Borrar);
        viewCorreoEnviar = view.findViewById(R.id.viewCorreo_Enviar);
        tvCorreoBorrar = view.findViewById(R.id.tvCorreo_Borrar);
        tvCorreoEnviar = view.findViewById(R.id.tvCorreo_Enviar);
        tvAlertaCorreo = view.findViewById(R.id.tvCorreo_Alerta);
        imgCaraLeonCorreo = view.findViewById(R.id.imgCorreo_Leon);

        etCorreo = view.findViewById(R.id.etCorreo_Correo);
    }

    public void initMediaPlayerCorreo() {
        mp_correo = MediaPlayer.create(getContext(), R.raw.correo_music);
        mp_correo.setLooping(true);
        mp_correo.start();
    }

    @Override
    public void onResume() {
        super.onResume();
        mp_correo.start();
    }

    @Override
    public void onPause() {
        super.onPause();
        mp_correo.pause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mp_correo.stop();
        mp_correo.release();
    }
}