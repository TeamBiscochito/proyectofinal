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

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

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

    private MediaPlayer mp_juego, mp_acierto, mp_fallo;
    View viewCloseJuego, viewBotonJuego1, viewBotonJuego2, viewBotonJuego3, viewBotonJuego4, viewPergaminoPreguntaJugar, viewNombreAnimalJuego, bird1, bird2, bird3, bird4;
    TextView tvBotonJuego1, tvBotonJuego2, tvBotonJuego3, tvBotonJuego4, tvPreguntaJuegoDeterminante, tvPreguntaJuegoTema, tvNombreAnimalJuego;
    Animation animScaleUp, animScaleDown, animPergaminoPregunta, animTableroNombre, animFade;
    ImageView imgAnimalJuego;
    NavController navController;
    Dialog salirJugarDialog, dialogPAcierto, dialogPFallo;

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
        imgAnimalJuego = view.findViewById(R.id.imgAnimalJuego);
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

    public void mostrarDialogPAcierto() {

        dialogPAcierto = new Dialog(getContext());
        dialogPAcierto.setContentView(R.layout.acierto_p_dialog);
        dialogPAcierto.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        Window window = dialogPAcierto.getWindow();
        window.setGravity(Gravity.CENTER);
        window.getAttributes().windowAnimations = R.style.DialogAnimationJuegoAF;

        dialogPAcierto.setCancelable(true);
        dialogPAcierto.setCanceledOnTouchOutside(false);
        window.setLayout(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        dialogPAcierto.show();

        Thread threadCerrarPAciertoDialog = new Thread() {

            @Override
            public void run() {

                try {

                    sleep(2000);
                    dialogPAcierto.dismiss();

                } catch (InterruptedException e) {

                }

            }

        };
        threadCerrarPAciertoDialog.start();

    }

    public void mostrarDialogPFallo() {

        dialogPFallo = new Dialog(getContext());
        dialogPFallo.setContentView(R.layout.fallo_p_dialog);
        dialogPFallo.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        Window window = dialogPFallo.getWindow();
        window.setGravity(Gravity.CENTER);
        window.getAttributes().windowAnimations = R.style.DialogAnimationJuegoAF;

        dialogPFallo.setCancelable(true);
        dialogPFallo.setCanceledOnTouchOutside(false);
        window.setLayout(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        dialogPFallo.show();

        Thread threadCerrarPFalloDialog = new Thread() {

            @Override
            public void run() {

                try {

                    sleep(2000);
                    dialogPFallo.dismiss();

                } catch (InterruptedException e) {

                }

            }

        };
        threadCerrarPFalloDialog.start();

    }

    public void initMediaPlayerJuego() {

        mp_juego = MediaPlayer.create(getContext(), R.raw.game_music);
        mp_juego.setLooping(true);
        mp_juego.start();

        mp_acierto = MediaPlayer.create(getContext(), R.raw.acierto_sound);
        mp_fallo = MediaPlayer.create(getContext(), R.raw.fallo_sound);

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

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mp_juego.stop();
        mp_juego.release();

    }

    /*-------- MÉTODOS DEL JUEGO ---------*/

    public void initJuego() {

        String determinante;

        // Seleccionamos la carta aleatoria
        currentCard = getRandomCard(viewModel.cards);

        // También recuperamos las preguntas de la carta y seleccionamos una al azar
        ArrayList<Question> preguntas = getQuestionsForCurrentCard();

        currentQuestion = getRandomQuestion(preguntas);

        // Generamos posibles respuestas para esta pregunta
        ArrayList<Double> respuestas = getPosiblesRespuestas(currentQuestion);

        // Finalmente mostramos la carta, sus preguntas y las posibles respuestas que salgan de una de las preguntas
        tvNombreAnimalJuego.setText(currentCard.getName());

        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.cargando)
                .error(R.drawable.cerdi)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH);

        Glide.with(getContext()).load(currentCard.getPicUrl())
                .apply(options)
                .into(imgAnimalJuego);

        tvPreguntaJuegoTema.setText((currentQuestion.getQuestion()).toUpperCase());

        switch (currentQuestion.getQuestion().toUpperCase()) {

            case "ALTURA":
                determinante = "la";
                break;
            case "PESO":
                determinante = "el";
                break;
            case "LONGITUD":
                determinante = "la";
                break;
            case "VELOCIDAD":
                determinante = "la";
                break;
            case "PODER":
                determinante = "el";
                break;
            default:
                determinante = "";
                break;

        }

        tvPreguntaJuegoDeterminante.setText("¿Cuál es " + determinante);

        // Muestro las posibles respuestas en forma de botón
        bindButtons(respuestas);

    }

    private void preguntaAcertada() {

        mp_acierto.start();
        mostrarDialogPAcierto();

        respuestasHastaElMomentoAcertadas = respuestasHastaElMomentoAcertadas + 1;
        currentUser.setAnswer(currentUser.getAnswer() + 1);
        currentUser.setTrueAnswer(currentUser.getTrueAnswer() + 1);

        viewModel.updateUser(currentUser);

        initJuego();

    }

    private void preguntaFallada() {

        mp_fallo.start();
        mostrarDialogPFallo();

        currentUser.setAnswer(currentUser.getAnswer() + 1);

        viewModel.updateUser(currentUser);

        initJuego();

    }

    public Card getRandomCard(List<Card> cards) {

        Card result = null;

        if (!cards.isEmpty()) {

            result = cards.get(new Random().nextInt(cards.size()));

        }

        if ((currentCard != null && currentCard == result) || result == null ) {

            return getRandomCard(cards);

        } else {

            return result;

        }

    }

    public Question getRandomQuestion(List<Question> preguntas) {

        Question result;

        result = preguntas.get(new Random().nextInt(preguntas.size()));

        return result;

    }

    public ArrayList<Double> getPosiblesRespuestas(Question incognita){
        ArrayList<Double> respuestas = new ArrayList<>();

        // Rellenamos el array sin la respuesta válida
        while (respuestas.size() < 3) {

            Double respuestaAleatoria;

            if( incognita.getMagnitude() == null ) {
                respuestaAleatoria = (double) new Random().nextInt(9) + 1;
            } else {

                respuestaAleatoria = generaRespuesta(incognita.getAnswer());

            }

            // Comprobamos que no hay dos iguales o numero inválido
            Boolean iguales = false;
            for (Double respuesta:respuestas) {

                if(respuestaAleatoria - respuesta == 0) {

                    iguales = true;

                }
            }

            if(respuestaAleatoria - incognita.getAnswer() == 0 || respuestaAleatoria < 0 || respuestaAleatoria > 9999) {

                iguales = true;

            }


            if (!iguales){
                respuestas.add(respuestaAleatoria);
            }

        }

        // Ordenar aleatoriamente las respuestas:
        // Como todas las respuestas están generadas aleatorias, solo tenemos que poner la respuesta correcta en una posicion del array aleatoria
        respuestas.add(new Random().nextInt(4), incognita.getAnswer());

        return respuestas;
    }


    private double generaRespuesta(Double respuestaInicial) {

        double min, max, numero;

        NumberFormat numberFormat = NumberFormat.getInstance();

        if(respuestaInicial <= 10) {

            Random r = new Random();
            min = respuestaInicial - (respuestaInicial / 100 * 35);
            max = respuestaInicial + (respuestaInicial / 100 * 35);

            if (respuestaInicial % 1 == 0) {
                numberFormat.setMaximumFractionDigits(0);



            } else {
                numberFormat.setMaximumFractionDigits(1);

            }

            numero = Double.parseDouble(numberFormat.format((max - min) * r.nextDouble()+ min)) ;

        } else if (respuestaInicial <= 20) {

            numberFormat.setMaximumFractionDigits(1);
            min = respuestaInicial - (respuestaInicial / 100 * 20);
            max = respuestaInicial + (respuestaInicial / 100 * 20);
            Random r = new Random();

            numero = Double.parseDouble(numberFormat.format((max - min) * r.nextDouble()+ min)) ;
            while( !(numero % 1 == 0 || (numero + 0.5) % 1 == 0) ){
                numero = Double.parseDouble(numberFormat.format((max - min) * r.nextDouble()+ min)) ;
            }

        } else if (respuestaInicial <= 100) {

            Random r = new Random();

            if (respuestaInicial % 1 == 0) {
                numberFormat.setMaximumFractionDigits(0);
                numero = (double) respuestaInicial + ((r.nextInt(8) - 3) * 5);
            } else {
                numberFormat.setMaximumFractionDigits(1);
                numero = (double) respuestaInicial + ((r.nextInt(80) - 30) * 0.5);
            }

        } else {

            Random r = new Random();

            numberFormat.setMaximumFractionDigits(0);
            numero = (double) respuestaInicial + ((r.nextInt(6) - 3) * 500);


        }

        return numero;
    }

    private ArrayList<Question> getQuestionsForCurrentCard() {

        ArrayList<Question> result = new ArrayList<>();

        for (Question q : viewModel.questions) {

            if(q.getCard_id() == currentCard.getId()) {

                result.add(q);

            }

        }

        return result;

    }

    private void bindButtons(ArrayList<Double> respuestas) {

        for (int i = 0; i < respuestas.size(); i++) {

            View vBt = btRespuestas.get(i);
            TextView tv = tvRespuestas.get(i);

            if(currentQuestion.getMagnitude() == null) {

                double d = respuestas.get(i);

                int valorPoderInt = (int) d;

                tv.setText("" + valorPoderInt);

            } else if(respuestas.get(i) % 1 == 0 ) {

                double d = respuestas.get(i);

                int valorPoderInt = (int) d;

                tv.setText("" + valorPoderInt + " " + currentQuestion.getMagnitude());

            } else {

                tv.setText(respuestas.get(i) + " " + currentQuestion.getMagnitude());


            }

            double valori = respuestas.get(i);

            vBt.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {

                    if (event.getAction() == MotionEvent.ACTION_DOWN) {

                        vBt.startAnimation(animScaleUp);
                        tv.startAnimation(animScaleUp);

                        if (valori == currentQuestion.getAnswer()) {

                            preguntaAcertada();

                        } else {

                            preguntaFallada();

                        }

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