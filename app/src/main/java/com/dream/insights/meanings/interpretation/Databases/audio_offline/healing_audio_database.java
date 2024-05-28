package com.dream.insights.meanings.interpretation.Databases.audio_offline;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


@Database(entities = {Healing_Audio.class}, version  = 1)
public abstract class healing_audio_database extends RoomDatabase {

    public abstract Healing_Audio_dao dao();

    private static healing_audio_database INSTANCE;

    public static healing_audio_database getDbInstance(Context context) {
        if(INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), healing_audio_database.class, "HEALING_A_DATABASE")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }
}