package teambiscochito.toptrumpsgame.view.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import teambiscochito.toptrumpsgame.R;
import teambiscochito.toptrumpsgame.model.room.pojo.Card;
import teambiscochito.toptrumpsgame.model.room.pojo.Question;
import teambiscochito.toptrumpsgame.validar.ValidarDatos;
import teambiscochito.toptrumpsgame.viewmodel.ViewModel;

public class EditCardFragment extends Fragment {

    NavController navController;
    View viewBackAdminEditCarta, viewEditCarta2;
    TextView tvEditCarta2, tvAlertaEditCarta;
    Animation animScaleUp, animScaleDown;

    private EditText nombreEt, descripcionEt, urlEt, alturaEt, longitudEt, pesoEt, velocidadEt, poderEt;
    private Spinner alturaSp, velocidadSp, pesoSp, longitudSp;
    private Card card;
    private ViewModel viewModel;
    DecimalFormat formatter;

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

        viewModel = new ViewModelProvider(getActivity()).get(ViewModel.class);

        card = viewModel.getCard();
        long id = card.getId();
        List<Question> questionList = viewModel.getQuestionListByCardId(id);

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

        double valorPoderDouble = Double.parseDouble(questionList.get(4).getAnswer().toString());
        int valorPoderInt = (int) valorPoderDouble;

        nombreEt.setText(card.getName());
        descripcionEt.setText(card.getDescription());
        urlEt.setText(card.getPicUrl());
        alturaEt.setText("" + questionList.get(0).getAnswer());
        pesoEt.setText("" + questionList.get(1).getAnswer());
        longitudEt.setText("" + questionList.get(2).getAnswer());
        velocidadEt.setText("" + questionList.get(3).getAnswer());
        poderEt.setText("" + valorPoderInt);

        viewBackAdminEditCarta.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    viewBackAdminEditCarta.startAnimation(animScaleUp);

                    navController.navigate(R.id.action_editCardFragment_to_adminCartasFragment);

                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    viewBackAdminEditCarta.startAnimation(animScaleDown);
                }

                return true;
            }
        });

        viewEditCarta2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    viewEditCarta2.startAnimation(animScaleUp);
                    tvEditCarta2.startAnimation(animScaleUp);

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

                    } catch (Exception ex) {

                    }

                    if (nombreNew.isEmpty() || descNew.isEmpty() || urlNew.isEmpty() || alturaNew == -1 || pesoNew == -1 || velocidadNew == -1 || longitudNew == -1 || poderNew == -1) {

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

                        card.setDescription(descNew);
                        card.setPicUrl(urlNew);
                        viewModel.updateCard(card);



                        String alturaDouble =formatter.format(alturaNew);
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

                    } else if (num != 0 && nombreNew.equals(card.getName())) {

                        card.setDescription(descNew);
                        card.setPicUrl(urlNew);
                        viewModel.updateCard(card);

                        String alturaDouble =formatter.format(alturaNew);
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

                    } else if (num != 0 && !(nombreNew.equals(card.getName()))) {

                        tvAlertaEditCarta.setText(R.string.tvNombreYaEnUso);

                    } else {

                        card.setDescription(descNew);
                        card.setName(nombreNew);
                        card.setPicUrl(urlNew);
                        viewModel.updateCard(card);

                        String alturaDouble =formatter.format(alturaNew);
                        double altd = Double.parseDouble(alturaDouble);

                        questionList.get(0).setAnswer(altd);
                        questionList.get(0).setMagnitude(alturaMagNew);
                        viewModel.updateQuestion(questionList.get(0));

                        String pesoDouble = formatter.format(pesoNew);
                        double pesd = Double.parseDouble(pesoDouble);

                        questionList.get(1).setAnswer(pesd);
                        questionList.get(1).setMagnitude(pesoMagNew);
                        viewModel.updateQuestion(questionList.get(1));

                        String longitudDouble =formatter.format(longitudNew);
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

                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    viewEditCarta2.startAnimation(animScaleDown);
                    tvEditCarta2.startAnimation(animScaleDown);
                }

                return true;
            }

        });

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