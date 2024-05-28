package com.dream.insights.meanings.interpretation.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.dream.insights.meanings.interpretation.R;
import com.dream.insights.meanings.interpretation.util.Utils;

public class Act_PaidVersion extends AppCompatActivity {

    TextView pro,ads,privacy,tos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_paid_version);
        pro = findViewById(R.id.pro);
        ads = findViewById(R.id.ads);
        privacy = findViewById(R.id.privacy);
        privacy.setPaintFlags(privacy.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        privacy.setOnClickListener(view -> {
            new Utils(Act_PaidVersion.this).privacy_Policy();
        });

        tos = findViewById(R.id.tos);
        tos.setPaintFlags(tos.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        tos.setOnClickListener(view -> {
            new Utils(Act_PaidVersion.this).tos();
        });
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        pro.setOnClickListener(view -> {
            Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.dialog_ad_free);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            ImageView iv_close = dialog.findViewById(R.id.iv_close);
            TextView privacy = dialog.findViewById(R.id.privacy);
            TextView tos = dialog.findViewById(R.id.tos);
            iv_close.setOnClickListener(viw->{
                dialog.dismiss();
            });
            privacy.setPaintFlags(privacy.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            tos.setPaintFlags(tos.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

            privacy.setOnClickListener(viw -> {
                new Utils(Act_PaidVersion.this).privacy_Policy();
            });

            tos.setOnClickListener(viw -> {
                new Utils(Act_PaidVersion.this).tos();
            });

            dialog.show();
        });

        ads.setOnClickListener(view -> {
            Intent i = new Intent(Act_PaidVersion.this, Activity_Intro.class);
            startActivity(i);
            finish();
        });
    }

}