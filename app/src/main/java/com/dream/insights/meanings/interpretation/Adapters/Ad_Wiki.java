package com.dream.insights.meanings.interpretation.Adapters;

import static com.dream.insights.meanings.interpretation.util.Constant.BASE_URL;
import static com.dream.insights.meanings.interpretation.util.Utils.WIKI_FINAL;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dream.insights.meanings.interpretation.Databases.wiki.Dream_Wiki;
import com.dream.insights.meanings.interpretation.R;
import com.dream.insights.meanings.interpretation.util.Utils;

import java.util.ArrayList;

public class Ad_Wiki extends RecyclerView.Adapter<Ad_Wiki.CategoryViewHolder> {

    Context context;
    ArrayList<Dream_Wiki> items;

    public Ad_Wiki(Context context, ArrayList<Dream_Wiki> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CategoryViewHolder(LayoutInflater.from(context).inflate(R.layout.lay_wiki_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.tv_title.setText(items.get(position).getTitle());
        Log.d("WIKI_IMAGE",BASE_URL +  items.get(position).getImage());
        Glide.with(context).load(BASE_URL +  items.get(position).getImage()).into(holder.iv1);
        holder.itemView.setOnClickListener(view -> {
            SharedPreferences sp = context.getSharedPreferences("BASE_APP",Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("WIKI_TITLE",items.get(position).getTitle());
            editor.putString("WIKI_IMAGE",items.get(position).getImage());
            editor.putString("WIKI_PM",items.get(position).getPossible_meaning());
            editor.putString("WIKI_DIRECTION",items.get(position).getDirection());
            editor.putInt("NAVIGATION",WIKI_FINAL);
            editor.putBoolean("SHOW_AD",false);
            editor.apply();
            new Utils(context).navigateToLoading();
        });

    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {
        TextView tv_title;
        ImageView iv1;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.title);
            iv1 = itemView.findViewById(R.id.image);

        }
    }

}
