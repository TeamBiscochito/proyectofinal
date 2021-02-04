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

package teambiscochito.toptrumpsgame.view.fragment.administrar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import teambiscochito.toptrumpsgame.R;
import teambiscochito.toptrumpsgame.view.fragment.DialogosGenerales;

/**
 * <h2 align="center">Team Biscochito</h2><hr>
 * <p>
 * Clase principal del modo administrador, en este fragmento tenemos dos bifurcaciones a la hora de
 * administrar las cartas o administrar a los jugadores.
 */
@SuppressWarnings({"Convert2Lambda"})
// Comente la línea de arriba para ver los posibles Lambdas a convertir
public class AdminFragment extends Fragment {
    private View viewCerrarAdmin, viewAdminEntrarJugadores, viewAdminEntrarCartas;
    private Animation animScaleUp, animScaleDown, animCaraLeon;
    private TextView tvAdminEntrarJugadores, tvAdminEntrarCartas;
    private NavController navController;
    private ImageView imgCaraLeonAdmin;

    public AdminFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_admin, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);

        navController = Navigation.findNavController(view);

        imgCaraLeonAdmin.startAnimation(animCaraLeon);

        viewMoveOver(viewAdminEntrarJugadores, tvAdminEntrarJugadores, R.id.action_adminFragment_to_adminJugadorFragment);
        viewMoveOver(viewAdminEntrarCartas, tvAdminEntrarCartas, R.id.action_adminFragment_to_adminCartasFragment);

        DialogosGenerales.salirDialogAdmin(
                R.id.action_adminFragment_to_chooseUserFragment,
                getContext(),
                view,
                viewCerrarAdmin);
    }

    /**
     * <h2 align="center">Team Biscochito</h2><hr>
     * <p>
     * Método para recoger las transiciones de las vistas (botones) para navegar a los diferentes
     * fragmentos disponibles. En este caso se comparte con EntrarJugadores y Entrar Cartas.
     * <br><br>
     * Referencia del método en: {@link AdminFragment#onViewCreated(View, Bundle)}
     *
     * @param viewMoveOver     vista que le pasamos a nuestro método dependiendo del "botón" que
     *                         pinchemos.
     * @param textViewMoveOver TextView que le pasamos a nuestro método para darle una animación.
     * @param fragmentDestino  ponemos la ID del fragmento del destino al pinchar sobre la vista.
     */

    private void viewMoveOver(View viewMoveOver, TextView textViewMoveOver, int fragmentDestino) {
        viewMoveOver.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        viewMoveOver.startAnimation(animScaleUp);
                        textViewMoveOver.startAnimation(animScaleUp);
                        navController.navigate(fragmentDestino);
                        break;
                    case MotionEvent.ACTION_UP:
                        viewMoveOver.startAnimation(animScaleDown);
                        textViewMoveOver.startAnimation(animScaleDown);
                        v.performClick();
                        break;
                }
                return true;
            }
        });
    }

    public void init(View view) {
        animScaleUp = AnimationUtils.loadAnimation(getContext(), R.anim.scale_up);
        animScaleDown = AnimationUtils.loadAnimation(getContext(), R.anim.scale_down);
        animCaraLeon = AnimationUtils.loadAnimation(getContext(), R.anim.slide_tutorial);

        viewCerrarAdmin = view.findViewById(R.id.viewAdmin_Close);
        viewAdminEntrarJugadores = view.findViewById(R.id.viewAdmin_Jugadores);
        viewAdminEntrarCartas = view.findViewById(R.id.viewAdmin_Cartas);
        tvAdminEntrarJugadores = view.findViewById(R.id.tvAdmin_Jugadores);
        tvAdminEntrarCartas = view.findViewById(R.id.tvAdmin_Cartas);
        imgCaraLeonAdmin = view.findViewById(R.id.imgAdmin_Leon);
    }
}