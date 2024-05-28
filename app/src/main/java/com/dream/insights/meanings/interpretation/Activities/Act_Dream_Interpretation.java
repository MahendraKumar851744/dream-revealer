package com.dream.insights.meanings.interpretation.Activities;

import static com.dream.insights.meanings.interpretation.util.Constant.BASE_URL;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.dream.insights.meanings.interpretation.Databases.ads.Ads;
import com.dream.insights.meanings.interpretation.Databases.ads.ads_database;
import com.dream.insights.meanings.interpretation.Databases.productdb.Product;
import com.dream.insights.meanings.interpretation.Databases.productdb.product_database;
import com.dream.insights.meanings.interpretation.dialogs.FeedBackDialog;
import com.dream.insights.meanings.interpretation.ads.MobileAds;
import com.dream.insights.meanings.interpretation.R;
import com.dream.insights.meanings.interpretation.dialogs.RatingDialog;
import com.dream.insights.meanings.interpretation.util.Utils;

import java.util.Locale;

public class Act_Dream_Interpretation extends AppCompatActivity implements TextToSpeech.OnInitListener {
    TextToSpeech t1;
    TextView title,pm,eo,dream_title;
    ImageView iv_back,speak,info,add,like,dislike;
    String cat_title;
    CardView cv_add;
    private Handler handler;
    private static final int SPEAKING_CHECK_DELAY = 300;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_dream_interpretation);
        speak = findViewById(R.id.speak);
        title = findViewById(R.id.title);
        pm = findViewById(R.id.pm);
        eo = findViewById(R.id.eo);
        dream_title = findViewById(R.id.dream_title);
        iv_back = findViewById(R.id.iv_back);
        info = findViewById(R.id.info);
        add = findViewById(R.id.add);
        cv_add = findViewById(R.id.cv_add);
        like = findViewById(R.id.like);
        dislike = findViewById(R.id.dislike);
        product_database p_db = product_database.getDbInstance(this);
        SharedPreferences sp = getSharedPreferences("BASE_APP",MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        int cat_id = sp.getInt("cat_id",0);
        int subcat_id = sp.getInt("subcat_id",0);
        cat_title = sp.getString("cat_title","");
        String subcat_title = sp.getString("subcat_title","");


        Product product = null;
        for(Product p:p_db.dao().getAllProducts()){
            if(p.getCat_id()==cat_id && p.getSubcat_id()==subcat_id){
                product = p;
            }
        }
        t1 = new TextToSpeech(this, this);

        t1.setSpeechRate(0.8f);
        info.setOnClickListener(view -> {
            Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.dialog3);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            ImageView iv_close = dialog.findViewById(R.id.iv_close);
            CardView ok = dialog.findViewById(R.id.ok);
            iv_close.setOnClickListener(viw->{
                dialog.dismiss();
            });
            ok.setOnClickListener(viw->{
                dialog.dismiss();
            });
            dialog.show();
        });
        iv_back.setOnClickListener(view -> {
            onBackPressed();
        });
        add.setOnClickListener(view -> {
            intentToDreamBook();
        });
        cv_add.setOnClickListener(view -> {
            intentToDreamBook();
        });
        like.setOnClickListener(view -> {
            RatingDialog customDialog = new RatingDialog(Act_Dream_Interpretation.this);
            customDialog.show();
        });
        dislike.setOnClickListener(view -> {
            FeedBackDialog customDialog = new FeedBackDialog(Act_Dream_Interpretation.this);
            customDialog.show();
        });
        if(product!=null){
            title.setText(cat_title);
            dream_title.setText(subcat_title);
            if(getRandomNumber(0,1)==0){
                pm.setText(product.getPm1());
                eo.setText(product.getEo1());
            }else{
                pm.setText(product.getPm2());
                eo.setText(product.getEo2());
            }


        }
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        handler = new Handler();
        speak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String content = "Possible Meaning."+pm.getText().toString()+"Expert Opinion."+eo.getText().toString();
                t1.speak(content, TextToSpeech.QUEUE_FLUSH, null,null);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (!t1.isSpeaking()) {
                            startTextToSpeech(content);
                        }
                    }
                }, SPEAKING_CHECK_DELAY);


            }
        });

        setBannerAd();
    }
    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    public void onPause(){
        super.onPause();
        if(t1 !=null){
            t1.stop();
            t1.shutdown();
        }
    }
    private void intentToDreamBook(){
        Intent i = new Intent(this, Add_Dream.class);
        i.putExtra("cat_title",cat_title);
        i.putExtra("dream_title",dream_title.getText().toString());
        startActivity(i);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(t1 !=null){
            t1.stop();
            t1.shutdown();
        }
        handler.removeCallbacks(null);
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int result = t1.setLanguage(Locale.US);

            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
            }

        } else {
        }
    }

    private void startTextToSpeech(String content) {
        t1 = new TextToSpeech(Act_Dream_Interpretation.this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if (i == TextToSpeech.SUCCESS) {
                    t1.setLanguage(Locale.US);
                   t1.speak(content, TextToSpeech.QUEUE_FLUSH, null, TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID);
                }
            }
        });
    }

    private void setBannerAd(){
//        MobileAds mobileAds = new MobileAds(this);
//        mobileAds.loadBannerAd(findViewById(R.id.adView3));
        new Utils(this).setBannerAd(findViewById(R.id.bannerplacer),6);
    }
}