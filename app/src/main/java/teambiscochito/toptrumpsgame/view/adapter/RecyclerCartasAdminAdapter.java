package teambiscochito.toptrumpsgame.view.adapter;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.text.LineBreaker;
import android.media.MediaPlayer;
import android.os.Build;
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
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.wajahatkarim3.easyflipview.EasyFlipView;

import java.text.NumberFormat;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import teambiscochito.toptrumpsgame.R;
import teambiscochito.toptrumpsgame.model.room.pojo.Card;
import teambiscochito.toptrumpsgame.model.room.pojo.Question;
import teambiscochito.toptrumpsgame.viewmodel.ViewModel;

public class RecyclerCartasAdminAdapter extends RecyclerView.Adapter<RecyclerCartasAdminAdapter.ViewHolder> {

    List<Card> cardList;
    View view;
    Activity activity;
    Context context;
    Animation animScaleUp, animScaleDown;
    ViewModel viewModel;
    Dialog dialogCartas, dialogConfirmarBorrar;

    NavController navController;
    private MediaPlayer mp_borrar;

    public RecyclerCartasAdminAdapter(List<Card> cardList, View view, Activity activity, Context context){
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

        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setMaximumFractionDigits(0);

        viewModel = new ViewModelProvider((ViewModelStoreOwner) activity).get(ViewModel.class);

        animScaleUp = AnimationUtils.loadAnimation(context, R.anim.scale_up);
        animScaleDown = AnimationUtils.loadAnimation(context, R.anim.scale_down);

        mp_borrar = MediaPlayer.create(context, R.raw.borrar_sound);

        Card card = viewModel.cards.get(position);

        navController = Navigation.findNavController(view);

        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.cargando)
                .error(R.drawable.cerdi)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH);

        Glide.with(context).load(viewModel.cards.get(position).getPicUrl())
                .apply(options)
                .into(holder.imgFotoCartaNoAdmin);

        holder.tvNombreCartaNoAdmin.setText(viewModel.cards.get(position).getName());
        holder.tvDescCartasNoAdminBack.setText(viewModel.cards.get(position).getDescription());

        try{

            List<Question> questionList = viewModel.getQuestionsForCurrentCard(viewModel.cards.get(position));

            if (questionList.get(0).getAnswer() % 1 == 0) {
                holder.tvAltura.setText(numberFormat.format(questionList.get(0).getAnswer()));
            } else {
                holder.tvAltura.setText(questionList.get(0).getAnswer().toString());
            }

            if (questionList.get(1).getAnswer() % 1 == 0) {
                holder.tvPeso.setText(numberFormat.format(questionList.get(1).getAnswer()));
            } else {
                holder.tvPeso.setText(questionList.get(1).getAnswer().toString());
            }

            if (questionList.get(2).getAnswer() % 1 == 0) {
                holder.tvLongitud.setText(numberFormat.format(questionList.get(2).getAnswer()));
            } else {
                holder.tvLongitud.setText(questionList.get(2).getAnswer().toString());
            }

            if (questionList.get(3).getAnswer() % 1 == 0) {
                holder.tvVelocidad.setText(numberFormat.format(questionList.get(3).getAnswer()));
            } else {
                holder.tvVelocidad.setText(questionList.get(3).getAnswer().toString());
            }



            double valorPoderDouble = Double.parseDouble(questionList.get(4).getAnswer().toString());
            int valorPoderInt = (int) valorPoderDouble;
            holder.tvPoder.setText("" + valorPoderInt);

            holder.tvAlturaUnidad.setText(questionList.get(0).getMagnitude());
            holder.tvPesoUnidad.setText(questionList.get(1).getMagnitude());
            holder.tvLongitudUnidad.setText(questionList.get(2).getMagnitude());
            holder.tvVelocidadUnidad.setText(questionList.get(3).getMagnitude());

        } catch (Exception ex){

        }

        holder.viewClicParaHacerFlipCarta.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    holder.viewClicParaHacerFlipCarta.startAnimation(animScaleUp);
                    holder.tvClicParaHacerFlipCarta.startAnimation(animScaleUp);

                    holder.easyFlipView.flipTheView();

                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    holder.viewClicParaHacerFlipCarta.startAnimation(animScaleDown);
                    holder.tvClicParaHacerFlipCarta.startAnimation(animScaleDown);
                }

                return true;
            }
        });

        holder.tvNombreCartaNoAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                viewModel.setCard(card);

                if(!((card.getId() == 1) || (card.getId() == 2) || (card.getId() == 3) || (card.getId() == 4) || (card.getId() == 5))) {

                    dialogCartas = new Dialog(context);
                    dialogCartas.setContentView(R.layout.cartas_dialog);
                    dialogCartas.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    Window window = dialogCartas.getWindow();
                    window.setGravity(Gravity.CENTER);
                    window.getAttributes().windowAnimations = R.style.DialogAnimation;

                    CircleImageView imgAnimal = dialogCartas.findViewById(R.id.imgInfoAdminCartas);
                    TextView tvNombre = dialogCartas.findViewById(R.id.tvNombreInfoAdminCartas);
                    TextView tvBorrarInfoAdminCartas = dialogCartas.findViewById(R.id.tvBorrarInfoAdminCartas);
                    TextView tvEstaCartaNoSeModifica = dialogCartas.findViewById(R.id.tvEstaCartaNoSeModifica);
                    View viewBackInfoAdminCartas = dialogCartas.findViewById(R.id.viewBackInfoAdminCartas);
                    View viewBorrarInfoAdminCartas = dialogCartas.findViewById(R.id.viewBorrarInfoAdminCartas);
                    View viewEditarInfoAdminCartas = dialogCartas.findViewById(R.id.viewEditarInfoAdminCartas);
                    TextView tvEditarInfoAdminCartas = dialogCartas.findViewById(R.id.tvEditarInfoAdminCartas);

                    tvEstaCartaNoSeModifica.setVisibility(View.GONE);

                    viewBackInfoAdminCartas.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            dialogCartas.dismiss();

                        }
                    });

                    viewEditarInfoAdminCartas.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {

                            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                                viewEditarInfoAdminCartas.startAnimation(animScaleUp);
                                tvEditarInfoAdminCartas.startAnimation(animScaleUp);

                                dialogCartas.dismiss();
                                navController.navigate(R.id.action_adminCartasFragment_to_editCardFragment);


                            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                                viewEditarInfoAdminCartas.startAnimation(animScaleDown);
                                tvEditarInfoAdminCartas.startAnimation(animScaleDown);
                            }

                            return true;
                        }
                    });

                    viewBorrarInfoAdminCartas.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {

                            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                                viewBorrarInfoAdminCartas.startAnimation(animScaleUp);
                                tvBorrarInfoAdminCartas.startAnimation(animScaleUp);

                                dialogConfirmarBorrar = new Dialog(context);
                                dialogConfirmarBorrar.setContentView(R.layout.borrar_confirmation_dialog);
                                dialogConfirmarBorrar.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                                Window window = dialogConfirmarBorrar.getWindow();
                                window.setGravity(Gravity.CENTER);
                                window.getAttributes().windowAnimations = R.style.DialogAnimation;

                                TextView tvConfirmarBorrarMsg = dialogConfirmarBorrar.findViewById(R.id.tvConfirmarBorrarMsg);
                                View viewCancelarBorrarJugador = dialogConfirmarBorrar.findViewById(R.id.viewCancelarBorrarJugador);
                                View viewAceptarBorrarJugador = dialogConfirmarBorrar.findViewById(R.id.viewAceptarBorrarJugador);

                                tvConfirmarBorrarMsg.setText(R.string.tvConfirmarBorrarCarta);

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
                                        dialogCartas.dismiss();
                                        mp_borrar.start();

                                        long idBorrar = cardList.get(position).getId();
                                        viewModel.deleteCardById(idBorrar);

                                    }
                                });

                                dialogConfirmarBorrar.setCancelable(true);
                                dialogConfirmarBorrar.setCanceledOnTouchOutside(false);
                                window.setLayout(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
                                dialogConfirmarBorrar.show();

                            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                                viewBorrarInfoAdminCartas.startAnimation(animScaleDown);
                                tvBorrarInfoAdminCartas.startAnimation(animScaleDown);

                            }

                            return true;
                        }
                    });

                    RequestOptions options = new RequestOptions()
                            .centerCrop()
                            .placeholder(R.drawable.cargando)
                            .error(R.drawable.cerdi)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .priority(Priority.HIGH);

                    Glide.with(context).load(viewModel.cards.get(position).getPicUrl())
                    .apply(options)
                    .into(imgAnimal);

                    tvNombre.setText(viewModel.cards.get(position).getName());

                    dialogCartas.setCancelable(true);
                    dialogCartas.setCanceledOnTouchOutside(false);
                    window.setLayout(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
                    dialogCartas.show();

                } else {

                    dialogCartas = new Dialog(context);
                    dialogCartas.setContentView(R.layout.cartas_dialog);
                    dialogCartas.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    Window window = dialogCartas.getWindow();
                    window.setGravity(Gravity.CENTER);
                    window.getAttributes().windowAnimations = R.style.DialogAnimation;

                    CircleImageView imgAnimal = dialogCartas.findViewById(R.id.imgInfoAdminCartas);
                    TextView tvNombre = dialogCartas.findViewById(R.id.tvNombreInfoAdminCartas);
                    TextView tvBorrarInfoAdminCartas = dialogCartas.findViewById(R.id.tvBorrarInfoAdminCartas);
                    TextView tvEstaCartaNoSeModifica = dialogCartas.findViewById(R.id.tvEstaCartaNoSeModifica);
                    View viewBackInfoAdminCartas = dialogCartas.findViewById(R.id.viewBackInfoAdminCartas);
                    View viewBorrarInfoAdminCartas = dialogCartas.findViewById(R.id.viewBorrarInfoAdminCartas);
                    View viewEditarInfoAdminCartas = dialogCartas.findViewById(R.id.viewEditarInfoAdminCartas);
                    TextView tvEditarInfoAdminCartas = dialogCartas.findViewById(R.id.tvEditarInfoAdminCartas);

                    viewBackInfoAdminCartas.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            dialogCartas.dismiss();

                        }
                    });

                    viewBorrarInfoAdminCartas.setVisibility(View.GONE);
                    tvBorrarInfoAdminCartas.setVisibility(View.GONE);
                    viewEditarInfoAdminCartas.setVisibility(View.GONE);
                    tvEditarInfoAdminCartas.setVisibility(View.GONE);

                    RequestOptions options = new RequestOptions()
                            .centerCrop()
                            .placeholder(R.drawable.cargando)
                            .error(R.drawable.cerdi)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .priority(Priority.HIGH);

                    Glide.with(context).load(viewModel.cards.get(position).getPicUrl())
                            .apply(options)
                            .into(imgAnimal);

                    tvNombre.setText(viewModel.cards.get(position).getName());
                    tvEstaCartaNoSeModifica.setText("Esta carta no se puede modificar");

                    dialogCartas.setCancelable(true);
                    dialogCartas.setCanceledOnTouchOutside(false);
                    window.setLayout(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
                    dialogCartas.show();

                }

            }
        });

    }

    @Override
    public int getItemCount() {
        try{
            return viewModel.cards.size();
        } catch (Exception exception){
            return -1;
        }

    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imgFotoCartaNoAdmin;
        TextView tvNombreCartaNoAdmin, tvDescCartasNoAdminBack, tvAltura, tvPeso, tvLongitud, tvVelocidad, tvPoder, tvAlturaUnidad, tvPesoUnidad, tvLongitudUnidad, tvVelocidadUnidad;
        EasyFlipView easyFlipView;
        TextView tvClicParaHacerFlipCarta;
        View viewClicParaHacerFlipCarta;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgFotoCartaNoAdmin = itemView.findViewById(R.id.imgFotoCartaNoAdmin);
            tvNombreCartaNoAdmin = itemView.findViewById(R.id.tvNombreCartaNoAdmin);
            tvDescCartasNoAdminBack = itemView.findViewById(R.id.tvDescCartasNoAdminBack);
            easyFlipView = itemView.findViewById(R.id.easyflipview);
            tvClicParaHacerFlipCarta = itemView.findViewById(R.id.tvClicParaHacerFlipCarta);
            viewClicParaHacerFlipCarta = itemView.findViewById(R.id.viewClicParaHacerFlipCarta);

            tvAltura = itemView.findViewById(R.id.tvValorAlturaMediaNoAdmin);
            tvPeso = itemView.findViewById(R.id.tvPesoMedioNoAdmin);
            tvLongitud = itemView.findViewById(R.id.tvLongitudMediaNoAdmin);
            tvVelocidad = itemView.findViewById(R.id.tvVelocidadMediaNoAdmin);
            tvPoder = itemView.findViewById(R.id.tvPoderMortiferoNoAdmin);

            tvAlturaUnidad = itemView.findViewById(R.id.tvUnidadAlturaMediaNoAdmin);
            tvPesoUnidad = itemView.findViewById(R.id.tvUnidadPesoMedioNoAdmin);
            tvLongitudUnidad = itemView.findViewById(R.id.tvUnidadLongitudMediaNoAdmin);
            tvVelocidadUnidad = itemView.findViewById(R.id.tvUnidadVelocidadMediaNoAdmin);

            // Poner texto de la carta justificado
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                tvDescCartasNoAdminBack.setJustificationMode(LineBreaker.JUSTIFICATION_MODE_INTER_WORD);
            } else {
                tvDescCartasNoAdminBack.setGravity(Gravity.CENTER);
            }
        }
    }
}