package teambiscochito.toptrumpsgame.view.adapter;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import teambiscochito.toptrumpsgame.R;
import teambiscochito.toptrumpsgame.model.room.pojo.User;

public class RecyclerUserAdapter extends RecyclerView.Adapter<RecyclerUserAdapter.ViewHolder> {
    List<User> userList;
    Fragment fragment;
    View view;
    public RecyclerUserAdapter(List<User> userList, Fragment fragment, View view){
        this.userList = userList;
        this.fragment = fragment;
        this.view = view;
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

                // TODO: Por comprobar
                Bundle bundle = new Bundle();

                bundle.putLong("userid", userList.get(position).getId());

                navController.navigate(R.id.action_chooseUserFragment_to_menuFragment2, bundle);

            }
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
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
