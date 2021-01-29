package teambiscochito.toptrumpsgame.view.fragment;

import android.app.ActionBar;
import android.app.Dialog;
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
import teambiscochito.toptrumpsgame.model.room.pojo.Card;
import teambiscochito.toptrumpsgame.view.adapter.RecyclerCartasAdminAdapter;
import teambiscochito.toptrumpsgame.viewmodel.ViewModel;

public class AdminCartasFragment extends Fragment {

    RecyclerView recyclerView;
    ViewModel viewModel;
    View viewBackAdminCartas, viewCerrarAdminCartas, viewAddCarta;
    ImageView imgAddCarta;
    TextView tvAddCarta;
    Animation animScaleUp, animScaleDown;
    Dialog dialogSalirAdmin;

    NavController navController;

    public AdminCartasFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_admin_cartas, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init(view);

        navController = Navigation.findNavController(view);

        viewModel = new ViewModelProvider(getActivity()).get(ViewModel.class);
        recyclerView = getView().findViewById(R.id.rvCartasAdmin);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);

        RecyclerCartasAdminAdapter adapter = new RecyclerCartasAdminAdapter(view, getActivity(), getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);

        viewBackAdminCartas.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    viewBackAdminCartas.startAnimation(animScaleUp);

                    navController.navigate(R.id.action_adminCartasFragment_to_adminFragment);

                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    viewBackAdminCartas.startAnimation(animScaleDown);

                }

                return true;
            }
        });

        viewAddCarta.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    viewAddCarta.startAnimation(animScaleUp);
                    imgAddCarta.startAnimation(animScaleUp);
                    tvAddCarta.startAnimation(animScaleUp);

                    navController.navigate(R.id.action_adminCartasFragment_to_addCardFragment);

                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    viewAddCarta.startAnimation(animScaleDown);
                    imgAddCarta.startAnimation(animScaleDown);
                    tvAddCarta.startAnimation(animScaleDown);

                }

                return true;
            }
        });

        viewCerrarAdminCartas.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    viewCerrarAdminCartas.startAnimation(animScaleUp);

                    salirAdminDialog();

                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    viewCerrarAdminCartas.startAnimation(animScaleDown);

                }

                return true;
            }
        });

    }

    private void init(View view) {

        animScaleUp = AnimationUtils.loadAnimation(getContext(), R.anim.scale_up);
        animScaleDown = AnimationUtils.loadAnimation(getContext(), R.anim.scale_down);

        viewBackAdminCartas = view.findViewById(R.id.viewBackAdminCartas);
        viewCerrarAdminCartas = view.findViewById(R.id.viewCerrarAdminCartas);
        viewAddCarta = view.findViewById(R.id.viewAddCarta);
        imgAddCarta = view.findViewById(R.id.imgAddCarta);
        tvAddCarta = view.findViewById(R.id.tvAddCarta);

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
                navController.navigate(R.id.action_adminCartasFragment_to_chooseUserFragment);

            }
        });

        dialogSalirAdmin.setCancelable(true);
        dialogSalirAdmin.setCanceledOnTouchOutside(false);
        window.setLayout(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        dialogSalirAdmin.show();

    }
}