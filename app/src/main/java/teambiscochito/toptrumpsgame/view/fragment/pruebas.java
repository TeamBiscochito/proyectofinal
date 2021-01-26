package teambiscochito.toptrumpsgame.view.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import teambiscochito.toptrumpsgame.R;
import teambiscochito.toptrumpsgame.model.room.pojo.Card;
import teambiscochito.toptrumpsgame.model.room.pojo.Question;
import teambiscochito.toptrumpsgame.viewmodel.ViewModel;

public class pruebas extends Fragment {


    ViewModel viewModel;

    public pruebas() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pruebas, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(getActivity()).get(ViewModel.class);

        TextView nombre = view.findViewById(R.id.nombreCarta);
        TextView desc = view.findViewById(R.id.questions);
        Button btPruebas = view.findViewById(R.id.btPruebas);

        List<Card> cartas = new ArrayList<>();

        viewModel.getCardLiveList().observe(getActivity(), new Observer<List<Card>>() {
            @Override
            public void onChanged(List<Card> cards) {
                nombre.setText("");
                desc.setText("");


                for(int i = 0; i < cards.size(); i ++){
                    nombre.append("\n" + cards.get(i).getId() + ".- " + cards.get(i).getName());

                }

            }
        });

        LiveData<List<Question>> questions = viewModel.getQuestionList();
        questions.observe(getActivity(), new Observer<List<Question>>() {
            @Override
            public void onChanged(List<Question> questions) {
                for(int j = 0; j < questions.size(); j ++){
                    desc.append("\n" + questions.get(j).getId() + ".- " + questions.get(j).getQuestion() + ": " + questions.get(j).getAnswer() + " " + questions.get(j).getMagnitude());
                }
            }
        });



        btPruebas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Card carta = new Card("https://informatica.ieszaidinvergeles.org:9027/Proyecto%20Top%20Trump/tigre.png",
                        "Tigre",
                        "desc");
                viewModel.insertCard(carta);

                Question question = new Question(carta.getId(), "altura", 95.0,"cm");
                Log.v("xyzyx", "Pregunta: " + question.toString());
                viewModel.insertQuestion(question);
                viewModel.insertQuestion(question);


            }
        });
    }
}