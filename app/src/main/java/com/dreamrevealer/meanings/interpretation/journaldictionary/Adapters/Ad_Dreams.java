package com.dreamrevealer.meanings.interpretation.journaldictionary.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dreamrevealer.meanings.interpretation.journaldictionary.Activities.Add_Dream;
import com.dreamrevealer.meanings.interpretation.journaldictionary.Databases.MyDreams.Dream;
import com.dreamrevealer.meanings.interpretation.journaldictionary.Databases.MyDreams.dream_database;
import com.dreamrevealer.meanings.interpretation.journaldictionary.listeners.OnClickListener;
import com.dreamrevealer.meanings.interpretation.journaldictionary.R;

import java.util.ArrayList;

public class Ad_Dreams extends RecyclerView.Adapter<Ad_Dreams.CategoryViewHolder> {

    Context context;
    ArrayList<Dream> items;
    OnClickListener listener;

    public Ad_Dreams(Context context, ArrayList<Dream> items,OnClickListener listener) {
        this.context = context;
        this.items = items;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CategoryViewHolder(LayoutInflater.from(context).inflate(R.layout.lay_dream_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.tv_title.setText(items.get(position).gettitle());
        holder.cat.setText("Categories : "+items.get(position).getcategory());
        holder.date.setText("Date : "+items.get(position).getDate());
        holder.update.setOnClickListener(view -> {
            Intent i = new Intent(context, Add_Dream.class);
            i.putExtra("update-item",items.get(position).getId());
            context.startActivity(i);
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dream_database db = dream_database.getDbInstance(context);
                db.dao().delete(items.get(position));
                listener.onDelete();
            }
        });
    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {
        TextView tv_title,cat,date;
        ImageView update,delete;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.title);
            date = itemView.findViewById(R.id.date);
            update = itemView.findViewById(R.id.update);
            delete = itemView.findViewById(R.id.delete);
            cat = itemView.findViewById(R.id.cat);

        }
    }

}
