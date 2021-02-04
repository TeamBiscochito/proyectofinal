package teambiscochito.toptrumpsgame.model.room.pojo;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * <h2 align="center">Team Biscochito</h2><hr>
 * <p>
 * Clase pojo de carta: id, url foto, nombre animal, descripción. Contiene varios constructores para
 * crear la carta. Creamos la tabla con sus campos y consultas correspondientes.
 */

@Entity(tableName = "card")
public class Card {

    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name = "picUrl")
    private String picUrl;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "description")
    private String description;

    /**
     * @param picUrl      url de la página que contiene la foto
     * @param name        nombre de animal / carta
     * @param description descripción del animal
     */
    public Card(@NonNull String picUrl, @NonNull String name, @NonNull String description) {
        this.picUrl = picUrl;
        this.name = name;
        this.description = description;
    }

    public Card() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @NonNull
    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(@NonNull String picUrl) {
        this.picUrl = picUrl;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    @NonNull
    public String getDescription() {
        return description;
    }

    public void setDescription(@NonNull String description) {
        this.description = description;
    }

    @NonNull
    @Override
    public String toString() {
        return "Card{" +
                "id=" + id +
                ", picUrl='" + picUrl + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}