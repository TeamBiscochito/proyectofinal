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
import android.os.Bundle;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.snackbar.Snackbar;

import java.text.DecimalFormat;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import teambiscochito.toptrumpsgame.R;
import teambiscochito.toptrumpsgame.model.room.pojo.User;
import teambiscochito.toptrumpsgame.viewmodel.ViewModel;

/**
 * <h2 align="center">Team Biscochito</h2><hr>
 * <p>
 * Clase en la que iniciamos el menú de nuestra aplicación, podremos acceder a todas las opciones
 * disponibles de la App desde este fragmento. Tanto sea para la parte de usuario como para la parte
 * de administración.
 */
@SuppressWarnings({"Convert2Lambda"})
// Comente la línea de arriba para ver los posibles Lambdas a convertir
public class MenuFragment extends Fragment {
    private final String linkWeb = "https://teambiscochito.github.io/animales-salvajes-web/";
    private Animation animTablero, animScaleUp, animScaleDown;
    private TextView tvAnimales, tvSalvajes, tvCartas, tvTuto;
    private View v, vp, vCartas, vTuto, vWeb, vSettings, vUser, vPlay, vCerrarSesion, viewVerDialogCreditos;
    private ImageView ivSettings, ivUser, ivCerrarSesion, ivWeb;
    private Dialog dialogCreditos, dialogPerfil, dialogAjustes, dialogWeb;
    private User userActual;
    private ViewModel viewModel;
    private SharedPreferences sharedPreferences;
    private NavController navController;
    private boolean estoyEnCreditos = false;
    private MediaPlayer mp_menu, mp_creditos;

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
        sharedPreferences = requireActivity().getPreferences(Context.MODE_PRIVATE);
        viewModel = new ViewModelProvider(requireActivity()).get(ViewModel.class);
        userActual = ViewModel.userActual;

        ivUser.setImageDrawable(AppCompatResources.getDrawable(requireContext(), userActual.getAvatar()));

        viewCartasButton();

        viewTutorialButton();

        viewPlayButton();

        viewWebButton();

        viewSettingsButton();

        viewPerfilButton();

        viewCerrarSesionButton();

