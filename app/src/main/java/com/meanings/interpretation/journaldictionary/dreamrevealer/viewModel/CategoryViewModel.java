package com.meanings.interpretation.journaldictionary.dreamrevealer.viewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.meanings.interpretation.journaldictionary.dreamrevealer.Databases.categories.Category;
import com.meanings.interpretation.journaldictionary.dreamrevealer.Databases.categories.cat_database;

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
