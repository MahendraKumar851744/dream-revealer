package com.meanings.interpretation.journaldictionary.dreamrevealer.Databases.productdb;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "products")
public class Product {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "category_id")
    private int cat_id;

    @ColumnInfo(name = "subcategory_id")
    private int subcat_id;

    @ColumnInfo(name = "pm1")
    private String pm1;

    @ColumnInfo(name = "pm2")
    private String pm2;

    @ColumnInfo(name = "eo1")
    private String eo1;

    @ColumnInfo(name = "eo2")
    private String eo2;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCat_id() {
        return cat_id;
    }

    public void setCat_id(int cat_id) {
        this.cat_id = cat_id;
    }

    public int getSubcat_id() {
        return subcat_id;
    }

    public void setSubcat_id(int subcat_id) {
        this.subcat_id = subcat_id;
    }

    public String getPm1() {
        return pm1;
    }

    public void setPm1(String pm1) {
        this.pm1 = pm1;
    }

    public String getPm2() {
        return pm2;
    }

    public void setPm2(String pm2) {
        this.pm2 = pm2;
    }

    public String getEo1() {
        return eo1;
    }

    public void setEo1(String eo1) {
        this.eo1 = eo1;
    }

    public String getEo2() {
        return eo2;
    }

    public void setEo2(String eo2) {
        this.eo2 = eo2;
    }

    public Product() {
    }

    public Product(int id, int categoryId, int subcategoryId, String pm1, String pm2, String eo1, String eo2) {
        this.id = id;
        this.cat_id = categoryId;
        this.subcat_id = subcategoryId;
        this.pm1 = pm1;
        this.pm2 = pm2;
        this.eo1 = eo1;
        this.eo2 = eo2;
    }
}

