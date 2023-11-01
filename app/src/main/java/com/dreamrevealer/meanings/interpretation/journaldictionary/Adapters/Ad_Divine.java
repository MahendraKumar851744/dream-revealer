package com.dreamrevealer.meanings.interpretation.journaldictionary.Adapters;

import static com.dreamrevealer.meanings.interpretation.journaldictionary.util.Constant.BASE_URL;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ShareActionProvider;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dreamrevealer.meanings.interpretation.journaldictionary.Databases.divine.Divine;
import com.dreamrevealer.meanings.interpretation.journaldictionary.Databases.healing.Healing;
import com.dreamrevealer.meanings.interpretation.journaldictionary.Databases.healing.Healing_dao;
import com.dreamrevealer.meanings.interpretation.journaldictionary.Databases.healing.healing_database;
import com.dreamrevealer.meanings.interpretation.journaldictionary.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Ad_Divine extends RecyclerView.Adapter<Ad_Divine.CategoryViewHolder> {

    Context context;
    ArrayList<Divine> items;
    int type;

    public Ad_Divine(Context context, ArrayList<Divine> items,int type) {
        this.context = context;
        this.items = items;
        this.type = type;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CategoryViewHolder(LayoutInflater.from(context).inflate(R.layout.lay_healing_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.tv_title.setText(items.get(position).getTitle());
        holder.desc.setText(items.get(position).getContent());
        if(type==1){

        }else{

        }
        holder.playNow.setOnClickListener(view -> {
            SharedPreferences sp = context.getSharedPreferences("BASE_APP",Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
//            editor.putString("")
            editor.apply();
        });

    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {
        TextView tv_title,desc;
        ImageView iv1;
        CardView playNow;
        ProgressBar progressBar;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.title);
            iv1 = itemView.findViewById(R.id.iv1);
            desc = itemView.findViewById(R.id.desc);
            playNow = itemView.findViewById(R.id.playNow);
            progressBar = itemView.findViewById(R.id.progressBar);

        }
    }





}
