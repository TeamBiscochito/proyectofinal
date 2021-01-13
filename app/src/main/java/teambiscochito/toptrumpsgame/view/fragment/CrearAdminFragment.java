package teambiscochito.toptrumpsgame.view.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import teambiscochito.toptrumpsgame.R;

public class CrearAdminFragment extends Fragment {
    SharedPreferences sharedPreferences;
    EditText etClave;
    public CrearAdminFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_crear_admin, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        Button btMenuParaProbar = view.findViewById(R.id.btMenuParaProbar);
        Button btSeleccionarJugadorParaProbar = view.findViewById(R.id.btSeleccionarParaProbar);

        final NavController navController = Navigation.findNavController(view);

        // -----------------------------------------------------------------------------------------
        etClave = view.findViewById(R.id.claveEt);
        sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        String random = cadenaAleatoria();
        String claveAdmin = sharedPreferences.getString("clave_admin", random);
        if (claveAdmin != random){
            Log.v("XYZ", claveAdmin);
            navController.navigate(R.id.action_prueba);
        }
        // -----------------------------------------------------------------------------------------
        btMenuParaProbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // -----------------------------------------------------------------------------------------
                try {
                    guardarClave();
                    String clave = etClave.getText().toString();
                    if(clave.isEmpty()){
                        Toast.makeText(getContext(),"La clave no puede estar vacia", Toast.LENGTH_LONG ).show();
                        return;
                    }
                    SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString("clave_admin", clave);
                    editor.commit();
                }catch (Exception e){
                    Log.v("xyz", e.getMessage() );
                }

                // -----------------------------------------------------------------------------------------
                navController.navigate(R.id.action_prueba);

            }
        });

        btSeleccionarJugadorParaProbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_crearAdminFragment_to_addUserFragment);
            }
        });
    }

    private void guardarClave() {

    }

    public String cadenaAleatoria(){
        String lista = "qwertyuioplkjhgfdsazxcvbnmQWERTYUIOPLKJHGFDSAZXCVBNM1234567890";
        String s="";
        for(int i = 0; i < 20; i++){
            s+= lista.charAt((int) (Math.random() *61)+1);
        }
        return s;
    }

}