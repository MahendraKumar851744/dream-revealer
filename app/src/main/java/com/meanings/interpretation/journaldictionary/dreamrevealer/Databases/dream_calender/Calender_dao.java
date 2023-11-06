package com.meanings.interpretation.journaldictionary.dreamrevealer.Databases.dream_calender;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface Calender_dao {
    @Query("SELECT * FROM calender ")
    List<Dream_Calender> getAllCalender();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Dream_Calender item);

    @Delete
    void delete(Dream_Calender item);

    @Update
    void update(Dream_Calender item);
}
