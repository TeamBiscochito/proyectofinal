package teambiscochito.toptrumpsgame.model.room.dao;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.ArrayList;
import java.util.List;

import teambiscochito.toptrumpsgame.model.room.pojo.Card;

@Dao
public interface CardDao {

    @Delete
    int delete(Card card);

    //un delete pero en query, porque no funciona muy bien el de arriba
    /*@Query("delete from card where id = :id")
    int delete(long id);*/

    @Insert
    long insert(Card card);

    @Update
    int update(Card card);

    //get one card
    @Query("select * from card where id = :id")
    Card getById(long id);

    //get ALL cards
    @Query("select * from card")
    LiveData<List<Card>> getAll();
}
