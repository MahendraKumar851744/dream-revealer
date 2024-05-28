package com.dream.insights.meanings.interpretation.Databases.ads;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

@Dao
public interface Ads_dao {
    @Query("SELECT * FROM ads WHERE id = :id")
    Ads getAd(int id);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Ads item);

    @Delete
    void delete(Ads item);

    @Update
    void update(Ads item);
}
