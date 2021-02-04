package teambiscochito.toptrumpsgame.view.fragment.administrar;

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
import java.util.List;
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
 * Clase para editar la carta seleccionada en {@link AdminCartasFragment} donde tendremos todas las
 * propiedades de la carta que podemos editar a nuestro antojo.
 */
@SuppressWarnings({"Convert2Lambda"})
// Comente la línea de arriba para ver los posibles Lambdas a convertir
public class EditCardFragment extends Fragment {

    NavController navController;
    View viewBackAdminEditCarta, viewEditCarta2;
    TextView tvEditCarta2, tvAlertaEditCarta;
    Animation animScaleUp, animScaleDown;

    private EditText nombreEt, descripcionEt, urlEt, alturaEt, longitudEt, pesoEt, velocidadEt, poderEt;
    private Spinner alturaSp, velocidadSp, pesoSp, longitudSp;
    private Card card;
    private ViewModel viewModel;

    public EditCardFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_card, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init(view);
        NumberFormat nf = NumberFormat.getNumberInstance(Locale.US);
        DecimalFormat formatter = (DecimalFormat) nf;
        formatter.applyPattern("##.#");

        navController = Navigation.findNavController(view);

        viewModel = new ViewModelProvider(requireActivity()).get(ViewModel.class);

        card = viewModel.getCard();
        long id = card.getId();
        List<Question> questionList = viewModel.getQuestionListByCardId(id);

        getDatosCheckBox(questionList);

        getDatosEditText(questionList);

        DialogosGenerales.volverAtrasDialog(R.id.action_editCardFragment_to_adminCartasFragment,
                getContext(), view, viewBackAdminEditCarta);

