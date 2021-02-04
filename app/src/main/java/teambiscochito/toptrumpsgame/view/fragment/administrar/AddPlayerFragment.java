package teambiscochito.toptrumpsgame.view.fragment.administrar;

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
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import teambiscochito.toptrumpsgame.R;
import teambiscochito.toptrumpsgame.model.room.pojo.User;
import teambiscochito.toptrumpsgame.validar.ValidarDatos;
import teambiscochito.toptrumpsgame.view.adapter.VpAvatarAdapter;
import teambiscochito.toptrumpsgame.view.fragment.DialogosGenerales;
import teambiscochito.toptrumpsgame.viewmodel.ViewModel;

/**
 * <h2 align="center">Team Biscochito</h2><hr>
 * <p>
 * Clase para añadir nuevos jugadores a nuestra base de datos. Podremos escoger un nombre y una foto
 * dentro de un ViewPager2. El nombre no se puede dejar en blanco.
 */
@SuppressWarnings({"Convert2Lambda"})
// Comente la línea de arriba para ver los posibles Lambdas a convertir
public class AddPlayerFragment extends Fragment {
    ViewPager2 vp_avatar;
    int[] avatares = {R.drawable.av_tigre, R.drawable.av_hipo, R.drawable.av_tucan, R.drawable.av_cerdo, R.drawable.av_gato, R.drawable.av_gallina};
    VpAvatarAdapter adapter;

    Animation animScaleUp, animScaleDown;

    NavController navController;
    View viewNextAvatar, viewPreviousAvatar, viewBackAdminAddPlayer, viewAddJugador;
    TextView tvAddJugador, tvAlertaAddJugador;

    EditText etNombreJugador;
    ViewModel viewModel;

    public AddPlayerFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_player, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);

        viewModel = new ViewModelProvider(requireActivity()).get(ViewModel.class);
        navController = Navigation.findNavController(view);

        vp_avatar = view.findViewById(R.id.vpAddPlayer_Avatar);

        vp_avatar.setUserInputEnabled(false);

        adapter = new VpAvatarAdapter(avatares);

        vp_avatar.setClipToPadding(false);
        vp_avatar.setClipChildren(false);
        vp_avatar.setOffscreenPageLimit(3);
        vp_avatar.getChildAt(0).setOverScrollMode(View.OVER_SCROLL_NEVER);
        vp_avatar.setAdapter(adapter);

        CompositePageTransformer transformer = new CompositePageTransformer();

        transformer.addTransformer(new MarginPageTransformer(8));

        transformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float v = 1 - Math.abs(position);

                page.setScaleY(0.8f + v * 0.2f);
            }
        });

        vp_avatar.setPageTransformer(transformer);

        nextAvatar();

        previousAvatar();

        addJugador();

        DialogosGenerales.volverAtrasDialog(R.id.action_addPlayerFragment_to_adminJugadorFragment,
                getContext(), view, viewBackAdminAddPlayer);
    }

    /**
     * <h2 align="center">Team Biscochito</h2><hr>
     * <p>
     * Método que hace referencia al ViewPager2 cuando le damos a la vista irá hacia adelante para
     * ver los diferentes avatares disponibles.
     * <br><br>
     * Referencia del método en: {@link AddPlayerFragment#onViewCreated(View, Bundle)}
     */
    private void nextAvatar() {
        viewNextAvatar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        viewNextAvatar.startAnimation(animScaleUp);
                        if (vp_avatar.getCurrentItem() + 1 < adapter.getItemCount()) {
                            vp_avatar.setCurrentItem(vp_avatar.getCurrentItem() + 1);
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        viewNextAvatar.startAnimation(animScaleDown);
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
     * Método que hace referencia al ViewPager2 cuando le damos a la vista irá hacia atrás para ver
     * los diferentes avatares disponibles.
     * <br><br>
     * Referencia del método en: {@link #onViewCreated(View, Bundle)}
     */
    private void previousAvatar() {
        viewPreviousAvatar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        viewPreviousAvatar.startAnimation(animScaleUp);
                        if (vp_avatar.getCurrentItem() + 1 > 0) {
                            vp_avatar.setCurrentItem(vp_avatar.getCurrentItem() - 1);
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        viewPreviousAvatar.startAnimation(animScaleDown);
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
     * Método por el cual le pulsamos en la vista de añadir jugador y lo agregamos a la base de datos.
     * Este método pasa una serie de verificaciones para un nombre correcto y cuando se añade vuelve
     * al fragmento anterior.
     * <br><br>
     * Referencia del método en: {@link AddPlayerFragment#onViewCreated(View, Bundle)}
     */
    private void addJugador() {
        viewAddJugador.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        viewAddJugador.startAnimation(animScaleUp);
                        tvAddJugador.startAnimation(animScaleUp);

                        String nombre = etNombreJugador.getText().toString();
                        viewModel.getNameFromName(nombre);
                        int num = viewModel.getRepeatedName();

                        if (nombre.isEmpty()) {
                            tvAlertaAddJugador.setText(R.string.tvIntroduceNombreSinPuntos);
                        } else if (ValidarDatos.validarNombreJugador(nombre)) {
                            tvAlertaAddJugador.setText(R.string.tvNombreDemasiadoLargo);
                        } else if (num != 0) {
                            tvAlertaAddJugador.setText(R.string.tvNombreYaEnUso);
                        } else {
                            int avatar = avatares[vp_avatar.getCurrentItem()];
                            viewModel.insertUser(new User(nombre, avatar));
                            tvAlertaAddJugador.setText("");
                            navController.navigate(R.id.action_addPlayerFragment_to_adminJugadorFragment);
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        viewAddJugador.startAnimation(animScaleDown);
                        tvAddJugador.startAnimation(animScaleDown);
                        v.performClick();
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
    }

    private void init(View view) {
        animScaleUp = AnimationUtils.loadAnimation(getContext(), R.anim.scale_up);
        animScaleDown = AnimationUtils.loadAnimation(getContext(), R.anim.scale_down);

        viewNextAvatar = view.findViewById(R.id.viewAddPlayer_Next);
        viewPreviousAvatar = view.findViewById(R.id.gdAddPlayer_Previous);
        viewBackAdminAddPlayer = view.findViewById(R.id.viewAddPlayer_Back);
        viewAddJugador = view.findViewById(R.id.viewAddPlayer_AddJugador);
        tvAddJugador = view.findViewById(R.id.tvAddPlayer_AddJugador);
        tvAlertaAddJugador = view.findViewById(R.id.tvAddPlayer_Alerta);

        etNombreJugador = view.findViewById(R.id.etAddPlayer_NombreJugador);
    }
}