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

package teambiscochito.toptrumpsgame.model.room.dao;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import teambiscochito.toptrumpsgame.model.room.pojo.Card;

/**
 * <h2 align="center">Team Biscochito</h2><hr>
 * <p>
 * Interfaz room para la carta, donde generamos la tabla y las consultas pertinentes.
 */
@SuppressWarnings("UnusedReturnValue")
// Comente la línea de arriba para ver los métodos que no usen su retorno de valor
@Dao
public interface CardDao {

    @Delete
    int delete(Card card);

    @Insert
    long insert(Card card);

    @Update
    void update(Card card);

    /**
     * @param id id de la carta para obtener la carta
     *
     * @return Get one card
     */
    @Query("select * from card where id = :id")
    Card getById(long id);

    /**
     * @param name name of the card and we got the
     *
     * @return Get one card
     */
    @Query("select id from card where name = :name")
    Long getIdByName(String name);

    /**
     * @param nombre nombre de la carta que queremos obtener
     *
     * @return Get the card with the name
     */
    @Query("select * from card where name = :nombre")
    int getNameFromNameCarta(String nombre);

    /**
     * @return Get all cards
     */
    @Query("select * from card")
    LiveData<List<Card>> getAll();

    @Query("delete from card")
    void deleteAll();

    @Query("delete from card where id = :id")
    void deleteById(long id);

    @Query("select * from card where name = :name")
    Card getCardByName(String name);
}