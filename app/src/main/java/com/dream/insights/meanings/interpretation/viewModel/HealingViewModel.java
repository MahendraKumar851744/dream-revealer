package com.dream.insights.meanings.interpretation.viewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.dream.insights.meanings.interpretation.Databases.healing.Healing;
import com.dream.insights.meanings.interpretation.Databases.healing.healing_database;

import java.util.List;

public class HealingViewModel extends AndroidViewModel {
    private final LiveData<List<Healing>> healings;

    public HealingViewModel(Application application) {
        super(application);

        healing_database p_db = healing_database.getDbInstance(application);

        healings = p_db.dao().getAllProducts();
    }
    public LiveData<List<Healing>> getHealingItems() {
        return healings;
    }
}
