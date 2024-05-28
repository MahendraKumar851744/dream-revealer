package com.dream.insights.meanings.interpretation.Activities;

import static com.dream.insights.meanings.interpretation.util.Constant.GET_ADS;
import static com.dream.insights.meanings.interpretation.util.Constant.GET_AFFIRMATION;
import static com.dream.insights.meanings.interpretation.util.Constant.GET_CALENDER;
import static com.dream.insights.meanings.interpretation.util.Constant.GET_CATEGORIES;
import static com.dream.insights.meanings.interpretation.util.Constant.GET_DIVINE;
import static com.dream.insights.meanings.interpretation.util.Constant.GET_HEALINGS;
import static com.dream.insights.meanings.interpretation.util.Constant.GET_PHYSCOLOGICAL_FACT;
import static com.dream.insights.meanings.interpretation.util.Constant.GET_PRODUCTS;
import static com.dream.insights.meanings.interpretation.util.Constant.GET_SUBCATEGORIES;
import static com.dream.insights.meanings.interpretation.util.Constant.GET_WIKI;
import static com.dream.insights.meanings.interpretation.util.Utils.MAIN_ACTIVITY;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dream.insights.meanings.interpretation.Databases.ads.Ads;
import com.dream.insights.meanings.interpretation.Databases.ads.ads_database;
import com.dream.insights.meanings.interpretation.listeners.DatabaseOperations;
import com.dream.insights.meanings.interpretation.Databases.categories.Category;
import com.dream.insights.meanings.interpretation.Databases.categories.cat_database;
import com.dream.insights.meanings.interpretation.util.InsertAsyncTask;
import com.dream.insights.meanings.interpretation.listeners.InsertionCallback;
import com.dream.insights.meanings.interpretation.util.NetworkConnection;
import com.dream.insights.meanings.interpretation.R;
import com.dream.insights.meanings.interpretation.util.RequestHandler;
import com.dream.insights.meanings.interpretation.util.Utils;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class Act_splash extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_splash);
        RequestHandler requestHandler = new RequestHandler(this);
        requestHandler.makeRequest(GET_PRODUCTS,1);
        requestHandler.makeRequest(GET_SUBCATEGORIES,2);
        requestHandler.makeRequest(GET_AFFIRMATION,3);
        requestHandler.makeRequest(GET_PHYSCOLOGICAL_FACT,4);
        requestHandler.makeRequest(GET_HEALINGS,5);
        requestHandler.makeRequest(GET_DIVINE,6);
        requestHandler.makeRequest(GET_CALENDER,7);
        requestHandler.makeRequest(GET_WIKI,8);
        getAdsData(this);
        getData(this);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(uiOptions);
        decorView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
                decorView.setSystemUiVisibility(uiOptions);
            }
        });
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
    }
    public void getAdsData(Context context){
        if(NetworkConnection.checkNetworkStatus(context)){
            String url = GET_ADS;
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("ADS",response);
                            ads_database p_db = ads_database.getDbInstance(context);
                            ArrayList<Ads> productList = new Gson().fromJson(response, new TypeToken<ArrayList<Ads>>() {}.getType());
                            InsertAsyncTask<ArrayList<Ads>> insertTask = new InsertAsyncTask<>(productList, new DatabaseOperations<ArrayList<Ads>>() {
                                @Override
                                public void insert(ArrayList<Ads> data) {
                                    p_db.clearAllTables();
                                    for (Ads product : data) {
                                        p_db.dao().insert(product);
                                    }
                                }
                            });
                            insertTask.execute();

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            try {
                                Log.d("APPLICATION", error.getMessage());
                            }catch (Exception e){
                                Log.d("APPLICATION", e.getMessage());
                            }
                        }
                    });
                stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, 5, 1.5f));


            Volley.newRequestQueue(context).add(stringRequest);
        }else{
            Toast.makeText(context, "Check Your Network Connection", Toast.LENGTH_SHORT).show();
        }
    }
    private void getData(Context context){
        if(NetworkConnection.checkNetworkStatus(context)){
            String url = GET_CATEGORIES;
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("APPLICATION",response);
                            cat_database p_db = cat_database.getDbInstance(context);
                            ArrayList<Category> productList = new Gson().fromJson(response, new TypeToken<ArrayList<Category>>() {}.getType());
                            InsertAsyncTask<ArrayList<Category>> insertTask = new InsertAsyncTask<>(productList, new DatabaseOperations<ArrayList<Category>>() {
                                @Override
                                public void insert(ArrayList<Category> data) {
                                    p_db.clearAllTables();
                                    for (Category product : data) {
                                        p_db.dao().insert(product);
                                    }
                                }
                            }, new InsertionCallback() {
                                @Override
                                public void onInsertionComplete() {
                                    SharedPreferences sp = getSharedPreferences("BASE_APP",MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sp.edit();
                                    boolean bool = sp.getBoolean("Intro",false);
                                    if(bool){
                                        editor.putString("CONTENT","Discover the hidden messages in your dreams!");
                                        editor.putBoolean("SHOW_AD",false);
                                        editor.putInt("NAVIGATION",MAIN_ACTIVITY);
                                        editor.apply();
                                        new Utils(Act_splash.this).navigateToLoading();
                                    }else{

                                        Intent i = new Intent(Act_splash.this, Activity_Intro.class);
                                        startActivity(i);
                                        finish();
                                    }
                                }
                            });
                            insertTask.execute();

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            try {
                                Log.d("APPLICATION", error.getMessage());
                            }catch (Exception e){
                                Log.d("APPLICATION", e.getMessage());
                            }
                        }
                    });
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, 5, 1.5f));


            Volley.newRequestQueue(context).add(stringRequest);
        }else{
            Toast.makeText(context, "Check Your Network Connection", Toast.LENGTH_SHORT).show();
        }
    }


}