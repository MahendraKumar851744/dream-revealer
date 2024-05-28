package com.dream.insights.meanings.interpretation.Databases.productdb;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface product_dao {
    @Query("SELECT * FROM products")
    List<Product> getAllProducts();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Product item);

    @Delete
    void delete(Product item);

    @Update
    void update(Product item);
}
