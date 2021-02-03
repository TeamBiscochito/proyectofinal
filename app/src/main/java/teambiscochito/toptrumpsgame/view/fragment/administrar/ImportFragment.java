package teambiscochito.toptrumpsgame.view.fragment.administrar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import teambiscochito.toptrumpsgame.R;
import teambiscochito.toptrumpsgame.model.room.pojo.Card;
import teambiscochito.toptrumpsgame.model.room.pojo.Question;
import teambiscochito.toptrumpsgame.view.adapter.RecyclerImportAdapter;
import teambiscochito.toptrumpsgame.view.fragment.DialogosGenerales;
import teambiscochito.toptrumpsgame.viewmodel.ViewModel;

/**
 * <h2 align="center">Team Biscochito</h2><hr>
 * <p>
 * Clase para importar nuestras nuevas cartas mediante el servidor alojado en Cloud9:
 * <a href="https://informatica.ieszaidinvergeles.org:9022/LaravelFinal/">Link de Cloud9</a>
 */
public class ImportFragment extends Fragment {

    RecyclerView recyclerView;
    ViewModel viewModel;
    List<Card> cardList;
    NavController navController;
    ArrayList<Question> questionArrayList = new ArrayList<>();
    View viewBackImport, viewImport;
    Animation animScaleUp, animScaleDown;
    TextView tvImport, textviewImportVacio;

    public ImportFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_import, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init(view);

        viewModel = new ViewModelProvider(requireActivity()).get(ViewModel.class);

        navController = Navigation.findNavController(view);

        Call<ArrayList<Card>> cartaCall = viewModel.getCardClient().getAllCards();
        cartaCall.enqueue(new Callback<ArrayList<Card>>() {

            @Override
            public void onResponse(@NonNull Call<ArrayList<Card>> call, @NonNull Response<ArrayList<Card>> response) {
                cardList = response.body();

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                initRecycler();
            }

            @Override
            public void onFailure(@NonNull Call<ArrayList<Card>> call, @NonNull Throwable t) {
            }
        });

        Call<ArrayList<Question>> call = viewModel.getCardClient().getAllQuestions();

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        call.enqueue(new Callback<ArrayList<Question>>() {

            @Override
            public void onResponse(@NonNull Call<ArrayList<Question>> call, @NonNull Response<ArrayList<Question>> response) {

                try {
//                    for (Question q : Objects.requireNonNull(response.body())) {
//                        questionArrayList.add(q);
//                    }
                    questionArrayList.addAll(Objects.requireNonNull(response.body()));
                } catch (NullPointerException ignored) {
                }
            }

            @Override
            public void onFailure(@NonNull Call<ArrayList<Question>> call, @NonNull Throwable t) {
                textviewImportVacio.setText(R.string.InternetDesconectado);
            }
        });

        DialogosGenerales.volverAtrasDialog(R.id.action_importFragment_to_adminCartasFragment,
                getContext(), view, viewBackImport);
    }

    /**
     * <h2 align="center">Team Biscochito</h2><hr>
     * <p>
     * Método para iniciar el Recycler y ver todos los elementos disponible mediante la URL de nuestro
     * servidor de Cloud9.
     * <br><br>
     * Referencia del método en: {@link ImportFragment#onViewCreated(View, Bundle)}
     **/
    private void initRecycler() {
        try {
            List<Card> newCardList = new ArrayList<>();
            for (Card c : cardList) {

                viewModel.getNameFromNameCarta(c.getName());
                int num = viewModel.getRepeatedNameCarta();
                if (num == 0) {
                    String url = c.getPicUrl();
                    c.setPicUrl("https://informatica.ieszaidinvergeles.org:9022/Github-Web/img/" + url);
                    newCardList.add(c);
                }
            }

            RecyclerImportAdapter adapter = new RecyclerImportAdapter(newCardList, getContext(), getActivity());
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));
            if (adapter.getItemCount() == 0) {
                textviewImportVacio.setText(R.string.TienesTodasLasCartas);
            }

            importarCartas(newCardList, adapter);
        } catch (NullPointerException ignored) {
        }
    }

    /**
     * <h2 align="center">Team Biscochito</h2><hr>
     * <p>
     * Método para importar las nuevas cartas que hayamos conseguido del recycler.
     * <br><br>
     * Referencia del método en: {@link ImportFragment#initRecycler()}
     *
     * @param newCardList nueva lista de cartas escogidas de nuestro recycler.
     * @param adapter     pasamos el adaptador del recycler.
     */
    private void importarCartas(List<Card> newCardList, RecyclerImportAdapter adapter) {
        viewImport.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        viewImport.startAnimation(animScaleUp);
                        tvImport.startAnimation(animScaleUp);

                        for (CheckBox checkBox : adapter.getCheckBoxes()) {
                            if (checkBox.isChecked()) {
                                Card card = newCardList.get(adapter.getCheckBoxes().indexOf(checkBox));
                                long cardid = card.getId();

                                /* Obtener sus preguntas */
                                ArrayList<Question> questionsFromCard = new ArrayList<>();

                                for (Question q : questionArrayList) {
                                    if (q.getCard_id() == cardid) {
                                        questionsFromCard.add(q);
                                    }
                                }

                                /* INSERTARLO EN LA BD */
                                card.setId(0);
                                viewModel.insertCard(card);

                                try {
                                    Thread.sleep(500);
                                } catch (InterruptedException ignored) {
                                }

                                long newId = viewModel.getIdByName(card.getName());

                                try {
                                    Thread.sleep(500);
                                } catch (InterruptedException ignored) {
                                }
                                for (Question q : questionsFromCard) {
                                    q.setId(0);
                                    q.setCard_id(newId);
                                }
                                viewModel.insertAll(questionsFromCard);
                                try {
                                    navController.navigate(R.id.action_importFragment_to_adminCartasFragment);
                                } catch (IllegalArgumentException ignored) {
                                }
                            }
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        viewImport.startAnimation(animScaleDown);
                        tvImport.startAnimation(animScaleDown);
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

        viewBackImport = view.findViewById(R.id.viewImport_Back);
        viewImport = view.findViewById(R.id.viewImport_Importar);
        recyclerView = view.findViewById(R.id.rvImport_Importar);
        tvImport = view.findViewById(R.id.tvImport_Importar);
        textviewImportVacio = view.findViewById(R.id.viewImport_ErrorInfo);
    }
}