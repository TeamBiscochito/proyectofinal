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
import teambiscochito.toptrumpsgame.view.fragment.administrar.EditCardFragment;
import teambiscochito.toptrumpsgame.viewmodel.ViewModel;

/**
 * <h2 align="center">Team Biscochito</h2><hr>
 * <p>
 * Clase Recycler para el fragmento de administración de cartas.
 */
@SuppressWarnings({"Convert2Lambda"})
// Comente la línea de arriba para ver los posibles Lambdas a convertir
public class RecyclerCartasAdminAdapter extends RecyclerView.Adapter<RecyclerCartasAdminAdapter.ViewHolder> {
    private final List<Card> cardList;
    private final View view;
    private final Activity activity;
    private final Context context;
    private Animation animScaleUp, animScaleDown;
    private ViewModel viewModel;
    private Dialog dialogCartas, dialogConfirmarBorrar;

    NavController navController;
    private MediaPlayer mp_borrar;

    public RecyclerCartasAdminAdapter(List<Card> cardList, View view, Activity activity, Context context) {
        this.cardList = cardList;
        this.view = view;
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
        viewModel = new ViewModelProvider((ViewModelStoreOwner) activity).get(ViewModel.class);

        animScaleUp = AnimationUtils.loadAnimation(context, R.anim.scale_up);
        animScaleDown = AnimationUtils.loadAnimation(context, R.anim.scale_down);

        mp_borrar = MediaPlayer.create(context, R.raw.borrar_sound);

        Card card = ViewModel.cards.get(position);

        navController = Navigation.findNavController(view);

        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.cargando)
                .error(R.drawable.cerdi)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH);

        Glide.with(context).load(ViewModel.cards.get(position).getPicUrl())
                .apply(options)
                .into(holder.imgFotoCartaNoAdmin);

        holder.tvNombreCartaNoAdmin.setText(ViewModel.cards.get(position).getName());
        holder.tvDescCartasNoAdminBack.setText(ViewModel.cards.get(position).getDescription());

        try {
            getDatos(holder, position);
        } catch (Exception ignored) {
        }

        flipCarta(holder);

        modificarCartaSiNo(holder, position, card);
    }

    /**
     * <h2 align="center">Team Biscochito</h2><hr>
     * <p>
     * Método para decir si la carta se tiene que modifcar o no. Establece las primeras 5 cartas
     * generadas por defecto a la instalación del juego que no se puedan modificar ni editar, que
     * estén completamente bloqueadas.
     * <br>
     * Método implementado en: {@link RecyclerCartasAdminAdapter#onBindViewHolder(ViewHolder, int)}
     *
     * @param holder   pasamos el item.
     * @param position pasamos la posicioón.
     * @param card     pasamos la carta.
     */
    private void modificarCartaSiNo(@NonNull ViewHolder holder, int position, Card card) {
        holder.tvNombreCartaNoAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.setCard(card);

                if (!((card.getId() == 1) || (card.getId() == 2) || (card.getId() == 3)
                        || (card.getId() == 4) || (card.getId() == 5))) {
                    modificarCarta(position);
                } else {
                    cartasNoModificar(position);
                }
            }
        });
    }

    /**
     * <h2 align="center">Team Biscochito</h2><hr>
     * <p>
     * Método con el que hacemos que la carta gire para que muestre la descripción de la carta una
     * vez que esté girada.
     * <br>
     * Método implementado en: {@link RecyclerCartasAdminAdapter#onBindViewHolder(ViewHolder, int)}
     *
     * @param holder item que pasamos del ViewHolder.
     */
    private void flipCarta(@NonNull ViewHolder holder) {
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
                    default:
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        try {
            return ViewModel.cards.size();
        } catch (Exception exception) {
            return -1;
        }
    }

    /**
     * <h2 align="center">Team Biscochito</h2><hr>
     * <p>
     * Método para obtener las cartas con sus respectivos datos, este método es llamado
     * por {@link RecyclerCartasAdminAdapter#onBindViewHolder(ViewHolder, int)}
     *
     * @param holder   pasamos el holder del recycler.
     * @param position posición del recycler.
     */
    public void getDatos(ViewHolder holder, int position) {
        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setMaximumFractionDigits(0);

        List<Question> questionList = viewModel.getQuestionsForCurrentCard(ViewModel.cards.get(position));

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

    /**
     * <h2 align="center">Team Biscochito</h2><hr>
     * <p>
     * Método específico para las cartas que no se pueden modificar en este caso son las 5 primeras.
     * <br><br>
     * Referencia del método en {@link #modificarCartaSiNo}
     *
     * @param position posición actual del recycler.
     */
    public void cartasNoModificar(int position) {
        dialogCartas = new Dialog(context);
        dialogCartas.setContentView(R.layout.cartas_dialog);
        dialogCartas.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        Window window = dialogCartas.getWindow();
        window.setGravity(Gravity.CENTER);
        window.getAttributes().windowAnimations = R.style.DialogAnimation;

        CircleImageView imgAnimal = dialogCartas.findViewById(R.id.imgDialogCartasPerfil);
        TextView tvNombre = dialogCartas.findViewById(R.id.tvDialogCartasNombre);
        TextView tvBorrarInfoAdminCartas = dialogCartas.findViewById(R.id.tvDialogCartasBorrar);
        TextView tvEstaCartaNoSeModifica = dialogCartas.findViewById(R.id.tvDialogCartasNoModifica);
        View viewBackInfoAdminCartas = dialogCartas.findViewById(R.id.viewDialogCartasBack);
        View viewBorrarInfoAdminCartas = dialogCartas.findViewById(R.id.viewDialogCartasBorrar);
        View viewEditarInfoAdminCartas = dialogCartas.findViewById(R.id.viewDialogCartasEditar);
        TextView tvEditarInfoAdminCartas = dialogCartas.findViewById(R.id.tvDialogCartasEditar);

        viewBackInfoAdminCartas.setOnClickListener(v12 -> dialogCartas.dismiss());

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

        Glide.with(context).load(ViewModel.cards.get(position).getPicUrl())
                .apply(options)
                .into(imgAnimal);

        tvNombre.setText(ViewModel.cards.get(position).getName());
        tvEstaCartaNoSeModifica.setText(R.string.cartaNoSePuedeModificar);

        dialogCartas.setCancelable(true);
        dialogCartas.setCanceledOnTouchOutside(false);
        window.setLayout(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        dialogCartas.show();
    }

    /**
     * <h2 align="center">Team Biscochito</h2><hr>
     * <p>
     * Método específico para las cartas que si se pueden modificar en este caso son las que
     * importaremos de internet.
     * <br><br>
     * Este método es usado específicamente en {@link #modificarCartaSiNo}
     *
     * @param position posición actual del recycler.
     */
    public void modificarCarta(int position) {
        dialogCartas = new Dialog(context);
        dialogCartas.setContentView(R.layout.cartas_dialog);
        dialogCartas.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        Window window = dialogCartas.getWindow();
        window.setGravity(Gravity.CENTER);
        window.getAttributes().windowAnimations = R.style.DialogAnimation;

        CircleImageView imgAnimal = dialogCartas.findViewById(R.id.imgDialogCartasPerfil);
        TextView tvNombre = dialogCartas.findViewById(R.id.tvDialogCartasNombre);
        TextView tvBorrarInfoAdminCartas = dialogCartas.findViewById(R.id.tvDialogCartasBorrar);
        TextView tvEstaCartaNoSeModifica = dialogCartas.findViewById(R.id.tvDialogCartasNoModifica);
        View viewBackInfoAdminCartas = dialogCartas.findViewById(R.id.viewDialogCartasBack);
        View viewBorrarInfoAdminCartas = dialogCartas.findViewById(R.id.viewDialogCartasBorrar);
        View viewEditarInfoAdminCartas = dialogCartas.findViewById(R.id.viewDialogCartasEditar);
        TextView tvEditarInfoAdminCartas = dialogCartas.findViewById(R.id.tvDialogCartasEditar);

        tvEstaCartaNoSeModifica.setVisibility(View.GONE);

        viewBackInfoAdminCartas.setOnClickListener(v1 -> dialogCartas.dismiss());

        editInfoAdmin(viewEditarInfoAdminCartas, tvEditarInfoAdminCartas);

        borrarInfoAdmin(position, tvBorrarInfoAdminCartas, viewBorrarInfoAdminCartas);

        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.cargando)
                .error(R.drawable.cerdi)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH);

        Glide.with(context).load(ViewModel.cards.get(position).getPicUrl())
                .apply(options)
                .into(imgAnimal);

        tvNombre.setText(ViewModel.cards.get(position).getName());

        dialogCartas.setCancelable(true);
        dialogCartas.setCanceledOnTouchOutside(false);
        window.setLayout(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        dialogCartas.show();
    }

    /**
     * <h2 align="center">Team Biscochito</h2><hr>
     * <p>
     * Método que actúa cuando pulsamos borrar, es decir borramos la carta que tendríamos en nuestra
     * base de datos.
     * <br><br>
     * Referencia del método en: {@link #modificarCarta}
     *
     * @param position                  posición de la carta seleccionada.
     * @param tvBorrarInfoAdminCartas   TextView que esta en la parte del {@link #modificarCarta(int)}
     *                                  donde instanciamos el contenido del diálogo.
     * @param viewBorrarInfoAdminCartas View que está {@link #modificarCarta(int)}, donde hacemos el
     *                                  evento al hacer click en la vista.
     */
    private void borrarInfoAdmin(int position, TextView tvBorrarInfoAdminCartas, View viewBorrarInfoAdminCartas) {
        viewBorrarInfoAdminCartas.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        viewBorrarInfoAdminCartas.startAnimation(animScaleUp);
                        tvBorrarInfoAdminCartas.startAnimation(animScaleUp);

                        dialogConfirmarBorrar = new Dialog(context);
                        dialogConfirmarBorrar.setContentView(R.layout.borrar_confirmation_dialog);
                        dialogConfirmarBorrar.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                        Window window = dialogConfirmarBorrar.getWindow();
                        window.setGravity(Gravity.CENTER);
                        window.getAttributes().windowAnimations = R.style.DialogAnimation;

                        TextView tvConfirmarBorrarMsg = dialogConfirmarBorrar.findViewById(R.id.tvDialogBorrarConfirmText);
                        View viewCancelarBorrarJugador = dialogConfirmarBorrar.findViewById(R.id.viewDialogBorrarConfirmCancel);
                        View viewAceptarBorrarJugador = dialogConfirmarBorrar.findViewById(R.id.viewDialogBorrarConfirmAccept);

                        tvConfirmarBorrarMsg.setText(R.string.tvConfirmarBorrarCarta);

                        viewCancelarBorrarJugador.setOnClickListener(v13 -> dialogConfirmarBorrar.dismiss());

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
                    case MotionEvent.ACTION_UP:
                        viewBorrarInfoAdminCartas.startAnimation(animScaleDown);
                        tvBorrarInfoAdminCartas.startAnimation(animScaleDown);
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
     * Método que actúa cuando pulsamos en editar, en este caso se redirige a un nuevo fragmento que
     * es específico para editar nuestra carta seleccionada. Fragmento al que redirecciona el
     * navController: {@link EditCardFragment}
     * <br><br>
     * Referencia del método en: {@link #modificarCarta}
     *
     * @param viewEditarInfoAdminCartas View que está {@link #modificarCarta(int)}, donde hacemos el
     *                                  evento al hacer click en la vista.
     * @param tvEditarInfoAdminCartas   TextView que esta en la parte del {@link #modificarCarta(int)}
     *                                  donde instanciamos el contenido del diálogo.
     */
    private void editInfoAdmin(View viewEditarInfoAdminCartas, TextView tvEditarInfoAdminCartas) {
        viewEditarInfoAdminCartas.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        viewEditarInfoAdminCartas.startAnimation(animScaleUp);
                        tvEditarInfoAdminCartas.startAnimation(animScaleUp);

                        dialogCartas.dismiss();
                        navController.navigate(R.id.action_adminCartasFragment_to_editCardFragment);
                        break;
                    case MotionEvent.ACTION_UP:
                        viewEditarInfoAdminCartas.startAnimation(animScaleDown);
                        tvEditarInfoAdminCartas.startAnimation(animScaleDown);
                        v.performClick();
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgFotoCartaNoAdmin;
        TextView tvNombreCartaNoAdmin, tvDescCartasNoAdminBack, tvAltura, tvPeso, tvLongitud, tvVelocidad,
                tvPoder, tvAlturaUnidad, tvPesoUnidad, tvLongitudUnidad, tvVelocidadUnidad;
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