package com.dream.insights.meanings.interpretation.Activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.dream.insights.meanings.interpretation.R;
import com.dream.insights.meanings.interpretation.ads.MobileAds;
import com.dream.insights.meanings.interpretation.dialogs.RatingDialog;
import com.dream.insights.meanings.interpretation.util.Utils;

import java.util.Locale;

public class Act_Final_Divine extends AppCompatActivity {

    ImageView iv_back,speaker,iv1;
    ImageView share,like;
    TextView title,content,heading;
    private Handler handler;
    TextToSpeech t1;
    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_final_divine);
        iv_back = findViewById(R.id.iv_back);
        share = findViewById(R.id.share);
        speaker = findViewById(R.id.speaker);
        like = findViewById(R.id.like);
        iv1 = findViewById(R.id.iv1);
        title = findViewById(R.id.title);
        content = findViewById(R.id.content);
        heading = findViewById(R.id.heading);
        SharedPreferences sp = getSharedPreferences("BASE_APP", Context.MODE_PRIVATE);
        String affirmation = sp.getString("DVINE_CONTENT","");
        String titl = sp.getString("DVINE_TITLE","");
        title.setText(titl);
        content.setText(affirmation);
        int type = sp.getInt("DVINE_TYPE",0);
        if(type==0){
            heading.setText("Morning Prayers");
            iv1.setImageDrawable(getDrawable(R.drawable.dawn));
        }else{
            heading.setText("Evening Prayers");
            iv1.setImageDrawable(getDrawable(R.drawable.half_moon));
        }


//        MobileAds mobileAds = new MobileAds(this);
//        mobileAds.loadBannerAd(findViewById(R.id.adView3));
        new Utils(this).setBannerAd(findViewById(R.id.bannerplacer),20);

        handler = new Handler();
        t1 = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if (i == TextToSpeech.SUCCESS) {
                    int result = t1.setLanguage(Locale.US);

                    if (result == TextToSpeech.LANG_MISSING_DATA ||
                            result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", "Language not supported");
                    } else {
                        // Text-to-Speech is initialized successfully
                    }
                } else {
                    Log.e("TTS", "Initialization failed");
                }
            }
        });
        t1.setSpeechRate(0.8f);
        speaker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                t1.speak(affirmation, TextToSpeech.QUEUE_FLUSH, null, TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (!t1.isSpeaking()) {
                            startTextToSpeech(affirmation);
                        }
                    }
                }, 500);
            }
        });

        share.setOnClickListener(view -> {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, "Divine Prayer:\n" + affirmation);
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
        });


        like.setOnClickListener(view -> {
            RatingDialog customDialog = new RatingDialog(this);
            customDialog.show();
        });
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        iv_back.setOnClickListener(view -> {
            getOnBackPressedDispatcher().onBackPressed();
        });
    }
    public void onPause(){

        super.onPause();
        if(t1 !=null){
            t1.stop();
            t1.shutdown();
        }
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
    private void startTextToSpeech(String affirmation) {
        t1 = new TextToSpeech(Act_Final_Divine.this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if (i == TextToSpeech.SUCCESS) {
                    t1.setLanguage(Locale.US);
                    // Start speaking
                    t1.speak(affirmation, TextToSpeech.QUEUE_FLUSH, null, TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID);
                }
            }
        });
    }
}