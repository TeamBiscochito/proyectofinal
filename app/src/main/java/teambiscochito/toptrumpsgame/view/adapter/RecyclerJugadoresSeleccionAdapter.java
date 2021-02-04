package teambiscochito.toptrumpsgame.view.adapter;


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import teambiscochito.toptrumpsgame.R;
import teambiscochito.toptrumpsgame.model.room.pojo.User;
import teambiscochito.toptrumpsgame.viewmodel.ViewModel;

/**
 * <h2 align="center">Team Biscochito</h2><hr>
 * <p>
 * Clase Recycler para el fragmento de la selección de los jugadores donde podremos elegir a nuestro
 * jugador y entrar al menú principal.
 */
public class RecyclerJugadoresSeleccionAdapter extends RecyclerView.Adapter<RecyclerJugadoresSeleccionAdapter.ViewHolder> {
    private final List<User> userList;
    private final View view;
    private final Activity activity;

    public RecyclerJugadoresSeleccionAdapter(List<User> userList, View view, Activity activity) {
        this.userList = userList;
        this.view = view;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_elegir_player, parent, false);
        return new ViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.avatarRecycler.setImageResource(userList.get(position).getAvatar());
        holder.nombreJugadorRecycler.setText(userList.get(position).getName());
        holder.parent.setOnClickListener(new View.OnClickListener() {
            final NavController navController = Navigation.findNavController(view);

            @Override
            public void onClick(View v) {
                new ViewModelProvider((ViewModelStoreOwner) activity).get(ViewModel.class);

                ViewModel.userActual = userList.get(position);

                navController.navigate(R.id.action_chooseUserFragment_to_menuFragment);
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