package teambiscochito.toptrumpsgame.model.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
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

    @Query("insert into question (card_id, question, answer, magnitude) values (:card_id, :question, :answer, :magnitude)")
    long insert(long card_id, String question, Double answer, String magnitude);

    @Query("Insert into question (card_id, question, answer, magnitude) values((select id from card where name = :name), :question, :answer, :magnitude)")
    void insertToName(String name , String question, Double answer, String magnitude);

    @Query("Insert into question (card_id, question, answer, magnitude) values((select id from card where name = :name), :question, :answer, null)")
    void insertToName(String name , String question, Double answer);

    @Update
    int update(Question question);

    @Query("select * from question where id = :id")
    Question getQuestionById(long id);

    //returs ALL questions about a card
    @Query("select * from question where card_id = :cardId")
    LiveData<List<Question>> getQuestionByCardId(long cardId);

    @Query("select * from question")
    LiveData<List<Question>> getQuestionList();

    @Query("delete from question")
    void deleteAll();


}
