package com.dream.insights.meanings.interpretation.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dream.insights.meanings.interpretation.dialogs.CustomDialog;
import com.dream.insights.meanings.interpretation.R;

public class Activity_Intro extends AppCompatActivity implements View.OnClickListener {

    TextView skip,next,prev;
    CardView ok;
    ImageView menu,iv1;
    LinearLayout lay1,lay2;
    RelativeLayout lay3;
    static int current_page = 0;
    CardView cv1,cv2,cv3,cv4,cv5,cv6,cv7,cv8,cv9,cv10,cv11;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        skip = findViewById(R.id.skip);
        iv1 = findViewById(R.id.iv1);
        menu = findViewById(R.id.menu);
        next = findViewById(R.id.next);
        ok = findViewById(R.id.ok);
        prev = findViewById(R.id.prev);
        lay1 = findViewById(R.id.lay1);
        lay2 = findViewById(R.id.lay2);
        lay3 = findViewById(R.id.lay3);

        findViews();
        next.setOnClickListener(view -> {
            current_page = 1;
            updateUi();
        });

        prev.setOnClickListener(view -> {
            current_page = 0;
            updateUi();
        });
        ok.setOnClickListener(view -> {

            Intent i = new Intent(Activity_Intro.this,MainActivity.class);
            startActivity(i);
        });
        skip.setOnClickListener(view -> {
            Intent i = new Intent(Activity_Intro.this,MainActivity.class);
            startActivity(i);
        });
        menu.setOnClickListener(view -> {
            CustomDialog customDialog = new CustomDialog(this,android.R.style.Theme_Black_NoTitleBar_Fullscreen);
            customDialog.show();

        });
        updateUi();
    }

    private void findViews() {
        cv1 = findViewById(R.id.cv1);
        cv2 = findViewById(R.id.cv2);
        cv3 = findViewById(R.id.cv3);
        cv4 = findViewById(R.id.cv4);
        cv5 = findViewById(R.id.cv5);
        cv6 = findViewById(R.id.cv6);
        cv7 = findViewById(R.id.cv7);
        cv8 = findViewById(R.id.cv8);
        cv9 = findViewById(R.id.cv9);
        cv10 = findViewById(R.id.cv10);
        cv11 = findViewById(R.id.cv11);
    }

    private void updateUi(){
        if(current_page==0){
            lay1.setVisibility(View.VISIBLE);
            lay2.setVisibility(View.GONE);
            iv1.setImageResource(R.drawable.mask_group_14);
        }else{
            lay2.setVisibility(View.VISIBLE);
            lay1.setVisibility(View.GONE);
            iv1.setImageResource(R.drawable.mask_group_15);
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if(id==R.id.cv1){

        }
    }


}