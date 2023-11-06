package com.meanings.interpretation.journaldictionary.dreamrevealer.Databases.dream_calender;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


@Database(entities = {Dream_Calender.class}, version  = 1)
public abstract class cal_database extends RoomDatabase {

    public abstract Calender_dao dao();

    private static cal_database INSTANCE;

    public static cal_database getDbInstance(Context context) {
        if(INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), cal_database.class, "CALENDER_DATABASE")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }
}