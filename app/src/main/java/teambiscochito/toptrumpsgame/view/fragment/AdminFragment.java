package teambiscochito.toptrumpsgame.view.fragment;

import android.app.ActionBar;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

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

import teambiscochito.toptrumpsgame.R;

public class AdminFragment extends Fragment {

    View viewCerrarAdmin, viewAdminEntrarJugadores, viewAdminEntrarCartas;
    Animation animScaleUp, animScaleDown, animCaraLeon;
    TextView tvAdminEntrarJugadores, tvAdminEntrarCartas;
    NavController navController;
    Dialog dialogSalirAdmin;
    ImageView imgCaraLeonAdmin;

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

        viewCerrarAdmin.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    viewCerrarAdmin.startAnimation(animScaleUp);

                    salirAdminDialog();

                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    viewCerrarAdmin.startAnimation(animScaleDown);
                }

                return true;
            }
        });

        viewAdminEntrarJugadores.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    viewAdminEntrarJugadores.startAnimation(animScaleUp);
                    tvAdminEntrarJugadores.startAnimation(animScaleUp);

                    navController.navigate(R.id.action_adminFragment_to_adminJugadorFragment);

                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    viewAdminEntrarJugadores.startAnimation(animScaleDown);
                    tvAdminEntrarJugadores.startAnimation(animScaleDown);
                }

                return true;
            }
        });

        viewAdminEntrarCartas.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    viewAdminEntrarCartas.startAnimation(animScaleUp);
                    tvAdminEntrarCartas.startAnimation(animScaleUp);

                    navController.navigate(R.id.action_adminFragment_to_adminCartasFragment);

                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    viewAdminEntrarCartas.startAnimation(animScaleDown);
                    tvAdminEntrarCartas.startAnimation(animScaleDown);
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

    public void salirAdminDialog() {

        View viewCancelarAdminDialog, viewAceptarAdminDialog;

        dialogSalirAdmin = new Dialog(getContext());
        dialogSalirAdmin.setContentView(R.layout.salir_admin_dialog);
        dialogSalirAdmin.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        Window window = dialogSalirAdmin.getWindow();
        window.setGravity(Gravity.CENTER);
        window.getAttributes().windowAnimations = R.style.DialogAnimation;

        viewCancelarAdminDialog = dialogSalirAdmin.findViewById(R.id.viewSalirDialogAdmin_Cancel);
        viewAceptarAdminDialog = dialogSalirAdmin.findViewById(R.id.viewSalirDialogAdmin_Accept);

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
                navController.navigate(R.id.action_adminFragment_to_chooseUserFragment);

            }
        });

        dialogSalirAdmin.setCancelable(true);
        dialogSalirAdmin.setCanceledOnTouchOutside(false);
        window.setLayout(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        dialogSalirAdmin.show();

    }

}