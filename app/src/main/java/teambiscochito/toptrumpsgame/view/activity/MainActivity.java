package teambiscochito.toptrumpsgame.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import com.google.android.material.navigation.NavigationView;

import teambiscochito.toptrumpsgame.MyReceiver;
import teambiscochito.toptrumpsgame.R;

public class MainActivity extends AppCompatActivity {

    MyReceiver receiver;
    IntentFilter intentFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        receiver = new MyReceiver();
        intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_BATTERY_LOW);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(receiver,intentFilter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(receiver);
    }

    public void inicioDrawer() {
        // TODO: Drawer en caso de que lo queramos
//        DrawerLayout drawerLayout = findViewById(R.id.drawerLayout);
//        toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        ctlLayout = findViewById(R.id.ctlLayout);
//        appBarLayout = findViewById(R.id.appBarLayout);
//
//        NavigationView navigationView = findViewById(R.id.navigationViewMain);
//
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//
//        AppBarConfiguration appBarConfiguration =
//                new AppBarConfiguration.Builder(navController.getGraph()).setOpenableLayout(drawerLayout).build();
//
//        NavigationUI.setupWithNavController(toolbar, navController, appBarConfiguration);
//
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
//        NavigationUI.setupWithNavController(navigationView, navController);
//
//        toolbarCollab(navController);
    }
}