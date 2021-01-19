package teambiscochito.toptrumpsgame.model.room.pojo;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "user", indices = {@Index(value = {"name"}, unique = true)})
public class User {

    //Usuario: id, nombre, avatar, número de respuestas, número de respuestas correctas

    @PrimaryKey(autoGenerate = true)
    private long id;

    @NonNull
    @ColumnInfo(name = "name")
    private String name;

    @NonNull
    @ColumnInfo(name = "avatar")
    private int avatar;

    @NonNull
    @ColumnInfo(name = "answer") //numero de respuestas, por defecto 0
    private int answer;

    @NonNull
    @ColumnInfo(name = "TrueAnswer") //numero de ACIERTOS, por defecto 0
    private int TrueAnswer;

    public User(@NonNull String name, int avatar, int answer, int trueAnswer) {
        this.name = name;
        this.avatar = avatar;
        this.answer = answer;
        TrueAnswer = trueAnswer;
    }

    public User(@NonNull String name, int avatar){
        this.name = name;
        this.avatar = avatar;
        this.answer = 0;
        TrueAnswer = 0;
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

    public int getAvatar() {
        return avatar;
    }

    public void setAvatar(int avatar) {
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
