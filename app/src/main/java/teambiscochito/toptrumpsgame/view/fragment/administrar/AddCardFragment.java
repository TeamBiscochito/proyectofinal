package teambiscochito.toptrumpsgame.view.fragment.administrar;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import teambiscochito.toptrumpsgame.R;
import teambiscochito.toptrumpsgame.model.room.pojo.Card;
import teambiscochito.toptrumpsgame.model.room.pojo.Question;
import teambiscochito.toptrumpsgame.validar.ValidarDatos;
import teambiscochito.toptrumpsgame.view.fragment.DialogosGenerales;
import teambiscochito.toptrumpsgame.viewmodel.ViewModel;

/**
 * <h2 align="center">Team Biscochito</h2><hr>
 * <p>
 * Clase para añadir una nueva carta con valores nuestros donde se mostraran y recogeremos todos los
 * valores para después añadirlos al conjunto de cartas de la base de datos.
 */
@SuppressWarnings({"Convert2Lambda"})
// Comente la línea de arriba para ver los posibles Lambdas a convertir
public class AddCardFragment extends Fragment {
    NavController navController;
    View viewBackAdminAddCarta, viewAddCarta2;
    TextView tvAddCarta2, tvAlertaAddCarta;
    Animation animScaleUp, animScaleDown;

    ViewModel viewModel;
    EditText nombreEt, descripcionEt, alturaEt, pesoEt, velocidadEt, longitudEt, poderEt, urlEt;
    Spinner alturaSp, pesoSp, velocidadSp, longitudSp;

    public AddCardFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_card, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init(view);

        navController = Navigation.findNavController(view);

        viewModel = new ViewModelProvider(requireActivity()).get(ViewModel.class);

        spinnersMagnitudesCarta();

        DialogosGenerales.volverAtrasDialog(R.id.action_addCardFragment_to_adminCartasFragment,
                getContext(), view, viewBackAdminAddCarta);

        buscarInternet(view);

