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
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.TextView;

import teambiscochito.toptrumpsgame.R;

public class CrearAdminFragment extends Fragment {

    SharedPreferences sharedPreferences;
    EditText etClave;
    Animation animScaleUp, animScaleDown;
    TextView tvAdminEntrar;
    View vAdminEntrar;
    private MediaPlayer mp_intro;

    TextView tvAlertaCrearAdmin;

    public CrearAdminFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_crear_admin, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initAnim();

        tvAdminEntrar = view.findViewById(R.id.tvAdminEntrar);
        vAdminEntrar = view.findViewById(R.id.viewBtAdminEntrar);
        tvAlertaCrearAdmin = view.findViewById(R.id.tvAlertaCrearAdmin);

        final NavController navController = Navigation.findNavController(view);

        etClave = view.findViewById(R.id.claveEt);
        /*sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        String random = cadenaAleatoria();
        String claveAdmin = sharedPreferences.getString("clave_admin", random);
        if (claveAdmin != random){
            Log.v("XYZ", claveAdmin);

            mp_intro.stop();
            navController.navigate(R.id.action_crearAdminFragment_to_chooseUserFragment);
        }*/

        vAdminEntrar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    vAdminEntrar.startAnimation(animScaleUp);
                    tvAdminEntrar.startAnimation(animScaleUp);

                    try {
                        String clave = etClave.getText().toString();
                        if(clave.isEmpty()){
                            tvAlertaCrearAdmin.setText(R.string.textIntroduceUnaClave);
                            return false;
                        }
                        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putString("clave_admin", clave);
                        editor.commit();
                    }catch (Exception e){
                        Log.v("xyz", e.getMessage() );
                    }

                    mp_intro.stop();
                    navController.navigate(R.id.action_crearAdminFragment_to_chooseUserFragment);


                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    vAdminEntrar.startAnimation(animScaleDown);
                    tvAdminEntrar.startAnimation(animScaleDown);
                }

                return true;
            }
        });

    }

    private void initAnim() {

        animScaleUp = AnimationUtils.loadAnimation(getContext(), R.anim.scale_up);
        animScaleDown = AnimationUtils.loadAnimation(getContext(), R.anim.scale_down);

        initMediaPlayerIntro();

    }

    public void initMediaPlayerIntro() {

        mp_intro = MediaPlayer.create(getContext(), R.raw.intro_music);
        mp_intro.setLooping(true);
        mp_intro.start();

    }

    /*public String cadenaAleatoria(){
        String lista = "qwertyuioplkjhgfdsazxcvbnmQWERTYUIOPLKJHGFDSAZXCVBNM1234567890";
        String s="";
        for(int i = 0; i < 20; i++){
            s+= lista.charAt((int) (Math.random() *61)+1);
        }
        return s;
    }*/

    @Override
    public void onResume() {
        super.onResume();
        mp_intro.start();

    }

    @Override
    public void onPause() {
        super.onPause();
        mp_intro.pause();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mp_intro.stop();
        mp_intro.release();

    }
}