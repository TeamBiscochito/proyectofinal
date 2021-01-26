package teambiscochito.toptrumpsgame.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import de.hdodenhof.circleimageview.CircleImageView;
import teambiscochito.toptrumpsgame.R;

public class VpAvatarAdapter extends RecyclerView.Adapter<VpAvatarAdapter.ViewHolder> {

    int[] avatares;

    public VpAvatarAdapter(int[] avatares) {
        this.avatares = avatares;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_vp_avatar, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.imgAvatar.setBackgroundResource(avatares[position]);

    }

    @Override
    public int getItemCount() {
        return avatares.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgAvatar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgAvatar = itemView.findViewById(R.id.iv_vp_avatar);
        }

    }

}