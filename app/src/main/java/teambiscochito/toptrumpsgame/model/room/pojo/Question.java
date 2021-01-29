package teambiscochito.toptrumpsgame.model.room.pojo;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "question")
public class Question {

    //Pregunta: id, id de la carta, pregunta, respuesta , magnitud

    @PrimaryKey(autoGenerate = true)
    private long id;

    @NonNull
    @ColumnInfo(name = "card_id")
    private long card_id;

    @NonNull
    @ColumnInfo(name = "question")
    private String question;

    @NonNull
    @ColumnInfo(name = "answer")
    private Double answer;

    @ColumnInfo(name = "magnitude")
    private String magnitude;



    public Question(@NonNull long idcard, @NonNull String question, @NonNull Double answer) {
        this.card_id = idcard;
        this.question = question;
        this.answer = answer;
        this.magnitude = null;

    }

    public Question(@NonNull long idcard, @NonNull String question, @NonNull Double answer, String magnitude) {
        this.card_id = idcard;
        this.question = question;
        this.answer = answer;
        this.magnitude = magnitude;
    }


    public Question() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @NonNull
    public long getCard_id() {
        return card_id;
    }

    public void setCard_id(@NonNull long card_id) {
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
    public Double getAnswer() {
        return answer;
    }

    public void setAnswer(@NonNull Double answer) {
        this.answer = answer;
    }

    public String getMagnitude() {
        return magnitude;
    }

    public void setMagnitude(String magnitude) {
        this.magnitude = magnitude;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", card_id=" + card_id +
                ", question='" + question + '\'' +
                ", answer=" + answer +
                ", magnitude='" + magnitude + '\'' +
                '}';
    }
}
