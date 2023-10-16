package com.dreamrevealer.meanings.interpretation.journaldictionary.Databases.subcategoriesdb;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface subcat_dao {
    @Query("SELECT * FROM subcategories")
    List<SubCategory> getAllProducts();

    @Insert
    void insert(SubCategory item);

    @Delete
    void delete(SubCategory item);

    @Update
    void update(SubCategory item);
}
