package teambiscochito.toptrumpsgame.view.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.text.LineBreaker;
import android.os.Build;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.wajahatkarim3.easyflipview.EasyFlipView;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import teambiscochito.toptrumpsgame.R;
import teambiscochito.toptrumpsgame.model.room.pojo.Card;
import teambiscochito.toptrumpsgame.model.room.pojo.Question;
import teambiscochito.toptrumpsgame.viewmodel.ViewModel;

public class RecyclerImportAdapter extends RecyclerView.Adapter<RecyclerImportAdapter.ViewHolder> {
    private List<Card> cardList;
    private ViewModel viewModel;
    private Context context;
    private Activity activity;
    private ArrayList<CheckBox> checkBoxes;

    public RecyclerImportAdapter(List<Card> cardList, Context context, Activity activity){
        this.cardList = cardList;
        this.context = context;
        this.activity = activity;
        checkBoxes = new ArrayList<>();
    }

    public ArrayList<CheckBox> getCheckBoxes() {
        return checkBoxes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_import, parent,false);
        RecyclerImportAdapter.ViewHolder holder = new RecyclerImportAdapter.ViewHolder(vista);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        viewModel = new ViewModelProvider((ViewModelStoreOwner) activity).get(ViewModel.class);
        checkBoxes.add(holder.cbImport);
        holder.tvImport.setText(cardList.get(position).getName());

        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.cargando)
                .error(R.drawable.cerdi)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH);

        Glide.with(context).load(cardList.get(position).getPicUrl())
                .apply(options)
                .into(holder.ivImport);

        Card c  = viewModel.getCardByName(cardList.get(position).getName());
        if( c != null){
            Log.v("xyz", "entro en el if mongolin");
            holder.cbImport.setEnabled(false);
        }
    }

    @Override
    public int getItemCount() {
        return cardList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        CheckBox cbImport;
        ImageView ivImport;
        TextView tvImport;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cbImport = itemView.findViewById(R.id.cbImport);
            ivImport = itemView.findViewById(R.id.ivImport);
            tvImport = itemView.findViewById(R.id.tvImport);
        }
    }
}


