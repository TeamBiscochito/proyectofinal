package teambiscochito.toptrumpsgame.view.fragment;

import android.app.ActionBar;
import android.app.Dialog;
import android.media.MediaPlayer;
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
import android.widget.TextView;

import teambiscochito.toptrumpsgame.R;

public class JuegoFragment extends Fragment {

    private int respuestasHastaElMomentoAcertadas = 0;

    private MediaPlayer mp_juego;
    View viewCloseJuego, viewBotonJuego1, viewBotonJuego2, viewBotonJuego3, viewBotonJuego4, viewPergaminoPreguntaJugar, viewNombreAnimalJuego, bird1, bird2, bird3, bird4;
    TextView tvBotonJuego1, tvBotonJuego2, tvBotonJuego3, tvBotonJuego4, tvPreguntaJuegoDeterminante, tvPreguntaJuegoTema, tvNombreAnimalJuego;
    Animation animScaleUp, animScaleDown, animPergaminoPregunta, animTableroNombre, animFade;
    NavController navController;
    Dialog salirJugarDialog;

    public JuegoFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_juego, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init(view);

        navController = Navigation.findNavController(view);

        initMediaPlayerJuego();

        viewPergaminoPreguntaJugar.startAnimation(animPergaminoPregunta);
        tvPreguntaJuegoTema.startAnimation(animPergaminoPregunta);

        viewNombreAnimalJuego.startAnimation(animTableroNombre);
        tvNombreAnimalJuego.startAnimation(animTableroNombre);

        bird1.startAnimation(animFade);
        bird2.startAnimation(animFade);
        bird3.startAnimation(animFade);
        bird4.startAnimation(animFade);

        viewCloseJuego.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    viewCloseJuego.startAnimation(animScaleUp);

                    salirJugarDialog();

                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    viewCloseJuego.startAnimation(animScaleDown);

                }

                return true;
            }
        });

        viewBotonJuego1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    viewBotonJuego1.startAnimation(animScaleUp);
                    tvBotonJuego1.startAnimation(animScaleUp);

                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    viewBotonJuego1.startAnimation(animScaleDown);
                    tvBotonJuego1.startAnimation(animScaleDown);

                }

                return true;
            }
        });

        viewBotonJuego2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    viewBotonJuego2.startAnimation(animScaleUp);
                    tvBotonJuego2.startAnimation(animScaleUp);

                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    viewBotonJuego2.startAnimation(animScaleDown);
                    tvBotonJuego2.startAnimation(animScaleDown);

                }

                return true;
            }
        });

        viewBotonJuego3.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    viewBotonJuego3.startAnimation(animScaleUp);
                    tvBotonJuego3.startAnimation(animScaleUp);

                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    viewBotonJuego3.startAnimation(animScaleDown);
                    tvBotonJuego3.startAnimation(animScaleDown);

                }

                return true;
            }
        });

        viewBotonJuego4.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    viewBotonJuego4.startAnimation(animScaleUp);
                    tvBotonJuego4.startAnimation(animScaleUp);

                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    viewBotonJuego4.startAnimation(animScaleDown);
                    tvBotonJuego4.startAnimation(animScaleDown);

                }

                return true;
            }
        });

    }

    private void init(View view) {

        animScaleUp = AnimationUtils.loadAnimation(getContext(), R.anim.scale_up);
        animScaleDown = AnimationUtils.loadAnimation(getContext(), R.anim.scale_down);
        animPergaminoPregunta = AnimationUtils.loadAnimation(getContext(), R.anim.slide_jugar);
        animTableroNombre = AnimationUtils.loadAnimation(getContext(), R.anim.slide_tutorial);
        animFade = AnimationUtils.loadAnimation(getContext(), R.anim.fade_birds);

        viewCloseJuego = view.findViewById(R.id.viewCloseJuego);
        viewBotonJuego1 = view.findViewById(R.id.viewBotonJuego1);
        viewBotonJuego2 = view.findViewById(R.id.viewBotonJuego2);
        viewBotonJuego3 = view.findViewById(R.id.viewBotonJuego3);
        viewBotonJuego4 = view.findViewById(R.id.viewBotonJuego4);
        viewPergaminoPreguntaJugar = view.findViewById(R.id.viewPergaminoPreguntaJugar);
        viewNombreAnimalJuego = view.findViewById(R.id.viewNombreAnimalJuego);
        bird1 = view.findViewById(R.id.bird1);
        bird2 = view.findViewById(R.id.bird2);
        bird3 = view.findViewById(R.id.bird3);
        bird4 = view.findViewById(R.id.bird4);

        tvBotonJuego1 = view.findViewById(R.id.tvBotonJuego1);
        tvBotonJuego2 = view.findViewById(R.id.tvBotonJuego2);
        tvBotonJuego3 = view.findViewById(R.id.tvBotonJuego3);
        tvBotonJuego4 = view.findViewById(R.id.tvBotonJuego4);
        tvPreguntaJuegoDeterminante = view.findViewById(R.id.tvPreguntaJuegoDeterminante);
        tvPreguntaJuegoTema = view.findViewById(R.id.tvPreguntaJuegoTema);
        tvNombreAnimalJuego = view.findViewById(R.id.tvNombreAnimalJuego);

    }

    public void salirJugarDialog() {

        View viewCancelarSalirAlMenuDialog, viewAceptarSalirAlMenuDialog;
        TextView tvPuntosJuegoDialog;

        salirJugarDialog = new Dialog(getContext());
        salirJugarDialog.setContentView(R.layout.salir_jugar_dialog);
        salirJugarDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        Window window = salirJugarDialog.getWindow();
        window.setGravity(Gravity.CENTER);
        window.getAttributes().windowAnimations = R.style.DialogAnimation;

        viewCancelarSalirAlMenuDialog = salirJugarDialog.findViewById(R.id.viewCancelarSalirAlMenuDialog);
        viewAceptarSalirAlMenuDialog = salirJugarDialog.findViewById(R.id.viewAceptarSalirAlMenuDialog);
        tvPuntosJuegoDialog = salirJugarDialog.findViewById(R.id.tvPuntosJuegoDialog);

        tvPuntosJuegoDialog.setText("" + respuestasHastaElMomentoAcertadas);

        viewCancelarSalirAlMenuDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                salirJugarDialog.dismiss();

            }
        });

        viewAceptarSalirAlMenuDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mp_juego.stop();
                salirJugarDialog.dismiss();
                navController.navigate(R.id.action_juegoFragment_to_menuFragment);

            }
        });

        salirJugarDialog.setCancelable(true);
        salirJugarDialog.setCanceledOnTouchOutside(false);
        window.setLayout(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        salirJugarDialog.show();

    }

    public void initMediaPlayerJuego() {

        mp_juego = MediaPlayer.create(getContext(), R.raw.game_music);
        mp_juego.setLooping(true);
        mp_juego.start();

    }

    @Override
    public void onResume() {
        super.onResume();
        mp_juego.start();

    }

    @Override
    public void onPause() {
        super.onPause();
        mp_juego.pause();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mp_juego.stop();
        mp_juego.release();

    }

}