package teambiscochito.toptrumpsgame.model.room.pojo;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "card")
public class Card {

    //Carta: id, url foto, nombre animal, descripción

    @PrimaryKey(autoGenerate = true)
    private long id;

    @NonNull
    @ColumnInfo(name = "picUrl")
    private String picUrl;

    @NonNull
    @ColumnInfo(name = "name")
    private String name;

    @NonNull
    @ColumnInfo(name = "description")
    private String description;


    public Card(@NonNull String picUrl, @NonNull String name, @NonNull String desc) {
        this.picUrl = picUrl;
        this.name = name;
        this.description = desc;
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

    @Override
    public String toString() {
        return "Card{" +
                "id=" + id +
                ", picUrl='" + picUrl + '\'' +
                ", name='" + name + '\'' +
                ", desc='" + description + '\'' +
                '}';
    }


}
