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
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import teambiscochito.toptrumpsgame.R;
import teambiscochito.toptrumpsgame.model.room.pojo.User;
import teambiscochito.toptrumpsgame.view.adapter.RecyclerJugadoresAdminAdapter;
import teambiscochito.toptrumpsgame.view.fragment.DialogosGenerales;
import teambiscochito.toptrumpsgame.viewmodel.ViewModel;

/**
 * <h2 align="center">Team Biscochito</h2><hr>
 * <p>
 * Clase para ver a los jugadores disponibles o que tenemos creado. Desde aqui podemos editar a los
 * jugadores como también poder eliminarlos.
 */
public class AdminJugadorFragment extends Fragment {

    RecyclerView recyclerView;
    ViewModel viewModel;
    View viewBackAdminJugadores, viewCerrarAdminJugadores, viewAddJugador;
    ImageView imgAddJugador;
    TextView tvAddJugador;
    Animation animScaleUp, animScaleDown;

    NavController navController;

    public AdminJugadorFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_admin_jugador, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);

        navController = Navigation.findNavController(view);

        viewModel = new ViewModelProvider(requireActivity()).get(ViewModel.class);
        recyclerView = requireView().findViewById(R.id.rvAdminJugador);

        LiveData<List<User>> userList = viewModel.getUserList();
        userList.observe(getViewLifecycleOwner(), new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                RecyclerJugadoresAdminAdapter adapter = new RecyclerJugadoresAdminAdapter(users, view, getActivity(), getContext());
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            }
        });

        addJugador();

        DialogosGenerales.volverAtrasDialog(R.id.action_adminJugadorFragment_to_adminFragment,
                getContext(), view, viewBackAdminJugadores);
    }

    /**
     * <h2 align="center">Team Biscochito</h2><hr>
     * <p>
     * Método para añadir jugador y crear un nuevo perfil para el juego. Al hacer clic iremos al
     * fragmento de añadir jugador.
     * <br><br>
     * Referencia del método en: {@link #onViewCreated(View, Bundle)}
     */
    private void addJugador() {
        viewAddJugador.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        viewAddJugador.startAnimation(animScaleUp);
                        imgAddJugador.startAnimation(animScaleUp);
                        tvAddJugador.startAnimation(animScaleUp);

                        navController.navigate(R.id.action_adminJugadorFragment_to_addPlayerFragment);
                        break;
                    case MotionEvent.ACTION_UP:
                        viewAddJugador.startAnimation(animScaleDown);
                        imgAddJugador.startAnimation(animScaleDown);
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

        viewBackAdminJugadores = view.findViewById(R.id.viewAdminJugador_Back);
        viewCerrarAdminJugadores = view.findViewById(R.id.viewAdminJugador_Close);
        viewAddJugador = view.findViewById(R.id.viewAdminJugadores_Add);
        imgAddJugador = view.findViewById(R.id.imgAdminJugadores_Add);
        tvAddJugador = view.findViewById(R.id.tvAdminJugadores_Add);
    }
}