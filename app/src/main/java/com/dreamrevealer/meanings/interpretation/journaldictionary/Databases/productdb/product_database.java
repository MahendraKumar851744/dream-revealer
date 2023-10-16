package com.dreamrevealer.meanings.interpretation.journaldictionary.Databases.productdb;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


@Database(entities = {Product.class}, version  = 1)
public abstract class product_database extends RoomDatabase {

    public abstract product_dao dao();

    private static product_database INSTANCE;

    public static product_database getDbInstance(Context context) {
        if(INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), product_database.class, "PRODUCT_DATABASE")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }
}