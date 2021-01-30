package teambiscochito.toptrumpsgame.model.laravel;

/*import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import teambiscochito.toptrumpsgame.model.room.pojo.Card;*/

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import teambiscochito.toptrumpsgame.model.room.pojo.Card;

public interface CardClient {
    @DELETE("carta/{id}")
    Call<Integer> deleteCard(@Path("id") long id);

    @GET("carta/{id}")
    Call<Card> getCardById(@Path("id") long id);

    @GET("carta")
    Call<ArrayList<Card>> getAllCards();

    @POST("carta")
    Call<Long> postCard(@Body Card Carta);

    @PUT("carta/{id}")
    Call<Integer> putCard(@Path("id") long id, @Body Card Carta);


/* private void init() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2/laravel/TopTrump/public/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        client = retrofit.create(TypeClient.class);
        inserbt = findViewById(R.id.insertbt);


               //Insertar una carta

                Call<Long> call = client.postType(new Card("picurl", "nombre", "descripcion"));
                call.enqueue(new Callback<Long>() {
                    @Override
                    public void onResponse(Call<Long> call, Response<Long> response) {
                        Log.v("zyx exito", response.body().toString());
                    }

                    @Override
                    public void onFailure(Call<Long> call, Throwable t) {
                        Log.v("xyz error", t.getLocalizedMessage());
                    }
                });
            }

             //Obtener toda la lista de cartas
        Call<ArrayList<Card>> cartaCall = client.getcartas();
        cartaCall.enqueue(new Callback<ArrayList<Carta>>() {
            @Override
            public void onResponse(Call<ArrayList<Carta>> call, Response<ArrayList<Carta>> response) {
                listaCartas = response.body();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                initRecycler();

            }

            @Override
            public void onFailure(Call<ArrayList<Card>> call, Throwable t) {
                Log.v("xyz error", t.getLocalizedMessage());

            }
        });
    }ç
    ---Implementaciones del gradle para laravel y Glide---
*   implementation 'com.squareup.retrofit2:retrofit:2.9.0'
    implementation 'com.squareup.retrofit2:converter-gson:2.9.0'
    implementation'com.github.bumptech.glide:glide:4.7.1'
*
*   ------Adapter de las cartas------
* public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>  {

    ArrayList<Carta> lista;
    Context context;

    public RecyclerViewAdapter(ArrayList<Carta> lista, Context context){
        this.lista = lista;
        this.context = context;
        Log.v("zyxrecycler", lista.toString());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemlista, parent,false);
        ViewHolder holder = new ViewHolder(vista);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {
        holder.tvnombre.setText(lista.get(position).getNombre());
        Glide.with(context).load(lista.get(position).getPicurl()).into(holder.iv);
        holder.tvdesc.setText(lista.get(position).getDescripcion());
    }


    @Override
    public int getItemCount() {
        return lista.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView iv;
        TextView tvnombre, tvdesc;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.iv);
            tvnombre = itemView.findViewById(R.id.tvnombre);
            tvdesc = itemView.findViewById(R.id.tvdesc);
        }
    }



}
*
* */





}