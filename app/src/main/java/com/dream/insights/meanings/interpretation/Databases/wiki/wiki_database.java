package com.dream.insights.meanings.interpretation.Databases.wiki;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


@Database(entities = {Dream_Wiki.class}, version  = 1)
public abstract class wiki_database extends RoomDatabase {

    public abstract Wiki_dao dao();

    private static wiki_database INSTANCE;

    public static wiki_database getDbInstance(Context context) {
        if(INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), wiki_database.class, "WIKI_DATABASE")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }
}