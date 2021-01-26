package teambiscochito.toptrumpsgame.view.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.xml.transform.Result;

import teambiscochito.toptrumpsgame.R;
import teambiscochito.toptrumpsgame.model.room.pojo.Card;
import teambiscochito.toptrumpsgame.model.room.pojo.Question;
import teambiscochito.toptrumpsgame.model.room.pojo.User;
import teambiscochito.toptrumpsgame.viewmodel.ViewModel;

public class JuegoFragment extends Fragment {

    private static ViewModel viewModel;
    private static User currentUser;
    static List<Card> cards;
    static List<Question> questions;
    private Card currentCard;
    private Question currentQuestion;
    private ArrayList<Button> btRespuestas = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(getActivity()).get(ViewModel.class);
        currentUser = viewModel.getUser();
        //cardList = viewModel.getAllCard();

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_juego, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //harvesting de botones y campos de texto
        Button btRespuesta1 = view.findViewById(R.id.btRespuesta1);
        Button btRespuesta2 = view.findViewById(R.id.btRespuesta2);
        Button btRespuesta3 = view.findViewById(R.id.btRespuesta3);
        Button btRespuesta4 = view.findViewById(R.id.btRespuesta4);

        btRespuestas.add(btRespuesta1);
        btRespuestas.add(btRespuesta2);
        btRespuestas.add(btRespuesta3);
        btRespuestas.add(btRespuesta4);

        //ANTES de iniciar el juego, vamos a iniciar los arrays para asi tener la ultima version
        viewModel.getCardLiveList().observe(getActivity(), new Observer<List<Card>>() {
            @Override
            public void onChanged(List<Card> liveCards) {
                cards = liveCards;

                viewModel.getQuestionList().observe(getActivity(), new Observer<List<Question>>() {
                    @Override
                    public void onChanged(List<Question> liveQuestions) {

                        questions = liveQuestions;

                        //iniciamos el juego
                        init();
                    }
                });
            }
        });

    }

    public void init(){

        //seleccionamos la carta aleatoria
        currentCard = getRandomCard(cards);

        //tambien recuperamos las preguntas de la carta y seleccionamos una al azar
        ArrayList<Question> preguntas = getQuestionsForCurrentCard();
        currentQuestion = getRandomQuestion(preguntas);


        //generamos posibles respuestas para esta pregunta
        ArrayList<Double> respuestas = getPosiblesRespuestas(currentQuestion);



        //finalmente mostramos la carta, sus preguntas y las posibles respuestas que salgan de una de las preguntas
        Toast.makeText(getContext(), "La carta elegida va a ser: " + currentCard.toString(), Toast.LENGTH_LONG).show();
        Toast.makeText(getContext(), "La PREGUNTA es: " + currentQuestion.toString(), Toast.LENGTH_LONG).show();
        Toast.makeText(getContext(), "Las POSIBLES RESPUESTAS: " + respuestas.toString(), Toast.LENGTH_LONG).show();
        Log.v("xyzyx", "La carta elegida va a ser: " + currentCard.toString());
        Log.v("xyzyx", "La PREGUNTA es: " + currentQuestion.toString());
        Log.v("xyzyx", "Las POSIBLES RESPUESTAS: " + respuestas.toString());


        //muestro las posibles respuestas en forma de boton
        bindButtons(respuestas);

        /*// trunca a dos digitos
        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setMaximumFractionDigits(2);*///Para sacar bien las respuestas podemos usar este metodo
    }


    @Override
    public void onDestroy() {
        viewModel.setUser(currentUser);
        super.onDestroy();
    }

    private void preguntaAcertada(){
        init();

        currentUser.setAnswer(currentUser.getAnswer() + 1);
        currentUser.setTrueAnswer(currentUser.getTrueAnswer() + 1);

        viewModel.updateUser(currentUser);
    }

    private void preguntaFallada(){
        init();

        currentUser.setAnswer(currentUser.getAnswer() + 1);

        viewModel.updateUser(currentUser);
    }


    /*--------- GAME METHODS ---------*/

    public Card getRandomCard(List<Card> cards){

        Card result = null;
        if(!cards.isEmpty()){
            result = cards.get(new Random().nextInt(cards.size()));
        }


        if ((currentCard != null && currentCard == result) || result == null ){
            return getRandomCard(cards);
        } else {
            return result;
        }
    }


    public Question getRandomQuestion(List<Question> preguntas){

        Question result = null;
        if(!preguntas.isEmpty()){
            result = preguntas.get(new Random().nextInt(preguntas.size()));
        }

        if ( result == null ){
            return getRandomQuestion(preguntas);
        } else {
            return result;
        }
    }

    public ArrayList<Double> getPosiblesRespuestas(Question incognita){
        ArrayList<Double> respuestas = new ArrayList<>();
        //en poder mortifero no usar ni un decimal


        //rellenamos el array sin la respuesta válida
        while (respuestas.size() < 3) {
            Double respuestaAleatoria = generaRespuesta(incognita.getAnswer());

            //comprobamos que no hay dos iguales
            Boolean iguales = false;
            for (Double respuesta:respuestas) {
                if(respuestaAleatoria == respuesta || respuestaAleatoria == incognita.getAnswer()){
                    iguales = true;
                }
            }


            if (!iguales){
                respuestas.add(respuestaAleatoria);
            }

        }

        //ordenar aleatoriamente las respuestas:
        //como todas las respuestas están generadas aleatorias, solo tenemos que poner la respuesta correcta en una posicion del array aleatoria

        respuestas.add(new Random().nextInt(4), incognita.getAnswer());

        return respuestas;
    }

    private double generaRespuesta(Double respuestaInicial){
        double min = respuestaInicial - (respuestaInicial / 100 * 15);
        double max = respuestaInicial + (respuestaInicial / 100 * 15);
        Random r = new Random();
        double numero = ((max - min) * r.nextDouble()) + min;

        return numero;
    }

    private ArrayList<Question> getQuestionsForCurrentCard() {
        ArrayList<Question> result = new ArrayList<>();

        for (Question q : questions) {
            if(q.getCard_id() == currentCard.getId()){
                result.add(q);
            }
        }

        return result;
    }


    private void bindButtons(ArrayList<Double> respuestas){
        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setMaximumFractionDigits(2);

        for (int i = 0; i < btRespuestas.size(); i++) {
            btRespuestas.get(i).setText(numberFormat.format(respuestas.get(i)));

            if (respuestas.get(i) == currentQuestion.getAnswer()){
                btRespuestas.get(i).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        preguntaAcertada();
                    }
                });
            } else {
                btRespuestas.get(i).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        preguntaFallada();
                    }
                });
            }
        }

    }

}