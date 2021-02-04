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

import android.app.ActionBar;
import android.app.Dialog;
import android.media.MediaPlayer;
import android.os.Bundle;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import teambiscochito.toptrumpsgame.R;
import teambiscochito.toptrumpsgame.model.room.pojo.Card;
import teambiscochito.toptrumpsgame.model.room.pojo.Question;
import teambiscochito.toptrumpsgame.model.room.pojo.User;
import teambiscochito.toptrumpsgame.view.DialogosGenerales;
import teambiscochito.toptrumpsgame.viewmodel.ViewModel;

/**
 * <h2 align="center">Team Biscochito</h2><hr>
 * <p>
 * Clase en la que iniciamos el juego al presionar el botón play de {@link MenuFragment}
 * <p>
 * Este generará una serie de preguntas totalmente aleatorias al igual que la respuesta, donde
 * podremos elegir entre una serie de respuestas (solo una es la correcta).
 */
@SuppressWarnings({"Convert2Lambda"})
// Comente la línea de arriba para ver los posibles Lambdas a convertir
public class JuegoFragment extends Fragment {
    private static ViewModel viewModel;
    private static User currentUser;
    private final ArrayList<TextView> tvRespuestas = new ArrayList<>();
    private final ArrayList<View> btRespuestas = new ArrayList<>();
    private View viewCloseJuego, viewBotonJuego1, viewBotonJuego2, viewBotonJuego3, viewBotonJuego4,
            viewPergaminoPreguntaJugar, viewNombreAnimalJuego, bird1, bird2, bird3, bird4;
    private TextView tvBotonJuego1, tvBotonJuego2, tvBotonJuego3, tvBotonJuego4, tvPreguntaJuegoDeterminante,
            tvPreguntaJuegoTema, tvNombreAnimalJuego;
    private Animation animScaleUp, animScaleDown, animPergaminoPregunta, animTableroNombre, animFade;
    private ImageView imgAnimalJuego;
    private NavController navController;
    private Dialog salirJugarDialog;
    private Card currentCard;
    private Question currentQuestion;
    private int respuestasHastaElMomentoAcertadas = 0;
    private MediaPlayer mp_juego, mp_acierto, mp_fallo;

