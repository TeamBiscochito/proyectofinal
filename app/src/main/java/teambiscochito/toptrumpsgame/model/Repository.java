package teambiscochito.toptrumpsgame.model;

import android.content.Context;

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

    public CardDao cardDao;
    public QuestionDao questionDao;
    public UserDao userDao;
    private int repeatedName, repeatedNameCarta;
    CardClient Cardclient;

    public Repository(Context context) {
        GameDataBase db = GameDataBase.getDatabase(context);
        cardDao = db.getCardDao();
        questionDao = db.getQuestionDao();
        userDao = db.getUserDao();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://informatica.ieszaidinvergeles.org:9022/LaravelFinal/TopTrump/public/api/")
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
                } catch (Exception e) {
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
                } catch (Exception e) {
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
                } catch (Exception e) {
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
                } catch (Exception e) {
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
                } catch (Exception e) {
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
                } catch (Exception e) {
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
                } catch (Exception e) {
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
                } catch (Exception e) {
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
                } catch (Exception e) {
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
                } catch (Exception e) {
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
                } catch (Exception e) {
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

        return questionDao.getQuestionList();
    }

    public void getNameFromName(String name) {
        UtilThread.threadExecutorPool.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    repeatedName = userDao.getNameFromName(name);
                } catch (Exception e) {
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

    public void getNameFromNameCarta(String name) {
        UtilThread.threadExecutorPool.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    repeatedNameCarta = cardDao.getNameFromNameCarta(name);
                } catch (Exception e) {
                }
            }
        });

        try {
            Thread.sleep(30);
        } catch (InterruptedException e) {

        }
    }

    public int getRepeatedNameCarta() {

        return repeatedNameCarta;

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
                        cardArrayList[0] = response.body();
                        try {
                            Thread.sleep(200);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        for(Card c : cardArrayList[0]){
                        }

                    }
                    @Override
                    public void onFailure(Call<ArrayList<Card>> call, Throwable t) {
                    }
                });
            }
        });
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
                    questionDao.deleteById(id);
                } catch (Exception e) {
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