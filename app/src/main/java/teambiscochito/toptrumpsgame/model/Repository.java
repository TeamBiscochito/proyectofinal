package teambiscochito.toptrumpsgame.model;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import teambiscochito.toptrumpsgame.model.room.GameDataBase;
import teambiscochito.toptrumpsgame.model.room.dao.CardDao;
import teambiscochito.toptrumpsgame.model.room.dao.QuestionDao;
import teambiscochito.toptrumpsgame.model.room.dao.UserDao;
import teambiscochito.toptrumpsgame.model.room.pojo.Card;
import teambiscochito.toptrumpsgame.model.room.pojo.Question;
import teambiscochito.toptrumpsgame.model.room.pojo.User;
import teambiscochito.toptrumpsgame.util.UtilThread;

public class Repository {


    public CardDao cardDao;
    public QuestionDao questionDao;
    public UserDao userDao;

    public Repository(Context context) {
        GameDataBase db = GameDataBase.getDb(context);
        cardDao = db.getCardDao();
        questionDao = db.getQuestionDao();
        userDao = db.getUserDao();
    }

    /*-------- Cards --------*/
    public void insertCard(Card card) {
        UtilThread.threadExecutorPool.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    long id = cardDao.insert(card);
                    Log.v("xyz", "insertada la carta: " + id);
                } catch (Exception e) {
                    Log.v("xyz", "ERROR(repositorio): " + e.toString());
                }
            }
        });
    }

    public void updateCard(Card card) {
        UtilThread.threadExecutorPool.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    cardDao.update(card);
                    Log.v("xyz", "update la carta: " + card.toString());
                } catch (Exception e) {
                    Log.v("xyz", "ERROR(repositorio): " + e.toString());
                }
            }
        });
    }

    public void deleteCard(Card card) {
        UtilThread.threadExecutorPool.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    long id = cardDao.delete(card);
                    Log.v("xyz", "borrada la carta" + id);
                } catch (Exception e) {
                    Log.v("xyz", "ERROR(repositorio): "+e.toString());
                }
            }
        });
    }


    public LiveData<List<Card>> getCardList() {
        return cardDao.getAll();
    }


    /*-------- Users --------*/

    public void insertUser(User user) {
        UtilThread.threadExecutorPool.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    long id = userDao.insert(user);
                    Log.v("xyz", "insertado el usuario " + id);
                } catch (Exception e) {
                    Log.v("xyz", "ERROR(repositorio): " + e.toString());
                }
            }
        });
    }

    public void updateUser(User user) {
        UtilThread.threadExecutorPool.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    userDao.update(user);
                    Log.v("xyz", "update user: " + user.toString());
                } catch (Exception e) {
                    Log.v("xyz", "ERROR(repositorio): " + e.toString());
                }
            }
        });
    }

    public void deleteUser(User user) {
        UtilThread.threadExecutorPool.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    long id = userDao.delete(user);
                    Log.v("xyz", "borrado el user: " + id);
                } catch (Exception e) {
                    Log.v("xyz", "ERROR(repositorio): " + e.toString());
                }
            }
        });
    }


    public LiveData<List<User>> getUserList() {
        return userDao.getAllUser();
    }


    /*-------- Questions --------*/


    public void insertQuestion(Question question) {
        UtilThread.threadExecutorPool.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    long id = questionDao.insert(question);
                    Log.v("xyz", "insertada la pregunta " + id);
                } catch (Exception e) {
                    Log.v("xyz", "ERROR(repositorio): " + e.toString());
                }
            }
        });
    }

    public void updateQuestion(Question question) {
        UtilThread.threadExecutorPool.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    questionDao.update(question);
                    Log.v("xyz", "update question: " + question.toString());
                } catch (Exception e) {
                    Log.v("xyz", "ERROR(repositorio): " + e.toString());
                }
            }
        });
    }

    public void deleteQuestion(Question question) {
        UtilThread.threadExecutorPool.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    long id = questionDao.delete(question);
                    Log.v("xyz", "borrada la pregunta: " + id);
                } catch (Exception e) {
                    Log.v("xyz", "ERROR(repositorio): " + e.toString());
                }
            }
        });
    }


    public LiveData<List<Question>> getQuestionListByCardId(long cardId) {
        return questionDao.getQuestionByCardId(cardId);
    }

}