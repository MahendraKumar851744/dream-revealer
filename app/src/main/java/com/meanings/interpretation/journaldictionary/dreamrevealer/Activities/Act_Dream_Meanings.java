package com.meanings.interpretation.journaldictionary.dreamrevealer.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.meanings.interpretation.journaldictionary.dreamrevealer.Adapters.Ad_Categories;
import com.meanings.interpretation.journaldictionary.dreamrevealer.viewModel.CategoryViewModel;
import com.meanings.interpretation.journaldictionary.dreamrevealer.dialogs.CustomDialog;
import com.meanings.interpretation.journaldictionary.dreamrevealer.Databases.categories.Category;
import com.meanings.interpretation.journaldictionary.dreamrevealer.ads.MobileAds;
import com.meanings.interpretation.journaldictionary.dreamrevealer.R;

import java.util.ArrayList;
import java.util.List;

public class Act_Dream_Meanings extends AppCompatActivity {
    RecyclerView rv_products;
    Ad_Categories ad_products;
    TextView askDream;
    ImageView menu;
    EditText search;
    ArrayList<Category> products;
    private ArrayList<Category> filteredItems;
    ProgressBar progress_bar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_dream_meanings);
        rv_products = findViewById(R.id.rv_items);
        askDream = findViewById(R.id.askDream);
        menu = findViewById(R.id.menu);
        search = findViewById(R.id.search);
        progress_bar = findViewById(R.id.progress_bar);
        askDream.setPaintFlags(askDream.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        askDream.setOnClickListener(view -> {
            Intent i = new Intent(Act_Dream_Meanings.this,Ask_Dream.class);
            startActivity(i);
        });
        MobileAds mobileAds = new MobileAds(this);
        mobileAds.loadBannerAd(findViewById(R.id.adView3));
        GridLayoutManager linearLayoutManager = new GridLayoutManager(this,1);
        rv_products.setLayoutManager(linearLayoutManager);

        CategoryViewModel viewModel = new CategoryViewModel(getApplication());
        progress_bar.setVisibility(View.VISIBLE);

        viewModel.getCatItems().observe(this, new Observer<List<Category>>() {
            @Override
            public void onChanged(List<Category> items) {
                progress_bar.setVisibility(View.GONE);
                products =  new ArrayList<>(items);
                filteredItems = new ArrayList<>(products);
                ad_products = new Ad_Categories(Act_Dream_Meanings.this, (ArrayList<Category>) items);
                rv_products.setAdapter(ad_products);
            }
        });


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