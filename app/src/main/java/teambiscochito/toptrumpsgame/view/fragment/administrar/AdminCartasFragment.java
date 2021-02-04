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

package teambiscochito.toptrumpsgame.view.fragment.administrar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

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
import teambiscochito.toptrumpsgame.view.adapter.RecyclerCartasAdminAdapter;
import teambiscochito.toptrumpsgame.viewmodel.ViewModel;

/**
 * <h2 align="center">Team Biscochito</h2><hr>
 * <p>
 * Clase para ver y poder administrar las cartas. Hay un recycler para ver todas las cartas que
 * poseemos tanto las que tengamos ya predefinidas como las nuevas importadas. Desde este fragmento
 * también podemos ver las cartas que vienen desde la página Web.
 */
@SuppressWarnings({"Convert2Lambda"})
// Comente la línea de arriba para ver los posibles Lambdas a convertir
public class AdminCartasFragment extends Fragment {
    private RecyclerView recyclerView;
    private View viewBackAdminCartas;
    private View viewCerrarAdminCartas;
    private View viewAddCarta;
    private View viewDownloadCartasWeb;
    private ImageView imgAddCarta;
    private TextView tvAddCarta;
    private Animation animScaleUp, animScaleDown;

    private NavController navController;

    public AdminCartasFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_admin_cartas, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);

        navController = Navigation.findNavController(view);

        ViewModel viewModel = new ViewModelProvider(requireActivity()).get(ViewModel.class);
        recyclerView = requireView().findViewById(R.id.rvAdminCartas);

        LiveData<List<Card>> cardList = viewModel.getCardList();
        cardList.observe(getViewLifecycleOwner(), new Observer<List<Card>>() {
            @Override
            public void onChanged(List<Card> cards) {
                LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);

                RecyclerCartasAdminAdapter adapter = new RecyclerCartasAdminAdapter(cards, view, getActivity(), getContext());
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(layoutManager);
            }
        });

        viewMoveOver(viewBackAdminCartas, R.id.action_adminCartasFragment_to_adminFragment);

        viewMoveOver(viewDownloadCartasWeb, R.id.action_adminCartasFragment_to_importFragment);

        viewMoveOver(viewAddCarta, R.id.action_adminCartasFragment_to_addCardFragment);

        DialogosGenerales.salirDialogAdmin(R.id.action_adminCartasFragment_to_chooseUserFragment,
                getContext(),
                view,
                viewCerrarAdminCartas);
    }

    /**
     * <h2 align="center">Team Biscochito</h2><hr>
     * <p>
     * Método para recoger las transiciones de las vistas (botones) para navegar a los diferentes
     * fragmentos disponibles.
     * <br><br>
     * Referencia del método en: {@link AdminCartasFragment#onViewCreated(View, Bundle)}
     *
     * @param viewMoveOver    vista que le pasamos a nuestro método dependiendo del "botón" que pinchemos.
     * @param fragmentDestino ponemos la ID del fragmento del destino al pinchar sobre la vista.
     */
    private void viewMoveOver(View viewMoveOver, int fragmentDestino) {
        viewMoveOver.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        viewMoveOver.startAnimation(animScaleUp);

                        if (fragmentDestino == R.id.action_adminCartasFragment_to_addCardFragment) {
                            imgAddCarta.startAnimation(animScaleUp);
                            tvAddCarta.startAnimation(animScaleUp);
                        }

                        navController.navigate(fragmentDestino);
                        break;
                    case MotionEvent.ACTION_UP:
                        viewMoveOver.startAnimation(animScaleDown);
                        if (fragmentDestino == R.id.action_adminCartasFragment_to_addCardFragment) {
                            imgAddCarta.startAnimation(animScaleDown);
                            tvAddCarta.startAnimation(animScaleDown);
                        }
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
        animScaleUp = AnimationUtils.loadAnimation(getContext(), R.anim.scale_up);
        animScaleDown = AnimationUtils.loadAnimation(getContext(), R.anim.scale_down);

        viewBackAdminCartas = view.findViewById(R.id.viewAdminCartas_Back);
        viewCerrarAdminCartas = view.findViewById(R.id.viewAdminCartas_Close);
        viewAddCarta = view.findViewById(R.id.viewAdminCartas_AddCarta);
        viewDownloadCartasWeb = view.findViewById(R.id.viewAdminCartas_CartasWeb);
        imgAddCarta = view.findViewById(R.id.imgAdminCartas_AddCarta);
        tvAddCarta = view.findViewById(R.id.tvAdminCartas_AddCarta);
    }
}