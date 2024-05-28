package com.dream.insights.meanings.interpretation.Databases.subcategoriesdb;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


@Database(entities = {SubCategory.class}, version  = 3)
public abstract class subcat_database extends RoomDatabase {

    public abstract subcat_dao dao();

    private static subcat_database INSTANCE;

    public static subcat_database getDbInstance(Context context) {
        if(INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), subcat_database.class, "SUBCATEGORY_DATABASE")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }
}