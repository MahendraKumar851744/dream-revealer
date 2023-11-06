package com.meanings.interpretation.journaldictionary.dreamrevealer.Databases.divine;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "divine")
public class Divine {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "content")
    private String content;


    @ColumnInfo(name = "morning_or_evening")
    private int morning_or_evening;



    public int getMorning_or_evening() {
        return morning_or_evening;
    }

    public void setMorning_or_evening(int url) {
        this.morning_or_evening = url;
    }

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Divine() {
    }


    public Divine(String title,String content,int morning_or_evening){
        this.title = title;
        this.content = content;
        this.morning_or_evening = morning_or_evening;
    }
}

