package teambiscochito.toptrumpsgame.view.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import teambiscochito.toptrumpsgame.R;

public class AddCardFragment extends Fragment {

    NavController navController;
    View viewBackAdminAddCarta, viewAddCarta2;
    TextView tvAddCarta2, tvAlertaAddCarta;
    Animation animScaleUp, animScaleDown;

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

    }
}