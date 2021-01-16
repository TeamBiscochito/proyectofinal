package teambiscochito.toptrumpsgame.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import teambiscochito.toptrumpsgame.R;

public class ReceiverBateria extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Integer nivelBateria = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);

        LayoutInflater inflater = LayoutInflater.from(context);

        View layout = inflater.inflate(R.layout.toast_bateria, null);
        final Toast toast = new Toast(context);

        toast.setGravity(Gravity.CENTER_VERTICAL,0,0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);

        // Lanza el aviso cuando el nivel de batería es igual o menor al 15%
        if(nivelBateria <= 15) {

            toast.show();

        }

    }
}