package com.meanings.interpretation.journaldictionary.dreamrevealer.Adapters;

import static com.meanings.interpretation.journaldictionary.dreamrevealer.util.Constant.BASE_URL;
import static com.meanings.interpretation.journaldictionary.dreamrevealer.util.Utils.SUBCAT;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.meanings.interpretation.journaldictionary.dreamrevealer.Databases.categories.Category;
import com.meanings.interpretation.journaldictionary.dreamrevealer.R;
import com.meanings.interpretation.journaldictionary.dreamrevealer.util.Utils;

import java.util.ArrayList;

public class Ad_Categories extends RecyclerView.Adapter<Ad_Categories.CategoryViewHolder> {

    Context context;
    ArrayList<Category> items;

    public Ad_Categories(Context context, ArrayList<Category> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CategoryViewHolder(LayoutInflater.from(context).inflate(R.layout.lay_dream_meanings_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.tv_title.setText(items.get(position).getTitle());
        holder.desc.setText(items.get(position).getDescription());
        Glide.with(context).load(BASE_URL +  items.get(position).getImage()).into(holder.iv1);
        holder.itemView.setOnClickListener(view -> {
            SharedPreferences sp = context.getSharedPreferences("BASE_APP",Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putInt("cat_id",items.get(position).getId());
            editor.putString("cat_img",items.get(position).getImage());
            editor.putString("cat_title",items.get(position).getTitle());
            editor.putInt("NAVIGATION",SUBCAT);
            editor.putBoolean("SHOW_AD",false);
            editor.putString("CONTENT",items.get(position).getTitle().toUpperCase().replace(".","")+"!!");
            editor.apply();
            new Utils(context).navigateToLoading();
        });

    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {
        TextView tv_title,desc;
        ImageView iv1;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.title);
            iv1 = itemView.findViewById(R.id.iv1);
            desc = itemView.findViewById(R.id.desc);

        }
    }

}
