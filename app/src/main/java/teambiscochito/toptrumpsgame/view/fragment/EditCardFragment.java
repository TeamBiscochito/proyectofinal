package teambiscochito.toptrumpsgame.view.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.List;

import teambiscochito.toptrumpsgame.R;
import teambiscochito.toptrumpsgame.model.room.pojo.Card;
import teambiscochito.toptrumpsgame.model.room.pojo.Question;
import teambiscochito.toptrumpsgame.viewmodel.ViewModel;

public class EditCardFragment extends Fragment {

    private EditText nombreEt, descripcionEt, urlEt, alturaEt, longitudEt, pesoEt, velocidadEt, poderEt;
    private Spinner alturaSp, velocidadSp, pesoSp, longitudSp;
    private Button actualizarBt;
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
        viewModel = new ViewModelProvider(getActivity()).get(ViewModel.class);
        card = viewModel.getCard();
        long id = card.getId();
        List<Question> questionList = viewModel.getQuestionListByCardId(id);

        alturaSp = view.findViewById(R.id.alturaspEdit);
        ArrayAdapter<CharSequence> alturaAdapter = ArrayAdapter.createFromResource(getContext(), R.array.magnitudesAltura, android.R.layout.simple_spinner_item);
        alturaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        alturaSp.setAdapter(alturaAdapter);
        alturaSp.setSelection(alturaAdapter.getPosition(questionList.get(0).getMagnitude()));

        pesoSp = view.findViewById(R.id.pesospEdit);
        ArrayAdapter<CharSequence> pesoAdapter = ArrayAdapter.createFromResource(getContext(), R.array.magnitudesPeso, android.R.layout.simple_spinner_item);
        pesoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        pesoSp.setAdapter(pesoAdapter);
        pesoSp.setSelection(pesoAdapter.getPosition(questionList.get(1).getMagnitude()));

        velocidadSp = view.findViewById(R.id.velocidadspEdit);
        ArrayAdapter<CharSequence> velocidadAdapter = ArrayAdapter.createFromResource(getContext(), R.array.magnitudesVelocidad, android.R.layout.simple_spinner_item);
        velocidadAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        velocidadSp.setAdapter(velocidadAdapter);
        velocidadSp.setSelection(velocidadAdapter.getPosition(questionList.get(3).getMagnitude()));

        longitudSp = view.findViewById(R.id.longitudspEdit);
        ArrayAdapter<CharSequence> longitudAdapter = ArrayAdapter.createFromResource(getContext(), R.array.magnitudesLongitud, android.R.layout.simple_spinner_item);
        longitudAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        longitudSp.setAdapter(longitudAdapter);
        longitudSp.setSelection(longitudAdapter.getPosition(questionList.get(2).getMagnitude()));


        actualizarBt = view.findViewById(R.id.bolberbt);

        nombreEt = view.findViewById(R.id.nombreEtEditCard);
        descripcionEt = view.findViewById(R.id.descriptionEtEditCard);
        urlEt = view.findViewById(R.id.urlEtEdit);
        alturaEt = view.findViewById(R.id.alturaEtEditCard);
        longitudEt = view.findViewById(R.id.longitudEtEditCard);
        pesoEt = view.findViewById(R.id.pesoEtEditCard);
        velocidadEt = view.findViewById(R.id.velocidadEtEditCard);
        poderEt = view.findViewById(R.id.poderEtEditCard);

        nombreEt.setText(card.getName());
        descripcionEt.setText(card.getDescription());
        urlEt.setText(card.getPicUrl());
        alturaEt.setText(""+questionList.get(0).getAnswer());
        pesoEt.setText(""+questionList.get(1).getAnswer());
        longitudEt.setText(""+questionList.get(2).getAnswer());
        velocidadEt.setText(""+questionList.get(3).getAnswer());
        poderEt.setText(""+questionList.get(4).getAnswer());





        actualizarBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombreNew = nombreEt.getText().toString();
                String descNew = descripcionEt.getText().toString();
                String urlNew = urlEt.getText().toString();
                double alturaNew = Double.parseDouble(alturaEt.getText().toString());
                double pesoNew = Double.parseDouble(pesoEt.getText().toString());
                double poderNew = Double.parseDouble(poderEt.getText().toString());
                double longitudNew = Double.parseDouble(longitudEt.getText().toString());
                double velocidadNew = Double.parseDouble(velocidadEt.getText().toString());
                String alturaMagNew = alturaSp.getSelectedItem().toString();
                String pesoMagNew = pesoSp.getSelectedItem().toString();
                String longitudMagNew = longitudSp.getSelectedItem().toString();
                String velocidadMagNew = velocidadSp.getSelectedItem().toString();

                card.setDescription(descNew);
                card.setName(nombreNew);
                card.setPicUrl(urlNew);
                viewModel.updateCard(card);

                questionList.get(0).setAnswer(alturaNew);
                questionList.get(0).setMagnitude(alturaMagNew);
                viewModel.updateQuestion(questionList.get(0));

                questionList.get(1).setAnswer(pesoNew);
                questionList.get(1).setMagnitude(pesoMagNew);
                viewModel.updateQuestion(questionList.get(1));

                questionList.get(2).setAnswer(longitudNew);
                questionList.get(2).setMagnitude(longitudMagNew);
                viewModel.updateQuestion(questionList.get(2));

                questionList.get(3).setAnswer(velocidadNew);
                questionList.get(3).setMagnitude(velocidadMagNew);
                viewModel.updateQuestion(questionList.get(3));

                questionList.get(4).setAnswer(poderNew);
                viewModel.updateQuestion(questionList.get(4));
            }
            });
        }
    }
