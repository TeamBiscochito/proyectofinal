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

                cardDao.insert( new Card("https://informatica.ieszaidinvergeles.org:9027/ProyectoTopTrump/tigre.png",
                        "Tigre",
                        "Tigre es el nombre común que reciben los integrantes de la especie Panthera tigris. Este animal mamífero, que está considerado como el felino más grande del planeta, se caracteriza por su pelaje amarillo con rayas negras en el lomo."));
                questionDao.insertToName("Tigre","Altura", 95.0, "cm");
                questionDao.insertToName("Tigre","Peso", 200.0, "kg");
                questionDao.insertToName("Tigre","Longitud", 3.4, "m");
                questionDao.insertToName("Tigre","Velocidad", 65.0, "km/h");
                questionDao.insertToName("Tigre","Poder", 9.0);

                cardDao.insert( new Card("https://informatica.ieszaidinvergeles.org:9027/ProyectoTopTrump/elefante.jpg",
                        "Elefante",
                        "Son los animales terrestres más grandes del mundo, siendo el elefante africano el de mayor tamaño ya que puede llegar a pesar hasta 7.000 - 7.500 kg. Las hembras por lo regular son más pequeñas. La trompa del elefante tiene 100 mil músculos y tendones."));
                questionDao.insertToName("Elefante","Altura", 3.2, "m");
                questionDao.insertToName("Elefante","Peso", 6000.0, "kg");
                questionDao.insertToName("Elefante","Longitud", 5.7, "m");
                questionDao.insertToName("Elefante","Velocidad", 40.0, "km/h");
                questionDao.insertToName("Elefante","Poder", 6.0);

                cardDao.insert( new Card("https://informatica.ieszaidinvergeles.org:9027/ProyectoTopTrump/cocodrilo.jpg",
                        "Cocodrilo",
                        "Estos reptiles están adaptados a llevar un modo de vida semiacuática. Son excelentes nadadores y también pueden pasar poco tiempo en tierra firme. Además, su piel es dura, rígida y cubierta por resistentes escamas que sirven como armadura."));
                questionDao.insertToName("Cocodrilo","Altura", 30.0, "cm");
                questionDao.insertToName("Cocodrilo","Peso", 1000.0, "kg");
                questionDao.insertToName("Cocodrilo","Longitud", 7.0, "m");
                questionDao.insertToName("Cocodrilo","Velocidad", 30.0, "km/h");
                questionDao.insertToName("Cocodrilo","Poder", 7.0);

                cardDao.insert( new Card("https://informatica.ieszaidinvergeles.org:9027/ProyectoTopTrump/oso.jpg",
                        "Oso",
                        "Los osos se caracterizan por su cabeza de gran tamaño, orejas pequeñas y redondeadas, ojos pequeños, un cuerpo pesado, robusto y una cola corta. Las patas son cortas y poderosas, con cinco dedos provistos de uñas fuertes y recurvadas garras."));
                questionDao.insertToName("Oso","Altura", 1.5, "m");
                questionDao.insertToName("Oso","Peso", 200.0, "kg");
                questionDao.insertToName("Oso","Longitud", 2.0, "m");
                questionDao.insertToName("Oso","Velocidad", 56.0, "km/h");
                questionDao.insertToName("Oso","Poder", 7.0);

                cardDao.insert( new Card("https://informatica.ieszaidinvergeles.org:9027/ProyectoTopTrump/tortuga.jpg",
                        "Tortuga",
                        "Las tortugas son un tipo de reptiles caracterizados por el sólido caparazón que protege sus órganos vitales del que emergen la cabeza, las patas y la cola. Son animales ovíparos que cavan sus nidos en la tierra, donde llevan a cabo la incubación de los huevos."));
                questionDao.insertToName("Tortuga","Altura", 30.0, "cm");
                questionDao.insertToName("Tortuga","Peso", 800.0, "kg");
                questionDao.insertToName("Tortuga","Longitud", 1.0, "m");
                questionDao.insertToName("Tortuga","Velocidad", 60.0, "km/h");
                questionDao.insertToName("Tortuga","Poder", 9.0);


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
