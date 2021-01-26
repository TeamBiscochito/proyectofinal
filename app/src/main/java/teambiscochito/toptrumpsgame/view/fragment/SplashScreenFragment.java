package teambiscochito.toptrumpsgame.view.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import teambiscochito.toptrumpsgame.R;

public class SplashScreenFragment extends Fragment {

    ImageView imgLogoTeam;
    Animation animLogo;

    SharedPreferences sharedPreferences;
    NavController navController;

    private MediaPlayer mp_sound;

    public SplashScreenFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_splash_screen, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);
        mp_sound = MediaPlayer.create(getContext(), R.raw.splash_screen_sound);

        animLogo = AnimationUtils.loadAnimation(getContext(), R.anim.fade_logo_team);
        imgLogoTeam = view.findViewById(R.id.imgLogoTeam);

        sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);

        String random = cadenaAleatoria();
        String claveAdmin = sharedPreferences.getString("clave_admin", random);

        imgLogoTeam.startAnimation(animLogo);

        mp_sound.start();

        Thread thread = new Thread() {

            @Override
            public void run() {

                try {

                    sleep(3500);

                    if (claveAdmin != random){

                        Log.v("XYZ", claveAdmin);
                        navController.navigate(R.id.action_fragmentSplashScreen_to_chooseUserFragment);

                    } else {

                        navController.navigate(R.id.action_fragmentSplashScreen_to_crearAdminFragment);

                    }

                } catch (InterruptedException e) {

                }
            }
        };
        thread.start();
    }

    public String cadenaAleatoria(){

        String lista = "qwertyuioplkjhgfdsazxcvbnmQWERTYUIOPLKJHGFDSAZXCVBNM1234567890";
        String s = "";
        for(int i = 0; i < 20; i++){
            s += lista.charAt((int) (Math.random() * 61) + 1);
        }
        return s;

    }

    @Override
    public void onResume() {
        super.onResume();
        mp_sound.start();

    }

    @Override
    public void onPause() {
        super.onPause();
        mp_sound.pause();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mp_sound.stop();
        mp_sound.release();

    }
}