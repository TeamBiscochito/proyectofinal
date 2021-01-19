package teambiscochito.toptrumpsgame.view.fragment;

import android.app.ActionBar;
import android.app.Dialog;
import android.media.MediaPlayer;
import android.os.Bundle;

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

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import teambiscochito.toptrumpsgame.R;
import teambiscochito.toptrumpsgame.model.room.pojo.User;
import teambiscochito.toptrumpsgame.view.adapter.RecyclerJugadoresAdminAdapter;
import teambiscochito.toptrumpsgame.viewmodel.ViewModel;

public class AdminJugadorFragment extends Fragment {

    RecyclerView recyclerView;
    ViewModel viewModel;
    View viewBackAdminJugadores, viewCerrarAdminJugadores, viewAddJugador;
    ImageView imgAddJugador;
    TextView tvAddJugador, tvRvVacioAdminUser;
    Animation animScaleUp, animScaleDown;
    Dialog dialogSalirAdmin;

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

        viewModel = new ViewModelProvider(getActivity()).get(ViewModel.class);
        recyclerView = getView().findViewById(R.id.rvJugadoresAdmin);

        LiveData<List<User>> userList = viewModel.getUserList();
        userList.observe(getViewLifecycleOwner(), new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {

                RecyclerJugadoresAdminAdapter adapter = new RecyclerJugadoresAdminAdapter(users ,view, getActivity(), getContext());
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

                if(adapter.getItemCount() == 0) {

                    tvRvVacioAdminUser.setText(R.string.alertRvVacioAdminUser);

                }

            }
        });

        viewBackAdminJugadores.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    viewBackAdminJugadores.startAnimation(animScaleUp);

                    navController.navigate(R.id.action_adminJugadorFragment_to_adminFragment);

                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    viewBackAdminJugadores.startAnimation(animScaleDown);

                }

                return true;
            }
        });

        viewAddJugador.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    viewAddJugador.startAnimation(animScaleUp);
                    imgAddJugador.startAnimation(animScaleUp);
                    tvAddJugador.startAnimation(animScaleUp);

                    navController.navigate(R.id.action_adminJugadorFragment_to_addPlayerFragment);

                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    viewAddJugador.startAnimation(animScaleDown);
                    imgAddJugador.startAnimation(animScaleDown);
                    tvAddJugador.startAnimation(animScaleDown);

                }

                return true;
            }
        });

        viewCerrarAdminJugadores.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    viewCerrarAdminJugadores.startAnimation(animScaleUp);

                    salirAdminDialog();

                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    viewCerrarAdminJugadores.startAnimation(animScaleDown);

                }

                return true;
            }
        });

    }

    private void init(View view) {

        animScaleUp = AnimationUtils.loadAnimation(getContext(), R.anim.scale_up);
        animScaleDown = AnimationUtils.loadAnimation(getContext(), R.anim.scale_down);

        viewBackAdminJugadores = view.findViewById(R.id.viewBackAdminJugadores);
        viewCerrarAdminJugadores = view.findViewById(R.id.viewCerrarAdminJugadores);
        viewAddJugador = view.findViewById(R.id.viewAddJugador);
        imgAddJugador = view.findViewById(R.id.imgAddJugador);
        tvAddJugador = view.findViewById(R.id.tvAddJugador);
        tvRvVacioAdminUser = view.findViewById(R.id.tvRvVacioAdminUser);

    }

    public void salirAdminDialog() {

        View viewCancelarAdminDialog, viewAceptarAdminDialog;

        dialogSalirAdmin = new Dialog(getContext());
        dialogSalirAdmin.setContentView(R.layout.salir_admin_dialog);
        dialogSalirAdmin.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        Window window = dialogSalirAdmin.getWindow();
        window.setGravity(Gravity.CENTER);
        window.getAttributes().windowAnimations = R.style.DialogAnimation;

        viewCancelarAdminDialog = dialogSalirAdmin.findViewById(R.id.viewCancelarAdminDialog);
        viewAceptarAdminDialog = dialogSalirAdmin.findViewById(R.id.viewAceptarAdminDialog);

        viewCancelarAdminDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialogSalirAdmin.dismiss();

            }
        });

        viewAceptarAdminDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialogSalirAdmin.dismiss();
                navController.navigate(R.id.action_adminJugadorFragment_to_chooseUserFragment);

            }
        });

        dialogSalirAdmin.setCancelable(true);
        dialogSalirAdmin.setCanceledOnTouchOutside(false);
        window.setLayout(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        dialogSalirAdmin.show();

    }

}