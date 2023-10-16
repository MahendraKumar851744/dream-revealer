package com.dreamrevealer.meanings.interpretation.journaldictionary.Databases.categories;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface cat_dao {
    @Query("SELECT * FROM categories")
    List<Category> getAllProducts();

    @Insert
    void insert(Category item);

    @Delete
    void delete(Category item);

    @Update
    void update(Category item);
}
