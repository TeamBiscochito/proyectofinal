package teambiscochito.toptrumpsgame.model.room.pojo;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "user")
public class User {

    //Usuario: id, nombre, avatar, número de respuestas, número de respuestas correctas

    @PrimaryKey(autoGenerate = true)
    private long id;

    @NonNull
    @ColumnInfo(name = "name")
    private String name;

    //@NonNull //may have a default value?
    @ColumnInfo(name = "avatar")
    private String avatar;

    @NonNull
    @ColumnInfo(name = "answer")//numero de respuestas, por defecto 0
    private int answer = 0;

    @NonNull
    @ColumnInfo(name = "TrueAnswer")//numero de ACIERTOS, por defecto 0
    private int TrueAnswer = 0;

    public User(@NonNull String name, String avatar, int answer, int trueAnswer) {
        this.name = name;
        this.avatar = avatar;
        this.answer = answer;
        TrueAnswer = trueAnswer;
    }

    public User() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getAnswer() {
        return answer;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }

    public int getTrueAnswer() {
        return TrueAnswer;
    }

    public void setTrueAnswer(int trueAnswer) {
        TrueAnswer = trueAnswer;
    }
}
