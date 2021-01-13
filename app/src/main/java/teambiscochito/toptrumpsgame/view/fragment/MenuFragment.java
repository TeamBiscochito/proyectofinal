package teambiscochito.toptrumpsgame.view.fragment;

import android.app.ActionBar;
import android.app.Dialog;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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

public class MenuFragment extends Fragment {

    private boolean estoyEnCreditos = false;

    private MediaPlayer mp_menu, mp_creditos;
    Animation animTablero, animScaleUp, animScaleDown;
    TextView tvAnimales, tvSalvajes, tvCartas, tvTuto, tvCreditos;
    View v, vp, vCartas, vTuto, vCreditos, vNote, vUser, vPlay;
    ImageView ivNote, ivUser;
    Dialog dialog;

    public MenuFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_menu, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init(view);

        vCartas.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    vCartas.startAnimation(animScaleUp);
                    tvCartas.startAnimation(animScaleUp);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    vCartas.startAnimation(animScaleDown);
                    tvCartas.startAnimation(animScaleDown);
                }

                return true;
            }
        });

        vTuto.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    vTuto.startAnimation(animScaleUp);
                    tvTuto.startAnimation(animScaleUp);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    vTuto.startAnimation(animScaleDown);
                    tvTuto.startAnimation(animScaleDown);
                }

                return true;
            }
        });

        vPlay.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    vPlay.startAnimation(animScaleUp);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    vPlay.startAnimation(animScaleDown);
                }
/*              ----------------------------------------------------------------------------------
                final EditText input = new EditText(getContext());
                AlertDialog builder = new AlertDialog.Builder(getContext())
                        .setTitle("Clave de acceso").setMessage("Introduzca una nueva clave de acceso")
                        .setView(input).setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String txt = input.getText().toString();
                                String claveAdmin = sharedPreferences.getString("clave_admin", "");
                                Log.v("xyz Menu", claveAdmin);
                                if(txt.equals(claveAdmin)){
                                    Toast.makeText(getContext(), "aceptado", Toast.LENGTH_LONG).show();
                                }else{
                                    Toast.makeText(getContext(), "Contraseña incorrecta", Toast.LENGTH_LONG).show();
                                }

                            }
                        }).setNegativeButton("cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getContext(), "Cancelado", Toast.LENGTH_LONG).show();
                            }
                        }).show();

                ----------------------------------------------------------------------------------
                */
                return true;
            }
        });

        vCreditos.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    vCreditos.startAnimation(animScaleUp);
                    tvCreditos.startAnimation(animScaleUp);

                    mp_menu.pause();
                    creditosDialog();

                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    vCreditos.startAnimation(animScaleDown);
                    tvCreditos.startAnimation(animScaleDown);
                }

                return true;
            }

        });

        vNote.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    vNote.startAnimation(animScaleUp);
                    ivNote.startAnimation(animScaleUp);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    vNote.startAnimation(animScaleDown);
                    ivNote.startAnimation(animScaleDown);
                }

                return true;
            }
        });

        vUser.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    vUser.startAnimation(animScaleUp);
                    ivUser.startAnimation(animScaleUp);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    vUser.startAnimation(animScaleDown);
                    ivUser.startAnimation(animScaleDown);
                }

                return true;
            }
        });
    }

    private void init(View view) {

        animTablero = AnimationUtils.loadAnimation(getContext(), R.anim.shake);
        animScaleUp = AnimationUtils.loadAnimation(getContext(), R.anim.scale_up);
        animScaleDown = AnimationUtils.loadAnimation(getContext(), R.anim.scale_down);

        initMediaPlayerMenu();

        tvAnimales = view.findViewById(R.id.tvTitulo1);
        tvSalvajes = view.findViewById(R.id.tvTitulo2);
        tvCartas = view.findViewById(R.id.tvMenuCartas);
        tvTuto = view.findViewById(R.id.tvMenuTuto);
        tvCreditos = view.findViewById(R.id.tvMenuCreditos);

        v = view.findViewById(R.id.viewTablero);
        vp = view.findViewById(R.id.viewPlayButton);
        vCartas = view.findViewById(R.id.viewCartas);
        vTuto = view.findViewById(R.id.viewTuto);
        vCreditos = view.findViewById(R.id.viewMenuCreditos);
        vNote = view.findViewById(R.id.viewMenuNote);
        vUser = view.findViewById(R.id.viewMenuUser);
        vPlay = view.findViewById(R.id.viewMenuPlay);

        ivNote = view.findViewById(R.id.ivMenuNote);
        ivUser = view.findViewById(R.id.ivMenuUser);

        vNote.setVisibility(View.INVISIBLE);
        ivNote.setVisibility(View.INVISIBLE);

        AnimationDrawable animDrawable = (AnimationDrawable) vp.getBackground();

        v.setAnimation(animTablero);
        tvAnimales.setAnimation(animTablero);
        tvSalvajes.setAnimation(animTablero);
        animDrawable.setEnterFadeDuration(500);
        animDrawable.setExitFadeDuration(1000);
        animDrawable.start();

    }

    public void creditosDialog() {

        ImageView imgAtras;

        initMediaPlayerCreditos();

        estoyEnCreditos = true;

        dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.creditos_dialog);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER);
        //dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        window.getAttributes().windowAnimations = R.style.DialogAnimation;

        imgAtras = dialog.findViewById(R.id.imgBackCreditos);

        imgAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                estoyEnCreditos = false;
                mp_creditos.stop();
                mp_menu.start();
            }
        });

        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(false);
        window.setLayout(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        dialog.show();

    }

    public void initMediaPlayerMenu() {

        mp_menu = MediaPlayer.create(getContext(), R.raw.menu_music);
        mp_menu.setLooping(true);
        mp_menu.start();

    }

    public void initMediaPlayerCreditos() {

        mp_creditos = MediaPlayer.create(getContext(), R.raw.creditos_music);
        mp_creditos.setLooping(true);
        mp_creditos.start();

    }

    @Override
    public void onResume() {
        super.onResume();

        if(!estoyEnCreditos) {
            mp_menu.start();
        } else {
            mp_creditos.start();
        }

    }

    @Override
    public void onPause() {
        super.onPause();

        if(!estoyEnCreditos) {
            mp_menu.pause();
        } else {
            mp_creditos.pause();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(!estoyEnCreditos) {
            mp_menu.stop();
            mp_menu.release();
        } else {
            mp_creditos.stop();
            mp_creditos.release();
        }
    }
}