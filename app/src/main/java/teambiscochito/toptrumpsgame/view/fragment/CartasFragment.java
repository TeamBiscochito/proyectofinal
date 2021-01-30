package teambiscochito.toptrumpsgame.view.fragment;

import android.media.MediaPlayer;
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

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import java.util.List;

import teambiscochito.toptrumpsgame.R;
import teambiscochito.toptrumpsgame.model.room.pojo.Card;
import teambiscochito.toptrumpsgame.view.adapter.RecyclerCartasNoAdminAdapter;
import teambiscochito.toptrumpsgame.viewmodel.ViewModel;

public class CartasFragment extends Fragment {

    RecyclerView recyclerView;
    ViewModel viewModel;

    Animation animScaleUp, animScaleDown;
    NavController navController;
    View viewBackCartasNoAdmin;
    private MediaPlayer mp_cards;
    TextView tvRvVacioCartasNoAdmin;

    public CartasFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cartas, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initMediaPlayerCards();

        navController = Navigation.findNavController(view);

        initAnim();

        viewBackCartasNoAdmin = view.findViewById(R.id.viewBackCartasNoAdmin);

        tvRvVacioCartasNoAdmin = view.findViewById(R.id.tvRvVacioCartasNoAdmin);

        viewBackCartasNoAdmin.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    viewBackCartasNoAdmin.startAnimation(animScaleUp);

                    mp_cards.stop();

                    navController.navigate(R.id.action_cartasFragment_to_menuFragment);

                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    viewBackCartasNoAdmin.startAnimation(animScaleDown);

                }

                return true;
            }
        });

        viewModel = new ViewModelProvider(getActivity()).get(ViewModel.class);
        recyclerView = getView().findViewById(R.id.rvCartasNoAdmin);



        LiveData<List<Card>> cardList = viewModel.getCardList();
        cardList.observe(getViewLifecycleOwner(), new Observer<List<Card>>() {
            @Override
            public void onChanged(List<Card> cards) {

                LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);

                RecyclerCartasNoAdminAdapter adapter = new RecyclerCartasNoAdminAdapter(cards, view, getActivity(), getContext());
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(layoutManager);

            }
        });

    }

    public void initAnim() {

        animScaleUp = AnimationUtils.loadAnimation(getContext(), R.anim.scale_up);
        animScaleDown = AnimationUtils.loadAnimation(getContext(), R.anim.scale_down);

    }

    public void initMediaPlayerCards() {

        mp_cards = MediaPlayer.create(getContext(), R.raw.card_music);
        mp_cards.setLooping(true);
        mp_cards.start();

    }

    @Override
    public void onPause() {
        super.onPause();
        mp_cards.pause();
    }

    @Override
    public void onResume() {
        super.onResume();
        mp_cards.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mp_cards.stop();
        mp_cards.release();
    }
}