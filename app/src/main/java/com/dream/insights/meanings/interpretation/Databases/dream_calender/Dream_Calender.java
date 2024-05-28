package com.dream.insights.meanings.interpretation.Databases.dream_calender;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "calender")
public class Dream_Calender {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "content")
    private String content;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Dream_Calender() {
    }


    public Dream_Calender(String content){
        this.content = content;
    }
}

