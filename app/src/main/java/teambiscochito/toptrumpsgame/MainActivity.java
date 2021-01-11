package teambiscochito.toptrumpsgame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("Hola", "Soy Oleg");
        Log.d("Hola", "Soy Manu");
        Log.d("Tag", "Buenas soy Gabri");
    }
}