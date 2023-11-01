package com.dreamrevealer.meanings.interpretation.journaldictionary.Databases.divine;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.dreamrevealer.meanings.interpretation.journaldictionary.Databases.healing.Healing;
import com.dreamrevealer.meanings.interpretation.journaldictionary.Databases.healing.Healing_dao;


@Database(entities = {Divine.class}, version  = 1)
public abstract class divine_database extends RoomDatabase {

    public abstract Divine_dao dao();

    private static divine_database INSTANCE;

    public static divine_database getDbInstance(Context context) {
        if(INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), divine_database.class, "DIVINE_DATABASE")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }
}