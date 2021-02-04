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
import android.graphics.text.LineBreaker;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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

import java.text.NumberFormat;
import java.util.List;

import teambiscochito.toptrumpsgame.R;
import teambiscochito.toptrumpsgame.model.room.pojo.Card;
import teambiscochito.toptrumpsgame.model.room.pojo.Question;
import teambiscochito.toptrumpsgame.viewmodel.ViewModel;

/**
 * <h2 align="center">Team Biscochito</h2><hr>
 * <p>
 * Clase Recycler para el fragmento posicionado en el menú en el cual se puede echar un ojo a las
 * cartas con las que se va a jugar en el juego. No se pueden editar ni hacer nada relacionado con
 * la administración.
 */
@SuppressWarnings({"Convert2Lambda"})
// Comente la línea de arriba para ver los posibles Lambdas a convertir
public class RecyclerCartasNoAdminAdapter extends RecyclerView.Adapter<RecyclerCartasNoAdminAdapter.ViewHolder> {
    private final List<Card> cardList;
    private final Activity activity;
    private final Context context;
    private Animation animScaleUp, animScaleDown;
    private ViewModel viewModel;

    public RecyclerCartasNoAdminAdapter(List<Card> cardList, Activity activity, Context context) {
        this.cardList = cardList;
        this.activity = activity;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_cartas_no_admin, parent, false);
        return new ViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setMaximumFractionDigits(0);

        viewModel = new ViewModelProvider((ViewModelStoreOwner) activity).get(ViewModel.class);
        animScaleUp = AnimationUtils.loadAnimation(context, R.anim.scale_up);
        animScaleDown = AnimationUtils.loadAnimation(context, R.anim.scale_down);

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
        holder.tvDescCartasNoAdminBack.setText(cardList.get(position).getDescription());

        try {
            getDatos(holder, position);
        } catch (Exception ignored) {
        }
        flipCartas(holder);
    }

    @Override
    public int getItemCount() {
        try {
            return cardList.size();
        } catch (Exception exception) {
            return -1;
        }
    }

    /**
     * <h2 align="center">Team Biscochito</h2><hr>
     * <p>
     * Método para hacer el efecto del flip en la carta.
     * <br><br>
     * Referencia del método en: {@link RecyclerCartasNoAdminAdapter#onBindViewHolder(ViewHolder, int)}
     *
     * @param holder pasamos el holder el item por parámetro.
     */
    private void flipCartas(ViewHolder holder) {
        holder.viewClicParaHacerFlipCarta.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        holder.viewClicParaHacerFlipCarta.startAnimation(animScaleUp);
                        holder.tvClicParaHacerFlipCarta.startAnimation(animScaleUp);

                        holder.easyFlipView.flipTheView();
                        break;
                    case MotionEvent.ACTION_UP:
                        holder.viewClicParaHacerFlipCarta.startAnimation(animScaleDown);
                        holder.tvClicParaHacerFlipCarta.startAnimation(animScaleDown);
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
     * Método para obtener las cartas con sus respectivos datos.
     * <br><br>
     * Referencia del método en: {@link RecyclerCartasNoAdminAdapter#onBindViewHolder(ViewHolder, int)}
     *
     * @param holder   pasamos el holder del reyclcer (item).
     * @param position posición del recycler.
     */
    private void getDatos(ViewHolder holder, int position) {
        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setMaximumFractionDigits(0);

        List<Question> questionList = viewModel.getQuestionListByCardId(cardList.get(position).getId());
        if (questionList.get(0).getAnswer() % 1 == 0) {
            holder.tvAltura.setText(numberFormat.format(questionList.get(0).getAnswer()));
        } else {
            holder.tvAltura.setText(String.format("%s", questionList.get(0).getAnswer().toString()));
        }

        if (questionList.get(1).getAnswer() % 1 == 0) {
            holder.tvPeso.setText(numberFormat.format(questionList.get(1).getAnswer()));
        } else {
            holder.tvPeso.setText(String.format("%s", questionList.get(1).getAnswer().toString()));
        }

        if (questionList.get(2).getAnswer() % 1 == 0) {
            holder.tvLongitud.setText(numberFormat.format(questionList.get(2).getAnswer()));
        } else {
            holder.tvLongitud.setText(String.format("%s", questionList.get(2).getAnswer().toString()));
        }

        if (questionList.get(3).getAnswer() % 1 == 0) {
            holder.tvVelocidad.setText(numberFormat.format(questionList.get(3).getAnswer()));
        } else {
            holder.tvVelocidad.setText(String.format("%s", questionList.get(3).getAnswer().toString()));
        }

        double valorPoderDouble = Double.parseDouble(questionList.get(4).getAnswer().toString());
        int valorPoderInt = (int) valorPoderDouble;
        holder.tvPoder.setText(String.valueOf(valorPoderInt));

        holder.tvAlturaUnidad.setText(questionList.get(0).getMagnitude());
        holder.tvPesoUnidad.setText(questionList.get(1).getMagnitude());
        holder.tvLongitudUnidad.setText(questionList.get(2).getMagnitude());
        holder.tvVelocidadUnidad.setText(questionList.get(3).getMagnitude());
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgFotoCartaNoAdmin;
        TextView tvNombreCartaNoAdmin, tvDescCartasNoAdminBack, tvAltura, tvPeso, tvLongitud, tvVelocidad, tvPoder, tvAlturaUnidad, tvPesoUnidad, tvLongitudUnidad, tvVelocidadUnidad;
        EasyFlipView easyFlipView;
        TextView tvClicParaHacerFlipCarta;
        View viewClicParaHacerFlipCarta;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgFotoCartaNoAdmin = itemView.findViewById(R.id.civCartaNoAdmin_Foto);
            tvNombreCartaNoAdmin = itemView.findViewById(R.id.tvCartasNoAdmin_Nombre);
            tvDescCartasNoAdminBack = itemView.findViewById(R.id.tvCartasNoAdmin_Descripcion);
            easyFlipView = itemView.findViewById(R.id.efvCartasNoAdmin_FlipCarta);
            tvClicParaHacerFlipCarta = itemView.findViewById(R.id.tvCartasNoAdmin_Flip);
            viewClicParaHacerFlipCarta = itemView.findViewById(R.id.viewCartasNoAdmin_Flip);

            tvAltura = itemView.findViewById(R.id.tvCartasNoAdmin_ValorAltura);
            tvPeso = itemView.findViewById(R.id.tvCartasNoAdmin_ValorPeso);
            tvLongitud = itemView.findViewById(R.id.tvCartasNoAdmin_ValorLongitud);
            tvVelocidad = itemView.findViewById(R.id.tvCartasNoAdmin_ValorVelocidad);
            tvPoder = itemView.findViewById(R.id.tvCartasNoAdmin_ValorPoder);

            tvAlturaUnidad = itemView.findViewById(R.id.tvCartasNoAdmin_UnidadAltura);
            tvPesoUnidad = itemView.findViewById(R.id.tvCartasNoAdmin_UnidadPeso);
            tvLongitudUnidad = itemView.findViewById(R.id.tvCartasNoAdmin_UnidadLongitud);
            tvVelocidadUnidad = itemView.findViewById(R.id.tvCartasNoAdmin_UnidadVelocidad);

            // Poner texto de la carta justificado
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                tvDescCartasNoAdminBack.setJustificationMode(LineBreaker.JUSTIFICATION_MODE_INTER_WORD);
            } else {
                tvDescCartasNoAdminBack.setGravity(Gravity.CENTER);
            }
        }
    }
}