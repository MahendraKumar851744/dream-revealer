package com.dream.insights.meanings.interpretation.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dream.insights.meanings.interpretation.Adapters.Ad_Divine;
import com.dream.insights.meanings.interpretation.Databases.divine.Divine;
import com.dream.insights.meanings.interpretation.Databases.divine.divine_database;
import com.dream.insights.meanings.interpretation.ads.MobileAds;
import com.dream.insights.meanings.interpretation.R;
import com.dream.insights.meanings.interpretation.util.Utils;

import java.util.ArrayList;
import java.util.List;

public class Act_Divine_Prayers extends AppCompatActivity implements View.OnClickListener  {
    TextView tv1,tv2;
    LinearLayout ll1,ll2;

    RecyclerView rv_products;
    Ad_Divine ad_products;
    ProgressBar progress_bar;
    divine_database db;
    ImageView menu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_divine_prayers);
        ll1 = findViewById(R.id.ll1);
        ll2 = findViewById(R.id.ll2);
        tv1 = findViewById(R.id.tv1);
        tv2 = findViewById(R.id.tv2);
        menu = findViewById(R.id.menu);
        rv_products = findViewById(R.id.rv_products);
        progress_bar = findViewById(R.id.progress_bar);
        ll1.setOnClickListener(this);
        ll2.setOnClickListener(this);
//        MobileAds mobileAds = new MobileAds(this);
//        mobileAds.loadBannerAd(findViewById(R.id.adView3));
        new Utils(this).setBannerAd(findViewById(R.id.bannerplacer),18);

        GridLayoutManager linearLayoutManager = new GridLayoutManager(this,1);
        rv_products.setLayoutManager(linearLayoutManager);
        progress_bar.setVisibility(View.VISIBLE);

        db = divine_database.getDbInstance(this);

        db.dao().getAllMorning().observe(this, new Observer<List<Divine>>() {
            @Override
            public void onChanged(List<Divine> divines) {
                progress_bar.setVisibility(View.GONE);
                ad_products = new Ad_Divine(Act_Divine_Prayers.this, (ArrayList<Divine>) divines,0);
                rv_products.setAdapter(ad_products);
            }
        });
        menu.setOnClickListener(view -> {
            getOnBackPressedDispatcher().onBackPressed();
        });

    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.ll1){
            ll1.setBackground(getDrawable(R.drawable.rounded_corner3));
            tv1.setTextColor(getResources().getColor(R.color.global));
            ll2.setBackground(getDrawable(R.drawable.rounded_corner));
            tv2.setTextColor(getResources().getColor(R.color.white));
            progress_bar.setVisibility(View.VISIBLE);

            db.dao().getAllMorning().observe(this, new Observer<List<Divine>>() {
                @Override
                public void onChanged(List<Divine> divines) {
                    progress_bar.setVisibility(View.GONE);
                    ad_products = new Ad_Divine(Act_Divine_Prayers.this, (ArrayList<Divine>) divines,0);
                    rv_products.setAdapter(ad_products);
                }
            });
        }else if(view.getId() == R.id.ll2){
            ll2.setBackground(getDrawable(R.drawable.rounded_corner3));
            tv2.setTextColor(getResources().getColor(R.color.global));
            ll1.setBackground(getDrawable(R.drawable.rounded_corner));
            tv1.setTextColor(getResources().getColor(R.color.white));
            progress_bar.setVisibility(View.VISIBLE);
            db.dao().getAllEvening().observe(this, new Observer<List<Divine>>() {
                @Override
                public void onChanged(List<Divine> divines) {
                    progress_bar.setVisibility(View.GONE);
                    ad_products = new Ad_Divine(Act_Divine_Prayers.this, (ArrayList<Divine>) divines,1);
                    rv_products.setAdapter(ad_products);
                }
            });
        }
    }
}