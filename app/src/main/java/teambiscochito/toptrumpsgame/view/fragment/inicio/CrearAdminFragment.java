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

package teambiscochito.toptrumpsgame.view.fragment.inicio;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import teambiscochito.toptrumpsgame.R;

/**
 * <h2 align="center">Team Biscochito</h2><hr>
 * <p>
 * Clase para la creación de administrador de inicio al instalar la aplicación por primera vez.
 */
@SuppressWarnings({"Convert2Lambda"})
// Comente la línea de arriba para ver los posibles Lambdas a convertir
public class CrearAdminFragment extends Fragment {
    private EditText etClave;
    private Animation animScaleUp, animScaleDown;
    private TextView tvAdminEntrar;
    private View vAdminEntrar;
    private TextView tvAlertaCrearAdmin;
    private MediaPlayer mp_intro;

    public CrearAdminFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_crear_admin, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initAnim();

        tvAdminEntrar = view.findViewById(R.id.tvCrearAdmin_Entrar);
        vAdminEntrar = view.findViewById(R.id.viewCrearAdmin_Entrar);
        tvAlertaCrearAdmin = view.findViewById(R.id.tvCrearAdmin_Error);

        final NavController navController = Navigation.findNavController(view);

        etClave = view.findViewById(R.id.etCrearAdmin_Clave);

        vAdminEntrar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        vAdminEntrar.startAnimation(animScaleUp);
                        tvAdminEntrar.startAnimation(animScaleUp);

                        if (guardarClave()) return false;

                        mp_intro.stop();

                        navController.navigate(R.id.action_crearAdminFragment_to_chooseUserFragment);
                        break;
                    case MotionEvent.ACTION_UP:
                        vAdminEntrar.startAnimation(animScaleDown);
                        tvAdminEntrar.startAnimation(animScaleDown);
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
     * Método para guardar las claves con preferencias compartidas.
     * <br><br>
     * Referencia del método en: {@link CrearAdminFragment#onViewCreated(View, Bundle)}
     *
     * @return booleana para saber si la persona ha ingresado una clave o no.
     */
    private boolean guardarClave() {
        try {
            String clave = etClave.getText().toString();
            if (clave.isEmpty()) {
                tvAlertaCrearAdmin.setText(R.string.textIntroduceUnaClave);
                return true;
            }

            SharedPreferences sharedPref = requireActivity().getPreferences(Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("clave_admin", clave);
            editor.apply();
        } catch (Exception ignored) {
        }
        return false;
    }

    /**
     * <h2 align="center">Team Biscochito</h2><hr>
     * <p>
     * Método para iniciar la animación.
     * <br><br>
     * Referencia del método en: {@link CrearAdminFragment#onViewCreated(View, Bundle)}
     */
    private void initAnim() {
        animScaleUp = AnimationUtils.loadAnimation(getContext(), R.anim.scale_up);
        animScaleDown = AnimationUtils.loadAnimation(getContext(), R.anim.scale_down);

        initMediaPlayerIntro();
    }

    public void initMediaPlayerIntro() {
        mp_intro = MediaPlayer.create(getContext(), R.raw.intro_music);
        mp_intro.setLooping(true);
        mp_intro.start();
    }

    @Override
    public void onResume() {
        super.onResume();
        mp_intro.start();
    }

    @Override
    public void onPause() {
        super.onPause();
        mp_intro.pause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mp_intro.stop();
        mp_intro.release();
    }
}