package teambiscochito.toptrumpsgame.view.fragment;

import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import teambiscochito.toptrumpsgame.R;

public class JuegoFragment extends Fragment {

    private MediaPlayer mp_juego;

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

        initMediaPlayerJuego();

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