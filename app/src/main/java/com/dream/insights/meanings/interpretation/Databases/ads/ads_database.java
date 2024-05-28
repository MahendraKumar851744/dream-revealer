package com.dream.insights.meanings.interpretation.Databases.ads;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.dream.insights.meanings.interpretation.Databases.wiki.Dream_Wiki;
import com.dream.insights.meanings.interpretation.Databases.wiki.Wiki_dao;


@Database(entities = {Ads.class}, version  = 1)
public abstract class ads_database extends RoomDatabase {

    public abstract Ads_dao dao();

    private static ads_database INSTANCE;

    public static ads_database getDbInstance(Context context) {
        if(INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), ads_database.class, "ADS_DATABASE")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }
}