        viewCreditosButton();
    }

    /**
     * <h2 align="center">Team Biscochito</h2><hr>
     * <p>
     * Método que se ejecuta al presionar sobre la vista de cartas, nos redirecciona al fragmento
     * {@link CartasFragment} donde se podrán ver todas las cartas disponibles que tenemos.
     * <br><br>
     * Referencia del método en: {@link JuegoFragment#onViewCreated(View, Bundle)}
     */
    public void viewCartasButton() {
        vCartas.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        vCartas.startAnimation(animScaleUp);
                        tvCartas.startAnimation(animScaleUp);

                        mp_menu.stop();
                        navController.navigate(R.id.action_menuFragment_to_cartasFragment);
                        break;
                    case MotionEvent.ACTION_UP:
                        vCartas.startAnimation(animScaleDown);
                        tvCartas.startAnimation(animScaleDown);
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
     * Método que se ejecuta al presionar sobre la vista de créditos (I).
     * <br><br>
     * Referencia del método en: {@link JuegoFragment#onViewCreated(View, Bundle)}
     */
    public void viewCreditosButton() {
        viewVerDialogCreditos.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        viewVerDialogCreditos.startAnimation(animScaleUp);

                        mp_menu.pause();

                        creditosDialog();
                        break;
                    case MotionEvent.ACTION_UP:
                        viewVerDialogCreditos.startAnimation(animScaleDown);
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
     * Método que se ejecuta al presionar sobre la vista de la puerta, hará que volvamos al fragmento
     * de selección de usuario {@link ChooseUserFragment}
     * <br><br>
     * Referencia del método en: {@link JuegoFragment#onViewCreated(View, Bundle)}
     */
    public void viewCerrarSesionButton() {
        vCerrarSesion.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        vCerrarSesion.startAnimation(animScaleUp);
                        ivCerrarSesion.startAnimation(animScaleUp);

                        mp_menu.stop();

                        navController.navigate(R.id.action_menuFragment_to_chooseUserFragment);
                        break;
                    case MotionEvent.ACTION_UP:
                        vCerrarSesion.startAnimation(animScaleDown);
                        ivCerrarSesion.startAnimation(animScaleDown);
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
     * Método que se ejecuta al presionar sobre la vista de nuestro icono de perfil. Nos muestra un
     * diálogo en el que hacemos referencia en este método {@link #perfilDialog()}
     * <br><br>
     * Referencia del método en: {@link JuegoFragment#onViewCreated(View, Bundle)}
     */
    public void viewPerfilButton() {
        vUser.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        vUser.startAnimation(animScaleUp);
                        ivUser.startAnimation(animScaleUp);

                        perfilDialog();
                        break;
                    case MotionEvent.ACTION_UP:
                        vUser.startAnimation(animScaleDown);
                        ivUser.startAnimation(animScaleDown);
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
     * Método que se ejecuta al presionar sobre la vista de ajustes, al presionar el icono de la
     * tuerca. Se nos abre un nuevo cuadro de diálogo {@link #ajustesDialog()} en el que tendremos
     * que poner la clave administrador para ir al modo administrador
     * {@link teambiscochito.toptrumpsgame.view.fragment.administrar.AdminFragment}
     * <br><br>
     * Referencia del método en: {@link JuegoFragment#onViewCreated(View, Bundle)}
     */
    public void viewSettingsButton() {
        vSettings.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        vSettings.startAnimation(animScaleUp);
                        ivSettings.startAnimation(animScaleUp);

                        ajustesDialog();
                        break;
                    case MotionEvent.ACTION_UP:
                        vSettings.startAnimation(animScaleDown);
                        ivSettings.startAnimation(animScaleDown);
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
     * Método que se ejecuta al presionar sobre la vista de la lupa. Donde se nos abre un pequeño
     * cuadro de diálogo {@link #webDialog()} donde podremos copiar el enlace o abrir en una nueva
     * ventana.
     * <br><br>
     * Referencia del método en: {@link JuegoFragment#onViewCreated(View, Bundle)}
     */
    public void viewWebButton() {
        vWeb.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        vWeb.startAnimation(animScaleUp);
                        ivWeb.startAnimation(animScaleUp);

                        webDialog();
                        break;
                    case MotionEvent.ACTION_UP:
                        vWeb.startAnimation(animScaleDown);
                        ivWeb.startAnimation(animScaleDown);
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
     * Método que se ejecuta al presionar sobre la vista de "Play" el icono. Navegamos directamente
     * a un nuevo fragmento que es donde está el juego {@link JuegoFragment}
     * <br><br>
     * Referencia del método en: {@link JuegoFragment#onViewCreated(View, Bundle)}
     */
    public void viewPlayButton() {
        vPlay.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        vPlay.startAnimation(animScaleUp);

                        mp_menu.stop();

                        viewModel.setUser(userActual);
                        navController.navigate(R.id.action_menuFragment_to_juegoFragment);
                        break;
                    case MotionEvent.ACTION_UP:
                        vPlay.startAnimation(animScaleDown);
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
     * Método que se ejecuta al presionar sobre la vista de "Play" el icono. Navegamos directamente
     * a un nuevo fragmento que es donde está el juego {@link JuegoFragment}
     * <br><br>
     * Referencia del método en: {@link JuegoFragment#onViewCreated(View, Bundle)}
     */
    public void viewTutorialButton() {
        vTuto.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        vTuto.startAnimation(animScaleUp);
                        tvTuto.startAnimation(animScaleUp);

                        mp_menu.stop();

                        Bundle bundle = new Bundle();
                        bundle.putBoolean("vieneDelFirstStart", false);

                        navController.navigate(R.id.action_menuFragment_to_tutorialFragment, bundle);
                        break;
                    case MotionEvent.ACTION_UP:
                        vTuto.startAnimation(animScaleDown);
                        tvTuto.startAnimation(animScaleDown);
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
     * Método que contiene el diálogo de los créditos.
     * <br><br>
     * Referencia del método en: {@link #viewCreditosButton()}
     */
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

        imgAtrasCreditos.setOnClickListener(v -> {
            dialogCreditos.dismiss();
            estoyEnCreditos = false;

            mp_creditos.stop();
            mp_menu.start();
        });

        dialogCreditos.setCancelable(true);
        dialogCreditos.setCanceledOnTouchOutside(false);
        window.setLayout(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        dialogCreditos.show();
    }

    /**
     * <h2 align="center">Team Biscochito</h2><hr>
     * <p>
     * Método que contiene el diálogo de la Web
     * <a href="https://teambiscochito.github.io/animales-salvajes-web/">Enlace a la página Web</a>.
     * <br><br>
     * Referencia del método en: {@link #viewWebButton()}
     */
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

        viewCerrarWebDialog.setOnClickListener(v -> dialogWeb.dismiss());

        viewCopiarEnlaceWeb(viewCopiarEnlaceWeb, tvCopiarEnlaceWeb);
        viewEntrarEnlaceWeb(viewEntrarEnlaceWeb, tvEntrarEnlaceWeb);

        dialogWeb.setCancelable(true);
        dialogWeb.setCanceledOnTouchOutside(false);
        window.setLayout(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        dialogWeb.show();
    }


    /**
     * <h2 align="center">Team Biscochito</h2><hr>
     * <p>
     * Método que crea un nuevo intent para abrir el buscador y redirecionar directamente a nuestra
     * página Web.
     * <br><br>
     * Referencia del método en: {@link #webDialog()}
     *
     * @param viewEntrarEnlaceWeb pasamos la vista de entrar en la web (botón).
     * @param tvEntrarEnlaceWeb   pasamos el propio TextView de entrar en la Web.
     */
    public void viewEntrarEnlaceWeb(View viewEntrarEnlaceWeb, TextView tvEntrarEnlaceWeb) {
        viewEntrarEnlaceWeb.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        viewEntrarEnlaceWeb.startAnimation(animScaleUp);
                        tvEntrarEnlaceWeb.startAnimation(animScaleUp);

                        Intent intentEntrarWeb = new Intent(Intent.ACTION_VIEW, Uri.parse(linkWeb));
                        startActivity(intentEntrarWeb);
                        break;
                    case MotionEvent.ACTION_UP:
                        viewEntrarEnlaceWeb.startAnimation(animScaleDown);
                        tvEntrarEnlaceWeb.startAnimation(animScaleDown);
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
     * Método que copia al portapaeles el enlace de la página Web.
     * <br><br>
     * Referencia del método en: {@link #webDialog()}
     *
     * @param viewCopiarEnlaceWeb pasamos la vista de copiar enlace en la web (botón).
     * @param tvCopiarEnlaceWeb   pasamos el propio TextView de copiar enlace en la Web.
     */
    public void viewCopiarEnlaceWeb(View viewCopiarEnlaceWeb, TextView tvCopiarEnlaceWeb) {
        viewCopiarEnlaceWeb.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        viewCopiarEnlaceWeb.startAnimation(animScaleUp);
                        tvCopiarEnlaceWeb.startAnimation(animScaleUp);

                        ClipboardManager clipboardManager = (ClipboardManager) requireActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                        ClipData clipData = ClipData.newPlainText("Enlace", linkWeb);
                        clipboardManager.setPrimaryClip(clipData);

                        Snackbar mSnackbar = Snackbar.make(viewCopiarEnlaceWeb, "Enlace copiado al portapapeles", Snackbar.LENGTH_LONG);

                        View mView = mSnackbar.getView();

                        TextView mTextView = mView.findViewById(R.id.snackbar_text);

                        mTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

                        mSnackbar.show();
                        break;
                    case MotionEvent.ACTION_UP:
                        viewCopiarEnlaceWeb.startAnimation(animScaleDown);
                        tvCopiarEnlaceWeb.startAnimation(animScaleDown);
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
     * Método que genera el diálogo para ver el perfil, y la puntuación que llevamos en el juego.
     * También contiene la vista para mandar la puntuación al correo.
     * <br><br>
     * Referencia del método en: {@link #viewPerfilButton()}
     */
    public void perfilDialog() {
        ImageView imgAtrasPerfil;

        dialogPerfil = new Dialog(getContext());
        dialogPerfil.setContentView(R.layout.perfil_dialog);
        dialogPerfil.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        Window window = dialogPerfil.getWindow();
        window.setGravity(Gravity.CENTER);
        window.getAttributes().windowAnimations = R.style.DialogAnimation;

        imgAtrasPerfil = dialogPerfil.findViewById(R.id.imgDialogPerfil_Back);

        imgAtrasPerfil.setOnClickListener(v -> dialogPerfil.dismiss());

        dialogPerfil.setCancelable(true);
        dialogPerfil.setCanceledOnTouchOutside(false);
        window.setLayout(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        dialogPerfil.show();

        TextView tvNumeroRespuestasAcertadas = dialogPerfil.findViewById(R.id.tvDialogPerfil_NumeroAcertadas);
        tvNumeroRespuestasAcertadas.setText(String.format(Locale.getDefault(), "%d", userActual.getTrueAnswer()));

        TextView tvNumeroRespuestas = dialogPerfil.findViewById(R.id.tvDialogPerfil_NumeroContestadas);
        tvNumeroRespuestas.setText(String.format(Locale.getDefault(), "%d", userActual.getAnswer()));

        TextView tvPorcentajeAciertos = dialogPerfil.findViewById(R.id.tvDialogPerfil_NumeroPorcentaje);

        if (userActual.getAnswer() == 0) {
            tvPorcentajeAciertos.setText("0 %");
        } else {
            DecimalFormat formateador = new DecimalFormat("##.#");
            tvPorcentajeAciertos.setText(String.format("%s %%",
                    formateador.format(((Double.parseDouble(String.valueOf(userActual.getTrueAnswer())))
                            / (Double.parseDouble(String.valueOf(userActual.getAnswer()))) * 100))));
        }

        TextView tvNombreVerPerfil = dialogPerfil.findViewById(R.id.tvDialogPerfil_Nombre);
        tvNombreVerPerfil.setText(userActual.getName());

        CircleImageView imgAvatarPerfil = dialogPerfil.findViewById(R.id.civDialogPerfil_Avatar);
        imgAvatarPerfil.setImageDrawable(AppCompatResources.getDrawable(requireContext(), userActual.getAvatar()));

        View viewPerfilEnviarCorreo = dialogPerfil.findViewById(R.id.viewDialogPerfil_Correo);
        ImageView ivPerfilEnviarCorreo = dialogPerfil.findViewById(R.id.imgDialogPerfil_Correo);

        viewPerfilCorreo(viewPerfilEnviarCorreo, ivPerfilEnviarCorreo);
    }

    /**
     * <h2 align="center">Team Biscochito</h2><hr>
     * <p>
     * Método que hace que cuando presionemos sobre la vista del icono del correo, nos dirijamos al
     * fragmento {@link CorreoFragment}
     * <br><br>
     * Referencia del método en: {@link #perfilDialog()}
     *
     * @param viewPerfilEnviarCorreo pasamos la vista del correo.
     * @param ivPerfilEnviarCorreo   pasamos el ImageView del correo.
     */
    public void viewPerfilCorreo(View viewPerfilEnviarCorreo, ImageView ivPerfilEnviarCorreo) {
        viewPerfilEnviarCorreo.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        viewPerfilEnviarCorreo.startAnimation(animScaleUp);
                        ivPerfilEnviarCorreo.startAnimation(animScaleUp);

                        mp_menu.stop();

                        dialogPerfil.dismiss();
                        navController.navigate(R.id.action_menuFragment_to_correoFragment);
                        break;
                    case MotionEvent.ACTION_UP:
                        viewPerfilEnviarCorreo.startAnimation(animScaleDown);
                        ivPerfilEnviarCorreo.startAnimation(animScaleDown);
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
     * Método que genera un nuevo diálogo para poder entrar en el modo administrador, este mismo
     * método tiene otra vista para validar que la contraseña introducida sea la correcta que
     * introdujimos en su momento. Si la contraseña es correcta nos redirige al fragmento admin
     * {@link teambiscochito.toptrumpsgame.view.fragment.administrar.AdminFragment}
     * <br><br>
     * Referencia del método en: {@link #viewSettingsButton()}
     */
    public void ajustesDialog() {
        ImageView imgAtrasAjustesDialog;

        dialogAjustes = new Dialog(getContext());
        dialogAjustes.setContentView(R.layout.ajustes_dialog);
        dialogAjustes.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        Window window = dialogAjustes.getWindow();
        window.setGravity(Gravity.CENTER);
        window.getAttributes().windowAnimations = R.style.DialogAnimation;

        imgAtrasAjustesDialog = dialogAjustes.findViewById(R.id.imgDialogAjustesBack);

        imgAtrasAjustesDialog.setOnClickListener(v -> dialogAjustes.dismiss());

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
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        viewBtAccederAjustesDialog.startAnimation(animScaleUp);
                        tvAccederAjustesDialog.startAnimation(animScaleUp);

                        String txt = claveEtAjustes.getText().toString();

                        if (txt.equals(claveAdmin)) {

                            mp_menu.stop();
                            dialogAjustes.dismiss();
                            navController.navigate(R.id.action_menuFragment_to_adminFragment);

                        } else {
                            tvAlertaAjustesDialog.setText(R.string.textAccesoDenegado);
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        viewBtAccederAjustesDialog.startAnimation(animScaleDown);
                        tvAccederAjustesDialog.startAnimation(animScaleDown);
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
        if (!estoyEnCreditos) {
            mp_menu.start();
        } else {
            mp_creditos.start();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (!estoyEnCreditos) {
            mp_menu.pause();
        } else {
            mp_creditos.pause();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (!estoyEnCreditos) {
            mp_menu.stop();
            mp_menu.release();
        } else {
            mp_creditos.stop();
            mp_creditos.release();
        }
    }
}