    public JuegoFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(requireActivity()).get(ViewModel.class);
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
        viewCerrarJuego();
    }


    /**
     * <h2 align="center">Team Biscochito</h2><hr>
     * <p>
     * Método que se ejecuta al darle a la X para cerrar el fragmento del juego. Posteriormente
     * llama a un nuevo diálogo para ver unas estadísticas. {@link #salirJugarDialog()}
     * <br><br>
     * Referencia del método en: {@link JuegoFragment#onViewCreated(View, Bundle)}
     */
    public void viewCerrarJuego() {
        viewCloseJuego.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        viewCloseJuego.startAnimation(animScaleUp);

                        salirJugarDialog();
                        break;
                    case MotionEvent.ACTION_UP:
                        viewCloseJuego.startAnimation(animScaleDown);
                        v.performClick();
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
    }

    /**
     * <h2 align="center">Team Biscochito</h2><hr>
     * <p>
     * Método que se ejecuta para generar el nuevo diálogo. Obtenemos la puntuación que el jugador
     * lleva actualmente, solo con las preguntas correctas. Tendrá dos opciones salirse de este
     * fragmento o seguir jugando.
     * <br><br>
     * Referencia del método en: {@link JuegoFragment#viewCerrarJuego()}
     */
    public void salirJugarDialog() {
        View viewCancelarSalirAlMenuDialog, viewAceptarSalirAlMenuDialog;
        TextView tvPuntosJuegoDialog;

        salirJugarDialog = new Dialog(getContext());
        salirJugarDialog.setContentView(R.layout.salir_jugar_dialog);
        salirJugarDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        Window window = salirJugarDialog.getWindow();
        window.setGravity(Gravity.CENTER);
        window.getAttributes().windowAnimations = R.style.DialogAnimation;

        viewCancelarSalirAlMenuDialog = salirJugarDialog.findViewById(R.id.viewSalirJugarDialog_Cancel);
        viewAceptarSalirAlMenuDialog = salirJugarDialog.findViewById(R.id.viewSalirJugarDialog_Accept);

        tvPuntosJuegoDialog = salirJugarDialog.findViewById(R.id.tvSalirJugarDialog_Puntos);
        tvPuntosJuegoDialog.setText(String.valueOf(respuestasHastaElMomentoAcertadas));

        viewCancelarSalirAlMenuDialog.setOnClickListener(v -> salirJugarDialog.dismiss());

        viewAceptarSalirAlMenuDialog.setOnClickListener(v -> {
            mp_juego.stop();
            salirJugarDialog.dismiss();
            navController.navigate(R.id.action_juegoFragment_to_menuFragment);
        });

        salirJugarDialog.setCancelable(true);
        salirJugarDialog.setCanceledOnTouchOutside(false);
        window.setLayout(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        salirJugarDialog.show();
    }

    /*-------- MÉTODOS DEL JUEGO ---------*/

    /**
     * <h2 align="center">Team Biscochito</h2><hr>
     * <p>
     * Método que se ejecuta para poder iniciar y empezar a jugar. Contiene todos los demás métodos
     * para hacer aleatoria todas las preguntas (algoritmia).
     * <br><br>
     * Referencia del método en: {@link JuegoFragment#onViewCreated(View, Bundle)}
     * <br>
     * Referencia del método en: {@link JuegoFragment#preguntaAcertada()}
     * <br>
     * Referencia del método en: {@link JuegoFragment#preguntaFallada()}
     */
    public void initJuego() {
        String determinante;

        // Seleccionamos la carta aleatoria
        currentCard = getRandomCard(ViewModel.cards);

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

        Glide.with(requireContext()).load(currentCard.getPicUrl())
                .apply(options)
                .into(imgAnimalJuego);

        tvPreguntaJuegoTema.setText((currentQuestion.getQuestion()).toUpperCase());

        switch (currentQuestion.getQuestion().toUpperCase()) {

            case "ALTURA":
            case "LONGITUD":
            case "VELOCIDAD":
                determinante = "la";
                break;
            case "PESO":
            case "PODER":
                determinante = "el";
                break;
            default:
                determinante = "";
                break;
        }

        tvPreguntaJuegoDeterminante.setText(String.format("¿Cuál es %s", determinante));

        // Muestro las posibles respuestas en forma de botón
        bindButtons(respuestas);
    }

    /**
     * <h2 align="center">Team Biscochito</h2><hr>
     * <p>
     * Método que se ejecuta cuando una pregunta es acertada, crea un diálogo y se suma al contador
     * de preguntas al igual que se actualiza su perfil. {@link MenuFragment#perfilDialog()}
     * <br><br>
     * Referencia del método en: {@link JuegoFragment#initJuego()}
     */
    private void preguntaAcertada() {
        mp_acierto.start();
        DialogosGenerales.dialogoRespuesta(getContext(), R.layout.acierto_p_dialog);

        respuestasHastaElMomentoAcertadas = respuestasHastaElMomentoAcertadas + 1;
        currentUser.setAnswer(currentUser.getAnswer() + 1);
        currentUser.setTrueAnswer(currentUser.getTrueAnswer() + 1);

        viewModel.updateUser(currentUser);

        initJuego();
    }

    /**
     * <h2 align="center">Team Biscochito</h2><hr>
     * <p>
     * Método que se ejecuta cuando una pregunta es fallida, crea un diálogo donde posteriormente se
     * guarda para poder ver todas las preguntas en total realizadas en el perfil del usuario.
     * {@link MenuFragment#perfilDialog()}
     * <br><br>
     * Referencia del método en: {@link JuegoFragment#initJuego()}
     */
    private void preguntaFallada() {
        mp_fallo.start();
        DialogosGenerales.dialogoRespuesta(getContext(), R.layout.fallo_p_dialog);

        currentUser.setAnswer(currentUser.getAnswer() + 1);

        viewModel.updateUser(currentUser);

        initJuego();
    }

    /**
     * <h2 align="center">Team Biscochito</h2><hr>
     * <p>
     * Método que se encarga de escoger una carta aleatoria del conjunto de cartas. No se repiten.
     * <br><br>
     * Referencia del método en: {@link JuegoFragment#initJuego()}
     *
     * @param cards pasamos todas las cartas que tenemos guardadas.
     *
     * @return retorna una carta aleatoria para que no se repitan.
     */
    public Card getRandomCard(List<Card> cards) {
        Card result = null;

        if (!cards.isEmpty()) {
            result = cards.get(new Random().nextInt(cards.size()));
        }

        if ((currentCard != null && currentCard == result) || result == null) {
            return getRandomCard(cards);
        } else {
            return result;
        }
    }

    /**
     * <h2 align="center">Team Biscochito</h2><hr>
     * <p>
     * Método que se encarga de escoger una pregunta de la carta de forma aleatoria.
     * <br><br>
     * Referencia del método en: {@link JuegoFragment#initJuego()}
     *
     * @param preguntas recuperamos el listado de preguntas que tiene la carta.
     *
     * @return una pregunta en específico aleatoria.
     */
    public Question getRandomQuestion(List<Question> preguntas) {
        Question result;

        result = preguntas.get(new Random().nextInt(preguntas.size()));

        return result;
    }

    /**
     * <h2 align="center">Team Biscochito</h2><hr>
     * <p>
     * Método que se encarga de conseguir las posibles respuestas correctas. Generar la correcta
     * dependiendo de las incorrectas {@link #generaRespuesta(Double)}
     * <br><br>
     * Referencia del método en: {@link JuegoFragment#initJuego()}
     *
     * @param incognita pasamos la pregunta.
     *
     * @return las posibles respuestas de la pregunta. En este mismo método generamos la respuesta
     * aleatoria correcta.
     */
    public ArrayList<Double> getPosiblesRespuestas(Question incognita) {
        ArrayList<Double> respuestas = new ArrayList<>();

        // Rellenamos el array sin la respuesta válida
        while (respuestas.size() < 3) {

            Double respuestaAleatoria;

            if (incognita.getQuestion().compareToIgnoreCase("PODER") == 0) {
                respuestaAleatoria = (double) new Random().nextInt(8) + 2;
            } else {
                respuestaAleatoria = generaRespuesta(incognita.getAnswer());
            }

            // Comprobamos que no hay dos iguales o numero inválido
            boolean iguales = false;
            for (Double respuesta : respuestas) {
                if (respuestaAleatoria - respuesta == 0) {
                    iguales = true;
                    break;
                }
            }

            if (respuestaAleatoria - incognita.getAnswer() == 0 || respuestaAleatoria < 0.0 || respuestaAleatoria > 9999) {
                iguales = true;
            }

            if (!iguales) {
                respuestas.add(respuestaAleatoria);
            }
        }

        // Ordenar aleatoriamente las respuestas:
        // Como todas las respuestas están generadas aleatorias, solo tenemos que poner la respuesta correcta en una posición del array aleatoria
        respuestas.add(new Random().nextInt(4), incognita.getAnswer());

        return respuestas;
    }

    /**
     * <h2 align="center">Team Biscochito</h2><hr>
     * <p>
     * Método que se encarga de escoger una carta aleatoria del conjunto de cartas. No se repiten.
     * <br><br>
     * Referencia del método en: {@link JuegoFragment#getPosiblesRespuestas(Question)}
     *
     * @param respuestaInicial respuesta inicial correcta.
     *
     * @return una respuesta aleatoria teniendo en cuenta las preguntas.
     */
    private double generaRespuesta(Double respuestaInicial) {
        double min, max, numero;

        NumberFormat numberFormat = NumberFormat.getInstance();

        Random r = new Random();

        if (respuestaInicial <= 1) {

            numero = Double.parseDouble(numberFormat.format(respuestaInicial + ((r.nextInt(10) - 5) * 0.2)));

        } else if (respuestaInicial <= 5) {
            if (respuestaInicial % 1 == 0) {
                numberFormat.setMaximumFractionDigits(0);
            } else {
                numberFormat.setMaximumFractionDigits(1);
            }

            numero = Double.parseDouble(numberFormat.format(respuestaInicial + ((r.nextInt(12) - 3) * 0.5)));

        } else if (respuestaInicial <= 10) {
            min = respuestaInicial - (respuestaInicial / 100 * 35);
            max = respuestaInicial + (respuestaInicial / 100 * 35);

            if (respuestaInicial % 1 == 0) {
                numberFormat.setMaximumFractionDigits(0);
            } else {
                numberFormat.setMaximumFractionDigits(1);
            }

            numero = Double.parseDouble(numberFormat.format((max - min) * r.nextDouble() + min));

        } else if (respuestaInicial <= 25) {

            if (respuestaInicial % 1 == 0) {
                numberFormat.setMaximumFractionDigits(0);
                numero = Double.parseDouble(numberFormat.format(respuestaInicial + ((r.nextInt(6) - 2) * 5)));
            } else {
                numberFormat.setMaximumFractionDigits(1);
                numero = Double.parseDouble(numberFormat.format(respuestaInicial + ((r.nextInt(60) - 30) * 0.5)));
            }

        } else if (respuestaInicial <= 100) {

            if (respuestaInicial % 1 == 0) {
                numberFormat.setMaximumFractionDigits(0);
                numero = Double.parseDouble(numberFormat.format(respuestaInicial + ((r.nextInt(7) - 3) * 5)));
            } else {
                numberFormat.setMaximumFractionDigits(1);
                numero = Double.parseDouble(numberFormat.format(respuestaInicial + ((r.nextInt(70) - 30) * 0.5)));
            }

        } else if (respuestaInicial <= 1000) {

            if (respuestaInicial % 1 == 0) {
                numberFormat.setMaximumFractionDigits(0);
            } else {
                numberFormat.setMaximumFractionDigits(1);
            }

            numero = (double) respuestaInicial + ((r.nextInt(8) - 3) * 50);

        } else {
            numberFormat.setMaximumFractionDigits(0);
            numero = (double) respuestaInicial + ((r.nextInt(6) - 3) * 500);
        }
        return numero;
    }

    /**
     * <h2 align="center">Team Biscochito</h2><hr>
     * <p>
     * Método que se encarga de escoger el listado de preguntas de la carta.
     * <br><br>
     * Referencia del método en: {@link JuegoFragment#initJuego()}
     *
     * @return el listado de preguntas de la carta.
     */
    private ArrayList<Question> getQuestionsForCurrentCard() {
        ArrayList<Question> result = new ArrayList<>();

        for (Question q : ViewModel.questions) {
            if (q.getCard_id() == currentCard.getId()) {
                result.add(q);
            }
        }
        return result;
    }

    /**
     * <h2 align="center">Team Biscochito</h2><hr>
     * <p>
     * Método que se ejecuta para mostrar las respuesta en forma de botón, para una mayor comodidad
     * a la hora de elegir la respuesta correcta. Llamamos a los diálogos cuando una pregunta es
     * correcta o fallida.
     * <br><br>
     * Referencia del método en: {@link JuegoFragment#initJuego()}
     *
     * @param respuestas mostrar todas las respuestas.
     */
    private void bindButtons(ArrayList<Double> respuestas) {
        for (int i = 0; i < respuestas.size(); i++) {
            View vBt = btRespuestas.get(i);
            TextView tv = tvRespuestas.get(i);
            if (currentQuestion.getMagnitude() == null) {
                double d = respuestas.get(i);

                int valorPoderInt = (int) d;

                tv.setText(String.valueOf(valorPoderInt));

            } else if (respuestas.get(i) % 1 == 0) {
                double d = respuestas.get(i);

                int valorPoderInt = (int) d;

                tv.setText(String.format(Locale.getDefault(), "%d %s", valorPoderInt, currentQuestion.getMagnitude()));
            } else {
                tv.setText(String.format("%s %s", respuestas.get(i), currentQuestion.getMagnitude()));
            }

            double valori = respuestas.get(i);

            vBt.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            vBt.startAnimation(animScaleUp);
                            tv.startAnimation(animScaleUp);

                            if (valori == currentQuestion.getAnswer()) {
                                preguntaAcertada();
                            } else {
                                preguntaFallada();
                            }
                        case MotionEvent.ACTION_UP:
                            vBt.startAnimation(animScaleDown);
                            tv.startAnimation(animScaleDown);
                            v.performClick();
                        default:
                            break;
                    }
                    return true;
                }
            });
        }
    }

    private void init(View view) {
        animScaleUp = AnimationUtils.loadAnimation(getContext(), R.anim.scale_up);
        animScaleDown = AnimationUtils.loadAnimation(getContext(), R.anim.scale_down);
        animPergaminoPregunta = AnimationUtils.loadAnimation(getContext(), R.anim.slide_jugar);
        animTableroNombre = AnimationUtils.loadAnimation(getContext(), R.anim.slide_tutorial);
        animFade = AnimationUtils.loadAnimation(getContext(), R.anim.fade_birds);

        viewCloseJuego = view.findViewById(R.id.viewJuego_Close);
        viewBotonJuego1 = view.findViewById(R.id.viewJuego_Respuesta1);
        viewBotonJuego2 = view.findViewById(R.id.viewJuego_Respuesta2);
        viewBotonJuego3 = view.findViewById(R.id.viewJuego_Respuesta3);
        viewBotonJuego4 = view.findViewById(R.id.viewJuego_Respuesta4);

        viewPergaminoPreguntaJugar = view.findViewById(R.id.viewJuego_PreguntaPergamino);
        viewNombreAnimalJuego = view.findViewById(R.id.viewJuego_Nombre);

        imgAnimalJuego = view.findViewById(R.id.civJuego_Animal);

        bird1 = view.findViewById(R.id.viewJuego_Bird1);
        bird2 = view.findViewById(R.id.viewJuego_Bird2);
        bird3 = view.findViewById(R.id.viewJuego_Bird3);
        bird4 = view.findViewById(R.id.viewJuego_Bird4);

        tvBotonJuego1 = view.findViewById(R.id.tvJuego_Respuesta1);
        tvBotonJuego2 = view.findViewById(R.id.tvJuego_Respuesta2);
        tvBotonJuego3 = view.findViewById(R.id.tvJuego_Respuesta3);
        tvBotonJuego4 = view.findViewById(R.id.tvJuego_Respuesta4);

        tvPreguntaJuegoDeterminante = view.findViewById(R.id.tvJuego_Pregunta1);
        tvPreguntaJuegoTema = view.findViewById(R.id.tvJuego_Pregunta);
        tvNombreAnimalJuego = view.findViewById(R.id.tvJuego_Nombre);
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
}