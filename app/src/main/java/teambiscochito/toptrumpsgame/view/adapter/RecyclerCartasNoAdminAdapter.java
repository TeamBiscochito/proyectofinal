package teambiscochito.toptrumpsgame.view.adapter;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import teambiscochito.toptrumpsgame.R;
import teambiscochito.toptrumpsgame.model.room.pojo.Card;

public class RecyclerCartasNoAdminAdapter extends RecyclerView.Adapter<RecyclerCartasNoAdminAdapter.ViewHolder> {
    List<Card> cardList;
    View view;
    Activity activity;
    Context context;

    public RecyclerCartasNoAdminAdapter(List<Card> cardList, View view, Activity activity, Context context){
        this.cardList = cardList;
        this.view = view;
        this.activity = activity;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_cartas_no_admin, parent,false);
        ViewHolder holder = new ViewHolder(vista);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.cargando)
                .error(R.drawable.cerdi)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH);

        Glide.with(context).load(cardList.get(position).getPicUrl())
                .apply(options)
                .into(holder.imgFotoCartaNoAdmin);

        holder.tvNombreCartaNoAdmin.setText(cardList.get(position).getName());
        holder.tvDescCartasNoAdminBack.setText(cardList.get(position).getDesc());

    }

    @Override
    public int getItemCount() {
        try{
            return cardList.size();
        } catch (Exception exception){
            return -1;
        }

    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imgFotoCartaNoAdmin;
        TextView tvNombreCartaNoAdmin, tvDescCartasNoAdminBack;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgFotoCartaNoAdmin = itemView.findViewById(R.id.imgFotoCartaNoAdmin);
            tvNombreCartaNoAdmin = itemView.findViewById(R.id.tvNombreCartaNoAdmin);
            tvDescCartasNoAdminBack = itemView.findViewById(R.id.tvDescCartasNoAdminBack);
        }
    }
}