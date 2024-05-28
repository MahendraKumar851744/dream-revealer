package com.dream.insights.meanings.interpretation.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.dream.insights.meanings.interpretation.Adapters.Ad_Wiki;
import com.dream.insights.meanings.interpretation.Databases.wiki.Dream_Wiki;
import com.dream.insights.meanings.interpretation.R;
import com.dream.insights.meanings.interpretation.ads.MobileAds;
import com.dream.insights.meanings.interpretation.util.Utils;
import com.dream.insights.meanings.interpretation.viewModel.WikiViewModel;

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

//        MobileAds mobileAds = new MobileAds(this);
//        mobileAds.loadBannerAd(findViewById(R.id.adView3));
        new Utils(this).setBannerAd(findViewById(R.id.bannerplacer),25);

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