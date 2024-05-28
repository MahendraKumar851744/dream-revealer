package com.dream.insights.meanings.interpretation.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.dream.insights.meanings.interpretation.Databases.MyDreams.dream_dao;
import com.dream.insights.meanings.interpretation.Databases.MyDreams.dream_database;
import com.dream.insights.meanings.interpretation.ads.MobileAds;
import com.dream.insights.meanings.interpretation.R;
import com.dream.insights.meanings.interpretation.util.Utils;

public class Act_Dream_Book extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dream_database db = dream_database.getDbInstance(this);
        dream_dao dao = db.dao();

        if(dao.getAllDreams().size()>0){
            setContentView(R.layout.activity_act_dream_book);
            ImageView iv_back = findViewById(R.id.iv_back);
            iv_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed();
                }
            });
            CardView add = findViewById(R.id.add_dream);
            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(Act_Dream_Book.this,Add_Dream.class);
                    startActivity(i);

                }
            });
            CardView view = findViewById(R.id.view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(Act_Dream_Book.this,Act_Dream_List.class);
                    startActivity(i);
                }
            });


            setBannerAd(findViewById(R.id.bannerplacer2),8);
            setBannerAd(findViewById(R.id.bannerplacer),9);


        }else{
            setContentView(R.layout.lay_empty_dream_book);
            ImageView iv_back = findViewById(R.id.iv_back);
            ImageView add = findViewById(R.id.add);
            iv_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed();
                }
            });

            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(Act_Dream_Book.this,Add_Dream.class);
                    startActivity(i);
                    finish();
                }
            });
            setBannerAd(findViewById(R.id.bannerplacer),7);
        }
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
    }
    private void setBannerAd(FrameLayout fl, int id){
        new Utils(this).setBannerAd(fl,id);
    }
}