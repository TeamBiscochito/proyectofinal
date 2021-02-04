package teambiscochito.toptrumpsgame.model.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import teambiscochito.toptrumpsgame.model.room.pojo.User;

/**
 * <h2 align="center">Team Biscochito</h2><hr>
 * <p>
 * Interfaz room para manejar los usuarios, donde generamos la tabla y las consultas pertinentes.
 */
@SuppressWarnings("UnusedReturnValue")
// Comente la línea de arriba para ver los métodos que no usen su retorno de valor
@Dao
public interface UserDao {

    @Delete
    int delete(User user);

    @Insert
    long insert(User user);

    @Update
    void update(User user);

    @Query("delete from user where id = :id")
    int deleteId(long id);

    @Query("select * from user where name = :nombre")
    int getNameFromName(String nombre);

    @Query("select * from user where id = :id")
    User getUserById(long id);

    /**
     * @return all questions about the card
     */
    @Query("select * from user")
    LiveData<List<User>> getAllUser();
}