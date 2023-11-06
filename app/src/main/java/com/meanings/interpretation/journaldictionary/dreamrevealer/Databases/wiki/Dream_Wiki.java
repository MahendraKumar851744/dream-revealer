package com.meanings.interpretation.journaldictionary.dreamrevealer.Databases.wiki;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "wiki")
public class Dream_Wiki {

    @PrimaryKey(autoGenerate = true)
    private int id;


    @ColumnInfo(name = "title")
    private String title;


    @ColumnInfo(name = "image")
    private String image;


    @ColumnInfo(name = "possible_meaning")
    private String possible_meaning;


    @ColumnInfo(name = "direction")
    private String direction;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPossible_meaning() {
        return possible_meaning;
    }

    public void setPossible_meaning(String possible_meaning) {
        this.possible_meaning = possible_meaning;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public Dream_Wiki() {
    }

    public Dream_Wiki(String title, String image, String possible_meaning, String direction) {
        this.title = title;
        this.image = image;
        this.possible_meaning = possible_meaning;
        this.direction = direction;
    }

}
