package com.dreamrevealer.meanings.interpretation.journaldictionary.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.dreamrevealer.meanings.interpretation.journaldictionary.ads.MobileAds;
import com.dreamrevealer.meanings.interpretation.journaldictionary.R;

public class Act_Divine_Prayers_Preview extends AppCompatActivity implements View.OnClickListener {
    CardView next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_divine_prayers_preview);
        next = findViewById(R.id.next);

        next.setOnClickListener(view -> {
            Intent i = new Intent(this,Act_Divine_Prayers.class);
            startActivity(i);
            finish();
        });
        MobileAds mobileAds = new MobileAds(this);
        mobileAds.loadBannerAd(findViewById(R.id.adView3));


    }

    @Override
    public void onClick(View view) {

    }
}