package teambiscochito.toptrumpsgame.view.activity;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import teambiscochito.toptrumpsgame.R;
import teambiscochito.toptrumpsgame.receiver.ReceiverBateria;

/**
 * <h2 align="center">Team Biscochito</h2><hr>
 * <p>
 * Clase principal {@link MainActivity}) donde empieza el juego y se ponen algunas propiedades básicas
 * para el correcto funcionamiento. Registramos el receiver, habilitamos pantalla completa y animamos
 * el diálogo para salir de la aplicación entre otras.
 */
@SuppressWarnings({"deprecation", "Convert2Lambda"})
// Comente la línea de arriba para ver los métodos deprecated y para ver los posibles Lambdas a convertir
public class MainActivity extends AppCompatActivity {
    ReceiverBateria receiver;
    IntentFilter intentFilter;
    Dialog salirDialog;
    private long backPressedTime;
    private View decorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        receiver = new ReceiverBateria();
        intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_BATTERY_LOW);

        /* Establecer sistema de navegación con un color predefinido */
        getWindow().setNavigationBarColor(Color.parseColor("#4B2C20"));

        decorView = getWindow().getDecorView();

        decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                if (visibility == 0) {
                    decorView.setSystemUiVisibility(hideBars());
                }
            }
        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            decorView.setSystemUiVisibility(hideBars());
        }
    }

    private int hideBars() {
        return View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                View.SYSTEM_UI_FLAG_FULLSCREEN |
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(receiver, intentFilter);

        View decorView = getWindow().getDecorView();

        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(receiver);
    }

    @Override
    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            salirDialog();
            return;
        } else {
            Toast.makeText(getApplicationContext(), "Pulsa otra vez para salir", Toast.LENGTH_SHORT).show();
        }
        backPressedTime = System.currentTimeMillis();
    }

    /**
     * Cuadro de diálogo para salir de la aplicación
     */
    public void salirDialog() {
        View viewCancelarSalirDialog, viewAceptarSalirDialog;

        salirDialog = new Dialog(MainActivity.this);
        salirDialog.setContentView(R.layout.salir_app_dialog);
        salirDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        Window window = salirDialog.getWindow();
        window.setGravity(Gravity.CENTER);
        window.getAttributes().windowAnimations = R.style.DialogAnimation;

        viewCancelarSalirDialog = salirDialog.findViewById(R.id.viewSalirDialogApp_Cancel);
        viewAceptarSalirDialog = salirDialog.findViewById(R.id.viewSalirDialogApp_Accept);

        viewCancelarSalirDialog.setOnClickListener(v -> salirDialog.dismiss());

        viewAceptarSalirDialog.setOnClickListener(v -> {
            finish();
            System.exit(0);
        });

        salirDialog.setCancelable(true);
        salirDialog.setCanceledOnTouchOutside(false);
        window.setLayout(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        salirDialog.show();
    }
}