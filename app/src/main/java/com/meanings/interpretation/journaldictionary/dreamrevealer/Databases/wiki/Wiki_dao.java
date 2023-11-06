package com.meanings.interpretation.journaldictionary.dreamrevealer.Databases.wiki;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface Wiki_dao {
    @Query("SELECT * FROM wiki ")
    LiveData<List<Dream_Wiki>> getAllWiki();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Dream_Wiki item);

    @Delete
    void delete(Dream_Wiki item);

    @Update
    void update(Dream_Wiki item);
}
