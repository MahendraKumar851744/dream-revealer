package com.dream.insights.meanings.interpretation.Databases.audio_offline;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "healing_audio")
public class Healing_Audio {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "healing_id")
    private int healing_id;

    @ColumnInfo(name = "path")
    private String path;



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHealing_id() {
        return healing_id;
    }

    public void setHealing_id(int healing_id) {
        this.healing_id = healing_id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }



    public Healing_Audio() {
    }

    public Healing_Audio(int healing_id,String path) {
        this.healing_id = healing_id;
        this.path = path;
    }
}

