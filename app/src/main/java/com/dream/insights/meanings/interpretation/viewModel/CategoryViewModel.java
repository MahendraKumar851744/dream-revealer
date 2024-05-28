package com.dream.insights.meanings.interpretation.viewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.dream.insights.meanings.interpretation.Databases.categories.Category;
import com.dream.insights.meanings.interpretation.Databases.categories.cat_database;

import java.util.List;

public class CategoryViewModel extends AndroidViewModel {
    private final LiveData<List<Category>> cats;

    public CategoryViewModel(Application application) {
        super(application);

        cat_database p_db = cat_database.getDbInstance(application);

        cats = p_db.dao().getAllProducts();
    }
    public LiveData<List<Category>> getCatItems() {
        return cats;
    }
}
