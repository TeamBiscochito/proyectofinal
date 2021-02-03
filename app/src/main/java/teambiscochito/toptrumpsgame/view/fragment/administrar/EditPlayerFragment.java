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
 * Clase para editar el jugador seleccionado en {@link AdminJugadorFragment} donde tendremos todas las
 * propiedades de nuestro perfil que podemos modificar a nuestro antojo.
 */
public class EditPlayerFragment extends Fragment {

    ViewPager2 vp_avatar;
    int[] avatares = {R.drawable.av_tigre, R.drawable.av_hipo, R.drawable.av_tucan, R.drawable.av_cerdo, R.drawable.av_gato, R.drawable.av_gallina};
    VpAvatarAdapter adapter;
    Animation animScaleUp, animScaleDown;
    NavController navController;
    View viewNextAvatar, viewPreviousAvatar, viewBackAdminEditPlayer, viewEditJugador, viewSeleccionarAvatarActual;
    TextView tvEditJugador, tvAlertaEditJugador, tvSeleccionarAvatarActual;

    EditText etNombreJugador;
    ViewModel viewModel;
    int numeroAvatarParaCargar;

    public EditPlayerFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_player, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init(view);

        viewModel = new ViewModelProvider(requireActivity()).get(ViewModel.class);
        navController = Navigation.findNavController(view);

        User user = viewModel.getUser();

        vp_avatar = view.findViewById(R.id.vpEditPlayer_Avatar);

        vp_avatar.setUserInputEnabled(false);

        etNombreJugador.setText(user.getName());

        cargarAvatres(user);

        adapter = new VpAvatarAdapter(avatares);

        vp_avatar.setClipToPadding(false);
        vp_avatar.setClipChildren(false);
        vp_avatar.setOffscreenPageLimit(3);
        vp_avatar.getChildAt(0).setOverScrollMode(View.OVER_SCROLL_NEVER);
        vp_avatar.setAdapter(adapter);

        CompositePageTransformer transformer = viewPager();

        vp_avatar.setPageTransformer(transformer);

        DialogosGenerales.volverAtrasDialog(R.id.action_editPlayerFragment_to_adminJugadorFragment,
                getContext(), view, viewBackAdminEditPlayer);

        avatarActual();

        nextAvatar();

        previousAvatar();

        viewEditJugador.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        viewEditJugador.startAnimation(animScaleUp);
                        tvEditJugador.startAnimation(animScaleUp);

                        String nombre = etNombreJugador.getText().toString();
                        viewModel.getNameFromName(nombre);
                        int num = viewModel.getRepeatedName();

                        verificarJugador(nombre, num, user);
                        break;
                    case MotionEvent.ACTION_UP:
                        viewEditJugador.startAnimation(animScaleDown);
                        tvEditJugador.startAnimation(animScaleDown);
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
     * Método para verificar los datos a la hora de editar el jugador.
     * <br><br>
     *
     * @param nombre nombre que usa el jugador.
     * @param num    número identificativo del jugador por si ya existe el nombre en la base de datos.
     * @param user   pasamos el perfil del jugador
     */
    private void verificarJugador(String nombre, int num, User user) {
        if (nombre.isEmpty()) {

            tvAlertaEditJugador.setText(R.string.tvIntroduceNombreSinPuntos);

        } else if (ValidarDatos.validarNombreJugador(nombre)) {

            tvAlertaEditJugador.setText(R.string.tvNombreDemasiadoLargo);

        } else if (nombre.equals(user.getName())) {

            getDatosAvatar(user);

        } else if (num != 0 && nombre.equals(user.getName())) {
            getDatosAvatar(user);

        } else if (num != 0 && !(nombre.equals(user.getName()))) {

            tvAlertaEditJugador.setText(R.string.tvNombreYaEnUso);

        } else {
            int avatar = avatares[vp_avatar.getCurrentItem()];

            user.setName(nombre);
            user.setAvatar(avatar);
            viewModel.updateUser(user);
            tvAlertaEditJugador.setText("");
            navController.navigate(R.id.action_editPlayerFragment_to_adminJugadorFragment);
        }
    }

    /**
     * <h2 align="center">Team Biscochito</h2><hr>
     * <p>
     * Método para actualizar la imagen de perfil en caso de que el nombre se exactamente el mismo.
     * <br><br>
     *
     * @param user pasamos el perfil del jugador.
     */

