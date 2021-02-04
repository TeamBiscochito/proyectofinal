package teambiscochito.toptrumpsgame.view.fragment.jugar;

import android.app.ActionBar;
import android.app.Dialog;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import teambiscochito.toptrumpsgame.R;
import teambiscochito.toptrumpsgame.view.adapter.VpAvatarAdapter;

/**
 * <h2 align="center">Team Biscochito</h2><hr>
 * <p>
 * Clase en la que iniciamos el menú de nuestra aplicación, podremos acceder a todas las opciones
 * disponibles de la App desde este fragmento. Tanto sea como para la parte de usuario como la
 * parte de administración.
 */
@SuppressWarnings({"Convert2Lambda"})
// Comente la línea de arriba para ver los posibles Lambdas a convertir
public class TutorialFragment extends Fragment {
    private final int[] bocadillos = {R.drawable.bocadillo_uno, R.drawable.bocadillo_dos, R.drawable.bocadillo_tres,
            R.drawable.bocadillo_cuatro, R.drawable.bocadillo_cinco, R.drawable.bocadillo_seis, R.drawable.bocadillo_siete, R.drawable.bocadillo_ocho};
    private ViewPager2 vp_bocadillos;
    private VpAvatarAdapter adapter;

    private Animation animArribaAbajo, animScaleUp, animScaleDown;

    private NavController navController;
    private ImageView imgCaraLeonTutorial;
    private View viewBackTutorial, viewNextBocadillo, viewPreviousBocadillo;
    private Dialog tutorialDialog;
    private MediaPlayer mp_tutorial;
    private boolean vieneDelFirstStart;

    public TutorialFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tutorial, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initAllAnim(view);
        initMediaPlayerTutorial();

        navController = Navigation.findNavController(view);
        vieneDelFirstStart = requireArguments().getBoolean("vieneDelFirstStart");

        if (vieneDelFirstStart) {
            viewBackTutorial.setBackgroundResource(R.drawable.cerrar);
        } else {
            viewBackTutorial.setBackgroundResource(R.drawable.back_2);
        }

        CompositePageTransformer transformer = getCompositePageTransformer();

        vp_bocadillos.setPageTransformer(transformer);

        viewNextBocadillo();

        viewPreviousBocadillo();

