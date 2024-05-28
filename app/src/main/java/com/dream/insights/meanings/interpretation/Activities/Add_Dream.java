package com.dream.insights.meanings.interpretation.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;


import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dream.insights.meanings.interpretation.Databases.MyDreams.Dream;
import com.dream.insights.meanings.interpretation.Databases.MyDreams.dream_dao;
import com.dream.insights.meanings.interpretation.Databases.MyDreams.dream_database;
import com.dream.insights.meanings.interpretation.util.KeyboardUtils;
import com.dream.insights.meanings.interpretation.ads.MobileAds;
import com.dream.insights.meanings.interpretation.R;
import com.dream.insights.meanings.interpretation.util.Utils;

import java.util.Calendar;

public class Add_Dream extends AppCompatActivity {
    private int mYear, mMonth, mDay;
    TextView tv_date;
    EditText cat,title,dream;
    CardView save;
    ImageView iv_back;
//    LinearLayout ad;
    RelativeLayout calender;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dream);
        iv_back = findViewById(R.id.iv_back);
        tv_date = findViewById(R.id.tv_date);
        save = findViewById(R.id.save);
        cat = findViewById(R.id.category);
        title = findViewById(R.id.title);
        dream = findViewById(R.id.content);
        calender = findViewById(R.id.calender);
//        ad = findViewById(R.id.ad);

        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        iv_back.setOnClickListener(view -> {
            onBackPressed();
        });
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);


        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        tv_date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                    }
                }, mYear, mMonth, mDay);

        calender.setOnClickListener(view -> {

            datePickerDialog.show();
        });

        dream_database db = dream_database.getDbInstance(Add_Dream.this);
        dream_dao dao = db.dao();

        Intent i = getIntent();
        int item = i.getIntExtra("update-item",-1);
        if(i.getStringExtra("cat_title")!=null){
            tv_date.setText(mDay + "-" + (mMonth + 1) + "-" + mYear);
            cat.setText(i.getStringExtra("cat_title"));
            title.setText(i.getStringExtra("dream_title"));
        }

        if(item!=-1){
            Dream dream1 = null;
            for(int j=0;j<dao.getAllDreams().size();j++){
                if(dao.getAllDreams().get(j).getId()==item){
                    dream1 =dao.getAllDreams().get(j);
                }

            }
            if(dream1!=null){
                tv_date.setText(dream1.getdate());
                cat.setText(dream1.getcategory());
                title.setText(dream1.gettitle());
                dream.setText(dream1.getdream());
            }
        }

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tv_date.getText().toString().isEmpty() || cat.getText().toString().isEmpty()
                || title.getText().toString().isEmpty() || dream.getText().toString().isEmpty()){
                    Toast.makeText(Add_Dream.this, "Please write your dream for your record!", Toast.LENGTH_SHORT).show();
                }else if(dream.getText().toString().length()<10){
                    Toast.makeText(Add_Dream.this, "Please write atleast 10 characters", Toast.LENGTH_SHORT).show();
                }else{
                    if(item!=-1){
                        dao.deleteById(item);
                    }
                    dao.insert(new Dream(tv_date.getText().toString(),cat.getText().toString(),
                            title.getText().toString(),dream.getText().toString()));


                    finish();

                }
            }
        });
//        MobileAds mobileAds = new MobileAds(this);
//        mobileAds.loadBannerAd(findViewById(R.id.adView3));

//        InputMethodManager imm = (InputMethodManager)
//                getSystemService(Context.INPUT_METHOD_SERVICE);
//
//        if (imm.isAcceptingText()) {
//            ad.setVisibility(View.GONE);
//        } else {
//            ad.setVisibility(View.VISIBLE);
//        }

//        KeyboardUtils.addKeyboardToggleListener(this, new KeyboardUtils.SoftKeyboardToggleListener()
//        {
//            @Override
//            public void onToggleSoftKeyboard(boolean isVisible)
//            {
//                Log.d("keyboard", "keyboard visible: "+isVisible);
//                if(isVisible){
//                    ad.setVisibility(View.GONE);
//                }else{
//                    ad.setVisibility(View.VISIBLE);
//                }
//            }
//        });



        setBannerAd();
    }

    private void setBannerAd(){
        new Utils(this).setBannerAd(findViewById(R.id.bannerplacer),10);
    }


}