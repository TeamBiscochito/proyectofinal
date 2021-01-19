package teambiscochito.toptrumpsgame.view.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.TextView;

import teambiscochito.toptrumpsgame.R;
import teambiscochito.toptrumpsgame.model.room.pojo.User;
import teambiscochito.toptrumpsgame.view.adapter.VpAvatarAdapter;
import teambiscochito.toptrumpsgame.viewmodel.ViewModel;

public class AddPlayerFragment extends Fragment {

    ViewPager2 vp_avatar;
    int [] avatares = {R.drawable.av_tigre, R.drawable.av_hipo, R.drawable.av_tucan, R.drawable.av_cerdo, R.drawable.av_gato, R.drawable.av_gallina};
    VpAvatarAdapter adapter;
    Animation animScaleUp, animScaleDown;
    NavController navController;
    View viewNextAvatar, viewPreviousAvatar, viewBackAdminAddPlayer, viewAddJugador;
    TextView tvAddJugador, tvAlertaAddJugador;

    EditText etNombreJugador;
    ViewModel viewModel;

    public AddPlayerFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_player, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init(view);

        viewModel = new ViewModelProvider(getActivity()).get(ViewModel.class);
        navController = Navigation.findNavController(view);

        vp_avatar = view.findViewById(R.id.vp_avatar);

        adapter = new VpAvatarAdapter(avatares);

        vp_avatar.setClipToPadding(false);
        vp_avatar.setClipChildren(false);
        vp_avatar.setOffscreenPageLimit(3);
        vp_avatar.getChildAt(0).setOverScrollMode(View.OVER_SCROLL_NEVER);
        vp_avatar.setAdapter(adapter);

        CompositePageTransformer transformer = new CompositePageTransformer();

        transformer.addTransformer(new MarginPageTransformer(8));

        transformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {

                float v = 1 - Math.abs(position);

                page.setScaleY(0.8f + v * 0.2f);

            }
        });

        vp_avatar.setPageTransformer(transformer);

        viewBackAdminAddPlayer.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    viewBackAdminAddPlayer.startAnimation(animScaleUp);

                    tvAlertaAddJugador.setText("");
                    navController.navigate(R.id.action_addPlayerFragment_to_adminJugadorFragment);

                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    viewBackAdminAddPlayer.startAnimation(animScaleDown);

                }

                return true;
            }
        });

        viewNextAvatar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    viewNextAvatar.startAnimation(animScaleUp);

                    if(vp_avatar.getCurrentItem() + 1 < adapter.getItemCount()) {
                        vp_avatar.setCurrentItem(vp_avatar.getCurrentItem() + 1);
                    }

                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    viewNextAvatar.startAnimation(animScaleDown);

                }

                return true;
            }
        });

        viewPreviousAvatar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    viewPreviousAvatar.startAnimation(animScaleUp);

                    if(vp_avatar.getCurrentItem() + 1 > 0) {
                        vp_avatar.setCurrentItem(vp_avatar.getCurrentItem() - 1);
                    }

                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    viewPreviousAvatar.startAnimation(animScaleDown);

                }

                return true;
            }
        });

        viewAddJugador.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    viewAddJugador.startAnimation(animScaleUp);
                    tvAddJugador.startAnimation(animScaleUp);

                    String nombre = etNombreJugador.getText().toString();
                    viewModel.getNameFromName(nombre);
                    int num = viewModel.getRepeatedName();

                    if(nombre.isEmpty()) {

                        tvAlertaAddJugador.setText(R.string.tvIntroduceNombreSinPuntos);

                    } else if (num != 0) {

                            tvAlertaAddJugador.setText(R.string.tvNombreYaEnUso);

                    } else {

                        int avatar = avatares[vp_avatar.getCurrentItem()];
                        viewModel.insertUser(new User(nombre, avatar));
                        tvAlertaAddJugador.setText("");
                        navController.navigate(R.id.action_addPlayerFragment_to_adminJugadorFragment);

                    }



                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    viewAddJugador.startAnimation(animScaleDown);
                    tvAddJugador.startAnimation(animScaleDown);

                }

                return true;
            }
        });

    }

    private void init(View view) {

        animScaleUp = AnimationUtils.loadAnimation(getContext(), R.anim.scale_up);
        animScaleDown = AnimationUtils.loadAnimation(getContext(), R.anim.scale_down);

        viewNextAvatar = view.findViewById(R.id.viewNextAvatar);
        viewPreviousAvatar = view.findViewById(R.id.viewPreviousAvatar);
        viewBackAdminAddPlayer = view.findViewById(R.id.viewBackAdminAddPlayer);
        viewAddJugador = view.findViewById(R.id.viewAddJugador2);
        tvAddJugador = view.findViewById(R.id.tvAddJugador2);
        tvAlertaAddJugador = view.findViewById(R.id.tvAlertaAddJugador);

        etNombreJugador = view.findViewById(R.id.etNombreJugador);

    }
}