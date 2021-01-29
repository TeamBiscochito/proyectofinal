package teambiscochito.toptrumpsgame.view.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import teambiscochito.toptrumpsgame.R;
import teambiscochito.toptrumpsgame.model.room.pojo.Card;
import teambiscochito.toptrumpsgame.model.room.pojo.Question;
import teambiscochito.toptrumpsgame.viewmodel.ViewModel;

public class AddCardFragment extends Fragment {

    NavController navController;
    View viewBackAdminAddCarta, viewAddCarta2;
    TextView tvAddCarta2, tvAlertaAddCarta;
    Animation animScaleUp, animScaleDown;

    ViewModel viewModel;
    Button btAddCard;
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
        viewModel = new ViewModelProvider(getActivity()).get(ViewModel.class);
        urlEt = view.findViewById(R.id.urlEt);
        nombreEt = view.findViewById(R.id.nombreEtAddCard);
        descripcionEt = view.findViewById(R.id.descriptionEtAddCard);
        alturaEt = view.findViewById(R.id.alturaEtAddCard);
        pesoEt = view.findViewById(R.id.pesoEtAddCard);
        velocidadEt = view.findViewById(R.id.velocidadEtAddCard);
        longitudEt = view.findViewById(R.id.longitudEtAddCard);
        poderEt = view.findViewById(R.id.poderEtAddCard);

        alturaSp = view.findViewById(R.id.alturasp);
        ArrayAdapter<CharSequence> alturaAdapter = ArrayAdapter.createFromResource(getContext(), R.array.magnitudesAltura, android.R.layout.simple_spinner_item);
        alturaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        alturaSp.setAdapter(alturaAdapter);

        pesoSp = view.findViewById(R.id.pesosp);
        ArrayAdapter<CharSequence> pesoAdapter = ArrayAdapter.createFromResource(getContext(), R.array.magnitudesPeso, android.R.layout.simple_spinner_item);
        alturaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        pesoSp.setAdapter(pesoAdapter);

        velocidadSp = view.findViewById(R.id.velocidadsp);
        ArrayAdapter<CharSequence> velocidadAdapter = ArrayAdapter.createFromResource(getContext(), R.array.magnitudesVelocidad, android.R.layout.simple_spinner_item);
        alturaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        velocidadSp.setAdapter(velocidadAdapter);

        longitudSp = view.findViewById(R.id.longitudsp);
        ArrayAdapter<CharSequence> longitudAdapter = ArrayAdapter.createFromResource(getContext(), R.array.magnitudesLongitud, android.R.layout.simple_spinner_item);
        alturaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        longitudSp.setAdapter(longitudAdapter);


        btAddCard = view.findViewById(R.id.btAddCard);
        btAddCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = nombreEt.getText().toString();
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
                try{
                    altura = Double.parseDouble(alturaEt.getText().toString());
                    peso = Double.parseDouble(pesoEt.getText().toString());
                    velocidad = Double.parseDouble(velocidadEt.getText().toString());
                    longitud = Double.parseDouble(longitudEt.getText().toString());
                    poder = Double.parseDouble(poderEt.getText().toString());
                }catch(Exception ex){
                    Log.v("xyz addcard", ex.getLocalizedMessage());
                }

                if(nombre.isEmpty()){
                    Toast.makeText(getContext(),"El nombre no puede estar vacio", Toast.LENGTH_LONG);
                }else if(altura ==-1 || peso == -1 || velocidad == -1 ||longitud == -1 || poder == -1){
                    Toast.makeText(getContext(),"Debes rellenar todas las caracteristicas", Toast.LENGTH_LONG);
                }
                // - Poder entre 1 y 10
                else if(poder > 10 || poder < 1 ){
                    Toast.makeText(getContext(),"El poder debe estar entre 1 y 10", Toast.LENGTH_LONG);
                    Log.v("xyz", "El poder debe estar entre 1 y 10");
                }

        init(view);

        navController = Navigation.findNavController(view);

        viewBackAdminAddCarta.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    viewBackAdminAddCarta.startAnimation(animScaleUp);

                    navController.navigate(R.id.action_addCardFragment_to_adminCartasFragment);

                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    viewBackAdminAddCarta.startAnimation(animScaleDown);
                }

                return true;
            }
        });

        viewAddCarta2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    viewAddCarta2.startAnimation(animScaleUp);
                    tvAddCarta2.startAnimation(animScaleUp);

                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    viewAddCarta2.startAnimation(animScaleDown);
                    tvAddCarta2.startAnimation(animScaleDown);
                }

                return true;
            }
        });

    }

    public void init(View view) {

        animScaleUp = AnimationUtils.loadAnimation(getContext(), R.anim.scale_up);
        animScaleDown = AnimationUtils.loadAnimation(getContext(), R.anim.scale_down);

        viewBackAdminAddCarta = view.findViewById(R.id.viewBackAdminAddCarta);
        viewAddCarta2 = view.findViewById(R.id.viewAddCarta2);
        tvAddCarta2 = view.findViewById(R.id.tvAddCarta2);
        tvAlertaAddCarta = view.findViewById(R.id.tvAlertaAddCarta);

                // ESPACIO PARA AÑADIR CUALQUIER OTRO POSIBLE PROBLEMA
                // -
                else{
                    Card card = new Card(url, nombre, descripcion );
                    Log.v("xyzpre", card.getId()+"");
                    viewModel.insertCard(card);
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Log.v("xyzpost", card.getId()+"");
                    long idcard = viewModel.getIdByName(nombre);
                    if(idcard == 0){
                        Log.v("xyz", "EntroTest");
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    Log.v("xyz", "FueraTest: "+idcard);
                    ArrayList<Question> questionArrayList = new ArrayList<>();
                    Question q1 = new Question(idcard, "Altura", altura, alturaMag);
                    questionArrayList.add(q1);
                    Question q2 = new Question(idcard, "Peso", peso, pesoMag);
                    questionArrayList.add(q2);
                    Question q3 = new Question(idcard, "Longitud", longitud, longitudMag);
                    questionArrayList.add(q3);
                    Question q4 = new Question(idcard, "Velocidad", velocidad, velocidadMag);
                    questionArrayList.add(q4);
                    Question q5 = new Question(idcard, "Poder", poder, "");
                    questionArrayList.add(q5);
                    viewModel.insertAll(questionArrayList);


                }
            }
        });





    }
}
