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

public class EditCardFragment extends Fragment {

    NavController navController;
    View viewBackAdminEditCarta, viewEditCarta2;
    TextView tvEditCarta2, tvAlertaEditCarta;
    Animation animScaleUp, animScaleDown;

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

        init(view);

        navController = Navigation.findNavController(view);

        viewBackAdminEditCarta.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    viewBackAdminEditCarta.startAnimation(animScaleUp);

                    navController.navigate(R.id.action_editCardFragment_to_adminCartasFragment);

                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    viewBackAdminEditCarta.startAnimation(animScaleDown);
                }

                return true;
            }
        });

        viewEditCarta2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    viewEditCarta2.startAnimation(animScaleUp);
                    tvEditCarta2.startAnimation(animScaleUp);

                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    viewEditCarta2.startAnimation(animScaleDown);
                    tvEditCarta2.startAnimation(animScaleDown);
                }

                return true;
            }
        });

    }

    public void init(View view) {

        animScaleUp = AnimationUtils.loadAnimation(getContext(), R.anim.scale_up);
        animScaleDown = AnimationUtils.loadAnimation(getContext(), R.anim.scale_down);

        viewBackAdminEditCarta = view.findViewById(R.id.viewBackAdminEditCarta);
        viewEditCarta2 = view.findViewById(R.id.viewEditCarta2);
        tvEditCarta2 = view.findViewById(R.id.tvEditCarta2);
        tvAlertaEditCarta = view.findViewById(R.id.tvAlertaEditCarta);

    }

}