        insertarCarta();
    }

    /**
     * <h2 align="center">Team Biscochito</h2><hr>
     * <p>
     * Método para recoger los valores del formulario al insertar la carta, iniciamos unos nuevos
     * valores para después hacer un check de que los valores no estén vacíos. Los metemos en un
     * try catch y obtenemos los valores de los editText.
     * <br><br>
     * Ocurre cuando hacemos click en la vista de añadir carta, en el otro método controlamos los
     * valores metidos en las diferentes opciones.
     * <br><br>
     * Método llamado en {@link AddCardFragment#onViewCreated(View, Bundle)}
     * <br>
     * Método de inserta carta:
     * {@link #insertarCarta(String, int, String, String, String, String, String, String, double,
     * double, double, double, double, double, double, double, double)}
     */
    private void insertarCarta() {
        viewAddCarta2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        viewAddCarta2.startAnimation(animScaleUp);
                        tvAddCarta2.startAnimation(animScaleUp);

                        String nombre = nombreEt.getText().toString();

                        viewModel.getNameFromNameCarta(nombre);
                        int num = viewModel.getRepeatedNameCarta();

                        String descripcion = descripcionEt.getText().toString();
                        String url = urlEt.getText().toString();
                        String alturaMag = alturaSp.getSelectedItem().toString();
                        String pesoMag = pesoSp.getSelectedItem().toString();
                        String longitudMag = longitudSp.getSelectedItem().toString();
                        String velocidadMag = velocidadSp.getSelectedItem().toString();

                        double altura = -1;
                        double peso = -1;
                        double velocidad = -1;
                        double longitud = -1;
                        double poder = -1;

                        double altd = -1;
                        double pesd = -1;
                        double veld = -1;
                        double lond = -1;

                        try {
                            altura = Double.parseDouble(alturaEt.getText().toString());
                            peso = Double.parseDouble(pesoEt.getText().toString());
                            velocidad = Double.parseDouble(velocidadEt.getText().toString());
                            longitud = Double.parseDouble(longitudEt.getText().toString());
                            poder = Double.parseDouble(poderEt.getText().toString());

                            NumberFormat nf = NumberFormat.getNumberInstance(Locale.US);
                            DecimalFormat formatter = (DecimalFormat) nf;
                            formatter.applyPattern("##.#");

                            String alturaDouble = formatter.format(altura);
                            altd = Double.parseDouble(alturaDouble);

                            String pesoDouble = formatter.format(peso);
                            pesd = Double.parseDouble(pesoDouble);

                            String velocidadDouble = formatter.format(velocidad);
                            veld = Double.parseDouble(velocidadDouble);

                            String longitudDouble = formatter.format(longitud);
                            lond = Double.parseDouble(longitudDouble);
                        } catch (Exception ignored) {
                        }

                        insertarCarta(nombre, num, descripcion, alturaMag, pesoMag, longitudMag,
                                velocidadMag, url, altura, peso, velocidad, longitud, poder, altd,
                                pesd, veld, lond);
                        break;
                    case MotionEvent.ACTION_UP:
                        viewAddCarta2.startAnimation(animScaleDown);
                        tvAddCarta2.startAnimation(animScaleDown);
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
     * Método con el que podemos buscar en internet cuando ponemos un nombre el texto del formulario
     * para añadir una nueva carta, creando un nuevo intent y abriendo Google Imágenes con el texto
     * que hayamos puesto en "nombre de carta".
     * <br><br>
     * Implementado en {@link AddCardFragment#onViewCreated(View, Bundle)}
     *
     * @param view pasamos la vista del viewHolder.
     */
    private void buscarInternet(@NonNull View view) {
        view.findViewById(R.id.btAddCard_BuscarInternet).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String busqueda = "Imágenes de animales";

                if (!(nombreEt.getText().toString().compareToIgnoreCase("") == 0)) {
                    busqueda = "Imágenes de " + nombreEt.getText().toString();
                }

                Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
                intent.putExtra(SearchManager.QUERY, busqueda);

                if (intent.resolveActivity(requireActivity().getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });
    }

    /**
     * <h2 align="center">Team Biscochito</h2><hr>
     * <p>
     * Método para los spinners de los campos altura, peso, velocidad y longitud. Le damos estilos y
     * agregamos el array de las magnitudes al checkbox.
     * <br><br>
     * Implementado en {@link AddCardFragment#onViewCreated(View, Bundle)}
     */
    private void spinnersMagnitudesCarta() {
        ArrayAdapter<CharSequence> alturaAdapter = ArrayAdapter.createFromResource(getContext(), R.array.magnitudesAltura, R.layout.style_spinner);
        alturaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        alturaSp.setAdapter(alturaAdapter);

        ArrayAdapter<CharSequence> pesoAdapter = ArrayAdapter.createFromResource(getContext(), R.array.magnitudesPeso, R.layout.style_spinner);
        alturaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        pesoSp.setAdapter(pesoAdapter);

        ArrayAdapter<CharSequence> velocidadAdapter = ArrayAdapter.createFromResource(getContext(), R.array.magnitudesVelocidad, R.layout.style_spinner);
        alturaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        velocidadSp.setAdapter(velocidadAdapter);

        ArrayAdapter<CharSequence> longitudAdapter = ArrayAdapter.createFromResource(getContext(), R.array.magnitudesLongitud, R.layout.style_spinner);
        alturaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        longitudSp.setAdapter(longitudAdapter);
    }

    /**
     * <h2 align="center">Team Biscochito</h2><hr>
     * <p>
     * Método que recoge todos los datos de {@link #insertarCarta()} carta, lo dividimos en dos
     * métodos para que no se haga muy tedioso a la hora de leer el código. Por lo tanto en este
     * método, le pasamos todos los parámetro que hemos obtenido en el método {@link #insertarCarta()}
     * <p>
     * Ingresamos todos los valores para posteriormente agregarlos a un ArrayList e insertarlas en
     * la base de datos.
     * <br><br>
     * Link de referencia: {@link ViewModel#insertAll(ArrayList)}
     *
     * @param nombre       nombre de la carta.
     * @param num          guardamos el numero de la carta para que no podamos repetir en un futuro.
     * @param descripcion  descripción de la carta.
     * @param alturaMag    guardamos la magnitud del spinner de la altura.
     * @param pesoMag      guardamos la magnitud del spinner del peso.
     * @param longitudMag  guardamos la magnitud del spinner de la longitud.
     * @param velocidadMag guardamos la magnitud del spinner de la velocidad.
     * @param url          guardamos la URL de la imágen para visualizarla más tarde.
     * @param altura       guardamos la altura de nuestro animal.
     * @param peso         guardamos el peso de nuestro animal.
     * @param velocidad    guardamos la velocidad de nuestro animal.
     * @param longitud     guardamos la longitud de nuestro animal.
     * @param poder        guardamos el poder de nuestro animal.
     * @param altd         nueva altura parseada a double.
     * @param pesd         nuevo peso parseado a double.
     * @param veld         nueva velocidad parseada a double.
     * @param lond         nueva longitud parseada a double.
     */
    private void insertarCarta(String nombre, int num, String descripcion, String alturaMag, String pesoMag,
                               String longitudMag, String velocidadMag, String url, double altura, double peso,
                               double velocidad, double longitud, double poder, double altd, double pesd, double veld, double lond) {
        if (nombre.isEmpty() || descripcion.isEmpty() || url.isEmpty() || altura == -1 || peso == -1
                || velocidad == -1 || longitud == -1 || poder == -1) {

            tvAlertaAddCarta.setText(R.string.alertAlgunCampoEstaVacio);

        } else if (ValidarDatos.validarNombreCarta(nombre)) {

            tvAlertaAddCarta.setText(R.string.alertNombreNoValido);
            nombreEt.setText("");

        } else if (num != 0) {

            tvAlertaAddCarta.setText(R.string.tvNombreYaEnUso);
            nombreEt.setText("");

        } else if (ValidarDatos.validarDescCarta(descripcion)) {

            tvAlertaAddCarta.setText(R.string.alertDescDemasiadoLarga);
            descripcionEt.setText("");

        } else if (ValidarDatos.validarDatosCarta(altura)) {

            tvAlertaAddCarta.setText(R.string.alertAlturaNoValida);
            alturaEt.setText("");

        } else if (ValidarDatos.validarDatosCarta(peso)) {

            tvAlertaAddCarta.setText(R.string.alertPesoNoValido);
            pesoEt.setText("");

        } else if (ValidarDatos.validarDatosCarta(longitud)) {

            tvAlertaAddCarta.setText(R.string.alertLongitudNoValida);
            longitudEt.setText("");

        } else if (ValidarDatos.validarDatosCarta(velocidad)) {

            tvAlertaAddCarta.setText(R.string.alertVelocidadNoValida);
            velocidadEt.setText("");

        } else if (ValidarDatos.validarPoderCarta(poder)) {

            tvAlertaAddCarta.setText(R.string.alertPoderNoValido);
            poderEt.setText("");

        } else {
            Card card = new Card(url, nombre, descripcion);

            viewModel.insertCard(card);
            try {
                Thread.sleep(500);
            } catch (InterruptedException ignored) {
            }

            long idcard = viewModel.getIdByName(nombre);

            if (idcard == 0) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ignored) {
                }
            }

            ArrayList<Question> questionArrayList = getQuestions(alturaMag, pesoMag, longitudMag,
                    velocidadMag, poder, altd, pesd, veld, lond, idcard);

            viewModel.insertAll(questionArrayList);

            tvAlertaAddCarta.setText("");
            navController.navigate(R.id.action_addCardFragment_to_adminCartasFragment);
        }
    }

    /**
     * <h2 align="center">Team Biscochito</h2><hr>
     * <p>
     * Método para agregar las propiedades de las cartas antes de agregarlas a nuestro ViewModel y
     * después al repositorio. (Altura, Peso, Longitud, Velocidad, Poder, con sus respectivas
     * magnitudes).
     * <br>
     * Link de referencia: {@link ViewModel#insertAll(ArrayList)}
     *
     * Método referenciado en: {@link #insertarCarta(String, int, String, String, String, String,
     * String, String, double, double, double, double, double, double, double, double, double)}
     *
     * @param alturaMag    la magnitud de la altura.
     * @param pesoMag      la magnitud del peso.
     * @param longitudMag  la magnitud de la longitud.
     * @param velocidadMag la magnitud de la velocidad.
     * @param poder        el poder de nuestro animal.
     * @param altd         la altura double de nuestro animal.
     * @param pesd         el peso double de nuestro animal.
     * @param veld         la velocidad double de nuestro animal.
     * @param lond         la longitud double de nuestro animal.
     * @param idcard       la id de la carta de nuestro animal.
     *
     * @return ArrayList de question {@link Question}
     */
    private ArrayList<Question> getQuestions(String alturaMag, String pesoMag, String longitudMag, String velocidadMag,
                                             double poder, double altd, double pesd, double veld, double lond, long idcard) {
        ArrayList<Question> questionArrayList = new ArrayList<>();

        Question q1 = new Question(idcard, "Altura", altd, alturaMag);
        questionArrayList.add(q1);

        Question q2 = new Question(idcard, "Peso", pesd, pesoMag);
        questionArrayList.add(q2);

        Question q3 = new Question(idcard, "Longitud", lond, longitudMag);
        questionArrayList.add(q3);

        Question q4 = new Question(idcard, "Velocidad", veld, velocidadMag);
        questionArrayList.add(q4);

        Question q5 = new Question(idcard, "Poder", poder);
        questionArrayList.add(q5);
        return questionArrayList;
    }

    public void init(View view) {
        animScaleUp = AnimationUtils.loadAnimation(getContext(), R.anim.scale_up);
        animScaleDown = AnimationUtils.loadAnimation(getContext(), R.anim.scale_down);

        viewBackAdminAddCarta = view.findViewById(R.id.viewAddCard_Back);
        viewAddCarta2 = view.findViewById(R.id.viewAddCard_AddCard);
        tvAddCarta2 = view.findViewById(R.id.tvAddCard_AddCard);
        tvAlertaAddCarta = view.findViewById(R.id.tvAddCard_AlertaError);

        urlEt = view.findViewById(R.id.etAddCard_URL);
        nombreEt = view.findViewById(R.id.etAddCard_Nombre);
        descripcionEt = view.findViewById(R.id.etAddCard_Descripcion);
        alturaEt = view.findViewById(R.id.etAddCard_Altura);
        pesoEt = view.findViewById(R.id.etAddCard_Peso);
        velocidadEt = view.findViewById(R.id.etAddCard_Velocidad);
        longitudEt = view.findViewById(R.id.etAddCard_Longitud);
        poderEt = view.findViewById(R.id.etAddCard_Poder);

        alturaSp = view.findViewById(R.id.chkAddCard_Altura);
        pesoSp = view.findViewById(R.id.chkAddCard_Peso);
        velocidadSp = view.findViewById(R.id.chkAddCard_Velocidad);
        longitudSp = view.findViewById(R.id.chkAddCard_Longitud);
    }
}