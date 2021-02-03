package teambiscochito.toptrumpsgame.view.fragment.jugar;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import teambiscochito.toptrumpsgame.R;
import teambiscochito.toptrumpsgame.model.room.pojo.User;
import teambiscochito.toptrumpsgame.view.adapter.RecyclerJugadoresSeleccionAdapter;
import teambiscochito.toptrumpsgame.viewmodel.ViewModel;

public class ChooseUserFragment extends Fragment {

    RecyclerView recyclerView;
    ViewModel viewModel;

    TextView tvEligeTuJugador, tvRvVacioChooseUser;
    Dialog dialogAjustes, salirDialog, tutorialDialog;
    SharedPreferences sharedPreferences;
    Animation anim, animScaleUp, animScaleDown;
    NavController navController;

    private MediaPlayer mp_seleccionarJugador;

    public ChooseUserFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_choose_user, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initMediaPlayerSeleccionarJugador();

        navController = Navigation.findNavController(view);
        sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);

        boolean firstStart = sharedPreferences.getBoolean("firstStart", true);

        if(firstStart) {

            tutorialDialog();

        }

        initAnim();

        tvEligeTuJugador = view.findViewById(R.id.tvChooseUser_Cartel);
        tvRvVacioChooseUser = view.findViewById(R.id.tvChooseUser_ErrorInfo);

        tvEligeTuJugador.startAnimation(anim);

        View viewCerrarAppChooseUser = view.findViewById(R.id.viewChooseUser_Close);

        viewCerrarAppChooseUser.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    viewCerrarAppChooseUser.startAnimation(animScaleUp);

                    salirDialog();

                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    viewCerrarAppChooseUser.startAnimation(animScaleDown);

                }

                return true;
            }
        });

        View imgAjustesChooseUser = view.findViewById(R.id.viewChooseUser_Ajustes);

        imgAjustesChooseUser.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    imgAjustesChooseUser.startAnimation(animScaleUp);

                    ajustesDialog();

                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    imgAjustesChooseUser.startAnimation(animScaleDown);

                }

                return true;
            }
        });

        viewModel = new ViewModelProvider(getActivity()).get(ViewModel.class);
        recyclerView = getView().findViewById(R.id.rvJugadoresSeleccion);

        LiveData<List<User>> userList = viewModel.getUserList();
        userList.observe(getViewLifecycleOwner(), new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {

                RecyclerJugadoresSeleccionAdapter adapter = new RecyclerJugadoresSeleccionAdapter(users ,view, getActivity(), getContext());
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

                if(adapter.getItemCount() == 0) {

                    tvRvVacioChooseUser.setText(R.string.alertRvVacioChooseUser);

                }

            }
        });

    }

    public void initAnim() {

        anim = AnimationUtils.loadAnimation(getContext(), R.anim.tv_choose_player);
        animScaleUp = AnimationUtils.loadAnimation(getContext(), R.anim.scale_up);
        animScaleDown = AnimationUtils.loadAnimation(getContext(), R.anim.scale_down);

    }

    public void ajustesDialog() {

        ImageView imgAtrasAjustesDialog;

        dialogAjustes = new Dialog(getContext());
        dialogAjustes.setContentView(R.layout.ajustes_dialog);
        dialogAjustes.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        Window window = dialogAjustes.getWindow();
        window.setGravity(Gravity.CENTER);
        window.getAttributes().windowAnimations = R.style.DialogAnimation;

        imgAtrasAjustesDialog = dialogAjustes.findViewById(R.id.imgDialogAjustesBack);

        imgAtrasAjustesDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialogAjustes.dismiss();

            }
        });

        dialogAjustes.setCancelable(true);
        dialogAjustes.setCanceledOnTouchOutside(false);
        window.setLayout(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        dialogAjustes.show();

        EditText claveEtAjustes = dialogAjustes.findViewById(R.id.etDialogAjustesClave);
        View viewBtAccederAjustesDialog = dialogAjustes.findViewById(R.id.viewDialogAjustesBT);
        TextView tvAccederAjustesDialog = dialogAjustes.findViewById(R.id.tvDialogAjustesTextBT);
        TextView tvAlertaAjustesDialog = dialogAjustes.findViewById(R.id.tvDialogAjustesError);

        String claveAdmin = sharedPreferences.getString("clave_admin", "");

        viewBtAccederAjustesDialog.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    viewBtAccederAjustesDialog.startAnimation(animScaleUp);
                    tvAccederAjustesDialog.startAnimation(animScaleUp);

                    String txt = claveEtAjustes.getText().toString();

                    if(txt.equals(claveAdmin)){

                        mp_seleccionarJugador.stop();
                        dialogAjustes.dismiss();
                        navController.navigate(R.id.action_chooseUserFragment_to_adminFragment);

                    }else{
                        tvAlertaAjustesDialog.setText(R.string.textAccesoDenegado);
                    }

                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    viewBtAccederAjustesDialog.startAnimation(animScaleDown);
                    tvAccederAjustesDialog.startAnimation(animScaleDown);
                }

                return true;
            }
        });

    }

    public void salirDialog() {

        View viewCancelarSalirDialog, viewAceptarSalirDialog;

        salirDialog = new Dialog(getContext());
        salirDialog.setContentView(R.layout.salir_app_dialog);
        salirDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        Window window = salirDialog.getWindow();
        window.setGravity(Gravity.CENTER);
        window.getAttributes().windowAnimations = R.style.DialogAnimation;

        viewCancelarSalirDialog = salirDialog.findViewById(R.id.viewSalirDialogApp_Cancel);
        viewAceptarSalirDialog = salirDialog.findViewById(R.id.viewSalirDialogApp_Accept);

        viewCancelarSalirDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                salirDialog.dismiss();

            }
        });

        viewAceptarSalirDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getActivity().finish();
                System.exit(0);

            }
        });

        salirDialog.setCancelable(true);
        salirDialog.setCanceledOnTouchOutside(false);
        window.setLayout(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        salirDialog.show();

    }

    public void tutorialDialog() {

        View viewCancelarTutorialDialog, viewAceptarTutorialDialog;

        tutorialDialog = new Dialog(getContext());
        tutorialDialog.setContentView(R.layout.ir_a_tutorial_dialog);
        tutorialDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        Window window = tutorialDialog.getWindow();
        window.setGravity(Gravity.CENTER);
        window.getAttributes().windowAnimations = R.style.DialogAnimation;

        viewCancelarTutorialDialog = tutorialDialog.findViewById(R.id.viewDialogIrTutorial_Cancel);
        viewAceptarTutorialDialog = tutorialDialog.findViewById(R.id.viewDialogIrTutorial_Accept);

        viewCancelarTutorialDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tutorialDialog.dismiss();

            }
        });

        viewAceptarTutorialDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mp_seleccionarJugador.stop();

                tutorialDialog.dismiss();

                Bundle bundle = new Bundle();
                bundle.putBoolean("vieneDelFirstStart", true);

                navController.navigate(R.id.action_chooseUserFragment_to_tutorialFragment, bundle);

            }
        });

        tutorialDialog.setCancelable(true);
        tutorialDialog.setCanceledOnTouchOutside(false);
        window.setLayout(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        tutorialDialog.show();

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("firstStart", false);
        editor.apply();

    }

    public void initMediaPlayerSeleccionarJugador() {

        mp_seleccionarJugador = MediaPlayer.create(getContext(), R.raw.jugador_music);
        mp_seleccionarJugador.setLooping(true);
        mp_seleccionarJugador.start();

    }

    @Override
    public void onPause() {
        super.onPause();
        mp_seleccionarJugador.pause();
    }

    @Override
    public void onResume() {
        super.onResume();
        mp_seleccionarJugador.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mp_seleccionarJugador.stop();
        mp_seleccionarJugador.release();
    }
}