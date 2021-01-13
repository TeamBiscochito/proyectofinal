package teambiscochito.toptrumpsgame.model.room.pojo;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "question",
        foreignKeys = @ForeignKey(
                entity = Card.class,
                parentColumns = "id",
                childColumns = "card_id",
                onDelete = ForeignKey.CASCADE))
public class Question {

    //Pregunta: id, id de la carta, pregunta, respuesta

    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name = "card_id")
    private long card_id;

    @NonNull
    @ColumnInfo(name = "question")
    private String question;

    @NonNull
    @ColumnInfo(name = "answer")
    private String answer;

    public Question(long idcard, @NonNull String question, @NonNull String answer) {
        this.card_id = idcard;
        this.question = question;
        this.answer = answer;
    }

    public Question() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCard_id() {
        return card_id;
    }

    public void setCard_id(long card_id) {
        this.card_id = card_id;
    }

    @NonNull
    public String getQuestion() {
        return question;
    }

    public void setQuestion(@NonNull String question) {
        this.question = question;
    }

    @NonNull
    public String getAnswer() {
        return answer;
    }

    public void setAnswer(@NonNull String answer) {
        this.answer = answer;
    }
}
