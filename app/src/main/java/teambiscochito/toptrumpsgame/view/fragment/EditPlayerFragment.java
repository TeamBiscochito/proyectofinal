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

public class EditPlayerFragment extends Fragment {

    ViewPager2 vp_avatar;
    int [] avatares = {R.drawable.av_tigre, R.drawable.av_hipo, R.drawable.av_tucan, R.drawable.av_cerdo, R.drawable.av_gato, R.drawable.av_gallina};
    VpAvatarAdapter adapter;
    Animation animScaleUp, animScaleDown;
    NavController navController;
    View viewNextAvatar, viewPreviousAvatar, viewBackAdminEditPlayer, viewEditJugador, viewSeleccionarAvatarActual;
    TextView tvEditJugador, tvAlertaEditJugador, tvSeleccionarAvatarActual;

    EditText etNombreJugador;
    ViewModel viewModel;
    int numeroAvatarParaCargar;

    public EditPlayerFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_player, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init(view);

        viewModel = new ViewModelProvider(getActivity()).get(ViewModel.class);
        navController = Navigation.findNavController(view);

        User user = viewModel.getUser();

        vp_avatar = view.findViewById(R.id.vp_avatar_edit);

        etNombreJugador.setText(user.getName());

        String nombreAvatarParaCargar = getResources().getResourceEntryName(user.getAvatar());

        switch (nombreAvatarParaCargar) {

            case "av_tigre":
                numeroAvatarParaCargar = 0;
                break;
            case "av_hipo":
                numeroAvatarParaCargar = 1;
                break;
            case "av_loro":
                numeroAvatarParaCargar = 2;
                break;
            case "av_cerdo":
                numeroAvatarParaCargar = 3;
                break;
            case "av_gato":
                numeroAvatarParaCargar = 4;
                break;
            case "av_gallina":
                numeroAvatarParaCargar = 5;
                break;

        }

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

        viewBackAdminEditPlayer.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    viewBackAdminEditPlayer.startAnimation(animScaleUp);

                    tvAlertaEditJugador.setText("");
                    navController.navigate(R.id.action_editPlayerFragment_to_adminJugadorFragment);

                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    viewBackAdminEditPlayer.startAnimation(animScaleDown);

                }

                return true;
            }
        });

        viewSeleccionarAvatarActual.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    viewSeleccionarAvatarActual.startAnimation(animScaleUp);
                    tvSeleccionarAvatarActual.startAnimation(animScaleUp);

                    vp_avatar.setCurrentItem(numeroAvatarParaCargar);

                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    viewSeleccionarAvatarActual.startAnimation(animScaleDown);
                    tvSeleccionarAvatarActual.startAnimation(animScaleDown);

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

        viewEditJugador.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    viewEditJugador.startAnimation(animScaleUp);
                    tvEditJugador.startAnimation(animScaleUp);

                    String nombre = etNombreJugador.getText().toString();
                    viewModel.getNameFromName(nombre);
                    int num = viewModel.getRepeatedName();

                    if(nombre.isEmpty()) {

                        tvAlertaEditJugador.setText(R.string.tvIntroduceNombreSinPuntos);

                    } else if (nombre.equals(user.getName())) {

                        int avatar = avatares[vp_avatar.getCurrentItem()];

                        user.setAvatar(avatar);
                        viewModel.updateUser(user);
                        tvAlertaEditJugador.setText("");
                        navController.navigate(R.id.action_editPlayerFragment_to_adminJugadorFragment);

                    } else if(num != 0 && nombre.equals(user.getName())) {

                        int avatar = avatares[vp_avatar.getCurrentItem()];

                        user.setAvatar(avatar);
                        viewModel.updateUser(user);
                        tvAlertaEditJugador.setText("");
                        navController.navigate(R.id.action_editPlayerFragment_to_adminJugadorFragment);

                    } else if (num != 0 && !(nombre.equals(user.getName()))) {

                        tvAlertaEditJugador.setText(R.string.tvNombreYaEnUso);

                    } else {

                        int avatar = avatares[vp_avatar.getCurrentItem()];

                        user.setName(nombre);
                        user.setAvatar(avatar);
                        viewModel.updateUser(user);
                        tvAlertaEditJugador.setText("");
                        navController.navigate(R.id.action_editPlayerFragment_to_adminJugadorFragment);

                    }



                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    viewEditJugador.startAnimation(animScaleDown);
                    tvEditJugador.startAnimation(animScaleDown);

                }

                return true;
            }
        });

    }

    private void init(View view) {

        animScaleUp = AnimationUtils.loadAnimation(getContext(), R.anim.scale_up);
        animScaleDown = AnimationUtils.loadAnimation(getContext(), R.anim.scale_down);

        viewNextAvatar = view.findViewById(R.id.viewNextAvatarEdit);
        viewPreviousAvatar = view.findViewById(R.id.viewPreviousAvatarEdit);
        viewBackAdminEditPlayer = view.findViewById(R.id.viewBackAdminEditPlayer);
        viewEditJugador = view.findViewById(R.id.viewEditJugador2);
        viewSeleccionarAvatarActual = view.findViewById(R.id.viewSeleccionarAvatarActual);
        tvEditJugador = view.findViewById(R.id.tvEditJugador2);
        tvAlertaEditJugador = view.findViewById(R.id.tvAlertaEditJugador);
        tvSeleccionarAvatarActual = view.findViewById(R.id.tvSeleccionarAvatarActual);

        etNombreJugador = view.findViewById(R.id.etNombreJugadorEditar);

    }
}