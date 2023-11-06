package com.meanings.interpretation.journaldictionary.dreamrevealer.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.meanings.interpretation.journaldictionary.dreamrevealer.ads.MobileAds;
import com.meanings.interpretation.journaldictionary.dreamrevealer.R;

public class Act_Divine_Prayers_Preview extends AppCompatActivity implements View.OnClickListener {
    CardView next;

    ImageView iv_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_divine_prayers_preview);
        next = findViewById(R.id.next);
        iv_back = findViewById(R.id.iv_back);
        iv_back.setOnClickListener(view -> {
            getOnBackPressedDispatcher().onBackPressed();
        });
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