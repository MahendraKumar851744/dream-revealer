package com.dream.insights.meanings.interpretation.Databases.ads;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "ads")
public class Ads {
    @PrimaryKey
    private int id;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "image")
    private String image;

    @ColumnInfo(name = "link")
    private String link;

    public Ads(){}

    public Ads(int id, String description, String image, String link) {
        this.id = id;
        this.description = description;
        this.image = image;
        this.link = link;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
