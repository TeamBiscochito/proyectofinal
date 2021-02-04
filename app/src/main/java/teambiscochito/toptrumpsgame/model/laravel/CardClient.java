package teambiscochito.toptrumpsgame.model.laravel;

import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import teambiscochito.toptrumpsgame.model.room.pojo.Card;
import teambiscochito.toptrumpsgame.model.room.pojo.Question;

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

    @GET("pregunta")
    Call<ArrayList<Question>> getAllQuestions();

}