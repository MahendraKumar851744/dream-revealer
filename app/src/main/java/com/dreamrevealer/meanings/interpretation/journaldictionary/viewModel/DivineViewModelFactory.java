package com.dreamrevealer.meanings.interpretation.journaldictionary.viewModel;

import android.app.Application;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class DivineViewModelFactory implements ViewModelProvider.Factory {
    private Application application;
    private int type;

    public DivineViewModelFactory(Application application, int type) {
        this.application = application;
        this.type = type;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(DivineViewModel.class)) {
            return (T) new DivineViewModel(application, type);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
