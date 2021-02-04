/*
 * Copyright (c) 2021. Team Biscochito.
 *
 * Licensed under the GNU General Public License v3.0
 *
 * https://www.gnu.org/licenses/gpl-3.0.html
 *
 * Permissions of this strong copyleft license are conditioned on making available complete
 * source code of licensed works and modifications, which include larger works using a licensed
 * work, under the same license. Copyright and license notices must be preserved. Contributors
 * provide an express grant of patent rights.
 */

package teambiscochito.toptrumpsgame.view.fragment.jugar;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

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

import java.util.List;

import teambiscochito.toptrumpsgame.R;
import teambiscochito.toptrumpsgame.model.room.pojo.Card;
import teambiscochito.toptrumpsgame.view.DialogosGenerales;
import teambiscochito.toptrumpsgame.view.adapter.RecyclerCartasNoAdminAdapter;
import teambiscochito.toptrumpsgame.viewmodel.ViewModel;

/**
 * <h2 align="center">Team Biscochito</h2><hr>
 * <p>
 * Clase para ver las cartas una vez que hayamos iniciado sesión. No se podrán modificar, solo ver
 * la descripción de dicha carta.
 */
@SuppressWarnings({"Convert2Lambda"})
// Comente la línea de arriba para ver los posibles Lambdas a convertir
public class CartasFragment extends Fragment {
    private RecyclerView recyclerView;

    private Animation animScaleUp, animScaleDown;
    private NavController navController;
    private View viewBackCartasNoAdmin;
    private MediaPlayer mp_cards;

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

        viewBackCartasNoAdmin = view.findViewById(R.id.viewCartas_Back);

        backViewBoton();

        ViewModel viewModel = new ViewModelProvider(requireActivity()).get(ViewModel.class);
        recyclerView = requireView().findViewById(R.id.rvCartasNoAdmin);

        LiveData<List<Card>> cardList = viewModel.getCardList();
        cardList.observe(getViewLifecycleOwner(), new Observer<List<Card>>() {
            @Override
            public void onChanged(List<Card> cards) {
                LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);

                RecyclerCartasNoAdminAdapter adapter = new RecyclerCartasNoAdminAdapter(cards, getActivity(), getContext());
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(layoutManager);
            }
        });
    }

    /**
     * <h2 align="center">Team Biscochito</h2><hr>
     * <p>
     * Método para ir hacia atrás, con este método especial y no el general de
     * {@link DialogosGenerales#volverAtrasDialog(int, Context, View, View)}
     * Paremos la música a la hora de cambiar de fragmento (ir al menú).
     * <br><br>
     * Referencia del método en: {@link CartasFragment#onViewCreated(View, Bundle)}
     **/
    private void backViewBoton() {
        viewBackCartasNoAdmin.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        viewBackCartasNoAdmin.startAnimation(animScaleUp);

                        mp_cards.stop();

                        navController.navigate(R.id.action_cartasFragment_to_menuFragment);
                        break;
                    case MotionEvent.ACTION_UP:
                        viewBackCartasNoAdmin.startAnimation(animScaleDown);
                        v.performClick();
                        break;
                    default:
                        break;
                }
                return true;
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