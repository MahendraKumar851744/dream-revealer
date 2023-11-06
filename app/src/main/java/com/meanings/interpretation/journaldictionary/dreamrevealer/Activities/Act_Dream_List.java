package com.meanings.interpretation.journaldictionary.dreamrevealer.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.meanings.interpretation.journaldictionary.dreamrevealer.Adapters.Ad_Dreams;
import com.meanings.interpretation.journaldictionary.dreamrevealer.Databases.MyDreams.Dream;
import com.meanings.interpretation.journaldictionary.dreamrevealer.Databases.MyDreams.dream_database;
import com.meanings.interpretation.journaldictionary.dreamrevealer.ads.MobileAds;
import com.meanings.interpretation.journaldictionary.dreamrevealer.listeners.OnClickListener;
import com.meanings.interpretation.journaldictionary.dreamrevealer.R;

import java.util.ArrayList;

public class Act_Dream_List extends AppCompatActivity {
    RecyclerView rv_products;
    Ad_Dreams ad_products;
    OnClickListener listener;
    dream_database p_db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_dream_list);
        rv_products = findViewById(R.id.rv);
        GridLayoutManager linearLayoutManager = new GridLayoutManager(this,1);
        rv_products.setLayoutManager(linearLayoutManager);
        p_db = dream_database.getDbInstance(this);
        ArrayList<Dream> products = (ArrayList<Dream>) p_db.dao().getAllDreams();
        listener  = new OnClickListener() {
            @Override
            public void onDelete() {
                ArrayList<Dream> products = (ArrayList<Dream>) p_db.dao().getAllDreams();
                ad_products = new Ad_Dreams(Act_Dream_List.this, products,listener);
                rv_products.setAdapter(ad_products);
            }
        };
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        MobileAds mobileAds = new MobileAds(this);
        mobileAds.loadBannerAd(findViewById(R.id.adView3));
        if(!products.isEmpty()) {
            ad_products = new Ad_Dreams(this, products,listener);
            rv_products.setAdapter(ad_products);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        ArrayList<Dream> products = (ArrayList<Dream>) p_db.dao().getAllDreams();
        ad_products = new Ad_Dreams(Act_Dream_List.this, products,listener);
        rv_products.setAdapter(ad_products);
    }
}