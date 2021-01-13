package teambiscochito.toptrumpsgame.view.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import teambiscochito.toptrumpsgame.R;
import teambiscochito.toptrumpsgame.model.room.pojo.User;
import teambiscochito.toptrumpsgame.viewmodel.ViewModel;

public class AddUserFragment extends Fragment {
    private ViewModel viewModel;
    private EditText usuarioEt;
    private Button añadirBt;
    private ImageView avatarIv;
    //Posible Recycler con imagenes para escoger el avatar. Por defecto vendrá seleccionada 1.
    public AddUserFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_user, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(getActivity()).get(ViewModel.class);

        //añadirBt = view.findViewById(R.id.);
        //usuarioEt = view.findViewById(R.id.);
        //avatarIv = view.findViewById(R.id.)

        añadirBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    String name = usuarioEt.getText().toString();
                    int avatar = 0 ;
                    //avatar = avatarIv.getTransitionName();
                    User user = new User(name, avatar);
                    viewModel.insertUser(user);
                } catch (Exception e){
                    Log.v("xyz", "Error en insertar usuario: "+ e.getMessage());
                }
            }
        });

    }
}