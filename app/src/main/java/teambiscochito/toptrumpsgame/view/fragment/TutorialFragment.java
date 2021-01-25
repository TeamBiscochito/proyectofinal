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
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import teambiscochito.toptrumpsgame.R;
import teambiscochito.toptrumpsgame.view.adapter.VpAvatarAdapter;

public class TutorialFragment extends Fragment {

    ViewPager2 vp_bocadillos;
    int [] bocadillos = {R.drawable.bocadillo_uno, R.drawable.bocadillo_dos, R.drawable.bocadillo_tres, R.drawable.bocadillo_cuatro, R.drawable.bocadillo_cinco, R.drawable.bocadillo_seis, R.drawable.bocadillo_siete, R.drawable.bocadillo_ocho};
    VpAvatarAdapter adapter;

    Animation animArribaAbajo, animScaleUp, animScaleDown;

    NavController navController;
    ImageView imgCaraLeonTutorial;
    View viewBackTutorial, viewNextBocadillo, viewPreviousBocadillo;
    Dialog tutorialDialog;
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

        initAnim();

        initMediaPlayerTutorial();

        navController = Navigation.findNavController(view);

        vieneDelFirstStart = getArguments().getBoolean("vieneDelFirstStart");

        viewBackTutorial = view.findViewById(R.id.viewBackTutorial);

        if(vieneDelFirstStart) {

            viewBackTutorial.setBackgroundResource(R.drawable.cerrar);

        } else {

            viewBackTutorial.setBackgroundResource(R.drawable.back_2);

        }

        vp_bocadillos = view.findViewById(R.id.vp_bocadillos);
        viewNextBocadillo = view.findViewById(R.id.viewNextTutorial);
        viewPreviousBocadillo = view.findViewById(R.id.viewPreviousTutorial);

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
                }
                else if (position <= 0) {
                    page.setAlpha(1);
                    page.setRotation(36000*(Math.abs(position)*Math.abs(position)*Math.abs(position)*Math.abs(position)*Math.abs(position)*Math.abs(position)*Math.abs(position)));

                } else if (position <= 1) {
                    page.setAlpha(1);
                    page.setRotation(-36000 *(Math.abs(position)*Math.abs(position)*Math.abs(position)*Math.abs(position)*Math.abs(position)*Math.abs(position)*Math.abs(position)));

                }
                else {
                    page.setAlpha(0);
                }
            }

        });

        vp_bocadillos.setPageTransformer(transformer);

        viewNextBocadillo.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    viewNextBocadillo.startAnimation(animScaleUp);

                    if(vp_bocadillos.getCurrentItem() + 1 < adapter.getItemCount()) {
                        vp_bocadillos.setCurrentItem(vp_bocadillos.getCurrentItem() + 1);
                    }

                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    viewNextBocadillo.startAnimation(animScaleDown);

                }

                return true;
            }
        });

        viewPreviousBocadillo.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    viewPreviousBocadillo.startAnimation(animScaleUp);

                    if(vp_bocadillos.getCurrentItem() + 1 > 0) {
                        vp_bocadillos.setCurrentItem(vp_bocadillos.getCurrentItem() - 1);
                    }

                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    viewPreviousBocadillo.startAnimation(animScaleDown);

                }

                return true;
            }
        });

        imgCaraLeonTutorial = view.findViewById(R.id.imgCaraLeonTutorial);

        imgCaraLeonTutorial.startAnimation(animArribaAbajo);
        vp_bocadillos.startAnimation(animArribaAbajo);

        viewBackTutorial.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    viewBackTutorial.startAnimation(animScaleUp);

                    if(vieneDelFirstStart) {

                        tutorialDialog();

                    } else {

                        mp_tutorial.stop();
                        navController.navigate(R.id.action_tutorialFragment_to_menuFragment);

                    }

                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    viewBackTutorial.startAnimation(animScaleDown);

                }

                return true;
            }
        });

    }

    public void initAnim() {

        animArribaAbajo = AnimationUtils.loadAnimation(getContext(), R.anim.slide_tutorial);
        animScaleUp = AnimationUtils.loadAnimation(getContext(), R.anim.scale_up);
        animScaleDown = AnimationUtils.loadAnimation(getContext(), R.anim.scale_down);

    }

    public void tutorialDialog() {

        View viewCancelarSalirTutorialDialog, viewAceptarSalirTutorialDialog;

        tutorialDialog = new Dialog(getContext());
        tutorialDialog.setContentView(R.layout.salir_tutorial_dialog);
        tutorialDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        Window window = tutorialDialog.getWindow();
        window.setGravity(Gravity.CENTER);
        window.getAttributes().windowAnimations = R.style.DialogAnimation;

        viewCancelarSalirTutorialDialog = tutorialDialog.findViewById(R.id.viewCancelarSalirTutorialDialog);
        viewAceptarSalirTutorialDialog = tutorialDialog.findViewById(R.id.viewAceptarSalirTutorialDialog);

        viewCancelarSalirTutorialDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tutorialDialog.dismiss();

            }
        });

        viewAceptarSalirTutorialDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mp_tutorial.stop();
                tutorialDialog.dismiss();

                navController.navigate(R.id.action_tutorialFragment_to_chooseUserFragment);

            }
        });

        tutorialDialog.setCancelable(true);
        tutorialDialog.setCanceledOnTouchOutside(false);
        window.setLayout(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        tutorialDialog.show();

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