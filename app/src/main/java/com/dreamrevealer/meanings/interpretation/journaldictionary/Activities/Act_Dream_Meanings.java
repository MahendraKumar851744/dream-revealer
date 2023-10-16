package com.dreamrevealer.meanings.interpretation.journaldictionary.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.dreamrevealer.meanings.interpretation.journaldictionary.Ad_Categories;
import com.dreamrevealer.meanings.interpretation.journaldictionary.CustomDialog;
import com.dreamrevealer.meanings.interpretation.journaldictionary.Databases.categories.Category;
import com.dreamrevealer.meanings.interpretation.journaldictionary.Databases.categories.cat_database;
import com.dreamrevealer.meanings.interpretation.journaldictionary.MobileAds;
import com.dreamrevealer.meanings.interpretation.journaldictionary.R;

import java.util.ArrayList;

public class Act_Dream_Meanings extends AppCompatActivity {
    RecyclerView rv_products;
    Ad_Categories ad_products;
    TextView askDream;
    ImageView menu;
    EditText search;
    ArrayList<Category> products;
    private ArrayList<Category> filteredItems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_dream_meanings);
        rv_products = findViewById(R.id.rv_items);
        askDream = findViewById(R.id.askDream);
        menu = findViewById(R.id.menu);
        search = findViewById(R.id.search);
        askDream.setPaintFlags(askDream.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        askDream.setOnClickListener(view -> {
            Intent i = new Intent(Act_Dream_Meanings.this,Ask_Dream.class);
            startActivity(i);
        });
        MobileAds mobileAds = new MobileAds(this);
        mobileAds.loadBannerAd(findViewById(R.id.adView3));
        GridLayoutManager linearLayoutManager = new GridLayoutManager(this,1);
        rv_products.setLayoutManager(linearLayoutManager);
        cat_database p_db = cat_database.getDbInstance(this);
        products = (ArrayList<Category>) p_db.dao().getAllProducts();
        filteredItems = new ArrayList<>(products);
        if(!products.isEmpty()) {
            ad_products = new Ad_Categories(this, products);
            rv_products.setAdapter(ad_products);
        }
        menu.setOnClickListener(view -> {
            CustomDialog customDialog = new CustomDialog(Act_Dream_Meanings.this,android.R.style.Theme_Black_NoTitleBar_Fullscreen);
            customDialog.show();
        });
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String searchText = s.toString();
                if(!searchText.isEmpty()){
                    filter(searchText);
                    ad_products = new Ad_Categories(Act_Dream_Meanings.this, filteredItems);
                    rv_products.setAdapter(ad_products);
                }else{
                    ad_products = new Ad_Categories(Act_Dream_Meanings.this, products);
                    rv_products.setAdapter(ad_products);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }
    public void filter(String searchText) {
        filteredItems.clear();

        if (searchText.isEmpty()) {
            filteredItems.addAll(products);
        } else {
            for (Category item : products) {
                if (item.getTitle().toLowerCase().contains(searchText.toLowerCase())) {
                    filteredItems.add(item);
                }
            }
        }

    }
}