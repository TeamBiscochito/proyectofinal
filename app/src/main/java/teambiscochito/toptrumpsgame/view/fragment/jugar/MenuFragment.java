package teambiscochito.toptrumpsgame.view.fragment.jugar;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.text.DecimalFormat;

import de.hdodenhof.circleimageview.CircleImageView;
import teambiscochito.toptrumpsgame.R;
import teambiscochito.toptrumpsgame.model.room.pojo.User;
import teambiscochito.toptrumpsgame.viewmodel.ViewModel;

public class MenuFragment extends Fragment {

    private boolean estoyEnCreditos = false;

    private MediaPlayer mp_menu, mp_creditos;
    Animation animTablero, animScaleUp, animScaleDown;
    TextView tvAnimales, tvSalvajes, tvCartas, tvTuto;
    View v, vp, vCartas, vTuto, vWeb, vSettings, vUser, vPlay, vCerrarSesion, viewVerDialogCreditos;
    ImageView ivSettings, ivUser, ivCerrarSesion, ivWeb;
    Dialog dialogCreditos, dialogPerfil, dialogAjustes, dialogWeb;
    User userActual;
    ViewModel viewModel;
    SharedPreferences sharedPreferences;
    NavController navController;
    private final String linkWeb = "https://teambiscochito.github.io/animales-salvajes-web/";

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

        navController = Navigation.findNavController(view);
        sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        viewModel = new ViewModelProvider(getActivity()).get(ViewModel.class);
        userActual = viewModel.userActual;

        ivUser.setImageDrawable(getResources().getDrawable(userActual.getAvatar()));

        vCartas.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    vCartas.startAnimation(animScaleUp);
                    tvCartas.startAnimation(animScaleUp);

                    mp_menu.stop();
                    navController.navigate(R.id.action_menuFragment_to_cartasFragment);

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

                    mp_menu.stop();

                    Bundle bundle = new Bundle();
                    bundle.putBoolean("vieneDelFirstStart", false);

                    navController.navigate(R.id.action_menuFragment_to_tutorialFragment, bundle);

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

                    mp_menu.stop();

