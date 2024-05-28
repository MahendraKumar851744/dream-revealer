package com.dream.insights.meanings.interpretation.viewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.dream.insights.meanings.interpretation.Databases.divine.Divine;
import com.dream.insights.meanings.interpretation.Databases.divine.divine_database;

import java.util.List;

public class DivineViewModel extends AndroidViewModel {
    private final LiveData<List<Divine>> divines;

    public DivineViewModel(Application application,int type) {
        super(application);
        divine_database p_db = divine_database.getDbInstance(application);
        if(type==0){
            divines = p_db.dao().getAllMorning();
        }else{
            divines = p_db.dao().getAllEvening();
        }
    }
    public LiveData<List<Divine>> getDivineItems() {
        return divines;
    }
}
