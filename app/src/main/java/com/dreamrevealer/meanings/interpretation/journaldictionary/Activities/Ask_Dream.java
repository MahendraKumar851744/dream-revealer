package com.dreamrevealer.meanings.interpretation.journaldictionary.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.os.Bundle;
import android.widget.ImageView;

import com.dreamrevealer.meanings.interpretation.journaldictionary.R;

public class Ask_Dream extends AppCompatActivity {

    ImageView iv_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_dream);
        iv_back = findViewById(R.id.iv_back);
        iv_back.setOnClickListener(view -> {
            getOnBackPressedDispatcher().onBackPressed();
        });
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

    }
}