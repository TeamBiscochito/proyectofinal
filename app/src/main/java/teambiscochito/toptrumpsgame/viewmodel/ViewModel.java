/*
 * Copyright (c) 2021. Team Biscochito.
 *
 * Licensed under the GNU General Public License v3.0
 *
 * https://www.gnu.org/licenses/gpl-3.0.html
 *
 * Permissions of this strong copyleft license are conditioned on making available complete
 * source code of licensed works and modifications, which include larger works using a licensed
 * work, under the same license. Copyright and license notices must be preserved. Contributors
 * provide an express grant of patent rights.
 */

package teambiscochito.toptrumpsgame.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import java.util.ArrayList;
import java.util.List;

import teambiscochito.toptrumpsgame.model.Repository;
import teambiscochito.toptrumpsgame.model.laravel.CardClient;
import teambiscochito.toptrumpsgame.model.room.pojo.Card;
import teambiscochito.toptrumpsgame.model.room.pojo.Question;
import teambiscochito.toptrumpsgame.model.room.pojo.User;

/**
 * <h2 align="center">Team Biscochito</h2><hr>
 * <p>
 * Clase que contiene el ViewModel, con el fin de almacenar y administrar datos relacionados con la
 * IU de manera optimizada para los ciclos de vida. La clase ViewModel permite que se conserven los
 * datos luego de cambios de configuración.
 */
@SuppressWarnings({"Convert2Lambda", "unused", "RedundantSuppression"})
// Comente la línea de arriba para ver los posibles Lambdas a convertir, y los métodos que no se usan
public class ViewModel extends AndroidViewModel {

    public static User userActual;
    public static List<Card> cards;
    public static List<Question> questions;
    private final Repository repository;
    private User user;
    private Card card;

    public ViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);

        repository.getQuestionList().observeForever(new Observer<List<Question>>() {
            @Override
            public void onChanged(List<Question> livequestions) {
                questions = livequestions;
            }
        });

        repository.getCardList().observeForever(new Observer<List<Card>>() {
            @Override
            public void onChanged(List<Card> livecards) {
                cards = livecards;
            }
        });

        repository.getQuestionList().observeForever(new Observer<List<Question>>() {
            @Override
            public void onChanged(List<Question> livequestions) {
                questions = livequestions;
            }
        });

        repository.getCardList().observeForever(new Observer<List<Card>>() {
            @Override
            public void onChanged(List<Card> livecards) {
                cards = livecards;
            }
        });
    }

    public void insertCard(Card card) {
        repository.insertCard(card);
    }

    public void updateCard(Card card) {
        repository.updateCard(card);
    }

    public void deleteCard(Card card) {
        repository.deleteCard(card);
    }

    public LiveData<List<Card>> getCardList() {
        return repository.getCardList();
    }

    public void insertUser(User user) {
        repository.insertUser(user);
    }

    public void updateUser(User user) {
        repository.updateUser(user);
    }

    public void deleteUser(User user) {
        repository.deleteUser(user);
    }

    public void deleteUserById(long id) {
        repository.deleteUserById(id);
    }

    public LiveData<List<User>> getUserList() {
        return repository.getUserList();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public void insertQuestion(Question question) {
        repository.insertQuestion(question);
    }

    public void updateQuestion(Question question) {
        repository.updateQuestion(question);
    }

    public void deleteQuestion(Question question) {
        repository.deleteQuestion(question);
    }

    public List<Question> getQuestionListByCardId(long cardId) {
        return repository.getQuestionListByCardId(cardId);
    }

    public LiveData<List<Question>> getQuestionList() {
        return repository.getQuestionList();
    }

    public void getNameFromName(String name) {
        repository.getNameFromName(name);
    }

    public void getNameFromNameCarta(String name) {
        repository.getNameFromNameCarta(name);
    }

    public int getRepeatedName() {
        return repository.getRepeatedName();
    }

    public int getRepeatedNameCarta() {
        return repository.getRepeatedNameCarta();
    }

    public Long getIdByName(String name) {
        return repository.getIdByName(name);
    }

    public Question getQuestionByName(String question, long idCard) {
        return repository.getQuestionByName(question, idCard);
    }

    public void deleteCardById(long id) {
        repository.deleteCardById(id);
    }

    public void insertToName(String name, String question, Double answer, String magnitude) {
        repository.insertToName(name, question, answer, magnitude);
    }

    public ArrayList<Question> getQuestionsForCurrentCard(Card card) {
        ArrayList<Question> result = new ArrayList<>();
        for (Question q : questions) {
            if (q.getCard_id() == card.getId()) {
                result.add(q);
            }
        }
        return result;
    }

    public void insertAll(ArrayList<Question> questionArrayList) {
        repository.insertAll(questionArrayList);
    }

    public List<Card> getAllCardsFromWeb() {
        return repository.getAllCardsFromWeb();
    }

    public CardClient getCardClient() {
        return repository.getCardClient();
    }

    public Card getCardByName(String name) {
        return repository.getCardByName(name);
    }
}