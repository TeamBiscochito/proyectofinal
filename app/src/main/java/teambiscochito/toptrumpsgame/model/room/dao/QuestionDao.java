package teambiscochito.toptrumpsgame.model.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import teambiscochito.toptrumpsgame.model.room.pojo.Question;

/**
 * <h2 align="center">Team Biscochito</h2><hr>
 * <p>
 * Interfaz room para la preguntas, donde generamos la tabla y las consultas pertinentes.
 */
@Dao
public interface QuestionDao {

    @Delete
    int delete(Question question);

    @Insert
    long insert(Question question);

    @Query("insert into question (card_id, question, answer, magnitude) values (:card_id, :question, :answer, :magnitude)")
    long insert(long card_id, String question, Double answer, String magnitude);

    @Query("Insert into question (card_id, question, answer, magnitude) values((select id from card where name = :name), :question, :answer, :magnitude)")
    void insertToName(String name, String question, Double answer, String magnitude);

    @Query("Insert into question (card_id, question, answer, magnitude) values((select id from card where name = :name), :question, :answer, null)")
    void insertToName(String name, String question, Double answer);

    @Update
    void update(Question question);

    @Query("select * from question where id = :id")
    Question getQuestionById(long id);

    /**
     * @param cardId id
     *
     * @return all questions about the card
     */
    @Query("select * from question where card_id = :cardId ")
    List<Question> getQuestionByCardId(long cardId);

    @Query("select * from question")
    LiveData<List<Question>> getQuestionList();

    @Query("delete from question")
    void deleteAll();

    @Query("select * from question where question = :question and card_id = :idCard ")
    Question getQuestionByName(String question, long idCard);

    @Query("delete from question where card_id = :id")
    void deleteById(long id);
}