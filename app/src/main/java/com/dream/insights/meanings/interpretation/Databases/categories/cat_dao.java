package com.dream.insights.meanings.interpretation.Databases.categories;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface cat_dao {
    @Query("SELECT * FROM categories")
    LiveData<List<Category>> getAllProducts();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Category item);

    @Delete
    void delete(Category item);

    @Update
    void update(Category item);
}
