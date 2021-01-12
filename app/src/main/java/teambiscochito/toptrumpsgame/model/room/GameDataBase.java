package teambiscochito.toptrumpsgame.model.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import teambiscochito.toptrumpsgame.model.room.dao.CardDao;
import teambiscochito.toptrumpsgame.model.room.dao.QuestionDao;
import teambiscochito.toptrumpsgame.model.room.dao.UserDao;
import teambiscochito.toptrumpsgame.model.room.pojo.Card;
import teambiscochito.toptrumpsgame.model.room.pojo.Question;
import teambiscochito.toptrumpsgame.model.room.pojo.User;

@Database(entities = {Card.class, Question.class, User.class}, version = 1, exportSchema = false)
public abstract class GameDataBase extends RoomDatabase {

    public abstract CardDao getCarDao();
    public abstract QuestionDao getQuestionDao();
    public abstract UserDao getUserDao();

    private volatile static GameDataBase INSTANCE;

    public static synchronized GameDataBase getDb(final Context context) {
        if(INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    GameDataBase.class, "gamedb.sqlite")
                    .build();
        }
        return INSTANCE;
    }
}
