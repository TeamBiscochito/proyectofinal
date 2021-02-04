/*
 * Copyright (c) 2021. Team Biscochito.
 *
 * Licensed under the GNU General Public License v3.0
 *
 * https://www.gnu.org/licenses/gpl-3.0.html
 *
 * Permissions of this strong copyleft license are conditioned on making available complete
 * source code of licensed works and modifications, which include larger works using a licensed
 * work, under the same license. Copyright and license notices must be preserved. Contributors
 * provide an express grant of patent rights.
 */

package teambiscochito.toptrumpsgame.view.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import java.util.ArrayList;
import java.util.List;

import teambiscochito.toptrumpsgame.R;
import teambiscochito.toptrumpsgame.model.room.pojo.Card;
import teambiscochito.toptrumpsgame.viewmodel.ViewModel;

/**
 * <h2 align="center">Team Biscochito</h2><hr>
 * <p>
 * Clase Recycler para el fragmento de importación de cartas.
 */
public class RecyclerImportAdapter extends RecyclerView.Adapter<RecyclerImportAdapter.ViewHolder> {
    private final List<Card> cardList;
    private final Context context;
    private final Activity activity;
    private final ArrayList<CheckBox> checkBoxes;

    public RecyclerImportAdapter(List<Card> cardList, Context context, Activity activity) {
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
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_import, parent, false);
        return new ViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ViewModel viewModel = new ViewModelProvider((ViewModelStoreOwner) activity).get(ViewModel.class);
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

        Card c = viewModel.getCardByName(cardList.get(position).getName());

        if (c != null) {
            holder.cbImport.setEnabled(false);
        }
    }

    @Override
    public int getItemCount() {
        return cardList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox cbImport;
        ImageView ivImport;
        TextView tvImport;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cbImport = itemView.findViewById(R.id.chkItemImport_Importar);
            ivImport = itemView.findViewById(R.id.civItemImport_Item);
            tvImport = itemView.findViewById(R.id.tvItemImport_Nombre);
        }
    }
}