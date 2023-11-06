package com.meanings.interpretation.journaldictionary.dreamrevealer.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.meanings.interpretation.journaldictionary.dreamrevealer.Databases.divine.Divine;
import com.meanings.interpretation.journaldictionary.dreamrevealer.Databases.divine.divine_database;
import com.meanings.interpretation.journaldictionary.dreamrevealer.Databases.dream_calender.Dream_Calender;
import com.meanings.interpretation.journaldictionary.dreamrevealer.Databases.dream_calender.cal_database;
import com.meanings.interpretation.journaldictionary.dreamrevealer.Databases.wiki.Dream_Wiki;
import com.meanings.interpretation.journaldictionary.dreamrevealer.Databases.wiki.wiki_database;
import com.meanings.interpretation.journaldictionary.dreamrevealer.listeners.DatabaseOperations;
import com.meanings.interpretation.journaldictionary.dreamrevealer.Databases.healing.Healing;
import com.meanings.interpretation.journaldictionary.dreamrevealer.Databases.healing.healing_database;
import com.meanings.interpretation.journaldictionary.dreamrevealer.Databases.productdb.Product;
import com.meanings.interpretation.journaldictionary.dreamrevealer.Databases.productdb.product_database;
import com.meanings.interpretation.journaldictionary.dreamrevealer.Databases.subcategoriesdb.SubCategory;
import com.meanings.interpretation.journaldictionary.dreamrevealer.Databases.subcategoriesdb.subcat_database;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class RequestHandler {
    private static final String TAG = RequestHandler.class.getSimpleName();

    private Context context;
    private RequestQueue requestQueue;

    public RequestHandler(Context context) {
        this.context = context;
        requestQueue = Volley.newRequestQueue(context);
    }

    public void makeRequest(String url, int type) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (type == 1) {
                            product_database p_db = product_database.getDbInstance(context);
                            ArrayList<Product> productList = new Gson().fromJson(response, new TypeToken<ArrayList<Product>>() {}.getType());
                            InsertAsyncTask<ArrayList<Product>> insertTask = new InsertAsyncTask<>(productList, new DatabaseOperations<ArrayList<Product>>() {
                                @Override
                                public void insert(ArrayList<Product> data) {
                                    p_db.clearAllTables();
                                    for (Product product : data) {
                                        p_db.dao().insert(product);
                                    }
                                }
                            });
                            insertTask.execute();
                        }else if (type == 2) {
                            subcat_database p_db = subcat_database.getDbInstance(context);
                            ArrayList<SubCategory> subCategoryList = new Gson().fromJson(response, new TypeToken<ArrayList<SubCategory>>() {}.getType());
                            InsertAsyncTask<ArrayList<SubCategory>> insertTask = new InsertAsyncTask<>(subCategoryList, new DatabaseOperations<ArrayList<SubCategory>>() {
                                @Override
                                public void insert(ArrayList<SubCategory> data) {
                                    p_db.clearAllTables();
                                    for (SubCategory subCategory : data) {
                                        p_db.dao().insert(subCategory);
                                    }
                                }
                            });
                            insertTask.execute();
                        }else if(type==3){
                            try {
                                JSONObject object = new JSONObject(response);
                                if(!object.isNull("content")){
                                    SharedPreferences sp = context.getSharedPreferences("BASE_APP",Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sp.edit();
                                    editor.putString("affirmation",object.getString("content"));
                                    editor.apply();
                                }
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }

                        }else if(type==4){
                            try {
                                JSONObject object = new JSONObject(response);
                                if(!object.isNull("content")){
                                    SharedPreferences sp = context.getSharedPreferences("BASE_APP",Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sp.edit();
                                    editor.putString("physcological",object.getString("content"));
                                    editor.apply();
                                }
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }

                        }else if(type==5){
                            healing_database p_db = healing_database.getDbInstance(context);
                            Log.d("HEALING-ITEMS",response);
                            ArrayList<Healing> productList = new ArrayList<>();
                            try {
                                JSONArray jsonArray = new JSONArray(response);
                                for (int i=0;i<jsonArray.length();i++){
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    productList.add(new Healing(object.getInt("id"),object.getString("image"),
                                            object.getString("title"),object.getString("music_file"),"",object.getString("size")));
                                }
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }

                            InsertAsyncTask<ArrayList<Healing>> insertTask = new InsertAsyncTask<>(productList, new DatabaseOperations<ArrayList<Healing>>() {
                                @Override
                                public void insert(ArrayList<Healing> data) {
                                    p_db.clearAllTables();
                                    for (Healing product : data) {
                                        p_db.dao().insert(product);
                                    }
                                }
                            });
                            insertTask.execute();

                        }else if(type==6){
                            divine_database p_db = divine_database.getDbInstance(context);
                            Log.d("DIVINE-ITEMS",response);
                            ArrayList<Divine> productList = new Gson().fromJson(response, new TypeToken<ArrayList<Divine>>() {}.getType());


                            InsertAsyncTask<ArrayList<Divine>> insertTask = new InsertAsyncTask<>(productList, new DatabaseOperations<ArrayList<Divine>>() {
                                @Override
                                public void insert(ArrayList<Divine> data) {
                                    p_db.clearAllTables();
                                    for (Divine product : data) {
                                        p_db.dao().insert(product);
                                    }
                                }
                            });
                            insertTask.execute();
                        }else if(type==7){
                            cal_database p_db = cal_database.getDbInstance(context);
                            Log.d("CALENDER-ITEMS",response);
                            ArrayList<Dream_Calender> productList = new Gson().fromJson(response, new TypeToken<ArrayList<Dream_Calender>>() {}.getType());
                                                        InsertAsyncTask<ArrayList<Dream_Calender>> insertTask = new InsertAsyncTask<>(productList, new DatabaseOperations<ArrayList<Dream_Calender>>() {
                                @Override
                                public void insert(ArrayList<Dream_Calender> data) {
                                    p_db.clearAllTables();
                                    for (Dream_Calender product : data) {
                                        p_db.dao().insert(product);
                                    }
                                }
                            });
                            insertTask.execute();
                        }else if(type==8){
                            wiki_database p_db = wiki_database.getDbInstance(context);
                            Log.d("WIKI-ITEMS",response);
                            ArrayList<Dream_Wiki> productList = new Gson().fromJson(response, new TypeToken<ArrayList<Dream_Wiki>>() {}.getType());
                            InsertAsyncTask<ArrayList<Dream_Wiki>> insertTask = new InsertAsyncTask<>(productList, new DatabaseOperations<ArrayList<Dream_Wiki>>() {
                                @Override
                                public void insert(ArrayList<Dream_Wiki> data) {
                                    p_db.clearAllTables();
                                    for (Dream_Wiki product : data) {
                                        p_db.dao().insert(product);
                                    }
                                }
                            });
                            insertTask.execute();
                        }


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
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, 3, 1.2f));


        Volley.newRequestQueue(context).add(stringRequest);


    }



}
