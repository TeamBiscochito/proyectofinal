package teambiscochito.toptrumpsgame.view.fragment;

import android.app.SearchManager;
import android.content.Intent;
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
import java.util.ArrayList;
import java.util.Locale;

import teambiscochito.toptrumpsgame.R;
import teambiscochito.toptrumpsgame.model.room.pojo.Card;
import teambiscochito.toptrumpsgame.model.room.pojo.Question;
import teambiscochito.toptrumpsgame.validar.ValidarDatos;
import teambiscochito.toptrumpsgame.viewmodel.ViewModel;

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

        viewModel = new ViewModelProvider(getActivity()).get(ViewModel.class);

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

        viewBackAdminAddCarta.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    viewBackAdminAddCarta.startAnimation(animScaleUp);

                    navController.navigate(R.id.action_addCardFragment_to_adminCartasFragment);

                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    viewBackAdminAddCarta.startAnimation(animScaleDown);
                }

                return true;
            }
        });

        view.findViewById(R.id.btBuscarEnInternet).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String busqueda = "Imágenes de animales";

                if (!(nombreEt.getText().toString().compareToIgnoreCase("") == 0)){
                    busqueda = "Imágenes de " + nombreEt.getText().toString();
                }

                Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
                intent.putExtra(SearchManager.QUERY, busqueda);
                if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });


        viewAddCarta2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    viewAddCarta2.startAnimation(animScaleUp);
                    tvAddCarta2.startAnimation(animScaleUp);

                    String nombre = nombreEt.getText().toString();

                    viewModel.getNameFromNameCarta(nombre);
                    int num = viewModel.getRepeatedNameCarta();

                    String descripcion = descripcionEt.getText().toString();
                    String alturaMag = alturaSp.getSelectedItem().toString();
                    String pesoMag = pesoSp.getSelectedItem().toString();
                    String longitudMag = longitudSp.getSelectedItem().toString();
                    String velocidadMag = velocidadSp.getSelectedItem().toString();
                    String url = urlEt.getText().toString();

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

                        String pesoDouble =  formatter.format(peso);
                        pesd = Double.parseDouble(pesoDouble);

                        String velocidadDouble =  formatter.format(velocidad);
                        veld = Double.parseDouble(velocidadDouble);

                        String longitudDouble = formatter.format(longitud);
                        lond = Double.parseDouble(longitudDouble);



                    } catch (Exception ex) {

                    }

                    if (nombre.isEmpty() || descripcion.isEmpty() || url.isEmpty() || altura == -1 || peso == -1 || velocidad == -1 || longitud == -1 || poder == -1) {

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

                        } catch (InterruptedException e) {

                        }

                        long idcard = viewModel.getIdByName(nombre);

                        if (idcard == 0) {

                            try {

                                Thread.sleep(500);

                            } catch (InterruptedException e) {

                            }

                        }

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

                        viewModel.insertAll(questionArrayList);

                        tvAlertaAddCarta.setText("");
                        navController.navigate(R.id.action_addCardFragment_to_adminCartasFragment);

                    }

                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    viewAddCarta2.startAnimation(animScaleDown);
                    tvAddCarta2.startAnimation(animScaleDown);

                }

                return true;
            }

        });

    }

    public void init (View view){

        animScaleUp = AnimationUtils.loadAnimation(getContext(), R.anim.scale_up);
        animScaleDown = AnimationUtils.loadAnimation(getContext(), R.anim.scale_down);

        viewBackAdminAddCarta = view.findViewById(R.id.viewBackAdminAddCarta);
        viewAddCarta2 = view.findViewById(R.id.viewAddCarta2);
        tvAddCarta2 = view.findViewById(R.id.tvAddCarta2);
        tvAlertaAddCarta = view.findViewById(R.id.tvAlertaAddCarta);

        urlEt = view.findViewById(R.id.urlEt);
        nombreEt = view.findViewById(R.id.nombreEtAddCard);
        descripcionEt = view.findViewById(R.id.descriptionEtAddCard);
        alturaEt = view.findViewById(R.id.alturaEtAddCard);
        pesoEt = view.findViewById(R.id.pesoEtAddCard);
        velocidadEt = view.findViewById(R.id.velocidadEtAddCard);
        longitudEt = view.findViewById(R.id.longitudEtAddCard);
        poderEt = view.findViewById(R.id.poderEtAddCard);

        alturaSp = view.findViewById(R.id.alturasp);
        pesoSp = view.findViewById(R.id.pesosp);
        velocidadSp = view.findViewById(R.id.velocidadsp);
        longitudSp = view.findViewById(R.id.longitudsp);

    }



}