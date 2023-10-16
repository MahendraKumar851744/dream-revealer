package com.dreamrevealer.meanings.interpretation.journaldictionary.Activities;

import static com.dreamrevealer.meanings.interpretation.journaldictionary.Constant.BASE_URL;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dreamrevealer.meanings.interpretation.journaldictionary.Ad_SubCategories;
import com.dreamrevealer.meanings.interpretation.journaldictionary.CustomDialog;
import com.dreamrevealer.meanings.interpretation.journaldictionary.Databases.subcategoriesdb.SubCategory;
import com.dreamrevealer.meanings.interpretation.journaldictionary.Databases.subcategoriesdb.subcat_database;
import com.dreamrevealer.meanings.interpretation.journaldictionary.LoadingDialog;
import com.dreamrevealer.meanings.interpretation.journaldictionary.MobileAds;
import com.dreamrevealer.meanings.interpretation.journaldictionary.R;

import java.util.ArrayList;

public class Act_Dream_Meanings_subcategories extends AppCompatActivity {
    RecyclerView rv_products;
    Ad_SubCategories ad_products;
    TextView askDream;
    ImageView menu,image3;
    EditText search;
    ArrayList<SubCategory> products;
    TextView title,sintheta;
    private ArrayList<SubCategory> filteredItems;
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
        LoadingDialog dialog = new LoadingDialog(this,android.R.style.Theme_Black_NoTitleBar_Fullscreen,sp.getString("cat_title","Dream Meanings").toUpperCase().replace(".","")+"!!",false);
        dialog.show();
        MobileAds mobileAds = new MobileAds(this);
        mobileAds.loadBannerAd(findViewById(R.id.adView3));
        String img = sp.getString("cat_img","");
        Glide.with(this).load(BASE_URL+img).into(image3);
        GridLayoutManager linearLayoutManager = new GridLayoutManager(this,1);
        rv_products.setLayoutManager(linearLayoutManager);
        subcat_database p_db = subcat_database.getDbInstance(this);
        ArrayList<SubCategory> items = new ArrayList<>();
        products = (ArrayList<SubCategory>) p_db.dao().getAllProducts();
        filteredItems = new ArrayList<>(products);
        for(SubCategory item:products){
            if(Integer.parseInt(item.getCat_id())==cat_id){
                items.add(item);
            }
        }

        if(!items.isEmpty()) {
            ad_products = new Ad_SubCategories(this, items,img);
            rv_products.setAdapter(ad_products);
        }

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
                String searchText = s.toString();
                if(!searchText.isEmpty()){
                    filter(searchText);
                    ad_products = new Ad_SubCategories(Act_Dream_Meanings_subcategories.this, filteredItems,img);
                    rv_products.setAdapter(ad_products);
                }else{
                    ad_products = new Ad_SubCategories(Act_Dream_Meanings_subcategories.this, products,img);
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
            for (SubCategory item : products) {
                if (item.getTitle().toLowerCase().contains(searchText.toLowerCase())) {
                    filteredItems.add(item);
                }
            }
        }

    }
}