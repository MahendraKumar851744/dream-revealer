package com.dream.insights.meanings.interpretation.viewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.dream.insights.meanings.interpretation.Databases.subcategoriesdb.SubCategory;
import com.dream.insights.meanings.interpretation.Databases.subcategoriesdb.subcat_database;

import java.util.List;

public class SubCategoryViewModel extends AndroidViewModel {
    private final LiveData<List<SubCategory>> subcats;

    public SubCategoryViewModel(Application application,int cat_id) {
        super(application);

        subcat_database p_db = subcat_database.getDbInstance(application);

        subcats = p_db.dao().getAllProducts(cat_id);
    }
    public LiveData<List<SubCategory>> getSubCatItems() {
        return subcats;
    }
}
