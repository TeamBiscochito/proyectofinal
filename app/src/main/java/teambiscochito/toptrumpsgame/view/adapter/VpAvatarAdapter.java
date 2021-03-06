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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import teambiscochito.toptrumpsgame.R;

/**
 * <h2 align="center">Team Biscochito</h2><hr>
 * <p>
 * Clase Recycler para el funcionamiento de los ViewPager2, y así instanciarlo donde lo necesitemos,
 * en este caso en los iconos de los avatares y los bocadillos.
 */
public class VpAvatarAdapter extends RecyclerView.Adapter<VpAvatarAdapter.ViewHolder> {
    private final int[] avatares;

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

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgAvatar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAvatar = itemView.findViewById(R.id.iv_vp_avatar);
        }
    }
}