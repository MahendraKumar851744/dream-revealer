package com.dream.insights.meanings.interpretation.Databases.MyDreams;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "dreams")
public class Dream {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "date")
    private String date;

    @ColumnInfo(name = "cat")
    private String category;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "dream")
    private String dream;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDream() {
        return dream;
    }

    public void setDream(String dream) {
        this.dream = dream;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getcategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getdate() {
        return date;
    }

    public void setdate(String date) {
        this.date = date;
    }

    public String gettitle() {
        return title;
    }

    public void settitle(String title) {
        this.title = title;
    }

    public String getdream() {
        return dream;
    }

    public void setdream(String dream) {
        this.dream = dream;
    }


    public Dream() {
    }

    public Dream(String date, String category, String title, String dream) {
        this.date = date;
        this.category = category;
        this.title = title;
        this.dream = dream;
    }
}

