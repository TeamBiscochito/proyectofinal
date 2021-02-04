package teambiscochito.toptrumpsgame.view.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import teambiscochito.toptrumpsgame.R;
import teambiscochito.toptrumpsgame.model.room.pojo.Card;
import teambiscochito.toptrumpsgame.model.room.pojo.Question;
import teambiscochito.toptrumpsgame.view.adapter.RecyclerImportAdapter;
import teambiscochito.toptrumpsgame.viewmodel.ViewModel;

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

        viewModel = new ViewModelProvider(getActivity()).get(ViewModel.class);

        navController = Navigation.findNavController(view);

        Call<ArrayList<Card>> cartaCall = viewModel.getCardClient().getAllCards();
        cartaCall.enqueue(new Callback<ArrayList<Card>>() {

            @Override
            public void onResponse(Call<ArrayList<Card>> call, Response<ArrayList<Card>> response) {
                cardList = response.body();

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                initRecycler(view);

            }

            @Override
            public void onFailure(Call<ArrayList<Card>> call, Throwable t) {

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
            public void onResponse(Call<ArrayList<Question>> call, Response<ArrayList<Question>> response) {

                try {

                    for (Question q : response.body()) {

                        questionArrayList.add(q);

                    }

                } catch (NullPointerException e) {

                }

            }

            @Override
            public void onFailure(Call<ArrayList<Question>> call, Throwable t) {
                textviewImportVacio.setText("Parece que no tienes conexión a internet, compruebala y vuelve a intentarlo");
            }

        });

        viewBackImport.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    viewBackImport.startAnimation(animScaleUp);

                    navController.navigate(R.id.action_importFragment_to_adminCartasFragment);

                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    viewBackImport.startAnimation(animScaleDown);

                }

                return true;
            }

        });

    }

    private void initRecycler(View view) {

        try {
            List<Card> newCardList = new ArrayList<>();
            for (Card c : cardList) {

                viewModel.getNameFromNameCarta(c.getName());
                int num = viewModel.getRepeatedNameCarta();
                if(num == 0){
                    String url = c.getPicUrl();
                    c.setPicUrl("https://informatica.ieszaidinvergeles.org:9022/Github-Web/img/" + url);
                    newCardList.add(c);
                }
            }

            RecyclerImportAdapter adapter = new RecyclerImportAdapter(newCardList, getContext(), getActivity());
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
            if(adapter.getItemCount() == 0){
                textviewImportVacio.setText("No se ha encontrado ninguna carta o ya posees todas las disponibles");
            }

            viewImport.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {

                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        viewImport.startAnimation(animScaleUp);
                        tvImport.startAnimation(animScaleUp);

                        for (CheckBox checkBox : adapter.getCheckBoxes()) {

                            if (checkBox.isChecked()) {

                                Card card = newCardList.get(adapter.getCheckBoxes().indexOf(checkBox));
                                long cardid = card.getId();

                                // Obtener sus preguntas
                                ArrayList<Question> questionsFromCard = new ArrayList<>();

                                for (Question q : questionArrayList) {

                                    if (q.getCard_id() == cardid) {

                                        questionsFromCard.add(q);

                                    }
                                }

                                // INSERTARLO EN LA BD
                                card.setId(0);

                                viewModel.insertCard(card);

                                try {

                                    Thread.sleep(500);

                                } catch (InterruptedException e) {

                                }

                                long newId = viewModel.getIdByName(card.getName());

                                try {

                                    Thread.sleep(500);

                                } catch (InterruptedException e) {

                                }

                                for (Question q : questionsFromCard) {

                                    q.setId(0);
                                    q.setCard_id(newId);

                                }

                                viewModel.insertAll(questionsFromCard);
                                try{
                                    navController.navigate(R.id.action_importFragment_to_adminCartasFragment);
                                }catch (IllegalArgumentException exception){

                                }
                            }
                        }

                    } else if (event.getAction() == MotionEvent.ACTION_UP) {
                        viewImport.startAnimation(animScaleDown);
                        tvImport.startAnimation(animScaleDown);

                    }

                    return true;
                }

            });

        } catch (NullPointerException e) {

        }

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