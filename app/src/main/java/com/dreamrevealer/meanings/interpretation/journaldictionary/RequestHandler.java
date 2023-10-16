package com.dreamrevealer.meanings.interpretation.journaldictionary;

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
import com.dreamrevealer.meanings.interpretation.journaldictionary.Databases.productdb.Product;
import com.dreamrevealer.meanings.interpretation.journaldictionary.Databases.productdb.product_database;
import com.dreamrevealer.meanings.interpretation.journaldictionary.Databases.subcategoriesdb.SubCategory;
import com.dreamrevealer.meanings.interpretation.journaldictionary.Databases.subcategoriesdb.subcat_database;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

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
                        if(type==1){
                            product_database p_db = product_database.getDbInstance(context);
                            p_db.clearAllTables();
                            ArrayList<Product> productList = new ArrayList<>();
                            Gson gson = new Gson();
                            productList = gson.fromJson(response, new TypeToken<ArrayList<Product>>() {}.getType());
                            for(int i=0;i<productList.size();i++){
                                Product product = productList.get(i);
                                p_db.dao().insert(product);
                            }

                        }else if(type==2){
                            subcat_database p_db = subcat_database.getDbInstance(context);
                            p_db.clearAllTables();
                            ArrayList<SubCategory> productList = new ArrayList<>();
                            Gson gson = new Gson();
                            productList = gson.fromJson(response, new TypeToken<ArrayList<SubCategory>>() {}.getType());
                            for(int i=0;i<productList.size();i++){
                                SubCategory product = productList.get(i);
                                p_db.dao().insert(product);
                            }
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
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, 3, 1.5f));


        Volley.newRequestQueue(context).add(stringRequest);


    }
}
