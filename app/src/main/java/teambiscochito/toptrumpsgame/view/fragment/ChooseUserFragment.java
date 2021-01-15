package teambiscochito.toptrumpsgame.view.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerViewAccessibilityDelegate;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import teambiscochito.toptrumpsgame.R;
import teambiscochito.toptrumpsgame.model.room.pojo.User;
import teambiscochito.toptrumpsgame.view.adapter.RecyclerUserAdapter;
import teambiscochito.toptrumpsgame.viewmodel.ViewModel;

public class ChooseUserFragment extends Fragment {
    RecyclerView recyclerView;
    ViewModel viewModel;

    TextView tvEligeTuJugador;

    private MediaPlayer mp_seleccionarJugador;

    public ChooseUserFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_choose_user, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initMediaPlayerSeleccionarJugador();

        tvEligeTuJugador = view.findViewById(R.id.tvEligeTuJugador);

        Animation anim = AnimationUtils.loadAnimation(getContext(), R.anim.tv_choose_player);
        tvEligeTuJugador.startAnimation(anim);


        final NavController navController = Navigation.findNavController(view);



        viewModel = new ViewModelProvider(getActivity()).get(ViewModel.class);
        recyclerView = getView().findViewById(R.id.recyclerView);
        //viewModel.insertUser(new User("Gabri", R.drawable.defaultimg));
        LiveData<List<User>> userList= viewModel.getUserList();
        userList.observe(getViewLifecycleOwner(), new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                RecyclerUserAdapter adapter = new RecyclerUserAdapter(users ,view, getActivity());
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            }
        });
    }

    public void initMediaPlayerSeleccionarJugador() {

        mp_seleccionarJugador = MediaPlayer.create(getContext(), R.raw.seleccionarjugador_music);
        mp_seleccionarJugador.setLooping(true);
        mp_seleccionarJugador.start();

    }

    @Override
    public void onPause() {
        super.onPause();
        mp_seleccionarJugador.pause();
    }

    @Override
    public void onResume() {
        super.onResume();
        mp_seleccionarJugador.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mp_seleccionarJugador.stop();
        mp_seleccionarJugador.release();
    }
}