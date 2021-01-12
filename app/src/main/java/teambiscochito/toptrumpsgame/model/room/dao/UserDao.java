package teambiscochito.toptrumpsgame.model.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.ArrayList;
import java.util.List;

import teambiscochito.toptrumpsgame.model.room.pojo.Question;
import teambiscochito.toptrumpsgame.model.room.pojo.User;

@Dao
public interface UserDao {

    @Delete
    int delete(User user);

    @Insert
    long insert(User user);

    @Update
    int update(User user);

    @Query("select * from user where id = :id")
    User getUserById(long id);

    //returs ALL questions about a card
    @Query("select * from user")
    LiveData<List<User>> getAllUser();
}
