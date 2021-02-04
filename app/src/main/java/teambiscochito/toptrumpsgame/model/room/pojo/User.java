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

package teambiscochito.toptrumpsgame.model.room.pojo;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

/**
 * <h2 align="center">Team Biscochito</h2><hr>
 * <p>
 * Clase pojo de usuario: id, nombre, avatar, número de respuestas, número de respuestas correctas.
 * Contiene varios constructores para para crear al usuario. Creamos la tabla con sus campos y
 * consultas correspondientes.
 */
@Entity(tableName = "user", indices = {@Index(value = {"name"}, unique = true)})
public class User {

    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "avatar")
    private int avatar;

    /**
     * Número de respuestas, por defecto 0
     */
    @ColumnInfo(name = "answer")
    private int answer;

    /**
     * Número de aciertos, por defecto 0
     */
    @ColumnInfo(name = "TrueAnswer")
    private int TrueAnswer;

    public User(@NonNull String name, int avatar, int answer, int trueAnswer) {
        this.name = name;
        this.avatar = avatar;
        this.answer = answer;
        TrueAnswer = trueAnswer;
    }

    public User(@NonNull String name, int avatar) {
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