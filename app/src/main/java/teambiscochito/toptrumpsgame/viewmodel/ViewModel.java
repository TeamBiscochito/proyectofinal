package teambiscochito.toptrumpsgame.viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import java.util.List;

import teambiscochito.toptrumpsgame.model.Repository;
import teambiscochito.toptrumpsgame.model.room.pojo.Card;
import teambiscochito.toptrumpsgame.model.room.pojo.Question;
import teambiscochito.toptrumpsgame.model.room.pojo.User;

public class ViewModel extends AndroidViewModel {

    private Repository repository;
    private User user;
    public static User userActual;
    public static List<Card> cards;
    public static List<Question> questions;

    public ViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);

        //repository.getQuestionList()
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

    public LiveData<List<Card>> getCardLiveList() {
        return repository.getCardList();
    }

    public Card[] getAllCard() {
        return repository.getAllCard();
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
        repository.updateUser(user);
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

    public LiveData<List<Question>> getQuestionListByCardId(long cardId) {
        return repository.getQuestionListByCardId(cardId);
    }

    public LiveData<List<Question>> getQuestionList() {
        return repository.getQuestionList();
    }

    public void getNameFromName(String name) {
        repository.getNameFromName(name);
    }

    public int getRepeatedName() {

        return repository.getRepeatedName();

    }

}