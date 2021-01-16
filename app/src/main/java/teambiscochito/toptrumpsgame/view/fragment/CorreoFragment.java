package teambiscochito.toptrumpsgame.view.fragment;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

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
import android.widget.TextView;

import teambiscochito.toptrumpsgame.R;
import teambiscochito.toptrumpsgame.model.room.pojo.User;
import teambiscochito.toptrumpsgame.viewmodel.ViewModel;

public class CorreoFragment extends Fragment {

    ViewModel viewModel;
    User userActual;
    View viewBackCorreo, viewCorreoBorrar, viewCorreoEnviar;
    Animation animScaleUp, animScaleDown;
    EditText etCorreo;
    TextView tvCorreoBorrar, tvCorreoEnviar, tvAlertaCorreo;
    NavController navController;
    private MediaPlayer mp_correo;
    Dialog dialogCorreo;

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

        navController = Navigation.findNavController(view);
        viewModel = new ViewModelProvider(getActivity()).get(ViewModel.class);
        userActual = viewModel.userActual;

        viewBackCorreo.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    viewBackCorreo.startAnimation(animScaleUp);

                    navController.navigate(R.id.action_correoFragment_to_menuFragment);

                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    viewBackCorreo.startAnimation(animScaleDown);
                }

                return true;
            }
        });

        viewCorreoBorrar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    viewCorreoBorrar.startAnimation(animScaleUp);
                    tvCorreoBorrar.startAnimation(animScaleUp);

                    etCorreo.setText("");

                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    viewCorreoBorrar.startAnimation(animScaleDown);
                    tvCorreoBorrar.startAnimation(animScaleDown);
                }

                return true;
            }
        });

        viewCorreoEnviar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    viewCorreoEnviar.startAnimation(animScaleUp);
                    tvCorreoEnviar.startAnimation(animScaleUp);

                    String correo = etCorreo.getText().toString();
                    String subject = getString(R.string.textCorreoSubject);
                    String mensaje = "¡Hola, " + userActual.getName() + "!\n" +
                            "La puntuación acumulada que llevas en Animales Salvajes es de " + userActual.getTrueAnswer() + "." + "\n" +
                            "¡Que sigas disfrutando del juego!" + "\n" +
                            "Team Biscochito © 2021";

                    if(correo.isEmpty()) {
                        tvAlertaCorreo.setText(R.string.textIntroduceCorreo);
                    } else if(!(Patterns.EMAIL_ADDRESS.matcher(correo).matches())) {
                        tvAlertaCorreo.setText(R.string.textCorreoNoValido);
                    } else if (Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {

                        Intent intent = new Intent(Intent.ACTION_SENDTO);

                        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{correo});
                        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
                        intent.putExtra(Intent.EXTRA_TEXT, mensaje);

                        intent.setData(Uri.parse("mailto:"));

                        if(intent.resolveActivity(getContext().getPackageManager()) != null) {

                            startActivity(intent);

                            etCorreo.setText("");
                            tvAlertaCorreo.setText("");

                            correoDialog();

                        } else {

                            tvAlertaCorreo.setText(R.string.textNoSePudoEnviarCorreo);

                        }

                    }

                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    viewCorreoEnviar.startAnimation(animScaleDown);
                    tvCorreoEnviar.startAnimation(animScaleDown);
                }

                return true;
            }
        });
    }

    public void init(View view) {

        animScaleUp = AnimationUtils.loadAnimation(getContext(), R.anim.scale_up);
        animScaleDown = AnimationUtils.loadAnimation(getContext(), R.anim.scale_down);

        initMediaPlayerCorreo();

        viewBackCorreo = view.findViewById(R.id.viewBackCorreo);
        viewCorreoBorrar = view.findViewById(R.id.viewCorreoBorrar);
        viewCorreoEnviar = view.findViewById(R.id.viewCorreoEnviar);
        tvCorreoBorrar = view.findViewById(R.id.tvCorreoBorrar);
        tvCorreoEnviar = view.findViewById(R.id.tvCorreoEnviar);
        tvAlertaCorreo = view.findViewById(R.id.tvAlertaCorreo);

        etCorreo = view.findViewById(R.id.etCorreo);

    }

    public void correoDialog() {

        View viewBackCorreoDialog;

        dialogCorreo = new Dialog(getContext());
        dialogCorreo.setContentView(R.layout.correo_dialog);
        dialogCorreo.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        Window window = dialogCorreo.getWindow();
        window.setGravity(Gravity.CENTER);
        window.getAttributes().windowAnimations = R.style.DialogAnimation;

        viewBackCorreoDialog = dialogCorreo.findViewById(R.id.viewBackCorreoDialog);

        viewBackCorreoDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogCorreo.dismiss();

            }
        });

        dialogCorreo.setCancelable(true);
        dialogCorreo.setCanceledOnTouchOutside(false);
        window.setLayout(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        dialogCorreo.show();

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