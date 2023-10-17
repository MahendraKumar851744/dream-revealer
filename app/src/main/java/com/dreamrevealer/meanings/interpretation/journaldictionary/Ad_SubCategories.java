package com.dreamrevealer.meanings.interpretation.journaldictionary;

import static com.dreamrevealer.meanings.interpretation.journaldictionary.Constant.BASE_URL;
import static com.dreamrevealer.meanings.interpretation.journaldictionary.Utils.INTERPRET;
import static com.dreamrevealer.meanings.interpretation.journaldictionary.Utils.PHYSCOLOGICAL;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dreamrevealer.meanings.interpretation.journaldictionary.Activities.Act_Dream_Interpretation;
import com.dreamrevealer.meanings.interpretation.journaldictionary.Databases.subcategoriesdb.SubCategory;

import java.util.ArrayList;
import java.util.List;

public class Ad_SubCategories extends RecyclerView.Adapter<Ad_SubCategories.CategoryViewHolder> {

    Context context;
    ArrayList<SubCategory> items;
    String image;

    public void addAll(List<SubCategory> newItems) {
        items.addAll(newItems);
        notifyDataSetChanged();
    }

    public Ad_SubCategories(Context context, ArrayList<SubCategory> items,String image) {
        this.context = context;
        this.items = items;
        this.image = image;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CategoryViewHolder(LayoutInflater.from(context).inflate(R.layout.lay_sub_dream_meanings_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.tv_title.setText(items.get(position).getTitle());
        Glide.with(context).load(BASE_URL + image).into(holder.iv1);
        holder.itemView.setOnClickListener(view -> {
            SharedPreferences sp = context.getSharedPreferences("BASE_APP",Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putInt("subcat_id",items.get(position).getId());
            editor.putString("subcat_title",items.get(position).getTitle());
            editor.putInt("NAVIGATION",INTERPRET);
            editor.putString("CONTENT","MEANING OF "+ items.get(position).getTitle().toUpperCase().replace(".","")+"!!");
            editor.putBoolean("SHOW_AD",true);
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
            iv1 = itemView.findViewById(R.id.iv1);

        }
    }

}
