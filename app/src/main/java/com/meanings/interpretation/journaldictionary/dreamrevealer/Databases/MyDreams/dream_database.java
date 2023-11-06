package com.meanings.interpretation.journaldictionary.dreamrevealer.Databases.MyDreams;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


@Database(entities = {Dream.class}, version  = 2)
public abstract class dream_database extends RoomDatabase {

    public abstract dream_dao dao();

    private static dream_database INSTANCE;

    public static dream_database getDbInstance(Context context) {
        if(INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), dream_database.class, "DREAM_DATABASE")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }
}