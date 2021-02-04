package teambiscochito.toptrumpsgame.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import teambiscochito.toptrumpsgame.R;

/**
 * <h2 align="center">Team Biscochito</h2><hr>
 * <p>
 * Clase para hacer un BroadcastReceiver de la batería y que salte un Toast personalizado cuando
 * la batería esté por debajo del 15% (en este caso es una variable llamada ACTION_BATTERY_LOW,
 * que hace referencia a Android cuando llega al 15%).
 */
@SuppressWarnings({"deprecation", "InflateParams"})
// Comente esta la línea de arriba para ver los métodos deprecated y parámetro inflate (null)
public class ReceiverBateria extends BroadcastReceiver {

    /**
     * @param context contexto de la app
     * @param intent  evento del broadcast
     */
    @Override
    public void onReceive(Context context, Intent intent) {

        if (Intent.ACTION_BATTERY_LOW.equals(intent.getAction())) {

            LayoutInflater inflater = LayoutInflater.from(context);

            View layout = inflater.inflate(R.layout.toast_bateria, null);
            final Toast toast = new Toast(context);

            toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
            toast.setDuration(Toast.LENGTH_LONG);
            toast.setView(layout);
            toast.show();
        }
    }
}