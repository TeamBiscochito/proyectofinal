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

/**
 * <h2 align="center">Team Biscochito</h2><hr>
 * <p>
 * Clase de Laravel para obtener las cartas del servidor Web de Laravel alojado en:
 * <br>
 * <a href="https://informatica.ieszaidinvergeles.org:9022/LaravelFinal/TopTrump/">Laravel de la página web</a>
 */
public interface CardClient {

    @DELETE("carta/{id}")
    Call<Integer> deleteCard(@Path("id") long id);

    @GET("carta/{id}")
    Call<Card> getCardById(@Path("id") long id);

    /**
     * @return obtenemos todas las posibles cartas del servidor web
     */
    @GET("carta")
    Call<ArrayList<Card>> getAllCards();

    @POST("carta")
    Call<Long> postCard(@Body Card Carta);

    @PUT("carta/{id}")
    Call<Integer> putCard(@Path("id") long id, @Body Card Carta);

    /**
     * @return obtenemos todas las preguntas del servidor web
     */
    @GET("pregunta")
    Call<ArrayList<Question>> getAllQuestions();
}