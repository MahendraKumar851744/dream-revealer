package com.dreamrevealer.meanings.interpretation.journaldictionary.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.dreamrevealer.meanings.interpretation.journaldictionary.Adapters.Ad_Healing_Sounds;
import com.dreamrevealer.meanings.interpretation.journaldictionary.Databases.healing.Healing;
import com.dreamrevealer.meanings.interpretation.journaldictionary.viewModel.HealingViewModel;
import com.dreamrevealer.meanings.interpretation.journaldictionary.ads.MobileAds;
import com.dreamrevealer.meanings.interpretation.journaldictionary.R;

import java.util.ArrayList;
import java.util.List;

public class Act_Healing_Sound extends AppCompatActivity {
    RecyclerView rv_products;
    Ad_Healing_Sounds ad_products;
    CardView next,cv1;
    ProgressBar progress_bar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_healing_sound);
        next = findViewById(R.id.next);
        cv1 = findViewById(R.id.cv1);
        progress_bar = findViewById(R.id.progress_bar);
        next.setOnClickListener(view -> {
            cv1.setVisibility(View.GONE);
        });
        MobileAds mobileAds = new MobileAds(this);
        mobileAds.loadBannerAd(findViewById(R.id.adView3));


        rv_products = findViewById(R.id.rv_items);
        GridLayoutManager linearLayoutManager = new GridLayoutManager(this,1);
        rv_products.setLayoutManager(linearLayoutManager);


        HealingViewModel viewModel = new HealingViewModel(getApplication());
        progress_bar.setVisibility(View.VISIBLE);

        viewModel.getHealingItems().observe(this, new Observer<List<Healing>>() {
            @Override
            public void onChanged(List<Healing> items) {
                Log.d("HEALING-ITEMS",items.size()+" ");
                progress_bar.setVisibility(View.GONE);
                ad_products = new Ad_Healing_Sounds(Act_Healing_Sound.this, (ArrayList<Healing>) items);
                rv_products.setAdapter(ad_products);
            }
        });


    }
}