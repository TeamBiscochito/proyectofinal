package teambiscochito.toptrumpsgame.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import teambiscochito.toptrumpsgame.model.Repository;
import teambiscochito.toptrumpsgame.model.room.pojo.Card;
import teambiscochito.toptrumpsgame.model.room.pojo.Question;
import teambiscochito.toptrumpsgame.model.room.pojo.User;

public class ViewModel extends AndroidViewModel {

    private Repository repository;
    public static User userActual;

    public ViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
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
}