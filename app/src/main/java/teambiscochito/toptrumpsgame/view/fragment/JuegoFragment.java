package teambiscochito.toptrumpsgame.view.fragment;

import android.app.ActionBar;
import android.app.Dialog;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import teambiscochito.toptrumpsgame.R;
import teambiscochito.toptrumpsgame.model.room.pojo.Card;
import teambiscochito.toptrumpsgame.model.room.pojo.Question;
import teambiscochito.toptrumpsgame.model.room.pojo.User;
import teambiscochito.toptrumpsgame.viewmodel.ViewModel;

public class JuegoFragment extends Fragment {

    private static ViewModel viewModel;
    private static User currentUser;
    private Card currentCard;
    private Question currentQuestion;
    private ArrayList<TextView> tvRespuestas = new ArrayList<>();
    private ArrayList<View> btRespuestas = new ArrayList<>();

    private int respuestasHastaElMomentoAcertadas = 0;

    private MediaPlayer mp_juego;
    View viewCloseJuego, viewBotonJuego1, viewBotonJuego2, viewBotonJuego3, viewBotonJuego4, viewPergaminoPreguntaJugar, viewNombreAnimalJuego, bird1, bird2, bird3, bird4;
    TextView tvBotonJuego1, tvBotonJuego2, tvBotonJuego3, tvBotonJuego4, tvPreguntaJuegoDeterminante, tvPreguntaJuegoTema, tvNombreAnimalJuego;
    Animation animScaleUp, animScaleDown, animPergaminoPregunta, animTableroNombre, animFade;
    NavController navController;
    Dialog salirJugarDialog;

    public JuegoFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(getActivity()).get(ViewModel.class);
        currentUser = viewModel.getUser();
        //cardList = viewModel.getAllCard();


        return inflater.inflate(R.layout.fragment_juego, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init(view);

        navController = Navigation.findNavController(view);

        initMediaPlayerJuego();

        viewPergaminoPreguntaJugar.startAnimation(animPergaminoPregunta);
        tvPreguntaJuegoTema.startAnimation(animPergaminoPregunta);

        viewNombreAnimalJuego.startAnimation(animTableroNombre);
        tvNombreAnimalJuego.startAnimation(animTableroNombre);

        bird1.startAnimation(animFade);
        bird2.startAnimation(animFade);
        bird3.startAnimation(animFade);
        bird4.startAnimation(animFade);

        tvRespuestas.add(tvBotonJuego1);
        tvRespuestas.add(tvBotonJuego2);
        tvRespuestas.add(tvBotonJuego3);
        tvRespuestas.add(tvBotonJuego4);

        btRespuestas.add(viewBotonJuego1);
        btRespuestas.add(viewBotonJuego2);
        btRespuestas.add(viewBotonJuego3);
        btRespuestas.add(viewBotonJuego4);

        initJuego();

        viewCloseJuego.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    viewCloseJuego.startAnimation(animScaleUp);

                    salirJugarDialog();

                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    viewCloseJuego.startAnimation(animScaleDown);

                }

