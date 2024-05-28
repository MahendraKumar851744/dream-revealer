package com.dream.insights.meanings.interpretation.Databases.healing;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "healing")
public class Healing {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "image")
    private String image;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "size")
    private String size;

    @ColumnInfo(name = "media_url")
    private String url;

    @ColumnInfo(name = "omedia_url")
    private String ourl;



    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getOurl() {
        return ourl;
    }

    public void setOurl(String url) {
        this.ourl = url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String description) {
        this.size = description;
    }

    public Healing() {
    }

    public Healing(int id, String image, String title,String url,String ourl, String size) {

        this.id = id;
        this.image = image;
        this.title = title;
        this.url = url;
        this.ourl = ourl;
        this.size = size;
    }
}

