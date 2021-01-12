package teambiscochito.toptrumpsgame.model.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.ArrayList;
import java.util.List;

import teambiscochito.toptrumpsgame.model.room.pojo.Question;

@Dao
public interface QuestionDao {

    @Delete
    int delete(Question question);

    @Insert
    long insert(Question question);

    @Update
    int update(Question question);

    @Query("select * from question where id = :id")
    Question getQuestionById(long id);

    //returs ALL questions about a card
    @Query("select * from question where card_id = :cardId")
    LiveData<List<Question>> getQuestionByCardId(long cardId);

}