                return true;
            }
        });

        /*viewBotonJuego1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    viewBotonJuego1.startAnimation(animScaleUp);
                    tvBotonJuego1.startAnimation(animScaleUp);

                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    viewBotonJuego1.startAnimation(animScaleDown);
                    tvBotonJuego1.startAnimation(animScaleDown);

                }

                return true;
            }
        });

        viewBotonJuego2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    viewBotonJuego2.startAnimation(animScaleUp);
                    tvBotonJuego2.startAnimation(animScaleUp);

                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    viewBotonJuego2.startAnimation(animScaleDown);
                    tvBotonJuego2.startAnimation(animScaleDown);

                }

                return true;
            }
        });

        viewBotonJuego3.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    viewBotonJuego3.startAnimation(animScaleUp);
                    tvBotonJuego3.startAnimation(animScaleUp);

                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    viewBotonJuego3.startAnimation(animScaleDown);
                    tvBotonJuego3.startAnimation(animScaleDown);

                }

                return true;
            }
        });

        viewBotonJuego4.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    viewBotonJuego4.startAnimation(animScaleUp);
                    tvBotonJuego4.startAnimation(animScaleUp);

                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    viewBotonJuego4.startAnimation(animScaleDown);
                    tvBotonJuego4.startAnimation(animScaleDown);

                }

                return true;
            }
        });*/



    }

    private void init(View view) {

        animScaleUp = AnimationUtils.loadAnimation(getContext(), R.anim.scale_up);
        animScaleDown = AnimationUtils.loadAnimation(getContext(), R.anim.scale_down);
        animPergaminoPregunta = AnimationUtils.loadAnimation(getContext(), R.anim.slide_jugar);
        animTableroNombre = AnimationUtils.loadAnimation(getContext(), R.anim.slide_tutorial);
        animFade = AnimationUtils.loadAnimation(getContext(), R.anim.fade_birds);

        viewCloseJuego = view.findViewById(R.id.viewCloseJuego);
        viewBotonJuego1 = view.findViewById(R.id.viewBotonJuego1);
        viewBotonJuego2 = view.findViewById(R.id.viewBotonJuego2);
        viewBotonJuego3 = view.findViewById(R.id.viewBotonJuego3);
        viewBotonJuego4 = view.findViewById(R.id.viewBotonJuego4);
        viewPergaminoPreguntaJugar = view.findViewById(R.id.viewPergaminoPreguntaJugar);
        viewNombreAnimalJuego = view.findViewById(R.id.viewNombreAnimalJuego);
        bird1 = view.findViewById(R.id.bird1);
        bird2 = view.findViewById(R.id.bird2);
        bird3 = view.findViewById(R.id.bird3);
        bird4 = view.findViewById(R.id.bird4);

        tvBotonJuego1 = view.findViewById(R.id.tvBotonJuego1);
        tvBotonJuego2 = view.findViewById(R.id.tvBotonJuego2);
        tvBotonJuego3 = view.findViewById(R.id.tvBotonJuego3);
        tvBotonJuego4 = view.findViewById(R.id.tvBotonJuego4);

        tvPreguntaJuegoDeterminante = view.findViewById(R.id.tvPreguntaJuegoDeterminante);
        tvPreguntaJuegoTema = view.findViewById(R.id.tvPreguntaJuegoTema);
        tvNombreAnimalJuego = view.findViewById(R.id.tvNombreAnimalJuego);



    }

    public void salirJugarDialog() {

        View viewCancelarSalirAlMenuDialog, viewAceptarSalirAlMenuDialog;
        TextView tvPuntosJuegoDialog;

        salirJugarDialog = new Dialog(getContext());
        salirJugarDialog.setContentView(R.layout.salir_jugar_dialog);
        salirJugarDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        Window window = salirJugarDialog.getWindow();
        window.setGravity(Gravity.CENTER);
        window.getAttributes().windowAnimations = R.style.DialogAnimation;

        viewCancelarSalirAlMenuDialog = salirJugarDialog.findViewById(R.id.viewCancelarSalirAlMenuDialog);
        viewAceptarSalirAlMenuDialog = salirJugarDialog.findViewById(R.id.viewAceptarSalirAlMenuDialog);
        tvPuntosJuegoDialog = salirJugarDialog.findViewById(R.id.tvPuntosJuegoDialog);

        tvPuntosJuegoDialog.setText("" + respuestasHastaElMomentoAcertadas);

        viewCancelarSalirAlMenuDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                salirJugarDialog.dismiss();

            }
        });

        viewAceptarSalirAlMenuDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mp_juego.stop();
                salirJugarDialog.dismiss();
                navController.navigate(R.id.action_juegoFragment_to_menuFragment);

            }
        });

        salirJugarDialog.setCancelable(true);
        salirJugarDialog.setCanceledOnTouchOutside(false);
        window.setLayout(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        salirJugarDialog.show();

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
        viewModel.updateUser(currentUser);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mp_juego.stop();
        mp_juego.release();
        viewModel.updateUser(currentUser);

    }

    /*-------- GAME METHODS ---------*/

    public void initJuego(){

        //seleccionamos la carta aleatoria
        currentCard = getRandomCard(viewModel.cards);

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

        tvBotonJuego1.setText(respuestas.get(0) + "");
        tvBotonJuego2.setText(respuestas.get(1) + "");
        tvBotonJuego3.setText(respuestas.get(2) + "");
        tvBotonJuego4.setText(respuestas.get(3) + "");


        //muestro las posibles respuestas en forma de boton
        bindButtons(respuestas);


    }


    private void preguntaAcertada(){
        initJuego();

        currentUser.setAnswer(currentUser.getAnswer() + 1);
        currentUser.setTrueAnswer(currentUser.getTrueAnswer() + 1);

        viewModel.updateUser(currentUser);
    }

    private void preguntaFallada(){
        initJuego();

        currentUser.setAnswer(currentUser.getAnswer() + 1);

        viewModel.updateUser(currentUser);
    }

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

        Question result;

        result = preguntas.get(new Random().nextInt(preguntas.size() - 1));


        /*if ( result == null ){
            initJuego();
        }*/

        return result;
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

        for (Question q : viewModel.questions) {
            if(q.getCard_id() == currentCard.getId()){
                result.add(q);
            }
        }

        return result;
    }


    private void bindButtons(ArrayList<Double> respuestas){
        /*NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setMaximumFractionDigits(2);*/
        Log.v("xyzyx", "BindButtons " + tvRespuestas.size());

        for (int i = 0; i < respuestas.size(); i++) {

            Log.v("xyzyx", "FOR");
            View vBt = btRespuestas.get(i);
            TextView tv = tvRespuestas.get(i);
            tv.setText(respuestas.get(i) + currentQuestion.getMagnitude());

            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.v("xyzyx", "hola click");
                }
            });

            if (respuestas.get(i) == currentQuestion.getAnswer()){


                vBt.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {

                        Log.v("xyzyx", "hola touch");
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            vBt.startAnimation(animScaleUp);
                            tv.startAnimation(animScaleUp);
                            preguntaAcertada();

                        } else if (event.getAction() == MotionEvent.ACTION_UP) {
                            vBt.startAnimation(animScaleDown);
                            tv.startAnimation(animScaleDown);

                        }


                        return true;
                    }
                });

            } else {
                vBt.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {

                        Log.v("xyzyx", "hola touch");
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            vBt.startAnimation(animScaleUp);
                            tv.startAnimation(animScaleUp);
                            preguntaFallada();

                        } else if (event.getAction() == MotionEvent.ACTION_UP) {
                            vBt.startAnimation(animScaleDown);
                            tv.startAnimation(animScaleDown);

                        }

                        return true;
                    }
                });
            }
        }

    }
}