    private void getDatosAvatar(User user) {
        int avatar = avatares[vp_avatar.getCurrentItem()];

        user.setAvatar(avatar);
        viewModel.updateUser(user);
        tvAlertaEditJugador.setText("");
        navController.navigate(R.id.action_editPlayerFragment_to_adminJugadorFragment);
    }

    /**
     * <h2 align="center">Team Biscochito</h2><hr>
     * <p>
     * Método para ir hacia detrás en la selección del avatar del {@link #viewPager()}
     * <br><br>
     * Referencia del método en: {@link EditPlayerFragment#onViewCreated(View, Bundle)}
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
     * Método para ir hacia delante en la selección del avatar del {@link #viewPager()}
     * <br><br>
     * Referencia del método en: {@link EditPlayerFragment#onViewCreated(View, Bundle)}
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
     * Método para seleccionar el avatar que teníamos antes aplicado.
     * <br><br>
     * Referencia del método en: {@link EditPlayerFragment#onViewCreated(View, Bundle)}
     */
    private void avatarActual() {
        viewSeleccionarAvatarActual.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        viewSeleccionarAvatarActual.startAnimation(animScaleUp);
                        tvSeleccionarAvatarActual.startAnimation(animScaleUp);

                        vp_avatar.setCurrentItem(numeroAvatarParaCargar);
                        break;
                    case MotionEvent.ACTION_UP:
                        viewSeleccionarAvatarActual.startAnimation(animScaleDown);
                        tvSeleccionarAvatarActual.startAnimation(animScaleDown);
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
     * Método método para retornar la instancia del {@link CompositePageTransformer}
     * <br><br>
     * Referencia del método en: {@link #onViewCreated(View, Bundle)}
     *
     * @return ViewPager para luego instanciarlo en {@link #onViewCreated(View, Bundle)}
     */
    private CompositePageTransformer viewPager() {
        CompositePageTransformer transformer = new CompositePageTransformer();
        transformer.addTransformer(new MarginPageTransformer(8));
        transformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float v = 1 - Math.abs(position);
                page.setScaleY(0.8f + v * 0.2f);
            }
        });
        return transformer;
    }

    /**
     * <h2 align="center">Team Biscochito</h2><hr>
     * <p>
     * Método para cargar los diferentes avatares que tenemos disponibles mediante un array de recursos.
     * <br><br>
     * Referencia del método en: {@link #onViewCreated(View, Bundle)}
     *
     * @param user pasamos un usuario, el seleccionado.
     */
    private void cargarAvatres(User user) {
        String nombreAvatarParaCargar = getResources().getResourceEntryName(user.getAvatar());

        switch (nombreAvatarParaCargar) {
            case "av_tigre":
                numeroAvatarParaCargar = 0;
                break;
            case "av_hipo":
                numeroAvatarParaCargar = 1;
                break;
            case "av_loro":
                numeroAvatarParaCargar = 2;
                break;
            case "av_cerdo":
                numeroAvatarParaCargar = 3;
                break;
            case "av_gato":
                numeroAvatarParaCargar = 4;
                break;
            case "av_gallina":
                numeroAvatarParaCargar = 5;
                break;
        }
    }

    private void init(View view) {
        animScaleUp = AnimationUtils.loadAnimation(getContext(), R.anim.scale_up);
        animScaleDown = AnimationUtils.loadAnimation(getContext(), R.anim.scale_down);

        viewNextAvatar = view.findViewById(R.id.viewEditPlayer_Next);
        viewPreviousAvatar = view.findViewById(R.id.viewEditPlayer_Previous);
        viewBackAdminEditPlayer = view.findViewById(R.id.viewEditPlayer_Back);
        viewEditJugador = view.findViewById(R.id.viewEditPlayer_Editar);
        viewSeleccionarAvatarActual = view.findViewById(R.id.viewEditPlayer_Selecciona);
        tvEditJugador = view.findViewById(R.id.tvEditPlayer_Editar);
        tvAlertaEditJugador = view.findViewById(R.id.tvEditPlayer_Alerta);
        tvSeleccionarAvatarActual = view.findViewById(R.id.tvEditPlayer_SeleccionaActual);

        etNombreJugador = view.findViewById(R.id.etEditPlayer_Nombre);
    }
}