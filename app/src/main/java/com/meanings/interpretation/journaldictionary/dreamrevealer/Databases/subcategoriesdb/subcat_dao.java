package com.meanings.interpretation.journaldictionary.dreamrevealer.Databases.subcategoriesdb;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface subcat_dao {
    @Query("SELECT * FROM subcategories WHERE cat_id = :categoryId")
    LiveData<List<SubCategory>> getAllProducts(int categoryId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(SubCategory item);

    @Delete
    void delete(SubCategory item);

    @Update
    void update(SubCategory item);
}