        viewEditCarta(formatter, questionList);
    }

    /**
     * <h2 align="center">Team Biscochito</h2><hr>
     * <p>
     * Método de viewEditCarta donde meteremos los datos y al hacer clic en editar obtendremos los
     * valores y después pasara una validación para ver si los datos insertados están correctos o ya
     * existen en la base de datos por ejemplo.
     * <br><br>
     * Referencia del método en: {@link EditCardFragment#onViewCreated(View, Bundle)}
     *
     * @param formatter    aplicar el formato de los número en este caso con un decimal solamente.
     * @param questionList pasamos la lista de Question.
     */
    private void viewEditCarta(DecimalFormat formatter, List<Question> questionList) {
        viewEditCarta2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        viewEditCarta2.startAnimation(animScaleUp);
                        tvEditCarta2.startAnimation(animScaleUp);

                        obtenerDatosNuevos(formatter, questionList);
                        break;
                    case MotionEvent.ACTION_UP:
                        viewEditCarta2.startAnimation(animScaleDown);
                        tvEditCarta2.startAnimation(animScaleDown);
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
     * Método para obtener los nuevos datos de los edit text, donde posteriormente pasamos a su
     * validación para ver si están correcto los datos.
     * <br><br>
     * Referencia del método en: {@link #viewEditCarta(DecimalFormat, List)}
     *
     * @param formatter    aplicar el formato de los número en este caso con un decimal solamente.
     * @param questionList pasamos la lista de Question.
     */
    private void obtenerDatosNuevos(DecimalFormat formatter, List<Question> questionList) {
        String nombreNew = nombreEt.getText().toString();

        viewModel.getNameFromNameCarta(nombreNew);
        int num = viewModel.getRepeatedNameCarta();

        String descNew = descripcionEt.getText().toString();
        String urlNew = urlEt.getText().toString();

        double alturaNew = -1;
        double pesoNew = -1;
        double velocidadNew = -1;
        double longitudNew = -1;
        double poderNew = -1;

        String alturaMagNew = alturaSp.getSelectedItem().toString();
        String pesoMagNew = pesoSp.getSelectedItem().toString();
        String longitudMagNew = longitudSp.getSelectedItem().toString();
        String velocidadMagNew = velocidadSp.getSelectedItem().toString();

        try {
            alturaNew = Double.parseDouble(alturaEt.getText().toString());
            pesoNew = Double.parseDouble(pesoEt.getText().toString());
            poderNew = Double.parseDouble(poderEt.getText().toString());
            longitudNew = Double.parseDouble(longitudEt.getText().toString());
            velocidadNew = Double.parseDouble(velocidadEt.getText().toString());
        } catch (Exception ignored) {
        }

        validarDatos(nombreNew, num, descNew, urlNew, alturaNew, pesoNew, velocidadNew, longitudNew,
                poderNew, alturaMagNew, pesoMagNew, longitudMagNew, velocidadMagNew, formatter, questionList);
    }

    /**
     * <h2 align="center">Team Biscochito</h2><hr>
     * <p>
     * Método para obtener los nuevos datos de los edit text, donde posteriormente pasamos a su
     * validación para ver si están correcto los datos.
     * <br><br>
     * Referencia del método en: {@link #obtenerDatosNuevos(DecimalFormat, List)}
     *
     * @param nombreNew       pasamos el nuevo nombre.
     * @param num             numero identificativo de la carta, para que no se repitan.
     * @param descNew         nueva descripción.
     * @param urlNew          nueva URL.
     * @param alturaNew       nueva altura.
     * @param pesoNew         nuevo peso.
     * @param velocidadNew    nueva velocidad.
     * @param longitudNew     nueva longitud.
     * @param poderNew        nuevo poder.
     * @param alturaMagNew    nueva magnitud de altura.
     * @param pesoMagNew      nueva magnitud de peso.
     * @param longitudMagNew  nueva magnitud de longitud.
     * @param velocidadMagNew nueva magnitud de velocidad.
     * @param formatter       aplicar el formato de los número en este caso con un decimal solamente.
     * @param questionList    pasamos la lista de Question.
     */
    private void validarDatos(String nombreNew, int num, String descNew, String urlNew, double alturaNew,
                              double pesoNew, double velocidadNew, double longitudNew, double poderNew,
                              String alturaMagNew, String pesoMagNew, String longitudMagNew, String velocidadMagNew,
                              DecimalFormat formatter, List<Question> questionList) {

        if (nombreNew.isEmpty() || descNew.isEmpty() || urlNew.isEmpty() || alturaNew == -1
                || pesoNew == -1 || velocidadNew == -1 || longitudNew == -1 || poderNew == -1) {

            tvAlertaEditCarta.setText(R.string.alertAlgunCampoEstaVacio);

        } else if (ValidarDatos.validarNombreCarta(nombreNew)) {

            tvAlertaEditCarta.setText(R.string.alertNombreNoValido);
            nombreEt.setText("");

        } else if (ValidarDatos.validarDescCarta(descNew)) {

            tvAlertaEditCarta.setText(R.string.alertDescDemasiadoLarga);
            descripcionEt.setText("");

        } else if (ValidarDatos.validarDatosCarta(alturaNew)) {

            tvAlertaEditCarta.setText(R.string.alertAlturaNoValida);
            alturaEt.setText("");

        } else if (ValidarDatos.validarDatosCarta(pesoNew)) {

            tvAlertaEditCarta.setText(R.string.alertPesoNoValido);
            pesoEt.setText("");

        } else if (ValidarDatos.validarDatosCarta(longitudNew)) {

            tvAlertaEditCarta.setText(R.string.alertLongitudNoValida);
            longitudEt.setText("");

        } else if (ValidarDatos.validarDatosCarta(velocidadNew)) {

            tvAlertaEditCarta.setText(R.string.alertVelocidadNoValida);
            velocidadEt.setText("");

        } else if (ValidarDatos.validarPoderCarta(poderNew)) {

            tvAlertaEditCarta.setText(R.string.alertPoderNoValido);
            poderEt.setText("");

        } else if (nombreNew.equals(card.getName())) {

            existeCarta(descNew, urlNew, alturaNew, pesoNew, velocidadNew, longitudNew, poderNew,
                    alturaMagNew, pesoMagNew, longitudMagNew, velocidadMagNew, formatter, questionList);

        } else if (num != 0 && nombreNew.equals(card.getName())) {

            existeCarta(descNew, urlNew, alturaNew, pesoNew, velocidadNew, longitudNew, poderNew,
                    alturaMagNew, pesoMagNew, longitudMagNew, velocidadMagNew, formatter, questionList);

        } else if (num != 0 && !(nombreNew.equals(card.getName()))) {

            tvAlertaEditCarta.setText(R.string.tvNombreYaEnUso);

        } else {
            insertarNuevosDatos(nombreNew, descNew, urlNew, alturaNew, pesoNew, velocidadNew, longitudNew,
                    poderNew, alturaMagNew, pesoMagNew, longitudMagNew, velocidadMagNew, formatter, questionList);
        }
    }

    /**
     * <h2 align="center">Team Biscochito</h2><hr>
     * <p>
     * Método que se llama cuando los datos son válidos y correctos los modificamos.
     * <br><br>
     * Referencia del método en: {@link #validarDatos(String, int, String, String, double, double,
     * double, double, double, String, String, String, String, DecimalFormat, List)}
     *
     * @param nombreNew       pasamos el nuevo nombre.
     * @param descNew         nueva descripción.
     * @param urlNew          nueva URL.
     * @param alturaNew       nueva altura.
     * @param pesoNew         nuevo peso.
     * @param velocidadNew    nueva velocidad.
     * @param longitudNew     nueva longitud.
     * @param poderNew        nuevo poder.
     * @param alturaMagNew    nueva magnitud de altura.
     * @param pesoMagNew      nueva magnitud de peso.
     * @param longitudMagNew  nueva magnitud de longitud.
     * @param velocidadMagNew nueva magnitud de velocidad.
     * @param formatter       aplicar el formato de los número en este caso con un decimal solamente.
     * @param questionList    pasamos la lista de Question.
     */
    private void insertarNuevosDatos(String nombreNew, String descNew, String urlNew, double alturaNew,
                                     double pesoNew, double velocidadNew, double longitudNew, double poderNew,
                                     String alturaMagNew, String pesoMagNew, String longitudMagNew,
                                     String velocidadMagNew, DecimalFormat formatter, List<Question> questionList) {
        card.setDescription(descNew);
        card.setName(nombreNew);
        card.setPicUrl(urlNew);
        viewModel.updateCard(card);

        String alturaDouble = formatter.format(alturaNew);
        double altd = Double.parseDouble(alturaDouble);

        questionList.get(0).setAnswer(altd);
        questionList.get(0).setMagnitude(alturaMagNew);
        viewModel.updateQuestion(questionList.get(0));

        String pesoDouble = formatter.format(pesoNew);
        double pesd = Double.parseDouble(pesoDouble);

        questionList.get(1).setAnswer(pesd);
        questionList.get(1).setMagnitude(pesoMagNew);
        viewModel.updateQuestion(questionList.get(1));

        String longitudDouble = formatter.format(longitudNew);
        double lond = Double.parseDouble(longitudDouble);


        questionList.get(2).setAnswer(lond);
        questionList.get(2).setMagnitude(longitudMagNew);
        viewModel.updateQuestion(questionList.get(2));

        String velocidadDouble = formatter.format(velocidadNew);
        double veld = Double.parseDouble(velocidadDouble);

        questionList.get(3).setAnswer(veld);
        questionList.get(3).setMagnitude(velocidadMagNew);
        viewModel.updateQuestion(questionList.get(3));

        questionList.get(4).setAnswer(poderNew);
        viewModel.updateQuestion(questionList.get(4));

        tvAlertaEditCarta.setText("");
        navController.navigate(R.id.action_editCardFragment_to_adminCartasFragment);
    }

    /**
     * <h2 align="center">Team Biscochito</h2><hr>
     * <p>
     * Método que se llama para validar los datos obtenidos del formulario.
     * <br><br>
     * Referencia del método en: {@link #validarDatos(String, int, String, String, double, double,
     * double, double, double, String, String, String, String, DecimalFormat, List)}
     *
     * @param descNew         nueva descripción.
     * @param urlNew          nueva URL.
     * @param alturaNew       nueva altura.
     * @param pesoNew         nuevo peso.
     * @param velocidadNew    nueva velocidad.
     * @param longitudNew     nueva longitud.
     * @param poderNew        nuevo poder.
     * @param alturaMagNew    nueva magnitud de altura.
     * @param pesoMagNew      nueva magnitud de peso.
     * @param longitudMagNew  nueva magnitud de longitud.
     * @param velocidadMagNew nueva magnitud de velocidad.
     * @param formatter       aplicar el formato de los número en este caso con un decimal solamente.
     * @param questionList    pasamos la lista de Question.
     */
    private void existeCarta(String descNew, String urlNew, double alturaNew, double pesoNew,
                             double velocidadNew, double longitudNew, double poderNew, String alturaMagNew,
                             String pesoMagNew, String longitudMagNew, String velocidadMagNew,
                             DecimalFormat formatter, List<Question> questionList) {
        card.setDescription(descNew);
        card.setPicUrl(urlNew);
        viewModel.updateCard(card);

        String alturaDouble = formatter.format(alturaNew);
        double altd = Double.parseDouble(alturaDouble);

        questionList.get(0).setAnswer(altd);
        questionList.get(0).setMagnitude(alturaMagNew);
        viewModel.updateQuestion(questionList.get(0));

        String pesoDouble = formatter.format(pesoNew);
        double pesd = Double.parseDouble(pesoDouble);

        questionList.get(1).setAnswer(pesd);
        questionList.get(1).setMagnitude(pesoMagNew);
        viewModel.updateQuestion(questionList.get(1));

        String longitudDouble = formatter.format(longitudNew);
        double lond = Double.parseDouble(longitudDouble);

        questionList.get(2).setAnswer(lond);
        questionList.get(2).setMagnitude(longitudMagNew);
        viewModel.updateQuestion(questionList.get(2));

        String velocidadDouble = formatter.format(velocidadNew);
        double veld = Double.parseDouble(velocidadDouble);

        questionList.get(3).setAnswer(veld);
        questionList.get(3).setMagnitude(velocidadMagNew);
        viewModel.updateQuestion(questionList.get(3));

        questionList.get(4).setAnswer(poderNew);
        viewModel.updateQuestion(questionList.get(4));

        tvAlertaEditCarta.setText("");
        navController.navigate(R.id.action_editCardFragment_to_adminCartasFragment);
    }

    /**
     * <h2 align="center">Team Biscochito</h2><hr>
     * <p>
     * Método que se llama al inicio para colocar los datos iniciales (solo EditText) que tiene la
     * carta. Así podremos saber que datos tiene la carta como la URL y todas las propiedades para
     * después poder modificarlas a nuestro antojo.
     * <br><br>
     * Referencia del método en: {@link EditCardFragment#onViewCreated(View, Bundle)}
     *
     * @param questionList pasamos la lista de Question.
     */
    private void getDatosEditText(List<Question> questionList) {
        double valorPoderDouble = Double.parseDouble(questionList.get(4).getAnswer().toString());
        int valorPoderInt = (int) valorPoderDouble;

        nombreEt.setText(card.getName());
        descripcionEt.setText(card.getDescription());
        urlEt.setText(card.getPicUrl());

        alturaEt.setText(String.valueOf(questionList.get(0).getAnswer()));
        pesoEt.setText(String.valueOf(questionList.get(1).getAnswer()));
        longitudEt.setText(String.valueOf(questionList.get(2).getAnswer()));
        velocidadEt.setText(String.valueOf(questionList.get(3).getAnswer()));
        poderEt.setText(String.valueOf(valorPoderInt));
    }

    /**
     * <h2 align="center">Team Biscochito</h2><hr>
     * <p>
     * Método que se llama al inicio para colocar los datos iniciales (solo CheckBox) que tiene la
     * carta. Así podremos modificar las magnitudes a nuestro antojo.
     * <br><br>
     * Referencia del método en: {@link #onViewCreated(View, Bundle)}
     *
     * @param questionList pasamos la lista de Question.
     */
    private void getDatosCheckBox(List<Question> questionList) {
        ArrayAdapter<CharSequence> alturaAdapter = ArrayAdapter.createFromResource(getContext(), R.array.magnitudesAltura, R.layout.style_spinner);
        alturaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        alturaSp.setAdapter(alturaAdapter);
        alturaSp.setSelection(alturaAdapter.getPosition(questionList.get(0).getMagnitude()));

        ArrayAdapter<CharSequence> pesoAdapter = ArrayAdapter.createFromResource(getContext(), R.array.magnitudesPeso, R.layout.style_spinner);
        pesoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        pesoSp.setAdapter(pesoAdapter);
        pesoSp.setSelection(pesoAdapter.getPosition(questionList.get(1).getMagnitude()));

        ArrayAdapter<CharSequence> velocidadAdapter = ArrayAdapter.createFromResource(getContext(), R.array.magnitudesVelocidad, R.layout.style_spinner);
        velocidadAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        velocidadSp.setAdapter(velocidadAdapter);
        velocidadSp.setSelection(velocidadAdapter.getPosition(questionList.get(3).getMagnitude()));

        ArrayAdapter<CharSequence> longitudAdapter = ArrayAdapter.createFromResource(getContext(), R.array.magnitudesLongitud, R.layout.style_spinner);
        longitudAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        longitudSp.setAdapter(longitudAdapter);
        longitudSp.setSelection(longitudAdapter.getPosition(questionList.get(2).getMagnitude()));
    }

    public void init(View view) {
        animScaleUp = AnimationUtils.loadAnimation(getContext(), R.anim.scale_up);
        animScaleDown = AnimationUtils.loadAnimation(getContext(), R.anim.scale_down);

        viewBackAdminEditCarta = view.findViewById(R.id.viewEditCard_Back);
        viewEditCarta2 = view.findViewById(R.id.viewEditCard_Editar);
        tvEditCarta2 = view.findViewById(R.id.tvEditCard_Editar);
        tvAlertaEditCarta = view.findViewById(R.id.tvEditCard_Alerta);

        alturaSp = view.findViewById(R.id.chkEditCard_Altura);
        pesoSp = view.findViewById(R.id.chkEditCard_Peso);
        velocidadSp = view.findViewById(R.id.chkEditCard_Velocidad);
        longitudSp = view.findViewById(R.id.chkEditCard_Longitud);

        nombreEt = view.findViewById(R.id.etEditCard_Nombre);
        descripcionEt = view.findViewById(R.id.etEditCard_Descripcion);
        urlEt = view.findViewById(R.id.etEditCard_URL);
        alturaEt = view.findViewById(R.id.etEditCard_Altura);
        longitudEt = view.findViewById(R.id.etEditCard_Longitud);
        pesoEt = view.findViewById(R.id.etEditCard_Peso);
        velocidadEt = view.findViewById(R.id.etEditCard_Velocidad);
        poderEt = view.findViewById(R.id.etEditCard_Poder);
    }
}