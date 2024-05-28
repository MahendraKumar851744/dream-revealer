package com.dream.insights.meanings.interpretation.Databases.MyDreams;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface dream_dao {
    @Query("SELECT * FROM dreams")
    List<Dream> getAllDreams();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Dream item);

    @Delete
    void delete(Dream item);

    @Query("DELETE FROM dreams WHERE id = :id")
    void deleteById(int id);

    @Update
    void update(Dream item);
}
