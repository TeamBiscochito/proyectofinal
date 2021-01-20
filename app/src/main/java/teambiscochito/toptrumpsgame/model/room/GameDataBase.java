package teambiscochito.toptrumpsgame.model.room;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import teambiscochito.toptrumpsgame.model.room.dao.CardDao;
import teambiscochito.toptrumpsgame.model.room.dao.QuestionDao;
import teambiscochito.toptrumpsgame.model.room.dao.UserDao;
import teambiscochito.toptrumpsgame.model.room.pojo.Card;
import teambiscochito.toptrumpsgame.model.room.pojo.Question;
import teambiscochito.toptrumpsgame.model.room.pojo.User;

import static teambiscochito.toptrumpsgame.util.UtilThread.threadExecutorPool;

@Database(entities = {Card.class, Question.class, User.class}, version = 1, exportSchema = false)
public abstract class GameDataBase extends RoomDatabase {

    public abstract CardDao getCardDao();
    public abstract QuestionDao getQuestionDao();
    public abstract UserDao getUserDao();

    private volatile static GameDataBase INSTANCE;

    static synchronized GameDataBase getDb(final Context context) {
        if(INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    GameDataBase.class, "gamedb.sqlite")
                    .build();
        }
        return INSTANCE;
    }


    private static RoomDatabase.Callback callback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            threadExecutorPool.execute(() -> {
                CardDao cardDao = INSTANCE.getCardDao();
                QuestionDao questionDao = INSTANCE.getQuestionDao();
                //INICIALIZAR O HACER PRUEBAS CON VALORES INICIALES EN LA BASE DE DATOS

                cardDao.insert( new Card("https://informatica.ieszaidinvergeles.org:9027/Proyecto%20Top%20Trump/tigre.png",
                        "Tigre",
                        "desc"));
                questionDao.insertToName("Tigre","Altura", 95.0, "Cm");
                questionDao.insertToName("Tigre","Peso", 200.0, "Kg");
                questionDao.insertToName("Tigre","Longitud", 3.4, "M");
                questionDao.insertToName("Tigre","Velocidad", 65.0, "Km/h");
                questionDao.insertToName("Tigre","Poder mortífero", 9.0);

                cardDao.insert( new Card("https://informatica.ieszaidinvergeles.org:9027/Proyecto%20Top%20Trump/tigre.png",
                        "Elefante",
                        "desc"));
                questionDao.insertToName("Elefante","Altura", 3.2, "M");
                questionDao.insertToName("Elefante","Peso", 6000.0, "Kg");
                questionDao.insertToName("Elefante","Longitud", 5.7, "M");
                questionDao.insertToName("Elefante","Velocidad", 40.0, "Km/h");
                questionDao.insertToName("Elefante","Poder mortífero", 6.0);

                cardDao.insert( new Card("https://informatica.ieszaidinvergeles.org:9027/Proyecto%20Top%20Trump/tigre.png",
                        "Cocodrilo",
                        "desc"));
                questionDao.insertToName("Cocodrilo","Altura", 30.0, "Cm");
                questionDao.insertToName("Cocodrilo","Peso", 1000.0, "Kg");
                questionDao.insertToName("Cocodrilo","Longitud", 7.0, "M");
                questionDao.insertToName("Cocodrilo","Velocidad", 30.0, "Km/h");
                questionDao.insertToName("Cocodrilo","Poder mortífero", 7.0);

                cardDao.insert( new Card("https://informatica.ieszaidinvergeles.org:9027/Proyecto%20Top%20Trump/tigre.png",
                        "Oso",
                        "desc"));
                questionDao.insertToName("Oso","Altura", 1.5, "M");
                questionDao.insertToName("Oso","Peso", 200.0, "Kg");
                questionDao.insertToName("Oso","Longitud", 2.0, "M");
                questionDao.insertToName("Oso","Velocidad", 56.0, "Km/h");
                questionDao.insertToName("Oso","Poder mortífero", 7.0);

                cardDao.insert( new Card("https://informatica.ieszaidinvergeles.org:9027/Proyecto%20Top%20Trump/tigre.png",
                        "Tortuga",
                        "desc"));
                questionDao.insertToName("Tortuga","Altura", 30.0, "Cm");
                questionDao.insertToName("Tortuga","Peso", 800.0, "Kg");
                questionDao.insertToName("Tortuga","Longitud", 1.0, "M");
                questionDao.insertToName("Tortuga","Velocidad", 60.0, "Km/h");
                questionDao.insertToName("Tortuga","Poder mortífero", 9.0);


            });
        }
    };

    public static GameDataBase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (GameDataBase.class) {
                if (INSTANCE == null) {
                    INSTANCE =  Room.databaseBuilder(context.getApplicationContext(),
                            GameDataBase.class, "gamedb.sqlite")
                            .addCallback(callback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}
