package com.dreamrevealer.meanings.interpretation.journaldictionary.viewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.dreamrevealer.meanings.interpretation.journaldictionary.Databases.subcategoriesdb.SubCategory;
import com.dreamrevealer.meanings.interpretation.journaldictionary.Databases.subcategoriesdb.subcat_database;

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