                    viewModel.setUser(userActual);
                    navController.navigate(R.id.action_menuFragment_to_juegoFragment);

                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    vPlay.startAnimation(animScaleDown);

                }

                return true;
            }
        });

        vWeb.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    vWeb.startAnimation(animScaleUp);
                    ivWeb.startAnimation(animScaleUp);

                    webDialog();

                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    vWeb.startAnimation(animScaleDown);
                    ivWeb.startAnimation(animScaleDown);
                }

                return true;
            }

        });

        vSettings.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    vSettings.startAnimation(animScaleUp);
                    ivSettings.startAnimation(animScaleUp);

                    ajustesDialog();

                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    vSettings.startAnimation(animScaleDown);
                    ivSettings.startAnimation(animScaleDown);
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

                    perfilDialog();

                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    vUser.startAnimation(animScaleDown);
                    ivUser.startAnimation(animScaleDown);
                }

                return true;
            }
        });

        vCerrarSesion.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    vCerrarSesion.startAnimation(animScaleUp);
                    ivCerrarSesion.startAnimation(animScaleUp);

                    mp_menu.stop();

                    navController.navigate(R.id.action_menuFragment_to_chooseUserFragment);

                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    vCerrarSesion.startAnimation(animScaleDown);
                    ivCerrarSesion.startAnimation(animScaleDown);
                }

                return true;
            }
        });

        viewVerDialogCreditos.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    viewVerDialogCreditos.startAnimation(animScaleUp);

                    mp_menu.pause();

                    creditosDialog();

                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    viewVerDialogCreditos.startAnimation(animScaleDown);

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

        tvAnimales = view.findViewById(R.id.viewMenu_Animales);
        tvSalvajes = view.findViewById(R.id.viewMenu_Salvajes);
        tvCartas = view.findViewById(R.id.tvMenuCartas);
        tvTuto = view.findViewById(R.id.tvMenuTuto);
        ivWeb = view.findViewById(R.id.imgMenu_Web);
        ivCerrarSesion = view.findViewById(R.id.imgMenu_CerrarSesion);

        v = view.findViewById(R.id.viewMenu_Tablero);
        vp = view.findViewById(R.id.viewMenu_BGGradient);
        vCartas = view.findViewById(R.id.viewMenu_Cartas);
        vTuto = view.findViewById(R.id.viewMenu_Tutorial);
        vWeb = view.findViewById(R.id.viewMenu_Web);
        vSettings = view.findViewById(R.id.viewMenu_Admin);
        vUser = view.findViewById(R.id.viewMenu_Perfil);
        vPlay = view.findViewById(R.id.viewMenu_Play);
        vCerrarSesion = view.findViewById(R.id.viewMenu_CerrarSesion);
        viewVerDialogCreditos = view.findViewById(R.id.viewMenu_Creditos);

        ivSettings = view.findViewById(R.id.imgMenu_Admin);
        ivUser = view.findViewById(R.id.civMenu_Perfil);

        AnimationDrawable animDrawable = (AnimationDrawable) vp.getBackground();

        v.setAnimation(animTablero);
        tvAnimales.setAnimation(animTablero);
        tvSalvajes.setAnimation(animTablero);
        animDrawable.setEnterFadeDuration(500);
        animDrawable.setExitFadeDuration(1000);
        animDrawable.start();

    }

    public void creditosDialog() {

        ImageView imgAtrasCreditos;

        initMediaPlayerCreditos();

        estoyEnCreditos = true;

        dialogCreditos = new Dialog(getContext());
        dialogCreditos.setContentView(R.layout.creditos_dialog);
        dialogCreditos.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        Window window = dialogCreditos.getWindow();
        window.setGravity(Gravity.CENTER);
        window.getAttributes().windowAnimations = R.style.DialogAnimation;

        imgAtrasCreditos = dialogCreditos.findViewById(R.id.imgDialogCreditosBack);

        imgAtrasCreditos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogCreditos.dismiss();
                estoyEnCreditos = false;

                mp_creditos.stop();
                mp_menu.start();

            }
        });

        dialogCreditos.setCancelable(true);
        dialogCreditos.setCanceledOnTouchOutside(false);
        window.setLayout(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        dialogCreditos.show();

    }

    public void webDialog() {

        View viewCerrarWebDialog, viewCopiarEnlaceWeb, viewEntrarEnlaceWeb;
        TextView tvCopiarEnlaceWeb, tvEntrarEnlaceWeb;

        dialogWeb = new Dialog(getContext());
        dialogWeb.setContentView(R.layout.web_dialog);
        dialogWeb.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        Window window = dialogWeb.getWindow();
        window.setGravity(Gravity.CENTER);
        window.getAttributes().windowAnimations = R.style.DialogAnimation;

        viewCerrarWebDialog = dialogWeb.findViewById(R.id.viewWebDialog_Cancel);
        viewCopiarEnlaceWeb = dialogWeb.findViewById(R.id.viewWebDialog_Copiar);
        tvCopiarEnlaceWeb = dialogWeb.findViewById(R.id.tvWebDialog_Copiar);

        viewEntrarEnlaceWeb = dialogWeb.findViewById(R.id.viewWebDialog_Entrar);
        tvEntrarEnlaceWeb = dialogWeb.findViewById(R.id.tvWebDialog_Entrar);

        viewCerrarWebDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialogWeb.dismiss();

            }
        });

        viewCopiarEnlaceWeb.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    viewCopiarEnlaceWeb.startAnimation(animScaleUp);
                    tvCopiarEnlaceWeb.startAnimation(animScaleUp);

                    ClipboardManager clipboardManager = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clipData = ClipData.newPlainText("Enlace", linkWeb);
                    clipboardManager.setPrimaryClip(clipData);

                    Snackbar mSnackbar = Snackbar.make(viewCopiarEnlaceWeb, "Enlace copiado al portapapeles", Snackbar.LENGTH_LONG);

                    View mView = mSnackbar.getView();

                    TextView mTextView = (TextView) mView.findViewById(R.id.snackbar_text);

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {

                        mTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

                    } else {

                        mTextView.setGravity(Gravity.CENTER_HORIZONTAL);

                    }

                    mSnackbar.show();

                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    viewCopiarEnlaceWeb.startAnimation(animScaleDown);
                    tvCopiarEnlaceWeb.startAnimation(animScaleDown);

                }

                return true;
            }
        });

        viewEntrarEnlaceWeb.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    viewEntrarEnlaceWeb.startAnimation(animScaleUp);
                    tvEntrarEnlaceWeb.startAnimation(animScaleUp);

                    Intent intentEntrarWeb = new Intent(Intent.ACTION_VIEW, Uri.parse(linkWeb));
                    startActivity(intentEntrarWeb);

                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    viewEntrarEnlaceWeb.startAnimation(animScaleDown);
                    tvEntrarEnlaceWeb.startAnimation(animScaleDown);

                }

                return true;
            }
        });

        dialogWeb.setCancelable(true);
        dialogWeb.setCanceledOnTouchOutside(false);
        window.setLayout(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        dialogWeb.show();

    }

    public void perfilDialog() {

        ImageView imgAtrasPerfil;

        dialogPerfil = new Dialog(getContext());
        dialogPerfil.setContentView(R.layout.perfil_dialog);
        dialogPerfil.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        Window window = dialogPerfil.getWindow();
        window.setGravity(Gravity.CENTER);
        window.getAttributes().windowAnimations = R.style.DialogAnimation;

        imgAtrasPerfil = dialogPerfil.findViewById(R.id.imgDialogPerfil_Back);

        imgAtrasPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialogPerfil.dismiss();

            }
        });

        dialogPerfil.setCancelable(true);
        dialogPerfil.setCanceledOnTouchOutside(false);
        window.setLayout(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        dialogPerfil.show();

        TextView tvNumeroRespuestasAcertadas = dialogPerfil.findViewById(R.id.tvDialogPerfil_NumeroAcertadas);
        tvNumeroRespuestasAcertadas.setText("" + userActual.getTrueAnswer());

        TextView tvNumeroRespuestas = dialogPerfil.findViewById(R.id.tvDialogPerfil_NumeroContestadas);
        tvNumeroRespuestas.setText("" + userActual.getAnswer());

        TextView tvPorcentajeAciertos = dialogPerfil.findViewById(R.id.tvDialogPerfil_NumeroPorcentaje);

        if(userActual.getAnswer() == 0) {

            tvPorcentajeAciertos.setText("0 %");

        } else {

            DecimalFormat formateador = new DecimalFormat("##.#");

            tvPorcentajeAciertos.setText(formateador.format(((Double.parseDouble(String.valueOf(userActual.getTrueAnswer()))) / (Double.parseDouble(String.valueOf(userActual.getAnswer()))) * 100)) + " %");

        }

        TextView tvNombreVerPerfil =  dialogPerfil.findViewById(R.id.tvDialogPerfil_Nombre);
        tvNombreVerPerfil.setText(userActual.getName());

        CircleImageView imgAvatarPerfil = dialogPerfil.findViewById(R.id.civDialogPerfil_Avatar);
        imgAvatarPerfil.setImageDrawable(getResources().getDrawable(userActual.getAvatar()));

        View viewPerfilEnviarCorreo = dialogPerfil.findViewById(R.id.viewDialogPerfil_Correo);
        ImageView ivPerfilEnviarCorreo = dialogPerfil.findViewById(R.id.imgDialogPerfil_Correo);

        viewPerfilEnviarCorreo.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    viewPerfilEnviarCorreo.startAnimation(animScaleUp);
                    ivPerfilEnviarCorreo.startAnimation(animScaleUp);

                    mp_menu.stop();

                    dialogPerfil.dismiss();
                    navController.navigate(R.id.action_menuFragment_to_correoFragment);

                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    viewPerfilEnviarCorreo.startAnimation(animScaleDown);
                    ivPerfilEnviarCorreo.startAnimation(animScaleDown);
                }

                return true;
            }
        });

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

                        mp_menu.stop();
                        dialogAjustes.dismiss();
                        navController.navigate(R.id.action_menuFragment_to_adminFragment);

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