        viewBackTutorial();
    }

    /**
     * <h2 align="center">Team Biscochito</h2><hr>
     * <p>
     * Método que se ejecuta al presionar sobre la vista de volver hacia atrás, iremos al fragmento
     * {@link MenuFragment} que es el menú principal de la aplicación. En caso de que vengamos del
     * FirstStart, nos mandará a un diálogo nuevo {@link #tutorialDialog()}, donde volvemos al menú
     * de selección de jugador {@link ChooseUserFragment}
     * <br><br>
     * Referencia del método en: {@link TutorialFragment#onViewCreated(View, Bundle)}
     */
    public void viewBackTutorial() {
        viewBackTutorial.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        viewBackTutorial.startAnimation(animScaleUp);
                        if (vieneDelFirstStart) {
                            tutorialDialog();
                        } else {
                            mp_tutorial.stop();
                            navController.navigate(R.id.action_tutorialFragment_to_menuFragment);
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        viewBackTutorial.startAnimation(animScaleDown);
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
     * Método que se ejecuta al presionar sobre la vista de volver hacia atrás pero de los los botones
     * de los bocadillos, este es el "Previous". Volveremos una posición atrás de nuestro ViewPager2.
     * <br><br>
     * Referencia del método en: {@link TutorialFragment#onViewCreated(View, Bundle)}
     */
    public void viewPreviousBocadillo() {
        viewPreviousBocadillo.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        viewPreviousBocadillo.startAnimation(animScaleUp);

                        if (vp_bocadillos.getCurrentItem() + 1 > 0) {
                            vp_bocadillos.setCurrentItem(vp_bocadillos.getCurrentItem() - 1);
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        viewPreviousBocadillo.startAnimation(animScaleDown);
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
     * Método que se ejecuta al presionar sobre la vista de volver hacia adelante pero de los los
     * botones de los bocadillos, este es el "Next". Iremos una posición hacia delante de nuestro
     * ViewPager2.
     * <br><br>
     * Referencia del método en: {@link TutorialFragment#onViewCreated(View, Bundle)}
     */
    public void viewNextBocadillo() {
        viewNextBocadillo.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        viewNextBocadillo.startAnimation(animScaleUp);

                        if (vp_bocadillos.getCurrentItem() + 1 < adapter.getItemCount()) {
                            vp_bocadillos.setCurrentItem(vp_bocadillos.getCurrentItem() + 1);
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        viewNextBocadillo.startAnimation(animScaleDown);
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
     * Método que genera nuevo ViewPager2para los bocadillos a la manera que nosotros queremos,
     * asignándole nuestro adaptador.
     * <br><br>
     * Referencia del método en: {@link TutorialFragment#onViewCreated(View, Bundle)}
     *
     * @return el nuevo ViewPager2 adaptado a lo que necesitamos para el tutorial.
     */
    public CompositePageTransformer getCompositePageTransformer() {
        vp_bocadillos.setUserInputEnabled(false);

        adapter = new VpAvatarAdapter(bocadillos);

        vp_bocadillos.setClipToPadding(false);
        vp_bocadillos.setClipChildren(false);
        vp_bocadillos.setOffscreenPageLimit(3);
        vp_bocadillos.getChildAt(0).setOverScrollMode(View.OVER_SCROLL_NEVER);
        vp_bocadillos.setAdapter(adapter);

        CompositePageTransformer transformer = new CompositePageTransformer();

        transformer.addTransformer(new MarginPageTransformer(8));

        transformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                page.setTranslationX(-position * page.getWidth());

                if (Math.abs(position) < 0.5) {
                    page.setVisibility(View.VISIBLE);
                    page.setScaleX(1 - Math.abs(position));
                    page.setScaleY(1 - Math.abs(position));
                } else if (Math.abs(position) > 0.5) {
                    page.setVisibility(View.GONE);
                }

                if (position < -1) {
                    page.setAlpha(0);
                } else {
                    float function = Math.abs(position) * Math.abs(position) * Math.abs(position) *
                            Math.abs(position) * Math.abs(position) * Math.abs(position) * Math.abs(position);
                    if (position <= 0) {
                        page.setAlpha(1);
                        page.setRotation(36000 * function);
                    } else if (position <= 1) {
                        page.setAlpha(1);
                        page.setRotation(-36000 * function);
                    } else {
                        page.setAlpha(0);
                    }
                }
            }
        });
        return transformer;
    }

    /**
     * <h2 align="center">Team Biscochito</h2><hr>
     * <p>
     * Método que genera el cuadro de diálogo al salir del modo tutorial. Este diálogo oslo se
     * produce si es la primera vez que entramos e instalamos la aplicación ya que cuando salgamos
     * nos redirige a la selección de usuario {@link ChooseUserFragment}
     * <br><br>
     * Referencia del método en: {@link TutorialFragment#viewBackTutorial()}
     */
    public void tutorialDialog() {
        View viewCancelarSalirTutorialDialog, viewAceptarSalirTutorialDialog;

        tutorialDialog = new Dialog(getContext());
        tutorialDialog.setContentView(R.layout.salir_tutorial_dialog);
        tutorialDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        Window window = tutorialDialog.getWindow();
        window.setGravity(Gravity.CENTER);
        window.getAttributes().windowAnimations = R.style.DialogAnimation;

        viewCancelarSalirTutorialDialog = tutorialDialog.findViewById(R.id.viewSalirDialogTutorial_Cancel);
        viewAceptarSalirTutorialDialog = tutorialDialog.findViewById(R.id.viewSalirDialogTutorial_Accept);

        viewCancelarSalirTutorialDialog.setOnClickListener(v -> tutorialDialog.dismiss());

        viewAceptarSalirTutorialDialog.setOnClickListener(v -> {
            mp_tutorial.stop();

            tutorialDialog.dismiss();

            navController.navigate(R.id.action_tutorialFragment_to_chooseUserFragment);
        });
        tutorialDialog.setCancelable(true);
        tutorialDialog.setCanceledOnTouchOutside(false);
        window.setLayout(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        tutorialDialog.show();
    }

    public void initAllAnim(View view) {
        viewBackTutorial = view.findViewById(R.id.viewTutorial_Back);
        vp_bocadillos = view.findViewById(R.id.vpTutorial_Bocadillo);
        viewNextBocadillo = view.findViewById(R.id.viewTutorial_Next);
        viewPreviousBocadillo = view.findViewById(R.id.viewTutorial_Previous);
        imgCaraLeonTutorial = view.findViewById(R.id.imgTutorial_LeonCara);

        animArribaAbajo = AnimationUtils.loadAnimation(getContext(), R.anim.slide_tutorial);
        animScaleUp = AnimationUtils.loadAnimation(getContext(), R.anim.scale_up);
        animScaleDown = AnimationUtils.loadAnimation(getContext(), R.anim.scale_down);

        imgCaraLeonTutorial.startAnimation(animArribaAbajo);
        vp_bocadillos.startAnimation(animArribaAbajo);
    }

    public void initMediaPlayerTutorial() {
        mp_tutorial = MediaPlayer.create(getContext(), R.raw.tutorial_music);
        mp_tutorial.setLooping(true);
        mp_tutorial.start();
    }

    @Override
    public void onResume() {
        super.onResume();
        mp_tutorial.start();
    }

    @Override
    public void onPause() {
        super.onPause();
        mp_tutorial.pause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mp_tutorial.stop();
        mp_tutorial.release();
    }
}