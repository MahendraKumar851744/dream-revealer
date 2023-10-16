package com.dreamrevealer.meanings.interpretation.journaldictionary.Databases.subcategoriesdb;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "subcategories")
public class SubCategory {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "cat_id")
    private String cat_id;


    @ColumnInfo(name = "title")
    private String title;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCat_id() {
        return cat_id;
    }

    public void setCat_id(String cat_id) {
        this.cat_id = cat_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public SubCategory() {
    }

    public SubCategory(int id, String cat_id, String title) {
        this.id = id;
        this.cat_id = cat_id;
        this.title = title;
    }
}

