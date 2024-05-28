package com.dream.insights.meanings.interpretation.viewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.dream.insights.meanings.interpretation.Databases.wiki.Dream_Wiki;
import com.dream.insights.meanings.interpretation.Databases.wiki.wiki_database;

import java.util.List;

public class WikiViewModel extends AndroidViewModel {
    private final LiveData<List<Dream_Wiki>> data;

    public WikiViewModel(Application application) {
        super(application);

        wiki_database p_db = wiki_database.getDbInstance(application);

        data = p_db.dao().getAllWiki();
    }
    public LiveData<List<Dream_Wiki>> getAllData() {
        return data;
    }
}
