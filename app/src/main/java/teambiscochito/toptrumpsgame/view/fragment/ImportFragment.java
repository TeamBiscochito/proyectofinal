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

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

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

    ViewModel viewModel;
    List<Card> cardList;
    Button btimportar, btback;
    NavController navController;
    ArrayList<Question> questionArrayList = new ArrayList<>();

    public ImportFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_import, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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
                Log.v("xyz error", t.getLocalizedMessage());
            }
        });

        btimportar = view.findViewById(R.id.btImport);

        Call<ArrayList<Question>> call = viewModel.getCardClient().getAllQuestions();
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        call.enqueue(new Callback<ArrayList<Question>>() {
            @Override
            public void onResponse(Call<ArrayList<Question>> call, Response<ArrayList<Question>> response) {
                for(Question q: response.body()){
                    questionArrayList.add(q);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Question>> call, Throwable t) {

            }
        });

        btback = view.findViewById(R.id.btback);
        btback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_importFragment_to_adminCartasFragment);
            }
        });


    }

        private void initRecycler(View view) {
            for(Card c: cardList){
                String url= c.getPicUrl();
                c.setPicUrl("http://10.0.2.2/TopTrump/img/"+url);


            }
            //Log.v("zyx", listaCartas.toString());
            RecyclerView recyclerView = view.findViewById(R.id.recyclerImport);
            RecyclerImportAdapter adapter = new RecyclerImportAdapter(cardList, getContext(), getActivity());
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
            btimportar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for(CheckBox checkBox: adapter.getCheckBoxes()){
                        if (checkBox.isChecked()){
                            Log.v("xyz", cardList.get(adapter.getCheckBoxes().indexOf(checkBox)).toString() );
                            Card card = cardList.get(adapter.getCheckBoxes().indexOf(checkBox));
                            long cardid = card.getId();

                            //Obtener sus preguntas

                            ArrayList<Question>  questionsFromCard = new ArrayList<>();
                            for(Question q : questionArrayList) {
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
                                e.printStackTrace();
                            }
                            long newId = viewModel.getIdByName(card.getName());
                            try {
                                Thread.sleep(500);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            for(Question q: questionsFromCard){
                                q.setId(0);
                                q.setCard_id(newId);
                            }
                            viewModel.insertAll(questionsFromCard);



                        }
                    }

                }
            });

        }
}
