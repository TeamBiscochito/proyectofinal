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

public class RecyclerJugadoresSeleccionAdapter extends RecyclerView.Adapter<RecyclerJugadoresSeleccionAdapter.ViewHolder> {
    List<User> userList;
    View view;
    ViewModel viewModel;
    Activity activity;

    public RecyclerJugadoresSeleccionAdapter(List<User> userList, View view, Activity activity){
        this.userList = userList;
        this.view = view;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_elegir_player, parent,false);
        ViewHolder holder = new ViewHolder(vista);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.avatarRecycler.setImageResource(userList.get(position).getAvatar());
        holder.nombreJugadorRecycler.setText(userList.get(position).getName());
        holder.parent.setOnClickListener(new View.OnClickListener() {
            final NavController navController = Navigation.findNavController(view);
            @Override
            public void onClick(View v) {
                viewModel = new ViewModelProvider((ViewModelStoreOwner) activity).get(ViewModel.class);


                // TODO: Por comprobar "Bundle en pruebas"
                //Bundle bundle = new Bundle();
                //bundle.putLong("userid", userList.get(position).getId());
                viewModel.userActual = userList.get(position);

                navController.navigate(R.id.action_chooseUserFragment_to_menuFragment);

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