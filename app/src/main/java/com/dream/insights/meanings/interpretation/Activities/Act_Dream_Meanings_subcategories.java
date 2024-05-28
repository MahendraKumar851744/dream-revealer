package com.dream.insights.meanings.interpretation.Activities;

import static com.dream.insights.meanings.interpretation.util.Constant.BASE_URL;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dream.insights.meanings.interpretation.Adapters.Ad_SubCategories;
import com.dream.insights.meanings.interpretation.Databases.ads.Ads;
import com.dream.insights.meanings.interpretation.Databases.ads.ads_database;
import com.dream.insights.meanings.interpretation.dialogs.CustomDialog;
import com.dream.insights.meanings.interpretation.Databases.subcategoriesdb.SubCategory;
import com.dream.insights.meanings.interpretation.ads.MobileAds;
import com.dream.insights.meanings.interpretation.R;
import com.dream.insights.meanings.interpretation.util.Utils;
import com.dream.insights.meanings.interpretation.viewModel.SubCategoryViewModel;
import com.dream.insights.meanings.interpretation.viewModel.SubCategoryViewModelFactory;

import java.util.ArrayList;
import java.util.List;

public class Act_Dream_Meanings_subcategories extends AppCompatActivity {
    RecyclerView rv_products;
    Ad_SubCategories ad_products;
    TextView askDream;
    ImageView menu,image3;
    EditText search;
    ArrayList<SubCategory> products;
    TextView title,sintheta;
    private ArrayList<SubCategory> filteredItems;
    private SubCategoryViewModel viewModel;
    private static final long SEARCH_DELAY = 300;

    private Handler searchHandler = new Handler();
    private Runnable searchRunnable;
    ProgressBar progress_bar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_dream_meanings);
        rv_products = findViewById(R.id.rv_items);
        askDream = findViewById(R.id.askDream);
        menu = findViewById(R.id.menu);
        search = findViewById(R.id.search);
        image3 = findViewById(R.id.image3);
        title = findViewById(R.id.title);
        sintheta = findViewById(R.id.sintheta);
        progress_bar = findViewById(R.id.progress_bar);
        sintheta.setVisibility(View.VISIBLE);
        askDream.setPaintFlags(askDream.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        askDream.setOnClickListener(view -> {
            Intent i = new Intent(Act_Dream_Meanings_subcategories.this,Ask_Dream.class);
            startActivity(i);
        });
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        SharedPreferences sp = getSharedPreferences("BASE_APP",MODE_PRIVATE);
        int cat_id = sp.getInt("cat_id",0);
        title.setText(sp.getString("cat_title","Dream Meanings"));


        String img = sp.getString("cat_img","");
        Glide.with(this).load(BASE_URL+img)
                .placeholder(R.drawable.image3)
                .into(image3);
        GridLayoutManager linearLayoutManager = new GridLayoutManager(this,1);
        rv_products.setLayoutManager(linearLayoutManager);

        progress_bar.setVisibility(View.VISIBLE);

        SubCategoryViewModelFactory factory = new SubCategoryViewModelFactory(getApplication(), cat_id);
        viewModel = new ViewModelProvider(this, factory).get(SubCategoryViewModel.class);
        viewModel.getSubCatItems().observe(this, new Observer<List<SubCategory>>() {
            @Override
            public void onChanged(List<SubCategory> items) {
                progress_bar.setVisibility(View.GONE);
                products =  new ArrayList<>(items);
                filteredItems = new ArrayList<>(products);
                ad_products = new Ad_SubCategories(Act_Dream_Meanings_subcategories.this, (ArrayList<SubCategory>) items, img);
                rv_products.setAdapter(ad_products);
            }
        });


        menu.setOnClickListener(view -> {
            CustomDialog customDialog = new CustomDialog(Act_Dream_Meanings_subcategories.this,android.R.style.Theme_Black_NoTitleBar_Fullscreen);
            customDialog.show();
        });

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                searchHandler.removeCallbacks(searchRunnable);

                searchRunnable = new Runnable() {
                    @Override
                    public void run() {
                        String searchText = s.toString();
                        filter(searchText);
                        ad_products = new Ad_SubCategories(Act_Dream_Meanings_subcategories.this, filteredItems, img);
                        rv_products.setAdapter(ad_products);
                    }
                };

                searchHandler.postDelayed(searchRunnable, SEARCH_DELAY);

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        setBannerAd();



    }
    public void filter(String searchText) {
        filteredItems.clear();

        if (searchText.isEmpty()) {
            filteredItems.addAll(products);
        } else {
            for (SubCategory item : products) {
                if (item.getTitle().toLowerCase().contains(searchText.toLowerCase())) {
                    filteredItems.add(item);
                }
            }
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        searchHandler.removeCallbacks(searchRunnable);
    }

    private void setBannerAd(){
//        MobileAds mobileAds = new MobileAds(this);
//        mobileAds.loadBannerAd(findViewById(R.id.adView3));
        new Utils(this).setBannerAd(findViewById(R.id.bannerplacer),5);
    }



}