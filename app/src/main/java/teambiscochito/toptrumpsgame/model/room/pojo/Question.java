package teambiscochito.toptrumpsgame.model.room.pojo;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * <h2 align="center">Team Biscochito</h2><hr>
 * <p>
 * Clase pojo de pregunta: id, id de la carta, pregunta, respuesta, magnitud. Contiene varios
 * constructores para para crear la pregunta. Creamos la tabla con sus campos y consultas correspondientes.
 */
@Entity(tableName = "question")
public class Question {

    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name = "card_id")
    private long card_id;

    @ColumnInfo(name = "question")
    private String question;

    @ColumnInfo(name = "answer")
    private Double answer;

    @ColumnInfo(name = "magnitude")
    private String magnitude;

    public Question(long idcard, @NonNull String question, @NonNull Double answer) {
        this.card_id = idcard;
        this.question = question;
        this.answer = answer;
        this.magnitude = null;
    }

    public Question(long idcard, @NonNull String question, @NonNull Double answer, String magnitude) {
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

    @NonNull
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