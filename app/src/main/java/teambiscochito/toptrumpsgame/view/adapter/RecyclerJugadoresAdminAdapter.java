package teambiscochito.toptrumpsgame.view.adapter;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
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

public class RecyclerJugadoresAdminAdapter extends RecyclerView.Adapter<RecyclerJugadoresAdminAdapter.ViewHolder> {

    List<User> userList;
    View view;
    ViewModel viewModel;
    Activity activity;
    Dialog dialogJugadores, dialogConfirmarBorrar;
    Context context;
    Animation animScaleUp, animScaleDown;

    public RecyclerJugadoresAdminAdapter(List<User> userList, View view, Activity activity, Context context){
        this.userList = userList;
        this.view = view;
        this.activity = activity;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_elegir_player, parent,false);
        ViewHolder holder = new ViewHolder(vista);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerJugadoresAdminAdapter.ViewHolder holder, int position) {

        holder.avatarRecycler.setImageResource(userList.get(position).getAvatar());
        holder.nombreJugadorRecycler.setText(userList.get(position).getName());

        animScaleUp = AnimationUtils.loadAnimation(context, R.anim.scale_up);
        animScaleDown = AnimationUtils.loadAnimation(context, R.anim.scale_down);

        holder.parent.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                viewModel = new ViewModelProvider((ViewModelStoreOwner) activity).get(ViewModel.class);

                dialogJugadores = new Dialog(context);
                dialogJugadores.setContentView(R.layout.jugadores_dialog);
                dialogJugadores.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                Window window = dialogJugadores.getWindow();
                window.setGravity(Gravity.CENTER);
                window.getAttributes().windowAnimations = R.style.DialogAnimation;

                CircleImageView imgAvatar = dialogJugadores.findViewById(R.id.imgAvatarInfoAdminJ);
                TextView tvNombre = dialogJugadores.findViewById(R.id.tvNombreInfoAdminJ);
                TextView tvBorrarInfoAdminJ = dialogJugadores.findViewById(R.id.tvBorrarInfoAdminJ);
                View viewBackInfoAdminJ = dialogJugadores.findViewById(R.id.viewBackInfoAdminJ);
                View viewBorrarInfoAdminJ = dialogJugadores.findViewById(R.id.viewBorrarInfoAdminJ);
                View viewEditarInfoAdminJ = dialogJugadores.findViewById(R.id.viewEditarInfoAdminJ);
                TextView tvEditarInfoAdminJ = dialogJugadores.findViewById(R.id.tvEditarInfoAdminJ);

                viewBackInfoAdminJ.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dialogJugadores.dismiss();

                    }
                });

                viewEditarInfoAdminJ.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {

                        if(event.getAction() == MotionEvent.ACTION_DOWN) {
                            viewEditarInfoAdminJ.startAnimation(animScaleUp);
                            tvEditarInfoAdminJ.startAnimation(animScaleUp);

                        } else if (event.getAction() == MotionEvent.ACTION_UP) {
                            viewEditarInfoAdminJ.startAnimation(animScaleDown);
                            tvEditarInfoAdminJ.startAnimation(animScaleDown);
                        }

                        return true;
                    }
                });

                viewBorrarInfoAdminJ.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {

                        if(event.getAction() == MotionEvent.ACTION_DOWN) {
                            viewBorrarInfoAdminJ.startAnimation(animScaleUp);
                            tvBorrarInfoAdminJ.startAnimation(animScaleUp);

                            dialogConfirmarBorrar = new Dialog(context);
                            dialogConfirmarBorrar.setContentView(R.layout.borrar_confirmation_dialog);
                            dialogConfirmarBorrar.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                            Window window = dialogConfirmarBorrar.getWindow();
                            window.setGravity(Gravity.CENTER);
                            window.getAttributes().windowAnimations = R.style.DialogAnimation;

                            View viewCancelarBorrarJugador = dialogConfirmarBorrar.findViewById(R.id.viewCancelarBorrarJugador);
                            View viewAceptarBorrarJugador = dialogConfirmarBorrar.findViewById(R.id.viewAceptarBorrarJugador);

                            viewCancelarBorrarJugador.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    dialogConfirmarBorrar.dismiss();

                                }
                            });

                            viewAceptarBorrarJugador.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    dialogConfirmarBorrar.dismiss();
                                    dialogJugadores.dismiss();

                                    long idBorrar = userList.get(position).getId();
                                    viewModel.deleteUserById(idBorrar);

                                }
                            });

                            dialogConfirmarBorrar.setCancelable(true);
                            dialogConfirmarBorrar.setCanceledOnTouchOutside(false);
                            window.setLayout(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
                            dialogConfirmarBorrar.show();

                        } else if (event.getAction() == MotionEvent.ACTION_UP) {
                            viewBorrarInfoAdminJ.startAnimation(animScaleDown);
                            tvBorrarInfoAdminJ.startAnimation(animScaleDown);

                        }

                        return true;
                    }
                });

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
        try{
            return userList.size();
        } catch (Exception exception){
            return -1;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView avatarRecycler;
        TextView nombreJugadorRecycler;
        ConstraintLayout parent;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            avatarRecycler = itemView.findViewById(R.id.avatarRecycler);
            nombreJugadorRecycler = itemView.findViewById(R.id.nombreJugadorRecycler);
            parent = itemView.findViewById(R.id.consRecyclerJugadores);
        }
    }
}