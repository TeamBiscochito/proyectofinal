package teambiscochito.toptrumpsgame.view.adapter;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.media.MediaPlayer;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import teambiscochito.toptrumpsgame.R;
import teambiscochito.toptrumpsgame.model.room.pojo.User;
import teambiscochito.toptrumpsgame.viewmodel.ViewModel;

/**
 * <h2 align="center">Team Biscochito</h2><hr>
 * <p>
 * Clase Recycler para el fragmento de la administración de los jugadores donde podremos editar el
 * jugador como también podemos borrar el jugador.
 */
public class RecyclerJugadoresAdminAdapter extends RecyclerView.Adapter<RecyclerJugadoresAdminAdapter.ViewHolder> {

    List<User> userList;
    View view;
    ViewModel viewModel;
    Activity activity;
    Dialog dialogJugadores, dialogConfirmarBorrar;
    Context context;
    Animation animScaleUp, animScaleDown;
    NavController navController;
    private MediaPlayer mp_borrar;

    public RecyclerJugadoresAdminAdapter(List<User> userList, View view, Activity activity, Context context) {
        this.userList = userList;
        this.view = view;
        this.activity = activity;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_elegir_player, parent, false);
        return new ViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerJugadoresAdminAdapter.ViewHolder holder, int position) {
        viewModel = new ViewModelProvider((ViewModelStoreOwner) activity).get(ViewModel.class);

        holder.avatarRecycler.setImageResource(userList.get(position).getAvatar());
        holder.nombreJugadorRecycler.setText(userList.get(position).getName());

        animScaleUp = AnimationUtils.loadAnimation(context, R.anim.scale_up);
        animScaleDown = AnimationUtils.loadAnimation(context, R.anim.scale_down);

        mp_borrar = MediaPlayer.create(context, R.raw.borrar_sound);

        User user = userList.get(position);

        navController = Navigation.findNavController(view);

        itemRecycler(holder, position, user);
    }

    /**
     * <h2 align="center">Team Biscochito</h2><hr>
     * <p>
     * Método que recoge el item seleccionado del RecyclerView, por el cual podremos hacer las
     * diferentes opciones de administración como editar o borrar al jugador.
     *
     * @param holder   item contenedor del recycler.
     * @param position posición del recycler.
     * @param user     usuario que coge en la posición en {@link #onBindViewHolder(ViewHolder, int)}
     */
    private void itemRecycler(@NonNull ViewHolder holder, int position, User user) {
        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.setUser(user);

                dialogJugadores = new Dialog(context);
                dialogJugadores.setContentView(R.layout.jugadores_dialog);
                dialogJugadores.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                Window window = dialogJugadores.getWindow();
                window.setGravity(Gravity.CENTER);
                window.getAttributes().windowAnimations = R.style.DialogAnimation;

                CircleImageView imgAvatar = dialogJugadores.findViewById(R.id.civDialogJugadores_Avatar);
                TextView tvNombre = dialogJugadores.findViewById(R.id.tvDialogJugadores_Nombre);
                TextView tvBorrarInfoAdminJ = dialogJugadores.findViewById(R.id.tvDialogJugadores_Borrar);
                View viewBackInfoAdminJ = dialogJugadores.findViewById(R.id.viewDialogJugadores_Back);
                View viewBorrarInfoAdminJ = dialogJugadores.findViewById(R.id.viewDialogJugadores_Borrar);
                View viewEditarInfoAdminJ = dialogJugadores.findViewById(R.id.viewDialogJugadores_Editar);
                TextView tvEditarInfoAdminJ = dialogJugadores.findViewById(R.id.tvDialogJugadores_Editar);

                viewBackInfoAdminJ.setOnClickListener(v13 -> dialogJugadores.dismiss());

                editarBoton(viewEditarInfoAdminJ, tvEditarInfoAdminJ);
                borrarBoton(viewBorrarInfoAdminJ, tvBorrarInfoAdminJ, position);

                imgAvatar.setImageResource(userList.get(position).getAvatar());
                tvNombre.setText(userList.get(position).getName());

                dialogJugadores.setCancelable(true);
                dialogJugadores.setCanceledOnTouchOutside(false);
                window.setLayout(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
                dialogJugadores.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        try {
            return userList.size();
        } catch (Exception exception) {
            return -1;
        }
    }

    /**
     * <h2 align="center">Team Biscochito</h2><hr>
     * <p>
     * Método de editar view, en el cual nos navegara a un nuevo fragmento.
     * <br><br>
     * Este método es llamado en: {@link #itemRecycler(ViewHolder, int, User)}
     *
     * @param viewEditarInfoAdminJ pasamos el view del editar.
     * @param tvEditarInfoAdminJ   pasamos el TexView de editar.
     */
    private void editarBoton(View viewEditarInfoAdminJ, TextView tvEditarInfoAdminJ) {
        viewEditarInfoAdminJ.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        viewEditarInfoAdminJ.startAnimation(animScaleUp);
                        tvEditarInfoAdminJ.startAnimation(animScaleUp);

                        dialogJugadores.dismiss();
                        navController.navigate(R.id.action_adminJugadorFragment_to_editPlayerFragment);
                        break;
                    case MotionEvent.ACTION_UP:
                        viewEditarInfoAdminJ.startAnimation(animScaleDown);
                        tvEditarInfoAdminJ.startAnimation(animScaleDown);
                        v.performClick();
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
    }

    /**
     * <h2 align="center">Team Biscochito</h2><hr>
     * <p>
     * Método de borrar view, en el cual ejecutaremos la acción para poder borrar el usuario y
     * eliminarlo de la base de datos.
     * <br><br>
     * Este método es llamado en: {@link #itemRecycler(ViewHolder, int, User)}
     *
     * @param viewBorrarInfoAdminJ pasamos el view de borrar.
     * @param tvBorrarInfoAdminJ   pasamos el TexView de borrar.
     * @param position             posición del recycler para eliminar dicho elemento.
     */
    private void borrarBoton(View viewBorrarInfoAdminJ, TextView tvBorrarInfoAdminJ, int position) {
        viewBorrarInfoAdminJ.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        viewBorrarInfoAdminJ.startAnimation(animScaleUp);
                        tvBorrarInfoAdminJ.startAnimation(animScaleUp);

                        dialogConfirmarBorrar = new Dialog(context);
                        dialogConfirmarBorrar.setContentView(R.layout.borrar_confirmation_dialog);
                        dialogConfirmarBorrar.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                        Window window = dialogConfirmarBorrar.getWindow();
                        window.setGravity(Gravity.CENTER);
                        window.getAttributes().windowAnimations = R.style.DialogAnimation;

                        TextView tvConfirmarBorrarMsg = dialogConfirmarBorrar.findViewById(R.id.tvDialogBorrarConfirmText);
                        View viewCancelarBorrarJugador = dialogConfirmarBorrar.findViewById(R.id.viewDialogBorrarConfirmCancel);
                        View viewAceptarBorrarJugador = dialogConfirmarBorrar.findViewById(R.id.viewDialogBorrarConfirmAccept);

                        tvConfirmarBorrarMsg.setText(R.string.tvConfirmarBorrarJugador);

                        viewCancelarBorrarJugador.setOnClickListener(v1 -> dialogConfirmarBorrar.dismiss());

                        viewAceptarBorrarJugador.setOnClickListener(v12 -> {

                            dialogConfirmarBorrar.dismiss();
                            dialogJugadores.dismiss();
                            mp_borrar.start();

                            long idBorrar = userList.get(position).getId();
                            viewModel.deleteUserById(idBorrar);
                        });

                        dialogConfirmarBorrar.setCancelable(true);
                        dialogConfirmarBorrar.setCanceledOnTouchOutside(false);
                        window.setLayout(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
                        dialogConfirmarBorrar.show();
                        break;
                    case MotionEvent.ACTION_UP:
                        viewBorrarInfoAdminJ.startAnimation(animScaleDown);
                        tvBorrarInfoAdminJ.startAnimation(animScaleDown);
                        v.performClick();
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView avatarRecycler;
        TextView nombreJugadorRecycler;
        ConstraintLayout parent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            avatarRecycler = itemView.findViewById(R.id.civRecyclerElegirPlayer_Avatar);
            nombreJugadorRecycler = itemView.findViewById(R.id.tvRecyclerElegirPlayer_Nombre);
            parent = itemView.findViewById(R.id.consRecyclerJugadores);
        }
    }
}