package com.dream.insights.meanings.interpretation.Databases.healing;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface Healing_dao {
    @Query("SELECT * FROM healing")
    LiveData<List<Healing>> getAllProducts();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Healing item);

    @Delete
    void delete(Healing item);

    @Update
    void update(Healing item);
}
