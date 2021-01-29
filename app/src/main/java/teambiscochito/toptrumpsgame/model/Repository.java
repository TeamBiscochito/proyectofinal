package teambiscochito.toptrumpsgame.model;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Query;

import java.util.ArrayList;
import java.util.List;

import okhttp3.internal.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import teambiscochito.toptrumpsgame.R;
import teambiscochito.toptrumpsgame.model.laravel.CardClient;
import teambiscochito.toptrumpsgame.model.room.GameDataBase;
import teambiscochito.toptrumpsgame.model.room.dao.CardDao;
import teambiscochito.toptrumpsgame.model.room.dao.QuestionDao;
import teambiscochito.toptrumpsgame.model.room.dao.UserDao;
import teambiscochito.toptrumpsgame.model.room.pojo.Card;
import teambiscochito.toptrumpsgame.model.room.pojo.Question;
import teambiscochito.toptrumpsgame.model.room.pojo.User;
import teambiscochito.toptrumpsgame.util.UtilThread;

public class Repository {

    private GameDataBase db;

    public CardDao cardDao;
    public QuestionDao questionDao;
    public UserDao userDao;
    private int repeatedName;
    CardClient Cardclient;

    public Repository(Context context) {
        GameDataBase db = GameDataBase.getDatabase(context);
        cardDao = db.getCardDao();
        questionDao = db.getQuestionDao();
        userDao = db.getUserDao();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2/laravel/TopTrump/public/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Cardclient = retrofit.create(CardClient.class);

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

    public void deleteUserById(long id) {
        UtilThread.threadExecutorPool.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    userDao.deleteId(id);
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


    public void insertAll(ArrayList<Question> questionArrayList){
        UtilThread.threadExecutorPool.execute(new Runnable() {
            @Override
            public void run() {

                for(Question q : questionArrayList){
                    questionDao.insert(q);
                }
            }
        });
    }

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


    public List<Question> getQuestionListByCardId(long cardId) {
        final List<Question>[] questions = new List[]{new ArrayList<>()};

        UtilThread.threadExecutorPool.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    questions[0] = questionDao.getQuestionByCardId(cardId);
                    Log.v("xyz", "consulta a las preguntas (REPOSITORIO)");
                } catch (Exception e) {
                    Log.v("xyz", "ERROR(repositorio): " + e.toString());
                }
            }
        });
        try{
            Thread.sleep(50);
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
        return questions[0];
    }

    public LiveData<List<Question>> getQuestionList() {

        Log.v("xyz", "consulta a las preguntas (REPOSITORIO)");
        return questionDao.getQuestionList();
    }

    public void getNameFromName(String name) {
        UtilThread.threadExecutorPool.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    repeatedName = userDao.getNameFromName(name);
                } catch (Exception e) {
                    Log.v("xyz", "ERROR(repositorio): " + e.toString());
                }
            }
        });

        try {
            Thread.sleep(30);
        } catch (InterruptedException e) {

        }
    }

    public int getRepeatedName() {

        return repeatedName;

    }
    public List<Card> getAllCardsFromWeb() {
        final List<Card>[] cardArrayList = new List[]{new ArrayList<>()};

        UtilThread.threadExecutorPool.execute(new Runnable() {
            @Override
            public void run() {
                Call<ArrayList<Card>> cartaCall = Cardclient.getAllCards();
                cartaCall.enqueue(new Callback<ArrayList<Card>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Card>> call, Response<ArrayList<Card>> response) {
                        Log.v("xyzresponse", response.code()+"");
                        cardArrayList[0] = response.body();
                        try {
                            Thread.sleep(200);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        for(Card c : cardArrayList[0]){
                            Log.v("xyzbodyrep", c.toString());
                        }
                    }
                    @Override
                    public void onFailure(Call<ArrayList<Card>> call, Throwable t) {
                        Log.v("xyz errorgetallcards", t.getLocalizedMessage());
                    }
                });
            }
        });
        Log.v("xyz", "toma mongolin");
        return cardArrayList[0];
    }

    public void saveCards(Card Carta) {
        return;
    }


    public Long getIdByName(String name) {
        final long[] id = new long[1];
        UtilThread.threadExecutorPool.execute(new Runnable() {
            @Override
            public void run() {
                id[0] = cardDao.getIdByName(name);

            }
        });
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return id[0];
    }

    public Question getQuestionByName(String question, long idCard){
        final Question[] q = new Question[1];
        UtilThread.threadExecutorPool.execute(new Runnable() {
            @Override
            public void run() {
                q[0] = questionDao.getQuestionByName(question, idCard);
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        return q[0];

    }

    public void deleteCardById(long id) {
        UtilThread.threadExecutorPool.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    cardDao.deleteById(id);
                    Log.v("xyz", "borrada la carta con id: " + id);
                    questionDao.deleteById(id);
                } catch (Exception e) {
                    Log.v("xyz", "ERROR(repositorio): " + e.toString());
                }
            }
        });
    }

    public void insertToName(String name, String question, Double answer, String magnitude) {
        UtilThread.threadExecutorPool.execute(new Runnable() {
            @Override
            public void run() {
                questionDao.insertToName(name, question, answer, magnitude);
            }
        });
    }
    public CardClient getCardClient(){
        return Cardclient;
    }

    public Card getCardByName(String name){
        final Card[] c = new Card[1];
        UtilThread.threadExecutorPool.execute(new Runnable() {
            @Override
            public void run() {
               c[0] = cardDao.getCardByName(name);
            }
        });
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return c[0];
    }
}
