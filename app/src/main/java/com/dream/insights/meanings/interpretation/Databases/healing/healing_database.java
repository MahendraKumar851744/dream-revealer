package com.dream.insights.meanings.interpretation.Databases.healing;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


@Database(entities = {Healing.class}, version  = 1)
public abstract class healing_database extends RoomDatabase {

    public abstract Healing_dao dao();

    private static healing_database INSTANCE;

    public static healing_database getDbInstance(Context context) {
        if(INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), healing_database.class, "HEALING_DATABASE")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }
}