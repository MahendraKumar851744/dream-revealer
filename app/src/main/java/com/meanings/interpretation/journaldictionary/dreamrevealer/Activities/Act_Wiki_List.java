package com.meanings.interpretation.journaldictionary.dreamrevealer.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.meanings.interpretation.journaldictionary.dreamrevealer.Adapters.Ad_Wiki;
import com.meanings.interpretation.journaldictionary.dreamrevealer.Databases.wiki.Dream_Wiki;
import com.meanings.interpretation.journaldictionary.dreamrevealer.R;
import com.meanings.interpretation.journaldictionary.dreamrevealer.ads.MobileAds;
import com.meanings.interpretation.journaldictionary.dreamrevealer.viewModel.WikiViewModel;

import java.util.ArrayList;
import java.util.List;

public class Act_Wiki_List extends AppCompatActivity {
    RecyclerView rv_products;
    Ad_Wiki ad_products;
    ProgressBar progress_bar;
    ImageView iv_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_wiki_list);
        rv_products = findViewById(R.id.rv_items);
        progress_bar = findViewById(R.id.progress_bar);
        iv_back = findViewById(R.id.iv_back);

        MobileAds mobileAds = new MobileAds(this);
        mobileAds.loadBannerAd(findViewById(R.id.adView3));
        GridLayoutManager linearLayoutManager = new GridLayoutManager(this,3);
        rv_products.setLayoutManager(linearLayoutManager);

        WikiViewModel viewModel = new WikiViewModel(getApplication());
        progress_bar.setVisibility(View.VISIBLE);

        viewModel.getAllData().observe(this, new Observer<List<Dream_Wiki>>() {
            @Override
            public void onChanged(List<Dream_Wiki> items) {
                progress_bar.setVisibility(View.GONE);
                ad_products = new Ad_Wiki(Act_Wiki_List.this, (ArrayList<Dream_Wiki>) items);
                rv_products.setAdapter(ad_products);
            }
        });

        iv_back.setOnClickListener(view -> {
            getOnBackPressedDispatcher().onBackPressed();
        });

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);


    }
}