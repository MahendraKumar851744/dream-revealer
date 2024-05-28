package com.dream.insights.meanings.interpretation.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.dream.insights.meanings.interpretation.Activities.Act_Final_Divine;
import com.dream.insights.meanings.interpretation.Databases.divine.Divine;
import com.dream.insights.meanings.interpretation.R;

import java.util.ArrayList;

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
        return new CategoryViewHolder(LayoutInflater.from(context).inflate(R.layout.lay_divine_item, parent, false));
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.tv_title.setText(items.get(position).getTitle());
        holder.desc.setText(items.get(position).getContent());
        if(type==1){
            holder.iv1.setImageDrawable(context.getResources().getDrawable(R.drawable.half_moon));
        }else{
            holder.iv1.setImageDrawable(context.getResources().getDrawable(R.drawable.dawn));
        }
        holder.playNow.setOnClickListener(view -> {
            SharedPreferences sp = context.getSharedPreferences("BASE_APP",Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("DVINE_TITLE",items.get(position).getTitle());
            editor.putString("DVINE_CONTENT",items.get(position).getContent());
            editor.putInt("DVINE_TYPE",items.get(position).getMorning_or_evening());
            editor.apply();
            Intent i = new Intent(context, Act_Final_Divine.class);
            context.startActivity(i);
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
