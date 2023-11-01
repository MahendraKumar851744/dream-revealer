package com.dreamrevealer.meanings.interpretation.journaldictionary.Databases.divine;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.dreamrevealer.meanings.interpretation.journaldictionary.Databases.healing.Healing;

import java.util.List;

@Dao
public interface Divine_dao {
    @Query("SELECT * FROM divine WHERE morning_or_evening = 0")
    LiveData<List<Divine>> getAllMorning();

    @Query("SELECT * FROM divine WHERE morning_or_evening = 1")
    LiveData<List<Divine>> getAllEvening();


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Divine item);

    @Delete
    void delete(Divine item);

    @Update
    void update(Divine item);
}
